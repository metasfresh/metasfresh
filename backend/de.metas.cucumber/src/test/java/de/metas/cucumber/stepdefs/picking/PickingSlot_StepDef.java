package de.metas.cucumber.stepdefs.picking;

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

@RequiredArgsConstructor
public class PickingSlot_StepDef
{
	@NonNull private final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@NonNull private final PickingSlot_StepDefData pickingSlotsTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;

	@Given("metasfresh contains M_PickingSlot:")
	public void createPickingSlots(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPickingSlot);
	}

	@Then("^after not more than (.*)s, validate M_PickingSlot:$")
	public void validateAndWaitPickingSlotStep(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		StepDefUtil.tryAndWait(timeoutSec, 1000,
							   () -> {
								   final SoftAssertions softAssertions = checkPickingSlot(dataTable);
								   return softAssertions.assertionErrorsCollected().isEmpty();
							   },
							   () -> checkPickingSlot(dataTable).assertAll());
	}

	@Then("validate M_PickingSlot:")
	public void validatePickingSlotStep(@NonNull final DataTable dataTable)
	{
		checkPickingSlot(dataTable).assertAll();
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

	@NonNull
	private SoftAssertions checkPickingSlot(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softAssertions = new SoftAssertions();

		DataTableRows.of(dataTable).forEach((tableRow) -> {
			final I_M_PickingSlot pickingSlot = tableRow
					.getAsIdentifier(I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID)
					.lookupIn(pickingSlotsTable);

			tableRow.getAsOptionalIdentifier(I_M_PickingSlot.COLUMNNAME_C_BPartner_ID)
					.map(identifier -> identifier.lookupIn(bpartnerTable))
					.map(I_C_BPartner::getC_BPartner_ID)
					.ifPresent(bpartnerId -> softAssertions.assertThat(pickingSlot.getC_BPartner_ID()).isEqualTo(bpartnerId));

			tableRow.getAsOptionalIdentifier(I_M_PickingSlot.COLUMNNAME_C_BPartner_Location_ID)
					.map(identifier -> identifier.lookupIn(bpartnerLocationTable))
					.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
					.ifPresent(bpartnerLocationId -> softAssertions.assertThat(pickingSlot.getC_BPartner_Location_ID()).isEqualTo(bpartnerLocationId));

			tableRow.getAsOptionalBoolean("IsAllocated")
					.ifPresent(isAllocated -> {
						if (isAllocated)
						{
							softAssertions.assertThat(pickingSlot.getC_BPartner_ID()).isGreaterThan(0);
							softAssertions.assertThat(pickingSlot.getC_BPartner_Location_ID()).isGreaterThan(0);
						}
						else
						{
							softAssertions.assertThat(pickingSlot.getC_BPartner_ID()).isEqualTo(0);
							softAssertions.assertThat(pickingSlot.getC_BPartner_Location_ID()).isEqualTo(0);
						}
					});
		});

		return softAssertions;
	}
}