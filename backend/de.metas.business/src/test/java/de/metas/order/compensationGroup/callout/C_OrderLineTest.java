package de.metas.order.compensationGroup.callout;

import de.metas.business.BusinessTestHelper;
import de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine;
import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstance;
import org.adempiere.ad.callout.annotations.api.impl.AnnotatedCalloutInstanceFactory;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Kalibrierfaktor editable on the order line: factor -> QtyEntered and QtyEntered -> factor,
 * only on a tab whose factor field is editable (core window 143 keeps it read-only, so nothing changes there).
 * <p>
 * Example line: uncalibrated (base) qty 375 LTR, rule factor 80, Qty 300.
 */
class C_OrderLineTest
{
	private static final int TAB_FACTOR_EDITABLE = 1000001;
	private static final int TAB_FACTOR_READONLY = 187;
	private static final int TAB_WITHOUT_FACTOR_FIELD = 999;

	private C_OrderLine callout;
	private I_C_UOM ltr;
	private I_C_UOM ml;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		callout = new C_OrderLine();

		ltr = BusinessTestHelper.createUOM("LTR", null, 0);
		ml = BusinessTestHelper.createUOM("ML", null, 0);

		final I_AD_Column factorColumn = newInstance(I_AD_Column.class);
		factorColumn.setColumnName(I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor);
		saveRecord(factorColumn);
		final I_AD_Column qtyColumn = newInstance(I_AD_Column.class);
		qtyColumn.setColumnName(I_C_OrderLine.COLUMNNAME_QtyEntered);
		saveRecord(qtyColumn);

