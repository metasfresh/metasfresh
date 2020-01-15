package de.metas.contracts.commission.commissioninstance.services.repos;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

	CommissionPoints extractForecastCommissionPoints(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final CommissionPoints forecastCommissionPoints;

		final ProductPrice priceActual = Services.get(IInvoiceCandBL.class).getPriceActual(icRecord);
		final BigDecimal baseCommissionPointsPerPriceUOM = icRecord.getBase_Commission_Ponits_Per_Price_UOM();

		final BigDecimal forecastQtyInPriceUOM = icRecord.getQtyEntered()
				.subtract( icRecord.getQtyToInvoiceInUOM() )
				.subtract( icRecord.getQtyInvoicedInUOM() );

		if (baseCommissionPointsPerPriceUOM.signum() > 0)
		{
			final BigDecimal forecastCommissionPointsAmount = baseCommissionPointsPerPriceUOM.multiply(forecastQtyInPriceUOM);
			forecastCommissionPoints = CommissionPoints.of(forecastCommissionPointsAmount);
		}
		else
		{
			final Money forecastNetAmt = moneyService.multiply(
					Quantitys.create(forecastQtyInPriceUOM, UomId.ofRepoId(icRecord.getC_UOM_ID())),
					priceActual);

			forecastCommissionPoints = CommissionPoints.of( forecastNetAmt.toBigDecimal() );
		}

		return forecastCommissionPoints;
	}

	CommissionPoints extractCommissionPointsToInvoice(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final CommissionPoints commissionPointsToInvoice;
		final BigDecimal baseCommissionPointsPerPriceUOM = icRecord.getBase_Commission_Ponits_Per_Price_UOM();

		if (baseCommissionPointsPerPriceUOM.signum() > 0)
		{
			commissionPointsToInvoice = CommissionPoints.of( baseCommissionPointsPerPriceUOM.multiply( icRecord.getQtyToInvoiceInUOM() ) );
		}
		else
		{
			commissionPointsToInvoice = CommissionPoints.of( icRecord.getNetAmtToInvoice() );
		}

		return commissionPointsToInvoice;
	}

	CommissionPoints extractInvoicedCommissionPoints(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final CommissionPoints commissionPointsToInvoice;
		final BigDecimal baseCommissionPointsPerPriceUOM = icRecord.getBase_Commission_Ponits_Per_Price_UOM();

		if (baseCommissionPointsPerPriceUOM.signum() > 0)
		{
			commissionPointsToInvoice = CommissionPoints.of( baseCommissionPointsPerPriceUOM.multiply( icRecord.getQtyInvoicedInUOM() ) );
		}
		else
		{
			commissionPointsToInvoice = CommissionPoints.of( icRecord.getNetAmtInvoiced() );
		}

		return commissionPointsToInvoice;
	}

	Percent extractTradedCommissionPercent(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		return Percent.of( icRecord.getTraded_Commission_Percent() );
	}
}
