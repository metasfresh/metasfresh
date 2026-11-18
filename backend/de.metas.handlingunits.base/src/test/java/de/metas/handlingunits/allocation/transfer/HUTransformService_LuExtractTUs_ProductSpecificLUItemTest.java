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

	/**
	 * @return an LU with one aggregate TU (representing {@code qtyAggregatedTUs} TUs) plus {@code qtyRealTUs} real (non-aggregated) TUs
	 */
	private I_M_HU createLUWithAggregateAndRealTUs(final int qtyAggregatedTUs, final int qtyRealTUs)
	{
		final I_M_HU lu = data.createLU(qtyAggregatedTUs, 40);
		for (int i = 0; i < qtyRealTUs; i++)
		{
			final I_M_HU realTU = handlingUnitsDAO.retrieveParent(data.mkRealCUWithTUandQtyCU("40"));
			huTransformService.tuToExistingLU(realTU, QtyTU.ONE, lu);
		}
		assertThat(countTUs(lu)).isEqualTo(qtyAggregatedTUs + qtyRealTUs);
		return lu;
	}

	private int countTUs(final I_M_HU lu)
	{
		return handlingUnitsDAO.retrieveIncludedHUs(lu).stream().mapToInt(tu -> huTransformService.getMaximumQtyTU(tu).toInt()).sum();
	}

	private LUTUResult extractIntoNewLUs(final I_M_HU sourceLU, final int qtyTUs, final ProductId newLUProductId)
	{
		final LUTUResult result = huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ofInt(qtyTUs))
				.targetLU(HUTransformService.TargetLU.ofNewLU(data.piLU))
				.newLUProductId(newLUProductId)
				.build());

		assertThat(result.getQtyTUs()).isEqualTo(QtyTU.ofInt(qtyTUs));
		return result;
	}

	/** @return the TU count of each LU of the result, ordered by LU ID (i.e. creation order) */
	private List<Integer> countTUsPerLU(final LUTUResult result)
	{
		return result.getLURecords().stream()
				.sorted(java.util.Comparator.comparing(I_M_HU::getM_HU_ID))
				.map(this::countTUs)
				.collect(java.util.stream.Collectors.toList());
	}

	/** Several TU lines (aggregate + real TUs) in the source LU, not all of them picked (else the source LU would be taken as is): the capacity of the product-specific item applies across all lines. */
	@Test
	public void luExtractTUs_newLU_aggregateAndRealTUs_productWithSpecificItem_rollsOverAcrossLines()
	{
		final I_M_HU_PI_Item specificItem = createSpecificItem(data.helper.pTomatoProductId, 8);
		final I_M_HU sourceLU = createLUWithAggregateAndRealTUs(6, 5);

		final LUTUResult result = extractIntoNewLUs(sourceLU, 10, data.helper.pTomatoProductId);

		assertThat(countTUsPerLU(result)).containsExactly(8, 2);
		// all TUs, also the ones added to an LU that was created for a previous TU line, hang on the product-specific item
		assertThat(result.getLURecords())
				.flatExtracting(handlingUnitsDAO::retrieveIncludedHUs)
				.extracting(tu -> tu.getM_HU_Item_Parent().getM_HU_PI_Item_ID())
				.containsOnly(specificItem.getM_HU_PI_Item_ID());
	}

	/** Same with the generic item (5 TUs per LU): the LUs are not over-filled either. */
	@Test
	public void luExtractTUs_newLU_aggregateAndRealTUs_genericItem_rollsOverAcrossLines()
	{
		final I_M_HU sourceLU = createLUWithAggregateAndRealTUs(3, 5);

		assertThat(countTUsPerLU(extractIntoNewLUs(sourceLU, 7, data.helper.pTomatoProductId))).containsExactly(5, 2);
	}

	/** A caller-supplied existing LU may exceed its capacity by design (see {@link HUTransformService#tuToExistingLU}). */
	@Test
	public void luExtractTUs_existingLU_aggregateAndRealTUs_noRollOver()
	{
		final I_M_HU sourceLU = createLUWithAggregateAndRealTUs(3, 5);
		final I_M_HU existingLU = data.createLU(4, 40);

		final LUTUResult result = huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ofInt(7))
				.targetLU(HUTransformService.TargetLU.ofExistingLU(existingLU))
				.build());

		assertThat(result.getQtyTUs()).isEqualTo(QtyTU.ofInt(7));
		assertThat(result.getLURecords()).extracting(I_M_HU::getM_HU_ID).containsExactly(existingLU.getM_HU_ID());
		assertThat(countTUs(existingLU)).isEqualTo(11);
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
