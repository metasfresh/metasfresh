package de.metas.fresh.picking.form;

import static org.adempiere.model.InterfaceWrapperHelper.create;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IClientOrgAware;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inoutcandidate.api.IPackageable;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.legacy.form.PackingMd;
import de.metas.picking.legacy.form.TableRow;
import de.metas.picking.legacy.form.TableRowKey;
import de.metas.picking.legacy.form.TableRowKey.TableRowKeyBuilder;

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
		final TableRowKeyBuilder keyBuilder = TableRowKey.builder();

		final BPartnerId bpartnerId = item.getBpartnerId();
		keyBuilder.bpartnerId(bpartnerId.getRepoId());

		final BigDecimal qtyToDeliver = item.getQtyToDeliver();

		final BPartnerLocationId bpartnerLocationId = item.getBpartnerLocationId();
		final String bPartnerAddress = item.getBpartnerAddress();
		keyBuilder.bpartnerAddress(Check.isEmpty(bPartnerAddress, true) ? null : bPartnerAddress.trim());

		final WarehouseId warehouseId = item.getWarehouseId();
		keyBuilder.warehouseId(WarehouseId.toRepoId(warehouseId));
		final String warehouseName = item.getWarehouseName();

		final int warehouseDestId;
		final String warehouseDestName;
		if (isGroupByWarehouseDest())
		{
			throw new UnsupportedOperationException("WarehouseDest is not supported");
			// keyBuilder.warehouseDestId(0);
		}
		else
		{
			warehouseDestId = 0;
			warehouseDestName = null;
			keyBuilder.warehouseDestId(-1);
		}

		final int productId;
		final String productName;
		if (isGroupByProduct()) // 06940: Note that this will also continue to group by product
		{
			productId = item.getProductId().getRepoId();
			productName = item.getProductName();

			keyBuilder.productId(productId > 0 ? productId : -1);
			keyBuilder.warehouseDestId(-1);
			keyBuilder.bpartnerAddress(null);
		}
		else
		{
			productId = -1;
			productName = null;
			keyBuilder.productId(-1);
		}

		final String deliveryVia = item.getDeliveryVia();

		final LocalDateTime deliveryDate = item.getDeliveryDate(); // customer01676
		final int shipmentScheduleId = item.getShipmentScheduleId().getRepoId();
		final String bpartnerValue = item.getBpartnerValue();
		final String bpartnerName = item.getBpartnerName();
		final String bPartnerLocationName = item.getBpartnerLocationName();
		final String shipper = item.getShipperName();

		final boolean isDisplayed = item.isDisplayed();

		final boolean groupByShipmentSchedule;

		final IClientOrgAware sched = create(Env.getCtx(), I_M_ShipmentSchedule.Table_Name, shipmentScheduleId, IClientOrgAware.class, ITrx.TRXNAME_ThreadInherited);
		groupByShipmentSchedule = Services.get(ISysConfigBL.class).getBooleanValue("de.metas.fresh.picking.form.FreshPackingMd.groupByShipmentSchedule", false, sched.getAD_Client_ID(), sched.getAD_Org_ID());

		final TableRowKey key;
		if (groupByShipmentSchedule)
		{
			key = keyBuilder
					.shipmentScheduleId(shipmentScheduleId)
					.build();
		}
		else
		{
			key = getCreateTableRowKey(keyBuilder);
		}
		final TableRow row = TableRow.builder()
				.shipmentScheduleId(shipmentScheduleId)
				.qtyToDeliver(qtyToDeliver)
				.bpartnerId(bpartnerId.getRepoId())
				.bpartnerValue(bpartnerValue)
				.bpartnerName(bpartnerName)
				.bpartnerLocationId(bpartnerLocationId.getRepoId())
				.bpartnerLocationName(bPartnerLocationName)
				.warehouseName(warehouseName)
				.deliveryVia(deliveryVia)
				.shipper(shipper)
				.displayed(isDisplayed)
				.key(key)
				//
				.warehouseDestId(warehouseDestId)
				.warehouseDestName(warehouseDestName)
				.productId(productId)
				.productName(productName)
				.deliveryDate(TimeUtil.asTimestamp(deliveryDate))
				.preparationDate(TimeUtil.asTimestamp(item.getPreparationDate()))
				.build();

		return row;
	}
}
