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

package de.metas.cucumber.stepdefs.pporder;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.material.dispo.service.simulation.ProductionSimulationService;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.order.OrderLineId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;

import static org.compiere.model.I_C_Order.COLUMNNAME_C_Order_ID;
import static org.compiere.model.I_C_OrderLine.COLUMNNAME_C_OrderLine_ID;

public class ProductionSimulation_StepDef
{
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;

	private final ProductionSimulationService productionSimulationService = SpringContextHolder.instance.getBean(ProductionSimulationService.class);

	public ProductionSimulation_StepDef(
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
	}

	@And("create and process 'simulated demand' for:")
	public void invoke_simulation(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::invokeSimulation);
	}

	private void invokeSimulation(@NonNull final DataTableRow tableRow)
	{
		final I_C_Order orderRecord = tableRow.getAsIdentifier(COLUMNNAME_C_Order_ID).lookupIn(orderTable);
		final OrderLineId orderLineId = tableRow.getAsIdentifier(COLUMNNAME_C_OrderLine_ID).lookupIdIn(orderLineTable);

		final OrderLineDescriptor orderLineDescriptor = OrderLineDescriptor.builder()
				.orderId(orderRecord.getC_Order_ID())
				.orderLineId(orderLineId.getRepoId())
				.orderBPartnerId(orderRecord.getC_BPartner_ID())
				.docTypeId(orderRecord.getC_DocType_ID())
				.build();

		productionSimulationService.processSimulatedDemand(orderLineId, orderRecord, orderLineDescriptor);
	}
}