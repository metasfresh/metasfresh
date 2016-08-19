package de.metas.ui.web.window;

import java.util.Arrays;
import java.util.List;

import org.adempiere.util.collections.ListUtils;
import org.junit.Ignore;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent.JSONOperation;
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
		// LogManager.dumpAllLevelsUpToRoot("org.apache.http.impl.conn.PoolingHttpClientConnectionManager");

		do
		{
			try
			{
				final ManualTest tester = new ManualTest();
				tester.test1();
			}
			catch (final Exception e)
			{
				e.printStackTrace();
				System.err.flush();
				System.out.flush();
			}

			System.out.println("Press any key to repeat...");
			System.out.flush();
			System.in.read();
		}
		while (true);
	}

	private void test1()
	{
		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Get Order layout");
		{
			restClient.layout(143);
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Create Order Header");
		String orderId = "NEW";
		{
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "C_BPartner_ID", JSONLookupValue.of("2156425", "test")) //
					, JSONDocumentChangedEvent.of(JSONOperation.replace, "M_Shipper_ID", JSONLookupValue.of("1000000", "test")) //
			);

			final List<JSONDocument> response = restClient.commit(143, orderId, events);
			orderId = getId(response, orderId);
			System.out.println("=> orderId=" + orderId);
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Update Order Header: POReference");
		{
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "POReference", "111") //
			);

			final List<JSONDocument> response = restClient.commit(143, orderId, events);
			orderId = getId(response, orderId);
			System.out.println("=> orderId=" + orderId);
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Create Order Line");
		String rowId = "NEW";
		{
			final List<JSONDocumentChangedEvent> events = Arrays.asList(
					JSONDocumentChangedEvent.of(JSONOperation.replace, "M_Product_ID", JSONLookupValue.of("2005577", "Convenience Salat 250g")) //
					, JSONDocumentChangedEvent.of(JSONOperation.replace, "M_AttributeSetInstance_ID", JSONLookupValue.of("0", "NEW")) //
			);

			final List<JSONDocument> response = restClient.commit(143, orderId, "1", rowId, events);
			rowId = getRowId(response, rowId);
			System.out.println("=> rowId=" + rowId);
		}

		//
		//
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Test C_Order.C_BPartner_ID lookup");
		{
			restClient.dropdown(143, orderId, null, null, "C_BPartner_ID");
		}
	}

	//
	//
	//

	private final WindowRestControllerClient restClient;

	public ManualTest()
	{
		super();
		restClient = new WindowRestControllerClient("localhost", 8080);
	}

	// public String layout(final int type)
	// {
	// final String json = httpGet("/layout?type=" + type);
	//
	// // NOTE: deserialization is not supported
	// // final JSONDocumentLayout layout = jsonObjectMapper.readValue(httpcon.getInputStream(), JSONDocumentLayout.class);
	// // return layout;
	//
	// return json;
	// }

	// public List<JSONDocument> commit(final int type, final String id, final List<JSONDocumentChangedEvent> events)
	// {
	// final String detailId = null;
	// final String rowId = null;
	// return commit(type, id, detailId, rowId, events);
	// }

	// public List<JSONDocument> commit(final int type, final String id, final String detailId, final String rowId, final List<JSONDocumentChangedEvent> events)
	// {
	// final String httpPath = "/commit?type=" + type + "&id=" + Strings.nullToEmpty(id) + "&tabid=" + Strings.nullToEmpty(detailId) + "&rowId=" + Strings.nullToEmpty(rowId);
	// System.out.println("COMMIT " + httpPath);
	// System.out.println("" + events);
	//
	// final String jsonResult = httpPatch(httpPath, events);
	// System.out.println("GOT ANSWER: " + jsonResult);
	// try
	// {
	// final JSONDocument[] jsonDocuments = jsonObjectMapper.readValue(jsonResult, JSONDocument[].class);
	// return Arrays.asList(jsonDocuments);
	// }
	// catch (final IOException e)
	// {
	// throw Throwables.propagate(e);
	// }
	// }

	// private String httpGet(final String path)
	// {
	// try
	// {
	// final CloseableHttpClient httpClient = HttpClients.createDefault();
	// final HttpUriRequest request = new HttpGet(URL_BASE + path);
	// final CloseableHttpResponse response = httpClient.execute(request);
	// return readString(response.getEntity().getContent());
	// }
	// catch (final Exception e)
	// {
	// throw Throwables.propagate(e);
	// }
	// }
	//
	// private String httpPatch(final String path, final Object jsonData)
	// {
	// try
	// {
	// final String requestJsonString = jsonObjectMapper.writeValueAsString(jsonData);
	// final CloseableHttpClient httpClient = HttpClients.createDefault();
	// final HttpPatch request = new HttpPatch(URL_BASE + path);
	// request.setHeader("Content-Type", "application/json");
	// request.setHeader("Accept", "*/*");
	// request.setEntity(new StringEntity(requestJsonString));
	// final CloseableHttpResponse response = httpClient.execute(request);
	// return readString(response.getEntity().getContent());
	// }
	// catch (final Exception e)
	// {
	// throw Throwables.propagate(e);
	// }
	// }

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

	// private static final String readString(final InputStream in) throws IOException
	// {
	// final StringBuilder result = new StringBuilder();
	// final byte[] buf = new byte[4096];
	// int len = 0;
	// while ((len = in.read(buf)) > 0)
	// {
	// final String str = new String(buf, 0, len);
	// result.append(str);
	// }
	//
	// return result.toString();
	// }

	private static String getId(final List<JSONDocument> jsonDocuments, final String defaultValue)
	{
		final JSONDocument jsonDocument = ListUtils.singleElement(jsonDocuments);
		return jsonDocument.getId();
	}

	private static String getRowId(final List<JSONDocument> jsonDocuments, final String defaultValue)
	{
		final JSONDocument jsonDocument = ListUtils.singleElement(jsonDocuments);
		return jsonDocument.getRowId();
	}

}
