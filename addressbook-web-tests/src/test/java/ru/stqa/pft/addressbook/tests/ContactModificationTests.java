package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase  {

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
    public void testContactModification () {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withContactEmail("test@test.ru").withMobilePhone("+99999999999");
        app.contact().modify(contact);
        app.goTo().homepage();
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }


}
