package de.metas.quantity;

import de.metas.business.BusinessTestHelper;
import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CapacityTest
{
	@BeforeEach
	void beforeEach() {AdempiereTestHelper.get().init();}

	@Test
	void testEquals()
	{
		final I_C_UOM uom = BusinessTestHelper.createUOM("uom");
		final Capacity capacity = Capacity.createCapacity(
				new BigDecimal("10"),
				ProductId.ofRepoId(111),
				uom,
				false);

		final I_C_UOM uomLoaded = InterfaceWrapperHelper.load(uom.getC_UOM_ID(), I_C_UOM.class);
		assertThat(uomLoaded).isNotSameAs(uom);

		final Capacity capacity2 = Capacity.createCapacity(
				new BigDecimal("10"),
				ProductId.ofRepoId(111),
				uomLoaded,
				false);

		assertThat(capacity2)
				.isEqualTo(capacity)
				.isNotSameAs(capacity)
				.hasSameHashCodeAs(capacity);
	}
}