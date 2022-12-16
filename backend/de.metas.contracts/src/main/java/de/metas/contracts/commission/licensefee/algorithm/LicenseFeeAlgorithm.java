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

package de.metas.contracts.commission.licensefee.algorithm;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.Check;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Payer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionState;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerChange;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.model.I_C_LicenseFeeSettings;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class LicenseFeeAlgorithm implements CommissionAlgorithm
{
	private static final Logger logger = LogManager.getLogger(LicenseFeeAlgorithm.class);

	@Override
	@NonNull
	public ImmutableList<CommissionShare> createCommissionShares(@NonNull final CreateCommissionSharesRequest request)
	{
		final List<LicenseFeeConfig> licenseFeeCommissionConfigs = request.getConfigs().stream()
				.filter(LicenseFeeConfig::isInstance)
				.map(LicenseFeeConfig::cast)
				.collect(ImmutableList.toImmutableList());

		Check.assume(licenseFeeCommissionConfigs.size() == 1, "There is always only one LicenseFeeConfig involved!");

		final LicenseFeeConfig licenseFeeConfig = licenseFeeCommissionConfigs.get(0);
		final CommissionTrigger commissionTrigger = request.getTrigger();

		if (licenseFeeConfig.getContractFor(commissionTrigger.getSalesRepId()) == null)
		{
			//dev-note: just a guard for the future as it will never happen in the current context
			throw new AdempiereException("No contract available for the given salesRep!")
					.appendParametersToMessage()
					.setParameter("SalesRepId", commissionTrigger.getSalesRepId())
					.setParameter("CustomerBPartnerId", commissionTrigger.getCustomer().getBPartnerId())
					.setParameter("OrgBPartnerId", commissionTrigger.getOrgBPartnerId())
					.setParameter("LicenseFeeConfig", licenseFeeConfig);
		}

		final CommissionShare commissionShare = CommissionShare.builder()
				.level(HierarchyLevel.ZERO) //dev-note: there is no hierarchy on this kind of commission
				.beneficiary(Beneficiary.of(commissionTrigger.getOrgBPartnerId()))
				.payer(Payer.of(commissionTrigger.getSalesRepId()))
				.config(licenseFeeConfig)
				.soTrx(SOTrx.SALES)
				.build();

		createAndAddFacts(commissionShare, commissionTrigger.getCommissionTriggerData());

		return ImmutableList.of(commissionShare);
	}

	@Override
	public void applyTriggerChangeToShares(@NonNull final CommissionTriggerChange change)
	{
		final List<CommissionShare> licenseFeeCommissionShares = change.getInstanceToUpdate()
				.getShares()
				.stream()
				.filter(share -> LicenseFeeConfig.isInstance(share.getConfig()))
				.collect(ImmutableList.toImmutableList());

		Check.assume(licenseFeeCommissionShares.size() == 1, "There is always only one license fee CommissionShare involved!");

		createAndAddFacts(licenseFeeCommissionShares.get(0), change.getNewCommissionTriggerData());
	}

	private void createAndAddFacts(
			@NonNull final CommissionShare commissionShare,
			@NonNull final CommissionTriggerData commissionTriggerData)
	{
		try (final MDC.MDCCloseable shareMDC = TableRecordMDC.putTableRecordReference(I_C_Commission_Share.Table_Name, commissionShare.getId()))
		{
			Check.assume(LicenseFeeConfig.isInstance(commissionShare.getConfig()), "The commission share is always carrying a license fee commission config at this stage!");

			final LicenseFeeConfig licenseFeeConfig = LicenseFeeConfig.cast(commissionShare.getConfig());

			try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(I_C_LicenseFeeSettings.Table_Name, licenseFeeConfig.getId()))
			{
				logger.debug("LicenseFeeConfig - Create commission shares and facts");

				final Function<CommissionPoints, CommissionPoints> computeOrgCommissionAmount = (basePoints) ->
						basePoints.computePercentageOf(licenseFeeConfig.getCommissionPercent(), licenseFeeConfig.getPointsPrecision());

				final CommissionPoints forecastCP = computeOrgCommissionAmount.apply(commissionTriggerData.getForecastedBasePoints());
				final CommissionPoints toInvoiceCP = computeOrgCommissionAmount.apply(commissionTriggerData.getInvoiceableBasePoints());
				final CommissionPoints invoicedCP = computeOrgCommissionAmount.apply(commissionTriggerData.getInvoicedBasePoints());

				final Instant triggerDataTimestamp = commissionTriggerData.getTimestamp();

				final Optional<CommissionFact> forecastedFact = CommissionFact.createFact(triggerDataTimestamp, CommissionState.FORECASTED, forecastCP, commissionShare.getForecastedPointsSum());
				final Optional<CommissionFact> toInvoiceFact = CommissionFact.createFact(triggerDataTimestamp, CommissionState.INVOICEABLE, toInvoiceCP, commissionShare.getInvoiceablePointsSum());
				final Optional<CommissionFact> invoicedFact = CommissionFact.createFact(triggerDataTimestamp, CommissionState.INVOICED, invoicedCP, commissionShare.getInvoicedPointsSum());

				forecastedFact.ifPresent(commissionShare::addFact);
				toInvoiceFact.ifPresent(commissionShare::addFact);
				invoicedFact.ifPresent(commissionShare::addFact);
			}
		}
	}
}
