package de.metas.fresh.api.invoicecandidate.impl;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.StartupListener;
import de.metas.currency.CurrencyRepository;
import de.metas.fresh.invoicecandidate.spi.impl.FreshQuantityDiscountAggregator;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.impl.aggregationEngine.TestTwoReceiptsOneInvoice_QualityDiscount1;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.money.MoneyService;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Similar to its super class {@link TestTwoReceiptsOneInvoice_QualityDiscount1}, but uses the {@link FreshQuantityDiscountAggregator} instead of the default aggregator.<br>
 * The setup is the same, but the expectations are different:
 * <ul>
 * <li>Return two invoice lines, the second one is dedicated to the in-dispute-iol
 * <li>The first invoice line aggregates both the "normal" iol11, iol21 and the in-dispute-iol22, with the qtys of <b>all</b> lines.
 * <li>The second line then has a qty of ONE and the negative net amount that goes back to iol22.
 * <ul>
 *
 * @task 08507
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, /* ShutdownListener.class,*/ MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class TestFreshTwoReceiptssOneInvoice_FreshQualityDiscount1 extends TestTwoReceiptsOneInvoice_QualityDiscount1
{
	private I_C_Invoice_Candidate_Agg freshAgg;

	@Override
	protected final void config_InvoiceCand_LineAggregation(final Properties ctx, final String trxName)
	{
		freshAgg = FreshAggregationTestHelper.configFreshAggregator(ctx, trxName);
	}

	@Override
	protected void step_validate_before_aggregation(@NonNull List<I_C_Invoice_Candidate> invoiceCandidates, @NonNull List<I_M_InOutLine> ignored)
	{
		super.step_validate_before_aggregation(invoiceCandidates, ignored);

		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		assertThat(ic.getC_Invoice_Candidate_Agg(), is(freshAgg));
	}

	@Override
	protected void step_validate_after_aggregation(List<I_C_Invoice_Candidate> invoiceCandidates, List<I_M_InOutLine> inOutLines, List<IInvoiceHeader> invoices)
	{
		assertEquals("We are expecting one invoice: " + invoices, 1, invoices.size());
		final IInvoiceHeader invoice = invoices.remove(0);

		final I_C_Invoice_Candidate ic = CollectionUtils.singleElement(invoiceCandidates);

		final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
		assertEquals("We are expecting two invoice lines: " + invoiceLines, 2, invoiceLines.size());

		// checking that the first il has the full quantity which includes the qtys that are in dispute
		final IInvoiceLineRW invoiceLine1 = getSingleForInOutLine(invoiceLines, iol11_three);
		assertNotNull("Missing IInvoiceLineRW for iol11=" + iol11_three, invoiceLine1);

		final BigDecimal qtyDeliveredInclDisputed = THREE.add(FIVE).add(TEN).add(TWENTY);
		final BigDecimal qtyDisputed = FIVE.add(TWENTY);

		assertThat(invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(qtyDeliveredInclDisputed));
		assertThat(invoiceLine1.getNetLineAmt().toBigDecimal(), comparesEqualTo(qtyDeliveredInclDisputed.multiply(TEN)));
		validateIcIlAllocationQty(ic, invoice, invoiceLine1, qtyDeliveredInclDisputed);

//		final I_C_InvoiceCandidate_InOutLine icIol11 = invoiceCandidateInOutLine(ic, iol11_three);
//		assertThat(icIol11.getQtyInvoiced(), comparesEqualTo(THREE)); // as of task 08529 we got rid of I_C_InvoiceCandidate_InOutLine.QtyInvoiced

//		final I_C_InvoiceCandidate_InOutLine icIol21 = invoiceCandidateInOutLine(ic, iol21_ten);
//		assertThat(icIol21.getQtyInvoiced(), comparesEqualTo(TEN)); // as of task 08529 we got rid of I_C_InvoiceCandidate_InOutLine.QtyInvoiced

		assertThat(getSingleForInOutLine(invoiceLines, iol11_three), is(invoiceLine1)); // we already know this, explicitly checking just for completeness
		assertThat(getSingleForInOutLine(invoiceLines, iol21_ten), is(invoiceLine1));

		final List<IInvoiceLineRW> forIol22 = getForInOutLine(invoiceLines, iol22_twenty_disp);
		assertThat(forIol22, hasItems(invoiceLine1));

		forIol22.remove(invoiceLine1); // remove the one we already validated

		// also verify that 'iol12_five_disp' is associated to both invoiceLines 1 and 2
		assertThat(getForInOutLine(invoiceLines, iol22_twenty_disp), hasItems(invoiceLine1));

		final IInvoiceLineRW invoiceLine2 = forIol22.get(0);

		assertThat(invoiceLine2.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(qtyDisputed.negate()));
		assertThat(invoiceLine2.getPriceActual().toMoney().toBigDecimal(), comparesEqualTo(BigDecimal.ONE));
		assertThat(invoiceLine2.getNetLineAmt().toBigDecimal(), comparesEqualTo(qtyDisputed.negate().multiply(TEN)));
		validateIcIlAllocationQty(ic, invoice, invoiceLine2, qtyDisputed.negate());

//		final I_C_InvoiceCandidate_InOutLine icIol12 = invoiceCandidateInOutLine(ic, iol12_five_disp);
//		assertThat("from the iol in dispute, no Qty shall be invoiced", icIol12.getQtyInvoiced(), comparesEqualTo(BigDecimal.ZERO)); // as of task 08529 we got rid of I_C_InvoiceCandidate_InOutLine.QtyInvoiced

//		final I_C_InvoiceCandidate_InOutLine icIol22 = invoiceCandidateInOutLine(ic, iol22_twenty_disp);
//		assertThat("from the iol in dispute, no Qty shall be invoiced", icIol22.getQtyInvoiced(), comparesEqualTo(BigDecimal.ZERO)); // as of task 08529 we got rid of I_C_InvoiceCandidate_InOutLine.QtyInvoiced
	}
}
