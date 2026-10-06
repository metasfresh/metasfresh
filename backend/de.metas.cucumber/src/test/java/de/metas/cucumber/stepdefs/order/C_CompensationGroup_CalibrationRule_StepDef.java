/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.order;

import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.bpgroup.C_BP_Group_StepDefData;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Assertions;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/** Step definitions for {@link I_C_CompensationGroup_CalibrationRule} records. */
@RequiredArgsConstructor
public class C_CompensationGroup_CalibrationRule_StepDef
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull private final C_CompensationGroup_CalibrationRule_StepDefData ruleTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BP_Group_StepDefData bpGroupTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final C_CompensationGroup_Schema_StepDefData schemaTable;

	/**
	 * Creates calibration rules; the variant {@code expecting error} expects the save to be rejected with the given AD_Message key.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 * <b>Identifier</b> — (required) alias of the rule<br>
	 * <b>GroupCompensationCalibrationFactor</b> — (required) the factor<br>
	 * <b>SeqNo</b> — (optional) evaluation order, defaults to 10<br>
	 * <b>C_BPartner_ID</b> — (optional, identifier-ref) customer<br>
	 * <b>C_BP_Group_ID</b> — (optional, identifier-ref) business partner group<br>
	 * <b>M_Product_ID</b> — (optional, identifier-ref) product<br>
	 * <b>C_CompensationGroup_Schema_ID</b> — (optional, identifier-ref) schema<br>
	 * <b>ErrorMessageKey</b> — (required with {@code expecting error}) AD_Message value of the expected error<br>
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData, C_BP_Group_StepDefData, M_Product_StepDefData, C_CompensationGroup_Schema_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_CompensationGroup_CalibrationRule:
	 *   | Identifier | C_BPartner_ID | M_Product_ID | GroupCompensationCalibrationFactor |
	 *   | rule_1     | customer      | component    | 0.5                                |
	 * </pre>
	 */
	@Given("^metasfresh contains C_CompensationGroup_CalibrationRule( expecting error)?:$")
	public void metasfresh_contains_C_CompensationGroup_CalibrationRule(
			@Nullable final String expectingError,
			@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final Runnable create = () -> createRule(row);
			if (expectingError == null)
			{
				create.run();
			}
			else
			{
				assertFailsWithMessageKey(row, create);
			}
		});
	}

	private void createRule(@NonNull final DataTableRow row)
	{
		final I_C_CompensationGroup_CalibrationRule record = newInstance(I_C_CompensationGroup_CalibrationRule.class);
		record.setGroupCompensationCalibrationFactor(row.getAsBigDecimal(I_C_CompensationGroup_CalibrationRule.COLUMNNAME_GroupCompensationCalibrationFactor));
		record.setSeqNo(row.getAsOptionalInt(I_C_CompensationGroup_CalibrationRule.COLUMNNAME_SeqNo).orElse(10));
		row.getAsOptionalIdentifier(I_C_CompensationGroup_CalibrationRule.COLUMNNAME_C_BPartner_ID)
				.ifPresent(id -> record.setC_BPartner_ID(id.lookupNotNullIn(bpartnerTable).getC_BPartner_ID()));
		row.getAsOptionalIdentifier(I_C_CompensationGroup_CalibrationRule.COLUMNNAME_C_BP_Group_ID)
				.ifPresent(id -> record.setC_BP_Group_ID(id.lookupNotNullIn(bpGroupTable).getC_BP_Group_ID()));
		row.getAsOptionalIdentifier(I_C_CompensationGroup_CalibrationRule.COLUMNNAME_M_Product_ID)
				.ifPresent(id -> record.setM_Product_ID(id.lookupNotNullIn(productTable).getM_Product_ID()));
		row.getAsOptionalIdentifier(I_C_CompensationGroup_CalibrationRule.COLUMNNAME_C_CompensationGroup_Schema_ID)
				.ifPresent(id -> record.setC_CompensationGroup_Schema_ID(id.lookupNotNullIn(schemaTable).getC_CompensationGroup_Schema_ID()));
		saveRecord(record);

		ruleTable.putOrReplace(row.getAsIdentifier(), record);
	}

	/**
	 * Changes the factor of an existing rule; the variant {@code expecting error} expects the save to be rejected with the given AD_Message key.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 * <b>Identifier</b> — (required, identifier-ref) the rule<br>
	 * <b>GroupCompensationCalibrationFactor</b> — (required) the new factor<br>
	 * <b>ErrorMessageKey</b> — (required with {@code expecting error}) AD_Message value of the expected error<br>
	 * @cucumber.depends StepDefData: C_CompensationGroup_CalibrationRule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update C_CompensationGroup_CalibrationRule:
	 *   | Identifier | GroupCompensationCalibrationFactor |
	 *   | rule_1     | 0.4                                |
	 * </pre>
	 */
	@When("^update C_CompensationGroup_CalibrationRule( expecting error)?:$")
	public void update_C_CompensationGroup_CalibrationRule(
			@Nullable final String expectingError,
			@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final Runnable update = () -> {
				final I_C_CompensationGroup_CalibrationRule record = getRuleFresh(row);
				record.setGroupCompensationCalibrationFactor(row.getAsBigDecimal(I_C_CompensationGroup_CalibrationRule.COLUMNNAME_GroupCompensationCalibrationFactor));
				saveRecord(record);
			};
			if (expectingError == null)
			{
				update.run();
			}
			else
			{
				assertFailsWithMessageKey(row, update);
			}
		});
	}

	/**
	 * Deactivates existing rules.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns <b>Identifier</b> — (required, identifier-ref) the rule<br>
	 * @cucumber.depends StepDefData: C_CompensationGroup_CalibrationRule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And deactivate C_CompensationGroup_CalibrationRule:
	 *   | Identifier |
	 *   | rule_1     |
	 * </pre>
	 */
	@And("deactivate C_CompensationGroup_CalibrationRule:")
	public void deactivate_C_CompensationGroup_CalibrationRule(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_CalibrationRule record = getRuleFresh(row);
			record.setIsActive(false);
			saveRecord(record);
		});
	}

	/**
	 * Deletes existing rules; the variant {@code expecting error} expects the delete to be rejected with the given AD_Message key.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 * <b>Identifier</b> — (required, identifier-ref) the rule<br>
	 * <b>ErrorMessageKey</b> — (required with {@code expecting error}) AD_Message value of the expected error<br>
	 * @cucumber.depends StepDefData: C_CompensationGroup_CalibrationRule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And delete C_CompensationGroup_CalibrationRule expecting error:
	 *   | Identifier | ErrorMessageKey                                           |
	 *   | rule_1     | C_CompensationGroup_CalibrationRule_UsedDeactivateInstead |
	 * </pre>
	 */
	@And("^delete C_CompensationGroup_CalibrationRule( expecting error)?:$")
	public void delete_C_CompensationGroup_CalibrationRule(
			@Nullable final String expectingError,
			@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final Runnable deleteRule = () -> delete(getRuleFresh(row));
			if (expectingError == null)
			{
				deleteRule.run();
			}
			else
			{
				assertFailsWithMessageKey(row, deleteRule);
			}
		});
	}

	private I_C_CompensationGroup_CalibrationRule getRuleFresh(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final I_C_CompensationGroup_CalibrationRule record = identifier.lookupNotNullIn(ruleTable);
		refresh(record);
		return record;
	}

	private void assertFailsWithMessageKey(@NonNull final DataTableRow row, @NonNull final Runnable action)
	{
		final AdMessageKey expectedKey = AdMessageKey.of(row.getAsString("ErrorMessageKey"));
		final String expectedErrorCode = msgBL.getErrorCode(expectedKey);
		final String expectedErrorCodeEffective = expectedErrorCode != null ? expectedErrorCode : expectedKey.toAD_Message();

		try
		{
			action.run();
			Assertions.fail("An exception with message key " + expectedKey + " should have been thrown");
		}
		catch (final AdempiereException exception)
		{
			assertThat(exception.getErrorCode()).isEqualTo(expectedErrorCodeEffective);
		}
	}
}
