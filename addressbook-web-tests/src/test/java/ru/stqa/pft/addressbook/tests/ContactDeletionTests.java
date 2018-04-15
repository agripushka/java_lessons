package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase  {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homepage();
        if (app.contact().list().size()==0){
            app.goTo().contactCreationPage();
            app.contact().create(new ContactData("Имя", "Фамилия", "адрес", "test@test.ru", "+99999999999", "test1"), true);
            app.goTo().homepage();
        }
    }

    @Test
    public void testContactDeletion() {
        List<ContactData> before = app.contact().list();
        int index = before.size() - 1;
        app.contact().delete(index);
        app.goTo().homepage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(index);
        for (int i = 0; i< after.size(); i++){
            Assert.assertEquals(after.get(i), before.get(i));
        }
    }


}
