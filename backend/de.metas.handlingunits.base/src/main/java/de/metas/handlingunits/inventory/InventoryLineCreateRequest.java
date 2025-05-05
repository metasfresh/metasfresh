/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inventory;

import de.metas.contracts.FlatrateTermId;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class InventoryLineCreateRequest
{
	@NonNull
	InventoryId inventoryId;

	@NonNull
	ProductId productId;

	@NonNull
	Quantity qtyCount;

	@NonNull
	Quantity qtyBooked;

	@NonNull
	LocatorId locatorId;
	@Nullable FlatrateTermId modularContractId;

	@Nullable
	AttributeSetInstanceId attributeSetId;

	@Nullable
	HUAggregationType aggregationType;

	@Nullable
	List<InventoryLineHU> inventoryLineHUList;

	@Builder
	public InventoryLineCreateRequest(
			@NonNull final InventoryId inventoryId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyCount,
			@NonNull final Quantity qtyBooked,
			@NonNull final LocatorId locatorId,
			@Nullable final FlatrateTermId modularContractId,
			@Nullable final AttributeSetInstanceId attributeSetId,
			@Nullable final HUAggregationType aggregationType,
			@Nullable final List<InventoryLineHU> inventoryLineHUList)
	{

		Quantity.getCommonUomIdOfAll(qtyBooked, qtyCount);

		if (inventoryLineHUList != null)
		{
			inventoryLineHUList
					.forEach(lineHU -> Quantity.getCommonUomIdOfAll(lineHU.getQtyBook(), qtyBooked));
		}

		this.inventoryId = inventoryId;
		this.productId = productId;
		this.qtyCount = qtyCount;
		this.qtyBooked = qtyBooked;
		this.locatorId = locatorId;
		this.attributeSetId = attributeSetId;
		this.aggregationType = aggregationType;
		this.modularContractId = modularContractId;
		this.inventoryLineHUList = inventoryLineHUList;
	}

}
