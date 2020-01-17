import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Navegador {
	
	private WebDriver driver = null;
	
	public Navegador() {
		if (driver == null) {
			try {
				System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
				System.setProperty("webdriver.gecko.driver", "C:\\Programacao/geckodriver.exe");

			//	System.setProperty("webdriver.firefox.marionette","C:\\Programacao\\geckodriver");
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("dom.webnotifications.enabled", false);
				FirefoxOptions options = new FirefoxOptions();
				options.setHeadless(true);
				options.setProfile(profile);
				driver = new FirefoxDriver(options);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	


//	public static void main(String[] args) {
//		try {
//		System.setProperty("webdriver.firefox.marionette","C:\\Programacao\\geckodriver");
//		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");
//		WebDriver driver = new FirefoxDriver();
//			
//			FirefoxProfile profile = new FirefoxProfile();
//			profile.setPreference("dom.webnotifications.enabled", false);
//			FirefoxOptions options = new FirefoxOptions();
//			options.setProfile(profile);
//			driver = new FirefoxDriver(options);
//		
//		
////		FirefoxProfile profile = new FirefoxProfile();
////		profile.setPreference("dom.webnotifications.enabled", false);
////		
////	      FirefoxOptions options = new FirefoxOptions();
////	        options.setProfile(profile);
////	        WebDriver driver = new FirefoxDriver(options);
//		
//		
//		//driver.manage().window().maximize();
//		driver.get("https://facebook.com");
//		WebElement campoEmail = driver.findElement(By.cssSelector("input[id=email]"));
//		campoEmail.clear();
//		campoEmail.sendKeys("willian.levandoski@yahoo.com.br");
//		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		 WebElement campoSenha = driver.findElement(By.cssSelector("input[id=pass]"));
//		 campoSenha.clear();
//		 campoSenha.sendKeys("f@c&1546");
//		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		 WebElement botaoEntrar = driver.findElement(By.cssSelector("input[value=Entrar]"));
//		 botaoEntrar.click();
//		 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		 
//		 WebElement campoPesquisar = driver.findElement(By.cssSelector("input[class=_1frb]"));
//		 campoPesquisar.sendKeys("jessica brum");
//		 driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//		 campoPesquisar.sendKeys(Keys.ENTER);
//		 
//		 WebElement resultadoPesquisa = driver.findElement(By.cssSelector("a[href=\"https://www.facebook.com/jessica.brum\"]"));
//		 resultadoPesquisa.click();
//		 driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//		 
//
//		 System.out.println(driver.getPageSource());
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public WebDriver getDriver() {
		return driver;
	}

	public void digitar(WebElement campoPesquisa, String pesquisa) {
		try {
			if(campoPesquisa!= null && pesquisa != null)
				campoPesquisa.sendKeys(pesquisa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clicar(WebElement botaoPesquisar) {
		try {
			if (botaoPesquisar!= null) 
				botaoPesquisar.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean temCampo(String css) {
		return temCampo(css, 10);
	}
	
	public boolean temCampo(String css, int tempo) {
		WebElement resultado;
		for(int i = 0; i < tempo; i++) {
			resultado= driver.findElement(By.cssSelector(css));
			if(resultado!= null)
				return true;
		}
		return false;
	}

}
