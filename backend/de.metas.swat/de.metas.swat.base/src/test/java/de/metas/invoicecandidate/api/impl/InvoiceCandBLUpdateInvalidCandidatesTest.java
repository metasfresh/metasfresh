package de.metas.invoicecandidate.api.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.material.MovementType;
import de.metas.money.MoneyService;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

public class InvoiceCandBLUpdateInvalidCandidatesTest extends AbstractICTestSupport
{
	@Before
	public void init()
	{
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
	}

	@Test
	public void testQtyToInvoice_AfterDelivery()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setInstanceName("ic1")
				.setBillBPartnerId(bPartner.getC_BPartner_ID())
				.setPriceEntered(1)
				.setQtyOrdered(3)
				.setSOTrx(false)
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				.build();

		assertThat("Invalid QtyDelivered on the IC level", ic1.getQtyOrdered(), comparesEqualTo(new BigDecimal("3")));
		assertThat("Invalid QtyDelivered on the IC level", ic1.getQtyDelivered(), comparesEqualTo(BigDecimal.ZERO));
		assertThat("Invalid QtyToInvoice on the IC level", ic1.getQtyToInvoice(), comparesEqualTo(BigDecimal.ZERO));

		final I_M_InOut inOut1;
		final StockQtyAndUOMQty partialQty1 = StockQtyAndUOMQtys.create(ONE, productId, ONE, uomId);
		{
			inOut1 = createInOut(ic1.getBill_BPartner_ID(), ic1.getC_Order_ID(), "1", MovementType.VendorReceipts); // DocumentNo
			createInvoiceCandidateInOutLine(ic1, inOut1, partialQty1, "1"); // inOutLineDescription
			completeInOut(inOut1);
		}

		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		InterfaceWrapperHelper.save(ic1);

		updateInvalidCandidates();

		InterfaceWrapperHelper.refresh(ic1);
		assertThat("Invalid QtyDelivered on the IC level", ic1.getQtyOrdered(), comparesEqualTo(new BigDecimal("3")));
		assertThat("Invalid QtyDelivered on the IC level", ic1.getQtyDelivered(), comparesEqualTo(partialQty1.getStockQty().toBigDecimal()));
		assertThat("Invalid QtyToInvoice on the IC level", ic1.getQtyToInvoice(), comparesEqualTo(partialQty1.getStockQty().toBigDecimal()));

	}
}
