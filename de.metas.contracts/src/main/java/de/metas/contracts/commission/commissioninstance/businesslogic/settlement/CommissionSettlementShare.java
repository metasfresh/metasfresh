package de.metas.contracts.commission.commissioninstance.businesslogic.settlement;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShareId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

/*
 * #%L
 * de.metas.contracts
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

@Data
public class CommissionSettlementShare
{
	/** a settlement share doesn't make sense without a sames commission share. */
	private SalesCommissionShareId salesCommissionShareId;

	@Setter(AccessLevel.NONE)
	private CommissionPoints pointsToSettleSum;

	@Setter(AccessLevel.NONE)
	private CommissionPoints settledPointsSum;

	/** Chronological list of facts that make it clear what happened when */
	private final ArrayList<CommissionSettlementFact> facts;

	@JsonCreator
	@Builder
	private CommissionSettlementShare(
			@JsonProperty("salesCommissionShareId") @NonNull final SalesCommissionShareId salesCommissionShareId,
			@JsonProperty("facts") @NonNull final List<CommissionSettlementFact> facts)
	{
		this.salesCommissionShareId = salesCommissionShareId;
		this.facts = new ArrayList<>();

		this.pointsToSettleSum = CommissionPoints.ZERO;
		this.settledPointsSum = CommissionPoints.ZERO;

		for (final CommissionSettlementFact fact : facts)
		{
			addFact(fact);
		}
	}

	public final CommissionSettlementShare addFact(@NonNull final CommissionSettlementFact fact)
	{
		facts.add(fact);

		switch (fact.getState())
		{
			case TO_SETTLE:
				pointsToSettleSum = pointsToSettleSum.add(fact.getPoints());
				break;
			case SETTLED:
				settledPointsSum = settledPointsSum.add(fact.getPoints());
				break;
			default:
				throw new AdempiereException("fact has unsupported state " + fact.getState())
						.appendParametersToMessage()
						.setParameter("fact", fact);
		}
		return this;
	}

	public ImmutableList<CommissionSettlementFact> getFacts()
	{
		return ImmutableList.copyOf(facts);
	}

}
