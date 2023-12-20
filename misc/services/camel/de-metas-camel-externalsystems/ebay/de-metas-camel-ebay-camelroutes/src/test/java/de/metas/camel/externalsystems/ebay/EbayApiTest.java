/*
 * #%L
 * de-metas-camel-ebay-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.externalsystems.ebay;

import com.ebay.api.client.auth.oauth2.CredentialUtil;
import com.ebay.api.client.auth.oauth2.OAuth2Api;
import com.ebay.api.client.auth.oauth2.model.Environment;
import com.ebay.api.client.auth.oauth2.model.OAuthResponse;
import de.metas.camel.externalsystems.ebay.api.OrderApi;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiClient;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiException;
import de.metas.camel.externalsystems.ebay.api.invoker.Configuration;
import de.metas.camel.externalsystems.ebay.api.invoker.auth.OAuth;
import de.metas.camel.externalsystems.ebay.api.model.OrderSearchPagedCollection;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EbayApiTest
{

	private static final Environment EXECUTION_ENV = Environment.SANDBOX;

	private static final List<String> SCOPE_LIST = Arrays.asList("https://api.ebay.com/oauth/api_scope",
			"https://api.ebay.com/oauth/api_scope/sell.marketing.readonly",
			"https://api.ebay.com/oauth/api_scope/sell.fulfillment");

	@BeforeAll
	public static void setupClass() throws FileNotFoundException
	{
		WebDriverManager.chromedriver().setup();

		CredentialUtil.load(new FileInputStream("src/test/resources/ebay-test-creds.yaml"));
		CredentialUtil.dump();
	}

	//@Test

	/**
	 * To run this test you need access to the ebay api.
	 * 1. Create ebay developer account
	 * 2. Put your credentials into ebay-test-creds.yaml
	 *    -> This are the credentials for developer access to create an app.
	 * 3. Put your ebay crentials into application.properties
	 *    -> This are credentials of an account who sells and wants to grant access to metasfresh.
	 * 3. Execute test -> it will open a browser, auth and fetch orders.
	 * @throws Exception
	 */
	public void testLoadOrders() throws Exception
	{

		String authorizationCode = getAuthorizationCode();

		OAuth2Api auth2Api = new OAuth2Api();
		OAuthResponse oauth2Response = auth2Api.exchangeCodeForAccessToken(EXECUTION_ENV, authorizationCode);
		Assert.assertNotNull(oauth2Response);

		if (oauth2Response.getAccessToken().isPresent())
		{

			ApiClient defaultClient = Configuration.getDefaultApiClient();
			defaultClient.setBasePath("https://api.sandbox.ebay.com/sell/fulfillment/v1");
			// defaultClient.setBasePath("https://api.ebay.com/sell/fulfillment/v1");

			// Configure OAuth2 access token for authorization: api_auth
			OAuth api_auth = (OAuth)defaultClient.getAuthentication("api_auth");
			api_auth.setAccessToken(oauth2Response.getAccessToken().get().getToken());

			try
			{
				final OrderApi api = new OrderApi(defaultClient);

				String fieldGroups = null;
				String filter = null;
				String limit = "20";
				String offset = null;
				String orderIds = null;
				OrderSearchPagedCollection response = api.getOrders(fieldGroups, filter, limit, offset, orderIds);

				Assert.assertNotNull(response);
				System.out.println(response);

			}
			catch (ApiException e)
			{
				System.err.println("Exception when calling OrderApi#getOrder");
				System.err.println("Status code: " + e.getCode());
				System.err.println("Reason: " + e.getResponseBody());
				System.err.println("Response headers: " + e.getResponseHeaders());
				e.printStackTrace();
			}
		}
		else
		{

		}

	}

	private String getAuthorizationResponseUrl() throws InterruptedException
	{
		Properties prop = new Properties();
		try
		{
			prop.load(EbayApiTest.class.getClassLoader().getResourceAsStream("application.properties"));

		}
		catch (IOException ex)
		{
			System.err.println("Please provide a a properties file with ebay user and pass.");
		}

		WebDriver driver = new ChromeDriver();
		OAuth2Api auth2Api = new OAuth2Api();
		String authorizeUrl = auth2Api.generateUserAuthorizationUrl(EXECUTION_ENV, SCOPE_LIST, Optional.of("current-page"));

		driver.get(authorizeUrl);
		Thread.sleep(4000);

		//enter username
		WebElement userId = (new WebDriverWait(driver, Duration.ofSeconds(10)))
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[type='text']"))));
		userId.sendKeys(prop.getProperty("ebay.app.userid"));
		
		driver.findElement(By.name("signin-continue-btn")).click();
		Thread.sleep(4000);
		
		//enter password
		WebElement password = (new WebDriverWait(driver, Duration.ofSeconds(10)))
				.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("input[type='password']"))));
				
		password.sendKeys(prop.getProperty("ebay.app.userpass"));
		
		//submit
		Thread.sleep(4000);
		driver.findElement(By.name("sgnBt")).submit();

		String url = null;
		if (driver.getCurrentUrl().contains("code="))
		{
			url = driver.getCurrentUrl();
		}
		else
		{
			WebElement agreeBtn = (new WebDriverWait(driver, Duration.ofSeconds(10)))
					.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("submit"))));

			agreeBtn.submit();
			Thread.sleep(5000);
			url = driver.getCurrentUrl();
		}
		driver.quit();
		return url;
	}

	private String getAuthorizationCode() throws InterruptedException
	{
		String url = getAuthorizationResponseUrl();
		int codeIndex = url.indexOf("code=");
		String authorizationCode = null;
		if (codeIndex > 0)
		{
			Pattern pattern = Pattern.compile("code=(.*?)&");
			Matcher matcher = pattern.matcher(url);
			if (matcher.find())
			{
				authorizationCode = matcher.group(1);
			}
		}
		return authorizationCode;
	}

}
