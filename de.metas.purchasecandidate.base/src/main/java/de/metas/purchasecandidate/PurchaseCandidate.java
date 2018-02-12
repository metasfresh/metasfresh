package de.metas.purchasecandidate;

import static java.util.stream.Collectors.toCollection;
import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_OrderLine;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem.PurchaseErrorItemBuilder;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem.PurchaseOrderItemBuilder;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.Delegate;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Data
public class PurchaseCandidate
{
	@Setter(AccessLevel.NONE)
	private int purchaseCandidateId;

	private BigDecimal qtyToPurchase;

	@Setter(AccessLevel.NONE)
	private BigDecimal qtyToPurchaseInitial;

	private Date dateRequired;

	@Setter(AccessLevel.NONE)
	private Date dateRequiredInitial;

	@Delegate
	private final PurchaseCandidateImmutableFields identifier;

	private final PurchaseCandidateState state;

	private final List<PurchaseOrderItem> purchaseOrderItems;

	private final List<PurchaseErrorItem> purchaseErrorItems;

	@Builder
	private PurchaseCandidate(
			final int purchaseCandidateId,
			final int salesOrderId,
			final int salesOrderLineId,
			final int purchaseOrderLineId,
			final int orgId,
			final int warehouseId,
			final int productId,
			final int uomId,
			final int vendorBPartnerId,
			@NonNull final VendorProductInfo vendorProductInfo,
			@NonNull final BigDecimal qtyToPurchase,
			@NonNull final Date dateRequired,
			final boolean processed,
			final boolean locked,
			@Singular final List<PurchaseItem> purchaseItems)
	{
		Check.assume(salesOrderId > 0, "salesOrderId > 0"); // for now this shall be always set; might be that in future this won't be mandatory
		Check.assume(salesOrderLineId > 0, "salesOrderLineId > 0"); // for now this shall be always set; might be that in future this won't be mandatory
		Check.assume(orgId > 0, "orgId > 0");
		Check.assume(warehouseId > 0, "warehouseId > 0");
		Check.assume(productId > 0, "productId > 0");
		Check.assume(uomId > 0, "uomId > 0");
		Check.assume(vendorBPartnerId > 0, "vendorBPartnerId > 0");

		this.purchaseCandidateId = purchaseCandidateId;

		this.identifier = PurchaseCandidateImmutableFields.builder()
				.orgId(orgId)
				.productId(productId)
				.salesOrderId(salesOrderId)
				.salesOrderLineId(salesOrderLineId)
				.uomId(uomId)
				.vendorBPartnerId(vendorBPartnerId)
				.vendorProductInfo(vendorProductInfo)
				.warehouseId(warehouseId).build();

		this.state = PurchaseCandidateState.builder()
				.locked(locked)
				.processed(processed).build();

		this.qtyToPurchase = qtyToPurchase;
		this.qtyToPurchaseInitial = qtyToPurchase;
		this.dateRequired = dateRequired;
		this.dateRequiredInitial = dateRequired;

		final ImmutableListMultimap<Boolean, PurchaseItem> purchaseItemsByType;
		purchaseItemsByType = Multimaps.index(purchaseItems, purchaseItem -> purchaseItem instanceof PurchaseOrderItem);

		this.purchaseOrderItems = purchaseItemsByType.get(true).stream()
				.map(PurchaseOrderItem::cast).collect(toCollection(ArrayList::new));
		this.purchaseErrorItems = purchaseItemsByType.get(false).stream()
				.map(PurchaseErrorItem::cast).collect(toCollection(ArrayList::new));
	}

	private PurchaseCandidate(@NonNull final PurchaseCandidate from)
	{
		this.purchaseCandidateId = from.purchaseCandidateId;

		this.qtyToPurchase = from.qtyToPurchase;
		this.qtyToPurchaseInitial = from.qtyToPurchaseInitial;
		this.dateRequired = from.dateRequired;
		this.dateRequiredInitial = from.dateRequiredInitial;

		this.identifier = from.identifier;
		this.state = from.state.createCopy();

		this.purchaseErrorItems = from.purchaseErrorItems;
		this.purchaseOrderItems = from.purchaseOrderItems;
	}

	public PurchaseCandidate copy()
	{
		return new PurchaseCandidate(this);
	}

	/**
	 * Flags this instance as processed, so no more changes can be made.<br>
	 * Does not persist this instance!
	 */
	public void markProcessed()
	{
		if(state.isProcessed())
		{
			return;
		}
		state.setProcessed();
	}

	public boolean isProcessedOrLocked()
	{
		return state.isProcessed() || state.isLocked();
	}

