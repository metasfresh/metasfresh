package de.metas.printing.client.engine;

/*
 * #%L
 * de.metas.printing.esb.client
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

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.metas.printing.client.Context;
import de.metas.printing.client.IPrintConnectionEndpoint;
import de.metas.printing.client.endpoint.LoginFailedPrintConnectionEndpointException;
import de.metas.printing.client.util.Util;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrinterHWList;

public class PrintingClientDaemon implements Runnable
{
	public static final String CTX_ROOT = "de.metas.printing.client.PrintingClientDaemon";
	public static final String CTX_PollIntervalMs = CTX_ROOT + ".PollIntervalMs";
	public static final Integer DEFAULT_PollIntervalMs = 1 * 1000; // 1sec

	private final transient Logger log = Logger.getLogger(getClass().getName());

	private final int pollInterval;
	private final IPrintConnectionEndpoint connection;
	private final PrintingEngine printingEngine;

	private final AtomicBoolean stop = new AtomicBoolean(false);

	public PrintingClientDaemon()
	{
		super();

		final Context ctx = Context.getContext();

		pollInterval = ctx.getPropertyAsInt(CTX_PollIntervalMs, DEFAULT_PollIntervalMs);
		if (pollInterval <= 0)
		{
			throw new RuntimeException("Invalid " + CTX_PollIntervalMs + " value: " + pollInterval);
		}

		connection = ctx.getInstance(Context.CTX_PrintConnectionEndpoint, IPrintConnectionEndpoint.class);
		printingEngine = PrintingEngine.get();
	}

	@Override
	public void run()
	{
		loginIfNeeded();

		//
		// Create and send what hardware printers do we have here
		try
		{
			final PrinterHWList printerHWList = PrintingEngine.get().createPrinterHW();
			connection.addPrinterHW(printerHWList);
		}
		catch (final Exception e)
		{
			// 04044: until we found the real error, don't show to the user
			// final IUserInterface ui = Context.getContext().getInstance(Context.CTX_UserInterface, IUserInterface.class);
			// ui.showError("Error", e);

			logException("addPrinterHW", e);
		}

		while (true && !stop.get())
		{
			try
			{
				if (Thread.currentThread().isInterrupted())
				{
					log.info("Thread was interrupted. Stoping daemon.");
					break;
				}

				// Sleeping first, so i case of an exception, we will sleep before calling runOnce() again.
				// if we called runOnce() and then slept, a recurring exception would cause the ESB to be flooded with HTTP requests.
				log.finest("Sleeping " + pollInterval + "ms");
				Thread.sleep(pollInterval);

				runOnce();
			}
			catch (final InterruptedException e)
			{
				log.info("Interrupted signal received. Stopping daemon.");
				return;
			}
			catch (final Exception e)
			{
				// 04044: log error but continue
				// until we found the real error, don't show to the user
				// final IUserInterface ui = Context.getContext().getInstance(Context.CTX_UserInterface, IUserInterface.class);
				// ui.showError("Error", e);
				logException("poll for print package", e);
			}
		}
	}

	private final IPrintConnectionEndpoint getConnection()
	{
		return connection;
	}

	private void loginIfNeeded()
	{
		final Context context = Context.getContext();

		//
		// Check if already logged in
		final String sessionIdOld = context.getProperty(Context.CTX_SessionId);
		if (sessionIdOld != null)
		{
			// already logged in
			return;
		}

		//
		// Login
		final LoginRequest loginRequest = new LoginRequest();
		final String username = context.getProperty(Context.CTX_Login_Username);
		final String password = context.getProperty(Context.CTX_Login_Password);
		final String hostkey = context.getProperty(Context.CTX_Login_HostKey);

		loginRequest.setUsername(username);
		loginRequest.setPassword(password);
		loginRequest.setHostKey(hostkey);

		final IPrintConnectionEndpoint connection = getConnection();
		final LoginResponse loginResponse = connection.login(loginRequest);

		//
		// Validate login result
		final String sessionId = loginResponse.getSessionId();
		if (sessionId == null || sessionId.trim().isEmpty())
		{
			throw new LoginFailedPrintConnectionEndpointException("Login failed. "
					+ "\nRequest: " + loginRequest
					+ "\nResponse: " + loginResponse);
		}

		//
		// We are logged in
		// => Update the context
		log.info("Successfully logged in as user " + username + ". Received sessionId=" + sessionId);
		context.setProperty(Context.CTX_SessionId, sessionId);
	}

	private void logException(final String context, final Exception e)
	{
		// note: don't ask for socket timeout because it could be that we need db connection for this and we don't have it
		// final int ctxSocketTimeout = Context.getContext().getPropertyAsInt(RestHttpPrintConnectionEndpoint.CTX_SocketTimeoutMillis, RestHttpPrintConnectionEndpoint.DEFAULT_SocketTimeoutMillis);

		final StringBuilder sb = new StringBuilder();
		sb.append("Exception during ").append(context).append(":").append(e == null ? "" : e.getLocalizedMessage());
		sb.append(" \n Poll interval (ms): ").append(pollInterval);
		sb.append(" \n Stopped: ").append(stop.get());

		log.log(Level.SEVERE, sb.toString(), e);
	}

	public void runOnce()
	{
		log.finest("Polling for next package");

		final PrintPackage printPackage = connection.getNextPrintPackage();
		if (printPackage == null)
		{
			log.finest("No print package found. Returning");
			return;
		}

		InputStream in = null;
		try
		{
			in = connection.getPrintPackageData(printPackage);
			final PrintJobInstructionsConfirm response = printingEngine.print(printPackage, in);

			final String supressResponse = Context.getContext().getProperty(Context.CTX_Testing_Dont_RespondAfterPrinting, Context.DEFAULT_Dont_RespondAfterPrinting);
			if (Boolean.parseBoolean(supressResponse))
			{
				log.log(Level.INFO, "{} is true, so we do *not* report anything", Context.CTX_Testing_Dont_RespondAfterPrinting);
				return;
			}
			connection.sendPrintPackageResponse(printPackage, response);
		}
		finally
		{
			Util.close(in);
			in = null;
		}
	}

	/**
	 * Gets runnable's name, to be used as Thread name.
	 *
	 * @return runnable's name
	 */
	public String getName()
	{
		return toString();
	}

	@Override
	public String toString()
	{
		return "PrintingClientDaemon [pollInterval=" + pollInterval + ", connection=" + connection + ", printingEngine=" + printingEngine + "]";
	}

	public void stop()
	{
		stop.set(true);
	}
}
