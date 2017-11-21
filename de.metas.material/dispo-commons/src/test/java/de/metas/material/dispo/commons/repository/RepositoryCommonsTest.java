package de.metas.material.dispo.commons.repository;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.PRODUCT_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.StringJoiner;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.adempiere.util.Services;
import org.junit.Test;

import de.metas.material.dispo.commons.CandidatesQuery;
import de.metas.material.dispo.commons.repository.RepositoryCommons;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.MaterialDescriptor.DateOperator;
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

		final List<IQueryFilter<I_MD_Candidate>> filters = queryBuilder.getCompositeFilter().getFilters();
		assertFiltersContainActiveFilter(filters);
		assertFiltersContainProductFilter(filters, PRODUCT_ID);
		assertFiltersContainDateFilter(filters, Operator.EQUAL);
	}

	@Test
	public void addProductionDetailToFilter_with_StorageAttributesKey_no_exact_matching()
	{
		final MaterialDescriptor materialDescriptor = commonSetupFor_addProductionDetailToFilter_with_StorageAttributesKey();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate.class);
		RepositoryCommons.addMaterialDescriptorToQueryBuilderIfNotNull(materialDescriptor, false, queryBuilder);

		final List<IQueryFilter<I_MD_Candidate>> filters = queryBuilder.getCompositeFilter().getFilters();

		assertThat(filters).hasSize(1);
		final IQueryFilter<I_MD_Candidate> asiKeyFilter = filters.get(0);
		assertThat(asiKeyFilter).isInstanceOf(StringLikeFilter.class);

		final StringLikeFilter<I_MD_Candidate> likeFilter = (StringLikeFilter<I_MD_Candidate>)asiKeyFilter;
		assertThat(likeFilter.getColumnName()).isEqualTo(I_MD_Candidate.COLUMNNAME_StorageAttributesKey);
		assertThat(likeFilter.getOperator()).isEqualTo(Operator.STRING_LIKE);
		assertThat(likeFilter.getValue()).isEqualTo("Key1%Key2%Key3");
	}

	@Test
	public void addProductionDetailToFilter_with_StorageAttributesKey_exact_matching()
	{
		final MaterialDescriptor materialDescriptor = commonSetupFor_addProductionDetailToFilter_with_StorageAttributesKey();

		final IQueryBuilder<I_MD_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate.class);
		RepositoryCommons.addMaterialDescriptorToQueryBuilderIfNotNull(materialDescriptor, true, queryBuilder);

		final List<IQueryFilter<I_MD_Candidate>> filters = queryBuilder.getCompositeFilter().getFilters();

		assertThat(filters).hasSize(1);
		final IQueryFilter<I_MD_Candidate> asiKeyFilter = filters.get(0);
		assertThat(asiKeyFilter).isInstanceOf(EqualsQueryFilter.class);

		final EqualsQueryFilter<I_MD_Candidate> likeFilter = (EqualsQueryFilter<I_MD_Candidate>)asiKeyFilter;
		assertThat(likeFilter.getColumnName()).isEqualTo(I_MD_Candidate.COLUMNNAME_StorageAttributesKey);
		assertThat(likeFilter.getOperator()).isEqualTo(Operator.EQUAL);
		assertThat(likeFilter.getValue()).isEqualTo("Key1§&§Key2§&§Key3");
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
	
	private static void assertFiltersContainProductFilter(final List<IQueryFilter<I_MD_Candidate>> filters, final int productId)
	{
		assertThat(filters).anySatisfy(productFilter -> {
			assertThat(productFilter).isInstanceOf(EqualsQueryFilter.class);
			final EqualsQueryFilter<I_MD_Candidate> equalsFilter = (EqualsQueryFilter<I_MD_Candidate>)productFilter;
			assertThat(equalsFilter.getColumnName()).isEqualTo(I_MD_Candidate.COLUMNNAME_M_Product_ID);
			assertThat(equalsFilter.getOperator()).isEqualTo(Operator.EQUAL);
			assertThat(equalsFilter.getValue()).isEqualTo(productId);
		});
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
		final List<IQueryFilter<I_MD_Candidate>> filters = queryBuilder.getCompositeFilter().getFilters();
		assertThat(filters).hasSize(2);

		assertFiltersContainActiveFilter(filters);
		assertFiltersContainDateFilter(filters, queryOperator);
	}

	private static void assertFiltersContainActiveFilter(@NonNull final List<IQueryFilter<I_MD_Candidate>> filters)
	{
		assertThat(filters).anySatisfy(onlyActiveFilter -> {
			assertThat(onlyActiveFilter).isInstanceOf(ActiveRecordQueryFilter.class);
		});
	}

	private static void assertFiltersContainDateFilter(
			@NonNull final List<IQueryFilter<I_MD_Candidate>> filters,
			@NonNull final Operator dateQueryOperator)
	{
		assertThat(filters).anySatisfy(dateFilter -> {
			assertThat(dateFilter).isInstanceOf(CompareQueryFilter.class);
			final CompareQueryFilter<I_MD_Candidate> compareFilter = (CompareQueryFilter<I_MD_Candidate>)dateFilter;
			assertThat(compareFilter.getColumnName()).isEqualTo(I_MD_Candidate.COLUMNNAME_DateProjected);
			assertThat(compareFilter.getOperator()).isEqualTo(dateQueryOperator);
			assertThat(compareFilter.getValue()).isEqualTo(NOW);
		});
	}

}
