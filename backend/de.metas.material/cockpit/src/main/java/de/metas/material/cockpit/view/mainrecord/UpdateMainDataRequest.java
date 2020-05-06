package de.metas.material.cockpit.view.mainrecord;

import java.math.BigDecimal;

import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

	@Default
	BigDecimal reservedSalesQty = BigDecimal.ZERO;

	/**
	 * Quantity ordered from our vendors
	 */
	@Default
	BigDecimal orderedPurchaseQty = BigDecimal.ZERO;

	@Default
	BigDecimal reservedPurchaseQty = BigDecimal.ZERO;

	@Default
	BigDecimal offeredQty = BigDecimal.ZERO;

	@Default
	BigDecimal requiredForProductionQty = BigDecimal.ZERO;
}
