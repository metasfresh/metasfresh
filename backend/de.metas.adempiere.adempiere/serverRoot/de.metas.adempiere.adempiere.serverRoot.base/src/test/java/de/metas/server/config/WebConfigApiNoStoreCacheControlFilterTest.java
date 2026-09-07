package de.metas.server.config;

import com.google.common.collect.ImmutableList;
import lombok.Value;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link WebConfig#apiNoStoreCacheControlFilter()} in an embedded tomcat, because the behaviour under test is a
 * response-commit semantic that a {@code MockHttpServletResponse} cannot reproduce - and, having no business or UI
 * dimension, warrants no cucumber/e2e tier.
 */
class WebConfigApiNoStoreCacheControlFilterTest
{
	/** Well above tomcat's default 8 KB response buffer, so the response is committed while the body is being written. */
	private static final int LARGE_BODY_CHUNK_SIZE = 4096;
	private static final int LARGE_BODY_CHUNK_COUNT = 16;
	private static final int LARGE_BODY_SIZE = LARGE_BODY_CHUNK_SIZE * LARGE_BODY_CHUNK_COUNT; // 64 KB

	private static final int SOCKET_TIMEOUT_MILLIS = 10_000;

	private WebServer webServer;
	private String baseUrl;

	@BeforeEach
	void startServer()
	{
		final FilterRegistrationBean<Filter> filterRegistration = new WebConfig().apiNoStoreCacheControlFilter();

		final AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.register(TestEndpointsConfig.class);

		final TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory(0);
		factory.setRegisterDefaultServlet(false);

		webServer = factory.getWebServer(servletContext -> {
			filterRegistration.onStartup(servletContext);

			final ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(mvcContext));
			dispatcher.setLoadOnStartup(1);
			dispatcher.addMapping("/");
		});
		webServer.start();

		baseUrl = "http://localhost:" + webServer.getPort();
	}

	@AfterEach
	void stopServer()
	{
		if (webServer != null)
		{
			webServer.stop();
			webServer = null;
		}
	}

	@Test
	void apiEndpointWithoutOwnCacheControl_getsNoStore() throws IOException
	{
		final Response response = get(TestEndpointsConfig.PATH_PLAIN);

		assertThat(response.statusCode).isEqualTo(200);
		assertThat(response.cacheControlValues).containsExactly("no-store");
	}

	@Test
	void apiEndpointSettingItsOwnCacheControl_isNotOverwritten() throws IOException
	{
		final Response response = get(TestEndpointsConfig.PATH_CACHEABLE);

		assertThat(response.statusCode).isEqualTo(200);
		// on ALL values, not on "the" header value: the append-vs-replace defect this guards against (see
		// WebConfig.CacheControlDefaultReplacingResponse) shows up only as a second Cache-Control header
		assertThat(response.cacheControlValues).containsExactly("max-age=10");
	}

	@Test
	void apiEndpointWithBodyExceedingTheResponseBuffer_stillGetsNoStore() throws IOException
	{
		final Response response = get(TestEndpointsConfig.PATH_LARGE_BODY);

		assertThat(response.statusCode).isEqualTo(200);
		assertThat(response.bodySize)
				.as("the body has to actually exceed the container response buffer, else the test proves nothing")
				.isEqualTo(LARGE_BODY_SIZE);
		assertThat(response.cacheControlValues).containsExactly("no-store");
	}

	private Response get(final String path) throws IOException
	{
		final HttpURLConnection connection = (HttpURLConnection)new URL(baseUrl + path).openConnection();
		connection.setRequestMethod("GET");
		// fail fast instead of hanging forever if the embedded container does not answer
		connection.setConnectTimeout(SOCKET_TIMEOUT_MILLIS);
		connection.setReadTimeout(SOCKET_TIMEOUT_MILLIS);
		try
		{
			final int statusCode = connection.getResponseCode();
			final ImmutableList<String> cacheControlValues = getHeaderValues(connection, HttpHeaders.CACHE_CONTROL);
			final int bodySize = drain(connection.getInputStream());
			return new Response(statusCode, cacheControlValues, bodySize);
		}
		finally
		{
			connection.disconnect();
		}
	}

	/**
	 * ALL values sent for that header - the duplicate-header case is the whole point, and
	 * {@link HttpURLConnection#getHeaderField(String)} would hide it: the JDK scans its header list backwards and
	 * returns only the LAST value, so two Cache-Control headers look exactly like one.
	 * <p>
	 * The values come out last-sent-first, because {@code getHeaderFields()} builds its per-key lists by that same
	 * backwards scan. Left as-is rather than re-ordered here: every expectation in this class is a single value, so
	 * there is nothing to order, and a re-ordering no test can catch being wrong is worse than none.
	 */
	private static ImmutableList<String> getHeaderValues(final HttpURLConnection connection, final String headerName)
	{
		return connection.getHeaderFields().entrySet().stream()
				// HttpURLConnection does not normalise header-name case, so match case-insensitively
				.filter(entry -> entry.getKey() != null && entry.getKey().equalsIgnoreCase(headerName))
				.flatMap(entry -> entry.getValue().stream())
				.collect(ImmutableList.toImmutableList());
	}

	private static int drain(final InputStream in) throws IOException
	{
		try
		{
			final byte[] buffer = new byte[8192];
			int total = 0;
			int read;
			while ((read = in.read(buffer)) >= 0)
			{
				total += read;
			}
			return total;
		}
		finally
		{
			in.close();
		}
	}

	@Value
	private static class Response
	{
		int statusCode;
		ImmutableList<String> cacheControlValues;
		int bodySize;
	}

	@Configuration
	@EnableWebMvc
	@RestController
	public static class TestEndpointsConfig
	{
		static final String PATH_PLAIN = "/api/v2/test/plain";
		static final String PATH_CACHEABLE = "/api/v2/test/cacheable";
		static final String PATH_LARGE_BODY = "/api/v2/test/largeBody";

		/** An endpoint that says nothing about caching - the case the filter exists for. */
		@GetMapping(PATH_PLAIN)
		public ResponseEntity<String> plain()
		{
			return ResponseEntity.ok("ok");
		}

		/** Mirrors {@code de.metas.rest_api.v2.image.ImageRestController}: an endpoint that sets its own Cache-Control. */
		@GetMapping(PATH_CACHEABLE)
		public ResponseEntity<byte[]> cacheable()
		{
			return ResponseEntity.ok()
					.cacheControl(CacheControl.maxAge(Duration.ofSeconds(10)))
					.body("image-bytes".getBytes(StandardCharsets.UTF_8));
		}

		/**
		 * Mirrors the order/shipment/invoice PDF downloads: a body far bigger than the container response buffer, so the
		 * response is already committed by the time the filter regains control after the chain.
		 */
		@GetMapping(PATH_LARGE_BODY)
		public void largeBody(final HttpServletResponse response) throws IOException
		{
			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

			final byte[] chunk = new byte[LARGE_BODY_CHUNK_SIZE];
			Arrays.fill(chunk, (byte)'x');

			final OutputStream out = response.getOutputStream();
			for (int i = 0; i < LARGE_BODY_CHUNK_COUNT; i++)
			{
				out.write(chunk);
				out.flush();
			}
		}
	}
}
