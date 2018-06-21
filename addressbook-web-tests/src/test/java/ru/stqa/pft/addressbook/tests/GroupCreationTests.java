package ru.stqa.pft.addressbook.tests;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        try ( BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/group.xml")))){
            String xml = "";
            String line = reader.readLine();
            do {
                xml +=line;
                line = reader.readLine();
            } while (line != null);
            XStream xstream = new XStream();
            xstream.processAnnotations(GroupData.class);
            List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
            return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
        }
    }
    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/group.json")))) {
            String json = "";
            String line = reader.readLine();
            do {
                json += line;
                line = reader.readLine();
            } while (line != null);
            Gson gson = new Gson();
            List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
            }.getType());
            return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }
/* //Работа через web-интерфейс
    @Test (dataProvider = "validGroupsFromJson")
    public void testGroupCreation(GroupData group) {
        app.goTo().groupPage();
        Groups before = app.group().all();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size() +1));
        Groups after = app.group().all();
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
    }

    @Test (enabled = false)
    public void testBadGroupCreation() {
        app.goTo().groupPage();
        GroupData group= new GroupData().withName("test'");
        Groups before = app.group().all();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.group().all();
        assertThat(after, equalTo(before));
    }
    */
     @Test (dataProvider = "validGroupsFromJson")
        public void testGroupCreation(GroupData group) {
            app.goTo().groupPage();
            Groups before = app.db().groups();
            app.group().create(group);
            app.goTo().groupPage();
            assertThat(app.group().count(), equalTo(before.size() + 1));
            Groups after = app.db().groups();
            assertThat(after, equalTo(before.withAdded(group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
        }

        @Test (enabled = false)
        public void testBadGroupCreation() {
            app.goTo().groupPage();
            GroupData group= new GroupData().withName("test'");
            Groups before = app.db().groups();
            app.group().create(group);
            assertThat(app.group().count(), equalTo(before.size()));
            Groups after = app.db().groups();
            assertThat(after, equalTo(before));
            verifyGroupListInUI();
        }

}
