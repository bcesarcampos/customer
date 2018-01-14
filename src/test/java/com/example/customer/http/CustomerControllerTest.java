package com.example.customer.http;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.example.customer.config.BaseControllerTest;
import com.example.customer.config.MockMvcUtils;
import com.example.customer.domains.Customer;
import com.example.customer.exceptions.CustomerAlreadyExistsException;
import com.example.customer.usecases.CrudCustomer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CustomerControllerTest extends BaseControllerTest{

    private static final String API_END_POINT = "/customers";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private MockMvc mockMvc;

    @Mock
    private CrudCustomer crudCustomer;

    private CustomerController controller;

    @BeforeClass
    public static void setUpClass() {
        FixtureFactoryLoader.loadTemplates("com.example.customer.templates");
    }

    @Before
    public void setUp()
    {
        controller = new CustomerController(crudCustomer);
        mockMvc = MockMvcUtils.buildMockMvcWithBusinessExecptionHandler(controller);
    }

    @Test
    public void shouldCreateCustomerWithSuccess() throws Exception {

        // GIVEN a customer information
        final Customer customer = Fixture.from(Customer.class).gimme("valid");

        // WHEN create user is called
        when(crudCustomer.create(any())).thenReturn(customer);

        MvcResult result = mockMvc.perform(
                post(API_END_POINT)
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(customer)))
                .andReturn();

        Customer resultBody =
                objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Customer>() {});

        // THEN should return a valid customer
        Assert.assertEquals(CREATED.value(), result.getResponse().getStatus());
        Assert.assertNotNull(resultBody);
    }

    @Test
    public void shouldNotCreateAlreadyExistCustomer() throws Exception {

        // GIVEN a customer information
        final Customer customer = Fixture.from(Customer.class).gimme("valid");

        // WHEN create user is called
        when(crudCustomer.create(any())).thenThrow(new CustomerAlreadyExistsException());

        MvcResult result = mockMvc.perform(
                post(API_END_POINT)
                        .contentType(CONTENT_TYPE)
                        .content(objectMapper.writeValueAsString(customer)))
                .andReturn();

        // THEN should return an error
        Assert.assertEquals( UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());
    }
}
