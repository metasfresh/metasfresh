package de.metas.ui.web.window;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;

import de.metas.ui.web.window.controller.IWindowRestController;
import de.metas.ui.web.window.controller.WindowRestController;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayout;
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

public class WindowRestControllerClient implements IWindowRestController
{
	private final String urlBase;
	private final ObjectMapper jsonObjectMapper = new ObjectMapper();

	public WindowRestControllerClient(final String host, final int port)
	{
		super();
		urlBase = "http://" + host + ":" + port + WindowRestController.ENDPOINT;
	}

	@Override
	public JSONDocumentLayout layout(final int adWindowId, final String detailId, final boolean advanced)
	{
		final String json = httpGet("/layout?type=" + adWindowId + "&tabid=" + Strings.emptyToNull(detailId) + "&advanced=" + advanced);
		final JSONDocumentLayout layout = fromJson(json, JSONDocumentLayout.class);
		System.out.println("GOT layout:\n" + toJson(layout));
		return layout;
	}

	@Override
	public JSONDocumentLayout sideListLayout(final int adWindowId)
	{
		final String json = httpGet("/sideListLayout?type=" + adWindowId);
		final JSONDocumentLayout layoutSideList = fromJson(json, JSONDocumentLayout.class);
		System.out.println("GOT side list layout:\n" + toJson(layoutSideList));
		return layoutSideList;
	}

	@Override
	public List<JSONDocument> data(final int adWindowId, final String idStr, final String detailId, final String rowIdStr, final String fieldsListStr, final boolean advanced)
	{
		final String httpPath = "/data?type=" + adWindowId + "&id=" + Strings.nullToEmpty(idStr) + "&tabid=" + Strings.nullToEmpty(detailId) + "&rowId=" + Strings.nullToEmpty(rowIdStr)
				+ "&fields=" + Strings.nullToEmpty(fieldsListStr)
				+ "&advanced=" + advanced;

		final String jsonResult = httpGet(httpPath);
		final JSONDocument[] jsonDocuments = fromJson(jsonResult, JSONDocument[].class);
		System.out.println("GOT data:\n" + toJson(jsonDocuments));
		return Arrays.asList(jsonDocuments);
	}

	public List<JSONDocument> commit(final int type, final String id, final List<JSONDocumentChangedEvent> events)
	{
		final String detailId = null;
		final String rowId = null;
		final boolean advanced = false;
		return commit(type, id, detailId, rowId, advanced, events);
	}

	@Override
	public List<JSONDocument> commit(final int adWindowId, final String idStr, final String detailId, final String rowIdStr, final boolean advanced, final List<JSONDocumentChangedEvent> events)
	{
		final String httpPath = "/commit?type=" + adWindowId + "&id=" + Strings.nullToEmpty(idStr)
				+ "&tabid=" + Strings.nullToEmpty(detailId) + "&rowId=" + Strings.nullToEmpty(rowIdStr)
				+ "&advanced=" + advanced;

		System.out.println("COMMIT " + httpPath);
		System.out.println("" + events);

		final String jsonResult = httpPatch(httpPath, events);

		final JSONDocument[] jsonDocuments = fromJson(jsonResult, JSONDocument[].class);
		System.out.println("GOT documents:\n" + toJson(jsonDocuments));
		return Arrays.asList(jsonDocuments);
	}

	@Override
	public List<JSONDocument> delete(final int adWindowId, final String idStr, final String detailId, final String rowIdStr)
	{
		final String httpPath = "/delete?type=" + adWindowId + "&id=" + Strings.nullToEmpty(idStr) + "&tabid=" + Strings.nullToEmpty(detailId) + "&rowId=" + Strings.nullToEmpty(rowIdStr);
		System.out.println("DELETE " + httpPath);

		final String jsonResult = httpDelete(httpPath);

		final JSONDocument[] jsonDocuments = fromJson(jsonResult, JSONDocument[].class);
		System.out.println("GOT documents:\n" + toJson(jsonDocuments));
		return Arrays.asList(jsonDocuments);
	}

