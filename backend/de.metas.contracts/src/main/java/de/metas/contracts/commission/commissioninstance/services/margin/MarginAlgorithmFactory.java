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

package de.metas.contracts.commission.commissioninstance.services.margin;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.commissioninstance.businesslogic.margin.MarginAlgorithm;
import de.metas.contracts.commission.commissioninstance.services.CommissionAlgorithmFactory;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class MarginAlgorithmFactory implements CommissionAlgorithmFactory
{
	@NonNull
	private final CustomerTradeMarginService customerTradeMarginService;

	public MarginAlgorithmFactory(final @NonNull CustomerTradeMarginService customerTradeMarginService)
	{
		this.customerTradeMarginService = customerTradeMarginService;
	}

	@Override
	public CommissionAlgorithm instantiateAlgorithm()
	{
		return new MarginAlgorithm(customerTradeMarginService);
	}

	@Override
	@NonNull
	public CommissionType getSupportedCommissionType()
	{
		return CommissionType.MARGIN_COMMISSION;
	}
}
