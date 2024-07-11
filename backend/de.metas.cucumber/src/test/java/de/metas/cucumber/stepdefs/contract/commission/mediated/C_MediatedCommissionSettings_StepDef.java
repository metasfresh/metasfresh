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

package de.metas.cucumber.stepdefs.contract.commission.mediated;

import de.metas.contracts.commission.model.I_C_MediatedCommissionSettings;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettingsLine;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.contracts.commission.model.I_C_MediatedCommissionSettings.COLUMNNAME_C_MediatedCommissionSettings_ID;
import static de.metas.contracts.commission.model.I_C_MediatedCommissionSettings.COLUMNNAME_Commission_Product_ID;
import static de.metas.contracts.commission.model.I_C_MediatedCommissionSettings.COLUMNNAME_Name;
import static de.metas.contracts.commission.model.I_C_MediatedCommissionSettings.COLUMNNAME_PointsPrecision;
import static de.metas.contracts.commission.model.I_C_MediatedCommissionSettingsLine.COLUMNNAME_M_Product_Category_ID;
import static de.metas.contracts.commission.model.I_C_MediatedCommissionSettingsLine.COLUMNNAME_PercentOfBasePoints;
import static de.metas.contracts.commission.model.I_C_MediatedCommissionSettingsLine.COLUMNNAME_SeqNo;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_MediatedCommissionSettings_StepDef
{
	private final C_MediatedCommissionSettings_StepDefData mediatedCommissionSettingsTable;
	private final C_MediatedCommissionSettingsLine_StepDefData mediatedCommissionSettingsLineTable;
	private final M_Product_StepDefData productTable;

	public C_MediatedCommissionSettings_StepDef(
			@NonNull final C_MediatedCommissionSettings_StepDefData mediatedCommissionSettingsTable,
			@NonNull final C_MediatedCommissionSettingsLine_StepDefData mediatedCommissionSettingsLineTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.mediatedCommissionSettingsTable = mediatedCommissionSettingsTable;
		this.mediatedCommissionSettingsLineTable = mediatedCommissionSettingsLineTable;
		this.productTable = productTable;
	}

	@And("metasfresh contains C_MediatedCommissionSettings:")
	public void metasfresh_contains_I_C_MediatedCommissionSettings(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final I_C_MediatedCommissionSettings mediatedCommissionSettings = InterfaceWrapperHelper.newInstance(I_C_MediatedCommissionSettings.class);

			final String name = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Name);
			assertThat(name).as(COLUMNNAME_Name + " is mandatory").isNotBlank();
			mediatedCommissionSettings.setName(name);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Commission_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(productIdentifier).as(COLUMNNAME_Commission_Product_ID + "." + TABLECOLUMN_IDENTIFIER + " is mandatory").isNotBlank();
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).as("Missing M_Product record for identifier " + productIdentifier).isNotNull();
			mediatedCommissionSettings.setCommission_Product_ID(product.getM_Product_ID());

			final int pointsPrecision = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_PointsPrecision);
			mediatedCommissionSettings.setPointsPrecision(pointsPrecision);

			mediatedCommissionSettings.setIsActive(true);

			InterfaceWrapperHelper.saveRecord(mediatedCommissionSettings);

			final String mediatedCommissionSettingsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_MediatedCommissionSettings_ID + "." + TABLECOLUMN_IDENTIFIER);

			mediatedCommissionSettingsTable.put(mediatedCommissionSettingsIdentifier, mediatedCommissionSettings);
		}
	}

	@And("metasfresh contains C_MediatedCommissionSettingsLine:")
	public void metasfresh_contains_I_C_MediatedCommissionSettingsLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final I_C_MediatedCommissionSettingsLine mediatedSettingsLine = InterfaceWrapperHelper.newInstance(I_C_MediatedCommissionSettingsLine.class);

			final String mediatedSettingsIdentifier = tableRow.get(I_C_MediatedCommissionSettingsLine.COLUMNNAME_C_MediatedCommissionSettings_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(mediatedSettingsIdentifier).as(I_C_MediatedCommissionSettingsLine.COLUMNNAME_C_MediatedCommissionSettings_ID + "." + TABLECOLUMN_IDENTIFIER + " is mandatory").isNotBlank();
			final I_C_MediatedCommissionSettings mediatedSettings = mediatedCommissionSettingsTable.get(mediatedSettingsIdentifier);
			assertThat(mediatedSettings).as("Missing I_C_MediatedCommissionSettings record for identifier " + mediatedSettingsIdentifier).isNotNull();

			mediatedSettingsLine.setC_MediatedCommissionSettings_ID(mediatedSettings.getC_MediatedCommissionSettings_ID());

			final int productCategoryId = DataTableUtil.extractIntOrMinusOneForColumnName(tableRow, "OPT." + COLUMNNAME_M_Product_Category_ID);
			if (productCategoryId > 0)
			{
				mediatedSettingsLine.setM_Product_Category_ID(productCategoryId);
			}

			final int seqNo = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_SeqNo);
			mediatedSettingsLine.setSeqNo(seqNo);

			final BigDecimal percentOfBasePoints = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_PercentOfBasePoints);
			mediatedSettingsLine.setPercentOfBasePoints(percentOfBasePoints);

			mediatedSettingsLine.setIsActive(true);

			InterfaceWrapperHelper.saveRecord(mediatedSettingsLine);

			final String mediatedLineIdentifier = tableRow.get(I_C_MediatedCommissionSettingsLine.COLUMNNAME_C_MediatedCommissionSettingsLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			mediatedCommissionSettingsLineTable.put(mediatedLineIdentifier, mediatedSettingsLine);
		}
	}
}
