/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.material.dispo;

import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

/**
 * Step definitions for running the {@code MD_Candidate_Rebuild} process.
 * <p>
 * Steps:
 * <ul>
 *   <li>{@code When the MD_Candidate_Rebuild process is run with IsBackupBeforeDelete=Y/N}</li>
 * </ul>
 */
@RequiredArgsConstructor
public class MD_Candidate_Rebuild_StepDef
{
	@NonNull private static final Logger logger = LogManager.getLogger(MD_Candidate_Rebuild_StepDef.class);

	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

	/**
	 * Runs the MD_Candidate_Rebuild AD_Process with the given backup parameter.
	 *
	 * @param isBackupBeforeDelete "Y" or "N"
	 */
	@When("the MD_Candidate_Rebuild process is run with IsBackupBeforeDelete={string}")
	public void run_md_candidate_rebuild(@NonNull final String isBackupBeforeDelete)
	{
		final AdProcessId processId = processDAO.retrieveProcessIdByValue("MD_Candidate_Rebuild");
		logger.info("Running MD_Candidate_Rebuild (AD_Process_ID={}, IsBackupBeforeDelete={})", processId, isBackupBeforeDelete);

		final boolean backup = "Y".equals(isBackupBeforeDelete);

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.addParameter("IsBackupBeforeDelete", backup)
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		logger.info("MD_Candidate_Rebuild completed successfully");
	}
}
