package org.adempiere.warehouse.spi.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_Order;

import de.metas.util.Services;

public class MockedSimpleWarehouseAdvisor extends WarehouseAdvisor
{
	public static MockedSimpleWarehouseAdvisor createAndRegister(final WarehouseId warehouseId)
	{
		final MockedSimpleWarehouseAdvisor instance = new MockedSimpleWarehouseAdvisor(warehouseId);
		Services.registerService(IWarehouseAdvisor.class, instance);

		return instance;
	}

	private final WarehouseId warehouseId;

	private MockedSimpleWarehouseAdvisor(final WarehouseId warehouseId)
	{
		this.warehouseId = warehouseId;
	}

	@Override
	protected WarehouseId findOrderWarehouseId(final I_C_Order order)
	{
		return warehouseId;
	}

}
