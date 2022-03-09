/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

import de.metas.common.util.EmptyUtil;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.order.InvoiceRule;
import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PricingSystem;

import java.util.List;
import java.util.Map;

import static de.metas.contracts.commission.model.I_C_Flatrate_Conditions.COLUMNNAME_C_HierarchyCommissionSettings_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_C_Flatrate_Conditions_ID;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_Name;
import static de.metas.contracts.model.I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_Flatrate_Conditions_StepDef
{
	private final StepDefData<I_C_HierarchyCommissionSettings> hierarchyCommissionSettingsTable;
	private final StepDefData<I_C_Flatrate_Conditions> conditionsTable;
	private final StepDefData<I_M_PricingSystem> pricingSysTable;

	public C_Flatrate_Conditions_StepDef(
			@NonNull final StepDefData<I_C_HierarchyCommissionSettings> hierarchyCommissionSettingsTable,
			@NonNull final StepDefData<I_C_Flatrate_Conditions> conditionsTable,
			@NonNull final StepDefData<I_M_PricingSystem> pricingSysTable)
	{
		this.hierarchyCommissionSettingsTable = hierarchyCommissionSettingsTable;
		this.conditionsTable = conditionsTable;
		this.pricingSysTable = pricingSysTable;
	}

	@Given("metasfresh contains C_Flatrate_Conditions:")
	public void metasfresh_contains_c_flatrate_conditions(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String name = tableRow.get(COLUMNNAME_Name);
			assertThat(name).as(COLUMNNAME_Name + " is mandatory").isNotBlank();

			final String type = tableRow.get(COLUMNNAME_Type_Conditions);
			assertThat(type).as(COLUMNNAME_Type_Conditions + " is mandatory").isNotBlank();

			final I_C_Flatrate_Conditions flatrateConditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class);

			final String commissionSettingsIdentifier = tableRow.get("OPT." + COLUMNNAME_C_HierarchyCommissionSettings_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(commissionSettingsIdentifier))
			{
				final I_C_HierarchyCommissionSettings commissionSettings = hierarchyCommissionSettingsTable.get(commissionSettingsIdentifier);
				assertThat(commissionSettings).as("Missing C_HierarchyCommissionSettings record for identifier " + commissionSettingsIdentifier).isNotNull();
				InterfaceWrapperHelper
						.create(flatrateConditions, de.metas.contracts.commission.model.I_C_Flatrate_Conditions.class)
						.setC_HierarchyCommissionSettings_ID(commissionSettings.getC_HierarchyCommissionSettings_ID());
			}

			flatrateConditions.setName(name);
			flatrateConditions.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			flatrateConditions.setType_Conditions(type);
			flatrateConditions.setC_Flatrate_Transition_ID(StepDefConstants.FLATRATE_TRANSITION_ID.getRepoId());
			flatrateConditions.setInvoiceRule(InvoiceRule.AfterDelivery.getCode());
			flatrateConditions.setDocStatus(X_C_Flatrate_Conditions.DOCSTATUS_Completed);
			flatrateConditions.setProcessed(true);

			final String pricingSystemIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Conditions.COLUMNNAME_M_PricingSystem_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(pricingSystemIdentifier))
			{
				final I_M_PricingSystem pricingSystem = pricingSysTable.get(pricingSystemIdentifier);
				flatrateConditions.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
			}

			final String onFlatrateTermExtend = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Flatrate_Conditions.COLUMNNAME_OnFlatrateTermExtend);
			if (Check.isNotBlank(onFlatrateTermExtend))
			{
				flatrateConditions.setOnFlatrateTermExtend(onFlatrateTermExtend);
			}

			InterfaceWrapperHelper.saveRecord(flatrateConditions);

			final String conditionsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Flatrate_Conditions_ID + "." + TABLECOLUMN_IDENTIFIER);

			conditionsTable.put(conditionsIdentifier, flatrateConditions);
		}
	}
}
