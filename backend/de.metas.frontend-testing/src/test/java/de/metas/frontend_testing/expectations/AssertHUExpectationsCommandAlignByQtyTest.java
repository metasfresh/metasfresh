package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link AssertHUExpectationsCommand#alignByQty}: CUs are matched to the expectations by qty, never dropping the qty check.
 */
public class AssertHUExpectationsCommandAlignByQtyTest
{
	private I_C_UOM uom;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setName("Stk");
		uom.setUOMSymbol("Stk");
		uom.setX12DE355("PCE");
		uom.setStdPrecision(0);
		InterfaceWrapperHelper.saveRecord(uom);
	}

	private Quantity qty(final int value)
	{
		return Quantity.of(BigDecimal.valueOf(value), uom);
	}

	private List<String> align(final List<Integer> expectedQtys, final List<Integer> actualQtys)
	{
		final List<String> actuals = actualQtys.stream().map(q -> "cu" + q).collect(ImmutableList.toImmutableList());
		return AssertHUExpectationsCommand.alignByQty(
				expectedQtys,
				q -> q != null ? qty(q) : null,
				actuals,
				cu -> qty(Integer.parseInt(cu.substring(2))));
	}

	@Test
	public void actualCreatedInOtherOrder_isAlignedToTheExpectation()
	{
		assertThat(align(ImmutableList.of(6, 4), ImmutableList.of(4, 6))).containsExactly("cu6", "cu4");
	}

	@Test
	public void wrongQty_staysMisaligned_soThePerCUQtyCheckFails()
	{
		// expected 4 + 4, actual 6 + 4: the second expectation (4) gets the 6, so the qty assertion of that pair is red
		assertThat(align(ImmutableList.of(4, 4), ImmutableList.of(6, 4))).containsExactly("cu4", "cu6");
	}
}
