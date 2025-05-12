/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.externalsystem.pcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.bpartner.BPartnerId;
import de.metas.common.externalsystem.JsonTaxCategoryMapping;
import de.metas.common.externalsystem.JsonTaxCategoryMappings;
import de.metas.externalsystem.pcm.source.PCMContentSourceLocalFile;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_BPARTNER_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_BPARTNER_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_ERRORED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_FREQUENCY_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PROCESSED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PRODUCT_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PURCHASE_ORDER_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_ROOT_LOCATION;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_WAREHOUSE_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_TAX_CATEGORY_MAPPINGS;

@Service
public class InvokePCMService
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull
	public Map<String, String> getParameters(
			@NonNull final ExternalSystemPCMConfig pcmConfig,
			@NonNull final String externalRequest,
			@NonNull final BPartnerId bPartnerId)
	{
		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_CHILD_CONFIG_VALUE, pcmConfig.getValue());
		parameters.putAll(extractContentSourceParameters(pcmConfig, externalRequest));
		parameters.put(PARAM_BPARTNER_ID, String.valueOf(bPartnerId.getRepoId())); // needed for the route that imports the warehouse-csv file
		parameters.put(PARAM_TAX_CATEGORY_MAPPINGS, getTaxCategoryMappings(pcmConfig));

		return parameters;
	}

	@NonNull
	private Map<String, String> extractContentSourceParameters(
			@NonNull final ExternalSystemPCMConfig pcmConfig,
			@NonNull final String externalRequest)
	{
		final PCMExternalRequest pcmExternalRequest = PCMExternalRequest.ofCode(externalRequest);

		final PCMContentSourceLocalFile pcmContentSourceLocalFile = Optional.ofNullable(pcmConfig.getContentSourceLocalFile())
				.orElseThrow(() -> new AdempiereException("No LocalFile config found for Pro Care Management!")
						.appendParametersToMessage()
						.setParameter("ExternalSystemConfigId", pcmConfig.getParentId().getRepoId()));

		throwErrorIfFalse(pcmContentSourceLocalFile.isStartServicePossible(pcmExternalRequest, pcmConfig.getParentId(), msgBL));

		return extractLocalFileSourceParameters(pcmContentSourceLocalFile);
	}

	@NonNull
	private static String getTaxCategoryMappings(@NonNull final ExternalSystemPCMConfig pcmConfig)
	{
		try
		{
			final List<JsonTaxCategoryMapping> taxCategoryMappings = pcmConfig.getTaxCategoryPCMMappingList()
					.stream()
					.map(InvokePCMService::toJsonTaxCategoryMapping)
					.collect(ImmutableList.toImmutableList());

			final JsonTaxCategoryMappings jsonTaxCategoryMappings = JsonTaxCategoryMappings.builder()
					.jsonTaxCategoryMappingList(taxCategoryMappings)
					.build();

			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(jsonTaxCategoryMappings);
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("pcmConfigId", pcmConfig.getId().getRepoId());
		}
	}

	@NonNull
	private static JsonTaxCategoryMapping toJsonTaxCategoryMapping(@NonNull final TaxCategoryPCMMapping taxCategory)
	{
		return JsonTaxCategoryMapping.builder()
				.taxCategory(taxCategory.getTaxCategory().getInternalName())
				.taxRates(taxCategory.getTaxRates().asList())
				.build();
	}

	@NonNull
	private static Map<String, String> extractLocalFileSourceParameters(@NonNull final PCMContentSourceLocalFile contentSourceLocalFile)
	{
		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_LOCAL_FILE_PROCESSED_DIRECTORY, contentSourceLocalFile.getProcessedDirectory());
		parameters.put(PARAM_LOCAL_FILE_ERRORED_DIRECTORY, contentSourceLocalFile.getErroredDirectory());
		parameters.put(PARAM_LOCAL_FILE_POLLING_FREQUENCY_MS, String.valueOf(contentSourceLocalFile.getPollingFrequency().toMillis()));
		parameters.put(PARAM_LOCAL_FILE_ROOT_LOCATION, contentSourceLocalFile.getRootLocation());

		parameters.put(PARAM_LOCAL_FILE_PRODUCT_FILE_NAME_PATTERN, contentSourceLocalFile.getFileNamePatternProduct());

		parameters.put(PARAM_LOCAL_FILE_BPARTNER_FILE_NAME_PATTERN, contentSourceLocalFile.getFileNamePatternBPartner());

		parameters.put(PARAM_LOCAL_FILE_WAREHOUSE_FILE_NAME_PATTERN, contentSourceLocalFile.getFileNamePatternWarehouse());

		parameters.put(PARAM_LOCAL_FILE_PURCHASE_ORDER_FILE_NAME_PATTERN, contentSourceLocalFile.getFileNamePatternPurchaseOrder());

		return parameters;
	}

	private static void throwErrorIfFalse(@NonNull final BooleanWithReason booleanWithReason)
	{
		if (booleanWithReason.isFalse())
		{
			throw new AdempiereException(booleanWithReason.getReason()).markAsUserValidationError();
		}
	}
}
