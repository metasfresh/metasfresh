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
import de.metas.cucumber.stepdefs.importFormat.AD_ImpFormat_StepDefData;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_ImpFormat_Row;
import org.compiere.model.I_AD_Org;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AD_Org_StepDef
{
	private final AD_Org_StepDefData orgTable;
	private final AD_ImpFormat_StepDefData impFormatTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AD_Org_StepDef(@NonNull final AD_Org_StepDefData orgTable, @NonNull final AD_ImpFormat_StepDefData impFormatTable)
	{
		this.orgTable = orgTable;
		this.impFormatTable = impFormatTable;
	}

	@And("load AD_Org from AD_ImpFormat config:")
	public void load_AD_Org_for_AD_ImpFormat_value(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String impFormatIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_ImpFormat.COLUMNNAME_AD_ImpFormat_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_AD_ImpFormat impFormat = impFormatTable.get(impFormatIdentifier);
			assertThat(impFormat).isNotNull();

			final String orgCodeFieldName = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_OrgCode + ".ImportRowFieldName");

			final String orgCodeForImportRowFieldName = queryBL.createQueryBuilder(I_AD_ImpFormat.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_AD_ImpFormat.COLUMNNAME_AD_ImpFormat_ID, impFormat.getAD_ImpFormat_ID())
					.andCollectChildren(I_AD_ImpFormat_Row.COLUMNNAME_AD_ImpFormat_ID, I_AD_ImpFormat_Row.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_AD_ImpFormat_Row.COLUMNNAME_Name, orgCodeFieldName)
					.create()
					.firstOptional(I_AD_ImpFormat_Row.class)
					.map(I_AD_ImpFormat_Row::getConstantValue)
					.orElse(null);

			assertThat(orgCodeForImportRowFieldName).isNotNull();

			final I_AD_Org org = queryBL.createQueryBuilder(I_AD_Org.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, orgCodeForImportRowFieldName)
					.create()
					.firstOnlyOrNull(I_AD_Org.class);

			assertThat(org).isNotNull();

			final String orgIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_Org.COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
			orgTable.putOrReplace(orgIdentifier, org);
		}
	}
}
