package de.metas.inoutcandidate.process;

import com.google.common.collect.ImmutableList;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.OlAndSched;
import de.metas.inoutcandidate.api.OlAndSchedCollection;
import de.metas.inoutcandidate.api.OlAndSchedSupportingService;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.cockpit.stock.StockDataQuery;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import org.adempiere.inout.util.ReservationKey;
import org.adempiere.inout.util.ShipmentScheduleAvailableStock;
import org.adempiere.inout.util.ShipmentScheduleAvailableStockDetail;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorage;
import org.adempiere.inout.util.ShipmentScheduleQtyOnHandStorageFactory;
import org.compiere.SpringContextHolder;

public class M_ShipmentSchedule_ShowMatchingStorages extends JavaProcess
{
	// services
	private final ShipmentScheduleQtyOnHandStorageFactory qtyOnHandStorageFactory = SpringContextHolder.instance.getBean(ShipmentScheduleQtyOnHandStorageFactory.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);

	@Override
	protected String doIt() throws Exception
	{
		final OlAndSchedCollection olAndSchedCollection = retrieveOlAndScheds();
		final OlAndSched olAndSched = olAndSchedCollection.getSingleOlAndSched();

		final ShipmentScheduleQtyOnHandStorage storagesContainer = qtyOnHandStorageFactory.ofOlAndScheds(olAndSchedCollection);

		final ShipmentScheduleAvailableStock storageDetails = storagesContainer.getStockDetailsMatching(olAndSched.getQtyOnHandSegment());

		addLog("@QtyOnHand@ (@Total@): " + storageDetails.getTotalQtyAvailable(ReservationKey.NO_KEY));

		for (int storageIndex = 0; storageIndex < storageDetails.size(); storageIndex++)
		{
			final ShipmentScheduleAvailableStockDetail storageDetail = storageDetails.getStorageDetail(storageIndex);

			addLog("------------------------------------------------------------");
			addLog(storageDetail.toString());
		}

		//
		// Also show the Storage Query
		{
			final StockDataQuery materialQuery = storagesContainer.toQuery(olAndSched.getQtyOnHandSegment());
			addLog("------------------------------------------------------------");
			addLog("Storage Query:");
			addLog(String.valueOf(materialQuery));
		}

		return MSG_OK;
	}

	private OlAndSchedCollection retrieveOlAndScheds()
	{
		final ShipmentScheduleId shipmentScheduleId = getRecordIdAssumingTableName(I_M_ShipmentSchedule.Table_Name, ShipmentScheduleId::ofRepoId);
		final I_M_ShipmentSchedule shipmentSchedule = shipmentSchedulePA.getById(shipmentScheduleId);
		return shipmentSchedulePA.createOlAndScheds(ImmutableList.of(shipmentSchedule), new OlAndSchedSupportingService());
	}

}
