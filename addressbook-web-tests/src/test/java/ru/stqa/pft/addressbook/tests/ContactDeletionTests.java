package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase  {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoHomepage();
        if (! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoContactCreationPage();
            app.getContactHelper().createContact(new ContactData("Имя", "Фамилия", "адрес", "test@test.ru", "+99999999999", "test1"), true);
            app.getNavigationHelper().gotoHomepage();
        }
        int before = app.getContactHelper().getGroupCount();
        app.getContactHelper().selectContact(before - 1);
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().submitContactDeletion();
        app.getNavigationHelper().gotoHomepage();
        int after = app.getContactHelper().getGroupCount();
        Assert.assertEquals(after, before - 1);
    }
}
