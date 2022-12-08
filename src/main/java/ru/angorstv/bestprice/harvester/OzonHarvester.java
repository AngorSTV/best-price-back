package ru.angorstv.bestprice.harvester;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import ru.angorstv.bestprice.common.Shop;
import ru.angorstv.bestprice.entity.Product;
import ru.angorstv.bestprice.service.WebDriverFabric;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class OzonHarvester implements Harvester {

    private static final String envelopeTag = "kt4";
    private static final String cellTag = "kr3";
    private static final String urlTag = "tile-hover-target k8n";
    private static final String priceTag = "_32-a9";
    private static final String nameTag = "tsBodyL";
    private static final String waitTag = "tsBodyM";

    @Override
    public List<Product> getProducts(String value) {
        List<Product> products = new LinkedList<>();
        WebDriver driver = WebDriverFabric.getDriver();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        try {
            driver.get("https://www.ozon.ru/search/?from_global=true&sorting=rating&text=" + value);
            products.addAll(getFromPage(driver));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        } finally {
            driver.close();
        }
        log.info("OzonHarvester found " + products.size() + " products.");
        return products;
    }

    private List<Product> getFromPage(WebDriver driver) {
        List<Product> products = new LinkedList<>();
        WebElement envelope = new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.className(envelopeTag))));
        List<WebElement> webElements = envelope.findElements(By.className(cellTag));
        if (webElements.isEmpty()) {
            log.warn("Bad name of cell for OZON");
        } else {
            for (WebElement element : webElements) {
                Product product = new Product();
                product.setShop(Shop.OZON);
                product.setUrl(element.findElement(By.tagName("a")).getAttribute("href"));

                String rawPrice = element.findElement(By.className(priceTag)).getText();
                String rawStr = rawPrice.replaceAll("[^0-9]+", "");
                product.setPrice(Integer.valueOf(rawStr));

                product.setName(element.findElement(By.className(nameTag))
                        .findElement(By.tagName("span"))
                        .getText());
                products.add(product);
            }
        }
        return products;
    }
}
