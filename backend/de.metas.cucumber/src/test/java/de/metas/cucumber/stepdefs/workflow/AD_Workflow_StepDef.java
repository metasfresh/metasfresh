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

package de.metas.cucumber.stepdefs.workflow;

import de.metas.copy_with_details.CopyRecordRequest;
import de.metas.copy_with_details.CopyRecordService;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.PO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_AD_WF_Node_ID;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_AD_Workflow_ID;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_AccessLevel;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_Cost;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_Description;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_Duration;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_DurationLimit;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_DurationUnit;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_Help;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_IsDefault;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_Name;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_Priority;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_ValidFrom;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_ValidTo;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_Version;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_WaitingTime;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_WorkflowType;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_AD_Workflow_ID;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_Name;

public class AD_Workflow_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CopyRecordService copyRecordService = SpringContextHolder.instance.getBean(CopyRecordService.class);

	private final AD_Workflow_StepDefData workflowTable;
	private final AD_WF_Node_StepDefData wfNodeTable;

	public AD_Workflow_StepDef(
			@NonNull final AD_Workflow_StepDefData workflowTable,
			@NonNull final AD_WF_Node_StepDefData wfNodeTable)
	{
		this.workflowTable = workflowTable;
		this.wfNodeTable = wfNodeTable;
	}
	
	@And("load AD_Workflow:")
	public void load_AD_Workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String workflowName = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Name);

			final I_AD_Workflow workflow = queryBL.createQueryBuilder(I_AD_Workflow.class)
					.addEqualsFilter(COLUMNNAME_Name, workflowName)
					.create()
					.firstOnlyNotNull(I_AD_Workflow.class);

			final String workflowIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);
			workflowTable.put(workflowIdentifier, workflow);
		}
	}

	@And("create AD_Workflow:")
	public void create_AD_Workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			createWorkflow(row);
		}
	}

	@And("clone AD_Workflow:")
	public void clone_AD_Workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			cloneWorkflow(row);
		}
	}

	@And("validate AD_Workflow:")
	public void validate_AD_Workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			validateWorkflow(row);
		}
	}

	@And("update AD_Workflow:")
	public void update_AD_Workflow(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			final String workflowIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_AD_Workflow workflowRecord = workflowTable.get(workflowIdentifier);

			final String wfNodeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_WF_Node_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(wfNodeIdentifier))
			{
				final I_AD_WF_Node wfNode = wfNodeTable.get(wfNodeIdentifier);
				workflowRecord.setAD_WF_Node_ID(wfNode.getAD_WF_Node_ID());
			}
			InterfaceWrapperHelper.saveRecord(workflowRecord);
		}
	}

	private void createWorkflow(@NonNull final Map<String, String> row)
	{
		final String workflowName = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Name);
		final String workflowType = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_WorkflowType);

		final I_AD_Workflow workflowRecord = InterfaceWrapperHelper.newInstance(I_AD_Workflow.class);
		workflowRecord.setName(workflowName);
		workflowRecord.setWorkflowType(workflowType);

		Optional.ofNullable(DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_Description))
				.filter(Check::isNotBlank)
				.ifPresent(description -> workflowRecord.setDescription(DataTableUtil.nullToken2Null(description)));

		Optional.ofNullable(DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_Help))
				.filter(Check::isNotBlank)
				.ifPresent(help -> workflowRecord.setHelp(DataTableUtil.nullToken2Null(help)));

		Optional.ofNullable(DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_AccessLevel))
				.filter(Check::isNotBlank)
				.ifPresent(workflowRecord::setAccessLevel);

		Optional.ofNullable(DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_DurationUnit))
				.filter(Check::isNotBlank)
				.ifPresent(durationUnit -> workflowRecord.setDurationUnit(DataTableUtil.nullToken2Null(durationUnit)));

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_Version))
				.ifPresent(workflowRecord::setVersion);

		Optional.ofNullable(DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + COLUMNNAME_ValidFrom))
				.ifPresent(workflowRecord::setValidFrom);

		Optional.ofNullable(DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + COLUMNNAME_ValidTo))
				.ifPresent(workflowRecord::setValidTo);

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_Priority))
				.ifPresent(workflowRecord::setPriority);

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_DurationLimit))
				.ifPresent(workflowRecord::setDurationLimit);

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_WF_Node_ID + "." + TABLECOLUMN_IDENTIFIER))
				.map(wfNodeTable::get)
				.ifPresent(nodeRecord -> workflowRecord.setAD_WF_Node_ID(nodeRecord.getAD_WF_Node_ID()));

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_Duration))
				.ifPresent(workflowRecord::setDuration);

		Optional.ofNullable(DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_Cost))
				.ifPresent(workflowRecord::setCost);

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_WaitingTime))
				.ifPresent(workflowRecord::setWaitingTime);

		workflowRecord.setIsDefault(DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsDefault, false));

		saveRecord(workflowRecord);

		final String workflowIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);
		workflowTable.putOrReplace(workflowIdentifier, workflowRecord);
	}

	private void cloneWorkflow(@NonNull final Map<String, String> row)
	{
		final String workflowIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_Workflow workflowRecord = workflowTable.get(workflowIdentifier);

		final TableRecordReference recordReference = TableRecordReference.of(I_AD_Workflow.Table_Name, workflowRecord.getAD_Workflow_ID());
		final CopyRecordRequest copyRecordRequest = CopyRecordRequest.builder()
				.tableRecordReference(recordReference)
				.build();

		final PO po = copyRecordService.copyRecord(copyRecordRequest);

		final String clonedWorkflowIdentifier = DataTableUtil.extractStringForColumnName(row, "ClonedWorkflow." + COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_Workflow clonedWorkflowRecord = load(po.get_ID(), I_AD_Workflow.class);
		workflowTable.putOrReplace(clonedWorkflowIdentifier, clonedWorkflowRecord);
	}

	private void validateWorkflow(@NonNull final Map<String, String> row)
	{
		final String workflowIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_Workflow workflowRecord = workflowTable.get(workflowIdentifier);

		final String workflowName = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Name);
		final String workflowType = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_WorkflowType);

		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(workflowRecord.getName()).as(COLUMNNAME_Name).isEqualTo(workflowName);
		softly.assertThat(workflowRecord.getWorkflowType()).as(COLUMNNAME_WorkflowType).isEqualTo(workflowType);

		Optional.ofNullable(DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_Description))
				.filter(Check::isNotBlank)
				.ifPresent(description -> softly.assertThat(workflowRecord.getDescription()).as(COLUMNNAME_Description).isEqualTo(DataTableUtil.nullToken2Null(description)));

		Optional.ofNullable(DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_Help))
				.filter(Check::isNotBlank)
				.ifPresent(help -> softly.assertThat(workflowRecord.getHelp()).as(COLUMNNAME_Help).isEqualTo(DataTableUtil.nullToken2Null(help)));

		Optional.ofNullable(DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_AccessLevel))
				.filter(Check::isNotBlank)
				.ifPresent(accessLevel -> softly.assertThat(workflowRecord.getAccessLevel()).as(COLUMNNAME_AccessLevel).isEqualTo(accessLevel));

		Optional.ofNullable(DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_DurationUnit))
				.filter(Check::isNotBlank)
				.ifPresent(durationUnit -> softly.assertThat(workflowRecord.getDurationUnit()).as(COLUMNNAME_DurationUnit).isEqualTo(DataTableUtil.nullToken2Null(durationUnit)));

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_Version))
				.ifPresent(version -> softly.assertThat(workflowRecord.getVersion()).as(COLUMNNAME_Version).isEqualTo(version));

		Optional.ofNullable(DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + COLUMNNAME_ValidFrom))
				.ifPresent(validFrom -> softly.assertThat(workflowRecord.getValidFrom()).as(COLUMNNAME_ValidFrom).isEqualTo(validFrom));

		Optional.ofNullable(DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + COLUMNNAME_ValidTo))
				.ifPresent(validTo -> softly.assertThat(workflowRecord.getValidTo()).as(COLUMNNAME_ValidTo).isEqualTo(validTo));

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_Priority))
				.ifPresent(priority -> softly.assertThat(workflowRecord.getPriority()).as(COLUMNNAME_Priority).isEqualTo(priority));

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_DurationLimit))
				.ifPresent(durationLimit -> softly.assertThat(workflowRecord.getDurationLimit()).as(COLUMNNAME_DurationLimit).isEqualTo(durationLimit));

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_WF_Node_ID + "." + TABLECOLUMN_IDENTIFIER))
				.map(wfNodeTable::get)
				.ifPresent(nodeRecord -> softly.assertThat(workflowRecord.getAD_WF_Node_ID()).as(COLUMNNAME_AD_WF_Node_ID).isEqualTo(nodeRecord.getAD_WF_Node_ID()));

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_Duration))
				.ifPresent(duration -> softly.assertThat(workflowRecord.getDuration()).as(COLUMNNAME_Duration).isEqualTo(duration));

		Optional.ofNullable(DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_Cost))
				.ifPresent(cost -> softly.assertThat(workflowRecord.getCost()).as(COLUMNNAME_Cost).isEqualTo(cost));

		Optional.ofNullable(DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + COLUMNNAME_WaitingTime))
				.ifPresent(waitingTime -> softly.assertThat(workflowRecord.getWaitingTime()).as(COLUMNNAME_WaitingTime).isEqualTo(waitingTime));

		softly.assertThat(workflowRecord.isDefault()).as(COLUMNNAME_IsDefault).isEqualTo(DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + COLUMNNAME_IsDefault, false));

		softly.assertAll();
	}
}
