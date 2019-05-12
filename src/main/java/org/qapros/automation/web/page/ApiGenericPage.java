package org.qapros.automation.web.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.qapros.framework.common.services.CallService;
import org.qapros.framework.common.services.ResponseBean;

import java.util.ArrayList;
import java.util.List;


/**
 * this class handle the check for response of API requests
 *
 * @author eyad mubarak
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Log
public class ApiGenericPage {

    public static String responseCode;
    public static String responseString = "";
    public List<ResponseBean> responseList = new ArrayList<>();
    public String urlCalled = "";

    /**
     * for adding a query param to the base url
     * @param queryParam the query param key and value
     */
    public void addQueryParamToUrl(String queryParam) {
        urlCalled = urlCalled + queryParam;
    }

    /**
     * set the value of the base Api url
     * @param url the value of the base url
     */
    public void fillBaseUrl(String url) {
        urlCalled = url;
    }

    /**
     * send a request of type get for the supplied url
     *
     * @param url the value of the url to be requested
     */
    public void requestApiGetMethod(String url) {
        if (!url.isEmpty()) {
            urlCalled = url;
        }
        CallService callService = new CallService();

        if (url.contains(".json")) {
            List<ResponseBean> response = callService.doGetCall(url);
            this.responseList = response;
        } else {
            responseString = callService.doGetCallForHtml(url);

        }
        urlCalled = "";
    }


    /**
     * validate response to be of type HTML by checking the main tag <!DOCTTYPE html>
     */
    public void validateResponseInHtmlFormat() {
        String temp = responseString;
        responseString = "";
        if (!temp.contains("<!DOCTYPE html>")) {
            Assert.assertTrue("The response format was not in HTML", false);
        }
    }

    /**
     * validate the response to be of type json, by checking that no html <!DOCTYPE html> tag does exist in the response
     */
    public void validateResponseInJsonFormat() {
        String temp = responseString;
        responseString = "";
        if (temp.contains("<!DOCTYPE html>")) {
            Assert.assertTrue("The response format was not in Json", false);
        }
    }

    /**
     * this check the response body size and verify it against the supplied argument
     */
    public void validateResponseBodySize() {
        if (responseList.size() < 50) {
            Assert.assertTrue("The response body didn't contains 50 record", false);
        }
    }

    /**
     *  this method verify that new page is displayed after changing page number
     * @param url the base url to change the page number for
     */
    public void validateNewPageIsDisplayed(String url) {
        List<ResponseBean> firstList = new ArrayList<>(responseList);

        CallService callService = new CallService();

        List<ResponseBean> secondList = callService.doGetCall(url);

        for (ResponseBean r : firstList) {
            for (ResponseBean s : secondList) {
                if (r.getId().equals(s.getId())) {
                    Assert.assertTrue("We dont' have unique results between pages, pagination is not working, in id " + s.getId(), false);
                }
            }
        }

    }

    /**
     * check the description field contains the supplied arguement
     * @param text the value to check against in the description field
     */
    public void validateDescriptionContains(String text) {
        for (ResponseBean r : responseList) {
            System.out.println(r.getDescription());
            if (!r.getDescription().toLowerCase().contains(text.toLowerCase())) {
                Assert.assertTrue("the description body didn't contains the description query param supplied in the url:" + text + " with job id: " + r.getId(), false);
            }
        }
    }


    /**
     * verify the location returned in the response against the supplied argument
     * @param location the value of the location to check against in the response
     */
    public void validateLocationInResponse(String location) {
        for (ResponseBean r : responseList) {
            if (r.getLocation().toLowerCase().contains(location.toLowerCase()))
                return;
        }
        Assert.assertTrue("the location returned didn't match the location supplied in the API url " + location, false);

    }

    /**
     * verify the that no filteratoin took place in the response based on the supplied location value, most likely "anywhere""
     * @param location the value of location to check against in the response records
     */
    public void validateNoLocationFiltration(String location) {
        for (ResponseBean r : responseList) {
            if (r.getLocation().contains(location))
                return;
            Assert.assertTrue("Location filtration took place and it should be: " + location, false);
        }

    }

    /**
     * this method verify that the response contains no records
     */
    public void validateResponseIsEmpty() {
        if (responseList.size() != 0)
            Assert.assertTrue("Response was retrieved when se the location and 'long' and 'lat' query params togather" +
                    "and it should not return any response", false);
    }

    /**
     * validate the response type doesn't contains the supplied type
     * @param type the job post type value to check against in the response
     */
    public void validateResponseType(String type) {

        for (ResponseBean r : responseList) {
            if (r.getType().toLowerCase().contains("part time"))
                Assert.assertTrue("job of type " + type + " was retrieved in the response and it should not be.", false);
        }
    }

    /**
     * this method verify that the response contains text in general, incase the response in html format
     * @param text the value of text to check against
     */
    public void verifyTextInResponse(String text) {

        if (!responseString.contains(text))
            Assert.assertTrue("the response didn't contain the intended text: " + text, false);
    }

    /**
     * this method verify the size of the response retrieved from calling an API
     * @param size the size value to check against
     */
    public void verifyResponseSizeInBody(int size) {

        if (responseList.size() != size)
            Assert.assertTrue("The job posts retrieved in response was greater than : " + size, false);

    }

    /**
     * this method verify the id exists in response
     * @param id the value of the id to check against in the response
     */
    public void verifyIdInResponse(String id) {
        if (!responseList.get(0).getId().equals(id))
            Assert.assertTrue("The id returned in the job post didn't match the expected id:" + id, false);
    }

    /**
     * this verify title in response does exist
     * @param title the value of the title to check against in the response records
     */
    public void verifyTitleInResponse(String title) {
        if (!responseList.get(0).getTitle().equals(title))
            Assert.assertTrue("The title returned in the job post didn't match the expected title:" + title, false);
    }

    /**
     * this verify the code returned in the response after hitting the API url
     * @param code the code to verify the response against
     */
    public void verifyCode(String code) {
        if (!CallService.responseCode.equals(code)) {
            String temp = CallService.responseCode;
            CallService.responseCode = "";
            Assert.assertTrue("The response code didn't match the expected: " + temp, false);
        }
    }

}
