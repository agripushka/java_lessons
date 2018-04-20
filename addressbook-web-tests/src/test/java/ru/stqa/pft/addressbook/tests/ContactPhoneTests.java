package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homepage();
        if (app.contact().list().size()==0){
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withContactEmail("test@test.ru").withMobilePhone("+99999999999").withGroup("test1"), true);
            app.goTo().homepage();
        }
    }

    @Test
    public void testContactPhones() {
        app.goTo().homepage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getHomePhone(), equalTo(cleaned(contactInfoFromEditForm.getHomePhone())));
        assertThat(contact.getMobilePhone(), equalTo(cleaned(contactInfoFromEditForm.getMobilePhone())));
        assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));

    }

    public String cleaned(String phone){
        return phone.replaceAll("\\s","").replaceAll("[-()]", "");
    }
}
