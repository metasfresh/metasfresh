package de.metas.printing.client.endpoint;

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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

import de.metas.printing.client.IPrintConnectionEndpoint;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrinterHWList;

public class BufferedPrintConnectionEndpoint implements IPrintConnectionEndpoint
{
	private final transient Logger log = Logger.getLogger(getClass().getName());

	private final Queue<PrintPackageAndData> printPackageQueue = new LinkedList<PrintPackageAndData>();

	private final Map<String, PrintPackageAndData> trx2PrintPackageMap = new HashMap<String, PrintPackageAndData>();

	private final Object sync = new Object();

	public BufferedPrintConnectionEndpoint()
	{
		super();
	}

	@Override
	public void addPrinterHW(final PrinterHWList printerHWList)
	{
		log.info("Found printer HWs: " + printerHWList);
	}

	public void addPrintPackage(final PrintPackage printPackage, final InputStream printDataStream)
	{
		synchronized (sync)
		{
			final String trx = printPackage.getTransactionId();
			if (trx2PrintPackageMap.containsKey(trx))
			{
				throw new IllegalArgumentException("Transaction already exists in queue: " + printPackage);
			}

			final PrintPackageAndData item = new PrintPackageAndData();
			item.printPackage = printPackage;
			item.printDataStream = printDataStream;

			printPackageQueue.add(item);
			trx2PrintPackageMap.put(trx, item);
		}
	}

	@Override
	public PrintPackage getNextPrintPackage()
	{
		synchronized (sync)
		{
			final PrintPackageAndData item = printPackageQueue.poll();
			if (item == null)
			{
				return null;
			}

			System.out.println("getNextPrintPackage: " + item.printPackage);
			return item.printPackage;
		}
	}

	@Override
	public InputStream getPrintPackageData(final PrintPackage printPackage)
	{
		synchronized (sync)
		{
			final String trx = printPackage.getTransactionId();
			final PrintPackageAndData item = trx2PrintPackageMap.remove(trx);
			if (item == null)
			{
				throw new IllegalStateException("No data found for " + printPackage);
			}

			System.out.println("getPrintPackageData: trx=" + trx + " => stream: " + item.printDataStream);

			return item.printDataStream;
		}
	}

	@Override
	public void sendPrintPackageResponse(final PrintPackage printPackage, final PrintJobInstructionsConfirm response)
	{
		log.info("Got : " + response + " for " + printPackage);
	}

	private static class PrintPackageAndData
	{
		public PrintPackage printPackage;
		public InputStream printDataStream;
	}

	@Override
	public LoginResponse login(final LoginRequest loginRequest)
	{
		throw new UnsupportedOperationException();
	}
}