	public boolean isProcessed()
	{
		return state.isProcessed();
	}

	public boolean hasChanges()
	{
		return purchaseCandidateId <= 0 // never saved
				|| state.hasChanges()
				|| qtyToPurchase.compareTo(qtyToPurchaseInitial) != 0
				|| !Objects.equals(dateRequired, dateRequiredInitial);
	}

	public void markSaved(final int C_PurchaseCandidate_ID)
	{
		this.purchaseCandidateId = C_PurchaseCandidate_ID;

		state.markSaved();

		qtyToPurchaseInitial = qtyToPurchase;
		dateRequiredInitial = dateRequired;
	}

	public AvailabilityRequestItem createAvailabilityRequestItem()
	{
		final ProductAndQuantity productAndQuantity = createProductAndQuantity();

		return AvailabilityRequestItem.builder()
				.productAndQuantity(productAndQuantity)
				.purchaseCandidateId(purchaseCandidateId)
				.salesOrderLineId(getSalesOrderLineId())
				.build();
	}

	public PurchaseOrderRequestItem createPurchaseOrderRequestItem()
	{
		return new PurchaseOrderRequestItem(
				getPurchaseCandidateId(),
				createProductAndQuantity());
	}

	private ProductAndQuantity createProductAndQuantity()
	{
		final String productValue = identifier.getVendorProductInfo().getProductNo();

		final I_C_OrderLine salesOrderLine = load(identifier.getSalesOrderLineId(), I_C_OrderLine.class);
		final BigDecimal qtyToDeliver = salesOrderLine.getQtyOrdered().subtract(salesOrderLine.getQtyDelivered());

		final ProductAndQuantity productAndQuantity = new ProductAndQuantity(
				productValue,
				qtyToDeliver);
		return productAndQuantity;
	}

	public ErrorItemBuilder createErrorItem()
	{
		return new ErrorItemBuilder(this);
	}

	public static final class ErrorItemBuilder
	{
		private final PurchaseCandidate parent;
		private final PurchaseErrorItemBuilder innerBuilder;

		private ErrorItemBuilder(@NonNull final PurchaseCandidate parent)
		{
			this.parent = parent;
			this.innerBuilder = PurchaseErrorItem.builder()
					.purchaseCandidateId(parent.getPurchaseCandidateId())
					.orgId(parent.getOrgId());
		}

		public ErrorItemBuilder transactionReference(ITableRecordReference transactionReference)
		{
			this.innerBuilder.transactionReference(transactionReference);
			return this;
		}

		public ErrorItemBuilder throwable(Throwable throwable)
		{
			this.innerBuilder.throwable(throwable);
			return this;
		}

		public ErrorItemBuilder issue(I_AD_Issue issue)
		{
			this.innerBuilder.issue(issue);
			return this;
		}

		public PurchaseErrorItem buildAndAdd()
		{
			final PurchaseErrorItem newItem = innerBuilder.build();
			parent.purchaseErrorItems.add(newItem);
			return newItem;
		}
	}

	public OrderItemBuilder createOrderItem()
	{
		return new OrderItemBuilder(this);
	}

	public static final class OrderItemBuilder
	{
		private final PurchaseCandidate parent;
		private final PurchaseOrderItemBuilder innerBuilder;

		private OrderItemBuilder(@NonNull final PurchaseCandidate parent)
		{
			this.parent = parent;
			innerBuilder = PurchaseOrderItem.builder().purchaseCandidate(parent);
		}

		public OrderItemBuilder datePromised(@NonNull final Date datePromised)
		{
			innerBuilder.datePromised(datePromised);
			return this;
		}

		public OrderItemBuilder purchasedQty(@NonNull final BigDecimal purchasedQty)
		{
			innerBuilder.purchasedQty(purchasedQty);
			return this;
		}

		public OrderItemBuilder remotePurchaseOrderId(final String remotePurchaseOrderId)
		{
			innerBuilder.remotePurchaseOrderId(remotePurchaseOrderId);
			return this;
		}

		public OrderItemBuilder transactionReference(final ITableRecordReference transactionReference)
		{
			innerBuilder.transactionReference(transactionReference);
			return this;
		}

		public PurchaseOrderItem buildAndAddToParent()
		{
			final PurchaseOrderItem newItem = innerBuilder.build();
			parent.purchaseOrderItems.add(newItem);
			return newItem;
		}
	}

	public BigDecimal getPurchasedQty()
	{
		return purchaseOrderItems.stream()
				.map(PurchaseOrderItem::getPurchasedQty)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
