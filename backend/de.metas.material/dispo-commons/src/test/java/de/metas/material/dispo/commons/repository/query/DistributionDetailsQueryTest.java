package de.metas.material.dispo.commons.repository.query;

import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.api.PPOrderBOMLineId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DistributionDetailsQueryTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void ppOrderBOMLineId_filtersByDistDetailBOMLine()
	{
		final I_MD_Candidate candidateOfOldLine = createCandidateWithDistDetail(111);
		final I_MD_Candidate candidateOfOtherLine = createCandidateWithDistDetail(222);
		createCandidateWithDistDetail(-1); // no BOM line at all

		final List<Integer> result = retrieveCandidateIds(DistributionDetailsQuery.builder().ppOrderBOMLineId(PPOrderBOMLineId.ofRepoId(111)).build());

		assertThat(result).containsExactly(candidateOfOldLine.getMD_Candidate_ID());
		assertThat(result).doesNotContain(candidateOfOtherLine.getMD_Candidate_ID());
	}

	private static I_MD_Candidate createCandidateWithDistDetail(final int ppOrderBOMLineId)
	{
		final I_MD_Candidate candidate = InterfaceWrapperHelper.newInstance(I_MD_Candidate.class);
		InterfaceWrapperHelper.saveRecord(candidate);

		final I_MD_Candidate_Dist_Detail detail = InterfaceWrapperHelper.newInstance(I_MD_Candidate_Dist_Detail.class);
		detail.setMD_Candidate_ID(candidate.getMD_Candidate_ID());
		detail.setPP_Order_BOMLine_ID(ppOrderBOMLineId);
		InterfaceWrapperHelper.saveRecord(detail);

		return candidate;
	}

	private static List<Integer> retrieveCandidateIds(final DistributionDetailsQuery query)
	{
		final IQueryBuilder<I_MD_Candidate> builder = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate.class);
		query.augmentQueryBuilder(builder);
		return builder.create().list().stream().map(I_MD_Candidate::getMD_Candidate_ID).collect(Collectors.toList());
	}
}
