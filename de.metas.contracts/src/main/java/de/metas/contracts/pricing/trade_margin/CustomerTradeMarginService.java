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
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyContract;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.SalesCommissionShare;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerTradeMarginService
{
	private final CustomerTradeMarginRepository customerTradeMarginRepository;

	public CustomerTradeMarginService(final CustomerTradeMarginRepository customerTradeMarginRepository)
	{
		this.customerTradeMarginRepository = customerTradeMarginRepository;
	}

	public Optional<CustomerTradeMarginSettings> getCustomerTradeMarginForCriteria(final CustomerTradeMarginSearchCriteria customerTradeMarginSearchCriteria)
	{
		return customerTradeMarginRepository.getBestMatchForCriteria(customerTradeMarginSearchCriteria);
	}

	public Optional<CommissionPoints> getTradedCommissionPointsFor( @NonNull final CustomerTradeMarginSettings customerTradeMarginSettings,
													          		@NonNull final ImmutableList<SalesCommissionShare> commissionShares,
			                                                  		@NonNull final CommissionContract commissionContract )
	{
		final Optional<SalesCommissionShare> salesCommissionShare = commissionShares
				.stream()
				.filter(share -> BPartnerId.equals( share.getBeneficiary().getBPartnerId(), customerTradeMarginSettings.getSalesRepId() ) )
				.findFirst();

		if ( !salesCommissionShare.isPresent() )
		{
			return Optional.empty();
		}

		final CommissionPoints salesRepCommissionPoints = salesCommissionShare.get().getForecastedPointsSum();

		if ( salesRepCommissionPoints.isZero() )
		{
			return Optional.empty();
		}

		final int commissionPointsPrecision = HierarchyContract.cast(commissionContract).getPointsPrecision();

		final CommissionPoints tradedCommissionPoints = salesRepCommissionPoints
				.computePercentageOf(
					Percent.of( customerTradeMarginSettings.getMarginPercent() ),
					commissionPointsPrecision );

		return Optional.of(tradedCommissionPoints);
	}
}
