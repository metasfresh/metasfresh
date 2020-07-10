package de.metas.contracts.refund.grossprofit;

import org.springframework.stereotype.Service;

import de.metas.contracts.refund.RefundContractRepository;
import de.metas.money.MoneyService;
import de.metas.money.grossprofit.ProfitPriceActualComponent;
import de.metas.money.grossprofit.ProfitPriceActualComponentProvider;
import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class RefundProfitPriceActualComponentProvider implements ProfitPriceActualComponentProvider
{
	private final RefundContractRepository refundContractRepository;
	private final MoneyService moneyService;

	public RefundProfitPriceActualComponentProvider(
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final MoneyService moneyService)
	{
		this.refundContractRepository = refundContractRepository;
		this.moneyService = moneyService;
	}

	@Override
	public ProfitPriceActualComponent provideForRequest(@NonNull final CalculateProfitPriceActualRequest request)
	{
		return new RefundProfitPriceActualComponent(request, refundContractRepository, moneyService);
	}
}
