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

package de.metas.cucumber.stepdefs.contract.commission.hierarchy;

import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
<<<<<<< HEAD
import de.metas.util.Services;
=======
import de.metas.cucumber.stepdefs.StepDefConstants;
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;

import static de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.COLUMNNAME_C_HierarchyCommissionSettings_ID;
import static de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.COLUMNNAME_Commission_Product_ID;
import static de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.COLUMNNAME_IsCreateShareForOwnRevenue;
import static de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.COLUMNNAME_IsSubtractLowerLevelCommissionFromBase;
import static de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings.COLUMNNAME_Name;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_HierarchyCommissionSettings_StepDef
{
<<<<<<< HEAD
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

=======
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
	private final M_Product_StepDefData productTable;
	private final C_HierarchyCommissionSettings_StepDefData hierarchyCommissionSettingsTable;

	public C_HierarchyCommissionSettings_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_HierarchyCommissionSettings_StepDefData hierarchyCommissionSettingsTable)
	{
		this.productTable = productTable;
		this.hierarchyCommissionSettingsTable = hierarchyCommissionSettingsTable;
	}

	@Given("metasfresh contains C_HierarchyCommissionSettings:")
	public void metasfresh_contains_C_HierarchyCommissionSettings(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final I_C_HierarchyCommissionSettings settings = InterfaceWrapperHelper.newInstance(I_C_HierarchyCommissionSettings.class);

			final String name = tableRow.get(COLUMNNAME_Name);
			assertThat(name).as(COLUMNNAME_Name + " is mandatory").isNotBlank();
			settings.setName(name);

			final String productIdentifier = tableRow.get(COLUMNNAME_Commission_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(productIdentifier).as(COLUMNNAME_Commission_Product_ID + "." + TABLECOLUMN_IDENTIFIER + " is mandatory").isNotBlank();
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).as("Missing M_Product record for identifier " + productIdentifier).isNotNull();
			settings.setCommission_Product_ID(product.getM_Product_ID());

			final boolean subtract = DataTableUtil.extractBooleanForColumnNameOr(tableRow, COLUMNNAME_IsSubtractLowerLevelCommissionFromBase, false);
			settings.setIsSubtractLowerLevelCommissionFromBase(subtract);

			final boolean ownRevenue = DataTableUtil.extractBooleanForColumnNameOr(tableRow, COLUMNNAME_IsCreateShareForOwnRevenue, false);
			settings.setIsCreateShareForOwnRevenue(ownRevenue);

			settings.setIsActive(true);

			InterfaceWrapperHelper.saveRecord(settings);

			final I_C_HierarchyCommissionSettings savedSettings = queryBL.createQueryBuilder(I_C_HierarchyCommissionSettings.class)
					.addEqualsFilter(COLUMNNAME_C_HierarchyCommissionSettings_ID, settings.getC_HierarchyCommissionSettings_ID())
					.create()
					.firstOnlyNotNull(I_C_HierarchyCommissionSettings.class);

			final String hierarchyIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_HierarchyCommissionSettings_ID + "." + TABLECOLUMN_IDENTIFIER);
			hierarchyCommissionSettingsTable.put(hierarchyIdentifier, savedSettings);
		}
	}
}