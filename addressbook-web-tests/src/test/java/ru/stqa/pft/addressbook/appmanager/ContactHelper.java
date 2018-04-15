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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void selectContactById (int id) {
        wd.findElement(By.cssSelector("input[value='" +id+ "']")).click();
    }
    public void initContactModification(int id) {
        click(By.cssSelector("a[href='edit.php?id="+ id +"']"));
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

    public void create(ContactData contact, boolean b) {
        fillContactForm(contact, b);
        submitContactCreation();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getGroupCount() {
        return wd.findElements(By.name("selected[]")).size();
    }
    public void modify(ContactData contact) {
        selectContactById(contact.getId());
        initContactModification(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();

    }
    public void delete(ContactData deleted) {
        selectContactById(deleted.getId());
        deleteSelectedContact();
        submitContactDeletion();
    }
    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        int i = 2;
        for (WebElement element : elements){
            WebElement lastnameObj = element.findElement(By.xpath("//table[@id='maintable']/tbody/tr["+ i + "]/td[2]"));
            String lastname =  lastnameObj.getText();
            WebElement firstnameObj = element.findElement(By.xpath("//table[@id='maintable']/tbody/tr["+ i + "]/td[3]"));
            String firstname =  firstnameObj.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add( new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
            i++;
        }
        return contacts;
    }
    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        int i = 2;
        for (WebElement element : elements){
            WebElement lastnameObj = element.findElement(By.xpath("//table[@id='maintable']/tbody/tr["+ i + "]/td[2]"));
            String lastname =  lastnameObj.getText();
            WebElement firstnameObj = element.findElement(By.xpath("//table[@id='maintable']/tbody/tr["+ i + "]/td[3]"));
            String firstname =  firstnameObj.getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add( new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
            i++;
        }
        return contacts;
    }
}
