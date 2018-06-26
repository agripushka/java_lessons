package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteInGroupTests extends TestBase {
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
    public void testContactDeleteInGroup() {
        Contacts before = app.db().contacts();
        ContactData contact = selectContactToTest();
        GroupData group = selectGroupToTest(contact);
        app.contact().deleteInGroup(contact, group);

        Contacts after = app.db().contacts();

        assertThat(after, equalTo(before));
        verifyContactListInUI();
    }


    // метод, который выбирает контакт, который можно удалить из группы
    private ContactData selectContactToTest() {
        ContactData selectedContact = null; //контакт, который будет удален из группы
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();

        //проверяет, соответствие групп и контактов
        for (ContactData contact : contacts) {
            if (contact.getGroups().size()!= 0) {
                selectedContact = contact;
            }
        }
        //если ни один контакт не добавлен ни в одну группу
        //добавляет контакт в группу
        if (selectedContact == null) {
            System.out.println("Добавьте контакт в группу");
            //app.contact().addInGroup(contact, group);
        }
        return selectedContact;
    }


    // метод, который выбирает группу, из которой можно удалить контакт
    private GroupData selectGroupToTest(ContactData contact) {
        //Groups groupsAll = app.db().groups();
        GroupData groupDelete = new GroupData();
        Groups contactGroups = app.db().contactById(contact.getId()).getGroups();
        for (GroupData group : contactGroups) {
            if (contactGroups.size()!= 0) {
                groupDelete = group;
            }
        }
        return groupDelete;
    }

}
