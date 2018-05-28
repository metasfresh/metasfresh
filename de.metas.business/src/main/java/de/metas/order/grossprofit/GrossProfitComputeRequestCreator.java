package de.metas.order.grossprofit;

import de.metas.money.grossprofit.GrossProfitComputeRequest;
import de.metas.order.OrderLine;
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

public class GrossProfitComputeRequestCreator
{
	public static GrossProfitComputeRequest of(@NonNull final OrderLine orderLine)
	{
		return GrossProfitComputeRequest.builder()
				.bPartnerId(orderLine.getBPartnerId())
				.productId(orderLine.getProductId())
				.date(orderLine.getDatePromised().toLocalDate())
				.baseAmount(orderLine.getPriceActual())
				.paymentTermId(orderLine.getPaymentTermId())
				.build();
	}
}
