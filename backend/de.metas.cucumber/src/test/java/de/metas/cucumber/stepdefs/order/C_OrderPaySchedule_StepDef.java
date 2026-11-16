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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.paymentterm.C_PaymentTerm_Break_StepDefData;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.inout.InOutId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleId;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.OrderPayScheduleStatus;
import de.metas.order.paymentschedule.core.service.OrderPayScheduleService;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.payment.paymentterm.ReferenceDateType;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_OrderPaySchedule;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for {@code C_OrderPaySchedule} / {@link OrderPayScheduleLine} assertions.
 *
 * @see C_OrderPaySchedule_StepDefData
 */
@RequiredArgsConstructor
public class C_OrderPaySchedule_StepDef
{
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_PaymentTerm_Break_StepDefData paymentTermBreakTable;
	@NonNull private final M_InOut_StepDefData inOutTable;
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
	 *   <li>{@code DueAmt} — planned due amount; use {@code null} to assert the column is NULL.</li>
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
		final DataTableRows rows = DataTableRows.of(dataTable);

		final OrderPaySchedule paySchedule = getOrderPaySchedule(orderIdentifier);
		assertThat(paySchedule.getLines()).hasSize(rows.size());

		final HashSet<OrderPayScheduleId> matchedIds = new HashSet<>();

