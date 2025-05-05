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

import de.metas.StartupListener;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.InvoiceCandidateInOutLineToUpdate;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.expectations.InvoiceLineAttributeExpectations;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.material.MovementType;
import de.metas.money.MoneyService;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Env;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Similar to {@link TestTwoReceiptsOneInvoice_QualityDiscount1}, but only the first InOut has two lines (one of them has <code>IsInDispute=Y</code>) <strike>and the iols of the two inOuts have
 * different ASIs</strike> (not yet implemented).
 * <p>
 * => Expectation: the MovementQty that is in dispute is not invoiced.
 * <p>
 *
 * @author ts
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, /* ShutdownListener.class,*/ MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class TwoReceiptsOneInvoice_QualityDiscount2_Tests extends AbstractNewAggregationEngineTests
{
	protected static final BigDecimal THREE = new BigDecimal("3");
	protected static final BigDecimal FIVE = new BigDecimal("5");
	protected static final BigDecimal TEN = new BigDecimal("10");
	protected static final BigDecimal TWENTY = new BigDecimal("20");

	protected I_M_Attribute attribute1;
	protected static final String ATTRIBUTE_ValueString1 = "AttValue-1";
	protected static final String ATTRIBUTE_ValueString2 = "AttValue-2";

	protected I_M_InOut inOut1;
	/** Receipt 1 - line 1 */
	protected I_M_InOutLine iol11_three;
	/** Receipt 1 - line 2 (IsInDispute) */
	protected I_M_InOutLine iol12_five_disp;
	/** {@link IInvoiceLineAttribute}s expectations for all lines which are coming from {@link #inOut1} */
	protected InvoiceLineAttributeExpectations<Object> ic_inout1_attributeExpectations;

	protected I_M_InOut inOut2;
	/** Receipt 2 - line 1 */
	protected I_M_InOutLine iol21_ten;
	/** {@link IInvoiceLineAttribute}s expectations for all lines which are coming from {@link #inOut2} */
	protected InvoiceLineAttributeExpectations<Object> ic_inout2_attributeExpectations;

	@Override
	public void init()
	{
		super.init();

		// Create Attribute1
		this.attribute1 = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Attribute.class, ITrx.TRXNAME_None);
		attribute1.setValue("Attribute1");
		attribute1.setName(attribute1.getValue());
		attribute1.setIsAttrDocumentRelevant(true);
		InterfaceWrapperHelper.save(attribute1);
	}

	@Override
	protected List<I_C_Invoice_Candidate> step_createInvoiceCandidates()
	{
		final List<I_C_Invoice_Candidate> ics = test_2StepShipment_CommonSetup_Step01(false, null);// isSOTrx, priceEtnered_Override
		final I_C_Invoice_Candidate ic = ics.get(0);
		ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		ic.setInvoiceRule_Override(null);
		InterfaceWrapperHelper.save(ic);

		return ics;
	}

	@Override
	protected List<I_M_InOutLine> step_createInOutLines(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		//
		// InOut 1
		{
			//@formatter:off
			ic_inout1_attributeExpectations = InvoiceLineAttributeExpectations.newExpectation()
					.newAttributeExpectation()
						.attribute(attribute1)
						.valueString(ATTRIBUTE_ValueString1)
					.endExpectation();
			//@formatter:on
			final I_M_AttributeSetInstance asi1 = ic_inout1_attributeExpectations.createM_AttributeSetInstance(ic);

			final String inOutDocumentNo = "1";
			final StockQtyAndUOMQty qtysDelivered_3 = StockQtyAndUOMQtys.create(new BigDecimal("3"), productId, new BigDecimal("30"), uomId);
			inOut1 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo, MovementType.VendorReceipts);
			iol11_three = createInvoiceCandidateInOutLine(ic, inOut1, qtysDelivered_3, inOutDocumentNo + "_1");
			iol11_three.setM_AttributeSetInstance(asi1);
			InterfaceWrapperHelper.save(iol11_three);

			final StockQtyAndUOMQty qtysDelivered_5 = StockQtyAndUOMQtys.create(new BigDecimal("5"), productId, new BigDecimal("50"), uomId);
			iol12_five_disp = createInvoiceCandidateInOutLine(ic, inOut1, qtysDelivered_5, inOutDocumentNo + "_2");
			iol12_five_disp.setM_AttributeSetInstance(asi1);
			iol12_five_disp.setIsInDispute(true);
			InterfaceWrapperHelper.save(iol12_five_disp);

			completeInOut(inOut1);
		}

		//
		// InOut 2
		{
			//@formatter:off
			ic_inout2_attributeExpectations = InvoiceLineAttributeExpectations.newExpectation()
					.newAttributeExpectation()
						.attribute(attribute1)
						.valueString(ATTRIBUTE_ValueString2)
					.endExpectation();
			//@formatter:on
			final I_M_AttributeSetInstance asi2 = ic_inout2_attributeExpectations.createM_AttributeSetInstance(ic);

			final String inOutDocumentNo = "2";
			final StockQtyAndUOMQty qtysDelivered_10 = StockQtyAndUOMQtys.create(new BigDecimal("10"), productId, new BigDecimal("100"), uomId);
			inOut2 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo, MovementType.VendorReceipts);
			iol21_ten = createInvoiceCandidateInOutLine(ic, inOut2, qtysDelivered_10, inOutDocumentNo + "_1");
			iol21_ten.setM_AttributeSetInstance(asi2);
			InterfaceWrapperHelper.save(iol21_ten);

			completeInOut(inOut2);
		}

		return Arrays.asList(iol11_three, iol12_five_disp, iol21_ten);
	}

	@Override
	protected void step_validate_before_aggregation(@NonNull final List<I_C_Invoice_Candidate> invoiceCandidates, final @NonNull List<I_M_InOutLine> ignored)
	{
		final I_C_Invoice_Candidate ic = invoiceCandidates.get(0);

		// guard; this is tested more in-depth in InvoiceCandBLUpdateInvalidCandidatesTest
		InvoiceCandidateExpectation.newExpectation()
				.inDispute(true)
				.qtyDelivered(THREE.add(TEN).add(FIVE)) // (=> 18)
				.qtyToInvoice(THREE.add(TEN)) // iol11 and iol21 (=> 13)
				.qtyWithIssues(FIVE)
				.qualityDiscountPercent(new BigDecimal("27.78")) // 5 divided by 18 times 100, rounded to a scale of 2
				.assertExpected(ic);
	}

	@Override
	protected void step_validate_after_aggregation(final List<I_C_Invoice_Candidate> invoiceCandidates, final List<I_M_InOutLine> inOutLines, final List<IInvoiceHeader> invoices)
	{
		assertEquals("We are expecting one invoice: " + invoices, 1, invoices.size());

		final IInvoiceHeader invoice1 = invoices.remove(0);

		//
		// guard
		assertThat(invoice1.isSOTrx(), is(false));

		final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
		assertEquals("We are expecting two invoice lines: " + invoiceLines1, 2, invoiceLines1.size()); // because we have a document-relevant att with different values

		//
		// expecting one line with the qty of the non-in-dispute iol icIol11
		{
			final IInvoiceLineRW il1 = getSingleForInOutLine(invoiceLines1, iol11_three);
			assertNotNull("Missing IInvoiceLineRW for iol11=" + iol11_three, il1);
			assertThat(il1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(THREE));
			assertThat(il1.getQtysToInvoice().getUOMQtyNotNull().toBigDecimal(), comparesEqualTo(THIRTY));

			// Validate invoice line attributes
			ic_inout1_attributeExpectations.assertExpected("invalid il1 attribute", il1.getInvoiceLineAttributes());

			final InvoiceCandidateInOutLineToUpdate icIol11 = retrieveIcIolToUpdateIfExists(il1, iol11_three);
			assertThat(icIol11.getQtyInvoiced().getStockQty().toBigDecimal(), comparesEqualTo(THREE));
			assertThat(icIol11.getQtyInvoiced().getUOMQtyNotNull().toBigDecimal(), comparesEqualTo(THIRTY));
		}

		//
		// expecting one line with the qty of the non-in-dispute iol icIol11
		{
			final IInvoiceLineRW il2 = getSingleForInOutLine(invoiceLines1, iol21_ten);
			assertNotNull("Missing IInvoiceLineRW for iol21=" + iol21_ten, il2);
			assertThat(il2.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(TEN));
			assertThat(il2.getQtysToInvoice().getUOMQtyNotNull().toBigDecimal(), comparesEqualTo(HUNDRET));

			// Validate invoice line attributes
			ic_inout2_attributeExpectations.assertExpected("invalid il2 attribute", il2.getInvoiceLineAttributes());

			final InvoiceCandidateInOutLineToUpdate icIol21 = retrieveIcIolToUpdateIfExists(il2, iol21_ten);
			assertThat(icIol21.getQtyInvoiced().getStockQty().toBigDecimal(), comparesEqualTo(TEN));
			assertThat(icIol21.getQtyInvoiced().getUOMQtyNotNull().toBigDecimal(), comparesEqualTo(HUNDRET));
		}

		//
		// checking the in-dispute-iol
		assertNull("Unexpected IInvoiceLineRW for iol12=" + iol12_five_disp, getSingleForInOutLine(invoiceLines1, iol12_five_disp));
	}
}
