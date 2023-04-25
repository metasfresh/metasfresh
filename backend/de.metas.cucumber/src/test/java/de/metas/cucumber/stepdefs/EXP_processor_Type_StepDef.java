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

import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_EXP_Processor_Type;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class EXP_processor_Type_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EXP_Processor_Type_StepDefData expProcessorTypeStepDefData;

	public EXP_processor_Type_StepDef(@NonNull final EXP_Processor_Type_StepDefData expProcessorTypeStepDefData)
	{
		this.expProcessorTypeStepDefData = expProcessorTypeStepDefData;
	}

	@Given("load EXP_Processor_Type")
	public void load_EXP_Processor_Type(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			loadEXPProcessorType(row);
		}
	}

	private void loadEXPProcessorType(@NonNull final Map<String, String> row)
	{
		final String expProcessorTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_EXP_Processor_Type.COLUMNNAME_EXP_Processor_Type_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final String value = DataTableUtil.extractStringForColumnName(row, I_EXP_Processor_Type.COLUMNNAME_Value);

		final I_EXP_Processor_Type expProcessorTypeRecord = getTypeByValue(value);

		assertThat(expProcessorTypeRecord).isNotNull();

		expProcessorTypeStepDefData.put(expProcessorTypeIdentifier, expProcessorTypeRecord);
	}

	private I_EXP_Processor_Type getTypeByValue(@NonNull final String value)
	{
		return queryBL.createQueryBuilder(I_EXP_Processor_Type.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EXP_Processor_Type.COLUMNNAME_Value, value)
				.create()
				.firstNotNull(I_EXP_Processor_Type.class);
	}
}
