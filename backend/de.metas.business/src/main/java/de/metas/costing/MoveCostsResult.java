package de.metas.costing;

import de.metas.acct.api.AcctSchema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
@ToString
public class MoveCostsResult
{
	private final AggregatedCostAmount outboundCosts;
	private final AggregatedCostAmount inboundCosts;

	@Builder(toBuilder = true)
	private MoveCostsResult(
			@NonNull final AggregatedCostAmount outboundCosts,
			@NonNull final AggregatedCostAmount inboundCosts)
	{
		this.outboundCosts = outboundCosts;
		this.inboundCosts = inboundCosts;
	}

	public CostAmount getOutboundAmountToPost(@NonNull final AcctSchema as)
	{
		return outboundCosts.getTotalAmountToPost(as);
	}

	public CostAmount getInboundAmountToPost(@NonNull final AcctSchema as)
	{
		return inboundCosts.getTotalAmountToPost(as);
	}

	public MoveCostsResult add(@NonNull final MoveCostsResult partialResult)
	{
		return toBuilder()
				.outboundCosts(outboundCosts.add(partialResult.outboundCosts))
				.inboundCosts(inboundCosts.add(partialResult.inboundCosts))
				.build();
	}
}
