package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contact.csv")));
        String line = reader.readLine();
        do {
            String[] split = line.split(",");
            list.add(new Object[] {new ContactData().withFirstname(split[0]).withLastname(split[1])
                    .withAddress(split[2]).withEmail(split[3]).withMobilePhone(split[4])});
            line = reader.readLine();
        } while (line != null);
        return list.iterator();
    }

    @Test (dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) {
        File photo = new File("src/test/resources/stru1.jpg");
        Contacts before = app.contact().all();
        app.goTo().contactCreationPage();
        app.contact().create(contact, true);
        app.goTo().homepage();
        assertThat(app.contact().count(), equalTo(before.size() +1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));

    }

}
