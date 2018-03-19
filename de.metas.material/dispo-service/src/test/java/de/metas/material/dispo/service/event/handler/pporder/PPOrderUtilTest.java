package de.metas.material.dispo.service.event.handler.pporder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PPOrderUtilTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void retrieveCandidatesForPPOrderId()
	{
		@SuppressWarnings("unused")
		final I_MD_Candidate candidateWithoutProdDetail = createCandidateRecord(SystemTime.asTimestamp());

		final I_MD_Candidate candidateWithUnrelatedProdDetail = createCandidateRecord(SystemTime.asTimestamp());
		final I_MD_Candidate_Prod_Detail unrelatedProductionDetail = newInstance(I_MD_Candidate_Prod_Detail.class);
		unrelatedProductionDetail.setPP_Order_ID(10);
		unrelatedProductionDetail.setMD_Candidate(candidateWithUnrelatedProdDetail);
		save(unrelatedProductionDetail);

		final I_MD_Candidate candidateWithRelatedProdDetail = createCandidateRecord(SystemTime.asTimestamp());
		final I_MD_Candidate_Prod_Detail relatedProductionDetail = newInstance(I_MD_Candidate_Prod_Detail.class);
		relatedProductionDetail.setPP_Order_ID(23);
		relatedProductionDetail.setMD_Candidate(candidateWithRelatedProdDetail);
		save(relatedProductionDetail);

		final List<Candidate> result = PPOrderUtil.retrieveCandidatesForPPOrderId(new CandidateRepositoryRetrieval(), 23);
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getId()).isEqualTo(relatedProductionDetail.getMD_Candidate_ID());
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
