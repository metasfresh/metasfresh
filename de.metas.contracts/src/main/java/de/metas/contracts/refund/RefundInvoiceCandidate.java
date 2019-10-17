package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.compiere.model.I_C_UOM;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.Money;
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
 * Represents the invoice candidate that will end up as "refund" invoice line, based on a refund contract.
 * Also see {@link AssignableInvoiceCandidate}.
 */
@Value
@Builder(toBuilder = true)
public class RefundInvoiceCandidate
{
	/** May be {@code null} is the candidate is not persisted */
	@Nullable
	InvoiceCandidateId id;

	@NonNull
	BPartnerId bpartnerId;

	@NonNull
	BPartnerLocationId bpartnerLocationId;

	@NonNull
	LocalDate invoiceableFrom;

	@NonNull
	RefundContract refundContract;

	/**
	 * If {@link RefundMode} is {@link RefundMode#APPLY_TO_EXCEEDING_QTY}, then there is one config per candidate; if it is {@link RefundMode#APPLY_TO_ALL_QTIES}, then there is one or many.
	 */
	@NonNull
	@Singular
	List<RefundConfig> refundConfigs;

	@NonNull
	Money money;

	/** The sum of the quantities of all assigned {@link AssignableInvoiceCandidate}s. */
	@NonNull
	transient Quantity assignedQuantity;

	/** Computes how much can still be assigned to this candidate, according to the given config. */
	public Quantity computeAssignableQuantity(@NonNull final RefundConfig refundCondig)
	{
		final Optional<RefundConfig> nextRefundConfig = getRefundContract()
				.getRefundConfigs()
				.stream()
				.filter(config -> config.getMinQty().compareTo(refundCondig.getMinQty()) > 0)
				.min(Comparator.comparing(RefundConfig::getMinQty));

		final I_C_UOM uomRecord = assignedQuantity.getUOM();

		if (!nextRefundConfig.isPresent())
		{
			return Quantity.infinite(uomRecord);
		}

		return Quantity
				.of(
						nextRefundConfig.get().getMinQty(),
						uomRecord)
				.subtract(getAssignedQuantity())
				.subtract(ONE)
				.max(Quantity.zero(uomRecord));
	}

	public RefundInvoiceCandidate subtractAssignment(@NonNull final AssignmentToRefundCandidate assignmentToRefundCandidate)
	{
		Check.assume(assignmentToRefundCandidate.getRefundInvoiceCandidate().getId().equals(getId()),
				"The given assignmentToRefundCandidate needs to be an assignment to this refund candidate; this={}, assignmentToRefundCandidate={}",
				this, assignmentToRefundCandidate);

		final RefundInvoiceCandidateBuilder builder = toBuilder();

		final Money moneySubtrahent = assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate();
		final Money newMoneyAmount = getMoney().subtract(moneySubtrahent);
		builder.money(newMoneyAmount);

		if (assignmentToRefundCandidate.isUseAssignedQtyInSum())
		{
			final Quantity assignedQuantitySubtrahent = assignmentToRefundCandidate.getQuantityAssigendToRefundCandidate();
			final Quantity newQuantity = getAssignedQuantity().subtract(assignedQuantitySubtrahent);

			builder.assignedQuantity(newQuantity);
		}

		return builder.build();
	}
}
