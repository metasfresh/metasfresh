package de.metas.report;

import ch.qos.logback.classic.Level;
import com.google.common.io.Files;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.print.IPrintService;
import de.metas.print.IPrintServiceRegistry;
import de.metas.process.ClientOnlyProcess;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy.ExecuteReportResult;
import de.metas.report.server.OutputType;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.print.JRReportViewerProvider;
import org.compiere.util.Ini;
import org.slf4j.Logger;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@ClientOnlyProcess
public abstract class ReportStarter extends JavaProcess
{
	// services
	private static final Logger logger = LogManager.getLogger(ReportStarter.class);

	private static JRReportViewerProvider swingJRReportViewerProvider;

	private static JRReportViewerProvider viewerProvider = null;

	private final transient ITaskExecutorService taskExecutorService = Services.get(ITaskExecutorService.class);
	private final transient IPrintServiceRegistry printServiceRegistry = Services.get(IPrintServiceRegistry.class);

	protected abstract ExecuteReportStrategy getExecuteReportStrategy();

	/**
	 * Start Jasper reporting process. Based on {@link ProcessInfo#isPrintPreview()}, it will:
	 * <ul>
	 * <li>directly print the report
	 * <li>will open the report viewer and it will display the report
	 * </ul>
	 */
	@Override
	protected final String doIt() throws Exception
	{
		final ProcessInfo pi = getProcessInfo();

		final ReportPrintingInfo reportPrintingInfo = extractReportPrintingInfo(pi);

		final boolean doNotInvokeMassPrintEngine = reportPrintingInfo.isPrintPreview() || !reportPrintingInfo.isArchiveReportData();
		if (doNotInvokeMassPrintEngine)
		{
			// Create report and preview / not-archive
			startProcessInvokeReportOnly(reportPrintingInfo);
		}
		else
		{
			// Create report and print it directly
			if (reportPrintingInfo.isForceSync())
			{
				// gh #1160 if the caller want you to execute synchronously, then do just that
				startProcess0(pi, reportPrintingInfo);
			}
			else
			{
				// task 08283: direct print can be done in background; no need to let the user wait for this
				taskExecutorService.submit(
						() -> startProcess0(pi, reportPrintingInfo),
						ReportStarter.class.getSimpleName());
			}
		}
		return MSG_OK;
	}

	/**
	 * Set jasper report viewer provider.
	 */
	public static void setNonSwingViewerProvider(@NonNull final JRReportViewerProvider provider)
	{
		viewerProvider = provider;
	}

	public static void setSwingViewerProvider(@NonNull final JRReportViewerProvider provider)
	{
		swingJRReportViewerProvider = provider;
	}

	private void startProcessDirectPrint(@NonNull final ReportPrintingInfo reportPrintingInfo)
	{
		final ProcessInfo pi = reportPrintingInfo.getProcessInfo();

		final ExecuteReportResult result = getExecuteReportStrategy().executeReport(pi, OutputType.PDF);

		final IPrintService printService = printServiceRegistry.getPrintService();
		printService.print(result, pi);
	}

	private void startProcessInvokeReportOnly(@NonNull final ReportPrintingInfo reportPrintingInfo) throws Exception
	{
		final ProcessInfo processInfo = reportPrintingInfo.getProcessInfo();

		final OutputType desiredOutputType;
		if (reportPrintingInfo.isPrintPreview())
		{
			// Get the jasper report viewer provider and ask it what format it wants
			final JRReportViewerProvider jrReportViewerProvider = getJRReportViewerProviderOrNull();
			desiredOutputType = jrReportViewerProvider == null ? null : jrReportViewerProvider.getDesiredOutputType();
		}
		else
		{
			desiredOutputType = OutputType.PDF;
		}

		//
		// Based on reporting system type, determine: output type
		final ReportingSystemType reportingSystemType = reportPrintingInfo.getReportingSystemType();
		final OutputType outputType;
		switch (reportingSystemType)
		{
			//
			// Jasper reporting
			case Jasper:
			case Other:
				outputType = CoalesceUtil.coalesce(
						// needs to take precedence because we might be invoked for an outer "preview" process, but with isPrintPreview()=false
						processInfo.getJRDesiredOutputType(),
						desiredOutputType,
						OutputType.PDF);
				break;

			//
			// Excel reporting
			case Excel:
				outputType = OutputType.XLS;
				break;

			default:
				throw new AdempiereException("Unknown " + ReportingSystemType.class + ": " + reportingSystemType);
		}

		//
		// Generate report data
		Loggables.withLogger(logger, Level.DEBUG).addLog("ReportStarter.startProcess run report: reportingSystemType={}, title={}, outputType={}", reportingSystemType, processInfo.getTitle(), outputType);
		final ExecuteReportResult result = getExecuteReportStrategy().executeReport(getProcessInfo(), outputType);

		//
		// Set report data to process execution result
		final ProcessExecutionResult processExecutionResult = processInfo.getResult();

		final String reportContentType = outputType.getContentType();

		final String reportFilename;
		if (Check.isNotBlank(result.getFilename()))
		{
			reportFilename = result.getFilename();
			logger.debug("executeReport's result has a non-blank filename={}; -> use it for the exported file", reportFilename);
		}
		else
		{
			reportFilename = extractReportFilename(processInfo, outputType);
			logger.debug("executeReport's result has a blank filename; -> use generic filename={} for the exported file", result.getFilename());
		}

		processExecutionResult.setReportData(result.getReportData(), reportFilename, reportContentType);

		//
		// Print preview (if swing client)
		if (reportPrintingInfo.isPrintPreview()
				&& Ini.isSwingClient()
				&& swingJRReportViewerProvider != null)
		{
			swingJRReportViewerProvider.openViewer(result.getReportData(), outputType, processInfo);
		}
	}

