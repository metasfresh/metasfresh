package de.metas.purchasecandidate;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.Dimension;
import de.metas.error.AdIssueId;
import de.metas.mforecast.impl.ForecastLineId;
import de.metas.money.CurrencyId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseErrorItem.PurchaseErrorItemBuilder;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemId;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem.PurchaseOrderItemBuilder;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Check;
import de.metas.util.lang.ExternalId;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toCollection;

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
	private Quantity qtyToPurchase;

	@Setter(AccessLevel.NONE)
	private Quantity qtyToPurchaseInitial;

	@Nullable
	private PurchaseProfitInfo profitInfoOrNull;

	@NonNull
	private ZonedDateTime purchaseDatePromised;

	@Setter(AccessLevel.NONE)
	private ZonedDateTime purchaseDatePromisedInitial;

	@Nullable
	private ZonedDateTime purchaseDateOrdered;

	private final Duration reminderTime;

	@Getter(AccessLevel.PRIVATE)
	private final PurchaseCandidateImmutableFields immutableFields;

	@Getter(AccessLevel.NONE)
	private final PurchaseCandidateState state;

	@Getter(AccessLevel.NONE)
	private final ArrayList<PurchaseOrderItem> purchaseOrderItems;

	@Getter(AccessLevel.NONE)
	private final ArrayList<PurchaseErrorItem> purchaseErrorItems;

	private final BigDecimal price;
	private final BigDecimal priceInternal;
	private final BigDecimal priceActual;
	private final BigDecimal priceEnteredEff;
	private final Percent discount;
	private final Percent discountInternal;
	private final Percent discountEff;
	private final boolean isManualDiscount;
	private final boolean isManualPrice;
	private final boolean isTaxIncluded;
	private final TaxCategoryId taxCategoryId;

	@Builder
	private PurchaseCandidate(
			final PurchaseCandidateId id,

			@Nullable final ExternalId externalHeaderId,
			@Nullable final ExternalId externalLineId,
			@NonNull final DemandGroupReference groupReference,
			@Nullable final OrderAndLineId salesOrderAndLineIdOrNull,

			final boolean prepared,
			final boolean processed,
			final boolean locked,
			final boolean reqCreated,
			//
			@NonNull final BPartnerId vendorId,
			//
			@NonNull final OrgId orgId,
			@NonNull final WarehouseId warehouseId,
			//
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final String vendorProductNo,
			@NonNull final Quantity qtyToPurchase,
			@NonNull final ZonedDateTime purchaseDatePromised,
			@Nullable final ZonedDateTime purchaseDateOrdered,
			final Duration reminderTime,
			@Nullable final PurchaseProfitInfo profitInfoOrNull,
			@Singular final List<PurchaseItem> purchaseItems,
			final boolean aggregatePOs,
			@Nullable final ForecastLineId forecastLineId,
			@Nullable final Dimension dimension,
			@Nullable final PurchaseCandidateSource source,
			@Nullable final BigDecimal price,
			@Nullable final BigDecimal priceInternal,
			@Nullable final BigDecimal priceActual,
			@Nullable final BigDecimal priceEnteredEff,
			@Nullable final Percent discount,
			@Nullable final Percent discountInternal,
			@Nullable final Percent discountEff,
			final boolean isManualDiscount,
			final boolean isManualPrice,
			final boolean isTaxIncluded,
			@Nullable final TaxCategoryId taxCategoryId,
			@Nullable final CurrencyId currencyId)
	{
		this.id = id;
		this.priceInternal = priceInternal;
		this.priceEnteredEff = priceEnteredEff;
		this.discountInternal = discountInternal;
		this.discountEff = discountEff;
		this.isTaxIncluded = isTaxIncluded;
		this.taxCategoryId = taxCategoryId;
		immutableFields = PurchaseCandidateImmutableFields.builder()
				.groupReference(groupReference)
				.salesOrderAndLineIdOrNull(salesOrderAndLineIdOrNull)
				.vendorId(vendorId)
				.orgId(orgId)
				.warehouseId(warehouseId)
				.productId(productId)
				.attributeSetInstanceId(attributeSetInstanceId)
				.vendorProductNo(vendorProductNo)
				.aggregatePOs(aggregatePOs)
				.forecastLineId(forecastLineId)
				.dimension(dimension)
				.externalHeaderId(externalHeaderId)
				.externalLineId(externalLineId)
				.source(source)
				.currencyId(currencyId)
				.build();

		state = PurchaseCandidateState.builder()
				.prepared(prepared)
				.processed(processed)
				.locked(locked)
				.reqCreated(reqCreated)
				.build();

		this.qtyToPurchase = qtyToPurchase;
		this.qtyToPurchaseInitial = qtyToPurchase;

		this.purchaseDatePromised = purchaseDatePromised;
		this.purchaseDatePromisedInitial = purchaseDatePromised;
		this.purchaseDateOrdered = purchaseDateOrdered;
		this.reminderTime = reminderTime;

		this.profitInfoOrNull = profitInfoOrNull;
		this.price = price;
		this.priceActual = priceActual;
		this.discount = discount;
		this.isManualDiscount = isManualDiscount;
		this.isManualPrice = isManualPrice;

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

		profitInfoOrNull = from.profitInfoOrNull;

		purchaseDatePromised = from.purchaseDatePromised;
		purchaseDatePromisedInitial = from.purchaseDatePromisedInitial;
		purchaseDateOrdered = from.purchaseDateOrdered;
		reminderTime = from.reminderTime;

		immutableFields = from.immutableFields;
		state = from.state.copy();

		purchaseOrderItems = from.purchaseOrderItems.stream()
				.map(item -> item.copy(this))
				.collect(toCollection(ArrayList::new));
		purchaseErrorItems = new ArrayList<>(from.purchaseErrorItems);
		price = from.price;
		priceActual = from.priceActual;
		discount = from.discount;
		isManualDiscount = from.isManualDiscount;
		isManualPrice = from.isManualPrice;
		isTaxIncluded = from.isTaxIncluded;
		taxCategoryId = from.taxCategoryId;
		priceInternal = from.priceInternal;
		discountInternal = from.discountInternal;
		priceEnteredEff = from.priceEnteredEff;
		discountEff = from.discountEff;
	}

	public PurchaseCandidate copy()
	{
		return new PurchaseCandidate(this);
	}

	public OrgId getOrgId()
	{
		return getImmutableFields().getOrgId();
	}

	public ProductId getProductId()
	{
		return getImmutableFields().getProductId();
	}

	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return getImmutableFields().getAttributeSetInstanceId();
	}

	public String getVendorProductNo()
	{
		return getImmutableFields().getVendorProductNo();
	}

	public WarehouseId getWarehouseId()
	{
		return getImmutableFields().getWarehouseId();
	}

	public DemandGroupReference getGroupReference()
	{
		return getImmutableFields().getGroupReference();
	}

	@Nullable
	public OrderAndLineId getSalesOrderAndLineIdOrNull()
	{
		return getImmutableFields().getSalesOrderAndLineIdOrNull();
	}

	@Nullable
	public ForecastLineId getForecastLineId()
	{
		return getImmutableFields().getForecastLineId();
	}

	@Nullable
	public Dimension getDimension()
	{
		return getImmutableFields().getDimension();
	}

	public BPartnerId getVendorId()
	{
		return getImmutableFields().getVendorId();
	}

	@Nullable
	public CurrencyId getCurrencyId()
	{
		return getImmutableFields().getCurrencyId();
	}

	public boolean isAggregatePOs()
	{
		return getImmutableFields().isAggregatePOs();
	}

	public void setPrepared(final boolean prepared)
	{
		state.setPrepared(prepared);
	}

	public boolean isPrepared()
	{
		return state.isPrepared();
	}

	/**
	 * Flags this instance as processed, so no more changes can be made.<br>
	 * Does not persist this instance!
	 */
	public void markProcessed()
	{
		state.setProcessed();
	}

	public boolean isProcessedOrLocked()
	{
		return isProcessed() || isLocked();
	}

	public boolean isLocked()
	{
		return state.isLocked();
	}

	public boolean isProcessed()
	{
		return state.isProcessed();
	}

	public void setReqCreated(final boolean reqCreated)
	{
		state.setReqCreated(reqCreated);
	}

	public boolean isReqCreated()
	{
		return state.isReqCreated();
	}

	public @Nullable PurchaseCandidateSource getSource()
	{
		return getImmutableFields().getSource();
	}

	public @Nullable ExternalId getExternalLineId()
	{
		return getImmutableFields().getExternalLineId();
	}

	public @Nullable ExternalId getExternalHeaderId()
	{
		return getImmutableFields().getExternalHeaderId();
	}

	public boolean hasChanges()
	{
		return id == null // never saved
				|| state.hasChanges()
				|| qtyToPurchase.compareTo(qtyToPurchaseInitial) != 0
				|| !Objects.equals(purchaseDatePromised, purchaseDatePromisedInitial);
	}

	public void markSaved(@NonNull final PurchaseCandidateId newId)
	{
		id = newId;

		state.markSaved();

		qtyToPurchaseInitial = qtyToPurchase;
		purchaseDatePromisedInitial = purchaseDatePromised;
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

		public ErrorItemBuilder adIssueId(final AdIssueId adIssueId)
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

		public OrderItemBuilder datePromised(@NonNull final ZonedDateTime datePromised)
		{
			innerBuilder.datePromised(datePromised);
			return this;
		}

		public OrderItemBuilder purchasedQty(@NonNull final Quantity purchasedQty)
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

		public OrderItemBuilder dimension(final Dimension dimension)
		{
			innerBuilder.dimension(dimension);
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
				"The given purchaseOrderItem's purchaseCandidateId needs to be equal to this instance's id; purchaseOrderItem={}; this={}",
				purchaseOrderItem, this);

		purchaseOrderItems.add(purchaseOrderItem);
	}

	public Quantity getPurchasedQty()
	{
		return purchaseOrderItems.stream()
				.map(PurchaseOrderItem::getPurchasedQty)
				.reduce(Quantity::add)
				.orElseGet(() -> getQtyToPurchase().toZero());
	}

	public List<PurchaseOrderItem> getPurchaseOrderItems()
	{
		return ImmutableList.copyOf(purchaseOrderItems);
	}

	public List<PurchaseErrorItem> getPurchaseErrorItems()
	{
		return ImmutableList.copyOf(purchaseErrorItems);
	}
}
