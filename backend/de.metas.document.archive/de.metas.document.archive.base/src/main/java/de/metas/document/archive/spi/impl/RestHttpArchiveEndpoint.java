package de.metas.document.archive.spi.impl;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StreamUtils;
import org.adempiere.util.beans.IBeanEnconder;
import org.adempiere.util.beans.JsonBeanEncoder;
import org.adempiere.util.text.MapFormat;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.document.archive.esb.api.ArchiveGetDataRequest;
import de.metas.document.archive.esb.api.ArchiveGetDataResponse;
import de.metas.document.archive.esb.api.ArchiveSetDataRequest;
import de.metas.document.archive.esb.api.ArchiveSetDataResponse;
import de.metas.document.archive.esb.api.IArchiveEndpoint;
import de.metas.logging.LogManager;

/**
 * Rest HTTP Remote Archive Endpoint connector
 *
 * @author tsa
 *
 */
public class RestHttpArchiveEndpoint implements IArchiveEndpoint
{
	private static final transient Logger logger = LogManager.getLogger(RestHttpArchiveEndpoint.class);

	public static final String SYSCONFIG_ServerUrl = "de.metas.document.archive.spi.impl.RestHttpArchiveEndpoint.ServerUrl";
	private String serverUrl;

	private final HttpClient httpclient;
	private final IBeanEnconder beanEncoder;

	public RestHttpArchiveEndpoint()
	{
		beanEncoder = new JsonBeanEncoder();

		httpclient = new HttpClient();
		// httpclient.getParams().setSoTimeout(socketTimeout);
	}

	private int getAD_Session_ID()
	{
		return Env.getContextAsInt(Env.getCtx(), Env.CTXNAME_AD_Session_ID);
	}

	@Override
	public ArchiveGetDataResponse getArchiveData(final ArchiveGetDataRequest request)
	{
		request.setSessionId(getAD_Session_ID());

		final Map<String, String> params = createInitialUrlParams();
		final ArchiveGetDataResponse response = executePost(IArchiveEndpoint.PATH_GetArchiveData, params, request, ArchiveGetDataResponse.class);
		return response;
	}

	@Override
	public ArchiveSetDataResponse setArchiveData(final ArchiveSetDataRequest request)
	{
		request.setSessionId(getAD_Session_ID());

		final Map<String, String> params = createInitialUrlParams();
		final ArchiveSetDataResponse response = executePost(IArchiveEndpoint.PATH_SetArchiveData, params, request, ArchiveSetDataResponse.class);
		return response;
	}

	public String getServerUrl()
	{
		if (serverUrl != null)
		{
			return serverUrl;
		}

		final String serverUrl = Services.get(ISysConfigBL.class).getValue(
				RestHttpArchiveEndpoint.SYSCONFIG_ServerUrl,
				(String)null, // defaultValue
				Env.getAD_Client_ID(Env.getCtx()) // AD_Client_ID
				);
		Check.assumeNotNull(serverUrl, "SysConfig {} is set", RestHttpArchiveEndpoint.SYSCONFIG_ServerUrl);
		return serverUrl;
	}

	public void setServerUrl(final String serverUrl)
	{
		this.serverUrl = serverUrl;
	}

	protected Map<String, String> createInitialUrlParams()
	{
		final Map<String, String> params = new HashMap<String, String>();
		params.put(IArchiveEndpoint.PARAM_SessionId, Integer.toString(getAD_Session_ID()));
		return params;
	}

	private URL getURL(final String relativePath, final Map<String, String> params)
	{
		final String serverUrl = getServerUrl();

		final StringBuilder urlStrBuf = new StringBuilder();
		urlStrBuf.append(serverUrl);

		if (!urlStrBuf.toString().endsWith("/") && !IArchiveEndpoint.PATH_Service.startsWith("/"))
		{
			urlStrBuf.append("/");
		}
		urlStrBuf.append(IArchiveEndpoint.PATH_Service);

		if (!urlStrBuf.toString().endsWith("/") && !relativePath.startsWith("/"))
		{
			urlStrBuf.append("/");
		}
		urlStrBuf.append(relativePath);

		final String urlStr = MapFormat.format(urlStrBuf.toString(), params);

		try
		{
			return new URL(urlStr);
		}
		catch (final MalformedURLException e)
		{
			throw new RuntimeException("Invalid URL " + urlStr, e);
		}
	}

	private <T> T executePost(final String path, final Map<String, String> params, final Object request, final Class<T> responseClass)
	{
		final URL url = getURL(path, params);
		final PostMethod httpPost = new PostMethod(url.toString());

		final byte[] data = beanEncoder.encode(request);
		final RequestEntity entity = new ByteArrayRequestEntity(data, beanEncoder.getContentType());
		httpPost.setRequestEntity(entity);

		int result = -1;
		InputStream in = null;
		try
		{
			result = executeHttpPost(httpPost);
			in = httpPost.getResponseBodyAsStream();
			if (result != 200)
			{
				final String errorMsg = in == null ? "code " + result : StreamUtils.toString(in);
				throw new AdempiereException("Error " + result + " while posting on " + url + ": " + errorMsg);
			}

			if (responseClass != null)
			{
				final T response = beanEncoder.decodeStream(in, responseClass);
				return response;
			}
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			StreamUtils.close(in);
			in = null;
		}

		return null;
	}

	private int executeHttpPost(final PostMethod httpPost) throws HttpException, IOException
	{
		final int result = httpclient.executeMethod(httpPost);
		RestHttpArchiveEndpoint.logger.trace("Result code: {}", result);

		// final DefaultMethodRetryHandler retryHandler = new DefaultMethodRetryHandler();
		// retryHandler.setRetryCount(3);
		// httpPost.setMethodRetryHandler(retryHandler);

		return result;
	}
}
