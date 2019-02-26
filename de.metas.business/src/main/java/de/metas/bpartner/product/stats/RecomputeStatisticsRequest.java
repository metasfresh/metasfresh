package de.metas.bpartner.product.stats;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
public class RecomputeStatisticsRequest
{
	@NonNull
	final BPartnerId bpartnerId;

	@NonNull
	final ImmutableSet<ProductId> productIds;

	boolean recomputeInOutStatistics;
	boolean recomputeInvoiceStatistics;

	@Builder
	private RecomputeStatisticsRequest(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Set<ProductId> productIds,
			boolean recomputeInOutStatistics,
			boolean recomputeInvoiceStatistics)
	{
		Check.assumeNotEmpty(productIds, "productIds is not empty");
		Check.assume(recomputeInOutStatistics || recomputeInvoiceStatistics,
				"At least one of the recompute actions shall be mentioned");

		this.bpartnerId = bpartnerId;
		this.productIds = ImmutableSet.copyOf(productIds);
		this.recomputeInOutStatistics = recomputeInOutStatistics;
		this.recomputeInvoiceStatistics = recomputeInvoiceStatistics;
	}

}
