package de.metas.contracts.commission.commissioninstance.businesslogic.algorithms;

import java.time.Instant;
import java.util.Optional;

import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionState;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerChange;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.lang.Percent;
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

	private static final Logger logger = LogManager.getLogger(HierachyAlgorithm.class);

	@Override
	public ImmutableList<SalesCommissionShare> createCommissionShares(@NonNull final CreateCommissionSharesRequest request)
	{
		final CommissionTrigger trigger = request.getTrigger();
		final CommissionTriggerData triggerData = trigger.getCommissionTriggerData();
		final Hierarchy hierarchy = request.getHierarchy();

		final ImmutableList.Builder<SalesCommissionShare> result = ImmutableList.builder();

		final ImmutableList<CommissionConfig> configs = request.getConfigs();
		for (final CommissionConfig config : configs)
		{
			if (!HierarchyConfig.isInstance(config))
			{
				logger.debug("Skipping commissionConfig because it is not a HierarchyConfig; config={}", config);
				continue;
			}
			final HierarchyConfig hierarchyConfig = HierarchyConfig.cast(config);

			final ImmutableList<SalesCommissionShare> shares = createNewShares(hierarchyConfig, hierarchy, trigger);

			createAndAddFacts(shares,
					triggerData.getTimestamp(),
					triggerData.getForecastedBasePoints(),
					triggerData.getInvoiceableBasePoints(),
					triggerData.getInvoicedBasePoints(),
					triggerData.getTradedCommissionPercent());

			result.addAll(shares);
		}

		return result.build();
	}

	private ImmutableList<SalesCommissionShare> createNewShares(
			@NonNull final HierarchyConfig config,
			@NonNull final Hierarchy hierarchy,
			@NonNull final CommissionTrigger trigger)
	{
		final ImmutableList.Builder<SalesCommissionShare> shares = ImmutableList.builder();

		HierarchyLevel currentLevel = HierarchyLevel.ZERO;

		for (final HierarchyNode node : hierarchy.getUpStream(trigger.getBeneficiary()))
		{
			try (final MDCCloseable beneficiaryMDC = TableRecordMDC.putTableRecordReference(I_C_BPartner.Table_Name, node.getBeneficiary().getBPartnerId()))
			{
				final Beneficiary beneficiary = node.getBeneficiary();
				final CommissionContract contract = config.getContractFor(beneficiary);
				if (contract == null)
				{
					logger.debug("Beneficiary is part of hierarchy but has no contract; -> skip level={}", currentLevel);
					continue;
				}

				final SalesCommissionShare share = SalesCommissionShare.builder()
						.beneficiary(beneficiary)
						.level(currentLevel)
						.config(config)
						.build();
				shares.add(share);

				currentLevel = currentLevel.incByOne();
			}
		}
		return shares.build();
	}

	@Override
	public void applyTriggerChangeToShares(@NonNull final CommissionTriggerChange change)
	{
		final CommissionInstance instanceToChange = change.getInstanceToUpdate();
		final CommissionTriggerData newTriggerData = change.getNewCommissionTriggerData();

		final CommissionPoints forecastedBase = newTriggerData.getForecastedBasePoints();
		final CommissionPoints toInvoiceBase = newTriggerData.getInvoiceableBasePoints();
		final CommissionPoints invoicedBase = newTriggerData.getInvoicedBasePoints();

		final ImmutableList<SalesCommissionShare> sharesToChange = instanceToChange.getShares();

		createAndAddFacts(
				sharesToChange,
				newTriggerData.getTimestamp(),
				forecastedBase,
				toInvoiceBase,
				invoicedBase,
				newTriggerData.getTradedCommissionPercent());
	}

	private void createAndAddFacts(
			@NonNull final ImmutableList<SalesCommissionShare> shares,
			@NonNull final Instant timestamp,
			@NonNull final CommissionPoints initialForecastedBase,
			@NonNull final CommissionPoints initialToInvoiceBase,
			@NonNull final CommissionPoints initialInvoicedBase,
			@NonNull final Percent tradedCommissionPercent)
	{
		CommissionPoints currentForecastedBase = initialForecastedBase;
		CommissionPoints currentToInvoiceBase = initialToInvoiceBase;
		CommissionPoints currentInvoicedBase = initialInvoicedBase;

		for (final SalesCommissionShare share : shares)
		{
			try (final MDCCloseable shareMDC = TableRecordMDC.putTableRecordReference(I_C_Commission_Share.Table_Name, share.getId()))
			{
				if (!HierarchyConfig.isInstance(share.getConfig()))
				{
					logger.debug("Skipping commissionConfig because it is not a HierarchyConfig; config={}", share.getConfig());
					continue;
				}

				final HierarchyConfig hierarchyConfig = HierarchyConfig.cast(share.getConfig());
				try (final MDCCloseable configSettingsMDC = TableRecordMDC.putTableRecordReference(I_C_HierarchyCommissionSettings.Table_Name, hierarchyConfig.getId()))
				{
					logger.debug("Create commission shares and facts");

					final HierarchyContract contract = HierarchyContract.cast(share.getContract());

					final Percent currentTradedCommissionPercent = HierarchyLevel.ZERO.equals(share.getLevel())
							? tradedCommissionPercent
							: Percent.ZERO;

					final CommissionPoints forecastCP = calculateSalesRepCommissionPoints(contract, currentForecastedBase);
					final CommissionPoints toInvoiceCP = calculateSalesRepCommissionPoints(contract, currentToInvoiceBase);
					final CommissionPoints invoicedCP = calculateSalesRepCommissionPoints(contract, currentInvoicedBase);

					final CommissionPoints tradedForecastCP = calculateTradedCommissionPoints(forecastCP, currentTradedCommissionPercent, contract.getPointsPrecision());
					final CommissionPoints tradedToInvoiceCP = calculateTradedCommissionPoints(toInvoiceCP, currentTradedCommissionPercent, contract.getPointsPrecision());
					final CommissionPoints tradedInvoicedCP = calculateTradedCommissionPoints(invoicedCP, currentTradedCommissionPercent, contract.getPointsPrecision());

					final Optional<SalesCommissionFact> forecastedFact = createFact(timestamp, SalesCommissionState.FORECASTED, forecastCP.subtract(tradedForecastCP), share.getForecastedPointsSum());
					final Optional<SalesCommissionFact> toInvoiceFact = createFact(timestamp, SalesCommissionState.INVOICEABLE, toInvoiceCP.subtract(tradedToInvoiceCP), share.getInvoiceablePointsSum());
					final Optional<SalesCommissionFact> invoicedFact = createFact(timestamp, SalesCommissionState.INVOICED, invoicedCP.subtract(tradedInvoicedCP), share.getInvoicedPointsSum());

					forecastedFact.ifPresent(share::addFact);
					toInvoiceFact.ifPresent(share::addFact);
					invoicedFact.ifPresent(share::addFact);

					if (hierarchyConfig.isSubtractLowerLevelCommissionFromBase())
					{
						currentForecastedBase = currentForecastedBase.subtract(forecastCP);
						currentToInvoiceBase = currentToInvoiceBase.subtract(toInvoiceCP);
						currentInvoicedBase = currentInvoicedBase.subtract(invoicedCP);
					}
				}
			}
		}
	}

	private Optional<SalesCommissionFact> createFact(
			@NonNull final Instant timestamp,
			@NonNull final SalesCommissionState state,
			@NonNull final CommissionPoints currentCommissionPoints,
			@NonNull final CommissionPoints previousCommissionPoints)
	{
		final CommissionPoints points = currentCommissionPoints.subtract(previousCommissionPoints);

		if (points.isZero())
		{
			return Optional.empty(); // a zero-points fact would not change anything, so don't bother creating it
		}

		final SalesCommissionFact fact = SalesCommissionFact.builder()
				.state(state)
				.points(points)
				.timestamp(timestamp)
				.build();
		return Optional.of(fact);
	}

	private CommissionPoints calculateSalesRepCommissionPoints(
			@NonNull final HierarchyContract contract,
			@NonNull final CommissionPoints base)
	{
		return base
				.computePercentageOf(
						contract.getCommissionPercent(),
						contract.getPointsPrecision());
	}

	private CommissionPoints calculateTradedCommissionPoints(
			@NonNull final CommissionPoints salesRepCommissionPoints,
			@NonNull final Percent tradedCommissionPercent,
			final int pointsPrecision)
	{
		return salesRepCommissionPoints
				.computePercentageOf(tradedCommissionPercent, pointsPrecision);
	}
}
