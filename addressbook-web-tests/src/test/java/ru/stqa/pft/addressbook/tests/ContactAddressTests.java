package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

    //Работа через web-интерфейс
    /*@BeforeMethod
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
    public void testContactAddress() {
        app.goTo().homepage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAddress().replaceAll("\\s","").replaceAll("\\n",""), equalTo(contactInfoFromEditForm.getAddress().replaceAll("\\s","").replaceAll("\\n","")));
    }
    */
    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size()==0){
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData()
                    .withFirstname("Имя").withLastname("Фамилия").withAddress("адрес").withEmail("test@test.ru").withMobilePhone("+99999999999"), true);
            app.goTo().homepage();
        }
    }

    @Test
    public void testContactAddress() {
        app.goTo().homepage();
        ContactData contact = app.db().contacts().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        //проверяет, удаляя лишние символы
        assertThat(contact.getAddress().replaceAll("\\s","").replaceAll("\\n",""), equalTo(contactInfoFromEditForm.getAddress().replaceAll("\\s","").replaceAll("\\n","")));

    }


}
