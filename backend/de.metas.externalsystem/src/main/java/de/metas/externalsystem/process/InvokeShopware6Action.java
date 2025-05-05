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

package de.metas.externalsystem.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMapping;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMappings;
import de.metas.common.ordercandidates.v2.request.JsonOrderDocType;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.EmptyUtil;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigMapping;
import de.metas.order.impl.DocTypeService;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_BASE_PATH;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CLIENT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CLIENT_SECRET;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CONFIG_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_METASFRESH_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_SHOPWARE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_EMAIL;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_SALES_REP_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_NORMAL_VAT_RATES;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ORDER_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ORDER_NO;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ORDER_PROCESSING;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_LOOKUP;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_REDUCED_VAT_RATES;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UOM_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE;

public class InvokeShopware6Action extends InvokeExternalSystemProcess
{
	private final InvokeShopwareService invokeShopwareService = SpringContextHolder.instance.getBean(InvokeShopwareService.class);

	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	private final DocTypeService docTypeService = SpringContextHolder.instance.getBean(DocTypeService.class);

	private static final String PARAM_ORDERNO = "OrderNo";
	@Param(parameterName = PARAM_ORDERNO)
	private String orderNo;

	private static final String PARAM_ORDERID = "OrderId";
	@Param(parameterName = PARAM_ORDERID)
	private String orderId;

