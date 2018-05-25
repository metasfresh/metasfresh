package de.metas.contracts.refund.grossprofit;

import java.util.Optional;

import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundContractQuery;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.money.Money;
import de.metas.money.grossprofit.GrossProfitComponent;
import de.metas.money.grossprofit.GrossProfitComputeRequest;
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

public class RefundGrossProfitComponent implements GrossProfitComponent
{
	private final GrossProfitComputeRequest request;
	private final RefundContractRepository refundContractRepository;

	public RefundGrossProfitComponent(
			@NonNull final GrossProfitComputeRequest request,
			@NonNull final RefundContractRepository refundContractRepository)
	{
		this.request = request;
		this.refundContractRepository = refundContractRepository;
	}

	@Override
	public Money applyToInput(@NonNull final Money input)
	{
		final RefundContractQuery query = RefundContractQuery.of(request);
		final Optional<RefundContract> refundContract = refundContractRepository.getByQuery(query);

		if (!refundContract.isPresent())
		{
			return input;
		}

		final Money percentage = input.percentage(refundContract
				.get()
				.getRefundConfig()
				.getPercent());

		return input.subtract(percentage);
	}

}
