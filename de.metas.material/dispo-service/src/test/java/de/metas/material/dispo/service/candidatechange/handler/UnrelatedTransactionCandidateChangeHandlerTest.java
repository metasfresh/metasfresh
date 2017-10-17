package de.metas.material.dispo.service.candidatechange.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.DispoTestUtils;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.MaterialDescriptor;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class UnrelatedTransactionCandidateChangeHandlerTest
{

	private UnrelatedTransactionCandidateHandler unrelatedTransactionCandidateChangeHandler;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		unrelatedTransactionCandidateChangeHandler = new UnrelatedTransactionCandidateHandler();
	}

	@Test
	public void increase_stock()
	{
		final Candidate candidate = Candidate.builder()
				.type(Type.UNRELATED_TRANSACTION)
				.materialDescr(MaterialDescriptor.builder()
						.productId(20)
						.date(SystemTime.asTimestamp())
						.warehouseId(30)
						.quantity(BigDecimal.TEN)
						.build())
				.build();

		unrelatedTransactionCandidateChangeHandler.onCandidateNewOrChange(candidate);

		final List<I_MD_Candidate> allRecords = DispoTestUtils.retrieveAllRecords();
		assertThat(allRecords).hasSize(2);

		final I_MD_Candidate unrelatedTransactionCandidate = allRecords.get(0);
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_UNRELATED_TRANSACTION);
		assertThat(unrelatedTransactionCandidate.getQty()).isEqualByComparingTo("10");

		final I_MD_Candidate stockCandidate = allRecords.get(1);
		assertThat(stockCandidate.getMD_Candidate_Type()).isEqualTo(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK);
		assertThat(stockCandidate.getQty()).isEqualByComparingTo("10");
		assertThat(unrelatedTransactionCandidate.getMD_Candidate_Parent_ID()).isEqualTo(stockCandidate.getMD_Candidate_ID());

	}

}