	@Override
	protected IExternalSystemChildConfigId getExternalChildConfigId()
	{
		final int id;

		if (this.childConfigId > 0)
		{
			id = this.childConfigId;
		}
		else
		{
			id = externalSystemConfigDAO.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()), getExternalSystemType())
					.get().getId().getRepoId();
		}

		return ExternalSystemShopware6ConfigId.ofRepoId(id);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemShopware6Config shopware6Config = ExternalSystemShopware6Config.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAM_BASE_PATH, shopware6Config.getBaseUrl());
		parameters.put(PARAM_CLIENT_SECRET, shopware6Config.getClientSecret());
		parameters.put(PARAM_CLIENT_ID, shopware6Config.getClientId());
		parameters.put(PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID, shopware6Config.getBPartnerLocationIdJSONPath());
		parameters.put(PARAM_JSON_PATH_EMAIL, shopware6Config.getEmailJSONPath());
		parameters.put(PARAM_JSON_PATH_SALES_REP_ID, shopware6Config.getSalesRepJSONPath());
		parameters.put(PARAM_CONFIG_MAPPINGS, invokeShopwareService.getConfigMappings(shopware6Config));
		parameters.put(PARAM_UOM_MAPPINGS, invokeShopwareService.getUOMMappings(shopware6Config));
		parameters.put(PARAM_CHILD_CONFIG_VALUE, shopware6Config.getValue());
		parameters.put(PARAM_PRODUCT_LOOKUP, shopware6Config.getProductLookup().getCode());
		parameters.put(PARAM_JSON_PATH_CONSTANT_METASFRESH_ID, shopware6Config.getMetasfreshIdJSONPath());
		parameters.put(PARAM_JSON_PATH_CONSTANT_SHOPWARE_ID, shopware6Config.getShopwareIdJSONPath());
		parameters.put(PARAM_ORDER_PROCESSING, shopware6Config.getOrderProcessingConfig().getCode());

		if (shopware6Config.getFreightCostNormalVatConfig() != null)
		{
			parameters.put(PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID, String.valueOf(shopware6Config.getFreightCostNormalVatConfig().getProductId().getRepoId()));
			parameters.put(PARAM_NORMAL_VAT_RATES, shopware6Config.getFreightCostNormalVatConfig().getVatRates());
		}

		if (shopware6Config.getFreightCostReducedVatConfig() != null)
		{
			parameters.put(PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID, String.valueOf(shopware6Config.getFreightCostReducedVatConfig().getProductId().getRepoId()));
			parameters.put(PARAM_REDUCED_VAT_RATES, shopware6Config.getFreightCostReducedVatConfig().getVatRates());
		}

		if (getSinceParameterValue() != null)
		{
			parameters.put(PARAM_UPDATED_AFTER_OVERRIDE, getSinceParameterValue().toInstant().toString());
		}

		if (EmptyUtil.isNotBlank(orderNo))
		{
			parameters.put(PARAM_ORDER_NO, orderNo);
		}

		if (EmptyUtil.isNotBlank(orderId))
		{
			parameters.put(PARAM_ORDER_ID, orderId);
		}

		parameters.putAll(invokeShopwareService.getPriceListParams(shopware6Config.getPriceListId()));

		return parameters;
	}

	@NonNull
	protected String getTabName()
	{
		return ExternalSystemType.Shopware6.getName();
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_Shopware6.Table_Name.equals(recordRef.getTableName()))
				.count();
	}

	@NonNull
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Shopware6;
	}

	@NonNull
	private String getConfigMappings(final ExternalSystemShopware6Config shopware6Config)
	{
		try
		{
			final List<JsonExternalSystemShopware6ConfigMapping> externalSystemShopware6ConfigMappings =
					shopware6Config.getExternalSystemShopware6ConfigMappingList()
							.stream()
							.map(this::toJsonExternalSystemShopware6ConfigMapping)
							.collect(ImmutableList.toImmutableList());

			final JsonExternalSystemShopware6ConfigMappings shopware6ConfigMappings = JsonExternalSystemShopware6ConfigMappings.builder()
					.jsonExternalSystemShopware6ConfigMappingList(externalSystemShopware6ConfigMappings)
					.build();

			return new ObjectMapper().writeValueAsString(shopware6ConfigMappings);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Shopware6 config mappings serialization failed! Shopware6 config id: " + shopware6Config.getId().getRepoId());
		}
	}

	private JsonExternalSystemShopware6ConfigMapping toJsonExternalSystemShopware6ConfigMapping(
			@NonNull final ExternalSystemShopware6ConfigMapping externalSystemShopware6ConfigMapping)
	{
		final SyncAdvise bPartnerSyncAdvice = SyncAdvise.builder()
				.ifExists(SyncAdvise.IfExists.valueOf(externalSystemShopware6ConfigMapping.getBpartnerIfExists()))
				.ifNotExists(SyncAdvise.IfNotExists.valueOf(externalSystemShopware6ConfigMapping.getBpartnerIfNotExists()))
				.build();

		final SyncAdvise bpartnerLocationSyncAdvice = SyncAdvise.builder()
				.ifExists(SyncAdvise.IfExists.valueOf(externalSystemShopware6ConfigMapping.getBpartnerLocationIfExists()))
				.ifNotExists(SyncAdvise.IfNotExists.valueOf(externalSystemShopware6ConfigMapping.getBpartnerLocationIfNotExists()))
				.build();


		final JsonExternalSystemShopware6ConfigMapping.JsonExternalSystemShopware6ConfigMappingBuilder builder =
				JsonExternalSystemShopware6ConfigMapping.builder()
						.paymentRule(externalSystemShopware6ConfigMapping.getPaymentRule())
						.sw6PaymentMethod(externalSystemShopware6ConfigMapping.getSw6PaymentMethod())
						.sw6CustomerGroup(externalSystemShopware6ConfigMapping.getSw6CustomerGroup())
						.description(externalSystemShopware6ConfigMapping.getDescription())
						.seqNo(externalSystemShopware6ConfigMapping.getSeqNo())
						.invoiceEmailEnabled(externalSystemShopware6ConfigMapping.getIsInvoiceEmailEnabled())
						.bPartnerSyncAdvice(bPartnerSyncAdvice)
						.bPartnerLocationSyncAdvice(bpartnerLocationSyncAdvice);

		final JsonOrderDocType orderDocType = docTypeService
				.getOrderDocType(externalSystemShopware6ConfigMapping.getDocTypeOrderId())
				.orElseThrow(() -> new AdempiereException("OrderDocType was not found for Id: " + externalSystemShopware6ConfigMapping.getDocTypeOrderId()));

		builder.docTypeOrder(orderDocType.getCode());

		final PaymentTermId paymentTermId = externalSystemShopware6ConfigMapping.getPaymentTermId();
		if (paymentTermId != null)
		{
			final PaymentTerm paymentTerm = paymentTermRepository.getById(paymentTermId);
			builder.paymentTermValue(paymentTerm.getValue());

		}

		return builder.build();
	}
	
	@Override
	protected String getOrgCode(@NonNull final ExternalSystemParentConfig config)
	{
		return orgDAO.getById(config.getOrgId()).getValue();
	}
}
