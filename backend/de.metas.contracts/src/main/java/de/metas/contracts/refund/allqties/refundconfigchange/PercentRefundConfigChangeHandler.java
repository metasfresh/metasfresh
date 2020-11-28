package de.metas.contracts.refund.allqties.refundconfigchange;

import de.metas.contracts.refund.AssignmentToRefundCandidate;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
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

public class PercentRefundConfigChangeHandler extends RefundConfigChangeHandler
{
	public static PercentRefundConfigChangeHandler newInstance(
			@NonNull final MoneyService moneyService,
			@NonNull final RefundConfig currentRefundConfig)
	{
		return new PercentRefundConfigChangeHandler(moneyService, currentRefundConfig);
	}

	private final MoneyService moneyService;

	private PercentRefundConfigChangeHandler(
			@NonNull final MoneyService moneyService,
			@NonNull final RefundConfig currentRefundConfig)
	{
		super(Check.assumeNotNull(currentRefundConfig, "currentRefundConfig may not be null"));

		Check.errorUnless(RefundBase.PERCENTAGE.equals(currentRefundConfig.getRefundBase()),
				"The given currentRefundConfig needs to have refundBase = PERCENTAGE; currentRefundConfig={}",
				currentRefundConfig);

		this.moneyService = moneyService;
	}

	@Override
	public AssignmentToRefundCandidate createNewAssignment(@NonNull final AssignmentToRefundCandidate existingAssignment)
	{
		// note: currentRefundConfig can't be null
		final Percent percentToApply = getCurrentRefundConfig()
				.getPercent()
				.subtract(getFormerRefundConfig().getPercent());

		final Money base = existingAssignment.getMoneyBase();

		final Money moneyToAssign = moneyService.percentage(percentToApply, base);

		return AssignmentToRefundCandidate.builder()
				.refundConfigId(getCurrentRefundConfig().getId())
				.assignableInvoiceCandidateId(existingAssignment.getAssignableInvoiceCandidateId())
				.refundInvoiceCandidate(existingAssignment.getRefundInvoiceCandidate())
				.moneyBase(existingAssignment.getMoneyBase())
				.moneyAssignedToRefundCandidate(moneyToAssign)
				.quantityAssigendToRefundCandidate(existingAssignment.getQuantityAssigendToRefundCandidate())
				.useAssignedQtyInSum(getCurrentRefundConfig().isIncludeAssignmentsWithThisConfigInSum())
				.build();
	}

	@Override
	protected RefundBase getExpectedRefundBase()
	{
		return RefundBase.PERCENTAGE;
	}
}
