/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.deliveryplanning;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.process.AD_Process_Para_StepDef;
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import de.metas.deliveryplanning.DeliveryPlanningList.AggregationKeyField;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.deliveryplanning.process.DeliveryPlanningProcessHelper;
import de.metas.deliveryplanning.process.M_Delivery_Planning_AddToDeliveryInstruction;
import de.metas.deliveryplanning.process.M_Delivery_Planning_MoveToDeliveryInstruction;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.I_AD_Val_Rule_Included;
import org.compiere.model.I_M_Delivery_Planning;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Generates / regenerates the delivery instruction ({@code M_ShipperTransportation}) for a delivery planning, puts
 * plannings on one, moves them between two and takes them off one, and drives its Complete / Re-Activate / Void
 * document actions.
 */
@RequiredArgsConstructor
public class M_Delivery_Instruction_StepDef
{
	/** every column {@link #combine_M_Delivery_Planning(DataTable)} understands besides the rejection expectations */
	private static final ImmutableSet<String> COMBINE_COLUMNS = ImmutableSet.of(
			I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID, "IsComplete");
	/**
	 * Every column the two target-instruction steps (add / move) understand besides the rejection expectations -
	 * one constant, because the two take the same table: a selection and a target.
	 */
	private static final ImmutableSet<String> TARGET_INSTRUCTION_COLUMNS = ImmutableSet.of(
			I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID);
	/** every column {@link #remove_M_Delivery_Planning_from_M_ShipperTransportation(DataTable)} understands besides the rejection expectations */
	private static final ImmutableSet<String> REMOVE_FROM_COLUMNS = ImmutableSet.of(
			I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID);

	@NonNull private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	@NonNull private final DeliveryPlanningRejectionHelper rejectionHelper;

	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	/**
	 * Generates one delivery instruction ({@code M_ShipperTransportation}) for the given delivery planning, via
	 * {@link DeliveryPlanningService#generateDeliveryInstructions(IQueryFilter, boolean)} - the same code path the
	 * {@code M_Delivery_Planning_GenerateDeliveryInstruction} process drives.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning to generate for<br>
	 *   <b>M_ShipperTransportation_ID</b> — (required, identifier-ref) alias to store the generated instruction under<br>
	 *   <b>IsComplete</b> — (optional) complete the generated instruction right away instead of leaving it a draft;
	 *   defaults to {@code false}, mirroring the process parameter's default: a draft is the default<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When generate M_ShipperTransportation for M_Delivery_Planning:
	 *   | M_ShipperTransportation_ID | M_Delivery_Planning_ID | IsComplete |
	 *   | deliveryInstruction        | deliveryPlanning       | true       |
	 * </pre>
	 */
	@And("generate M_ShipperTransportation for M_Delivery_Planning:")
	public void generate_Delivery_Instructions(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);

			final boolean isComplete = row.getAsOptionalBoolean("IsComplete").orElseFalse();
			deliveryPlanningService.generateDeliveryInstructions(getQueryFilterFor(deliveryPlanning), isComplete);

			InterfaceWrapperHelper.refresh(deliveryPlanning);

			assertThat(deliveryPlanning.getM_ShipperTransportation_ID()).isNotZero();
			final I_M_ShipperTransportation deliveryInstruction = load(deliveryPlanning.getM_ShipperTransportation_ID(), I_M_ShipperTransportation.class);

