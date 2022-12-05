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

package de.metas.cucumber.stepdefs.interiminvoice.settings;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Calendar_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Interim_Invoice_Settings;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class C_Interim_Invoice_Settings_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Calendar_StepDefData calendarTable;
	private final M_Product_StepDefData productTable;
	private final C_Interim_Invoice_Settings_StepDefData interimSettingsTable;

	public C_Interim_Invoice_Settings_StepDef(
			@NonNull final C_Calendar_StepDefData calendarTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Interim_Invoice_Settings_StepDefData interimSettingsTable)
	{
		this.calendarTable = calendarTable;
		this.productTable = productTable;
		this.interimSettingsTable = interimSettingsTable;
	}

	@And("metasfresh contains C_Interim_Invoice_Settings")
	public void create_C_Interim_Invoice_Settings(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String harvestingCalendarIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Interim_Invoice_Settings.COLUMNNAME_C_Harvesting_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Calendar harvestingCalendar = calendarTable.get(harvestingCalendarIdentifier);

			final String productWithholdingIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Interim_Invoice_Settings.COLUMNNAME_M_Product_Witholding_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Product productWithholding = productTable.get(productWithholdingIdentifier);

			final I_C_Interim_Invoice_Settings interimSettings = CoalesceUtil.coalesceSuppliersNotNull(
					() -> queryBL.createQueryBuilder(I_C_Interim_Invoice_Settings.class)
							.addEqualsFilter(I_C_Interim_Invoice_Settings.COLUMNNAME_C_Harvesting_Calendar_ID, harvestingCalendar.getC_Calendar_ID())
							.addEqualsFilter(I_C_Interim_Invoice_Settings.COLUMNNAME_M_Product_Witholding_ID, productWithholding.getM_Product_ID())
							.create()
							.firstOnly(I_C_Interim_Invoice_Settings.class),
					() -> InterfaceWrapperHelper.newInstance(I_C_Interim_Invoice_Settings.class));

			interimSettings.setC_Harvesting_Calendar_ID(harvestingCalendar.getC_Calendar_ID());
			interimSettings.setM_Product_Witholding_ID(productWithholding.getM_Product_ID());

			InterfaceWrapperHelper.saveRecord(interimSettings);

			final String interimSettingsIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Interim_Invoice_Settings.COLUMNNAME_C_Interim_Invoice_Settings_ID + "." + TABLECOLUMN_IDENTIFIER);
			interimSettingsTable.put(interimSettingsIdentifier, interimSettings);
		}
	}
}
