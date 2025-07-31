/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.invoicecandidate.api.impl.aggregationEngine;

import de.metas.StartupListener;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import lombok.NonNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @see AbstractDoubleReceiptQtyOverride
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { StartupListener.class, /* ShutdownListener.class,*/ MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class TestDoubleShipmentsInvoiceJustOnePartial extends AbstractDoubleReceiptQtyOverride
{
	@Override
	protected void step_validate_after_aggregation(final List<I_C_Invoice_Candidate> ignored1,
												   final List<I_M_InOutLine> ignored2,
												   @NonNull final List<IInvoiceHeader> invoices)
	{
		assertThat(invoices).as("We are expecting one invoice: %s", invoices).hasSize(1);

		final IInvoiceHeader invoice1 = invoices.removeFirst();

		assertThat(invoice1.getPOReference()).isEqualTo(IC_PO_REFERENCE);
		assertThat(invoice1.getDateAcct()).isEqualTo(IC_DATE_ACCT);// task 08437

		assertThat(invoice1.isSOTrx()).isEqualTo(config_IsSOTrx());
		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		assertThat(invoiceLines1).as("We are expecting one invoice line: %s", invoiceLines1).hasSize(1);
	
		final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol111);
		assertThat(il1).as("Missing IInvoiceLineRW for iol111=%s", iol111).isNotNull();
		
		assertThat(il1.getQtysToInvoice().getStockQty().toBigDecimal()).isEqualByComparingTo(config_GetQtyToInvoice_Override());

		final IInvoiceLineRW il2 = getSingleForInOutLine(invoiceLines1, iol121);
		assertThat(il2).as("Unexpected IInvoiceLineRW for iol121=%s", iol121).isNull();
	}

	@Override
	BigDecimal config_GetQtyToInvoice_Override()
	{
		return THIRTY;
	}

}
