package udm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            // 1. Переходимо на сайт Google
            driver.get("https://www.google.com");

            // 2. Знаходимо поле введення пошуку Google за його ім'ям (name="q")
            WebElement searchBox = driver.findElement(By.name("q"));

            // 3. Вводимо текст у пошуковий рядок
            searchBox.sendKeys("skillklan");

            // 4. Натискаємо клавішу Enter
            searchBox.submit();

            // 5. Коротка пауза для перегляду результатів
            Thread.sleep(3000);

            System.out.println("Тест успішно виконано! Заголовок після пошуку: " + driver.getTitle());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Закриваємо браузер
            driver.quit();
        }
    }
}