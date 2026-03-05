package de.metas.cucumber.stepdefs.promotioncode;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableRow;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_PromotionCode;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@RequiredArgsConstructor
public class C_PromotionCode_StepDef
{
	@NonNull private final C_PromotionCode_StepDefData promotionCodeTable;

	@Given("metasfresh contains C_PromotionCode:")
	public void metasfresh_contains_c_promotioncode(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPromotionCode);
	}

	private void createPromotionCode(@NonNull final DataTableRow row)
	{
		final I_C_PromotionCode record = InterfaceWrapperHelper.newInstance(I_C_PromotionCode.class);

		row.getAsOptionalString(I_C_PromotionCode.COLUMNNAME_Value)
				.ifPresent(record::setValue);
		row.getAsOptionalString(I_C_PromotionCode.COLUMNNAME_Name)
				.ifPresent(record::setName);
		row.getAsOptionalInstant(I_C_PromotionCode.COLUMNNAME_ValidTo)
				.ifPresent(validTo -> record.setValidTo(java.sql.Timestamp.from(validTo)));

		saveRecord(record);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> promotionCodeTable.putOrReplace(identifier, record));
	}
}
