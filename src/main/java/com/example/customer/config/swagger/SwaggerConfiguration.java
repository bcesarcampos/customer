package com.example.customer.config.swagger;


import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String MSG_NOT_FOUND = "Not found";
    private static final String MSG_INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String MSG_BAD_REQUEST = "Bad Request";

    private static final String MODEL_REF_ERROR = "Error";

    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .globalResponseMessage(RequestMethod.GET, getErrors())
                .globalResponseMessage(RequestMethod.POST, postErrors())
                .globalResponseMessage(RequestMethod.PUT, defaultErrors())
                .globalResponseMessage(RequestMethod.DELETE, defaultErrors())
                .globalResponseMessage(RequestMethod.PATCH, defaultErrors())
                .globalResponseMessage(RequestMethod.HEAD, getErrors())
                .apiInfo(apiInfo());
    }

    private List<ResponseMessage> getErrors() {
        return Lists.newArrayList(
                new ResponseMessageBuilder().code(HttpStatus.NOT_FOUND.value())
                        .message(MSG_NOT_FOUND)
                        .responseModel(new ModelRef(MODEL_REF_ERROR)).build(),
                new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(MSG_INTERNAL_SERVER_ERROR).build()
        );
    }

    private List<ResponseMessage> postErrors() {
        return Lists.newArrayList(
                new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
                        .message(MSG_BAD_REQUEST)
                        .responseModel(new ModelRef(MODEL_REF_ERROR)).build(),
                new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(MSG_INTERNAL_SERVER_ERROR).build()
        );
    }

    private List<ResponseMessage> defaultErrors() {
        return Lists.newArrayList(
                new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
                        .message(MSG_BAD_REQUEST)
                        .responseModel(new ModelRef(MODEL_REF_ERROR)).build(),
                new ResponseMessageBuilder().code(HttpStatus.NOT_FOUND.value())
                        .message(MSG_NOT_FOUND)
                        .responseModel(new ModelRef(MODEL_REF_ERROR)).build(),
                new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(MSG_INTERNAL_SERVER_ERROR).build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Customer")
                .version("1.0")
                .build();
    }
}