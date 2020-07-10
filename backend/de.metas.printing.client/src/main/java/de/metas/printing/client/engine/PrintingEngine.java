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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.MediaTray;
import javax.print.attribute.standard.PageRanges;

import de.metas.printing.client.Context;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.api.PrintJobInstructionsStatusEnum;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrintPackageInfo;
import de.metas.printing.esb.api.PrinterHW;
import de.metas.printing.esb.api.PrinterHW.PrinterHWMediaSize;
import de.metas.printing.esb.api.PrinterHW.PrinterHWMediaTray;
import de.metas.printing.esb.api.PrinterHWList;

public class PrintingEngine
{
	public static final String CTX_ROOT = "de.metas.printing.client.engine.PrintingEngine";
	public static final String CTX_RedirectToFolder = CTX_ROOT + ".RedirectToFolder";

	private static final PrintingEngine instance = new PrintingEngine();

	public static PrintingEngine get()
	{
		return instance;
	}

	private final transient Logger logger = Logger.getLogger(getClass().getName());

	private final String redirectDirName;

	private PrintingEngine()
	{
		super();
		// redirectDirName = Context.getContext().getProperty(CTX_RedirectToFolder);
		redirectDirName = "";
	}

	/**
	 * Attempts to print the given package and input stream and closes the stream.
	 */
	public PrintJobInstructionsConfirm print(final PrintPackage printPackage, final InputStream in)
	{
		logger.log(Level.INFO, "Printing {}", printPackage);

		final String printJobInstructionsId = printPackage.getPrintJobInstructionsID();

		if (printJobInstructionsId == null || printJobInstructionsId.isEmpty())
		{
			throw new IllegalArgumentException("PrintJobInstructionsID cannot be null or empty!");
		}

		final PrintJobInstructionsConfirm printPackageResponse = new PrintJobInstructionsConfirm();
		printPackageResponse.setPrintJobInstructionsID(printJobInstructionsId);

		try (final InputStream inInternal = in)
		{
			final PrintablePDF printable = createPrintable(printPackage, inInternal);

			// task 08958: repeating the print for our number of copies
			final int iterations = printPackage.getCopies();
			for (int i = 0; i < iterations; i++)
			{
				// printing in reverse order, so the customer doesn't have to shuffle
				final List<PrintPackageInfo> printPackageInfos = printPackage.getPrintPackageInfos();
				for (int j = printPackageInfos.size() - 1; j >= 0; j--)
				{
					final PrintPackageInfo printPackageInfo = printPackageInfos.get(j);
					printable.setCalX(printPackageInfo.getCalX());
					printable.setCalY(printPackageInfo.getCalY());
					print(printPackage, printPackageInfo, printable);
				}
			}

			printPackageResponse.setStatus(PrintJobInstructionsStatusEnum.Gedruckt);
			printPackageResponse.setErrorMsg(null);

			return printPackageResponse;
		}
		catch (final Exception e)
		{
			logger.log(Level.SEVERE, "Exception while trying to print PrintPackage " + printPackage, e);
			printPackageResponse.setStatus(PrintJobInstructionsStatusEnum.Druckfehler);
			printPackageResponse.setErrorMsg(e.getLocalizedMessage());

			return printPackageResponse;
		}
	}

	private PrintablePDF createPrintable(
			final PrintPackage printPackage,
			final InputStream in)
	{
		final String format = printPackage.getFormat();
		if (PrintPackage.FORMAT_PDF.equalsIgnoreCase(format))
		{
			return new PrintablePDF(in);
		}
		else
		{
			throw new RuntimeException("Format not supported: " + format);
		}
	}

