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

import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Creates {@link I_C_CompensationGroup_ContractSettings_TakeOver} records — per product category, which of the
 * contract's own discount lines is taken over from the customer's sales order.
 */
@RequiredArgsConstructor
public class C_CompensationGroup_ContractSettings_TakeOver_StepDef
{
	private final @NonNull C_CompensationGroup_ContractSettings_StepDefData settingsTable;
	private final @NonNull C_CompensationGroup_ContractSettings_TakeOver_StepDefData takeOverTable;
	private final @NonNull M_Product_Category_StepDefData productCategoryTable;
	private final @NonNull M_Product_StepDefData productTable;

	/**
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code Identifier} (required) — identifier for later reference</li>
	 *     <li>{@code C_CompensationGroup_ContractSettings_ID} (required, identifier-ref) — the settings record</li>
	 *     <li>{@code M_Product_Category_ID} (required, identifier-ref) — the product category the take-over applies to</li>
	 *     <li>{@code M_Product_ID} (required, identifier-ref) — the contract's own discount product</li>
	 * </ul>
	 * <pre>
	 * And metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:
	 *   | Identifier | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID    |
	 *   | takeOver1  | contractSettings                        | goodsCategory         | discountProduct |
	 * </pre>
	 */
	@Given("metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:")
	public void createTakeOvers(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_ContractSettings_TakeOver record = buildTakeOver(row);
			saveRecord(record);
			takeOverTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}

	/**
	 * Asserts that saving a take-over record is refused with the given {@code AD_Message.ErrorCode} — e.g. a second
	 * take-over for a product category that already has one on the same settings.
	 * <p>
	 * DataTable columns: same as {@code metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:}, except {@code Identifier} (not stored).
	 * <pre>
	 * Then creating C_CompensationGroup_ContractSettings_TakeOver is refused with error code DBUniqueConstraint:
	 *   | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID    |
	 *   | contractSettings                        | goodsCategory         | discountProduct |
	 * </pre>
	 */
	@Then("creating C_CompensationGroup_ContractSettings_TakeOver is refused with error code {word}:")
	public void createTakeOverRefused(@NonNull final String errorCode, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_ContractSettings_TakeOver record = buildTakeOver(row);
			StepDefUtil.assertRefusedWithErrorCode(errorCode, () -> saveRecord(record));
		});
	}

	private I_C_CompensationGroup_ContractSettings_TakeOver buildTakeOver(@NonNull final DataTableRow row)
	{
		final I_C_CompensationGroup_ContractSettings settings = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_C_CompensationGroup_ContractSettings_ID)
				.lookupNotNullIn(settingsTable);
		final I_M_Product_Category category = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_M_Product_Category_ID)
				.lookupNotNullIn(productCategoryTable);
		final I_M_Product product = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_M_Product_ID)
				.lookupNotNullIn(productTable);

		final I_C_CompensationGroup_ContractSettings_TakeOver record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		record.setAD_Org_ID(settings.getAD_Org_ID());
		record.setC_CompensationGroup_ContractSettings_ID(settings.getC_CompensationGroup_ContractSettings_ID());
		record.setM_Product_Category_ID(category.getM_Product_Category_ID());
		record.setM_Product_ID(product.getM_Product_ID());
		return record;
	}
}
