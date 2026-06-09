package de.metas.handlingunits.storage;

import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class ProductAvailableStockPerLocatorTest
{
	private HUTestHelper helper;
	private IHandlingUnitsBL handlingUnitsBL;

	private I_C_UOM uomKg;
	private ProductId productId;
	private ProductId otherProductId;

	private LocatorId locatorA;
	private LocatorId locatorB;
	private LocatorId locatorC;

	@BeforeEach
	void beforeEach()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		uomKg = BusinessTestHelper.createUOM("Kg", 3, 3);
		productId = BusinessTestHelper.createProductId("Product", uomKg);
		otherProductId = BusinessTestHelper.createProductId("OtherProduct", uomKg);

		final I_M_Warehouse warehouse = BusinessTestHelper.createWarehouse("WH");
		locatorA = LocatorId.ofRecord(BusinessTestHelper.createLocator("locA", warehouse));
		locatorB = LocatorId.ofRecord(BusinessTestHelper.createLocator("locB", warehouse));
		locatorC = LocatorId.ofRecord(BusinessTestHelper.createLocator("locC", warehouse));
	}

	private void createActiveHU(final ProductId productId, final String qtyInKg, final LocatorId locatorId)
	{
		helper.newVHU()
				.productId(productId)
				.qty(Quantity.of(qtyInKg, uomKg))
				.huStatus(X_M_HU.HUSTATUS_Active)
				.locatorId(locatorId)
				.build();
	}

	@Test
	void getQtyOnHandByLocator_groupsByLocator_andOmitsLocatorsWithoutStock()
	{
		// stock of the product we ask for: 10 in A, 7 in B
		createActiveHU(productId, "10", locatorA);
		createActiveHU(productId, "7", locatorB);
		// stock of a different product in C => C must NOT appear in the result
		createActiveHU(otherProductId, "5", locatorC);

		final ProductAvailableStockPerLocator service = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);

		final Map<LocatorId, Quantity> result = service.getQtyOnHandByLocator(
				productId,
				ImmutableSet.of(locatorA, locatorB, locatorC));

		assertThat(result).containsOnlyKeys(locatorA, locatorB);
		assertThat(result.get(locatorA)).isEqualTo(Quantity.of("10", uomKg));
		assertThat(result.get(locatorB)).isEqualTo(Quantity.of("7", uomKg));
	}

	@Test
	void getQtyOnHandByLocator_sumsMultipleHUsInSameLocator()
	{
		createActiveHU(productId, "10", locatorA);
		createActiveHU(productId, "4", locatorA);

		final ProductAvailableStockPerLocator service = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);

		final Map<LocatorId, Quantity> result = service.getQtyOnHandByLocator(
				productId,
				ImmutableSet.of(locatorA));

		assertThat(result).containsOnlyKeys(locatorA);
		assertThat(result.get(locatorA)).isEqualTo(Quantity.of("14", uomKg));
	}

	@Test
	void getQtyOnHandByLocator_emptyLocatorIds_returnsEmptyMap()
	{
		createActiveHU(productId, "10", locatorA);

		final ProductAvailableStockPerLocator service = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);

		final Map<LocatorId, Quantity> result = service.getQtyOnHandByLocator(productId, ImmutableSet.of());

		assertThat(result).isEmpty();
	}
}
