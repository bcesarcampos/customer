package com.example.customer.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.example.customer.domains.Address;
import com.example.customer.domains.Customer;

import java.time.LocalDate;

public class CustomerTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Customer.class).addTemplate("valid", new Rule() {{
            add("uuid", "5a5bb64d40dfcd1b0c798bf2");
            add("document", "49163226065");
            add("email", "abc@teste.com");
            add("name", "Nome");
            add("lastName", "Sobrenome");
            add("gender", "M");
            add("dateOfBirth", LocalDate.of(2018, 1, 15));
            add("addresses", has(1).of(Address.class, "valid"));
        }});
    }
}
