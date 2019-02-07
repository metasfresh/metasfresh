package de.metas.ordercandidate.rest;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.OrgId;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.money.CurrencyId;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandBPartnerInfo;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandCreateRequest.OLCandCreateRequestBuilder;
import de.metas.ordercandidate.rest.ProductMasterDataProvider.ProductInfo;
import de.metas.pricing.PricingSystemId;
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
public class JsonConverters
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

		final ProductMasterDataProvider productMasterDataProvider = masterdataProvider.getProductMasterDataProvider();
		final ProductInfo productInfo = productMasterDataProvider.getCreateProductInfo(request.getProduct(), orgId);

		final PricingSystemId pricingSystemId = masterdataProvider.getPricingSystemIdByValue(request.getPricingSystemCode());

		final CurrencyId currencyId = masterdataProvider.getCurrencyId(request.getCurrencyCode());

		final BPartnerMasterDataProvider //
		bpartnerMasterdataProvider = masterdataProvider.getBPartnerMasterDataProvider();

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
				.bpartner(bpartnerMasterdataProvider.getCreateBPartnerInfo(request.getBpartner(), orgId))
				.billBPartner(bpartnerMasterdataProvider.getCreateBPartnerInfo(request.getBillBPartner(), orgId))
				.dropShipBPartner(bpartnerMasterdataProvider.getCreateBPartnerInfo(request.getDropShipBPartner(), orgId))
				.handOverBPartner(bpartnerMasterdataProvider.getCreateBPartnerInfo(request.getHandOverBPartner(), orgId))
				//
				.poReference(request.getPoReference())
				//
				.dateRequired(request.getDateRequired())
				//
				.dateInvoiced(request.getDateInvoiced())
				.docTypeInvoiceId(masterdataProvider.getDocTypeId(request.getInvoiceDocType(), orgId))

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

				.discount(Percent.ofNullable(request.getDiscount()));
	}

	private final JsonBPartnerInfo toJson(
			final OLCandBPartnerInfo bpartnerInfo,
			final MasterdataProvider masterdataProvider)
	{
		if (bpartnerInfo == null)
		{
			return null;
		}

		final BPartnerId bpartnerId = bpartnerInfo.getBpartnerId();
		final BPartnerLocationId bpartnerLocationId = bpartnerInfo.getBpartnerLocationId();
		final BPartnerContactId contactId = bpartnerInfo.getContactId();

		final BPartnerMasterDataProvider //
		bpartnerMasterdataProvider = masterdataProvider.getBPartnerMasterDataProvider();

		return JsonBPartnerInfo.builder()
				.bpartner(bpartnerMasterdataProvider.getJsonBPartnerById(bpartnerId))
				.location(bpartnerMasterdataProvider.getJsonBPartnerLocationById(bpartnerLocationId))
				.contact(bpartnerMasterdataProvider.getJsonBPartnerContactById(contactId))
				.build();
	}

	public JsonOLCandCreateBulkResponse toJson(
			@NonNull final List<OLCand> olCands,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		return JsonOLCandCreateBulkResponse.of(olCands.stream()
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
}
