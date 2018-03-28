package de.metas.ordercandidate.rest;

import java.util.List;

import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandBPartnerInfo;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandCreateRequest.OLCandCreateRequestBuilder;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
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
public class JsonConverters
{
	public final OLCandCreateRequestBuilder toOLCandCreateRequest(final JsonOLCandCreateRequest request)
	{
		final int productId = Services.get(IProductDAO.class).retrieveProductIdByValue(request.getProductCode());
		final int uomId = request.getUomId() > 0 ? request.getUomId() : Services.get(IProductBL.class).getStockingUOM(productId).getC_UOM_ID();

		return OLCandCreateRequest.builder()
				.externalId(request.getExternalId())
				//
				.bpartner(toOLCandBPartnerInfo(request.getBpartner()))
				.billBPartner(toOLCandBPartnerInfo(request.getBillBPartner()))
				.dropShipBPartner(toOLCandBPartnerInfo(request.getDropShipBPartner()))
				.handOverBPartner(toOLCandBPartnerInfo(request.getHandOverBPartner()))
				.poReference(request.getPoReference())
				//
				.dateRequired(request.getDateRequired())
				.flatrateConditionsId(request.getFlatrateConditionsId())
				//
				.productId(productId)
				.productDescription(request.getProductDescription())
				.qty(request.getQty())
				.uomId(uomId)
				.huPIItemProductId(request.getHuPIItemProductId())
				//
				.pricingSystemId(request.getPricingSystemId())
				.price(request.getPrice())
				.discount(request.getDiscount());
	}

	private static final OLCandBPartnerInfo toOLCandBPartnerInfo(final JsonBPartnerInfo json)
	{
		if (json == null)
		{
			return null;
		}
		return OLCandBPartnerInfo.builder()
				.bpartnerId(json.getBpartnerId())
				.bpartnerLocationId(json.getBpartnerLocationId())
				.contactId(json.getContactId())
				.build();
	}

	private static final JsonBPartnerInfo toJson(final OLCandBPartnerInfo bpartner)
	{
		if (bpartner == null)
		{
			return null;
		}

		return JsonBPartnerInfo.builder()
				.bpartnerId(bpartner.getBpartnerId())
				.bpartnerLocationId(bpartner.getBpartnerLocationId())
				.contactId(bpartner.getContactId())
				.build();
	}

	public JsonOLCand toJson(final OLCand olCand)
	{
		return JsonOLCand.builder()
				.id(olCand.getId())
				.externalId(olCand.getExternalId())
				//
				.bpartner(toJson(olCand.getBPartnerInfo()))
				.billBPartner(toJson(olCand.getBillBPartnerInfo()))
				.dropShipBPartner(toJson(olCand.getDropShipBPartnerInfo()))
				.handOverBPartner(toJson(olCand.getHandOverBPartnerInfo()))
				//
				.datePromised(TimeUtil.asLocalDate(olCand.getDatePromised()))
				.flatrateConditionsId(olCand.getFlatrateConditionsId())
				//
				.productId(olCand.getM_Product_ID())
				.productDescription(olCand.getProductDescription())
				.qty(olCand.getQty())
				.uomId(olCand.getC_UOM_ID())
				.huPIItemProductId(olCand.getHUPIProductItemId())
				//
				.pricingSystemId(olCand.getPricingSystemId())
				.price(olCand.getPriceActual())
				.discount(olCand.getDiscount())
				//
				.build();
	}

	public JsonOLCandCreateBulkResponse toJsonOLCandCreateBulkResponse(@NonNull final List<OLCand> olCands)
	{
		return JsonOLCandCreateBulkResponse.of(olCands.stream()
				.map(olCand -> toJson(olCand))
				.collect(ImmutableList.toImmutableList()));
	}
}
