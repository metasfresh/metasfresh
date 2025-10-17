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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.StringUtils;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfig;
import de.metas.externalsystem.alberta.ExternalSystemAlbertaConfigId;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfig;
import de.metas.externalsystem.grssignum.ExternalSystemGRSSignumConfigId;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfig;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigId;
import de.metas.externalsystem.leichmehl.PLUFileDestination;
import de.metas.externalsystem.leichmehl.PLUType;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Alberta;
import de.metas.externalsystem.model.I_ExternalSystem_Config_GRSSignum;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ProCareManagement;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ProCareManagement_LocalFile;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ProCareManagement_TaxCategory;
import de.metas.externalsystem.model.I_ExternalSystem_Config_RabbitMQ_HTTP;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion;
import de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedImportConversion;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6Mapping;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6_UOM;
import de.metas.externalsystem.model.I_ExternalSystem_Config_WooCommerce;
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
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfigId;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionRepository;
import de.metas.externalsystem.scriptedimportconversion.ExternalSystemScriptedImportConversionConfig;
import de.metas.externalsystem.scriptedimportconversion.ExternalSystemScriptedImportConversionConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigMapping;
import de.metas.externalsystem.shopware6.ProductLookup;
import de.metas.externalsystem.shopware6.UOMShopwareMapping;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfig;
import de.metas.externalsystem.woocommerce.ExternalSystemWooCommerceConfigId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.tax.TaxCategoryDAO;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.user.UserGroupId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExternalSystemConfigRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	private final ExternalSystemOtherConfigRepository externalSystemOtherConfigRepository;
	@NonNull
	private final TaxCategoryDAO taxCategoryDAO;
	@NonNull
	private final ExternalSystemRepository externalSystemRepository;

	@VisibleForTesting
	public static ExternalSystemConfigRepo newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ExternalSystemConfigRepo(new ExternalSystemOtherConfigRepository(), new TaxCategoryDAO(),
				ExternalSystemRepository.newInstanceForUnitTesting());
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
		// change the private methods' names to getByCastedId to avoid a StackoverflowError in case on of them gets lost -which was the case for the one with ExternalSystemPCMConfigId
		final ExternalSystemType type = id.getType();
		if (type.isAlberta())
		{
			return getByCastedId(ExternalSystemAlbertaConfigId.cast(id));
		}
		else if (type.isShopware6())
		{
			return getByCastedId(ExternalSystemShopware6ConfigId.cast(id));
		}
		else if (type.isOther())
		{
			return getByCastedId(ExternalSystemOtherConfigId.cast(id));
		}
		else if (type.isRabbitMQ())
		{
			return getByCastedId(ExternalSystemRabbitMQConfigId.cast(id));
		}
		else if (type.isWOO())
		{
			return getByCastedId(ExternalSystemWooCommerceConfigId.cast(id));
		}
		else if (type.isGRSSignum())
		{
			return getByCastedId(ExternalSystemGRSSignumConfigId.cast(id));
		}
		else if (type.isLeichUndMehl())
		{
			return getByCastedId(ExternalSystemLeichMehlConfigId.cast(id));
		}
		else if (type.isProCareManagement())
		{
			return getByCastedId(ExternalSystemPCMConfigId.cast(id));
		}
		else if (type.isScriptedExportConversion())
		{
			return getByCastedId(ExternalSystemScriptedExportConversionConfigId.cast(id));
		}
		else if (type.isScriptedImportConversion())
		{
			return getByCastedId(ExternalSystemScriptedImportConversionConfigId.cast(id));
		}
		throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", id.getType());
	}

	@NonNull
	public Optional<ExternalSystemParentConfig> getByTypeAndValue(@NonNull final ExternalSystemType type, @NonNull final String value)
	{
		if (type.isAlberta())
		{
			return getAlbertaConfigByValue(value)
					.map(this::getExternalSystemParentConfig);
		}
		else if (type.isShopware6())
		{
			return getShopware6ConfigByValue(value)
					.map(this::getExternalSystemParentConfig);
		}
		else if (type.isWOO())
		{
			return getWooCommerceConfigByValue(value)
					.map(this::getExternalSystemParentConfig);
		}
		else if (type.isGRSSignum())
		{
			return getGRSSignumConfigByValue(value)
					.map(this::getExternalSystemParentConfig);
		}
		else if (type.isRabbitMQ())
		{
			return getRabbitMQConfigByValue(value)
					.map(this::getExternalSystemParentConfig);
		}
		else if (type.isLeichUndMehl())
		{
			return getLeichMehlConfigByValue(value)
					.map(this::getExternalSystemParentConfig);
		}
		else if (type.isProCareManagement())
		{
			return getPCMConfigByValue(value)
					.map(this::getExternalSystemParentConfig);
		}
		throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", type);
	}

	@NonNull
	public Optional<IExternalSystemChildConfig> getChildByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		final ExternalSystemType type = ExternalSystemType.ofValue(getParentTypeById(id));
		return getChildByParentIdAndType(id, type);
	}

	public Optional<IExternalSystemChildConfig> getChildByParentIdAndType(
			@NonNull final ExternalSystemParentConfigId id,
			@NonNull final ExternalSystemType externalSystemType)
	{
		if (externalSystemType.isAlberta())
		{
			return getAlbertaConfigByParentId(id);
		}
		else if (externalSystemType.isShopware6())
		{
			return getShopware6ConfigByParentId(id);
		}
		else if (externalSystemType.isOther())
		{
			final ExternalSystemOtherConfigId externalSystemOtherConfigId = ExternalSystemOtherConfigId.ofExternalSystemParentConfigId(id);
			return Optional.of(externalSystemOtherConfigRepository.getById(externalSystemOtherConfigId));
		}
		else if (externalSystemType.isRabbitMQ())
		{
			return getRabbitMQConfigByParentId(id);
		}
		else if (externalSystemType.isWOO())
		{
			return getWooCommerceConfigByParentId(id);
		}
		else if (externalSystemType.isGRSSignum())
		{
			return getGRSSignumConfigByParentId(id);
		}
		else if (externalSystemType.isLeichUndMehl())
		{
			return getLeichMehlConfigByParentId(id);
		}
		else if (externalSystemType.isProCareManagement())
		{
			return getPCMConfigByParentId(id);
		}
		else if (externalSystemType.isScriptedImportConversion())
		{
			return getScriptedImportConversionConfigByParentId(id);
		}
		throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
	}

	@NonNull
	public String getParentTypeById(final @NonNull ExternalSystemParentConfigId id)
	{
		final I_ExternalSystem_Config externalSystemConfigRecord = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config.class);
		return externalSystemRepository.getById(ExternalSystemId.ofRepoId(externalSystemConfigRecord.getExternalSystem_ID())).getType().getValue();
	}

	/**
	 * @return the configs if both their parent and child records have {@code IsActive='Y'}
	 */
	@NonNull
	public ImmutableList<ExternalSystemParentConfig> getActiveByType(@NonNull final ExternalSystemType externalSystemType)
	{
		final ImmutableList<ExternalSystemParentConfig> result;

		if (externalSystemType.isAlberta())
		{
			result = getAllByTypeAlberta();
		}
		else if (externalSystemType.isRabbitMQ())
		{
			result = getAllByTypeRabbitMQ();
		}
		else if (externalSystemType.isWOO())
		{
			result = getAllByTypeWOO();
		}
		else if (externalSystemType.isGRSSignum())
		{
			result = getAllByTypeGRS();
		}
		else if (externalSystemType.isLeichUndMehl())
		{
			result = getAllByTypeLeichMehl();
		}
		else if (externalSystemType.isProCareManagement())
		{
			result = getAllByTypePCM();
		}
		else if (externalSystemType.isShopware6() || externalSystemType.isOther())
		{
			throw new AdempiereException("Method not supported")
					.appendParametersToMessage()
					.setParameter("externalSystemType", externalSystemType);
		}
		else
		{
			throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
		}

		return result
				.stream()
				.filter(ExternalSystemParentConfig::isActive)
				.collect(ImmutableList.toImmutableList());
	}

	public void saveConfig(@NonNull final ExternalSystemParentConfig config)
	{
		if (config.getType().isShopware6())
		{
			storeShopware6Config(config);
		}
		else
		{
			throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", config.getType());
		}
	}

	@NonNull
	public Optional<ExternalSystemParentConfig> getByQuery(
			@NonNull final ExternalSystemType externalSystemType,
			@NonNull final ExternalSystemConfigQuery query)
	{
		if (externalSystemType.isAlberta())
		{
			return getAlbertaConfigByQuery(query);
		}
		else if (externalSystemType.isShopware6())
		{
			return getShopware6ConfigByQuery(query);
		}
		throw Check.fail("Unsupported IExternalSystemChildConfigId.type={}", externalSystemType);
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

	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemAlbertaConfigId id)
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
	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemRabbitMQConfigId id)
	{
		final I_ExternalSystem_Config_RabbitMQ_HTTP config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_RabbitMQ_HTTP.class);

		return getExternalSystemParentConfig(config);
	}

	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemShopware6ConfigId id)
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
				.uomShopwareMappingList(getUOMShopwareMappingList(externalSystemShopware6ConfigId))
				.priceListId(PriceListId.ofRepoIdOrNull(config.getM_PriceList_ID()))
				.clientId(config.getClient_Id())
				.bPartnerLocationIdJSONPath(config.getJSONPathConstantBPartnerLocationID())
				.salesRepJSONPath(config.getJSONPathSalesRepID())
				.emailJSONPath(config.getJSONPathEmail())
				.isActive(config.isActive())
				.value(config.getExternalSystemValue())
				.productLookup(ProductLookup.ofCode(config.getProductLookup()))
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
				.type(externalSystemRepository.getById(ExternalSystemId.ofRepoId(externalSystemConfigRecord.getExternalSystem_ID())).getType())
				.id(ExternalSystemParentConfigId.ofRepoId(externalSystemConfigRecord.getExternalSystem_Config_ID()))
				.name(externalSystemConfigRecord.getName())
				.orgId(OrgId.ofRepoId(externalSystemConfigRecord.getAD_Org_ID()))
				.active(externalSystemConfigRecord.isActive())
				.writeAudit(externalSystemConfigRecord.isWriteAudit())
				.auditFileFolder(externalSystemConfigRecord.getAuditFileFolder());
	}

	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemOtherConfigId id)
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
	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemWooCommerceConfigId id)
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
		record.setExternalSystem_ID(externalSystemRepository.getByType(config.getType()).getId().getRepoId());
		record.setIsActive(config.isActive());

		return record;
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
	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemGRSSignumConfigId id)
	{
		final I_ExternalSystem_Config_GRSSignum config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_GRSSignum.class);

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
	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemLeichMehlConfigId id)
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
	private ExternalSystemLeichMehlConfig buildExternalSystemLeichMehlConfig(@NonNull final I_ExternalSystem_Config_LeichMehl configRecord)
	{
		final ExternalSystemLeichMehlConfigId id = ExternalSystemLeichMehlConfigId.ofRepoId(configRecord.getExternalSystem_Config_LeichMehl_ID());

		return ExternalSystemLeichMehlConfig.builder()
				.id(id)
				.parentId(ExternalSystemParentConfigId.ofRepoId(configRecord.getExternalSystem_Config_ID()))
				.value(configRecord.getExternalSystemValue())
				.productBaseFolderName(configRecord.getProduct_BaseFolderName())
				.pluFileDestination(PLUFileDestination.ofCode(configRecord.getPluFileDestination()))
				.tcpPort(configRecord.getTCP_PortNumber())
				.tcpHost(configRecord.getTCP_Host())
				.pluFileServerFolder(configRecord.getPluFileLocalFolder())
				.pluType(PLUType.ofCode(configRecord.getCU_TU_PLU()))
				.pluFileExportAuditEnabled(configRecord.isPluFileExportAuditEnabled())
				.build();
	}

	@NonNull
	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemPCMConfigId id)
	{
		final I_ExternalSystem_Config_ProCareManagement config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_ProCareManagement.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemScriptedExportConversionConfigId id)
	{
		final I_ExternalSystem_Config_ScriptedExportConversion config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_ScriptedExportConversion.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private ExternalSystemParentConfig getByCastedId(@NonNull final ExternalSystemScriptedImportConversionConfigId id)
	{
		final I_ExternalSystem_Config_ScriptedImportConversion config = InterfaceWrapperHelper.load(id, I_ExternalSystem_Config_ScriptedImportConversion.class);

		return getExternalSystemParentConfig(config);
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_ScriptedExportConversion config)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final ExternalSystemScriptedExportConversionConfig child = ExternalSystemScriptedExportConversionRepository.fromRecord(config, parentConfigId);

		return getById(parentConfigId)
				.childConfig(child)
				.build();
	}

	@NonNull
	private ExternalSystemParentConfig getExternalSystemParentConfig(@NonNull final I_ExternalSystem_Config_ScriptedImportConversion config)
	{
		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final ExternalSystemScriptedImportConversionConfig child = buildExternalSystemScriptedImportConversionConfig(config);

		return getById(parentConfigId)
				.childConfig(child)
				.build();
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
	private ExternalSystemScriptedImportConversionConfig buildExternalSystemScriptedImportConversionConfig(@NonNull final I_ExternalSystem_Config_ScriptedImportConversion config)
	{
		final ExternalSystemScriptedImportConversionConfigId scriptedImportConfigId = ExternalSystemScriptedImportConversionConfigId.ofRepoId(config.getExternalSystem_Config_ScriptedImportConversion_ID());

		return ExternalSystemScriptedImportConversionConfig.builder()
				.id(scriptedImportConfigId)
				.parentId(ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID()))
				.value(config.getExternalSystemValue())
				.endpointName(config.getEndpointName())
				.scriptIdentifier(config.getScriptIdentifier())
				.userImportId(UserId.ofRepoId(config.getAD_User_Import_ID()))
				.description(config.getDescription())
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
	private Optional<IExternalSystemChildConfig> getScriptedImportConversionConfigByParentId(@NonNull final ExternalSystemParentConfigId id)
	{
		return queryBL.createQueryBuilder(I_ExternalSystem_Config_ScriptedImportConversion.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ExternalSystem_Config_ScriptedImportConversion.COLUMNNAME_ExternalSystem_Config_ID, id.getRepoId())
				.create()
				.firstOnlyOptional(I_ExternalSystem_Config_ScriptedImportConversion.class)
				.map(this::buildExternalSystemScriptedImportConversionConfig);
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
