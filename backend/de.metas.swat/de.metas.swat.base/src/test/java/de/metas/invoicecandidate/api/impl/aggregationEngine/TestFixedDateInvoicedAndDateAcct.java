package de.metas.invoicecandidate.api.impl.aggregationEngine;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoicecandidate.C_Invoice_Candidate_Builder;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.impl.AggregationEngine;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.X_C_PaymentTerm;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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

public class TestFixedDateInvoicedAndDateAcct extends AbstractAggregationEngineTestBase
{
	@Override
	public void init()
	{
		super.init();
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
	}

	private C_Invoice_Candidate_Builder prepareInvoiceCandidate()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		return createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setSOTrx(true);
	}

	/**
	 * Verifies that the "param" dateInvoiced takes precedence over the IC's "preset" dateInvoiced.
	 */
	@Test
	public void test_using_dateInvoicedParam()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate()
				.setPresetDateInvoiced(LocalDate.of(2019, Month.SEPTEMBER, 13))
				.build();

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);

		final AggregationEngine engine = AggregationEngine.builder()
				.matchInvoiceService(MatchInvoiceService.newInstanceForUnitTesting())
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getDateInvoiced()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 1));
		assertThat(invoice.getDateAcct()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 13));
	}

	@Test
	public void test_using_defaultDateInvoiced_and_defaultDateAcct()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate().build();

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);

		final AggregationEngine engine = AggregationEngine.builder()
				.matchInvoiceService(MatchInvoiceService.newInstanceForUnitTesting())
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.dateAcctParam(LocalDate.of(2019, Month.SEPTEMBER, 2))
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getDateInvoiced()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 1));
		assertThat(invoice.getDateAcct()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 2));
	}

	/**
	 * Verifies that the IC's "preset" dateInvoiced takes precedence over the IC's dateInvoiced.
	 */
	@Test
	public void test_using_presetDateInvoiced()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate()
				.setPresetDateInvoiced(LocalDate.of(2019, Month.SEPTEMBER, 13))
				.setDateInvoiced(LocalDate.of(2019, Month.SEPTEMBER, 14))
				.setDateAcct(LocalDate.of(2019, Month.SEPTEMBER, 1)) // dateInvoiced shall take precedence!
				.build();

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);

		final AggregationEngine engine = AggregationEngine.builder()
				.matchInvoiceService(MatchInvoiceService.newInstanceForUnitTesting())
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 13))
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getDateInvoiced()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 13));
		assertThat(invoice.getDateAcct()).isEqualTo(LocalDate.of(2019, Month.SEPTEMBER, 13));
	}

	/**
	 * Verifies that the "param" DueDateOverride is used
	 */
	@Test
	public void test_using_dateDateDueOverrideParam()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate()
				.build();

		final int paymentTermId = createPaymentTerm();
		ic1.setC_PaymentTerm_ID(paymentTermId);
		InterfaceWrapperHelper.save(ic1);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);

		final AggregationEngine engine = AggregationEngine.builder()
				.matchInvoiceService(MatchInvoiceService.newInstanceForUnitTesting())
				.overrideDueDateParam(LocalDate.of(2023, Month.FEBRUARY, 1))
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getOverrideDueDate()).isEqualTo(LocalDate.of(2023, Month.FEBRUARY, 1));

	}

	private int createPaymentTerm()
	{
		I_C_PaymentTerm pt = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		pt.setC_PaymentTerm_ID(100);
		pt.setIsAllowOverrideDueDate(true);
		pt.setCalculationMethod(X_C_PaymentTerm.CALCULATIONMETHOD_BaseLineDatePlusXDays);
		pt.setBaseLineType(X_C_PaymentTerm.BASELINETYPE_InvoiceDate);
		InterfaceWrapperHelper.save(pt);

		return pt.getC_PaymentTerm_ID();
	}
}
