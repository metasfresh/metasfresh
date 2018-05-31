package de.metas.purchasecandidate;

import static java.util.stream.Collectors.toCollection;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.service.OrgId;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Util;

import com.google.common.collect.ImmutableList;

import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem.PurchaseErrorItemBuilder;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemId;
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
	private PurchaseCandidateId id;

	@NonNull
	private final BigDecimal salesOrderQtyToDeliver;

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
			final PurchaseCandidateId id,
			final OrderAndLineId salesOrderAndLineId,
			final boolean processed,
			final boolean locked,
			//
			@NonNull final OrgId orgId,
			@NonNull final WarehouseId warehouseId,
			//
			final ProductId productId,
			final int uomId,
			@NonNull final VendorProductInfo vendorProductInfo,
			//
			@NonNull final BigDecimal qtyToPurchase,
			final BigDecimal salesOrderQtyToDeliver,
			//
			@NonNull final LocalDateTime dateRequired,
			final Duration reminderTime,
			//
			@NonNull final PurchaseProfitInfo profitInfo,
			//
			@Singular final List<PurchaseItem> purchaseItems)
	{
		Check.assume(uomId > 0, "uomId > 0");

		this.id = id;

		identifier = PurchaseCandidateImmutableFields.builder()
				.orgId(orgId)
				.productId(productId)
				.salesOrderAndLineId(salesOrderAndLineId)
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
		this.salesOrderQtyToDeliver = salesOrderQtyToDeliver;

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
		id = from.id;

		qtyToPurchase = from.qtyToPurchase;
		qtyToPurchaseInitial = from.qtyToPurchaseInitial;
		salesOrderQtyToDeliver = from.salesOrderQtyToDeliver;

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

	public OrgId getOrgId()
	{
		return getIdentifier().getOrgId();
	}

	public ProductId getProductId()
	{
		return getIdentifier().getProductId();
	}

	public int getUomId()
	{
		return getIdentifier().getUomId();
	}

	public WarehouseId getWarehouseId()
	{
		return getIdentifier().getWarehouseId();
	}

	public OrderAndLineId getSalesOrderAndLineId()
	{
		return getIdentifier().getSalesOrderAndLineId();
	}

	public BPartnerId getVendorId()
	{
		return getVendorProductInfo().getVendorId();
	}

	public Optional<VendorProductInfoId> getVendorProductInfoId()
	{
		return getVendorProductInfo().getId();
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
		return id == null // never saved
				|| state.hasChanges()
				|| qtyToPurchase.compareTo(qtyToPurchaseInitial) != 0
				|| !Objects.equals(dateRequired, dateRequiredInitial);
	}

	public void markSaved(@NonNull final PurchaseCandidateId newId)
	{
		id = newId;

		state.markSaved();

		qtyToPurchaseInitial = qtyToPurchase;
		dateRequiredInitial = dateRequired;
	}

	public AvailabilityRequestItem createAvailabilityRequestItem()
	{
		final ProductAndQuantity productAndQuantity = createProductAndQuantity();

		return AvailabilityRequestItem.builder()
				.productAndQuantity(productAndQuantity)
				.purchaseCandidateId(PurchaseCandidateId.getRepoIdOr(getId(), -1))
				.salesOrderLineId(OrderAndLineId.getOrderLineRepoIdOr(getSalesOrderAndLineId(), -1))
				.build();
	}

	public PurchaseOrderRequestItem createPurchaseOrderRequestItem()
	{
		return PurchaseOrderRequestItem.builder()
				.purchaseCandidateId(PurchaseCandidateId.getRepoIdOr(id, -1))
				.productAndQuantity(createProductAndQuantity())
				.build();
	}

	private ProductAndQuantity createProductAndQuantity()
	{
		final String productValue = getVendorProductInfo().getProductNo();
		final BigDecimal qtyToDeliver = Util.coalesce(getSalesOrderQtyToDeliver(), getQtyToPurchase());

		return ProductAndQuantity.of(productValue, qtyToDeliver);
	}

	public static final class ErrorItemBuilder
	{
		private final PurchaseCandidate parent;
		private final PurchaseErrorItemBuilder innerBuilder;

		private ErrorItemBuilder(@NonNull final PurchaseCandidate parent)
		{
			this.parent = parent;
			innerBuilder = PurchaseErrorItem.builder()
					.purchaseCandidateId(parent.getId())
					.orgId(parent.getOrgId());
		}

		public ErrorItemBuilder purchaseItemId(final PurchaseItemId purchaseItemId)
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

		public ErrorItemBuilder adIssueId(final int adIssueId)
		{
			innerBuilder.adIssueId(adIssueId);
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

		public OrderItemBuilder purchaseItemId(final PurchaseItemId purchaseItemId)
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
		final PurchaseCandidateId id = getId();
		Check.assumeNotNull(id, "purchase candidate shall be saved: {}", this);

		Check.assumeEquals(id, purchaseOrderItem.getPurchaseCandidateId(),
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
