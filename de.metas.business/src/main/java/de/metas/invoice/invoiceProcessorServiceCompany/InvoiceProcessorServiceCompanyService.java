package de.metas.invoice.invoiceProcessorServiceCompany;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.InvoiceId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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
public class InvoiceProcessorServiceCompanyService
{
	private final InvoiceProcessorServiceCompanyConfigRepository configRepository;
	private final CurrencyRepository currencyRepository;

	public InvoiceProcessorServiceCompanyService(
			@NonNull final InvoiceProcessorServiceCompanyConfigRepository configRepository,
			@NonNull final CurrencyRepository currencyRepository)
	{
		this.configRepository = configRepository;
		this.currencyRepository = currencyRepository;
	}

	public Optional<InvoiceProcessorFeeCalculation> computeFee(@NonNull final InvoiceProcessorFeeComputeRequest request)
	{
		final BPartnerId customerId = request.getCustomerId();
		final InvoiceId invoiceId = request.getInvoiceId();
		final Amount invoiceGrandTotal = request.getInvoiceGrandTotal();

		final InvoiceProcessorServiceCompanyConfig config = configRepository.getByCustomerId(customerId).orElse(null);
		if (config == null)
		{
			return Optional.empty();
		}

		final CurrencyPrecision precision = currencyRepository.getStdPrecision(invoiceGrandTotal.getCurrencyCode());
		final Amount feeAmountIncludingTax = invoiceGrandTotal.multiply(config.getFeePercentageOfGrandTotal(), precision);

		return Optional.of(InvoiceProcessorFeeCalculation.builder()
				.customerId(customerId)
				.invoiceId(invoiceId)
				.invoiceGrandTotal(invoiceGrandTotal)
				//
				.serviceCompanyBPartnerId(config.getServiceCompanyBPartnerId())
				.serviceFeeProductId(config.getServiceFeeProductId())
				.feeAmountIncludingTax(feeAmountIncludingTax)
				//
				.build());
	}
}
