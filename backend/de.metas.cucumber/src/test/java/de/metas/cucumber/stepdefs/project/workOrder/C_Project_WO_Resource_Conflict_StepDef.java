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

package de.metas.cucumber.stepdefs.project.workOrder;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.cucumber.stepdefs.simulationplan.C_SimulationPlan_StepDefData;
import de.metas.project.ProjectId;
import de.metas.project.workorder.resource.WOProjectResourceId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.I_C_Project_WO_Resource_Conflict;
import org.compiere.model.I_C_SimulationPlan;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_Project_WO_Resource_Conflict_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_Project_StepDefData projectTable;
	private final C_Project_WO_Resource_StepDefData woProjectResourceTable;
	private final C_SimulationPlan_StepDefData simulationPlanTable;
	private final C_Project_WO_Resource_Conflict_StepDefData resourceConflictTable;

	public C_Project_WO_Resource_Conflict_StepDef(
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final C_Project_WO_Resource_StepDefData woProjectResourceTable,
			@NonNull final C_SimulationPlan_StepDefData simulationPlanTable,
			@NonNull final C_Project_WO_Resource_Conflict_StepDefData resourceConflictTable)
	{
		this.projectTable = projectTable;
		this.woProjectResourceTable = woProjectResourceTable;
		this.simulationPlanTable = simulationPlanTable;
		this.resourceConflictTable = resourceConflictTable;
	}

	@And("C_Project_WO_Resource_Conflict is found")
	public void lookup_C_Project_WO_Resource_Conflict(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			findProjectWoResourceConflict(tableRow);
		}
	}

	@And("C_Project_WO_Resource_Conflict is validated")
	public void validate_C_Project_WO_Resource_Conflict(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateC_Project_WO_Resource_Conflict(tableRow);
		}
	}

	@And("C_Project_WO_Resource_Conflict is approved")
	public void approve_C_Project_WO_Resource_Conflict(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			approveC_Project_WO_Resource_Conflict(tableRow);
		}
	}

	private void approveC_Project_WO_Resource_Conflict(@NonNull final Map<String, String> tableRow)
	{
		final String projectWoResourceConflictIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_WO_Resource_Conflict_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Project_WO_Resource_Conflict projectWoResourceConflictRecord = resourceConflictTable.get(projectWoResourceConflictIdentifier);
		assertThat(projectWoResourceConflictRecord).isNotNull();

		projectWoResourceConflictRecord.setIsApproved(true);

		saveRecord(projectWoResourceConflictRecord);
	}

	private void validateC_Project_WO_Resource_Conflict(@NonNull final Map<String, String> tableRow)
	{
		final String projectWoResourceConflictIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_WO_Resource_Conflict_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Project_WO_Resource_Conflict projectWoResourceConflictRecord = resourceConflictTable.get(projectWoResourceConflictIdentifier);
		assertThat(projectWoResourceConflictRecord).isNotNull();

		final boolean isApproved = DataTableUtil.extractBooleanForColumnNameOr(tableRow, I_C_Project_WO_Resource_Conflict.COLUMNNAME_IsApproved, false);

		assertThat(projectWoResourceConflictRecord.isApproved()).isEqualTo(isApproved);
	}

	private void findProjectWoResourceConflict(@NonNull final Map<String, String> tableRow)
	{
		final String project2Identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project2_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_C_Project project = projectTable.get(project2Identifier);
		assertThat(project).isNotNull();

		final ProjectId projectId = ProjectId.ofRepoId(project.getC_Project_ID());

		final String resource2Identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_WO_Resource2_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Project_WO_Resource woProjectResource2Record = woProjectResourceTable.get(resource2Identifier);
		assertThat(woProjectResource2Record).isNotNull();

		final String simulationPlanIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_SimulationPlan_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_SimulationPlan simulationPlanRecord = simulationPlanTable.get(simulationPlanIdentifier);
		assertThat(simulationPlanRecord).isNotNull();

		final C_Project_WO_Resource_Conflict_StepDef.C_Project_WO_Resource_Conflict_Query projectWoResourceConflictQuery = C_Project_WO_Resource_Conflict_StepDef.C_Project_WO_Resource_Conflict_Query.builder()
				.project2Id(projectId)
				.woProjectResource2Id(WOProjectResourceId.ofRepoId(projectId, woProjectResource2Record.getC_Project_WO_Resource_ID()))
				.simulationPlanId(SimulationPlanId.ofRepoId(simulationPlanRecord.getC_SimulationPlan_ID()))
				.build();

		final I_C_Project_WO_Resource_Conflict projectWoResourceConflictRecord = getResourceConflict(projectWoResourceConflictQuery);
		final String projectWoResourceConflictIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_WO_Resource_Conflict_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		resourceConflictTable.putOrReplace(projectWoResourceConflictIdentifier, projectWoResourceConflictRecord);
	}

	@NonNull
	private I_C_Project_WO_Resource_Conflict getResourceConflict(
			@NonNull final C_Project_WO_Resource_Conflict_StepDef.C_Project_WO_Resource_Conflict_Query projectWoResourceConflictQuery)
	{
		return queryBL.createQueryBuilder(I_C_Project_WO_Resource_Conflict.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project2_ID, projectWoResourceConflictQuery.getProject2Id())
				.addEqualsFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_Project_WO_Resource2_ID, projectWoResourceConflictQuery.getWoProjectResource2Id())
				.addEqualsFilter(I_C_Project_WO_Resource_Conflict.COLUMNNAME_C_SimulationPlan_ID, projectWoResourceConflictQuery.getSimulationPlanId())
				.create()
				.firstOnlyOptional(I_C_Project_WO_Resource_Conflict.class)
				.orElseThrow(() -> new AdempiereException("Couldn't find any C_Project_WO_Resource_Conflict querying by"
																  + " C_Project2_ID=" + projectWoResourceConflictQuery.getProject2Id().getRepoId()
																  + " C_Project_WO_Resource2_ID=" + projectWoResourceConflictQuery.getWoProjectResource2Id().getRepoId()
																  + " C_SimulationPlan_ID=" + projectWoResourceConflictQuery.getSimulationPlanId().getRepoId()));
	}

	@Builder
	@Value
	private static class C_Project_WO_Resource_Conflict_Query
	{
		@NonNull
		ProjectId project2Id;

		@NonNull
		WOProjectResourceId woProjectResource2Id;

		@NonNull
		SimulationPlanId simulationPlanId;
	}
}
