package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Set;

import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        Set<ContactData> before = app.contact().all();
        app.goTo().contactCreationPage();
        ContactData contact = new ContactData()
                .withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withContactEmail("test@test.ru").withMobile("+99999999999").withGroup("test1");
        app.contact().create(contact, true);
        app.goTo().homepage();
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        contact.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt());
        before.add(contact);
        Assert.assertEquals(before, after);
    }

}
