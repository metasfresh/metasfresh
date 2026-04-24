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

package de.metas.cucumber.stepdefs.order;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.paymentterm.C_PaymentTerm_Break_StepDefData;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.OrderPayScheduleId;
import de.metas.order.paymentschedule.OrderPayScheduleLine;
import de.metas.order.paymentschedule.OrderPayScheduleStatus;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.model.I_C_PaymentTerm_Break;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Step definitions for {@code C_OrderPaySchedule} / {@link OrderPayScheduleLine} assertions.
 *
 * @see C_OrderPaySchedule_StepDefData
 */
@RequiredArgsConstructor
public class C_OrderPaySchedule_StepDef
{
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_PaymentTerm_Break_StepDefData paymentTermBreakTable;
	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final C_OrderPaySchedule_StepDefData orderPayScheduleTable;

	/**
	 * Verifies the full set of pay-schedule lines for an order, matched by payment-term break identifier.
	 *
	 * <p>Required DataTable columns:
	 * <ul>
	 *   <li>{@code C_PaymentTerm_Break_ID} — identifier of the break (required)</li>
	 * </ul>
	 * Optional DataTable columns:
	 * <ul>
	 *   <li>{@code DueAmt} — planned due amount</li>
	 *   <li>{@code DueDate} — due date</li>
	 *   <li>{@code Status} — {@link OrderPayScheduleStatus} enum name</li>
	 * </ul>
	 *
	 * <pre>{@code
	 * Then the order identified by po has following pay schedules
	 *   | C_PaymentTerm_Break_ID | DueAmt    | Status  |
	 *   | ptb_lc                 | 20596.32  | Pending |
	 *   | ptb_del                | 48058.08  | Pending |
	 * }</pre>
	 */
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

	/**
	 * Verifies pay-schedule lines for an order matched by {@link ReferenceDateType}.
	 * This step is used for split-payment LC scenarios (TC1-TC4) where lines are
	 * identified by their reference-date type rather than a specific break identifier.
	 *
	 * <p>Required DataTable columns:
	 * <ul>
	 *   <li>{@code ReferenceDateType} — enum name: {@code LetterOfCreditDate}, {@code OrderDate}, etc.</li>
	 * </ul>
	 * Optional DataTable columns:
	 * <ul>
	 *   <li>{@code DueAmt} — planned due amount</li>
	 *   <li>{@code Status} — {@link OrderPayScheduleStatus} enum name</li>
	 *   <li>{@code DueAmt_Actual} — actual due amount from the proforma invoice;
	 *       use {@code null} to assert the column is NULL/zero.</li>
	 * </ul>
	 *
	 * <pre>{@code
	 * Then the order identified by po has following pay schedule lines by ReferenceDateType
	 *   | ReferenceDateType    | DueAmt    | Status       | DueAmt_Actual |
	 *   | LetterOfCreditDate   | 20596.32  | Awaiting_Pay | 20596.32      |
	 *   | OrderDate            | 48058.08  | Pending      | null          |
	 * }</pre>
	 */
	@And("^the order identified by (.*) has following pay schedule lines by ReferenceDateType$")
	public void verifyOrderPaySchedulesByRefDateType(@NonNull final String orderIdentifier, @NonNull final DataTable dataTable)
	{
		final OrderId orderId = orderTable.getId(orderIdentifier);
		// Reload from the service each time to get the latest DB state (Status, DueAmt_Actual, etc.)
		final OrderPaySchedule paySchedule = orderPayScheduleService.getByOrderId(orderId)
				.orElseThrow(() -> new AdempiereException("No pay schedule found for orderId: " + orderId));

		DataTableRows.of(dataTable).forEach(row -> {
			final ReferenceDateType referenceDateType = row.getAsOptionalEnum(I_C_OrderPaySchedule.COLUMNNAME_ReferenceDateType, ReferenceDateType.class)
					.orElseThrow(() -> new AdempiereException("Column 'ReferenceDateType' is required"));

			final List<OrderPayScheduleLine> matchingLines = paySchedule.getLines().stream()
					.filter(line -> line.getReferenceDateType() == referenceDateType)
					.collect(Collectors.toList());

			if (matchingLines.isEmpty())
			{
				throw new AdempiereException("No pay schedule line found for ReferenceDateType=" + referenceDateType + " in orderId=" + orderId);
			}
			if (matchingLines.size() > 1)
			{
				throw new AdempiereException("Multiple pay schedule lines found for ReferenceDateType=" + referenceDateType + " in orderId=" + orderId);
			}

			final OrderPayScheduleLine payScheduleLine = matchingLines.get(0);
			verifyOrderPayScheduleExtended(row, payScheduleLine);

			row.getAsOptionalIdentifier()
					.ifPresent(identifier -> orderPayScheduleTable.put(identifier, payScheduleLine));
		});
	}

