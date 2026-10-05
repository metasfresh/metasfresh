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
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_M_Product;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Creates {@link I_C_CompensationGroup_ContractSettings_TakeOver_Product} records — the customer discount products whose
 * percentages a take-over record takes over from the linked sales order.
 */
@RequiredArgsConstructor
public class C_CompensationGroup_ContractSettings_TakeOver_Product_StepDef
{
	private final @NonNull C_CompensationGroup_ContractSettings_TakeOver_StepDefData takeOverTable;
	private final @NonNull M_Product_StepDefData productTable;

	/**
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code C_CompensationGroup_ContractSettings_TakeOver_ID} (required, identifier-ref) — the take-over record</li>
	 *     <li>{@code M_Product_ID} (required, identifier-ref) — the customer's discount product</li>
	 * </ul>
	 * <pre>
	 * And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:
	 *   | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID            |
	 *   | takeOver1                                        | customerDiscountProduct |
	 * </pre>
	 */
	@Given("metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:")
	public void createTakeOverProducts(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> saveRecord(buildTakeOverProduct(row)));
	}

	/**
	 * Asserts that saving a take-over product is refused with the given {@code AD_Message.ErrorCode} — e.g. the same
	 * customer discount product on a second take-over record of one settings.
	 * <p>
	 * DataTable columns: same as {@code metasfresh contains C_CompensationGroup_ContractSettings_TakeOver_Product:}.
	 * <pre>
	 * Then creating C_CompensationGroup_ContractSettings_TakeOver_Product is refused with error code ContractCompGroup_TakeOverProductUnique:
	 *   | C_CompensationGroup_ContractSettings_TakeOver_ID | M_Product_ID            |
	 *   | takeOver2                                        | customerDiscountProduct |
	 * </pre>
	 */
	@Then("creating C_CompensationGroup_ContractSettings_TakeOver_Product is refused with error code {word}:")
	public void createTakeOverProductRefused(@NonNull final String errorCode, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_ContractSettings_TakeOver_Product record = buildTakeOverProduct(row);
			StepDefUtil.assertRefusedWithErrorCode(errorCode, () -> saveRecord(record));
		});
	}

	private I_C_CompensationGroup_ContractSettings_TakeOver_Product buildTakeOverProduct(@NonNull final DataTableRow row)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver takeOver = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID)
				.lookupNotNullIn(takeOverTable);
		final I_M_Product product = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings_TakeOver_Product.COLUMNNAME_M_Product_ID)
				.lookupNotNullIn(productTable);

		final I_C_CompensationGroup_ContractSettings_TakeOver_Product record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
		record.setAD_Org_ID(takeOver.getAD_Org_ID());
		record.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOver.getC_CompensationGroup_ContractSettings_TakeOver_ID());
		record.setM_Product_ID(product.getM_Product_ID());
		return record;
	}
}
