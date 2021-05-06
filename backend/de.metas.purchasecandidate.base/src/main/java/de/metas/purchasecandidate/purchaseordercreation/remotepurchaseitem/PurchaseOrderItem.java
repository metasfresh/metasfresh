package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.Dimension;
import de.metas.mforecast.impl.ForecastLineId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

/**
 * Instances of this class represent a piece of a <b>factual</b> purchase order,
 * for which the system now needs to create a {@code C_Order} etc.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@ToString(exclude = "purchaseCandidate") // exclude purchaseCandidate to avoid stacktrace, since purchaseCandidate can hold a reference to this
public class PurchaseOrderItem implements PurchaseItem
{
	public static PurchaseOrderItem cast(final PurchaseItem purchaseItem)
	{
		return (PurchaseOrderItem)purchaseItem;
	}

	public static PurchaseOrderItem castOrNull(final PurchaseItem purchaseItem)
	{
		return (purchaseItem instanceof PurchaseOrderItem) ? cast(purchaseItem) : null;
	}

	@Getter
	private final PurchaseItemId purchaseItemId;

	@Getter
	private final ITableRecordReference transactionReference;

	@Getter
	private final String remotePurchaseOrderId;

	@Getter(AccessLevel.PRIVATE)
	private final PurchaseCandidate purchaseCandidate;

	@Getter
	private final Quantity purchasedQty;

	@Getter
	private final ZonedDateTime datePromised;

	@Getter
	private OrderAndLineId purchaseOrderAndLineId;

	@Getter
	private final Dimension dimension;

	@Builder(toBuilder = true)
	private PurchaseOrderItem(
			final PurchaseItemId purchaseItemId,
			@NonNull final PurchaseCandidate purchaseCandidate,
			@NonNull final Quantity purchasedQty,
			@NonNull final ZonedDateTime datePromised,
			@NonNull final String remotePurchaseOrderId,
			@Nullable final ITableRecordReference transactionReference,
			final OrderAndLineId purchaseOrderAndLineId,
			@Nullable final Dimension dimension)
	{
		this.purchaseItemId = purchaseItemId;

		this.purchaseCandidate = purchaseCandidate;

		this.purchasedQty = purchasedQty;
		this.datePromised = datePromised;
		this.remotePurchaseOrderId = remotePurchaseOrderId;

		this.purchaseOrderAndLineId = purchaseOrderAndLineId;

		final boolean remotePurchaseExists = !Objects.equals(remotePurchaseOrderId, NullVendorGatewayInvoker.NO_REMOTE_PURCHASE_ID);
		Check.errorIf(remotePurchaseExists && transactionReference == null,
				"If there is a remote purchase order, then the given transactionReference may not be null; remotePurchaseOrderId={}",
				remotePurchaseOrderId);
		this.transactionReference = transactionReference;

		this.dimension = dimension;
	}

	private PurchaseOrderItem(final PurchaseOrderItem from, final PurchaseCandidate newPurchaseCandidate)
	{
		this.purchaseItemId = from.purchaseItemId;

		this.purchaseCandidate = newPurchaseCandidate;

		this.purchasedQty = from.purchasedQty;
		this.datePromised = from.datePromised;
		this.remotePurchaseOrderId = from.remotePurchaseOrderId;

		this.purchaseOrderAndLineId = from.purchaseOrderAndLineId;

		this.transactionReference = from.transactionReference;

		this.dimension = from.dimension;
	}

	public PurchaseOrderItem copy(final PurchaseCandidate newPurchaseCandidate)
	{
		return new PurchaseOrderItem(this, newPurchaseCandidate);
	}

	@Override
	public PurchaseCandidateId getPurchaseCandidateId()
	{
		return getPurchaseCandidate().getId();
	}

	public ProductId getProductId()
	{
		return getPurchaseCandidate().getProductId();
	}

	public int getUomId()
	{
		final Quantity purchasedQty = getPurchasedQty();
		if (purchasedQty != null)
		{
			return purchasedQty.getUOMId();
		}

		return getQtyToPurchase().getUOMId();
	}

	public OrgId getOrgId()
	{
		return getPurchaseCandidate().getOrgId();
	}

	public WarehouseId getWarehouseId()
	{
		return getPurchaseCandidate().getWarehouseId();
	}

	public BPartnerId getVendorId()
	{
		return getPurchaseCandidate().getVendorId();
	}

	public ZonedDateTime getPurchaseDatePromised()
	{
		return getPurchaseCandidate().getPurchaseDatePromised();
	}

	public OrderId getSalesOrderId()
	{
		final OrderAndLineId salesOrderAndLineId = getPurchaseCandidate().getSalesOrderAndLineIdOrNull();

		return salesOrderAndLineId != null ? salesOrderAndLineId.getOrderId() : null;
	}

	public ForecastLineId getForecastLineId()
	{
		return getPurchaseCandidate().getForecastLineId();
	}

	private Quantity getQtyToPurchase()
	{
		return getPurchaseCandidate().getQtyToPurchase();
	}

	public boolean purchaseMatchesRequiredQty()
	{
		return getPurchasedQty().compareTo(getQtyToPurchase()) == 0;
	}

	private boolean purchaseMatchesOrExceedsRequiredQty()
	{
		return getPurchasedQty().compareTo(getQtyToPurchase()) >= 0;
	}

	public void setPurchaseOrderLineId(@NonNull final OrderAndLineId purchaseOrderAndLineId)
	{
		this.purchaseOrderAndLineId = purchaseOrderAndLineId;
	}

	public void markPurchasedIfNeeded()
	{
		if (purchaseMatchesOrExceedsRequiredQty())
		{
			purchaseCandidate.markProcessed();
		}
	}

	public void markReqCreatedIfNeeded()
	{
		if (purchaseMatchesOrExceedsRequiredQty())
		{
			purchaseCandidate.setReqCreated(true);
		}
	}

	public BigDecimal getPrice()
	{
		return purchaseCandidate.getPriceEnteredEff();
	}

	public Percent getDiscount()
	{
		return purchaseCandidate.getDiscountEff();
	}
}
