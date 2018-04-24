package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() {
        List<Object[]> list = new ArrayList<Object[]>();
        File photo = new File("src/test/resources/stru.png");
        list.add(new Object[] {new ContactData().withFirstname("Имя1").withLastname("Фамилия1").withAddress("адрес").withEmail("test@test.ru").withMobilePhone("+99999999999").withPhoto(photo)});
        list.add(new Object[] {new ContactData().withFirstname("Имя2").withLastname("Фамилия2").withAddress("адрес").withEmail("test@test.ru").withMobilePhone("+99999999999").withPhoto(photo)});
        list.add(new Object[] {new ContactData().withFirstname("Имя3").withLastname("Фамилия3").withAddress("адрес").withEmail("test@test.ru").withMobilePhone("+99999999999")});
        return list.iterator();
    }

    @Test (dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) {
        File photo = new File("src/test/resources/stru1.jpg");
        Contacts before = app.contact().all();
        app.goTo().contactCreationPage();
        app.contact().create(contact.withPhoto(photo), true);
        app.goTo().homepage();
        assertThat(app.contact().count(), equalTo(before.size() +1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
    }

}
