package de.metas.purchasecandidate;

import static java.util.stream.Collectors.toCollection;
import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_OrderLine;

import com.google.common.collect.ImmutableList;

import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

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
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class PurchaseCandidate
{
	@Setter(AccessLevel.NONE)
	private int purchaseCandidateId;

	@NonNull
	private BigDecimal qtyToPurchase;

	@Setter(AccessLevel.NONE)
	private BigDecimal qtyToPurchaseInitial;

	@Setter(AccessLevel.NONE)
	private PurchaseProfitInfo profitInfo;

	@NonNull
	private LocalDateTime dateRequired;

	@Setter(AccessLevel.NONE)
	private LocalDateTime dateRequiredInitial;

	@Getter(AccessLevel.NONE)
	private final Duration reminderTime;

	@Getter(AccessLevel.PRIVATE)
	private final PurchaseCandidateImmutableFields identifier;

	@Getter(AccessLevel.NONE)
	private final PurchaseCandidateState state;

	@Getter(AccessLevel.NONE)
	private final ArrayList<PurchaseOrderItem> purchaseOrderItems;

	@Getter(AccessLevel.NONE)
	private final ArrayList<PurchaseErrorItem> purchaseErrorItems;

	@Builder
	private PurchaseCandidate(
			final int purchaseCandidateId,
			final int salesOrderId,
			@NonNull final OrderLineId salesOrderLineId, // for now this shall be always set; might be that in future this won't be mandatory
			final int purchaseOrderLineId,
			final int orgId,
			final int warehouseId,
			final ProductId productId,
			final int uomId,
			@NonNull final VendorProductInfo vendorProductInfo,
			@NonNull final BigDecimal qtyToPurchase,
			@NonNull final LocalDateTime dateRequired,
			final Duration reminderTime,
			final boolean processed,
			final boolean locked,
			@NonNull final PurchaseProfitInfo profitInfo,
			@Singular final List<PurchaseItem> purchaseItems)
	{
		Check.assume(salesOrderId > 0, "salesOrderId > 0"); // for now this shall be always set; might be that in future this won't be mandatory
		Check.assume(orgId > 0, "orgId > 0");
		Check.assume(warehouseId > 0, "warehouseId > 0");
		Check.assume(uomId > 0, "uomId > 0");

		this.purchaseCandidateId = purchaseCandidateId;

		identifier = PurchaseCandidateImmutableFields.builder()
				.orgId(orgId)
				.productId(productId.getRepoId())
				.salesOrderId(salesOrderId)
				.salesOrderLineId(salesOrderLineId)
				.uomId(uomId)
				.vendorProductInfo(vendorProductInfo)
				.warehouseId(warehouseId)
				.build();

		state = PurchaseCandidateState.builder()
				.locked(locked)
				.processed(processed)
				.build();

		this.qtyToPurchase = qtyToPurchase;
		this.qtyToPurchaseInitial = qtyToPurchase;

		this.dateRequired = dateRequired;
		this.reminderTime = reminderTime;
		this.dateRequiredInitial = dateRequired;

		this.profitInfo = profitInfo;

		this.purchaseOrderItems = purchaseItems
				.stream()
				.filter(purchaseItem -> purchaseItem instanceof PurchaseOrderItem)
				.map(PurchaseOrderItem::cast)
				.collect(toCollection(ArrayList::new));
		this.purchaseErrorItems = purchaseItems
				.stream()
				.filter(purchaseItem -> purchaseItem instanceof PurchaseErrorItem)
				.map(PurchaseErrorItem::cast)
				.collect(toCollection(ArrayList::new));
	}

	private PurchaseCandidate(@NonNull final PurchaseCandidate from)
	{
		purchaseCandidateId = from.purchaseCandidateId;

		qtyToPurchase = from.qtyToPurchase;
		qtyToPurchaseInitial = from.qtyToPurchaseInitial;
		dateRequired = from.dateRequired;
		dateRequiredInitial = from.dateRequiredInitial;
		reminderTime = from.reminderTime;

		identifier = from.identifier;
		state = from.state.copy();

		purchaseOrderItems = new ArrayList<>(from.purchaseOrderItems);
		purchaseErrorItems = new ArrayList<>(from.purchaseErrorItems);
	}

	public PurchaseCandidate copy()
	{
		return new PurchaseCandidate(this);
	}

	public int getOrgId()
	{
		return getIdentifier().getOrgId();
	}

	public int getProductId()
	{
		return getIdentifier().getProductId();
	}

	public int getUomId()
	{
		return getIdentifier().getUomId();
	}

	public int getWarehouseId()
	{
		return getIdentifier().getWarehouseId();
	}

	public int getSalesOrderId()
	{
		return getIdentifier().getSalesOrderId();
	}

	public OrderLineId getSalesOrderLineId()
	{
		return getIdentifier().getSalesOrderLineId();
	}

	public BPartnerId getVendorBPartnerId()
	{
		return getVendorProductInfo().getVendorBPartnerId();
	}

	public OptionalInt getBpartnerProductId()
	{
		return getVendorProductInfo().getBpartnerProductId();
	}

	public boolean isAggregatePOs()
	{
		return getVendorProductInfo().isAggregatePOs();
	}

	public VendorProductInfo getVendorProductInfo()
	{
		return getIdentifier().getVendorProductInfo();
	}

	/**
	 * Flags this instance as processed, so no more changes can be made.<br>
	 * Does not persist this instance!
	 */
	public void markProcessed()
	{
		if (state.isProcessed())
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
		purchaseCandidateId = C_PurchaseCandidate_ID;

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
				.salesOrderLineId(getSalesOrderLineId().getRepoId())
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

		// FIXME: don't use load in POJOs!!!
		final I_C_OrderLine salesOrderLine = load(identifier.getSalesOrderLineId().getRepoId(), I_C_OrderLine.class);
		final BigDecimal qtyToDeliver = salesOrderLine.getQtyOrdered().subtract(salesOrderLine.getQtyDelivered());

		final ProductAndQuantity productAndQuantity = new ProductAndQuantity(
				productValue,
				qtyToDeliver);
		return productAndQuantity;
	}

	public static final class ErrorItemBuilder
	{
		private final PurchaseCandidate parent;
		private final PurchaseErrorItemBuilder innerBuilder;

		private ErrorItemBuilder(@NonNull final PurchaseCandidate parent)
		{
			this.parent = parent;
			innerBuilder = PurchaseErrorItem.builder()
					.purchaseCandidateId(parent.getPurchaseCandidateId())
					.orgId(parent.getOrgId());
		}

		public ErrorItemBuilder purchaseItemId(final int purchaseItemId)
		{
			innerBuilder.purchaseItemId(purchaseItemId);
			return this;
		}

		public ErrorItemBuilder transactionReference(final ITableRecordReference transactionReference)
		{
			innerBuilder.transactionReference(transactionReference);
			return this;
		}

		public ErrorItemBuilder throwable(final Throwable throwable)
		{
			innerBuilder.throwable(throwable);
			return this;
		}

		public ErrorItemBuilder issue(final I_AD_Issue issue)
		{
			innerBuilder.issue(issue);
			return this;
		}

		public PurchaseErrorItem buildAndAdd()
		{
			final PurchaseErrorItem newItem = innerBuilder.build();
			parent.purchaseErrorItems.add(newItem);
			return newItem;
		}
	}

	public ErrorItemBuilder createErrorItem()
	{
		return new ErrorItemBuilder(this);
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

		public OrderItemBuilder purchaseItemId(final int purchaseItemId)
		{
			innerBuilder.purchaseItemId(purchaseItemId);
			return this;
		}

		public OrderItemBuilder datePromised(@NonNull final LocalDateTime datePromised)
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

	public OrderItemBuilder createOrderItem()
	{
		return new OrderItemBuilder(this);
	}

	/**
	 * Intended to be used by the persistence layer
	 */
	public void addLoadedPurchaseOrderItem(@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		Check.errorIf(getPurchaseCandidateId() <= 0,
				"This instance needs to have purchaseCandidateId>0; this={}", this);

		Check.errorIf(purchaseOrderItem.getPurchaseCandidateId() != getPurchaseCandidateId(),
				"The given purchaseOrderItem's purchaseCandidateId needs to be equan to this instance's id; purchaseOrderItem={}; this={}",
				purchaseOrderItem, this);

		purchaseOrderItems.add(purchaseOrderItem);
	}

	public BigDecimal getPurchasedQty()
	{
		return purchaseOrderItems.stream()
				.map(PurchaseOrderItem::getPurchasedQty)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<PurchaseOrderItem> getPurchaseOrderItems()
	{
		return ImmutableList.copyOf(purchaseOrderItems);
	}

	public List<PurchaseErrorItem> getPurchaseErrorItems()
	{
		return ImmutableList.copyOf(purchaseErrorItems);
	}

	public LocalDateTime getReminderDate()
	{
		if (reminderTime == null || dateRequired == null)
		{
			return null;
		}

		return dateRequired.minus(reminderTime);
	}
}
