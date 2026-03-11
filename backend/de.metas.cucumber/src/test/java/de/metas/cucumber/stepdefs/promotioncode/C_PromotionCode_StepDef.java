package de.metas.cucumber.stepdefs.promotioncode;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ValueAndName;
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

	/**
	 * Creates {@code C_PromotionCode} master data records.
	 *
	 * <h3>DataTable columns:</h3>
	 * <ul>
	 *   <li>{@code Identifier} — record identifier for cross-step references</li>
	 *   <li>{@code Value} (optional) — search key / code number (auto-generated if omitted)</li>
	 *   <li>{@code Name} (optional) — descriptive name (auto-generated if omitted)</li>
	 *   <li>{@code Description} (optional) — description text</li>
	 *   <li>{@code ValidTo} (optional) — expiry date (format: {@code yyyy-MM-dd})</li>
	 * </ul>
	 */
	@Given("metasfresh contains C_PromotionCode:")
	public void metasfresh_contains_c_promotioncode(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createPromotionCode);
	}

	private void createPromotionCode(@NonNull final DataTableRow row)
	{
		final I_C_PromotionCode record = InterfaceWrapperHelper.newInstance(I_C_PromotionCode.class);

		final ValueAndName valueAndName = row.suggestValueAndName();
		record.setValue(valueAndName.getValue());
		record.setName(valueAndName.getName());

		row.getAsOptionalString(I_C_PromotionCode.COLUMNNAME_Description)
				.ifPresent(record::setDescription);
		row.getAsOptionalLocalDate(I_C_PromotionCode.COLUMNNAME_ValidTo)
				.ifPresent(validTo -> record.setValidTo(java.sql.Timestamp.valueOf(validTo.atStartOfDay())));

		saveRecord(record);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> promotionCodeTable.putOrReplace(identifier, record));
	}
}
