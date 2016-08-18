package de.metas.ui.web.window;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Ignore;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent.JSONOperation;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Ignore
public class ManualTest
{
	public static void main(final String[] args) throws Exception
	{
		LogManager.setLoggerLevel(Logger.ROOT_LOGGER_NAME, Level.INFO);
		LogManager.dumpAllLevelsUpToRoot("org.apache.http.impl.conn.PoolingHttpClientConnectionManager");

		final ManualTest tester = new ManualTest();

		do
		{
			try
			{
				tester.test1();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.err.flush();
				System.out.flush();
			}

			System.out.println("Press any key to repeat...");
			System.in.read();
		}
		while (true);
	}

	private void test1()
	{
		System.out.println("---------------------------------------------------------------------");
		String orderId = "NEW";
		{
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "C_BPartner_ID", JSONLookupValue.of("2156425", "test")) //
					, JSONDocumentChangedEvent.of(JSONOperation.replace, "M_Shipper_ID", JSONLookupValue.of("1000000", "test")) //
			);

			final JSONDocumentField[] response = commit(143, orderId, events, JSONDocumentField[].class);
			orderId = getId(response, orderId);
			System.out.println("=> orderId=" + orderId);
			System.out.println(Joiner.on("\n").join(response));
		}

		System.out.println("---------------------------------------------------------------------");
		{
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "POReference", "111") //
			);

			final JSONDocumentField[] response = commit(143, orderId, events, JSONDocumentField[].class);
			orderId = getId(response, orderId);
			System.out.println("=> orderId=" + orderId);
			System.out.println(Joiner.on("\n").join(response));

		}

		System.out.println("---------------------------------------------------------------------");
		String rowId = "NEW";
		{
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "M_Product_ID", JSONLookupValue.of("2005577", "Convenience Salat 250g")) //
					, JSONDocumentChangedEvent.of(JSONOperation.replace, "M_AttributeSetInstance_ID", JSONLookupValue.of("0", "NEW")) //
			);

			final JSONDocumentField[] response = commit(143, orderId, "1", rowId, events, JSONDocumentField[].class);
			rowId = getId(response, rowId);
			System.out.println("=> rowId=" + rowId);
			System.out.println(Joiner.on("\n").join(response));
		}
	}

	private static final String URL_BASE = "http://localhost:8080/rest/api/window";
	private final ObjectMapper jsonObjectMapper;

	public ManualTest()
	{
		super();
		jsonObjectMapper = new ObjectMapper();
	}

	public String layout(final int type)
	{
		final String json = httpGet("/layout?type=" + type);

		// NOTE: deserialization is not supported
		// final JSONDocumentLayout layout = jsonObjectMapper.readValue(httpcon.getInputStream(), JSONDocumentLayout.class);
		// return layout;

		return json;
	}

	public <T> T commit(final int type, final String id, final List<JSONDocumentChangedEvent> events, final Class<T> responseType)
	{
		final String detailId = null;
		final String rowId = null;
		return commit(type, id, detailId, rowId, events, responseType);
	}

	public <T> T commit(final int type, final String id, final String detailId, final String rowId, final List<JSONDocumentChangedEvent> events, final Class<T> responseType)
	{
		final String httpPath = "/commit?type=" + type + "&id=" + Strings.nullToEmpty(id) + "&tabid=" + Strings.nullToEmpty(detailId) + "&rowId=" + Strings.nullToEmpty(rowId);
		System.out.println("COMMIT " + httpPath);
		System.out.println("" + events);
		System.out.println("Expecting " + responseType);

		final String jsonResult = httpPatch(httpPath, events);
		System.out.println("GOT ANSWER: " + jsonResult);
		try
		{
			return jsonObjectMapper.readValue(jsonResult, responseType);
		}
		catch (final IOException e)
		{
			throw Throwables.propagate(e);
		}
	}

	private String httpGet(final String path)
	{
		try
		{
			final CloseableHttpClient httpClient = HttpClients.createDefault();
			final HttpUriRequest request = new HttpGet(URL_BASE + path);
			final CloseableHttpResponse response = httpClient.execute(request);
			return readString(response.getEntity().getContent());
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

	private String httpPatch(final String path, final Object jsonData)
	{
		try
		{
			final String requestJsonString = jsonObjectMapper.writeValueAsString(jsonData);
			final CloseableHttpClient httpClient = HttpClients.createDefault();
			final HttpPatch request = new HttpPatch(URL_BASE + path);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Accept", "*/*");
			request.setEntity(new StringEntity(requestJsonString));
			final CloseableHttpResponse response = httpClient.execute(request);
			return readString(response.getEntity().getContent());
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

	// private HttpURLConnection connect(final String method, final String path) throws IOException
	// {
	// final URL url = new URL(URL_BASE + path);
	//
	// final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	// conn.setDoOutput(true);
	// conn.setRequestProperty("Content-Type", "application/json");
	// conn.setRequestProperty("Accept", "application/json");
	//
	// if ("PATCH".equals(method))
	// {
	// conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
	// conn.setRequestMethod("POST");
	// }
	// else
	// {
	// conn.setRequestMethod(method);
	// }
	//
	// conn.connect();
	//
	// return conn;
	// }

	private static final String readString(final InputStream in) throws IOException
	{
		final StringBuilder result = new StringBuilder();
		final byte[] buf = new byte[4096];
		int len = 0;
		while ((len = in.read(buf)) > 0)
		{
			final String str = new String(buf, 0, len);
			result.append(str);
		}

		return result.toString();
	}

	private static String getId(final JSONDocumentField[] fields, final String defaultValue)
	{
		if (fields == null || fields.length == 0)
		{
			return defaultValue;
		}

		for (final JSONDocumentField field : fields)
		{
			if (JSONDocumentField.FIELD_VALUE_ID.equals(field.getField()))
			{
				return String.valueOf(field.getValue());
			}
		}

		return defaultValue;
	}
}
