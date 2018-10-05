package de.metas.inoutcandidate.api;

import java.time.LocalDate;

import org.adempiere.warehouse.WarehouseId;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PackageableQuery
{
	public static final PackageableQuery ALL = PackageableQuery.builder().build();

	private WarehouseId warehouseId;
	private LocalDate deliveryDate;
	/** retrieve only those packageables which are created from sales order/lines */
	private boolean onlyFromSalesOrder;
}
