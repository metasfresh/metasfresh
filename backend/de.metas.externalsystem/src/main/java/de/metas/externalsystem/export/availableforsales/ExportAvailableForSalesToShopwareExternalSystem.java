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

package de.metas.externalsystem.export.availableforsales;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.externalsystem.JsonAvailableForSales;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonProductIdentifier;
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
import de.metas.material.cockpit.availableforsales.RetrieveAvailableForSalesQuery;
import de.metas.organization.IOrgDAO;
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
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_AVAILABLE_FOR_SALES;

@Service
public class ExportAvailableForSalesToShopwareExternalSystem
{
	private static final String EXTERNAL_SYSTEM_COMMAND_EXPORT_AVAILABLE_FOR_SALES = "exportStock";

	private static final Logger logger = LogManager.getLogger(ExportAvailableForSalesToShopwareExternalSystem.class);
	private static final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private final ExternalReferenceRepository externalReferenceRepository;
	private final ExternalSystemConfigRepo externalSystemConfigRepo;
	private final ExternalSystemMessageSender externalSystemMessageSender;
	private final ExternalSystemConfigService externalSystemConfigService;
	private final AvailableForSalesRepository availableForSalesRepository;
	private final Debouncer<ProductId> syncAvailableForSalesDebouncer;

	protected ExportAvailableForSalesToShopwareExternalSystem(
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
				.name("syncAvailableForSalesDebouncer")
				.bufferMaxSize(sysConfigBL.getIntValue("de.metas.externalsystem.export.stock.ExportAvailableForSalesToShopwareExternalSystem.debouncer.bufferMaxSize", 100))
				.delayInMillis(sysConfigBL.getIntValue("de.metas.externalsystem.export.stock.ExportAvailableForSalesToShopwareExternalSystem.debouncer.delayInMillis", 5000))
				.distinct(true)
				.consumer(this::exportAvailableForSales)
				.build();
	}

	public void enqueueProductToBeExported(@NonNull final ProductId productId)
	{
		Loggables.withLogger(logger, Level.DEBUG).addLog("ProductId: {} enqueued to be exported.", productId);

		syncAvailableForSalesDebouncer.add(productId);
	}

	private void exportAvailableForSales(@NonNull final Collection<ProductId> productIdList)
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

