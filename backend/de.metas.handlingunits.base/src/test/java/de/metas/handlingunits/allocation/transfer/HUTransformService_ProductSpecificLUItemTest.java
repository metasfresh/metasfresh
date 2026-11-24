package de.metas.handlingunits.allocation.transfer;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
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

	@Test
	public void tuToNewLU_productWithSpecificItem_usesSpecificItemCapacity()
	{
		final I_M_HU tu = createSourceTUof10();
		final List<I_M_HU> lus = tuToNewLU(tu, data.helper.pTomatoProductId);

		assertThat(lus).hasSize(2); // 8 + 2
		assertLU(lus.get(0), luPIItemTomato8, 8);
		assertLU(lus.get(1), luPIItemTomato8, 2);
	}

	@Test
	public void tuToNewLU_productWithoutSpecificItem_usesGenericItem()
	{
		final I_M_HU tu = createSourceTUof10();
		final List<I_M_HU> lus = tuToNewLU(tu, data.helper.pSaladProductId);

		assertThat(lus).hasSize(2); // 5 + 5
		assertLU(lus.get(0), luPIItemGeneric5, 5);
		assertLU(lus.get(1), luPIItemGeneric5, 5);
	}

	@Test
	public void tuToNewLU_noProduct_usesGenericItem()
	{
		final I_M_HU tu = createSourceTUof10();
		final List<I_M_HU> lus = tuToNewLU(tu, null);

		assertThat(lus).hasSize(2); // 5 + 5
		assertLU(lus.get(0), luPIItemGeneric5, 5);
		assertLU(lus.get(1), luPIItemGeneric5, 5);
	}

	/** no product item: a partner-specific item (20) next to the generic one (30) must not change how the TU is attached */
	@Test
	public void tuToNewLU_noProductItem_partnerSpecificVsGeneric_attachesAsBefore()
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
	public void tuToNewLU_realTU_productWithSpecificItem_usesSpecificItem()
	{
		final I_M_HU realTU = handlingUnitsDAO.retrieveParent(data.mkRealCUWithTUandQtyCU("20"));
		assertThat(handlingUnitsBL.isAggregateHU(realTU)).isFalse();

		final I_M_HU lu = huTransformService
				.tuToNewLU(realTU, QtyTU.ONE, luPIId(), data.helper.pTomatoProductId)
				.getLURecords().get(0);

		assertLU(lu, luPIItemTomato8, 1);
	}

	/** A TU of the product moved onto an LU that was built from the product-specific item stays on that item */
	@Test
	public void tuToExistingLU_existingLUBuiltFromProductSpecificItem_keepsThatItem()
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
	 * Characterizes the current behaviour, it does not endorse it: a TU of another product (same TU PI) moved onto an LU that was built from
	 * the tomato-specific item joins that item; the TU's product is not checked, so the LU then holds both products on the tomato item.
	 */
	@Test
	public void tuToExistingLU_existingLUBuiltFromProductSpecificItem_tuOfOtherProduct_currentlyJoinsThatItem()
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
				.extracting(storage -> storage.getProductId(), storage -> storage.getQty().toBigDecimal().intValueExact())
				.containsExactlyInAnyOrder(tuple(data.helper.pTomatoProductId, 40), tuple(data.helper.pSaladProductId, 40));
	}

	/** An LU without any item yet (as tuToNewLUs creates it): the lookup has no product context, so the TU is attached via the generic item. */
	@Test
	public void tuToExistingLU_emptyLU_tuOfProductWithSpecificItem_attachedViaGenericItem()
	{
		final I_M_HU emptyLU = handlingUnitsDAO.createHUBuilder(data.helper.getHUContext())
				.setLocatorId(data.defaultLocatorId)
				.create(data.piLU);
		assertThat(handlingUnitsDAO.retrieveIncludedHUs(emptyLU)).as("precondition").isEmpty();

		huTransformService.tuToExistingLU(createRealTU(data.helper.pTomatoProductId), QtyTU.ONE, emptyLU);

		assertLU(emptyLU, luPIItemGeneric5, 1);
	}

	/** The LU PI's only item for IFCO TUs is the tomato-specific one; the LU holds a bag TU (generic bag item), so it has no IFCO item yet. */
	@Test
	public void tuToExistingLU_onlyProductSpecificItemForTUPI_luHasNoSuchItem_tuOfThatProduct_isRefused()
	{
		final I_M_HU lu = createLUWithBagTU_noGenericIFCOItem();

		assertTuToExistingLURefused(createRealTU(data.helper.pTomatoProductId), lu);
	}

	/** Same as above, with a TU of a product that has no item at all. */
	@Test
	public void tuToExistingLU_onlyProductSpecificItemForTUPI_luHasNoSuchItem_tuOfOtherProduct_isRefused()
	{
		final I_M_HU lu = createLUWithBagTU_noGenericIFCOItem();

		assertTuToExistingLURefused(createRealTU(data.helper.pSaladProductId), lu);
	}

	private I_M_HU createLUWithBagTU_noGenericIFCOItem()
	{
		InterfaceWrapperHelper.delete(luPIItemGeneric5);
		final I_M_HU lu = huTransformService.tuToNewLU(createRealBagTU(), QtyTU.ONE, luPIId(), null).getLURecords().get(0);
		assertLU(lu, data.piLU_Item_Bag, 1);
		return lu;
	}

	private void assertTuToExistingLURefused(final I_M_HU tu, final I_M_HU lu)
	{
		assertThatThrownBy(() -> huTransformService.tuToExistingLU(tu, QtyTU.ONE, lu))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("because there is no link between them");
		assertThat(handlingUnitsDAO.retrieveParent(tu)).as("the TU stays where it was").isNull();
		assertLU(lu, data.piLU_Item_Bag, 1);
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
