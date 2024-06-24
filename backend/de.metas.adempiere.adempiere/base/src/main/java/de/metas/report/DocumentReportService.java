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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.Profiles;
import de.metas.document.DocTypeId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.printing.IMassPrintingService;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.server.ReportConstants;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

@Service
@Profile(Profiles.PROFILE_NOT_ReportService_Standalone)
public class DocumentReportService
{
	private static final Logger logger = LogManager.getLogger(DocumentReportService.class);
	private final DocumentPrintOptionDescriptorsRepository documentPrintOptionDescriptorsRepository;
	private final DocTypePrintOptionsRepository docTypePrintOptionsRepository;
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	private final ImmutableMap<StandardDocumentReportType, DocumentReportAdvisor> advisorsByType;
	private final ImmutableMap<String, DocumentReportAdvisor> advisorsByTableName;
	private final FallbackDocumentReportAdvisor fallbackAdvisor;
	private final DocumentReportAdvisorUtil util;

	public DocumentReportService(
			@NonNull final List<DocumentReportAdvisor> advisors,
			@NonNull final DocumentPrintOptionDescriptorsRepository documentPrintOptionDescriptorsRepository,
			@NonNull final DocTypePrintOptionsRepository docTypePrintOptionsRepository,
			@NonNull final DocumentReportAdvisorUtil util)
	{
		this.advisorsByType = Maps.uniqueIndex(advisors, DocumentReportAdvisor::getStandardDocumentReportType);
		this.advisorsByTableName = Maps.uniqueIndex(advisors, DocumentReportAdvisor::getHandledTableName);
		this.fallbackAdvisor = new FallbackDocumentReportAdvisor(util);
		logger.info("Advisors: {}", advisors);

		this.documentPrintOptionDescriptorsRepository = documentPrintOptionDescriptorsRepository;
		this.docTypePrintOptionsRepository = docTypePrintOptionsRepository;
		this.util = util;
	}

	@NonNull
	private DocumentReportAdvisor getAdvisorByType(@NonNull final StandardDocumentReportType type)
	{
		final DocumentReportAdvisor advisor = advisorsByType.get(type);
		return advisor != null ? advisor : fallbackAdvisor;
	}

	@NonNull
	private DocumentReportAdvisor getAdvisorByTableName(@NonNull final String tableName)
	{
		final DocumentReportAdvisor advisor = advisorsByTableName.get(tableName);
		return advisor != null ? advisor : fallbackAdvisor;
	}

	@Nullable
	@Deprecated
	public ReportResultData createStandardDocumentReportData(
			@NonNull final Properties ctx,
			@NonNull final StandardDocumentReportType type,
			final int recordId)
	{
		final DocumentReportResult result = createReport(DocumentReportRequest.builder()
				.flavor(DocumentReportFlavor.PRINT)
				.documentRef(TableRecordReference.of(type.getBaseTableName(), recordId))
				.clientId(Env.getClientId(ctx))
				.orgId(Env.getOrgId(ctx))
				.userId(Env.getLoggedUserId(ctx))
				.roleId(Env.getLoggedRoleId(ctx))
				.printPreview(true)
				.build());

		return result.getReportResultData();
	}

	public DocumentReportResult createReport(@NonNull final DocumentReportRequest request)
	{
		final StandardDocumentReportType standardDocumentReportType = computeStandardDocumentReportTypeOrNull(request);

		final DocumentReportAdvisor advisor;
		DocumentReportRequest requestEffective = request;
		if (standardDocumentReportType != null)
		{
			advisor = getAdvisorByType(standardDocumentReportType);
		}
		else if (requestEffective.getReportProcessId() != null)
		{
			advisor = getAdvisorByTableName(requestEffective.getDocumentRef().getTableName());
		}
		else if (requestEffective.getPrintFormatIdToUse() != null)
		{
			final AdProcessId reportProcessId = util.getReportProcessIdByPrintFormatId(requestEffective.getPrintFormatIdToUse());
			requestEffective = requestEffective.withReportProcessId(reportProcessId);

			advisor = getAdvisorByTableName(requestEffective.getDocumentRef().getTableName());
		}
		else
		{
			final org.compiere.model.I_AD_Archive lastArchiveRecord = archiveBL
					.getLastArchive(requestEffective.getDocumentRef())
					.orElseThrow(() -> new AdempiereException("@NoDocPrintFormat@@NoArchive@"));

			final ArchiveResult lastArchive = ArchiveResult.builder()
					.archiveRecord(lastArchiveRecord)
					.data(archiveBL.getBinaryData(lastArchiveRecord))
					.build();

			return DocumentReportResult.builder()
					.lastArchive(lastArchive)
					.build();
		}

		//
		final DocumentReportInfo reportInfo = getDocumentReportInfo(
				advisor,
				requestEffective.getDocumentRef(),
				requestEffective.getPrintFormatIdToUse(),
				requestEffective.getReportProcessId(),
				requestEffective.getFlavor());

		requestEffective = requestEffective
				.withPrintCopies(reportInfo.getCopies())
				.withPrintOptionsFallback(reportInfo.getPrintOptions())
				.withPrintOptionsFallback(documentPrintOptionDescriptorsRepository.getPrintingOptionDescriptors(reportInfo.getReportProcessId()).getDefaults())
				.withReportProcessId(reportInfo.getReportProcessId())
				.withReportLanguage(reportInfo.getLanguage());

		//
		final DocumentReportResult report = executeReportProcessAndComputeResult(requestEffective);
		return report.withBpartnerId(reportInfo.getBpartnerId());
	}

