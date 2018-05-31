package de.metas.purchasecandidate.purchaseordercreation.localorder;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.OrgId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableSet;

import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.quantity.Quantity;
import lombok.NonNull;

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

public class PurchaseCandidateAggregate
{
	public static PurchaseCandidateAggregate of(final PurchaseCandidateAggregateKey aggregationKey)
	{
		return new PurchaseCandidateAggregate(aggregationKey);
	}

	private final PurchaseDemandId purchaseDemandId;
	private final PurchaseCandidateAggregateKey aggregationKey;

	private final ArrayList<PurchaseCandidate> purchaseCandidates = new ArrayList<>();
	private Quantity qtyToDeliver;
	private Quantity qtyToDeliverTotal;
	private LocalDateTime datePromised;
	private final HashSet<OrderLineId> salesOrderLineIds = new HashSet<>();

	private PurchaseCandidateAggregate(@NonNull final PurchaseCandidateAggregateKey aggregationKey)
	{
		this.aggregationKey = aggregationKey;
		purchaseDemandId = PurchaseDemandId.newAggregateId();

		final I_C_UOM uom = loadOutOfTrx(aggregationKey.getUomId(), I_C_UOM.class); // TODO: get rid of this loader!
		qtyToDeliver = Quantity.zero(uom);
		qtyToDeliverTotal = Quantity.zero(uom);
	}

	public void add(@NonNull final PurchaseCandidate purchaseCandidate)
	{
		final PurchaseCandidateAggregateKey purchaseCandidateAggKey = PurchaseCandidateAggregateKey.fromPurchaseCandidate(purchaseCandidate);
		if (!aggregationKey.equals(purchaseCandidateAggKey))
		{
			throw new AdempiereException("" + purchaseCandidate + " does not have the expected aggregation key: " + aggregationKey);
		}

		purchaseCandidates.add(purchaseCandidate);

		//
		final BigDecimal qtyToPurchase = purchaseCandidate.getQtyToPurchase();
		final BigDecimal purchasedQty = purchaseCandidate.getPurchasedQty();
		final BigDecimal qtyToPurchaseTotal = qtyToPurchase.add(purchasedQty);
		qtyToDeliver = qtyToDeliver.add(qtyToPurchase);
		qtyToDeliverTotal = qtyToDeliverTotal.add(qtyToPurchaseTotal);

		//
		datePromised = TimeUtil.min(datePromised, purchaseCandidate.getDateRequired());

		if (purchaseCandidate.getSalesOrderAndLineId() != null)
		{
			salesOrderLineIds.add(purchaseCandidate.getSalesOrderAndLineId().getOrderLineId());
		}
	}

	public PurchaseDemandId getPurchaseDemandId()
	{
		return purchaseDemandId;
	}

	public OrgId getOrgId()
	{
		return aggregationKey.getOrgId();
	}

	public WarehouseId getWarehouseId()
	{
		return aggregationKey.getWarehouseId();
	}

	public ProductId getProductId()
	{
		return aggregationKey.getProductId();
	}

	public AttributeSetInstanceId getAttributeSetInstanceId()
	{
		return AttributeSetInstanceId.NONE;
	}

	public Quantity getQtyToDeliverTotal()
	{
		return qtyToDeliverTotal;
	}

	public Quantity getQtyToDeliver()
	{
		return qtyToDeliver;
	}

	public LocalDateTime getDatePromised()
	{
		return datePromised;
	}

	public LocalDateTime getPreparationDate()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Set<OrderLineId> getSalesOrderLineIds()
	{
		return ImmutableSet.copyOf(salesOrderLineIds);
	}
}
