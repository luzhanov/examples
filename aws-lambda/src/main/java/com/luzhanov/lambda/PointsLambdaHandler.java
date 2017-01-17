package com.luzhanov.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Calendar;

public class PointsLambdaHandler implements RequestHandler<String, String> {

    public String handleRequest(String input, Context context) {
        context.getLogger().log("Request ID: " + context.getAwsRequestId()
                + ", input: " + input
                + ", time: " + Calendar.getInstance().getTimeInMillis());

        return input.toUpperCase();
    }
}
