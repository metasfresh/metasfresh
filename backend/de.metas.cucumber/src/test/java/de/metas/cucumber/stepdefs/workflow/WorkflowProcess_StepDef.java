/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.workflow;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.workflow.dto.WFProcessId;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.picking.workflow.handlers.PickingMobileApplication;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;

public class WorkflowProcess_StepDef
{
	private final JsonWFProcess_StepDefData workflowProcessTable;

	public WorkflowProcess_StepDef(final JsonWFProcess_StepDefData workflowProcessTable)
	{
		this.workflowProcessTable = workflowProcessTable;
	}

	@Then("validate workflow process")
	public void validateDocStatusForProcess(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softAssertions = new SoftAssertions();
		DataTableRows.of(dataTable).forEach((row) -> {
			final WFProcessId processId = row.getAsIdentifier("WorkflowProcess").lookupIn(workflowProcessTable);

			if (processId.getId().startsWith(PickingMobileApplication.APPLICATION_ID.getAsString()))
			{
				validatePickingJob(processId, row, softAssertions);
			}
			else
			{
				softAssertions.fail("Unknown processId = " + processId.getId());
			}
		});

		softAssertions.assertAll();
	}

	private void validatePickingJob(
			@NonNull final WFProcessId processId,
			@NonNull final DataTableRow tableRow,
			@NonNull final SoftAssertions assertionCollector)
	{
		final de.metas.workflow.rest_api.model.WFProcessId backendProcessId = de.metas.workflow.rest_api.model.WFProcessId.ofString(processId.getId());

		assertionCollector.assertThat(backendProcessId.getApplicationId()).isEqualTo(PickingMobileApplication.APPLICATION_ID);
		final PickingJobId pickingJobId = backendProcessId.getRepoId(PickingJobId::ofRepoId);

		final I_M_Picking_Job pickingJob = InterfaceWrapperHelper.load(pickingJobId, I_M_Picking_Job.class);

		final String docStatus = tableRow.getAsString("DocStatus");

		assertionCollector.assertThat(pickingJob.getDocStatus()).isEqualTo(docStatus);
	}
}
