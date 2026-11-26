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

package de.metas.cucumber.stepdefs.i18n;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.i18n.ILanguageDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Language;
import org.compiere.model.I_M_Product;
import org.compiere.model.PO;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the translation tables ({@code M_Product_Trl}) and the writers that fill them.
 *
 * @see M_Product_StepDefData
 */
@RequiredArgsConstructor
public class Translation_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ILanguageDAO languageDAO = Services.get(ILanguageDAO.class);

	@NonNull private final M_Product_StepDefData productTable;

	/** the language picked by the step "delete M_Product_Trl ... for the first active non-base system language" */
	private I_AD_Language thatLanguage;
	/** M_Product.Updated / UpdatedBy per product identifier, as remembered by {@link #set_M_Product_Trl_of_base_language_by_SQL} */
	private final Map<String, ProductAudit> rememberedProductAuditByIdentifier = new HashMap<>();

	@Value
	private static class ProductAudit
	{
		@NonNull Timestamp updated;
		int updatedBy;
	}

	/**
	 * Picks the first AD_Language with IsActive=Y, IsSystemLanguage=Y, IsBaseLanguage=N (lowest AD_Language_ID) and deletes
	 * the M_Product_Trl row of the product for it. The language is remembered as "that language" for the next steps.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given delete M_Product_Trl of product_1 for the first active non-base system language
	 * </pre>
	 */
	@Given("delete M_Product_Trl of {word} for the first active non-base system language")
	public void delete_M_Product_Trl_for_first_active_non_base_system_language(@NonNull final String productIdentifier)
	{
		thatLanguage = queryBL.createQueryBuilder(I_AD_Language.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Language.COLUMNNAME_IsSystemLanguage, true)
				.addEqualsFilter(I_AD_Language.COLUMNNAME_IsBaseLanguage, false)
				.orderBy(I_AD_Language.COLUMNNAME_AD_Language_ID)
				.create()
				.first(I_AD_Language.class);
		assertThat(thatLanguage).as("an active non-base system AD_Language").isNotNull();

		final PO trl = loadProductTrl(productIdentifier, thatLanguage.getAD_Language());
		assertThat(trl).as("M_Product_Trl of " + productIdentifier + " for " + thatLanguage.getAD_Language()).isNotNull();
		trl.deleteEx(true);
	}

	/**
	 * Calls {@code ILanguageDAO.addMissingTranslations} for "that language" while no user is logged in: the context value
	 * {@code #AD_User_ID} is removed and put back (when it was set) afterwards.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When add missing translations for that language without a logged-in user
	 * </pre>
	 */
	@When("add missing translations for that language without a logged-in user")
	public void add_missing_translations_for_that_language_without_a_logged_in_user()
	{
		assertThat(thatLanguage).as("that language (set by the delete step)").isNotNull();

		final Properties ctx = Env.getCtx();
		final Object previousUserId = ctx.remove(Env.CTXNAME_AD_User_ID);
		try
		{
			languageDAO.addMissingTranslations(thatLanguage);
		}
		finally
		{
			if (previousUserId != null)
			{
				ctx.put(Env.CTXNAME_AD_User_ID, previousUserId);
			}
		}
	}

	/**
	 * Checks {@code CreatedBy} and {@code UpdatedBy} of the M_Product_Trl row of the product for "that language"; the row must exist.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then M_Product_Trl of product_1 for that language has CreatedBy 0 and UpdatedBy 0
	 * </pre>
	 */
	@Then("M_Product_Trl of {word} for that language has CreatedBy {int} and UpdatedBy {int}")
	public void M_Product_Trl_for_that_language_has_CreatedBy_and_UpdatedBy(
			@NonNull final String productIdentifier,
			final int expectedCreatedBy,
			final int expectedUpdatedBy)
	{
		assertThat(thatLanguage).as("that language (set by the delete step)").isNotNull();

		final PO trl = loadProductTrl(productIdentifier, thatLanguage.getAD_Language());
		assertThat(trl).as("M_Product_Trl of " + productIdentifier + " for " + thatLanguage.getAD_Language()).isNotNull();
		assertThat(trl.getCreatedBy()).as("M_Product_Trl.CreatedBy").isEqualTo(expectedCreatedBy);
		assertThat(trl.getUpdatedBy()).as("M_Product_Trl.UpdatedBy").isEqualTo(expectedUpdatedBy);
	}

	/**
	 * Remembers {@code M_Product.Updated} / {@code M_Product.UpdatedBy} of the product (for the step "keeps Updated and UpdatedBy"),
	 * then writes the given columns into the base-language M_Product_Trl row of the product with plain SQL (a trl row whose audit
	 * columns and Name differ from the product's; a model save would overwrite the audit columns).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Updated</b> — (required) date to store in M_Product_Trl.Updated<br>
	 *   <b>UpdatedBy</b> — (required) AD_User_ID to store in M_Product_Trl.UpdatedBy<br>
	 *   <b>Name</b> — (optional) text to store in M_Product_Trl.Name<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And set M_Product_Trl of product_b for the base language by SQL:
	 *   | Updated    | UpdatedBy | Name        |
	 *   | 2020-01-01 | 99        | Translation |
	 * </pre>
	 */
	@And("set M_Product_Trl of {word} for the base language by SQL:")
	public void set_M_Product_Trl_of_base_language_by_SQL(
			@NonNull final String productIdentifier,
			@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final Timestamp updated = Timestamp.valueOf(row.getAsLocalDate("Updated").atStartOfDay());
		final int updatedBy = row.getAsInt("UpdatedBy");
		final String name = row.getAsOptionalString("Name").orElse(null);

		final I_M_Product product = loadProduct(productIdentifier);
		rememberedProductAuditByIdentifier.put(productIdentifier, new ProductAudit(product.getUpdated(), product.getUpdatedBy()));

		final int updatedRows = DB.executeUpdateAndThrowExceptionOnFail(
				"UPDATE M_Product_Trl SET Updated=?, UpdatedBy=?, Name=COALESCE(?, Name) WHERE M_Product_ID=? AND AD_Language=?",
				new Object[] { updated, updatedBy, name, product.getM_Product_ID(), languageDAO.retrieveBaseLanguage() },
				ITrx.TRXNAME_None);
		assertThat(updatedRows).as("updated base-language M_Product_Trl rows of " + productIdentifier).isEqualTo(1);
	}

	/**
	 * Loads the base-language M_Product_Trl row of the product as a PO, sets {@code Name} and saves it, i.e. the path of a
	 * translation save in the application ({@code PO.saveEx} -> {@code POTrlRepository.onTrlRecordChanged}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When change Name of the base-language M_Product_Trl of product_a to "New name A" and save
	 * </pre>
	 */
	@When("change Name of the base-language M_Product_Trl of {word} to {string} and save")
	public void change_Name_of_the_base_language_M_Product_Trl_and_save(
			@NonNull final String productIdentifier,
			@NonNull final String newName)
	{
		final PO trl = loadProductTrl(productIdentifier, languageDAO.retrieveBaseLanguage());
		assertThat(trl).as("base-language M_Product_Trl of " + productIdentifier).isNotNull();
		trl.set_ValueOfColumn("Name", newName);
		trl.saveEx();
	}

	/**
	 * Checks {@code M_Product.Updated} / {@code M_Product.UpdatedBy} against the values remembered by the step that set the
	 * M_Product_Trl audit.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then M_Product product_b keeps Updated and UpdatedBy
	 * </pre>
	 */
	@Then("M_Product {word} keeps Updated and UpdatedBy")
	public void M_Product_keeps_Updated_and_UpdatedBy(@NonNull final String productIdentifier)
	{
		final ProductAudit expected = rememberedProductAuditByIdentifier.get(productIdentifier);
		assertThat(expected).as("remembered M_Product.Updated / UpdatedBy of " + productIdentifier).isNotNull();

		final I_M_Product product = loadProduct(productIdentifier);
		assertThat(product.getUpdated()).as("M_Product.Updated of " + productIdentifier).isEqualTo(expected.getUpdated());
		assertThat(product.getUpdatedBy()).as("M_Product.UpdatedBy of " + productIdentifier).isEqualTo(expected.getUpdatedBy());
	}

	/** the product freshly loaded from the DB (not the instance held by the scenario) */
	@NonNull
	private I_M_Product loadProduct(@NonNull final String productIdentifier)
	{
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).as("M_Product " + productIdentifier).isNotNull();
		return InterfaceWrapperHelper.loadOutOfTrx(InterfaceWrapperHelper.getId(product), I_M_Product.class);
	}

	/** the M_Product_Trl row of the product for the language, freshly loaded; null if there is none. */
	private PO loadProductTrl(@NonNull final String productIdentifier, @NonNull final String adLanguage)
	{
		return TableModelLoader.instance.getPO(
				Env.getCtx(),
				"M_Product_Trl",
				"M_Product_ID=? AND AD_Language=?",
				new Object[] { loadProduct(productIdentifier).getM_Product_ID(), adLanguage },
				ITrx.TRXNAME_None);
	}
}
