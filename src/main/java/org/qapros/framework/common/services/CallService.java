package org.qapros.framework.common.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.java.Log;
import org.apache.commons.io.IOUtils;
import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.Post;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Log
public class CallService {

    public String method;
    public String requestBody;
    public Map<String, String> requestHeaders;
    static public String responseCode;


    /**
     * Do Post API call method
     *
     * @return status code , ex:200, 400
     */
    public int doPostCall(String url) {
        Post post = Http.post(url, getRequestBody())
                .header("Accept", "application/json")
                .header("Content-Type", "application/json");

        return post.responseCode();

    }


    /**
     * do Get API call method
     *
     * @return object from the called api response
     */
    public List<ResponseBean> doGetCall(String url) {

        Get get = Http.get(url);
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(get.getInputStream(), writer, "UTF8");
        } catch (Exception exception) {
            exception.getMessage();
        }
        return stringConverterToObjectList(writer.toString());

    }


    public String doGetCallForHtml(String url) {

        Get get = Http.get(url);
        responseCode = String.valueOf(get.responseCode());
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(get.getInputStream(), writer, "UTF8");
        } catch (Exception exception) {
            exception.getMessage();
        }
        return objectConverterToJSONString(writer.toString());

    }


    /**
     * convert any object to json string format
     *
     * @param payload the payload to convert to json string
     * @return string formatted in json nods
     */
    public String objectConverterToJSONString(Object payload) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(payload);
        } catch (Exception exception) {
            log.info("objectConverterToJSONString : " + exception);
        }
        return null;
    }

    /**
     * convert any string formatted in json nods to json object
     *
     * @param jsonString
     * @return object that represent the json string
     */
    public List<ResponseBean> stringConverterToObjectList(String jsonString) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

            return objectMapper.readValue(jsonString, new TypeReference<List<ResponseBean>>(){});
        } catch (Exception exception) {
            log.info("objectConverterToJSONString : " + exception);
        }
        return null;
    }


    /**
     * convert any string formatted in json nods to json object in the provided class
     *
     * @param jsonString
     * @param myclass    represent the class to cast the json object to
     * @return object that represent the json string
     */
    public Object stringConverterToObjectList(String jsonString, Class myclass) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

            return objectMapper.readValue(jsonString, myclass);
        } catch (Exception exception) {
            log.info("objectConverterToJSONString :  " + exception);
        }
        return null;
    }

    public Object getCallService(String url, String subResource) {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "APIHelloFreshConsumer");
        headers.put("Content-Type", "application/json");
        String callUrl = url + subResource;
        setRequestHeaders(headers);
        return doGetCall(callUrl);
    }

    public Object postCallService(String url, String subResource, ResponseBean results) {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "APIHelloFreshConsumer");
        headers.put("Content-Type", "application/json");
        setRequestBody(objectConverterToJSONString(results));
        setRequestHeaders(headers);
        String callUrl = url + subResource;
        return doPostCall(callUrl);
    }
}
