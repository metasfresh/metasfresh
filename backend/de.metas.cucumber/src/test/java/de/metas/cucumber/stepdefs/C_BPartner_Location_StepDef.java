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

package de.metas.cucumber.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import java.util.List;
import java.util.Map;

public class C_BPartner_Location_StepDef
{
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;

	public C_BPartner_Location_StepDef(
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable)
	{
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
	}

	@Given("metasfresh contains C_BPartner_Locations:")
	public void createC_BPartner_Location(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createC_BPartner_Location(tableRow);
		}
	}

	private void createC_BPartner_Location(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);
		final String gln = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_GLN);

		final I_C_Location locationRecord = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		locationRecord.setC_Country_ID(StepDefConstants.COUNTRY_ID.getRepoId());
		InterfaceWrapperHelper.saveRecord(locationRecord);

		final I_C_BPartner_Location bPartnerLocationRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		bPartnerLocationRecord.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		bPartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		bPartnerLocationRecord.setGLN(gln);
		bPartnerLocationRecord.setIsBillToDefault(true);
		bPartnerLocationRecord.setIsBillTo(true);
		bPartnerLocationRecord.setIsShipTo(true);
		bPartnerLocationRecord.setIsShipToDefault(true);
		InterfaceWrapperHelper.saveRecord(bPartnerLocationRecord);

		bPartnerLocationTable.put(DataTableUtil.extractRecordIdentifier(tableRow, "C_BPartner_Location"), bPartnerLocationRecord);
	}
}