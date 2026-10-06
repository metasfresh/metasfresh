package de.metas.contracts.refund.paymentdeduction;

import de.metas.bpartner.BPartnerId;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import org.compiere.model.I_C_InvoiceSchedule;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import org.compiere.model.X_C_InvoiceSchedule;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.packaging.RefundPackagingFilter;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentbonus.PaymentBonusDeduction;
import de.metas.invoice.paymentbonus.PaymentBonusDeductionLine;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class PaymentBonusDeductionServiceTest
{
	private static final LocalDate DATE_INVOICED = LocalDate.parse("2026-07-15");

	/** The bonus products are taxed with 7 %. */
	private static final Tax BONUS_TAX = Tax.builder()
			.taxId(TaxId.ofRepoId(7))
			.name("7 %")
			.orgId(OrgId.ANY)
			.validFrom(TimeUtil.asTimestamp(LocalDate.parse("2020-01-01")))
			.taxCategoryId(TaxCategoryId.ofRepoId(1))
			.rate(new BigDecimal("7"))
			.isTaxExempt(false)
			.requiresTaxCertificate(false)
			.seqNo(10)
			.build();

	private PaymentBonusDeductionService service;

	private CurrencyId currencyId;
	private int invoiceScheduleId;
	private BPartnerId customerId;
	private BPartnerId shipmentPartnerId;
	private I_M_Product_Category goodsCategory;
	private I_M_Product_Category packagingCategory;
	private ProductId fruit;
	private ProductId crate;
	private ProductId goodsBonusProduct;
	private ProductId packagingBonusProduct;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		service = new PaymentBonusDeductionService(
				new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository())),
				new RefundPackagingFilter(Optional.empty()),
				(salesInvoice, bonusProductId) -> BONUS_TAX);

		currencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();

		final I_C_InvoiceSchedule invoiceSchedule = newInstance(I_C_InvoiceSchedule.class);
		invoiceSchedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		invoiceSchedule.setInvoiceDay(31);
		invoiceSchedule.setInvoiceDistance(1);
		saveRecord(invoiceSchedule);
		invoiceScheduleId = invoiceSchedule.getC_InvoiceSchedule_ID();

		customerId = createBPartner();
		shipmentPartnerId = createBPartner();

		// goods > fruit; the base category of a contract includes its sub-categories
		goodsCategory = createProductCategory(null);
		final I_M_Product_Category fruitCategory = createProductCategory(goodsCategory);
		packagingCategory = createProductCategory(null);
		final I_M_Product_Category bonusCategory = createProductCategory(null);

		fruit = createProduct(fruitCategory);
		crate = createProduct(packagingCategory);
		goodsBonusProduct = createProduct(bonusCategory);
		packagingBonusProduct = createProduct(bonusCategory);
	}

	@Test
	void bonusOnTheNetGoodsValueOnly_includingSubCategories()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2.6", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);
		createInvoiceLine(invoiceId, crate, "50", null);

		final PaymentBonusDeduction deduction = service.computeForInvoice(invoiceId).get();

		assertThat(deduction.getInvoiceId()).isEqualTo(invoiceId);
		assertThat(deduction.getCustomerId()).isEqualTo(customerId);
		assertThat(deduction.getLines()).hasSize(1);
		final PaymentBonusDeductionLine line = deduction.getLines().get(0);
		assertThat(line.getBonusProductId()).isEqualTo(goodsBonusProduct);
		assertThat(line.getTax()).isEqualTo(BONUS_TAX);
		assertThat(line.getNetAmt().toBigDecimal()).isEqualByComparingTo("2.60"); // 2.6 % of the 100 goods, not of the 50 packaging
		assertThat(deduction.getGrossAmount().toBigDecimal()).isEqualByComparingTo("2.78"); // + 7 % VAT of the bonus product
	}

	@Test
	void twoTermsWithTwoBonusProducts_twoLines()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "5.2", goodsBonusProduct);
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "0.8", packagingBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "200", null);

		final PaymentBonusDeduction deduction = service.computeForInvoice(invoiceId).get();

		assertThat(deduction.getLines())
				.extracting(PaymentBonusDeductionLine::getBonusProductId, line -> line.getNetAmt().toBigDecimal().setScale(2))
				.containsExactlyInAnyOrder(
						tuple(goodsBonusProduct, new BigDecimal("10.40")),
						tuple(packagingBonusProduct, new BigDecimal("1.60")));
		assertThat(deduction.getGrossAmount().toBigDecimal()).isEqualByComparingTo("12.84"); // 12.00 + 7 % of the sum
	}

	@Test
	void shipmentPartnerRecipient_matchesTheLinesShippedToThatPartner()
	{
		createDeductedAtPaymentTerm(shipmentPartnerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_ShipmentPartner, goodsCategory, "3", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", createOrderLine(shipmentPartnerId));
		createInvoiceLine(invoiceId, fruit, "1000", createOrderLine(customerId)); // shipped to the invoice partner itself

		final PaymentBonusDeduction deduction = service.computeForInvoice(invoiceId).get();

		assertThat(deduction.getLines()).hasSize(1);
		assertThat(deduction.getLines().get(0).getNetAmt().toBigDecimal()).isEqualByComparingTo("3.00");
		assertThat(deduction.getCustomerId()).isEqualTo(customerId); // the invoice partner deducts it
	}

	@Test
	void shipmentPartnerRecipient_doesNotMatchTheInvoicePartnersOwnTerm()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_ShipmentPartner, goodsCategory, "3", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", createOrderLine(shipmentPartnerId));

		assertThat(service.computeForInvoice(invoiceId)).isEmpty();
	}

	@Test
	void invoicePartnerRecipient_doesNotMatchAnotherPartnersTerm()
	{
		createDeductedAtPaymentTerm(shipmentPartnerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", createOrderLine(shipmentPartnerId));

		assertThat(service.computeForInvoice(invoiceId)).isEmpty();
	}

	@Test
	void termThatIsNotDeductedAtPayment_noDeduction()
	{
		final I_C_Flatrate_RefundConfig config = createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		config.setIsDeductedAtPayment(false);
		saveRecord(config);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);

		assertThat(service.computeForInvoice(invoiceId)).isEmpty();
	}

	@Test
	void emptyBase_noDeduction()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, crate, "100", null);

		assertThat(service.computeForInvoice(invoiceId)).isEmpty();
	}

	@Test
	void termNotValidAtTheInvoiceDate_noDeduction()
	{
		final I_C_Flatrate_RefundConfig config = createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		final I_C_Flatrate_Term term = loadOutOfTrx(retrieveTermIdOfConditions(config.getC_Flatrate_Conditions_ID()), I_C_Flatrate_Term.class);
		term.setStartDate(TimeUtil.asTimestamp(DATE_INVOICED.plusDays(1)));
		saveRecord(term);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);

		assertThat(service.computeForInvoice(invoiceId)).isEmpty();
	}

	@Test
	void purchaseInvoice_noDeduction()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		final InvoiceId invoiceId = createInvoice(false, X_C_DocType.DOCBASETYPE_APInvoice);
		createInvoiceLine(invoiceId, fruit, "100", null);

		assertThat(service.computeForInvoice(invoiceId)).isEmpty();
	}

	@Test
	void salesCreditMemo_noDeduction()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		final InvoiceId invoiceId = createInvoice(true, X_C_DocType.DOCBASETYPE_ARCreditMemo);
		createInvoiceLine(invoiceId, fruit, "100", null);

		assertThat(service.computeForInvoice(invoiceId)).isEmpty();
	}

	private BPartnerId createBPartner()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		saveRecord(bpartner);
		return BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
	}

	private I_M_Product_Category createProductCategory(@Nullable final I_M_Product_Category parent)
	{
		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		category.setM_Product_Category_Parent_ID(parent != null ? parent.getM_Product_Category_ID() : -1);
		saveRecord(category);
		return category;
	}

	private ProductId createProduct(@NonNull final I_M_Product_Category category)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(category.getM_Product_Category_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private I_C_Flatrate_RefundConfig createDeductedAtPaymentTerm(
			@NonNull final BPartnerId termPartnerId,
			@NonNull final String bonusRecipient,
			@NonNull final I_M_Product_Category baseCategory,
			@NonNull final String percent,
			@NonNull final ProductId bonusProductId)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		config.setM_Product_Category_ID(baseCategory.getM_Product_Category_ID());
		config.setBonus_Product_ID(bonusProductId.getRepoId());
		config.setBonusRecipient(bonusRecipient);
		config.setIsDeductedAtPayment(true);
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Creditmemo);
		config.setC_InvoiceSchedule_ID(invoiceScheduleId);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		config.setRefundPercent(new BigDecimal(percent));
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		saveRecord(config);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		term.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term.setBill_BPartner_ID(termPartnerId.getRepoId());
		term.setStartDate(TimeUtil.asTimestamp(LocalDate.parse("2026-07-01")));
		term.setEndDate(TimeUtil.asTimestamp(LocalDate.parse("2026-12-31")));
		saveRecord(term);

		return config;
	}

	private static int retrieveTermIdOfConditions(final int conditionsId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, conditionsId)
				.create()
				.firstIdOnly();
	}

	private InvoiceId createSalesInvoice()
	{
		return createInvoice(true, X_C_DocType.DOCBASETYPE_ARInvoice);
	}

	private InvoiceId createInvoice(final boolean soTrx, @NonNull final String docBaseType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType);
		docType.setIsSOTrx(soTrx);
		saveRecord(docType);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(1);
		invoice.setIsSOTrx(soTrx);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setC_DocTypeTarget_ID(docType.getC_DocType_ID());
		invoice.setC_BPartner_ID(customerId.getRepoId());
		invoice.setC_Currency_ID(currencyId.getRepoId());
		invoice.setDateInvoiced(TimeUtil.asTimestamp(DATE_INVOICED));
		invoice.setIsTaxIncluded(false);
		saveRecord(invoice);
		return InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
	}

	private I_C_OrderLine createOrderLine(@NonNull final BPartnerId orderPartnerId)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(orderPartnerId.getRepoId());
		order.setIsSOTrx(true);
		saveRecord(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		saveRecord(orderLine);
		return orderLine;
	}

	private void createInvoiceLine(
			@NonNull final InvoiceId invoiceId,
			@NonNull final ProductId productId,
			@NonNull final String lineNetAmt,
			@Nullable final I_C_OrderLine orderLine)
	{
		final I_C_InvoiceLine invoiceLine = newInstance(I_C_InvoiceLine.class);
		invoiceLine.setC_Invoice_ID(invoiceId.getRepoId());
		invoiceLine.setM_Product_ID(productId.getRepoId());
		invoiceLine.setLineNetAmt(new BigDecimal(lineNetAmt));
		if (orderLine != null)
		{
			invoiceLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
			invoiceLine.setC_Order_ID(orderLine.getC_Order_ID());
		}
		saveRecord(invoiceLine);
	}
}
