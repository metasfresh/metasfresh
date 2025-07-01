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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ReplicationStrategy;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class AD_Client_StepDef
{
	private static final String CUCUMBER_UNIX_ARCHIVE_PATH = "/reports/metasfreshArchives";

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
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_AD_Client.COLUMNNAME_AD_Client_ID)
				.forEach(this::updateClient);
	}

	private void updateClient(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier clientIdentifier = row.getAsIdentifier();

		final Integer clientId = adClientTable.getOptional(clientIdentifier)
				.map(I_AD_Client::getAD_Client_ID)
				.orElseGet(clientIdentifier::getAsInt);

		final I_AD_Client clientRecord = InterfaceWrapperHelper.load(clientId, I_AD_Client.class);

		row.getAsOptionalIdentifier(I_AD_Client.COLUMNNAME_AD_ReplicationStrategy_ID)
				.ifPresent(replicationStrategyIdentifier ->
						{
							final Integer replicationStrategyID = adReplicationStrategyTable.getOptional(replicationStrategyIdentifier)
									.map(I_AD_ReplicationStrategy::getAD_ReplicationStrategy_ID)
									.orElseGet(replicationStrategyIdentifier::getAsInt);
							clientRecord.setAD_ReplicationStrategy_ID(replicationStrategyID);
						}
				);

		row.getAsOptionalBoolean(I_AD_Client.COLUMNNAME_StoreArchiveOnFileSystem)
				.ifPresent(isStoreArchiveOnFileSystem ->
						{
							clientRecord.setStoreArchiveOnFileSystem(isStoreArchiveOnFileSystem);
							clientRecord.setUnixArchivePath(CUCUMBER_UNIX_ARCHIVE_PATH);
						}
				);

		saveRecord(clientRecord);
	}
}
