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
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Creates and updates {@link I_C_CompensationGroup_ContractSettings_TakeOver} records — per product category, the discount product of
 * the purchase order's own take-over line — and asserts the description the take-over writes onto the compensation order line.
 */
@RequiredArgsConstructor
public class C_CompensationGroup_ContractSettings_TakeOver_StepDef
{
	/**
	 * The partial unique index "one active take-over record per product category and settings" (= its {@code AD_Index_Table.Name}).
	 */
	private static final String CATEGORY_UNIQUE_INDEX_NAME = "c_compgroup_contractsettings_takeover_category_active_uq";

	@NonNull private final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

	@NonNull private final C_CompensationGroup_ContractSettings_StepDefData settingsTable;
	@NonNull private final C_CompensationGroup_ContractSettings_TakeOver_StepDefData takeOverTable;
	@NonNull private final M_Product_Category_StepDefData productCategoryTable;
	@NonNull private final M_Product_StepDefData productTable;

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
	 * Changes the product category of an existing take-over record (the record is registered under its {@code Identifier}
	 * by {@code metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:}).
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code Identifier} (required, identifier-ref) — the take-over record</li>
	 *     <li>{@code OPT.M_Product_Category_ID} (optional, identifier-ref) — the new product category</li>
	 * </ul>
	 * <pre>
	 * When update C_CompensationGroup_ContractSettings_TakeOver:
	 *   | Identifier | OPT.M_Product_Category_ID |
	 *   | takeOver1  | otherCategory             |
	 * </pre>
	 */
	@When("update C_CompensationGroup_ContractSettings_TakeOver:")
	public void updateTakeOvers(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_ContractSettings_TakeOver record = row.getAsIdentifier().lookupNotNullIn(takeOverTable);
			refresh(record);

			row.getAsOptionalIdentifier(I_C_CompensationGroup_ContractSettings_TakeOver.COLUMNNAME_M_Product_Category_ID)
					.map(identifier -> identifier.lookupNotNullIn(productCategoryTable))
					.ifPresent(category -> record.setM_Product_Category_ID(category.getM_Product_Category_ID()));

			saveRecord(record);
			takeOverTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}

	/**
	 * Asserts that saving a take-over record is refused with the given error code AND with the given user-facing message
	 * in German and English — e.g. a second take-over for a product category that already has one on the same settings
	 * (error code {@code DBUniqueConstraint}, message from the unique index's {@code AD_Index_Table.ErrorMsg}).
	 * <p>
	 * DataTable columns: same as {@code metasfresh contains C_CompensationGroup_ContractSettings_TakeOver:}, except {@code Identifier} (not stored), plus:
	 * <ul>
	 *     <li>{@code Message_de_DE} (required) — the exact expected message in German</li>
	 *     <li>{@code Message_en_US} (required) — the exact expected message in English</li>
	 * </ul>
	 * <pre>
	 * Then creating C_CompensationGroup_ContractSettings_TakeOver is refused with error code DBUniqueConstraint and messages:
	 *   | C_CompensationGroup_ContractSettings_ID | M_Product_Category_ID | M_Product_ID    | Message_de_DE | Message_en_US |
	 *   | contractSettings                        | goodsCategory         | discountProduct | Für diese ... | There is ...  |
	 * </pre>
	 */
	@Then("creating C_CompensationGroup_ContractSettings_TakeOver is refused with error code {word} and messages:")
	public void createTakeOverRefused(@NonNull final String errorCode, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_ContractSettings_TakeOver record = buildTakeOver(row);
			assertThatThrownBy(() -> saveRecord(record))
					.isInstanceOfSatisfying(AdempiereException.class, exception -> {
						assertThat(exception.getErrorCode()).as("ErrorCode of %s", exception).isEqualTo(errorCode);

						// In developer mode, DBUniqueConstraintException appends the violated index's name to its AD_Index_Table.ErrorMsg
						final String developerModeSuffix = exception instanceof DBUniqueConstraintException && developerModeBL.isEnabled()
								? " (AD_Index_Table:" + CATEGORY_UNIQUE_INDEX_NAME + ")"
								: "";

						final ITranslatableString message = AdempiereException.extractMessageTrl(exception);
						assertThat(message.translate("de_DE")).as("de_DE message of %s", exception).isEqualTo(row.getAsString("Message_de_DE") + developerModeSuffix);
						assertThat(message.translate("en_US")).as("en_US message of %s", exception).isEqualTo(row.getAsString("Message_en_US") + developerModeSuffix);
					});
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
