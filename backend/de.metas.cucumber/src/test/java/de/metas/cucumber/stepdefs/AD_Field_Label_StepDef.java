package de.metas.cucumber.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.assertThat;

public class AD_Field_Label_StepDef
{
	/**
	 * Checks the stored label of an application dictionary field per language: the field's translation
	 * ({@code AD_Field_Trl.Name}) and the translation of the element behind it ({@code AD_Element_Trl.Name}, via {@code AD_Field.AD_Name_ID}).
	 */
	@Then("validate AD_Field labels:")
	public void validate_AD_Field_labels(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final int adFieldId = row.getAsInt("AD_Field_ID");
			final String adLanguage = row.getAsString("AD_Language");
			final String expectedName = row.getAsString("Name");

			final String fieldTrlName = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
					"SELECT Name FROM AD_Field_Trl WHERE AD_Field_ID=? AND AD_Language=?", adFieldId, adLanguage);
			final String elementTrlName = DB.getSQLValueStringEx(ITrx.TRXNAME_None,
					"SELECT et.Name FROM AD_Field f JOIN AD_Element_Trl et ON et.AD_Element_ID=f.AD_Name_ID WHERE f.AD_Field_ID=? AND et.AD_Language=?", adFieldId, adLanguage);

			assertThat(fieldTrlName).as("AD_Field_Trl.Name of AD_Field " + adFieldId + " in " + adLanguage).isEqualTo(expectedName);
			assertThat(elementTrlName).as("AD_Element_Trl.Name of AD_Field " + adFieldId + " in " + adLanguage).isEqualTo(expectedName);
		});
	}
}
