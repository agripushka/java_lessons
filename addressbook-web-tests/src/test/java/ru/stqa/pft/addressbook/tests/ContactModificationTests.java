package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTests extends TestBase  {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homepage();
        if (app.contact().list().size()==0){
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withContactEmail("test@test.ru").withMobile("+99999999999").withGroup("test1"), true);
            app.goTo().homepage();
        }
    }

    @Test
    public void testContactModification () {
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withContactEmail("test@test.ru").withMobile("+99999999999");
        app.contact().modify(contact);
        app.goTo().homepage();
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedContact);
        before.add(contact);
        Assert.assertEquals(before, after);
    }


}
