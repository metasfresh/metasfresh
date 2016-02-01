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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.metas.printing.client.Context;
import de.metas.printing.client.IPrintConnectionEndpoint;
import de.metas.printing.client.encoder.IBeanEnconder;
import de.metas.printing.client.util.Util;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrinterHWList;

public class DirectoryPrintConnectionEndpoint implements IPrintConnectionEndpoint
{
	private static final String CTX_ROOT = "de.metas.printing.client.endpoint.DirectoryPrintConnectionEndpoint";
	public static final String CTX_Directory = CTX_ROOT + ".Directory";

	public static final String CTX_FileExtension = CTX_ROOT + ".FileExtension";
	public static final String DEFAULT_FileExtension = "json";

	private final transient Logger log = Logger.getLogger(getClass().getName());

	private final BufferedPrintConnectionEndpoint buffer = new BufferedPrintConnectionEndpoint();
	private final IBeanEnconder beanEncoder;
	private final File directory;
	private final String fileExtension;

	public DirectoryPrintConnectionEndpoint()
	{
		super();

		final Context ctx = Context.getContext();
		beanEncoder = ctx.getInstance(Context.CTX_BeanEncoder, IBeanEnconder.class);

		String dirName = ctx.getProperty(CTX_Directory);
		if (dirName == null)
		{
			final String tempDirName = ctx.getProperty(Context.CTX_TempDir);
			dirName = tempDirName + "/in";
		}
		directory = new File(dirName);
		directory.mkdirs();
		if (!directory.isDirectory() || !directory.canRead() || !directory.canWrite())
		{
			throw new IllegalStateException("File " + directory + " is not a read/write directory");
		}

		fileExtension = ctx.getProperty(CTX_FileExtension, DEFAULT_FileExtension);
		if (fileExtension == null)
		{
			throw new IllegalStateException("No value found for property " + CTX_FileExtension);
		}
	}

	@Override
	public void addPrinterHW(final PrinterHWList printerHWList)
	{
		log.info("Found printer HWs: " + printerHWList);
	}

	@Override
	public PrintPackage getNextPrintPackage()
	{
		pollNewFiles();
		return buffer.getNextPrintPackage();
	}

	@Override
	public InputStream getPrintPackageData(final PrintPackage printPackage)
	{
		return buffer.getPrintPackageData(printPackage);
	}

	@Override
	public void sendPrintPackageResponse(final PrintPackage printPackage, final PrintJobInstructionsConfirm response)
	{
		log.info("Got : " + response + " for " + printPackage);
	}

	private void pollNewFiles()
	{
		log.fine("Polling directory: " + directory);

		final File[] files = directory.listFiles();
		if (files == null || files.length == 0)
		{
			return;
		}

		for (final File file : files)
		{
			if (!file.isFile())
			{
				continue;
			}

			final String ext = Util.getFileExtension(file.getName());
			if (!fileExtension.equals(ext))
			{
				continue;
			}

			enqueueFile(file);
		}
	}

	private boolean enqueueFile(final File file)
	{
		log.info("Enqueuing " + file);

		PrintPackage printPackage = null;
		InputStream printPackageStream = null;

		final File dataFile = new File(Util.changeFileExtension(file.getAbsolutePath(), "pdf"));
		InputStream printDataStream = null;

		try
		{
			printPackageStream = new FileInputStream(file);
			printPackage = beanEncoder.decodeStream(printPackageStream, PrintPackage.class);
			printPackageStream.close();
			printPackageStream = null;

			printDataStream = new FileInputStream(dataFile);
			final byte[] data = Util.toByteArray(printDataStream);
			printDataStream.close();
			printDataStream = new ByteArrayInputStream(data);

			file.delete();
			dataFile.delete();
		}
		catch (final Exception e)
		{
			log.log(Level.WARNING, e.getLocalizedMessage(), e);
			return false;
		}
		finally
		{
			Util.close(printPackageStream);
			Util.close(printDataStream);
		}

		log.info("Adding package to buffer: " + printPackage);
		buffer.addPrintPackage(printPackage, printDataStream);
		return true;
	}

	@Override
	public String toString()
	{
		return "DirectoryPrintConnectionEndpoint [directory=" + directory + ", fileExtension=" + fileExtension + ", beanEncoder=" + beanEncoder + ", buffer=" + buffer + "]";
	}

	@Override
	public LoginResponse login(final LoginRequest loginRequest)
	{
		throw new UnsupportedOperationException();
	}
}
