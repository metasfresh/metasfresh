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
import de.metas.invoicecandidate.api.impl.aggregationEngine.TestQualityDiscountPercentOverrideToZero;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg;
import de.metas.money.MoneyService;
import lombok.NonNull;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Checks the {@link FreshQuantityDiscountAggregator} when using {@link I_C_Invoice_Candidate#setQualityDiscountPercent_Override(BigDecimal)}, to "ignore" an in-dispute line (i.e set
 * QualityDiscount_Override to zero). Also see {@link TestQualityDiscountPercentOverrideToZero}.
 * <p>
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, /* ShutdownListener.class,*/ MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class TestFreshQualityDiscountPercentOverrideToZero extends TestQualityDiscountPercentOverrideToZero
{
	private I_C_Invoice_Candidate_Agg freshAgg;

	@Override
	protected final void config_InvoiceCand_LineAggregation(final Properties ctx, final String trxName)
	{
		freshAgg = FreshAggregationTestHelper.configFreshAggregator(ctx, trxName);
	}

	@Override
	protected void step_validate_before_aggregation(final @NonNull List<I_C_Invoice_Candidate> invoiceCandidates, final @NonNull List<I_M_InOutLine> ignored)
	{
		super.step_validate_before_aggregation(invoiceCandidates, ignored);

		// Make sure it's using our aggregator
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);
		assertThat(ic.getC_Invoice_Candidate_Agg(), is(freshAgg));
	}

	/**
	 * expecting no difference to the base class.
	 */
	@Override
	protected void step_validate_after_aggregation(final @NonNull List<I_C_Invoice_Candidate> invoiceCandidates, final @NonNull List<I_M_InOutLine> inOutLines, final @NonNull List<IInvoiceHeader> invoices)
	{
		super.step_validate_after_aggregation(invoiceCandidates, inOutLines, invoices);
	}
}
