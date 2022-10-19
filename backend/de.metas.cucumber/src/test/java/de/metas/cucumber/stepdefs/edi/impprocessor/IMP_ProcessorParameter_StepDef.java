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
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorParameter;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IMP_ProcessorParameter_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IMP_ProcessorParameter_StepDefData processorParameterTable;
	private final IMP_Processor_StepDefData impProcessorStepDefData;

	public IMP_ProcessorParameter_StepDef(
			@NonNull final IMP_ProcessorParameter_StepDefData processorParameterTable,
			@NonNull final IMP_Processor_StepDefData impProcessorStepDefData)
	{
		this.processorParameterTable = processorParameterTable;
		this.impProcessorStepDefData = impProcessorStepDefData;
	}

	@Given("^update IMP_ProcessorParameter for the following IMP_Processor:(.*)$")
	public void update_IMP_ProcessorParameters(@NonNull final String impProcessorIdentifier, @NonNull final DataTable dataTable)
	{
		final I_IMP_Processor impProcessor = impProcessorStepDefData.get(impProcessorIdentifier);
		assertThat(impProcessor).isNotNull();

		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			updateIMPProcessorParameters(row, impProcessor);
		}
	}

	private void updateIMPProcessorParameters(@NonNull final Map<String, String> row, @NonNull final I_IMP_Processor impProcessor)
	{
		final String value = DataTableUtil.extractStringForColumnName(row, I_IMP_ProcessorParameter.COLUMNNAME_Value);
		final String parameterValue = DataTableUtil.extractStringForColumnName(row, I_IMP_ProcessorParameter.COLUMNNAME_ParameterValue);

		final I_IMP_ProcessorParameter impProcessorParameter = queryBL.createQueryBuilder(I_IMP_ProcessorParameter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_IMP_ProcessorParameter.COLUMNNAME_IMP_Processor_ID, impProcessor.getIMP_Processor_ID())
				.addEqualsFilter(I_IMP_ProcessorParameter.COLUMNNAME_Value, value)
				.create()
				.firstOnlyNotNull(I_IMP_ProcessorParameter.class);

		impProcessorParameter.setParameterValue(parameterValue);

		saveRecord(impProcessorParameter);

		final String paramIdentifier = DataTableUtil.extractStringForColumnName(row, I_IMP_ProcessorParameter.COLUMNNAME_IMP_ProcessorParameter_ID + "." + TABLECOLUMN_IDENTIFIER);
		processorParameterTable.putOrReplace(paramIdentifier, impProcessorParameter);
	}
}
