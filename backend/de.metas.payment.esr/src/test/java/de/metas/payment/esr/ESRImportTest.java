/**
 *
 */
package de.metas.payment.esr;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.calendar.IPeriodBL;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_DocType;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lock.api.ILockManager;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.esr.actionhandler.impl.DuplicatePaymentESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.MoneyTransferedBackESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.UnableToAssignESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.WithCurrenttInvoiceESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.WithNextInvoiceESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.WriteoffESRActionHandler;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.api.IESRLineHandlersService;
import de.metas.payment.esr.dataimporter.impl.v11.ESRTransactionLineMatcherUtil;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.payment.esr.spi.impl.DefaultESRLineHandler;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.util.trxConstraints.api.IOpenTrxBL;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * This class tests the entire module of importing ESR
 *
 * @author cg
 *
 */
public class ESRImportTest extends ESRTestBase
{
	/**
	 * This test emulates a real-world case of a perfect match
	 * <ul>
	 * <li>invoice 50
	 * <li>one ESR line with amount 50
	 * <li>perfect match: partner, invoice, amount
	 * <li>payment created with value 50 and allocated with 50;
	 * <li>esr line processed
	 * </ul>
	 */
	@Test
	public void testStandardCase_T01()
	{
		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);

		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* refNo, */ ESR_Rendered_AccountNo, partnerValue, "50", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(true));
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));
		assertThat(esrImportLine.getImportErrorMsg(), nullValue());
		assertThat(esrImportLine.getMatchErrorMsg(), nullValue());

		// check invoice
		refresh(getC_Invoice(), true);
		assertThat(getC_Invoice().isPaid(), is(true));

		// check the created payments

		final PaymentId esrImportLine1PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLine1PaymentId == null ? null
				: paymentDAO.getById(esrImportLine1PaymentId);

		refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(esrImportLine.getC_Invoice_ID()));
		assertThat(esrLine1Payment.isAllocated(), is(true));

		// check allocations
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

	}

	/**
	 * This test emulates a real-world case of double payment of same invoice in one file
	 * <ul>
	 * <li>invoice 25
	 * <li>one ESR line with amount 25
	 * <li>other ESR line with amount 25
	 * <li>first line will be perfect match: partner, invoice, amount
	 * <li>payment created with value 25 and allocated with 25;
	 * <li>esr line processed
	 * <li>second line, will be an overpayment
	 * <li>payment will be created but not allocated
	 * </ul>
	 */
	@Test
	public void testDoublePayment_T02()
	{
		// org
		final I_AD_Org org = getAD_Org();
		org.setValue("106");
		save(org);

		// partner
		final I_C_BPartner partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
															 org.getAD_Org_ID(),
															 Env.getAD_User_ID(getCtx()),
															 "01-067789-3",
															 currencyEUR);

		// doc type
		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal("25");
		final I_C_Invoice inv = newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo("654321");
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getRepoId());
		inv.setProcessed(true);
		inv.setIsSOTrx(true);
		save(inv);

		final String esrLineText = "01201067789300000001060012345600654321400000025009072  030014040914041014041100001006800000000000090                          ";
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);

		// reference no
		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo(completeRef);
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		save(esrReferenceNumberDocument);

		final I_ESR_Import esrImport = createImport();


		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);
		// register listeners
		Services.get(IESRLineHandlersService.class).registerESRLineListener(new DefaultESRLineHandler()); // 08741

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile,
												   new ByteArrayInputStream((esrLineText + '\n' + esrLineText).getBytes()));

		// start processing
		esrImportBL.process(esrImport);

		final List<I_ESR_ImportLine> lines = Services.get(IESRImportDAO.class).retrieveLines(esrImport);
		assertThat(lines.size(), is(2));

		// check first import line
		final I_ESR_ImportLine esrImportLine1 = lines.get(0);
		assertThat(esrImportLine1.isValid(), is(true));
		assertThat(esrImportLine1.isProcessed(), is(true));
		assertThat(esrImportLine1.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts));
		assertThat(esrImportLine1.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));
		assertThat(esrImportLine1.getImportErrorMsg(), nullValue());
		assertThat(esrImportLine1.getMatchErrorMsg(), nullValue());
		assertThat(esrImportLine1.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(0)));

		// check second import line
		final I_ESR_ImportLine esrImportLine2 = lines.get(1);
		assertThat(esrImportLine2.isValid(), is(true));
		assertThat(esrImportLine2.isProcessed(), is(false));
		assertThat(esrImportLine2.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment));
		assertThat(esrImportLine2.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));
		assertThat(esrImportLine2.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(-25)));

		// check invoice
		assertThat(esrImportLine1.getC_Invoice().isPaid(), is(true));

		// check the created payments - first payment
		final PaymentId esrImportLine1PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine1.getC_Payment_ID());
		final I_C_Payment esrLine1Payment1 = paymentDAO.getById(esrImportLine1PaymentId);

		refresh(esrLine1Payment1, true);
		assertThat(esrLine1Payment1.getPayAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(esrLine1Payment1.getC_Invoice_ID(), is(esrImportLine1.getC_Invoice_ID()));
		assertThat(esrLine1Payment1.isAllocated(), is(true));

		// check the created payments - second payment

		final PaymentId esrImportLine2PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine2.getC_Payment_ID());
		final I_C_Payment esrLine1Payment2 = paymentDAO.getById(esrImportLine2PaymentId);

		refresh(esrLine1Payment2, true);
		assertThat(esrLine1Payment2.getPayAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(esrLine1Payment2.getC_Invoice_ID(), is(0));
		assertThat(esrLine1Payment2.isAllocated(), is(false));

		// check allocations - first payment
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine1.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(25)));

		// check allocations - second payment
		allocLines = Services.get(IPaymentDAO.class).retrieveAllocationLines(esrLine1Payment2);
		assertThat(allocLines.size(), is(0));

	}

	/**
	 * This test emulates a real-world case of a already paid invoice
	 * <ul>
	 * <li>invoice already paid with amount of 50
	 * <li>one ESR line with amount 50
	 * <li>payment created with value 50 and not allocated
	 * <li>change the invoice to an unpaid invoice
	 * <li>complete esr -> allocation created and line with amount 50 allocated against the newly set invoice
	 * </ul>
	 */
	@Test
	public void testAlreadyPaidInvoice_T03()
	{
		Services.get(ILockManager.class);

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, true, completeRef, /* esrLineText, refNo, */ ESR_Rendered_AccountNo, partnerValue, "50", true);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing
		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));
		assertThat(esrImportLine.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(-50)));

		assertThat(esrImportLine.getImportErrorMsg(), nullValue());
		assertThat(esrImportLine.getMatchErrorMsg(), notNullValue());

		// check the created payments

		final PaymentId esrImportLine1PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = paymentDAO.getById(esrImportLine1PaymentId);

		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(0));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// shall be a previous allocation
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));

		// create new invoice
		final I_C_Invoice inv1 = newInstance(I_C_Invoice.class, contextProvider);
		inv1.setGrandTotal(new BigDecimal(50));
		inv1.setC_BPartner_ID(esrImportLine.getC_BPartner_ID());
		inv1.setDocumentNo("654322");
		inv1.setAD_Org_ID(esrImportLine.getAD_Org_ID());
		inv1.setC_DocType_ID(esrImportLine.getC_Invoice().getC_DocType_ID());
		inv1.setC_Currency_ID(esrImportLine.getC_Invoice().getC_Currency_ID());
		inv1.setIsSOTrx(true);
		inv1.setProcessed(true);
		save(inv1);

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice, new WithCurrenttInvoiceESRActionHandler());

		// assign the new invoice to the esrline
		refresh(esrLine1Payment, true);
		esrImportLine.setC_Invoice_ID(inv1.getC_Invoice_ID());
		save(esrImportLine);
		Services.get(IESRImportBL.class).setInvoice(esrImportLine, inv1);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));

		// check if invoice is paid
		refresh(inv1, true);
		assertThat(inv1.isPaid(), is(true));

		// check the created payments
		// reload payment
		final PaymentId esrImportLine1CreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = paymentDAO.getById(esrImportLine1CreatedPaymentId);

		refresh(esrLine1CreatedPayment, true);
		assertThat(esrLine1CreatedPayment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1CreatedPayment.isAllocated(), is(true));
		// shall be one allocation
		allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		// esr processed
		assertThat(esrImport.isProcessed(), is(true));

	}

	/**
	 * This test emulates a real-world case of a payment lower then the invoice
	 * <ul>
	 * <li>One ESR Payment lower than invoice amount
	 * <li>invoice with amount of 50
	 * <li>one ESR line with amount 25
	 * <li>esr line has payment with amount of 25; the payment is not allocated
	 * <li>choose action W_Betrag abschreiben and complete esr
	 * <li>Payment is marked as IsOverUnderPayment; OverUnderAmt is -25;
	 * <li>An allocation of 25 for the invoice was created
	 * <li>An allocation with WriteOff amount of 25 was created
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testPaymentLowerThenInvoice_T04()
	{

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000025009072  030014040914041014041100001006800000000000090                          ";
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* esrLineText, refNo, */ ESR_Rendered_AccountNo, partnerValue, "25", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing
		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(true));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));

		// check the created payments

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = paymentDAO.getById(esrImportLinePaymentId);

		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(0));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount, new WriteoffESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		refresh(getC_Invoice(), true);
		assertThat(getC_Invoice().isPaid(), is(true));

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getC_Invoice_ID(), is(getC_Invoice().getC_Invoice_ID()));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));
		assertThat(esrImportLine.getESR_Invoice_Openamt(), comparesEqualTo(new BigDecimal(25)));

		// check the created payments

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1CreatedPayment.getPayAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(esrLine1CreatedPayment.getC_Invoice_ID(), is(getC_Invoice().getC_Invoice_ID()));
		assertThat(esrLine1CreatedPayment.isAllocated(), is(true));
		assertThat(esrLine1CreatedPayment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(-25)));

		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(2));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(25)));
		assertThat(allocLines.get(0).getC_Invoice_ID(), is(getC_Invoice().getC_Invoice_ID()));
		assertThat(allocLines.get(1).getWriteOffAmt(), comparesEqualTo(new BigDecimal(25)));
		assertThat(allocLines.get(1).getC_Invoice_ID(), notNullValue());

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a payment higher then the invoice
	 * <ul>
	 * <li>One ESR Payment lower than invoice amount
	 * <li>invoice with amount of 50
	 * <li>one ESR line with amount 70
	 * <li>esr line has payment with amount of 70; the payment is not allocated
	 * <li>choose action <code>X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice</code> and complete esr
	 * <li>Payment is marked as IsOverUnderPayment; OverUnderAmt is 20;
	 * <li>An allocation of 50 for the invoice was created
	 * <li>The flag isAutoAllocateAvailableAmt was checked
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testPaymentHigherThenInvoice_Action_N_T05()
	{
		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000070009072  030014040914041014041100001006800000000000090                          ";
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* esrLineText, refNo, */ ESR_Rendered_AccountNo, partnerValue, "70", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing
		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(true));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));

		// check the created payments

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(70)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(0));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, new WithNextInvoiceESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		// check the invoice
		refresh(getC_Invoice(), true);
		assertThat(getC_Invoice().isPaid(), is(true));

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));

		// check the created payments
		refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(getC_Invoice().getC_Invoice_ID()));
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(70)));
		assertThat(esrLine1Payment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(20)));
		assertThat(esrLine1Payment.isAutoAllocateAvailableAmt(), is(true));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// alocations
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a payment higher then the invoice
	 * <ul>
	 * <li>One ESR Payment lower than invoice amount
	 * <li>invoice with amount of 50
	 * <li>one ESR line with amount 70
	 * <li>esr line has payment with amount of 70; the payment is not allocated
	 * <li>choose action <code>X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner</code> and complete esr
	 * <li>Payment is fully allocated
	 * <li>An allocation of 70 for the invoice was created
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testPaymentHigherThenInvoice_Action_B_T05()
	{
		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000070009072  030014040914041014041100001006800000000000090                          ";
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* esrLineText, refNo, */ ESR_Rendered_AccountNo, partnerValue, "70", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing
		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid(), is(true));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLineCreatedPaymentId == null ? null
				: paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1Payment, notNullValue());

		// check the created payments
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(70)));
		assertThat(esrLine1Payment.getC_Invoice_ID(), is(0));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// allocations
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(0));

		// Register payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, new MoneyTransferedBackESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner);
		save(esrImportLine);

		// create doc type credit memo - need for B action
		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo);
		save(type);

		esrImportBL.complete(esrImport, "Complete");

		// check the invoice
		refresh(getC_Invoice(), true);
		assertThat(getC_Invoice().isPaid(), is(true));

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));

		// check the created payments
		refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getPayAmt(), comparesEqualTo(new BigDecimal(70)));
		assertThat(esrLine1Payment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(20)));
		assertThat(esrLine1Payment.isAllocated(), is(true));

		// allocations
		allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		allocLines = Services.get(IPaymentDAO.class).retrieveAllocationLines(esrLine1Payment);
		assertThat(allocLines.size(), is(2));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));
		assertThat(allocLines.get(1).getAmount(), comparesEqualTo(new BigDecimal(20)));

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a completely not found reference
	 * <ul>
	 * <li>One ESR Payment with amount of 50
	 * <li>invoice not matched, partner not matched
	 * <li>no payment is created
	 * <li>choose and complete the esr
	 * <li>A payment is created with amount of 50
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testReferenceCompletelyNotFound_T06()
	{
		// org
		final I_AD_Org org = newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		save(org);



		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
															 org.getAD_Org_ID(),
															 Env.getAD_User_ID(getCtx()),
															 "01-067789-3",
															 currencyEUR);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<>();
		final String esrLineText = "01201067789300000001060000000000000000400000050009072  030014040914041014041100001006800000000000090                          ";
		final I_ESR_Import esrImport = createImport();
		esrImport.setAD_Org_ID(org.getAD_Org_ID());
		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrLineText.getBytes()));

		// start processing
		esrImportBL.process(esrImport);

		// this needs to be here because happens when saving, while importing the line
		// process emulates the importing of the file and at the end the line is saved when the default values are set
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		esrImportLine.setESR_IsManual_ReferenceNo(true); // is by default on 'Y' in db
		save(esrImportLine);

		// check import line
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));
		assertThat(esrImportLine.getImportErrorMsg(), nullValue());
		assertThat(esrImportLine.getMatchErrorMsg(), notNullValue());

		// check the created payments

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		assertThat(esrLine1Payment, nullValue());

		// partner
		final I_C_BPartner partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		save(partner);

		// doc type
		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal("50");
		final I_C_Invoice inv = newInstance(I_C_Invoice.class, contextProvider);
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo("654321");
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getRepoId());
		inv.setIsSOTrx(true);
		inv.setProcessed(true);
		save(inv);

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice, new WithCurrenttInvoiceESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);

		esrImportBL.setInvoice(esrImportLine, inv);
		save(esrImportLine);
		lines.add(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		// check invoice
		refresh(inv, true);
		assertThat(inv.isPaid(), is(true));

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));

		// check the created payments

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = esrImportLineCreatedPaymentId == null ? null
				: paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1CreatedPayment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1CreatedPayment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(0)));
		assertThat(esrLine1CreatedPayment.isAllocated(), is(true));

		// allocations
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a case when invoice reference not found
	 * <ul>
	 * <li>One ESR Payment with amount of 50
	 * <li>invoice not matched, partner matched
	 * <li>A payment is created, but not allocated
	 * <li>choose an unpaid invoice and complete the esr
	 * <li>Allocation against invoice
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testInvoiceReferenceNotFound_T07()
	{
		// org
		final I_AD_Org org = newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		save(org);

		// partner
		final I_C_BPartner partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);


		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		// esr line
		final String esrLineText = "01201067789300000001060012345600000000400000050009072  030014040914041014041100001006800000000000090                          ";
		final I_ESR_Import esrImport = createImport();
		esrImport.setAD_Org_ID(org.getAD_Org_ID());

		// currency
		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		// org bp
		final I_C_BPartner orgBP = newInstance(I_C_BPartner.class, contextProvider);
		orgBP.setValue("orgBP");
		orgBP.setAD_Org_ID(org.getAD_Org_ID());
		orgBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		save(orgBP);

		// bank account
		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setC_Bank_ID(999);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-067789-3");
		account.setC_Currency_ID(currencyEUR.getRepoId());
		account.setC_BPartner_ID(orgBP.getC_BPartner_ID());
		save(account);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrLineText.getBytes()));

		// start processing
		esrImportBL.process(esrImport);
		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		final List<I_ESR_ImportLine> lines = new ArrayList<>();
		esrImportLine.setC_BPartner_ID(partner.getC_BPartner_ID());
		save(esrImportLine);
		lines.add(esrImportLine);

		// this needs to be here because happens when saving, while importing the line
		// process emulates the importing of the file and at the end the line is saved when the default values are set
		refresh(esrImportLine, true);
		esrImportLine.setESR_IsManual_ReferenceNo(true); // is by default on 'Y' in db
		save(esrImportLine);

		// check import line
		assertThat(esrImportLine.isValid()).isFalse();
		assertThat(esrImportLine.isProcessed()).isFalse();
		assertThat(esrImportLine.getC_BPartner_ID()).isGreaterThan(0);
		assertThat(esrImportLine.getESR_Payment_Action()).isNull();
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		assertThat(esrImportLine.getImportErrorMsg()).isNull();
		assertThat(esrImportLine.getMatchErrorMsg()).isNotNull();

		// check the created payments

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		assertThat(esrLine1Payment.getPayAmt(), is(esrImportLine.getAmount()));
		assertThat(esrLine1Payment.isAllocated(), is(false));

		// doc type
		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal("50");
		final I_C_Invoice inv = newInstance(I_C_Invoice.class, contextProvider);
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo("654321");
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getRepoId());
		inv.setIsSOTrx(true);
		inv.setProcessed(true);
		save(inv);

		// Register payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice, new WithCurrenttInvoiceESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);
		esrImportBL.setInvoice(esrImportLine, inv);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		// check invoice
		refresh(inv, true);
		assertThat(inv.isPaid(), is(true));

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));

		// check the created payments

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = esrImportLineCreatedPaymentId == null ? null
				: paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1CreatedPayment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1CreatedPayment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(0)));
		assertThat(esrLine1CreatedPayment.isAllocated(), is(true));

		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(inv);
		assertThat(allocLines.size(), is(1));
		assertThat(allocLines.get(0).getAmount(), comparesEqualTo(new BigDecimal(50)));

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	/**
	 * This test emulates a real-world case of a case when invoice reference not found
	 * <ul>
	 * <li>One ESR Payment with amount of 50
	 * <li>invoice not matched, partner matched
	 * <li>A payment is created, but not allocated
	 * <li>leave invoice field empty, choose action 'E' and complete the esr
	 * <li>ESR processed
	 * </ul>
	 */
	@Test
	public void testInvoiceReferenceNotFound_T09()
	{

		// org
		final I_AD_Org org = newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		save(org);

		// partner
		final I_C_BPartner partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		// esr line
		final List<I_ESR_ImportLine> lines = new ArrayList<>();
		final String esrLineText = "01201067789300000001060012345600000000400000050009072  030014040914041014041100001006800000000000090                          ";
		final I_ESR_Import esrImport = createImport();
		esrImport.setAD_Org_ID(org.getAD_Org_ID());

		// currency
		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		// org bp
		final I_C_BPartner orgBP = newInstance(I_C_BPartner.class, contextProvider);
		orgBP.setValue("orgBP");
		orgBP.setAD_Org_ID(org.getAD_Org_ID());
		orgBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		save(orgBP);

		// bank account
		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setC_Bank_ID(999);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-067789-3");
		account.setC_Currency_ID(currencyEUR.getRepoId());
		account.setC_BPartner_ID(orgBP.getC_BPartner_ID());
		save(account);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrLineText.getBytes()));

		// start processing
		esrImportBL.process(esrImport);

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);
		esrImportLine.setC_BPartner_ID(partner.getC_BPartner_ID());
		save(esrImportLine);
		lines.add(esrImportLine);

		// this needs to be here because happens when saving, while importing the line
		// process emulates the importing of the file and at the end the line is saved when the default values are set
		refresh(esrImportLine, true);
		esrImportLine.setESR_IsManual_ReferenceNo(true); // is by default on 'Y' in db
		save(esrImportLine);

		// check import line
		assertThat(esrImportLine.isValid(), is(false));
		assertThat(esrImportLine.isProcessed(), is(false));
		assertThat(esrImportLine.getESR_Payment_Action(), nullValue());
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));
		assertThat(esrImportLine.getImportErrorMsg(), nullValue());
		assertThat(esrImportLine.getMatchErrorMsg(), notNullValue());

		// check the created payments

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);
		assertThat(esrLine1Payment.getPayAmt(), is(esrImportLine.getAmount()));

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed(), is(true));
		assertThat(esrImportLine.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income));
		assertThat(esrImportLine.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched));

		// check the created payments
		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = esrImportLineCreatedPaymentId == null ? null
				: paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1CreatedPayment.getPayAmt(), comparesEqualTo(new BigDecimal(50)));
		assertThat(esrLine1CreatedPayment.getOverUnderAmt(), comparesEqualTo(new BigDecimal(0)));
		assertThat(esrLine1CreatedPayment.isAutoAllocateAvailableAmt(), is(false));
		assertThat(esrLine1CreatedPayment.isAllocated(), is(false));

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed(), is(true));
	}

	@RepeatedTest(50)
	public void testStandardCase_T01_diffThreads()
	{
		Services.get(IESRImportDAO.class);
		Services.get(IESRImportBL.class);
		Services.get(IDocumentBL.class);
		Services.get(ITrxManager.class);
		Services.get(ITrxConstraintsBL.class);
		Services.get(ISysConfigBL.class);
		Services.get(IQueryBL.class);
		Services.get(ISysConfigDAO.class);
		Services.get(IOpenTrxBL.class);
		Services.get(IReferenceNoDAO.class);
		Services.get(IAllocationDAO.class);
		Services.get(IPeriodBL.class);
		Services.get(IPaymentBL.class);
		Services.get(IDocTypeDAO.class);
		Services.get(IInvoiceDAO.class);
		Services.get(IInvoiceBL.class);
		Services.get(IOrgDAO.class);
		Services.get(ILockManager.class);

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* esrLineText, refNo, */ ESR_Rendered_AccountNo, partnerValue, "50", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		final Runnable runnable = () -> esrImportBL.process(esrImport);

		final List<Thread> threadsRunning = new ArrayList<>();
		for (int threadNo = 1; threadNo <= 5; threadNo++)
		{
			final Thread thread = new Thread(runnable, Thread.currentThread().getName() + "_PrintJobsProducer" + threadNo);
			thread.start();
			threadsRunning.add(thread);
		}

		//
		// Wait until all threads finished
		while (!threadsRunning.isEmpty())
		{
			for (Iterator<Thread> it = threadsRunning.iterator(); it.hasNext(); )
			{
				final Thread thread = it.next();
				try
				{
					thread.join();
				}
				catch (InterruptedException e)
				{
					throw new RuntimeException(e);
				}

				it.remove();
			}
		}

		final List<I_C_Payment> payments = POJOLookupMap.get().getRecords(I_C_Payment.class);
		// System.out.println("***************************TEST PAYMENTS**********************");
		//
		// for (final I_C_Payment payment : payments)
		// {
		// System.out.println("Payment " + payment.getC_Payment_ID() + " Allocated " + payment.isAllocated());
		// }

		assertThat(payments).hasSize(1);
	}

	/**
	 * This test emulates a real-world case of a different org for the invoice partner
	 * <ul>
	 * <li>invoice 50
	 * <li>one ESR line with amount 50
	 * <li>perfect match: partner, invoice, amount
	 * <li>but the partner of the invoice belong to different org
	 * <li>esr line should not be processed
	 * <li>esr line should not be valid
	 * <li>esr line should have an error message
	 * <li>esr line should have fields invoice, partner and payment empty
	 * </ul>
	 */
	@Test
	public void testStandardCase_DiffOrgForInvoicePartner()
	{

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		// org
		final I_AD_Org org = newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		save(org);

		// second org
		final I_AD_Org org1 = newInstance(I_AD_Org.class, contextProvider);
		org1.setValue("105");
		save(org1);

		// partner
		final I_C_BPartner partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue(partnerValue);
		partner.setAD_Org_ID(org1.getAD_Org_ID());
		save(partner);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
															 org.getAD_Org_ID(),
															 Env.getAD_User_ID(getCtx()),
															 ESR_Rendered_AccountNo,
															 currencyEUR);

		// doc type
		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal(grandTotal);
		final I_C_Invoice inv = newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo(invDocNo);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getRepoId());
		inv.setIsPaid(false);
		inv.setIsSOTrx(true);
		save(inv);

		// reference no
		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo(refNo);
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		save(esrReferenceNumberDocument);

		// esr line
		final I_ESR_Import esrImport = createImport();

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		esrImport.setAD_Org_ID(org.getAD_Org_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrLineText.getBytes()));

		esrImportBL.process(esrImport);

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid()).isFalse();
		assertThat(esrImportLine.isProcessed()).isFalse();
		assertThat(esrImportLine.getC_Invoice_ID()).isLessThanOrEqualTo(0);
		assertThat(esrImportLine.getC_BPartner_ID()).isLessThanOrEqualTo(0);
		assertThat(esrImportLine.getC_Payment_ID()).isLessThanOrEqualTo(0);
		assertThat(esrImportLine.getImportErrorMsg()).isNull();
		assertThat(esrImportLine.getMatchErrorMsg()).isNotNull();

	}

	/**
	 * This test emulates a real-world case of different org for invoice and partner
	 * <ul>
	 * <li>invoice 50
	 * <li>one ESR line with amount 50
	 * <li>perfect match: partner, invoice, amount
	 * <li>but the partner and invoice belong to different org than the import line
	 * <li>esr line should not be processed
	 * <li>esr line should not be valid
	 * <li>esr line should have an error message
	 * <li>esr line should have fields invoice, partner and payment empty
	 * </ul>
	 */
	@Test
	public void testStandardCase_DiffOrgForInvoiceAndPartner()
	{

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		// org
		final I_AD_Org org = newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		save(org);

		// second org
		final I_AD_Org org1 = newInstance(I_AD_Org.class, contextProvider);
		org1.setValue("105");
		save(org1);

		// partner
		final I_C_BPartner partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue(partnerValue);
		partner.setAD_Org_ID(org1.getAD_Org_ID());
		save(partner);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
															 org.getAD_Org_ID(),
															 Env.getAD_User_ID(getCtx()),
															 ESR_Rendered_AccountNo,
															 currencyEUR);

		// doc type
		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal(grandTotal);
		final I_C_Invoice inv = newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org1.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo(invDocNo);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getRepoId());
		inv.setIsPaid(false);
		inv.setIsSOTrx(true);
		save(inv);

		// reference no
		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo(refNo);
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setAD_Org_ID(org1.getAD_Org_ID());
		referenceNo.setIsManual(true);
		save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		esrReferenceNumberDocument.setAD_Org_ID(org1.getAD_Org_ID());
		save(esrReferenceNumberDocument);

		// esr line
		final I_ESR_Import esrImport = createImport();

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		esrImport.setAD_Org_ID(org.getAD_Org_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrLineText.getBytes()));

		esrImportBL.process(esrImport);

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid()).isFalse();
		assertThat(esrImportLine.isProcessed()).isFalse();
		assertThat(esrImportLine.getC_Invoice_ID()).isLessThanOrEqualTo(0);
		assertThat(esrImportLine.getC_BPartner_ID()).isLessThanOrEqualTo(0);
		assertThat(esrImportLine.getC_Payment_ID()).isLessThanOrEqualTo(0);
		assertThat(esrImportLine.getImportErrorMsg()).isNull();
		assertThat(esrImportLine.getMatchErrorMsg()).isNotNull();
	}

	/**
	 * This test emulates a real-world of Invoice from a different organization
	 * <ul>
	 * <li>invoice 50
	 * <li>one ESR line with amount 50
	 * <li>perfect match: partner, invoice, amount
	 * <li>but the partner and invoice belong to different org then the import line
	 * <li>esr line should not be processed
	 * <li>esr line should not be valid
	 * <li>esr line should have an error message
	 * <li>esr line should have field invoiceempty
	 * </ul>
	 */
	@Test
	public void testStandardCase_DiffOrgForOnlyForInvoice()
	{

		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String refNo = "300000001060012345600654321";
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		// org
		final I_AD_Org org = newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		save(org);

		// second org
		final I_AD_Org org1 = newInstance(I_AD_Org.class, contextProvider);
		org1.setValue("105");
		save(org1);

		// partner
		final I_C_BPartner partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue(partnerValue);
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		// org bp
		final I_C_BPartner orgBP = newInstance(I_C_BPartner.class, contextProvider);
		orgBP.setValue("orgBP");
		orgBP.setAD_Org_ID(org.getAD_Org_ID());
		orgBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		save(orgBP);

		// bank account
		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setC_Bank_ID(999);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo(ESR_Rendered_AccountNo);
		account.setC_Currency_ID(999);
		account.setC_BPartner_ID(orgBP.getC_BPartner_ID());
		save(account);

		// currency
		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		// doc type
		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal(grandTotal);
		final I_C_Invoice inv = newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org1.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo(invDocNo);
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getRepoId());
		inv.setIsPaid(false);
		inv.setIsSOTrx(true);
		save(inv);

		// reference no
		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo(refNo);
		referenceNo.setAD_Org_ID(org1.getAD_Org_ID());
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		esrReferenceNumberDocument.setAD_Org_ID(org1.getAD_Org_ID());
		save(esrReferenceNumberDocument);

		// esr line
		final I_ESR_Import esrImport = createImport();

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		esrImport.setAD_Org_ID(org.getAD_Org_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrLineText.getBytes()));

		esrImportBL.process(esrImport);

		final I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		// check import line
		refresh(esrImportLine, true);
		final String msg = "Invalid (errmsg=" + esrImportLine.getMatchErrorMsg() + ")";
		assertThat(msg, esrImportLine.isValid(), is(false));
		assertThat(msg, esrImportLine.isProcessed(), is(false));
		assertThat(msg, esrImportLine.getC_Invoice(), nullValue());
		assertThat(msg, esrImportLine.getC_BPartner_ID(), is(partner.getC_BPartner_ID()));

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLinePayment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		assertThat(msg, esrLinePayment, notNullValue());
		assertThat(esrImportLine.getImportErrorMsg(), nullValue());
		assertThat(esrImportLine.getMatchErrorMsg(), notNullValue());

	}

	/**
	 * This test emulates a real-world case of multiple lines and no action
	 * <ul>
	 * <li>invoice 25
	 * <li>one ESR line with amount 25
	 * <li>other ESR line with amount 25
	 * <li>other ESR line with amount 25
	 * <li>noone of the line has action set
	 * <li>we set for the third one and check that was processed
	 * </ul>
	 */
	@Test
	public void testMultipleLines_NoAction()
	{
		// org
		final I_AD_Org org = newInstance(I_AD_Org.class, contextProvider);
		org.setValue("106");
		save(org);

		// partner
		final I_C_BPartner partner = newInstance(I_C_BPartner.class, contextProvider);
		partner.setValue("123456");
		partner.setAD_Org_ID(org.getAD_Org_ID());
		save(partner);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class, contextProvider);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		// org bp
		final I_C_BPartner orgBP = newInstance(I_C_BPartner.class, contextProvider);
		orgBP.setValue("orgBP");
		orgBP.setAD_Org_ID(org.getAD_Org_ID());
		orgBP.setAD_OrgBP_ID(org.getAD_Org_ID());
		save(orgBP);

		// bank account
		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class, contextProvider);
		account.setC_Bank_ID(999);
		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-067789-3");
		account.setC_Currency_ID(999);
		account.setC_BPartner_ID(orgBP.getC_BPartner_ID());
		save(account);

		// currency
		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		// doc type
		final I_C_DocType type = newInstance(I_C_DocType.class, contextProvider);
		type.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		save(type);

		// invoice
		final BigDecimal invoiceGrandTotal = new BigDecimal("25");
		final I_C_Invoice inv = newInstance(I_C_Invoice.class, contextProvider);
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setGrandTotal(invoiceGrandTotal);
		inv.setC_BPartner_ID(partner.getC_BPartner_ID());
		inv.setDocumentNo("654321");
		inv.setAD_Org_ID(org.getAD_Org_ID());
		inv.setC_DocType_ID(type.getC_DocType_ID());
		inv.setC_Currency_ID(currencyEUR.getRepoId());
		inv.setProcessed(true);
		inv.setIsSOTrx(true);
		inv.setIsPaid(true);
		save(inv);

		// allocation for invoice
		final I_C_AllocationLine allocAmt = newInstance(I_C_AllocationLine.class, contextProvider);
		allocAmt.setAmount(new BigDecimal(25));
		allocAmt.setC_Invoice_ID(inv.getC_Invoice_ID());
		save(allocAmt);

		// reference no
		final I_C_ReferenceNo referenceNo = newInstance(I_C_ReferenceNo.class, contextProvider);
		referenceNo.setReferenceNo("300000001060012345600654321");
		referenceNo.setC_ReferenceNo_Type(refNoType);
		referenceNo.setIsManual(true);
		save(referenceNo);

		// reference nodoc
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = newInstance(I_C_ReferenceNo_Doc.class, contextProvider);
		esrReferenceNumberDocument.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Invoice.Table_Name));
		esrReferenceNumberDocument.setRecord_ID(inv.getC_Invoice_ID());
		esrReferenceNumberDocument.setC_ReferenceNo(referenceNo);
		save(esrReferenceNumberDocument);

		final I_ESR_Import esrImport = createImport();

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		esrImport.setAD_Org_ID(org.getAD_Org_ID());
		save(esrImport);

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		// esr line
		// first line
		final String esrLineText = "01201067789300000001060012345600654321400000025009072  030014040914041014041100001006800000000000090                          ";

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile,
												   new ByteArrayInputStream((esrLineText + '\n' + esrLineText + '\n' + esrLineText).getBytes()));

		esrImportBL.process(esrImport);

		final List<I_ESR_ImportLine> lines = Services.get(IESRImportDAO.class).retrieveLines(esrImport);
		final I_ESR_ImportLine esrImportLine1 = lines.get(0);
		final I_ESR_ImportLine esrImportLine2 = lines.get(1);
		final I_ESR_ImportLine esrImportLine3 = lines.get(2);

		// check first import line
		refresh(esrImportLine1, true);
		assertThat(esrImportLine1.isProcessed(), is(false));
		assertThat(esrImportLine1.getESR_Payment_Action(), nullValue());

		// check second import line
		refresh(esrImportLine2, true);
		assertThat(esrImportLine2.isProcessed(), is(false));
		assertThat(esrImportLine2.getESR_Payment_Action(), nullValue());

		// check third import line
		refresh(esrImportLine3, true);
		assertThat(esrImportLine3.isProcessed(), is(false));
		assertThat(esrImportLine3.getESR_Payment_Action(), nullValue());

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());
		esrImportLine3.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);
		save(esrImportLine3);
		// esrBL.process(esrImport, trxRunConfig);
		esrImportBL.complete(esrImport, "test");

		refresh(esrImportLine3, true);
		assertThat(esrImportLine3.isProcessed(), is(true));
	}

	@Test
	public void testDuplicatePayments()
	{
		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);

		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine1 = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* refNo, */ ESR_Rendered_AccountNo, partnerValue, "50", false);
		esrImportLine1.setESRLineText(esrLineText);
		save(esrImportLine1);

		final I_ESR_Import esrImport = esrImportLine1.getESR_Import();

		esrImportBL.process(esrImport);


		final I_ESR_ImportLine esrImportLine2 = createESR_ImportLineFromOtherLine(esrImportLine1);
		esrImportLine2.setESRLineText(esrLineText);
		save(esrImportLine2);
		final I_ESR_Import esrImport2 = esrImportLine2.getESR_Import();
		esrImportBL.process(esrImport2);


		// check import line
		refresh(esrImportLine1, true);
		assertThat(esrImportLine1.isValid(), is(true));
		assertThat(esrImportLine1.isProcessed(), is(true));
		assertThat(esrImportLine1.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts));
		assertThat(esrImportLine1.getESR_Document_Status(), is(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched));
		assertThat(esrImportLine1.getImportErrorMsg(), nullValue());
		assertThat(esrImportLine1.getMatchErrorMsg(), nullValue());

		refresh(esrImportLine2, true);
		assertThat(esrImportLine2.getESR_Payment_Action(), is(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment));
		assertThat(esrImportLine2.getImportErrorMsg(), nullValue());
		assertThat(esrImportLine2.getMatchErrorMsg(), is("Rechnung " + invDocNo + " wurde im System als bereits bezahlt markiert"));

		// check the payment
		assertThat(esrImportLine2.getC_Payment_ID(), is(esrImportLine2.getC_Payment_ID()));
	}

}
