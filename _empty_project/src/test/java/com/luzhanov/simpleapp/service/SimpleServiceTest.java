package com.luzhanov.simpleapp.service;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimpleServiceTest {

    private SimpleService simpleService;

    @BeforeEach
    void setUp() {
        simpleService = new SimpleService();
    }

    @Test
    void shouldReturnGreetingWithValidName() {
        String name = "John";

        String result = simpleService.sayHello(name);

        assertThat(result)
                .isNotNull()
                .isEqualTo("Hello John");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
    void shouldThrowExceptionForBlankName(String blankName) {
        assertThatThrownBy(() -> simpleService.sayHello(blankName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name cannot be blank");
    }

    @Test
    void shouldThrowExceptionForNullName() {
        assertThatThrownBy(() -> simpleService.sayHello(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name cannot be blank");
    }

    @Test
    void shouldHandleUnicodeNamesFromFile() throws Exception {
        // Arrange
        File unicodeNamesFile = new File("src/test/resources/unicode_names.txt");
        List<String> names = FileUtils.readLines(unicodeNamesFile, StandardCharsets.UTF_8);

        // Act & Assert
        for (String name : names) {
            if (!name.trim().isEmpty()) {  // Skip empty lines
                String result = simpleService.sayHello(name.trim());
                assertThat(result)
                        .isNotNull()
                        .isEqualTo("Hello " + name.trim())
                        .containsPattern("Hello .*");  // Verify format
            }
        }
    }

}