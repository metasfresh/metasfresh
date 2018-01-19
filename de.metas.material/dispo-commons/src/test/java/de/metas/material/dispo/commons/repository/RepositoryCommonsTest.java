package de.metas.material.dispo.commons.repository;

import static de.metas.material.event.EventTestHelper.BPARTNER_ID;
import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.STORAGE_ATTRIBUTES_KEY;
import static de.metas.testsupport.MetasfreshAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.junit.Test;

import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery.DateOperator;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.AttributesKey;
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
		performDateFilterTest(DateOperator.AT, Operator.EQUAL);
		performDateFilterTest(DateOperator.AFTER, Operator.GREATER);
		performDateFilterTest(DateOperator.AT_OR_AFTER, Operator.GREATER_OR_EQUAL);
		performDateFilterTest(DateOperator.BEFORE_OR_AT, Operator.LESS_OR_EQUAL);
		performDateFilterTest(DateOperator.BEFORE, Operator.LESS);
	}

	@Test
	public void mkQueryBuilder_with_date_and_product_id()
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.productId(PRODUCT_ID)
				.bPartnerId(BPARTNER_ID)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
				.dateOperator(DateOperator.AT)
				.date(NOW).build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.build();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = RepositoryCommons.mkQueryBuilder(query);
		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = queryBuilder.getCompositeFilter();

		assertThat(compositeFilter).hasActiveRecordQueryFilter();
		assertThat(compositeFilter).hasEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, PRODUCT_ID);
		assertThat(compositeFilter).hasCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.EQUAL, NOW);
	}

	@Test
	public void mkQueryBuilder_with_bpartner_id()
	{
		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = setupAndInvokeWithBPartnerId(BPARTNER_ID);
		assertThat(compositeFilter).hasEqualsFilter(I_MD_Candidate.COLUMN_C_BPartner_ID, BPARTNER_ID);
	}

	@Test
	public void mkQueryBuilder_with_any_bpartner_id()
	{
		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = setupAndInvokeWithBPartnerId(StockQuery.BPARTNER_ID_ANY);
		assertThat(compositeFilter).hasNoFilterRegarding(I_MD_Candidate.COLUMN_C_BPartner_ID);
	}

	@Test
	public void mkQueryBuilder_with_none_bpartner_id()
	{
		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = setupAndInvokeWithBPartnerId(StockQuery.BPARTNER_ID_NONE);
		assertThat(compositeFilter).hasEqualsFilter(I_MD_Candidate.COLUMN_C_BPartner_ID, null);
	}

	public ICompositeQueryFilter<I_MD_Candidate> setupAndInvokeWithBPartnerId(final int bpartnerId)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.bPartnerId(bpartnerId)
				.build();
		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.build();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = RepositoryCommons.mkQueryBuilder(query);
		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = queryBuilder.getCompositeFilter();
		return compositeFilter;
	}

	@Test
	public void mkQueryBuilder_with_parent_id()
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.productId(PRODUCT_ID)
				.storageAttributesKey(STORAGE_ATTRIBUTES_KEY)
				.dateOperator(DateOperator.AT)
				.date(NOW).build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.parentId(30)
				.build();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = RepositoryCommons.mkQueryBuilder(query);
		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = queryBuilder.getCompositeFilter();

		assertThat(compositeFilter).hasActiveRecordQueryFilter();
		assertThat(compositeFilter).hasEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, PRODUCT_ID);
		assertThat(compositeFilter).hasCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.EQUAL, NOW);

		assertThat(compositeFilter).hasEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, 30);
	}

	@Test
	public void addMaterialDescriptorToQueryBuilderIfNotNull_with_StorageAttributesKey_no_exact_matching()
	{
		final MaterialDescriptorQuery materialDescriptorQuery = commonSetupFor_addProductionDetailToFilter_with_StorageAttributesKey();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate.class);
		RepositoryCommons.addMaterialDescriptorToQueryBuilderIfNotNull(materialDescriptorQuery, false, queryBuilder);

		final List<IQueryFilter<I_MD_Candidate>> filters = queryBuilder.getCompositeFilter().getFilters();

		assertThat(filters).hasSize(1);
		assertThat(filters.get(0)).isStringLikeFilter(I_MD_Candidate.COLUMN_StorageAttributesKey, "1%2%3");
	}

	@Test
	public void addMaterialDescriptorToQueryBuilderIfNotNull_with_StorageAttributesKey_exact_matching()
	{
		final MaterialDescriptorQuery materialDescriptorQuery = commonSetupFor_addProductionDetailToFilter_with_StorageAttributesKey();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate.class);
		RepositoryCommons.addMaterialDescriptorToQueryBuilderIfNotNull(materialDescriptorQuery, true, queryBuilder);

		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = queryBuilder.getCompositeFilter();
		final List<IQueryFilter<I_MD_Candidate>> filters = compositeFilter.getFilters();
		assertThat(filters).hasSize(1);

		assertThat(compositeFilter).hasEqualsFilter(I_MD_Candidate.COLUMN_StorageAttributesKey, "1§&§2§&§3");
	}

	private MaterialDescriptorQuery commonSetupFor_addProductionDetailToFilter_with_StorageAttributesKey()
	{
		final AttributesKey attributesKey = AttributesKey.ofAttributeValueIds(1, 2, 3);

		// this query won't occur in real life, but we want only the storage-key-filter
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.storageAttributesKey(attributesKey)
				.build();
		return materialDescriptorQuery;
	}

	private static void performDateFilterTest(
			@NonNull final DateOperator dateOperator,
			@NonNull final Operator queryOperator)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder().date(NOW)
				.dateOperator(dateOperator)
				.build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.build();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = RepositoryCommons.mkQueryBuilder(query);
		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = queryBuilder.getCompositeFilter();
		final List<IQueryFilter<I_MD_Candidate>> filters = compositeFilter.getFilters();
		assertThat(filters).hasSize(2);

		assertThat(compositeFilter).hasActiveRecordQueryFilter();
		assertThat(compositeFilter).hasCompareFilter(I_MD_Candidate.COLUMN_DateProjected, queryOperator, NOW);
	}
}
