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

package de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Payer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstance;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyNode;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionState;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerChange;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.model.I_C_HierarchyCommissionSettings;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_BPartner;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import java.time.Instant;
import java.util.Optional;

@Value
public class HierachyAlgorithm implements CommissionAlgorithm
{

	private static final Logger logger = LogManager.getLogger(HierachyAlgorithm.class);

	@Override
	public ImmutableList<CommissionShare> createCommissionShares(@NonNull final CreateCommissionSharesRequest request)
	{
		final CommissionTrigger trigger = request.getTrigger();
		final CommissionTriggerData triggerData = trigger.getCommissionTriggerData();

		final Hierarchy hierarchy = request.getHierarchy();

		final ImmutableList<HierarchyConfig> hierarchyConfigs = request.getConfigs().stream()
				.filter(HierarchyConfig::isInstance)
				.map(HierarchyConfig::cast)
				.collect(ImmutableList.toImmutableList());

		/* The direct beneficiary; Will probably be part of a hierarchy.

		  Note: used to be the customer's "direct" sales rep. Now it's the customer themselves, because they *might* be salesReps too, and might require
		  some commission-treatment also for their own.
		 */
		final Beneficiary directBeneficiary = Beneficiary.of(trigger.getCustomer().getBPartnerId());

		final Payer commissionPayer = Payer.of(trigger.getOrgBPartnerId());

		final ImmutableList<CommissionShare> shares =
				createNewShares(hierarchyConfigs, hierarchy.getUpStream(directBeneficiary), commissionPayer, request.getStartingHierarchyLevel());

		createAndAddFacts(shares,
						  triggerData.getTimestamp(),
						  triggerData.getForecastedBasePoints(),
						  triggerData.getInvoiceableBasePoints(),
						  triggerData.getInvoicedBasePoints(),
						  triggerData.getTradedCommissionPercent());

		return shares;
	}

	private ImmutableList<CommissionShare> createNewShares(
			@NonNull final ImmutableList<HierarchyConfig> hierarchyConfigs,
			@NonNull final Iterable<HierarchyNode> beneficiaryUpStream,
			@NonNull final Payer payer,
			@NonNull final HierarchyLevel startingHierarchyLevel)
	{
		final ImmutableList.Builder<CommissionShare> shares = ImmutableList.builder();

		HierarchyLevel currentHierarchyLevel = startingHierarchyLevel;

		for (final HierarchyNode beneficiaryNode : beneficiaryUpStream)
		{
			final Beneficiary beneficiary = beneficiaryNode.getBeneficiary();

			for (final HierarchyConfig hierarchyConfig : hierarchyConfigs)
			{
				try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(I_C_BPartner.Table_Name, beneficiary.getBPartnerId()))
				{
					final CommissionContract contract = hierarchyConfig.getContractFor(beneficiary.getBPartnerId());
					if (contract == null)
					{
						logger.debug("Beneficiary C_BPartner_ID={} is part of hierarchy but has no contract; -> skip level={}", beneficiary.getBPartnerId().getRepoId(), currentHierarchyLevel);
						continue;
					}

					final CommissionShare share = CommissionShare.builder()
							.payer(payer)
							.beneficiary(beneficiary)
							.level(currentHierarchyLevel)
							.config(hierarchyConfig)
							.soTrx(SOTrx.PURCHASE)
							.build();
					shares.add(share);

					currentHierarchyLevel = currentHierarchyLevel.incByOne();
				}
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

		final ImmutableList<CommissionShare> sharesToChange = instanceToChange.getShares()
				.stream()
				.filter(share -> HierarchyConfig.isInstance(share.getConfig()))
				.collect(ImmutableList.toImmutableList());

		if (sharesToChange.isEmpty())
		{
			return;
		}

		createAndAddFacts(
				sharesToChange,
				newTriggerData.getTimestamp(),
				forecastedBase,
				toInvoiceBase,
				invoicedBase,
				newTriggerData.getTradedCommissionPercent());
	}

	private void createAndAddFacts(
			@NonNull final ImmutableList<CommissionShare> shares,
			@NonNull final Instant timestamp,
			@NonNull final CommissionPoints initialForecastedBase,
			@NonNull final CommissionPoints initialToInvoiceBase,
			@NonNull final CommissionPoints initialInvoicedBase,
			@NonNull final Percent tradedCommissionPercent)
	{
		CommissionPoints currentForecastedBase = initialForecastedBase;
		CommissionPoints currentToInvoiceBase = initialToInvoiceBase;
		CommissionPoints currentInvoicedBase = initialInvoicedBase;

		for (final CommissionShare share : shares)
		{
			try (final MDCCloseable shareMDC = TableRecordMDC.putTableRecordReference(I_C_Commission_Share.Table_Name, share.getId()))
			{
				if (!HierarchyConfig.isInstance(share.getConfig()))
				{
					logger.debug("Skipping commissionConfig because it is not a HierarchyConfig; config={}", share.getConfig());
					continue;
				}

				final HierarchyConfig hierarchyConfig = HierarchyConfig.cast(share.getConfig());
				try (final MDCCloseable ignore = TableRecordMDC.putTableRecordReference(I_C_HierarchyCommissionSettings.Table_Name, hierarchyConfig.getId()))
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

					final Optional<CommissionFact> forecastedFact = CommissionFact.createFact(timestamp, CommissionState.FORECASTED, forecastCP.subtract(tradedForecastCP), share.getForecastedPointsSum());
					final Optional<CommissionFact> toInvoiceFact = CommissionFact.createFact(timestamp, CommissionState.INVOICEABLE, toInvoiceCP.subtract(tradedToInvoiceCP), share.getInvoiceablePointsSum());
					final Optional<CommissionFact> invoicedFact = CommissionFact.createFact(timestamp, CommissionState.INVOICED, invoicedCP.subtract(tradedInvoicedCP), share.getInvoicedPointsSum());

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
