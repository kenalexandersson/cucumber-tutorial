package com.jeppesen.nimbus.supportcode;


import com.jayway.restassured.response.ValidatableResponse;
import com.jeppesen.nimbus.automation.IsoOutput;
import com.jeppesen.nimbus.automation.RestHandler;

import javax.inject.Inject;
import java.util.List;

public class IsoSupport {

    @Inject
    private RestHandler restHandler;

    public IsoOutput getIsoOutput(String countryCode) {

        ValidatableResponse response = restHandler.performGetRequest(countryCode, "application/json");

        IsoOutput isoOutput = extract(response);

        validate(isoOutput);

        return isoOutput;
    }

    private void validate(IsoOutput isoOutput) {

        if (isoOutput.getMessage().contains("No matching country found")) {
            throw new RuntimeException(isoOutput.getMessage());
        }
    }

    private IsoOutput extract(ValidatableResponse response) {

        List<String> messages = response
                .extract()
                .path("RestResponse.messages");

        IsoOutput output = new IsoOutput();
        output.setMessage(messages.get(1));
        output.setName(response.extract().path("RestResponse.result.name"));
        output.setAlpha3Code(response.extract().path("RestResponse.result.alpha3_code"));

        return output;
    }
}
