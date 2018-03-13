package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase  {

    @Test
    public void testContactModification () {
        app.getNavigationHelper().gotoHomepage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Имя2", "Фамилия2", "адрес2", "test2@test.ru", "+29999999999"));
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoHomepage();
    }
}
