package de.metas.rest_api.ordercandidates.impl;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.TimeUtil;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.AddProductPriceRequest;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.PriceListsCollection;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class ProductPriceMasterDataProvider
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

	public void createProductPrice(@NonNull final ProductPriceCreateRequest request)
	{
		final BPartnerLocationId bpartnerAndLocationId = request.getBpartnerAndLocationId();
		final BPartnerId bpartnerId = bpartnerAndLocationId.getBpartnerId();
		final ZonedDateTime date = request.getDate();
		final ProductId productId = request.getProductId();
		final UomId uomId = request.getUomId();
		final BigDecimal priceStd = request.getPriceStd();
		final SOTrx soTrx = SOTrx.SALES;

		final PricingSystemId pricingSystemId;
		if (request.getPricingSystemId() != null)
		{
			pricingSystemId = request.getPricingSystemId();
		}
		else
		{
			// we need to retrieve within the current trx, because maybe the BPartner itself was also only just created
			pricingSystemId = bpartnersRepo.retrievePricingSystemIdOrNullInTrx(bpartnerId, soTrx);
			if (pricingSystemId == null)
			{
				throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@")
						.setParameter("C_BPartner_ID", bpartnerId)
						.setParameter("SOTrx", soTrx);
			}
		}

		final CountryId countryId = bpartnersRepo.retrieveBPartnerLocationCountryIdInTrx(bpartnerAndLocationId);

		final PriceListsCollection priceLists = priceListsRepo.retrievePriceListsCollectionByPricingSystemId(pricingSystemId);
		final I_M_PriceList priceList = priceLists.getPriceList(countryId, soTrx).orElse(null);
		if (priceList == null)
		{
			throw new AdempiereException("@NotFound@ @M_PriceList_ID@")
					.setParameter("priceLists", priceLists)
					.setParameter("countryId", countryId)
					.setParameter("soTrx", soTrx);
		}

		final PriceListId priceListId = PriceListId.ofRepoId(priceList.getM_PriceList_ID());
		final TaxCategoryId taxCategoryId = TaxCategoryId.ofRepoIdOrNull(priceList.getDefault_TaxCategory_ID());
		if (taxCategoryId == null)
		{
			throw new AdempiereException("@NotFound@ @Default_TaxCategory_ID@ of @M_PriceList_ID@")
					.setParameter("priceListId", priceListId);
		}

		final PriceListVersionId priceListVersionId = priceListsRepo.retrievePriceListVersionId(
				priceListId,
				TimeUtil.asLocalDate(date));

		priceListsRepo.addProductPrice(AddProductPriceRequest.builder()
				.priceListVersionId(priceListVersionId)
				.productId(productId)
				.uomId(uomId)
				.priceStd(priceStd)
				// .priceList(price)
				// .priceLimit(price)
				.taxCategoryId(taxCategoryId)
				.build());
	}
}
