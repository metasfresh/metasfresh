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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.cucumber.stepdefs.simulationplan.C_SimulationPlan_StepDefData;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_Project_WO_Step;
import org.compiere.model.I_C_Project_WO_Step_Simulation;
import org.compiere.model.I_C_SimulationPlan;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class C_Project_WO_Step_Simulation_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final C_Project_StepDefData projectTable;
	private final C_Project_WO_Step_StepDefData projectWOStepTable;
	private final C_SimulationPlan_StepDefData simulationPlanTable;
	private final C_Project_WO_Step_Simulation_StepDefData woStepSimulationTable;

	public C_Project_WO_Step_Simulation_StepDef(
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final C_Project_WO_Step_StepDefData projectWOStepTable,
			@NonNull final C_SimulationPlan_StepDefData simulationPlanTable,
			@NonNull final C_Project_WO_Step_Simulation_StepDefData woStepSimulationTable)
	{
		this.projectTable = projectTable;
		this.projectWOStepTable = projectWOStepTable;
		this.simulationPlanTable = simulationPlanTable;
		this.woStepSimulationTable = woStepSimulationTable;
	}

	@And("validate C_Project_WO_Step_Simulation")
	public void validate_C_Project_WO_Step_Simulation(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateProjectWoStepSimulation(tableRow);
		}
	}

	@And("load C_Project_WO_Step_Simulation")
	public void load_C_Project_WO_Step_Simulation(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadProjectWoStepSimulation(tableRow);
		}
	}

	private void validateProjectWoStepSimulation(@NonNull final Map<String, String> tableRow)
	{
		final String woProjectStepSimulationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Step_Simulation.COLUMNNAME_C_Project_WO_Step_Simulation_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Project_WO_Step_Simulation woProjectStepSimulation = woStepSimulationTable.get(woProjectStepSimulationIdentifier);
		assertThat(woProjectStepSimulation).isNotNull();

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(woProjectStepSimulation.getAD_Org_ID()));

		final LocalDate dateStart = DataTableUtil.extractLocalDateOrNullForColumnName(tableRow, "OPT." + I_C_Project_WO_Step_Simulation.COLUMNNAME_DateStart);
		if (dateStart != null)
		{
			assertThat(TimeUtil.asLocalDate(woProjectStepSimulation.getDateStart(), zoneId)).isEqualTo(dateStart);
		}

		final LocalDate dateEnd = DataTableUtil.extractLocalDateOrNullForColumnName(tableRow, "OPT." + I_C_Project_WO_Step_Simulation.COLUMNNAME_DateEnd);
		if (dateEnd != null)
		{
			assertThat(TimeUtil.asLocalDate(woProjectStepSimulation.getDateEnd(), zoneId)).isEqualTo(dateEnd);
		}
	}

	private void loadProjectWoStepSimulation(@NonNull final Map<String, String> tableRow)
	{
		final String projectIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Step_Simulation.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_C_Project project = projectTable.get(projectIdentifier);
		assertThat(project).isNotNull();

		final ProjectId projectId = ProjectId.ofRepoId(project.getC_Project_ID());

		final String woProjectStepIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Step_Simulation.COLUMNNAME_C_Project_WO_Step_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Project_WO_Step woProjectStepRecord = projectWOStepTable.get(woProjectStepIdentifier);
		assertThat(woProjectStepRecord).isNotNull();

		final String simulationIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Step_Simulation.COLUMNNAME_C_SimulationPlan_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_SimulationPlan simulationPlanRecord = simulationPlanTable.get(simulationIdentifier);

		final I_C_Project_WO_Step_Simulation woProjectResourceSimulation = queryBL.createQueryBuilder(I_C_Project_WO_Step_Simulation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_Project_ID, projectId)
				.addEqualsFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_Project_WO_Step_ID, woProjectStepRecord.getC_Project_WO_Step_ID())
				.addEqualsFilter(I_C_Project_WO_Step_Simulation.COLUMNNAME_C_SimulationPlan_ID, simulationPlanRecord.getC_SimulationPlan_ID())
				.create()
				.firstOnlyNotNull(I_C_Project_WO_Step_Simulation.class);

		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_Project_WO_Step_Simulation.COLUMNNAME_C_Project_WO_Step_Simulation_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		woStepSimulationTable.putOrReplace(identifier, woProjectResourceSimulation);
	}
}
