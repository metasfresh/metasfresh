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
import org.compiere.model.I_EXP_Processor;
import org.compiere.model.I_EXP_ProcessorParameter;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class EXP_ProcessorParameter_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EXP_Processor_StepDefData expProcessorStepDefData;

	public EXP_ProcessorParameter_StepDef(@NonNull final EXP_Processor_StepDefData expProcessorStepDefData)
	{
		this.expProcessorStepDefData = expProcessorStepDefData;
	}

	@Given("update EXP_ProcessorParameter for the following EXP_Processor")
	public void update_EXP_ProcessorParameters(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			updateEXPProcessorParameters(row);
		}
	}

	private void updateEXPProcessorParameters(@NonNull final Map<String, String> row)
	{
		final String processorIdentifier = DataTableUtil.extractStringForColumnName(row, I_EXP_ProcessorParameter.COLUMNNAME_EXP_Processor_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Integer expProcessorId = expProcessorStepDefData.getOptional(processorIdentifier)
				.map(I_EXP_Processor::getEXP_Processor_ID)
				.orElseGet(() -> Integer.parseInt(processorIdentifier));

		final String value = DataTableUtil.extractStringForColumnName(row, I_EXP_ProcessorParameter.COLUMNNAME_Value);
		final String parameterValue = DataTableUtil.extractStringForColumnName(row, I_EXP_ProcessorParameter.COLUMNNAME_ParameterValue);

		final I_EXP_ProcessorParameter expProcessorParameter = queryBL.createQueryBuilder(I_EXP_ProcessorParameter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EXP_ProcessorParameter.COLUMNNAME_EXP_Processor_ID, expProcessorId)
				.addEqualsFilter(I_EXP_ProcessorParameter.COLUMNNAME_Value, value)
				.create()
				.firstOnlyNotNull(I_EXP_ProcessorParameter.class);

		expProcessorParameter.setParameterValue(parameterValue);

		saveRecord(expProcessorParameter);
	}
}
