package com.example.customer.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.example.customer.domains.Address;

public class AddressTemplates implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Address.class).addTemplate("valid", new Rule() {{
            add("id", "5a5bb64d40dfcd1b0c798bf3");
            add("name", "49163226065");
            add("zipCode", "abc@teste.com");
            add("streetName", "Rua teste");
            add("streetNumber", "99");
            add("neighborhood", "Vila teste");
            add("city", "Campinas");
            add("state", "SP");
            add("country", "BR");
        }});
    }
}
