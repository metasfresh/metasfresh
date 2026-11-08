/*
 * #%L
 * de.metas.util.web
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.util.web;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.audit.apirequest.config.ApiAuditConfigRepository;
import de.metas.audit.apirequest.request.ApiRequestAuditRepository;
import de.metas.audit.apirequest.request.log.ApiAuditRequestLogDAO;
import de.metas.audit.apirequest.response.ApiResponseAuditRepository;
import de.metas.audit.data.service.CompositeDataAuditService;
import de.metas.security.UserAuthTokenRepository;
import de.metas.user.UserGroupRepository;
import de.metas.util.Services;
import de.metas.util.web.audit.ApiAuditService;
import de.metas.util.web.filter.FilterRegistrationConfig;
import de.metas.util.web.security.UserAuthTokenFilterConfiguration;
import de.metas.util.web.security.UserAuthTokenService;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_API_Audit_Config;
import org.compiere.model.I_API_Request_Audit;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * An embedded Tomcat with the app server's REST setup, for tests that need the real {@code /api/v2} filter chain:
 * <ul>
 * <li>{@code @EnableWebMvc}, as in the app server's {@code de.metas.server.config.SwaggerConfig};</li>
 * <li>the app server's content negotiation, see {@link AppServerContentNegotiation};</li>
 * <li>the real {@link FilterRegistrationConfig} filters ({@code UserAuthTokenFilter}, {@code ApiAuditFilter}) with the real {@link ApiAuditService}, on the in-memory DB.</li>
 * </ul>
 * The caller initializes the in-memory DB ({@code AdempiereTestHelper}) before {@link #start(Class[])} and passes the controllers (and their dependencies) to test.
 * Requests are sent as raw HTTP/1.1, so the path and the {@code Accept} header are sent exactly as given.
 */
public final class AppServerLikeRestTestServer implements AutoCloseable
{
	private static final int SOCKET_TIMEOUT_MILLIS = 10_000;

	private final ConfigurableApplicationContext context;
	private final int port;

	private AppServerLikeRestTestServer(@NonNull final ConfigurableApplicationContext context)
	{
		this.context = context;
		this.port = ((ServletWebServerApplicationContext)context).getWebServer().getPort();
	}

	/**
	 * @param sources the controllers under test, plus any {@code @Configuration} classes providing their dependencies
	 */
	public static AppServerLikeRestTestServer start(@NonNull final Class<?>... sources)
	{
		final ConfigurableApplicationContext context = new SpringApplicationBuilder(AppServerLikeRestConfig.class)
				.sources(sources)
				.web(WebApplicationType.SERVLET)
				.profiles(Profiles.PROFILE_App)
				.properties("server.port=0", "spring.main.banner-mode=off")
				.run();
		return new AppServerLikeRestTestServer(context);
	}

	/**
	 * Creates an {@code API_Audit_Config} like the ones shipped by migration (no path prefix, synchronous audit, no response wrapping).
	 *
	 * @param method the HTTP method to audit, or {@code null} for any
	 */
	public static void createSynchronousAuditConfig(@Nullable final String method)
	{
		final I_API_Audit_Config auditConfig = newInstance(I_API_Audit_Config.class);
		auditConfig.setAD_Org_ID(0);
		auditConfig.setMethod(method);
		auditConfig.setPathPrefix(null);
		auditConfig.setSeqNo(9980);
		auditConfig.setIsBypassAudit(false);
		auditConfig.setIsForceProcessedAsync(false);
		auditConfig.setIsSynchronousAuditLoggingEnabled(true);
		auditConfig.setIsWrapApiResponse(false);
		saveRecord(auditConfig);
	}

