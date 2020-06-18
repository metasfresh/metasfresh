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

import javax.annotation.Nullable;
import javax.print.attribute.standard.MediaSize;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
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
import de.metas.util.Check;
import de.metas.util.Pair;
import de.metas.util.Services;

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
	 */
	public PrintJobLinesAggregator(
			@NonNull final IPrintPackageCtx printCtx,
			@NonNull final I_C_Print_Job_Instructions printJobInstructions)
	{
		this.printCtx = printCtx;

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
	}

	;

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

	private void add0(
			@NonNull final I_C_Print_Job_Line jobLine,
			@NonNull final Mutable<ArrayKey> precedingKey)
	{
		final PrintingData printingData = new PrintingDataFactory().createPrintingDataForPrintJobLine(jobLine, printCtx.getHostKey());

		//
		// Add ArchiveParts to internal map
		final ArchiveData archiveData = new ArchiveData(jobLine, printingData.getData());
		for (final PrintingSegment printingSegment : printingData.getPrintingSegments())
		{
			final ArchivePart archivePart = new ArchivePart(archiveData, printingSegment.getPageFrom(), printingSegment.getPageTo());

			final IPair<ArrayKey, I_C_Print_PackageInfo> keyAndPackageInfo = getCreatePrintPackageInfo(
					printingSegment,
					precedingKey.getValue());
			addArchivePartToMap(keyAndPackageInfo.getRight(), archivePart);
			precedingKey.setValue(keyAndPackageInfo.getLeft());
		}
	}

	@Override
	public I_C_Print_Package createPrintPackage()
	{
		final ByteArrayOutputStream pdfBuf = new ByteArrayOutputStream();
		final int pages = createPDFData(pdfBuf);
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
	 * @param precedingGroupKey used to prevent multiple job lines from from being split, i.e 50-times the first part of each job line, then 50-time the 2nd part.
	 */
	private IPair<ArrayKey, I_C_Print_PackageInfo> getCreatePrintPackageInfo(final PrintingSegment archivePart,
			final ArrayKey precedingGroupKey)
	{

		final I_AD_PrinterHW hwPrinter = InterfaceWrapperHelper.loadOutOfTrx(archivePart.getPrinterId(), I_AD_PrinterHW.class);

		// the following 3 variables will be set from the printer tray, if there is any
		final int hwMediaTrayID;
		final int calX;
		final int calY;

		if (archivePart.getTrayId() != null)
		{
			final I_AD_PrinterHW_MediaTray hwTray = InterfaceWrapperHelper.loadOutOfTrx(archivePart.getTrayId(), I_AD_PrinterHW_MediaTray.class);

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

		final IPair<ArrayKey, I_C_Print_PackageInfo> printPackageInfo = getCreatePrintPackageInfo(
				hwPrinter.getAD_PrinterHW_ID(),
				hwMediaTrayID,
				calX,
				calY,
				precedingGroupKey);
		return printPackageInfo;
	}

	/**
	 * @param precedingGroupKey used to check if the current parameters (the other 4 params of this method) match the key of the preceeding invocation. If yes, then the last element of
	 *                          {@link #printPackageInfos} is used to check if and existing print package info can be reused. Otherwise, a new map is created.
	 */
	private IPair<ArrayKey, I_C_Print_PackageInfo> getCreatePrintPackageInfo(final int hwPrinterId,
			final int hwTrayId,
			final int calX,
			final int calY,
			final ArrayKey precedingGroupKey)
	{
		final ArrayKey groupKey = new ArrayKey(hwPrinterId, hwTrayId, calX, calY);

		if (!Objects.equals(groupKey, precedingGroupKey) || printPackageInfos.isEmpty())
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

		final IPair<ArrayKey, I_C_Print_PackageInfo> result = ImmutablePair.of(groupKey, printPackageInfo);
		return result;
	}

	/**
	 * Add {@link ArchivePart} to internal map.
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
	 * Iterates all values of the {@link #printPackageInfos} map and appends their PDF data to the given <code>out</code> stream, while updating the individual {@link I_C_Print_PackageInfo}s'
	 * <code>pageFrom</code> and <code>pageTo</code> values.
	 *
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
			logger.debug("Page to ({}) is greater then number of pages. Considering number of pages: {}", new Object[] { pageTo, archivePageNums });
			pageTo = archivePageNums;
		}
		if (pageFrom > pageTo)
		{
			// shall not happen at this point
			logger.warn("Page from ({}) is greater then Page to ({}). Skipping: {}", new Object[] { pageFrom, pageTo, archivePart });
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
		// Parameters
		private final I_C_Print_Job_Line printJobLine;

		// Archive's Data
		private final transient byte[] data;

		public ArchiveData(@NonNull final I_C_Print_Job_Line printJobLine, @Nullable final byte[] data)
		{
			this.printJobLine = printJobLine;
			this.data = data;
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

		public PdfReader createPdfReader() throws IOException
		{
			final PdfReader reader = new PdfReader(data);
			return reader;
		}

		public boolean hasData()
		{
			return data != null;
		}
	}

	/**
	 * Helper class that holds a reference to an {@link I_AD_Archive}, a print job line and a page range.
	 */
	private static class ArchivePart
	{
		@Getter
		private final ArchiveData archiveData;

		@Getter
		private final int pageFrom;

		@Getter
		private final int pageTo;

		public ArchivePart(
				@NonNull final ArchiveData archiveData,
				final int pageFrom,
				final int pageTo)
		{
			this.archiveData = archiveData;

			this.pageFrom = pageFrom;
			this.pageTo = pageTo;
			Check.assume(pageFrom <= pageTo, "pageFrom={} is less or equal to pageTo={} for {}", pageFrom, pageTo, archiveData);
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public I_C_Print_Job_Line getPrintJobLine()
		{
			return archiveData.getPrintJobLine();
		}
	}
}
