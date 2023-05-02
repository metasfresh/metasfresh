package de.metas.handlingunits.storage.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultHUStorageFactoryTest
{
	private HUTestHelper helper;
	private DefaultHUStorageFactory defaultHUStorageFactory;

	// Master data
	private I_M_HU_PI tuPI;
	private I_C_UOM uomKg;
	private I_C_UOM uomEach;
	private ProductId productId1;
	private ProductId productId2;

	@BeforeEach
	void beforeEach()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();
		defaultHUStorageFactory = new DefaultHUStorageFactory();

		uomKg = helper.uomKg;
		productId1 = BusinessTestHelper.createProductId("P1", uomKg);
		uomEach = helper.uomEach;
		productId2 = BusinessTestHelper.createProductId("P2", uomEach);

		tuPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuPIItem = helper.createHU_PI_Item_Material(tuPI);
		helper.assignProduct(tuPIItem, productId1, Quantity.of("100", helper.uomKg));
		helper.assignProduct(tuPIItem, productId2, Quantity.of("100", helper.uomEach));
	}

	private I_M_HU createTUWithProduct1(final String qty)
	{
		return helper.createSingleHU(tuPI, productId1, Quantity.of(qty, uomKg))
				.orElseThrow(() -> new AdempiereException("Failed creating HU"));
	}

	private void loadTUWithProduct2(final I_M_HU hu, final String qty)
	{
		HULoader.builder()
				.source(helper.createDummySourceDestination(productId2, new BigDecimal("100000000"), uomEach, true))
				.destination(HUListAllocationSourceDestination.of(hu))
				.load(AllocationUtils.builder()
						.setHUContext(helper.createMutableHUContextForProcessingOutOfTrx())
						.setProduct(productId2)
						.setQuantity(Quantity.of(qty, uomEach))
						.setDate(SystemTime.asZonedDateTime())
						.setFromReferencedModel(null)
						.setForceQtyAllocation(true)
						.create());
	}

	@Nested
	class isSingleProductWithQtyEqualsTo
	{
		@Test
		void oneProduct_lessThanNeeded()
		{
			final I_M_HU hu = createTUWithProduct1("1");
			assertThat(defaultHUStorageFactory.isSingleProductWithQtyEqualsTo(hu, productId1, Quantity.of("50", uomKg))).isFalse();
		}

		@Test
		void oneProduct_exactlyAsMuchAsNeeded()
		{
			final I_M_HU hu = createTUWithProduct1("50");
			assertThat(defaultHUStorageFactory.isSingleProductWithQtyEqualsTo(hu, productId1, Quantity.of("50", uomKg))).isTrue();
		}

		@Test
		void oneProduct_moreThanNeeded()
		{
			final I_M_HU hu = createTUWithProduct1("70");
			assertThat(defaultHUStorageFactory.isSingleProductWithQtyEqualsTo(hu, productId1, Quantity.of("50", uomKg))).isFalse();
		}

		@Test
		void twoProducts_oneExactlyAsMuchAsNeeded()
		{
			final I_M_HU hu = createTUWithProduct1("50");
			loadTUWithProduct2(hu, "50");
			assertThat(defaultHUStorageFactory.isSingleProductWithQtyEqualsTo(hu, productId1, Quantity.of("50", uomKg))).isFalse();
		}
	}
}