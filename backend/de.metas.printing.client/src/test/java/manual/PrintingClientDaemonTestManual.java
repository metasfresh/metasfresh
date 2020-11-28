package manual;

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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;

import de.metas.printing.client.Context;
import de.metas.printing.client.encoder.JsonBeanEncoder;
import de.metas.printing.client.endpoint.DirectoryPrintConnectionEndpoint;
import de.metas.printing.client.engine.PrintingClientDaemon;
import de.metas.printing.client.engine.PrintingEngine;

@Ignore
public class PrintingClientDaemonTestManual
{
	public static String xpsPrintingServiceName = "Microsoft XPS Document Writer";

	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		//
		// Config
		{
			final String sessionId = "testSession" + new SimpleDateFormat("-yyyyMMdd-hhmmss").format(new Date());

			final Context ctx = Context.getContext();
			ctx.setProperty(Context.CTX_SessionId, sessionId);

			ctx.setProperty(Context.CTX_BeanEncoder, JsonBeanEncoder.class);

			ctx.setProperty(DirectoryPrintConnectionEndpoint.CTX_Directory, "d:/tmp/test.printClient/in");
			ctx.setProperty(DirectoryPrintConnectionEndpoint.CTX_FileExtension, "json");
			ctx.setProperty(Context.CTX_PrintConnectionEndpoint, DirectoryPrintConnectionEndpoint.class);

			ctx.setProperty(PrintingEngine.CTX_RedirectToFolder, "d:/tmp/test.printClient/out");

			System.out.println("Context: " + ctx);
		}

		System.out.println("Starting at " + new java.util.Date());

		final PrintingClientDaemon daemon = new PrintingClientDaemon();
		daemon.run();

		System.out.println("Done at " + new java.util.Date());
	}

}
