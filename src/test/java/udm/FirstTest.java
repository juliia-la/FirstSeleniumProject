package udm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert; // <-- НАШ НОВИЙ ІМПОРТ ДЛЯ ПЕРЕВІРОК
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FirstTest {
    public class AlloComprehensiveTest {

        // Створюємо змінну для драйвера браузера
        private WebDriver driver;

        // 1. ПІДГОТОВКА (Preconditions)
        @BeforeMethod
        public void setUp() {
            System.out.println("[СТАРТ] Ініціалізація браузера перед тестом...");
            driver = new ChromeDriver();
            driver.manage().window().maximize();

            // Відкриваємо безпосередньо сайт магазину АЛЛО
            driver.get("https://allo.ua");
        }

        // 2. САМ АВТОТЕСТ (Логіка та перевірки)
        @Test
        public void testAlloE2EFlow() {
            System.out.println("[ТЕСТ] Запуск основного сценарію тестування...");

            try {
                // Крок 1. Перевіряємо, чи на сторінці є стабільна кнопка "Каталог" за допомогою чистого XPath та тексту
                WebElement catalogButton = driver.findElement(By.xpath("//span[@class='mh-button__title' and normalize-space()='Каталог']"));
                Assert.assertTrue(catalogButton.isDisplayed(), "Кнопка 'Каталог' не відображається на головній сторінці!");

                // Крок 2. Знаходимо рядок пошуку за допомогою об'єднаного CSS-селектора (Тег + ID + Клас без пробілів)
                WebElement searchInput = driver.findElement(By.cssSelector("input#search-form__input.search-form__input"));

                // Вводимо назву товару (використовуємо чисті одинарні лапки всередині коду)
                searchInput.click();
                searchInput.sendKeys("iPhone");
                searchInput.submit(); // Відправляємо форму пошуку

                // Тимчасова пауза на 4 секунди, щоб результати встигли повністю відрендеритися
                Thread.sleep(4000);

                // Крок 3. Перевіряємо заголовок сторінки після пошуку за допомогою Assert.assertEquals
                String actualTitle = driver.getTitle();
                System.out.println("Реальний заголовок сторінки результатів: " + actualTitle);

                // Перевіряємо, що в заголовку є слово "iPhone" (захищаємо тест від падіння за допомогою contains)
                Assert.assertTrue(actualTitle.contains("iPhone"), "Сторінка пошуку не містить результатів для iPhone!");

                // Крок 4. Знаходимо іконку кошика в шапці сайту за допомогою динамічного XPath таcontains(text())
                WebElement cartButton = driver.findElement(By.xpath("//button[contains(@class, 'mh-cart') or @aria-label='Кошик']"));
                Assert.assertTrue(cartButton.isDisplayed(), "Динамічна кнопка кошика не знайдена в шапці сайту!");

                System.out.println("[УСПІХ] Усі кроки та перевірки Assert виконані успішно!");

            } catch (InterruptedException e) {
                e.printStackTrace();
                Assert.fail("Тест перервано через помилку виконання потоку (Thread.sleep)!");
            }
        }

        // 3. ОЧИЩЕННЯ (Postconditions)
        @AfterMethod
        public void tearDown() {
            System.out.println("[ФІНІШ] Закриття браузера та очищення пам'яті...");
            // Повністю закриваємо вікно браузера та вбиваємо процеси драйвера
            if (driver != null) {
                driver.quit();
            }
        }
    }

}