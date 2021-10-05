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
import de.metas.JsonObjectMapperHolder;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMapping;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMappings;
import de.metas.common.externalsystem.JsonUOM;
import de.metas.common.externalsystem.JsonUOMMapping;
import de.metas.common.externalsystem.JsonUOMMappings;
import de.metas.common.ordercandidates.v2.request.JsonOrderDocType;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6Config;
import de.metas.externalsystem.shopware6.ExternalSystemShopware6ConfigMapping;
import de.metas.externalsystem.shopware6.UOMShopwareMapping;
import de.metas.order.impl.DocTypeService;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_PaymentTerm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvokeShopwareService
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	private final DocTypeService docTypeService;

	public InvokeShopwareService(@NonNull final DocTypeService docTypeService)
	{
		this.docTypeService = docTypeService;
	}

	@NonNull
	public String getUOMMappings(@NonNull final ExternalSystemShopware6Config shopware6Config)
	{
		try
		{
			final List<JsonUOMMapping> uomMappings = shopware6Config.getUomShopwareMappingList()
					.stream()
					.map(this::toJsonUOMMapping)
					.collect(ImmutableList.toImmutableList());

			final JsonUOMMappings shopware6UOMMappings = JsonUOMMappings.builder()
					.jsonUOMMappingList(uomMappings)
					.build();

			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(shopware6UOMMappings);
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("shopware6ConfigId", shopware6Config.getId().getRepoId());
		}
	}

	@NonNull
	private JsonUOMMapping toJsonUOMMapping(@NonNull final UOMShopwareMapping uomShopwareMapping)
	{
		return JsonUOMMapping.builder()
				.uom(JsonUOM.builder()
							 .id(JsonMetasfreshId.of(uomShopwareMapping.getUomId().getRepoId()))
							 .code(uomDAO.getX12DE355ById(uomShopwareMapping.getUomId()).getCode())
							 .build())
				.externalCode(uomShopwareMapping.getShopwareCode())
				.build();
	}

	@NonNull
	public String getConfigMappings(final ExternalSystemShopware6Config shopware6Config)
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
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("shopware6ConfigId", shopware6Config.getId().getRepoId());
		}
	}

	@NonNull
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
				.orElseThrow(() -> new AdempiereException("OrderDocType was not found for Id: "
																  + externalSystemShopware6ConfigMapping.getDocTypeOrderId()));

		builder.docTypeOrder(orderDocType.getCode());

		final PaymentTermId paymentTermId = externalSystemShopware6ConfigMapping.getPaymentTermId();
		if (paymentTermId != null)
		{
			final I_C_PaymentTerm paymentTerm = paymentTermRepository.getById(paymentTermId);
			builder.paymentTermValue(paymentTerm.getValue());

		}
		return builder.build();
	}
}
