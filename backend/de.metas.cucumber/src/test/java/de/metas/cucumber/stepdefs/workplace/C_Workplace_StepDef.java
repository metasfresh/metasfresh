package de.metas.cucumber.stepdefs.workplace;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceCreateRequest;
import de.metas.workplace.WorkplaceService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Workplace;

@RequiredArgsConstructor
public class C_Workplace_StepDef
{
	@NonNull private final WorkplaceService workplaceService = SpringContextHolder.instance.getBean(WorkplaceService.class);

	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_Workplace_StepDefData workplaceTable;

	@Given("metasfresh contains C_Workplaces")
	public void createWorkplaces(DataTable dataTable) throws Throwable
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_Workplace.COLUMNNAME_C_Workplace_ID)
				.forEach(this::createWorkplace);
	}

	private void createWorkplace(final DataTableRow row)
	{
		final String name = row.suggestValueAndName().getName();
		final WarehouseId warehouseId = row.getAsIdentifier("M_Warehouse_ID").lookupNotNullIdIn(warehouseTable);
		final Workplace workplace = workplaceService.create(
				WorkplaceCreateRequest.builder()
						.name(name)
						.warehouseId(warehouseId)
						.build()
		);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> workplaceTable.put(identifier, workplace));
	}
}
