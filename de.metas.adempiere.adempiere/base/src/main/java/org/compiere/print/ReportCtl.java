/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.print;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelectionCheck;
import org.compiere.model.MProcess;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PrintInfo;
import org.compiere.process.ProcessInfo;
import org.compiere.util.ASyncProcess;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.logging.LogManager;
import de.metas.process.ProcessCtl;

/**
 * Report Controller.
 * Changes by metas: start method now also provides the
 * process info with the table id (not only record id)
 *
 * @author Jorg Janke
 * @version $Id: ReportCtl.java,v 1.3 2006/10/08 07:05:08 comdivision Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>FR [ 1866739 ] ReportCtl: use printformat from the transient/serializable
 */
public final class ReportCtl
{
	/**
	 * Constructor - prevent instance
	 */
	private ReportCtl()
	{
		super();
	}	// ReportCtrl

	/** Static Logger */
	private static final Logger logger = LogManager.getLogger(ReportCtl.class);

	private static ReportViewerProvider viewerProvider = re -> logger.warn("No " + ReportViewerProvider.class + " registered to display " + re);

	/**
	 * Create Report.
	 * Called from ProcessCtl.
	 * - Check special reports first, if not, create standard Report
	 *
	 * @param parent The window which invoked the printing
	 * @param WindowNo The windows number which invoked the printing
	 * @param pi process info
	 */
	public static void start(final ASyncProcess parent, final ProcessInfo pi)
	{
		logger.info("start - {}", pi);

		//
		// Get printer name
		String printerName = null;
		final IPrinterRoutingBL printerRouting = Services.get(IPrinterRoutingBL.class);
		if (printerRouting != null)
		{
			printerName = printerRouting.findPrinterName(pi);
		}

		//
		// Custom print format
		final MPrintFormat customPrintFormat = null; // nothing

		final int adProcessId = pi.getAD_Process_ID();
		final int reportEngineDocumentType = extractReportEngineDocumentType(adProcessId);

		//
		// Payment Check Print
		if (adProcessId == 313)     		// C_Payment
		{
			final int WindowNo = pi.getWindowNo();
			final int C_Payment_ID = pi.getRecord_ID();
			final int adTableId = I_C_PaySelectionCheck.Table_ID;
			final int C_PaySelectionCheck_ID = prepareCheckPrint(C_Payment_ID);
			startDocumentPrint(ReportEngine.CHECK, customPrintFormat, C_PaySelectionCheck_ID, adTableId, pi.getAD_PInstance_ID(), parent, WindowNo, !pi.isPrintPreview(), printerName);
		}
		//
		// Standard document print
		else if (reportEngineDocumentType >= 0)
		{
			final int WindowNo = pi.getWindowNo();
			startDocumentPrint(reportEngineDocumentType, customPrintFormat, pi.getRecord_ID(), pi.getTable_ID(), pi.getAD_PInstance_ID(), parent, WindowNo, !pi.isPrintPreview(), printerName);
		}
		//
		// Financial reporting
		else if (adProcessId == 202			// Financial Report
				|| adProcessId == 204)     			// Financial Statement
		{
			startFinReport(pi, printerName);
		}
		//
		// Standard report
		else
		{
			startStandardReport(pi, printerName);
		}
	}

	private static final int extractReportEngineDocumentType(final int adProcessId)
	{
		if (adProcessId == 110)
		{
			return ReportEngine.ORDER;
		}
		else if (adProcessId == 116)
		{
			return ReportEngine.INVOICE;
		}
		else if (adProcessId == 117)
		{
			return ReportEngine.SHIPMENT;
		}
		else if (adProcessId == 217)
		{
			return ReportEngine.PROJECT;
		}
		else if (adProcessId == 159)
		{
			return ReportEngine.DUNNING;
		}
		// else if(adProcessId == 313) // Payment // => will be handled on upper level
		// return;
		if (adProcessId == MProcess.getProcess_ID("Rpt PP_Order", null))
		{
			return ReportEngine.MANUFACTURING_ORDER;
		}
		if (adProcessId == MProcess.getProcess_ID("Rpt DD_Order", null))
		{
			return ReportEngine.DISTRIBUTION_ORDER;
		}
		else
		{
			return -1;
		}
	}

	/**************************************************************************
	 * Start Standard Report.
	 * - Get Table Info & submit.<br>
	 * A report can be created from:
	 * <ol>
	 * <li>attached MPrintFormat, if any (see {@link ProcessInfo#setTransientObject(Object)}, {@link ProcessInfo#setSerializableObject(java.io.Serializable)}
	 * <li>process information (AD_Process.AD_PrintFormat_ID, AD_Process.AD_ReportView_ID)
	 * </ol>
	 *
	 * @param pi Process Info
	 * @param IsDirectPrint if true, prints directly - otherwise View
	 * @return true if OK
	 */
	private static void startStandardReport(final ProcessInfo pi, final String printerName)
	{
		final ReportEngine re = createReportEngineForStandardReport(pi, printerName);
		createOutput(re, pi.isPrintPreview(), printerName);
	}

