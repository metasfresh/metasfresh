/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.StringUtils;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfig;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6Mapping;
import de.metas.externalsystem.model.I_ExternalSystem_Config_WooCommerce;
import de.metas.externalsystem.other.ExternalSystemOtherConfig;
import de.metas.externalsystem.other.ExternalSystemOtherConfigId;
import de.metas.externalsystem.other.ExternalSystemOtherConfigRepository;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfig;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigMapping;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfig;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfigId;
import de.metas.pricing.PriceListId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ExternalSystemConfigRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ExternalSystemOtherConfigRepository externalSystemOtherConfigRepository;

	public ExternalSystemConfigRepo(final ExternalSystemOtherConfigRepository externalSystemOtherConfigRepository)
	{
		this.externalSystemOtherConfigRepository = externalSystemOtherConfigRepository;
	}

	public boolean isAnyConfigActive(final @NonNull ExternalSystemType type)
	{
		return getAllByType(type)
				.stream()
				.anyMatch(ExternalSystemParentConfig::getIsActive);
	}

	@NonNull
	public ExternalSystemParentConfig getById(final @NonNull IExternalSystemChildConfigId id)
	{
		switch (id.getType())
		{
			case Alberta:
				return getById(ExternalSystemAlbertaConfigId.cast(id));
			case Shopware6:
				return getById(ExternalSystemShopware6ConfigId.cast(id));
			case Other:
				return getById(ExternalSystemOtherConfigId.cast(id));
			case RabbitMQ:
				return getById(ExternalSystemRabbitMQConfigId.cast(id));
			case WOO:
				return getById(ExternalSystemWooCommerceConfigId.cast(id));
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", id.getType());
		}
	}

	@NonNull
	public Optional<ExternalSystemParentConfig> getByTypeAndValue(@NonNull final ExternalSystemType type, @NonNull final String value)
	{
		switch (type)
		{
			case Alberta:
				return getAlbertaConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case Shopware6:
				return getShopware6ConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case WOO:
				return getWooCommerceConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case RabbitMQ:
				return getRabbitMQConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", type);
		}
	}

	public Optional<IExternalSystemChildConfig> getChildByParentIdAndType(
			@NonNull final ExternalSystemParentConfigId id,
			@NonNull final ExternalSystemType externalSystemType)
	{
		switch (externalSystemType)
		{
			case Alberta:
				return getAlbertaConfigByParentId(id);
			case Shopware6:
				return getShopware6ConfigByParentId(id);
			case Other:
				final ExternalSystemOtherConfigId externalSystemOtherConfigId = ExternalSystemOtherConfigId.ofExternalSystemParentConfigId(id);
				return Optional.of(externalSystemOtherConfigRepository.getById(externalSystemOtherConfigId));
			case RabbitMQ:
				return getRabbitMQConfigByParentId(id);				
			case WOO:
				return getWooCommerceConfigByParentId(id);
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
		}
	}

	@NonNull
	public String getParentTypeById(final @NonNull ExternalSystemParentConfigId id)
	{
		final I_ExternalSystem_Config externalSystemConfigRecord = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config.class);

		return externalSystemConfigRecord.getType();
	}

	public void saveConfig(@NonNull final ExternalSystemParentConfig config)
	{
		switch (config.getType())
		{
			case Shopware6:
				storeShopware6Config(config);
				return;
			case Alberta:
			case Other:
				throw new AdempiereException("Method not supported for externalSystemType="+config.getType())
						.appendParametersToMessage()
						.setParameter("externalSystemType", config.getType());
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", config.getType());
		}
	}

	@NonNull
	public Optional<ExternalSystemParentConfig> getByQuery(
			@NonNull final ExternalSystemType externalSystemType,
			@NonNull final ExternalSystemConfigQuery query)
	{
		switch (externalSystemType)
		{
			case Shopware6:
				return getShopware6ConfigByQuery(query);
			case Alberta:
			case Other:
				throw new AdempiereException("Method not supported")
						.appendParametersToMessage()
						.setParameter("externalSystemType", externalSystemType);
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
		}
	}


	@NonNull
	private Optional<I_ExternalSystem_Config_Alberta> getAlbertaConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Alberta.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Alberta.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Alberta.class);
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_Shopware6> getShopware6ConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Shopware6.class);
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_RabbitMQ_HTTP> getRabbitMQConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_RabbitMQ_HTTP.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_RabbitMQ_HTTP.class);
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getAlbertaConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Alberta.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Alberta.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Alberta.class)
				.map(ex -> buildExternalSystemAlbertaConfig(ex, id));
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getShopware6ConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Shopware6.class)
				.map(ex -> buildExternalSystemShopware6Config(ex, id));
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getRabbitMQConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_RabbitMQ_HTTP.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_RabbitMQ_HTTP.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_RabbitMQ_HTTP.class)
				.map(this::buildExternalSystemRabbitMQConfig);
	}

	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemAlbertaConfigId id)
	{
		final I_ExternalSystem_Config_Alberta config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_Alberta.class);

		return getExternalSystemParentConfig(config);
	}

	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_Alberta config)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final ExternalSystemAlbertaConfig child = buildExternalSystemAlbertaConfig(config, parentConfigId);

		return getById(parentConfigId)
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_RabbitMQ_HTTP config)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final ExternalSystemRabbitMQConfig child = buildExternalSystemRabbitMQConfig(config);

		return getById(parentConfigId)
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemAlbertaConfig buildExternalSystemAlbertaConfig(final @NonNull I_ExternalSystem_Config_Alberta config,
			@NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		return ExternalSystemAlbertaConfig.builder()
				.id(ExternalSystemAlbertaConfigId.ofRepoId(config.getExternalSystem_Config_Alberta_ID()))
				.parentId(parentConfigId)
				.apiKey(config.getApiKey())
				.baseUrl(config.getBaseURL())
				.value(config.getExternalSystemValue())
				.tenant(config.getTenant())
				.pharmacyPriceListId(PriceListId.ofRepoIdOrNull(config.getPharmacy_PriceList_ID()))
				.rootBPartnerIdForUsers(BPartnerId.ofRepoIdOrNull(config.getC_Root_BPartner_ID()))
				.build();
	}

	@NonNull
	private ExternalSystemRabbitMQConfig buildExternalSystemRabbitMQConfig(final @NonNull I_ExternalSystem_Config_RabbitMQ_HTTP rabbitMQConfigRecord)
	{
		return ExternalSystemRabbitMQConfig.builder()
				.id(ExternalSystemRabbitMQConfigId.ofRepoId(rabbitMQConfigRecord.getExternalSystem_Config_RabbitMQ_HTTP_ID()))
				.parentId(ExternalSystemParentConfigId.ofRepoId(rabbitMQConfigRecord.getExternalSystem_Config_ID()))
				.value(rabbitMQConfigRecord.getExternalSystemValue())
				.routingKey(rabbitMQConfigRecord.getRouting_Key())
				.remoteUrl(rabbitMQConfigRecord.getRemoteURL())
				.authToken(rabbitMQConfigRecord.getAuthToken())
				.isSyncBPartnerToRabbitMQ(rabbitMQConfigRecord.isSyncBPartnersToRabbitMQ())
				.build();
	}

	@NonNull
	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemRabbitMQConfigId id)
	{
		final I_ExternalSystem_Config_RabbitMQ_HTTP config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_RabbitMQ_HTTP.class);

		return getExternalSystemParentConfig(config);
	}

	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemShopware6ConfigId id)
	{
		final I_ExternalSystem_Config_Shopware6 config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_Shopware6.class);

		return getExternalSystemParentConfig(config);
	}

	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_Shopware6 config)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final ExternalSystemShopware6Config child = buildExternalSystemShopware6Config(config, parentConfigId);

		return getById(parentConfigId)
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemShopware6Config buildExternalSystemShopware6Config(
			@NonNull final I_ExternalSystem_Config_Shopware6 config,
			@NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		final ExternalSystemShopware6ConfigId externalSystemShopware6ConfigId =
				ExternalSystemShopware6ConfigId.ofRepoId(config.getExternalSystem_Config_Shopware6_ID());

		final ExternalSystemShopware6Config.ExternalSystemShopware6ConfigBuilder configBuilder = ExternalSystemShopware6Config.builder();

		if (Check.isNotBlank(config.getFreightCost_NormalVAT_Rates()) && config.getM_FreightCost_NormalVAT_Product_ID() > 0)
		{
			final ExternalSystemShopware6Config.FreightCostConfig normalVatFreightCostConfig = ExternalSystemShopware6Config.FreightCostConfig.builder()
					.productId(ProductId.ofRepoId(config.getM_FreightCost_NormalVAT_Product_ID()))
					.vatRates(config.getFreightCost_NormalVAT_Rates())
					.build();

			configBuilder.freightCostNormalVatConfig(normalVatFreightCostConfig);
		}

		if (Check.isNotBlank(config.getFreightCost_Reduced_VAT_Rates()) && config.getM_FreightCost_ReducedVAT_Product_ID() > 0)
		{
			final ExternalSystemShopware6Config.FreightCostConfig reducedVatFreightCost = ExternalSystemShopware6Config.FreightCostConfig.builder()
					.productId(ProductId.ofRepoId(config.getM_FreightCost_ReducedVAT_Product_ID()))
					.vatRates(config.getFreightCost_Reduced_VAT_Rates())
					.build();

			configBuilder.freightCostReducedVatConfig(reducedVatFreightCost);
		}

		return configBuilder
				.id(externalSystemShopware6ConfigId)
				.parentId(parentConfigId)
				.baseUrl(config.getBaseURL())
				.clientSecret(config.getClient_Secret())
				.externalSystemShopware6ConfigMappingList(getExternalSystemShopware6ConfigMappingList(externalSystemShopware6ConfigId))
				.clientId(config.getClient_Id())
				.bPartnerIdJSONPath(config.getJSONPathConstantBPartnerID())
				.bPartnerLocationIdJSONPath(config.getJSONPathConstantBPartnerLocationID())
				.salesRepJSONPath(config.getJSONPathSalesRepID())
				.isActive(config.isActive())
				.build();
	}

	@NonNull
	private List<ExternalSystemShopware6ConfigMapping> getExternalSystemShopware6ConfigMappingList(
			@NonNull final ExternalSystemShopware6ConfigId externalSystemShopware6ConfigId)
	{

		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6Mapping.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Shopware6Mapping.COLUMN_ExternalSystem_Config_Shopware6_ID, externalSystemShopware6ConfigId.getRepoId())
				.create()
				.list()
				.stream()
				.map(this::toExternalSystemShopware6ConfigMapping)
				.collect(ImmutableList.toImmutableList());

	}

	@NonNull
	private ExternalSystemShopware6ConfigMapping toExternalSystemShopware6ConfigMapping(@NonNull final I_ExternalSystem_Config_Shopware6Mapping record)
	{
		return ExternalSystemShopware6ConfigMapping.builder()
				.docTypeOrderId(record.getC_DocTypeOrder_ID())
				.paymentRule(record.getPaymentRule())
				.paymentTermId(record.getC_PaymentTerm_ID())
				.sw6CustomerGroup(record.getSW6_Customer_Group())
				.sw6PaymentMethod(record.getSW6_Payment_Method())
				.description(record.getDescription())
				.seqNo(record.getSeqNo())
				.isInvoiceEmailEnabled(StringUtils.toBoolean(record.getIsInvoiceEmailEnabled(), null))
				.bpartnerIfExists(record.getBPartner_IfExists())
				.bpartnerIfNotExists(record.getBPartner_IfNotExists())
				.bpartnerLocationIfExists(record.getBPartnerLocation_IfExists())
				.bpartnerLocationIfNotExists(record.getBPartnerLocation_IfNotExists())
				.build();
	}

	private ExternalSystemParentConfig.ExternalSystemParentConfigBuilder getById(final @NonNull ExternalSystemParentConfigId id)
	{
		final I_ExternalSystem_Config externalSystemConfigRecord = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config.class);

		return ExternalSystemParentConfig.builder()
				.type(ExternalSystemType.ofCode(externalSystemConfigRecord.getType()))
				.id(ExternalSystemParentConfigId.ofRepoId(externalSystemConfigRecord.getExternalSystem_Config_ID()))
				.name(externalSystemConfigRecord.getName())
				.isActive(externalSystemConfigRecord.isActive());
	}

	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemOtherConfigId id)
	{
		final ExternalSystemOtherConfig childConfig = externalSystemOtherConfigRepository.getById(id);

		return getById(id.getParentConfigId())
				.childConfig(childConfig)
				.build();
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeAlberta()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Alberta.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemWooCommerceConfigId id)
	{
		final I_ExternalSystem_Config_WooCommerce config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_WooCommerce.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_WooCommerce config)
	{
		final ExternalSystemWooCommerceConfig child = buildExternalSystemWooCommerceConfig(config);

		return getById(child.getParentId())
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemWooCommerceConfig buildExternalSystemWooCommerceConfig(@NonNull final I_ExternalSystem_Config_WooCommerce config)
	{
		return ExternalSystemWooCommerceConfig.builder()
				.id(ExternalSystemWooCommerceConfigId.ofRepoId(config.getExternalSystem_Config_WooCommerce_ID()))
				.parentId(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.value(config.getExternalSystemValue())
				.camelHttpResourceAuthKey(config.getCamelHttpResourceAuthKey())
				.build();
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_WooCommerce> getWooCommerceConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_WooCommerce.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_WooCommerce.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_WooCommerce.class);
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getWooCommerceConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_WooCommerce.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_WooCommerce.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_WooCommerce.class)
				.map(this::buildExternalSystemWooCommerceConfig);
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeWOO()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_WooCommerce.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Optional<ExternalSystemParentConfig> getShopware6ConfigByQuery(@NonNull final ExternalSystemConfigQuery query)
	{
		final IQueryBuilder<I_ExternalSystem_Config_Shopware6> queryBuilder = queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6.class);

		queryBuilder.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_ExternalSystem_Config_ID, query.getParentConfigId().getRepoId());

		if (query.getIsActive() != null)
		{
			queryBuilder.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_IsActive, query.getIsActive());
		}

		return queryBuilder
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Shopware6.class)
				.map(ex -> buildExternalSystemShopware6Config(ex, query.getParentConfigId()))
				.map(shopwareConfig -> getById(query.getParentConfigId())
						.childConfig(shopwareConfig).build());
	}

	private void storeShopware6Config(@NonNull final ExternalSystemParentConfig config)
	{
		final ExternalSystemShopware6Config configToSave = ExternalSystemShopware6Config.cast(config.getChildConfig());

		final I_ExternalSystem_Config_Shopware6 existingRecord = toShopware6Record(configToSave);
		InterfaceWrapperHelper.save(existingRecord);

		final I_ExternalSystem_Config externalSystemConfig = toRecord(config);
		InterfaceWrapperHelper.save(externalSystemConfig);
	}

	@NonNull
	private I_ExternalSystem_Config_Shopware6 toShopware6Record(@NonNull final ExternalSystemShopware6Config config)
	{
		final I_ExternalSystem_Config_Shopware6 record = InterfaceWrapperHelper.loadOrNew(config.getId(), I_ExternalSystem_Config_Shopware6.class);

		record.setBaseURL(config.getBaseUrl());
		record.setClient_Id(config.getClientId());
		record.setClient_Secret(config.getClientSecret());

		record.setJSONPathConstantBPartnerID(config.getBPartnerIdJSONPath());
		record.setJSONPathConstantBPartnerLocationID(config.getBPartnerLocationIdJSONPath());
		record.setJSONPathSalesRepID(config.getSalesRepJSONPath());

		record.setIsActive(config.getIsActive());

		if (config.getFreightCostNormalVatConfig() != null)
		{
			if (!EmptyUtil.isEmpty(config.getFreightCostNormalVatConfig().getVatRates()))
			{
				record.setFreightCost_NormalVAT_Rates(config.getFreightCostNormalVatConfig().getVatRates());
			}

			if (!EmptyUtil.isEmpty(config.getFreightCostNormalVatConfig().getProductId()))
			{
				record.setM_FreightCost_NormalVAT_Product_ID(config.getFreightCostNormalVatConfig().getProductId().getRepoId());
			}
		}

		if (config.getFreightCostReducedVatConfig() != null)
		{
			if (!EmptyUtil.isEmpty(config.getFreightCostReducedVatConfig().getVatRates()))
			{
				record.setFreightCost_Reduced_VAT_Rates(config.getFreightCostReducedVatConfig().getVatRates());
			}

			if (!EmptyUtil.isEmpty(config.getFreightCostReducedVatConfig().getProductId()))
			{
				record.setM_FreightCost_ReducedVAT_Product_ID(config.getFreightCostReducedVatConfig().getProductId().getRepoId());
			}
		}

		return record;
	}

	@NonNull
	private I_ExternalSystem_Config toRecord(@NonNull final ExternalSystemParentConfig config)
	{
		final I_ExternalSystem_Config record = InterfaceWrapperHelper.loadOrNew(config.getId(), I_ExternalSystem_Config.class);

		record.setName(config.getName());
		record.setType(config.getType().getCode());
		record.setIsActive(config.getIsActive());

		return record;
	}

	@NonNull
	public ImmutableList<ExternalSystemParentConfig> getAllByType(@NonNull final ExternalSystemType externalSystemType)
	{
		switch (externalSystemType)
		{
			case Alberta:
				return getAllByTypeAlberta();
			case RabbitMQ:
				return getAllByTypeRabbitMQ();
			case WOO:
				return getAllByTypeWOO();
			case Shopware6:
			case Other:
				throw new AdempiereException("Method not supported")
						.appendParametersToMessage()
						.setParameter("externalSystemType", externalSystemType);
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
		}
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeRabbitMQ()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_RabbitMQ_HTTP.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
	}
}
