package ru.angorstv.bestprice.harvester;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import ru.angorstv.bestprice.entity.Product;
import ru.angorstv.bestprice.service.WebDriverFabric;

import java.util.ArrayList;
import java.util.List;

@Component
public class AliHarvester implements Harvester {

    @Override
    public List<Product> getProducts(String value) throws JsonProcessingException {
        List<Product> products = new ArrayList<>();
        WebDriver driver = WebDriverFabric.getDriver();
        driver.get("https://aliexpress.ru/wholesale?catId=&SearchText=" + value);
        products.addAll(getFromPage(driver));
        for (int i = 0; i < 10; i++) {
            WebElement button = driver.findElement(By.className("SearchPagination_Button__button__177je"));
            button.click();
            products.addAll(getFromPage(driver));
        }
        driver.close();
        return products;
    }

    private List<Product> getFromPage(WebDriver driver) {
        List<Product> products = new ArrayList<>();
        List<WebElement> webElements = driver.findElements(By.className("product-snippet_ProductSnippet__description__tusfnx"));
        for (WebElement element : webElements) {
            Product product = new Product();
            product.setShop(Shop.ALIEXPRESS);
            product.setUrl(element.findElement(By.tagName("a")).getAttribute("href"));
            WebElement nameEl = element.findElement(By.className("product-snippet_ProductSnippet__name__tusfnx"));
            product.setName(nameEl.getText());
            List<WebElement> prices = element.findElements(By.className("product-snippet_ProductSnippet__block__tusfnx"));
            for (WebElement price : prices) {
                String[] split = price.getAttribute("class").split(" ");
                if (split.length > 1) {
                    String pattern = split[0].split("__")[2];
                    WebElement priceElement = price.findElement(By.className("snow-price_SnowPrice__mainM__" + pattern));
                    product.setPrice(Integer.valueOf(priceElement.getText().split(",")[0].replaceAll(" ", "")));
                }
            }
            products.add(product);
        }
        return products;
    }
}
