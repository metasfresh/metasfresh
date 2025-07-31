package de.metas.invoicecandidate.api.impl;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
public class InvoiceCandBLUpdateInvalidCandidatesTest extends AbstractICTestSupport
{
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

		assertThat(ic1.getQtyOrdered())
			.as("Invalid QtyDelivered on the IC level")
			.isEqualByComparingTo(new BigDecimal("3"));
		assertThat(ic1.getQtyDelivered())
			.as("Invalid QtyDelivered on the IC level")
			.isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(ic1.getQtyToInvoice())
			.as("Invalid QtyToInvoice on the IC level")
			.isEqualByComparingTo(BigDecimal.ZERO);

		final I_M_InOut inOut1;
		final StockQtyAndUOMQty partialQty1 = StockQtyAndUOMQtys.create(ONE, productId, ONE, uomId);
		{
			inOut1 = createInOut(ic1.getBill_BPartner_ID(), ic1.getC_Order_ID(), "1"); // DocumentNo
			createInvoiceCandidateInOutLine(ic1, inOut1, partialQty1, "1"); // inOutLineDescription
			completeInOut(inOut1);
		}

		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		InterfaceWrapperHelper.save(ic1);

		updateInvalidCandidates();

		InterfaceWrapperHelper.refresh(ic1);
		assertThat(ic1.getQtyOrdered())
			.as("Invalid QtyDelivered on the IC level")
			.isEqualByComparingTo(new BigDecimal("3"));
		assertThat(ic1.getQtyDelivered())
			.as("Invalid QtyDelivered on the IC level")
			.isEqualByComparingTo(partialQty1.getStockQty().toBigDecimal());
		assertThat(ic1.getQtyToInvoice())
			.as("Invalid QtyToInvoice on the IC level")
			.isEqualByComparingTo(partialQty1.getStockQty().toBigDecimal());

	}
}