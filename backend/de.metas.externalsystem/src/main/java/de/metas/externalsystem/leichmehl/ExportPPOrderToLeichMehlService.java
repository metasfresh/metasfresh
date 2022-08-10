/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.leichmehl;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.JsonObjectMapperHolder;
import de.metas.audit.data.repository.DataExportAuditLogRepository;
import de.metas.audit.data.repository.DataExportAuditRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlConfigProductMapping;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlPluFileConfig;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlPluFileConfigs;
import de.metas.common.externalsystem.leichundmehl.JsonReplacementSource;
import de.metas.common.externalsystem.leichundmehl.JsonTargetFieldType;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.export.pporder.ExportPPOrderToExternalSystem;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.logging.LogManager;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExportPPOrderToLeichMehlService extends ExportPPOrderToExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_PP_ORDER = "exportPPOrder";

	private static final Logger logger = LogManager.getLogger(ExportPPOrderToLeichMehlService.class);

	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	protected ExportPPOrderToLeichMehlService(
			final @NonNull DataExportAuditRepository dataExportAuditRepository,
			final @NonNull DataExportAuditLogRepository dataExportAuditLogRepository,
			final @NonNull ExternalSystemConfigRepo externalSystemConfigRepo,
			final @NonNull ExternalSystemMessageSender externalSystemMessageSender,
			final @NonNull ExternalSystemConfigService externalSystemConfigService)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender, externalSystemConfigService);
	}

	@Override
	protected Map<String, String> buildParameters(final @NonNull IExternalSystemChildConfig childConfig, final @NonNull PPOrderId ppOrderId)
	{
		final ExternalSystemLeichMehlConfig leichMehlConfig = ExternalSystemLeichMehlConfig.cast(childConfig);

		if (leichMehlConfig.isProductMappingEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemLeichMehlConfig: {} has no product mappings! No action is performed!", leichMehlConfig.getId());
			return ImmutableMap.of();
		}

		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);

		final ExternalSystemLeichMehlConfigProductMapping productMappingConfig = matchProductMappingConfig(ppOrder, leichMehlConfig).orElse(null);

		if (productMappingConfig == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No config to export found for ExternalSystem_Config_LeichMehl_ID: {} and PPOrderId: {}! No action is performed!", leichMehlConfig.getId().getRepoId(), ppOrderId);
			return ImmutableMap.of();
		}

		if (leichMehlConfig.hasNoPluFileConfigs())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No pluFileConfig found for ExternalSystem_Config_LeichMehl_ID: {}! No action is performed!", leichMehlConfig.getId().getRepoId());
			return ImmutableMap.of();
		}

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_PP_ORDER_ID, String.valueOf(ppOrderId.getRepoId()));
		parameters.put(ExternalSystemConstants.PARAM_PRODUCT_BASE_FOLDER_NAME, leichMehlConfig.getProductBaseFolderName());
		parameters.put(ExternalSystemConstants.PARAM_TCP_PORT_NUMBER, String.valueOf(leichMehlConfig.getTcpPort()));
		parameters.put(ExternalSystemConstants.PARAM_TCP_HOST, leichMehlConfig.getTcpHost());
		parameters.put(ExternalSystemConstants.PARAM_PLU_FILE_EXPORT_AUDIT_ENABLED, String.valueOf(leichMehlConfig.isPluFileExportAuditEnabled()));
		parameters.put(ExternalSystemConstants.PARAM_CONFIG_MAPPINGS, toJsonProductMapping(productMappingConfig.getPluFile(),
																						   ProductId.ofRepoId(ppOrder.getM_Product_ID())));
		parameters.put(ExternalSystemConstants.PARAM_PLU_FILE_CONFIG, toJsonPluFileConfig(leichMehlConfig.getPluFileConfigs()));

		return parameters;
	}

	@Override
	protected String getExternalCommand()
	{
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_PP_ORDER;
	}

	@Override
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.LeichUndMehl;
	}

	@NonNull
	private Optional<ExternalSystemLeichMehlConfigProductMapping> matchProductMappingConfig(
			@NonNull final I_PP_Order ppOrder,
			@NonNull final ExternalSystemLeichMehlConfig leichMehlConfig)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoIdOrNull(ppOrder.getC_BPartner_ID());

		final ProductId ppOrderProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());

		final ProductCategoryId productCategoryId = productDAO.retrieveProductCategoryByProductId(ppOrderProductId);

		final LeichMehlProductMappingQuery query = LeichMehlProductMappingQuery.builder()
				.productId(ppOrderProductId)
				.bPartnerId(bPartnerId)
				.productCategoryId(productCategoryId)
				.build();

		return leichMehlConfig.findMappingForQuery(query);
	}

	@NonNull
	private static String toJsonProductMapping(@NonNull final String pluFile, @NonNull final ProductId productId)
	{
		final JsonMetasfreshId jsonProductId = JsonMetasfreshId.of(productId.getRepoId());

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(
							JsonExternalSystemLeichMehlConfigProductMapping.builder()
									.pluFile(pluFile)
									.productId(jsonProductId)
									.build());
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private static String toJsonPluFileConfig(@NonNull final List<ExternalSystemLeichMehlPluFileConfig> configs)
	{
		final List<JsonExternalSystemLeichMehlPluFileConfig> jsonConfigs = configs.stream()
				.map(config -> JsonExternalSystemLeichMehlPluFileConfig.builder()
						.targetFieldName(config.getTargetFieldName())
						.targetFieldType(JsonTargetFieldType.ofCode(config.getTargetFieldType().getCode()))
						.replacePattern(config.getReplaceRegExp())
						.replacement(config.getReplacement())
						.replacementSource(JsonReplacementSource.ofCode(config.getReplacementSource().getCode()))
						.build())
				.collect(ImmutableList.toImmutableList());

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper()
					.writeValueAsString(JsonExternalSystemLeichMehlPluFileConfigs.builder()
												.pluFileConfigs(jsonConfigs)
												.build());
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