			getExportExternalSystemRequest(externalReference.get())
					.ifPresent(externalSystemMessageSender::send);
		}
	}

	@NonNull
	private Optional<JsonExternalSystemRequest> getExportExternalSystemRequest(@NonNull final ExternalReference productExternalRef)
	{
		final ExternalSystemParentConfigId externalSystemParentConfigId = (ExternalSystemParentConfigId)productExternalRef
				.getExternalSystemParentConfigId(ExternalSystemParentConfigId::ofRepoIdOrNull);

		if (externalSystemParentConfigId == null)
		{
			return Optional.empty();
		}

		final String orgCode = orgDAO.getById(productExternalRef.getOrgId()).getValue();

		final ExternalSystemType parentType = ExternalSystemType.ofCode(externalSystemConfigRepo.getParentTypeById(externalSystemParentConfigId));

		final IExternalSystemChildConfig externalSystemChildConfig = externalSystemConfigRepo.getChildByParentIdAndType(externalSystemParentConfigId, parentType)
				.orElseThrow(() -> new AdempiereException("Child config not found for ExternalSystemType and ParentConfigId!")
						.appendParametersToMessage()
						.setParameter("ExternalSystemType", parentType)
						.setParameter("ParentConfigId", externalSystemParentConfigId));

		if (!isSyncAvailableForSalesEnabled(externalSystemChildConfig))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("ExternalSystemChildConfig: {} isSyncAvailableForSalesEnabled to external system is false! No action is performed!", externalSystemParentConfigId);

			return Optional.empty();
		}

		final ExternalSystemParentConfig parentConfig = externalSystemConfigRepo.getById(externalSystemChildConfig.getId());

		return Optional.of(JsonExternalSystemRequest.builder()
								   .externalSystemName(JsonExternalSystemName.of(getExternalSystemType().getName()))
								   .externalSystemConfigId(JsonMetasfreshId.of(ExternalSystemParentConfigId.toRepoId(externalSystemParentConfigId)))
								   .externalSystemChildConfigValue(externalSystemChildConfig.getValue())
								   .orgCode(orgCode)
								   .command(getExternalCommand())
								   .parameters(buildParameters(productExternalRef, externalSystemChildConfig.getId()))
								   .writeAuditEndpoint(parentConfig.getAuditEndpointIfEnabled())
								   .traceId(externalSystemConfigService.getTraceId())
								   .build());
	}

	private boolean isSyncAvailableForSalesEnabled(@NonNull final IExternalSystemChildConfig childConfig)
	{
		final ExternalSystemShopware6Config shopware6Config = ExternalSystemShopware6Config.cast(childConfig);

		return shopware6Config.isSyncAvailableForSalesToShopware6();
	}

	@NonNull
	private Map<String, String> buildParameters(
			@NonNull final ExternalReference externalReference,
			@NonNull final IExternalSystemChildConfigId externalSystemChildConfigId)
	{
		final ExternalSystemParentConfig externalSystemParentConfig = externalSystemConfigRepo.getById(externalSystemChildConfigId);

		final JsonAvailableForSales jsonAvailableForSales = buildJsonAvailableForSales(externalReference, externalSystemParentConfig);

		final ExternalSystemShopware6Config shopware6Config = ExternalSystemShopware6Config.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAM_BASE_PATH, shopware6Config.getBaseUrl());
		parameters.put(PARAM_CLIENT_SECRET, shopware6Config.getClientSecret());
		parameters.put(PARAM_CLIENT_ID, shopware6Config.getClientId());
		parameters.put(PARAM_JSON_AVAILABLE_FOR_SALES, writeJsonAvailableForSales(jsonAvailableForSales));

		return parameters;
	}

	@NonNull
	private BigDecimal getAvailableStock(
			@NonNull final ExternalSystemParentConfig externalSystemParentConfig,
			@NonNull final ProductId productId)
	{
		final RetrieveAvailableForSalesQuery.RetrieveAvailableForSalesQueryBuilder retrieveAvailableForSalesQueryBuilder = RetrieveAvailableForSalesQuery.builder()
				.productId(productId);
		if (externalSystemParentConfig.getOrgId().isRegular())
		{
			retrieveAvailableForSalesQueryBuilder.orgId(externalSystemParentConfig.getOrgId());
		}

		final BigDecimal availableForSalesQty = availableForSalesRepository.getRecordsByQuery(retrieveAvailableForSalesQueryBuilder.build())
				.stream()
				.map(ExportAvailableForSalesToShopwareExternalSystem::getAvailableForSalesQty)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		final ExternalSystemShopware6Config shopware6Config = ExternalSystemShopware6Config.cast(externalSystemParentConfig.getChildConfig());

		return shopware6Config.getPercentageToDeductFromAvailableForSales()
				.subtractFromBase(availableForSalesQty, availableForSalesQty.precision());
	}

	@NonNull
	private JsonAvailableForSales buildJsonAvailableForSales(
			@NonNull final ExternalReference productExternalReference,
			@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final BigDecimal stock = getAvailableStock(externalSystemParentConfig, ProductId.ofRepoId(productExternalReference.getRecordId()));

		return JsonAvailableForSales.builder()
				.productIdentifier(JsonProductIdentifier.builder()
										   .metasfreshId(JsonMetasfreshId.of(productExternalReference.getRecordId()))
										   .externalReference(productExternalReference.getExternalReference())
										   .build())
				.stock(stock)
				.build();
	}

	@NonNull
	private String writeJsonAvailableForSales(@NonNull final JsonAvailableForSales jsonAvailableForSales)
	{
		try
		{
			return objectMapper.writeValueAsString(jsonAvailableForSales);
		}
		catch (final JsonProcessingException jsonProcessingException)
		{
			throw AdempiereException.wrapIfNeeded(jsonProcessingException)
					.appendParametersToMessage()
					.setParameter("JsonAvailableForSales", jsonAvailableForSales);
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
		return EXTERNAL_SYSTEM_COMMAND_EXPORT_AVAILABLE_FOR_SALES;
	}

	@NonNull
	private static BigDecimal getAvailableForSalesQty(@NonNull final I_MD_Available_For_Sales availableForSales)
	{
		return availableForSales.getQtyOnHandStock().subtract(availableForSales.getQtyToBeShipped());
	}
}
