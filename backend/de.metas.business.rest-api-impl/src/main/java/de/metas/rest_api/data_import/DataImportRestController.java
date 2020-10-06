package de.metas.rest_api.data_import;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.impexp.ActualImportRecordsResult;
import de.metas.impexp.AsyncImportRecordsResponse;
import de.metas.impexp.DataImportRequest;
import de.metas.impexp.DataImportResult;
import de.metas.impexp.DataImportService;
import de.metas.impexp.InsertIntoImportTableResult;
import de.metas.impexp.ValidateImportRecordsResult;
import de.metas.impexp.config.DataImportConfig;
import de.metas.logging.LogManager;
import de.metas.rest_api.data_import.JsonDataImportResponse.JsonActualImport;
import de.metas.rest_api.data_import.JsonDataImportResponse.JsonActualImportAsync;
import de.metas.rest_api.data_import.JsonDataImportResponse.JsonImportRecordsValidation;
import de.metas.rest_api.data_import.JsonDataImportResponse.JsonInsertIntoImportTable;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.Trace;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

@RequestMapping(MetasfreshRestAPIConstants.ENDPOINT_API + "/import")
@RestController
@Profile(Profiles.PROFILE_App)
public class DataImportRestController
{
	private static final Logger logger = LogManager.getLogger(DataImportRestController.class);
	private final DataImportService dataImportService;

	private static final String ERROR_PARAM_WHERE = "where";

	public DataImportRestController(@NonNull final DataImportService dataImportService)
	{
		this.dataImportService = dataImportService;
	}

	@ApiOperation("Uploads a text file. Using this endpoint is technically simpler for clients of this API than to upload a multipart file via the other endpoint.")
	@PostMapping("/text")
	public ResponseEntity<JsonDataImportResponseWrapper> importFile(
			@ApiParam("Data Import internal name (i.e. `C_DataImport.InternalName`)") //
			@RequestParam("dataImportConfig") @NonNull final String dataImportConfigInternalName,

			@ApiParam("Try to complete documents if it applies to the given import") //
			@RequestParam(name = "completeDocuments", required = false, defaultValue = "true") final boolean completeDocuments,

			@ApiParam("If true the data import will run synchronously") //
			@RequestParam(name = "runSynchronous", required = false, defaultValue = "false") final boolean runSynchronous,

			@ApiParam("The text file you are importing") //
			@RequestBody @NonNull final String content)
	{
		final Resource data = new ByteArrayResource(content.getBytes(StandardCharsets.UTF_8));
		return importFile(dataImportConfigInternalName, completeDocuments, data, runSynchronous);
	}

	@PostMapping
	public ResponseEntity<JsonDataImportResponseWrapper> importFile(
			@ApiParam("Data Import internal name (i.e. `C_DataImport.InternalName`)") //
			@RequestParam("dataImportConfig") @NonNull final String dataImportConfigInternalName,

			@ApiParam("Try to complete documents if it applies to the given import") //
			@RequestParam(name = "completeDocuments", required = false, defaultValue = "true") final boolean completeDocuments,

			@ApiParam("If true the data import will run synchronously") //
			@RequestParam(name = "runSynchronous", required = false, defaultValue = "false") final boolean runSynchronous,

			@ApiParam("The text file you are importing") //
			@RequestParam("file") @NonNull final MultipartFile file)
	{
		final Resource data = toResource(file);
		return importFile(dataImportConfigInternalName, completeDocuments, data, runSynchronous);
	}

