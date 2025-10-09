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
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.OrderPayScheduleLine;
import de.metas.order.paymentschedule.OrderPayScheduleService;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.model.I_C_PaymentTerm_Break;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_OrderPaySchedule_StepDef
{

	private final @NonNull C_Order_StepDefData orderTable;

	private final @NonNull C_PaymentTerm_Break_StepDefData paymentTermBreakTable;
	private final @NonNull OrderPayScheduleService orderPayScheduleService;

	@Then("The order pay schedules were created :")
	public void verifyOrderPaySchedules(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID)
				.forEach(this::verifyOrderPaySchedule);

	}

	private void verifyOrderPaySchedule(@NonNull final DataTableRow tableRow)
	{

		final OrderId orderId = tableRow.getAsOptionalIdentifier(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID)
				.map(orderTable::getId)
				.orElseThrow(() -> new AdempiereException(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID + " is mandatory"));

		final OrderPaySchedule paySchedule = orderPayScheduleService.getByOrderId(orderId).orElse(null);

		assertThat(paySchedule).isNotNull();

		final List<OrderPayScheduleLine> actualLines = paySchedule.getLines();

		final PaymentTermBreakId expectedPaymentTermBreakId = tableRow.getAsOptionalIdentifier(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_Break_ID)
				.map(paymentTermBreakTable::getId)
				.orElseThrow(() -> new AdempiereException(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_Break_ID + " is mandatory for line verification"));

		final Optional<OrderPayScheduleLine> actualLine = actualLines.stream()
				.filter(line -> line.getPaymentTermBreakId().equals(expectedPaymentTermBreakId))
				.findFirst();

		assertThat(actualLine).isPresent();

		final BigDecimal expectedDueAmt = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_C_OrderPaySchedule.COLUMNNAME_DueAmt);
		final Timestamp expectedDueDate = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_C_OrderPaySchedule.COLUMNNAME_DueDate);
		final String expectedStatus = DataTableUtil.extractStringForColumnName(tableRow, I_C_OrderPaySchedule.COLUMNNAME_Status);

		// Assertions

		assertThat(actualLine.get().getDueAmount().toBigDecimal())
				.as("Due amount mismatch for line based on C_PaymentTerm_Break_ID: %s", expectedPaymentTermBreakId)
				.isEqualByComparingTo(expectedDueAmt);

		assertThat(actualLine.get().getDueDate())
				.as("Due date mismatch for line based on C_PaymentTerm_Break_ID: %s", expectedPaymentTermBreakId)
				.isEqualTo(expectedDueDate.toInstant());

		assertThat(actualLine.get().getOrderPayScheduleStatus().getCode())
				.as("Status mismatch for line based on C_PaymentTerm_Break_ID: %s", expectedPaymentTermBreakId)
				.isEqualTo(expectedStatus);

	}
}
