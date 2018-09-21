package org.adempiere.warehouse.spi.impl;

import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;

import de.metas.util.Services;

public class MockedSimpleWarehouseAdvisor extends WarehouseAdvisor
{
	public static MockedSimpleWarehouseAdvisor createAndRegister(final I_M_Warehouse warehouse)
	{
		final MockedSimpleWarehouseAdvisor instance = new MockedSimpleWarehouseAdvisor(warehouse);
		Services.registerService(IWarehouseAdvisor.class, instance);

		return instance;
	}

	private final I_M_Warehouse warehouse;

	private MockedSimpleWarehouseAdvisor(final I_M_Warehouse warehouse)
	{
		super();
		this.warehouse = warehouse;
	}

	@Override
	protected I_M_Warehouse findOrderWarehouse(final I_C_Order order)
	{
		return warehouse;
	}

}
