package de.metas.printing.api.impl;

import de.metas.adempiere.form.IClientUI;
import de.metas.common.util.time.SystemTime;
import de.metas.document.archive.api.ArchiveFileNameService;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.i18n.Language;
import de.metas.lock.api.ILockManager;
import de.metas.lock.api.impl.PlainLockManager;
import de.metas.lock.spi.impl.PlainLockDatabase;
import de.metas.lock.spi.impl.PlainLockDatabase.LockKey;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.OutputType;
import de.metas.printing.PrintOutputFacade;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.model.I_AD_Archive;
import de.metas.printing.model.I_AD_Printer;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_Calibration;
import de.metas.printing.model.I_AD_PrinterHW_MediaSize;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_AD_PrinterTray_Matching;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.printing.model.I_AD_Printer_Tray;
import de.metas.printing.model.I_C_PrintPackageData;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.printing.model.validator.AD_Archive;
import de.metas.printing.printingdata.PrintingDataFactory;
import de.metas.printing.printingdata.PrintingDataToPDFFileStorer;
import de.metas.printing.rpl.requesthandler.CreatePrintPackageRequestHandler;
import de.metas.printing.spi.impl.ExternalSystemsPrintingNotifier;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.TestClientUI;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Session;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.junit.Assert;
import org.junit.jupiter.api.TestInfo;
import org.junit.rules.TestName;

import javax.annotation.Nullable;
import javax.print.attribute.standard.MediaSize;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.assertj.core.api.Assertions.*;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

// there is high amount of methods because it's a helper...
public class Helper
{
	public static final POJOLookupMap db;
	public static final PlainPrintingDAO printingDAO;

	static
	{
		// NOTE: we need to enable UnitTestMode before getting the database, else we will get NULL
		org.compiere.Adempiere.enableUnitTestMode();
		db = POJOLookupMap.get();
		Check.assumeNotNull(db, "db not null");

		printingDAO = new PlainPrintingDAO();

		staticInit();
	}

	public static final double PDFCOMPARE_MatchingPercent = 98.00;

	protected static final String HOSTKEY_Host01 = "host01";

	protected Properties ctx;

	protected String trxName; // set in the setup method

	/**
	 * Shall we always generate PDF files, even if the test went OK?
	 */
	protected boolean alwaysGenerateOutputPDFs = false;

	protected boolean autoCreateHWPrinters = true;

	private final String testDisplayName;

	private TestClientUI clientUI = null;
	private PrintOutputFacade printOutputFacade;

	public Helper(@NonNull final TestName testName)
	{
		this.testDisplayName = testName.getMethodName();
	}

	public Helper(@NonNull final TestInfo testInfo)
	{
		this.testDisplayName = testInfo.getDisplayName();
	}

	private static boolean staticInitialized = false;

	public static void staticInit()
	{
		if (staticInitialized)
		{
			return;
		}

		AdempiereTestHelper.get().staticInit();

		// de.metas.printing (this module):
		Services.registerService(IPrintingDAO.class, printingDAO);
		Services.registerService(IDocumentBL.class, new PlainDocumentBL());

		staticInitialized = true;
	}

