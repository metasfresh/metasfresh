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

package de.metas.externalsystem.export.stock;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.GetExternalReferenceByRecordIdReq;
import de.metas.externalreference.Shopware6ExternalSystem;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.rabbitmq.ExternalSystemMessageSender;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.availableforsales.AvailableForSalesRepository;
import de.metas.common.externalsystem.JsonAvailableStock;
import de.metas.common.externalsystem.JsonProductIdentifier;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.async.Debouncer;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_MD_Available_For_Sales;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_BASE_PATH;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CLIENT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CLIENT_SECRET;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_AVAILABLE_STOCK;

@Service
public class ExportStockToShopwareExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_STOCK = "exportStock";

	private static final Logger logger = LogManager.getLogger(ExportStockToShopwareExternalSystem.class);
	private static final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final ExternalReferenceRepository externalReferenceRepository;
	private final ExternalSystemConfigRepo externalSystemConfigRepo;
	private final ExternalSystemMessageSender externalSystemMessageSender;
	private final ExternalSystemConfigService externalSystemConfigService;
	private final AvailableForSalesRepository availableForSalesRepository;
	private final Debouncer<ProductId> syncAvailableForSalesDebouncer;

	protected ExportStockToShopwareExternalSystem(
			@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo,
			@NonNull final ExternalSystemMessageSender externalSystemMessageSender,
			@NonNull final ExternalReferenceRepository externalReferenceRepository,
			@NonNull final ExternalSystemConfigService externalSystemConfigService,
			@NonNull final AvailableForSalesRepository availableForSalesRepository)
	{
		this.externalSystemConfigRepo = externalSystemConfigRepo;
		this.externalSystemMessageSender = externalSystemMessageSender;
		this.externalReferenceRepository = externalReferenceRepository;
		this.externalSystemConfigService = externalSystemConfigService;
		this.availableForSalesRepository = availableForSalesRepository;
		this.syncAvailableForSalesDebouncer = Debouncer.<ProductId>builder()
				.name("exportStockToShopwareDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue("de.metas.externalsystem.export.stock.ExportStockToShopwareExternalSystem.debouncer.bufferMaxSize", 100))
				.delayInMillis(sysConfigBL.getIntValue("de.metas.externalsystem.export.stock.ExportStockToShopwareExternalSystem.debouncer.delayInMillis", 5000))
				.distinct(true)
				.consumer(this::exportAvailableStock)
				.build();
	}

	public void enqueueProductToBeExported(@NonNull final ProductId productId)
	{
		Loggables.withLogger(logger, Level.DEBUG).addLog("ProductId: {} enqueued to be exported.", productId);

		syncAvailableForSalesDebouncer.add(productId);
	}

	private void exportAvailableStock(@NonNull final Collection<ProductId> productIdList)
	{
		if (productIdList.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ProductId list to export is empty! No action is performed!");
			return; // nothing to do
		}

		if (!externalSystemConfigRepo.isAnyConfigActive(getExternalSystemType()))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No active config found for external system type: {}! No action is performed!", getExternalSystemType());
			return; // nothing to do
		}

		for (final ProductId productId : productIdList)
		{
			final GetExternalReferenceByRecordIdReq getExternalReferenceByRecordIdReq = buildGetExternalReferenceByRecordIdReq(productId);

			final Optional<ExternalReference> externalReference = externalReferenceRepository.getExternalReferenceByMFReference(getExternalReferenceByRecordIdReq);

			if (!externalReference.isPresent())
			{
				Loggables.withLogger(logger, Level.DEBUG).addLog("No external reference found for Shopware6 and ProductId: {}! No action is performed!", productId);
				continue; // nothing to do
			}

			getExportExternalSystemRequest(externalReference.get(), productId)
					.ifPresent(externalSystemMessageSender::send);
		}
	}

	@NonNull
	private Optional<JsonExternalSystemRequest> getExportExternalSystemRequest(
			@NonNull final ExternalReference externalReference,
			@NonNull final ProductId productId)
	{
		final ExternalSystemParentConfigId externalReferenceParentConfigId = (ExternalSystemParentConfigId)externalReference
				.getExternalSystemParentConfigId(ExternalSystemParentConfigId::ofRepoIdOrNull);

		if (externalReferenceParentConfigId == null)
		{
			return Optional.empty();
		}

		final String orgCode = orgDAO.getById(externalReference.getOrgId()).getValue();

		final ExternalSystemType parentType = ExternalSystemType.ofCode(externalSystemConfigRepo.getParentTypeById(externalReferenceParentConfigId));
		final IExternalSystemChildConfig externalSystemChildConfig = externalSystemConfigRepo.getChildByParentIdAndType(externalReferenceParentConfigId, parentType)
				.orElseThrow(() -> new AdempiereException("Child config not found for ExternalSystemType and ParentConfigId!")
						.appendParametersToMessage()
						.setParameter("ExternalSystemType", parentType)
						.setParameter("ParentConfigId", externalReferenceParentConfigId));

		return Optional.of(JsonExternalSystemRequest.builder()
								   .externalSystemName(JsonExternalSystemName.of(getExternalSystemType().getName()))
								   .externalSystemConfigId(JsonMetasfreshId.of(ExternalSystemParentConfigId.toRepoId(externalReferenceParentConfigId)))
								   .externalSystemChildConfigValue(externalSystemChildConfig.getValue())
								   .orgCode(orgCode)
								   .command(getExternalCommand())
								   .parameters(buildParameters(externalReference, productId, externalSystemChildConfig.getId()))
								   .traceId(externalSystemConfigService.getTraceId())
								   .build());
	}

	@NonNull
	private Map<String, String> buildParameters(
			@NonNull final ExternalReference externalReference,
			@NonNull final ProductId productId,
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId)
	{
		final ExternalSystemParentConfig externalSystemParentConfig = externalSystemConfigRepo.getById(externalSystemChildConfigId);

		final JsonAvailableStock jsonAvailableStock = buildJsonAvailableStock(productId, externalReference.getExternalReference(), externalSystemParentConfig);

		final ExternalSystemShopware6Config shopware6Config = ExternalSystemShopware6Config.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAM_BASE_PATH, shopware6Config.getBaseUrl());
		parameters.put(PARAM_CLIENT_SECRET, shopware6Config.getClientSecret());
		parameters.put(PARAM_CLIENT_ID, shopware6Config.getClientId());
		parameters.put(PARAM_JSON_AVAILABLE_STOCK, writeJsonAvailableStock(jsonAvailableStock));

		return parameters;
	}

	@NonNull
	private BigDecimal getAvailableStock(
			@NonNull final ExternalSystemParentConfig externalSystemParentConfig,
			@NonNull final ProductId productId)
	{
		if (OrgId.ANY.equals(externalSystemParentConfig.getOrgId()))
		{
			return availableForSalesRepository.getRecordsByProductId(productId)
					.stream()
					.map(I_MD_Available_For_Sales::getQtyOnHandStock)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		else
		{
			return availableForSalesRepository.getRecordsByProductAndOrg(productId, externalSystemParentConfig.getOrgId())
					.stream()
					.map(I_MD_Available_For_Sales::getQtyOnHandStock)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		}
	}

	@NonNull
	private JsonAvailableStock buildJsonAvailableStock(
			@NonNull final ProductId productId,
			@NonNull final String externalReference,
			@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final BigDecimal stock = getAvailableStock(externalSystemParentConfig, productId);

		return JsonAvailableStock.builder()
				.productIdentifier(JsonProductIdentifier.builder()
										   .metasfreshId(JsonMetasfreshId.of(ProductId.toRepoId(productId)))
										   .externalReference(externalReference)
										   .build())
				.stock(stock)
				.build();
	}

	@NonNull
	private String writeJsonAvailableStock(@NonNull final JsonAvailableStock jsonAvailableStock)
	{
		try
		{
			return objectMapper.writeValueAsString(jsonAvailableStock);
		}
		catch (final JsonProcessingException jsonProcessingException)
		{
			throw AdempiereException.wrapIfNeeded(jsonProcessingException)
					.appendParametersToMessage()
					.setParameter("JsonAvailableStock", jsonAvailableStock);
		}
	}

	@NonNull
	private GetExternalReferenceByRecordIdReq buildGetExternalReferenceByRecordIdReq(@NonNull final ProductId productId)
	{
		return GetExternalReferenceByRecordIdReq.builder()
				.recordId(productId.getRepoId())
				.externalSystem(Shopware6ExternalSystem.SHOPWARE6)
				.externalReferenceType(ProductExternalReferenceType.PRODUCT)
				.build();
	}

	@NonNull
	private ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Shopware6;
	}

	@NonNull
	private String getExternalCommand()
	{
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_STOCK;
	}
}
