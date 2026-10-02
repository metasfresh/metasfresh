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
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Creates {@link I_C_CompensationGroup_ContractSettings_TakeOver} records — per product category, which of the
 * contract's own discount lines is taken over from the customer's sales order — and asserts the composition
 * description the take-over writes onto the resulting compensation order line.
 */
@RequiredArgsConstructor
public class C_CompensationGroup_ContractSettings_TakeOver_StepDef
{
	private final @NonNull C_CompensationGroup_ContractSettings_StepDefData settingsTable;
	private final @NonNull C_CompensationGroup_ContractSettings_TakeOver_StepDefData takeOverTable;
	private final @NonNull M_Product_Category_StepDefData productCategoryTable;
	private final @NonNull M_Product_StepDefData productTable;
	private final @NonNull C_OrderLine_StepDefData orderLineTable;

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

	/**
	 * Asserts the {@code Description} of an order line created earlier (registered under an identifier by a
	 * {@code validate the created order lines} step) — the take-over composition, e.g. {@code 3% Bonus A + 3% Bonus B}.
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code C_OrderLine_ID} (required, identifier-ref) — the order line</li>
	 *     <li>{@code Description} (required) — the exact expected description</li>
	 * </ul>
	 * <pre>
	 * Then validate the take-over composition description of the order lines:
	 *   | C_OrderLine_ID | Description                          |
	 *   | ol_discount    | 3% Bonus A + 3% Bonus B              |
	 * </pre>
	 */
	@Then("validate the take-over composition description of the order lines:")
	public void validateTakeOverDescription(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_OrderLine orderLine = row.getAsIdentifier(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID).lookupNotNullIn(orderLineTable);
			refresh(orderLine);
			assertThat(orderLine.getDescription())
					.as("Description of C_OrderLine %s", row.getAsIdentifier(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID).getAsString())
					.isEqualTo(row.getAsString(I_C_OrderLine.COLUMNNAME_Description));
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
