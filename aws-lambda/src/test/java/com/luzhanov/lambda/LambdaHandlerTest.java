package com.luzhanov.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LambdaHandlerTest {

    private LambdaHandler handler = new LambdaHandler();

    @Ignore
    @Test
    public void testHandleRequestChangeToUppercase() throws Exception {
        Context mockContext = mock(Context.class);

        //todo: set up the mock behavior

        assertThat(handler.handleRequest("aaa", mockContext)).isEqualTo("AAA");
    }

    //todo: test the blank input

    //todo: test the word count functionality

}    





