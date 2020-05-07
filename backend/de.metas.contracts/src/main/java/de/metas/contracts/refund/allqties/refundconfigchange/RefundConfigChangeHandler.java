package de.metas.contracts.refund.allqties.refundconfigchange;

import de.metas.contracts.refund.AssignmentToRefundCandidate;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.util.Check;
import lombok.Getter;
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

public abstract class RefundConfigChangeHandler
{
	private RefundConfig formerRefundConfig;

	@Getter
	private RefundConfig currentRefundConfig;

	protected RefundConfigChangeHandler(@NonNull final RefundConfig currentRefundConfig)
	{
		assertCorrectRefundBase(currentRefundConfig);

		this.currentRefundConfig = currentRefundConfig;
		this.formerRefundConfig = null;

	}

	public void changeCurrentRefundConfig(@NonNull final RefundConfig refundConfig)
	{
		assertCorrectRefundBase(refundConfig);

		this.formerRefundConfig = this.currentRefundConfig;
		this.currentRefundConfig = refundConfig;
	}

	private void assertCorrectRefundBase(final RefundConfig refundConfig)
	{
		Check.errorUnless(getExpectedRefundBase().equals(refundConfig.getRefundBase()),
				"The given currentRefundConfig needs to have refundBase = AMOUNT_PER_UNIT; currentRefundConfig={}",
				refundConfig);
	}

	/**
	 * @return the refund config that was the current config before {@link #currentRefundConfig(RefundConfig)} was called.
	 */
	public RefundConfig getFormerRefundConfig()
	{
		return Check.assumeNotNull(formerRefundConfig, "formerRefundConfig may not be null; invoke the currentRefundConfig() method first; this={}", this);
	}

	/**
	 * @param existingAssignment an assignment that belongs to the refund config from {@link #getFormerRefundConfig()}.
	 * @return a new assignment that belongs to the refund config that was last set using {@link #currentRefundConfig(RefundConfig)}.
	 */
	public abstract AssignmentToRefundCandidate createNewAssignment(AssignmentToRefundCandidate existingAssignment);

	protected abstract RefundBase getExpectedRefundBase();
}
