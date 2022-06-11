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

package de.metas.cucumber.stepdefs.attribute;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_AttributeSet;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_AttributeSet.COLUMNNAME_M_AttributeSet_ID;

public class M_AttributeSet_StepDef
{
	private final StepDefData<I_M_AttributeSet> attributeSetTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_AttributeSet_StepDef(@NonNull final StepDefData<I_M_AttributeSet> attributeSetTable)
	{
		this.attributeSetTable = attributeSetTable;
	}

	@And("load M_AttributeSet:")
	public void load_M_AttributeSet(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String name = DataTableUtil.extractStringForColumnName(row, I_M_AttributeSet.COLUMNNAME_Name);

			final I_M_AttributeSet attributeSetRecord = queryBL.createQueryBuilder(I_M_AttributeSet.class)
					.addEqualsFilter(I_M_AttributeSet.COLUMNNAME_Name, name)
					.addOnlyActiveRecordsFilter()
					.create()
					.firstOnly(I_M_AttributeSet.class);

			assertThat(attributeSetRecord).isNotNull();

			final String attributeSetIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_AttributeSet_ID + "." + TABLECOLUMN_IDENTIFIER);

			attributeSetTable.put(attributeSetIdentifier, attributeSetRecord);
		}
	}
}
