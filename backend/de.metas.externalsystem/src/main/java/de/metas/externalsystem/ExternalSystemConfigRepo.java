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
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.StringUtils;
import de.metas.document.DocTypeId;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfig;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.amazon.ExternalSystemAmazonConfig;
import de.metas.externalsystem.amazon.ExternalSystemAmazonConfigId;
import de.metas.externalsystem.ebay.ApiMode;
import de.metas.externalsystem.ebay.ExternalSystemEbayConfig;
import de.metas.externalsystem.ebay.ExternalSystemEbayConfigId;
import de.metas.externalsystem.ebay.ExternalSystemEbayConfigMapping;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfig;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfigId;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfig;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigId;
import de.metas.externalsystem.metasfresh.ExternalSystemMetasfreshConfig;
import de.metas.externalsystem.metasfresh.ExternalSystemMetasfreshConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Amazon;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Ebay_Mapping;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Metasfresh;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ProCareManagement;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ProCareManagement_LocalFile;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ProCareManagement_TaxCategory;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_Acct_Export;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_LocalFile;
import de.metas.externalsystem.model.I_ExternalSystem_Config_SAP_SFTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6Mapping;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6_UOM;
import de.metas.externalsystem.model.I_ExternalSystem_Config_WooCommerce;
import de.metas.externalsystem.model.I_SAP_BPartnerImportSettings;
import de.metas.externalsystem.other.ExternalSystemOtherConfig;
import de.metas.externalsystem.other.ExternalSystemOtherConfigId;
import de.metas.externalsystem.other.ExternalSystemOtherConfigRepository;
import de.metas.externalsystem.pcm.ExternalSystemPCMConfig;
import de.metas.externalsystem.pcm.ExternalSystemPCMConfigId;
import de.metas.externalsystem.pcm.PCMConfigMapper;
import de.metas.externalsystem.pcm.TaxCategoryPCMMapping;
import de.metas.externalsystem.pcm.source.PCMContentSourceLocalFile;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfig;
import de.metas.externalsystem.rabbitmqhttp.ExternalSystemRabbitMQConfigId;
import de.metas.externalsystem.sap.ExternalSystemSAPConfig;
import de.metas.externalsystem.sap.ExternalSystemSAPConfigId;
import de.metas.externalsystem.sap.SAPConfigMapper;
import de.metas.externalsystem.sap.export.SAPExportAcctConfig;
import de.metas.externalsystem.sap.importsettings.SAPBPartnerImportSettings;
import de.metas.externalsystem.sap.source.SAPContentSourceLocalFile;
import de.metas.externalsystem.sap.source.SAPContentSourceSFTP;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigMapping;
import de.metas.externalsystem.shopware6.OrderProcessingConfig;
import de.metas.externalsystem.shopware6.ProductLookup;
import de.metas.externalsystem.shopware6.UOMShopwareMapping;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfig;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfigId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.tax.TaxCategoryDAO;
import de.metas.process.AdProcessId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.user.UserGroupId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ExternalSystemConfigRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ExternalSystemOtherConfigRepository externalSystemOtherConfigRepository;
	private final TaxCategoryDAO taxCategoryDAO;

	public ExternalSystemConfigRepo(
			@NonNull final ExternalSystemOtherConfigRepository externalSystemOtherConfigRepository,
			@NonNull final TaxCategoryDAO taxCategoryDAO)
	{
		this.externalSystemOtherConfigRepository = externalSystemOtherConfigRepository;
		this.taxCategoryDAO = taxCategoryDAO;
	}

	public boolean isAnyConfigActive(final @NonNull ExternalSystemType type)
	{
		return getActiveByType(type)
				.stream()
				.anyMatch(ExternalSystemParentConfig::isActive);
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
			case Ebay:
				return getById(ExternalSystemEbayConfigId.cast(id));
			case RabbitMQ:
				return getById(ExternalSystemRabbitMQConfigId.cast(id));
			case WOO:
				return getById(ExternalSystemWooCommerceConfigId.cast(id));
			case GRSSignum:
				return getById(ExternalSystemGRSSignumConfigId.cast(id));
			case LeichUndMehl:
				return getById(ExternalSystemLeichMehlConfigId.cast(id));
			case SAP:
				return getById(ExternalSystemSAPConfigId.cast(id));
			case Metasfresh:
				return getById(ExternalSystemMetasfreshConfigId.cast(id));
			case Amazon:
				return getById(ExternalSystemAmazonConfigId.cast(id));
			case ProCareManagement:
				return getById(ExternalSystemPCMConfigId.cast(id));
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

			case Ebay:
				return getEbayConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case WOO:
				return getWooCommerceConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case GRSSignum:
				return getGRSSignumConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case RabbitMQ:
				return getRabbitMQConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case ProCareManagement:
				return getPCMConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case LeichUndMehl:
				return getLeichMehlConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case SAP:
				return getSAPConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case Other:
				return Optional.of(getExternalSystemParentConfigByValue(value));
			case Metasfresh:
				return getMetasfreshConfigByValue(value)
						.map(this::getExternalSystemParentConfig);
			case Amazon:
				return getAmazonConfigByValue(value)
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
			case Ebay:
				return getEbayConfigByParentId(id);
			case RabbitMQ:
				return getRabbitMQConfigByParentId(id);
			case WOO:
				return getWooCommerceConfigByParentId(id);
			case GRSSignum:
				return getGRSSignumConfigByParentId(id);
			case LeichUndMehl:
				return getLeichMehlConfigByParentId(id);
			case SAP:
				return getSAPConfigByParentId(id);
			case Metasfresh:
				return getMetasfreshConfigByParentId(id);
			case Amazon:
				return getAmazonConfigByParentId(id);
			case ProCareManagement:
				return getPCMConfigByParentId(id);
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

	/**
	 * @return the configs if both their parent and child records have {@code IsActive='Y'}
	 */
	@NonNull
	public ImmutableList<ExternalSystemParentConfig> getActiveByType(@NonNull final ExternalSystemType externalSystemType)
	{
		final ImmutableList<ExternalSystemParentConfig> result;

		switch (externalSystemType)
		{
			case Alberta:
				result = getAllByTypeAlberta();
				break;
			case RabbitMQ:
				result = getAllByTypeRabbitMQ();
				break;
			case WOO:
				result = getAllByTypeWOO();
				break;
			case GRSSignum:
				result = getAllByTypeGRS();
				break;
			case ProCareManagement:
				result = getAllByTypePCM();
				break;
			case LeichUndMehl:
				result = getAllByTypeLeichMehl();
				break;
			case Shopware6:
				result = getAllByTypeShopware6();
				break;
			case SAP:
				result = getAllByTypeSAP();
				break;
			case Metasfresh:
				result = getAllByTypeMetasfresh();
				break;
			case Amazon:
				result = null;
				break;
			case Other:
				result = getAllByTypeOther();
				break;
			default:
				throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
		}

		return result
				.stream()
				.filter(ExternalSystemParentConfig::isActive)
				.collect(ImmutableList.toImmutableList());
	}

	public void saveConfig(@NonNull final ExternalSystemParentConfig config)
	{
		switch (config.getType())
		{
			case Shopware6:
				storeShopware6Config(config);
				break;
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
			case Alberta:
				return getAlbertaConfigByQuery(query);
			case Shopware6:
				return getShopware6ConfigByQuery(query);
			case Ebay:
				return getEbayConfigByQuery(query);
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
				.isAutoSendWhenCreatedByUserGroup(rabbitMQConfigRecord.isAutoSendWhenCreatedByUserGroup())
				.userGroupId(UserGroupId.ofRepoIdOrNull(rabbitMQConfigRecord.getSubjectCreatedByUserGroup_ID()))
				.isSyncExternalReferencesToRabbitMQ(rabbitMQConfigRecord.isSyncExternalReferencesToRabbitMQ())
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
				.baseUrl(config.getBaseURL())
				.bPartnerLocationIdJSONPath(config.getJSONPathConstantBPartnerLocationID())
				.clientId(config.getClient_Id())
				.clientSecret(config.getClient_Secret())
				.emailJSONPath(config.getJSONPathEmail())
				.externalSystemShopware6ConfigMappingList(getExternalSystemShopware6ConfigMappingList(externalSystemShopware6ConfigId))
				.id(externalSystemShopware6ConfigId)
				.isActive(config.isActive())
				.metasfreshIdJSONPath(config.getJSONPathMetasfreshID())
				.parentId(parentConfigId)
				.priceListId(PriceListId.ofRepoIdOrNull(config.getM_PriceList_ID()))
				.productLookup(ProductLookup.ofCode(config.getProductLookup()))
				.salesRepJSONPath(config.getJSONPathSalesRepID())
				.shopwareIdJSONPath(config.getJSONPathShopwareID())
				.uomShopwareMappingList(getUOMShopwareMappingList(externalSystemShopware6ConfigId))
				.value(config.getExternalSystemValue())
				.productLookup(ProductLookup.ofCode(config.getProductLookup()))
				.metasfreshIdJSONPath(config.getJSONPathMetasfreshID())
				.shopwareIdJSONPath(config.getJSONPathShopwareID())
				.syncAvailableForSalesToShopware6(config.isSyncAvailableForSalesToShopware6())
				.percentageToDeductFromAvailableForSales(Percent.ofNullable(config.getPercentageOfAvailableForSalesToSync()))
				.orderProcessingConfig(OrderProcessingConfig.ofCode(config.getOrderProcessing()))
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
				.bpartnerIfExists(record.getBPartner_IfExists())
				.bpartnerIfNotExists(record.getBPartner_IfNotExists())
				.bpartnerLocationIfExists(record.getBPartnerLocation_IfExists())
				.bpartnerLocationIfNotExists(record.getBPartnerLocation_IfNotExists())
				.description(record.getDescription())
				.docTypeOrderId(record.getC_DocTypeOrder_ID())
				.isInvoiceEmailEnabled(StringUtils.toBoolean(record.getIsInvoiceEmailEnabled(), null))
				.paymentRule(record.getPaymentRule())
				.paymentTermId(record.getC_PaymentTerm_ID())
				.seqNo(record.getSeqNo())
				.sw6CustomerGroup(record.getSW6_Customer_Group())
				.sw6PaymentMethod(record.getSW6_Payment_Method())
				.build();
	}

	private ExternalSystemParentConfig.ExternalSystemParentConfigBuilder getById(final @NonNull ExternalSystemParentConfigId id)
	{
		final I_ExternalSystem_Config externalSystemConfigRecord = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config.class);

		return ExternalSystemParentConfig.builder()
				.type(ExternalSystemType.ofCode(externalSystemConfigRecord.getType()))
				.id(ExternalSystemParentConfigId.ofRepoId(externalSystemConfigRecord.getExternalSystem_Config_ID()))
				.name(externalSystemConfigRecord.getName())
				.orgId(OrgId.ofRepoId(externalSystemConfigRecord.getAD_Org_ID()))
				.active(externalSystemConfigRecord.isActive())
				.writeAudit(externalSystemConfigRecord.isWriteAudit())
				.auditFileFolder(externalSystemConfigRecord.getAuditFileFolder());
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
	private Optional<IExternalSystemChildConfig> getEbayConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Ebay.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Ebay.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Ebay.class)
				.map(this::buildExternalSystemEbayConfig);
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_Ebay> getEbayConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Ebay.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Ebay.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Ebay.class);
	}

	@NonNull
	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemEbayConfigId id)
	{
		final I_ExternalSystem_Config_Ebay config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_Ebay.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_Ebay config)
	{
		final ExternalSystemEbayConfig child = buildExternalSystemEbayConfig(config);

		return getById(child.getParentId())
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemEbayConfig buildExternalSystemEbayConfig(@NonNull final I_ExternalSystem_Config_Ebay config)
	{

		final ExternalSystemEbayConfigId externalSystemEbayConfigId =
				ExternalSystemEbayConfigId.ofRepoId(config.getExternalSystem_Config_Ebay_ID());

		return ExternalSystemEbayConfig.builder()
				.id(ExternalSystemEbayConfigId.ofRepoId(config.getExternalSystem_Config_Ebay_ID()))
				.parentId(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.externalSystemEbayConfigMappingList(getExternalSystemEbayConfigMappingList(externalSystemEbayConfigId))
				.appId(config.getAppId())
				.certId(config.getCertId())
				.devId(config.getDevId())
				.refreshToken(config.getRefreshToken())
				.apiMode(ApiMode.valueOf(config.getAPI_Mode()))
				.value(config.getExternalSystemValue())
				.priceListId(PriceListId.ofRepoIdOrNull(config.getM_PriceList_ID()))
				.isActive(config.isActive())
				.build();
	}

	private Optional<ExternalSystemParentConfig> getEbayConfigByQuery(@NonNull final ExternalSystemConfigQuery query)
	{
		final IQueryBuilder<I_ExternalSystem_Config_Ebay> queryBuilder = queryBL.createQueryBuilder(I_ExternalSystem_Config_Ebay.class);

		queryBuilder.addEqualsFilter(I_ExternalSystem_Config_Ebay.COLUMNNAME_ExternalSystem_Config_ID, query.getParentConfigId().getRepoId());

		if (query.getIsActive() != null)
		{
			queryBuilder.addEqualsFilter(I_ExternalSystem_Config_Ebay.COLUMNNAME_IsActive, query.getIsActive());
		}

		return queryBuilder
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Ebay.class)
				.map(ex -> buildExternalSystemEbayConfig(ex))
				.map(shopwareConfig -> getById(query.getParentConfigId())
						.childConfig(shopwareConfig).build());
	}

	private List<ExternalSystemEbayConfigMapping> getExternalSystemEbayConfigMappingList(@NonNull final ExternalSystemEbayConfigId externalSystemEbayConfigId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Ebay_Mapping.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Ebay_Mapping.COLUMNNAME_ExternalSystem_Config_Ebay_ID, externalSystemEbayConfigId)
				.create()
				.list()
				.stream()
				.map(this::toExternalSystemEbayConfigMapping)
				.collect(ImmutableList.toImmutableList());

	}

	@NonNull
	private ExternalSystemEbayConfigMapping toExternalSystemEbayConfigMapping(@NonNull final I_ExternalSystem_Config_Ebay_Mapping record)
	{
		return ExternalSystemEbayConfigMapping.builder()
				.docTypeOrderId(record.getC_DocTypeOrder_ID())
				.paymentRule(record.getPaymentRule())
				.paymentTermId(record.getC_PaymentTerm_ID())
				.ebayCustomerGroup(record.getEBayCustomerGroup())
				.ebayPaymentMethod(record.getEBayPaymentMethod())
				.description(record.getDescription())
				.seqNo(record.getSeqNo())
				.isInvoiceEmailEnabled(record.isInvoiceEmailEnabled())
				.bpartnerIfExists(record.getBPartner_IfExists())
				.bpartnerIfNotExists(record.getBPartner_IfNotExists())
				.bpartnerLocationIfExists(record.getBPartnerLocation_IfExists())
				.bpartnerLocationIfNotExists(record.getBPartnerLocation_IfNotExists())
				.build();
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
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_Metasfresh config)
	{
		final ExternalSystemMetasfreshConfig child = buildExternalSystemMetasfreshConfig(config);

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
	private ExternalSystemMetasfreshConfig buildExternalSystemMetasfreshConfig(@NonNull final I_ExternalSystem_Config_Metasfresh config)
	{
		return ExternalSystemMetasfreshConfig.builder()
				.id(ExternalSystemMetasfreshConfigId.ofRepoId(config.getExternalSystem_Config_Metasfresh_ID()))
				.parentId(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.value(config.getExternalSystemValue())
				.camelHttpResourceAuthKey(config.getCamelHttpResourceAuthKey())
				.feedbackResourceURL(config.getFeedbackResourceURL())
				.feedbackResourceAuthToken(config.getFeedbackResourceAuthToken())
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
	private Optional<ExternalSystemParentConfig> getAlbertaConfigByQuery(@NonNull final ExternalSystemConfigQuery query)
	{
		final IQueryBuilder<I_ExternalSystem_Config_Alberta> queryBuilder = queryBL.createQueryBuilder(I_ExternalSystem_Config_Alberta.class);

		queryBuilder.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_ExternalSystem_Config_ID, query.getParentConfigId().getRepoId());

		if (query.getIsActive() != null)
		{
			queryBuilder.addEqualsFilter(I_ExternalSystem_Config_Shopware6.COLUMNNAME_IsActive, query.getIsActive());
		}

		return queryBuilder
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Alberta.class)
				.map(ex -> buildExternalSystemAlbertaConfig(ex, query.getParentConfigId()))
				.map(shopwareConfig -> getById(query.getParentConfigId())
						.childConfig(shopwareConfig).build());
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

		record.setJSONPathConstantBPartnerLocationID(config.getBPartnerLocationIdJSONPath());
		record.setJSONPathSalesRepID(config.getSalesRepJSONPath());

		record.setM_PriceList_ID(NumberUtils.asInteger(config.getPriceListId(), -1));
		record.setIsActive(config.getIsActive());
		record.setExternalSystemValue(config.getValue());

		record.setProductLookup(config.getProductLookup().getCode());

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
		record.setIsActive(config.isActive());

		return record;
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getMetasfreshConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Metasfresh.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Metasfresh.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Metasfresh.class)
				.map(this::buildExternalSystemMetasfreshConfig);
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getGRSSignumConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_GRSSignum.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_GRSSignum.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_GRSSignum.class)
				.map(this::buildExternalSystemGRSSignumConfig);
	}

	@NonNull
	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemGRSSignumConfigId id)
	{
		final I_ExternalSystem_Config_GRSSignum config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_GRSSignum.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemMetasfreshConfigId id)
	{
		final I_ExternalSystem_Config_Metasfresh config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_Metasfresh.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_GRSSignum config)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final ExternalSystemGRSSignumConfig child = buildExternalSystemGRSSignumConfig(config);

		return getById(parentConfigId)
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemGRSSignumConfig buildExternalSystemGRSSignumConfig(@NonNull final I_ExternalSystem_Config_GRSSignum config)
	{
		return ExternalSystemGRSSignumConfig.builder()
				.id(ExternalSystemGRSSignumConfigId.ofRepoId(config.getExternalSystem_Config_GRSSignum_ID()))
				.parentId(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.value(config.getExternalSystemValue())
				.camelHttpResourceAuthKey(config.getCamelHttpResourceAuthKey())
				.baseUrl(config.getBaseURL())
				.tenantId(config.getTenantId())
				.authToken(config.getAuthToken())
				.syncBPartnersToRestEndpoint(config.isSyncBPartnersToRestEndpoint())
				.autoSendVendors(config.isAutoSendVendors())
				.autoSendCustomers(config.isAutoSendCustomers())
				.syncHUsOnMaterialReceipt(config.isSyncHUsOnMaterialReceipt())
				.syncHUsOnProductionReceipt(config.isSyncHUsOnProductionReceipt())
				.basePathForExportDirectories(config.getBasePathForExportDirectories())
				.createBPartnerFolders(config.isCreateBPartnerFolders())
				.bPartnerExportDirectories(config.getBPartnerExportDirectories())
				.build();
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_GRSSignum> getGRSSignumConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_GRSSignum.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_GRSSignum.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_GRSSignum.class);
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_Metasfresh> getMetasfreshConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Metasfresh.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Metasfresh.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Metasfresh.class);
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeGRS()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_GRSSignum.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeShopware6()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
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

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeAmazon()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6.class) // todo generate your models !!!
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeMetasfresh()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Metasfresh.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeOther()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config.COLUMNNAME_Type, ExternalSystemType.Other.getCode())
				.create()
				.stream()
				.map(I_ExternalSystem_Config::getExternalSystem_Config_ID)
				.map(ExternalSystemParentConfigId::ofRepoId)
				.map(ExternalSystemOtherConfigId::ofExternalSystemParentConfigId)
				.map(this::getById)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private List<UOMShopwareMapping> getUOMShopwareMappingList(@NonNull final ExternalSystemShopware6ConfigId externalSystemShopware6ConfigId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Shopware6_UOM.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Shopware6_UOM.COLUMNNAME_ExternalSystem_Config_Shopware6_ID, externalSystemShopware6ConfigId.getRepoId())
				.create()
				.stream()
				.map(this::toUOMShopwareMapping)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private List<TaxCategoryPCMMapping> getTaxCategoryPCMMappingList(@NonNull final ExternalSystemPCMConfigId externalSystemPCMConfigId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_ProCareManagement_TaxCategory.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_ProCareManagement_TaxCategory.COLUMN_ExternalSystem_Config_ProCareManagement_ID, externalSystemPCMConfigId.getRepoId())
				.create()
				.stream()
				.map(this::toTaxCategoryPCMMapping)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private TaxCategoryPCMMapping toTaxCategoryPCMMapping(@NonNull final I_ExternalSystem_Config_ProCareManagement_TaxCategory record)
	{
		final ImmutableSet<BigDecimal> taxRates = Arrays.stream(record.getTaxRates().split(","))
				.map(StringUtils::toBigDecimalOrZero)
				.collect(ImmutableSet.toImmutableSet());

		return TaxCategoryPCMMapping.builder()
				.externalSystemPCMConfigId(ExternalSystemPCMConfigId.ofRepoId(record.getExternalSystem_Config_ProCareManagement_ID()))
				.taxCategory(taxCategoryDAO.getTaxCategory(TaxCategoryId.ofRepoId(record.getC_TaxCategory_ID())))
				.taxRates(taxRates)
				.build();
	}

	@NonNull
	private UOMShopwareMapping toUOMShopwareMapping(@NonNull final I_ExternalSystem_Config_Shopware6_UOM record)
	{
		return UOMShopwareMapping.builder()
				.externalSystemShopware6ConfigId(ExternalSystemShopware6ConfigId.ofRepoId(record.getExternalSystem_Config_Shopware6_ID()))
				.uomId(UomId.ofRepoId(record.getC_UOM_ID()))
				.shopwareCode(record.getShopwareCode())
				.build();
	}

	@NonNull
	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemLeichMehlConfigId id)
	{
		final I_ExternalSystem_Config_LeichMehl config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_LeichMehl.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_LeichMehl> getLeichMehlConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_LeichMehl.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_LeichMehl.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_LeichMehl.class);
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getLeichMehlConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_LeichMehl.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_LeichMehl.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_LeichMehl.class)
				.map(this::buildExternalSystemLeichMehlConfig);
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeLeichMehl()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_LeichMehl.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_LeichMehl config)
	{
		final ExternalSystemLeichMehlConfig child = buildExternalSystemLeichMehlConfig(config);

		return getById(child.getParentId())
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemLeichMehlConfig buildExternalSystemLeichMehlConfig(@NonNull final I_ExternalSystem_Config_LeichMehl config)
	{
		final ExternalSystemLeichMehlConfigId id = ExternalSystemLeichMehlConfigId.ofRepoId(config.getExternalSystem_Config_LeichMehl_ID());

		return ExternalSystemLeichMehlConfig.builder()
				.id(id)
				.parentId(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.value(config.getExternalSystemValue())
				.productBaseFolderName(config.getProduct_BaseFolderName())
				.tcpPort(config.getTCP_PortNumber())
				.tcpHost(config.getTCP_Host())
				.pluFileExportAuditEnabled(config.isPluFileExportAuditEnabled())
				.build();
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfigByValue(@NonNull final String value)
	{
		final ExternalSystemOtherConfig childConfig = externalSystemOtherConfigRepository.getByValue(value);

		return getById(childConfig.getId().getExternalSystemParentConfigId())
				.childConfig(childConfig)
				.build();
	}

	@NonNull
	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemSAPConfigId id)
	{
		final I_ExternalSystem_Config_SAP config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_SAP.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_SAP config)
	{
		final ExternalSystemSAPConfig child = buildExternalSystemSAPConfig(config);

		return getById(child.getParentId())
				.childConfig(child)
				.build();
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_SAP> getSAPConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_SAP.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_SAP.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_SAP.class);
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getSAPConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_SAP.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_SAP.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_SAP.class)
				.map(this::buildExternalSystemSAPConfig);
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypeSAP()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_SAP.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ExternalSystemSAPConfig buildExternalSystemSAPConfig(final @NonNull I_ExternalSystem_Config_SAP config)
	{
		final ExternalSystemSAPConfigId sapConfigId = ExternalSystemSAPConfigId.ofRepoId(config.getExternalSystem_Config_SAP_ID());

		final SAPContentSourceSFTP contentSourceSFTP = getContentSourceSFTPByConfigId(sapConfigId).orElse(null);
		final SAPContentSourceLocalFile contentSourceLocalFile = getContentSourceLocalFileByConfigId(sapConfigId).orElse(null);

		return ExternalSystemSAPConfig.builder()
				.id(ExternalSystemSAPConfigId.ofRepoId(config.getExternalSystem_Config_SAP_ID()))
				.parentId(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.value(config.getExternalSystemValue())
				.contentSourceSFTP(contentSourceSFTP)
				.contentSourceLocalFile(contentSourceLocalFile)
				.checkDescriptionForMaterialType(config.isCheckDescriptionForMaterialType())
				.baseURL(config.getBaseURL())
				.apiVersion(config.getApiVersion())
				.postAcctDocumentsPath(config.getPost_Acct_Documents_Path())
				.signature(config.getSignatureSAS())
				.signedVersion(config.getSignedVersion())
				.signedPermissions(config.getSignedPermissions())
				.exportAcctConfigList(getSAPAcctConfigBySAPConfigId(sapConfigId))
				.bPartnerImportSettings(getBPartnerImportSettingsBySAPConfigId(sapConfigId))
				.build();
	}

	@NonNull
	private Optional<SAPContentSourceSFTP> getContentSourceSFTPByConfigId(@NonNull final ExternalSystemSAPConfigId configId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_SAP_SFTP.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_SAP_SFTP.COLUMNNAME_ExternalSystem_Config_SAP_ID, configId.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_SAP_SFTP.class)
				.map(SAPConfigMapper::buildContentSourceSFTP);
	}

	@NonNull
	private Optional<SAPContentSourceLocalFile> getContentSourceLocalFileByConfigId(@NonNull final ExternalSystemSAPConfigId configId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_SAP_LocalFile.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_SAP_LocalFile.COLUMNNAME_ExternalSystem_Config_SAP_ID, configId.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_SAP_LocalFile.class)
				.map(SAPConfigMapper::buildContentSourceLocalFile);
	}

	@NonNull
	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemAmazonConfigId id)
	{
		final I_ExternalSystem_Config_Amazon config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_Amazon.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_Amazon> getAmazonConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Amazon.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Amazon.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Amazon.class);
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getAmazonConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_Amazon.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_Amazon.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_Amazon.class)
				.map(ex -> buildExternalSystemAmazonConfig(ex, id));
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_Amazon config)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final ExternalSystemAmazonConfig child = buildExternalSystemAmazonConfig(config, parentConfigId);

		return getById(parentConfigId)
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemAmazonConfig buildExternalSystemAmazonConfig(
			@NonNull final I_ExternalSystem_Config_Amazon config,
			@NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		final ExternalSystemAmazonConfigId externalSystemAmazonConfigId =
				ExternalSystemAmazonConfigId.ofRepoId(config.getExternalSystem_Config_Amazon_ID());

		final ExternalSystemAmazonConfig.ExternalSystemAmazonConfigBuilder configBuilder = ExternalSystemAmazonConfig.builder();

		return configBuilder
				.id(externalSystemAmazonConfigId)
				.parentId(parentConfigId)
				.value(config.getExternalSystemValue())
				.name(config.getName())
				.basePath(config.getBasePath())
				.accessKeyId(config.getAccessKeyId())
				.clientId(config.getClientID())
				.clientSecret(config.getClientSecret())
				.lwaEndpoint(config.getLWAEndpoint())
				.secretKey(config.getSecretKey())
				.refreshToken(config.getRefreshToken())
				.regionName(config.getRegionName())
				.roleArn(config.getRoleArn())
				.debugProtocol(config.isDebugProtocol())
				.active(config.isActive())
				.build();
	}

	@NonNull
	private ImmutableList<SAPExportAcctConfig> getSAPAcctConfigBySAPConfigId(@NonNull final ExternalSystemSAPConfigId configId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_SAP_Acct_Export.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_SAP_Acct_Export.COLUMNNAME_ExternalSystem_Config_SAP_ID, configId.getRepoId())
				.create()
				.stream()
				.map(exportConfigRecord -> SAPExportAcctConfig.builder()
						.docTypeId(DocTypeId.ofRepoId(exportConfigRecord.getC_DocType_ID()))
						.processId(AdProcessId.ofRepoId(exportConfigRecord.getAD_Process_ID()))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableList<SAPBPartnerImportSettings> getBPartnerImportSettingsBySAPConfigId(@NonNull final ExternalSystemSAPConfigId configId)
	{
		return queryBL.createQueryBuilder(I_SAP_BPartnerImportSettings.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_SAP_BPartnerImportSettings.COLUMNNAME_ExternalSystem_Config_SAP_ID, configId.getRepoId())
				.create()
				.stream()
				.map(SAPConfigMapper::ofBPartnerImportSettingsRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ExternalSystemParentConfig getById(@NonNull final ExternalSystemPCMConfigId id)
	{
		final I_ExternalSystem_Config_ProCareManagement config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_ProCareManagement.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_ProCareManagement config)
	{
		final ExternalSystemPCMConfig child = buildExternalSystemPCMConfig(config);

		return getById(child.getParentId())
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemPCMConfig buildExternalSystemPCMConfig(@NonNull final I_ExternalSystem_Config_ProCareManagement config)
	{
		final ExternalSystemPCMConfigId pcmConfigId = ExternalSystemPCMConfigId.ofRepoId(config.getExternalSystem_Config_ProCareManagement_ID());

		final PCMContentSourceLocalFile contentSourceLocalFile = getContentSourceLocalFileByConfigId(pcmConfigId).orElse(null);

		final OrgId orgId = OrgId.ofRepoId(config.getAD_Org_ID());
		
		// we need this to find the org for the orders, warehouses etc
		Check.errorUnless(orgId.isRegular(), "AD_Org_ID of ExternalSystem_Config_ProCareManagement_ID={0} (ExternalSystem_Config_ID={1}) may not be 0!", config.getExternalSystem_Config_ProCareManagement_ID(), config.getExternalSystem_Config_ID());

		return ExternalSystemPCMConfig.builder()
				.id(pcmConfigId)
				.orgId(orgId)
				.parentId(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.value(config.getExternalSystemValue())
				.contentSourceLocalFile(contentSourceLocalFile)
				.taxCategoryPCMMappingList(getTaxCategoryPCMMappingList(pcmConfigId))
				.build();
	}

	@NonNull
	private Optional<PCMContentSourceLocalFile> getContentSourceLocalFileByConfigId(@NonNull final ExternalSystemPCMConfigId configId)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_ProCareManagement_LocalFile.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_ProCareManagement_LocalFile.COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, configId.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_ProCareManagement_LocalFile.class)
				.map(PCMConfigMapper::buildContentSourceLocalFile);
	}

	@NonNull
	private Optional<IExternalSystemChildConfig> getPCMConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_ProCareManagement.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_ProCareManagement.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_ProCareManagement.class)
				.map(this::buildExternalSystemPCMConfig);
	}

	@NonNull
	private Optional<I_ExternalSystem_Config_ProCareManagement> getPCMConfigByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_ProCareManagement.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_ProCareManagement.COLUMNNAME_ExternalSystemValue, value)
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_ProCareManagement.class);
	}

	@NonNull
	private ImmutableList<ExternalSystemParentConfig> getAllByTypePCM()
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_ProCareManagement.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::getExternalSystemParentConfig)
				.collect(ImmutableList.toImmutableList());
	}
}
