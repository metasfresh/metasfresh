package de.metas.handlingunits.storage;

import com.google.common.collect.ImmutableList;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

		final Map<LocatorId, Quantity> result = service.getQtyOnHandByLocator(productId, ImmutableSet.of(locatorA, locatorB, locatorC)).toMap();

		assertThat(result)
				.containsOnlyKeys(locatorA, locatorB)
				.containsEntry(locatorA, Quantity.of("10", uomKg))
				.containsEntry(locatorB, Quantity.of("7", uomKg));
	}

	@Test
	void getQtyOnHandByLocator_sumsMultipleHUsInSameLocator()
	{
		createActiveHU(productId, "10", locatorA);
		createActiveHU(productId, "4", locatorA);

		final ProductAvailableStockPerLocator service = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);

		final Map<LocatorId, Quantity> result = service.getQtyOnHandByLocator(productId, ImmutableSet.of(locatorA)).toMap();

		assertThat(result)
				.containsOnlyKeys(locatorA)
				.containsEntry(locatorA, Quantity.of("14", uomKg));
	}

	@Test
	void getQtyOnHandByLocator_emptyLocatorIds_returnsEmptyMap()
	{
		createActiveHU(productId, "10", locatorA);

		final ProductAvailableStockPerLocator service = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);

		final Map<LocatorId, Quantity> result = service.getQtyOnHandByLocator(productId, ImmutableSet.of()).toMap();
		assertThat(result).isEmpty();
	}

	// ---------------------------------------------------------------------------------------------
	// streamLocatorQtyOnHandOrdered
	// ---------------------------------------------------------------------------------------------

	@Test
	void streamLocatorQtyOnHandOrdered_preservesInputOrder_andOmitsLocatorsWithoutStock()
	{
		createActiveHU(productId, "5", locatorA);
		// locatorB: no stock for this product
		createActiveHU(productId, "7", locatorC);

		final ProductAvailableStockPerLocator service = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);

		// Input order C, B, A -> output must be C then A (B omitted), regardless of any DB-natural order.
		final List<LocatorIdAndQty> result = service
				.streamLocatorQtyOnHandOrdered(productId, 10, ImmutableList.of(locatorC, locatorB, locatorA))
				.collect(ImmutableList.toImmutableList());

		assertThat(result).extracting(LocatorIdAndQty::getLocatorId)
				.containsExactly(locatorC, locatorA);
		assertThat(result).extracting(LocatorIdAndQty::getQty)
				.containsExactly(Quantity.of("7", uomKg), Quantity.of("5", uomKg));
	}

	@Test
	void streamLocatorQtyOnHandOrdered_chunkSizeSmallerThanInput_visitsAllChunksInOrder()
	{
		createActiveHU(productId, "1", locatorA);
		createActiveHU(productId, "2", locatorB);
		createActiveHU(productId, "3", locatorC);

		final ProductAvailableStockPerLocator service = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);

		// chunkSize=1 forces three separate per-locator chunk queries; each chunk must emit its locator
		// and the overall stream must preserve input order across chunk boundaries.
		final List<LocatorId> result = service
				.streamLocatorQtyOnHandOrdered(productId, 1, ImmutableList.of(locatorA, locatorB, locatorC))
				.map(LocatorIdAndQty::getLocatorId)
				.collect(ImmutableList.toImmutableList());

		assertThat(result).containsExactly(locatorA, locatorB, locatorC);
	}

	@Test
	void streamLocatorQtyOnHandOrdered_findFirst_returnsFirstHitInInputOrder()
	{
		// locatorA: no stock -> must be skipped. First hit in input order is locatorB.
		createActiveHU(productId, "5", locatorB);
		createActiveHU(productId, "7", locatorC);

		final ProductAvailableStockPerLocator service = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);

		// chunkSize=1 ensures locatorA, locatorB and locatorC each land in their own chunk: findFirst must
		// short-circuit at locatorB (chunk 2) and never need to look at locatorC (chunk 3).
		final Optional<LocatorIdAndQty> first = service
				.streamLocatorQtyOnHandOrdered(productId, 1, ImmutableList.of(locatorA, locatorB, locatorC))
				.findFirst();

		assertThat(first).isPresent();
		assertThat(first.get().getLocatorId()).isEqualTo(locatorB);
		assertThat(first.get().getQty()).isEqualTo(Quantity.of("5", uomKg));
	}

	@Test
	void streamLocatorQtyOnHandOrdered_emptyInput_returnsEmptyStream()
	{
		createActiveHU(productId, "10", locatorA);

		final ProductAvailableStockPerLocator service = ProductAvailableStockPerLocator.newInstance(handlingUnitsBL);

		final long count = service
				.streamLocatorQtyOnHandOrdered(productId, 10, ImmutableList.of())
				.count();

		assertThat(count).isZero();
	}
}
