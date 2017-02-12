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

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;

import ch.qos.logback.classic.Level;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.impl.AggregationEngine;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.logging.LogManager;

public abstract class AbstractAggregationEngineTestBase extends AbstractICTestSupport
{
	protected I_C_ILCandHandler manualHandler;

	@Rule
	public TestWatcher testWatchman = new AdempiereTestWatcher();

	@Before
	@OverridingMethodsMustInvokeSuper
	public void init()
	{
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_CH");

		manualHandler = InterfaceWrapperHelper.create(ctx, I_C_ILCandHandler.class, Trx.createTrxName());
		manualHandler.setTableName(ManualCandidateHandler.MANUAL);
		manualHandler.setClassname(ManualCandidateHandler.class.getName());
		InterfaceWrapperHelper.save(manualHandler);

		// registerModelInterceptors(); doesn't work well with the legacy tests. Only register then in AbstractNewAggregationEngineTests

		LogManager.setLevel(Level.DEBUG);
	}

	protected List<IInvoiceLineRW> getInvoiceLines(final IInvoiceHeader invoice)
	{
		final List<IInvoiceLineRW> result = new ArrayList<IInvoiceLineRW>();
		for (final IInvoiceCandAggregate lineAgg : invoice.getLines())
		{
			result.addAll(lineAgg.getAllLines());
		}

		return result;
	}

	protected IInvoiceLineRW getInvoiceLineByCandidate(final IInvoiceHeader invoice, final I_C_Invoice_Candidate ic)
	{
		final List<IInvoiceLineRW> result = new ArrayList<IInvoiceLineRW>();
		for (final IInvoiceCandAggregate lineAgg : invoice.getLines())
		{
			result.addAll(lineAgg.getLinesForOrEmpty(ic));
		}

		if (result.isEmpty())
		{
			return null;
		}

		else if (result.size() > 1)
		{
			throw new IllegalStateException("Only one invoice line was expected for " + ic + ": " + result);
		}

		return result.get(0);
	}

	protected List<I_C_Invoice_Candidate> test_2StepShipment_CommonSetup_Step01(
			final boolean isSOTrx,
			final boolean allowConsolidateInvoice,
			final BigDecimal priceEntered_Override)
	{
		return createInvoiceCandidate()
				.setInstanceName("ic")
				.setBillBPartnerId(1)
				.setPriceEntered(1)
				.setPriceEntered_Override(priceEntered_Override)
				.setQty(40)
				.setDiscount(0)
				.setManual(false)
				.setSOTrx(isSOTrx)
				.setAllowConsolidateInvoiceOnBPartner(allowConsolidateInvoice)
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				.buildAsSigletonList();
	}

	protected AggregationEngine test_2StepShipment_CommonSetup_Step02(final String invoiceRuleOverride,
			final I_C_Invoice_Candidate ic,
			final BigDecimal partialQty1,
			final BigDecimal partialQty2)
	{
		//
		// Partially invoice both at the same time
		final I_M_InOut inOut1;
		{
			final String inOutDocumentNo = "1";
			inOut1 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo); // DocumentNo
			createInvoiceCandidateInOutLine(ic, inOut1, partialQty1, inOutDocumentNo); // inOutLineDescription
			completeInOut(inOut1);
		}

