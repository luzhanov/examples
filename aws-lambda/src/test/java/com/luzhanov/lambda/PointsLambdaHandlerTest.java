package com.luzhanov.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PointsLambdaHandlerTest {

    private PointsLambdaHandler handler = new PointsLambdaHandler();

    @Ignore
    @Test
    public void testHandleRequestChangeToUppercase() throws Exception {
        Context mockContext = mock(Context.class);

        //todo: set up the mock behavior

        assertThat(handler.handleRequest("aaa", mockContext)).isEqualTo("AAA");
    }

}    





