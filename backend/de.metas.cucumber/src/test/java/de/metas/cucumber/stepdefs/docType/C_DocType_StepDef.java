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

package de.metas.cucumber.stepdefs.docType;

import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_DocType;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_DocType_StepDef
{
	private final C_DocType_StepDefData docTypeTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public C_DocType_StepDef(final C_DocType_StepDefData docTypeTable)
	{
		this.docTypeTable = docTypeTable;
	}

	@Then("load C_DocType:")
	public void load_C_DocType(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String docBaseType = DataTableUtil.extractStringForColumnName(row, I_C_DocType.COLUMNNAME_DocBaseType);
			final String docSubType = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_C_DocType.COLUMNNAME_DocSubType);
			final Boolean isDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_DocType.COLUMNNAME_IsDefault, null);

			final IQueryBuilder<I_C_DocType> queryBuilder = queryBL.createQueryBuilder(I_C_DocType.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, docBaseType);

			if (isDefault != null)
			{
				queryBuilder.addEqualsFilter(I_C_DocType.COLUMNNAME_IsDefault, isDefault);
			}

			if (Check.isNotBlank(docSubType))
			{
				queryBuilder.addEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, DataTableUtil.nullToken2Null(docSubType));
			}

			final I_C_DocType docType = queryBuilder.create().firstOnlyOrNull(I_C_DocType.class);

			assertThat(docType).isNotNull();

			final String docTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DocType.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
			docTypeTable.putOrReplace(docTypeIdentifier, docType);
		}
	}
}
