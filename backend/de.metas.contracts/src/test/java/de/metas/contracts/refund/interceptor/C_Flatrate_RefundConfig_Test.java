package de.metas.contracts.refund.interceptor;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundConfigs;
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

		interceptor = new C_Flatrate_RefundConfig(new RefundConfigRepository(new InvoiceScheduleRepository()));

		conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		schedule = newInstance(I_C_InvoiceSchedule.class);
		schedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		schedule.setInvoiceDay(31);
		schedule.setInvoiceDistance(1);
		saveRecord(schedule);
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
