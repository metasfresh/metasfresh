package de.metas.contracts.commission.commissioninstance.businesslogic.algorithms;

import java.time.Instant;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateInstanceRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance.CommissionInstanceBuilder;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerChange;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionState;
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
		final CommissionTriggerData triggerData = trigger.getCommissionTriggerData();

		final CommissionInstanceBuilder result = CommissionInstance.builder()
				.currentTriggerData(triggerData)
				.config(config);

		final ImmutableList<SalesCommissionShare> shares = createNewShares(config, hierarchy, trigger);
		result.shares(shares);

		createAndAddFacts(shares,
				config,
				triggerData.getTimestamp(),
				triggerData.getForecastedPoints(),
				triggerData.getInvoiceablePoints(),
				triggerData.getInvoicedPoints());

		return result.build();
	}

	private ImmutableList<SalesCommissionShare> createNewShares(
			final HierarchyConfig config,
			final Hierarchy hierarchy,
			final CommissionTrigger trigger)
	{
		final ImmutableList.Builder<SalesCommissionShare> shares = ImmutableList.builder();

		HierarchyLevel currentLevel = HierarchyLevel.ZERO;

		for (final HierarchyNode node : hierarchy.getUpStream(trigger.getBeneficiary()))
		{
			final Beneficiary beneficiary = node.getBeneficiary();
			final HierarchyContract contract = HierarchyContract.cast(config.getContractFor(beneficiary));
			if (contract == null)
			{
				// current hierarchy member doesn't have a contract; skip it.
				continue;

			}
			final SalesCommissionShare share = SalesCommissionShare.builder()
					.beneficiary(beneficiary)
					.level(currentLevel)
					.build();
			shares.add(share);

			currentLevel = currentLevel.incByOne();
		}
		return shares.build();
	}

	@Override
	public void applyTriggerChangeToShares(@NonNull final CommissionTriggerChange change)
	{
		final CommissionInstance instanceToChange = change.getInstanceToUpdate();
		final CommissionTriggerData newTriggerData = change.getNewCommissionTriggerData();

		final CommissionTriggerData oldTriggerData = instanceToChange.getCurrentTriggerData();

		final CommissionPoints forecastedBaseDelta = newTriggerData.getForecastedPoints().subtract(oldTriggerData.getForecastedPoints());
		final CommissionPoints toInvoiceBaseDelta = newTriggerData.getInvoiceablePoints().subtract(oldTriggerData.getInvoiceablePoints());
		final CommissionPoints invoicedBaseDelta = newTriggerData.getInvoicedPoints().subtract(oldTriggerData.getInvoicedPoints());

		final ImmutableList<SalesCommissionShare> sharesToChange = instanceToChange.getShares();

		createAndAddFacts(
				sharesToChange,
				HierarchyConfig.cast(instanceToChange.getConfig()),
				newTriggerData.getTimestamp(),
				forecastedBaseDelta,
				toInvoiceBaseDelta,
				invoicedBaseDelta);
	}

	private void createAndAddFacts(
			@NonNull final ImmutableList<SalesCommissionShare> shares,
			@NonNull final HierarchyConfig config,
			@NonNull final Instant timestamp,
			@NonNull final CommissionPoints initialForecastedBase,
			@NonNull final CommissionPoints initialToInvoiceBase,
			@NonNull final CommissionPoints initialInvoicedBase)
	{
		CommissionPoints currentForecastedBase = initialForecastedBase;
		CommissionPoints currentToInvoiceBase = initialToInvoiceBase;
		CommissionPoints currentInvoicedBase = initialInvoicedBase;

		for (final SalesCommissionShare share : shares)
		{
			final HierarchyContract contract = HierarchyContract.cast(config.getContractFor(share.getBeneficiary()));

			final Optional<SalesCommissionFact> forecastedFact = createFact(timestamp, contract, SalesCommissionState.FORECASTED, currentForecastedBase);
			final Optional<SalesCommissionFact> toInvoiceFact = createFact(timestamp, contract, SalesCommissionState.INVOICEABLE, currentToInvoiceBase);
			final Optional<SalesCommissionFact> invoicedFact = createFact(timestamp, contract, SalesCommissionState.INVOICED, currentInvoicedBase);

			forecastedFact.ifPresent(share::addFact);
			toInvoiceFact.ifPresent(share::addFact);
			invoicedFact.ifPresent(share::addFact);

			if (config.isSubtractLowerLevelCommissionFromBase())
			{
				currentForecastedBase = currentForecastedBase.subtract(forecastedFact.map(SalesCommissionFact::getPoints).orElse(CommissionPoints.ZERO));
				currentToInvoiceBase = currentToInvoiceBase.subtract(toInvoiceFact.map(SalesCommissionFact::getPoints).orElse(CommissionPoints.ZERO));
				currentInvoicedBase = currentInvoicedBase.subtract(invoicedFact.map(SalesCommissionFact::getPoints).orElse(CommissionPoints.ZERO));
			}
		}
	}

	private Optional<SalesCommissionFact> createFact(
			final Instant timestamp,
			final HierarchyContract contract,
			final SalesCommissionState state,
			final CommissionPoints base)
	{
		final CommissionPoints percentage = base.computePercentageOf(
				contract.getCommissionPercent(),
				contract.getPointsPrecision());

		if (percentage.isZero())
		{
			return Optional.empty();
		}

		final SalesCommissionFact fact = SalesCommissionFact.builder()
				.state(state)
				.points(percentage)
				.timestamp(timestamp)
				.build();
		return Optional.of(fact);
	}
}
