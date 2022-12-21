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

package de.metas.cucumber.stepdefs.simulationplan;

import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.calendar.simulation.SimulationPlanRef;
import de.metas.calendar.simulation.SimulationPlanRepository;
import de.metas.calendar.simulation.SimulationPlanService;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_SimulationPlan;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class C_SimulationPlan_StepDef
{
	private final static Logger logger = LogManager.getLogger(C_SimulationPlan_StepDef.class);

	private final SimulationPlanRepository simulationPlanRepository = SpringContextHolder.instance.getBean(SimulationPlanRepository.class);
	private final SimulationPlanService simulationPlanService = SpringContextHolder.instance.getBean(SimulationPlanService.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_User_StepDefData userTable;
	private final C_SimulationPlan_StepDefData simulationPlanTable;

	public C_SimulationPlan_StepDef(
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final C_SimulationPlan_StepDefData simulationPlanTable)
	{
		this.userTable = userTable;
		this.simulationPlanTable = simulationPlanTable;
	}

	@And("master C_SimulationPlan is found")
	public void lookup_master_C_SimulationPlan(@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			findMasterSimulationPlan(tableRow);
		}
	}

	@And("C_SimulationPlan is processed")
	public void process_C_SimulationPlan(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			processSimulationPlan(tableRow);
		}
	}

	@And("validate C_SimulationPlan")
	public void lookup_C_SimulationPlan(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			findSimulationPlan(tableRow);
		}
	}

	@And("metasfresh contains C_SimulationPlan")
	public void create_C_SimulationPlan(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createSimulationPlan(tableRow);
		}
	}

	@And("C_SimulationPlan is deactivated")
	public void deactivate_C_SimulationPlan(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			deactivateSimulationPlan(tableRow);
		}
	}

	@And("deactivate all master C_SimulationPlan")
	public void deactivate_all_master_C_SimulationPlan()
	{
		queryBL.createQueryBuilder(I_C_SimulationPlan.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SimulationPlan.COLUMNNAME_IsMainSimulation, true)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_SimulationPlan.COLUMNNAME_IsActive, false);
	}

	private void findMasterSimulationPlan(@NonNull final Map<String, String> tableRow)
	{
		final String orgCode = DataTableUtil.extractStringForColumnName(tableRow, "OrgCode");
		final OrgId orgId = queryBL.createQueryBuilder(I_AD_Org.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, orgCode)
				.create()
				.firstIdOnly(OrgId::ofRepoId);

		assertThat(orgId).isNotNull();

		final Optional<SimulationPlanRef> simulationPlanRef = simulationPlanRepository.getCurrentMainSimulationPlan(orgId);
		assertThat(simulationPlanRef).isPresent();

		final I_C_SimulationPlan simulationPlanRecord = InterfaceWrapperHelper.load(simulationPlanRef.get().getId().getRepoId(), I_C_SimulationPlan.class);
		final String simulationPlanIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_SimulationPlan.COLUMNNAME_C_SimulationPlan_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		simulationPlanTable.putOrReplace(simulationPlanIdentifier, simulationPlanRecord);
	}

	private void deactivateSimulationPlan(@NonNull final Map<String, String> tableRow)
	{
		final String simulationPlanIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_SimulationPlan.COLUMNNAME_C_SimulationPlan_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_SimulationPlan simulationPlanRecord = simulationPlanTable.get(simulationPlanIdentifier);
		assertThat(simulationPlanRecord).isNotNull();

		simulationPlanRecord.setIsActive(false);

		saveRecord(simulationPlanRecord);
	}

	private void createSimulationPlan(@NonNull final Map<String, String> tableRow)
	{
		final String responsibleUserIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_SimulationPlan.COLUMNNAME_AD_User_Responsible_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_AD_User userRecord = userTable.get(responsibleUserIdentifier);
		assertThat(userRecord).isNotNull();

		final boolean isMainSimulation = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_SimulationPlan.COLUMNNAME_IsMainSimulation, true);
		final boolean processed = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_SimulationPlan.COLUMNNAME_Processed, false);

		final SimulationPlanRef simulationPlan = simulationPlanService.getOrCreateMainSimulationPlan(
				UserId.ofRepoId(userRecord.getAD_User_ID()),
				OrgId.ofRepoId(userRecord.getAD_Org_ID()));

		final I_C_SimulationPlan simulationPlanRecord = InterfaceWrapperHelper.load(simulationPlan.getId().getRepoId(), I_C_SimulationPlan.class);
		assertThat(simulationPlanRecord).isNotNull();
		if (simulationPlan.isMainSimulation() != isMainSimulation || simulationPlan.isProcessed() != processed)
		{
			simulationPlanRecord.setIsMainSimulation(isMainSimulation);
			simulationPlanRecord.setProcessed(processed);
		}

		final String simulationPlanIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_SimulationPlan.COLUMNNAME_C_SimulationPlan_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		simulationPlanTable.putOrReplace(simulationPlanIdentifier, simulationPlanRecord);
	}

	private void findSimulationPlan(@NonNull final Map<String, String> tableRow)
	{
		final String simulationPlanIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_SimulationPlan.COLUMNNAME_C_SimulationPlan_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_SimulationPlan simulationPlanRecord = simulationPlanTable.get(simulationPlanIdentifier);
		assertThat(simulationPlanRecord).isNotNull();

		InterfaceWrapperHelper.refresh(simulationPlanRecord);

		final SoftAssertions softly = new SoftAssertions();

		final String responsibleUserIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_SimulationPlan.COLUMNNAME_AD_User_Responsible_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_AD_User userRecord = userTable.get(responsibleUserIdentifier);
		softly.assertThat(userRecord).isNotNull();

		final boolean isMainSimulation = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_SimulationPlan.COLUMNNAME_IsMainSimulation, true);
		final boolean isProcessed = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_SimulationPlan.COLUMNNAME_Processed, false);

		softly.assertThat(simulationPlanRecord.getAD_User_Responsible_ID()).isEqualTo(userRecord.getAD_User_ID());
		softly.assertThat(simulationPlanRecord.isMainSimulation()).isEqualTo(isMainSimulation);
		softly.assertThat(simulationPlanRecord.isProcessed()).isEqualTo(isProcessed);

		softly.assertAll();
	}

	private void processSimulationPlan(@NonNull final Map<String, String> tableRow)
	{
		final String simulationPlanIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_SimulationPlan.COLUMNNAME_C_SimulationPlan_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_SimulationPlan simulationPlanRecord = simulationPlanTable.get(simulationPlanIdentifier);
		assertThat(simulationPlanRecord).isNotNull();

		simulationPlanService.complete(SimulationPlanId.ofRepoId(simulationPlanRecord.getC_SimulationPlan_ID()));
	}
}
