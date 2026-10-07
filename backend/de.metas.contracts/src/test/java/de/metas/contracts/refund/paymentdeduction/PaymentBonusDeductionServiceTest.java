package de.metas.contracts.refund.paymentdeduction;

import de.metas.bpartner.BPartnerId;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig_PackingOption;
import de.metas.contracts.refund.packaging.RefundPackagingMaterialProvider;
import de.metas.handlingunits.HUPIItemProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Tax;
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
import de.metas.contracts.refund.RefundConfigs;
import org.adempiere.exceptions.AdempiereException;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.packaging.RefundPackagingFilter;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentbonus.PaymentBonusCreditMemoService;
import de.metas.invoice.paymentbonus.PaymentBonusDeduction;
import de.metas.invoice.paymentbonus.PaymentBonusDeductionLine;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import java.util.List;
import lombok.Getter;
import de.metas.invoice.service.impl.PlainInvoiceDAO;
import de.metas.invoice.service.IInvoiceDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

		service = newService(new RefundPackagingFilter(Optional.empty()));

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

	/**
	 * The discount line that a compensation-group contract put on the order (the on-invoice bonus) is not part of the base:
	 * the bonus at payment is computed on the goods value before that on-invoice discount, even if the discount product is in the goods category.
	 */
	@Test
	void contractCompensationLine_isNotInTheBase()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2.6", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);
		createInvoiceLineOfCandidates(invoiceId, "-3", createCandidateInGroup(createCompensationGroupContract(), true));

		assertThat(computeNetBonus(invoiceId)).isEqualByComparingTo("2.60"); // 2.6 % of the 100 goods, not of 97
	}

	/**
	 * Invoicing can aggregate the discount lines of several orders into one invoice line, which then has no order line;
	 * the line is still recognised through its invoice candidates.
	 */
	@Test
	void contractCompensationLinesAggregatedIntoOneLineWithoutOrderLine_areNotInTheBase()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2.6", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);
		final I_C_Flatrate_Term contract = createCompensationGroupContract();
		final I_C_InvoiceLine aggregatedLine = createInvoiceLineOfCandidates(invoiceId, "-6", createCandidateInGroup(contract, true), createCandidateInGroup(contract, true));
		assertThat(aggregatedLine.getC_OrderLine_ID()).isLessThanOrEqualTo(0); // guard

		assertThat(computeNetBonus(invoiceId)).isEqualByComparingTo("2.60");
	}

	/**
	 * A discount line of a group the user put together on the order (no contract) reduces what the customer pays, so it stays in the base.
	 */
	@Test
	void manualCompensationLine_staysInTheBase()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2.6", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);
		createInvoiceLineOfCandidates(invoiceId, "-3", createCandidateInGroup(null, true));

		assertThat(computeNetBonus(invoiceId)).isEqualByComparingTo("2.52"); // 2.6 % of 97 = 2.522
	}

	/**
	 * The goods of a contract-created group are the base; only the group's discount line is left out.
	 */
	@Test
	void goodsLineOfAContractGroup_staysInTheBase()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2.6", goodsBonusProduct);
		final I_C_Flatrate_Term contract = createCompensationGroupContract();
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLineOfCandidates(invoiceId, "100", createCandidateInGroup(contract, false));
		createInvoiceLineOfCandidates(invoiceId, "-3", createCandidateInGroup(contract, true));

		assertThat(computeNetBonus(invoiceId)).isEqualByComparingTo("2.60");
	}

	/**
	 * The discount lines of several invoices are recognised in one go; each invoice's bonus is on its own goods.
	 */
	@Test
	void calculatorOfSeveralInvoices_leavesOutTheContractCompensationLineOfEach()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2.6", goodsBonusProduct);
		final I_C_Flatrate_Term contract = createCompensationGroupContract();
		final InvoiceId invoiceId1 = createSalesInvoice();
		createInvoiceLine(invoiceId1, fruit, "100", null);
		createInvoiceLineOfCandidates(invoiceId1, "-3", createCandidateInGroup(contract, true));
		final InvoiceId invoiceId2 = createSalesInvoice();
		createInvoiceLine(invoiceId2, fruit, "200", null);
		createInvoiceLineOfCandidates(invoiceId2, "-6", createCandidateInGroup(contract, true));

		final PaymentBonusDeductionService.Calculator calculator = service.newCalculator(ImmutableSet.of(invoiceId1, invoiceId2));

		assertThat(calculator.computeForInvoice(invoiceId1).get().getNetAmount().toBigDecimal()).isEqualByComparingTo("2.60");
		assertThat(calculator.computeForInvoice(invoiceId2).get().getNetAmount().toBigDecimal()).isEqualByComparingTo("5.20");
	}

	private BigDecimal computeNetBonus(@NonNull final InvoiceId invoiceId)
	{
		final PaymentBonusDeduction deduction = service.computeForInvoice(invoiceId).get();
		return CollectionUtils.singleElement(deduction.getLines()).getNetAmt().toBigDecimal();
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

	/** The customer deducts the bonus once per invoice; a partial payment's second allocation does not deduct it again. */
	@Test
	void creditMemoAlreadyGenerated_noDeduction()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);

		final I_C_DocType creditMemoDocType = newInstance(I_C_DocType.class);
		creditMemoDocType.setDocBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo);
		creditMemoDocType.setDocSubType(X_C_DocType.DOCSUBTYPE_PaymentBonusCreditMemo);
		creditMemoDocType.setIsSOTrx(true);
		saveRecord(creditMemoDocType);
		final I_C_Invoice creditMemo = newInstance(I_C_Invoice.class);
		creditMemo.setC_DocTypeTarget_ID(creditMemoDocType.getC_DocType_ID());
		creditMemo.setRef_Invoice_ID(invoiceId.getRepoId());
		creditMemo.setDocStatus(DocStatus.Completed.getCode());
		saveRecord(creditMemo);

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

	/** The payment allocation view loads the contracts once for all its invoices; each invoice still gets its own bonus. */
	@Test
	void calculatorOfSeveralInvoices_computesTheBonusOfEachInvoice()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2.6", goodsBonusProduct);
		final InvoiceId invoiceId1 = createSalesInvoice();
		createInvoiceLine(invoiceId1, fruit, "100", null);
		final InvoiceId invoiceId2 = createSalesInvoice();
		createInvoiceLine(invoiceId2, fruit, "200", null);
		final InvoiceId invoiceIdNotLoaded = createSalesInvoice();
		createInvoiceLine(invoiceIdNotLoaded, fruit, "300", null);

		final PaymentBonusDeductionService.Calculator calculator = service.newCalculator(ImmutableSet.of(invoiceId1, invoiceId2));

		assertThat(calculator.computeForInvoice(invoiceId1).get().getNetAmount().toBigDecimal()).isEqualByComparingTo("2.60");
		assertThat(calculator.computeForInvoice(invoiceId2).get().getNetAmount().toBigDecimal()).isEqualByComparingTo("5.20");
		assertThat(calculator.computeForInvoice(invoiceIdNotLoaded)).isEmpty();
	}

	/**
	 * Only the contracts that are deducted at payment are loaded: a refund contract of the refund engine whose condition cannot be loaded
	 * (here: it has no config) must not keep the bonus of the customer's invoices from being computed.
	 */
	@Test
	void anotherRefundContractThatCannotBeLoaded_doesNotMatter()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2.6", goodsBonusProduct);

		final I_C_Flatrate_Conditions conditionsWithoutConfig = newInstance(I_C_Flatrate_Conditions.class);
		conditionsWithoutConfig.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditionsWithoutConfig);
		final I_C_Flatrate_Term termWithoutConfig = newInstance(I_C_Flatrate_Term.class);
		termWithoutConfig.setC_Flatrate_Conditions_ID(conditionsWithoutConfig.getC_Flatrate_Conditions_ID());
		termWithoutConfig.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		termWithoutConfig.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		termWithoutConfig.setBill_BPartner_ID(customerId.getRepoId());
		termWithoutConfig.setStartDate(TimeUtil.asTimestamp(LocalDate.parse("2026-07-01")));
		termWithoutConfig.setEndDate(TimeUtil.asTimestamp(LocalDate.parse("2026-12-31")));
		saveRecord(termWithoutConfig);

		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);

		assertThat(service.computeForInvoice(invoiceId).get().getNetAmount().toBigDecimal()).isEqualByComparingTo("2.60");
	}

	/**
	 * The minimum quantity is no threshold when paying, so the only line of a condition that is deducted at payment has minimum quantity 0 (validated when it is saved).
	 * One with another minimum quantity is rejected like when saving; in particular, the 0 % config that a contract adds for quantity 0 is never booked instead.
	 */
	@Test
	void singleConfigWithAMinimumQuantity_failsLikeWhenItIsSaved()
	{
		final I_C_Flatrate_RefundConfig config = createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		config.setMinQty(new BigDecimal("100"));
		saveRecord(config);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);

		assertThatThrownBy(() -> service.computeForInvoice(invoiceId))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_DEDUCTED_AT_PAYMENT_SINGLE_LINE.toAD_Message()));
	}

	/**
	 * A condition that is deducted at payment has one line (validated when a line is saved); one that has several anyway (e.g. from before that validation)
	 * is rejected like when saving, instead of silently booking one line's percentage.
	 */
	@Test
	void severalConfigs_failLikeWhenTheyAreSaved()
	{
		final I_C_Flatrate_RefundConfig config = createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2", goodsBonusProduct);
		final I_C_Flatrate_RefundConfig secondConfig = newInstance(I_C_Flatrate_RefundConfig.class);
		InterfaceWrapperHelper.copyValues(config, secondConfig);
		secondConfig.setMinQty(new BigDecimal("1000"));
		secondConfig.setRefundPercent(new BigDecimal("5"));
		saveRecord(secondConfig);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);

		assertThatThrownBy(() -> service.computeForInvoice(invoiceId))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_DEDUCTED_AT_PAYMENT_SINGLE_LINE.toAD_Message()));
	}

	/** The bonus is a percentage of the net goods value; the VAT that is included in the line amount is not part of it. */
	@Test
	void taxIncludedInvoice_bonusOnTheNetAmount()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "2.6", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		final I_C_Invoice invoice = loadOutOfTrx(invoiceId, I_C_Invoice.class);
		invoice.setIsTaxIncluded(true);
		saveRecord(invoice);

		final I_C_Tax tax = newInstance(I_C_Tax.class);
		tax.setName("7 %");
		tax.setRate(new BigDecimal("7"));
		tax.setC_TaxCategory_ID(1);
		tax.setValidFrom(TimeUtil.asTimestamp(LocalDate.parse("2020-01-01")));
		tax.setRequiresTaxCertificate("N");
		saveRecord(tax);
		final I_C_InvoiceLine line = createInvoiceLine(invoiceId, fruit, "107", null);
		line.setC_Tax_ID(tax.getC_Tax_ID());
		saveRecord(line);

		assertThat(service.computeForInvoice(invoiceId).get().getNetAmount().toBigDecimal()).isEqualByComparingTo("2.60"); // 2.6 % of 100, not of 107
	}

	/** A condition that is restricted to packing materials only takes the lines whose packing instruction has one of them. */
	@Test
	void packagingFilter_onlyTheLinesWithTheConditionsPackingMaterial()
	{
		final int crateMaterialId = 500;
		final int boxMaterialId = 600;
		final HUPIItemProductId crateInstructionId = HUPIItemProductId.ofRepoId(10);
		final HUPIItemProductId boxInstructionId = HUPIItemProductId.ofRepoId(20);
		final RefundPackagingMaterialProvider packingMaterialProvider = (huPIItemProductId, bpartnerId) -> Optional.of(huPIItemProductId.equals(crateInstructionId) ? crateMaterialId : boxMaterialId);
		service = newService(new RefundPackagingFilter(Optional.of(ImmutableList.of(packingMaterialProvider))));

		final I_C_Flatrate_RefundConfig config = createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "10", goodsBonusProduct);
		config.setIsPackingOptionFiltered(true);
		saveRecord(config);
		final I_C_Flatrate_RefundConfig_PackingOption option = newInstance(I_C_Flatrate_RefundConfig_PackingOption.class);
		option.setC_Flatrate_RefundConfig_ID(config.getC_Flatrate_RefundConfig_ID());
		option.setM_HU_PackingMaterial_ID(crateMaterialId);
		saveRecord(option);

		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", createOrderLine(customerId, crateInstructionId));
		createInvoiceLine(invoiceId, fruit, "50", createOrderLine(customerId, boxInstructionId));
		createInvoiceLine(invoiceId, fruit, "30", null); // no packing instruction

		assertThat(service.computeForInvoice(invoiceId).get().getNetAmount().toBigDecimal()).isEqualByComparingTo("10.00"); // 10 % of the 100 in crates only
	}

	/** Only periodic refund contracts (the usual case of a refund customer): no invoice line is loaded at all. */
	@Test
	void onlyContractsThatAreNotDeductedAtPayment_noLinesAreLoaded()
	{
		final I_C_Flatrate_RefundConfig config = createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		config.setIsDeductedAtPayment(false);
		saveRecord(config);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);
		final LineLoadCountingInvoiceDAO invoiceDAO = registerLineLoadCountingInvoiceDAO();

		assertThat(service.computeForInvoice(invoiceId)).isEmpty();
		assertThat(invoiceDAO.getLineLoads()).isZero();
	}

	/** A contract deducted at payment of another partner, whose bonus goes to that partner: the customer's invoice lines are not loaded. */
	@Test
	void deductedContractOfAnotherPartnerOnly_noLinesAreLoaded()
	{
		createDeductedAtPaymentTerm(shipmentPartnerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);
		final LineLoadCountingInvoiceDAO invoiceDAO = registerLineLoadCountingInvoiceDAO();

		assertThat(service.computeForInvoice(invoiceId)).isEmpty();
		assertThat(invoiceDAO.getLineLoads()).isZero();
	}

	/**
	 * The customer's contract is loaded for the view (it is valid at one of its invoices' dates), but not valid at the other invoice's date:
	 * only the lines of the invoice that it may apply to are loaded.
	 */
	@Test
	void contractNotValidAtAnInvoicesDate_thatInvoicesLinesAreNotLoaded()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct); // 2026-07-01 .. 2026-12-31
		// another partner's contract in the first half-year, so that there is a contract deducted at payment on the other invoice's date
		final I_C_Flatrate_RefundConfig otherConfig = createDeductedAtPaymentTerm(shipmentPartnerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		final I_C_Flatrate_Term otherTerm = loadOutOfTrx(retrieveTermIdOfConditions(otherConfig.getC_Flatrate_Conditions_ID()), I_C_Flatrate_Term.class);
		otherTerm.setStartDate(TimeUtil.asTimestamp(LocalDate.parse("2026-01-01")));
		otherTerm.setEndDate(TimeUtil.asTimestamp(LocalDate.parse("2026-06-30")));
		saveRecord(otherTerm);

		final InvoiceId julyInvoiceId = createSalesInvoice(); // 2026-07-15
		createInvoiceLine(julyInvoiceId, fruit, "100", null);
		final InvoiceId marchInvoiceId = createSalesInvoice();
		final I_C_Invoice marchInvoice = loadOutOfTrx(marchInvoiceId, I_C_Invoice.class);
		marchInvoice.setDateInvoiced(TimeUtil.asTimestamp(LocalDate.parse("2026-03-15")));
		saveRecord(marchInvoice);
		createInvoiceLine(marchInvoiceId, fruit, "100", null);
		final LineLoadCountingInvoiceDAO invoiceDAO = registerLineLoadCountingInvoiceDAO();

		final PaymentBonusDeductionService.Calculator calculator = service.newCalculator(ImmutableSet.of(julyInvoiceId, marchInvoiceId));

		assertThat(calculator.computeForInvoice(julyInvoiceId)).isPresent();
		assertThat(calculator.computeForInvoice(marchInvoiceId)).isEmpty();
		assertThat(invoiceDAO.getLineLoads()).isEqualTo(1);
	}

	/** The cached pre-check of the calculator: a periodic refund contract does not count, a contract that is deducted at payment does, on its dates only. */
	@Test
	void hasAnyDeductedAtPaymentContract()
	{
		final RefundContractRepository refundContractRepository = new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository()));
		final I_C_Flatrate_RefundConfig periodicConfig = createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		periodicConfig.setIsDeductedAtPayment(false);
		saveRecord(periodicConfig);
		refundContractRepository.resetCaches();
		assertThat(refundContractRepository.hasAnyDeductedAtPaymentContract(DATE_INVOICED)).isFalse();

		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct); // 2026-07-01 .. 2026-12-31
		refundContractRepository.resetCaches();
		assertThat(refundContractRepository.hasAnyDeductedAtPaymentContract(DATE_INVOICED)).isTrue();
		assertThat(refundContractRepository.hasAnyDeductedAtPaymentContract(LocalDate.parse("2026-06-30"))).isFalse();
	}

	/** the control of the two tests above: with a contract of the customer, the lines are loaded */
	@Test
	void deductedContractOfTheCustomer_linesAreLoaded()
	{
		createDeductedAtPaymentTerm(customerId, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_InvoicePartner, goodsCategory, "3", goodsBonusProduct);
		final InvoiceId invoiceId = createSalesInvoice();
		createInvoiceLine(invoiceId, fruit, "100", null);
		final LineLoadCountingInvoiceDAO invoiceDAO = registerLineLoadCountingInvoiceDAO();

		assertThat(service.computeForInvoice(invoiceId)).isPresent();
		assertThat(invoiceDAO.getLineLoads()).isEqualTo(1);
	}

	/** Registers an invoice DAO that counts how often invoice lines are loaded, and a new service that uses it. */
	private LineLoadCountingInvoiceDAO registerLineLoadCountingInvoiceDAO()
	{
		final LineLoadCountingInvoiceDAO invoiceDAO = new LineLoadCountingInvoiceDAO();
		Services.registerService(IInvoiceDAO.class, invoiceDAO);
		service = newService(new RefundPackagingFilter(Optional.empty()));
		return invoiceDAO;
	}

	private static class LineLoadCountingInvoiceDAO extends PlainInvoiceDAO
	{
		@Getter
		private int lineLoads = 0;

		@Override
		public List<de.metas.adempiere.model.I_C_InvoiceLine> retrieveLines(@NonNull final InvoiceId invoiceId)
		{
			lineLoads++;
			return super.retrieveLines(invoiceId);
		}
	}

	private PaymentBonusDeductionService newService(@NonNull final RefundPackagingFilter refundPackagingFilter)
	{
		return new PaymentBonusDeductionService(
				new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository())),
				refundPackagingFilter,
				(salesInvoice, bonusProductId) -> BONUS_TAX,
				new PaymentBonusCreditMemoService());
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
		return createOrderLine(orderPartnerId, null);
	}

	private I_C_OrderLine createOrderLine(@NonNull final BPartnerId orderPartnerId, @Nullable final HUPIItemProductId huPIItemProductId)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(orderPartnerId.getRepoId());
		order.setIsSOTrx(true);
		saveRecord(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		InterfaceWrapperHelper.create(orderLine, de.metas.interfaces.I_C_OrderLine.class).setM_HU_PI_Item_Product_ID(HUPIItemProductId.toRepoId(huPIItemProductId));
		saveRecord(orderLine);
		return orderLine;
	}

	private static I_C_Flatrate_Term createCompensationGroupContract()
	{
		final I_C_Flatrate_Term contract = newInstance(I_C_Flatrate_Term.class);
		contract.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_CompensationGroup);
		saveRecord(contract);
		return contract;
	}

	/**
	 * The invoice candidate of an order line in a compensation group of its own order.
	 *
	 * @param compensationGroupContract the contract that created the group; {@code null} for a group the user put together
	 * @param compensationLine          {@code true} for the group's discount line, {@code false} for one of its goods
	 */
	private I_C_Invoice_Candidate createCandidateInGroup(@Nullable final I_C_Flatrate_Term compensationGroupContract, final boolean compensationLine)
	{
		final I_C_OrderLine orderLine = createOrderLine(customerId);

		final I_C_Order_CompensationGroup group = newInstance(I_C_Order_CompensationGroup.class);
		group.setC_Order_ID(orderLine.getC_Order_ID());
		if (compensationGroupContract != null)
		{
			group.setC_Flatrate_Term_ID(compensationGroupContract.getC_Flatrate_Term_ID());
		}
		saveRecord(group);

		orderLine.setC_Order_CompensationGroup_ID(group.getC_Order_CompensationGroup_ID());
		orderLine.setIsGroupCompensationLine(compensationLine);
		saveRecord(orderLine);

		final I_C_Invoice_Candidate candidate = newInstance(I_C_Invoice_Candidate.class);
		candidate.setC_Order_ID(orderLine.getC_Order_ID());
		candidate.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		candidate.setC_Order_CompensationGroup_ID(group.getC_Order_CompensationGroup_ID());
		candidate.setIsGroupCompensationLine(compensationLine);
		saveRecord(candidate);
		return candidate;
	}

	/**
	 * An invoice line created from the given invoice candidates, linked to them like invoicing does ({@code C_Invoice_Line_Alloc}).
	 * It refers to the candidate's order line only if there is exactly one candidate.
	 */
	private I_C_InvoiceLine createInvoiceLineOfCandidates(
			@NonNull final InvoiceId invoiceId,
			@NonNull final String lineNetAmt,
			@NonNull final I_C_Invoice_Candidate... candidates)
	{
		final I_C_OrderLine orderLine = candidates.length == 1
				? InterfaceWrapperHelper.load(candidates[0].getC_OrderLine_ID(), I_C_OrderLine.class)
				: null;
		final I_C_InvoiceLine invoiceLine = createInvoiceLine(invoiceId, fruit, lineNetAmt, orderLine);
		for (final I_C_Invoice_Candidate candidate : candidates)
		{
			final I_C_Invoice_Line_Alloc alloc = newInstance(I_C_Invoice_Line_Alloc.class);
			alloc.setC_Invoice_Candidate_ID(candidate.getC_Invoice_Candidate_ID());
			alloc.setC_InvoiceLine_ID(invoiceLine.getC_InvoiceLine_ID());
			saveRecord(alloc);
		}
		return invoiceLine;
	}

	private I_C_InvoiceLine createInvoiceLine(
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
		return invoiceLine;
	}
}
