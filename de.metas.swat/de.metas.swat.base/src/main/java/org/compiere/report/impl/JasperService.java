package org.compiere.report.impl;

/*
 * #%L
 * de.metas.swat.base
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


import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.MUser;
import org.compiere.model.PrintInfo;
import org.compiere.process.ProcessInfo;
import org.compiere.report.AbstractJasperService;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.service.IPrinterRoutingBL;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

/**
 * Old school Jasper Printing Service.
 * 
 * It is:
 * <ul>
 * <li>directly print (NOTE: this not using our de.metas.printing service)
 * <li>archive the document if needed
 * </ul>
 * 
 * @author tsa
 *
 */
public final class JasperService extends AbstractJasperService
{
	private JRExporter printExporter = new JRPrintServiceExporter();

	@Override
	public void print(
			final JasperPrint jasperPrint,
			final ProcessInfo pi,
			final boolean displayDialog)
	{
		Check.assumeNotNull(pi, "pi not null");

		try
		{
			final PrintInfo printInfo = extractPrintInfo(pi);
			final int numberOfPrintouts = printInfo.getCopies();

			final String printServiceName = Services.get(IPrinterRoutingBL.class).findPrinterName(pi);

			logger.info("Going to print on printer " + printServiceName + "; number of copies: " + numberOfPrintouts);

			final boolean printed = setupAndPrint(jasperPrint, displayDialog, numberOfPrintouts, printServiceName);

			final int archiveId;
			if (printed && printInfo != null)
			{
				logger.info("Jasper report " + jasperPrint.getName() + " has been printed");

				final org.adempiere.archive.api.IArchiveBL archiveService = Services.get(org.adempiere.archive.api.IArchiveBL.class);

				if (archiveService.isToArchive(printInfo))
				{
					final byte[] exportData = exportToPdf(jasperPrint);

					archiveId = archiveService.archive(exportData, printInfo);

					logger.info("Jasper report " + jasperPrint.getName() + " has been archived (ID or -1): " + archiveId);
				}
				else
				{
					archiveId = -1;
				}
			}
			else
			{
				logger.info("Jasper report " + jasperPrint.getName() + " not been printed or printInfo is NULL");
				archiveId = -1;
			}

			if (archiveId > 0)
			{	// task 08046 only try to store it, if there is an AD_Archive to store
				storeDocExchange(pi, numberOfPrintouts, printInfo, printServiceName, printed, archiveId);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("Failed printing " + pi, e);
		}
	}

	private boolean setupAndPrint(
			final JasperPrint jasperPrint,
			final boolean displayDialog,
			final int numberOfPrintouts,
			final String printServiceName)
	{
		final PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();

		printServiceAttributeSet.add(new PrinterName(printServiceName, null));

		printExporter.setParameter(
				JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET,
				printServiceAttributeSet);

		final PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

		printRequestAttributeSet.add(new Copies(numberOfPrintouts));

		printExporter.setParameter(
				JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET,
				printRequestAttributeSet);

		printExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

		printExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, displayDialog);

		boolean printed = false;
		try
		{
			printExporter.exportReport();
			printed = true;
		}
		catch (JRException e)
		{
			// metas: ts: US1176: add better diagnostics
			final StringBuilder sb = new StringBuilder();
			sb.append(e.getLocalizedMessage() + "\n");
			sb.append("PrintServiceName=" + printServiceName);

			throw new AdempiereException(sb.toString(), e);
		}
		return printed;
	}

	/**
	 * 
	 * @see "<a href='http://dewiki908/mediawiki/index.php/Belege_Status/_Uhrzeit_%282009_0027_G30%29'>(2009_0027_G30)</a>"
	 */
	private void storeDocExchange(final ProcessInfo pi, int numberOfPrintouts,
			final PrintInfo printInfo, final String printServiceName,
			final boolean printed, final int archiveId)
	{

		// we just var those vars for debugging sessions
		@SuppressWarnings("unused")
		final int tableId;
		@SuppressWarnings("unused")
		final int recordId;

		if (printInfo != null)
		{
			tableId = printInfo.getAD_Table_ID();
			recordId = printInfo.getRecord_ID();
		}
		else
		{
			tableId = pi.getTable_ID();
			recordId = pi.getRecord_ID();
		}

		DB.saveConstraints();
		try
		{
			DB.getConstraints().incMaxTrx(1).addAllowedTrxNamePrefix("POSave");

			final int userId = pi.getAD_User_ID();

			Services.get(IArchiveEventManager.class).firePrintOut(
					InterfaceWrapperHelper.create(Env.getCtx(), archiveId, I_AD_Archive.class, ITrx.TRXNAME_None),
					MUser.get(Env.getCtx(), userId),
					printServiceName,
					numberOfPrintouts,
					printed && printInfo != null ? IArchiveEventManager.STATUS_Success : IArchiveEventManager.STATUS_Failure);

		}
		finally
		{
			DB.restoreConstraints();
		}

	}

	@Override
	public void setJrPrintExporter(final JRExporter jrExporter)
	{
		printExporter = jrExporter;
	}

}
