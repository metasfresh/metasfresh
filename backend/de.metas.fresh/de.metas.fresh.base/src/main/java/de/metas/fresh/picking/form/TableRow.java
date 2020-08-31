package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.swat.base
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

import java.math.BigDecimal;
import java.util.Date;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
public class TableRow
{
	private final ShipmentScheduleId shipmentScheduleId;

	private final int bpartnerId;
	private final String bpartnerValue;
	private final String bpartnerName;
	private final int bpartnerLocationId;
	private final String bpartnerLocationName;

	private final String warehouseName;

	private final String warehouseDestName;

	private final int warehouseDestId;

	private final String deliveryVia;

	private final String shipper;

	private final TableRowKey key;

	private final boolean displayed;

	private final String freightCostRule;

	private final ProductId productId;
	private final String productName;
	
	private @NonNull BigDecimal qtyToDeliver;
	private Date deliveryDate;
	private Date preparationDate;


	public TableRow copy()
	{
		return toBuilder().build();
	}
}
