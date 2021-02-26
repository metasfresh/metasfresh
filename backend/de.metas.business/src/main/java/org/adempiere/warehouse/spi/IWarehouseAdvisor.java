package org.adempiere.warehouse.spi;

import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.order.OrderLineId;
import de.metas.util.ISingletonService;

/**
 * Service used to advice which shall be the Warehouse of given document/document lines
 *
 * @author tsa
 *
 */
public interface IWarehouseAdvisor extends ISingletonService
{
	/**
	 * Suggests warehouse to be used by given order line
	 */
	public WarehouseId evaluateWarehouse(final I_C_OrderLine orderLine);

	public WarehouseId evaluateWarehouse(final OrderLineId orderLineId);

	/**
	 * Suggests warehouse to be used by given order
	 */
	public WarehouseId evaluateOrderWarehouse(final I_C_Order order);
}
