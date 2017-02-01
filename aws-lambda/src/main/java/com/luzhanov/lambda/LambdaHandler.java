package com.luzhanov.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Calendar;
import java.util.HashMap;

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
        HashMap<String, Integer> wordsCount = new HashMap<>();
        for (String s : words){
            //todo: replace with Map.merge()
            if (wordsCount.containsKey(s)) {
                wordsCount.replace(s, wordsCount.get(s) + 1);
            } else {
                wordsCount.put(s, 1);
            }
        }

        return wordsCount.toString();
    }
}
