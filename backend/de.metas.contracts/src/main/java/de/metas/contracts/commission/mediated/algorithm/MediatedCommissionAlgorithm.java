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

package de.metas.contracts.commission.mediated.algorithm;

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
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.contracts.commission.model.I_C_MediatedCommissionSettings;
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

public class MediatedCommissionAlgorithm implements CommissionAlgorithm
{
	private static final Logger logger = LogManager.getLogger(MediatedCommissionAlgorithm.class);

	@Override
	public ImmutableList<CommissionShare> createCommissionShares(@NonNull final CreateCommissionSharesRequest request)
	{
		final List<MediatedCommissionConfig> mediatedCommissionConfigs = request.getConfigs().stream()
				.filter(MediatedCommissionConfig::isInstance)
				.map(MediatedCommissionConfig::cast)
				.collect(ImmutableList.toImmutableList());

		Check.assume(mediatedCommissionConfigs.size() == 1, "There is always only one MediatedCommissionConfig involved!");
		Check.assume(CommissionTriggerType.MediatedOrder.equals(request.getTrigger().getCommissionTriggerData().getTriggerType()), "Trigger document is a mediated order!");

		final MediatedCommissionConfig mediatedCommissionConfig = mediatedCommissionConfigs.get(0);
		final CommissionTrigger commissionTrigger = request.getTrigger();

		if (mediatedCommissionConfig.getContractFor(commissionTrigger.getCustomer().getBPartnerId()) == null)
		{
			//dev-note: just a guard for the future as it will never happen in the current context
			throw new AdempiereException("No contract available for the given vendor!")
					.appendParametersToMessage()
					.setParameter("Vendor.BPartnerId", commissionTrigger.getCustomer().getBPartnerId())
					.setParameter("CommissionConfig", mediatedCommissionConfig);
		}

		final CommissionShare commissionShare = CommissionShare.builder()
				//dev-note: there is no hierarchy on this kind of commission
				.level(HierarchyLevel.ZERO)
				.payer(Payer.of(commissionTrigger.getCustomer().getBPartnerId()))
				.beneficiary(Beneficiary.of(commissionTrigger.getOrgBPartnerId()))
				.config(mediatedCommissionConfig)
				.soTrx(SOTrx.SALES)
				.build();

		createAndAddFacts(commissionShare,
						  commissionTrigger.getCommissionTriggerData().getTimestamp(),
						  commissionTrigger.getCommissionTriggerData().getForecastedBasePoints(),
						  commissionTrigger.getCommissionTriggerData().getInvoiceableBasePoints(),
						  commissionTrigger.getCommissionTriggerData().getInvoicedBasePoints());

		return ImmutableList.of(commissionShare);
	}

	@Override
	public void applyTriggerChangeToShares(@NonNull final CommissionTriggerChange change)
	{
		final List<CommissionShare> mediatedCommissionShares = change.getInstanceToUpdate()
				.getShares()
				.stream()
				.filter(share -> MediatedCommissionConfig.isInstance(share.getConfig()))
				.collect(ImmutableList.toImmutableList());

		final CommissionTriggerData newTriggerData = change.getNewCommissionTriggerData();

		Check.assume(mediatedCommissionShares.size() == 1, "There is always only one mediated CommissionShare involved!");
		Check.assume(CommissionTriggerType.MediatedOrder.equals(newTriggerData.getTriggerType()), "Trigger document is a mediated order!");

		createAndAddFacts(mediatedCommissionShares.get(0),
						  newTriggerData.getTimestamp(),
						  newTriggerData.getForecastedBasePoints(),
						  newTriggerData.getInvoiceableBasePoints(),
						  newTriggerData.getInvoicedBasePoints());
	}

	private void createAndAddFacts(
			@NonNull final CommissionShare commissionShare,
			@NonNull final Instant timestamp,
			@NonNull final CommissionPoints forecastedBase,
			@NonNull final CommissionPoints toInvoiceBase,
			@NonNull final CommissionPoints invoicedBase)
	{
		try (final MDC.MDCCloseable shareMDC = TableRecordMDC.putTableRecordReference(I_C_Commission_Share.Table_Name, commissionShare.getId()))
		{
			Check.assume(MediatedCommissionConfig.isInstance(commissionShare.getConfig()), "The commission share is always carrying a mediated commission config at this stage!");

			final MediatedCommissionConfig mediatedCommissionConfig = MediatedCommissionConfig.cast(commissionShare.getConfig());

			try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(I_C_MediatedCommissionSettings.Table_Name, mediatedCommissionConfig.getId()))
			{
				logger.debug("Create commission shares and facts");

				final Function<CommissionPoints, CommissionPoints> computeSalesRepCommissionPoints = (basePoints) ->
						basePoints.computePercentageOf(mediatedCommissionConfig.getCommissionPercent(), mediatedCommissionConfig.getPointsPrecision());

				final CommissionPoints forecastCP = computeSalesRepCommissionPoints.apply(forecastedBase);
				final CommissionPoints toInvoiceCP = computeSalesRepCommissionPoints.apply(toInvoiceBase);
				final CommissionPoints invoicedCP = computeSalesRepCommissionPoints.apply(invoicedBase);

				final Optional<CommissionFact> forecastedFact = CommissionFact.createFact(timestamp, CommissionState.FORECASTED, forecastCP, commissionShare.getForecastedPointsSum());
				final Optional<CommissionFact> toInvoiceFact = CommissionFact.createFact(timestamp, CommissionState.INVOICEABLE, toInvoiceCP, commissionShare.getInvoiceablePointsSum());
				final Optional<CommissionFact> invoicedFact = CommissionFact.createFact(timestamp, CommissionState.INVOICED, invoicedCP, commissionShare.getInvoicedPointsSum());

				forecastedFact.ifPresent(commissionShare::addFact);
				toInvoiceFact.ifPresent(commissionShare::addFact);
				invoicedFact.ifPresent(commissionShare::addFact);
			}
		}
	}
}
