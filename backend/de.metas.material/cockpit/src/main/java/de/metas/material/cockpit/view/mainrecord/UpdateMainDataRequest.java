/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.material.cockpit.view.mainrecord;

import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class UpdateMainDataRequest
{
	@NonNull
	MainDataRecordIdentifier identifier;

	@Default
	BigDecimal countedQty = BigDecimal.ZERO;

	@Default
	BigDecimal onHandQtyChange = BigDecimal.ZERO;

	@Default
	BigDecimal directMovementQty = BigDecimal.ZERO;

	/**
	 * Quantity reserved for our customers
	 */
	@Default
	BigDecimal orderedSalesQty = BigDecimal.ZERO;
	/**
	 * Quantity ordered from our vendors
	 */
	@Default
	BigDecimal orderedPurchaseQty = BigDecimal.ZERO;

	@Default
	BigDecimal offeredQty = BigDecimal.ZERO;

	@Default
	BigDecimal offeredQtyNextDay = BigDecimal.ZERO;

	@Default
	BigDecimal qtyDemandPPOrder = BigDecimal.ZERO;
	@Default
	BigDecimal qtyDemandSalesOrder = BigDecimal.ZERO;
	@Default
	BigDecimal qtyDemandDDOrder = BigDecimal.ZERO;

	@Default
	BigDecimal qtySupplyPurchaseOrder = BigDecimal.ZERO;
	@Default
	BigDecimal qtySupplyDDOrder = BigDecimal.ZERO;
	@Default
	BigDecimal qtySupplyPPOrder = BigDecimal.ZERO;

	@Default
	BigDecimal qtySupplyRequired = BigDecimal.ZERO;

	@Default
	BigDecimal qtyInventoryCount = BigDecimal.ZERO;
	@Default
	Instant qtyInventoryTime = Instant.ofEpochSecond(0);

	@Nullable
	Integer qtyStockEstimateSeqNo;
	@Nullable
	BigDecimal qtyStockEstimateCount;
	@Nullable
	Instant qtyStockEstimateTime;
}
