package de.metas.contracts.refund.refundConfigChange;

import org.adempiere.util.Check;

import de.metas.contracts.refund.AssignmentToRefundCandidate;
import de.metas.contracts.refund.RefundConfig;
import lombok.NonNull;

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

public class PercentRefundConfigChangeHandler implements RefundConfigChangeHandler
{
	private RefundConfig formerRefundConfig;

	private RefundConfig currentRefundConfig;

	private PercentRefundConfigChangeHandler(@NonNull final RefundConfig currentRefundConfig)
	{
		this.currentRefundConfig = currentRefundConfig;
		this.formerRefundConfig = null;
	}

	@Override
	public void currentRefundConfig(@NonNull final RefundConfig refundConfig)
	{
		this.formerRefundConfig = this.currentRefundConfig;
		this.currentRefundConfig = refundConfig;
	}

	@Override
	public RefundConfig getFormerRefundConfig()
	{
		return Check.assumeNotNull(formerRefundConfig, "formerRefundConfig may not be null; invoke the currentRefundConfig() method first; this={}", this);
	}

	@Override
	public AssignmentToRefundCandidate createNewAssignment(@NonNull final AssignmentToRefundCandidate existingAssignment)
	{
		Check.assumeNotNull(formerRefundConfig, "formerRefundConfig may not be null; invoke the currentRefundConfig() method first; this={}", this);
		// note: currentRefundConfig can't be null; no need to assume.

		return new AssignmentToRefundCandidate(
				existingAssignment.getAssignableInvoiceCandidateId(),
				existingAssignment.getRefundInvoiceCandidate(),
				currentRefundConfig.getId(),
				moneyAssignedToRefundCandidate,
				quantityAssigendToRefundCandidate);
	}
}
