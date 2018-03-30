package de.metas.material.event.stock;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-event
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

@Value
public class StockChangedEvent implements MaterialEvent
{
	EventDescriptor eventDescriptor;
	ProductDescriptor productDescriptor;
	int warehouseId;
	BigDecimal qtyOnHand;
	BigDecimal qtyOnHandOld;

	@Builder
	private StockChangedEvent(
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final ProductDescriptor productDescriptor,
			final int warehouseId,
			@NonNull final BigDecimal qtyOnHand,
			@NonNull final BigDecimal qtyOnHandOld)
	{
		Check.assume(warehouseId > 0, "warehouseId > 0");

		this.eventDescriptor = eventDescriptor;
		this.productDescriptor = productDescriptor;
		this.warehouseId = warehouseId;
		this.qtyOnHand = qtyOnHand;
		this.qtyOnHandOld = qtyOnHandOld;
	}
	
	public int getProductId()
	{
		return productDescriptor.getProductId();
	}
}
