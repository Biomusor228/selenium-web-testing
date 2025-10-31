package ru.netology.testing;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderTest extends TestBase {

    @Test
    public void shouldSubmitFormWithValidData() {
        driver.get("http://localhost:9999");

        // Заполняем форму
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем успешную отправку
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
    }

    @Test
    public void shouldShowErrorWithEmptyName() {
        driver.get("http://localhost:9999");

        // Заполняем только телефон
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об ошибке
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
    public void shouldShowErrorWithEnglishName() {
        driver.get("http://localhost:9999");

        // Заполняем имя английскими буквами
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivan Ivanov");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об ошибке
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
    }

    @Test
    public void shouldShowErrorWithEmptyPhone() {
        driver.get("http://localhost:9999");

        // Заполняем только имя
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об ошибке
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
    public void shouldShowErrorWithShortPhone() {
        driver.get("http://localhost:9999");

        // Заполняем имя и короткий телефон
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+792112345");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об ошибке
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
    }

    @Test
    public void shouldShowErrorWithoutAgreement() {
        driver.get("http://localhost:9999");

        // Заполняем форму без согласия
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        // Не ставим галочку согласия
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем, что чекбокс выделен красным
        WebElement checkbox = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid"));
        Assertions.assertTrue(checkbox.isDisplayed());
    }

    @Test
    public void shouldSubmitFormWithHyphenInName() {
        driver.get("http://localhost:9999");

        // Заполняем форму с дефисом в имени
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анна-Мария Петрова");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем успешную отправку
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
    }
}