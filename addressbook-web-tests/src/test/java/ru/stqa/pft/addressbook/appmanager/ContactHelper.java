package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver wd) {
        super(wd);
    }
    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"),contactData.getFirstname());
        type(By.name("lastname"),contactData.getLastname());
        type(By.name("address"),contactData.getAddress());
        type(By.name("email"),contactData.getContactEmail());
        type(By.name("mobile"),contactData.getMobile());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }

    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void initContactModification(int number) {
        click(By.xpath("//table[@id='maintable']/tbody/tr["+ number + "]/td[8]/a/img"));
    }

    public void submitContactModification() {
        click(By.xpath("//div[@id='content']/form[1]/input[22]"));
    }

    public void deleteSelectedContact() {
        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    }

    public void submitContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public void createContact(ContactData contact, boolean b) {
        fillContactForm(contact, b);
        submitContactCreation();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }
    public void modifyContact(List<ContactData> before, int index, ContactData contact) {
        selectContact(index);
        initContactModification(before.size() + 1);
        fillContactForm(contact, false);
        submitContactModification();

    }
    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        int i = 2;
        for (WebElement element : elements){
            WebElement lastnameObj = element.findElement(By.xpath("//table[@id='maintable']/tbody/tr["+ i + "]/td[2]"));
            String lastname =  lastnameObj.getText();
            WebElement firstnameObj = element.findElement(By.xpath("//table[@id='maintable']/tbody/tr["+ i + "]/td[3]"));
            String firstname =  firstnameObj.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData(id, firstname, lastname, null, null, null,null);
            contacts.add(contact);
            i++;
        }
        return contacts;
    }
}
