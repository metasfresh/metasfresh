package de.metas.ordercandidate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.service.OrgId;
import org.adempiere.uom.UomId;

import de.metas.document.DocTypeId;
import de.metas.lang.Percent;
import de.metas.money.CurrencyId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.util.Check;
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

	private String dataSourceInternalName;
	private String dataDestInternalName;

	private OrgId orgId;

	private OLCandBPartnerInfo bpartner;
	private OLCandBPartnerInfo billBPartner;
	private OLCandBPartnerInfo dropShipBPartner;
	private OLCandBPartnerInfo handOverBPartner;

	private String poReference;

	private LocalDate dateRequired;

	private LocalDate dateInvoiced;
	private DocTypeId docTypeInvoiceId;

	private int flatrateConditionsId;

	private ProductId productId;
	private String productDescription;
	private BigDecimal qty;
	private UomId uomId;
	private int huPIItemProductId;

	private PricingSystemId pricingSystemId;
	private BigDecimal price;
	private CurrencyId currencyId; // mandatory if price is provided
	private Percent discount;

	@Builder
	private OLCandCreateRequest(
			@Nullable final String externalId,
			final OrgId orgId,
			@NonNull final  String dataSourceInternalName,
			@Nullable final  String dataDestInternalName,
			@NonNull final OLCandBPartnerInfo bpartner,
			final OLCandBPartnerInfo billBPartner,
			final OLCandBPartnerInfo dropShipBPartner,
			final OLCandBPartnerInfo handOverBPartner,
			final String poReference,
			@Nullable final LocalDate dateRequired,
			@Nullable final LocalDate dateInvoiced,
			@Nullable final DocTypeId docTypeInvoiceId,
			final int flatrateConditionsId,
			@NonNull final ProductId productId,
			final String productDescription,
			@NonNull final BigDecimal qty,
			@NonNull final UomId uomId,
			final int huPIItemProductId,
			@Nullable final PricingSystemId pricingSystemId,
			final BigDecimal price,
			final CurrencyId currencyId,
			final Percent discount,
			//
			final String adInputDataSourceInternalName)
	{
		Check.assume(qty.signum() > 0, "qty > 0");
		// Check.assume(price == null || price.signum() >= 0, "price >= 0");
		// Check.assume(discount == null || discount.signum() >= 0, "discount >= 0");
		Check.assumeNotEmpty(adInputDataSourceInternalName, "adInputDataSourceInternalName is not empty");

		this.externalId = externalId;
		this.orgId = orgId;

		this.dataDestInternalName = dataDestInternalName;
		this.dataSourceInternalName = dataSourceInternalName;

		this.bpartner = bpartner;
		this.billBPartner = billBPartner;
		this.dropShipBPartner = dropShipBPartner;
		this.handOverBPartner = handOverBPartner;
		this.poReference = poReference;
		this.dateRequired = dateRequired;

		this.dateInvoiced = dateInvoiced;
		this.docTypeInvoiceId = docTypeInvoiceId;

		this.flatrateConditionsId = flatrateConditionsId;
		this.productId = productId;
		this.productDescription = productDescription;
		this.qty = qty;
		this.uomId = uomId;
		this.huPIItemProductId = huPIItemProductId;
		this.pricingSystemId = pricingSystemId;
		this.price = price;
		this.currencyId = currencyId;
		this.discount = discount;
	}
}
