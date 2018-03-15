package de.metas.printing.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.printing.base
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


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.print.attribute.standard.MediaSize;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Pair;
import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import de.metas.logging.LogManager;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintJobLinesAggregator;
import de.metas.printing.api.IPrintPackageCtx;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.exception.PrintingQueueAggregationException;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_C_PrintPackageData;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;

public class PrintJobLinesAggregator implements IPrintJobLinesAggregator
{
	public static final String DEFAULT_BinaryFormat = "application/pdf";

	// Services
	private static final transient Logger logger = LogManager.getLogger(PrintJobLinesAggregator.class);
	private final transient IPrintingDAO dao = Services.get(IPrintingDAO.class);
	private final transient IPrintJobBL printJobBL = Services.get(IPrintJobBL.class);

	private final IPrintPackageCtx printCtx;
	private final I_C_Print_Job_Instructions printJobInstructions;
	private final I_C_Print_Job printJob;
	private final Properties ctx;
	private final String trxName;
	private I_C_Print_Package printPackageToUse;

	private final List<Map<ArrayKey, I_C_Print_PackageInfo>> printPackageInfos = new ArrayList<>();

	// NOTE: we shall use IdentityHashMap instead of HashMap because key content (I_C_Print_PackageInfo) is changing
	private final Map<I_C_Print_PackageInfo, List<ArchivePart>> mapArchiveParts = new IdentityHashMap<>();

	/**
	 * True if aggregator was already executed
	 */
	private boolean processed = false;

	/**
	 * NOTE: trxName from printJob will be used
	 *
	 * @param printCtx
	 * @param printJobInstructions
	 */
	public PrintJobLinesAggregator(final IPrintPackageCtx printCtx,
			final I_C_Print_Job_Instructions printJobInstructions)
	{
		super();
		Check.assume(printCtx != null, "printCtx not null");
		this.printCtx = printCtx;

		Check.assume(printJobInstructions != null, "printJobInstructions not null");
		this.printJobInstructions = printJobInstructions;
		printJob = printJobInstructions.getC_Print_Job();

		ctx = InterfaceWrapperHelper.getCtx(printJobInstructions);
		trxName = InterfaceWrapperHelper.getTrxName(printJobInstructions);
	}

	public String getTrxName()
	{
		return trxName;
	}

	@Override
	public void setPrintPackageToUse(final I_C_Print_Package printPackage)
	{
		if (printPackage != null)
		{
			Check.assume(printPackage.getC_Print_Package_ID() > 0, "printPackage shall be saved");

			final String printPackageTrxName = InterfaceWrapperHelper.getTrxName(printPackage);
			Check.assume(
					Objects.equals(printPackageTrxName, trxName),
					"printPackage shall have the same trxName {} as the local instance {}",
					printPackageTrxName, trxName);
		}
		printPackageToUse = printPackage;
	};

	@Override
	public void add(final I_C_Print_Job_Line jobLine, final Mutable<ArrayKey> preceedingKey)
	{
		Check.assume(!processed, "Aggregator not already processed");
		Check.assume(jobLine.getC_Print_Job_ID() == printJob.getC_Print_Job_ID(), "jobLine shall be from save print job");

		try
		{
			add0(jobLine, preceedingKey);
		}
		catch (final Exception e)
		{
			throw new PrintingQueueAggregationException(jobLine.getC_Printing_Queue_ID(), e);
		}
	}

