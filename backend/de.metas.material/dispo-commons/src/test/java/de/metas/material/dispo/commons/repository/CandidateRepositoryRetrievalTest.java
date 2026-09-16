package de.metas.material.dispo.commons.repository;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		DimensionService dimensionService = new DimensionService(ImmutableList.of(new MDCandidateDimensionFactory()));
		//SpringContextHolder.registerJUnitBean(dimensionService);

		final StockChangeDetailRepo stockChangeDetailRepo = new StockChangeDetailRepo();

		candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
	}

	@SuppressWarnings("DataFlowIssue")
	@Test
	public void retrieveCandidatesForPPOrderId()
	{
		//
		// Create data
		final CandidateId candidateId;
		{
			// noise: candidate without production details
			createCandidateRecord(de.metas.common.util.time.SystemTime.asTimestamp());

			// noise: some other candidate with production detail but not the one we will query for
			{
				final I_MD_Candidate candidate = createCandidateRecord(de.metas.common.util.time.SystemTime.asTimestamp());
				final I_MD_Candidate_Prod_Detail prodDetail = newInstance(I_MD_Candidate_Prod_Detail.class);
				prodDetail.setPP_Order_ID(10);
				prodDetail.setMD_Candidate_ID(candidate.getMD_Candidate_ID());
				save(prodDetail);
			}

			// candidate with production details, the one we will query for
			{
				final I_MD_Candidate candidate = createCandidateRecord(SystemTime.asTimestamp());
				candidateId = CandidateId.ofRepoId(candidate.getMD_Candidate_ID());

				final I_MD_Candidate_Prod_Detail prodDetail = newInstance(I_MD_Candidate_Prod_Detail.class);
				prodDetail.setPP_Order_ID(23);
				prodDetail.setMD_Candidate_ID(candidate.getMD_Candidate_ID());
				save(prodDetail);
			}
		}

		final List<Candidate> result = candidateRepositoryRetrieval.retrieveCandidatesForPPOrderId(PPOrderId.ofRepoId(23));
		System.out.println("Got result: " + result);

		assertThat(result).hasSize(1);
		assertThat(result.get(0).getId()).isEqualTo(candidateId);

		final ProductionDetail productionDetail = ProductionDetail.cast(result.get(0).getBusinessCaseDetailNotNull());
		assertThat(productionDetail.getPpOrderRef().getPpOrderId().getRepoId()).isEqualTo(23);
	}

	/**
	 * Reproduces a real drift case: an {@code UNEXPECTED_DECREASE} row with NO
	 * {@code MD_Candidate_Transaction_Detail} at all - something this repository's own write path never
	 * produces (the {@link Candidate} constructor enforces the invariant at creation time), but which can
	 * exist as legacy data. {@link #retrieveOrderedByDateAndSeqNo(CandidatesQuery)} would abort on it (via
	 * {@link Candidate#validateNonStockCandidate()}); the tolerant variant must skip just that one row.
	 * <p>
	 * Calling {@link Candidate#validateNonStockCandidate()} explicitly (as the tolerant path does) fires
	 * even under {@code Adempiere.isUnitTestMode()} - unlike the constructor's own check, which that mode
	 * suppresses - so this test can actually exercise the failure the tolerant path is guarding against.
	 */
	@Test
	public void retrieveOrderedByDateAndSeqNoTolerant_skipsCandidateFailingValidation()
	{
		final int productId = 5000;
		final int warehouseId = 6000;

		final I_MD_Candidate validCandidate = newInstance(I_MD_Candidate.class);
		validCandidate.setDateProjected(SystemTime.asTimestamp());
		validCandidate.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_SUPPLY);
		validCandidate.setM_Product_ID(productId);
		validCandidate.setM_Warehouse_ID(warehouseId);
		save(validCandidate);

		final I_MD_Candidate malformedCandidate = newInstance(I_MD_Candidate.class);
		malformedCandidate.setDateProjected(SystemTime.asTimestamp());
		malformedCandidate.setMD_Candidate_Type(X_MD_Candidate.MD_CANDIDATE_TYPE_UNEXPECTED_DECREASE);
		malformedCandidate.setM_Product_ID(productId);
		malformedCandidate.setM_Warehouse_ID(warehouseId);
		malformedCandidate.setQty(BigDecimal.ONE);
		malformedCandidate.setQtyFulfilled(BigDecimal.ONE);
		save(malformedCandidate);
		// deliberately no MD_Candidate_Transaction_Detail row for malformedCandidate

		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptorQuery(MaterialDescriptorQuery.builder()
						.productId(productId)
						.warehouseId(WarehouseId.ofRepoId(warehouseId))
						.customer(BPartnerClassifier.any())
						.build())
				.build();

		final List<Candidate> result = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNoTolerant(query);

		assertThat(result).extracting(Candidate::getId)
				.containsExactly(CandidateId.ofRepoId(validCandidate.getMD_Candidate_ID()));
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
