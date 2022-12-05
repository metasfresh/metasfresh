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

import de.metas.cucumber.stepdefs.AD_ReplicationStrategy_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ReplicationStrategy;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class AD_Client_StepDef
{
	private final AD_Client_StepDefData adClientTable;
	private final AD_ReplicationStrategy_StepDefData adReplicationStrategyTable;

	public AD_Client_StepDef(
			@NonNull final AD_Client_StepDefData adClientTable,
			@NonNull final AD_ReplicationStrategy_StepDefData adReplicationStrategyTable)
	{
		this.adClientTable = adClientTable;
		this.adReplicationStrategyTable = adReplicationStrategyTable;
	}

	@Given("update AD_Client")
	public void update_AD_Client(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			updateClient(tableRow);
		}
	}

	private void updateClient(@NonNull final Map<String, String> row)
	{
		final String clientIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_Client.COLUMNNAME_AD_Client_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer clientId = adClientTable.getOptional(clientIdentifier)
				.map(I_AD_Client::getAD_Client_ID)
				.orElseGet(() -> Integer.parseInt(clientIdentifier));

		final I_AD_Client clientRecord = InterfaceWrapperHelper.load(clientId, I_AD_Client.class);

		final String replicationStrategyIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_Client.COLUMNNAME_AD_ReplicationStrategy_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final Integer replicationStrategyID = adReplicationStrategyTable.getOptional(replicationStrategyIdentifier)
				.map(I_AD_ReplicationStrategy::getAD_ReplicationStrategy_ID)
				.orElseGet(() -> Integer.parseInt(replicationStrategyIdentifier));

		clientRecord.setAD_ReplicationStrategy_ID(replicationStrategyID);

		saveRecord(clientRecord);
	}
}
