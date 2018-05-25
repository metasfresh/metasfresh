package de.metas.order.grossprofit.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.money.Money;
import de.metas.money.grossprofit.GrossProfitComputeRequest;
import de.metas.money.grossprofit.GrossProfitPrice;
import de.metas.money.grossprofit.GrossProfitPriceFactory;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineRepository;
import de.metas.order.grossprofit.GrossProfitComputeRequestCreator;
import de.metas.order.grossprofit.model.I_C_OrderLine;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

@Component
@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	private final GrossProfitPriceFactory grossProfitPriceFactory;
	private final OrderLineRepository orderLineRepository;

	public C_OrderLine(
			@NonNull final GrossProfitPriceFactory grossProfitPriceFactory,
			@NonNull final OrderLineRepository orderLineRepository)
	{
		this.orderLineRepository = orderLineRepository;
		this.grossProfitPriceFactory = grossProfitPriceFactory;
	}

	@ModelChange(//
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_C_OrderLine.COLUMNNAME_PriceActual)
	public void updateGrossMarginPrice(@NonNull final I_C_OrderLine orderLineRecord)
	{
		final OrderLine orderLine = orderLineRepository.ofRecord(orderLineRecord);
		final GrossProfitComputeRequest grossProfitComputeRequest = //
				GrossProfitComputeRequestCreator.of(orderLine);

		final GrossProfitPrice grossProfitPrice = //
				grossProfitPriceFactory.createGrossProfitPrice(grossProfitComputeRequest);

		final Money profitPrice = grossProfitPrice.compute();
		orderLineRecord.setPriceGrossProfit(profitPrice.getValue());
	}
}