	public static int countRequestAudits()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_API_Request_Audit.class).create().count();
	}

	@Override
	public void close()
	{
		context.close();
	}

	public <T> T getBean(@NonNull final Class<T> type)
	{
		return context.getBean(type);
	}

	public Response get(@NonNull final String path, @Nullable final String accept)
	{
		return send("GET", path, accept, null);
	}

	public Response postJson(@NonNull final String path, @Nullable final String accept, @NonNull final String jsonBody)
	{
		return send("POST", path, accept, jsonBody);
	}

	private Response send(
			@NonNull final String method,
			@NonNull final String path,
			@Nullable final String accept,
			@Nullable final String jsonBody)
	{
		final byte[] bodyBytes = jsonBody != null ? jsonBody.getBytes(StandardCharsets.UTF_8) : null;

		final StringBuilder request = new StringBuilder()
				.append(method).append(" ").append(path).append(" HTTP/1.1\r\n")
				.append("Host: localhost\r\n");
		if (accept != null)
		{
			request.append("Accept: ").append(accept).append("\r\n");
		}
		if (bodyBytes != null)
		{
			request.append("Content-Type: ").append(MediaType.APPLICATION_JSON_VALUE).append("\r\n")
					.append("Content-Length: ").append(bodyBytes.length).append("\r\n");
		}
		request.append("Connection: close\r\n\r\n");

		try (final Socket socket = new Socket("localhost", port))
		{
			socket.setSoTimeout(SOCKET_TIMEOUT_MILLIS);
			final OutputStream out = socket.getOutputStream();
			out.write(request.toString().getBytes(StandardCharsets.US_ASCII));
			if (bodyBytes != null)
			{
				out.write(bodyBytes);
			}
			out.flush();

			return Response.parse(readFully(socket.getInputStream()));
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static byte[] readFully(@NonNull final InputStream in) throws IOException
	{
		final ByteArrayOutputStream result = new ByteArrayOutputStream();
		final byte[] buffer = new byte[8192];
		int read;
		while ((read = in.read(buffer)) != -1)
		{
			result.write(buffer, 0, read);
		}
		return result.toByteArray();
	}

	@Value
	public static class Response
	{
		String statusLine;
		@Nullable String contentType;
		/**
		 * The body bytes exactly as sent by the server (de-chunked).
		 */
		byte[] bodyBytes;

		public int getStatusCode()
		{
			return Integer.parseInt(statusLine.split(" ")[1]);
		}

		/**
		 * @return the body, decoded as UTF-8
		 */
		public String getBody()
		{
			return new String(bodyBytes, StandardCharsets.UTF_8);
		}

		@Nullable
		public MediaType getMediaType()
		{
			return contentType != null ? MediaType.parseMediaType(contentType) : null;
		}

		@Override
		public String toString()
		{
			return "Response(statusLine=" + statusLine + ", contentType=" + contentType + ", body=" + getBody() + ")";
		}

		/**
		 * @param raw the raw HTTP response; it is handled as ISO-8859-1, which maps each byte to one char, so the body bytes are kept unchanged
		 */
		private static Response parse(@NonNull final byte[] raw)
		{
			final String rawString = new String(raw, StandardCharsets.ISO_8859_1);
			final int headerEnd = rawString.indexOf("\r\n\r\n");
			final String[] headLines = rawString.substring(0, headerEnd).split("\r\n");

			String contentType = null;
			boolean chunked = false;
			for (final String line : headLines)
			{
				final String lineLC = line.toLowerCase(Locale.ROOT);
				if (lineLC.startsWith("content-type:"))
				{
					contentType = line.substring("content-type:".length()).trim();
				}
				else if (lineLC.startsWith("transfer-encoding:") && lineLC.contains("chunked"))
				{
					chunked = true;
				}
			}

			final String rawBody = rawString.substring(headerEnd + 4);
			final String body = chunked ? dechunk(rawBody) : rawBody;
			return new Response(headLines[0], contentType, body.getBytes(StandardCharsets.ISO_8859_1));
		}

		private static String dechunk(@NonNull final String chunkedBody)
		{
			final StringBuilder body = new StringBuilder();
			int pos = 0;
			while (true)
			{
				final int lineEnd = chunkedBody.indexOf("\r\n", pos);
				final int size = Integer.parseInt(chunkedBody.substring(pos, lineEnd).trim(), 16);
				if (size == 0)
				{
					return body.toString();
				}
				body.append(chunkedBody, lineEnd + 2, lineEnd + 2 + size);
				pos = lineEnd + 2 + size + 2;
			}
		}
	}

	@Configuration
	@EnableWebMvc
	@ImportAutoConfiguration({ ServletWebServerFactoryAutoConfiguration.class, DispatcherServletAutoConfiguration.class })
	@Import({ AppServerContentNegotiation.class, FilterRegistrationConfig.class, UserAuthTokenFilterConfiguration.class })
	static class AppServerLikeRestConfig
	{
		@Bean
		public UserAuthTokenService userAuthTokenService()
		{
			return new UserAuthTokenService(new UserAuthTokenRepository());
		}

		@Bean
		public ApiAuditService apiAuditService()
		{
			return new ApiAuditService(
					new ApiAuditConfigRepository(),
					new ApiRequestAuditRepository(),
					new ApiResponseAuditRepository(),
					new ApiAuditRequestLogDAO(),
					new UserGroupRepository(),
					new CompositeDataAuditService(ImmutableList.of()));
		}
	}

	/**
	 * Mirrors {@code de.metas.server.config.WebConfig#configureContentNegotiation}, whose module is not on the test classpath.
	 * If that method changes, this mirror has to follow.
	 */
	@Configuration
	static class AppServerContentNegotiation implements WebMvcConfigurer
	{
		@Override
		public void configureContentNegotiation(final ContentNegotiationConfigurer configurer)
		{
			configurer.defaultContentType(MediaType.APPLICATION_JSON);
		}
	}
}
