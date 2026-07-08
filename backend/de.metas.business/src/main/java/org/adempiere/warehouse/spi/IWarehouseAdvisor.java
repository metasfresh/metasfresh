package org.adempiere.warehouse.spi;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderLineId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;

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

	/**
	 * Returns the customer's picking warehouse if the given BPartner is flagged as customer
	 * and has a warehouse assigned that is flagged as picking warehouse; otherwise {@code null}.
	 */
	@Nullable
	public WarehouseId evaluateCustomerPickingWarehouse(@NonNull BPartnerId bpartnerId);
}
