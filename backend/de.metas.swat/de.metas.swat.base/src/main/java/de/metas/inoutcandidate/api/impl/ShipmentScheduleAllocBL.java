package de.metas.inoutcandidate.api.impl;

import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ShipmentScheduleAllocBL implements IShipmentScheduleAllocBL
{
	@Override
	public I_M_ShipmentSchedule_QtyPicked createNewQtyPickedRecord(
			@NonNull final I_M_ShipmentSchedule sched,
			@NonNull final StockQtyAndUOMQty qty)
	{
		final I_M_ShipmentSchedule_QtyPicked record = createNewQtyPickedRecordNoSave(sched, qty, I_M_ShipmentSchedule_QtyPicked.class);
		saveRecord(record);
		return record;
	}

	@Override
	public <T extends I_M_ShipmentSchedule_QtyPicked> T createNewQtyPickedRecordNoSave(
			@NonNull final I_M_ShipmentSchedule sched,
			@NonNull final StockQtyAndUOMQty qty,
			@NonNull final Class<T> type)
	{
		final T record = newInstance(type, sched);
		record.setAD_Org_ID(sched.getAD_Org_ID());
		record.setM_ShipmentSchedule(sched);
		record.setIsActive(true);
		record.setQtyPicked(qty.getStockQty().toBigDecimal());

		qty.getUOMQtyOpt().ifPresent(uomQty -> {
			record.setCatch_UOM_ID(uomQty.getUomId().getRepoId());
			record.setQtyDeliveredCatch(uomQty.toBigDecimal());
		});

		//saveRecord(record);

		return record;
	}

	@Override
	public boolean isDelivered(final I_M_ShipmentSchedule_QtyPicked alloc)
	{
		// task 08959
		// Only the allocations made on inout lines that belong to a completed inout are considered Delivered.
		final I_M_InOutLine line = alloc.getM_InOutLine();
		if (line == null)
		{
			return false;
		}

		final org.compiere.model.I_M_InOut io = line.getM_InOut();

		return DocStatus.ofCode(io.getDocStatus()).isCompletedOrClosed();
	}

	@Override
	public Quantity retrieveQtyPickedAndUnconfirmed(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

		final BigDecimal qtyPicked = shipmentScheduleAllocDAO.retrieveQtyPickedAndUnconfirmed(shipmentSchedule);
		final I_C_UOM stockUOMRecord = productBL.getStockUOM(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()));

		return Quantity.of(qtyPicked, stockUOMRecord);
	}

	@Override
	public void deleteRecords(final List<? extends I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords)
	{
		InterfaceWrapperHelper.deleteAll(qtyPickedRecords);
	}

	@Override
	public StockQtyAndUOMQty extractQtyPicked(
			@NonNull final I_M_ShipmentSchedule_QtyPicked alloc,
			@NonNull final ProductId productId)
	{
		return StockQtyAndUOMQtys.create(
				alloc.getQtyPicked(), productId,
				alloc.getQtyDeliveredCatch(), UomId.ofRepoIdOrNull(alloc.getCatch_UOM_ID()));
	}

}
