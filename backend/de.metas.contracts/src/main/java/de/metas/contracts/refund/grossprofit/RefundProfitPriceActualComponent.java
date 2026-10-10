package de.metas.contracts.refund.grossprofit;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundContractQuery;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.packaging.RefundPackagingFilter;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import de.metas.money.grossprofit.ProfitPriceActualComponent;
import de.metas.util.Loggables;
import de.metas.util.lang.Percent;

import lombok.NonNull;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

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
	private static final Logger logger = LogManager.getLogger(RefundProfitPriceActualComponent.class);

	private final CalculateProfitPriceActualRequest request;
	private final RefundContractRepository refundContractRepository; // TODO: take out the repo/service from here !
	private final MoneyService moneyService;
	private final RefundPackagingFilter refundPackagingFilter;

	public RefundProfitPriceActualComponent(
			@NonNull final CalculateProfitPriceActualRequest request,
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final MoneyService moneyService,
			@NonNull final RefundPackagingFilter refundPackagingFilter)
	{
		this.request = request;
		this.refundContractRepository = refundContractRepository;
		this.moneyService = moneyService;
		this.refundPackagingFilter = refundPackagingFilter;
	}

	/**
	 * All matching refund contracts apply: their percentages are summed up and subtracted in one step (and not one after the other, which would compound them), then their amounts per unit are subtracted.
	 * An amount per unit in another currency than the input is left out.
	 */
	@Override
	public Money applyToInput(@NonNull final Money input)
	{
		final RefundContractQuery query = RefundContractQuery.of(request);

		final List<RefundConfig> refundConfigs = refundContractRepository
				.getByQuery(query)
				.stream()
				.filter(contract -> refundPackagingFilter.isIncluded(contract.getConditionsId(), request.getHuPIItemProductId(), request.getBPartnerId()))
				.map(RefundContract::getRefundConfigToUseProfitCalculation)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableList.toImmutableList());

		Percent totalPercent = Percent.ZERO;
		Money amountsPerUnit = null;
		for (final RefundConfig refundConfig : refundConfigs)
		{
			if (refundConfig.isAmountPerUnitInOtherCurrencyThan(input.getCurrencyId()))
			{
				// not converted; its refund invoice candidate shows the error
				Loggables.withLogger(logger, Level.WARN).addLog("Leaving out the amount per unit of {}, which is not in the currency of {}", refundConfig.getId(), input);
			}
			else if (RefundBase.AMOUNT_PER_UNIT.equals(refundConfig.getRefundBase()))
			{
				amountsPerUnit = amountsPerUnit == null ? refundConfig.getAmount() : amountsPerUnit.add(refundConfig.getAmount());
			}
			else
			{
				totalPercent = totalPercent.add(refundConfig.getPercent());
			}
		}

		Money result = input;
		if (!totalPercent.isZero())
		{
			result = moneyService.subtractPercent(totalPercent, result);
		}
		if (amountsPerUnit != null)
		{
			result = result.subtract(amountsPerUnit);
		}
		return result;
	}
}
