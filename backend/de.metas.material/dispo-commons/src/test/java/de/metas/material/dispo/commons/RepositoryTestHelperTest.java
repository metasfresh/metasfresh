package de.metas.material.dispo.commons;

import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.MDCandidateDimensionFactory;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.repohelpers.StockChangeDetailRepo;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

public class RepositoryTestHelperTest
{
	private RepositoryTestHelper repositoryTestHelper;
	private DimensionService dimensionService;
	private StockChangeDetailRepo stockChangeDetailRepo;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new MDCandidateDimensionFactory());
		dimensionService = new DimensionService(dimensionFactories);
		stockChangeDetailRepo = new StockChangeDetailRepo();
		SpringContextHolder.registerJUnitBean(dimensionService);
		final CandidateRepositoryWriteService candidateRepositoryWriteService = new CandidateRepositoryWriteService(dimensionService, stockChangeDetailRepo);

		repositoryTestHelper = new RepositoryTestHelper(candidateRepositoryWriteService);
	}

	@Test
	public void mkQueryForStockUntilDate_has_unspecified_StorageAttributesKey()
	{

		final CandidatesQuery stockCandidatequery = repositoryTestHelper
				.mkQueryForStockUntilDate(EventTestHelper.NOW);

		assertThat(stockCandidatequery.getMaterialDescriptorQuery().getStorageAttributesKey())
				.isSameAs(AttributesKey.ALL);
	}

	@Test
	public void constructor_sets_up_candidates_correctly()
	{
		final Candidate laterStockCandidate = repositoryTestHelper.laterStockCandidate;
		final Candidate stockCandidate = repositoryTestHelper.stockCandidate;

		assertThat(laterStockCandidate.getDate()).isAfter(stockCandidate.getDate());
		assertThat(laterStockCandidate.getId().isNull()).isFalse();
		assertThat(stockCandidate.getId().isNull()).isFalse();
		assertThat(stockCandidate.getId()).isNotEqualTo(laterStockCandidate.getId());

		final I_MD_Candidate stockCandidateRecord = retrieveForId(stockCandidate.getId());
		assertThat(stockCandidateRecord).isNotNull();
		assertThat(stockCandidateRecord.getQty()).isEqualByComparingTo("11");

		final I_MD_Candidate laterStockCandidateRecord = retrieveForId(laterStockCandidate.getId());
		assertThat(laterStockCandidateRecord).isNotNull();
		assertThat(laterStockCandidateRecord.getQty()).isEqualByComparingTo("10");
	}

	private static I_MD_Candidate retrieveForId(final CandidateId candidateId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID,
								 candidateId.getRepoId())
				.create().firstOnly(I_MD_Candidate.class);
	}

	@Test
	public void constructor_sets_up_candidates_correctly_and_queries_work()
	{
		final CandidatesQuery stockCandidatequery = repositoryTestHelper.mkQueryForStockUntilDate(EventTestHelper.NOW);
		final CandidateRepositoryRetrieval candidateRepositoryRetrieval = new CandidateRepositoryRetrieval(dimensionService, stockChangeDetailRepo);
		final Candidate retrievedStockCandidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(stockCandidatequery);
		assertThat(retrievedStockCandidate).isNotNull();
		assertThat(retrievedStockCandidate).isEqualTo(repositoryTestHelper.stockCandidate);
	}

}