	/**
	 * @return report engine; never returns null
	 */
	private static final ReportEngine createReportEngineForStandardReport(final ProcessInfo pi, final String printerName)
	{
		//
		// Create Report Engine by using attached MPrintFormat (if any)
		Object piObject = pi.getTransientObject();
		if (piObject == null)
		{
			piObject = pi.getSerializableObject();
		}

		if (piObject instanceof MPrintFormat)
		{
			final Properties ctx = Env.getCtx();
			final MPrintFormat format = (MPrintFormat)piObject;
			final String TableName = MTable.getTableName(ctx, format.getAD_Table_ID());
			final MQuery query = MQuery.get(ctx, pi.getAD_PInstance_ID(), TableName);
			final PrintInfo info = new PrintInfo(pi);
			return new ReportEngine(ctx, format, query, info);
		}
		//
		// Create Report Engine normally
		else
		{
			final ReportEngine re = ReportEngine.get(Env.getCtx(), pi);
			if (re == null)
			{
				throw new AdempiereException("ReportEngine could not be created");
			}
			return re;
		}

	}

	/**
	 * Start Financial Report.
	 *
	 * @param pi Process Info
	 * @return true if OK
	 */
	private static void startFinReport(final ProcessInfo pi, final String printerName)
	{
		final ReportEngine re = createReportEngineForFinancialReporting(pi);
		createOutput(re, pi.isPrintPreview(), printerName);
	}	// startFinReport

	private static ReportEngine createReportEngineForFinancialReporting(final ProcessInfo pi)
	{
		// Create Query from Parameters
		final Properties ctx = Env.getCtx();
		final String TableName = pi.getAD_Process_ID() == 202 ? "T_Report" : "T_ReportStatement";
		final MQuery query = MQuery.get(ctx, pi.getAD_PInstance_ID(), TableName);

		// Get PrintFormat
		MPrintFormat format = (MPrintFormat)pi.getTransientObject();
		if (format == null)
		{
			format = (MPrintFormat)pi.getSerializableObject();
		}
		if (format == null)
		{
			throw new AdempiereException("@NotFound@ @AD_PrintFormat_ID@: " + pi);
			// s_log.error("startFinReport - No PrintFormat");
			// return false;
		}
		final PrintInfo info = new PrintInfo(pi);
		final ReportEngine re = new ReportEngine(ctx, format, query, info);
		return re;
	}

	/**
	 * Start Document Print for Type with specified printer.
	 *
	 * @param type document type in ReportEngine
	 * @param Record_ID id
	 * @param parent The window which invoked the printing
	 * @param windowNo The windows number which invoked the printing
	 * @param printerName Specified printer name
	 * @return true if success
	 */
	private static void startDocumentPrint(
			final int type //
			, final MPrintFormat customPrintFormat //
			, final int Record_ID //
			, final int tableId //
			, final int pInstanceId //
			, final ASyncProcess parent //
			, final int windowNo //
			, final boolean isDirectPrint //
			, final String printerName //
	)
	{
		final Properties ctx = Env.getCtx();
		final ReportEngine re = ReportEngine.get(ctx, type, Record_ID, pInstanceId, ITrx.TRXNAME_None);
		if (re == null)
		{
			throw new AdempiereException("@NoDocPrintFormat@");
		}

		// Use custom print format if available
		if (customPrintFormat != null)
		{
			re.setPrintFormat(customPrintFormat);
		}

		final MPrintFormat printFormat = re.getPrintFormat();
		if (printFormat == null)
		{
			throw new AdempiereException("@NoDocPrintFormat@");
		}

		if (printFormat.getJasperProcess_ID() > 0)
		{
			final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
					.setWindowNo(windowNo)
					.setAD_Process_ID(printFormat.getJasperProcess_ID())
					.setRecord(tableId, re.getPrintInfo().getRecord_ID())
					.setPrintPreview(!isDirectPrint)
					.setReportLanguage(printFormat.getLanguage())
					.build();

			// Execute Process
			ProcessCtl.builder()
					.setAsyncParent(parent)
					.setProcessInfo(jasperProcessInfo)
					.execute();
		}
		else
		{
			createOutput(re, !isDirectPrint, printerName);
			if (isDirectPrint)
			{
				ReportEngine.printConfirm(type, Record_ID);
			}
		}
	}

	/**
	 * @return C_PaySelectionCheck_ID
	 */
	private static final int prepareCheckPrint(final int C_Payment_ID)
	{
		// FIXME: HARDCODED: this code shall go in process implementation or deleted because it's not used!
		throw new UnsupportedOperationException();
//
//		// afalcone - [ 1871567 ] Wrong value in Payment document
//		final Properties ctx = Env.getCtx();
//		MPaySelectionCheck.deleteGeneratedDraft(ctx, C_Payment_ID, ITrx.TRXNAME_None);
//		//
//
//		MPaySelectionCheck psc = MPaySelectionCheck.getOfPayment(ctx, C_Payment_ID, ITrx.TRXNAME_None);
//		if (psc == null)
//		{
//			psc = MPaySelectionCheck.createForPayment(ctx, C_Payment_ID, ITrx.TRXNAME_None);
//		}
//
//		return psc == null ? -1 : psc.getC_PaySelectionCheck_ID();
	}

	private static void createOutput(final ReportEngine re, final boolean printPreview, final String printerName)
	{
		if (printPreview)
		{
			preview(re);
		}
		else
		{
			if (printerName != null)
			{
				re.getPrintInfo().setPrinterName(printerName);
			}
			re.print();
		}
	}

	/**
	 * Launch viewer for report
	 *
	 * @param re
	 */
	public static void preview(final ReportEngine re)
	{
		final ReportViewerProvider provider = getReportViewerProvider();
		provider.openViewer(re);
	}

	public static void setReportViewerProvider(final ReportViewerProvider provider)
	{
		if (provider == null)
		{
			throw new IllegalArgumentException("Cannot set report viewer provider to null");
		}
		viewerProvider = provider;
	}

	public static ReportViewerProvider getReportViewerProvider()
	{
		return viewerProvider;
	}
}	// ReportCtl
