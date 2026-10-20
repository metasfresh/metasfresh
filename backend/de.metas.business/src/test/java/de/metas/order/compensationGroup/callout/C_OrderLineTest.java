package de.metas.order.compensationGroup.callout;

import de.metas.business.BusinessTestHelper;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstance;
import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstanceFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Calibration factor editable on the order line: factor -> QtyEntered and QtyEntered -> factor,
 * in any window.
 * <p>
 * Example line: uncalibrated (base) qty 375 LTR, factor 0.8 (80 %), Qty 300. Factors are given as stored and typed: a fraction.
 */
class C_OrderLineTest
{

	private C_OrderLine callout;
	private I_C_UOM ltr;
	private I_C_UOM ml;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		callout = new C_OrderLine(GroupTemplateRepository.newInstanceForUnitTesting());

		ltr = BusinessTestHelper.createUOM("LTR", null, 0);
		ml = BusinessTestHelper.createUOM("ML", null, 0);

	}

	private int templateLine(final I_C_UOM uom)
	{
		final I_C_CompensationGroup_Schema_TemplateLine templateLine = newInstance(I_C_CompensationGroup_Schema_TemplateLine.class);
		templateLine.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(templateLine);
		return templateLine.getC_CompensationGroup_Schema_TemplateLine_ID();
	}

	/**
	 * A calibrated line as created from a menu template: rule + base + factor stored, line UOM = template line UOM.
	 */
	private I_C_OrderLine calibratedLine(final String base, final String factor, final String qty)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_UOM_ID(ltr.getC_UOM_ID());
		line.setC_CompensationGroup_Schema_TemplateLine_ID(templateLine(ltr));
		line.setC_CompensationGroup_CalibrationRule_ID(10);
		line.setGroupCompensationQtyEnteredUncalibrated(new BigDecimal(base));
		OrderGroupRepository.setCalibrationFactor(line, Percent.ofNullableFraction(new BigDecimal(factor)));
		line.setQtyEntered(new BigDecimal(qty));
		saveRecord(line);
		return line;
	}


	/**
	 * A calibrated line whose UOM is a bucket (1 bucket = 25 LTR for the product), the template line / base being in LTR:
	 * base 375 LTR = 15 buckets, factor 0.8, Qty 12 buckets.
	 */
	private I_C_OrderLine calibratedLineInBuckets()
	{
		return calibratedLineInBuckets("375", "25");
	}

	private I_C_OrderLine calibratedLineInBuckets(final String baseLtr, final String ltrPerBucket)
	{
		final I_C_UOM bucket = BusinessTestHelper.createUOM("BUCKET", null, 0);
		final I_M_Product product = BusinessTestHelper.createProduct("P", ltr);
		Services.get(IUOMConversionDAO.class).createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(bucket.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(ltr.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal(ltrPerBucket))
				.build());

		final I_C_OrderLine line = calibratedLine(baseLtr, "0.8", "300");
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(bucket.getC_UOM_ID());
		line.setQtyEntered(new BigDecimal("12"));
		saveRecord(line);
		return line;
	}

	/**
	 * A manual or menu line: no rule, no base, no factor.
	 */
	private I_C_OrderLine uncalibratedLine(final boolean saved)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_UOM_ID(ltr.getC_UOM_ID());
		line.setQtyEntered(new BigDecimal("5"));
		if (saved)
		{
			saveRecord(line);
		}
		return line;
	}

	private void typeFactor(final I_C_OrderLine line, @Nullable final String factor)
	{
		OrderGroupRepository.setCalibrationFactor(line, Percent.ofNullableFraction(NumberUtils.asBigDecimal(factor)));
		callout.onCalibrationFactorChanged(line);
	}

	private void typeQty(final I_C_OrderLine line, final String qty)
	{
		line.setQtyEntered(new BigDecimal(qty));
		callout.onQtyEnteredChanged(line);
	}

	@Nested
	class FactorChanged
	{
		@Test
		void factor100_givesTheBase()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			typeFactor(line, "1");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("375");
			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("375");
			assertThat(line.getC_CompensationGroup_CalibrationRule_ID()).isEqualTo(10);
		}

		@Test
		void factor0_givesQty0()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			typeFactor(line, "0");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("0");
		}

		@Test
		void decimalFactor_roundsUpToTheUomPrecision()
		{
			// 375 x 93.33 % = 349.9875
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			typeFactor(line, "0.9333");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("350");

			ltr.setStdPrecision(2);
			saveRecord(ltr);
			final I_C_OrderLine line2 = calibratedLine("375", "0.8", "300");
			typeFactor(line2, "0.9333");
			assertThat(line2.getQtyEntered()).isEqualByComparingTo("349.99");
		}

		@Test
		void base0_givesQty0()
		{
			final I_C_OrderLine line = calibratedLine("0", "0.5", "0");
			line.setQtyEntered(new BigDecimal("3"));
			typeFactor(line, "1");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("0");
		}

		@Test
		void negativeFactor_isRefused()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			assertThatThrownBy(() -> typeFactor(line, "-0.01")).isInstanceOf(AdempiereException.class).hasMessageContaining("C_CompensationGroup_CalibrationRule_NegativeFactor").matches(AdempiereException::isUserValidationError, "user validation error");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("300");
		}

		@Test
		void emptyFactor_isRefused()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			assertThatThrownBy(() -> typeFactor(line, null)).isInstanceOf(AdempiereException.class).hasMessageContaining("C_OrderLine_CalibrationFactorMandatory").matches(AdempiereException::isUserValidationError, "user validation error");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("300");
		}

		@Test
		void lineWithoutRuleAndBase_isRefused()
		{
			final I_C_OrderLine line = uncalibratedLine(true);
			assertThatThrownBy(() -> typeFactor(line, "0.8")).isInstanceOf(AdempiereException.class).hasMessageContaining("C_OrderLine_CalibrationFactorNotCalibrated").matches(AdempiereException::isUserValidationError, "user validation error");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("5");
		}

		@Test
		void lineWithBaseButWithoutRule_factorTyped_computesQty()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			line.setC_CompensationGroup_CalibrationRule_ID(0);
			typeFactor(line, "1");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("375");
		}

		@Test
		void newManualLine_factorTyped_isRefused()
		{
			final I_C_OrderLine line = uncalibratedLine(false);
			assertThatThrownBy(() -> typeFactor(line, "0.8")).isInstanceOf(AdempiereException.class).hasMessageContaining("C_OrderLine_CalibrationFactorNotCalibrated").matches(AdempiereException::isUserValidationError, "user validation error");
		}

		@Test
		void newLine_emptyFactor_noBase_isNoOp()
		{
			final I_C_OrderLine line = uncalibratedLine(false);
			typeFactor(line, null);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("5");
			assertThat(OrderGroupRepository.getCalibrationFactor(line)).isNull();
		}

		@Test
		void lineUomConvertibleFromTemplateLineUom_baseIsConverted()
		{
			final I_C_OrderLine line = calibratedLineInBuckets();
			typeFactor(line, "1");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("15"); // 375 LTR = 15 buckets
			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("375");
		}

		@Test
		void convertedBase15Buckets_factor80_gives12()
		{
			final I_C_OrderLine line = calibratedLineInBuckets();
			typeFactor(line, "0.8");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("12");
		}

		@Test
		void convertedBaseWithMoreDecimalsThanTheUomPrecision_isRoundedUpOnce()
		{
			// 100 LTR = 3.333333 buckets (1 bucket = 30 LTR); bucket precision 0; x 100 % = 3.333333 -> 4
			final I_C_OrderLine line = calibratedLineInBuckets("100", "30");
			typeFactor(line, "1");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("4");
		}

		@Test
		void convertedBaseIsNotRoundedBeforeTheFactorIsApplied()
		{
			// 100 LTR = 12.5 buckets (1 bucket = 8 LTR), precision 0; x 40 % = 5 exactly (rounding the base to 13 first would give 5.2 -> 6)
			final I_C_OrderLine line = calibratedLineInBuckets("100", "8");
			typeFactor(line, "0.4");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("5");
		}

		@Test
		void lineUomWithoutConversion_isRefused()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			line.setC_UOM_ID(ml.getC_UOM_ID());
			assertThatThrownBy(() -> typeFactor(line, "1")).isInstanceOf(AdempiereException.class).hasMessageContaining("C_OrderLine_CalibrationFactorUOMChanged").matches(AdempiereException::isUserValidationError, "user validation error");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("300");
		}

	}

	@Nested
	class QtyEnteredChanged
	{
		@Test
		void qty350_givesFactor93_33()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			typeQty(line, "350");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("93.33");
			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("375");
			assertThat(line.getC_CompensationGroup_CalibrationRule_ID()).isEqualTo(10);
		}

		@Test
		void qty375_givesFactor100()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			typeQty(line, "375");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("100");
		}

		@Test
		void factorIsRoundedHalfUpTo2Decimals()
		{
			// 1 / 3 x 100 = 33.333..; 2 / 3 x 100 = 66.666..
			final I_C_OrderLine line = calibratedLine("3", "1", "3");
			typeQty(line, "1");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("33.33");
			typeQty(line, "2");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("66.67");
		}

		@Test
		void base0_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("0", "0.5", "0");
			typeQty(line, "2");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("50");
		}

		@Test
		void emptyQty_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			line.setQtyEntered(null);
			callout.onQtyEnteredChanged(line);
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("80");
		}

		@Test
		void negativeQty_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			typeQty(line, "-10");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("80");
		}

		@Test
		void lineUomConvertibleFromTemplateLineUom_factorFromConvertedBase()
		{
			final I_C_OrderLine line = calibratedLineInBuckets();
			typeQty(line, "12"); // 12 of 15 buckets
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("80");
			typeQty(line, "15");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("100");
		}

		@Test
		void lineWithFactorButWithoutRule_qtyEdit_updatesFactor()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			line.setC_CompensationGroup_CalibrationRule_ID(0);
			typeQty(line, "375");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("100");
		}

		@Test
		void lineUomWithoutConversion_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			line.setC_UOM_ID(ml.getC_UOM_ID());
			typeQty(line, "375");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("80");
		}

		@Test
		void uncalibratedLine_staysWithoutFactor()
		{
			final I_C_OrderLine line = uncalibratedLine(true);
			typeQty(line, "7");
			assertThat(OrderGroupRepository.getCalibrationFactor(line)).isNull();
		}
	}

	/**
	 * The column holds the factor as a fraction (0.8 = 80 %, 1 = unchanged), which is what the user types in the WebUI.
	 */
	@Nested
	class StoredScale
	{
		@Test
		void typedFraction0_8_onBase375_gives300()
		{
			final I_C_OrderLine line = calibratedLine("375", "1", "375");
			line.setGroupCompensationCalibrationFactor(new BigDecimal("0.8"));
			callout.onCalibrationFactorChanged(line);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("300");
		}

		@Test
		void qty350_storesFraction0_9333()
		{
			final I_C_OrderLine line = calibratedLine("375", "0.8", "300");
			typeQty(line, "350");
			// 350 / 375 = 0.93333.. -> 93.33 % -> 0.9333
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("0.9333");
		}
	}

	/**
	 * Both methods must be one callout instance: the callout executor does not re-enter an instance that is already running,
	 * which stops the cycle factor -> QtyEntered -> factor. Neither may skip indirect calls, so a TU qty edit,
	 * which sets QtyEntered from another callout, still updates the factor.
	 */
	@Test
	void bothColumnsAreServedByOneCalloutInstance()
	{
		final List<AnnotatedCalloutInstance> instances = new AnnotatedCalloutInstanceFactory()
				.setAnnotatedCalloutObject(callout)
				.create();

		assertThat(instances).hasSize(1);
		assertThat(instances.get(0).getColumnNames()).containsExactlyInAnyOrder(
				I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor,
				I_C_OrderLine.COLUMNNAME_QtyEntered);
	}
}
