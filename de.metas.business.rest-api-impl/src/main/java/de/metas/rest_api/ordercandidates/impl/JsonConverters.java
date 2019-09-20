package de.metas.rest_api.ordercandidates.impl;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.money.CurrencyId;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandCreateRequest.OLCandCreateRequestBuilder;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.rest_api.ordercandidates.impl.ProductMasterDataProvider.ProductInfo;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.rest_api.ordercandidates.response.JsonOLCand;
import de.metas.rest_api.ordercandidates.response.JsonOLCandCreateBulkResponse;
import de.metas.rest_api.ordercandidates.response.JsonResponseBPartnerLocationAndContact;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
class JsonConverters
{
	public final OLCandCreateRequestBuilder fromJson(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		// POReference shall be mandatory, else we would have problems later,
		// when we will aggregate the invoice candidates by POReference
		if (Check.isEmpty(request.getPoReference(), true))
		{
			throw new AdempiereException("@FillMandatory@ @POReference@: " + request);
		}

		final OrgId orgId = masterdataProvider.getCreateOrgId(request.getOrg());

		final ProductInfo productInfo = masterdataProvider.getCreateProductInfo(request.getProduct(), orgId);

		final PricingSystemId pricingSystemId = masterdataProvider.getPricingSystemIdByValue(request.getPricingSystemCode());

		final CurrencyId currencyId = masterdataProvider.getCurrencyId(request.getCurrencyCode());

		final WarehouseId warehouseDestId = !Check.isEmpty(request.getWarehouseDestCode())
				? masterdataProvider.getWarehouseIdByValue(request.getWarehouseDestCode())
				: null;

		return OLCandCreateRequest.builder()
				//
				.orgId(orgId)
				//
				.dataSourceInternalName(request.getDataSourceInternalName())
				.dataDestInternalName(request.getDataDestInternalName())
				.externalLineId(request.getExternalLineId())
				.externalHeaderId(request.getExternalHeaderId())
				//
				.dataDestInternalName(request.getDataDestInternalName())
				//
				.bpartner(masterdataProvider.getCreateBPartnerInfo(request.getBpartner(), orgId))
				.billBPartner(masterdataProvider.getCreateBPartnerInfo(request.getBillBPartner(), orgId))
				.dropShipBPartner(masterdataProvider.getCreateBPartnerInfo(request.getDropShipBPartner(), orgId))
				.handOverBPartner(masterdataProvider.getCreateBPartnerInfo(request.getHandOverBPartner(), orgId))
				//
				.poReference(request.getPoReference())
				//
				.dateOrdered(request.getDateOrdered())
				.dateRequired(request.getDateRequired())
				//
				.docTypeInvoiceId(masterdataProvider.getDocTypeId(request.getInvoiceDocType(), orgId))
				.presetDateInvoiced(request.getPresetDateInvoiced())
				//
				.presetDateShipped(request.getPresetDateShipped())
				//
				.flatrateConditionsId(request.getFlatrateConditionsId())
				//
				.productId(productInfo.getProductId())
				.productDescription(request.getProductDescription())
				.qty(request.getQty())
				.uomId(productInfo.getUomId())
				.huPIItemProductId(request.getPackingMaterialId())
				//
				.pricingSystemId(pricingSystemId)
				.price(request.getPrice())
				.currencyId(currencyId)
				.discount(Percent.ofNullable(request.getDiscount()))
				//
				.warehouseDestId(warehouseDestId)
		//
		;
	}

	private final JsonResponseBPartnerLocationAndContact toJson(
			final BPartnerInfo bpartnerInfo,
			final MasterdataProvider masterdataProvider)
	{
		if (bpartnerInfo == null)
		{
			return null;
		}

		final BPartnerId bpartnerId = bpartnerInfo.getBpartnerId();
		final BPartnerLocationId bpartnerLocationId = bpartnerInfo.getBpartnerLocationId();
		final BPartnerContactId contactId = bpartnerInfo.getContactId();

		return JsonResponseBPartnerLocationAndContact.builder()
				.bpartner(masterdataProvider.getJsonBPartnerById(bpartnerId))
				.location(masterdataProvider.getJsonBPartnerLocationById(bpartnerLocationId))
				.contact(masterdataProvider.getJsonBPartnerContactById(contactId))
				.build();
	}

	public JsonOLCandCreateBulkResponse toJson(
			@NonNull final List<OLCand> olCands,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		return JsonOLCandCreateBulkResponse.ok(olCands.stream()
				.map(olCand -> toJson(olCand, masterdataProvider))
				.collect(ImmutableList.toImmutableList()));
	}

	private JsonOLCand toJson(final OLCand olCand, final MasterdataProvider masterdataProvider)
	{
		return JsonOLCand.builder()
				.id(olCand.getId())
				.poReference(olCand.getPOReference())
				.externalLineId(olCand.getExternalLineId())
				.externalHeaderId(olCand.getExternalHeaderId())
				//
				.org(masterdataProvider.getJsonOrganizationById(olCand.getAD_Org_ID()))
				//
				.bpartner(toJson(olCand.getBPartnerInfo(), masterdataProvider))
				.billBPartner(toJson(olCand.getBillBPartnerInfo(), masterdataProvider))
				.dropShipBPartner(toJson(olCand.getDropShipBPartnerInfo(), masterdataProvider))
				.handOverBPartner(toJson(olCand.getHandOverBPartnerInfo(), masterdataProvider))
				//
				.dateOrdered(olCand.getDateDoc())
				.datePromised(TimeUtil.asLocalDate(olCand.getDatePromised()))
				.flatrateConditionsId(olCand.getFlatrateConditionsId())
				//
				.productId(olCand.getM_Product_ID())
				.productDescription(olCand.getProductDescription())
				.qty(olCand.getQty())
				.uomId(olCand.getC_UOM_ID())
				.huPIItemProductId(olCand.getHUPIProductItemId())
				//
				.pricingSystemId(PricingSystemId.toRepoId(olCand.getPricingSystemId()))
				.price(olCand.getPriceActual())
				.discount(olCand.getDiscount())
				//
				.warehouseDestId(WarehouseId.toRepoId(olCand.getWarehouseDestId()))
				//
				.build();
	}
}
