package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactAddInGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size()==0){
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withEmail("test@test.ru").withMobilePhone("+99999999999").withGroup("test1"), true);
            app.goTo().homepage();
        }
    }

    @Test
    public void testContactAddInGroup() {
     /*   Groups groups = app.db().groups();
        if (groups.size()==0){
            app.goTo().groupPage();
            GroupData group = new GroupData().withName("test1");
            app.group().create(group);
            groups = app.db().groups();
        }
        Contacts before = app.db().contacts();
        ContactData contactInGroup = before.iterator().next();
       // app.contact().addInGroup(contactInGroup.inGroup(groups.iterator().next()));
        app.contact().selectContactById(contactInGroup.getId());
        app.group().selectGroupByIdToGroup(groups.iterator().next().getId());
        app.contact().submitAddInGroup();
        app.goTo().homepage();*/
    }

}
