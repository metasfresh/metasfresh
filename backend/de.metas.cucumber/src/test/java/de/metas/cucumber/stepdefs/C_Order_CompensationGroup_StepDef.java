package de.metas.cucumber.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_CompensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@RequiredArgsConstructor
public class C_Order_CompensationGroup_StepDef
{
	private final @NonNull C_Order_CompensationGroup_StepDefData compGroupTable;
	private final @NonNull C_Order_StepDefData orderTable;

	@Given("metasfresh contains C_Order_CompensationGroups:")
	public void createCompensationGroups(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Order orderRecord = row.getAsIdentifier(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_ID)
					.lookupNotNullIn(orderTable);

			final I_C_Order_CompensationGroup record = newInstance(I_C_Order_CompensationGroup.class);
			record.setAD_Org_ID(orderRecord.getAD_Org_ID());
			record.setC_Order_ID(orderRecord.getC_Order_ID());
			record.setName(row.getAsString(I_C_Order_CompensationGroup.COLUMNNAME_Name));

			saveRecord(record);

			compGroupTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}
}
