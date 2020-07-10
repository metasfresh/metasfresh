/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.print;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.MQuery;
import org.compiere.model.PrintInfo;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.slf4j.Logger;

import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.util.Check;
import de.metas.util.Services;

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
		this.processInfo = builder.getProcessInfo();
	}

	/**
	 * Create Report.
	 */
	private void start()
	{
		logger.info("start - {}", processInfo);

		final int adProcessId = processInfo.getAdProcessId().getRepoId();
		final int reportEngineDocumentType = extractReportEngineDocumentType(adProcessId);

		//
		// Payment Check Print
		if (adProcessId == 313)     		// C_Payment
		{
			throw new UnsupportedOperationException("Not supported");
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
	 * <li>process information (AD_Process.AD_PrintFormat_ID, AD_Process.AD_ReportView_ID)
	 * </ol>
	 *
	 * @param pi Process Info
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
			final MQuery query = MQuery.get(ctx, pi.getPinstanceId(), TableName);
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
		final String TableName = pi.getAdProcessId().getRepoId() == 202 ? "T_Report" : "T_ReportStatement";
		final MQuery query = MQuery.get(ctx, pi.getPinstanceId(), TableName);

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
		final PInstanceId adPInstanceId = processInfo.getPinstanceId();
		final ReportEngine re = ReportEngine.get(ctx, reportEngineDocumentType, recordId, adPInstanceId, ITrx.TRXNAME_None);

		if (re == null)
		{
			ITableRecordReference reference = TableRecordReference.of(adTableId, recordId);

			final List<I_AD_Archive> lastArchive = Services.get(IArchiveDAO.class).retrieveLastArchives(ctx, reference, 1);

			if (lastArchive.isEmpty())
			{
				throw new AdempiereException("@NoDocPrintFormat@@NoArchive@");
			}

			final I_AD_Archive archive = lastArchive.get(0);

			final ProcessExecutionResult callerProcessResult = processInfo.getResult();
			final String fileName = archive.getName();
			callerProcessResult.setReportData(archive.getBinaryData(), fileName, MimeType.getMimeType(fileName));
		}
		else
		{
			final MPrintFormat printFormat = re.getPrintFormat();
			if (printFormat == null)
			{
				throw new AdempiereException("@NoDocPrintFormat@");
			}
			else if (printFormat.getJasperProcess_ID() > 0)
			{
				final ProcessExecutionResult jasperProcessResult = ProcessInfo.builder()
						//
						.setCtx(processInfo.getCtx())
						.setCreateTemporaryCtx()
						.setClientId(processInfo.getClientId())
						.setUserId(processInfo.getUserId())
						.setRoleId(processInfo.getRoleId())
						.setWhereClause(processInfo.getWhereClause())
						.setWindowNo(processInfo.getWindowNo())
						.setTabNo(processInfo.getTabNo())
						.setPrintPreview(processInfo.isPrintPreview())
						//
						.setAD_Process_ID(printFormat.getJasperProcess_ID())
						.setRecord(adTableId, recordId)
						.setReportLanguage(processInfo.getReportLanguage())
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