	private void verifyOrderPaySchedule(@NonNull final DataTableRow row, @NonNull final OrderPayScheduleLine payScheduleLine)
	{
		SharedTestContext.put("payScheduleLine", payScheduleLine);

		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalBigDecimal(I_C_OrderPaySchedule.COLUMNNAME_DueAmt)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueAmount().toBigDecimal()).as("DueAmt").isEqualByComparingTo(expected));
		row.getAsOptionalLocalDate(I_C_OrderPaySchedule.COLUMNNAME_DueDate)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueDate()).as("DueDate").isEqualTo(expected));
		row.getAsOptionalEnum(I_C_OrderPaySchedule.COLUMNNAME_Status, OrderPayScheduleStatus.class)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getStatus()).as("Status").isEqualTo(expected));

		softly.assertAll();

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> orderPayScheduleTable.put(identifier, payScheduleLine));
	}

	/**
	 * Extended verification that additionally supports {@code DueAmt_Actual} (nullable) and
	 * {@code ReferenceDateType} columns.  Used by {@link #verifyOrderPaySchedulesByRefDateType}.
	 */
	private void verifyOrderPayScheduleExtended(@NonNull final DataTableRow row, @NonNull final OrderPayScheduleLine payScheduleLine)
	{
		SharedTestContext.put("payScheduleLine", payScheduleLine);

		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalBigDecimal(I_C_OrderPaySchedule.COLUMNNAME_DueAmt)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueAmount().toBigDecimal()).as("DueAmt").isEqualByComparingTo(expected));
		row.getAsOptionalLocalDate(I_C_OrderPaySchedule.COLUMNNAME_DueDate)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueDate()).as("DueDate").isEqualTo(expected));
		row.getAsOptionalEnum(I_C_OrderPaySchedule.COLUMNNAME_Status, OrderPayScheduleStatus.class)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getStatus()).as("Status").isEqualTo(expected));

		// DueAmt_Actual: "null" in the feature means assert NULL; a numeric value asserts equality.
		row.getAsOptionalString(I_C_OrderPaySchedule.COLUMNNAME_DueAmt_Actual)
				.ifPresent(rawValue -> {
					if (DataTableUtil.NULL_STRING.equals(rawValue))
					{
						final BigDecimal actual = payScheduleLine.getDueAmtActual();
						softly.assertThat(actual == null || BigDecimal.ZERO.compareTo(actual) == 0)
								.as("DueAmt_Actual should be null/zero but was: " + actual)
								.isTrue();
					}
					else
					{
						final BigDecimal expected = new BigDecimal(rawValue);
						softly.assertThat(payScheduleLine.getDueAmtActual()).as("DueAmt_Actual").isNotNull();
						if (payScheduleLine.getDueAmtActual() != null)
						{
							softly.assertThat(payScheduleLine.getDueAmtActual()).as("DueAmt_Actual").isEqualByComparingTo(expected);
						}
					}
				});

		softly.assertAll();
	}
}