		rows.forEach(row -> {
			final OrderPayScheduleLine payScheduleLine = findMatchingLine(paySchedule, row, matchedIds);
			verifyOrderPaySchedule(row, payScheduleLine);

			matchedIds.add(payScheduleLine.getIdNotNull());
		});
	}

	/**
	 * Verifies a subset of pay-schedule lines for an order, matched by {@link ReferenceDateType} (and any other
	 * key columns present in the DataTable row). Only the rows listed in the DataTable are asserted; additional
	 * lines in the order's pay schedule are silently ignored. Use this step when a scenario needs to assert
	 * specific lines without caring about the full set (e.g. asserting only the LC line after a payment reversal
	 * while OD lines are irrelevant to the assertion).
	 *
	 * <p>Required DataTable columns (at least one key column required to identify a line):
	 * <ul>
	 *   <li>{@code ReferenceDateType} — DB code ({@code LC} / {@code OD} / {@code IV} / {@code BL} / {@code ET}).
	 *       Resolved via {@code ReferenceListAwareEnums.ofNullableCode} — use the code, not the enum name.</li>
	 * </ul>
	 * Optional DataTable columns:
	 * <ul>
	 *   <li>{@code DueAmt} — planned due amount; use {@code null} to assert the column is NULL.</li>
	 *   <li>{@code Status} — {@link OrderPayScheduleStatus} DB code: {@code PR} (Pending), {@code WP} (Awaiting_Pay), {@code P} (Paid)</li>
	 *   <li>{@code DueAmt_Actual} — actual due amount from the proforma invoice;
	 *       use {@code null} to assert the column is NULL/zero.</li>
	 *   <li>{@code IsPaid} — boolean; whether the line is paid</li>
	 * </ul>
	 *
	 * <pre>{@code
	 * Then the order identified by po has following pay schedule lines by ReferenceDateType
	 *   | ReferenceDateType | DueAmt   | DueAmt_Actual | Status |
	 *   | LC                | 20596.32 | 20596.32      | WP     |
	 * }</pre>
	 */
	@And("^the order identified by (.*) has following pay schedule lines by ReferenceDateType$")
	public void verifyOrderPaySchedulesByRefDateType(@NonNull final String orderIdentifier, @NonNull final DataTable dataTable)
	{
		final DataTableRows rows = DataTableRows.of(dataTable);

		final OrderPaySchedule paySchedule = getOrderPaySchedule(orderIdentifier);
		final HashSet<OrderPayScheduleId> matchedIds = new HashSet<>();

		rows.forEach(row -> {
			final OrderPayScheduleLine line = findMatchingLine(paySchedule, row, matchedIds);
			verifyOrderPaySchedule(row, line);
			matchedIds.add(line.getIdNotNull());
		});
	}

	private OrderPayScheduleLine findMatchingLine(
			@NonNull final OrderPaySchedule paySchedule,
			@NonNull final DataTableRow row,
			@NonNull final HashSet<OrderPayScheduleId> alreadyMatchedIds)
	{
		return paySchedule.streamLines()
				.filter(line -> !alreadyMatchedIds.contains(line.getIdNotNull())) // not already matched
				.filter(extractMatchingLinePredicate(row))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No pay schedule line matching" + row + " in orderId=" + paySchedule.getOrderId()));
	}

	private Predicate<OrderPayScheduleLine> extractMatchingLinePredicate(final DataTableRow row)
	{
		Predicate<OrderPayScheduleLine> resultingPredicate = null;

		final PaymentTermBreakId paymentTermBreakId = row.getAsOptionalIdentifier(I_C_OrderPaySchedule.COLUMNNAME_C_PaymentTerm_Break_ID)
				.map(paymentTermBreakTable::getId)
				.orElse(null);

		if (paymentTermBreakId != null)
		{
			final Predicate<OrderPayScheduleLine> predicate = (line) -> PaymentTermBreakId.equals(line.getPaymentTermBreakId(), paymentTermBreakId);
			//noinspection ConstantValue
			resultingPredicate = resultingPredicate == null ? predicate : resultingPredicate.and(predicate);
		}

		final ReferenceDateType referenceDateType = row.getAsOptionalEnum(I_C_OrderPaySchedule.COLUMNNAME_ReferenceDateType, ReferenceDateType.class).orElse(null);
		if (referenceDateType != null)
		{
			final Predicate<OrderPayScheduleLine> predicate = (line) -> line.getReferenceDateType() == referenceDateType;
			resultingPredicate = resultingPredicate == null ? predicate : resultingPredicate.and(predicate);
		}

		final Optional<StepDefDataIdentifier> inOutIdentifierOpt = row.getAsOptionalIdentifier(I_C_OrderPaySchedule.COLUMNNAME_M_InOut_ID);
		if (inOutIdentifierOpt.isPresent())
		{
			final Predicate<OrderPayScheduleLine> predicate;
			if (inOutIdentifierOpt.get().isNullPlaceholder())
			{
				predicate = (line) -> line.getInoutId() == null;
			}
			else
			{
				final InOutId inOutId = inOutTable.getId(inOutIdentifierOpt.get());
				predicate = (line) -> InOutId.equals(line.getInoutId(), inOutId);
			}
			resultingPredicate = resultingPredicate == null ? predicate : resultingPredicate.and(predicate);
		}

		if (resultingPredicate == null)
		{
			throw new AdempiereException("No matching line predicate found for " + row);
		}

		return resultingPredicate;
	}

	private @NotNull OrderPaySchedule getOrderPaySchedule(final @NotNull String orderIdentifier)
	{
		final OrderId orderId = orderTable.getId(orderIdentifier);
		return orderPayScheduleService.getByOrderId(orderId)
				.orElseThrow(() -> new AdempiereException("No pay schedule found for orderId: " + orderId));
	}

	private void verifyOrderPaySchedule(@NonNull final DataTableRow row, @NonNull final OrderPayScheduleLine payScheduleLine)
	{
		SharedTestContext.put("payScheduleLine", payScheduleLine);

		final SoftAssertions softly = new SoftAssertions();

		row.getAsOptionalString(I_C_OrderPaySchedule.COLUMNNAME_BaseAmt)
				.ifPresent(rawValue -> {
					final Money actual = payScheduleLine.getBaseAmount();
					if (DataTableUtil.isNullPlaceholder(rawValue))
					{
						softly.assertThat(actual).as("BaseAmt should be null").isNull();
					}
					else
					{
						softly.assertThat(actual != null ? actual.toBigDecimal() : null).as("BaseAmt").isEqualByComparingTo(rawValue);
					}
				});
		row.getAsOptionalString(I_C_OrderPaySchedule.COLUMNNAME_DueAmt)
				.ifPresent(rawValue -> {
					final Money actual = payScheduleLine.getDueAmount();
					if (DataTableUtil.isNullPlaceholder(rawValue))
					{
						softly.assertThat(actual).as("DueAmt should be null").isNull();
					}
					else
					{
						softly.assertThat(actual != null ? actual.toBigDecimal() : null).as("DueAmt").isEqualByComparingTo(rawValue);
					}
				});
		row.getAsOptionalString(I_C_OrderPaySchedule.COLUMNNAME_DueAmt_Actual)
				.ifPresent(rawValue -> {
					final Money actual = payScheduleLine.getDueAmountActual();
					if (DataTableUtil.isNullPlaceholder(rawValue))
					{
						softly.assertThat(actual).as("DueAmt_Actual should be null").isNull();
					}
					else
					{
						softly.assertThat(actual != null ? actual.toBigDecimal() : null).as("DueAmt_Actual").isEqualByComparingTo(rawValue);
					}
				});
		row.getAsOptionalString(I_C_OrderPaySchedule.COLUMNNAME_ReferenceDate)
				.ifPresent(rawValue -> {
					if (DataTableUtil.isNullPlaceholder(rawValue))
					{
						softly.assertThat(payScheduleLine.getReferenceDate()).as("ReferenceDate").isNull();
					}
					else
					{
						softly.assertThat(payScheduleLine.getReferenceDate()).as("ReferenceDate").isEqualTo(rawValue);
					}
				});
		row.getAsOptionalLocalDate(I_C_OrderPaySchedule.COLUMNNAME_DueDate)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getDueDate()).as("DueDate").isEqualTo(expected));
		row.getAsOptionalEnum(I_C_OrderPaySchedule.COLUMNNAME_Status, OrderPayScheduleStatus.class)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.getStatus()).as("Status").isEqualTo(expected));
		row.getAsOptionalBoolean(I_C_OrderPaySchedule.COLUMNNAME_IsPaid)
				.ifPresent(expected -> softly.assertThat(payScheduleLine.isPaid()).as("IsPaid").isEqualTo(expected));
		row.getAsOptionalIdentifier(I_C_OrderPaySchedule.COLUMNNAME_C_Invoice_ID)
				.ifPresent(identifier -> {
					if (identifier.isNullPlaceholder())
					{
						softly.assertThat(payScheduleLine.getInvoiceId()).as("C_Invoice_ID").isNull();
					}
					else
					{
						softly.assertThat(payScheduleLine.getInvoiceId()).as("C_Invoice_ID").isEqualTo(invoiceTable.getId(identifier));
					}
				});
		row.getAsOptionalIdentifier(I_C_OrderPaySchedule.COLUMNNAME_M_InOut_ID)
				.ifPresent(identifier -> {
					if (identifier.isNullPlaceholder())
					{
						softly.assertThat(payScheduleLine.getInoutId()).as("M_InOut_ID").isNull();
					}
					else
					{
						softly.assertThat(payScheduleLine.getInoutId()).as("M_InOut_ID").isEqualTo(inOutTable.getId(identifier));
					}
				});

		softly.assertAll();

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> orderPayScheduleTable.putOrReplace(identifier, payScheduleLine));
	}
}
