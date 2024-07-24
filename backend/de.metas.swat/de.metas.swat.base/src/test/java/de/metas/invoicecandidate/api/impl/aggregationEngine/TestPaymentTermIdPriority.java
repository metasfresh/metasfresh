package de.metas.invoicecandidate.api.impl.aggregationEngine;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolRepository;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolService;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoicecandidate.C_Invoice_Candidate_Builder;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.impl.AggregationEngine;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.X_C_PaymentTerm;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class TestPaymentTermIdPriority extends AbstractAggregationEngineTestBase
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	@Override
	public void init()
	{
		super.init();
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
	}

	private C_Invoice_Candidate_Builder prepareInvoiceCandidate()
	{
		final int paymentTerm = createPaymentTerm().getC_PaymentTerm_ID();
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		bPartner.setC_PaymentTerm_ID(paymentTerm);
		saveRecord(bPartner);
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		return createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setSOTrx(true);
	}

	/**
	 * If payment term is defined on IC, it takes precedence over everything else
	 */
	@Test
	public void test_using_IC_PaymentTerm()
	{
		createDefaultPaymentTerm();

		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate()
				.build();
		saveRecord(ic1);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);

		final AggregationEngine engine = AggregationEngine.builder()
				.docTypeInvoicingPoolService(new DocTypeInvoicingPoolService(new DocTypeInvoicingPoolRepository()))
				.matchInvoiceService(MatchInvoiceService.newInstanceForUnitTesting())
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getPaymentTermId()).isEqualTo(paymentTermId);
	}

	/**
	 * If payment term on IC is null, use the one defined on BPartner level
	 */
	@Test
	public void test_using_BP_PaymentTerm()
	{
		createDefaultPaymentTerm();
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate()
				.build();
		ic1.setC_PaymentTerm_ID(-1);
		saveRecord(ic1);

		final I_C_BPartner bp = bpartnerDAO.getById(ic1.getBill_BPartner_ID());

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);

		final AggregationEngine engine = AggregationEngine.builder()
				.docTypeInvoicingPoolService(new DocTypeInvoicingPoolService(new DocTypeInvoicingPoolRepository()))
				.matchInvoiceService(MatchInvoiceService.newInstanceForUnitTesting())
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getPaymentTermId()).isEqualTo(PaymentTermId.ofRepoId(bp.getC_PaymentTerm_ID()));
	}

	/**
	 * If payment term on IC is null and no paymentTerm defined on BP, use the default payment term
	 */
	@Test
	public void test_using_default_PaymentTerm()
	{
		final int defaultPaymentTerm = createDefaultPaymentTerm();
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate()
				.build();
		ic1.setC_PaymentTerm_ID(-1);
		saveRecord(ic1);

		final I_C_BPartner bp = bpartnerDAO.getById(ic1.getBill_BPartner_ID());
		bp.setC_PaymentTerm_ID(-1);
		saveRecord(bp);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);

		final AggregationEngine engine = AggregationEngine.builder()
				.docTypeInvoicingPoolService(new DocTypeInvoicingPoolService(new DocTypeInvoicingPoolRepository()))
				.matchInvoiceService(MatchInvoiceService.newInstanceForUnitTesting())
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getPaymentTermId()).isEqualTo(PaymentTermId.ofRepoId(defaultPaymentTerm));
	}

	/**
	 * If payment term on IC is null, no paymentTerm defined on BP and no default payment term, throw an error
	 */
	@Test
	public void test_no_DefaultPaymentTerm_exist()
	{
		// final int defaultPaymentTerm = createDefaultPaymentTerm();
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate()
				.build();
		ic1.setC_PaymentTerm_ID(-1);
		saveRecord(ic1);

		final I_C_BPartner bp = bpartnerDAO.getById(ic1.getBill_BPartner_ID());
		bp.setC_PaymentTerm_ID(-1);
		saveRecord(bp);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);

		final AggregationEngine engine = AggregationEngine.builder()
				.docTypeInvoicingPoolService(new DocTypeInvoicingPoolService(new DocTypeInvoicingPoolRepository()))
				.matchInvoiceService(MatchInvoiceService.newInstanceForUnitTesting())
				.build();

		Assert.assertThrows(AdempiereException.class, () -> engine.addInvoiceCandidate(ic1));
	}

	private I_C_PaymentTerm createPaymentTerm()
	{
		final I_C_PaymentTerm pt = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		pt.setC_PaymentTerm_ID(new Random().nextInt(1000000));
		pt.setValue("testValue");
		pt.setName("testName");
		pt.setIsAllowOverrideDueDate(true);
		pt.setCalculationMethod(X_C_PaymentTerm.CALCULATIONMETHOD_BaseLineDatePlusXDays);
		pt.setBaseLineType(X_C_PaymentTerm.BASELINETYPE_InvoiceDate);
		saveRecord(pt);

		return pt;
	}

	private int createDefaultPaymentTerm()
	{
		final I_C_PaymentTerm paymentTerm = createPaymentTerm();
		paymentTerm.setIsDefault(true);
		saveRecord(paymentTerm);
		return paymentTerm.getC_PaymentTerm_ID();
	}
}
