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

import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.assertj.core.api.Assertions.*;
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

	@And("metasfresh contains C_Location:")
	public void add_C_Location(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_C_Location location = newInstanceOutOfTrx(I_C_Location.class);
			assertThat(location).isNotNull();

			final String countryCode = DataTableUtil.extractStringForColumnName(row, I_C_Country.COLUMNNAME_CountryCode);
			final I_C_Country country = queryBL.createQueryBuilder(I_C_Country.class)
					.addEqualsFilter(I_C_Country.COLUMNNAME_CountryCode, countryCode)
					.create()
					.firstOnlyNotNull(I_C_Country.class);

			location.setC_Country_ID(country.getC_Country_ID());

			final String address1 = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Location.COLUMNNAME_Address1);
			final String postalCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Location.COLUMNNAME_Postal);
			final String city = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Location.COLUMNNAME_City);
			final String regionName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Location.COLUMNNAME_RegionName);
			final boolean isActive = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_Location.COLUMNNAME_IsActive, true);

			location.setAddress1(address1);
			location.setPostal(postalCode);
			location.setCity(city);
			location.setRegionName(regionName);
			location.setIsActive(isActive);

			final String orgIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Location.COLUMNNAME_AD_Org_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orgIdentifier))
			{
				final I_AD_Org org = orgTable.get(orgIdentifier);
				location.setAD_Org_ID(org.getAD_Org_ID());
			}

			InterfaceWrapperHelper.saveRecord(location);

			final String locationIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			locationTable.put(locationIdentifier, location);
		}
	}
}
