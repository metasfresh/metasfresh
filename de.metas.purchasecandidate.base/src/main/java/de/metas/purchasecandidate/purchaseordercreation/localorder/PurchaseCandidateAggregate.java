package de.metas.purchasecandidate.purchaseordercreation.localorder;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.OrgId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import de.metas.money.Currency;
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
	public static PurchaseCandidateAggregate of(final PurchaseOrderLineAggregationKey aggregationKey)
	{
		return new PurchaseCandidateAggregate(aggregationKey);
	}

	private static final AtomicInteger nextId = new AtomicInteger(1);

	private final PurchaseDemandId purchaseDemandId;
	private final PurchaseOrderLineAggregationKey aggregationKey;
	private final ArrayList<PurchaseCandidate> purchaseCandidates = new ArrayList<>();
	private Quantity qtyToDeliver;
	private Quantity qtyToDeliverTotal;

	private PurchaseCandidateAggregate(@NonNull final PurchaseOrderLineAggregationKey aggregationKey)
	{
		this.aggregationKey = aggregationKey;
		this.purchaseDemandId = PurchaseDemandId.ofTableAndRecordId("aggregate", nextId.getAndIncrement());

		final I_C_UOM uom = loadOutOfTrx(aggregationKey.getUomId(), I_C_UOM.class); // TODO: get rid of this loader!
		this.qtyToDeliver = Quantity.zero(uom);
		this.qtyToDeliverTotal = Quantity.zero(uom);
	}

	public void add(@NonNull final PurchaseCandidate purchaseCandidate)
	{
		final PurchaseOrderLineAggregationKey purchaseCandidateAggKey = PurchaseOrderLineAggregationKey.fromPurchaseCandidate(purchaseCandidate);
		if (!aggregationKey.equals(purchaseCandidateAggKey))
		{
			throw new AdempiereException("" + purchaseCandidate + " does not have the expected aggregation key: " + aggregationKey);
		}

		purchaseCandidates.add(purchaseCandidate);

		final BigDecimal qtyToPurchase = purchaseCandidate.getQtyToPurchase();
		final BigDecimal purchasedQty = purchaseCandidate.getPurchasedQty();
		final BigDecimal qtyToPurchaseTotal = qtyToPurchase.add(purchasedQty);

		this.qtyToDeliver = qtyToDeliver.add(qtyToPurchase);
		this.qtyToDeliverTotal = qtyToDeliverTotal.add(qtyToPurchaseTotal);

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

	public AttributeSetInstanceId getAsiId()
	{
		return aggregationKey.getAsiId();
	}

	public Quantity getQtyToDeliverTotal()
	{
		return qtyToDeliverTotal;
	}

	public Quantity getQtyToDeliver()
	{
		return qtyToDeliver;
	}

	public Currency getCurrency()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public LocalDateTime getDatePromised()
	{
		return aggregationKey.getDatePromised();
	}

	public LocalDateTime getPreparationDate()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
