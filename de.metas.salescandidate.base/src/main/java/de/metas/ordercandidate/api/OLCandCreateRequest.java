package de.metas.ordercandidate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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

@Value
public class OLCandCreateRequest
{
	private String externalId;
	
	private OLCandBPartnerInfo bpartner;
	private OLCandBPartnerInfo billBPartner;
	private OLCandBPartnerInfo dropShipBPartner;
	private OLCandBPartnerInfo handOverBPartner;

	private String poReference;

	private LocalDate dateRequired;
	private int flatrateConditionsId;

	private int productId;
	private String productDescription;
	private BigDecimal qty;
	private int uomId;
	private int huPIItemProductId;

	private int pricingSystemId;
	private BigDecimal price;
	private BigDecimal discount;
	// private String currencyCode; // shall come from pricingSystem/priceList

	private String adInputDataSourceInternalName;

	@Builder
	private OLCandCreateRequest(
			@Nullable final String externalId,
			@NonNull final OLCandBPartnerInfo bpartner,
			final OLCandBPartnerInfo billBPartner,
			final OLCandBPartnerInfo dropShipBPartner,
			final OLCandBPartnerInfo handOverBPartner,
			final String poReference,
			@NonNull final LocalDate dateRequired,
			final int flatrateConditionsId,
			final int productId,
			final String productDescription,
			@NonNull final BigDecimal qty,
			final int uomId,
			final int huPIItemProductId,
			final int pricingSystemId,
			final BigDecimal price,
			final BigDecimal discount,
			//
			final String adInputDataSourceInternalName)
	{
		Check.assume(productId > 0, "productId is set");
		Check.assume(uomId > 0, "uomId is set");
		// Check.assume(pricingSystemId > 0, "pricingSystemId is set");
		Check.assume(qty.signum() > 0, "qty > 0");
		Check.assume(price == null || price.signum() >= 0, "price >= 0");
		Check.assume(discount == null || discount.signum() >= 0, "discount >= 0");
		Check.assumeNotEmpty(adInputDataSourceInternalName, "adInputDataSourceInternalName is not empty");

		this.externalId = externalId;
		this.bpartner = bpartner;
		this.billBPartner = billBPartner;
		this.dropShipBPartner = dropShipBPartner;
		this.handOverBPartner = handOverBPartner;
		this.poReference = poReference;
		this.dateRequired = dateRequired;
		this.flatrateConditionsId = flatrateConditionsId;
		this.productId = productId;
		this.productDescription = productDescription;
		this.qty = qty;
		this.uomId = uomId;
		this.huPIItemProductId = huPIItemProductId;
		this.pricingSystemId = pricingSystemId;
		this.price = price;
		this.discount = discount;

		this.adInputDataSourceInternalName = adInputDataSourceInternalName;
	}

}
