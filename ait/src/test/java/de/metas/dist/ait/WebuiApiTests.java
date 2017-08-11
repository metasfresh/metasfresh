package de.metas.dist.ait;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;

import de.metas.dist.ait.login.AuthenticateResponse;
import de.metas.dist.ait.login.AvailableLanguages;
import de.metas.dist.ait.login.LoginClient;
import de.metas.dist.ait.sales.order.SalesOrder;
import de.metas.dist.ait.sales.order.SalesOrderClient;

public class WebuiApiTests
{
	private static final String HOST = "localhost";

	private static final String PORT = "8080";

	private static final String ENDPOINT_ROOT = "rest/api";

	private RequestSpecification spec;

	@Before
	public void initSpec()
	{
		spec = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.setBaseUri("http://" + HOST + ":" + PORT + "/" + ENDPOINT_ROOT)
				.addFilter(new ResponseLoggingFilter())// log request and response for better debugging. You can also only log if a requests fails.
				.addFilter(new RequestLoggingFilter())
				.build();
	}

	/**
	 * Verifies that {@code en_US} is among the available languages and also the default language.
	 */
	@Test
	public void testAvailableLanguages()
	{
		final AvailableLanguages languagess = new LoginClient(spec).getAvailableLanguages();

		assertThat(languagess).isNotNull();
		assertThat(languagess.getDefaultValue()).isEqualTo("en_US");
		assertThat(languagess.getValuesMap()).containsKey("en_US");
	}

	@Test
	public void testLogInSuccess()
	{
		final LoginClient login = new LoginClient(spec);

		// initially not logged in
		assertThat(login.isLoggedIn()).isFalse();

		final AuthenticateResponse authResponse = login.authenticate("SuperUser", "System");
		assertThat(authResponse.isLoginComplete()).isFalse();
		assertThat(authResponse.getRoles()).isNotEmpty(); // expecting at least one role

		// still not "fully" logged in
		assertThat(login.isLoggedIn()).isFalse();

		// choose a role
		login.loginComplete(authResponse.getRoles().get(0));

		assertThat(login.isLoggedIn()).isTrue();
	}

	@Test
	public void testNewSalesOrder()
	{
		final LoginClient login = new LoginClient(spec);
		final AuthenticateResponse authResponse = login.authenticate("SuperUser", "System");
		login.loginComplete(authResponse.getRoles().get(0));

		final SalesOrder newSalesOrder = new SalesOrderClient(login)
				.newSalesOrder();
		assertThat(newSalesOrder).isNotNull();
	}

}