	private void print(
			final PrintPackage printPackage,
			final PrintPackageInfo printPackageInfo,
			final PrintablePDF printable) throws PrinterException
	{
		logger.log(Level.FINE, "Printing {}", printPackageInfo);

		final PrintPackageRequest printRequest = new PrintPackageRequest(printPackage, printPackageInfo);
		printRequest.setPrintJobName("adempiere-job-" + printPackageInfo.toString());
		printRequest.setPrintable(printable);
		printRequest.setNumPages(printable.getNumPages());
		//
		// Attribute: Page Range
		printRequest.getAttributes().add(new PageRanges(printPackageInfo.getPageFrom(), printPackageInfo.getPageTo()));

		// task 08958: !not! discarding our local printer settings, but *also* using the preset from the print package package
		final Copies copies = (Copies)printRequest.getAttributes().get(Copies.class);
		if (copies != null && copies.getValue() != 1)
		{
			logger.log(Level.INFO, "Local printer setting: {} = {} ", new Object[] { copies.getName(), copies.getValue() });
			// printRequest.getAttributes().add(new Copies(1)); don't discard the local printer setting because this is just what the user might want.
		}

		//
		// Attribute: Redirect to file
		if (isRedirectToFile())
		{
			final Destination destination = new Destination(mkTmpPrintFile(printRequest).toURI());
			printRequest.getAttributes().add(destination);
		}

		// task 09832: retry on error
		final Context context = Context.getContext();
		final int retryCount = context.getPropertyAsInt(Context.CTX_Engine_RetryCount, Context.DEFAULT_RetryCount);
		final int retryIntervalMS = context.getPropertyAsInt(Context.CTX_Engine_RetryIntervalMS, Context.DEFAULT_RetryIntervalMS);

		for (int i = 0; i <= retryCount; i++)
		{
			try
			{
				// these might also throw an exception if the printer is not available temporarily. In this case we might also want to retry.
				resolvePrintService(printRequest);
				resolveMediaTray(printRequest);

				print(printRequest);

				break; // if we get here, we did the print and can stop retrying.
			}
			catch (final PrinterException e)
			{
				final int retriesLeft = retryCount - i;
				if (retriesLeft <= 0)
				{
					throw e; // we exhausted our retries
				}
				logger.log(Level.WARNING, "Caught PrinterException while trying to print. " + retriesLeft + " retries left. Waiting " + retryIntervalMS + " millisseconds for the next retry", e);
				sleep(retryIntervalMS);
			}
		}
	}

	private void sleep(final int retryIntervalMS)
	{
		try
		{
			Thread.sleep(retryIntervalMS);
		}
		catch (final InterruptedException e1)
		{
			// do nothing
		}
	}

	private void print(final PrintPackageRequest request) throws PrinterException
	{
		logger.log(Level.FINE, "Printing request {}", request);

		// Create Print Job
		final PrinterJob pjob = PrinterJob.getPrinterJob();
		pjob.setJobName(request.getPrintJobName());

		final PageFormat pf = pjob.defaultPage();
		final Paper paper = pjob.defaultPage().getPaper();
		final MediaSize size = MediaSize.getMediaSizeForName(MediaSizeName.ISO_A4);

		paper.setSize(size.getSize(Size2DSyntax.INCH)[0] * 72, size.getSize(Size2DSyntax.INCH)[1] * 72);
		paper.setImageableArea(0, 0, size.getSize(Size2DSyntax.INCH)[0] * 72, size.getSize(Size2DSyntax.INCH)[1] * 72);
		pf.setPaper(paper);

		final Book book = new Book();// java.awt.print.Book
		book.append(request.getPrintable(), pf, request.getNumPages());
		pjob.setPageable(book);

		pjob.setPrintService(request.getPrintService());
		pjob.print(request.getAttributes());

		// task 09618: allow us to configure the client to return an error even if everything went OK, so we can test
		final String alwaysReturnError = Context.getContext().getProperty(Context.CTX_Testing_AlwaysReturnError, Context.DEFAULT_AlwaysReturnError);
		if (Boolean.parseBoolean(alwaysReturnError))
		{
			logger.log(Level.INFO, "{} is true, so we report an error, despite the print was OK", Context.CTX_Testing_AlwaysReturnError);

			final String errorMsg = Context.getContext().getProperty(Context.CTX_Testing_ErrorMessage, Context.DEFAULT_ErrorMessage);
			throw new PrinterException(errorMsg);
		}
	}

	/**
	 * Finds the {@link PrintService} for the name from the given <code>request</code>'s {@link PrintPackageInfo} and sets it to the request.
	 *
	 * @param request
	 * @throws PrinterException
	 */
	private void resolvePrintService(final PrintPackageRequest request) throws PrinterException
	{
		final PrintPackageInfo printPackageInfo = request.getPrintPackageInfo();
		final String requiredServiceName = printPackageInfo.getPrintService();

		final PrintService[] printServices = getPrintServices();
		PrintService requiredService = null;
		for (final PrintService printService : printServices)
		{
			if (requiredServiceName.equals(printService.getName()))
			{
				requiredService = printService;
				break;
			}
		}

		if (requiredService == null)
		{
			throw new PrinterException("PrintService not found: " + requiredServiceName + ". Available print services are: " + Arrays.toString(printServices));
		}
		request.setPrintService(requiredService);
	}

