package com.example.customer.config;

import com.example.customer.exceptions.RestResponseEntityExceptionHandler;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MockMvcUtils {

    private MockMvcUtils() { }

    public static MockMvc buildMockMvcWithBusinessExecptionHandler(final Object controller) {
        final StaticApplicationContext applicationContext = new StaticApplicationContext();
        applicationContext.registerSingleton("startExceptionHandler", RestResponseEntityExceptionHandler.class);

        final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
        webMvcConfigurationSupport.setApplicationContext(applicationContext);

        return standaloneSetup(controller)
                .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver()).build();
    }
}
