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

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_ReplicationStrategy;
import org.compiere.model.I_EXP_Processor;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class AD_ReplicationStrategy_StepDef
{
	private final EXP_Processor_StepDefData expProcessorTable;
	private final AD_ReplicationStrategy_StepDefData adReplicationStrategyTable;

	public AD_ReplicationStrategy_StepDef(
			@NonNull final EXP_Processor_StepDefData expProcessorTable,
			@NonNull final AD_ReplicationStrategy_StepDefData adReplicationStrategyTable)
	{
		this.expProcessorTable = expProcessorTable;
		this.adReplicationStrategyTable = adReplicationStrategyTable;
	}

	@Given("metasfresh contains AD_Replication_Strategy")
	public void create_AD_Replication_Strategy(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			createReplicationStrategy(row);
		}
	}

	private void createReplicationStrategy(@NonNull final Map<String, String> row)
	{
		final String replicationStrategyIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_ReplicationStrategy.COLUMNNAME_AD_ReplicationStrategy_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String entityType = DataTableUtil.extractStringForColumnName(row, I_AD_ReplicationStrategy.COLUMNNAME_EntityType);
		final String name = DataTableUtil.extractStringForColumnName(row, I_AD_ReplicationStrategy.COLUMNNAME_Name);

		final String processorIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_ReplicationStrategy.COLUMNNAME_EXP_Processor_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Integer expProcessorId = expProcessorTable.getOptional(processorIdentifier)
				.map(I_EXP_Processor::getEXP_Processor_ID)
				.orElseGet(() -> Integer.parseInt(processorIdentifier));

		final I_AD_ReplicationStrategy replicationStrategyRecord = InterfaceWrapperHelper.newInstance(I_AD_ReplicationStrategy.class);

		replicationStrategyRecord.setEntityType(entityType);
		replicationStrategyRecord.setName(name);
		replicationStrategyRecord.setEXP_Processor_ID(expProcessorId);

		saveRecord(replicationStrategyRecord);

		adReplicationStrategyTable.put(replicationStrategyIdentifier, replicationStrategyRecord);
	}
}
