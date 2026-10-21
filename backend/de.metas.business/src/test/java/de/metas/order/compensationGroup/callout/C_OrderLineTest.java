package de.metas.order.compensationGroup.callout;

import de.metas.business.BusinessTestHelper;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UomId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstance;
import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstanceFactory;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.NoUOMConversionException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Example line: uncalibrated qty 375 LTR, factor 0.8, Qty 300. Factors are given as stored and typed: a fraction.
 */
class C_OrderLineTest
{
	private C_OrderLine callout;
	private I_C_UOM ltr;
	private I_C_UOM ml;
	private I_C_UOM bucket;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		callout = new C_OrderLine();

		ltr = BusinessTestHelper.createUOM("LTR", null, 0);
		ml = BusinessTestHelper.createUOM("ML", null, 0);
		bucket = BusinessTestHelper.createUOM("BUCKET", null, 0);
	}

	/**
	 * As created from a menu template: rule, uncalibrated qty (in the line UOM) and factor stored.
	 */
	private I_C_OrderLine calibratedLine(final String base, final String factor, final String qty)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_UOM_ID(ltr.getC_UOM_ID());
		line.setC_CompensationGroup_CalibrationRule_ID(10);
		line.setGroupCompensationQtyEnteredUncalibrated(new BigDecimal(base));
		OrderGroupRepository.setCalibrationFactor(line, Percent.ofFraction(new BigDecimal(factor)));
		line.setQtyEntered(new BigDecimal(qty));
		saveRecord(line);
		return line;
	}

	/**
	 * A calibrated line in LTR of a product that can also be sold in buckets.
	 */
	private I_C_OrderLine calibratedLineWithBucketConversion(final String baseLtr, final String ltrPerBucket)
	{
		final I_M_Product product = BusinessTestHelper.createProduct("P", ltr);
		Services.get(IUOMConversionDAO.class).createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(bucket.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(ltr.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal(ltrPerBucket))
				.build());

		final I_C_OrderLine line = calibratedLine(baseLtr, "0.8", "300");
		line.setM_Product_ID(product.getM_Product_ID());
		saveRecord(line);
		return line;
	}

	/**
	 * The line before the changes is the saved one.
	 */
	private static ICalloutField calloutFieldWithLineBeforeChanges(final I_C_OrderLine line)
	{
		final I_C_OrderLine lineBeforeChanges = newInstance(I_C_OrderLine.class);
		lineBeforeChanges.setM_Product_ID(line.getM_Product_ID());
		lineBeforeChanges.setC_UOM_ID(line.getC_UOM_ID());
		lineBeforeChanges.setGroupCompensationQtyEnteredUncalibrated(InterfaceWrapperHelper.getValueOrNull(line, I_C_OrderLine.COLUMNNAME_GroupCompensationQtyEnteredUncalibrated));
		final ICalloutField field = Mockito.mock(ICalloutField.class);
		Mockito.when(field.getModelBeforeChanges(I_C_OrderLine.class)).thenReturn(lineBeforeChanges);
		return field;
	}

	private void changeUom(final I_C_OrderLine line, final I_C_UOM uom)
	{
		final ICalloutField field = calloutFieldWithLineBeforeChanges(line);
		line.setC_UOM_ID(uom.getC_UOM_ID());
		callout.onUomChanged(line, field);
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
		void lineWithoutRuleAndBase_isNoOp()
		{
			final I_C_OrderLine line = uncalibratedLine(true);
			typeFactor(line, "0.8");
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
		void newManualLine_factorTyped_isNoOp()
		{
			final I_C_OrderLine line = uncalibratedLine(false);
			typeFactor(line, "0.8");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("5");
		}

		@Test
		void lineWithoutBase_negativeFactorTyped_isNoOp()
		{
			final I_C_OrderLine line = uncalibratedLine(true);
			typeFactor(line, "-0.5");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("5");
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
		void afterUomChange_factor1_givesTheConvertedBase()
		{
			final I_C_OrderLine line = calibratedLineWithBucketConversion("375", "25");
			changeUom(line, bucket);
			typeFactor(line, "1");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("15"); // 375 LTR = 15 buckets
		}

		@Test
		void afterUomChange_factor0_8_gives12Buckets()
		{
			final I_C_OrderLine line = calibratedLineWithBucketConversion("375", "25");
			changeUom(line, bucket);
			typeFactor(line, "0.8");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("12");
		}

		@Test
		void afterUomChange_baseRoundedUpAtConversion_thenFactorApplied()
		{
			// 100 LTR = 12.5 buckets (1 bucket = 8 LTR), bucket precision 0: base 13; x 0.4 = 5.2 -> 6
			final I_C_OrderLine line = calibratedLineWithBucketConversion("100", "8");
			changeUom(line, bucket);
			typeFactor(line, "0.4");
			assertThat(line.getQtyEntered()).isEqualByComparingTo("6");
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
		void afterUomChange_factorFromConvertedBase()
		{
			final I_C_OrderLine line = calibratedLineWithBucketConversion("375", "25");
			changeUom(line, bucket);
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
		void uncalibratedLine_staysWithoutFactor()
		{
			final I_C_OrderLine line = uncalibratedLine(true);
			typeQty(line, "7");
			assertThat(OrderGroupRepository.getCalibrationFactor(line)).isNull();
		}
	}

	@Nested
	class UomChanged
	{
		@Test
		void toConvertibleUom_convertsTheBase()
		{
			final I_C_OrderLine line = calibratedLineWithBucketConversion("375", "25");
			changeUom(line, bucket);
			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("15");
			assertThat(OrderGroupRepository.getCalibrationFactor(line).toBigDecimalOneHundredBased()).isEqualByComparingTo("80");
			assertThat(line.getC_CompensationGroup_CalibrationRule_ID()).isEqualTo(10);
		}

		@Test
		void toConvertibleUom_roundsTheBaseUpToTheNewUomPrecision()
		{
			// 100 LTR = 3.333.. buckets (1 bucket = 30 LTR), bucket precision 0
			final I_C_OrderLine line = calibratedLineWithBucketConversion("100", "30");
			changeUom(line, bucket);
			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("4");
		}

		@Test
		void toUomWithoutConversion_isRefused()
		{
			final I_C_OrderLine line = calibratedLineWithBucketConversion("375", "25");
			assertThatThrownBy(() -> changeUom(line, ml)).isInstanceOf(NoUOMConversionException.class);
			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("375");
		}

		@Test
		void productChangedInTheSameEdit_leavesTheBase()
		{
			// CalloutOrder.product resets the UOM to the new product's stock UOM; that must not be blocked for a missing conversion
			final I_C_OrderLine line = calibratedLineWithBucketConversion("375", "25");
			final ICalloutField field = calloutFieldWithLineBeforeChanges(line);

			line.setM_Product_ID(BusinessTestHelper.createProduct("P2", ml).getM_Product_ID());
			line.setC_UOM_ID(ml.getC_UOM_ID());
			callout.onUomChanged(line, field);

			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("375");
		}

		@Test
		void secondUomChangeBeforeSave_convertsFromTheSavedUom()
		{
			// saved: 375 LTR; the user picks BUCKET (15), then LTR again, without saving in between
			final I_C_OrderLine line = calibratedLineWithBucketConversion("375", "25");
			final ICalloutField field = calloutFieldWithLineBeforeChanges(line);
			line.setC_UOM_ID(bucket.getC_UOM_ID());
			callout.onUomChanged(line, field);
			line.setC_UOM_ID(ltr.getC_UOM_ID());
			callout.onUomChanged(line, field);
			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("375");
		}

		@Test
		void uncalibratedLine_isLeftAlone()
		{
			final I_C_OrderLine line = uncalibratedLine(true);
			changeUom(line, ml); // no conversion LTR -> ML, but nothing to convert
			assertThat(InterfaceWrapperHelper.<BigDecimal>getValueOrNull(line, I_C_OrderLine.COLUMNNAME_GroupCompensationQtyEnteredUncalibrated)).isNull();
		}
	}

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
	 * See the {@link C_OrderLine} Javadoc.
	 */
	@Test
	void allColumnsAreServedByOneCalloutInstance()
	{
		final List<AnnotatedCalloutInstance> instances = new AnnotatedCalloutInstanceFactory()
				.setAnnotatedCalloutObject(callout)
				.create();

		assertThat(instances).hasSize(1);
		assertThat(instances.get(0).getColumnNames()).containsExactlyInAnyOrder(
				I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor,
				I_C_OrderLine.COLUMNNAME_QtyEntered,
				I_C_OrderLine.COLUMNNAME_C_UOM_ID);
	}
}
