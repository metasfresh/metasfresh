package org.adempiere.warehouse.spi;

import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;

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
	 *
	 * @param orderLine
	 * @return
	 */
	public I_M_Warehouse evaluateWarehouse(final I_C_OrderLine orderLine);

	public I_M_Warehouse evaluateWarehouse(final OrderLineId orderLineId);

	/**
	 * Suggests warehouse to be used by given order
	 *
	 * @param order
	 * @return
	 */
	public I_M_Warehouse evaluateOrderWarehouse(final I_C_Order order);
}
