package czar.qacg.acceptance;

import static org.junit.Assert.assertTrue;

import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MetricasTest {
   private WebDriver driver;
   private String    baseUrl;
   
   private final static String[] CHARTS = new String[]{
         "curve_chart_1",
         "curve_chart_2",
         "curve_chart_4",
         "curve_chart_5",
         "curve_chart_6",
         "curve_chart_7"
   };
   
   private final static String[] LINKS = new String[] {
         "sitioDistribuidores",
         "sitioClientes",
         "Cotizador",
         "Subastador",
         "SitioProveedores",
         "Timer",
         "DFSIP",
         "Remarketing",
         "OnOffBoarding",
         "mbims",
         "ProductManager",
         "SAUPP",
         "GoLegal",
         "Itv",
         "EfilesAutos",
         "IC"
   };

   @Before
   public void setUp() {
      try {
         Properties properties = new Properties();
         properties.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
         Enumeration<?> names = properties.propertyNames();
         while(names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = properties.getProperty(name);
            System.setProperty(name, value);
         }
         driver = new ChromeDriver();
         baseUrl = "http://192.168.1.235";
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   @Test
   public void allTest() throws Exception {
      driver.get(baseUrl + "/sqst/viewer/");
      for (String link : LINKS) {
         driver.findElement(By.linkText(link)).click();
         for (String chart : CHARTS) {
            String msg = String.format("%s not present in %s", chart, link);
            assertTrue(msg, isElementPresent(By.id(chart)));
         }
      }
   }

   @After
   public void tearDown() throws Exception {
      driver.quit();
   }

   private boolean isElementPresent(By by) {
      try {
         driver.findElement(by);
         return true;
      } catch (NoSuchElementException e) {
         return false;
      }
   }
}
