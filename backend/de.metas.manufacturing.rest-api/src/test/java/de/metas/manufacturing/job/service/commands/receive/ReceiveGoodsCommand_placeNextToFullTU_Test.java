package de.metas.manufacturing.job.service.commands.receive;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.manufacturing.job.model.ReceivingTarget;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TU mode with the mandatory catch-weight scan: when the TU picked by its QR code is full, a new TU is started.
 * It belongs where the full TU is: on the full TU's LU, if it sits on one.
 */
class ReceiveGoodsCommand_placeNextToFullTU_Test
{
	private static final HUPIItemProductId TU_PACKING = HUPIItemProductId.ofRepoId(12345);

	private LUTUProducerDestinationTestSupport testSupport;
	private IHandlingUnitsBL handlingUnitsBL;

	@BeforeEach
	void beforeEach()
	{
		testSupport = new LUTUProducerDestinationTestSupport();
		SpringContextHolder.registerJUnitBean(new DistributionNetworkRepository()); // moving a TU onto an LU collects packing materials
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	}

	@Test
	void fullTUOnAnLU_newTUGoesOntoThatLU()
	{
		final I_M_HU fullTU = newRealTU("2");
		final I_M_HU lu = HUTransformService.newInstance()
				.tuToNewLUs(fullTU, QtyTU.ONE, testSupport.piLU_Item_IFCO, false)
				.getSingleLURecord();
		final I_M_HU newTU = newRealTU("1");

		final ReceivingTarget target = ReceiveGoodsCommand.placeNextToFullTU(handlingUnitsBL, newTU, fullTU, TU_PACKING);

		final I_M_HU luOfNewTU = handlingUnitsBL.getLoadingUnitHU(newTU);
		assertThat(luOfNewTU).as("LU of the new TU").isNotNull();
		assertThat(luOfNewTU.getM_HU_ID()).isEqualTo(lu.getM_HU_ID());
		assertThat(target.getLuId()).isNotNull();
		assertThat(target.getLuId().getRepoId()).isEqualTo(lu.getM_HU_ID());
		assertThat(target.getTuPIItemProductId()).isEqualTo(TU_PACKING);
	}

	@Test
	void standaloneFullTU_newTUStaysStandaloneAndBecomesCurrent()
	{
		final I_M_HU fullTU = newRealTU("2");
		final I_M_HU newTU = newRealTU("1");

		final ReceivingTarget target = ReceiveGoodsCommand.placeNextToFullTU(handlingUnitsBL, newTU, fullTU, TU_PACKING);

		assertThat(handlingUnitsBL.getLoadingUnitHU(newTU)).isNull();
		assertThat(target.getLuId()).isNull();
		assertThat(target.getTuId()).isNotNull();
		assertThat(target.getTuId().getRepoId()).isEqualTo(newTU.getM_HU_ID());
	}

	private I_M_HU newRealTU(final String qtyCU)
	{
		final I_M_HU cu = testSupport.mkRealCUWithTUandQtyCU(qtyCU);
		return handlingUnitsBL.getTransportUnitHU(cu);
	}
}
