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
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.impl.DocTypeBL;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Shopware6;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigId;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigMapping;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.X_C_DocType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_BASE_PATH;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CLIENT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CLIENT_SECRET;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CONFIG_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UPDATED_AFTER;

public class InvokeShopware6Action extends InvokeExternalSystemProcess
{
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);

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
	protected Map<String, String> extractExternalSystemParameters(final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemShopware6Config shopware6Config = ExternalSystemShopware6Config.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAM_BASE_PATH, shopware6Config.getBaseUrl());
		parameters.put(PARAM_CLIENT_SECRET, shopware6Config.getClientSecret());
		parameters.put(PARAM_CLIENT_ID, shopware6Config.getClientId());
		parameters.put(PARAM_UPDATED_AFTER, extractEffectiveSinceTimestamp().toInstant().toString());
		parameters.put(PARAM_JSON_PATH_CONSTANT_BPARTNER_ID, shopware6Config.getBPartnerIdJSONPath());
		parameters.put(PARAM_JSON_PATH_CONSTANT_BPARTNER_LOCATION_ID, shopware6Config.getBPartnerLocationIdJSONPath());
		parameters.put(PARAM_CONFIG_MAPPINGS,getConfigMappings(shopware6Config));

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
	private String getConfigMappings(final ExternalSystemShopware6Config shopware6Config){

		final String configMappings;
		try
		{
			final List<JsonExternalSystemShopware6ConfigMapping> externalSystemShopware6ConfigMappings =
					shopware6Config.getExternalSystemShopware6ConfigMappingList()
					.stream()
					.map(this::toJsonExternalSystemShopware6ConfigMapping)
					.collect(ImmutableList.toImmutableList());

			final ObjectMapper objectMapper = new ObjectMapper();
			configMappings= objectMapper.writeValueAsString(externalSystemShopware6ConfigMappings);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Shopware6 config mappings serialization failed! Shopware6 config id: " + shopware6Config.getId().getRepoId());
		}

		return configMappings;
	}

	private JsonExternalSystemShopware6ConfigMapping toJsonExternalSystemShopware6ConfigMapping(
			@NonNull final ExternalSystemShopware6ConfigMapping externalSystemShopware6ConfigMapping)
	{
		final JsonExternalSystemShopware6ConfigMapping.JsonExternalSystemShopware6ConfigMappingBuilder builder =
				JsonExternalSystemShopware6ConfigMapping.builder()
						.paymentRule(externalSystemShopware6ConfigMapping.getPaymentRule())
						.sw6PaymentMethod(externalSystemShopware6ConfigMapping.getSw6PaymentMethod())
						.sw6CustomerGroup(externalSystemShopware6ConfigMapping.getSw6CustomerGroup())
						.description(externalSystemShopware6ConfigMapping.getDescription())
						.seqNo(externalSystemShopware6ConfigMapping.getSeqNo());

		final I_C_DocType docType = docTypeBL.getById(externalSystemShopware6ConfigMapping.getDocTypeOrderId());

		if(!X_C_DocType.DOCBASETYPE_SalesOrder.equals(docType.getDocBaseType()))
		{
			throw new AdempiereException("Invalid base doc type!");
		}

		builder.docTypeOrder(docType.getDocSubType());

		final PaymentTermId paymentTermId = externalSystemShopware6ConfigMapping.getPaymentTermId();
		if(paymentTermId != null)
		{
			final I_C_PaymentTerm paymentTerm = paymentTermRepository.getById(paymentTermId);
			builder.paymentTerm(paymentTerm.getValue());

		}

		return builder.build();
	}
}