	private void add0(final I_C_Print_Job_Line jobLine,
			final Mutable<ArrayKey> preceedingKey)
	{
		//
		// Retrieve Print Job Line's Details
		final List<I_C_Print_Job_Detail> printJobDetails = printJobBL.getCreatePrintJobDetails(jobLine);
		if (printJobDetails.isEmpty())
		{
			logger.info("Print Job Line has no details: {}. Skipping it", jobLine);
			return;
		}

		//
		// Create ArchiveData from Print Job Line
		final I_AD_Archive archive = jobLine.getC_Printing_Queue().getAD_Archive();
		final ArchiveData archiveData = new ArchiveData(jobLine, archive);
		if (!archiveData.hasData())
		{
			logger.info("Print Job Line's Archive has no data: {}. Skipping it", archiveData);
			return;
		}

		//
		// Iterate Print Job Line's details and create Archive Parts; further down we will decide which archive part shall be used with this page range
		final List<ArchivePart> archiveParts = new ArrayList<>(printJobDetails.size());
		for (final I_C_Print_Job_Detail detail : printJobDetails)
		{
			final I_AD_PrinterRouting routing = loadOutOfTrx(detail.getAD_PrinterRouting_ID(), I_AD_PrinterRouting.class);
			final ArchivePart archivePart = new ArchivePart(archiveData, routing);
			archiveParts.add(archivePart);
		}

		// the number of pages to "divide" among our archive parts
		final int numberOfPagesAvailable = archiveData.getNumberOfPages();
		// task 08958: maintain a bitmap of pages that we already assigned to archive parts, to avoid printing them more than once
		final boolean[] pagesCovered = new boolean[numberOfPagesAvailable];

		//
		// set PageFrom/PageTo for "LastPages" routings
		int lastPagesMax = 0;
		for (final Iterator<ArchivePart> it = archiveParts.iterator(); it.hasNext();)
		{
			final ArchivePart archivePart = it.next();
			if (!archivePart.isLastPages())
			{
				continue;
			}

			int lastPages = archivePart.getLastPages();

			// Make sure lastPages is not negative
			if (lastPages <= 0)
			{
				// negative last pages, wtf?
				final AdempiereException ex = new AdempiereException("Got negative last pages."
						+ "\n Print package: " + printPackageToUse
						+ "\n ArchivePart: " + archivePart
						+ "\n LastPages: " + lastPages);
				if (Services.get(IDeveloperModeBL.class).isEnabled())
				{
					throw ex;
				}
				else
				{
					logger.warn("Ignoring: archive part because LastPages <= 0", ex);
				}
				it.remove();
				continue;
			}

			// Make sure lastPages is not bigger than document's number of pages
			if (lastPages > numberOfPagesAvailable)
			{
				lastPages = numberOfPagesAvailable;
			}

			if (lastPages > lastPagesMax)
			{
				lastPagesMax = lastPages;
			}

			// Calculate PageFrom/PageTo
			final int pageTo = numberOfPagesAvailable;
			final int pageFrom = numberOfPagesAvailable - lastPages + 1;
			if (pageFrom > pageTo)
			{
				// invalid page range => skip this archive part
				it.remove();
				continue;
			}

			// 08958: compute the final range by by skipping forward (pageFrom) and backwards (pageTo) to avoid page range overlap
			final int pageFromFinal = skipForward(pagesCovered, pageFrom, pageTo);
			final int pageToFinal = skipBackward(pagesCovered, pageTo, pageFrom);
			if (pageFromFinal > pageToFinal)
			{
				it.remove();
				continue;
			}
			archivePart.setPageFrom(pageFromFinal);
			archivePart.setPageTo(pageToFinal);
			markCovered(pagesCovered, pageFromFinal, pageToFinal);
		}

		//
		// Adjust PageFrom/PageTo for "PageRange" routings
		for (final Iterator<ArchivePart> it = archiveParts.iterator(); it.hasNext();)
		{
			final ArchivePart archivePart = it.next();
			if (!archivePart.isPageRange())
			{
				continue;
			}

			int pageFrom = archivePart.getPageFrom();
			if (pageFrom <= 0)
			{
				// First page is 1 - See com.lowagie.text.pdf.PdfWriter.getImportedPage
				pageFrom = 1;
			}
			int pageTo = archivePart.getPageTo();
			if (pageTo <= 0)
			{
				pageTo = numberOfPagesAvailable;
			}
			if (pageTo > numberOfPagesAvailable)
			{
				logger.debug("Page to ({}) is greather then number of pages available. Considering number of pages: {}", new Object[] { pageTo, numberOfPagesAvailable });
				pageTo = numberOfPagesAvailable;
			}
			if (pageFrom > pageTo)
			{
				// invalid page range => skip this archive part
				it.remove();
				continue;
			}
			final int pageFromFinal = skipForward(pagesCovered, pageFrom, pageTo);
			final int pageToFinal = skipBackward(pagesCovered, pageTo, pageFrom);
			if (pageFromFinal > pageToFinal)
			{
				it.remove();
				continue;
			}

			archivePart.setPageFrom(pageFromFinal);
			archivePart.setPageTo(pageToFinal);
			markCovered(pagesCovered, pageFromFinal, pageToFinal);
		}

		//
		// Add ArchiveParts to internal map
		for (final ArchivePart archivePart : archiveParts)
		{
			final Pair<ArrayKey, I_C_Print_PackageInfo> keyAndPackageInfo = getCreatePrintPackageInfo(
					archivePart,
					preceedingKey.getValue()
					);
			addArchivePartToMap(keyAndPackageInfo.getSecond(), archivePart);
			preceedingKey.setValue(keyAndPackageInfo.getFirst());
		}
	}

