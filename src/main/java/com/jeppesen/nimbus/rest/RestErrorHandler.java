package com.jeppesen.nimbus.rest;

import javax.ws.rs.core.Response;

public final class RestErrorHandler {


    private RestErrorHandler() {
    }

    public static void handleErrorResponse(Response response) throws RestErrorException {

        if (response == null) {
            throw new IllegalArgumentException("Response must not be null");
        }

        int responseStatusCode = response.getStatus();

        if (responseStatusCode == 401) {
            handleGeneric401(response);

        } else {
            handleUnknown(response);
        }
    }

    private static void handleUnknown(Response response) throws RestErrorException {
        Response.StatusType statusInfo = response.getStatusInfo();
        String message = String.format("The request generated a response of unknown error. The response status code: %s. Reason: %s. Origin of error: %s", response.getStatus(), statusInfo.getReasonPhrase(), statusInfo.getFamily().name());
        throw new RestErrorException(message);
    }

    private static void handleGeneric401(Response response) throws RestErrorException {
        throw new RestErrorException("Response code 401 Unauthorized. Please correct the submitted authorization details");
    }
}