	private String resolveMediaTray(final PrintPackageRequest request) throws PrinterException
	{
		final int requiredTrayNo = request.getPrintPackageInfo().getTrayNumber();
		// 04124 : Tray number 0 is used too. Use -1 for "No tray required".
		if (requiredTrayNo < 0)
		{
			// no tray required
			return null;
		}

		final PrintService printService = request.getPrintService();
		final Media[] medias = (Media[])printService.getSupportedAttributeValues(Media.class, null, null);
		if (medias == null)
		{
			throw new RuntimeException("No media tray '" + requiredTrayNo + "' found for " + printService + ". There are no available trays.");
		}

		for (final Media media : medias)
		{
			if (media instanceof MediaTray)
			{
				final MediaTray mediaTray = (MediaTray)media;
				if (mediaTray.getValue() == requiredTrayNo)
				{
					request.getAttributes().add(mediaTray);
					return null;
				}
			}
		}
		final String warningMessage = "No media tray '" + requiredTrayNo + "' found for " + printService + ". Therefore we won't specify a tray for this print job. Available medias (incl. trays) are: " + Arrays.toString(medias);
		return warningMessage;
	}

	private PrintService[] _printServices = null;

	private PrintService[] getPrintServices()
	{
		if (_printServices == null)
		{
			_printServices = PrintServiceLookup.lookupPrintServices(
					null, // DocFlavor flavor,
					null // AttributeSet attributes
			);
			if (_printServices != null)
			{
				// 04005: if there is a default printer, then we add id to the list as first item
				final PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
				if (defaultPrintService != null)
				{
					final List<PrintService> printServiceList = new ArrayList<>(Arrays.asList(_printServices));
					if (printServiceList.contains(defaultPrintService))
					{
						printServiceList.remove(defaultPrintService);
						printServiceList.add(0, defaultPrintService);
						printServiceList.toArray(_printServices);
					}
				}
			}
		}

		return _printServices;
	}

	private File mkTmpPrintFile(final PrintPackageRequest printRequest)
	{
		final PrintPackage printPackage = printRequest.getPrintPackage();
		final PrintPackageInfo printPackageInfo = printRequest.getPrintPackageInfo();

		final File dir = new File(redirectDirName);

		final String filename = "out"
				+ "-" + printPackage.getTransactionId()
				+ "-" + Context.getContext().getProperty(Context.CTX_SessionId)
				+ "-" + printPackageInfo.getPrintService()
				+ "-" + printPackageInfo.getTray()
				+ "-" + printPackageInfo.getPageFrom()
				+ "-" + printPackageInfo.getPageTo()
				+ ".xps";

		final File file = new File(dir, filename);
		return file;
	}

	private boolean isRedirectToFile()
	{
		return redirectDirName != null && !redirectDirName.isEmpty();
	}

	public PrinterHWList createPrinterHW()
	{
		final List<PrinterHW> printerHWs = new ArrayList<>();
		for (final PrintService printService : getPrintServices())
		{
			final PrinterHW printerHW = new PrinterHW();
			printerHW.setName(printService.getName());

			final List<PrinterHWMediaSize> printerHWMediaSizes = new ArrayList<>();
			printerHW.setPrinterHWMediaSizes(printerHWMediaSizes);

			final List<PrinterHWMediaTray> printerHWMediaTrays = new ArrayList<>();
			printerHW.setPrinterHWMediaTrays(printerHWMediaTrays);

			// 04005: detect the default media tray
			final MediaTray defaultMediaTray = (MediaTray)printService.getDefaultAttributeValue(MediaTray.class);

			final Media[] medias = (Media[])printService.getSupportedAttributeValues(Media.class, null, null); // flavor=null, attributes=null
			for (final Media media : medias)
			{
				if (media instanceof MediaSizeName)
				{
					final String name = media.toString();

					// final MediaSizeName mediaSize = (MediaSizeName)media;
					final PrinterHWMediaSize printerHWMediaSize = new PrinterHWMediaSize();
					printerHWMediaSize.setName(name);
					// printerHWMediaSize.setIsDefault(isDefault);
					printerHWMediaSizes.add(printerHWMediaSize);
				}
				else if (media instanceof MediaTray)
				{
					final MediaTray mediaTray = (MediaTray)media;
					final String name = mediaTray.toString();
					final String trayNumber = Integer.toString(mediaTray.getValue());

					final PrinterHWMediaTray printerHWMediaTray = new PrinterHWMediaTray();
					printerHWMediaTray.setName(name);
					printerHWMediaTray.setTrayNumber(trayNumber);

					// 04005: default media tray shall be first in the list
					if (mediaTray.equals(defaultMediaTray))
					{
						printerHWMediaTrays.add(0, printerHWMediaTray);
					}
					else
					{
						printerHWMediaTrays.add(printerHWMediaTray);
					}
				}
			}

			printerHWs.add(printerHW);
		}

		final PrinterHWList list = new PrinterHWList();
		list.setHwPrinters(printerHWs);
		return list;
	}
}