			row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID).putOrReplace(deliveryInstructionTable, deliveryInstruction);
		});
	}

	/**
	 * Regenerates the delivery instruction ({@code M_ShipperTransportation}) for the given delivery planning, via
	 * {@link DeliveryPlanningService#regenerateDeliveryInstructions(IQueryFilter)}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning to regenerate for<br>
	 *   <b>M_ShipperTransportation_ID</b> — (required, identifier-ref) alias to store the regenerated instruction under<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When regenerate M_ShipperTransportation for M_Delivery_Planning:
	 *   | M_ShipperTransportation_ID     | M_Delivery_Planning_ID |
	 *   | deliveryInstructionRegenerated | deliveryPlanning       |
	 * </pre>
	 */
	@And("regenerate M_ShipperTransportation for M_Delivery_Planning:")
	public void regenerate_Delivery_Instructions(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row ->
		{
			final I_M_Delivery_Planning deliveryPlanning = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID).lookupNotNullIn(deliveryPlanningTable);

			deliveryPlanningService.regenerateDeliveryInstructions(getQueryFilterFor(deliveryPlanning));

			InterfaceWrapperHelper.refresh(deliveryPlanning);

			assertThat(deliveryPlanning.getM_ShipperTransportation_ID()).isNotZero();
			final I_M_ShipperTransportation deliveryInstruction = load(deliveryPlanning.getM_ShipperTransportation_ID(), I_M_ShipperTransportation.class);

			row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID).putOrReplace(deliveryInstructionTable, deliveryInstruction);
		});
	}

	/**
	 * Combines the given delivery plannings into ONE delivery instruction, via
	 * {@link DeliveryPlanningService#combine(IQueryFilter, boolean)} - the same code path the
	 * {@code M_Delivery_Planning_CombineIntoDeliveryInstruction} process drives.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) comma-separated aliases of the plannings to combine<br>
	 *   <b>M_ShipperTransportation_ID</b> — (optional, identifier-ref) alias to store the created instruction
	 *   under; omitted when a rejection is expected<br>
	 *   <b>IsComplete</b> — (optional, default {@code false}) complete the instruction right away instead of
	 *   leaving it a draft, mirroring the process parameter's default<br>
	 *   <b>ErrorAdMessage</b> — (optional) when set, the action is expected to be REJECTED with this
	 *   {@code AD_Message}<br>
	 *   <b>ErrorFields</b> — (optional) comma-separated {@link AggregationKeyField} names the rejection message
	 *   has to name, all of them, in ONE message<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When combine M_Delivery_Planning into one M_ShipperTransportation:
	 *   | M_ShipperTransportation_ID | M_Delivery_Planning_ID                                          |
	 *   | deliveryInstruction        | deliveryPlanning_1,deliveryPlanning_2,deliveryPlanning_3        |
	 * </pre>
	 */
	@When("combine M_Delivery_Planning into one M_ShipperTransportation:")
	public void combine_M_Delivery_Planning(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final IQueryFilter<I_M_Delivery_Planning> selectionFilter = getQueryFilterFor(row);
			final boolean isComplete = row.getAsOptionalBoolean("IsComplete").orElseFalse();

			rejectionHelper.runExpectingRejectionIfAny(row, COMBINE_COLUMNS, () -> {
				final ShipperTransportationId deliveryInstructionId = deliveryPlanningService.combine(selectionFilter, isComplete);

				row.getAsOptionalIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID)
						.ifPresent(identifier -> identifier.putOrReplace(deliveryInstructionTable, load(deliveryInstructionId, I_M_ShipperTransportation.class)));
			});
		});
	}

	/**
	 * Puts the given delivery plannings - which must be on NO delivery instruction yet - on an EXISTING draft one,
	 * via {@link DeliveryPlanningService#addTo(IQueryFilter, ShipperTransportationId)} - the same code path the
	 * {@code M_Delivery_Planning_AddToDeliveryInstruction} process drives. Re-booking a planning that is already
	 * allocated is {@link #move_M_Delivery_Planning_to_M_ShipperTransportation(DataTable)}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) comma-separated aliases of the plannings to add<br>
	 *   <b>M_ShipperTransportation_ID</b> — (required, identifier-ref) the TARGET delivery instruction<br>
	 *   <b>ErrorAdMessage</b> — (optional) when set, the action is expected to be REJECTED with this {@code AD_Message}<br>
	 *   <b>ErrorFields</b> — (optional) comma-separated {@link AggregationKeyField} names the rejection has to name<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When add M_Delivery_Planning to M_ShipperTransportation:
	 *   | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
	 *   | deliveryInstruction_target | deliveryPlanning_2     |
	 * </pre>
	 */
	@When("add M_Delivery_Planning to M_ShipperTransportation:")
	public void add_M_Delivery_Planning_to_M_ShipperTransportation(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final IQueryFilter<I_M_Delivery_Planning> selectionFilter = getQueryFilterFor(row);

			final I_M_ShipperTransportation targetDeliveryInstruction = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID).lookupNotNullIn(deliveryInstructionTable);
			final ShipperTransportationId targetDeliveryInstructionId = ShipperTransportationId.ofRepoId(targetDeliveryInstruction.getM_ShipperTransportation_ID());

			rejectionHelper.runExpectingRejectionIfAny(row, TARGET_INSTRUCTION_COLUMNS, () -> deliveryPlanningService.addTo(selectionFilter, targetDeliveryInstructionId));
		});
	}

	/**
	 * Moves the given delivery plannings - which must already be on a draft delivery instruction - to another draft
	 * one, via {@link DeliveryPlanningService#moveTo(IQueryFilter, ShipperTransportationId)} - the same code path
	 * the {@code M_Delivery_Planning_MoveToDeliveryInstruction} process drives.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) comma-separated aliases of the plannings to move<br>
	 *   <b>M_ShipperTransportation_ID</b> — (required, identifier-ref) the TARGET delivery instruction<br>
	 *   <b>ErrorAdMessage</b> — (optional) when set, the action is expected to be REJECTED with this {@code AD_Message}<br>
	 *   <b>ErrorFields</b> — (optional) comma-separated {@link AggregationKeyField} names the rejection has to name<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When move M_Delivery_Planning to M_ShipperTransportation:
	 *   | M_ShipperTransportation_ID | M_Delivery_Planning_ID |
	 *   | deliveryInstruction_target | deliveryPlanning_2     |
	 * </pre>
	 */
	@When("move M_Delivery_Planning to M_ShipperTransportation:")
	public void move_M_Delivery_Planning_to_M_ShipperTransportation(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final IQueryFilter<I_M_Delivery_Planning> selectionFilter = getQueryFilterFor(row);

			final I_M_ShipperTransportation targetDeliveryInstruction = row.getAsIdentifier(I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID).lookupNotNullIn(deliveryInstructionTable);
			final ShipperTransportationId targetDeliveryInstructionId = ShipperTransportationId.ofRepoId(targetDeliveryInstruction.getM_ShipperTransportation_ID());

			rejectionHelper.runExpectingRejectionIfAny(row, TARGET_INSTRUCTION_COLUMNS, () -> deliveryPlanningService.moveTo(selectionFilter, targetDeliveryInstructionId));
		});
	}

	/**
	 * Takes the given delivery plannings off the draft delivery instruction they are on, via
	 * {@link DeliveryPlanningService#removeFrom(IQueryFilter)} - the same code path the
	 * {@code M_Delivery_Planning_RemoveFromDeliveryInstruction} process drives. Which instruction a planning
	 * leaves is not a parameter: it is the one it is on.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) comma-separated aliases of the plannings to remove<br>
	 *   <b>ErrorAdMessage</b> — (optional) when set, the action is expected to be REJECTED with this {@code AD_Message}<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When remove M_Delivery_Planning from M_ShipperTransportation:
	 *   | M_Delivery_Planning_ID |
	 *   | deliveryPlanning_2     |
	 * </pre>
	 */
	@When("remove M_Delivery_Planning from M_ShipperTransportation:")
	public void remove_M_Delivery_Planning_from_M_ShipperTransportation(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final IQueryFilter<I_M_Delivery_Planning> selectionFilter = getQueryFilterFor(row);

			rejectionHelper.runExpectingRejectionIfAny(row, REMOVE_FROM_COLUMNS, () -> deliveryPlanningService.removeFrom(selectionFilter));
		});
	}

	/**
	 * Drives a document action on the delivery instruction - the same {@code Complete} / {@code Re-Activate} /
	 * {@code Void} buttons a planner presses on the instruction's own window, which is what makes the allocations
	 * follow (or stay put).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the M_ShipperTransportation identified by deliveryInstruction is voided
	 * </pre>
	 */
	@When("^the M_ShipperTransportation identified by (.*) is (completed|reactivated|voided)$")
	public void deliveryInstruction_docAction(@NonNull final String deliveryInstructionIdentifier, @NonNull final String action)
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);

		processDeliveryInstruction(deliveryInstruction, StepDefDocAction.valueOf(action));

		InterfaceWrapperHelper.refresh(deliveryInstruction);
	}

	/**
	 * Presses {@code Complete} on the delivery instruction expecting it to be REFUSED, and asserts which rejection
	 * came back.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>ErrorAdMessage</b> — (optional) the {@code AD_Message} the completion is expected to be rejected with<br>
	 *   <b>ErrorMessage</b> — (optional) the raw rejection text, {@code @token@}s included<br>
	 * @cucumber.depends StepDefData: M_ShipperTransportation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When completing the M_ShipperTransportation identified by deliveryInstruction is refused:
	 *   | ErrorAdMessage                                                              |
	 *   | de.metas.deliveryplanning.CompleteDeliveryInstruction.EmptyDeliveryInstruction |
	 * </pre>
	 */
	@When("^completing the M_ShipperTransportation identified by (.*) is refused:$")
	public void completing_deliveryInstruction_is_refused(@NonNull final String deliveryInstructionIdentifier, @NonNull final DataTable dataTable)
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstructionTable.get(deliveryInstructionIdentifier);

		rejectionHelper.runExpectingRejectionIfAny(
				DataTableRows.of(dataTable).singleRow(),
				ImmutableSet.of(),
				() -> processDeliveryInstruction(deliveryInstruction, StepDefDocAction.completed));

		InterfaceWrapperHelper.refresh(deliveryInstruction);
	}

	/**
	 * Asserts the Application-Dictionary half of the lockstep {@link AggregationKeyField} declares: on BOTH the Add-to
	 * and the Move-to process, every key field is carried by a process parameter AND compared by the target
	 * parameter's value rule. A field added to the enum but not here leaves the picker offering targets the selection
	 * then refuses - the defect that filtering was introduced to remove.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the Add-to and Move-to target pickers filter on every aggregation key field
	 * </pre>
	 */
	@Then("the Add-to and Move-to target pickers filter on every aggregation key field")
	public void validate_target_pickers_filter_on_every_aggregation_key_field()
	{
		final SoftAssertions softly = new SoftAssertions();

		for (final Class<?> processClass : ImmutableList.of(M_Delivery_Planning_AddToDeliveryInstruction.class, M_Delivery_Planning_MoveToDeliveryInstruction.class))
		{
			final String classname = processClass.getName();
			final String valRuleCode = getTargetParameterValRuleCode(classname);

			for (final AggregationKeyField field : AggregationKeyField.values())
			{
				final String columnName = getParameterColumnName(field);

				softly.assertThat(AD_Process_Para_StepDef.getProcessParaOrNull(classname, columnName))
						.as("%s carries a parameter for the %s aggregation key field", classname, field)
						.isNotNull();

				softly.assertThat(valRuleCode)
						.as("the target picker of %s compares the delivery instruction's %s - the %s aggregation key field", classname, columnName, field)
						.contains(I_M_ShipperTransportation.Table_Name + "." + columnName);

				softly.assertThat(valRuleCode)
						.as("the target picker of %s reads the %s parameter - the %s aggregation key field", classname, columnName, field)
						.contains("@" + columnName + "/");
			}
		}

		softly.assertAll();
	}

	/**
	 * The parameter a key field's value arrives in. Every field has one of its own, except
	 * {@link AggregationKeyField#Direction}, which is fed by the TransportDirection parameter both processes already
	 * had before the key fields were added.
	 */
	@NonNull
	private static String getParameterColumnName(@NonNull final AggregationKeyField field)
	{
		if (field == AggregationKeyField.Direction)
		{
			return I_M_Delivery_Planning.COLUMNNAME_TransportDirection;
		}

		final String columnName = DeliveryPlanningProcessHelper.aggregationKeyParameterColumnNameByField().get(field);
		assertThat(columnName)
				.as("the %s aggregation key field is carried by a hidden process parameter", field)
				.isNotNull();
		return columnName;
	}

	/**
	 * The value rule narrowing the given process's target-instruction parameter, flattened: a composite carries no
	 * {@code Code} of its own, only the rules it includes.
	 */
	@NonNull
	private String getTargetParameterValRuleCode(@NonNull final String classname)
	{
		final I_AD_Process_Para targetParameter = AD_Process_Para_StepDef.getProcessParaOrNull(classname, I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID);
		assertThat(targetParameter)
				.as("the target-instruction parameter of %s", classname)
				.isNotNull();
		assertThat(targetParameter.getAD_Val_Rule_ID())
				.as("the target-instruction parameter of %s is narrowed by a value rule", classname)
				.isNotZero();

		return getValRuleCodeIncludingIncluded(targetParameter.getAD_Val_Rule_ID());
	}

	@NonNull
	private String getValRuleCodeIncludingIncluded(final int adValRuleId)
	{
		final StringBuilder code = new StringBuilder(CoalesceUtil.coalesceNotNull(load(adValRuleId, I_AD_Val_Rule.class).getCode(), ""));

		queryBL.createQueryBuilder(I_AD_Val_Rule_Included.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Val_Rule_Included.COLUMNNAME_AD_Val_Rule_ID, adValRuleId)
				.create()
				.stream()
				.forEach(included -> code.append("\n").append(getValRuleCodeIncludingIncluded(included.getIncluded_Val_Rule_ID())));

		return code.toString();
	}

	private void processDeliveryInstruction(@NonNull final I_M_ShipperTransportation deliveryInstruction, @NonNull final StepDefDocAction action)
	{
		switch (action)
		{
			case completed:
				documentBL.processEx(deliveryInstruction, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				break;
			case reactivated:
				// a re-activated document lands In Progress, not back in Drafted
				documentBL.processEx(deliveryInstruction, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);
				break;
			case voided:
				documentBL.processEx(deliveryInstruction, IDocument.ACTION_Void, IDocument.STATUS_Voided);
				break;
			default:
				throw new AdempiereException("Unsupported action for M_ShipperTransportation: " + action);
		}
	}

	/**
	 * The grid selection the three aggregation actions receive: the plannings named in the row's
	 * {@code M_Delivery_Planning_ID} column, which is a comma-separated identifier list because these actions
	 * act on a MULTI-row selection.
	 */
	@NonNull
	private IQueryFilter<I_M_Delivery_Planning> getQueryFilterFor(@NonNull final DataTableRow row)
	{
		final ImmutableList<Integer> deliveryPlanningIds = row.getAsIdentifierList(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID)
				.stream()
				.map(identifier -> identifier.lookupNotNullIn(deliveryPlanningTable))
				.map(I_M_Delivery_Planning::getM_Delivery_Planning_ID)
				.collect(ImmutableList.toImmutableList());

		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addInArrayFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningIds);
	}

	@NonNull
	private IQueryFilter<I_M_Delivery_Planning> getQueryFilterFor(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanning.getM_Delivery_Planning_ID());
	}
}
