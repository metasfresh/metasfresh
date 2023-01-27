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
import de.metas.location.ILocationBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
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

	@Given("load C_BPartner_Location:")
	public void load_bpartner_location(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			load_bpartner_location(tableRow);
		}
	}

	private void createC_BPartner_Location(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);
		final String gln = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_GLN);

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

		final boolean isShipToDefault = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_IsShipToDefault, false);
		bPartnerLocationRecord.setIsShipToDefault(isShipToDefault);

		final boolean isShipTo = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_IsShipTo, isShipToDefault);
		bPartnerLocationRecord.setIsShipTo(isShipTo);

		final boolean isBillToDefault = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_IsBillToDefault, false);
		bPartnerLocationRecord.setIsBillToDefault(isBillToDefault);

		final boolean isBillTo = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_IsBillTo, isBillToDefault);
		bPartnerLocationRecord.setIsBillTo(isBillTo);

		final String locationIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_C_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(locationIdentifier))
		{
			final I_C_Location location = locationTable.get(locationIdentifier);
			assertThat(location).isNotNull();

			bPartnerLocationRecord.setC_Location_ID(location.getC_Location_ID());
			bPartnerLocationRecord.setAddress(locationBL.mkAddress(location));
		}
		else
		{
			final I_C_Location locationRecord = InterfaceWrapperHelper.newInstance(I_C_Location.class);
			locationRecord.setC_Country_ID(StepDefConstants.COUNTRY_ID.getRepoId());
			saveRecord(locationRecord);

			bPartnerLocationRecord.setC_Location_ID(locationRecord.getC_Location_ID());
		}

		final String emailLocation = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_EMail);
		if (Check.isNotBlank(emailLocation))
		{
			bPartnerLocationRecord.setEMail(emailLocation);
		}

		final String name = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_Name);
		if (Check.isNotBlank(name))
		{
			bPartnerLocationRecord.setName(name);
		}

		final String bpLocationBPartnerName = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_BPartnerName);
		if (Check.isNotBlank(bpLocationBPartnerName))
		{
			bPartnerLocationRecord.setBPartnerName(bpLocationBPartnerName);
		}

		final String phone = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Location.COLUMNNAME_Phone);
		if (Check.isNotBlank(phone))
		{
			bPartnerLocationRecord.setPhone(phone);
		}

		saveRecord(bPartnerLocationRecord);

		final String bpLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, TABLECOLUMN_IDENTIFIER);
		bPartnerLocationTable.put(bpLocationIdentifier, bPartnerLocationRecord);
	}

	private void load_bpartner_location(@NonNull final Map<String, String> tableRow)
	{
		final String bpartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);

		final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_C_BPartner_Location_ID);

		if (id != null)
		{
			bPartnerLocationTable.putOrReplace(bpartnerLocationIdentifier, InterfaceWrapperHelper.load(id, I_C_BPartner_Location.class));
			return;
		}

		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer bpartnerId = bPartnerTable.getOptional(bpartnerIdentifier)
				.map(I_C_BPartner::getC_BPartner_ID)
				.orElseGet(() -> Integer.parseInt(bpartnerIdentifier));

		final int bpartnerLocationId = DataTableUtil.extractIntForColumnName(tableRow, COLUMNNAME_C_BPartner_Location_ID);
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocationId);

		final I_C_BPartner_Location bPartnerLocation = bpartnerDAO.getBPartnerLocationByIdInTrx(bPartnerLocationId);
		assertThat(bPartnerLocation).isNotNull();

		bPartnerLocationTable.put(bpartnerLocationIdentifier, bPartnerLocation);
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
}