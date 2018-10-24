package de.metas.inoutcandidate.process;

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
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.inout.util.ShipmentScheduleAvailableStockDetail;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorageFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.process.JavaProcess;

public class M_ShipmentSchedule_ShowMatchingStorages extends JavaProcess
{
	private I_M_ShipmentSchedule shipmentSchedule;

	private final transient ShipmentScheduleQtyOnHandStorageFactory //
	shipmentScheduleQtyOnHandStorageFactory = Adempiere.getBean(ShipmentScheduleQtyOnHandStorageFactory.class);

	@Override
	protected void prepare()
	{
		if (I_M_ShipmentSchedule.Table_Name.equals(getTableName()) && getRecord_ID() > 0)
		{
			shipmentSchedule = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_M_ShipmentSchedule.class, ITrx.TRXNAME_None);
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (shipmentSchedule == null || shipmentSchedule.getM_ShipmentSchedule_ID() <= 0)
		{
			throw new FillMandatoryException(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID);
		}

		final ShipmentScheduleQtyOnHandStorage storagesContainer = shipmentScheduleQtyOnHandStorageFactory.ofShipmentSchedule(shipmentSchedule);
		final List<ShipmentScheduleAvailableStockDetail> storageRecords = storagesContainer.getStockDetailsMatching(shipmentSchedule);

		final BigDecimal qtyOnHandTotal = ShipmentScheduleAvailableStockDetail.calculateQtyOnHandSum(storageRecords);
		addLog("@QtyOnHand@ (@Total@): " + qtyOnHandTotal);

		for (final ShipmentScheduleAvailableStockDetail storage : storageRecords)
		{
			addLog("------------------------------------------------------------");
			addLog(storage.getSummary());
		}

		//
		// Also show the Storage Query
		{
			final StockDataQuery materialQuery = storagesContainer.getMaterialQuery(shipmentSchedule);
			addLog("------------------------------------------------------------");
			addLog("Storage Query:");
			addLog(String.valueOf(materialQuery));
		}

		return MSG_OK;
	}
}
