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

import de.metas.cucumber.stepdefs.DataTableUtil;
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

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
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

	@And("generate M_ShipperTransportation for M_Delivery_Planning:")
	public void generate_Delivery_Instructions(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String deliveryPlanningIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Delivery_Planning deliveryPlanning = deliveryPlanningTable.get(deliveryPlanningIdentifier);
			assertThat(deliveryPlanning).isNotNull();

			deliveryPlanningService.generateDeliveryInstructions(getQueryFilterFor(deliveryPlanning));

			InterfaceWrapperHelper.refresh(deliveryPlanning);

			assertThat(deliveryPlanning.getM_ShipperTransportation_ID()).isNotZero();
			final I_M_ShipperTransportation deliveryInstruction = load(deliveryPlanning.getM_ShipperTransportation_ID(), I_M_ShipperTransportation.class);

			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			deliveryInstructionTable.putOrReplace(deliveryInstructionIdentifier, deliveryInstruction);
		}
	}

	@And("regenerate M_ShipperTransportation for M_Delivery_Planning:")
	public void regenerate_Delivery_Instructions(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String deliveryPlanningIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Delivery_Planning deliveryPlanning = deliveryPlanningTable.get(deliveryPlanningIdentifier);
			assertThat(deliveryPlanning).isNotNull();

			deliveryPlanningService.regenerateDeliveryInstructions(getQueryFilterFor(deliveryPlanning));

			InterfaceWrapperHelper.refresh(deliveryPlanning);

			assertThat(deliveryPlanning.getM_ShipperTransportation_ID()).isNotZero();
			final I_M_ShipperTransportation deliveryInstruction = load(deliveryPlanning.getM_ShipperTransportation_ID(), I_M_ShipperTransportation.class);

			final String deliveryInstructionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Delivery_Planning.COLUMNNAME_M_ShipperTransportation_ID + "." + TABLECOLUMN_IDENTIFIER);
			deliveryInstructionTable.putOrReplace(deliveryInstructionIdentifier, deliveryInstruction);
		}
	}

	@NonNull
	private IQueryFilter<I_M_Delivery_Planning> getQueryFilterFor(@NonNull final I_M_Delivery_Planning deliveryPlanning)
	{
		return queryBL.createCompositeQueryFilter(I_M_Delivery_Planning.class)
				.addEqualsFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanning.getM_Delivery_Planning_ID());
	}
}
