package org.qapros.stepdefs.api;

import cucumber.api.java.en.And;
import org.qapros.automation.web.page.ApiGenericPage;

/**
 * this class is the steps definition that exist in the features
 * @author eyad mubarak
 */
public class ApiGenericStepDefs extends ApiGenericPage {

    static String  baseUrl ="";
    @And("^request api url with get method: (.*)$")
    public void requestApiGet(String apiUrl) {

        requestApiGetMethod(apiUrl.trim());
    }

    @And("^set API base url: (.*)$")
    public void setBaseUrl(String url) {
        baseUrl = url;
    }

    @And("^add query param to the api url: (.*)$")
    public void addQueryParam(String queryParam) {
        baseUrl = baseUrl + queryParam;

    }

    @And("^request constructed API url using get method$")
    public void requestApiGetMethod() {
        requestApiGet(baseUrl);
        baseUrl="";
    }


    @And("^validate response body in html format")
    public void validateHtmlFormat() {
        validateResponseInHtmlFormat();
    }


    @And("^validate response body in json format")
    public void validateJsonFormat() {
        validateResponseInJsonFormat();
    }

    @And("^validate response body contains 50 record")
    public void validateResponseBodyContainsFiftyRecord() {
        validateResponseBodySize();
    }

    @And("^validate pagination param and will direct user to unique result page: (.*)$")
    public void validateNewPageIsDisplayedToUser(String url) {
        validateNewPageIsDisplayed(url);
    }

    @And("validate response nods description contains the description query param supplied in the url: (.*)$")
    public void validateDescription(String text) {
        validateDescriptionContains(text);
    }

    @And("^validate response nods location contains the location query param supplied in the url: (.*)$")
    public void validateLocation(String location) {
        validateLocationInResponse(location);
    }
    @And("^validate no location filtration took place: (.*)$")
    public void validateNoLocationFilterationTookPlace(String location) {
        validateNoLocationFiltration(location);
    }

    @And("^validate response body is empty")
    public void validateResponseBodyIsEmpty() {
        validateResponseIsEmpty();
    }

    @And("^validate no job with type part time will be retrieved in the response body : (.*)$")
    public void validateReponseType(String type) {
        validateResponseType(type);
    }
    @And("^validate response body contains text: (.*)$")
    public void verifyText(String text) {
       verifyTextInResponse(text);
    }
    @And("^request constructed API url using get method for non Json response")
    public void callGetForNonJson() {
        requestApiGetMethod(baseUrl);
        baseUrl="";
    }

    @And("^validate the response records size equal to: (.*)$")
    public void verifyResponseSize(String size) {
        verifyResponseSizeInBody(Integer.parseInt(size));

    }

    @And("^validate response title contains: (.*)$")
    public void verifyTitle(String title) {
        verifyTitleInResponse(title);

    }
    @And("^validate response id equals: (.*)$")
    public void verifyId(String title) {
        verifyIdInResponse(title);

    }

    @And("^verify response code is: (.*)$")
    public void verifyResponseCode(String code) {
        verifyCode(code);

    }
}



