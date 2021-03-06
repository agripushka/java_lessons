package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase {

    //Работа через web-интерфейс
    /*
    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homepage();
        if (app.contact().list().size()==0){
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withEmail("test@test.ru").withMobilePhone("+99999999999").withGroup("test1"), true);
            app.goTo().homepage();
        }
    }

    @Test
    public void testContactPhones() {
        app.goTo().homepage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }
    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s)->!s.equals(""))
                .map(ContactEmailTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String email){
        return email.replaceAll("\\s","").replaceAll("[-()]", "");
    }
    */
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
    public void testContactEmails() {
        app.goTo().homepage();
        ContactData contact = app.db().contacts().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(mergeEmails(contact), equalTo(mergeEmails(contactInfoFromEditForm)));
    }
    private String mergeEmails(ContactData contact) {
        String email = contact.getEmail();
        String email2 = contact.getEmail2();
        String email3 = contact.getEmail3();
        if ( email == null) {
            email = "";
        }
        if ( email2 == null) {
            email2 = "";
        }
        if ( email3 == null) {
            email3 = "";
        }
        return Arrays.asList(email, email2, email3)
                .stream().filter((s)->!s.equals(""))
                .map(ContactEmailTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String email){
        return email.replaceAll("\\s","").replaceAll("[-()]", "").replaceAll("null", "");
    }
}
