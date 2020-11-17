/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.report;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.server.ReportConstants;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

@Service
public class DocumentReportService
{
	private static final Logger logger = LogManager.getLogger(DocumentReportService.class);
	private final DocumentPrintOptionDescriptorsRepository documentPrintOptionDescriptorsRepository;
	private final DocTypePrintOptionsRepository docTypePrintOptionsRepository;

	private final ImmutableMap<StandardDocumentReportType, DocumentReportAdvisor> advisorsByType;
	private final FallbackDocumentReportAdvisor fallbackAdvisor;

	DocumentReportService(
			@NonNull final List<DocumentReportAdvisor> advisors,
			@NonNull final DocumentPrintOptionDescriptorsRepository documentPrintOptionDescriptorsRepository,
			@NonNull final DocTypePrintOptionsRepository docTypePrintOptionsRepository)
	{
		this.advisorsByType = Maps.uniqueIndex(advisors, DocumentReportAdvisor::getStandardDocumentReportType);
		this.fallbackAdvisor = new FallbackDocumentReportAdvisor();
		logger.info("Advisors: {}", advisors);

		this.documentPrintOptionDescriptorsRepository = documentPrintOptionDescriptorsRepository;
		this.docTypePrintOptionsRepository = docTypePrintOptionsRepository;
	}

	private DocumentReportAdvisor getAdvisorByType(@NonNull final StandardDocumentReportType type)
	{
		final DocumentReportAdvisor advisor = advisorsByType.get(type);
		return advisor != null ? advisor : fallbackAdvisor;
	}

	public void createReport(@NonNull final ProcessInfo callerProcessInfo)
	{
		final ReportResultData documentReportResult = createReport(toDocumentReportRequest(callerProcessInfo));

		final ProcessExecutionResult callerProcessResult = callerProcessInfo.getResult();
		callerProcessResult.setReportData(
				documentReportResult.getReportData(),
				documentReportResult.getReportFilename(),
				documentReportResult.getReportContentType());
	}

	private static DocumentReportRequest toDocumentReportRequest(final ProcessInfo processInfo)
	{
		return DocumentReportRequest.builder()
				.callingFromProcessId(processInfo.getAdProcessId())
				.documentRef(processInfo.getRecordRefOrNull())
				.clientId(processInfo.getClientId())
				.orgId(processInfo.getOrgId())
				.userId(processInfo.getUserId())
				.roleId(processInfo.getRoleId())
				.windowNo(processInfo.getWindowNo())
				.tabNo(processInfo.getTabNo())
				.printPreview(processInfo.isPrintPreview())
				.reportLanguage(processInfo.getReportLanguage())
				.build();
	}

	public ReportResultData createStandardDocumentReport(
			@NonNull final Properties ctx,
			@NonNull final StandardDocumentReportType type,
			final int recordId)
	{
		final String tableName = type.getBaseTableName();
		if (tableName == null)
		{
			throw new AdempiereException("Cannot determine table name for " + type);
		}

		final StandardDocumentReportInfo standardDocumentReportInfo = getDocumentReportInfo(
				type,
				recordId,
				null // adPrintFormatToUseId
		);

		final DocumentReportRequest request = DocumentReportRequest.builder()
				.documentRef(TableRecordReference.of(tableName, recordId))
				.clientId(Env.getClientId(ctx))
				.orgId(Env.getOrgId(ctx))
				.userId(Env.getLoggedUserId(ctx))
				.roleId(Env.getLoggedRoleId(ctx))
				.printPreview(true)
				.build();

		return startReportProcess(request, standardDocumentReportInfo.getReportProcessId());
	}

	public ReportResultData createReport(@NonNull final DocumentReportRequest request)
	{
		final AdProcessId callingFromProcessId = request.getCallingFromProcessId();
		final StandardDocumentReportType standardDocumentReportType = StandardDocumentReportType.ofProcessIdOrNull(callingFromProcessId);
		if (standardDocumentReportType != null)
		{
			final StandardDocumentReportInfo reportInfo = getDocumentReportInfo(
					standardDocumentReportType,
					request.getDocumentRef().getRecord_ID(),
					null // adPrintFormatToUseId
			);

			final DocumentPrintOptions printOptions = request.getPrintOptions()
					.mergeWithFallback(reportInfo.getPrintOptions());

			return startReportProcess(
					request.withPrintOptions(printOptions),
					reportInfo.getReportProcessId());
		}
		else if (callingFromProcessId != null)
		{
			// TODO: handle printing options
			return startReportProcess(request, callingFromProcessId);
		}
		else
		{
			throw new AdempiereException("No reporting process found for " + request);
		}
	}

