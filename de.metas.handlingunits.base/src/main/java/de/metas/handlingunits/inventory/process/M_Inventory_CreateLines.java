package de.metas.handlingunits.inventory.process;

import java.math.BigDecimal;

import org.compiere.model.I_M_Inventory;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.inventory.OldTransactionsStrategy;
import de.metas.process.Param;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class M_Inventory_CreateLines extends DraftInventoryBase
{
	@Param(parameterName = "ValueMin")
	private BigDecimal valueMin;

	@Param(parameterName = "Qty")
	private int Qty;

	@Override
	protected OldTransactionsStrategy createStrategy(@NonNull final I_M_Inventory inventoryRecord)
	{
		return OldTransactionsStrategy
				.builder()
				.maxLocators(Qty)
				.minimumPrice(valueMin)
				.movementDate(TimeUtil.asLocalDate(inventoryRecord.getMovementDate()))
				.build();
	}

}
