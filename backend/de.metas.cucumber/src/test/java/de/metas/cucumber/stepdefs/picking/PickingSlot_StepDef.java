package de.metas.cucumber.stepdefs.picking;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;

@RequiredArgsConstructor
public class PickingSlot_StepDef
{
	@NonNull private final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@NonNull private final PickingSlot_StepDefData pickingSlotsTable;

	@Given("metasfresh contains M_PickingSlot:")
	public void createPickingSlots(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPickingSlot);
	}

	private void createPickingSlot(@NonNull final DataTableRow row)
	{
		final String pickingSlotCode = row.getAsName("PickingSlot");

		final I_M_PickingSlot record = pickingSlotDAO.getPickingSlotIdAndCaptionByCode(pickingSlotCode)
				.map(pickingSlotIdAndCaption -> pickingSlotDAO.getById(pickingSlotIdAndCaption.getPickingSlotId()))
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_M_PickingSlot.class));
		record.setPickingSlot(pickingSlotCode);
		record.setIsActive(true);
		row.getAsOptionalBoolean("IsDynamic").ifPresent(record::setIsDynamic);

		if (InterfaceWrapperHelper.isNew(record))
		{
			final LocatorId mainLocatorId = getMainLocatorId();
			record.setM_Warehouse_ID(mainLocatorId.getWarehouseId().getRepoId());
			record.setM_Locator_ID(mainLocatorId.getRepoId());
		}

		// Make sure the picking slot is related for the test will come
		if (record.isDynamic())
		{
			record.setC_BPartner_ID(-1);
			record.setC_BPartner_Location_ID(-1);
		}

		InterfaceWrapperHelper.saveRecord(record);
		row.getAsOptionalIdentifier()
				.orElseGet(() -> StepDefDataIdentifier.ofString(pickingSlotCode))
				.putOrReplace(pickingSlotsTable, record);
	}

	private LocatorId getMainLocatorId()
	{
		return warehouseBL.getOrCreateDefaultLocatorId(WarehouseId.MAIN);
	}
}