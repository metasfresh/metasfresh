package de.metas.handlingunits.allocation.transfer;

import de.metas.handlingunits.HuPackingInstructionsId;
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
 * Picking TUs out of an LU into a NEW LU ({@link HUTransformService#luExtractTUs}): when the target LU's packing instructions
 * have an item that is specific for the picked product, that item's TU count splits the TUs over LUs (roll-over);
 * otherwise the generic item (5 TUs per LU) is used, as before.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class HUTransformService_LuExtractTUs_ProductSpecificLUItemTest
{
	private IHandlingUnitsDAO handlingUnitsDAO;
	private LUTUProducerDestinationTestSupport data;
	private HUTransformService huTransformService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		SpringContextHolder.registerJUnitBean(HUQRCodesService.newInstanceForUnitTesting());

		data = new HUTransformTestsBase().getData();
		huTransformService = HUTransformService.newInstance(data.helper.getHUContext());
		data.disableHUPackingMaterialsCollector("new LUs would otherwise want a packing material movement");
	}

	private I_M_HU_PI_Item createSpecificItem(final ProductId productId, final int qtyTUsPerLU)
	{
		final I_M_HU_PI_Item item = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal(qtyTUsPerLU));
		item.setM_Product_ID(productId.getRepoId());
		save(item);
		return item;
	}

	/** @return the TU count of each LU that was created, in creation order */
	private List<Integer> pick10TomatoTUsFromLuOf50IntoNewLU(final ProductId newLUProductId)
	{
		final I_M_HU sourceLU = data.createLU(50, 40);
		final LUTUResult result = huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ofInt(10))
				.targetLU(HUTransformService.TargetLU.ofNewLU(data.piLU))
				.newLUProductId(newLUProductId)
				.build());

		assertThat(result.getQtyTUs()).isEqualTo(QtyTU.ofInt(10));
		return result.getLURecords().stream()
				.map(lu -> handlingUnitsDAO.retrieveIncludedHUs(lu).stream().mapToInt(tu -> huTransformService.getMaximumQtyTU(tu).toInt()).sum())
				.collect(java.util.stream.Collectors.toList());
	}

	@Test
	public void luExtractTUs_newLU_productWithSpecificItem_rollsOverAtSpecificCount()
	{
		createSpecificItem(data.helper.pTomatoProductId, 8);

		assertThat(pick10TomatoTUsFromLuOf50IntoNewLU(data.helper.pTomatoProductId)).containsExactly(8, 2);
	}

	@Test
	public void luExtractTUs_newLU_otherProductHasSpecificItem_usesGenericCount()
	{
		createSpecificItem(data.helper.pSaladProductId, 8);

		assertThat(pick10TomatoTUsFromLuOf50IntoNewLU(data.helper.pTomatoProductId)).containsExactly(5, 5);
	}

	@Test
	public void luExtractTUs_newLU_noProduct_usesGenericCount()
	{
		createSpecificItem(data.helper.pTomatoProductId, 8);

		assertThat(pick10TomatoTUsFromLuOf50IntoNewLU(null)).containsExactly(5, 5);
	}
}
