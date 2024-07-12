package de.metas.material.planning.ddorder;

import de.metas.business.BusinessTestHelper;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.planning.exception.MrpException;
import de.metas.quantity.Quantity;
import de.metas.uom.X12DE355;
import de.metas.util.lang.Percent;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DDOrderPojoSupplierTest
{
	private ModelProductDescriptorExtractor modelProductDescriptorExtractor;
	private I_C_UOM uom;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		modelProductDescriptorExtractor = Mockito.mock(ModelProductDescriptorExtractor.class);
	}

	@Test
	public void test_calculateQtyToMove_ZeroQty()
	{
		uom = BusinessTestHelper.createUOM("uom", 0, X12DE355.KILOGRAM);

		test_calculateQtyToMove(
				new BigDecimal("0"), // qtyToMoveExpected
				new BigDecimal("0"), // qtyToMoveRequested
				new BigDecimal("100") // transferPercent
		);
	}

	@Test
	public void test_calculateQtyToMove_HighPrecision_Transfer100()
	{
		uom = BusinessTestHelper.createUOM("uom", 7, X12DE355.KILOGRAM);

		test_calculateQtyToMove(
				new BigDecimal("12.3456789"), // qtyToMoveExpected
				new BigDecimal("12.3456789"), // qtyToMoveRequested
				new BigDecimal("100") // transferPercent
		);
	}

	@Test
	public void test_calculateQtyToMove_HighPrecision_Transfer50()
	{
		uom = BusinessTestHelper.createUOM("uom", 8, X12DE355.KILOGRAM);

		test_calculateQtyToMove(
				new BigDecimal("15.22222222"), // qtyToMoveExpected
				new BigDecimal("30.44444444"), // qtyToMoveRequested
				new BigDecimal("50") // transferPercent
		);
	}

	@Test
	public void test_calculateQtyToMove_HighPrecision_Transfer10()
	{
		uom = BusinessTestHelper.createUOM("uom", 16, X12DE355.KILOGRAM);

		test_calculateQtyToMove(
				new BigDecimal("03.0123713812937129"), // qtyToMoveExpected
				new BigDecimal("30.123713812937129"), // qtyToMoveRequested
				new BigDecimal("10") // transferPercent
		);
	}

	@Test
	public void test_calculateQtyToMove_AnyQty_Transfer0()
	{
		uom = BusinessTestHelper.createUOM("uom", 16, X12DE355.KILOGRAM);

		test_calculateQtyToMove(
				new BigDecimal("0"), // qtyToMoveExpected
				new BigDecimal("30.123713812937129"), // qtyToMoveRequested
				new BigDecimal("0") // transferPercent
		);
	}

	@Test
	public void test_calculateQtyToMove_AnyQty_TransferNegative()
	{
		uom = BusinessTestHelper.createUOM("uom", 16, X12DE355.KILOGRAM);

		assertThatThrownBy(() -> test_calculateQtyToMove(
				new BigDecimal("99999999999999999"), // qtyToMoveExpected - does not matter
				new BigDecimal("30.123713812937129"), // qtyToMoveRequested
				new BigDecimal("-10") // transferPercent
		))
				//
				.isInstanceOf(MrpException.class);
	}

	private void test_calculateQtyToMove(
			final BigDecimal qtyToMoveExpected,
			final BigDecimal qtyToMoveRequested,
			final BigDecimal transferPercent)
	{
		final DDOrderPojoSupplier ddOrderPojoSupplier = new DDOrderPojoSupplier(modelProductDescriptorExtractor);

		final BigDecimal qtyToMoveActual = ddOrderPojoSupplier
				.calculateQtyToMove(Quantity.of(qtyToMoveRequested, uom), Percent.of(transferPercent))
				.toBigDecimal();

		assertThat(qtyToMoveActual)
				.as("QtyToMove for " + " QtyToMoveRequested=" + qtyToMoveRequested + ", TransferPercent=" + transferPercent)
				.isEqualByComparingTo(qtyToMoveExpected);
	}
}
