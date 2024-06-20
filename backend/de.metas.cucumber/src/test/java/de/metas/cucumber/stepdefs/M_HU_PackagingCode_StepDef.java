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

import de.metas.handlingunits.model.I_M_HU_PackagingCode;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class M_HU_PackagingCode_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_HU_PackagingCode_StepDefData huPackagingCodeTable;

	public M_HU_PackagingCode_StepDef(@NonNull final M_HU_PackagingCode_StepDefData huPackagingCodeTable)
	{
		this.huPackagingCodeTable = huPackagingCodeTable;
	}

	@And("load M_HU_PackagingCode:")
	public void load_M_HU_PackagingCode(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String packagingCode = DataTableUtil.extractStringForColumnName(row, I_M_HU_PackagingCode.COLUMNNAME_PackagingCode);
			final String unitType = DataTableUtil.extractStringForColumnName(row, I_M_HU_PackagingCode.COLUMNNAME_HU_UnitType);

			final I_M_HU_PackagingCode huPackagingCodeRecord = queryBL.createQueryBuilder(I_M_HU_PackagingCode.class)
					.addEqualsFilter(I_M_HU_PackagingCode.COLUMNNAME_PackagingCode, packagingCode)
					.addEqualsFilter(I_M_HU_PackagingCode.COLUMNNAME_HU_UnitType, unitType)
					.create()
					.firstOnlyNotNull(I_M_HU_PackagingCode.class);

			final String huPackagingCodeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_PackagingCode.COLUMNNAME_M_HU_PackagingCode_ID + "." + TABLECOLUMN_IDENTIFIER);
			huPackagingCodeTable.put(huPackagingCodeIdentifier, huPackagingCodeRecord);
		}
	}
}
