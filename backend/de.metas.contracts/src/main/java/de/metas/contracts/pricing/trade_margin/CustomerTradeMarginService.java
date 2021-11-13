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

package de.metas.contracts.pricing.trade_margin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomerTradeMarginService
{

	private static final Logger logger = LogManager.getLogger(CustomerTradeMarginService.class);

	private final CustomerTradeMarginRepository customerTradeMarginRepository;

	public CustomerTradeMarginService(final CustomerTradeMarginRepository customerTradeMarginRepository)
	{
		this.customerTradeMarginRepository = customerTradeMarginRepository;
	}

	public Optional<CustomerTradeMarginSettings> getCustomerTradeMarginForCriteria(final CustomerTradeMarginSearchCriteria customerTradeMarginSearchCriteria)
	{
		return customerTradeMarginRepository.getBestMatchForCriteria(customerTradeMarginSearchCriteria);
	}

	public Map<CommissionShare, CommissionPoints> getTradedCommissionPointsFor(
			@NonNull final CustomerTradeMarginSettings customerTradeMarginSettings,
			@NonNull final ImmutableList<CommissionShare> commissionShares)
	{
		try (final MDCCloseable methodMDC = MDC.putCloseable("method", "CustomerTradeMarginService.getTradedCommissionPointsFor"))
		{
			ImmutableMap.Builder<CommissionShare, CommissionPoints> result = ImmutableMap.builder();

			for (final CommissionShare commissionShare : commissionShares)
			{
				try (final MDCCloseable commissionShareMDC = TableRecordMDC.putTableRecordReference(I_C_Commission_Share.Table_Name, commissionShare.getId()))
				{
					if (!HierarchyLevel.ZERO.equals(commissionShare.getLevel()))
					{
						logger.debug("commission-share has level=0; -> skipping");
						continue;
					}

					if (!BPartnerId.equals(commissionShare.getBeneficiary().getBPartnerId(), customerTradeMarginSettings.getSalesRepId()))
					{
						logger.debug("commission-share has beneficiary-bpartner-id={} which is != {}; -> skipping",
								commissionShare.getBeneficiary().getBPartnerId().getRepoId(), customerTradeMarginSettings.getSalesRepId().getRepoId());
						continue;
					}

					final CommissionPoints salesRepCommissionPoints = commissionShare.getForecastedPointsSum();
					if (salesRepCommissionPoints.isZero())
					{
						logger.debug("commission-share has zero forecasted commission points; -> skipping");
						continue;
					}

					final CommissionContract commissionContract = commissionShare.getContract();
					if (!HierarchyContract.isInstance(commissionContract))
					{
						logger.debug("commission-share's contract is not a HierarchyContract; -> skipping; commissionContract={}", commissionContract);
						continue;
					}

					final int commissionPointsPrecision = HierarchyContract.cast(commissionContract).getPointsPrecision();

					final CommissionPoints tradedCommissionPoints = salesRepCommissionPoints
							.computePercentageOf(
									Percent.of(customerTradeMarginSettings.getMarginPercent()),
									commissionPointsPrecision);

					logger.debug("put commission-share with tradedCommissionPoints={} to the overall result", tradedCommissionPoints);
					result.put(commissionShare, tradedCommissionPoints);
				}
			}

			return result.build();
		}
	}
}
