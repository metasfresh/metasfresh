/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.pinstance;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfoLog;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_PInstance_Log;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AD_PInstance_StepDef
{
	private final IADPInstanceDAO instanceDAO = Services.get(IADPInstanceDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@Then("a new metasfresh AD_PInstance_Log is stored for PInstance id {string}")
	public void new_metasfresh_ad_pinstance_log_is_stored(final String pInstanceIdString)
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(Integer.parseInt(pInstanceIdString));
		final List<ProcessInfoLog> processInfoLogs = instanceDAO.retrieveProcessInfoLogs(pInstanceId);

		assertThat(processInfoLogs).isNotNull();
		assertThat(processInfoLogs.get(0).getP_Msg()).isEqualTo("External job finished successfully");
	}

	@Then("validate AD_PInstance_Log:")
	public void validate_created_pInstanceLog(final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final int pInstanceId = DataTableUtil.extractIntForColumnName(row, I_AD_PInstance_Log.COLUMNNAME_AD_PInstance_ID);
			final int recordId = DataTableUtil.extractIntForColumnName(row, I_AD_PInstance_Log.COLUMNNAME_Record_ID);
			final String message = DataTableUtil.extractStringForColumnName(row, I_AD_PInstance_Log.COLUMNNAME_P_Msg);

			final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_PInstance_Log.COLUMNNAME_AD_Table_ID + ".TableName");
			final int tableId = tableDAO.retrieveTableId(tableName);

			final List<ProcessInfoLog> processInfoLogs = instanceDAO.retrieveProcessInfoLogs(PInstanceId.ofRepoId(pInstanceId));

			assertThat(processInfoLogs).isNotNull();
			assertThat(processInfoLogs.size()).isEqualTo(1);
			assertThat(processInfoLogs.get(0).getP_Msg()).isEqualTo(message);

			assertThat(processInfoLogs.get(0).getTableRecordReference()).isNotNull();
			assertThat(processInfoLogs.get(0).getTableRecordReference().getRecord_ID()).isEqualTo(recordId);
			assertThat(processInfoLogs.get(0).getTableRecordReference().getAD_Table_ID()).isEqualTo(tableId);
		}
	}

	@Given("add I_AD_PInstance with id {int}")
	public void new_PInstanceId_is_created(final int pInstanceId)
	{
		final PInstanceId pInstanceIdRepoAware = PInstanceId.ofRepoId(pInstanceId);
		final I_AD_PInstance pInstance = CoalesceUtil.coalesceSuppliersNotNull(() -> InterfaceWrapperHelper.load(pInstanceIdRepoAware, I_AD_PInstance.class),
																			   () -> InterfaceWrapperHelper.newInstance(I_AD_PInstance.class));

		pInstance.setAD_PInstance_ID(pInstanceIdRepoAware.getRepoId());
		pInstance.setAD_Process_ID(150); //a valid processId, picked randomly as it doesn't matter for our test
		pInstance.setRecord_ID(0); //mandatory

		InterfaceWrapperHelper.save(pInstance);
	}
}
