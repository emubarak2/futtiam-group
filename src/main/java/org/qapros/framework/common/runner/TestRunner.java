package org.qapros.framework.common.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * this is the cucumber running initiator, which generate reports for each feature
 * @author  eyadm@amazon.com
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {
        "json:target/cucumber.json", "pretty:target/cucumber-pretty.txt",
        "usage:target/cucumber-usage.json", "progress" ,
//        "com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"
},
        monochrome = true,
        tags = "@TestType-Smoke",
        glue = {"org.qapros.stepdefs.common","org.qapros.framework.common.runner.TestRunner" })
public class TestRunner {





}
