package de.metas.distribution.ddordercandidate.process;

import de.metas.impexp.InputDataSourceId;
import de.metas.testsupport.MetasfreshAssertions;
import de.metas.util.Services;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.model.I_DD_Order_Candidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class DD_Order_Candidate_EnqueueToProcessTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void buildsSelectionQuery()
	{
		final DD_Order_Candidate_EnqueueToProcess process = new DD_Order_Candidate_EnqueueToProcess();

		// (c) base filters Processed=false, QtyToProcess>0, active ALWAYS present — check with null source, null grid
		{
			final IQueryBuilder<I_DD_Order_Candidate> qb = process.buildSelectionQuery(null, null);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = qb.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Processed, false)
					.hasCompareFilter(I_DD_Order_Candidate.COLUMN_QtyToProcess, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
					.hasActiveRecordQueryFilter();
		}

		// (a) source set → source equals-filter present
		{
			final InputDataSourceId sourceId = InputDataSourceId.ofRepoId(42);
			final IQueryBuilder<I_DD_Order_Candidate> qb = process.buildSelectionQuery(null, sourceId);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = qb.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID, sourceId);
		}

		// (b) source null → no source filter
		{
			final IQueryBuilder<I_DD_Order_Candidate> qb = process.buildSelectionQuery(null, null);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = qb.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasNoFilterRegarding(I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID);
		}

		// (d) grid-selection filter applied when provided
		{
			final IQueryBL queryBL = Services.get(IQueryBL.class);
			final IQueryBuilder<I_DD_Order_Candidate> gridSelectionQB = queryBL
					.createQueryBuilder(I_DD_Order_Candidate.class)
					.addEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID, 999);

			final IQueryBuilder<I_DD_Order_Candidate> withGrid = process.buildSelectionQuery(gridSelectionQB, null);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = withGrid.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID, 999);
		}

		// (d) grid-selection filter absent when null
		{
			final IQueryBuilder<I_DD_Order_Candidate> withoutGrid = process.buildSelectionQuery(null, null);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = withoutGrid.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasNoFilterRegarding(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID);
		}
	}
}
