package de.metas.report.jasper.client;

import static de.metas.util.Check.assumeGreaterThanZero;
import static de.metas.util.Check.assumeNotEmpty;
import static de.metas.util.Check.assumeNotNull;

/*
 * #%L
 * de.metas.report.jasper.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import com.google.common.io.Closeables;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.report.jasper.IJasperServer;
import de.metas.adempiere.report.jasper.JasperConstants;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.exceptions.ServiceConnectionException;

public class RemoteServletInvoker implements IJasperServer
{
	public static final String SYSCONFIG_JRServerServlet = "de.metas.adempiere.report.jasper.JRServerServlet";
	public static final String SYSCONFIG_JRServerServlet_DEFAULT = "http://localhost:8080/adempiereJasper/ReportServlet";

	private final Logger logger = LogManager.getLogger(getClass());

	private final String jrServlet;
	private final String mgtServlet;

	/**
	 * Timeout for URL connection in milliseconds ( -> 5 seconds)
	 */
	public final int connectTimeout = 5000;

	/**
	 * Timeout for reading from URL in milliseconds ( -> 5 seconds)
	 */
	public final int readTimeout = 5000;

	public RemoteServletInvoker()
	{
		jrServlet = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_JRServerServlet, SYSCONFIG_JRServerServlet_DEFAULT);

		// Set MgtServlet
		{
			int idx = jrServlet.lastIndexOf('/');
			mgtServlet = jrServlet.substring(0, idx) + JasperConstants.JRSERVERSERVLET_MGTSERVLET_SUFFIX;
		}
	}

	@Override
	public byte[] report(
			int AD_Process_ID,
			int AD_PInstance_ID,
			String adLanguage,
			OutputType outputType)
	{
		final String urlStr = jrServlet + "?"
				+ "AD_Process_ID=" + assumeGreaterThanZero(AD_Process_ID, "AD_Process_ID")
				+ "&AD_PInstance_ID=" + assumeGreaterThanZero(AD_PInstance_ID, "AD_PInstance_ID")
				+ "&AD_Language=" + assumeNotEmpty(adLanguage, "adLanguage")
				+ "&output=" + assumeNotNull(outputType, "outputType").toString();

		logger.info("Calling URL " + urlStr);

		InputStream in = null;
		try
		{
			in = new URL(urlStr).openStream();

			final ByteArrayOutputStream out = new ByteArrayOutputStream();

			final byte[] buf = new byte[4096];

			int len = -1;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
			return out.toByteArray();
		}
		catch (final ConnectException e)
		{
			writeLog(urlStr, e);

			final int retryInMillis = Services.get(ISysConfigBL.class).getIntValue(JRClient.SYSCONFIG_JRServerRetryMS, -1);
			throw new ServiceConnectionException(urlStr, retryInMillis, e);
		}
		catch (final IOException e)
		{
			writeLog(urlStr, e);
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("URL", urlStr);
		}
		finally
		{
			Closeables.closeQuietly(in);
			in = null;
		}
	}

	private void writeLog(final String urlStr, final IOException e)
	{
		Loggables.withLogger(logger, Level.ERROR)
				.addLog("Caught {} trying to invoke URL {}; message: {}", e.getClass(), urlStr, e.getMessage());
	}

	@Override
	public void cacheReset()
	{
		try
		{
			cacheReset0();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private void cacheReset0() throws MalformedURLException, IOException
	{
		final String urlStr = mgtServlet + "?"
				+ JasperConstants.MGTSERVLET_PARAM_Action + "=" + JasperConstants.MGTSERVLET_ACTION_CacheReset;
		logger.info("Calling URL " + urlStr);

		InputStream in = null;
		URLConnection urlConnection = null;
		try
		{
			final URL url = new URL(urlStr);
			urlConnection = url.openConnection();

			// connect timeout and read timeout are both 5 seconds
			urlConnection.setConnectTimeout(connectTimeout);
			urlConnection.setReadTimeout(readTimeout);

			in = urlConnection.getInputStream();

			final ByteArrayOutputStream out = new ByteArrayOutputStream();

			final byte[] buf = new byte[4096];

			int len = -1;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}

			String result = new String(out.toByteArray());
			logger.info("result: " + result);
		}
		finally
		{
			Closeables.closeQuietly(in);

			if (urlConnection instanceof HttpURLConnection)
			{
				try
				{
					((HttpURLConnection)urlConnection).disconnect();
				}
				catch (Exception e)
				{
					logger.warn("Failed to disconnect from " + urlConnection + ". Ignored.", e);
				}
			}
		}
	}
}
