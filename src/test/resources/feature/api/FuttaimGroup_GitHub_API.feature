@FatoumGroup_GitHubApi
Feature: GitHub API

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline:Base API URL without “.json” and without any search criteria  – general jobs entries data will be retrieved in HTML format
    And request api url with get method: <url>
    And validate response body in html format
    Examples:
      |url|
      |https://jobs.github.com/positions|


  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline:Base API URL with “.json” and without any search criteria  – general jobs entries data will be retrieved in Json format
    And request api url with get method: <url>
    And validate response body in json format
    Examples:
      |url|
      |https://jobs.github.com/positions.json|

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: Pagination param doesn't exist in call URl  – 50 entry will be retrieved in the response body
    And request api url with get method: <url>
    And validate response body contains 50 record
    Examples:
      |url|
      |https://jobs.github.com/positions.json/|

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: Pagination param exist in call URl  – query param present in URL, and will direct user to unique result page
    And request api url with get method: <url>
    And validate pagination param and will direct user to unique result page: <secondUrl>
    Examples:
      |url|secondUrl|
      |https://jobs.github.com/positions.json?page=1|https://jobs.github.com/positions.json?page=2|


  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: description is added to API URL – description query param will make sure that description field contains the supplied keyword in all response results
    And request api url with get method: <url>
    And validate response nods description contains the description query param supplied in the url: <description>
    Examples:
      |url|description|
      |https://jobs.github.com/positions.json?description=MongoDB|MongoDB|

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: Location query param – query param “location” present in URL, and will filter the response job entries to be located in the value provided in the API URL
    And request api url with get method: <url>
    And validate response nods location contains the location query param supplied in the url: <location>
    Examples:
      |url|location|
      |https://jobs.github.com/positions.json?location=germany|germany|


  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: Lat and long query param – query params “lat” and “long” will filter response job entries with the provided latitude and longitude values in the query param in the API url
    And set API base url: <url>
    And add query param to the api url: <lat>
    And add query param to the api url: <long>
    And request constructed API url using get method
    And validate response nods location contains the location query param supplied in the url: <location>
    Examples:
      |url|lat|long|location|
      |https://jobs.github.com/positions.json?|lat=37.3229978|&long=-122.0321823|CA|


  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: Lat and long query param – query params “lat” is present while the “long” query is not present, no filtration will take place on response job entries location data
    And set API base url: <url>
    And add query param to the api url: <lat>
    And request constructed API url using get method
    And validate no location filtration took place: <location>
    Examples:
      |url|lat|location|
      |https://jobs.github.com/positions.json?|lat=37.3229978|anywhere|

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: “lat” query param and long param contain invalid values, no data will be retrieved in the response body
    And set API base url: <url>
    And add query param to the api url: <lat>
    And add query param to the api url: <long>
    And validate the response records size equal to: <size>
    Examples:
      |url|lat|long|size|
      |https://jobs.github.com/positions.json?page=1|&lat=2222|&long=44444|0|

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: Lat and long query params – query params “lat” and “long” query params are present along with “location” query param, no data will be retrieved in the response (empty response body)
    And set API base url: <url>
    And add query param to the api url: <lat>
    And add query param to the api url: <long>
    And add query param to the api url: <location>
    And request constructed API url using get method
    And validate response body is empty
    Examples:
      |url|lat|long|location|
      |https://jobs.github.com/positions.json?|lat=37.3229978|&long=-122.0321823|&location=germany|

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: search query param – the search query param will make sure the searched keyword will be listed in the description field in the response body
    And set API base url: <url>
    And add query param to the api url: <search>
    And add query param to the api url: <page>
    And request constructed API url using get method
    And validate response nods description contains the description query param supplied in the url: <description>
    Examples:
      |url|search|page|description|
      |https://jobs.github.com/positions.json?|search=cleaner|&page=1|cleaner|

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: search query param – the search query param will make sure the search keyword will be listed in the description field in the response body
    And set API base url: <url>
    And add query param to the api url: <search>
    And add query param to the api url: <page>
    And request constructed API url using get method
    And validate response nods description contains the description query param supplied in the url: <description>
    Examples:
      |url|search|page|description|
      |https://jobs.github.com/positions.json?|search=cleaner|&page=1|cleaner|

   @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: full time query param – set full time query param to true will not return job entries with part time type.
    And set API base url: <url>
    And add query param to the api url: <fullTime>
    And add query param to the api url: <page>
    And request constructed API url using get method
    And validate no job with type part time will be retrieved in the response body : <type>
    Examples:
      |url|page|fullTime|type|
      |https://jobs.github.com/positions.json?|page=1r|&full_time=true|part time|

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: description query param: cleaner, one job will be returned and location is remote
    And set API base url: <url>
    And add query param to the api url: <desc>
    And request constructed API url using get method
    And validate response nods description contains the description query param supplied in the url: <description>
    And validate the response records size equal to: <size>
    Examples:
      |url|desc|description|size|
      |https://jobs.github.com/positions.json?page=1|&description=cleaner|cleaner|1|

  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: description query param: cleaner, and location is Germany: empty post will be returned to user, no matching job posting for query params values
    And set API base url: <url>
    And add query param to the api url: <desc>
    And add query param to the api url: <location>
    And validate the response records size equal to: <size>
    Examples:
      |url|desc|location|size|
      |https://jobs.github.com/positions.json?page=1|&description=cleaner|germany|0|



  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: Retrieve single job description post – single job post will be returned related to the job id posted in the request api url
    And set API base url: <url>
    And request constructed API url using get method
    And validate the response records size equal to: <size>
    And validate response nods description contains the description query param supplied in the url: <description>
    And validate response title contains: <title>
    And validate response id equals: <id>
    Examples:
      |url|size|description|title|id|
      |https://jobs.github.com/positions/caf01612-4169-430f-b14a-bda654f241a9.json|1|<p>We are an innovation lab in Washington, DC area working with start-ups|Software Developer (Africa)|caf01612-4169-430f-b14a-bda654f241a9|


  @TestType-Smoke @Priority-p0 @Country-uae
  Scenario Outline: get job post with non existing Id
    And set API base url: <url>
    And request constructed API url using get method for non Json response
    And verify response code is: <responseCode>
    Examples:
      |url|responseCode|
      |https://jobs.github.com/positions/21516|404|
