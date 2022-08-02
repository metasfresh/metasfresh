package de.metas.invoicecandidate.api.impl.aggregationEngine;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.service.impl.BPartnerStatisticsUpdater;
import de.metas.greeting.GreetingRepository;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.impl.AggregationEngine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * This abstract class implements one generic test-scenario (see method {@link #testStandardScenario()}) and declared a number of methods that need to be implemented by the actual test cases.
 *
 * Tests from {@link I_C_Invoice_Candidate}s to {@link IInvoiceHeader}s.
 */
public abstract class AbstractNewAggregationEngineTests extends AbstractAggregationEngineTestBase
{

	protected static final String IC_PO_REFERENCE = "ic-POReference";

	protected static final LocalDate IC_DATE_ACCT = LocalDate.of(2015, Month.JANUARY, 15); // task 08437

	protected static final BigDecimal FIVE = new BigDecimal("5");
	protected static final BigDecimal TEN = new BigDecimal("10");
	protected static final BigDecimal TWENTY = new BigDecimal("20");
	protected static final BigDecimal THIRTY = new BigDecimal("30");
	protected static final BigDecimal FIFTY = new BigDecimal("50");
	protected static final BigDecimal HUNDRET = new BigDecimal("100");
	protected static final BigDecimal TWO_HUNDRET = new BigDecimal("200");
	protected static final BigDecimal FIVE_HUNDRET = new BigDecimal("500");
	protected static final BigDecimal THOUSAND = new BigDecimal("1000");

	@Override
	public void init()
	{
		registerModelInterceptors();

		final BPartnerStatisticsUpdater asyncBPartnerStatisticsUpdater = new BPartnerStatisticsUpdater();
		Services.registerService(IBPartnerStatisticsUpdater.class, asyncBPartnerStatisticsUpdater);
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));
	}

	@Test
	public final void testStandardScenario()
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = step_createInvoiceCandidates();
		final List<I_M_InOutLine> inOutLines = step_createInOutLines(invoiceCandidates);

		updateInvalidCandidates();
		refreshInvoiceCandidates(invoiceCandidates);

		step_updateInvoiceCandidates(invoiceCandidates, inOutLines);

		updateInvalidCandidates();
		refreshInvoiceCandidates(invoiceCandidates);

		step_validate_before_aggregation(invoiceCandidates, inOutLines);

		final AggregationEngine engine = AggregationEngine.newInstance();
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			engine.addInvoiceCandidate(ic);
		}
		final List<IInvoiceHeader> invoices = invokeAggregationEngine(engine);

		step_validate_after_aggregation(invoiceCandidates, inOutLines, invoices);
	}

	private void refreshInvoiceCandidates(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			InterfaceWrapperHelper.refresh(ic);
		}
	}

	protected abstract List<I_C_Invoice_Candidate> step_createInvoiceCandidates();

	protected abstract List<I_M_InOutLine> step_createInOutLines(List<I_C_Invoice_Candidate> invoiceCandidates);

	/**
	 * Does nothing; override if you need to do something with the ICs after the inoutLines were created. Afterwards, the ICs will be updated/revalidated once again.
	 *
	 * @param invoiceCandidates
	 * @param inOutLines
	 */
	protected void step_updateInvoiceCandidates(List<I_C_Invoice_Candidate> invoiceCandidates, List<I_M_InOutLine> inOutLines)
	{
		// nothing; override if you need to do something with the ICs after the inoutLines were created
	}

	/**
	 * PErform guard tests before the actual call to the aggregation engine under test is made.
	 *
	 * @param invoiceCandidates
	 * @param inOutLines
	 */
	protected abstract void step_validate_before_aggregation(List<I_C_Invoice_Candidate> invoiceCandidates, List<I_M_InOutLine> inOutLines);

	protected abstract void step_validate_after_aggregation(List<I_C_Invoice_Candidate> invoiceCandidates, List<I_M_InOutLine> inOutLines, List<IInvoiceHeader> invoices);

	/**
	 * Helper method, to be called from the {@link #step_validate_after_aggregation(List, List, List)} methods of our subclasses. <br>
	 * Validate the IC<->IL qty allocation, This is relevant because aggregate.getAllocatedQty() is used to create the C_Invoice_Line_allocation records which in turn are used to compute the invoice
	 * candidate's QtyInvoiced value. And this QtyInvoiced decides if and when the IC is flagged as processed (fully invoiced).
	 *
	 * @param ic
	 * @param invoice
	 * @param invoiceLine
	 * @param expectedAllocatedQty
	 */
	protected final void validateIcIlAllocationQty(
			final I_C_Invoice_Candidate ic,
			final IInvoiceHeader invoice,
			final IInvoiceLineRW invoiceLine,
			final BigDecimal expectedAllocatedQty)
	{
		final List<IInvoiceCandAggregate> aggregates = invoice.getLines();

		IInvoiceCandAggregate aggregateForLine = null;
		for (final IInvoiceCandAggregate aggregate : aggregates)
		{
			if (aggregate.getLinesFor(ic).contains(invoiceLine))
			{
				assertThat("This verification code can handle only one aggregate for ic=" + ic + " and invoiceLine=" + invoiceLine,
						aggregateForLine, nullValue());
				aggregateForLine = aggregate;
			}
		}
		assertThat(aggregateForLine, notNullValue());

		final List<I_C_Invoice_Candidate> candsForInvoiceLine1 = aggregateForLine.getCandsFor(invoiceLine);
		assertThat(candsForInvoiceLine1.size(), is(1));
		assertThat(candsForInvoiceLine1.get(0), is(ic));

		final StockQtyAndUOMQty qtyInvoiced = aggregateForLine.getAllocatedQty(ic, invoiceLine);
		assertThat(qtyInvoiced.getStockQty().toBigDecimal(), comparesEqualTo(expectedAllocatedQty));
	}
}
