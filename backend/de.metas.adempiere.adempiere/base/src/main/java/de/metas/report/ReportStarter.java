package de.metas.report;

import ch.qos.logback.classic.Level;
import com.google.common.io.Files;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.printing.IMassPrintingService;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.RunOutOfTrx;
import de.metas.report.ExecuteReportStrategy.ExecuteReportResult;
import de.metas.report.server.OutputType;
import de.metas.util.Check;
import de.metas.util.FileUtil;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.archive.api.ArchiveInfo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;

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

public abstract class ReportStarter extends JavaProcess
{
	// services
	private static final Logger logger = LogManager.getLogger(ReportStarter.class);

	private final ITaskExecutorService taskExecutorService = Services.get(ITaskExecutorService.class);
	private final IMassPrintingService printService = SpringContextHolder.instance.getBean(IMassPrintingService.class);

	protected abstract ExecuteReportStrategy getExecuteReportStrategy();

	/**
	 * Start Jasper reporting process. Based on {@link ProcessInfo#isPrintPreview()}, it will:
	 * <ul>
	 * <li>directly print the report
	 * <li>will open the report viewer and it will display the report
	 * </ul>
	 */
	@Override
	@RunOutOfTrx // IMPORTANT: run out of trx because in case we are creating some T_Selection, and then we want to call the jasper server, we need that selection to be commited
	protected final String doIt()
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
				startProcess0(reportPrintingInfo);
			}
			else
			{
				// task 08283: direct print can be done in background; no need to let the user wait for this
				taskExecutorService.submit(
						() -> startProcess0(reportPrintingInfo),
						ReportStarter.class.getSimpleName());
			}
		}
		return MSG_OK;
	}

	private void startProcessDirectPrint(@NonNull final ReportPrintingInfo reportPrintingInfo)
	{
		final ProcessInfo pi = reportPrintingInfo.getProcessInfo();

		final ExecuteReportResult result = getExecuteReportStrategy().executeReport(pi, OutputType.PDF);

		printService.print(result.getReportData(), extractArchiveInfo(pi));
	}

	private static ArchiveInfo extractArchiveInfo(@NonNull final ProcessInfo pi)
	{
		// make sure that we never have zero copies. Apparently metasfresh
		// thinks of "copies" as the number of printouts _additional_ to the
		// original document while the java printing API thinks of copies as
		// the absolute number of printouts and thus doesn't accept any
		// number <=0.
		PrintCopies numberOfPrintouts = PrintCopies.ONE;
		ArchiveInfo archiveInfo = null;

		if (pi.getParameter() != null)
		{
			for (final ProcessInfoParameter param : pi.getParameter())
			{
				final String parameterName = param.getParameterName();
				final Object objParam = param.getParameter();

				if (objParam == null)
				{
					continue;
				}

				if (objParam instanceof ArchiveInfo)
				{
					archiveInfo = (ArchiveInfo)objParam;
					numberOfPrintouts = archiveInfo.getCopies();

					if (numberOfPrintouts.isZero())
					{
						logger.debug("Setting numberOfPrintouts from 0 (specified by archiveInfo) to 1");
						numberOfPrintouts = PrintCopies.ONE;
					}
					break;
				}
				else if (IMassPrintingService.PARAM_PrintCopies.equals(parameterName))
				{
					numberOfPrintouts = PrintCopies.ofIntOrOne(param.getParameterAsInt());
				}
			}
		}

		//
		// Do a copy of found print info, or create a new one
		if (archiveInfo == null)
		{
			archiveInfo = new ArchiveInfo(pi);
		}
		else
		{
			archiveInfo = archiveInfo.copy();
		}

		// Update archiveInfo
		archiveInfo.setCopies(numberOfPrintouts.minimumOne());

		return archiveInfo;
	}

	private void startProcessInvokeReportOnly(@NonNull final ReportPrintingInfo reportPrintingInfo)
	{
		final ProcessInfo processInfo = reportPrintingInfo.getProcessInfo();

		final OutputType desiredOutputType;
		if (reportPrintingInfo.isPrintPreview())
		{
			desiredOutputType = null;
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
				outputType = CoalesceUtil.coalesceNotNull(
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
		if (Check.isNotBlank(result.getFilename()) && !isDocument(processInfo)) // in case of documents we use DocumentInfo as filename
		{
			reportFilename = result.getFilename();
			logger.debug("executeReport's result has a non-blank filename={}; -> use it for the exported file", reportFilename);
		}
		else
		{
			reportFilename = extractReportFilename(processInfo, outputType);
			logger.debug("executeReport's result has a blank filename or the record is a document; -> use extracted filename={} for the exported file", reportFilename);
		}

		processExecutionResult.setReportData(result.getReportData(), reportFilename, reportContentType);
	}

	private String extractReportFilename(final ProcessInfo pi, final OutputType outputType)
	{
		final String fileBasename = CoalesceUtil.firstValidValue(
				basename -> !Check.isEmpty(basename, true),
				() -> extractReportBasename_IfDocument(pi),
				pi::getTitle,
				() -> "report_" + PInstanceId.toRepoIdOr(pi.getPinstanceId(), 0));

		final String fileExtension = outputType.getFileExtension();

		final String filename = fileBasename.trim() + "." + fileExtension;
		return FileUtil.stripIllegalCharacters(filename);
	}

	@Nullable
	private String extractReportBasename_IfDocument(@NonNull final ProcessInfo pi)
	{
		return Optional.ofNullable(getDocument(pi))
				.map(IDocument::getDocumentInfo)
				.orElse(null);
	}

	private static boolean isDocument(@NonNull final ProcessInfo pi)
	{
		return Optional.ofNullable(getDocument(pi))
				.isPresent();
	}

	@Nullable
	private static IDocument getDocument(@NonNull final ProcessInfo pi)
	{
		final TableRecordReference recordRef = pi.getRecordRefOrNull();
		if (recordRef == null)
		{
			return null;
		}

		final Object record = recordRef.getModel();

		final IDocumentBL documentBL = Services.get(IDocumentBL.class);
		return documentBL.getDocumentOrNull(record);
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

	private void startProcess0(final ReportPrintingInfo reportPrintingInfo)
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
	private static class ReportPrintingInfo
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
