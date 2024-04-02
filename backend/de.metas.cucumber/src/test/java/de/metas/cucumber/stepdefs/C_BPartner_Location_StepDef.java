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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Location_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID;

public class C_BPartner_Location_StepDef
{
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_Location_StepDefData locationTable;

	private final ILocationBL locationBL = Services.get(ILocationBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	public C_BPartner_Location_StepDef(
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final C_Location_StepDefData locationTable)
	{
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.locationTable = locationTable;
	}

	@Given("metasfresh contains C_BPartner_Locations:")
	public void createC_BPartner_Location(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createC_BPartner_Location);
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

	@Given("load C_BPartner_Location:")
	public void load_bpartner_location(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::load_bpartner_location);
	}

	private void createC_BPartner_Location(@NonNull final DataTableRow row)
	{
		final String bPartnerIdentifier = row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID).getAsString();
		final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);
		final String gln = row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_GLN).orElse(null);

		final I_C_BPartner_Location bPartnerLocationRecord = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_C_BPartner_Location.class)
						.addEqualsFilter(COLUMNNAME_C_BPartner_ID, bPartner.getC_BPartner_ID())
						.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_GLN, gln)
						.create()
						.firstOnlyOrNull(I_C_BPartner_Location.class),
				() -> newInstanceOutOfTrx(I_C_BPartner_Location.class));

		assertThat(bPartnerLocationRecord).isNotNull();

		bPartnerLocationRecord.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		bPartnerLocationRecord.setGLN(gln);

		final boolean isShipToDefault = row.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_IsShipToDefault).orElse(false);
		bPartnerLocationRecord.setIsShipToDefault(isShipToDefault);

		final boolean isShipTo = row.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_IsShipTo).orElse(isShipToDefault);
		bPartnerLocationRecord.setIsShipTo(isShipTo);

		final boolean isBillToDefault = row.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_IsBillToDefault).orElse(false);
		bPartnerLocationRecord.setIsBillToDefault(isBillToDefault);

		final boolean isBillTo = row.getAsOptionalBoolean(I_C_BPartner_Location.COLUMNNAME_IsBillTo).orElse(isBillToDefault);
		bPartnerLocationRecord.setIsBillTo(isBillTo);

		final StepDefDataIdentifier locationIdentifier = row.getAsOptionalIdentifier(I_C_BPartner_Location.COLUMNNAME_C_Location_ID).orElse(null);
		final I_C_Location locationRecord;
		if (locationIdentifier != null)
		{
			locationRecord = locationTable.get(locationIdentifier);
			assertThat(locationRecord).isNotNull();
		}
		else
		{
			locationRecord = InterfaceWrapperHelper.newInstance(I_C_Location.class);
			locationRecord.setC_Country_ID(StepDefConstants.COUNTRY_ID.getRepoId());
		}

		row.getAsOptionalString("CountryCode")
				.map(countryDAO::getCountryIdByCountryCode)
				.ifPresent(countryId -> locationRecord.setC_Country_ID(countryId.getRepoId()));

		saveRecord(locationRecord);
		bPartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		bPartnerLocationRecord.setAddress(locationBL.mkAddress(locationRecord));

		final String emailLocation = row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_EMail).orElse(null);
		if (Check.isNotBlank(emailLocation))
		{
			bPartnerLocationRecord.setEMail(emailLocation);
		}

		final String name = row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_Name).orElse(null);
		if (Check.isNotBlank(name))
		{
			bPartnerLocationRecord.setName(name);
		}

		final String bpLocationBPartnerName = row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_BPartnerName).orElse(null);
		if (Check.isNotBlank(bpLocationBPartnerName))
		{
			bPartnerLocationRecord.setBPartnerName(bpLocationBPartnerName);
		}

		final String phone = row.getAsOptionalString(I_C_BPartner_Location.COLUMNNAME_Phone).orElse(null);
		if (Check.isNotBlank(phone))
		{
			bPartnerLocationRecord.setPhone(phone);
		}

		final int bpartnerLocationRepoId = row.getAsOptionalInt(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID).orElse(-1);
		if (bpartnerLocationRepoId > 0)
		{
			bPartnerLocationRecord.setC_BPartner_Location_ID(bpartnerLocationRepoId);
		}

		saveRecord(bPartnerLocationRecord);

		row.getAsIdentifier(TABLECOLUMN_IDENTIFIER).putOrReplace(bPartnerLocationTable, bPartnerLocationRecord);
	}

	private void load_bpartner_location(@NonNull final DataTableRow tableRow)
	{
		final String bpartnerLocationIdentifier = tableRow.getAsIdentifier(COLUMNNAME_C_BPartner_Location_ID).getAsString();

		final int id = tableRow.getAsOptionalInt(COLUMNNAME_C_BPartner_Location_ID).orElse(-1);
		if (id > 0)
		{
			bPartnerLocationTable.putOrReplace(bpartnerLocationIdentifier, InterfaceWrapperHelper.load(id, I_C_BPartner_Location.class));
			return;
		}

		final String bpartnerIdentifier = tableRow.getAsIdentifier(COLUMNNAME_C_BPartner_ID).getAsString();
		final Integer bpartnerId = bPartnerTable.getOptional(bpartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerIdentifier));

		final int bpartnerLocationRepoId = tableRow.getAsInt(COLUMNNAME_C_BPartner_Location_ID);
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocationRepoId);

		final I_C_BPartner_Location bpartnerLocation = bpartnerDAO.getBPartnerLocationByIdInTrx(bPartnerLocationId);
		assertThat(bpartnerLocation).isNotNull();

		bPartnerLocationTable.put(bpartnerLocationIdentifier, bpartnerLocation);
	}
	
	private void updateCBPartnerLocation(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer bPartnerLocationID = bPartnerLocationTable.getOptional(bPartnerLocationIdentifier)
				.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
				.orElseGet(() -> Integer.parseInt(bPartnerLocationIdentifier));

		final I_C_BPartner_Location bPartnerLocation = InterfaceWrapperHelper.load(bPartnerLocationID, I_C_BPartner_Location.class);

		final String email = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_EMail);
		if (Check.isNotBlank(email))
		{
			bPartnerLocation.setEMail(DataTableUtil.nullToken2Null(email));
		}

		final String gln = DataTableUtil.extractNullableStringForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_GLN);

		if (Check.isNotBlank(gln))
		{
			bPartnerLocation.setGLN(DataTableUtil.nullToken2Null(gln));
		}

		saveRecord(bPartnerLocation);
		bPartnerLocationTable.putOrReplace(bPartnerLocationIdentifier, bPartnerLocation);
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

	@And("validate C_BPartner_Location:")
	public void validate_C_BPartner_Location(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String bpLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location bpLocation = bPartnerLocationTable.get(bpLocationIdentifier);

			final String bpIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner bPartnerRecord = bPartnerTable.get(bpIdentifier);
			softly.assertThat(bpLocation.getC_BPartner_ID()).as("C_BPartner_ID").isEqualTo(bPartnerRecord.getC_BPartner_ID());

			final String address = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BPartner_Location.COLUMNNAME_Address);
			if (Check.isNotBlank(address))
			{
				softly.assertThat(bpLocation.getAddress()).as("Address").isEqualTo(address);
			}

			final String name = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BPartner_Location.COLUMNNAME_Name);
			if (Check.isNotBlank(name))
			{
				softly.assertThat(bpLocation.getName()).as("Name").isEqualTo(name);
			}
		}

		softly.assertAll();
	}
}