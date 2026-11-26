/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.org;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_Org;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AD_Org_StepDef
{
	private final AD_Org_StepDefData orgTable;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * {@code AD_Org_ID} -> the {@code IsDropShipTaxFromDestCountry} the organisation had before the scenario changed it.
	 */
	private final Map<Integer, Boolean> isDropShipTaxFromDestCountryBeforeScenario = new LinkedHashMap<>();

	public AD_Org_StepDef(@NonNull final AD_Org_StepDefData orgTable)
	{
		this.orgTable = orgTable;
	}

	@Given("metasfresh contains AD_Org:")
	public void metasfresh_contains_ad_orgs(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orgIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Org.COLUMNNAME_AD_Org_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final String name = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Org.COLUMNNAME_Name);
			// Value is optional: without it a unique one is generated, so each run gets a new organisation instead of the one of a previous run
			final String value = CoalesceUtil.coalesceSuppliersNotNull(
					() -> DataTableUtil.extractStringOrNullForColumnName(tableRow, I_AD_Org.COLUMNNAME_Value),
					() -> ValueAndName.unique(orgIdentifier).getValue());

			final I_AD_Org orgRecord = CoalesceUtil.coalesce(queryBL.createQueryBuilder(I_AD_Org.class)
																	 .addEqualsFilter(I_AD_Org.COLUMNNAME_Value, value)
																	 .create()
																	 .firstOnly(I_AD_Org.class),
															 InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_Org.class));

			Assertions.assertThat(orgRecord).isNotNull();

			orgRecord.setName(name);
			orgRecord.setValue(value);

			orgDAO.save(orgRecord);

			orgTable.putOrReplace(orgIdentifier, orgRecord);
		}
	}

	@And("load AD_Org:")
	public void load_AD_Org(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String orgCode = DataTableUtil.extractStringForColumnName(row, I_AD_Org.COLUMNNAME_Value);

			final I_AD_Org orgRecord = queryBL.createQueryBuilder(I_AD_Org.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, orgCode)
					.create()
					.firstOnlyOrNull(I_AD_Org.class);

			assertThat(orgRecord).as("AD_Org for identifier=%S", orgCode).isNotNull();

			final String orgIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_Org.COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
			orgTable.putOrReplace(orgIdentifier, orgRecord);
		}
	}

	/**
	 * Sets the organisation-wide drop-ship tax switch; the former value is restored after the scenario, also when a step failed.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends the organisation is loaded by its repo id
	 * @cucumber.example
	 * <pre>
	 * And the organisation 1000000 has IsDropShipTaxFromDestCountry "Y" for this scenario
	 * </pre>
	 */
	@And("the organisation {int} has IsDropShipTaxFromDestCountry {string} for this scenario")
	public void organisation_has_IsDropShipTaxFromDestCountry(final int orgRepoId, @NonNull final String isDropShipTaxFromDestCountry)
	{
		final I_AD_Org org = load(orgRepoId, I_AD_Org.class);
		isDropShipTaxFromDestCountryBeforeScenario.putIfAbsent(orgRepoId, org.isDropShipTaxFromDestCountry());
		org.setIsDropShipTaxFromDestCountry(parseYesNo(isDropShipTaxFromDestCountry));
		saveRecord(org);
	}

	/**
	 * The flag is org-wide and cucumber does not roll back, so a changed value would leak into the next scenario.
	 */
	@After
	public void restoreIsDropShipTaxFromDestCountry()
	{
		try
		{
			isDropShipTaxFromDestCountryBeforeScenario.forEach((orgRepoId, value) -> {
				final I_AD_Org org = load(orgRepoId, I_AD_Org.class);
				org.setIsDropShipTaxFromDestCountry(value);
				saveRecord(org);
			});
		}
		finally
		{
			isDropShipTaxFromDestCountryBeforeScenario.clear();
		}
	}

	private static boolean parseYesNo(@NonNull final String value)
	{
		if ("Y".equals(value) || "true".equals(value))
		{
			return true;
		}
		if ("N".equals(value) || "false".equals(value))
		{
			return false;
		}
		throw new IllegalArgumentException("Expected Y/N/true/false but got: " + value);
	}
}
