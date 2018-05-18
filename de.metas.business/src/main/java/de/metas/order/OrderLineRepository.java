package de.metas.order;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.bpartner.BPartnerId;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
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

@Repository
public class OrderLineRepository
{
	public OrderLine getById(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLineRecord = load(orderLineId.getRepoId(), I_C_OrderLine.class);
		return ofRecord(orderLineRecord);
	}

	public OrderLine ofRecord(@NonNull final I_C_OrderLine orderLineRecord)
	{
		return OrderLine.builder()
				.id(OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID()))
				.orderId(OrderId.ofRepoId(orderLineRecord.getC_Order_ID()))
				.bPartnerId(BPartnerId.ofRepoId(orderLineRecord.getC_BPartner_ID()))
				.datePromised(TimeUtil.asLocalDate(orderLineRecord.getDatePromised()))
				.productId(ProductId.ofRepoId(orderLineRecord.getM_Product_ID()))
				.priceActual(moneyOfRecordsPriceActual(orderLineRecord))
				.build();
	}

	private static Money moneyOfRecordsPriceActual(@NonNull final I_C_OrderLine orderLineRecord)
	{
		final Currency currency = Currency.builder()
				.id(CurrencyId.ofRepoId(orderLineRecord
						.getC_Currency_ID()))
				.precision(orderLineRecord
						.getC_Currency()
						.getStdPrecision())
				.build();

		return Money.of(
				orderLineRecord.getPriceActual(),
				currency);
	}

}
