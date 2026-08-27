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

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDefData;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Generates / regenerates the delivery instruction ({@code M_ShipperTransportation}) for a delivery planning.
 * <p>
 * Loading and validating the resulting {@code M_ShipperTransportation} is handled by
 * {@code de.metas.cucumber.stepdefs.shipment.M_ShipperTransportation_StepDef}; the two step-defs share the same
 * {@link M_ShipperTransportation_StepDefData} instance (injected by PicoContainer), so a delivery instruction stored
 * here is visible to the validating/loading steps.
 */
@AllArgsConstructor
public class M_Delivery_Instruction_StepDef
{
	private final M_ShipperTransportation_StepDefData deliveryInstructionTable;
	private final M_Delivery_Planning_StepDefData deliveryPlanningTable;

	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
	 *   | deliveryInstruction        | deliveryPlanning        | true       |
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
	 *   | M_ShipperTransportation_ID    | M_Delivery_Planning_ID |
	 *   | deliveryInstructionRegenerated | deliveryPlanning        |
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

	@NonNull
	private IQueryFilter<I_M_Delivery_Planning> getQueryFilterFor(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanning.getM_Delivery_Planning_ID());
	}
}
