package de.metas.handlingunits.allocation.transfer;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

/**
 * TU -> new LU / existing LU when the LU packing instructions have a generic item (5 TUs per LU)
 * and an item that is specific for the tomato product (8 TUs per LU).
 */
@ExtendWith(AdempiereTestWatcher.class)
public class HUTransformService_ProductSpecificLUItemTest
{
	private IHandlingUnitsBL handlingUnitsBL;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private LUTUProducerDestinationTestSupport data;
	private HUTransformService huTransformService;
	private I_M_HU_PI_Item luPIItemGeneric5;
	private I_M_HU_PI_Item luPIItemTomato8;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		SpringContextHolder.registerJUnitBean(HUQRCodesService.newInstanceForUnitTesting());

		data = new HUTransformTestsBase().getData();
		huTransformService = HUTransformService.newInstance(data.helper.getHUContext());
		data.disableHUPackingMaterialsCollector("new LUs would otherwise want a packing material movement");

		createLUItems();
	}

	private void createLUItems()
	{
		luPIItemGeneric5 = data.piLU_Item_IFCO;

		luPIItemTomato8 = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("8"));
		luPIItemTomato8.setM_Product_ID(data.helper.pTomatoProductId.getRepoId());
		save(luPIItemTomato8);
	}

	private HuPackingInstructionsId luPIId()
	{
		return HuPackingInstructionsId.ofRepoId(data.piLU.getM_HU_PI_ID());
	}

	private I_M_HU createSourceTUof10()
	{
		final I_M_HU tu = data.mkAggregateHUWithTotalQtyCU("400"); // 10 TUs a 40kg tomatoes
		assertThat(handlingUnitsBL.isAggregateHU(tu)).isTrue();
		return tu;
	}

	private List<I_M_HU> tuToNewLU(final I_M_HU tu, final ProductId productId)
	{
		return huTransformService
				.tuToNewLU(tu, QtyTU.ofString("10"), luPIId(), productId)
				.getLURecords();
	}

	@Nested
	class TuToNewLU
	{
		@Test
		public void productWithSpecificItem_usesSpecificItemCapacity()
		{
			final I_M_HU tu = createSourceTUof10();
			final List<I_M_HU> lus = tuToNewLU(tu, data.helper.pTomatoProductId);

			assertThat(lus).hasSize(2); // 8 + 2
			assertLU(lus.get(0), luPIItemTomato8, 8);
			assertLU(lus.get(1), luPIItemTomato8, 2);
		}

		@Test
		public void productWithoutSpecificItem_usesGenericItem()
		{
			final I_M_HU tu = createSourceTUof10();
			final List<I_M_HU> lus = tuToNewLU(tu, data.helper.pSaladProductId);

			assertThat(lus).hasSize(2); // 5 + 5
			assertLU(lus.get(0), luPIItemGeneric5, 5);
			assertLU(lus.get(1), luPIItemGeneric5, 5);
		}

		@Test
		public void noProduct_usesGenericItem()
		{
			final I_M_HU tu = createSourceTUof10();
			final List<I_M_HU> lus = tuToNewLU(tu, null);

			assertThat(lus).hasSize(2); // 5 + 5
			assertLU(lus.get(0), luPIItemGeneric5, 5);
			assertLU(lus.get(1), luPIItemGeneric5, 5);
		}

		/** no product item: a partner-specific item (20) next to the generic one (30) must not change how the TU is attached */
		@Test
		public void noProductItem_partnerSpecificVsGeneric_attachesAsBefore()
		{
			InterfaceWrapperHelper.delete(luPIItemTomato8);
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(BusinessTestHelper.createBPartner("BP").getC_BPartner_ID());
			final I_M_HU_PI_Item partnerItem20 = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("20"), bpartnerId);
			final I_M_HU_PI_Item generic30 = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("30"));

			final I_M_HU realTU = handlingUnitsDAO.retrieveParent(data.mkRealCUWithTUandQtyCU("20"));
			realTU.setC_BPartner_ID(bpartnerId.getRepoId());
			save(realTU);

			final I_M_HU_PI_Item chosen = handlingUnitsDAO.retrieveFirstPIItem(luPIId(), HuPackingInstructionsId.ofRepoId(data.piTU_IFCO.getM_HU_PI_ID()), bpartnerId, null).get();
			final I_M_HU lu = huTransformService.tuToNewLU(realTU, QtyTU.ONE, luPIId(), null).getLURecords().get(0);

			// as before: attached via the generic-first lookup result, not via the item that tuToNewLU chose by itself
			final I_M_HU_PI_Item expected = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(lu, data.piTU_IFCO, data.helper.getHUContext());
			assertThat(handlingUnitsDAO.retrieveParentItem(realTU).getM_HU_PI_Item_ID()).isEqualTo(expected.getM_HU_PI_Item_ID());
			assertThat(chosen.getM_HU_PI_Item_ID()).as("guard: the two lookups differ in this setup").isNotEqualTo(expected.getM_HU_PI_Item_ID());
		}

		@Test
		public void realTU_productWithSpecificItem_usesSpecificItem()
		{
			final I_M_HU realTU = handlingUnitsDAO.retrieveParent(data.mkRealCUWithTUandQtyCU("20"));
			assertThat(handlingUnitsBL.isAggregateHU(realTU)).isFalse();

			final I_M_HU lu = huTransformService
					.tuToNewLU(realTU, QtyTU.ONE, luPIId(), data.helper.pTomatoProductId)
					.getLURecords().get(0);

			assertLU(lu, luPIItemTomato8, 1);
		}
	}

	@Nested
	class TuToExistingLU
	{
		/** A TU of the product moved onto an LU that was built from the product-specific item stays on that item */
		@Test
		public void existingLUBuiltFromProductSpecificItem_keepsThatItem()
		{
			final I_M_HU sourceForExistingLU = data.mkAggregateHUWithTotalQtyCU("40"); // 1 TU
			final I_M_HU tuToAdd = data.mkAggregateHUWithTotalQtyCU("80"); // 2 TUs
			final I_M_HU existingLU = huTransformService
					.tuToNewLU(sourceForExistingLU, QtyTU.ONE, luPIId(), data.helper.pTomatoProductId)
					.getLURecords().get(0);
			assertLU(existingLU, luPIItemTomato8, 1);

			huTransformService.tuToExistingLU(tuToAdd, QtyTU.ofString("2"), existingLU);

			assertLU(existingLU, luPIItemTomato8, 3);
		}

		/**
		 * A TU of another product (same TU PI) moved onto an LU that was built from the tomato-specific item joins that item; the TU's product is not checked,
		 * so the LU then holds both products on the tomato item. This is wanted: different products on the same LU are allowed.
		 * <p>
		 * If that behaviour is changed, update this test to the new behaviour; do not delete it.
		 */
		@Test
		public void existingLUBuiltFromProductSpecificItem_tuOfOtherProduct_joinsThatItem()
		{
			final I_M_HU tomatoTU = createRealTU(data.helper.pTomatoProductId);
			final I_M_HU existingLU = huTransformService
					.tuToNewLU(tomatoTU, QtyTU.ONE, luPIId(), data.helper.pTomatoProductId)
					.getLURecords().get(0);
			assertLU(existingLU, luPIItemTomato8, 1);

			final I_M_HU saladTU = createRealTU(data.helper.pSaladProductId);
			huTransformService.tuToExistingLU(saladTU, QtyTU.ONE, existingLU);

			assertLU(existingLU, luPIItemTomato8, 2);
			assertThat(handlingUnitsDAO.retrieveParentItem(saladTU).getM_HU_Item_ID())
					.as("the salad TU hangs on the same LU item as the tomato TU")
					.isEqualTo(handlingUnitsDAO.retrieveParentItem(tomatoTU).getM_HU_Item_ID());
			assertThat(handlingUnitsBL.getStorageFactory().getStorage(existingLU).getProductStorages())
					.extracting(IHUProductStorage::getProductId, storage -> storage.getQty().toBigDecimal().intValueExact())
					.containsExactlyInAnyOrder(tuple(data.helper.pTomatoProductId, 40), tuple(data.helper.pSaladProductId, 40));
		}

		/** An LU without any TU item yet (as tuToNewLUs creates it): the lookup has no product context, so the TU is attached via the generic item. */
		@Test
		public void emptyLU_tuOfProductWithSpecificItem_attachedViaGenericItem()
		{
			final I_M_HU emptyLU = handlingUnitsDAO.createHUBuilder(data.helper.getHUContext())
					.setLocatorId(data.defaultLocatorId)
					.create(data.piLU);
			assertThat(handlingUnitsDAO.retrieveItems(emptyLU, HUItemType.HandlingUnit)).as("precondition: no TU item").isEmpty();

			huTransformService.tuToExistingLU(createRealTU(data.helper.pTomatoProductId), QtyTU.ONE, emptyLU);

			assertLU(emptyLU, luPIItemGeneric5, 1);
		}

		/**
		 * The LU PI's only item for IFCO TUs is the tomato-specific one; the LU holds a bag TU (generic bag item), so it has no IFCO item yet.
		 * A tomato IFCO TU is attached via the tomato-specific item.
		 */
		@Test
		public void onlyProductSpecificItemForTUPI_luHasNoSuchItem_tuOfThatProduct_isAttachedViaThatItem()
		{
			final I_M_HU lu = createLUWithBagTU_noGenericIFCOItem();
			final I_M_HU tomatoTU = createRealTU(data.helper.pTomatoProductId);

			huTransformService.tuToExistingLU(tomatoTU, QtyTU.ONE, lu);

			assertAttachedVia(tomatoTU, lu, luPIItemTomato8);
			assertLUStorage(lu, tuple(data.helper.pTomatoProductId, 8 + 40));
		}

		/** Same as above, with a TU of a product that has no item at all: different products on the same LU are allowed, so it is attached via the tomato-specific item. */
		@Test
		public void onlyProductSpecificItemForTUPI_luHasNoSuchItem_tuOfOtherProduct_isAttachedViaTheProductSpecificItem()
		{
			final I_M_HU lu = createLUWithBagTU_noGenericIFCOItem();
			final I_M_HU saladTU = createRealTU(data.helper.pSaladProductId);

			huTransformService.tuToExistingLU(saladTU, QtyTU.ONE, lu);

			assertAttachedVia(saladTU, lu, luPIItemTomato8);
			assertLUStorage(lu, tuple(data.helper.pTomatoProductId, 8), tuple(data.helper.pSaladProductId, 40));
		}

		/** Same as above, but only one TU of an aggregate of two is moved (this goes through the LU/TU producer instead of attaching the TU directly). */
		@Test
		public void onlyProductSpecificItemForTUPI_luHasNoSuchItem_oneTUOfAggregate_isAttachedViaThatItem()
		{
			final I_M_HU aggregateTU = createAggregateTUof2_thenDeactivateGenericItem();
			final I_M_HU lu = createLUWithBagTU();

			huTransformService.tuToExistingLU(aggregateTU, QtyTU.ONE, lu);

			assertLUAttachedVia(lu, data.piLU_Item_Bag, luPIItemTomato8);
			assertLUStorage(lu, tuple(data.helper.pTomatoProductId, 8 + 40));
			assertSourceAggregateHasOneTUOf40Left(aggregateTU);
		}

		/**
		 * Partial move from an aggregate onto an LU of partner BP whose LU PI links the IFCO TU via a BP-specific item without product and via the tomato item (no generic one).
		 * The producer's own lookup (without partner and product) finds nothing, so only the product-specific fallback is added: the tomato item is used, the partner item is not.
		 */
		@Test
		public void partialMoveFromAggregate_partnerItemWithoutProductAndProductSpecificItem_usesProductSpecificItem()
		{
			final I_M_HU aggregateTU = createAggregateTUof2_thenDeactivateGenericItem();
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(BusinessTestHelper.createBPartner("BP").getC_BPartner_ID());
			data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("20"), bpartnerId);
			final I_M_HU lu = createLUWithBagTU();
			lu.setC_BPartner_ID(bpartnerId.getRepoId());
			save(lu);

			huTransformService.tuToExistingLU(aggregateTU, QtyTU.ONE, lu);

			assertLUAttachedVia(lu, data.piLU_Item_Bag, luPIItemTomato8);
			assertLUStorage(lu, tuple(data.helper.pTomatoProductId, 8 + 40));
			assertSourceAggregateHasOneTUOf40Left(aggregateTU);
		}

		/** Same, but without any product-specific item: the partial move fails as it did before (only product-specific items are added as fallback). */
		@Test
		public void partialMoveFromAggregate_onlyPartnerItemWithoutProduct_failsAsBefore()
		{
			final I_M_HU aggregateTU = createAggregateTUof2_thenDeactivateGenericItem();
			InterfaceWrapperHelper.delete(luPIItemTomato8);
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(BusinessTestHelper.createBPartner("BP").getC_BPartner_ID());
			data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("20"), bpartnerId);
			final I_M_HU lu = createLUWithBagTU();
			lu.setC_BPartner_ID(bpartnerId.getRepoId());
			save(lu);

			assertThatThrownBy(() -> huTransformService.tuToExistingLU(aggregateTU, QtyTU.ONE, lu))
					.hasMessageContaining("ERR_LU_HAS_NO_TU_SUB_PACK_INSTR");
		}

		/** Two product-specific IFCO items (tomato, then salad) and no generic one: a salad TU picks the salad item, not the lower-ID tomato item. */
		@Test
		public void onlyProductSpecificItemsForTUPI_tuOfSecondProduct_picksItsOwnItem()
		{
			final I_M_HU_PI_Item luPIItemSalad6 = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("6"));
			luPIItemSalad6.setM_Product_ID(data.helper.pSaladProductId.getRepoId());
			save(luPIItemSalad6);
			assertThat(luPIItemSalad6.getM_HU_PI_Item_ID()).as("guard: the salad item is not the lowest ID").isGreaterThan(luPIItemTomato8.getM_HU_PI_Item_ID());

			final I_M_HU lu = createLUWithBagTU_noGenericIFCOItem();
			final I_M_HU saladTU = createRealTU(data.helper.pSaladProductId);

			huTransformService.tuToExistingLU(saladTU, QtyTU.ONE, lu);

			assertAttachedVia(saladTU, lu, luPIItemSalad6);
			assertLUStorage(lu, tuple(data.helper.pTomatoProductId, 8), tuple(data.helper.pSaladProductId, 40));
		}
	}

	private I_M_HU createLUWithBagTU_noGenericIFCOItem()
	{
		InterfaceWrapperHelper.delete(luPIItemGeneric5);
		return createLUWithBagTU();
	}

	private I_M_HU createLUWithBagTU()
	{
		final I_M_HU lu = huTransformService.tuToNewLU(createRealBagTU(), QtyTU.ONE, luPIId(), null).getLURecords().get(0);
		assertLU(lu, data.piLU_Item_Bag, 1);
		return lu;
	}

	/** @return an aggregate TU of 2 IFCO TUs a 40kg tomatoes, on an LU via the generic item; the generic item is then deactivated (not deleted, because that LU item still references it) */
	private I_M_HU createAggregateTUof2_thenDeactivateGenericItem()
	{
		final I_M_HU aggregateTU = data.mkAggregateHUWithTotalQtyCU("80");
		luPIItemGeneric5.setIsActive(false);
		save(luPIItemGeneric5);
		return aggregateTU;
	}

	private void assertLUAttachedVia(final I_M_HU lu, final I_M_HU_PI_Item... expectedItems)
	{
		assertThat(handlingUnitsDAO.retrieveIncludedHUs(lu))
				.as("PI items via which the LU's TUs are attached")
				.extracting(tu -> handlingUnitsDAO.retrieveParentItem(tu).getM_HU_PI_Item_ID())
				.containsExactlyInAnyOrder(Arrays.stream(expectedItems).map(I_M_HU_PI_Item::getM_HU_PI_Item_ID).toArray(Integer[]::new));
	}

	private void assertSourceAggregateHasOneTUOf40Left(final I_M_HU aggregateTU)
	{
		assertThat(huTransformService.getMaximumQtyTU(aggregateTU).toInt()).as("TUs left in the source aggregate").isEqualTo(1);
		assertThat(handlingUnitsBL.getStorageFactory().getStorage(aggregateTU).getProductStorages())
				.extracting(IHUProductStorage::getProductId, storage -> storage.getQty().toBigDecimal().intValueExact())
				.containsExactly(tuple(data.helper.pTomatoProductId, 40));
	}

	private void assertAttachedVia(final I_M_HU tu, final I_M_HU lu, final I_M_HU_PI_Item expectedItem)
	{
		assertThat(handlingUnitsDAO.retrieveParent(tu).getM_HU_ID()).as("the TU's parent is the LU").isEqualTo(lu.getM_HU_ID());
		assertThat(handlingUnitsDAO.retrieveParentItem(tu).getM_HU_PI_Item_ID()).as("PI item via which the TU is attached").isEqualTo(expectedItem.getM_HU_PI_Item_ID());
	}

	private void assertLUStorage(final I_M_HU lu, final Tuple... expectedProductQtys)
	{
		assertThat(handlingUnitsBL.getStorageFactory().getStorage(lu).getProductStorages())
				.extracting(IHUProductStorage::getProductId, storage -> storage.getQty().toBigDecimal().intValueExact())
				.containsExactlyInAnyOrder(expectedProductQtys);
	}

	/** @return a standalone real IFCO TU holding 40 of the given product */
	private I_M_HU createRealTU(final ProductId productId)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(data.defaultLocatorId);
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.addCUPerTU(productId, BigDecimal.valueOf(40), data.helper.uomKg);
		data.helper.load(lutuProducer, productId, BigDecimal.valueOf(40), data.helper.uomKg);
		return CollectionUtils.singleElement(lutuProducer.getCreatedHUs());
	}

	/** @return a standalone real bag TU holding 8 tomatoes */
	private I_M_HU createRealBagTU()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(data.defaultLocatorId);
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_Bag);
		lutuProducer.addCUPerTU(data.helper.pTomatoProductId, new BigDecimal("8"), data.helper.uomKg);
		data.helper.load(lutuProducer, data.helper.pTomatoProductId, new BigDecimal("8"), data.helper.uomKg);
		return CollectionUtils.singleElement(lutuProducer.getCreatedHUs());
	}

	/** asserts that the LU holds exactly {@code expectedQtyTUs} TUs, all of them attached via {@code expectedItem} */
	private void assertLU(final I_M_HU lu, final I_M_HU_PI_Item expectedItem, final int expectedQtyTUs)
	{
		final List<I_M_HU> includedTUs = handlingUnitsDAO.retrieveIncludedHUs(lu);
		assertThat(includedTUs).isNotEmpty();
		assertThat(includedTUs)
				.as("PI items via which the TUs are attached")
				.allSatisfy(tu -> assertThat(handlingUnitsDAO.retrieveParentItem(tu).getM_HU_PI_Item_ID()).isEqualTo(expectedItem.getM_HU_PI_Item_ID()));
		final int qtyTUs = includedTUs.stream().mapToInt(tu -> huTransformService.getMaximumQtyTU(tu).toInt()).sum();
		assertThat(qtyTUs).as("qty TUs").isEqualTo(expectedQtyTUs);
	}
}
