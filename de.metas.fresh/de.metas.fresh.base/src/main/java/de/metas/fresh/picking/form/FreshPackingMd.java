package de.metas.fresh.picking.form;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;

import de.metas.adempiere.form.PackingMd;
import de.metas.adempiere.form.TableRow;
import de.metas.adempiere.form.TableRowKey;
import de.metas.adempiere.form.TableRowKeyBuilder;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.inoutcandidate.api.IPackageable;

public class FreshPackingMd extends PackingMd
{
	public FreshPackingMd(final ITerminalContext terminalContext)
	{
		super(terminalContext.getWindowNo(),
				terminalContext.getAD_User_ID());
	}

	@Override
	protected TableRow createTableRow(final IPackageable item)
	{
		final TableRowKeyBuilder keyBuilder = new TableRowKeyBuilder();

		final int bpartnerId = item.getBPartnerId();
		keyBuilder.setBPartnerId(bpartnerId);

		final BigDecimal qtyToDeliver = item.getQtyToDeliver();

		final int bPartnerLocationId = item.getBPartnerLocationId();
		final String bPartnerAddress = item.getBPartnerAddress();
		keyBuilder.setBPartnerAddress(bPartnerAddress);

		final int warehouseId = item.getWarehouseId();
		keyBuilder.setWarehouseId(warehouseId);
		final String warehouseName = item.getWarehouseName();

		final int warehouseDestId;
		final String warehouseDestName;
		if (isGroupByWarehouseDest())
		{
			warehouseDestId = item.getWarehouseDestId();
			warehouseDestName = item.getWarehouseDestName();
			keyBuilder.setWarehouseDestId(warehouseDestId);
		}
		else
		{
			warehouseDestId = 0;
			warehouseDestName = null;
			keyBuilder.setWarehouseDestId(-1);
		}

		final int productId;
		final String productName;
		if (isGroupByProduct()) // 06940: Note that this will also continue to group by product
		{
			productId = item.getProductId();
			productName = item.getProductName();

			keyBuilder.setProductId(productId);
			keyBuilder.setWarehouseDestId(-1);
			keyBuilder.setBPartnerAddress(null);
		}
		else
		{
			productId = -1;
			productName = null;
			keyBuilder.setProductId(-1);
		}

		final String deliveryVia = item.getDeliveryVia();
		// final String deliveryViaName = item.getDeliveryViaName();
		// final int shipperId = rs.getInt(I_M_Shipper.COLUMNNAME_M_Shipper_ID);
		final Timestamp deliveryDate = item.getDeliveryDate(); // customer01676
		final int shipmentScheduleId = item.getShipmentScheduleId();
		final String bPartnerValue = item.getBPartnerValue();
		final String bPartnerName = item.getBPartnerName();
		final String bPartnerLocName = item.getBPartnerLocationName();
		final String shipper = item.getShipperName();

		final boolean isDisplayed = item.isDisplayed();

		final TableRowKey key = getCreateTableRowKey(keyBuilder);
		final TableRow row = new TableRow(
				bPartnerLocationId,
				shipmentScheduleId,
				qtyToDeliver,
				bpartnerId, bPartnerValue, bPartnerName,
				bPartnerLocName,
				warehouseName,
				deliveryVia,
				shipper,
				isDisplayed,
				key);
		row.setWarehouseDestId(warehouseDestId);
		row.setWarehouseDestName(warehouseDestName);
		row.setProductId(productId);
		row.setProductName(productName);
		row.setDeliveryDate(deliveryDate);
		row.setPreparationDate(item.getPreparationDate());

		return row;
	}
}
