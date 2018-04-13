package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase  {

    @BeforeMethod
    public void ensurePreconditions() {
        app.getNavigationHelper().gotoHomepage();
        if (!app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoContactCreationPage();
            app.getContactHelper().createContact(new ContactData("Имя", "Фамилия", "адрес", "test@test.ru", "+99999999999", "test1"), true);
            app.getNavigationHelper().gotoHomepage();
        }
    }

    @Test
    public void testContactDeletion() {
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().submitContactDeletion();
        app.getNavigationHelper().gotoHomepage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size()-1);
        for (int i = 0; i< after.size(); i++){
            Assert.assertEquals(after.get(i), before.get(i));
        }
    }
}
