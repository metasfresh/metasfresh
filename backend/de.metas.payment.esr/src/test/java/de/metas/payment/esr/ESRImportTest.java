/**
 *
 */
package de.metas.payment.esr;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
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
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.lock.api.ILockManager;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.util.trxConstraints.api.IOpenTrxBL;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class tests the entire module of importing ESR
 *
 * @author cg
 */
@SuppressWarnings("DataFlowIssue")
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
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);

		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* refNo, */ ESR_Rendered_AccountNo, partnerValue, "50", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid()).isTrue();
		assertThat(esrImportLine.isProcessed()).isTrue();
		assertThat(esrImportLine.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts);
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);
		assertThat(esrImportLine.getImportErrorMsg()).isNull();
		assertThat(esrImportLine.getMatchErrorMsg()).isNull();

		// check invoice
		refresh(getC_Invoice(), true);
		assertInvoiceFullyPaid();

		// check the created payments

		final PaymentId esrImportLine1PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLine1PaymentId == null ? null
				: paymentDAO.getById(esrImportLine1PaymentId);

		refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getPayAmt()).isEqualByComparingTo(new BigDecimal(50));
		assertThat(esrLine1Payment.getC_Invoice_ID()).isEqualTo(esrImportLine.getC_Invoice_ID());
		assertThat(esrLine1Payment.isAllocated()).isTrue();

		// check allocations
		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines).hasSize(1);
		assertThat(allocLines.get(0).getAmount()).isEqualByComparingTo(new BigDecimal(50));

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
		inv.setIsFinancial(InvoiceDocBaseType.ofCode(type.getDocBaseType()).isFinancial());
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
		assertThat(lines).hasSize(2);

		// check first import line
		final I_ESR_ImportLine esrImportLine1 = lines.get(0);
		assertThat(esrImportLine1.isValid()).isTrue();
		assertThat(esrImportLine1.isProcessed()).isTrue();
		assertThat(esrImportLine1.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts);
		assertThat(esrImportLine1.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);
		assertThat(esrImportLine1.getImportErrorMsg()).isNull();
		assertThat(esrImportLine1.getMatchErrorMsg()).isNull();
		assertThat(esrImportLine1.getESR_Invoice_Openamt()).isEqualByComparingTo(new BigDecimal(0));

		// check second import line
		final I_ESR_ImportLine esrImportLine2 = lines.get(1);
		assertThat(esrImportLine2.isValid()).isTrue();
		assertThat(esrImportLine2.isProcessed()).isFalse();
		assertThat(esrImportLine2.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);
		assertThat(esrImportLine2.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		assertThat(esrImportLine2.getESR_Invoice_Openamt()).isEqualByComparingTo(new BigDecimal(-25));

		// check invoice
		assertInvoiceFullyPaid(esrImportLine1.getC_Invoice());

		// check the created payments - first payment
		final PaymentId esrImportLine1PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine1.getC_Payment_ID());
		final I_C_Payment esrLine1Payment1 = paymentDAO.getById(esrImportLine1PaymentId);

		refresh(esrLine1Payment1, true);
		assertThat(esrLine1Payment1.getPayAmt()).isEqualByComparingTo(new BigDecimal(25));
		assertThat(esrLine1Payment1.getC_Invoice_ID()).isEqualTo(esrImportLine1.getC_Invoice_ID());
		assertThat(esrLine1Payment1.isAllocated()).isTrue();

		// check the created payments - second payment

		final PaymentId esrImportLine2PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine2.getC_Payment_ID());
		final I_C_Payment esrLine1Payment2 = paymentDAO.getById(esrImportLine2PaymentId);

		refresh(esrLine1Payment2, true);
		assertThat(esrLine1Payment2.getPayAmt()).isEqualByComparingTo(new BigDecimal(25));
		assertThat(esrLine1Payment2.getC_Invoice_ID()).isZero();
		assertThat(esrLine1Payment2.isAllocated()).isFalse();

		// check allocations - first payment
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine1.getC_Invoice());
		assertThat(allocLines).hasSize(1);
		assertThat(allocLines.get(0).getAmount()).isEqualByComparingTo(new BigDecimal(25));

		// check allocations - second payment
		allocLines = Services.get(IPaymentDAO.class).retrieveAllocationLines(esrLine1Payment2);
		assertThat(allocLines).isEmpty();

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
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, true, completeRef, /* esrLineText, refNo, */ ESR_Rendered_AccountNo, partnerValue, "50", true);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing
		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid()).isFalse();
		assertThat(esrImportLine.isProcessed()).isFalse();
		assertThat(esrImportLine.getESR_Payment_Action()).isNull();
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		assertThat(esrImportLine.getESR_Invoice_Openamt()).isEqualByComparingTo(new BigDecimal(-50));

		assertThat(esrImportLine.getImportErrorMsg()).isNull();
		assertThat(esrImportLine.getMatchErrorMsg()).isNotNull();

		// check the created payments

		final PaymentId esrImportLine1PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = paymentDAO.getById(esrImportLine1PaymentId);

		assertThat(esrLine1Payment.getPayAmt()).isEqualByComparingTo(new BigDecimal(50));
		assertThat(esrLine1Payment.getC_Invoice_ID()).isZero();
		assertThat(esrLine1Payment.isAllocated()).isFalse();

		// shall be a previous allocation
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines).hasSize(1);

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
		inv1.setIsFinancial(true);
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
		assertThat(esrImportLine.isValid()).isFalse();
		assertThat(esrImportLine.isProcessed()).isTrue();
		assertThat(esrImportLine.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);

		// check if invoice is paid
		refresh(inv1, true);
		assertInvoiceFullyPaid(inv1);

		// check the created payments
		// reload payment
		final PaymentId esrImportLine1CreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = paymentDAO.getById(esrImportLine1CreatedPaymentId);

		refresh(esrLine1CreatedPayment, true);
		assertThat(esrLine1CreatedPayment.getPayAmt()).isEqualByComparingTo(new BigDecimal(50));
		assertThat(esrLine1CreatedPayment.isAllocated()).isTrue();
		// shall be one allocation
		allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines).hasSize(1);
		assertThat(allocLines.get(0).getAmount()).isEqualByComparingTo(new BigDecimal(50));

		// esr processed
		assertThat(esrImport.isProcessed()).isTrue();

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
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* esrLineText, refNo, */ ESR_Rendered_AccountNo, partnerValue, "25", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing
		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid()).isTrue();
		assertThat(esrImportLine.isProcessed()).isFalse();
		assertThat(esrImportLine.getESR_Payment_Action()).isNull();
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);

		// check the created payments

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = paymentDAO.getById(esrImportLinePaymentId);

		assertThat(esrLine1Payment.getPayAmt()).isEqualByComparingTo(new BigDecimal(25));
		assertThat(esrLine1Payment.getC_Invoice_ID()).isZero();
		assertThat(esrLine1Payment.isAllocated()).isFalse();

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount, new WriteoffESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		refresh(getC_Invoice(), true);
		assertInvoiceFullyPaid();

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed()).isTrue();
		assertThat(esrImportLine.getC_Invoice_ID()).isEqualTo(getC_Invoice().getC_Invoice_ID());
		assertThat(esrImportLine.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount);
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);
		assertThat(esrImportLine.getESR_Invoice_Openamt()).isEqualByComparingTo(new BigDecimal(25));

		// check the created payments

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1CreatedPayment.getPayAmt()).isEqualByComparingTo(new BigDecimal(25));
		assertThat(esrLine1CreatedPayment.getC_Invoice_ID()).isEqualTo(getC_Invoice().getC_Invoice_ID());
		assertThat(esrLine1CreatedPayment.isAllocated()).isTrue();
		assertThat(esrLine1CreatedPayment.getOverUnderAmt()).isEqualByComparingTo(new BigDecimal(-25));

		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines).hasSize(2);
		assertThat(allocLines.get(0).getAmount()).isEqualByComparingTo(new BigDecimal(25));
		assertThat(allocLines.get(0).getC_Invoice_ID()).isEqualTo(getC_Invoice().getC_Invoice_ID());
		assertThat(allocLines.get(1).getWriteOffAmt()).isEqualByComparingTo(new BigDecimal(25));
		assertThat(allocLines.get(1).getC_Invoice_ID()).isNotNull();

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed()).isTrue();
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
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* esrLineText, refNo, */ ESR_Rendered_AccountNo, partnerValue, "70", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing
		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid()).isTrue();
		assertThat(esrImportLine.isProcessed()).isFalse();
		assertThat(esrImportLine.getESR_Payment_Action()).isNull();
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);

		// check the created payments

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1Payment.getPayAmt()).isEqualByComparingTo(new BigDecimal(70));
		assertThat(esrLine1Payment.getC_Invoice_ID()).isZero();
		assertThat(esrLine1Payment.isAllocated()).isFalse();

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice, new WithNextInvoiceESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		// check the invoice
		refresh(getC_Invoice(), true);
		assertInvoiceFullyPaid();

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed()).isTrue();
		assertThat(esrImportLine.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Next_Invoice);
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);

		// check the created payments
		refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getC_Invoice_ID()).isEqualTo(getC_Invoice().getC_Invoice_ID());
		assertThat(esrLine1Payment.getPayAmt()).isEqualByComparingTo(new BigDecimal(70));
		assertThat(esrLine1Payment.getOverUnderAmt()).isEqualByComparingTo(new BigDecimal(20));
		assertThat(esrLine1Payment.isAutoAllocateAvailableAmt()).isTrue();
		assertThat(esrLine1Payment.isAllocated()).isFalse();

		// alocations
		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines).hasSize(1);
		assertThat(allocLines.get(0).getAmount()).isEqualByComparingTo(new BigDecimal(50));

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed()).isTrue();
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
		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* esrLineText, refNo, */ ESR_Rendered_AccountNo, partnerValue, "70", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		// start processing
		esrImportBL.process(esrImport);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isValid()).isTrue();
		assertThat(esrImportLine.isProcessed()).isFalse();
		assertThat(esrImportLine.getESR_Payment_Action()).isNull();
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLineCreatedPaymentId == null ? null
				: paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1Payment).isNotNull();

		// check the created payments
		assertThat(esrLine1Payment.getPayAmt()).isEqualByComparingTo(new BigDecimal(70));
		assertThat(esrLine1Payment.getC_Invoice_ID()).isZero();
		assertThat(esrLine1Payment.isAllocated()).isFalse();

		// allocations
		List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines).isEmpty();

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
		assertInvoiceFullyPaid();

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed()).isTrue();
		assertThat(esrImportLine.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner);
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);

		// check the created payments
		refresh(esrLine1Payment, true);
		assertThat(esrLine1Payment.getPayAmt()).isEqualByComparingTo(new BigDecimal(70));
		assertThat(esrLine1Payment.getOverUnderAmt()).isEqualByComparingTo(new BigDecimal(20));
		assertThat(esrLine1Payment.isAllocated()).isTrue();

		// allocations
		allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines).hasSize(1);
		assertThat(allocLines.get(0).getAmount()).isEqualByComparingTo(new BigDecimal(50));

		allocLines = Services.get(IPaymentDAO.class).retrieveAllocationLines(esrLine1Payment);
		assertThat(allocLines).hasSize(2);
		assertThat(allocLines.get(0).getAmount()).isEqualByComparingTo(new BigDecimal(50));
		assertThat(allocLines.get(1).getAmount()).isEqualByComparingTo(new BigDecimal(20));

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed()).isTrue();
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
		assertThat(esrImportLine.isValid()).isFalse();
		assertThat(esrImportLine.isProcessed()).isFalse();
		assertThat(esrImportLine.getESR_Payment_Action()).isNull();
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		assertThat(esrImportLine.getImportErrorMsg()).isNull();
		assertThat(esrImportLine.getMatchErrorMsg()).isNotNull();

		// check the created payments

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		assertThat(esrLine1Payment).isNull();

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
		inv.setIsFinancial(InvoiceDocBaseType.ofCode(type.getDocBaseType()).isFinancial());
		save(inv);

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice, new WithCurrenttInvoiceESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);

		esrImportBL.setInvoice(esrImportLine, inv);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		// check invoice
		refresh(inv, true);
		assertInvoiceFullyPaid(inv);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed()).isTrue();
		assertThat(esrImportLine.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);

		// check the created payments

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = esrImportLineCreatedPaymentId == null ? null
				: paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1CreatedPayment.getPayAmt()).isEqualByComparingTo(new BigDecimal(50));
		assertThat(esrLine1CreatedPayment.getOverUnderAmt()).isEqualByComparingTo(new BigDecimal(0));
		assertThat(esrLine1CreatedPayment.isAllocated()).isTrue();

		// allocations
		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(esrImportLine.getC_Invoice());
		assertThat(allocLines).hasSize(1);
		assertThat(allocLines.get(0).getAmount()).isEqualByComparingTo(new BigDecimal(50));

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed()).isTrue();
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

		esrImportLine.setC_BPartner_ID(partner.getC_BPartner_ID());
		save(esrImportLine);

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

		assertThat(esrLine1Payment.getPayAmt()).isEqualTo(esrImportLine.getAmount());
		assertThat(esrLine1Payment.isAllocated()).isFalse();

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
		inv.setIsFinancial(InvoiceDocBaseType.ofCode(type.getDocBaseType()).isFinancial());
		save(inv);

		// Register payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice, new WithCurrenttInvoiceESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);
		esrImportBL.setInvoice(esrImportLine, inv);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		// check invoice
		refresh(inv, true);
		assertInvoiceFullyPaid(inv);

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed()).isTrue();
		assertThat(esrImportLine.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Allocate_Payment_With_Current_Invoice);
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);

		// check the created payments

		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = esrImportLineCreatedPaymentId == null ? null
				: paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1CreatedPayment.getPayAmt()).isEqualByComparingTo(new BigDecimal(50));
		assertThat(esrLine1CreatedPayment.getOverUnderAmt()).isEqualByComparingTo(new BigDecimal(0));
		assertThat(esrLine1CreatedPayment.isAllocated()).isTrue();

		final List<I_C_AllocationLine> allocLines = Services.get(IAllocationDAO.class).retrieveAllocationLines(inv);
		assertThat(allocLines).hasSize(1);
		assertThat(allocLines.get(0).getAmount()).isEqualByComparingTo(new BigDecimal(50));

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed()).isTrue();
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

		// this needs to be here because happens when saving, while importing the line
		// process emulates the importing of the file and at the end the line is saved when the default values are set
		refresh(esrImportLine, true);
		esrImportLine.setESR_IsManual_ReferenceNo(true); // is by default on 'Y' in db
		save(esrImportLine);

		// check import line
		assertThat(esrImportLine.isValid()).isFalse();
		assertThat(esrImportLine.isProcessed()).isFalse();
		assertThat(esrImportLine.getESR_Payment_Action()).isNull();
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		assertThat(esrImportLine.getImportErrorMsg()).isNull();
		assertThat(esrImportLine.getMatchErrorMsg()).isNotNull();

		// check the created payments

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1Payment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);
		assertThat(esrLine1Payment.getPayAmt()).isEqualTo(esrImportLine.getAmount());

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);
		save(esrImportLine);

		esrImportBL.complete(esrImport, "Complete");

		// check import line
		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed()).isTrue();
		assertThat(esrImportLine.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);
		assertThat(esrImportLine.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);

		// check the created payments
		final PaymentId esrImportLineCreatedPaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLine1CreatedPayment = esrImportLineCreatedPaymentId == null ? null
				: paymentDAO.getById(esrImportLineCreatedPaymentId);

		assertThat(esrLine1CreatedPayment.getPayAmt()).isEqualByComparingTo(new BigDecimal(50));
		assertThat(esrLine1CreatedPayment.getOverUnderAmt()).isEqualByComparingTo(new BigDecimal(0));
		assertThat(esrLine1CreatedPayment.isAutoAllocateAvailableAmt()).isFalse();
		assertThat(esrLine1CreatedPayment.isAllocated()).isFalse();

		// esr processed
		refresh(esrImport, true);
		assertThat(esrImport.isProcessed()).isTrue();
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
			for (final Iterator<Thread> it = threadsRunning.iterator(); it.hasNext(); )
			{
				final Thread thread = it.next();
				try
				{
					thread.join();
				}
				catch (final InterruptedException e)
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
		assertThat(esrImportLine.isValid()).as(msg).isFalse();
		assertThat(esrImportLine.isProcessed()).as(msg).isFalse();
		assertThat(esrImportLine.getC_Invoice()).as(msg).isNull();
		assertThat(esrImportLine.getC_BPartner_ID()).as(msg).isEqualTo(partner.getC_BPartner_ID());

		final PaymentId esrImportLinePaymentId = PaymentId.ofRepoIdOrNull(esrImportLine.getC_Payment_ID());
		final I_C_Payment esrLinePayment = esrImportLinePaymentId == null ? null
				: paymentDAO.getById(esrImportLinePaymentId);

		assertThat(esrLinePayment).as(msg).isNotNull();
		assertThat(esrImportLine.getImportErrorMsg()).isNull();
		assertThat(esrImportLine.getMatchErrorMsg()).isNotNull();

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
		inv.setIsFinancial(InvoiceDocBaseType.ofCode(type.getDocBaseType()).isFinancial());
		save(inv);

		// allocation for invoice
		final I_C_AllocationHdr allocHdr = newInstance(I_C_AllocationHdr.class, contextProvider);
		allocHdr.setC_Currency_ID(currencyEUR.getRepoId());
		save(allocHdr);
		final I_C_AllocationLine allocAmt = newInstance(I_C_AllocationLine.class, contextProvider);
		allocAmt.setC_AllocationHdr_ID(allocHdr.getC_AllocationHdr_ID());
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
		assertThat(esrImportLine1.isProcessed()).isFalse();
		assertThat(esrImportLine1.getESR_Payment_Action()).isNull();

		// check second import line
		refresh(esrImportLine2, true);
		assertThat(esrImportLine2.isProcessed()).isFalse();
		assertThat(esrImportLine2.getESR_Payment_Action()).isNull();

		// check third import line
		refresh(esrImportLine3, true);
		assertThat(esrImportLine3.isProcessed()).isFalse();
		assertThat(esrImportLine3.getESR_Payment_Action()).isNull();

		// Registrate payment action handlers.
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());
		esrImportLine3.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);
		save(esrImportLine3);
		// esrBL.process(esrImport, trxRunConfig);
		esrImportBL.complete(esrImport, "test");

		refresh(esrImportLine3, true);
		assertThat(esrImportLine3.isProcessed()).isTrue();
	}

	/**
	 * Cross-import duplicate with NO PaymentDate on either line (the fixture default): the second line
	 * must still get its own payment, and the "invoice already paid" note must appear exactly once.
	 */
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
		assertThat(esrImportLine1.isValid()).isTrue();
		assertThat(esrImportLine1.isProcessed()).isTrue();
		assertThat(esrImportLine1.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts);
		assertThat(esrImportLine1.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);
		assertThat(esrImportLine1.getImportErrorMsg()).isNull();
		assertThat(esrImportLine1.getMatchErrorMsg()).isNull();

		refresh(esrImportLine2, true);
		assertThat(esrImportLine2.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);
		assertThat(esrImportLine2.isProcessed()).as("the duplicate line must stay unprocessed so the accountant still sees it").isFalse();
		assertThat(esrImportLine2.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		assertThat(esrImportLine2.getImportErrorMsg()).isNull();
		// note text now comes from msgBL.getMsg(ESR_INVOICE_ALREADY_PAID_1P, ...); in this no-DB test env
		// an AD_Message with no registered row resolves to "<key>_[<params>]" (see ESRImportBLTest#test_setInvoice_wrongOrg
		// for the same fallback shape with 2 params)
		assertThat(esrImportLine2.getMatchErrorMsg()).isEqualTo("de.metas.payment.esr.InvoiceAlreadyPaid_[" + invDocNo + "]");

		// check the payment: the duplicate-flagged line must have its OWN payment, not the first line's
		assertThat(esrImportLine2.getC_Payment_ID()).as("the duplicate line must not carry the first line's C_Payment_ID").isNotEqualTo(esrImportLine1.getC_Payment_ID());

		final PaymentId esrImportLine2PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine2.getC_Payment_ID());
		final I_C_Payment esrLine2Payment = esrImportLine2PaymentId == null ? null
				: paymentDAO.getById(esrImportLine2PaymentId);
		assertThat(esrLine2Payment).as("the duplicate-flagged line must have created its own payment").isNotNull();
		assertThat(esrLine2Payment.getPayAmt()).isEqualByComparingTo("50");
		assertThat(esrLine2Payment.isAllocated()).as("the duplicate payment must not be allocated to the invoice").isFalse();
		assertThat(esrLine2Payment.getC_Invoice_ID()).as("the duplicate payment must not be linked to the invoice").isEqualTo(0);
	}

	/**
	 * Same ESR reference and same amount arrive again after the first, already fully-matched payment,
	 * either on the same day or a later day.
	 * <ul>
	 * <li>invoice 50, first ESR line pays it in full on D1
	 * <li>second ESR line: same reference, same amount 50, PaymentDate D1 or a later D2
	 * <li>on the same day, the second line must still be flagged as a duplicate payment and remain unprocessed
	 * <li>either way it must get its OWN payment (not the first line's), for PayAmt 50, not allocated or linked to the invoice
	 * </ul>
	 */
	@Nested
	class DuplicatePaymentGetsOwnPayment
	{
		@Test
		void arrivingOnTheSameDay()
		{
			assertDuplicateGetsOwnPayment(0, X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);
		}

		/**
		 * On this branch, {@code AbstractPaymentDAO#retrievePaymentIds} filters candidate payments on an exact
		 * {@code DateTrx} match, so a receipt arriving one day after the first payment never matches it and is
		 * therefore not flagged as {@code Duplicate_Payment} here -- it still receives its own payment, which is
		 * the money-is-booked guarantee this test pins.
		 */
		@Test
		void arrivingOnALaterDay()
		{
			assertDuplicateGetsOwnPayment(1, null);
		}

		/**
		 * A later "Process ESR" run must NOT close a line the import flagged Duplicate_Payment.
		 * <p>
		 * The action pass ({@code ESRImportBL.handleEsrImportLine(String, I_ESR_ImportLine)}) only checks that
		 * ESR_Payment_Action is non-null before running a handler and setting Processed. Since the import sets
		 * Duplicate_Payment itself, that flag would otherwise be read as "the accountant decided" and the line
		 * would be closed -- observed in UAT, where completing the import for an unrelated line silently closed
		 * a duplicate line. Duplicate_Payment is not offered in the action dropdown at all, so the accountant
		 * must still pick one of the overpayment actions; the line has to stay open until then.
		 */
		@Test
		void completingTheImportMustNotCloseTheFlaggedDuplicate()
		{
			final String grandTotal = "50";
			final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
			final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);

			final String partnerValue = "123456";
			final String invDocNo = "654321";
			final String ESR_Rendered_AccountNo = "01-067789-3";

			final I_ESR_ImportLine esrImportLine1 = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, ESR_Rendered_AccountNo, partnerValue, "50", false);
			esrImportLine1.setESRLineText(esrLineText);
			final Timestamp paymentDate = TimeUtil.getDay(2024, 1, 10);
			esrImportLine1.setPaymentDate(paymentDate);
			save(esrImportLine1);
			esrImportBL.process(esrImportLine1.getESR_Import());

			final I_ESR_ImportLine esrImportLine2 = createESR_ImportLineFromOtherLine(esrImportLine1);
			esrImportLine2.setESRLineText(esrLineText);
			esrImportLine2.setPaymentDate(paymentDate);
			save(esrImportLine2);
			final I_ESR_Import esrImport2 = esrImportLine2.getESR_Import();
			esrImportBL.process(esrImport2);

			refresh(esrImportLine2, true);
			assertThat(esrImportLine2.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);
			assertThat(esrImportLine2.isProcessed()).as("precondition: the import leaves the flagged duplicate open").isFalse();
			final int paymentIdAfterImport = esrImportLine2.getC_Payment_ID();

			// the accountant completes the import without having chosen an overpayment action
			esrImportBL.complete(esrImport2, "completing the import");

			refresh(esrImportLine2, true);
			assertThat(esrImportLine2.isProcessed())
					.as("a flagged duplicate must stay OPEN until an overpayment action is chosen")
					.isFalse();
			assertThat(esrImportLine2.getESR_Payment_Action())
					.as("the flag itself must survive, so the accountant still sees why the line is open")
					.isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);
			assertThat(esrImportLine2.getC_Payment_ID())
					.as("and its own payment must not be swapped or dropped")
					.isEqualTo(paymentIdAfterImport);
		}

		/**
		 * Duplicate recognised because the earlier payment is already allocated to this line's invoice.
		 * The sibling tests give both lines the same {@code ESRLineText}, so they never reach this route:
		 * hence the differing line text here, and the equal payment date so the candidate query still
		 * matches on branches that filter on an exact {@code DateTrx}.
		 */
		@Test
		void earlierPaymentAllocatedToTheSameInvoice_isDetectedViaTheInvoice_notTheLineText()
		{
			final String grandTotal = "50";
			final String esrLineText1 = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
			final String esrLineText2 = esrLineText1.replace("041100", "041200");

			assertThat(esrLineText2).as("the two bank lines must differ").isNotEqualTo(esrLineText1);
			assertThat(esrLineText2.length()).as("same record length").isEqualTo(esrLineText1.length());
			assertThat(ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText2))
					.as("both lines must still resolve the same invoice reference")
					.isEqualTo(ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText1));

			final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText1);
			final String partnerValue = "123456";
			final String invDocNo = "654321";
			final String ESR_Rendered_AccountNo = "01-067789-3";

			final I_ESR_ImportLine esrImportLine1 = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, ESR_Rendered_AccountNo, partnerValue, "50", false);
			esrImportLine1.setESRLineText(esrLineText1);
			final Timestamp paymentDate = TimeUtil.getDay(2024, 1, 10);
			esrImportLine1.setPaymentDate(paymentDate);
			save(esrImportLine1);
			esrImportBL.process(esrImportLine1.getESR_Import());

			refresh(esrImportLine1, true);
			assertThat(esrImportLine1.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts);
			final int firstPaymentId = esrImportLine1.getC_Payment_ID();
			assertThat(firstPaymentId).as("the first line must have produced a payment").isNotZero();

			final I_ESR_ImportLine esrImportLine2 = createESR_ImportLineFromOtherLine(esrImportLine1);
			esrImportLine2.setESRLineText(esrLineText2);
			esrImportLine2.setPaymentDate(paymentDate);
			save(esrImportLine2);
			esrImportBL.process(esrImportLine2.getESR_Import());

			refresh(esrImportLine2, true);
			assertThat(esrImportLine2.getESR_Payment_Action())
					.as("flagged through the invoice route, even though no other line carries this bank line")
					.isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Duplicate_Payment);
			assertThat(esrImportLine2.isProcessed())
					.as("must stay open until the accountant picks an overpayment action")
					.isFalse();
			assertThat(esrImportLine2.getC_Payment_ID())
					.as("the money that arrived a second time must be booked as its OWN payment")
					.isNotZero()
					.isNotEqualTo(firstPaymentId);
		}

		/**
		 * @param expectedPaymentAction the second line's expected {@code ESR_Payment_Action}, or {@code null} if it
		 * is expected to stay unflagged (see {@link #arrivingOnALaterDay()}).
		 */
		private void assertDuplicateGetsOwnPayment(final int daysAfterFirstPayment, final String expectedPaymentAction)
		{
			final String grandTotal = "50";
			final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
			final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);

			final String partnerValue = "123456";
			final String invDocNo = "654321";
			final String ESR_Rendered_AccountNo = "01-067789-3";

			final I_ESR_ImportLine esrImportLine1 = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* refNo, */ ESR_Rendered_AccountNo, partnerValue, "50", false);
			esrImportLine1.setESRLineText(esrLineText);
			final Timestamp paymentDate1 = TimeUtil.getDay(2024, 1, 10);
			esrImportLine1.setPaymentDate(paymentDate1);
			save(esrImportLine1);

			final I_ESR_Import esrImport = esrImportLine1.getESR_Import();

			esrImportBL.process(esrImport);

			final I_ESR_ImportLine esrImportLine2 = createESR_ImportLineFromOtherLine(esrImportLine1);
			esrImportLine2.setESRLineText(esrLineText);
			final Timestamp paymentDate2 = TimeUtil.addDays(paymentDate1, daysAfterFirstPayment);
			esrImportLine2.setPaymentDate(paymentDate2);
			save(esrImportLine2);
			final I_ESR_Import esrImport2 = esrImportLine2.getESR_Import();
			esrImportBL.process(esrImport2);

			// check first import line: unaffected, still fully matched and paid
			refresh(esrImportLine1, true);
			assertThat(esrImportLine1.isProcessed()).isTrue();
			assertThat(esrImportLine1.getESR_Payment_Action()).isEqualTo(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Fit_Amounts);

			// check second import line
			refresh(esrImportLine2, true);
			if (expectedPaymentAction == null)
			{
				// cross-day: the date-scoped duplicate search misses the earlier payment, so no flag is set
				assertThat(esrImportLine2.getESR_Payment_Action()).isNull();
			}
			else
			{
				// same-day: flagged as a duplicate payment, and remains unprocessed for the accountant
				assertThat(esrImportLine2.getESR_Payment_Action()).isEqualTo(expectedPaymentAction);
				assertThat(esrImportLine2.isProcessed()).as("the duplicate line must stay unprocessed so the accountant still sees it").isFalse();
				assertThat(esrImportLine2.getESR_Document_Status()).isEqualTo(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
			}

			// ... but it must have its OWN payment, not the first line's -- the money that arrived a second
			// time is booked either way, which is the guarantee this port exists to provide
			assertThat(esrImportLine2.getC_Payment_ID()).as("the second line must not carry the first line's C_Payment_ID").isNotEqualTo(esrImportLine1.getC_Payment_ID());

			final PaymentId esrImportLine2PaymentId = PaymentId.ofRepoIdOrNull(esrImportLine2.getC_Payment_ID());
			final I_C_Payment esrLine2Payment = esrImportLine2PaymentId == null ? null
					: paymentDAO.getById(esrImportLine2PaymentId);
			assertThat(esrLine2Payment).as("the second line must have created its own payment").isNotNull();
			assertThat(esrLine2Payment.getPayAmt()).isEqualByComparingTo("50");
			assertThat(esrLine2Payment.isAllocated()).as("the payment must not be allocated to the invoice").isFalse();
			assertThat(esrLine2Payment.getC_Invoice_ID()).as("the payment must not be linked to the invoice").isEqualTo(0);
		}
	}

	/**
	 * Re-processing the SAME already-processed ESR import must be idempotent.
	 * <ul>
	 * <li>invoice 50, one ESR line pays it in full
	 * <li>{@code esrImportBL.process(esrImport)} is called a SECOND time on the very same, already-processed import
	 * <li>production code refuses the second call (the header's {@code Processed=Y} guard throws
	 * {@code AdempiereException}) -- that guard IS the idempotency protection this test pins
	 * <li>the line must keep its original payment (no new {@code C_Payment_ID})
	 * <li>exactly ONE {@code C_Payment} must exist for the partner, counted from the actual table, not merely
	 * re-read from the line's FK
	 * </ul>
	 */
	@Nested
	class LineWithoutInvoice
	{
		/** Scaffolding for a line whose reference resolves to no invoice at all. */
		private I_ESR_Import importWithUnmatchableLine()
		{
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

			// the reference in this line belongs to no invoice in the system
			final String esrLineText = "01201067789300000001060000000000000000400000050009072  030014040914041014041100001006800000000000090                          ";
			final I_ESR_Import esrImport = createImport();
			esrImport.setAD_Org_ID(org.getAD_Org_ID());
			esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
			save(esrImport);

			esrImportBL.loadAndEvaluateESRImportStream(createImportFile(esrImport), new ByteArrayInputStream(esrLineText.getBytes()));
			return esrImport;
		}

		private int createPartner()
		{
			final I_C_BPartner bpartner = newInstance(I_C_BPartner.class, contextProvider);
			bpartner.setValue("payer-without-invoice");
			save(bpartner);
			return bpartner.getC_BPartner_ID();
		}

		/**
		 * An unmatchable line creates no payment, so nothing can be done with it; setting the partner by
		 * hand and processing again is the documented recovery, and it is what creates the payment.
		 */
		@Test
		void unknownReference_createsNoPaymentUntilThePartnerIsSetByHand()
		{
			final I_ESR_Import esrImport = importWithUnmatchableLine();
			esrImportBL.process(esrImport);

			final I_ESR_ImportLine line = ESRTestUtil.retrieveSingleLine(esrImport);
			refresh(line, true);
			assertThat(line.getC_Payment_ID()).as("no partner, so no payment can be created").isZero();
			assertThat(line.getC_Invoice_ID()).as("the reference matched no invoice").isZero();

			// the recovery: the accountant sets whoever actually paid
			line.setC_BPartner_ID(createPartner());
			line.setESR_IsManual_ReferenceNo(true); // 'Y' by default in the real DB, false in the POJO store
			save(line);
			esrImportBL.process(esrImport);

			refresh(line, true);
			assertThat(line.getC_Payment_ID())
					.as("processing again after the partner was set is what creates the payment")
					.isNotZero();
		}

		/**
		 * The payment such a line receives: booked for the full amount, but deliberately not allocated and
		 * with no action chosen, so the accountant still has to decide what happens to the money.
		 */
		@Test
		void theOwnPaymentIsCompletedUnallocatedAndLeavesTheActionOpen()
		{
			final I_ESR_Import esrImport = importWithUnmatchableLine();
			final I_ESR_ImportLine line = ESRTestUtil.retrieveSingleLine(esrImport);
			line.setC_BPartner_ID(createPartner());
			line.setESR_IsManual_ReferenceNo(true);
			save(line);

			esrImportBL.process(esrImport);

			refresh(line, true);
			final PaymentId paymentId = PaymentId.ofRepoIdOrNull(line.getC_Payment_ID());
			assertThat(paymentId).as("the line must have got its own payment").isNotNull();

			final I_C_Payment payment = paymentDAO.getById(paymentId);
			assertThat(payment.getPayAmt())
					.as("booked for the amount that arrived")
					.isEqualByComparingTo(line.getAmount());
			assertThat(payment.getC_Invoice_ID()).as("there is no invoice to link").isZero();
			assertThat(payment.isAllocated()).as("left unallocated for the accountant").isFalse();
			assertThat(payment.getDocStatus()).as("completed, so the money is booked").isEqualTo("CO");
			assertThat(line.getESR_Payment_Action())
					.as("no action is set for the accountant, so the line stays a visible todo")
					.isNull();
			assertThat(line.isProcessed()).as("and the line stays open").isFalse();
		}

		/**
		 * The refund branch that only a line WITHOUT an invoice reaches: there is no over-payment to
		 * compute against, so the whole received amount is what gets transferred back. The sibling test
		 * in ESRActionHandlerTest covers the with-invoice case, where only the excess is refunded.
		 */
		@Test
		void choosingRefund_transfersBackTheWholeReceivedAmount()
		{
			final I_ESR_Import esrImport = importWithUnmatchableLine();
			final I_ESR_ImportLine line = ESRTestUtil.retrieveSingleLine(esrImport);
			line.setC_BPartner_ID(createPartner());
			line.setESR_IsManual_ReferenceNo(true);
			save(line);
			esrImportBL.process(esrImport);

			refresh(line, true);
			final java.math.BigDecimal received = line.getAmount();
			assertThat(POJOLookupMap.get().getRecords(I_C_Payment.class))
					.as("guard: the import booked the incoming payment")
					.hasSize(1);

			line.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner);
			save(line);
			esrImportBL.registerActionHandler(
					X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner,
					new MoneyTransferedBackESRActionHandler());
			esrImportBL.complete(esrImport, "");

			final java.util.List<I_C_Payment> payments = POJOLookupMap.get().getRecords(I_C_Payment.class);
			assertThat(payments).as("an outbound payment must have been booked for the refund").hasSize(2);

			final I_C_Payment refund = payments.stream()
					.filter(pmt -> !pmt.isReceipt())
					.findFirst()
					.orElse(null);
			assertThat(refund).as("the refund is an OUTBOUND payment").isNotNull();
			assertThat(refund.getPayAmt())
					.as("with no invoice there is no excess to compute, so the whole amount goes back")
					.isEqualByComparingTo(received);
		}
	}

	@Test
	public void testReprocessSameImport_createsNoSecondPayment()
	{
		final String grandTotal = "50";
		final String esrLineText = "01201067789300000001060012345600654321400000050009072  030014040914041014041100001006800000000000090                          ";
		final String completeRef = ESRTransactionLineMatcherUtil.extractReferenceNumberStr(esrLineText);

		final String partnerValue = "123456";
		final String invDocNo = "654321";
		final String ESR_Rendered_AccountNo = "01-067789-3";

		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(invDocNo, grandTotal, false, completeRef, /* refNo, */ ESR_Rendered_AccountNo, partnerValue, "50", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		esrImportBL.process(esrImport);

		refresh(esrImportLine, true);
		assertThat(esrImportLine.isProcessed()).isTrue();
		final int originalPaymentId = esrImportLine.getC_Payment_ID();
		assertThat(originalPaymentId).as("first processing must have created a payment").isNotEqualTo(0);

		// process the very same, already-processed import a second time: the header-level
		// "Processed=Y" guard in ESRImportBL#processAndCountLines refuses the call outright
		final AdempiereException thrown = assertThrows(
				AdempiereException.class,
				() -> esrImportBL.process(esrImport));
		assertThat(thrown.getMessage()).contains("Processed");

		// the line must still point to its original payment, not a new one
		refresh(esrImportLine, true);
		assertThat(esrImportLine.getC_Payment_ID()).as("re-processing must not swap in a new payment").isEqualTo(originalPaymentId);

		// load-bearing assertion: count the partner's actual C_Payment rows, not just the line's FK,
		// so a stray second payment created for the same partner would be caught even if the line's FK was untouched
		final int partnerId = esrImportLine.getC_Invoice().getC_BPartner_ID();
		final long partnerPaymentCount = paymentDAO.streamPaymentIdsByBPartnerId(BPartnerId.ofRepoId(partnerId)).count();
		assertThat(partnerPaymentCount).as("exactly one C_Payment must exist for the partner after re-processing").isEqualTo(1L);
	}

}