		final I_M_InOut inOut2;
		{
			final String inOutDocumentNo = "2";
			inOut2 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo); // DocumentNo
			createInvoiceCandidateInOutLine(ic, inOut2, partialQty2, inOutDocumentNo); // inOutLineDescription
			completeInOut(inOut2);
		}

		ic.setInvoiceRule_Override(invoiceRuleOverride);
		InterfaceWrapperHelper.save(ic);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic);

		// guard; this is tested more in-depth in InvoiceCandBLUpdateInvalidCandidatesTest
		final BigDecimal summedQty = partialQty1.add(partialQty2);
		assertThat("Invalid QtyToDeliver on the IC level", ic.getQtyDelivered(), comparesEqualTo(summedQty));
		assertThat("Invalid QtyToInvoice on the IC level", ic.getQtyToInvoice(), comparesEqualTo(summedQty));

		final AggregationEngine engine = new AggregationEngine();
		engine.addInvoiceCandidate(ic);

		return engine;
	}

	protected IInvoiceHeader removeInvoiceHeaderForInOutId(final List<IInvoiceHeader> invoices, final int inOutId)
	{
		final Iterator<IInvoiceHeader> it = invoices.iterator();
		while (it.hasNext())
		{
			final IInvoiceHeader invoice = it.next();
			if (invoice.getM_InOut_ID() == inOutId)
			{
				it.remove();
				return invoice;
			}
		}

		Assert.fail("No Invoice was found for M_InOut_ID=" + inOutId + " in " + invoices);
		return null; // shall not reach this point
	}

	protected List<IInvoiceLineRW> getForInOutLine(final List<IInvoiceLineRW> invoiceLines, final I_M_InOutLine iol)
	{
		final ArrayList<IInvoiceLineRW> result = new ArrayList<IInvoiceLineRW>();
		for (final IInvoiceLineRW il : invoiceLines)
		{
			for (final int icIolId : il.getC_InvoiceCandidate_InOutLine_IDs())
			{
				final I_C_InvoiceCandidate_InOutLine icIol = InterfaceWrapperHelper.create(
						InterfaceWrapperHelper.getCtx(iol),
						icIolId,
						I_C_InvoiceCandidate_InOutLine.class,
						InterfaceWrapperHelper.getTrxName(iol));

				if (icIol.getM_InOutLine_ID() == iol.getM_InOutLine_ID())
				{
					result.add(il);
				}
			}
		}
		return result;
	}

	protected IInvoiceLineRW getSingleForInOutLine(final List<IInvoiceLineRW> invoiceLines, final I_M_InOutLine iol)
	{
		final List<IInvoiceLineRW> result = getForInOutLine(invoiceLines, iol);
		assertThat(result.size(), lessThan(2));
		if (result.isEmpty())
		{
			return null;
		}
		return result.get(0);
	}

	protected List<IInvoiceHeader> invokeAggregationEngine(final AggregationEngine engine)
	{
		final List<IInvoiceHeader> invoices = engine.aggregate();

		return invoices;
	}

	public final void validateInvoiceHeader(final String message, final IInvoiceHeader invoice, final I_C_Invoice_Candidate fromIC)
	{
		final boolean invoiceReferencesOrder = true;
		validateInvoiceHeader(message, invoice, fromIC, invoiceReferencesOrder);
	}

	public final void validateInvoiceHeader(final String message, final IInvoiceHeader invoice, final I_C_Invoice_Candidate fromIC, final boolean invoiceReferencesOrder)
	{
		final String messagePrefix = message + " - IC=" + POJOWrapper.getInstanceName(fromIC);

		assertEquals(messagePrefix + " - Invalid AD_Org_ID", fromIC.getAD_Org_ID(), invoice.getAD_Org_ID());

		if (fromIC.getM_PriceList_Version_ID() > 0)
		{
			// if our IC had a M_PriceListVersion_ID, we want that PLV's M_PriceList to be in the invoice
			assertEquals(messagePrefix + " - Invalid M_PriceList_ID(", fromIC.getM_PriceList_Version().getM_PriceList_ID(), invoice.getM_PriceList_ID());
		}

		assertEquals(messagePrefix + " - Invalid Bill_BPartner_ID", fromIC.getBill_BPartner_ID(), invoice.getBill_BPartner_ID());
		assertEquals(messagePrefix + " - Invalid Bill_Location_ID", fromIC.getBill_Location_ID(), invoice.getBill_Location_ID());

		// task 08241: we want to aggregate candidates with different Bill_User_IDs into one invoice
		// this commented-out check is synchronized with ICHeaderAggregationKeyValueHandler
		// assertEquals(messagePrefix + " - Invalid Bill_User_ID", fromIC.getBill_User_ID(), invoice.getBill_User_ID());

		assertEquals(messagePrefix + " - Invalid C_Currency_ID", fromIC.getC_Currency_ID(), invoice.getC_Currency_ID());
		if (invoiceReferencesOrder)
		{
			assertEquals(messagePrefix + " - Invalid C_Order_ID", fromIC.getC_Order_ID(), invoice.getC_Order_ID());
		}
		assertEquals(messagePrefix + " - Invalid isSOTrx", fromIC.isSOTrx(), invoice.isSOTrx());
	}

	protected IInvoiceCandidateInOutLineToUpdate retrieveIcIolToUpdateIfExists(final IInvoiceLineRW invoiceLineRW, final I_M_InOutLine iol)
	{
		for (IInvoiceCandidateInOutLineToUpdate icIolToUpdate : invoiceLineRW.getInvoiceCandidateInOutLinesToUpdate())
		{
			if (iol.equals(icIolToUpdate.getC_InvoiceCandidate_InOutLine().getM_InOutLine()))
			{
				return icIolToUpdate;
			}
		}
		return null;
	}
}
