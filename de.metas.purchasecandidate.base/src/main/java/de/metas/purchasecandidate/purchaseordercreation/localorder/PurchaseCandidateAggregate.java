package de.metas.purchasecandidate.purchaseordercreation.localorder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.OrgId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableSet;

import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.PurchaseCandidate;
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

	private final PurchaseCandidateAggregateKey aggregationKey;

	private LocalDateTime datePromised;
	private Quantity qtyToDeliver;
	private final ArrayList<PurchaseCandidate> purchaseCandidates = new ArrayList<>();
	private final HashSet<OrderAndLineId> salesOrderAndLineIds = new HashSet<>();

	private PurchaseCandidateAggregate(@NonNull final PurchaseCandidateAggregateKey aggregationKey)
	{
		this.aggregationKey = aggregationKey;
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
		datePromised = TimeUtil.min(datePromised, purchaseCandidate.getPurchaseDatePromised());

		if (purchaseCandidate.getSalesOrderAndLineId() != null)
		{
			salesOrderAndLineIds.add(purchaseCandidate.getSalesOrderAndLineId());
		}
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

	public Quantity getQtyToDeliver()
	{
		return qtyToDeliver;
	}

	void setQtyToDeliver(final Quantity qtyToDeliver)
	{
		this.qtyToDeliver = qtyToDeliver;
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

	public Set<OrderAndLineId> getSalesOrderAndLineIds()
	{
		return ImmutableSet.copyOf(salesOrderAndLineIds);
	}

	public void calculateAndSetQtyToDeliver()
	{
		if (purchaseCandidates.isEmpty())
		{
			return;
		}

		final Quantity qtyToPurchase = purchaseCandidates.get(0).getQtyToPurchase();
		final Quantity sum = purchaseCandidates
				.stream()
				.map(PurchaseCandidate::getQtyToPurchase)
				.reduce(qtyToPurchase.toZero(), Quantity::add);
		setQtyToDeliver(sum);

	}
}