	private ResponseEntity<JsonDataImportResponseWrapper> importFile(
			@NonNull final String dataImportConfigInternalName,
			final boolean completeDocuments,
			@NonNull final Resource data,
			final boolean runSynchronous)
	{
		try
		{
			final DataImportConfig dataImportConfig = dataImportService.getDataImportConfigByInternalName(dataImportConfigInternalName)
					.orElseThrow(() -> new AdempiereException("No data import configuration found for: " + dataImportConfigInternalName));

			final DataImportResult result = dataImportService.importDataFromResource(DataImportRequest.builder()
					.data(data)
					.dataImportConfigId(dataImportConfig.getId())
					.clientId(Env.getClientId())
					.orgId(Env.getOrgId()) // in case of I_Inventory, the AD_Org_ID might be overwritten with the respective M_Warehouse's AD_Org_ID
					.userId(Env.getLoggedUserId())
					.completeDocuments(completeDocuments)
					.processImportRecordsSynchronously(runSynchronous)
					.stopOnFirstError(true)
					.build());

			return toHttpResponse(result);
		}
		catch (final Exception ex)
		{
			logger.warn("Got error", ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.badRequest()
					.body(JsonDataImportResponseWrapper.error(ex, adLanguage));
		}
	}

	private static Resource toResource(final MultipartFile file)
	{
		final String filename = file.getOriginalFilename();

		final byte[] data;
		try
		{
			data = file.getBytes();
		}
		catch (final IOException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}

		return new ByteArrayResource(data, filename);
	}

	private static ResponseEntity<JsonDataImportResponseWrapper> toHttpResponse(final DataImportResult result)
	{
		final JsonDataImportResponse jsonResult = toJson(result);
		if (result.hasErrors())
		{
			return ResponseEntity.badRequest()
					.body(JsonDataImportResponseWrapper.error(jsonResult));
		}
		else
		{
			return ResponseEntity.ok()
					.body(JsonDataImportResponseWrapper.ok(jsonResult));
		}
	}

	private static JsonDataImportResponse toJson(@NonNull final DataImportResult result)
	{
		return JsonDataImportResponse.builder()
				.insertIntoImportTable(toJson(result.getInsertIntoImportTable()))
				.importRecordsValidation(toJson(result.getImportRecordsValidation()))
				.actualImport(toJson(result.getActualImport()))
				.actualImportAsync(toJson(result.getAsyncImportResult()))
				.build();
	}

	private static JsonInsertIntoImportTable toJson(@NonNull final InsertIntoImportTableResult result)
	{
		return JsonInsertIntoImportTable.builder()
				.fromResource(result.getFromResource() != null ? result.getFromResource().toString() : null)
				.toImportTableName(result.getToImportTableName())
				.importFormatName(result.getImportFormatName())
				.dataImportConfigId(result.getDataImportConfigId())
				//
				.duration(result.getDuration().toString())
				.dataImportRunId(result.getDataImportRunId())
				.countTotalRows(result.getCountTotalRows())
				.countValidRows(result.getCountValidRows())
				.errors(result.getErrors()
						.stream()
						.map(error -> toJsonErrorItem(error))
						.collect(ImmutableList.toImmutableList()))
				//
				.build();
	}

	private static JsonImportRecordsValidation toJson(@Nullable final ValidateImportRecordsResult result)
	{
		if (result == null)
		{
			return null;
		}

		return JsonImportRecordsValidation.builder()
				.importTableName(result.getImportTableName())
				.duration(result.getDuration().toString())
				.countImportRecordsDeleted(result.getCountImportRecordsDeleted())
				.countErrors(result.getCountImportRecordsWithValidationErrors().orElse(0))
				.build();
	}

	private static JsonActualImport toJson(@Nullable final ActualImportRecordsResult result)
	{
		if (result == null)
		{
			return null;
		}

		return JsonActualImport.builder()
				.importTableName(result.getImportTableName())
				.targetTableName(result.getTargetTableName())
				.countImportRecordsConsidered(result.getCountImportRecordsConsidered().orElse(0))
				.countInsertsIntoTargetTable(result.getCountInsertsIntoTargetTable().orElse(0))
				.countUpdatesIntoTargetTable(result.getCountUpdatesIntoTargetTable().orElse(0))
				.errors(result.getErrors()
						.stream()
						.map(error -> toJsonErrorItem(error))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static JsonActualImportAsync toJson(@Nullable final AsyncImportRecordsResponse result)
	{
		if (result == null)
		{
			return null;
		}

		return JsonActualImportAsync.builder()
				.workpackageId(result.getWorkpackageId())
				.build();
	}

	private static JsonErrorItem toJsonErrorItem(final InsertIntoImportTableResult.Error error)
	{
		return JsonErrorItem.builder()
				.message(error.getMessage())
				.parameter(ERROR_PARAM_WHERE, "source-file")
				.parameter("importLineNo", String.valueOf(error.getLineNo()))
				.parameter("importLineContent", error.getLineContent())
				.build();
	}

	private static JsonErrorItem toJsonErrorItem(final ActualImportRecordsResult.Error error)
	{
		return JsonErrorItem.builder()
				.message(error.getMessage())
				.adIssueId(JsonMetasfreshId.of(error.getAdIssueId().getRepoId()))
				.throwable(error.getException())
				.stackTrace(error.getException() != null
						? Trace.toOneLineStackTraceString(error.getException())
						: null)
				.parameter(ERROR_PARAM_WHERE, "actual-import")
				.parameter("affectedRecordsCount", String.valueOf(error.getAffectedImportRecordsCount()))
				.build();
	}
}
