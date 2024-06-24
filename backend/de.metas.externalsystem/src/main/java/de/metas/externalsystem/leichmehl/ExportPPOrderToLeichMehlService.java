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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
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
import de.metas.externalsystem.ExternalSystemLeichMehlConfigProductMappingRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.export.pporder.ExportPPOrderToExternalSystem;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExportPPOrderToLeichMehlService extends ExportPPOrderToExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_PP_ORDER = "exportPPOrder";
	private static final AdMessageKey MISSING_PRODUCT_PLU_CONFIG = AdMessageKey.of("de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingPLUConfigForProduct");
	private static final AdMessageKey MISSING_PLU_CONFIG_GROUP_ENTRIES = AdMessageKey.of("de.metas.externalsystem.leichmehl.ExportPPOrderToLeichMehlService.MissingConfigsInPLUConfigGroup");

	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ExternalSystemLeichMehlConfigProductMappingRepository externalSystemLeichMehlConfigProductMappingRepository;

	protected ExportPPOrderToLeichMehlService(
			final @NonNull DataExportAuditRepository dataExportAuditRepository,
			final @NonNull DataExportAuditLogRepository dataExportAuditLogRepository,
			final @NonNull ExternalSystemConfigRepo externalSystemConfigRepo,
			final @NonNull ExternalSystemMessageSender externalSystemMessageSender,
			final @NonNull ExternalSystemConfigService externalSystemConfigService,
			final @NonNull ExternalSystemLeichMehlConfigProductMappingRepository externalSystemLeichMehlConfigProductMappingRepository)
	{
		super(dataExportAuditRepository, dataExportAuditLogRepository, externalSystemConfigRepo, externalSystemMessageSender, externalSystemConfigService);
		this.externalSystemLeichMehlConfigProductMappingRepository = externalSystemLeichMehlConfigProductMappingRepository;
	}

	@Override
	protected Map<String, String> buildParameters(final @NonNull IExternalSystemChildConfig childConfig, final @NonNull PPOrderId ppOrderId)
	{
		final ExternalSystemLeichMehlConfig leichMehlConfig = ExternalSystemLeichMehlConfig.cast(childConfig);

		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);

		final ExternalSystemLeichMehlConfigProductMapping productMappingConfig = matchProductMappingConfig(ppOrder).orElse(null);

		if (productMappingConfig == null)
		{
			final ITranslatableString productName = productBL.getProductNameTrl(ProductId.ofRepoId(ppOrder.getM_Product_ID()));
			throw new AdempiereException(MISSING_PRODUCT_PLU_CONFIG, productName);
		}

		final List<ExternalSystemLeichMehlPluFileConfig> externalSystemLeichMehlPluFileConfigs = productMappingConfig.getLeichMehlPluFileConfigGroup().getExternalSystemLeichMehlPluFileConfigs();

		if (externalSystemLeichMehlPluFileConfigs.isEmpty())
		{
			throw new AdempiereException(MISSING_PLU_CONFIG_GROUP_ENTRIES, productMappingConfig.getLeichMehlPluFileConfigGroup().getName());
		}

		final Map<String, String> parameters = new HashMap<>();

		parameters.put(ExternalSystemConstants.PARAM_PP_ORDER_ID, String.valueOf(ppOrderId.getRepoId()));
		parameters.put(ExternalSystemConstants.PARAM_PRODUCT_BASE_FOLDER_NAME, leichMehlConfig.getProductBaseFolderName());
		parameters.put(ExternalSystemConstants.PARAM_TCP_PORT_NUMBER, String.valueOf(leichMehlConfig.getTcpPort()));
		parameters.put(ExternalSystemConstants.PARAM_TCP_HOST, leichMehlConfig.getTcpHost());
		parameters.put(ExternalSystemConstants.PARAM_PLU_FILE_EXPORT_AUDIT_ENABLED, String.valueOf(leichMehlConfig.isPluFileExportAuditEnabled()));
		parameters.put(ExternalSystemConstants.PARAM_CONFIG_MAPPINGS, toJsonProductMapping(productMappingConfig.getPluFile(),
																						   ProductId.ofRepoId(ppOrder.getM_Product_ID())));
		parameters.put(ExternalSystemConstants.PARAM_PLU_FILE_CONFIG, toJsonPluFileConfig(externalSystemLeichMehlPluFileConfigs));

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
			@NonNull final I_PP_Order ppOrder)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoIdOrNull(ppOrder.getC_BPartner_ID());

		final ProductId ppOrderProductId = ProductId.ofRepoId(ppOrder.getM_Product_ID());


		return externalSystemLeichMehlConfigProductMappingRepository.getByProductIdAndPartnerId(ppOrderProductId, bPartnerId);
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
