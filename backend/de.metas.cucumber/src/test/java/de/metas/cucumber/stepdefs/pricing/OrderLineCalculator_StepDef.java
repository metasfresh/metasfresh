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

package de.metas.cucumber.stepdefs.pricing;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.impl.OrderLinePriceCalculatorTest;
import de.metas.util.Services;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;

import java.util.List;
import java.util.Map;

public class OrderLineCalculator_StepDef
{
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	private final C_OrderLine_StepDefData orderLineTable;

	public OrderLineCalculator_StepDef(final C_OrderLine_StepDefData orderLineTable)
	{
		this.orderLineTable = orderLineTable;
	}

	@When("update prices for order line")
	public void update_order_line_scale_prices(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);

			final OrderLinePriceUpdateRequest request = OrderLinePriceUpdateRequest.ofOrderLine(orderLine);

			OrderLinePriceCalculatorTest.builder()
					.request(request)
					.orderLineBL(orderLineBL)
					.build()
					.updateOrderLine();

			InterfaceWrapperHelper.refresh(orderLine);

			orderLineTable.putOrReplace(orderLineIdentifier, orderLine);
		}
	}
}
