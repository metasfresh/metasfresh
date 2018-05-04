package de.metas.invoicecandidate.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.expectations.InOutLineExpectation;
import de.metas.inoutcandidate.spi.impl.QualityNoticesCollection;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.api.impl.InvoiceCandBL;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class InvoiceCandBL_QtyAndQuality_FromInOutLine_Test extends AbstractICTestSupport
{
	/** service under test */
	private InvoiceCandBL invoiceCandBL;
	private IContextAware context;

	//
	// Master data
	private I_C_Invoice_Candidate invoiceCandidate;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.context = new PlainContextAware(Env.getCtx());

		invoiceCandBL = new InvoiceCandBL();

		this.invoiceCandidate = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class, context);
		this.invoiceCandidate.setIsInDispute(false);
		InterfaceWrapperHelper.save(invoiceCandidate);
	}

	@Test
	public void test1()
	{
		final I_M_InOutLine inoutLine01 = new InOutLineExpectation<>(null, context)
				.movementQty(new BigDecimal("100"))
				.inDispute(false)
				.qualityNote(qualityNotices("note 1", "note 2", "note 3"))
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidate, inoutLine01);

		final I_M_InOutLine inoutLine02 = new InOutLineExpectation<>(null, context)
				.movementQty(new BigDecimal("100"))
				.inDispute(true)
				.qualityNote(qualityNotices("note 4", "note 5"))
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidate, inoutLine02);

		invoiceCandBL.updateQtyWithIssues(invoiceCandidate);

		InvoiceCandidateExpectation.newExpectation()
				.inDispute(true)
				.qtyWithIssues(new BigDecimal("100"))
				.qualityDiscountPercent(new BigDecimal("50"))
				.assertExpected(invoiceCandidate);
	}
	
	@Test
	public void test2()
	{
		final I_M_InOutLine inoutLine01 = new InOutLineExpectation<>(null, context)
				.movementQty(new BigDecimal("10"))
				.inDispute(false)
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidate, inoutLine01);

		final I_M_InOutLine inoutLine02 = new InOutLineExpectation<>(null, context)
				.movementQty(new BigDecimal("10"))
				.inDispute(false)
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidate, inoutLine02);

		final I_M_InOutLine inoutLine03 = new InOutLineExpectation<>(null, context)
				.movementQty(new BigDecimal("10"))
				.inDispute(true)
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidate, inoutLine03);
		
		invoiceCandBL.updateQtyWithIssues(invoiceCandidate);

		InvoiceCandidateExpectation.newExpectation()
				.inDispute(true)
				.qtyWithIssues(new BigDecimal("10"))
				.qualityDiscountPercent(new BigDecimal("33.33"))
				.assertExpected(invoiceCandidate);
	}

	private final String qualityNotices(final String... notices)
	{
		final QualityNoticesCollection col = new QualityNoticesCollection();
		for (final String qualityNote : notices)
		{
			col.addQualityNotice(qualityNote);
		}

		return col.asQualityNoticesString();
	}

}
