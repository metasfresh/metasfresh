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

import de.metas.cache.CacheMgt;
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
 */
@RequiredArgsConstructor
public class Translation_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ILanguageDAO languageDAO = Services.get(ILanguageDAO.class);

	@NonNull private final M_Product_StepDefData productTable;

	/** the language picked by the step "delete M_Product_Trl ... for the first active non-base system language" */
	private I_AD_Language thatLanguage;
	/** M_Product.Updated / UpdatedBy as remembered by {@link #set_M_Product_Trl_audit_of_base_language_by_SQL} */
	private final Map<String, String> productUpdatedByIdentifier = new HashMap<>();
	private final Map<String, Integer> productUpdatedByByIdentifier = new HashMap<>();

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

		final int deleted = DB.executeUpdateAndThrowExceptionOnFail(
				"DELETE FROM M_Product_Trl WHERE M_Product_ID=? AND AD_Language=?",
				new Object[] { productId(productIdentifier), thatLanguage.getAD_Language() },
				ITrx.TRXNAME_None);
		assertThat(deleted).as("deleted M_Product_Trl rows of " + productIdentifier + " for " + thatLanguage.getAD_Language()).isEqualTo(1);
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
	 * @cucumber.stepdef
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

		final Object[] params = { productId(productIdentifier), thatLanguage.getAD_Language() };
		final String where = " FROM M_Product_Trl WHERE M_Product_ID=? AND AD_Language=?";
		assertThat(DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT count(1)" + where, params)).as("M_Product_Trl rows of " + productIdentifier + " for " + thatLanguage.getAD_Language()).isEqualTo(1);
		assertThat(DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT CreatedBy" + where, params)).as("M_Product_Trl.CreatedBy").isEqualTo(expectedCreatedBy);
		assertThat(DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT UpdatedBy" + where, params)).as("M_Product_Trl.UpdatedBy").isEqualTo(expectedUpdatedBy);
	}

	/**
	 * Remembers {@code M_Product.Updated} / {@code M_Product.UpdatedBy} of the product, then writes {@code Updated} = 2020-01-01
	 * and {@code UpdatedBy} = 99 into the base-language M_Product_Trl row of the product with plain SQL (a trl row whose audit
	 * columns differ from the product's).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And set M_Product_Trl audit of product_b for the base language by SQL:
	 *   | Updated    | UpdatedBy |
	 *   | 2020-01-01 | 99        |
	 * </pre>
	 */
	@And("set M_Product_Trl audit of {word} for the base language by SQL:")
	public void set_M_Product_Trl_audit_of_base_language_by_SQL(
			@NonNull final String productIdentifier,
			@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final Timestamp updated = Timestamp.valueOf(row.getAsLocalDate("Updated").atStartOfDay());
		final int updatedBy = row.getAsInt("UpdatedBy");
		final int productId = productId(productIdentifier);

		productUpdatedByIdentifier.put(productIdentifier, DB.getSQLValueStringEx(ITrx.TRXNAME_None, "SELECT Updated::text FROM M_Product WHERE M_Product_ID=?", productId));
		productUpdatedByByIdentifier.put(productIdentifier, DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT UpdatedBy FROM M_Product WHERE M_Product_ID=?", productId));

		final int updatedRows = DB.executeUpdateAndThrowExceptionOnFail(
				"UPDATE M_Product_Trl SET Updated=?, UpdatedBy=? WHERE M_Product_ID=? AND AD_Language=?",
				new Object[] { updated, updatedBy, productId, languageDAO.retrieveBaseLanguage() },
				ITrx.TRXNAME_None);
		assertThat(updatedRows).as("updated base-language M_Product_Trl rows of " + productIdentifier).isEqualTo(1);
	}

	/**
	 * Loads the base-language M_Product_Trl row of the product as a PO, sets {@code Name} and saves it, i.e. the path of a
	 * translation save in the application ({@code PO.saveEx} -> {@code POTrlRepository.onTrlRecordChanged}).
	 *
	 * @cucumber.stepdef
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
		final PO trl = TableModelLoader.instance.getPO(
				Env.getCtx(),
				"M_Product_Trl",
				"M_Product_ID=? AND AD_Language=?",
				new Object[] { productId(productIdentifier), languageDAO.retrieveBaseLanguage() },
				ITrx.TRXNAME_None);
		assertThat(trl).as("base-language M_Product_Trl of " + productIdentifier).isNotNull();
		trl.set_ValueOfColumn("Name", newName);
		trl.saveEx();
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then M_Product product_a has Name "New name A"
	 * </pre>
	 */
	@Then("M_Product {word} has Name {string}")
	public void M_Product_has_Name(@NonNull final String productIdentifier, @NonNull final String expectedName)
	{
		final int productId = productId(productIdentifier);
		CacheMgt.get().reset(I_M_Product.Table_Name, productId);
		final String name = DB.getSQLValueStringEx(ITrx.TRXNAME_None, "SELECT Name FROM M_Product WHERE M_Product_ID=?", productId);
		assertThat(name).as("M_Product.Name of " + productIdentifier).isEqualTo(expectedName);
	}

	/**
	 * Checks {@code M_Product.Updated} / {@code M_Product.UpdatedBy} against the values remembered by the step that set the
	 * M_Product_Trl audit.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then M_Product product_b keeps Updated and UpdatedBy
	 * </pre>
	 */
	@Then("M_Product {word} keeps Updated and UpdatedBy")
	public void M_Product_keeps_Updated_and_UpdatedBy(@NonNull final String productIdentifier)
	{
		final String expectedUpdated = productUpdatedByIdentifier.get(productIdentifier);
		final Integer expectedUpdatedBy = productUpdatedByByIdentifier.get(productIdentifier);
		assertThat(expectedUpdated).as("remembered M_Product.Updated of " + productIdentifier).isNotNull();

		final int productId = productId(productIdentifier);
		assertThat(DB.getSQLValueStringEx(ITrx.TRXNAME_None, "SELECT Updated::text FROM M_Product WHERE M_Product_ID=?", productId)).as("M_Product.Updated of " + productIdentifier).isEqualTo(expectedUpdated);
		assertThat(DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT UpdatedBy FROM M_Product WHERE M_Product_ID=?", productId)).as("M_Product.UpdatedBy of " + productIdentifier).isEqualTo(expectedUpdatedBy);
	}

	private int productId(@NonNull final String productIdentifier)
	{
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).as("M_Product " + productIdentifier).isNotNull();
		return InterfaceWrapperHelper.getId(product);
	}
}