	private int skipBackward(final boolean[] pagesCovered,
			final int pageTo,
			final int limit)
	{
		final int limitToUse = limit;

		int pageToFinal = pageTo;
		for (int i = pageTo; i >= limitToUse; i--)
		{
			if (!pagesCovered[i - 1])
			{
				break;
			}
			pageToFinal = i - 1;
		}
		return pageToFinal;
	}

	private int skipForward(final boolean[] pagesCovered,
			final int pageFrom,
			final int limit)
	{
		final int limitToUse = Math.min(limit, pagesCovered.length);

		int pageFromFinal = pageFrom;
		for (int i = pageFrom; i <= limitToUse; i++)
		{
			if (!pagesCovered[i - 1])
			{
				break;
			}
			pageFromFinal = i + 1;
		}
		return pageFromFinal;
	}

	private void markCovered(final boolean[] pagesCovered,
			final int pageFrom,
			final int pageTo)
	{
		for (int i = pageFrom; i <= pageTo; i++)
		{
			pagesCovered[i - 1] = true;
		}
	}

	@Override
	public I_C_Print_Package createPrintPackage()
	{
		final ByteArrayOutputStream pdfBuf = new ByteArrayOutputStream();
		final int pages = createPDFData(pdfBuf); // create the PDS data, and update the print package infos' pageFrom and pageto values
		if (pages <= 0)
		{
			throw new AdempiereException("No PDF pages found. No package created."); // TRL
		}

		final byte[] data = pdfBuf.toByteArray();
		if (data == null || data.length == 0)
		{
			throw new AdempiereException("No PDF data found. No package created."); // TRL
		}

		final I_C_Print_Package printPackage = printPackageToUse == null ? InterfaceWrapperHelper.create(ctx, I_C_Print_Package.class, trxName) : printPackageToUse;
		printPackage.setC_Print_Job_Instructions(printJobInstructions);
		printPackage.setAD_Org_ID(printJob.getAD_Org_ID());
		printPackage.setTransactionID(printCtx.getTransactionId());
		printPackage.setPageCount(pages);
		printPackage.setBinaryFormat(DEFAULT_BinaryFormat);

		if (printPackageToUse == null)
		{
			// NOTE: we are saving the printPackage ONLY if is not managed outside
			InterfaceWrapperHelper.save(printPackage);
		}

		for (final Map<ArrayKey, I_C_Print_PackageInfo> currentMap : printPackageInfos)
		{
			for (final I_C_Print_PackageInfo printPackageInfo : currentMap.values())
			{
				printPackageInfo.setC_Print_Package(printPackage);
				printPackageInfo.setAD_Org_ID(printPackage.getAD_Org_ID());
				InterfaceWrapperHelper.save(printPackageInfo);

				updatePrintJobLines(printPackageInfo, printPackage);
			}
		}
		final I_C_PrintPackageData printPackageData = InterfaceWrapperHelper.create(ctx, I_C_PrintPackageData.class, trxName);
		printPackageData.setC_Print_Package(printPackage);
		printPackageData.setAD_Org_ID(printPackage.getAD_Org_ID());
		printPackageData.setPrintData(data);
		InterfaceWrapperHelper.save(printPackageData);

		// Mark processed print jobs
		updatePrintJob();

		// Mark this aggregator as processed
		processed = true;

		return printPackage;
	}

	private void updatePrintJobLines(final I_C_Print_PackageInfo printPackageInfo, final I_C_Print_Package printPackage)
	{
		// for (I_C_Print_PackageInfo i : mapArchiveParts.keySet()) System.out.println("-> "+i.hashCode());
		final List<ArchivePart> archiveParts = mapArchiveParts.get(printPackageInfo);
		if (archiveParts == null || archiveParts.isEmpty())
		{
			return;
		}

		for (final ArchivePart archivePart : archiveParts)
		{
			final I_C_Print_Job_Line printJobLine = archivePart.getPrintJobLine();
			printJobLine.setC_Print_Package_ID(printPackage.getC_Print_Package_ID());
			InterfaceWrapperHelper.save(printJobLine);
		}
	}

	private void updatePrintJob()
	{
		// NOTE: print job instructions' Status will be set to send de.metas.printing.api.impl.PrintPackageBL.createPrintPackage(I_C_Print_Package, I_C_Print_Job_Instructions, IPrintPackageCtx)
		// when the package will be actually sent to client
		// printJobInstructions.setStatus(X_C_Print_Job_Instructions.STATUS_Send);
		// InterfaceWrapperHelper.save(printJobInstructions);

		printJob.setProcessed(true);
		InterfaceWrapperHelper.save(printJob);
	}

