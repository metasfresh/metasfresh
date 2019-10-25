package de.metas.contracts.commission.commissioninstance.businesslogic;

import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerChange;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;

/*
 * #%L
 * de.metas.commission
 * %%
 * Copyright (C) 2019 metas GmbH
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

public interface CommissionAlgorithm
{
	/**
	 * Create a new commission instance with the given {@code trigger} and {@link SalesCommissionShare}s.
	 * The method is invoked by the framework and can safely assume that no commission instance exists yet (in case that matters).
	 */
	CommissionInstance createInstance(CreateInstanceRequest request);

	/**
	 * Apply the given {@code change}'s {@code newCommissionTriggerData} to its {@link CommissionInstance}.
	 */
	void applyTriggerChangeToShares(CommissionTriggerChange change);
}
