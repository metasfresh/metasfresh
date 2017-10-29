package de.metas.material.dispo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.junit.Test;

import de.metas.material.dispo.CandidatesQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.EventTestHelper;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialDescriptor.DateOperator;
import lombok.NonNull;

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

public class RepositoryCommonsTest
{

	@Test
	public void mkQueryBuilder_with_date()
	{
		performTest(DateOperator.AT, Operator.EQUAL);
		performTest(DateOperator.AFTER, Operator.GREATER);
		performTest(DateOperator.AT_OR_AFTER, Operator.GREATER_OR_EQUAL);
		performTest(DateOperator.BEFORE_OR_AT, Operator.LESS_OR_EQUAL);
	}

	@Test
	public void mkQueryBuilder_with_date_and_product_id()
	{
		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescr(MaterialDescriptor
						.builderForQuery()
						.productDescriptor(EventTestHelper.createProductDescriptor())
						.dateOperator(DateOperator.AT)
						.date(EventTestHelper.NOW)
						.build())
				.build();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = RepositoryCommons.mkQueryBuilder(query);

		final List<IQueryFilter<I_MD_Candidate>> filters = queryBuilder.getFilters().getFilters();
		assertFiltersContainActiveFilter(filters);
		assertFiltersContainProductFilter(filters, EventTestHelper.PRODUCT_ID);
		assertFiltersContainDateFilter(filters, Operator.EQUAL);
	}

	private void assertFiltersContainProductFilter(final List<IQueryFilter<I_MD_Candidate>> filters, final int productId)
	{
		assertThat(filters).anySatisfy(productFilter -> {
			assertThat(productFilter).isInstanceOf(EqualsQueryFilter.class);
			final EqualsQueryFilter<I_MD_Candidate> equalsFilter = (EqualsQueryFilter<I_MD_Candidate>)productFilter;
			assertThat(equalsFilter.getColumnName()).isEqualTo(I_MD_Candidate.COLUMNNAME_M_Product_ID);
			assertThat(equalsFilter.getOperator()).isEqualTo(Operator.EQUAL);
			assertThat(equalsFilter.getValue()).isEqualTo(productId);
		});
	}

	private void performTest(final DateOperator dateOperator, final Operator queryOperator)
	{
		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescr(MaterialDescriptor
						.builderForQuery()
						.dateOperator(dateOperator)
						.date(EventTestHelper.NOW)
						.build())
				.build();
		final IQueryBuilder<I_MD_Candidate> queryBuilder = RepositoryCommons.mkQueryBuilder(query);
		final List<IQueryFilter<I_MD_Candidate>> filters = queryBuilder.getFilters().getFilters();
		assertThat(filters).hasSize(2);

		assertFiltersContainActiveFilter(filters);
		assertFiltersContainDateFilter(filters, queryOperator);
	}

	private void assertFiltersContainActiveFilter(final List<IQueryFilter<I_MD_Candidate>> filters)
	{
		assertThat(filters).anySatisfy(onlyActiveFilter -> {
			assertThat(onlyActiveFilter).isInstanceOf(ActiveRecordQueryFilter.class);
		});
	}

	private void assertFiltersContainDateFilter(
			@NonNull final List<IQueryFilter<I_MD_Candidate>> filters,
			@NonNull final Operator dateQueryOperator)
	{
		assertThat(filters).anySatisfy(dateFilter -> {
			assertThat(dateFilter).isInstanceOf(CompareQueryFilter.class);
			final CompareQueryFilter<I_MD_Candidate> compareFilter = (CompareQueryFilter<I_MD_Candidate>)dateFilter;
			assertThat(compareFilter.getColumnName()).isEqualTo(I_MD_Candidate.COLUMNNAME_DateProjected);
			assertThat(compareFilter.getOperator()).isEqualTo(dateQueryOperator);
			assertThat(compareFilter.getValue()).isEqualTo(EventTestHelper.NOW);
		});
	}

}