	/**
	 *
	 * @param routing
	 * @param preceedingGroupKey used to prevent multiple job lines from from being split, i.e 50-times the first part of each job line, then 50-time the 2nd part.
	 * @return
	 */
	private Pair<ArrayKey, I_C_Print_PackageInfo> getCreatePrintPackageInfo(final ArchivePart archivePart,
			final ArrayKey preceedingGroupKey)
	{
		final I_AD_PrinterRouting routing = archivePart.getAD_PrinterRouting();

		final I_AD_Printer_Matching printerMatching = dao.retrievePrinterMatching(printCtx.getHostKey(), routing);

		final I_AD_PrinterHW hwPrinter = printerMatching.getAD_PrinterHW();

		// task 04005, R810: don't make a fuss if there is no printer *tray* matching
		final I_AD_PrinterTray_Matching trayMatching = dao.retrievePrinterTrayMatching(
				printerMatching,
				routing,
				false); // throwExIfMissing=false

		// the following 3 variables will be set from the printer tray matching, if there is any
		final int hwMediaTrayID;
		final int calX;
		final int calY;

		if (trayMatching != null)
		{
			final I_AD_PrinterHW_MediaTray hwTray = trayMatching.getAD_PrinterHW_MediaTray();

			final I_AD_PrinterHW_MediaSize hwMediaSize = dao.retrieveMediaSize(hwPrinter, MediaSize.ISO.A4, true);
			final I_AD_PrinterHW_Calibration printerCalibration = dao.retrieveCalibration(hwMediaSize, hwTray);

			calX = printerCalibration == null ? 0 : printerCalibration.getCalX();
			calY = printerCalibration == null ? 0 : printerCalibration.getCalY();
			hwMediaTrayID = hwTray.getAD_PrinterHW_MediaTray_ID();
		}
		else
		{
			calX = 0;
			calY = 0;
			hwMediaTrayID = 0;
		}

		final Pair<ArrayKey, I_C_Print_PackageInfo> printPackageInfo = getCreatePrintPackageInfo(
				hwPrinter.getAD_PrinterHW_ID(),
				hwMediaTrayID,
				calX,
				calY,
				preceedingGroupKey);
		return printPackageInfo;
	}

	/**
	 *
	 * @param hwPrinterId
	 * @param hwTrayId
	 * @param calX
	 * @param calY
	 * @param preceedingGroupKey used to check if the current parameters (the other 4 params of this method) match the key of the preceeding invocation. If yes, then the last element of
	 *            {@link #printPackageInfos} is used to check if and existing print package info can be reused. Otherwise, a new map is created.
	 *
	 * @return
	 */
	private Pair<ArrayKey, I_C_Print_PackageInfo> getCreatePrintPackageInfo(final int hwPrinterId,
			final int hwTrayId,
			final int calX,
			final int calY,
			final ArrayKey preceedingGroupKey)
	{
		final ArrayKey groupKey = new ArrayKey(hwPrinterId, hwTrayId, calX, calY);

		if (!Check.equals(groupKey, preceedingGroupKey) || printPackageInfos.isEmpty())
		{
			// create a new map and add it to the end of the list.
			printPackageInfos.add(new LinkedHashMap<ArrayKey, I_C_Print_PackageInfo>());
		}

		// get the last item and check if there is an existing print package info to reuse.
		final Map<ArrayKey, I_C_Print_PackageInfo> currentPrintPackageInfos = printPackageInfos.get(printPackageInfos.size() - 1);

		I_C_Print_PackageInfo printPackageInfo = currentPrintPackageInfos.get(groupKey);

		if (printPackageInfo == null)
		{
			// there is no existing printing package info to use; create a new one.
			printPackageInfo = InterfaceWrapperHelper.create(ctx, I_C_Print_PackageInfo.class, trxName);
			printPackageInfo.setAD_PrinterHW_ID(hwPrinterId);
			printPackageInfo.setAD_PrinterHW_MediaTray_ID(hwTrayId);
			printPackageInfo.setCalX(calX);
			printPackageInfo.setCalY(calY);
			// will be set when the actual package will be created:
			// printPackageInfo.setC_Print_Package(printPackage);
			// printPackageInfo.setAD_Org_ID(printPackage.getAD_Org_ID());

			currentPrintPackageInfos.put(groupKey, printPackageInfo);
		}

		final Pair<ArrayKey, I_C_Print_PackageInfo> result = new Pair<>(groupKey, printPackageInfo);
		return result;
	}

