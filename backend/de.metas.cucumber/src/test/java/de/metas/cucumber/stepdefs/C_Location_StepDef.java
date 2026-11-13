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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.compiere.model.I_C_Location.COLUMNNAME_C_Location_ID;

public class C_Location_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Location_StepDefData locationTable;
	private final AD_Org_StepDefData orgTable;

	public C_Location_StepDef(
			@NonNull final C_Location_StepDefData locationTable,
			@NonNull final AD_Org_StepDefData orgTable)
	{
		this.locationTable = locationTable;
		this.orgTable = orgTable;
	}

	/**
	 * Creates and saves one {@link I_C_Location} per row and registers it under the given identifier.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Location_ID</b> - (required, identifier; also written <code>C_Location_ID.Identifier</code>) identifier under which the location is registered<br>
	 *   <b>CountryCode</b> - (required) <code>C_Country.CountryCode</code>, e.g. DE<br>
	 *   <b>Address1</b> - (optional; also written <code>OPT.Address1</code>)<br>
	 *   <b>Postal</b> - (optional)<br>
	 *   <b>City</b> - (optional)<br>
	 *   <b>RegionName</b> - (optional)<br>
	 *   <b>IsActive</b> - (optional) default Y<br>
	 *   <b>AD_Org_ID</b> - (optional, identifier-ref; also written <code>OPT.AD_Org_ID.Identifier</code>) an already registered AD_Org; default is the context org<br>
	 *   The legacy spellings <code>OPT.&lt;column&gt;</code> and <code>&lt;column&gt;.Identifier</code> are still accepted.
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Location:
	 *   | C_Location_ID | CountryCode | Address1    | Postal | City |
	 *   | location1     | DE          | Hauptstr. 1 | 53111  | Bonn |
	 * </pre>
	 */
	@And("metasfresh contains C_Location:")
	public void add_C_Location(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createLocation);
	}

	private void createLocation(@NonNull final DataTableRow row)
	{
		final I_C_Location location = newInstanceOutOfTrx(I_C_Location.class);

		final String countryCode = row.getAsString(I_C_Country.COLUMNNAME_CountryCode);
		final I_C_Country country = queryBL.createQueryBuilder(I_C_Country.class)
				.addEqualsFilter(I_C_Country.COLUMNNAME_CountryCode, countryCode)
				.create()
				.firstOnlyNotNull(I_C_Country.class);
		location.setC_Country_ID(country.getC_Country_ID());

		location.setAddress1(row.getAsOptionalString(I_C_Location.COLUMNNAME_Address1).map(DataTableUtil::nullToken2Null).orElse(null));
		location.setPostal(row.getAsOptionalString(I_C_Location.COLUMNNAME_Postal).map(DataTableUtil::nullToken2Null).orElse(null));
		location.setCity(row.getAsOptionalString(I_C_Location.COLUMNNAME_City).map(DataTableUtil::nullToken2Null).orElse(null));
		location.setRegionName(row.getAsOptionalString(I_C_Location.COLUMNNAME_RegionName).map(DataTableUtil::nullToken2Null).orElse(null));
		location.setIsActive(row.getAsOptionalBoolean(I_C_Location.COLUMNNAME_IsActive).orElseTrue());

		row.getAsOptionalIdentifier(I_C_Location.COLUMNNAME_AD_Org_ID)
				.map(orgTable::get)
				.ifPresent(org -> location.setAD_Org_ID(org.getAD_Org_ID()));

		InterfaceWrapperHelper.saveRecord(location);

		locationTable.put(row.getAsIdentifier(COLUMNNAME_C_Location_ID), location);
	}
}
