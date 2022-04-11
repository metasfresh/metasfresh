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

package de.metas.cucumber.stepdefs.productionorder;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.material.dispo.service.simulation.ProductionSimulationService;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.order.OrderLineId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import java.util.List;
import java.util.Map;

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
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			invokeSimulation(tableRow);
		}
	}

	private void invokeSimulation(@NonNull final Map<String, String> tableRow)
	{
		final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_OrderLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_C_Order orderRecord = orderTable.get(orderIdentifier);
		final I_C_OrderLine orderLineRecord = orderLineTable.get(orderLineIdentifier);

		final OrderLineId orderLineId = OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID());

		final OrderLineDescriptor orderLineDescriptor = OrderLineDescriptor.builder()
				.orderId(orderRecord.getC_Order_ID())
				.orderLineId(orderLineId.getRepoId())
				.orderBPartnerId(orderRecord.getC_BPartner_ID())
				.docTypeId(orderRecord.getC_DocType_ID())
				.build();

		productionSimulationService.processSimulatedDemand(orderLineId, orderRecord, orderLineDescriptor);
	}
}