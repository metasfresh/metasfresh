package de.metas.handlingunits.picking.job.service.commands.grai;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * TDD test for {@link PickingJobGraiTargetService#assertTuAllowedOnLu}.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobGraiTargetServiceTest
{
	private HUTestHelper huTestHelper;
	private PickingJobGraiTargetService service;

	/** LU PI that contains the TU PI via an M_HU_PI_Item(ItemType=HU). */
	private I_M_HU_PI luPI;
	/** TU PI that is linked to the LU PI. */
	private I_M_HU_PI tuPI;
	/** Another TU PI that is NOT linked to the LU PI. */
	private I_M_HU_PI otherTuPI;

	@BeforeEach
	void setUp()
	{
		huTestHelper = HUTestHelper.newInstanceOutOfTrx();

		service = new PickingJobGraiTargetService();

		// Create LU PI
		luPI = huTestHelper.createHUDefinition("LU-PI", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		// Create TU PI that will be linked to the LU PI
		tuPI = huTestHelper.createHUDefinition("TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		// Create a second TU PI that is NOT linked to the LU PI
		otherTuPI = huTestHelper.createHUDefinition("OTHER-TU-PI", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		// Link tuPI to luPI via an M_HU_PI_Item(ItemType='HU', Included_HU_PI_ID=tuPI)
		huTestHelper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("10"));
	}

	@Test
	void tuPIIncludedInLuPI_doesNotThrow()
	{
		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());
		final LUPickingTarget luTarget = buildNewLuTarget(luPI);

		assertThatCode(() -> service.assertTuAllowedOnLu(tuPIId, luTarget))
				.doesNotThrowAnyException();
	}

	@Test
	void tuPINotIncludedInLuPI_throwsGRAITUNotAllowedOnLU()
	{
		final HuPackingInstructionsId otherTuPIId = HuPackingInstructionsId.ofRepoId(otherTuPI.getM_HU_PI_ID());
		final LUPickingTarget luTarget = buildNewLuTarget(luPI);

		assertThatThrownBy(() -> service.assertTuAllowedOnLu(otherTuPIId, luTarget))
				.hasMessageContaining("de.metas.handlingunits.picking.GRAITUNotAllowedOnLU");
	}

	@NonNull
	private static LUPickingTarget buildNewLuTarget(@NonNull final I_M_HU_PI pi)
	{
		return LUPickingTarget.ofPackingInstructions(
				HuPackingInstructionsId.ofRepoId(pi.getM_HU_PI_ID()),
				pi.getName()
		);
	}
}
