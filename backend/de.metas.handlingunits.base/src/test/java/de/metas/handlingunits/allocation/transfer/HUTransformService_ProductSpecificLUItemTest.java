package de.metas.handlingunits.allocation.transfer;

import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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
