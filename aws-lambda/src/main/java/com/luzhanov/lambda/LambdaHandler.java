package com.luzhanov.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Calendar;
import java.util.TreeMap;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class LambdaHandler implements RequestHandler<String, String> {

    public String handleRequest(String input, Context context) {
        context.getLogger().log("Request ID: " + context.getAwsRequestId()
                + ", input: " + input
                + ", time: " + Calendar.getInstance().getTimeInMillis());

        if (isBlank(input)) {
            String errorMessage = "Error: input is empty";
            context.getLogger().log(errorMessage);
            return errorMessage;
        }

        String[] words = input.split(" ");
        TreeMap<String, Integer> wordsCount = new TreeMap<>();
        for (String currentWord : words){
            wordsCount.merge(currentWord.toLowerCase(), 1, Integer::sum);
        }

        return wordsCount.toString();
    }
}
