package de.metas.contracts.commission.commissioninstance.businesslogic.sales;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;

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

@Data
public class SalesCommissionShare
{
	/** can be null if this share was not yet persisted. */
	@Setter(AccessLevel.NONE)
	private final SalesCommissionShareId id;

	private final HierarchyLevel level;

	private final Beneficiary beneficiary;

	@Setter(AccessLevel.NONE)
	private CommissionPoints forecastedPointsSum;

	@Setter(AccessLevel.NONE)
	private CommissionPoints invoiceablePointsSum;

	@Setter(AccessLevel.NONE)
	private CommissionPoints invoicedPointsSum;

	/** Chronological list of facts that make it clear what happened when */
	private final ArrayList<SalesCommissionFact> facts;

	@JsonCreator
	@Builder
	private SalesCommissionShare(
			@JsonProperty("id") @Nullable final SalesCommissionShareId id,
			//@JsonProperty("contract") @NonNull final CommissionContract contract,
			@JsonProperty("level") @NonNull final HierarchyLevel level,
			@JsonProperty("beneficiary") @NonNull final Beneficiary beneficiary,
			@JsonProperty("facts") @NonNull @Singular final List<SalesCommissionFact> facts)
	{
		this.id = id;
		this.level = level;
		this.beneficiary = beneficiary;
		this.facts = new ArrayList<>();

		this.forecastedPointsSum = CommissionPoints.ZERO;
		this.invoiceablePointsSum = CommissionPoints.ZERO;
		this.invoicedPointsSum = CommissionPoints.ZERO;

		for (final SalesCommissionFact fact : facts)
		{
			addFact(fact);
		}
	}

	public final SalesCommissionShare addFact(@NonNull final SalesCommissionFact fact)
	{
		facts.add(fact);

		switch (fact.getState())
		{
			case FORECASTED:
				forecastedPointsSum = forecastedPointsSum.add(fact.getPoints());
				break;
			case INVOICEABLE:
				invoiceablePointsSum = invoiceablePointsSum.add(fact.getPoints());
				break;
			case INVOICED:
				invoicedPointsSum = invoicedPointsSum.add(fact.getPoints());
				break;
			default:
				throw new AdempiereException("fact has unsupported state " + fact.getState())
						.appendParametersToMessage()
						.setParameter("fact", fact);
		}
		return this;
	}

	public ImmutableList<SalesCommissionFact> getFacts()
	{
		return ImmutableList.copyOf(facts);
	}
}
