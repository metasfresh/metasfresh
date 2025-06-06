package de.metas.material.planning.pporder;

import de.metas.business.BusinessTestHelper;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class OrderBOMLineQuantitiesTest
{
	private I_C_UOM uomEach;
	private I_C_UOM uomKg;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomEach = BusinessTestHelper.createUomEach();
		uomKg = BusinessTestHelper.createUomKg();
	}

	@Test
	void convertQuantities()
	{
		final OrderBOMLineQuantities qtys = OrderBOMLineQuantities.ofQtyRequired(Quantity.of("123.123", uomEach));
		final OrderBOMLineQuantities qtys2 = qtys.convertQuantities(qty -> Quantity.of(qty.toBigDecimal().multiply(BigDecimal.TEN), uomKg));
		Assertions.assertThat(qtys2.getQtyRequired()).isEqualTo(Quantity.of("1231.23", uomKg));
	}
}