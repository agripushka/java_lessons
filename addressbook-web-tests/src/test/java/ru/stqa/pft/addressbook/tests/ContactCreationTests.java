package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

 @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contact.xml")))) {
            String xml = "";
            String line = reader.readLine();
            do {
                xml += line;
                line = reader.readLine();
            } while (line != null);
            XStream xstream = new XStream();
            xstream.processAnnotations(ContactData.class);
            List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }
    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contact.json")))) {
            String json = "";
            String line = reader.readLine();
            do {
                json += line;
                line = reader.readLine();
            } while (line != null);
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
            }.getType());
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    //Работа через web-интерфейс
    /*
       @Test (dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact) {
        //File photo = new File("src/test/resources/stru1.jpg");
        app.goTo().homepage();
        Contacts before = app.contact().all();
        app.goTo().contactCreationPage();
        app.contact().create(contact, true);
        app.goTo().homepage();
        assertThat(app.contact().count(), equalTo(before.size() +1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));

    }
    */

    @Test (dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact) {
        Groups groups = app.db().groups();
        if (groups.size()==0){
            app.goTo().groupPage();
            GroupData group= new GroupData().withName("test1");
            app.group().create(group);
            groups = app.db().groups();
        }
        Contacts before = app.db().contacts();
        app.goTo().homepage();
        app.goTo().contactCreationPage();
        app.contact().create(contact.inGroup(groups.iterator().next()), true);
        app.goTo().homepage();
        assertThat(app.contact().count(), equalTo(before.size() +1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
        verifyContactListInUI();
    }

}
