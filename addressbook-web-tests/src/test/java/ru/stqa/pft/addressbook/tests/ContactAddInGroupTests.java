package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.security.acl.Group;

public class ContactAddInGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        //проверка, есть ли контакты в списке
        //если нет, добавляет новый
        if (app.db().contacts().size() == 0) {
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withEmail("test@test.ru").withMobilePhone("+99999999999").withGroup("test1"), true);
            app.goTo().homepage();
        }
        //проверка, есть ли группы в списке
        //если нет, добавляет новую
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
            app.goTo().homepage();
        }
    }

    @Test
    public void testContactAddInGroup() {
        ContactData contact = selectContactToTest();
        GroupData group = selectGroupToTest(contact);
        app.contact().addInGroup(contact, group);

    }

    // метод, который выбирает контакт, который можно добавить в группу или создает новый в случае отсутствия подходящего контакта
    private ContactData selectContactToTest() {
        ContactData selectedContact = null;
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        for (ContactData contact : contacts) {
            if (contact.getGroups().size() < groups.size()) {
                selectedContact = contact;
            }
        }
        //если все контакты добавлены во все группы,
        //создает новый контакт
        if (selectedContact == null) {
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withEmail("test@test.ru").withMobilePhone("+99999999999").withGroup("test1"), true);
            app.goTo().homepage();
            selectedContact = new ContactData().withId(app.db().contacts().stream().mapToInt((g) -> g.getId()).max().getAsInt());
        }
        return selectedContact;
    }
    // метод, который выбирает группу, в которую можно добавить контакт
    private GroupData selectGroupToTest(ContactData contact) {
        GroupData selectedGroup = null;
        Groups groupsAll = app.db().groups();
        Groups contactGroups = app.db().contactById(contact.getId()).getGroups();
        for (GroupData group : contactGroups) {
            groupsAll.remove(group);
        }
        return groupsAll.iterator().next();
    }





}
