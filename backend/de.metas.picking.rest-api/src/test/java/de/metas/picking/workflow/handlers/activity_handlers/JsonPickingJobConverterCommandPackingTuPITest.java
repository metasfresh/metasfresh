package de.metas.picking.workflow.handlers.activity_handlers;

import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.i18n.TranslatableStrings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/** {@code JsonPickingJobConverterCommand.toPackingTuPIId}: the TU packing instructions the line's packing names (null = no TU PI, e.g. virtual packing). */
class JsonPickingJobConverterCommandPackingTuPITest
{
	private static final HuPackingInstructionsItemId PI_ITEM_ID = HuPackingInstructionsItemId.ofRepoId(540001);
	private static final HuPackingInstructionsId TU_PI_ID = HuPackingInstructionsId.ofRepoId(540002);

	private IHandlingUnitsBL handlingUnitsBL;

	@BeforeEach
	void beforeEach()
	{
		handlingUnitsBL = mock(IHandlingUnitsBL.class);
		when(handlingUnitsBL.getPackingInstructionsId(any(HuPackingInstructionsItemId.class))).thenReturn(TU_PI_ID);
	}

	private static HUPIItemProduct packing(final int huPiItemProductRepoId)
	{
		return HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(huPiItemProductRepoId))
				.name(TranslatableStrings.constant("packing"))
				.piItemId(PI_ITEM_ID)
				.build();
	}

	@Test
	void tuPacking_hasItsTuPI()
	{
		assertThat(JsonPickingJobConverterCommand.toPackingTuPIId(packing(540010), handlingUnitsBL)).isEqualTo(TU_PI_ID);
		verify(handlingUnitsBL).getPackingInstructionsId(PI_ITEM_ID);
	}

	@Test
	void virtualPacking_hasNoTuPI()
	{
		assertThat(JsonPickingJobConverterCommand.toPackingTuPIId(packing(HUPIItemProductId.VIRTUAL_HU.getRepoId()), handlingUnitsBL)).isNull();
		verifyNoInteractions(handlingUnitsBL);
	}
}
