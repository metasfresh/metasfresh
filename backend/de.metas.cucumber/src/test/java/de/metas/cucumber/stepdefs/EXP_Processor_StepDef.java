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

import de.metas.CommandLineParser;
import de.metas.ServerBoot;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_EXP_Processor;
import org.compiere.model.I_EXP_Processor_Type;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class EXP_Processor_StepDef
{

	private final EXP_Processor_Type_StepDefData expProcessorTypeStepDefData;
	private final EXP_Processor_StepDefData expProcessorStepDefData;

	public EXP_Processor_StepDef(
			@NonNull final EXP_Processor_Type_StepDefData expProcessorTypeStepDefData,
			@NonNull final EXP_Processor_StepDefData expProcessorStepDefData)
	{
		this.expProcessorTypeStepDefData = expProcessorTypeStepDefData;
		this.expProcessorStepDefData = expProcessorStepDefData;
	}

	@Given("metasfresh contains Exp_Processor")
	public void create_EXP_Processor(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			createExpProcessor(row);
		}
	}

	private void createExpProcessor(@NonNull final Map<String, String> row)
	{
		final String expProcessorIdentifier = DataTableUtil.extractStringForColumnName(row, I_EXP_Processor.COLUMNNAME_EXP_Processor_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String expProcessorTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_EXP_Processor_Type.COLUMNNAME_EXP_Processor_Type_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer expProcessorTypeId = expProcessorTypeStepDefData.getOptional(expProcessorTypeIdentifier)
				.map(I_EXP_Processor_Type::getEXP_Processor_Type_ID)
				.orElseGet(() -> Integer.parseInt(expProcessorTypeIdentifier));

		final String name = DataTableUtil.extractStringForColumnName(row, I_EXP_Processor.COLUMNNAME_Name);

		final I_EXP_Processor expProcessor = InterfaceWrapperHelper.newInstance(I_EXP_Processor.class);

		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineParser.CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();
		assertThat(commandLineOptions.getRabbitPort()).isNotNull();

		expProcessor.setName(name);
		expProcessor.setEXP_Processor_Type_ID(expProcessorTypeId);
		expProcessor.setPasswordInfo(commandLineOptions.getRabbitPassword());
		expProcessor.setAccount(commandLineOptions.getRabbitUser());
		expProcessor.setPort(commandLineOptions.getRabbitPort());
		expProcessor.setHost(commandLineOptions.getRabbitHost());

		saveRecord(expProcessor);

		expProcessorStepDefData.put(expProcessorIdentifier, expProcessor);
	}
}
