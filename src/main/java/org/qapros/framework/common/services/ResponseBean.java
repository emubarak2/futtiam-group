package org.qapros.framework.common.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * this class represent the response bean for the json format for gitHub "https://jobs.github.com/positions.json" API
 * @author eyad mubarak
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {

    @JsonProperty
    String id;

    @JsonProperty
    String type;

    @JsonProperty
    String url;

    @JsonProperty("created_at")
    String createdAt;

    String company;

    @JsonProperty("company_url")
    String companyUrl;

    String location;

    String title;

    String description;

    @JsonProperty("how_to_apply")
    String howToApply;

    @JsonProperty("company_logo")
    String companyLogo;

}
