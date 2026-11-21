package de.metas.handlingunits.picking.job.service.external.hu;

import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.i18n.TranslatableStrings;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/** {@link PickingJobHUService#getPackingTuPIId}: the TU packing instructions a line's packing names (null = no TU PI, e.g. virtual packing). */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobHUServicePackingTuPITest
{
	private PickingJobHUService huService;
	private HuPackingInstructionsId tuPIId;
	private HuPackingInstructionsItemId tuPIItemId;

	@BeforeEach
	void beforeEach()
	{
		final HUTestHelper huTestHelper = HUTestHelper.newInstanceOutOfTrx();
		huService = PickingJobHUService.newInstanceForUnitTesting();

		final I_M_HU_PI tuPI = huTestHelper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuPIItem = huTestHelper.createHU_PI_Item_Material(tuPI);
		tuPIId = HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());
		tuPIItemId = HuPackingInstructionsItemId.ofRepoId(tuPIItem.getM_HU_PI_Item_ID());
	}

	private HUPIItemProduct packing(final int huPiItemProductRepoId)
	{
		return HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(huPiItemProductRepoId))
				.name(TranslatableStrings.constant("packing"))
				.piItemId(tuPIItemId)
				.build();
	}

	@Test
	void tuPacking_hasItsTuPI()
	{
		assertThat(huService.getPackingTuPIId(packing(540010))).isEqualTo(tuPIId);
	}

	@Test
	void virtualPacking_hasNoTuPI()
	{
		assertThat(huService.getPackingTuPIId(packing(HUPIItemProductId.VIRTUAL_HU.getRepoId()))).isNull();
	}
}
