package de.metas.purchasecandidate.grossprofit;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.metas.money.Money;
import de.metas.money.grossprofit.GrossProfitPriceFactory;
import de.metas.order.OrderLineId;
import de.metas.order.grossprofit.OrderLineWithGrossProfitPriceRepository;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
public class PurchaseProfitInfoFactory
{
	// services
	private final OrderLineWithGrossProfitPriceRepository grossProfitPriceRepo;
	private final GrossProfitPriceFactory grossProfitPriceFactory;

	public PurchaseProfitInfoFactory(
			@NonNull final OrderLineWithGrossProfitPriceRepository grossProfitPriceRepo,
			@NonNull final GrossProfitPriceFactory grossProfitPriceFactory)
	{
		this.grossProfitPriceRepo = grossProfitPriceRepo;
		this.grossProfitPriceFactory = grossProfitPriceFactory;
	}

	public Optional<Money> retrieveSalesNetPrice(@NonNull final Collection<OrderLineId> salesOrderLineIds)
	{
		return grossProfitPriceRepo.getProfitMinBasePrice(salesOrderLineIds);
	}
}
