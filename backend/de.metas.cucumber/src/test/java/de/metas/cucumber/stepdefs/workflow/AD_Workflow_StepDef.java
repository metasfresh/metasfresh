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
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.productplanning.PP_Product_Planning_StepDefData;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WorkflowId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
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
import org.eevolution.model.I_PP_Product_Planning;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_AD_Workflow.COLUMNNAME_AD_User_InCharge_ID;
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

public class AD_Workflow_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CopyRecordService copyRecordService = SpringContextHolder.instance.getBean(CopyRecordService.class);

	private final AD_Workflow_StepDefData workflowTable;
	private final AD_WF_Node_StepDefData wfNodeTable;
	private final AD_User_StepDefData userTable;
	private final PP_Product_Planning_StepDefData productPlanningTable;

	/**
	 * Every {@code AD_Workflow} this step-def created -- directly ({@link #createWorkflow(Map)}) or via {@link
	 * #cloneWorkflow(Map)} -- so {@link #deactivateCreatedWorkflows()} can deactivate them afterwards -- unlike
	 * the {@code Name} collision (fixed by uniquifying it), a created-but-never-deactivated workflow does not
	 * break anything by itself, but it accumulates forever on a persistent local DB, one row per run. Mirrors
	 * {@code PP_Product_Planning_StepDef}'s own cleanup of the same shape, using the same {@code WorkflowId} type
	 * that class uses ({@code ProductPlanningId}).
	 * <p>
	 * Also mirrored into {@code workflowTable} (via {@link AD_Workflow_StepDefData#markCreated}) so {@code
	 * AD_WF_Node_StepDef} can tell a workflow this step-def created apart from one it only registered via
	 * {@code load AD_Workflow:} (masterdata) -- see that class's own teardown.
	 */
	private final Set<WorkflowId> createdWorkflowIds = new HashSet<>();

	/**
	 * Every {@code AD_WF_Node} that {@link #cloneWorkflow(Map)} produced as a side effect of cloning a workflow
	 * (via {@link CopyRecordService}), which never goes through {@code AD_WF_Node_StepDef.create_AD_WF_Node} and
	 * so is invisible to that class's own {@code createdNodeIds} tracking. Deactivated by {@link
	 * #deactivateCreatedWorkflows()} alongside the cloned workflows themselves.
	 */
	private final Set<WFNodeId> createdViaCloneNodeIds = new HashSet<>();

	/**
	 * Every {@code AD_Workflow.AD_User_InCharge_ID} this scenario overwrote via
	 * {@link #update_AD_Workflow_user_in_charge(DataTable)}, keyed by the routing and mapped to the user in charge
	 * from BEFORE the overwrite ({@code null} standing for "was unset") -- restored by
	 * {@link #restoreUserInChargeAfterScenario()}. Unlike {@link #createdWorkflowIds} (rows this scenario
	 * itself INSERTED, which are deactivated afterwards), a workflow named here is a pre-existing SHARED row
	 * -- e.g. the default manufacturing routing every order in this feature completes against -- that other
	 * scenarios and executors also read, so its prior value must be put BACK, never merely deactivated.
	 */
	private final Map<PPRoutingId, UserId> priorUserInChargeIdByRoutingId = new LinkedHashMap<>();

	public AD_Workflow_StepDef(
			@NonNull final AD_Workflow_StepDefData workflowTable,
			@NonNull final AD_WF_Node_StepDefData wfNodeTable,
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final PP_Product_Planning_StepDefData productPlanningTable)
	{
		this.workflowTable = workflowTable;
		this.wfNodeTable = wfNodeTable;
		this.userTable = userTable;
		this.productPlanningTable = productPlanningTable;
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

	/**
	 * Creates an {@code AD_Workflow} (routing) record per row.
	 * <p>
	 * Required: {@code AD_Workflow_ID.Identifier}, {@code WorkflowType}. {@code Name} is optional — when
	 * omitted, a per-run-unique one is generated from the identifier, so the fixture never collides with a
	 * leftover row of the same name from an earlier local run (see the comment on the fallback inside
	 * {@link #createWorkflow(Map)}). A table that pins a literal {@code Name} (e.g. because a later step
	 * asserts it) keeps that exact collision risk on a persistent local DB -- unavoidable without breaking
	 * that assertion, so it is not this step's call to make silently. Also optional: {@code
	 * OPT.AD_User_InCharge_ID.Identifier}, an
	 * {@code AD_User} registered earlier (e.g. via {@code load AD_User:}) — carried through to
	 * {@code PPRouting.getUserInChargeId()}, the responsible-user grouping key the order-checkup report
	 * builds its "Warehouse" rows by.
	 */
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

	/**
	 * Overwrites an existing {@code AD_Workflow}'s (manufacturing routing's) {@code AD_User_InCharge_ID} --
	 * the same field edit a customer makes in the window to configure who a routing's print job goes to.
	 * Omitting {@code AD_User_InCharge_ID} (or leaving it blank) clears the routing to NO user in charge,
	 * which is exactly the unconfigured state that silently cancels the print job with no error anywhere
	 * (see {@code OrderCheckupPrintingQueueHandler}).
	 * <p>
	 * The routing is the one a previously created {@code PP_Product_Planning} uses -- typically a SHARED,
	 * pre-existing routing (e.g. the default manufacturing routing every order in this feature completes
	 * against), not one this scenario itself created. The prior value is captured before the overwrite and
	 * restored by {@link #restoreUserInChargeAfterScenario()} -- see {@link #priorUserInChargeIdByRoutingId}'s
	 * own Javadoc for why.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>PP_Product_Planning_ID</b> — (required, identifier-ref) the product planning whose routing to update<br>
	 *   <b>AD_User_InCharge_ID</b> — (optional, identifier-ref) the user to put in charge; omitted/blank
	 *       clears the routing to no user in charge<br>
	 * @cucumber.depends StepDefData: PP_Product_Planning_StepDefData, AD_User_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update AD_Workflow user in charge:
	 *   | PP_Product_Planning_ID | AD_User_InCharge_ID |
	 *   | productPlanning        | routingUser         |
	 * </pre>
	 */
	@And("update AD_Workflow user in charge:")
	public void update_AD_Workflow_user_in_charge(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateUserInCharge);
	}

	private void updateUserInCharge(@NonNull final DataTableRow row)
	{
		final ProductPlanning productPlanning = row.getAsIdentifier(I_PP_Product_Planning.COLUMNNAME_PP_Product_Planning_ID).lookupNotNullIn(productPlanningTable);
		final PPRoutingId routingId = Check.assumeNotNull(productPlanning.getWorkflowId(), "PP_Product_Planning has a routing: {}", productPlanning);
		final UserId userId = row.getAsOptionalIdentifier(COLUMNNAME_AD_User_InCharge_ID)
				.filter(StepDefDataIdentifier::isNotNullPlaceholder)
				.map(identifier -> identifier.lookupNotNullIdIn(userTable))
				.orElse(null);

		final I_AD_Workflow workflowRecord = load(routingId, I_AD_Workflow.class);

		// captured once per routing per scenario: a second overwrite in the same scenario must not clobber the
		// ALREADY-captured original with this scenario's own first write (mirrors C_Doc_Outbound_Config_StepDef).
		// containsKey rather than putIfAbsent, because a prior "unset" is stored as a null value.
		if (!priorUserInChargeIdByRoutingId.containsKey(routingId))
		{
			priorUserInChargeIdByRoutingId.put(routingId, UserId.ofRepoIdOrNullIfSystem(workflowRecord.getAD_User_InCharge_ID()));
		}

		workflowRecord.setAD_User_InCharge_ID(UserId.toRepoId(userId));
		InterfaceWrapperHelper.saveRecord(workflowRecord);
	}

	/**
	 * Guaranteed-execution restore for {@link #update_AD_Workflow_user_in_charge(DataTable)} -- an
	 * {@code @After} hook rather than a trailing Gherkin step, since Cucumber skips remaining steps once one
	 * fails, i.e. on exactly the runs that need the restore (mirrors {@code C_Doc_Outbound_Config_StepDef}
	 * and {@code AD_PrinterRouting_StepDef}). A no-op for every scenario that never called that step.
	 */
	@After
	public void restoreUserInChargeAfterScenario()
	{
		if (priorUserInChargeIdByRoutingId.isEmpty())
		{
			return;
		}

		priorUserInChargeIdByRoutingId.forEach((routingId, priorUserId) -> {
			final I_AD_Workflow workflowRecord = load(routingId, I_AD_Workflow.class);
			workflowRecord.setAD_User_InCharge_ID(UserId.toRepoId(priorUserId));
			InterfaceWrapperHelper.saveRecord(workflowRecord);
		});

		priorUserInChargeIdByRoutingId.clear();
	}

	private void createWorkflow(@NonNull final Map<String, String> row)
	{
		final String workflowIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_Workflow_ID + "." + TABLECOLUMN_IDENTIFIER);

		// AD_Workflow has UNIQUE(AD_Client_ID, Name) and this step always INSERTs (no upsert), so a fixed literal
		// collides on the next local run against a persistent (non-testcontainer) DB. A table that pins a known
		// Name (e.g. for a later "validate AD_Workflow:" assertion) still gets exactly that literal, unchanged --
		// uniquifying it unconditionally would break that assertion, so this fallback only ever applies when the
		// table omits Name. ValueAndName.unique(prefix) is the same helper suggestValueAndName() (S_Resource_StepDef's
		// own convention) falls back to when neither Name nor Value is given -- used directly here since createWorkflow
		// is still Map-based, not yet on DataTableRow.
		final String workflowName = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_Name))
				.filter(Check::isNotBlank)
				.orElseGet(() -> ValueAndName.unique(workflowIdentifier).getName());
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

		Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_User_InCharge_ID + "." + TABLECOLUMN_IDENTIFIER))
				.map(userTable::get)
				.ifPresent(user -> workflowRecord.setAD_User_InCharge_ID(user.getAD_User_ID()));

		saveRecord(workflowRecord);
		final WorkflowId workflowId = WorkflowId.ofRepoId(workflowRecord.getAD_Workflow_ID());
		createdWorkflowIds.add(workflowId);
		workflowTable.markCreated(workflowId);

		workflowTable.putOrReplace(workflowIdentifier, workflowRecord);
	}

	/**
	 * Deactivates every {@code AD_Workflow} this scenario created or cloned (tracked in {@link
	 * #createdWorkflowIds}), and every {@code AD_WF_Node} a clone produced as a side effect (tracked in {@link
	 * #createdViaCloneNodeIds}) -- see those fields' own Javadoc. Runs on scenario pass AND failure.
	 * <p>
	 * Clears each created workflow's own {@code AD_WF_Node_ID} pointer first, unconditionally: {@code
	 * AD_Workflow.validateFirstNode} explicitly permits a cleared pointer, but {@code
	 * PPRoutingRepository.isFirstNodeOfWorkflow} ignores {@code IsActive} when guarding a node's own
	 * deactivation, so a still-set pointer blocks that node from ever being deactivated -- by this method (for a
	 * cloned node, below) or by {@code AD_WF_Node_StepDef}'s own teardown (for a node created via {@code create
	 * AD_WF_Node:}), regardless of which {@code @After} hook happens to run first. Scoped to {@link
	 * #createdWorkflowIds} only, so a workflow this scenario merely loaded (masterdata) is never touched.
	 */
	@After
	public void deactivateCreatedWorkflows()
	{
		for (final WorkflowId workflowId : createdWorkflowIds)
		{
			final I_AD_Workflow record = InterfaceWrapperHelper.load(workflowId, I_AD_Workflow.class);
			if (record.getAD_WF_Node_ID() > 0)
			{
				record.setAD_WF_Node_ID(0);
				InterfaceWrapperHelper.saveRecord(record);
			}
		}

		for (final WFNodeId nodeId : createdViaCloneNodeIds)
		{
			final I_AD_WF_Node record = InterfaceWrapperHelper.load(nodeId, I_AD_WF_Node.class);
			if (record.isActive())
			{
				record.setIsActive(false);
				InterfaceWrapperHelper.saveRecord(record);
			}
		}

		for (final WorkflowId workflowId : createdWorkflowIds)
		{
			final I_AD_Workflow record = InterfaceWrapperHelper.load(workflowId, I_AD_Workflow.class);
			if (record.isActive())
			{
				record.setIsActive(false);
				InterfaceWrapperHelper.saveRecord(record);
			}
		}
	}

	/**
	 * Clones an {@code AD_Workflow} via {@link CopyRecordService}, which also clones every one of its {@code
	 * AD_WF_Node} rows and remaps the clone's own {@code AD_WF_Node_ID} pointer to its own copied node (not the
	 * original's). Both the cloned workflow and its cloned nodes are tracked here (never through {@code
	 * AD_WF_Node_StepDef}, which only ever sees nodes created via its own {@code create AD_WF_Node:} step) so
	 * {@link #deactivateCreatedWorkflows()} deactivates them afterwards instead of leaving them behind on every
	 * run.
	 */
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

		final WorkflowId clonedWorkflowId = WorkflowId.ofRepoId(clonedWorkflowRecord.getAD_Workflow_ID());
		createdWorkflowIds.add(clonedWorkflowId);
		workflowTable.markCreated(clonedWorkflowId);

		queryBL.createQueryBuilder(I_AD_WF_Node.class)
				.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID, clonedWorkflowRecord.getAD_Workflow_ID())
				.create()
				.list()
				.forEach(clonedNode -> createdViaCloneNodeIds.add(WFNodeId.ofRepoId(clonedNode.getAD_WF_Node_ID())));
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
