package com.luzhanov.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Calendar;
import java.util.Objects;
import java.util.TreeMap;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class LambdaHandler implements RequestHandler<Message, String> {

    public String handleRequest(Message message, Context context) {
        context.getLogger().log("Request ID: " + context.getAwsRequestId()
                + ", input: " + Objects.toString(message)
                + ", time: " + Calendar.getInstance().getTimeInMillis());

        if (message == null || isBlank(message.getData())) {
            String errorMessage = "Error: input is empty";
            context.getLogger().log(errorMessage);
            return errorMessage;
        }

        String[] words = message.getData().split(" ");
        TreeMap<String, Integer> wordsCount = new TreeMap<>();
        for (String currentWord : words){
            wordsCount.merge(currentWord.toLowerCase(), 1, Integer::sum);
        }

        return wordsCount.toString();
    }
}