	/**
	 * Add {@link ArchivePart} to internal map.
	 *
	 * @param printPackageInfo
	 * @param archiveData
	 * @param routing
	 */
	private void addArchivePartToMap(final I_C_Print_PackageInfo printPackageInfo, final ArchivePart archivePart)
	{
		List<ArchivePart> archiveParts = mapArchiveParts.get(printPackageInfo);
		if (archiveParts == null)
		{
			archiveParts = new ArrayList<>();
			mapArchiveParts.put(printPackageInfo, archiveParts);
		}

		logger.debug("Adding archive to map: {}", archivePart);
		archiveParts.add(archivePart);
	}

	/**
	 * Iterates all values of the {@link #mapPrintPackageInfos} map and appends their PDF data to the given <code>out</code> stream, while updating the individual {@link I_C_Print_PackageInfo}s'
	 * <code>pageFrom</code> and <code>pageTo</code> values.
	 *
	 * @param out
	 * @return number of pages created in out stream
	 */
	private int createPDFData(final OutputStream out)
	{
		final Document document = new Document();

		final PdfCopy copy;
		try
		{
			copy = new PdfCopy(document, out);
		}
		catch (final DocumentException e)
		{
			throw new AdempiereException(e);
		}

		document.open();

		int documentCurrentPage = 0;
		for (final Map<ArrayKey, I_C_Print_PackageInfo> curentMap : printPackageInfos)
		{
			for (final I_C_Print_PackageInfo printPackageInfo : curentMap.values())
			{
				logger.debug("Adding {}", printPackageInfo);

				final List<ArchivePart> archiveParts = mapArchiveParts.get(printPackageInfo);
				if (archiveParts == null || archiveParts.isEmpty())
				{
					logger.info("Skipping {} because there are not archive parts", printPackageInfo);
					continue;
				}

				int pagesAdded = 0;
				for (final ArchivePart archivePart : archiveParts)
				{
					pagesAdded += addArchivePartToPDF(copy, archivePart);
				}
				if (pagesAdded == 0)
				{
					logger.info("Skipping {} because no pages were added", printPackageInfo);
				}

				final int pageFrom = documentCurrentPage + 1;
				final int pageTo = pageFrom + pagesAdded - 1;
				logger.debug("Added {}: PageFrom={}, PageTo={}", new Object[] { printPackageInfo, pageFrom, pageTo });

				printPackageInfo.setPageFrom(pageFrom);
				printPackageInfo.setPageTo(pageTo);

				documentCurrentPage = pageTo;
			}
		}
		if (documentCurrentPage == 0)
		{
			logger.info("No pages were added");
			return 0;
		}

		document.close();

		return documentCurrentPage;
	}

	private int addArchivePartToPDF(final PdfCopy copy, final ArchivePart archivePart)
	{
		try
		{
			return addArchivePartToPDF0(copy, archivePart);
		}
		catch (final Exception e)
		{
			throw new PrintingQueueAggregationException(archivePart.getPrintJobLine().getC_Printing_Queue_ID(), e);
		}
	}

	private int addArchivePartToPDF0(final PdfCopy copy, final ArchivePart archivePart) throws IOException
	{
		logger.debug("Adding {}", archivePart);

		final ArchiveData archiveData = archivePart.getArchiveData();
		if (!archiveData.hasData())
		{
			logger.info("Archive {} does not contain any data. Skip", archivePart);
			return 0;
		}

		final PdfReader reader = archiveData.createPdfReader();

		final int archivePageNums = reader.getNumberOfPages();

		int pageFrom = archivePart.getPageFrom();
		if (pageFrom <= 0)
		{
			// First page is 1 - See com.lowagie.text.pdf.PdfWriter.getImportedPage
			pageFrom = 1;
		}
		int pageTo = archivePart.getPageTo();
		if (pageTo > archivePageNums)
		{
			// shall not happen at this point
			logger.debug("Page to ({}) is greather then number of pages. Considering number of pages: {}", new Object[] { pageTo, archivePageNums });
			pageTo = archivePageNums;
		}
		if (pageFrom > pageTo)
		{
			// shall not happen at this point
			logger.warn("Page from ({}) is greather then Page to ({}). Skipping: {}", new Object[] { pageFrom, pageTo, archivePart });
			return 0;
		}

		logger.debug("PageFrom={}, PageTo={}, NumberOfPages={}", new Object[] { pageFrom, pageTo, archivePageNums });

		int pagesAdded = 0;
		for (int page = pageFrom; page <= pageTo; page++)
		{
			try
			{
				copy.addPage(copy.getImportedPage(reader, page));
			}
			catch (final BadPdfFormatException e)
			{
				throw new AdempiereException("@Invalid@ " + archivePart + " (Page: " + page + ")", e);
			}
			pagesAdded++;
		}

		copy.freeReader(reader);
		reader.close();

		logger.debug("Added {} pages", pagesAdded);
		return pagesAdded;
	}

