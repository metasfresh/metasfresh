package de.metas.material.dispo.commons.repository;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.testsupport.MetasfreshAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.StringJoiner;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Services;
import org.junit.Test;

import de.metas.material.dispo.commons.CandidatesQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MaterialDescriptor.DateOperator;
import de.metas.material.event.commons.ProductDescriptor;
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
		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptor(MaterialDescriptor
						.builderForQuery()
						.productDescriptor(createProductDescriptor())
						.dateOperator(DateOperator.AT)
						.date(NOW)
						.build())
				.build();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = RepositoryCommons.mkQueryBuilder(query);
		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = queryBuilder.getCompositeFilter();

		assertThat(compositeFilter).hasActiveRecordQueryFilter();
		assertThat(compositeFilter).hasEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, PRODUCT_ID);
		assertThat(compositeFilter).hasCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.EQUAL, NOW);
	}

	@Test
	public void mkQueryBuilder_with_parent_id()
	{
		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptor(MaterialDescriptor
						.builderForQuery()
						.productDescriptor(createProductDescriptor())
						.dateOperator(DateOperator.AT)
						.date(NOW)
						.build())
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
		final MaterialDescriptor materialDescriptor = commonSetupFor_addProductionDetailToFilter_with_StorageAttributesKey();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate.class);
		RepositoryCommons.addMaterialDescriptorToQueryBuilderIfNotNull(materialDescriptor, false, queryBuilder);

		final List<IQueryFilter<I_MD_Candidate>> filters = queryBuilder.getCompositeFilter().getFilters();

		assertThat(filters).hasSize(1);
		assertThat(filters.get(0)).isStringLikeFilter(I_MD_Candidate.COLUMN_StorageAttributesKey, "Key1%Key2%Key3");
	}

	@Test
	public void addMaterialDescriptorToQueryBuilderIfNotNull_with_StorageAttributesKey_exact_matching()
	{
		final MaterialDescriptor materialDescriptor = commonSetupFor_addProductionDetailToFilter_with_StorageAttributesKey();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate.class);
		RepositoryCommons.addMaterialDescriptorToQueryBuilderIfNotNull(materialDescriptor, true, queryBuilder);

		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = queryBuilder.getCompositeFilter();
		final List<IQueryFilter<I_MD_Candidate>> filters = compositeFilter.getFilters();
		assertThat(filters).hasSize(1);

		assertThat(compositeFilter).hasEqualsFilter(I_MD_Candidate.COLUMN_StorageAttributesKey, "Key1§&§Key2§&§Key3");
	}

	private MaterialDescriptor commonSetupFor_addProductionDetailToFilter_with_StorageAttributesKey()
	{
		final String storageAttributesKey = new StringJoiner(ProductDescriptor.STORAGE_ATTRIBUTES_KEY_DELIMITER)
				.add("Key1")
				.add("Key2")
				.add("Key3")
				.toString();

		// this descriptor won't occur in real life, but we want only the storage-key-filter
		final ProductDescriptor productDescriptor = new ProductDescriptor(false, -1, storageAttributesKey, -1);

		final MaterialDescriptor materialDescriptor = MaterialDescriptor
				.builderForQuery()
				.productDescriptor(productDescriptor)
				.build();
		return materialDescriptor;
	}

	private static void performDateFilterTest(
			@NonNull final DateOperator dateOperator,
			@NonNull final Operator queryOperator)
	{
		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptor(MaterialDescriptor
						.builderForQuery()
						.dateOperator(dateOperator)
						.date(NOW)
						.build())
				.build();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = RepositoryCommons.mkQueryBuilder(query);
		final ICompositeQueryFilter<I_MD_Candidate> compositeFilter = queryBuilder.getCompositeFilter();
		final List<IQueryFilter<I_MD_Candidate>> filters = compositeFilter.getFilters();
		assertThat(filters).hasSize(2);

		assertThat(compositeFilter).hasActiveRecordQueryFilter();
		assertThat(compositeFilter).hasCompareFilter(I_MD_Candidate.COLUMN_DateProjected, queryOperator, NOW);
	}
}
