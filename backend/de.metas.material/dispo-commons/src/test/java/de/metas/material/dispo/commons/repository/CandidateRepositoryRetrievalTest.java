package de.metas.material.dispo.commons.repository;

import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class CandidateRepositoryRetrievalTest
{
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	private DimensionService dimensionService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());

		dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
	}

	@Test
	public void retrieveCandidatesForPPOrderId()
	{
		@SuppressWarnings("unused")
		final I_MD_Candidate candidateWithoutProdDetail = createCandidateRecord(de.metas.common.util.time.SystemTime.asTimestamp());

		final I_MD_Candidate candidateWithUnrelatedProdDetail = createCandidateRecord(de.metas.common.util.time.SystemTime.asTimestamp());
		final I_MD_Candidate_Prod_Detail unrelatedProductionDetail = newInstance(I_MD_Candidate_Prod_Detail.class);
		unrelatedProductionDetail.setPP_Order_ID(10);
		unrelatedProductionDetail.setMD_Candidate(candidateWithUnrelatedProdDetail);
		save(unrelatedProductionDetail);

		final I_MD_Candidate candidateWithRelatedProdDetail = createCandidateRecord(SystemTime.asTimestamp());
		final I_MD_Candidate_Prod_Detail relatedProductionDetail = newInstance(I_MD_Candidate_Prod_Detail.class);
		relatedProductionDetail.setPP_Order_ID(23);
		relatedProductionDetail.setMD_Candidate(candidateWithRelatedProdDetail);
		save(relatedProductionDetail);

		final List<Candidate> result = candidateRepositoryRetrieval.retrieveCandidatesForPPOrderId(23);
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getId().getRepoId()).isEqualTo(relatedProductionDetail.getMD_Candidate_ID());
	}

	private I_MD_Candidate createCandidateRecord(@NonNull final Timestamp dateProjected)
	{
		final I_MD_Candidate candidateRecord = newInstance(I_MD_Candidate.class);
		candidateRecord.setDateProjected(dateProjected);
		candidateRecord.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_SUPPLY);
		candidateRecord.setM_Product_ID(20);
		candidateRecord.setM_Warehouse_ID(30);
		save(candidateRecord);

		return candidateRecord;
	}
}
