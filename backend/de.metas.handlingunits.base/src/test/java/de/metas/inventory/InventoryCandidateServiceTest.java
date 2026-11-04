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
	public void getInventoryCandidatesByHUId_returnsOnlyActiveCandidatesOfThatHU()
	{
		final HuId huId = HuId.ofRepoId(101);
		final HuId otherHuId = HuId.ofRepoId(102);

		final I_M_Inventory_Candidate active = createCandidate(huId, true);
		createCandidate(huId, false);
		createCandidate(otherHuId, true);

		final List<I_M_Inventory_Candidate> result = service.getInventoryCandidatesByHUId(huId);

		assertThat(result)
				.extracting(I_M_Inventory_Candidate::getM_Inventory_Candidate_ID)
				.containsExactly(active.getM_Inventory_Candidate_ID());
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
