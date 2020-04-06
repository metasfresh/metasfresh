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

import javax.swing.JFrame;

import de.metas.printing.client.Context;
import de.metas.printing.client.PrintingClient;
import de.metas.printing.client.endpoint.RestHttpPrintConnectionEndpoint;
import de.metas.printing.client.ui.SwingUserInterface;

public class PrintingClientRunner
{
	static
	{
		final Context ctx = Context.getContext();
		ctx.setProperty(RestHttpPrintConnectionEndpoint.CTX_ServerUrl, "http://<INSERT_HOSTNAME>:8182/printing");
		ctx.setProperty(Context.CTX_SessionId, "1010751");
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception
	{
		final JFrame frame = new JFrame("Printing client");
		frame.setBounds(100, 100, 100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Context.getContext().setProperty(Context.CTX_UserInterface, new SwingUserInterface(frame));

		final PrintingClient client = new PrintingClient();
		client.start();
		System.out.println("Client started...");
	}
}
