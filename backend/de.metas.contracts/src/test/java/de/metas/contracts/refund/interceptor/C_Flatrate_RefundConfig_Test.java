package de.metas.contracts.refund.interceptor;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundConfigs;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateInvalidator;
import org.mockito.Mockito;
import de.metas.invoice.service.InvoiceScheduleRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_InvoiceSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class C_Flatrate_RefundConfig_Test
{
	private C_Flatrate_RefundConfig interceptor;
	private I_C_Flatrate_Conditions conditions;
	private I_C_InvoiceSchedule schedule;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		saveRecord(newInstance(I_C_UOM.class));

		final RefundConfigRepository refundConfigRepository = new RefundConfigRepository(new InvoiceScheduleRepository());
		interceptor = new C_Flatrate_RefundConfig(refundConfigRepository, new RefundContractRepository(refundConfigRepository), Mockito.mock(RefundInvoiceCandidateInvalidator.class), Mockito.mock(AssignmentToRefundCandidateRepository.class));

		conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		schedule = newInstance(I_C_InvoiceSchedule.class);
		schedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		schedule.setInvoiceDay(31);
		schedule.setInvoiceDistance(1);
		saveRecord(schedule);
	}

	/** saving a refund line with a monthly schedule whose distance does not divide the year (refund periods are calendar periods) is rejected */
	@Test
	public void assertValid_monthlyScheduleOfFiveMonths_fails()
	{
		schedule.setInvoiceDistance(5);
		saveRecord(schedule);

		assertThatThrownBy(() -> interceptor.assertValid(createConfig(0, 40, 41)))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_CALENDAR_INVOICE_DISTANCE.toAD_Message()));
	}

	/** A config of a product category does not need a product: it applies to every product of the category. */
	@Test
	public void assertValid_categoryWithBonusProduct_withoutProduct_isValid()
	{
		assertThatCode(() -> interceptor.assertValid(createConfig(0, 40, 41))).doesNotThrowAnyException();
	}

	/** a product and a bonus product, no category: the bonus product must not be demanded just because a category is missing */
	@Test
	public void assertValid_productAndBonusProduct_withoutCategory_isValid()
	{
		assertThatCode(() -> interceptor.assertValid(createConfig(30, 0, 40))).doesNotThrowAnyException();
	}

	/** a product and a bonus product, with a category: the bonus product is no reason to reject the line */
	@Test
	public void assertValid_productAndBonusProduct_withCategory_isValid()
	{
		assertThatCode(() -> interceptor.assertValid(createConfig(30, 50, 40))).doesNotThrowAnyException();
	}

	/** Only active lines have to share the category: an inactive line is not used by the engine, so it can be edited or deactivated freely. */
	@Test
	public void assertValid_inactiveLineWithAnotherProductCategory_isValid()
	{
		createConfig(0, 40, 41);
		final I_C_Flatrate_RefundConfig inactiveConfig = createConfig(0, 50, 41);
		inactiveConfig.setIsActive(false);
		assertThatCode(() -> interceptor.assertValid(inactiveConfig)).doesNotThrowAnyException();
	}

	/** The cross-line check is skipped for an inactive line, the single-line check is not: it would still need a product or a bonus product. */
	@Test
	public void assertValid_inactiveLineWithoutProductAndBonusProduct_fails()
	{
		final I_C_Flatrate_RefundConfig inactiveConfig = createConfig(0, 50, 0);
		inactiveConfig.setIsActive(false);
		assertThatThrownBy(() -> interceptor.assertValid(inactiveConfig))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_BONUS_PRODUCT_REQUIRED.toAD_Message()));
	}

	/** A line that is activated again is compared with the active lines of the condition again. */
	@Test
	public void assertValid_reactivatingALineWithAnotherProductCategory_fails()
	{
		createConfig(0, 40, 41);
		final I_C_Flatrate_RefundConfig config = createConfig(0, 50, 41);
		config.setIsActive(false);
		saveRecord(config);

		config.setIsActive(true);
		assertThatThrownBy(() -> interceptor.assertValid(config))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_SAME_PRODUCT_CATEGORY.toAD_Message()));
	}

	/** A line of a product is compared with the lines without product as well, not just with the ones of its own product. */
	@Test
	public void assertValid_changingTheCategoryOfAProductLine_nextToAnotherLineOfTheOldCategory_fails()
	{
		final I_C_Flatrate_RefundConfig productConfig = createConfig(30, 40, 41);
		createConfig(0, 40, 41);

		productConfig.setM_Product_Category_ID(41);
		assertThatThrownBy(() -> interceptor.assertValid(productConfig))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_SAME_PRODUCT_CATEGORY.toAD_Message()));
	}

	@Test
	public void assertValid_productWithoutBonusProduct_isValid()
	{
		assertThatCode(() -> interceptor.assertValid(createConfig(30, 0, 0))).doesNotThrowAnyException();
	}

	/** Neither a product nor a bonus product: the refund line would be booked on the product that was sold. */
	@Test
	public void assertValid_withoutProductAndWithoutBonusProduct_fails()
	{
		assertThatThrownBy(() -> interceptor.assertValid(createConfig(0, 40, 0)))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException adempiereException = (AdempiereException)ex;
					assertThat(adempiereException.isUserValidationError()).isTrue();
					assertThat(adempiereException.getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_BONUS_PRODUCT_REQUIRED.toAD_Message());
				});
	}

	/** A second line of the condition with another product category: the engine would book on an arbitrary line's percentage. */
	@Test
	public void assertValid_secondLineWithAnotherProductCategory_fails()
	{
		createConfig(0, 40, 41);
		assertThatThrownBy(() -> interceptor.assertValid(createConfig(0, 50, 41)))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_SAME_PRODUCT_CATEGORY.toAD_Message()));
	}

	@Test
	public void assertValid_secondLineWithTheSameProductCategory_isValid()
	{
		createConfig(0, 40, 41);
		assertThatCode(() -> interceptor.assertValid(createConfig(0, 40, 41))).doesNotThrowAnyException();
	}

	/** The record's own stored state must not count against its new state, or the category of a condition's only line could never be changed. */
	@Test
	public void assertValid_changingTheProductCategoryOfTheOnlyLine_isValid()
	{
		final I_C_Flatrate_RefundConfig config = createConfig(0, 40, 41);
		config.setM_Product_Category_ID(50);
		assertThatCode(() -> interceptor.assertValid(config)).doesNotThrowAnyException();
	}

	/** A bonus that the customer deducts at payment is a percentage of the net goods value, booked on the bonus product. */
	@Test
	public void assertValid_deductedAtPayment_withPercentageAndBonusProduct_isValid()
	{
		final I_C_Flatrate_RefundConfig config = createConfig(0, 40, 41);
		config.setIsDeductedAtPayment(true);
		assertThatCode(() -> interceptor.assertValid(config)).doesNotThrowAnyException();
	}

	/** A product is enough for a refund line of the refund engine, but not for the bonus at payment: it is booked on a bonus product. */
	@Test
	public void assertValid_deductedAtPayment_withoutBonusProduct_fails()
	{
		final I_C_Flatrate_RefundConfig config = createConfig(30, 0, 0);
		config.setIsDeductedAtPayment(true);
		assertThatThrownBy(() -> interceptor.assertValid(config))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException adempiereException = (AdempiereException)ex;
					assertThat(adempiereException.isUserValidationError()).isTrue();
					assertThat(adempiereException.getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_DEDUCTED_AT_PAYMENT_NEEDS_PERCENTAGE_AND_BONUS_PRODUCT.toAD_Message());
				});
	}

	/** The bonus at payment is computed as a percentage; an amount per quantity has no base when paying. */
	@Test
	public void assertValid_deductedAtPayment_withAmountBase_fails()
	{
		final I_C_Flatrate_RefundConfig config = createConfig(0, 40, 41);
		config.setIsDeductedAtPayment(true);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Amount);
		config.setRefundAmt(BigDecimal.ONE);
		config.setC_Currency_ID(102);
		assertThatThrownBy(() -> interceptor.assertValid(config))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_DEDUCTED_AT_PAYMENT_NEEDS_PERCENTAGE_AND_BONUS_PRODUCT.toAD_Message()));
	}

	/** An inactive line is not used for the bonus at payment, so a line that is not valid for it can still be deactivated (e.g. to fix it). */
	@Test
	public void assertValid_inactiveDeductedAtPaymentLineWithoutBonusProduct_isValid()
	{
		final I_C_Flatrate_RefundConfig config = createConfig(30, 0, 0);
		config.setIsDeductedAtPayment(true);
		config.setIsActive(false);
		assertThatCode(() -> interceptor.assertValid(config)).doesNotThrowAnyException();
	}

	/**
	 * The bonus at payment is one flat percentage per condition: a second line (e.g. another product with another percentage) would get no bonus,
	 * because the minimum quantity is no threshold when paying. Separate bonuses are separate conditions.
	 */
	@Test
	public void assertValid_deductedAtPayment_secondActiveLine_fails()
	{
		final I_C_Flatrate_RefundConfig firstConfig = createConfig(30, 0, 41);
		firstConfig.setIsDeductedAtPayment(true);
		saveRecord(firstConfig);

		final I_C_Flatrate_RefundConfig secondConfig = createConfig(31, 0, 41);
		secondConfig.setIsDeductedAtPayment(true);
		assertThatThrownBy(() -> interceptor.assertValid(secondConfig))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException adempiereException = (AdempiereException)ex;
					assertThat(adempiereException.isUserValidationError()).isTrue();
					assertThat(adempiereException.getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_DEDUCTED_AT_PAYMENT_SINGLE_LINE.toAD_Message());
				});
	}

	/**
	 * A condition that already has a line deducted at payment gets no second line, whatever the new line's own flag (which is N by default):
	 * the user is told that right away, not first that the lines must agree on the flag.
	 */
	@Test
	public void assertValid_secondLineNotFlaggedNextToADeductedAtPaymentLine_failsWithTheSingleLineMessage()
	{
		final I_C_Flatrate_RefundConfig firstConfig = createConfig(30, 0, 41);
		firstConfig.setIsDeductedAtPayment(true);
		saveRecord(firstConfig);

		final I_C_Flatrate_RefundConfig secondConfig = createConfig(31, 0, 41); // IsDeductedAtPayment=N
		assertThatThrownBy(() -> interceptor.assertValid(secondConfig))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_DEDUCTED_AT_PAYMENT_SINGLE_LINE.toAD_Message()));
	}

	/** The minimum quantity of the only line is no threshold when paying either, so it must be 0. */
	@Test
	public void assertValid_deductedAtPayment_minQtyAboveZero_fails()
	{
		final I_C_Flatrate_RefundConfig config = createConfig(0, 40, 41);
		config.setIsDeductedAtPayment(true);
		config.setMinQty(new BigDecimal("100"));
		assertThatThrownBy(() -> interceptor.assertValid(config))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_DEDUCTED_AT_PAYMENT_SINGLE_LINE.toAD_Message()));
	}

	/** A line that is deactivated does not count: the condition's other line can be the only active one. */
	@Test
	public void assertValid_deductedAtPayment_secondLineNextToAnInactiveLine_isValid()
	{
		final I_C_Flatrate_RefundConfig firstConfig = createConfig(30, 0, 41);
		firstConfig.setIsDeductedAtPayment(true);
		firstConfig.setIsActive(false);
		saveRecord(firstConfig);

		final I_C_Flatrate_RefundConfig secondConfig = createConfig(31, 0, 41);
		secondConfig.setIsDeductedAtPayment(true);
		assertThatCode(() -> interceptor.assertValid(secondConfig)).doesNotThrowAnyException();
	}

	/** Without completed contracts, the condition is still being set up: the flag can be changed. */
	@Test
	public void assertDeductedAtPaymentNotChanged_withoutCompletedContracts_isValid()
	{
		final I_C_Flatrate_RefundConfig config = createConfig(0, 40, 41);
		createTerm(X_C_Flatrate_Term.DOCSTATUS_Drafted);

		config.setIsDeductedAtPayment(true);
		assertThatCode(() -> interceptor.assertDeductedAtPaymentNotChanged(config)).doesNotThrowAnyException();
	}

	/**
	 * With completed contracts, the refund engine already created refund candidates (or the customer already deducted bonuses at payment) under the old setting;
	 * changing it would refund the same sales twice, or not at all.
	 */
	@Test
	public void assertDeductedAtPaymentNotChanged_withCompletedContract_fails()
	{
		final I_C_Flatrate_RefundConfig config = createConfig(0, 40, 41);
		createTerm(X_C_Flatrate_Term.DOCSTATUS_Completed);

		config.setIsDeductedAtPayment(true);
		assertThatThrownBy(() -> interceptor.assertDeductedAtPaymentNotChanged(config))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final AdempiereException adempiereException = (AdempiereException)ex;
					assertThat(adempiereException.isUserValidationError()).isTrue();
					assertThat(adempiereException.getErrorCode()).isEqualTo(RefundConfigs.MSG_REFUND_CONFIG_DEDUCTED_AT_PAYMENT_NOT_CHANGEABLE.toAD_Message());
				});
	}

	private void createTerm(final String docStatus)
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		term.setDocStatus(docStatus);
		term.setProcessed(X_C_Flatrate_Term.DOCSTATUS_Completed.equals(docStatus));
		saveRecord(term);
	}

	private I_C_Flatrate_RefundConfig createConfig(final int productId, final int categoryId, final int bonusProductId)
	{
		final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		config.setC_InvoiceSchedule_ID(schedule.getC_InvoiceSchedule_ID());
		config.setM_Product_ID(productId);
		config.setM_Product_Category_ID(categoryId);
		config.setBonus_Product_ID(bonusProductId);
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		config.setRefundPercent(BigDecimal.TEN);
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		config.setMinQty(BigDecimal.ZERO);
		saveRecord(config); // the WebUI also has the id before the interceptor runs
		return config;
	}
}
