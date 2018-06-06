package de.metas.contracts.refund.grossprofit;

import org.springframework.stereotype.Service;

import de.metas.contracts.refund.RefundContractRepository;
import de.metas.money.grossprofit.GrossProfitComputeRequest;
import de.metas.money.grossprofit.GrossProfitComponent;
import de.metas.money.grossprofit.GrossProfitComponentProvider;
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
public class RefundGrossProfitComponentProvider implements GrossProfitComponentProvider
{
private final RefundContractRepository refundContractRepository;

	public RefundGrossProfitComponentProvider(
			@NonNull final RefundContractRepository refundContractRepository)
	{
		this.refundContractRepository = refundContractRepository;
	}

	@Override
	public GrossProfitComponent provideForRequest(@NonNull final GrossProfitComputeRequest request)
	{
		return new RefundGrossProfitComponent(request, refundContractRepository);
	}
}
