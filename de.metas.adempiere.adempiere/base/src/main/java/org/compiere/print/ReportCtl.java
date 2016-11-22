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

import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_PaySelectionCheck;
import org.compiere.model.MQuery;
import org.compiere.model.PrintInfo;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.logging.LogManager;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;

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
	public static final Builder builder()
	{
		return new Builder();
	}
	
	/** Static Logger */
	private static final Logger logger = LogManager.getLogger(ReportCtl.class);
	
	private static ReportViewerProvider defaultReportEngineViewerProvider = re -> logger.warn("No {} registered to display {}", ReportViewerProvider.class, re);

	private final ProcessInfo processInfo;

	private Optional<String> printerName;

	private ReportCtl(final Builder builder)
	{
		super();
		this.processInfo = builder.getProcessInfo();
	}


	/**
	 * Create Report.
	 */
	private void start()
	{
		logger.info("start - {}", processInfo);

		final int adProcessId = processInfo.getAD_Process_ID();
		final int reportEngineDocumentType = extractReportEngineDocumentType(adProcessId);

		//
		// Payment Check Print
		if (adProcessId == 313)     		// C_Payment
		{
			final int C_Payment_ID = processInfo.getRecord_ID();
			final int C_PaySelectionCheck_ID = prepareCheckPrint(C_Payment_ID);
			startDocumentPrint(ReportEngine.CHECK, I_C_PaySelectionCheck.Table_ID, C_PaySelectionCheck_ID);
		}
		//
		// Standard document print
		else if (reportEngineDocumentType >= 0)
		{
			startDocumentPrint(reportEngineDocumentType, processInfo.getTable_ID(), processInfo.getRecord_ID());
		}
		//
		// Financial reporting
		else if (adProcessId == 202			// Financial Report
				|| adProcessId == 204)     			// Financial Statement
		{
			final String printerName = getPrinterName();
			startFinReport(processInfo, printerName);
		}
		//
		// Standard report
		else
		{
			final String printerName = getPrinterName();
			startStandardReport(processInfo, printerName);
		}
	}
	
	private String getPrinterName()
	{
		if (printerName == null)
		{
			printerName = Optional.ofNullable(findPrinterName());
		}
		return printerName.orElse(null);
	}
	
	private final String findPrinterName()
	{
		String printerName = null;
		final IPrinterRoutingBL printerRouting = Services.get(IPrinterRoutingBL.class);
		if (printerRouting != null)
		{
			printerName = printerRouting.findPrinterName(processInfo);
		}
		return printerName;
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
		if (adProcessId == 53028) // Rpt PP_Order
		{
			return ReportEngine.MANUFACTURING_ORDER;
		}
		if (adProcessId == 53044) // Rpt DD_Order
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
		final MPrintFormat format = pi.getResult().getPrintFormat();
		if (format != null)
		{
			final Properties ctx = Env.getCtx();
			final String TableName = Services.get(IADTableDAO.class).retrieveTableName(format.getAD_Table_ID());
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
		final MPrintFormat format = pi.getResult().getPrintFormat();
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

	private void startDocumentPrint(
			final int reportEngineDocumentType //
			, final int adTableId //
			, final int recordId //
	)
	{
		final Properties ctx = Env.getCtx();
		final int adPInstanceId = processInfo.getAD_PInstance_ID();
		final ReportEngine re = ReportEngine.get(ctx, reportEngineDocumentType, recordId, adPInstanceId, ITrx.TRXNAME_None);
		if (re == null)
		{
			throw new AdempiereException("@NoDocPrintFormat@");
		}

		final MPrintFormat printFormat = re.getPrintFormat();
		if (printFormat == null)
		{
			throw new AdempiereException("@NoDocPrintFormat@");
		}

		if (printFormat.getJasperProcess_ID() > 0)
		{
			final ProcessExecutionResult jasperProcessResult = ProcessInfo.builder()
					//
					.setCtx(processInfo.getCtx())
					.setCreateTemporaryCtx()
					.setAD_Client_ID(processInfo.getAD_Client_ID())
					.setAD_User_ID(processInfo.getAD_User_ID())
					.setAD_Role_ID(processInfo.getAD_Role_ID())
					.setWhereClause(processInfo.getWhereClause())
					.setWindowNo(processInfo.getWindowNo())
					.setTabNo(processInfo.getTabNo())
					.setPrintPreview(processInfo.isPrintPreview())
					//
					.setAD_Process_ID(printFormat.getJasperProcess_ID())
					.setRecord(adTableId, recordId)
					.setReportLanguage(printFormat.getLanguage())
					//
					// Execute Process
					.buildAndPrepareExecution()
					.executeSync()
					.getResult();

			//
			// Throw exception in case of failure
			jasperProcessResult.propagateErrorIfAny();
			
			//
			// Update caller process result
			final ProcessExecutionResult callerProcessResult = processInfo.getResult();
			callerProcessResult.setReportData(jasperProcessResult.getReportData(), jasperProcessResult.getReportFilename(), jasperProcessResult.getReportContentType());
		}
		else
		{
			final boolean isPrintPreview = processInfo.isPrintPreview();
			final String printerName = getPrinterName();
			createOutput(re, isPrintPreview, printerName);
			if (!isPrintPreview)
			{
				ReportEngine.printConfirm(reportEngineDocumentType, recordId);
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
		final ReportViewerProvider provider = getDefaultReportEngineViewerProvider();
		provider.openViewer(re);
	}

	public static void setDefaultReportEngineReportViewerProvider(final ReportViewerProvider reportEngineViewerProvider)
	{
		if (reportEngineViewerProvider == null)
		{
			throw new IllegalArgumentException("Cannot set report viewer provider to null");
		}
		defaultReportEngineViewerProvider = reportEngineViewerProvider;
	}

	public static ReportViewerProvider getDefaultReportEngineViewerProvider()
	{
		return defaultReportEngineViewerProvider;
	}
	
	public static final class Builder
	{
		private ProcessInfo processInfo;
		
		private Builder()
		{
			super();
		}

		public ReportCtl start()
		{
			final ReportCtl reportCtl = new ReportCtl(this);
			reportCtl.start();
			return reportCtl;
		}
		
		public Builder setProcessInfo(final ProcessInfo processInfo)
		{
			this.processInfo = processInfo;
			return this;
		}
		
		private ProcessInfo getProcessInfo()
		{
			Check.assumeNotNull(processInfo, "Parameter processInfo is not null");
			return processInfo;
		}
	}

}	// ReportCtl
