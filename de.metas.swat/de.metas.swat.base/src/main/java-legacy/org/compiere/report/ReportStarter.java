/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *                      *
 *****************************************************************************/
package org.compiere.report;

import java.util.Properties;

import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.print.JRReportViewerProvider;
import org.compiere.report.IJasperServiceRegistry.ServiceType;
import org.compiere.util.Ini;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.io.Files;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.client.JRClient;
import de.metas.logging.LogManager;
import de.metas.process.ClientProcess;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessCall;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Jasper Report Process: this process is used on all AD_Processes which are about creating jasper reports.
 * 
 * @author rlemeill originaly coming from an application note from compiere.co.uk --- Modifications: Allow Jasper
 *         Reports to be able to be run on VPN profile (i.e: no direct connection to DB). Implemented ClientProcess for
 *         it to run on client.
 * @author Ashley Ramdass
 * @author tsa
 */
public class ReportStarter implements ProcessCall, ClientProcess
{
	// services
	private static final Logger log = LogManager.getLogger(ReportStarter.class);
	private static JRReportViewerProvider swingJRReportViewerProvider = new SwingJRViewerProvider();

	/**
	 * Start Jasper reporting process. Based on {@link ProcessInfo#isPrintPreview()}, it will:
	 * <ul>
	 * <li>directly print the report
	 * <li>will open the report viewer and it will display the report
	 * </ul>
	 */
	@Override
	public final void startProcess(final ProcessInfo pi, final ITrx trx) throws Exception
	{
		final ReportPrintingInfo reportPrintingInfo = extractReportPrintingInfo(pi);

		//
		// Create report and print it directly
		if (!reportPrintingInfo.isPrintPreview())
		{
			// task 08283: direct print can be done in background; no need to let the user wait for this
			Services.get(ITaskExecutorService.class).submit(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						log.info("Doing direct print without preview: {}", reportPrintingInfo);
						startProcessDirectPrint(reportPrintingInfo);
					}
					catch (final Exception e)
					{
						final ProcessExecutionResult result = pi.getResult();
						result.markAsError(e);
						Services.get(IADPInstanceDAO.class).unlockAndSaveResult(pi.getCtx(), result);
						Services.get(IClientUI.class).warn(pi.getWindowNo(), e);
					}
				}
			},
					ReportStarter.class.getSimpleName());
		}
		//
		// Create report and preview
		else
		{
			startProcessPrintPreview(reportPrintingInfo);
		}
	}

	private void startProcessDirectPrint(final ReportPrintingInfo reportPrintingInfo)
	{
		final Properties ctx = reportPrintingInfo.getCtx();
		final ProcessInfo pi = reportPrintingInfo.getProcessInfo();
		final JRClient jrClient = JRClient.get();
		final JasperPrint jasperPrint = jrClient.createJasperPrint(ctx, pi);
		log.info("ReportStarter.startProcess print report: {}", jasperPrint.getName());

		//
		// 08284: if we work without preview, use the mass printing framework
		final IJasperServiceRegistry jasperServiceFactory = Services.get(IJasperServiceRegistry.class);
		final IJasperService jasperService = jasperServiceFactory.getJasperService(ServiceType.MASS_PRINT_FRAMEWORK);
		final boolean displayPrintDialog = false;
		jasperService.print(jasperPrint, pi, displayPrintDialog);
	}

	private void startProcessPrintPreview(final ReportPrintingInfo reportPrintingInfo) throws Exception
	{
		final Properties ctx = reportPrintingInfo.getCtx();
		final ProcessInfo processInfo = reportPrintingInfo.getProcessInfo();

		//
		// Get Jasper report viewer provider
		final JRReportViewerProvider jrReportViewerProvider = getJRReportViewerProviderOrNull();
		final OutputType desiredOutputType = jrReportViewerProvider == null ? null : jrReportViewerProvider.getDesiredOutputType();

		//
		// Based on reporting system type, determine: output type
		final ReportingSystemType reportingSystemType = reportPrintingInfo.getReportingSystemType();
		final OutputType outputType;
		switch (reportingSystemType)
		{
			//
			// Jasper reporting
			case Jasper:
				outputType = Util.coalesce(desiredOutputType, processInfo.getJRDesiredOutputType(), OutputType.PDF);
				break;

			//
			// Excel reporting
			case Excel:
				outputType = OutputType.XLS;

			default:
				throw new AdempiereException("Unknown " + ReportingSystemType.class + ": " + reportingSystemType);
		}

		//
		// Generate report data
		log.info("ReportStarter.startProcess run report: reportingSystemType={}, title={}, outputType={}", reportingSystemType, processInfo.getTitle(), outputType);
		final JRClient jrClient = JRClient.get();
		final byte[] reportData = jrClient.report(ctx, processInfo, outputType);

		//
		// Set report data to process execution result
		final ProcessExecutionResult processExecutionResult = processInfo.getResult();
		final String reportFilename = "report." + outputType.getFileExtension(); // TODO: find a better name
		final String reportContentType = outputType.getContentType();
		processExecutionResult.setReportData(reportData, reportFilename, reportContentType);

		//
		// Print preview (if swing client)
		if (Ini.isClient() && swingJRReportViewerProvider != null)
		{
			swingJRReportViewerProvider.openViewer(reportData, outputType, processInfo);
		}
	}

	private ReportPrintingInfo extractReportPrintingInfo(final ProcessInfo pi)
	{
		final ReportPrintingInfo info = new ReportPrintingInfo();
		info.setCtx(pi.getCtx());
		info.setProcessInfo(pi);
		info.setPrintPreview(pi.isPrintPreview());

		//
		// Determine the ReportingSystem type based on report template file extension
		// TODO: make it more general and centralized with the other reporting code
		final String reportTemplate = pi.getReportTemplate().orElseThrow(() -> new AdempiereException("No report template defined for " + pi));
		final String reportFileExtension = Files.getFileExtension(reportTemplate).toLowerCase();
		if ("jasper".equalsIgnoreCase(reportFileExtension)
				|| "jrxml".equalsIgnoreCase(reportFileExtension))
		{
			info.setReportingSystemType(ReportingSystemType.Jasper);
		}
		else if ("xls".equalsIgnoreCase(reportFileExtension))
		{
			info.setReportingSystemType(ReportingSystemType.Excel);
			info.setPrintPreview(true); // TODO: atm only print preview is supported
		}

		return info;
	}

	/**
	 * 
	 * @return {@link JRReportViewerProvider} or null
	 */
	private JRReportViewerProvider getJRReportViewerProviderOrNull()
	{
		if (Ini.isClient())
		{
			return swingJRReportViewerProvider;
		}
		else
		{
			return null;
		}
	}

	private static enum ReportingSystemType
	{
		Jasper, Excel,
	};

	private static final class ReportPrintingInfo
	{
		private Properties ctx;
		private ProcessInfo processInfo;
		private ReportingSystemType reportingSystemType;
		private boolean printPreview;
		private JRReportViewerProvider reportViewerProvider;

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("reportingSystemType", reportingSystemType)
					.add("printPreview", printPreview)
					.add("processInfo", processInfo)
					.add("reportViewerProvider", reportViewerProvider)
					.toString();
		}

		public void setCtx(final Properties ctx)
		{
			this.ctx = ctx;
		}

		public Properties getCtx()
		{
			return ctx;
		}

		public void setProcessInfo(final ProcessInfo processInfo)
		{
			this.processInfo = processInfo;
		}

		public ProcessInfo getProcessInfo()
		{
			return processInfo;
		}

		public void setReportingSystemType(final ReportingSystemType reportingSystemType)
		{
			this.reportingSystemType = reportingSystemType;
		}

		public ReportingSystemType getReportingSystemType()
		{
			return reportingSystemType;
		}

		public void setPrintPreview(final boolean printPreview)
		{
			this.printPreview = printPreview;
		}

		public boolean isPrintPreview()
		{
			return printPreview;
		}
	}
}
