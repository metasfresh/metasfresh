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

import de.metas.common.util.CoalesceUtil;
import de.metas.location.ILocationBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
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
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.*;

public class C_BPartner_Location_StepDef
{
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final C_Location_StepDefData locationTable;

	private final ILocationBL locationBL = Services.get(ILocationBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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

	@And("update C_BPartner_Location:")
	public void update_C_BPartner_Location(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String bpLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bpLocationIdentifier);

			assertThat(bPartnerLocation).isNotNull();

			final String email = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_BPartner_Location.COLUMNNAME_EMail);
			if (Check.isNotBlank(email))
			{
				bPartnerLocation.setEMail(email);
			}

			InterfaceWrapperHelper.saveRecord(bPartnerLocation);
			bPartnerLocationTable.putOrReplace(bpLocationIdentifier, bPartnerLocation);
		}

	}

	private void createC_BPartner_Location(@NonNull final Map<String, String> tableRow)
	{
		final String bPartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);
		final String gln = DataTableUtil.extractStringOrNullForColumnName(tableRow, I_C_BPartner_Location.COLUMNNAME_GLN);

		final I_C_BPartner_Location bPartnerLocationRecord = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_C_BPartner_Location.class)
						.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bPartner.getC_BPartner_ID())
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
			InterfaceWrapperHelper.saveRecord(locationRecord);

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

		InterfaceWrapperHelper.saveRecord(bPartnerLocationRecord);

		final String bpLocationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, TABLECOLUMN_IDENTIFIER);
		bPartnerLocationTable.put(bpLocationIdentifier, bPartnerLocationRecord);
	}
}