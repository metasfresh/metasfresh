package de.metas.contracts.refund.grossprofit;

import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundContractQuery;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.lang.Percent;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import de.metas.money.grossprofit.ProfitPriceActualComponent;
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

public class RefundProfitPriceActualComponent implements ProfitPriceActualComponent
{
	private final CalculateProfitPriceActualRequest request;
	private final RefundContractRepository refundContractRepository; // TODO: take out the repo/service from here !
	private final MoneyService moneyService;

	public RefundProfitPriceActualComponent(
			@NonNull final CalculateProfitPriceActualRequest request,
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final MoneyService moneyService)
	{
		this.request = request;
		this.refundContractRepository = refundContractRepository;
		this.moneyService = moneyService;
	}

	@Override
	public Money applyToInput(@NonNull final Money input)
	{
		final RefundContractQuery query = RefundContractQuery.of(request);

		final RefundConfig refundConfig = refundContractRepository
				.getByQuery(query)
				.flatMap(RefundContract::getRefundConfigToUseProfitCalculation)
				.orElse(null);

		if (refundConfig == null)
		{
			return input;
		}

		if (RefundBase.AMOUNT_PER_UNIT.equals(refundConfig.getRefundBase()))
		{
			final Money amountPerUnit = refundConfig.getAmount();
			return input.subtract(amountPerUnit);
		}

		final Percent percent = refundConfig.getPercent();
		return moneyService.subtractPercent(percent, input);
	}
}
