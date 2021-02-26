package de.metas.ordercandidate.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.warehouse.WarehouseId;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.impex.InputDataSourceId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperId;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
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
	String externalLineId;

	String externalHeaderId;

	/** Mandatory; an Identifier of an existing AD_InputDataSource record. */
	InputDataSourceId dataSourceId;

	/**
	 * Mandatory; an Identifier of an existing AD_InputDataSource record.
	 * It's mandatory because all the code that processes C_OLCands into something else expects this to be set to its respective destination.
	 * Therefore, {@code C_OLCand}s without any dataDest will go nowhere.
	 */
	InputDataSourceId dataDestId;

	OrgId orgId;

	BPartnerInfo bpartner;
	BPartnerInfo billBPartner;
	BPartnerInfo dropShipBPartner;
	BPartnerInfo handOverBPartner;

	String poReference;

	LocalDate dateOrdered;
	LocalDate dateRequired;

	LocalDate presetDateInvoiced;
	DocTypeId docTypeInvoiceId;

	DocTypeId docTypeOrderId;

	LocalDate presetDateShipped;

	int flatrateConditionsId;

	ProductId productId;
	String productDescription;
	BigDecimal qty;
	UomId uomId;
	int huPIItemProductId;

	PricingSystemId pricingSystemId;
	BigDecimal price;
	CurrencyId currencyId; // mandatory if price is provided
	Percent discount;

	WarehouseId warehouseId;
	WarehouseId warehouseDestId;

	ShipperId shipperId;

	BPartnerId salesRepId;

	PaymentRule paymentRule;

	PaymentTermId paymentTermId;

	@Builder
	private OLCandCreateRequest(
			@Nullable final String externalLineId,
			@Nullable final String externalHeaderId,
			final OrgId orgId,
			@NonNull final InputDataSourceId dataSourceId,
			@NonNull final InputDataSourceId dataDestId,
			@NonNull final BPartnerInfo bpartner,
			final BPartnerInfo billBPartner,
			final BPartnerInfo dropShipBPartner,
			final BPartnerInfo handOverBPartner,
			final String poReference,
			@Nullable final LocalDate dateOrdered,
			@Nullable final LocalDate dateRequired,
			@Nullable final LocalDate presetDateInvoiced,
			@Nullable final LocalDate presetDateShipped,
			@Nullable final DocTypeId docTypeInvoiceId,
			@Nullable final DocTypeId docTypeOrderId,
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
			@Nullable final WarehouseId warehouseId,
			@Nullable final WarehouseId warehouseDestId,
			@Nullable final ShipperId shipperId,
			@Nullable final BPartnerId salesRepId,
			@Nullable final PaymentRule paymentRule,
			@Nullable final PaymentTermId paymentTermId)
	{
		// Check.assume(qty.signum() > 0, "qty > 0"); qty might very well also be <= 0

		this.externalLineId = externalLineId;
		this.externalHeaderId = externalHeaderId;

		this.orgId = orgId;

		this.dataSourceId = dataSourceId;
		this.dataDestId = dataDestId;

		this.bpartner = bpartner;
		this.billBPartner = billBPartner;
		this.dropShipBPartner = dropShipBPartner;
		this.handOverBPartner = handOverBPartner;
		this.poReference = poReference;
		this.dateRequired = dateRequired;

		this.dateOrdered = dateOrdered;
		this.presetDateInvoiced = presetDateInvoiced;
		this.docTypeInvoiceId = docTypeInvoiceId;
		this.docTypeOrderId = docTypeOrderId;

		this.presetDateShipped = presetDateShipped;

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

		this.shipperId = shipperId;
		this.salesRepId = salesRepId;

		this.warehouseId = warehouseId;
		this.warehouseDestId = warehouseDestId;

		this.paymentRule = paymentRule;

		this.paymentTermId = paymentTermId;
	}
}
