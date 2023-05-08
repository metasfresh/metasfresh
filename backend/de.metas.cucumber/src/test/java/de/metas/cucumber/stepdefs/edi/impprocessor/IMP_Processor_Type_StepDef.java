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

package de.metas.cucumber.stepdefs.edi.impprocessor;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_IMP_Processor_Type;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IMP_Processor_Type_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IMP_Processor_Type_StepDefData impProcessorTypeStepDefData;

	public IMP_Processor_Type_StepDef(@NonNull final IMP_Processor_Type_StepDefData impProcessorTypeStepDefData)
	{
		this.impProcessorTypeStepDefData = impProcessorTypeStepDefData;
	}

	@Given("load IMP_Processor_Type")
	public void load_IMP_Processor_Type(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			IMPProcessorType(row);
		}
	}

	private void IMPProcessorType(@NonNull final Map<String, String> row)
	{
		final String value = DataTableUtil.extractStringForColumnName(row, I_IMP_Processor_Type.COLUMNNAME_Value);

		final I_IMP_Processor_Type impProcessorTypeRecord = queryBL.createQueryBuilder(I_IMP_Processor_Type.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_IMP_Processor_Type.COLUMNNAME_Value, value)
				.create()
				.firstOnlyNotNull(I_IMP_Processor_Type.class);

		assertThat(impProcessorTypeRecord).isNotNull();

		final String impProcessorTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_IMP_Processor_Type.COLUMNNAME_IMP_Processor_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		impProcessorTypeStepDefData.putOrReplace(impProcessorTypeIdentifier, impProcessorTypeRecord);
	}
}
