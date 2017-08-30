package ru.pflb.wd;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;


import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by user on 30.08.2017.
 *
 * Домашнее задание.
 * <p>
 * Сценарий:<ol>
 * <li>Открыть http://localhost:4200/</li>
 * <li>Перейти в меню Owners -> Add new</li>
 * <li>Ввести валидные случайные данные (новые для каждого запуска теста)</li>
 * <li>Нажать Add Owner, открылась страница Owners</li>
 * <li>Проверить, что добавилась новая запись, и все ее поля соответствуют введенным значениям</li>
 * </ul>
 *
 */

public class HomeworkTest {
    @Test
    public void shouldValidateAddedUser() {
        System.setProperty("webdriver.chrome.driver", new File("src/main/resources/chromedriver.exe").getAbsolutePath());
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);

        // открываем PetClinic по ссылке
        driver.get("http://localhost:8080/");

        // клик по меню Find Owners
        driver.findElement(By.xpath("//span[text()='Find owners']")).click();

        // клик по Add Owners
        driver.findElement(By.xpath("//a[text()='Add Owner']")).click();

        // Генерируем рандомные данные (буквенные имя, фамилию, адрес, город и цифровой телефон)
        String newFirstName = RandomStringUtils.randomAlphabetic(6);
        String newLastName = RandomStringUtils.randomAlphabetic(10);
        String newFullName = newFirstName + ' ' + newLastName; // Склеиваем имя с фамилией для последующей проверки
        String newAddress = RandomStringUtils.randomAlphabetic(14);
        String newCity = RandomStringUtils.randomAlphabetic(6);
        String newTelephone = RandomStringUtils.randomNumeric(10);

        // Заполняем соответствующие строки данными
        WebElement firstNameEditBox = driver.findElement(By.xpath("//input[@id='firstName']"));
        firstNameEditBox.sendKeys("" + newFirstName);
        WebElement lastNameEditBox = driver.findElement(By.xpath("//input[@id='lastName']"));
        lastNameEditBox.sendKeys("" + newLastName);
        WebElement addressEditBox = driver.findElement(By.xpath("//input[@id='address']"));
        addressEditBox.sendKeys("" + newAddress);
        WebElement cityEditBox = driver.findElement(By.xpath("//input[@id='city']"));
        cityEditBox.sendKeys("" + newCity);
        WebElement telephoneEditBox = driver.findElement(By.xpath("//input[@id='telephone']"));
        telephoneEditBox.sendKeys("" + newTelephone);

        // клик по Add Owner
        driver.findElement(By.xpath("//button[text()='Add Owner']")).click();

        // клик по меню Find Owners
        driver.findElement(By.xpath("//span[text()='Find owners']")).click();

        // клик по Find Owner, выводим список пользователей
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // проверка выставленных значений
        assertThat(driver.findElement(By.xpath("//tr[last()]/td[1]/a")).getText()).isEqualTo(newFullName);
        assertThat(driver.findElement(By.xpath("//tr[last()]/td[2]")).getText()).isEqualTo(newAddress);
        assertThat(driver.findElement(By.xpath("//tr[last()]/td[3]")).getText()).isEqualTo(newCity);
        assertThat(driver.findElement(By.xpath("//tr[last()]/td[4]")).getText()).isEqualTo(newTelephone);

    }

}
