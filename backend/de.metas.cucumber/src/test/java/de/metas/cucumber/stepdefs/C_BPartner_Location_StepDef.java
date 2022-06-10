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

import de.metas.util.Check;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

	@Given("update C_BPartner_Location:")
	public void update_C_BPartner_Location(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			updateCBPartnerLocation(tableRow);
		}
	}

	@Given("update C_Location of the following C_BPartner_Location")
	public void update_C_Location_of_the_C_BPartner_Location(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			updateLocationOfTheBPartnerLocation(tableRow);
		}
	}

	private void createC_BPartner_Location(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);
		final String gln = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_GLN);

		final I_C_Location locationRecord = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		locationRecord.setC_Country_ID(StepDefConstants.COUNTRY_ID.getRepoId());
		saveRecord(locationRecord);

		final I_C_BPartner_Location bPartnerLocationRecord = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		bPartnerLocationRecord.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		bPartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		bPartnerLocationRecord.setGLN(gln);
		bPartnerLocationRecord.setIsBillToDefault(true);
		bPartnerLocationRecord.setIsShipTo(true);
		saveRecord(bPartnerLocationRecord);

		bPartnerLocationTable.put(DataTableUtil.extractRecordIdentifier(tableRow, "C_BPartner_Location"), bPartnerLocationRecord);
	}

	private void updateCBPartnerLocation(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer bPartnerLocationID = bPartnerLocationTable.getOptional(bPartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerLocationIdentifier));

		final I_C_BPartner_Location bPartnerLocation = InterfaceWrapperHelper.load(bPartnerLocationID, I_C_BPartner_Location.class);

		final String gln = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_GLN);

		if (Check.isNotBlank(gln))
		{
			bPartnerLocation.setGLN(gln);
		}

		saveRecord(bPartnerLocation);
	}

	private void updateLocationOfTheBPartnerLocation(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer bPartnerLocationID = bPartnerLocationTable.getOptional(bPartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerLocationIdentifier));

		final I_C_BPartner_Location bPartnerLocationRecord = InterfaceWrapperHelper.load(bPartnerLocationID, I_C_BPartner_Location.class);

		final I_C_Location locationRecord = InterfaceWrapperHelper.load(bPartnerLocationRecord.getC_Location_ID(), I_C_Location.class);

		final String address1 = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Location.COLUMNNAME_Address1);

		if (Check.isNotBlank(address1))
		{
			locationRecord.setAddress1(address1);
		}

		final String address2 = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Location.COLUMNNAME_Address2);

		if (Check.isNotBlank(address2))
		{
			locationRecord.setAddress2(address2);
		}

		final String address3 = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_Location.COLUMNNAME_Address3);

		if (Check.isNotBlank(address3))
		{
			locationRecord.setAddress3(address3);
		}

		saveRecord(locationRecord);
	}
}