	public void setup()
	{
		AdempiereTestHelper.get().init();

		ctx = Env.getCtx();
		trxName = Services.get(ITrxManager.class).createTrxName(Helper.class.getName());

		// Make sure database is clean
		db.clear();

		clientUI = new TestClientUI();
		Services.registerService(IClientUI.class, clientUI);

		//
		// AD_Client
		final I_AD_Client client = printingDAO.newInstance(ctx, I_AD_Client.class, ITrx.TRXNAME_None);
		client.setStoreArchiveOnFileSystem(false);
		InterfaceWrapperHelper.save(client);
		Env.setContext(ctx, "#AD_Client_ID", client.getAD_Client_ID());

		//
		// Login user
		final I_AD_User loggedUser = printingDAO.newInstance(ctx, I_AD_User.class, ITrx.TRXNAME_None);
		loggedUser.setName("loggedUser-" + getClass().getSimpleName());
		printingDAO.save(loggedUser);
		Env.setContext(ctx, "#AD_User_ID", loggedUser.getAD_User_ID());

		//
		// AD_Session
		final I_AD_Session session = printingDAO.newInstance(ctx, I_AD_Session.class, ITrx.TRXNAME_None);
		session.setHostKey(HOSTKEY_Host01);
		session.setLoginUsername(loggedUser.getName());
		printingDAO.save(session);
		Env.setContext(ctx, Env.CTXNAME_AD_Session_ID, session.getAD_Session_ID());

		//
		// Base Language
		Language.setBaseLanguage(() -> "de_DE");

		SpringContextHolder.registerJUnitBean(new ExternalSystemsPrintingNotifier(new ArrayList<>()));

		printOutputFacade = new PrintOutputFacade(
				new PrintingDataFactory(new HardwarePrinterRepository(), new ArchiveFileNameService()),
				new PrintingDataToPDFFileStorer());
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public String getSessionHostKey()
	{
		return Services.get(ISessionBL.class).getCurrentSession(ctx).getOrCreateHostKey(ctx);
	}

	public POJOLookupMap getDB()
	{
		return db;
	}

	public PlainPrintingDAO getDAO()
	{
		return printingDAO;
	}

	public TestClientUI getClientUI()
	{
		return clientUI;
	}

	public I_C_Print_Job createPrintJob()
	{
		final I_C_Print_Job printJob = printingDAO.newInstance(ctx, I_C_Print_Job.class, ITrx.TRXNAME_None);
		printingDAO.save(printJob);
		return printJob;
	}

	public I_C_Print_Job_Line createPrintJobLine(
			final I_C_Print_Job printJob,
			final I_AD_PrinterRouting singleRouting,
			final String pdfSuffix)
	{
		return createPrintJobLine(printJob,
				Collections.singletonList(singleRouting),
				getPdf(pdfSuffix));
	}

	public I_C_Print_Job_Line createPrintJobLine(
			final I_C_Print_Job printJob,
			final List<I_AD_PrinterRouting> routings,
			final byte[] pdfBinaryData)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printJob);
		final String trxName = InterfaceWrapperHelper.getTrxName(printJob);

		final IArchiveStorage storage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(ctx);
		final org.compiere.model.I_AD_Archive archive = storage.newArchive(ctx, trxName);
		archive.setName(Long.toString(SystemTime.millis()));
		storage.setBinaryData(archive, pdfBinaryData);
		printingDAO.save(archive);

		final I_C_Printing_Queue queue = printingDAO.newInstance(ctx, I_C_Printing_Queue.class, trxName);
		queue.setAD_Archive(archive);
		printingDAO.save(queue);

		final int seqNo = IteratorUtils.toList(printingDAO.retrievePrintJobLines(printJob)).size() + 1;
		final I_C_Print_Job_Line printJobLine = printingDAO.newInstance(ctx, I_C_Print_Job_Line.class, trxName);
		printJobLine.setC_Print_Job(printJob);
		printJobLine.setC_Printing_Queue(queue);
		printJobLine.setSeqNo(seqNo);
		printingDAO.save(printJobLine);

