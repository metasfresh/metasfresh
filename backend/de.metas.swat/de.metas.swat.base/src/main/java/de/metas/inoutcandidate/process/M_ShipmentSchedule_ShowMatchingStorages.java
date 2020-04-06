package de.metas.inoutcandidate.process;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.inout.util.ShipmentScheduleAvailableStockDetail;
import org.adempiere.inout.util.ShipmentScheduleAvailableStock;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorageFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.process.JavaProcess;

public class M_ShipmentSchedule_ShowMatchingStorages extends JavaProcess
{
	private I_M_ShipmentSchedule shipmentSchedule;

	private final transient ShipmentScheduleQtyOnHandStorageFactory //
	shipmentScheduleQtyOnHandStorageFactory = SpringContextHolder.instance.getBean(ShipmentScheduleQtyOnHandStorageFactory.class);

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
		final ShipmentScheduleAvailableStock storageDetails = storagesContainer.getStockDetailsMatching(shipmentSchedule);

		addLog("@QtyOnHand@ (@Total@): " + storageDetails.getTotalQtyAvailable());

		for (int storageIndex = 0; storageIndex < storageDetails.size(); storageIndex++)
		{
			final ShipmentScheduleAvailableStockDetail storageDetail = storageDetails.getStorageDetail(storageIndex);

			addLog("------------------------------------------------------------");
			addLog(storageDetail.toString());
		}

		//
		// Also show the Storage Query
		{
			final StockDataQuery materialQuery = storagesContainer.toQuery(shipmentSchedule);
			addLog("------------------------------------------------------------");
			addLog("Storage Query:");
			addLog(String.valueOf(materialQuery));
		}

		return MSG_OK;
	}
}
