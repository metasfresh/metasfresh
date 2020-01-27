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
import java.nio.charset.StandardCharsets;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import com.google.common.io.Closeables;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import de.metas.report.server.IReportServer;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportConstants;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.exceptions.ServiceConnectionException;

public class RemoteServletInvoker implements IReportServer
{
	private static final Logger logger = LogManager.getLogger(RemoteServletInvoker.class);

	private static final String SYSCONFIG_JRServerRetryMS = "de.metas.report.jasper.client.ServiceConnectionExceptionRetryAdvisedInMillis";

	private final String reportsServlet;
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
		final ISysConfigBL sysConfigs = Services.get(ISysConfigBL.class);

		reportsServlet = sysConfigs.getValue(
				ReportConstants.SYSCONFIG_ReportsServerServlet,
				ReportConstants.SYSCONFIG_ReportsServerServlet_DEFAULT);

		// Set MgtServlet
		{
			final int idx = reportsServlet.lastIndexOf('/');
			mgtServlet = reportsServlet.substring(0, idx) + ReportConstants.JRSERVERSERVLET_MGTSERVLET_SUFFIX;
		}
	}

	@Override
	public byte[] report(
			final int AD_Process_ID,
			final int AD_PInstance_ID,
			final String adLanguage,
			final OutputType outputType)
	{
		final String urlStr = reportsServlet + "?"
				+ "AD_Process_ID=" + assumeGreaterThanZero(AD_Process_ID, "AD_Process_ID")
				+ "&AD_PInstance_ID=" + assumeGreaterThanZero(AD_PInstance_ID, "AD_PInstance_ID")
				+ "&AD_Language=" + assumeNotEmpty(adLanguage, "adLanguage")
				+ "&output=" + assumeNotNull(outputType, "outputType").toString();

		logger.debug("Calling URL {}", urlStr);

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

			final int retryInMillis = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_JRServerRetryMS, -1);
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
		catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private void cacheReset0() throws MalformedURLException, IOException
	{
		final String urlStr = mgtServlet + "?"
				+ ReportConstants.MGTSERVLET_PARAM_Action + "=" + ReportConstants.MGTSERVLET_ACTION_CacheReset;
		logger.debug("Calling URL {}", urlStr);

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

			final String result = new String(out.toByteArray(), StandardCharsets.UTF_8);
			logger.debug("result: {}", result);
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
				catch (final Exception e)
				{
					logger.warn("Failed to disconnect from {}. Ignored.", urlConnection, e);
				}
			}
		}
	}
}
