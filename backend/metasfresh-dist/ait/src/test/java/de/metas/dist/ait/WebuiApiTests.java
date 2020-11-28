package de.metas.dist.ait;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
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
	public static final String METASFRESH_LOGIN = "metasfresh";

	private static final String METASFRESH_PASSWORD ="metasfresh";

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
	public void available_languages_contains_en_US()
	{
		final AvailableLanguages languagess = new LoginClient(spec).getAvailableLanguages();

		assertThat(languagess).isNotNull();
		assertThat(languagess.getDefaultValue()).isEqualTo("en_US");
		assertThat(languagess.getValuesMap()).containsKey("en_US");
	}

	/**
	 * Log in as metasfresh/metasfresh, assuming that metasfresh has has only one role.
	 */
	@Test
	public void logIn_one_role()
	{
		final LoginClient login = new LoginClient(spec);

		// initially not logged in
		assertThat(login.isLoggedIn()).isFalse();

		final AuthenticateResponse authResponse = login.authenticate(METASFRESH_LOGIN, METASFRESH_PASSWORD);
		assertThat(authResponse.getRoles()).hasSize(1);
		assertThat(authResponse.isLoginComplete()).isTrue();
	}

	/**
	 * Log in as metasfresh/metasfresh, assuming that metasfresh has has two roles.<br>
	 * In order to complete the login, one of the two roles needs to be selected.
	 */
	@Test
	@Ignore
	public void logIn_two_roles()
	{
		final LoginClient login = new LoginClient(spec);

		// initially not logged in
		assertThat(login.isLoggedIn()).isFalse();

		final AuthenticateResponse authResponse = login.authenticate(METASFRESH_LOGIN, METASFRESH_PASSWORD);
		assertThat(authResponse.getRoles().size()).isGreaterThan(1);
		assertThat(authResponse.isLoginComplete()).isFalse();

		// still not "fully" logged in
		assertThat(login.isLoggedIn()).isFalse();

		// choose a role
		login.loginComplete(authResponse.getRoles().get(0));

		assertThat(login.isLoggedIn()).isTrue();
	}

	@Test
	public void new_sales_order()
	{
		logIn_one_role();

		final SalesOrder newSalesOrder = new SalesOrderClient(spec)
				.newSalesOrder();
		assertThat(newSalesOrder).isNotNull();

		final Map<String, Field> fieldsByName = newSalesOrder.getFieldsByName();
		assertThat(fieldsByName).isNotNull();
	}

}
