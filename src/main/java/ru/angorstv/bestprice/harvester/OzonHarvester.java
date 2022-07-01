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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class OzonHarvester implements Harvester {

    @Override
    public List<Product> getProducts(String value) throws JsonProcessingException {
        List<Product> products = new ArrayList<>();
        WebDriver driver = WebDriverFabric.getDriver();
        driver.get("https://www.ozon.ru/search/?from_global=true&text=" + value);
        products.addAll(getFromPage(driver));
        driver.close();
        return products;
    }

    private List<Product> getFromPage(WebDriver driver) {
        List<Product> products = new ArrayList<>();
        List<WebElement> webElements = driver.findElements(By.className("jt3"));
        for (WebElement element : webElements) {
            Product product = new Product();
            product.setShop(Shop.OZON);
            product.setUrl(element.findElement(By.tagName("a")).getAttribute("href"));
            String rawPrice = element.findElement(By.className("ui-q5")).getText();
            String rawStr = rawPrice.replaceAll(" ", "").replace("₽", "");
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(rawStr);
            m.find();
            product.setPrice(Integer.valueOf(m.group()));
            product.setName(element.findElement(By.className("tj4"))
                    .findElement(By.tagName("a"))
                    .findElement(By.tagName("span")).findElement(By.tagName("span"))
                    .getText());
            products.add(product);
        }
        return products;
    }
}
