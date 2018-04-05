package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        int before = app.getContactHelper().getGroupCount();
        app.getNavigationHelper().gotoContactCreationPage();
        app.getContactHelper().createContact(new ContactData("Имя", "Фамилия", "адрес", "test@test.ru", "+99999999999", "test1"), true);
        app.getNavigationHelper().gotoHomepage();
        int after = app.getContactHelper().getGroupCount();
        Assert.assertEquals(after, before + 1);
    }

}