		for (final I_AD_PrinterRouting routing : routings)
		{
			final I_C_Print_Job_Detail detail = printingDAO.newInstance(ctx, I_C_Print_Job_Detail.class, trxName);
			detail.setC_Print_Job_Line(printJobLine);
			detail.setAD_PrinterRouting_ID(routing.getAD_PrinterRouting_ID());
			printingDAO.save(detail);
		}
		return printJobLine;
	}

	/**
	 * Calls {@link #createPrintJobInstructions(I_C_Print_Job, int)} with copies == 1.
	 */
	public I_C_Print_Job_Instructions createPrintJobInstructions(final I_C_Print_Job printJob)
	{
		return createPrintJobInstructions(printJob, 1);
	}

	public I_C_Print_Job_Instructions createPrintJobInstructions(@NonNull final I_C_Print_Job printJob, final int copies)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printJob);
		final UserId userToPrintId = Env.getLoggedUserId(ctx);
		final I_C_Print_Job_Line firstLine = printingDAO.retrievePrintJobLine(printJob, IPrintingDAO.SEQNO_First);
		final I_C_Print_Job_Line lastLine = printingDAO.retrievePrintJobLine(printJob, IPrintingDAO.SEQNO_Last);

		final boolean createWithSpecificHostKey = true; // create with hostKey, so we can verify the key later, if we want.

		return Services.get(IPrintJobBL.class).createPrintJobInstructions(userToPrintId,
				createWithSpecificHostKey,
				firstLine,
				lastLine,
				copies);
	}

	public I_AD_Printer getCreatePrinter(final String printerName)
	{
		I_AD_Printer printer = printingDAO.getLookupMap().getFirstOnly(I_AD_Printer.class, pojo -> Objects.equals(pojo.getPrinterName(), printerName));
		if (printer == null)
		{
			printer = printingDAO.newInstance(ctx, I_AD_Printer.class, ITrx.TRXNAME_None);
			printer.setPrinterName(printerName);
			printingDAO.save(printer);
		}

		return printer;
	}

	public I_AD_PrinterHW getCreatePrinterHW(@NonNull final String printerName)
	{
		return getCreatePrinterHW(printerName, OutputType.Queue);
	}

	public I_AD_PrinterHW getCreatePrinterHW(@NonNull final String printerName, @NonNull final OutputType outputType)
	{
		I_AD_PrinterHW printer = printingDAO.getLookupMap().getFirstOnly(I_AD_PrinterHW.class, pojo -> Objects.equals(pojo.getName(), printerName));
		if (printer == null)
		{
			printer = printingDAO.newInstance(ctx, I_AD_PrinterHW.class, ITrx.TRXNAME_None);
			printer.setName(printerName);
			printer.setOutputType(outputType.getCode());
			printingDAO.save(printer);
		}
		return printer;
	}

	/**
	 * @param trayName optional; if {@code null}, then {@code null} is returned.
	 */
	public I_AD_Printer_Tray getCreatePrinterTray(
			@NonNull final String printerName,
			@Nullable final String trayName,
			@Nullable final Integer hwTrayNumber)
	{
		if (Check.isBlank(trayName))
		{
			return null;
		}

		final I_AD_Printer printer = getCreatePrinter(printerName);
		I_AD_Printer_Tray tray = printingDAO
				.getLookupMap()
				.getFirstOnly(
						I_AD_Printer_Tray.class,
						pojo -> Objects.equals(load(pojo.getAD_Printer_ID(), I_AD_Printer.class), printer)
								&& Objects.equals(pojo.getName(), trayName));
		if (tray == null)
		{
			tray = printingDAO.newInstance(ctx, I_AD_Printer_Tray.class, ITrx.TRXNAME_None);
			tray.setAD_Printer_ID(printer.getAD_Printer_ID());
			tray.setName(trayName);
			printingDAO.save(tray);

			if (autoCreateHWPrinters)
			{
				try
				{
					autoCreateHWPrinters = false; // avoid recursive loops
					createPrinterConfigAndMatching(HOSTKEY_Host01,
							createHWName(printerName),
							createHWName(trayName),
							hwTrayNumber,
							printerName,
							trayName							);
				}
				finally
				{
					autoCreateHWPrinters = true;
				}
			}
		}

		return tray;
	}

	/**
	 * Creates default HW name for a given printer or tray name
	 *
	 * @return name + "-HW"
	 */
	public String createHWName(@NonNull final String name)
	{
		Check.assume(!Check.isEmpty(name, true), "Name not empty: {}", name);
		return name + "-HW";
	}

	/**
	 * @param trayName optional; if {@code null}, then {@code null} is returned.
	 */
	public I_AD_PrinterHW_MediaTray getCreatePrinterTrayHW(
			@NonNull final String printerName,
			@Nullable final String trayName,
			@Nullable final Integer hwTrayNumber)
	{
		if (Check.isBlank(trayName))
		{
			return null;
		}
		final I_AD_PrinterHW printer = getCreatePrinterHW(printerName);
		I_AD_PrinterHW_MediaTray tray = printingDAO.getLookupMap().getFirstOnly(I_AD_PrinterHW_MediaTray.class, pojo -> Objects.equals(pojo.getAD_PrinterHW(), printer)
				&& Objects.equals(pojo.getName(), trayName));
		if (tray == null)
		{
			tray = printingDAO.newInstance(ctx, I_AD_PrinterHW_MediaTray.class, ITrx.TRXNAME_None);
			tray.setAD_PrinterHW(printer);
			tray.setName(trayName);
			tray.setTrayNumber(hwTrayNumber);
			printingDAO.save(tray);
		}

		return tray;
	}

	/**
	 * Create printer routing for {@link I_AD_PrinterRouting#ROUTINGTYPE_PageRange}.
	 *
	 * @param C_DocType_ID if > 0, then this is set to be the new routing's matching-doctype ID.
	 */
	public I_AD_PrinterRouting createPrinterRouting(
			@NonNull final String printerName,
			@Nullable final String trayName,
			@Nullable final Integer hwTrayNumber,
			final int C_DocType_ID,
			final int pageFrom,
			final int pageTo)
	{
		final I_AD_PrinterRouting routing = printingDAO.newInstance(ctx, I_AD_PrinterRouting.class, ITrx.TRXNAME_None);
		routing.setAD_Org_ID(0); // All Orgs by default
		routing.setAD_Printer_ID(getCreatePrinter(printerName).getAD_Printer_ID());
		if (Check.isNotBlank(trayName))
		{
			routing.setAD_Printer_Tray(getCreatePrinterTray(printerName, trayName, hwTrayNumber));
		}
		if (C_DocType_ID > 0)
		{
			routing.setC_DocType_ID(C_DocType_ID);
		}

		if (pageFrom > 0)
		{
			routing.setPageFrom(pageFrom);
		}
		if (pageTo > 0)
		{
			routing.setPageTo(pageTo);
		}

		routing.setRoutingType(I_AD_PrinterRouting.ROUTINGTYPE_PageRange);

		printingDAO.save(routing);
		POJOWrapper.setInstanceName(routing, printerName + "-" + trayName);
		return routing;
	}

	/**
	 * Create printer routing for {@link I_AD_PrinterRouting#ROUTINGTYPE_LastPages}.
	 *
	 * @return printer routing
	 */
	public I_AD_PrinterRouting createPrinterRoutingForLastPages(
			final String printerName,
			final String trayName,
			@Nullable final Integer hwTrayNumber,
			final int C_DocType_ID,
			final int lastPages)
	{
		final I_AD_PrinterRouting routing = printingDAO.newInstance(ctx, I_AD_PrinterRouting.class, ITrx.TRXNAME_None);
		routing.setAD_Org_ID(0); // All Orgs by default
		routing.setAD_Printer_ID(getCreatePrinter(printerName).getAD_Printer_ID());
		routing.setAD_Printer_Tray(getCreatePrinterTray(printerName, trayName, hwTrayNumber));

		if (C_DocType_ID > 0)
		{
			routing.setC_DocType_ID(C_DocType_ID);
		}

		if (lastPages > 0)
		{
			routing.setLastPages(lastPages);
		}

		routing.setRoutingType(I_AD_PrinterRouting.ROUTINGTYPE_LastPages);

		printingDAO.save(routing);
		POJOWrapper.setInstanceName(routing, printerName + "-" + trayName);
		return routing;
	}

	public void createPrinterConfigAndMatching(
			@Nullable final String hostKey,
			@NonNull final String hwPrinterName,
			@Nullable final String hwTrayName,
			@Nullable final Integer hwTrayNumber,
			@NonNull final String printerName,
			@Nullable final String trayName
			)
	{
		final I_AD_Printer_Config printerConfig = printingDAO
				.getLookupMap()
				.getFirstOnly(I_AD_Printer_Config.class, model -> Objects.equals(hostKey, model.getConfigHostKey()));

		final I_AD_Printer_Config printerConfigToUse;
		if (printerConfig == null)
		{
			printerConfigToUse = printingDAO.newInstance(ctx, I_AD_Printer_Config.class, ITrx.TRXNAME_None);
			printerConfigToUse.setConfigHostKey(hostKey);
			printerConfigToUse.setAD_User_PrinterMatchingConfig_ID(Env.getAD_User_ID());
			printingDAO.save(printerConfigToUse);
		}
		else
		{
			printerConfigToUse = printerConfig;
		}

		I_AD_Printer_Matching printerMatching = printingDAO
				.getLookupMap()
				.getFirstOnly(I_AD_Printer_Matching.class,
						pojo -> Objects.equals(pojo.getAD_Printer_Config_ID(), printerConfigToUse.getAD_Printer_Config_ID())
								&& Objects.equals(load(pojo.getAD_Printer_ID(), I_AD_Printer.class).getPrinterName(), printerName)
								&& Objects.equals(pojo.getAD_PrinterHW().getName(), hwPrinterName));
		if (printerMatching == null)
		{
			printerMatching = printingDAO.newInstance(ctx, I_AD_Printer_Matching.class, ITrx.TRXNAME_None);
			printerMatching.setAD_Printer_Config(printerConfigToUse);
			printerMatching.setAD_Printer_ID(getCreatePrinter(printerName).getAD_Printer_ID());
			printerMatching.setAD_PrinterHW(getCreatePrinterHW(hwPrinterName));
			printingDAO.save(printerMatching);
		}

		final int printerMatchingID = printerMatching.getAD_Printer_Matching_ID();
		I_AD_PrinterTray_Matching trayMatching = printingDAO.getLookupMap().getFirstOnly(I_AD_PrinterTray_Matching.class, pojo -> Objects.equals(pojo.getAD_Printer_Tray().getName(), trayName)
				&& Objects.equals(pojo.getAD_PrinterHW_MediaTray(), hwTrayName)
				&& Objects.equals(pojo.getAD_Printer_Matching_ID(), printerMatchingID));
		if (trayMatching == null)
		{
			trayMatching = printingDAO.newInstance(ctx, I_AD_PrinterTray_Matching.class, ITrx.TRXNAME_None);
			trayMatching.setAD_Printer_Matching(printerMatching);
			trayMatching.setAD_Printer_Tray(getCreatePrinterTray(printerName, trayName, hwTrayNumber));
			trayMatching.setAD_PrinterHW_MediaTray(getCreatePrinterTrayHW(hwPrinterName, hwTrayName, hwTrayNumber));
			printingDAO.save(trayMatching);
		}
	}

	public void createPrinterHWCalibration(
			final String printerName,
			final String mediaSizeName,
			final String trayName,
			@Nullable final Integer hwTrayNumber,
			final int calX,
			final int calY)
	{
		final I_AD_PrinterHW printer = getCreatePrinterHW(printerName);

		final I_AD_PrinterHW_MediaSize mediaSize = printingDAO.newInstance(ctx, I_AD_PrinterHW_MediaSize.class, ITrx.TRXNAME_None);
		mediaSize.setName(mediaSizeName);
		mediaSize.setAD_PrinterHW(printer);
		printingDAO.save(mediaSize);

		final I_AD_PrinterHW_MediaTray mediaTray = getCreatePrinterTrayHW(printerName, trayName, hwTrayNumber);
		// created by 'getCreatePrinterHW(printerName)'
		final I_AD_PrinterHW_MediaSize hwMediaSize = printingDAO.retrieveMediaSize(printer, MediaSize.ISO.A4, true);

		final I_AD_PrinterHW_Calibration calibration = printingDAO.newInstance(ctx, I_AD_PrinterHW_Calibration.class, ITrx.TRXNAME_None);
		calibration.setAD_PrinterHW(printer);
		calibration.setAD_PrinterHW_MediaTray(mediaTray);
		calibration.setAD_PrinterHW_MediaSize(hwMediaSize);
		calibration.setCalX(calX);
		calibration.setCalY(calY);
		printingDAO.save(calibration);
	}

	/**
	 * Gets the PDF from src/test/resources (document[suffix].pdf)
	 *
	 * @return PDF as bytes array
	 */
	public byte[] getPdf(final String suffix)
	{
		final String resourceName = "/document" + suffix + ".pdf";
		final InputStream in = getClass().getResourceAsStream(resourceName);
		Assert.assertNotNull("Resource not found: " + resourceName, in);

		return Util.readBytes(in);
	}

	public static File writePdf(final String prefix, final byte[] data)
	{
		try
		{
			final File file = File.createTempFile(prefix, ".pdf");
			final FileOutputStream out = new FileOutputStream(file);

			out.write(data);
			out.close();

			return file;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e); // NOPMD by tsa on 2/28/13 2:14 AM
		}
	}

	public void assertEqualsPDF(final byte[] pdfExpected, final byte[] pdfActual)
	{
		assertEqualsPDF(testDisplayName, pdfExpected, pdfActual);
	}

	private void assertEqualsPDF(final String testName, final byte[] pdfExpected, final byte[] pdfActual)
	{
		final boolean equals = equalPDFs(pdfExpected, pdfActual, PDFCOMPARE_MatchingPercent);
		if (equals && !alwaysGenerateOutputPDFs)
		{
			return;
		}

		final File fileExpected = writePdf(testName + "-expected_", pdfExpected);
		final File fileActual = writePdf(testName + "-actual_", pdfActual);

		final String msg = "Produced PDF is not valid for '" + testName + " (matching tolerance: " + PDFCOMPARE_MatchingPercent + ")."
				+ " Please check " + fileExpected + " and " + fileActual + ".";
		Assert.assertTrue(msg, equals);
	}

	/**
	 * Compare if to PDFs are equal
	 *
	 * @return true if equal
	 */
	public static boolean equalPDFs(final byte[] data1, final byte[] data2, final double matchingPercent)
	{
		Check.assume(matchingPercent >= 0 && matchingPercent <= 100, "Invalid matchingPercent: " + matchingPercent);

		if (data1 == data2)
		{
			return true;
		}
		if (data2 == null)
		{
			return false;
		}

		if (data1.length != data2.length)
		{
			return false;
		}

		final int len = data1.length;

		int bytesEqual = 0;
		for (int i = 0; i < len; i++)
		{
			if (data1[i] == data2[i])
			{
				bytesEqual++;
			}
		}

		final double equalsPerc = bytesEqual * 100 / len;

		if (equalsPerc < matchingPercent)
		{
			return false;
		}

		return true;
	}

	public I_Test createTestRecord()
	{
		final I_Test record = printingDAO.newInstance(ctx, I_Test.class, ITrx.TRXNAME_None);
		printingDAO.save(record);
		return record;
	}

	public I_AD_Archive createArchiveAndEnqueue(final Object record, final String pdfSuffix)
	{
		final byte[] data = getPdf(pdfSuffix);

		final POJOWrapper wrapper = POJOWrapper.getWrapper(record);
		assertThat(wrapper).as("POJOWrapper not found for record: %s", record).isNotNull();

		Integer AD_Org_ID = (Integer)wrapper.getValue("AD_Org_ID", Integer.class);
		if (AD_Org_ID == null)
		{
			AD_Org_ID = 0;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(record);
		final String trxName = InterfaceWrapperHelper.getTrxName(record);

		final IArchiveStorage storage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(ctx);
		final I_AD_Archive archive = InterfaceWrapperHelper.create(storage.newArchive(ctx, trxName), I_AD_Archive.class);
		archive.setAD_Org_ID(AD_Org_ID);
		archive.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(wrapper.getTableName()));
		archive.setRecord_ID(wrapper.getId());
		archive.setName(pdfSuffix);
		storage.setBinaryData(archive, data);
		archive.setIsDirectEnqueue(true);
		printingDAO.save(archive);

		//
		// Enqueue archive to printing queue
		new AD_Archive(printOutputFacade).printArchive(archive);

		return archive;
	}

	public I_C_Print_Package createNextPrintPackageAndTest(
			@Nullable final I_C_Print_Job printJobExpected,
			@Nullable final byte[] dataExpected)
	{
		//
		// Validation: Print Job Instructions before processing (if available)
		if (printJobExpected != null)
		{
			final I_C_Print_Job_Instructions printJobInstructions = printingDAO.retrievePrintJobInstructionsForPrintJob(printJobExpected);
			assertThat(printJobInstructions).as("No print job instructions for %s", printJobExpected).isNotNull();

			// task 09028: don't check for the host key..the user shall be able to print this wherever they are logged in
			// Assert.assertEquals("Invalid HostKey for print job instructions " + printJobInstructions, getSessionHostKey(), printJobInstructions.getHostKey());
			assertThat(printJobInstructions.getStatus()).as("After package created, %s shall be marked as Send", printJobInstructions)
					.isEqualTo(X_C_Print_Job_Instructions.STATUS_Pending);
		}

		//
		// Creating the Print Package template (request):
		final I_C_Print_Package printPackageRequest = createPrintPackageRequest();

		// Trigger print package response producer
		final I_C_Print_Package printPackageResponse = createPrintPackageResponse(printPackageRequest);
		assertThat(printPackageResponse).as("A print package response shall be created for request: %s", printPackageRequest)
				.isNotNull();
		//
		// Validation: PrintPackage - PrintJob link
		// 04072: C_Print_Job_ID is taken from C-Print_Job_Instructions of the C_Print_Package
		final I_C_Print_Job_Instructions printInstructions = printPackageResponse.getC_Print_Job_Instructions();
		final I_C_Print_Job printJobActual = printInstructions.getC_Print_Job();
		assertThat(printJobActual).as("No print job set for: %s", printPackageResponse).isNotNull();
		if (printJobExpected != null)
		{
			refresh(printJobExpected);
			assertThat(printJobActual).as("Invalid print job for: %s", printPackageResponse).isEqualTo(printJobExpected);
		}
		assertThat(printJobActual.isProcessed()).as("Print job not marked as processed: %s", printJobActual).isTrue();

		for (final I_C_Print_Job_Line jobLine : IteratorUtils.asIterable(printingDAO.retrievePrintJobLines(printJobActual)))
		{
			final I_C_Print_Package printPackageActual = jobLine.getC_Print_Package();
			assertThat(printPackageActual).as("Invalid print package set for: %s", jobLine).isEqualTo(printPackageActual);
		}

		//
		// Validate Print Job Instructions (after processing)
		final I_C_Print_Job_Instructions printJobInstructions = printingDAO.retrievePrintJobInstructionsForPrintJob(printJobActual);
		assertThat(printJobInstructions).as("No print job instructions for %s", printJobActual).isNotNull();
		assertThat(printJobInstructions.getStatus()).as("After package created, %s shall be marked as Send", printJobInstructions)
				.isEqualTo(X_C_Print_Job_Instructions.STATUS_Send);
		//
		// Validation: Print Package Data
		final I_C_PrintPackageData printData = printingDAO.getPrintPackageData(printPackageResponse);
		assertThat(printData).as("No print data created for: %s", printPackageResponse).isNotNull();

		// Validation: Generated PDF (if available)
		final byte[] dataActual = printData.getPrintData();
		assertThat(dataActual).as("No print package data for %s", printPackageResponse).isNotNull();
		if (dataExpected != null)
		{
			assertEqualsPDF(dataExpected, dataActual);
		}

		//
		// Return it for further validations
		return printPackageResponse;
	}

	public I_C_Print_Package createPrintPackageRequest()
	{
		final String transactionId = UUID.randomUUID().toString();
		final I_C_Print_Package printPackageRequest = InterfaceWrapperHelper.newInstance(I_C_Print_Package.class, PlainContextAware.newWithTrxName(ctx, trxName));
		printPackageRequest.setTransactionID(transactionId);
		InterfaceWrapperHelper.save(printPackageRequest);

		return printPackageRequest;
	}

	public I_C_Print_Package createPrintPackageResponse(final I_C_Print_Package printPackage)
	{
		// Trigger print package response producer

		// Variant 1: call the BL directly
		// final boolean created = Services.get(IPrintPackageBL.class).createPrintPackage(printPackage, printJob, printCtx);
		// Assert.assertTrue("Print package shall be created for request: " + printPackage, created);

		// Variant 2: use the response handler
		final I_C_Print_Package printPackageResponse = new CreatePrintPackageRequestHandler().createResponse(printPackage);
		return printPackageResponse;
		// final boolean created = new CreatePrintPackageRequestHandler().updatePrintPackage(printPackage);
		// Assert.assertTrue("Print package shall be updated: " + printPackage, created);
	}

	public void addToPrintQueue(final String pdfSuffix, final int AD_Org_ID, final int C_DocType_ID)
	{
		final I_Test record = createTestRecord();
		if (AD_Org_ID >= 0)
		{
			record.setAD_Org_ID(AD_Org_ID);
		}
		if (C_DocType_ID > 0)
		{
			POJOWrapper.getWrapper(record).setValue("C_DocType_ID", C_DocType_ID);
			InterfaceWrapperHelper.save(record);
		}

		createArchiveAndEnqueue(record, pdfSuffix);
	}

	public void createAllPrintJobs()
	{
		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		final IPrintingQueueQuery query = printingQueueBL.createPrintingQueueQuery();
		final IPrintJobBL printJobBL = Services.get(IPrintJobBL.class);

		query.setFilterByProcessedQueueItems(false);

		final List<IPrintingQueueSource> sources = printingQueueBL.createPrintingQueueSources(getCtx(), query);

		for (final IPrintingQueueSource source : sources)
		{
			printJobBL.createPrintJobs(source);
		}
	}

	public void assumeNothingLocked()
	{
		final PlainLockManager lockManager = (PlainLockManager)Services.get(ILockManager.class);
		final PlainLockDatabase lockDatabase = lockManager.getLockDatabase();
		final List<LockKey> locks = lockDatabase.getLocks();

		assertThat(locks).as("No locks expecteded").isEmpty();
	}
}
