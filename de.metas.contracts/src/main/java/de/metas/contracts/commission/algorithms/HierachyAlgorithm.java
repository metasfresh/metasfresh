package de.metas.contracts.commission.algorithms;

import java.time.Instant;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.CommissionAlgorithm;
import de.metas.contracts.commission.CommissionFact;
import de.metas.contracts.commission.CommissionInstance;
import de.metas.contracts.commission.CommissionInstance.CommissionInstanceBuilder;
import de.metas.contracts.commission.CommissionPoints;
import de.metas.contracts.commission.CommissionShare;
import de.metas.contracts.commission.CommissionTrigger;
import de.metas.contracts.commission.CommissionTriggerChange;
import de.metas.contracts.commission.CreateInstanceRequest;
import de.metas.contracts.commission.CommissionState;
import de.metas.contracts.commission.hierarchy.Hierarchy;
import de.metas.contracts.commission.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.hierarchy.HierarchyNode;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class HierachyAlgorithm implements CommissionAlgorithm
{
	@Override
	public CommissionInstance createInstance(@NonNull final CreateInstanceRequest request)
	{
		final HierarchyConfig config = HierarchyConfig.cast(request.getConfig());

		final Hierarchy hierarchy = request.getHierarchy();

		final CommissionTrigger trigger = request.getTrigger();

		final CommissionInstanceBuilder result = CommissionInstance.builder()
				.trigger(trigger)
				.config(config);

		final ImmutableList<CommissionShare> shares = createNewShares(config, hierarchy, trigger);
		result.shares(shares);

		createAndAddFacts(shares,
				trigger.getTimestamp(),
				trigger.getForecastedPoints(),
				trigger.getPointsToInvoice(),
				trigger.getInvoicedPoints());

		return result.build();
	}

	private ImmutableList<CommissionShare> createNewShares(
			final HierarchyConfig config,
			final Hierarchy hierarchy,
			final CommissionTrigger trigger)
	{
		final ImmutableList.Builder<CommissionShare> shares = ImmutableList.builder();

		HierarchyLevel currentLevel = HierarchyLevel.ZERO;

		for (final HierarchyNode node : hierarchy.getUpStream(trigger.getBeneficiary()))
		{
			final Beneficiary beneficiary = node.getBeneficiary();
			final HierarchyContract contract = HierarchyContract.cast(config.getContractFor(beneficiary));

			final CommissionShare share = CommissionShare.builder()
					.beneficiary(beneficiary)
					.level(currentLevel)
					.contract(contract)
					.build();
			shares.add(share);

			currentLevel = currentLevel.incByOne();
		}
		return shares.build();
	}

	@Override
	public void applyTriggerChange(@NonNull final CommissionTriggerChange change)
	{
		final CommissionInstance instanceToChange = change.getOldCommissionInstance();

		final CommissionTrigger oldTrigger = instanceToChange.getTrigger();

		final CommissionPoints forecastedBaseDelta = change.getForecastedPoints().subtract(oldTrigger.getForecastedPoints());
		final CommissionPoints toInvoiceBaseDelta = change.getPointsToInvoice().subtract(oldTrigger.getPointsToInvoice());
		final CommissionPoints invoicedBaseDelta = change.getInvoicedPoints().subtract(oldTrigger.getInvoicedPoints());

		final ImmutableList<CommissionShare> sharesToChange = instanceToChange.getShares();

		createAndAddFacts(
				sharesToChange,
				change.getTimestamp(),
				forecastedBaseDelta,
				toInvoiceBaseDelta,
				invoicedBaseDelta);
	}

	private void createAndAddFacts(
			@NonNull final ImmutableList<CommissionShare> shares,
			@NonNull final Instant timestamp,
			@NonNull final CommissionPoints initialForecastedBase,
			@NonNull final CommissionPoints initialToInvoiceBase,
			@NonNull final CommissionPoints initialInvoicedBase)
	{
		CommissionPoints currentForecastedBase = initialForecastedBase;
		CommissionPoints currentToInvoiceBase = initialToInvoiceBase;
		CommissionPoints currentInvoicedBase = initialInvoicedBase;

		for (final CommissionShare share : shares)
		{
			final HierarchyContract contract = HierarchyContract.cast(share.getContract());

			final Optional<CommissionFact> forecastedFact = createFact(timestamp, contract, CommissionState.FORECASTED, currentForecastedBase);
			final Optional<CommissionFact> toInvoiceFact = createFact(timestamp, contract, CommissionState.TO_INVOICE, currentToInvoiceBase);
			final Optional<CommissionFact> invoicedFact = createFact(timestamp, contract, CommissionState.INVOICED, currentInvoicedBase);

			forecastedFact.ifPresent(share::addFact);
			toInvoiceFact.ifPresent(share::addFact);
			invoicedFact.ifPresent(share::addFact);

			final HierarchyConfig config = contract.getConfig();
			if (config.isSubtractLowerLevelCommissionFromBase())
			{
				currentForecastedBase = currentForecastedBase.subtract(forecastedFact.map(CommissionFact::getPoints).orElse(CommissionPoints.ZERO));
				currentToInvoiceBase = currentToInvoiceBase.subtract(toInvoiceFact.map(CommissionFact::getPoints).orElse(CommissionPoints.ZERO));
				currentInvoicedBase = currentInvoicedBase.subtract(invoicedFact.map(CommissionFact::getPoints).orElse(CommissionPoints.ZERO));
			}
		}
	}

	private Optional<CommissionFact> createFact(
			final Instant timestamp,
			final HierarchyContract contract,
			final CommissionState state,
			final CommissionPoints base)
	{
		final CommissionPoints percentage = base.computePercentageOf(
				contract.getCommissionPercent(),
				contract.getPointsPrecision());

		if (percentage.isZero())
		{
			return Optional.empty();
		}

		final CommissionFact fact = CommissionFact.builder()
				.state(state)
				.points(percentage)
				.timestamp(timestamp)
				.build();
		return Optional.of(fact);
	}
}
