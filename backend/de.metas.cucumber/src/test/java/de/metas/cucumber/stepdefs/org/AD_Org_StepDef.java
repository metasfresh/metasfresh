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

package de.metas.cucumber.stepdefs.org;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.organization.IOrgDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_Org;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AD_Org_StepDef
{
	private final AD_Org_StepDefData orgTable;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AD_Org_StepDef(@NonNull final AD_Org_StepDefData orgTable)
	{
		this.orgTable = orgTable;
	}

	@Given("metasfresh contains AD_Org:")
	public void metasfresh_contains_ad_orgs(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String name = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Org.COLUMNNAME_Name);
			final String value = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Org.COLUMNNAME_Value);

			final I_AD_Org orgRecord = CoalesceUtil.coalesce(queryBL.createQueryBuilder(I_AD_Org.class)
							.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, value)
							.create()
							.firstOnly(I_AD_Org.class),
					InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_Org.class));

			Assertions.assertThat(orgRecord).isNotNull();

			orgRecord.setName(name);
			orgRecord.setValue(value);

			orgDAO.save(orgRecord);

			final String orgIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Org.COLUMNNAME_AD_Org_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			orgTable.putOrReplace(orgIdentifier, orgRecord);
		}
	}

	@And("load AD_Org:")
	public void load_AD_Org(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final String orgCode = row.getAsString("Value");

			final I_AD_Org orgRecord = queryBL.createQueryBuilder(I_AD_Org.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, orgCode)
					.create()
					.firstOnlyOrNull(I_AD_Org.class);

			assertThat(orgRecord).as("AD_Org for identifier=%S", orgCode).isNotNull();

			final StepDefDataIdentifier orgIdentifier = row.getAsIdentifier("AD_Org_ID");

			try (final IAutoCloseable ignored = orgTable.temporaryDisableDuplicatesChecking())
			{
				orgTable.putOrReplace(orgIdentifier, orgRecord);
			}
		});
	}
}