	@Override
	public List<JSONLookupValue> typeahead(final int adWindowId, final String idStr, final String detailId, final String rowIdStr, final String fieldName, final String query)
	{
		final String httpPath = "/dropdown?type=" + adWindowId + "&id=" + Strings.nullToEmpty(idStr) + "&tabid=" + Strings.nullToEmpty(detailId) + "&rowId=" + Strings.nullToEmpty(rowIdStr)
				+ "&field=" + fieldName //
				+ "&query=" + query;

		final String json = httpGet(httpPath);
		final JSONLookupValue[] jsonLookupValues = fromJson(json, JSONLookupValue[].class);
		System.out.println("GOT lookup values:\n" + toJson(jsonLookupValues));
		return Arrays.asList(jsonLookupValues);
	}

	public List<JSONLookupValue> dropdown(final int adWindowId, final String idStr, final String fieldName)
	{
		final String detailId = null;
		final String rowIdStr = null;
		return dropdown(adWindowId, idStr, detailId, rowIdStr, fieldName);
	}

	@Override
	public List<JSONLookupValue> dropdown(final int adWindowId, final String idStr, final String detailId, final String rowIdStr, final String fieldName)
	{
		final String httpPath = "/dropdown?type=" + adWindowId + "&id=" + Strings.nullToEmpty(idStr) + "&tabid=" + Strings.nullToEmpty(detailId) + "&rowId=" + Strings.nullToEmpty(rowIdStr)
				+ "&field=" + fieldName;

		final String json = httpGet(httpPath);
		final JSONLookupValue[] jsonLookupValues = fromJson(json, JSONLookupValue[].class);
		System.out.println("GOT lookup values:\n" + toJson(jsonLookupValues));
		return Arrays.asList(jsonLookupValues);
	}

	private <T> T fromJson(final String json, final Class<T> valueType)
	{
		try
		{
			return jsonObjectMapper.readValue(json, valueType);
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

	private String toJson(final Object value)
	{
		try
		{
			return jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
		}
		catch (final JsonProcessingException e)
		{
			throw Throwables.propagate(e);
		}
	}

	private String httpGet(final String path)
	{
		try (final CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			System.out.println("GET " + path);
			final HttpUriRequest request = new HttpGet(urlBase + path);

			final Stopwatch stopwatch = Stopwatch.createStarted();
			final CloseableHttpResponse response = httpClient.execute(request);
			stopwatch.stop();

			final String responseStr = readString(response.getEntity().getContent());
			System.out.println("GOT ANSWER in " + stopwatch + ": " + responseStr);

			return responseStr;
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

	private String httpPatch(final String path, final Object jsonData)
	{
		try (final CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			System.out.println("PATCH " + path);
			System.out.println("Using: " + jsonData);

			final String requestJsonString = jsonObjectMapper.writeValueAsString(jsonData);
			final HttpPatch request = new HttpPatch(urlBase + path);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Accept", "*/*");
			request.setEntity(new StringEntity(requestJsonString));

			final Stopwatch stopwatch = Stopwatch.createStarted();
			final CloseableHttpResponse response = httpClient.execute(request);
			stopwatch.stop();

			final String responseStr = readString(response.getEntity().getContent());
			System.out.println("GOT ANSWER in " + stopwatch + ": " + responseStr);

			return responseStr;
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

	private String httpDelete(final String path)
	{
		try (final CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			System.out.println("DELETE " + path);

			final HttpDelete request = new HttpDelete(urlBase + path);
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Accept", "*/*");

			final Stopwatch stopwatch = Stopwatch.createStarted();
			final CloseableHttpResponse response = httpClient.execute(request);
			stopwatch.stop();

			final String responseStr = readString(response.getEntity().getContent());
			System.out.println("GOT ANSWER in " + stopwatch + ": " + responseStr);

			return responseStr;
		}
		catch (final Exception e)
		{
			throw Throwables.propagate(e);
		}
	}

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
}
