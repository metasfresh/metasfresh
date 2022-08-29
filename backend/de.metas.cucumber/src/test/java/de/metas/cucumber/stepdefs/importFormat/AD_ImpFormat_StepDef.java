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

package de.metas.cucumber.stepdefs.importFormat;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_ImpFormat;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AD_ImpFormat_StepDef
{
	private final AD_ImpFormat_StepDefData impFormatTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AD_ImpFormat_StepDef(@NonNull final AD_ImpFormat_StepDefData impFormatTable)
	{
		this.impFormatTable = impFormatTable;
	}

	@And("load AD_ImpFormat:")
	public void load_AD_ImpFormat_by_name(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_AD_ImpFormat.COLUMNNAME_Name);

			final I_AD_ImpFormat impFormat = queryBL.createQueryBuilder(I_AD_ImpFormat.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_AD_ImpFormat.COLUMNNAME_Name, name)
					.create()
					.firstOnly(I_AD_ImpFormat.class);

			assertThat(impFormat).isNotNull();

			final String impFormatIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_ImpFormat.COLUMNNAME_AD_ImpFormat_ID + "." + TABLECOLUMN_IDENTIFIER);
			impFormatTable.putOrReplace(impFormatIdentifier, impFormat);
		}
	}
}
