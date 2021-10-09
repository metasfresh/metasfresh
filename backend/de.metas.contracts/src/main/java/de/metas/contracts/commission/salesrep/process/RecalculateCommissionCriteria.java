/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.commission.salesrep.process;

import de.metas.bpartner.BPartnerId;
import de.metas.util.time.InstantInterval;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RecalculateCommissionCriteria
{
	@NonNull
	BPartnerId salesrepPartnerId;

	@NonNull
	InstantInterval targetInterval;

	@NonNull
	BPartnerId topLevelSalesRepId;

	public static RecalculateCommissionCriteria of(
			@NonNull final BPartnerId salesrepPartnerId,
			@NonNull final InstantInterval interval,
			@NonNull final BPartnerId topLevelSalesRepId)
	{
		return RecalculateCommissionCriteria.builder()
				.salesrepPartnerId(salesrepPartnerId)
				.targetInterval(interval)
				.topLevelSalesRepId(topLevelSalesRepId)
				.build();
	}

}
