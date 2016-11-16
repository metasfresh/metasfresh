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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.inout.util.CachedObjects;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleStorageRecord;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_OrderLine;

import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.SvrProcess;
import de.metas.storage.IStorageBL;
import de.metas.storage.IStorageQuery;

public class M_ShipmentSchedule_ShowMatchingStorages extends SvrProcess
{
	private I_M_ShipmentSchedule shipmentSchedule;

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

		final I_C_OrderLine orderLine = shipmentSchedule.getC_OrderLine();
		final OlAndSched olAndSched = new OlAndSched(orderLine, shipmentSchedule);

		final ShipmentScheduleQtyOnHandStorage storagesContainer = new ShipmentScheduleQtyOnHandStorage();
		storagesContainer.setContext(this);
		storagesContainer.setDate(SystemTime.asDayTimestamp());
		storagesContainer.setCachedObjects(new CachedObjects());
		storagesContainer.loadStoragesFor(olAndSched);

		final List<ShipmentScheduleStorageRecord> storageRecords = storagesContainer.getStorageRecordsMatching(olAndSched);

		final BigDecimal qtyOnHandTotal = Services.get(IStorageBL.class).calculateQtyOnHandSum(storageRecords);
		addLog("@QtyOnHand@ (@Total@): " + qtyOnHandTotal);

		for (final ShipmentScheduleStorageRecord storage : storageRecords)
		{
			addLog("------------------------------------------------------------");
			addLog(storage.getSummary());
		}
		
		//
		// Also show the Storage Query
		{
			final IStorageQuery storageQuery = storagesContainer.createStorageQuery(olAndSched);
			addLog("------------------------------------------------------------");
			addLog("Storage Query:");
			addLog(storageQuery.getSummary());
		}

		return MSG_OK;
	}
}
