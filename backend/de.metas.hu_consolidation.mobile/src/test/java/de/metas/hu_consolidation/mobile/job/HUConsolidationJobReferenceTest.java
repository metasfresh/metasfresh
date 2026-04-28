package de.metas.hu_consolidation.mobile.job;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.picking.api.PickingSlotId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HUConsolidationJobReferenceTest
{
	@Test
	void test_toParams_fromParams()
	{
		final HUConsolidationJobReference jobReference = HUConsolidationJobReference.builder()
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(1, 2))
				.pickingSlotId(PickingSlotId.ofRepoId(3))
				.pickingSlotId(PickingSlotId.ofRepoId(4))
				.countHUs(123)
				.startedJobId(HUConsolidationJobId.ofRepoId(678))
				.build();

		final HUConsolidationJobReference jobReference2 = HUConsolidationJobReference.ofParams(jobReference.toParams());
		assertThat(jobReference2).usingRecursiveComparison().isEqualTo(jobReference);
		assertThat(jobReference2).isEqualTo(jobReference);
	}
}