package de.metas.distribution.ddordercandidate.process;

import de.metas.impexp.InputDataSourceId;
import de.metas.testsupport.MetasfreshAssertions;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
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

		// (base) null, null → base filters always present
		{
			final IQueryBuilder<I_DD_Order_Candidate> qb = process.selectionQueryBuilder(null, null);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = qb.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_Processed, false)
					.hasCompareFilter(I_DD_Order_Candidate.COLUMN_QtyToProcess, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO)
					.hasActiveRecordQueryFilter();
		}

		// (source set) null, sourceId → source equals-filter present
		{
			final InputDataSourceId sourceId = InputDataSourceId.ofRepoId(42);
			final IQueryBuilder<I_DD_Order_Candidate> qb = process.selectionQueryBuilder(null, sourceId);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = qb.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID, sourceId);
		}

		// (source null) null, null → no source filter
		{
			final IQueryBuilder<I_DD_Order_Candidate> qb = process.selectionQueryBuilder(null, null);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = qb.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasNoFilterRegarding(I_DD_Order_Candidate.COLUMNNAME_AD_InputDataSource_ID);
		}

		// (grid present) plain EqualsQueryFilter as userSelectionFilter → composite has that equals-filter as direct child
		{
			final IQueryFilter<I_DD_Order_Candidate> userSelectionFilter =
					EqualsQueryFilter.of(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID, 999);

			final IQueryBuilder<I_DD_Order_Candidate> qb = process.selectionQueryBuilder(userSelectionFilter, null);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = qb.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasEqualsFilter(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID, 999);
		}

		// (grid null) null, null → no DD_Order_Candidate_ID filter
		{
			final IQueryBuilder<I_DD_Order_Candidate> qb = process.selectionQueryBuilder(null, null);
			final ICompositeQueryFilter<I_DD_Order_Candidate> compositeFilter = qb.getCompositeFilter();
			MetasfreshAssertions.assertThat(compositeFilter)
					.hasNoFilterRegarding(I_DD_Order_Candidate.COLUMNNAME_DD_Order_Candidate_ID);
		}
	}
}
