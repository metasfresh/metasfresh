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

import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfoLog;
import de.metas.util.Services;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AD_PInstance_StepDef
{
	private final IADPInstanceDAO instanceDAO = Services.get(IADPInstanceDAO.class);

	@Then("a new metasfresh AD_PInstance_Log is stored for PInstance id {string}")
	public void new_metasfresh_ad_pinstance_log_is_stored(final String pInstanceIdString)
	{
		final PInstanceId pInstanceId = PInstanceId.ofRepoId(Integer.parseInt(pInstanceIdString));
		final List<ProcessInfoLog> processInfoLogs = instanceDAO.retrieveProcessInfoLogs(pInstanceId);

		assertThat(processInfoLogs).isNotNull();
		assertThat(processInfoLogs.get(0).getP_Msg()).isEqualTo("External job finished successfully");
	}
}
