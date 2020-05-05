package de.metas.rest_api.data_import;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
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

import de.metas.Profiles;
import de.metas.impexp.DataImportRequest;
import de.metas.impexp.DataImportResult;
import de.metas.impexp.DataImportService;
import de.metas.impexp.config.DataImportConfig;
import de.metas.logging.LogManager;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;

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

			@ApiParam("The text file you are importing") //
			@RequestBody @NonNull final String content)
	{
		final Resource data = new ByteArrayResource(content.getBytes(StandardCharsets.UTF_8));
		return importFile(dataImportConfigInternalName, completeDocuments, data);
	}

	@PostMapping
	public ResponseEntity<JsonDataImportResponseWrapper> importFile(
			@ApiParam("Data Import internal name (i.e. `C_DataImport.InternalName`)") //
			@RequestParam("dataImportConfig") @NonNull final String dataImportConfigInternalName,

			@ApiParam("Try to complete documents if it applies to the given import") //
			@RequestParam(name = "completeDocuments", required = false, defaultValue = "true") final boolean completeDocuments,

			@ApiParam("The text file you are importing") //
			@RequestParam("file") @NonNull final MultipartFile file)
	{
		final Resource data = toResource(file);
		return importFile(dataImportConfigInternalName, completeDocuments, data);
	}

	private ResponseEntity<JsonDataImportResponseWrapper> importFile(
			@NonNull final String dataImportConfigInternalName,
			final boolean completeDocuments,
			@NonNull final Resource data)
	{
		try
		{
			final DataImportConfig dataImportConfig = dataImportService.getDataImportConfigByInternalName(dataImportConfigInternalName)
					.orElseThrow(() -> new AdempiereException("No data import configuration found for: " + dataImportConfigInternalName));

			final DataImportResult result = dataImportService.importData(DataImportRequest.builder()
					.data(data)
					.dataImportConfigId(dataImportConfig.getId())
					.clientId(Env.getClientId())
					.orgId(Env.getOrgId())
					.userId(Env.getLoggedUserId())
					.completeDocuments(completeDocuments)
					.build());

			return ResponseEntity.accepted()
					.body(toJsonDataImportResponse(result));
		}
		catch (final Exception ex)
		{
			logger.debug("Got error", ex);

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

	private static JsonDataImportResponseWrapper toJsonDataImportResponse(final DataImportResult result)
	{
		return JsonDataImportResponseWrapper.ok(JsonDataImportResponse.builder()
				.dataImportConfigId(result.getDataImportConfigId().getRepoId())
				.importFormatName(result.getImportFormatName())
				//
				.countSourceFileValidLines(result.getCountSourceFileValidLines())
				.countSourceFileErrorLines(result.getCountSourceFileErrorLines())
				//
				.importTableName(result.getImportTableName())
				.countImportRecordsWithErrors(result.getCountImportRecordsWithErrors())
				//
				.targetTableName(result.getTargetTableName())
				//
				.build());
	}
}