	@Nullable
	private static StandardDocumentReportType computeStandardDocumentReportTypeOrNull(@NonNull final DocumentReportRequest request)
	{
		if (request.getReportProcessId() != null)
		{
			final StandardDocumentReportType standardDocumentReportType = StandardDocumentReportType.ofProcessIdOrNull(request.getReportProcessId());
			if (standardDocumentReportType != null)
			{
				return standardDocumentReportType;
			}
		}

		return StandardDocumentReportType.ofTableNameOrNull(request.getDocumentRef().getTableName());
	}

	@NonNull
	private DocumentReportResult executeReportProcessAndComputeResult(@NonNull final DocumentReportRequest request)
	{
		final ExecuteReportProcessResult processResult = executeReportProcess(request);

		return DocumentReportResult.builder()
				.flavor(request.getFlavor())
				.reportResultData(processResult.getReportData())
				.reportPInstanceId(processResult.getReportPInstanceId())
				.asyncBatchId(request.getAsyncBatchId())
				//
				.documentRef(request.getDocumentRef())
				.reportProcessId(request.getReportProcessId())
				.language(request.getReportLanguage())
				.copies(request.getPrintCopies())
				//
				.build();
	}

	@Value
	@Builder
	@VisibleForTesting
	protected static class ExecuteReportProcessResult
	{
		@Nullable
		ReportResultData reportData;

		@NonNull
		PInstanceId reportPInstanceId;
	}

	@VisibleForTesting
	protected ExecuteReportProcessResult executeReportProcess(@NonNull final DocumentReportRequest request)
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
				.setAD_Process_ID(request.getReportProcessId())
				.setRecord(request.getDocumentRef())
				.setReportLanguage(request.getReportLanguage())
				.addParameter(ReportConstants.REPORT_PARAM_BARCODE_URL, getBarcodeServlet(request.getClientId(), request.getOrgId()))
				.addParameter(IMassPrintingService.PARAM_PrintCopies, request.getPrintCopies().toInt())
				.addParameters(toProcessInfoParameters(request.getPrintOptions()))
				//
				// Execute Process
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		//
		// Throw exception in case of failure
		reportProcessResult.propagateErrorIfAny();

		return ExecuteReportProcessResult.builder()
				.reportData(reportProcessResult.getReportData())
				.reportPInstanceId(reportProcessResult.getPinstanceId())
				.build();
	}

	private static List<ProcessInfoParameter> toProcessInfoParameters(@NonNull final DocumentPrintOptions printOptions)
	{
		if (printOptions.isEmpty())
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

	public DocumentPrintOptionsIncludingDescriptors getDocumentPrintOptionsIncludingDescriptors(
			@NonNull final AdProcessId reportProcessId,
			@NonNull final TableRecordReference recordRef,
			@NonNull final DocumentReportFlavor flavor)
	{
		final StandardDocumentReportType type = StandardDocumentReportType.ofProcessIdOrNull(reportProcessId);
		if (type != null)
		{
			final DocumentReportAdvisor advisor = getAdvisorByType(type);
			final DocumentReportInfo reportInfo = getDocumentReportInfo(advisor, recordRef, null, null, flavor);

			final DocumentPrintOptionDescriptorsList printOptionDescriptors = documentPrintOptionDescriptorsRepository.getPrintingOptionDescriptors(reportInfo.getReportProcessId());
			final DocumentPrintOptions printOptions = reportInfo.getPrintOptions()
					.mergeWithFallback(printOptionDescriptors.getDefaults());

			return DocumentPrintOptionsIncludingDescriptors.builder()
					.descriptors(printOptionDescriptors)
					.values(printOptions)
					.build();
		}
		else
		{
			final DocumentPrintOptionDescriptorsList printOptionDescriptors = documentPrintOptionDescriptorsRepository.getPrintingOptionDescriptors(reportProcessId);
			return DocumentPrintOptionsIncludingDescriptors.builder()
					.descriptors(printOptionDescriptors)
					.values(printOptionDescriptors.getDefaults())
					.build();
		}
	}

	private DocumentReportInfo getDocumentReportInfo(
			@NonNull final DocumentReportAdvisor advisor,
			@NonNull final TableRecordReference recordRef,
			@Nullable final PrintFormatId printFormatIdToUse,
			@Nullable final AdProcessId reportProcessId,
			@NonNull final DocumentReportFlavor flavor)
	{
		final DocumentReportInfo reportInfo = advisor.getDocumentReportInfo(recordRef, printFormatIdToUse, reportProcessId);

		return reportInfo.withPrintOptionsFallback(getDocTypePrintOptions(reportInfo.getDocTypeId(), flavor));
	}

	private DocumentPrintOptions getDocTypePrintOptions(
			@Nullable final DocTypeId docTypeId,
			@Nullable final DocumentReportFlavor flavor)
	{
		return docTypeId != null && flavor != null
				? docTypePrintOptionsRepository.getByDocTypeAndFlavor(docTypeId, flavor)
				: DocumentPrintOptions.NONE;
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
