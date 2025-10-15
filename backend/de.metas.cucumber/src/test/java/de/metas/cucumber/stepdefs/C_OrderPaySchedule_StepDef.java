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

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.paymentterm.C_PaymentTerm_Break_StepDefData;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.order.paymentschedule.OrderPayScheduleLine;
import de.metas.order.paymentschedule.OrderPayScheduleService;
import de.metas.order.paymentschedule.OrderPayScheduleStatus;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.model.I_C_PaymentTerm_Break;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor
public class C_OrderPaySchedule_StepDef
{
	private final @NonNull C_Order_StepDefData orderTable;
	private final @NonNull C_PaymentTerm_Break_StepDefData paymentTermBreakTable;
	private final @NonNull OrderPayScheduleService orderPayScheduleService;

	@And("^the order identified by (.*) has following pay schedules$")
	public void verifyOrderPaySchedules(@NonNull final String orderIdentifier, @NonNull final DataTable dataTable)
	{
		final OrderId orderId = orderTable.getId(orderIdentifier);
		final OrderPaySchedule paySchedule = orderPayScheduleService.getByOrderId(orderId).orElseThrow(() -> new AdempiereException("No pay schedule found for orderId: " + orderId));
		
		final HashSet<OrderPayScheduleId> expectedIds = new HashSet<>();

		DataTableRows.of(dataTable)
				.forEach(row -> {
					final PaymentTermBreakId paymentTermBreakId = row.getAsIdentifier(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_Break_ID).lookupNotNullIdIn(paymentTermBreakTable);
					final OrderPayScheduleLine payScheduleLine = paySchedule.getLineByPaymentTermBreakId(paymentTermBreakId);
					verifyOrderPaySchedule(row, payScheduleLine);
					expectedIds.add(payScheduleLine.getId());
				});

		final ImmutableSet<OrderPayScheduleId> actualIds = paySchedule.getLines().stream().map(OrderPayScheduleLine::getId).collect(ImmutableSet.toImmutableSet());
		assertThat(actualIds).as("actual pay schedule IDs").isEqualTo(expectedIds);
	}

	private void verifyOrderPaySchedule(@NonNull final DataTableRow row, @NonNull final OrderPayScheduleLine payScheduleLine)
	{
		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalBigDecimal(I_C_OrderPaySchedule.COLUMNNAME_DueAmt)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueAmount().toBigDecimal()).as("DueAmt").isEqualByComparingTo(expected));
		row.getAsOptionalInstant(I_C_OrderPaySchedule.COLUMNNAME_DueDate)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueDate()).as("DueDate").isEqualTo(expected));
		row.getAsOptionalEnum(I_C_OrderPaySchedule.COLUMNNAME_Status, OrderPayScheduleStatus.class)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getStatus()).as("Status").isEqualTo(expected));

		softly.assertAll();
	}
}
