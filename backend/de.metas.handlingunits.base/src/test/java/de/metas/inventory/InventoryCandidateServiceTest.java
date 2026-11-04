package de.metas.inventory;

import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.AdRefListRepositoryMocked;
import de.metas.ad_reference.AdRefTableRepositoryMocked;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Inventory_Candidate;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class InventoryCandidateServiceTest
{
	private InventoryCandidateService service;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		service = new InventoryCandidateService(
				new ADReferenceService(new AdRefListRepositoryMocked(), new AdRefTableRepositoryMocked()),
				new InventoryCandidateRepository());
	}

	@Test
	public void getInventoryCandidatesByHUId_returnsTheActiveCandidatesOfThatHU_processedOrNot()
	{
		final HuId huId = HuId.ofRepoId(101);
		final HuId otherHuId = HuId.ofRepoId(102);

		final I_M_Inventory_Candidate active = createCandidate(huId, true);
		createCandidate(huId, false);
		createCandidate(otherHuId, true);
		final I_M_Inventory_Candidate processed = createCandidate(huId, true);
		processed.setProcessed(true);
		saveRecord(processed);

		final List<I_M_Inventory_Candidate> result = service.getInventoryCandidatesByHUId(huId);

		assertThat(result)
				.extracting(I_M_Inventory_Candidate::getM_Inventory_Candidate_ID)
				.containsExactlyInAnyOrder(active.getM_Inventory_Candidate_ID(), processed.getM_Inventory_Candidate_ID());
	}

	@Test
	public void isDisposalPending_trueOnlyForAnActiveUnprocessedCandidateWithADisposeReason()
	{
		final HuId pendingHuId = HuId.ofRepoId(201);
		createDisposalCandidate(pendingHuId, true, false, "X");

		final HuId processedHuId = HuId.ofRepoId(202);
		createDisposalCandidate(processedHuId, true, true, "X");

		final HuId noReasonHuId = HuId.ofRepoId(203);
		createDisposalCandidate(noReasonHuId, true, false, null);

		final HuId inactiveHuId = HuId.ofRepoId(204);
		createDisposalCandidate(inactiveHuId, false, false, "X");

		assertThat(service.isDisposalPending(pendingHuId)).isTrue();
		assertThat(service.isDisposalPending(processedHuId)).isFalse();
		assertThat(service.isDisposalPending(noReasonHuId)).isFalse();
		assertThat(service.isDisposalPending(inactiveHuId)).isFalse();
		assertThat(service.isDisposalPending(HuId.ofRepoId(299))).isFalse();
	}

	private static void createDisposalCandidate(final HuId huId, final boolean active, final boolean processed, final String disposeReason)
	{
		final I_M_Inventory_Candidate record = newInstance(I_M_Inventory_Candidate.class);
		record.setM_HU_ID(huId.getRepoId());
		record.setIsActive(active);
		record.setProcessed(processed);
		record.setDisposeReason(disposeReason);
		saveRecord(record);
	}

	private static I_M_Inventory_Candidate createCandidate(final HuId huId, final boolean active)
	{
		final I_M_Inventory_Candidate record = newInstance(I_M_Inventory_Candidate.class);
		record.setM_HU_ID(huId.getRepoId());
		record.setIsActive(active);
		saveRecord(record);
		return record;
	}
}
