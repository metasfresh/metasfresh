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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/**
 * Test case:
 * <ul>
 * <li>one invoice candidate
 * <li>two shipments, the first one with one line, the second one with two lines..each line has the same product etc
 * <li>both iols belong to the same order line and thus are associated to the same invoice candidate
 * </ul>
 * 
 * => Expectation: see subclasses
 * <p>
 * 
 * @author ts
 *
 */
public abstract class AbstractTwoInOutsTests extends AbstractNewAggregationEngineTests
{

	protected static final BigDecimal partialQty1 = new BigDecimal("32");
	protected static final BigDecimal partialQty2 = new BigDecimal("8");
	protected static final BigDecimal partialQty3 = new BigDecimal("4");

	protected I_M_InOut inOut1;
	protected I_M_InOutLine iol11;

	protected I_M_InOut inOut2;
	protected I_M_InOutLine iol21;
	protected I_M_InOutLine iol22;

	abstract protected BigDecimal config_GetPriceEntered_Override();

	abstract protected boolean config_IsSOTrx();

	@Override
	protected List<I_C_Invoice_Candidate> step_createInvoiceCandidates()
	{
		return test_2StepShipment_CommonSetup_Step01(config_IsSOTrx(), config_GetPriceEntered_Override());
	}

	@Override
	protected List<I_M_InOutLine> step_createInOutLines(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);
		//
		// Partially invoice both at the same time
		{
			final String inOutDocumentNo = "1";
			inOut1 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo); // DocumentNo
			iol11 = createInvoiceCandidateInOutLine(ic, inOut1, partialQty1, inOutDocumentNo); // inOutLineDescription
			completeInOut(inOut1);
		}

		{
			final String inOutDocumentNo = "2";
			inOut2 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo); // DocumentNo
			iol21 = createInvoiceCandidateInOutLine(ic, inOut2, partialQty2, inOutDocumentNo + "_1"); // inOutLineDescription
			iol22 = createInvoiceCandidateInOutLine(ic, inOut2, partialQty3, inOutDocumentNo + "_2"); // inOutLineDescription
			completeInOut(inOut2);
		}

		return Arrays.asList(iol11, iol21);
	}

	@Override
	protected void step_validate_before_aggregation(final List<I_C_Invoice_Candidate> invoiceCandidates, final List<I_M_InOutLine> inOutLines)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		// guard; this is tested more in-depth in InvoiceCandBLUpdateInvalidCandidatesTest
		final BigDecimal summedQty = partialQty1.add(partialQty2).add(partialQty3);
		assertThat("Invalid QtyToDeliver on the IC level", ic.getQtyDelivered(), comparesEqualTo(summedQty));
		assertThat("Invalid QtyToInvoice on the IC level", ic.getQtyToInvoice(), comparesEqualTo(summedQty));

		if (config_GetPriceEntered_Override() != null)
		{
			final BigDecimal priceEntered = invoiceCandBL.getPriceEntered(ic);
			assertThat("Invalide priceEntered", priceEntered, comparesEqualTo(config_GetPriceEntered_Override()));
			
			final BigDecimal priceActual = invoiceCandBL.getPriceActual(ic);
			assertThat("Invalide priceActual", priceActual, comparesEqualTo(config_GetPriceEntered_Override())); // because we don't have a discount
		}
	}
}
