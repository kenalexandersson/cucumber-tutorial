package com.jeppesen.nimbus;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "@new",
        features = "src/test/resources/feature",
        plugin = {"pretty", "junit:target/cucumber/feature-test-report.xml", "html:target/cucumber/html-report", "usage:target/cucumber/feature-test-usage-report.json"},
        snippets = SnippetType.CAMELCASE
)
public class RunCucumberTest {
}