	private ReportResultData startReportProcess(
			@NonNull final DocumentReportRequest request,
			@NonNull final AdProcessId reportProcessId)
	{
		final ProcessExecutionResult reportProcessResult = ProcessInfo.builder()
				//
				.setCtx(Env.getCtx())
				.setCreateTemporaryCtx()
				.setClientId(request.getClientId())
				.setUserId(request.getUserId())
				.setRoleId(request.getRoleId())
				.setWindowNo(request.getWindowNo())
				.setTabNo(request.getTabNo())
				.setPrintPreview(request.isPrintPreview())
				//
				.setAD_Process_ID(reportProcessId)
				.setRecord(request.getDocumentRef())
				.setReportLanguage(request.getReportLanguage())
				.addParameter(ReportConstants.REPORT_PARAM_BARCODE_URL, getBarcodeServlet(request.getClientId(), request.getOrgId()))
				// TODO .addParameter(IPrintService.PARAM_PrintCopies, getPrintInfo().getCopies())
				.addParameters(toProcessInfoParameters(request.getPrintOptions()))
				//
				// Execute Process
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		//
		// Throw exception in case of failure
		reportProcessResult.propagateErrorIfAny();

		return ReportResultData.builder()
				.reportFilename(reportProcessResult.getReportFilename())
				.reportContentType(reportProcessResult.getReportContentType())
				.reportData(reportProcessResult.getReportData())
				.build();
	}

	private static List<ProcessInfoParameter> toProcessInfoParameters(@NonNull final DocumentPrintOptions printOptions)
	{
		if (printOptions.isNone())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<ProcessInfoParameter> result = ImmutableList.builder();
		for (final String optionName : printOptions.getOptionNames())
		{
			final boolean value = printOptions.getOption(optionName).isTrue();
			result.add(ProcessInfoParameter.of(optionName, value));
		}

		return result.build();
	}

	@NonNull
	public StandardDocumentReportInfo getDocumentReportInfo(
			@NonNull final AdProcessId printProcessId,
			@NonNull final TableRecordReference recordRef)
	{
		final StandardDocumentReportType type = StandardDocumentReportType.ofProcessIdOrNull(printProcessId);
		if (type != null)
		{
			return getDocumentReportInfo(type, recordRef.getRecord_ID(), null);
		}
		else
		{
			// TODO implement
			throw new UnsupportedOperationException();
		}
	}

	@NonNull
	public StandardDocumentReportInfo getDocumentReportInfo(
			@NonNull final StandardDocumentReportType type,
			final int recordId,
			@Nullable final PrintFormatId adPrintFormatToUseId)
	{
		final StandardDocumentReportInfo reportInfo = getAdvisorByType(type).getDocumentReportInfo(type, recordId, adPrintFormatToUseId);

		final DocumentPrintOptionDescriptorsList printOptionDescriptors = documentPrintOptionDescriptorsRepository.getPrintingOptionDescriptors(reportInfo.getReportProcessId());

		DocumentPrintOptions printOptions = reportInfo.getPrintOptions();
		if (reportInfo.getDocTypeId() != null)
		{
			final DocumentPrintOptions docTypePrintOptions = docTypePrintOptionsRepository.getByDocTypeId(reportInfo.getDocTypeId());
			printOptions = printOptions.mergeWithFallback(docTypePrintOptions);
		}

		return reportInfo.toBuilder()
				.printOptionsDescriptor(printOptionDescriptors)
				.printOptions(printOptions)
				.build();
	}

	public static String getBarcodeServlet(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		return sysConfigBL.getValue(
				ReportConstants.SYSCONFIG_BarcodeServlet,
				null,  // defaultValue,
				clientId.getRepoId(),
				orgId.getRepoId());
	}
}
