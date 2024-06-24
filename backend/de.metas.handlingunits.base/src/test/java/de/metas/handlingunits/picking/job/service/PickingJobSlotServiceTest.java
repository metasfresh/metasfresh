package de.metas.handlingunits.picking.job.service;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.PickingSlotConnectedComponent;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.picking.api.PickingSlotId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;

@ExtendWith(AdempiereTestWatcher.class)
class PickingJobSlotServiceTest
{
	private PickingJobSlotService pickingJobSlotService;

	// master data
	private final BPartnerLocationId bpartnerAndLocationId1 = BPartnerLocationId.ofRepoId(1, 2);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		pickingJobSlotService = new PickingJobSlotService(new PickingJobRepository());
		SpringContextHolder.registerJUnitBean(new PickingCandidateRepository());
		SpringContextHolder.registerJUnitBean((PickingSlotConnectedComponent)slotId -> false);
	}

	private PickingJobId createJob(@NonNull DocStatus docStatus, @Nullable final PickingSlotId pickingSlotId)
	{
		final I_M_Picking_Job record = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		record.setDocStatus(docStatus.getCode());
		record.setM_PickingSlot_ID(PickingSlotId.toRepoId(pickingSlotId));
		InterfaceWrapperHelper.save(record);
		return PickingJobId.ofRepoId(record.getM_Picking_Job_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private PickingSlotId createPickingSlot(String name, BPartnerLocationId bpartnerAndLocationId)
	{
		final I_M_PickingSlot record = InterfaceWrapperHelper.newInstance(I_M_PickingSlot.class);
		record.setPickingSlot(name);
		record.setIsDynamic(true);
		if (bpartnerAndLocationId != null)
		{
			record.setC_BPartner_ID(bpartnerAndLocationId.getBpartnerId().getRepoId());
			record.setC_BPartner_Location_ID(bpartnerAndLocationId.getRepoId());
		}
		InterfaceWrapperHelper.save(record);
		return PickingSlotId.ofRepoIdOrNull(record.getM_PickingSlot_ID());
	}

	private boolean isReleased(final PickingSlotId pickingSlotId)
	{
		final I_M_PickingSlot record = InterfaceWrapperHelper.load(pickingSlotId, I_M_PickingSlot.class);
		return record.getC_BPartner_ID() <= 0
				&& record.getC_BPartner_Location_ID() <= 0
				&& record.getM_Picking_Job_ID() <= 0;
	}

	@Nested
	class release_pickingSlot
	{
		@Test
		void usedOnlyOnOneJob()
		{
			final PickingSlotId pickingSlotId = createPickingSlot("P1", bpartnerAndLocationId1);
			Assertions.assertThat(isReleased(pickingSlotId)).isFalse();

			final PickingJobId pickingJobId1 = createJob(DocStatus.Drafted, pickingSlotId);
			createJob(DocStatus.Completed, pickingSlotId); // noise

			pickingJobSlotService.release(pickingSlotId, pickingJobId1);
			Assertions.assertThat(isReleased(pickingSlotId)).isTrue();
		}

		@Test
		void usedByMultipleJobs()
		{
			final PickingSlotId pickingSlotId = createPickingSlot("P1", bpartnerAndLocationId1);
			Assertions.assertThat(isReleased(pickingSlotId)).isFalse();

			final PickingJobId pickingJobId1 = createJob(DocStatus.Drafted, pickingSlotId);
			createJob(DocStatus.Drafted, pickingSlotId);

			pickingJobSlotService.release(pickingSlotId, pickingJobId1);
			Assertions.assertThat(isReleased(pickingSlotId)).isFalse();
		}
	}

}