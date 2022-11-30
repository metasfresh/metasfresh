package de.metas.invoicecandidate.api.impl;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.expectations.InOutLineExpectation;
import de.metas.inoutcandidate.spi.impl.QualityNoticesCollection;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class InvoiceCandBL_QtyAndQuality_FromInOutLine_Test // extends AbstractICTestSupport
{
	private IContextAware context;

	//
	// Master data
	private I_C_Invoice_Candidate invoiceCandidateRecord;

	private ProductId productId;

	private UomId icUomId;

	@BeforeClass
	public static void beforeClass()
	{
		AdempiereTestHelper.get().forceStaticInit();
	}

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM stockUomRecord = newInstance(I_C_UOM.class);
		saveRecord(stockUomRecord);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		saveRecord(productRecord);
		productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_C_UOM icUomRecord = newInstance(I_C_UOM.class);
		saveRecord(icUomRecord);
		icUomId = UomId.ofRepoId(icUomRecord.getC_UOM_ID());

		final I_C_UOM_Conversion uomConversionRecord = newInstance(I_C_UOM_Conversion.class);
		uomConversionRecord.setC_UOM_ID(stockUomRecord.getC_UOM_ID());
		uomConversionRecord.setC_UOM_To_ID(icUomRecord.getC_UOM_ID());
		uomConversionRecord.setMultiplyRate(TEN);
		uomConversionRecord.setDivideRate(ONE.divide(TEN));
		saveRecord(uomConversionRecord);

		this.invoiceCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		this.invoiceCandidateRecord.setM_Product_ID(productRecord.getM_Product_ID());
		this.invoiceCandidateRecord.setC_UOM_ID(icUomRecord.getC_UOM_ID());
		this.invoiceCandidateRecord.setC_Currency_ID(10);
		this.invoiceCandidateRecord.setIsSOTrx(false);
		this.invoiceCandidateRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		this.invoiceCandidateRecord.setIsInDispute(false);

		saveRecord(invoiceCandidateRecord);
	}

	@Test
	public void test1_noCatchWeight()
	{
		final StockQtyAndUOMQty qtys_100 = StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal("100"), productId);

		final I_M_InOutLine inoutLine01 = new InOutLineExpectation<>(null, context)
				.stockQtyAndMaybeCatchQty(qtys_100)
				.uomId(icUomId)
				.inDispute(false)
				.qualityNote(qualityNotices("note 1", "note 2", "note 3"))
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidateRecord, inoutLine01);

		final I_M_InOutLine inoutLine02 = new InOutLineExpectation<>(null, context)
				.stockQtyAndMaybeCatchQty(qtys_100)
				.uomId(icUomId)
				.inDispute(true)
				.qualityNote(qualityNotices("note 4", "note 5"))
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidateRecord, inoutLine02);

		final InvoiceCandidateRecordService invoiceCandidateRecordService = new InvoiceCandidateRecordService();
		final InvoiceCandidate invoiceCandidate = invoiceCandidateRecordService.ofRecord(invoiceCandidateRecord);
		invoiceCandidateRecordService.updateRecord(invoiceCandidate, invoiceCandidateRecord);

		InvoiceCandidateExpectation.newExpectation()
				.inDispute(true)
				.qtyWithIssues(new BigDecimal("100"))
				.qualityDiscountPercent(new BigDecimal("50"))
				.assertExpected(invoiceCandidateRecord);
	}

	@Test
	public void test2()
	{
		final StockQtyAndUOMQty qtys_10 = StockQtyAndUOMQtys.create(new BigDecimal("10"), productId, new BigDecimal("100"), icUomId);

		final I_M_InOutLine inoutLine01 = new InOutLineExpectation<>(null, context)
				.stockQtyAndMaybeCatchQty(qtys_10)
				.inDispute(false)
				.uomId(icUomId)
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidateRecord, inoutLine01);

		final I_M_InOutLine inoutLine02 = new InOutLineExpectation<>(null, context)
				.stockQtyAndMaybeCatchQty(qtys_10)
				.inDispute(false)
				.uomId(icUomId)
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidateRecord, inoutLine02);

		final I_M_InOutLine inoutLine03 = new InOutLineExpectation<>(null, context)
				.stockQtyAndMaybeCatchQty(qtys_10)
				.inDispute(true)
				.uomId(icUomId)
				.createInOutLine(I_M_InOutLine.class);
		invoiceCandidateInOutLine(invoiceCandidateRecord, inoutLine03);

		final InvoiceCandidateRecordService invoiceCandidateRecordService = new InvoiceCandidateRecordService();
		final InvoiceCandidate invoiceCandidate = invoiceCandidateRecordService.ofRecord(invoiceCandidateRecord);
		invoiceCandidateRecordService.updateRecord(invoiceCandidate, invoiceCandidateRecord);

		InvoiceCandidateExpectation.newExpectation()
				.inDispute(true)
				.qtyWithIssues(new BigDecimal("10"))
				.qualityDiscountPercent(new BigDecimal("33.33"))
				.assertExpected(invoiceCandidateRecord);
	}

	private void invoiceCandidateInOutLine(@NonNull final I_C_Invoice_Candidate icRecord, @NonNull final I_M_InOutLine iolRecord)
	{
		final I_C_InvoiceCandidate_InOutLine icIol = newInstance(I_C_InvoiceCandidate_InOutLine.class);
		icIol.setC_Invoice_Candidate(icRecord);
		Services.get(IInvoiceCandBL.class).updateICIOLAssociationFromIOL(icIol, iolRecord);
		saveRecord(icIol);
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
