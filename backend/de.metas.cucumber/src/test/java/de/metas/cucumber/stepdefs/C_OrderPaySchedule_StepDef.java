/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.paymentterm.C_PaymentTerm_Break_StepDefData;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.OrderPayScheduleLine;
import de.metas.order.paymentschedule.OrderPayScheduleService;
import de.metas.order.paymentschedule.OrderPayScheduleStatus;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.model.I_C_PaymentTerm_Break;

import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_Order_ID;

@RequiredArgsConstructor
public class C_OrderPaySchedule_StepDef
{

	private final @NonNull C_Order_StepDefData orderTable;

	private final @NonNull C_PaymentTerm_Break_StepDefData paymentTermBreakTable;
	private final @NonNull OrderPayScheduleService orderPayScheduleService;
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@Then("The order pay schedules were created:")
	public void verifyOrderPaySchedules(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID)
				.forEach(this::verifyOrderPaySchedule);

	}

	private void verifyOrderPaySchedule(@NonNull final DataTableRow tableRow)
	{
		final OrderId orderId = tableRow.getAsIdentifier(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID).lookupNotNullIdIn(orderTable);
		final OrderPaySchedule paySchedule = orderPayScheduleService.getByOrderId(orderId).orElseThrow(() -> new AdempiereException("No pay schedule found for orderId: " + orderId));

		final PaymentTermBreakId paymentTermBreakId = tableRow.getAsIdentifier(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_Break_ID).lookupNotNullIdIn(paymentTermBreakTable);
		final OrderPayScheduleLine payScheduleLine = paySchedule.getLineByPaymentTermBreakId(paymentTermBreakId);

		final SoftAssertions softly = new SoftAssertions();

		tableRow.getAsOptionalBigDecimal(I_C_OrderPaySchedule.COLUMNNAME_DueAmt)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueAmount().toBigDecimal()).as("DueAmt").isEqualByComparingTo(expected));
		tableRow.getAsOptionalInstant(I_C_OrderPaySchedule.COLUMNNAME_DueDate)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueDate()).as("DueDate").isEqualTo(expected));
		tableRow.getAsOptionalEnum(I_C_OrderPaySchedule.COLUMNNAME_Status, OrderPayScheduleStatus.class)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getOrderPayScheduleStatus()).as("Status").isEqualTo(expected));

		softly.assertAll();
	}

	@When("update C_Order:")
	public void update_c_order(io.cucumber.datatable.DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_C_Order_ID)
				.forEach(this::updateLC_Date);

	}

	private void updateLC_Date(@NonNull final DataTableRow tableRow)
	{
		final OrderId orderId = tableRow.getAsIdentifier().lookupNotNullIdIn(orderTable);
		final I_C_Order order = orderBL.getById(orderId);

		tableRow.getAsOptionalInstant(I_C_Order.COLUMNNAME_LC_Date)
				.ifPresent(expected -> order.setLC_Date(Timestamp.from(expected)));
		saveRecord(order);

		orderTable.putOrReplace(tableRow.getAsIdentifier(), order);

	}

	@Then("The order pay schedules table contains only the following records:")
	public void the_order_pay_schedules_table_contains_only_the_following_records(io.cucumber.datatable.DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID)
				.forEach(this::verifyOrderPaySchedule);
	}
}
