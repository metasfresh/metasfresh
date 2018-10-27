package org.adempiere.inout.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.Collection;

import org.adempiere.warehouse.WarehouseId;

import de.metas.material.cockpit.stock.StockDataItem;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;

/**
 * Stock detail with mutable qtyOnHand.
 */
@ToString
@Getter
public class ShipmentScheduleAvailableStockDetail
{
	public static ShipmentScheduleAvailableStockDetail of(final StockDataItem result)
	{
		return builder()
				.productId(result.getProductId())
				.warehouseId(result.getWarehouseId())
				//.bpartnerId(result.getBpartnerId())
				.storageAttributesKey(result.getStorageAttributesKey())
				.qtyOnHand(result.getQtyOnHand())
				.build();
	}

	public static BigDecimal calculateQtyOnHandSum(final Collection<ShipmentScheduleAvailableStockDetail> storageRecords)
	{
		return storageRecords.stream()
				.map(ShipmentScheduleAvailableStockDetail::getQtyOnHand)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private final ProductId productId;
	private final WarehouseId warehouseId;
	private final AttributesKey storageAttributesKey;
//	private final int bpartnerId;
	private BigDecimal qtyOnHand;

	@Builder
	private ShipmentScheduleAvailableStockDetail(
			final ProductId productId,
			final WarehouseId warehouseId,
			@NonNull final AttributesKey storageAttributesKey,
//			final int bpartnerId,
			@NonNull final BigDecimal qtyOnHand)
	{
		this.productId = productId;
		this.warehouseId = warehouseId;
		this.storageAttributesKey = storageAttributesKey;
	//	this.bpartnerId = bpartnerId;
		this.qtyOnHand = qtyOnHand;
	}

	public String getSummary()
	{
		return toString();
	}

	private void setQtyOnHand(@NonNull final BigDecimal qtyOnHand)
	{
		this.qtyOnHand = qtyOnHand;
	}

	public void addQtyOnHand(final BigDecimal qtyOnHandToAdd)
	{
		final BigDecimal qtyOnHandOld = getQtyOnHand();
		final BigDecimal qtyOnHandNew = qtyOnHandOld.add(qtyOnHandToAdd);
		setQtyOnHand(qtyOnHandNew);
	}

	public void subtractQtyOnHand(final BigDecimal qtyOnHandToRemove)
	{
		final BigDecimal qtyOnHandOld = getQtyOnHand();
		final BigDecimal qtyOnHandNew = qtyOnHandOld.subtract(qtyOnHandToRemove);
		setQtyOnHand(qtyOnHandNew);
	}
}
