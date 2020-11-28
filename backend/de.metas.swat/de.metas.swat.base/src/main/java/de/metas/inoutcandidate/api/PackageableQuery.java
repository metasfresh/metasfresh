package de.metas.inoutcandidate.api;

import java.time.LocalDate;

import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PackageableQuery
{
	public static final PackageableQuery ALL = PackageableQuery.builder().build();

	BPartnerId customerId;
	WarehouseTypeId warehouseTypeId;
	WarehouseId warehouseId;
	LocalDate deliveryDate;
	LocalDate preparationDate;
	ShipperId shipperId;

	/** retrieve only those packageables which are created from sales order/lines */
	boolean onlyFromSalesOrder;
	OrderId salesOrderId;
}