	private static class ArchiveData
	{
		// Services
		private final transient IArchiveBL archiveBL = Services.get(IArchiveBL.class);

		// Parameters
		private final I_C_Print_Job_Line printJobLine;
		private final I_AD_Archive archive;

		// Arhive's Data
		private boolean dataLoaded;
		private transient byte[] data;
		private Integer numberOfPages = null;

		public ArchiveData(final I_C_Print_Job_Line printJobLine, final I_AD_Archive archive)
		{
			super();
			this.printJobLine = printJobLine;
			this.archive = archive;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public I_C_Print_Job_Line getPrintJobLine()
		{
			return printJobLine;
		}

		private final byte[] getData()
		{
			if (dataLoaded)
			{
				return data;
			}

			data = archiveBL.getBinaryData(archive);
			dataLoaded = true;
			if (data == null || data.length == 0)
			{
				logger.info("Archive {} does not contain any data. Skip", archive);
				data = null;
			}

			return data;
		}

		public boolean hasData()
		{
			return getData() != null;
		}

		public PdfReader createPdfReader() throws IOException
		{
			final PdfReader reader = new PdfReader(getData());
			return reader;
		}

		public int getNumberOfPages()
		{
			if (numberOfPages != null)
			{
				return numberOfPages;
			}

			if (!hasData())
			{
				return 0;
			}

			PdfReader reader = null;
			try
			{
				reader = createPdfReader();
				numberOfPages = reader.getNumberOfPages();
				return numberOfPages;
			}
			catch (final IOException e)
			{
				throw new AdempiereException("Cannot get number of pages for archive " + archive, e);
			}
			finally
			{
				if (reader != null)
				{
					try
					{
						reader.close();
					}
					catch (final Exception e)
					{
					}
					reader = null;
				}
			}
		}
	}

	/**
	 * Helper class that holds a reference to an {@link I_AD_Archive}, a print job line and a page range.
	 *
	 *
	 */
	private static class ArchivePart
	{
		private final ArchiveData archiveData;
		private final I_AD_PrinterRouting routing;
		private Integer pageFrom;
		private Integer pageTo;

		public ArchivePart(final ArchiveData archiveData, final I_AD_PrinterRouting routing)
		{
			super();

			Check.assumeNotNull(archiveData, "archiveData not null");
			this.archiveData = archiveData;

			Check.assumeNotNull(routing, "routing not null");
			final int pageFrom = routing.getPageFrom();
			final int pageTo = routing.getPageTo();
			Check.assume(pageFrom <= pageTo, "pageFrom={} is less or equal to pageTo={} for {}", pageFrom, pageTo, archiveData);

			this.routing = routing;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public ArchiveData getArchiveData()
		{
			return archiveData;
		}

		public I_C_Print_Job_Line getPrintJobLine()
		{
			return archiveData.getPrintJobLine();
		}

		public I_AD_PrinterRouting getAD_PrinterRouting()
		{
			return routing;
		}

		public void setPageFrom(final int pageFrom)
		{
			this.pageFrom = pageFrom;
		}

		public int getPageFrom()
		{
			if (pageFrom != null)
			{
				return pageFrom;
			}

			return routing.getPageFrom();
		}

		private int getLastPages()
		{
			return routing.getLastPages();
		}

		public void setPageTo(final int pageTo)
		{
			this.pageTo = pageTo;
		}

		public int getPageTo()
		{
			if (pageTo != null)
			{
				return pageTo;
			}

			return routing.getPageTo();
		}

		private boolean isPageRange()
		{
			return I_AD_PrinterRouting.ROUTINGTYPE_PageRange.equals(routing.getRoutingType());
		}

		private boolean isLastPages()
		{
			return I_AD_PrinterRouting.ROUTINGTYPE_LastPages.equals(routing.getRoutingType());
		}
	}
}
