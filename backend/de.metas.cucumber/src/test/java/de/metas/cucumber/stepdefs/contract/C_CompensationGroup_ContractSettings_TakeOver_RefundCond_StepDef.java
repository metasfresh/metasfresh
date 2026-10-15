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


package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Creates {@link I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond} records — the customer refund conditions whose
 * refund percentages a take-over record additionally takes over into the purchase order's take-over line.
 */
@RequiredArgsConstructor
public class C_CompensationGroup_ContractSettings_TakeOver_RefundCond_StepDef
{
	@NonNull private final C_CompensationGroup_ContractSettings_TakeOver_StepDefData takeOverTable;
	@NonNull private final C_Flatrate_Conditions_StepDefData conditionsTable;

	/**
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code C_CompensationGroup_ContractSettings_TakeOver_ID} (required, identifier-ref) — the take-over record</li>
	 *     <li>{@code C_Flatrate_Conditions_ID} (required, identifier-ref) — the customer's refund condition</li>
	 * </ul>
	 * <pre>
	 * And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:
	 *   | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
	 *   | takeOver1                                        | refundConditions1        |
	 * </pre>
	 */
	@Given("metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:")
	public void createTakeOverRefundConditions(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> saveRecord(buildTakeOverRefundCond(row)));
	}

	/**
	 * Asserts that saving a take-over refund condition is refused with the given {@code AD_Message.ErrorCode} — e.g. the same
	 * refund condition on a second take-over record of one settings.
	 * <p>
	 * DataTable columns: same as {@code metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_RefundCond:}.
	 * <pre>
	 * Then creating C_CompensationGroup_ContractSettings_TakeOver_RefundCond is refused with error code ContractCompGroup_TakeOverRefundCondDup:
	 *   | C_CompensationGroup_ContractSettings_TakeOver_ID | C_Flatrate_Conditions_ID |
	 *   | takeOver2                                        | refundConditions1        |
	 * </pre>
	 */
	@Then("creating C_CompensationGroup_ContractSettings_TakeOver_RefundCond is refused with error code {word}:")
	public void createTakeOverRefundCondRefused(@NonNull final String errorCode, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond record = buildTakeOverRefundCond(row);
			StepDefUtil.assertRefusedWithErrorCode(errorCode, () -> saveRecord(record));
		});
	}

	private I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond buildTakeOverRefundCond(@NonNull final DataTableRow row)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver takeOver = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID)
				.lookupNotNullIn(takeOverTable);
		final I_C_Flatrate_Conditions conditions = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.COLUMNNAME_C_Flatrate_Conditions_ID)
				.lookupNotNullIn(conditionsTable);

		final I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class);
		record.setAD_Org_ID(takeOver.getAD_Org_ID());
		record.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOver.getC_CompensationGroup_ContractSettings_TakeOver_ID());
		record.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		return record;
	}
}
