package de.metas.ui.web.pickingV2.packageable;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.WarehouseTypeId;

import java.time.LocalDate;

/*
 * #%L
 * metasfresh-webui-api
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
@Builder
public class PackageableViewFilterVO
{
	public static final PackageableViewFilterVO ANY = builder().build();

	public static final String FILTER_ID = "defaultFilter";
	public static final String PARAM_C_Order_ID = "C_Order_ID";
	public static final String PARAM_Customer_ID = "C_BPartner_ID";
	public static final String PARAM_M_Warehouse_ID = "M_Warehouse_ID";
	public static final String PARAM_M_Warehouse_Type_ID = "M_Warehouse_Type_ID";
	public static final String PARAM_DeliveryDate = "DeliveryDate";
	public static final String PARAM_PreparationDate = "PreparationDate";
	public static final String PARAM_M_Shipper_ID = "M_Shipper_ID";

	OrderId salesOrderId;
	BPartnerId customerId;
	WarehouseTypeId warehouseTypeId;
	WarehouseId warehouseId;
	LocalDate deliveryDate;
	LocalDate preparationDate;
	ShipperId shipperId;
}
