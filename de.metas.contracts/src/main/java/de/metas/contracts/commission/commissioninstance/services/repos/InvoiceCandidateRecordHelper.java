package de.metas.contracts.commission.commissioninstance.services.repos;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class InvoiceCandidateRecordHelper
{
	private final MoneyService moneyService;

	public InvoiceCandidateRecordHelper(@NonNull final MoneyService moneyService)
	{
		this.moneyService = moneyService;
	}

	Money extractForecastNetAmt(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final ProductPrice priceActual = Services.get(IInvoiceCandBL.class).getPriceActual(icRecord);

		final BigDecimal forecastQtyInUOM = icRecord.getQtyEntered()
				.subtract(icRecord.getQtyToInvoiceInUOM())
				.subtract(icRecord.getQtyInvoicedInUOM());

		final Money forecastNetAmt = moneyService.multiply(
				Quantitys.create(forecastQtyInUOM, UomId.ofRepoId(icRecord.getC_UOM_ID())),
				priceActual);
		return forecastNetAmt;
	}

	Money extractNetAmtToInvoice(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		return Money.of(icRecord.getNetAmtToInvoice(), CurrencyId.ofRepoId(icRecord.getC_Currency_ID()));
	}

	Money extractInvoicedNetAmt(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		return Money.of(icRecord.getNetAmtInvoiced(), CurrencyId.ofRepoId(icRecord.getC_Currency_ID()));
	}
}
