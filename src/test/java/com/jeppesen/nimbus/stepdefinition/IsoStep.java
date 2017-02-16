package com.jeppesen.nimbus.stepdefinition;

import com.jeppesen.nimbus.automation.IsoOutput;
import com.jeppesen.nimbus.supportcode.IsoSupport;
import com.jeppesen.nimbus.supportcode.TestSessionIso;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@Singleton
public class IsoStep {

    @Inject
    private IsoSupport isoSupport;

    @Inject
    private TestSessionIso testSessionIso;

    @Given("^I have access to the system$")
    public void iHaveAccessToTheSystem() throws Throwable {
    }

    @When("^I enter the country code \"([^\"]*)\"$")
    public void iEnterTheCountryCode(String countryCode) throws Throwable {

        testSessionIso.setIsoOutput(isoSupport.getIsoOutput(countryCode));
    }

    @Then("^the full name of the country should be \"([^\"]*)\"$")
    public void theFullNameOfTheCountryShouldBe(String expectedCountryName) throws Throwable {

        IsoOutput isoOutput = testSessionIso.getIsoOutput();

        assertThat(isoOutput, is(notNullValue()));
        assertThat(isoOutput.getName(), is(expectedCountryName));
    }

    @Then("^the alpha 3 code should be \"([^\"]*)\"$")
    public void theAlpha3CodeShouldBe(String expectedCode) throws Throwable {

        IsoOutput isoOutput = testSessionIso.getIsoOutput();

        assertThat(isoOutput, is(notNullValue()));
        assertThat(isoOutput.getAlpha3Code(), is(expectedCode));
    }


}
