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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_Org;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AD_Org_StepDef
{
	private final AD_Org_StepDefData orgTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AD_Org_StepDef(final AD_Org_StepDefData orgTable)
	{
		this.orgTable = orgTable;
	}

	@And("load AD_Org:")
	public void load_AD_Org(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String orgCode = DataTableUtil.extractStringForColumnName(row, I_AD_Org.COLUMNNAME_Value);

			final I_AD_Org orgRecord = queryBL.createQueryBuilder(I_AD_Org.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, orgCode)
					.create()
					.firstOnlyOrNull(I_AD_Org.class);

			assertThat(orgRecord).isNotNull();

			final String orgIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_Org.COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
			orgTable.putOrReplace(orgIdentifier, orgRecord);
		}
	}
}
