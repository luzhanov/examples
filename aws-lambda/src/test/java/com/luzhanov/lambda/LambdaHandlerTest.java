package com.luzhanov.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LambdaHandlerTest {

    private LambdaHandler handler = new LambdaHandler();

    @Test
    public void testHandleEmptyResult() throws Exception {
        Context mockContext = mockContext();

        assertThat(handler.handleRequest(new Message(""), mockContext)).isEqualTo("Error: input is empty");
        assertThat(handler.handleRequest(null, mockContext)).isEqualTo("Error: input is empty");
    }

    @Test
    public void testHandleResult() throws Exception {
        Context mockContext = mockContext();

        assertThat(handler.handleRequest(new Message("Test word test second word third"), mockContext))
                .isEqualTo("{second=1, test=2, third=1, word=2}");
    }

    private Context mockContext() {
        Context context = mock(Context.class);
        when(context.getLogger()).thenReturn(mock(LambdaLogger.class));
        return context;
    }

}    





