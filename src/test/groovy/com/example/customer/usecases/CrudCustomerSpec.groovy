package com.example.customer.usecases

import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.example.customer.domains.Customer
import com.example.customer.exceptions.CustomerAlreadyExistsException
import com.example.customer.exceptions.CustomerNotFoundException
import com.example.customer.gateways.CustomerGateway
import com.google.common.collect.Lists
import spock.lang.Specification

class CrudCustomerSpec extends Specification {

    CustomerGateway customerGateway = Mock(CustomerGateway)

    CrudCustomer crudCustomer = new CrudCustomer(customerGateway)

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.example.customer.templates")
    }

    def "Should create a new customer with success"() {
        given: "a valid customer information"
        Customer customer = Fixture.from(Customer.class).gimme("valid")

        when: "usecase is called"
        Customer result = crudCustomer.create(customer)

        then: "the customer should not be found in database"
        customerGateway.findByDocument(_ as String) >> Optional.empty()

        and: "gateway must be called once for save"
        1 * customerGateway.save(_ as Customer) >> Fixture.from(Customer.class).gimme("valid")

        and: "should return a created customer"
        result != null
        result.uuid != null

        and: "every address must be filled with uuid"
        result.addresses.every { it.id != null }
    }

    def "Should not create an already existed customer"() {
        given: "a valid customer information"
        Customer customer = Fixture.from(Customer.class).gimme("valid")

        when: "usecase is called"
        crudCustomer.create(customer)

        then: "the customer is found in database"
        customerGateway.findByDocument(_ as String) >> Optional.of(Fixture.from(Customer.class).gimme("valid"))

        and: "gateway must not be called"
        0 * customerGateway.save(_ as Customer)

        and: "should return an exception"
        thrown(CustomerAlreadyExistsException)
    }

    def "Should update a customer with success"() {
        given: "a valid customer information"
        Customer customer = Fixture.from(Customer.class).gimme("valid")

        when: "usecase is called"
        Customer result = crudCustomer.update(customer)

        then: "the customer should not be found in database"
        customerGateway.findByDocument(_ as String) >> Optional.of(Fixture.from(Customer.class).gimme("valid"))

        and: "gateway must be called once for save"
        1 * customerGateway.save(_ as Customer) >> Fixture.from(Customer.class).gimme("valid")

        and: "should return a created customer"
        result != null
        result.uuid != null
    }

    def "Should not update a nonexistent customer"() {
        given: "a valid customer information"
        Customer customer = Fixture.from(Customer.class).gimme("valid")

        when: "usecase is called"
        crudCustomer.update(customer)

        then: "the customer is found in database"
        customerGateway.findByDocument(_ as String) >> Optional.empty()

        and: "gateway must not be called"
        0 * customerGateway.save(_ as Customer)

        and: "should return an exception"
        thrown(CustomerNotFoundException)
    }

    def "Should return a customer with sucess"() {
        given: "a valid customer information"
        Customer customer = Fixture.from(Customer.class).gimme("valid")

        when: "usecase is called"
        Customer result = crudCustomer.findByDocument(customer.getDocument())

        then: "the customer is found in database"
        customerGateway.findByDocument(_ as String) >> Optional.of(Fixture.from(Customer.class).gimme("valid"))

        and: "should return the customer"
        result != null
        result.uuid != null
    }

    def "Should not return a customer with invalid document"() {
        given: "a valid customer information"
        Customer customer = Fixture.from(Customer.class).gimme("valid")

        when: "usecase is called"
        crudCustomer.findByDocument(customer.getDocument())

        then: "the customer is found in database"
        customerGateway.findByDocument(_ as String) >> Optional.empty()

        and: "should return an exception"
        thrown(CustomerNotFoundException)
    }

    def "Should return a list of customers with sucess"() {
        given: "a valid customer information"
        List<Customer> customers = Lists.newArrayList(
                Fixture.from(Customer.class).gimme("valid"),
                Fixture.from(Customer.class).gimme("valid"))

        when: "usecase is called"
        crudCustomer.findAll()

        then: "the customer is found in database"
        customerGateway.findAll(_ as String) >> customers

        and: "should return the customer"
        customers != null
        customers.size() > 1
    }

    def "Should delete a customer with sucess"() {
        given: "a customer found in database"
        customerGateway.findByDocument(_ as String) >> Optional.of(Fixture.from(Customer.class).gimme("valid"))

        when: "usecase is called"
        crudCustomer.delete(_ as String)

        then: "the customer must be deleted"
        1 * customerGateway.delete(_ as Customer)
    }
}