		createField(TAB_FACTOR_EDITABLE, factorColumn, false);
		createField(TAB_FACTOR_EDITABLE, qtyColumn, false);
		createField(TAB_FACTOR_READONLY, factorColumn, true);
		createField(TAB_FACTOR_READONLY, qtyColumn, false);
		createField(TAB_WITHOUT_FACTOR_FIELD, qtyColumn, false);
	}

	private static void createField(final int tabId, final I_AD_Column column, final boolean readOnly)
	{
		final I_AD_Field field = newInstance(I_AD_Field.class);
		field.setAD_Tab_ID(tabId);
		field.setAD_Column_ID(column.getAD_Column_ID());
		field.setIsReadOnly(readOnly);
		saveRecord(field);
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
		line.setGroupCompensationCalibrationFactor(new BigDecimal(factor));
		line.setQtyEntered(new BigDecimal(qty));
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

	private static ICalloutField fieldOnTab(final int tabId)
	{
		final ICalloutRecord record = mock(ICalloutRecord.class);
		when(record.getAD_Tab_ID()).thenReturn(tabId);
		final ICalloutField field = mock(ICalloutField.class);
		when(field.getCalloutRecord()).thenReturn(record);
		return field;
	}

	private void typeFactor(final I_C_OrderLine line, @Nullable final String factor, final int tabId)
	{
		line.setGroupCompensationCalibrationFactor(factor != null ? new BigDecimal(factor) : null);
		callout.onCalibrationFactorChanged(line, fieldOnTab(tabId));
	}

	private void typeQty(final I_C_OrderLine line, final String qty, final int tabId)
	{
		line.setQtyEntered(new BigDecimal(qty));
		callout.onQtyEnteredChanged(line, fieldOnTab(tabId));
	}

	@Nested
	class FactorChanged
	{
		@Test
		void factor100_givesTheBase()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			typeFactor(line, "100", TAB_FACTOR_EDITABLE);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("375");
			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("375");
			assertThat(line.getC_CompensationGroup_CalibrationRule_ID()).isEqualTo(10);
		}

		@Test
		void factor0_givesQty0()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			typeFactor(line, "0", TAB_FACTOR_EDITABLE);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("0");
		}

		@Test
		void decimalFactor_roundsUpToTheUomPrecision()
		{
			// 375 x 93.33 % = 349.9875
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			typeFactor(line, "93.33", TAB_FACTOR_EDITABLE);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("350");

			ltr.setStdPrecision(2);
			saveRecord(ltr);
			final I_C_OrderLine line2 = calibratedLine("375", "80", "300");
			typeFactor(line2, "93.33", TAB_FACTOR_EDITABLE);
			assertThat(line2.getQtyEntered()).isEqualByComparingTo("349.99");
		}

		@Test
		void base0_givesQty0()
		{
			final I_C_OrderLine line = calibratedLine("0", "50", "0");
			line.setQtyEntered(new BigDecimal("3"));
			typeFactor(line, "100", TAB_FACTOR_EDITABLE);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("0");
		}

		@Test
		void negativeFactor_isRefused()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			assertThatThrownBy(() -> typeFactor(line, "-1", TAB_FACTOR_EDITABLE)).isInstanceOf(AdempiereException.class);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("300");
		}

		@Test
		void emptyFactor_isRefused()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			assertThatThrownBy(() -> typeFactor(line, null, TAB_FACTOR_EDITABLE)).isInstanceOf(AdempiereException.class);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("300");
		}

		@Test
		void lineWithoutRuleAndBase_isRefused()
		{
			final I_C_OrderLine line = uncalibratedLine(true);
			assertThatThrownBy(() -> typeFactor(line, "80", TAB_FACTOR_EDITABLE)).isInstanceOf(AdempiereException.class);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("5");
		}

		@Test
		void newManualLine_factorTyped_isRefused()
		{
			final I_C_OrderLine line = uncalibratedLine(false);
			assertThatThrownBy(() -> typeFactor(line, "80", TAB_FACTOR_EDITABLE)).isInstanceOf(AdempiereException.class);
		}

		@Test
		void newLine_emptyFactor_noBase_isNoOp()
		{
			final I_C_OrderLine line = uncalibratedLine(false);
			typeFactor(line, null, TAB_FACTOR_EDITABLE);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("5");
			assertThat(InterfaceWrapperHelper.<BigDecimal>getValueOrNull(line, I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor)).isNull();
		}

		@Test
		void lineUomDiffersFromTemplateLineUom_isRefused()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			line.setC_UOM_ID(ml.getC_UOM_ID());
			assertThatThrownBy(() -> typeFactor(line, "100", TAB_FACTOR_EDITABLE)).isInstanceOf(AdempiereException.class);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("300");
		}

		@Test
		void readOnlyTab_leavesQty()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			typeFactor(line, "100", TAB_FACTOR_READONLY);
			assertThat(line.getQtyEntered()).isEqualByComparingTo("300");
		}
	}

	@Nested
	class QtyEnteredChanged
	{
		@Test
		void qty350_givesFactor93_33()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			typeQty(line, "350", TAB_FACTOR_EDITABLE);
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("93.33");
			assertThat(line.getGroupCompensationQtyEnteredUncalibrated()).isEqualByComparingTo("375");
			assertThat(line.getC_CompensationGroup_CalibrationRule_ID()).isEqualTo(10);
		}

		@Test
		void qty375_givesFactor100()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			typeQty(line, "375", TAB_FACTOR_EDITABLE);
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("100");
		}

		@Test
		void factorIsRoundedHalfUpTo2Decimals()
		{
			// 1 / 3 x 100 = 33.333..; 2 / 3 x 100 = 66.666..
			final I_C_OrderLine line = calibratedLine("3", "100", "3");
			typeQty(line, "1", TAB_FACTOR_EDITABLE);
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("33.33");
			typeQty(line, "2", TAB_FACTOR_EDITABLE);
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("66.67");
		}

		@Test
		void base0_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("0", "50", "0");
			typeQty(line, "2", TAB_FACTOR_EDITABLE);
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("50");
		}

		@Test
		void emptyQty_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			line.setQtyEntered(null);
			callout.onQtyEnteredChanged(line, fieldOnTab(TAB_FACTOR_EDITABLE));
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("80");
		}

		@Test
		void negativeQty_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			typeQty(line, "-10", TAB_FACTOR_EDITABLE);
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("80");
		}

		@Test
		void lineUomDiffersFromTemplateLineUom_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			line.setC_UOM_ID(ml.getC_UOM_ID());
			typeQty(line, "375", TAB_FACTOR_EDITABLE);
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("80");
		}

		@Test
		void readOnlyTab_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			typeQty(line, "375", TAB_FACTOR_READONLY);
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("80");
		}

		@Test
		void tabWithoutFactorField_leavesFactor()
		{
			final I_C_OrderLine line = calibratedLine("375", "80", "300");
			typeQty(line, "375", TAB_WITHOUT_FACTOR_FIELD);
			assertThat(line.getGroupCompensationCalibrationFactor()).isEqualByComparingTo("80");
		}

		@Test
		void uncalibratedLine_staysWithoutFactor()
		{
			final I_C_OrderLine line = uncalibratedLine(true);
			typeQty(line, "7", TAB_FACTOR_EDITABLE);
			assertThat(InterfaceWrapperHelper.<BigDecimal>getValueOrNull(line, I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor)).isNull();
		}
	}

	/**
	 * Both methods must be one callout instance: the callout executor does not re-enter an instance that is already running,
	 * which stops the cycle factor -> QtyEntered -> factor. Neither may skip indirect calls, so a Gebindemenge (TU) edit,
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
