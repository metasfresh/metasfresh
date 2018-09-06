package de.metas.ordercandidate.rest;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.uom.UomId;
import org.adempiere.uom.api.IUOMDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.lang.Percent;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandBPartnerInfo;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandCreateRequest.OLCandCreateRequestBuilder;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
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
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productsBL = Services.get(IProductBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	public final OLCandCreateRequestBuilder fromJson(final JsonOLCandCreateRequest request)
	{
		final ProductId productId = productsRepo.retrieveProductIdByValue(request.getProductCode());

		final UomId uomId;
		if (!Check.isEmpty(request.getUomCode(), true))
		{
			uomId = uomsRepo.getUomIdByX12DE355(request.getUomCode());
		}
		else
		{
			uomId = UomId.ofRepoId(productsBL.getStockingUOMId(productId));
		}

		PricingSystemId pricingSystemId;
		if (!Check.isEmpty(request.getPricingSystemCode(), true))
		{
			pricingSystemId = priceListsRepo.getPricingSystemIdByValue(request.getPricingSystemCode());
		}
		else
		{
			pricingSystemId = null;
		}

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
				.huPIItemProductId(request.getPackingMaterialId())
				//
				.pricingSystemId(pricingSystemId)
				.price(request.getPrice())
				.discount(Percent.ofNullable(request.getDiscount()));
	}

	private final OLCandBPartnerInfo toOLCandBPartnerInfo(final JsonBPartnerInfo json)
	{
		if (json == null)
		{
			return null;
		}

		final BPartnerId bpartnerId = bpartnersRepo.getBPartnerIdByValue(json.getBpartner().getCode());
		final int bpartnerLocationId = convertCodeToBPartnerLocationId(json.getLocation().getCode());
		final int bpartnerContactId = convertCodeToBPartnerContactId(json.getContact().getCode());

		return OLCandBPartnerInfo.builder()
				.bpartnerId(bpartnerId.getRepoId())
				.bpartnerLocationId(bpartnerLocationId)
				.contactId(bpartnerContactId)
				.build();
	}

	private final JsonBPartnerInfo toJson(final OLCandBPartnerInfo bpartnerInfo)
	{
		if (bpartnerInfo == null)
		{
			return null;
		}

		final int bpartnerId = bpartnerInfo.getBpartnerId();
		final I_C_BPartner bpartnerRecord = bpartnersRepo.getById(bpartnerId);

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, bpartnerInfo.getBpartnerLocationId());
		final I_C_BPartner_Location bpLocationRecord = bpartnersRepo.getBPartnerLocationById(bpartnerLocationId);

		final BPartnerContactId contactId = bpartnerInfo.getContactId() > 0 ? BPartnerContactId.ofRepoId(bpartnerId, bpartnerInfo.getContactId()) : null;
		final I_AD_User bpContactRecord = contactId != null ? bpartnersRepo.getContactById(contactId) : null;

		return JsonBPartnerInfo.builder()
				.bpartner(toJson(bpartnerRecord))
				.location(toJson(bpLocationRecord))
				.contact(toJson(bpContactRecord))
				.build();
	}

	private static JsonBPartner toJson(final I_C_BPartner bpartnerRecord)
	{
		return JsonBPartner.builder()
				.code(bpartnerRecord.getValue())
				.name(bpartnerRecord.getName())
				.build();
	}

	private JsonBPartnerLocation toJson(final I_C_BPartner_Location bpLocationRecord)
	{
		if (bpLocationRecord == null)
		{
			return null;
		}

		final I_C_Location location = bpLocationRecord.getC_Location();

		final String countryCode = countryDAO.retrieveCountryCode2ByCountryId(location.getC_Country_ID());

		return JsonBPartnerLocation.builder()
				.code(convertBPartnerLocationIdToCode(bpLocationRecord.getC_BPartner_Location_ID()))
				.address1(location.getAddress1())
				.address2(location.getAddress2())
				.postal(location.getPostal())
				.city(location.getCity())
				.countryCode(countryCode)
				.build();
	}

	private static final String convertBPartnerLocationIdToCode(final int bpartnerLocationId)
	{
		return String.valueOf(bpartnerLocationId);
	}

	private static final int convertCodeToBPartnerLocationId(final String code)
	{
		if (code == null || code.isEmpty())
		{
			return -1;
		}

		try
		{
			return Integer.parseInt(code);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid BPartner Location code: " + code, ex);
		}
	}

	private JsonBPartnerContact toJson(I_AD_User bpContactRecord)
	{
		if (bpContactRecord == null)
		{
			return null;
		}

		return JsonBPartnerContact.builder()
				.code(convertBPartnerContactIdToCode(bpContactRecord.getAD_User_ID()))
				.name(bpContactRecord.getName())
				.email(bpContactRecord.getEMail())
				.phone(bpContactRecord.getPhone())
				.build();
	}

	private static String convertBPartnerContactIdToCode(final int bpContactId)
	{
		return String.valueOf(bpContactId);
	}

	private static final int convertCodeToBPartnerContactId(final String code)
	{
		if (code == null || code.isEmpty())
		{
			return -1;
		}

		try
		{
			return Integer.parseInt(code);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid BPartner Contact code: " + code, ex);
		}
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
				.pricingSystemId(PricingSystemId.getRepoId(olCand.getPricingSystemId()))
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
