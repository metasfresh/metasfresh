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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import de.metas.deliveryplanning.DeliveryPlanningList.AdmissibilityField;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Generates / regenerates the delivery instruction ({@code M_ShipperTransportation}) for a delivery planning, moves
 * plannings on and off one, and drives its Complete / Re-Activate / Void document actions.
 * <p>
 * Loading and validating the resulting {@code M_ShipperTransportation} is handled by
 * {@code de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDef}; the two step-defs share the same
 * {@link M_ShipperTransportation_StepDefData} instance (injected by PicoContainer), so a delivery instruction stored
 * here is visible to the validating/loading steps.
 */
@AllArgsConstructor
public class M_Delivery_Instruction_StepDef
{
	/** every column {@link #combine_M_Delivery_Planning(DataTable)} understands besides the rejection expectations */
	private static final ImmutableSet<String> COMBINE_COLUMNS = ImmutableSet.of(
			I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID, "IsComplete");
	/** every column {@link #add_M_Delivery_Planning_to_M_ShipperTransportation(DataTable)} understands besides the rejection expectations */
	private static final ImmutableSet<String> ADD_TO_COLUMNS = ImmutableSet.of(
			I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID);
	/** every column {@link #remove_M_Delivery_Planning_from_M_ShipperTransportation(DataTable)} understands besides the rejection expectations */
	private static final ImmutableSet<String> REMOVE_FROM_COLUMNS = ImmutableSet.of(
			I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID);

	@NonNull private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	@NonNull private final DeliveryPlanningRejectionHelper rejectionHelper;

	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

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
	 * {@code M_Delivery_Planning_CombineIntoDeliveryInstruction} process drives, that process being a thin
	 * adapter that only forwards the grid selection and the completion flag.
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
	 *   <b>ErrorFields</b> — (optional) comma-separated {@link AdmissibilityField} names the rejection message
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
	 * Puts the given delivery plannings on an EXISTING draft delivery instruction, via
	 * {@link DeliveryPlanningService#addTo(IQueryFilter, ShipperTransportationId)} - the same code path the
	 * {@code M_Delivery_Planning_AddToDeliveryInstruction} process drives.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) comma-separated aliases of the plannings to move<br>
	 *   <b>M_ShipperTransportation_ID</b> — (required, identifier-ref) the TARGET delivery instruction<br>
	 *   <b>ErrorAdMessage</b> — (optional) when set, the action is expected to be REJECTED with this {@code AD_Message}<br>
	 *   <b>ErrorFields</b> — (optional) comma-separated {@link AdmissibilityField} names the rejection has to name<br>
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

			rejectionHelper.runExpectingRejectionIfAny(row, ADD_TO_COLUMNS, () -> deliveryPlanningService.addTo(selectionFilter, targetDeliveryInstructionId));
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

	private void processDeliveryInstruction(@NonNull final I_M_ShipperTransportation deliveryInstruction, @NonNull final StepDefDocAction action)
	{
		switch (action)
		{
			case completed:
				documentBL.processEx(deliveryInstruction, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				break;
			case reactivated:
				// a re-activated document lands In Progress, not back in Drafted - DocumentEngine#reActivateIt
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
