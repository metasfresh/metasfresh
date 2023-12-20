/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

import de.metas.contracts.commission.model.I_C_CommissionSettingsLine;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.cucumber.stepdefs.DataTableUtil;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.contracts.commission.model.I_C_CommissionSettingsLine.COLUMNNAME_C_CommissionSettingsLine_ID;
import static de.metas.contracts.commission.model.I_C_CommissionSettingsLine.COLUMNNAME_PercentOfBasePoints;
import static de.metas.contracts.commission.model.I_C_CommissionSettingsLine.COLUMNNAME_SeqNo;
import static de.metas.contracts.commission.model.I_C_Flatrate_Conditions.COLUMNNAME_C_HierarchyCommissionSettings_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_CommissionSettingsLine_StepDef
{
	private final C_HierarchyCommissionSettings_StepDefData commissionSettingsTable;
	private final C_CommissionSettingsLine_StepDefData commissionSettingsLineTable;
	public C_CommissionSettingsLine_StepDef(
			@NonNull final C_HierarchyCommissionSettings_StepDefData commissionSettingsTable,
			@NonNull final C_CommissionSettingsLine_StepDefData commissionSettingsLineTable)
	{
		this.commissionSettingsTable = commissionSettingsTable;
		this.commissionSettingsLineTable = commissionSettingsLineTable;
	}

	@Given("metasfresh contains C_CommissionSettingsLines:")
	public void metasfresh_contains_C_CommissionSettingsLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final I_C_CommissionSettingsLine commissionSettingsLine = newInstance(I_C_CommissionSettingsLine.class);

			final String commissionSettingsIdentifier = tableRow.get(COLUMNNAME_C_HierarchyCommissionSettings_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(commissionSettingsIdentifier).as(COLUMNNAME_C_HierarchyCommissionSettings_ID + "." + TABLECOLUMN_IDENTIFIER + " is mandatory").isNotBlank();
			final I_C_HierarchyCommissionSettings commissionSettings = commissionSettingsTable.get(commissionSettingsIdentifier);
			assertThat(commissionSettings).as("Missing C_HierarchyCommissionSettings record for identifier " + commissionSettingsIdentifier).isNotNull();

			commissionSettingsLine.setC_HierarchyCommissionSettings_ID(commissionSettings.getC_HierarchyCommissionSettings_ID());

			final int seqNo = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_SeqNo);
			commissionSettingsLine.setSeqNo(seqNo);

			final BigDecimal percent = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_PercentOfBasePoints);
			commissionSettingsLine.setPercentOfBasePoints(percent);

			commissionSettingsLine.setIsActive(true);

			saveRecord(commissionSettingsLine);

			final String hierarchySettingsLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_CommissionSettingsLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			commissionSettingsLineTable.put(hierarchySettingsLineIdentifier, commissionSettingsLine);
		}
	}
}
