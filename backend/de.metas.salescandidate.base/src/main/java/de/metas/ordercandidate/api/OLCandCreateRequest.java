package de.metas.ordercandidate.api;

import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.impex.InputDataSourceId;
import de.metas.money.CurrencyId;
<<<<<<< HEAD
=======
import de.metas.order.InvoiceRule;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.order.OrderLineGroup;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
<<<<<<< HEAD
=======
import de.metas.quantity.Quantity;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.shipping.ShipperId;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

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

<<<<<<< HEAD
	/** Mandatory; an Identifier of an existing AD_InputDataSource record. */
=======
	/**
	 * Mandatory; an Identifier of an existing AD_InputDataSource record.
	 */
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
	LocalDate dateCandidate;

	LocalDate presetDateInvoiced;
	DocTypeId docTypeInvoiceId;

	DocTypeId docTypeOrderId;

	LocalDate presetDateShipped;

	int flatrateConditionsId;

	ProductId productId;
	String productDescription;
	BigDecimal qty;
	UomId uomId;
<<<<<<< HEAD
=======
	@Nullable BigDecimal manualQtyInPriceUOM;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	int huPIItemProductId;

	PricingSystemId pricingSystemId;
	BigDecimal price;
	CurrencyId currencyId; // mandatory if price is provided
	Percent discount;

	WarehouseId warehouseId;
	WarehouseId warehouseDestId;

	ShipperId shipperId;

	BPartnerId salesRepId;

<<<<<<< HEAD
	PaymentRule paymentRule;
=======
	@Nullable InvoiceRule invoiceRule;
	@Nullable PaymentRule paymentRule;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	PaymentTermId paymentTermId;
	OrderLineGroup orderLineGroup;

	Integer line;
	String description;

	Boolean isManualPrice;
	Boolean isImportedWithIssues;

	String deliveryViaRule;
	String deliveryRule;

	String importWarningMessage;

	AsyncBatchId asyncBatchId;

	BigDecimal qtyShipped;
<<<<<<< HEAD
=======
	@Nullable Quantity qtyShippedCatchWeight;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	BigDecimal qtyItemCapacity;

	AssignSalesRepRule assignSalesRepRule;

	BPartnerId salesRepInternalId;

	String bpartnerName;
	String email;
	String phone;
<<<<<<< HEAD
	
=======

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
=======
			@Nullable final BigDecimal manualQtyInPriceUOM,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			final int huPIItemProductId,
			@Nullable final PricingSystemId pricingSystemId,
			final BigDecimal price,
			final CurrencyId currencyId,
			final Percent discount,
			@Nullable final WarehouseId warehouseId,
			@Nullable final WarehouseId warehouseDestId,
			@Nullable final ShipperId shipperId,
			@Nullable final BPartnerId salesRepId,
<<<<<<< HEAD
=======
			@Nullable final InvoiceRule invoiceRule,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			@Nullable final PaymentRule paymentRule,
			@Nullable final PaymentTermId paymentTermId,
			@Nullable final OrderLineGroup orderLineGroup,
			@Nullable final LocalDate dateCandidate,
			@Nullable final Integer line,
			@Nullable final String description,
			@Nullable final Boolean isManualPrice,
			@Nullable final Boolean isImportedWithIssues,
			@Nullable final String deliveryViaRule,
			@Nullable final String deliveryRule,
			@Nullable final String importWarningMessage,
			@Nullable final AsyncBatchId asyncBatchId,
			@Nullable final BigDecimal qtyShipped,
<<<<<<< HEAD
=======
			@Nullable final Quantity qtyShippedCatchWeight,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			@Nullable final BigDecimal qtyItemCapacity,
			@Nullable final AssignSalesRepRule assignSalesRepRule,
			@Nullable final BPartnerId salesRepInternalId,
			@Nullable final String bpartnerName,
			@Nullable final String email,
			@Nullable final String phone)
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

		this.dateCandidate = dateCandidate;

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
<<<<<<< HEAD
=======
		this.manualQtyInPriceUOM = manualQtyInPriceUOM;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.huPIItemProductId = huPIItemProductId;
		this.pricingSystemId = pricingSystemId;
		this.price = price;
		this.currencyId = currencyId;
		this.discount = discount;

		this.shipperId = shipperId;
		this.salesRepId = salesRepId;

		this.warehouseId = warehouseId;
		this.warehouseDestId = warehouseDestId;

<<<<<<< HEAD
=======
		this.invoiceRule = invoiceRule;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.paymentRule = paymentRule;

		this.paymentTermId = paymentTermId;
		this.orderLineGroup = orderLineGroup;
		this.line = line;
		this.description = description;
		this.isManualPrice = isManualPrice;
		this.isImportedWithIssues = isImportedWithIssues;
		this.deliveryViaRule = deliveryViaRule;
		this.deliveryRule = deliveryRule;
		this.importWarningMessage = importWarningMessage;
		this.asyncBatchId = asyncBatchId;
		this.qtyShipped = qtyShipped;
<<<<<<< HEAD
=======
		this.qtyShippedCatchWeight = qtyShippedCatchWeight;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.qtyItemCapacity = qtyItemCapacity;

		this.assignSalesRepRule = CoalesceUtil.coalesceNotNull(assignSalesRepRule, AssignSalesRepRule.CandidateFirst);
		this.salesRepInternalId = salesRepInternalId;

		this.bpartnerName = bpartnerName;
		this.email = email;
		this.phone = phone;
	}
}
