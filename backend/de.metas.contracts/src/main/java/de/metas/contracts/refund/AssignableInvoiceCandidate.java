package de.metas.contracts.refund;

import static de.metas.common.util.CoalesceUtil.coalesce;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Represents a "normal" invoice candidate (e.g. from a purchase order line) that matches a refund contract and can therefore be assigned to one or more {@link RefundInvoiceCandidate}(s).
 */
@Value
public class AssignableInvoiceCandidate
{
	/**
	 * <li>May be {@code null} if this instance was not persisted.
	 * <li>The result of {@link #splitQuantity(BigDecimal)} can contain multiple instances that have the same id
	 */
	InvoiceCandidateId id;

	BPartnerLocationId bpartnerLocationId;

	/** The partner the goods are shipped to: the order's drop-ship partner, else the order's partner. {@code null} if the candidate has no order. */
	@Nullable
	BPartnerId shipmentBPartnerId;

	/** The sales order line the candidate stems from; {@code null} if it has none. */
	@Nullable
	OrderLineId orderLineId;

	/** The side the candidate is on, sales or purchase. {@code null} if not known: it then matches the refund candidates of both sides. */
	@Nullable
	SOTrx soTrx;

	ProductId productId;
	LocalDate invoiceableFrom;

	Money money;

	/** needed when splitting assignable candidates. */
	int precision;

	/** the underlying record's {@code QtyToInvoice} plus {@code QtyInvoiced}. */
	Quantity quantity;

	/** Like {@link #getQuantity()}, but contains the old quantity, if the underlying record was just changed. */
	Quantity quantityOld;

	/** i there is more than one, they are ordered by their refund candidates' configs' minQty, ascending. */
	List<AssignmentToRefundCandidate> assignmentsToRefundCandidates;

	@Builder(toBuilder = true)
	private AssignableInvoiceCandidate(
			@Nullable final InvoiceCandidateId id,
			@NonNull final BPartnerLocationId bpartnerLocationId,
			@Nullable final BPartnerId shipmentBPartnerId,
			@Nullable final OrderLineId orderLineId,
			@Nullable final SOTrx soTrx,
			@NonNull final ProductId productId,
			@NonNull final LocalDate invoiceableFrom,
			@NonNull final Money money,
			final int precision,
			@NonNull final Quantity quantity,
			@Nullable final Quantity quantityOld,
			@Singular("assignmentToRefundCandidate") final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates)
	{
		this.id = id;
		this.bpartnerLocationId = bpartnerLocationId;
		this.shipmentBPartnerId = shipmentBPartnerId;
		this.orderLineId = orderLineId;
		this.soTrx = soTrx;
		this.productId = productId;
		this.invoiceableFrom = invoiceableFrom;
		this.money = money;
		this.precision = Check.assumeGreaterOrEqualToZero(precision, "precision");
		this.quantity = quantity;
		this.quantityOld = coalesce(quantityOld, quantity);

		this.assignmentsToRefundCandidates = assignmentsToRefundCandidates;
	}

	public AssignableInvoiceCandidate withoutRefundInvoiceCandidates()
	{
		return toBuilder()
				.clearAssignmentsToRefundCandidates()
				.build();
	}

	public boolean isAssigned()
	{
		return !assignmentsToRefundCandidates.isEmpty();
	}

	public SplitResult splitQuantity(@NonNull final BigDecimal qtyToSplit)
	{
		Check.errorIf(qtyToSplit.compareTo(quantity.toBigDecimal()) >= 0,
				"The given qtyToSplit={} needs to be less than this instance's quantity; this={}",
				qtyToSplit, this);

		final Quantity newQuantity = Quantity.of(qtyToSplit, quantity.getUOM());
		final Quantity remainderQuantity = quantity.subtract(qtyToSplit);

		final BigDecimal newFraction = qtyToSplit
				.setScale(precision * 2, RoundingMode.HALF_UP)
				.divide(quantity.toBigDecimal(), RoundingMode.HALF_UP);

		final BigDecimal newMoneyValue = money
				.toBigDecimal()
				.setScale(precision, RoundingMode.HALF_UP)
				.multiply(newFraction)
				.setScale(precision, RoundingMode.HALF_UP);
		final Money newMoney = Money.of(newMoneyValue, money.getCurrencyId());
		final Money remainderMoney = money.subtract(newMoney);

		final AssignableInvoiceCandidate remainderCandidate = toBuilder()
				.quantity(remainderQuantity)
				.money(remainderMoney)
				.build();

		final AssignableInvoiceCandidate newCandidate = toBuilder()
				.id(id)
				.quantity(newQuantity)
				.money(newMoney)
				.build();

		return new SplitResult(remainderCandidate, newCandidate);
	}

	@Value
	public static final class SplitResult
	{
		AssignableInvoiceCandidate remainder;

		AssignableInvoiceCandidate newCandidate;
	}
}
