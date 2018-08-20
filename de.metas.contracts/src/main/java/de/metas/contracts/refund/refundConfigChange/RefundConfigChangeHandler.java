package de.metas.contracts.refund.refundConfigChange;

import de.metas.contracts.refund.AssignmentToRefundCandidate;
import de.metas.contracts.refund.RefundConfig;

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

public interface RefundConfigChangeHandler
{
	/** Compute and store the difference (percent or otherwise) between the previous config and the given one. */
	public void currentRefundConfig(RefundConfig refundConfig);

	/**
	 * @return the refund config that was the current config before {@link #currentRefundConfig(RefundConfig)} was called.
	 */
	public RefundConfig getFormerRefundConfig();

	/**
	 * @param existingAssignment an assignment that belongs to the refund config from {@link #getFormerRefundConfig()}.
	 * @return a new assignment that belongs to the refund config that was set using {@link #currentRefundConfig(RefundConfig)}.
	 */
	public AssignmentToRefundCandidate createNewAssignment(AssignmentToRefundCandidate existingAssignment);
}