	private static final String extractReportFilename(final ProcessInfo pi, final OutputType outputType)
	{
		final String fileBasename = CoalesceUtil.firstValidValue(
				basename -> !Check.isEmpty(basename, true),
				() -> extractReportBasename_IfDocument(pi),
				() -> pi.getTitle(),
				() -> "report_" + PInstanceId.toRepoIdOr(pi.getPinstanceId(), 0));

		final String fileExtension = outputType.getFileExtension();

		final String filename = fileBasename.trim() + "." + fileExtension;
		return FileUtil.stripIllegalCharacters(filename);
	}

	private static String extractReportBasename_IfDocument(final ProcessInfo pi)
	{
		final TableRecordReference recordRef = pi.getRecordRefOrNull();
		if (recordRef == null)
		{
			return null;
		}

		final Object record = recordRef.getModel();

		final IDocumentBL documentBL = Services.get(IDocumentBL.class);
		final IDocument document = documentBL.getDocumentOrNull(record);
		if (document == null)
		{
			return null;
		}

		return document.getDocumentInfo();
	}

	private ReportPrintingInfo extractReportPrintingInfo(@NonNull final ProcessInfo pi)
	{
		final ReportPrintingInfo.ReportPrintingInfoBuilder info = ReportPrintingInfo
				.builder()
				.processInfo(pi)
				.printPreview(pi.isPrintPreview())
				.archiveReportData(pi.isArchiveReportData())
				.forceSync(!pi.isAsync()); // gh #1160 if the process info says "sync", then sync it is

		//
		// Determine the ReportingSystem type based on report template file extension
		// TODO: make it more general and centralized with the other reporting code
		final String reportFileExtension = pi
				.getReportTemplate()
				.map(reportTemplate -> Files.getFileExtension(reportTemplate).toLowerCase())
				.orElse(null);

		if ("jasper".equalsIgnoreCase(reportFileExtension)
				|| "jrxml".equalsIgnoreCase(reportFileExtension))
		{
			info.reportingSystemType(ReportingSystemType.Jasper);
		}
		else if ("xls".equalsIgnoreCase(reportFileExtension))
		{
			info.reportingSystemType(ReportingSystemType.Excel);
			info.printPreview(true); // TODO: atm only print preview is supported
		}
		else
		{
			info.reportingSystemType(ReportingSystemType.Other);
		}
		return info.build();
	}

	/**
	 * @return {@link JRReportViewerProvider} or null
	 */
	private JRReportViewerProvider getJRReportViewerProviderOrNull()
	{
		if (Ini.isSwingClient())
		{
			return swingJRReportViewerProvider;
		}
		else
		{
			return viewerProvider;
		}
	}

	private void startProcess0(final ProcessInfo pi, final ReportPrintingInfo reportPrintingInfo)
	{
		try
		{
			logger.info("Doing direct print without preview: {}", reportPrintingInfo);
			startProcessDirectPrint(reportPrintingInfo);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private enum ReportingSystemType
	{
		Jasper,

		Excel,

		/**
		 * May be used when no invocation to the jasper service is done
		 */
		Other
	}

	@Value
	@Builder
	private static final class ReportPrintingInfo
	{
		ProcessInfo processInfo;

		ReportingSystemType reportingSystemType;

		boolean printPreview;

		boolean archiveReportData;

		/**
		 * Even if {@link #isPrintPreview()} is {@code false}, we do <b>not</b> print in a background thread, if this is false.
		 */
		@Default
		boolean forceSync = false;
	}
}
