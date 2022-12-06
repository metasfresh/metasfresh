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

package de.metas.cucumber.stepdefs.contract.commission.licensefee;

import de.metas.bpartner.BPGroupId;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettings;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettingsLine;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.contracts.commission.model.I_C_LicenseFeeSettingsLine.COLUMNNAME_C_LicenseFeeSettingsLine_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_LicenseFeeSettings_StepDef
{
	private final M_Product_StepDefData productTable;
	private final C_LicenseFeeSettings_StepDefData licenseFeeSettingsTable;
	private final C_LicenseFeeSettingsLine_StepDefData licenseFeeSettingsLineTable;

	public C_LicenseFeeSettings_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_LicenseFeeSettings_StepDefData licenseFeeSettingsTable,
			@NonNull final C_LicenseFeeSettingsLine_StepDefData licenseFeeSettingsLineTable)
	{
		this.productTable = productTable;
		this.licenseFeeSettingsTable = licenseFeeSettingsTable;
		this.licenseFeeSettingsLineTable = licenseFeeSettingsLineTable;
	}

	@And("metasfresh contains I_C_LicenseFeeSettings:")
	public void metasfresh_contains_I_C_LicenseFeeSettings(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final I_C_LicenseFeeSettings licenseFeeSettings = InterfaceWrapperHelper.newInstance(I_C_LicenseFeeSettings.class);

			final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_LicenseFeeSettings.COLUMNNAME_Name);
			assertThat(name).as(I_C_LicenseFeeSettings.COLUMNNAME_Name + " is mandatory").isNotBlank();
			licenseFeeSettings.setName(name);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_LicenseFeeSettings.COLUMNNAME_Commission_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			assertThat(productIdentifier).as(I_C_LicenseFeeSettings.COLUMNNAME_Commission_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER + " is mandatory").isNotBlank();
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).as("Missing M_Product record for identifier " + productIdentifier).isNotNull();
			licenseFeeSettings.setCommission_Product_ID(product.getM_Product_ID());

			final int pointsPrecision = DataTableUtil.extractIntForColumnName(tableRow, I_C_LicenseFeeSettings.COLUMNNAME_PointsPrecision);
			licenseFeeSettings.setPointsPrecision(pointsPrecision);

			licenseFeeSettings.setIsActive(true);

			InterfaceWrapperHelper.saveRecord(licenseFeeSettings);

			final String licenseFeeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_LicenseFeeSettings.COLUMNNAME_C_LicenseFeeSettings_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			licenseFeeSettingsTable.put(licenseFeeIdentifier, licenseFeeSettings);
		}
	}

	@And("metasfresh contains I_C_LicenseFeeSettingsLine:")
	public void metasfresh_contains_I_C_LicenseFeeSettingsLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final I_C_LicenseFeeSettingsLine licenseFeeSettingsLine = InterfaceWrapperHelper.newInstance(I_C_LicenseFeeSettingsLine.class);

			final String licenseFeeSettingsIdentifier = tableRow.get(I_C_LicenseFeeSettingsLine.COLUMNNAME_C_LicenseFeeSettings_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(licenseFeeSettingsIdentifier).as(I_C_LicenseFeeSettingsLine.COLUMNNAME_C_LicenseFeeSettings_ID + "." + TABLECOLUMN_IDENTIFIER + " is mandatory").isNotBlank();
			final I_C_LicenseFeeSettings licenseFeeSettings = licenseFeeSettingsTable.get(licenseFeeSettingsIdentifier);
			assertThat(licenseFeeSettings).as("Missing I_C_LicenseFeeSettings record for identifier " + licenseFeeSettingsIdentifier).isNotNull();

			licenseFeeSettingsLine.setC_LicenseFeeSettings_ID(licenseFeeSettings.getC_LicenseFeeSettings_ID());

			final int seqNo = DataTableUtil.extractIntForColumnName(tableRow, I_C_LicenseFeeSettingsLine.COLUMNNAME_SeqNo);
			licenseFeeSettingsLine.setSeqNo(seqNo);

			final BigDecimal percentOfBasePoints = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_LicenseFeeSettingsLine.COLUMNNAME_PercentOfBasePoints);
			licenseFeeSettingsLine.setPercentOfBasePoints(percentOfBasePoints);

			licenseFeeSettingsLine.setC_BP_Group_Match_ID(BPGroupId.STANDARD.getRepoId());
			licenseFeeSettingsLine.setIsActive(true);

			InterfaceWrapperHelper.saveRecord(licenseFeeSettingsLine);

			final String licenseFeeSettingLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow,  COLUMNNAME_C_LicenseFeeSettingsLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			licenseFeeSettingsLineTable.put(licenseFeeSettingLineIdentifier, licenseFeeSettingsLine);
		}
	}
}
