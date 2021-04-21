/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.dao;

import com.google.common.collect.ImmutableMap;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaArticle;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaBillableTherapy;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaPackagingUnit;
import de.metas.vertical.healthcare.alberta.model.I_M_Product_AlbertaTherapy;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class AlbertaProductDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Map<ProductId, List<I_M_Product_AlbertaTherapy>> getTherapies(@NonNull final AlbertaDataQuery albertaDataQuery)
	{
		final IQueryBuilder<I_M_Product_AlbertaTherapy> queryBuilder = queryBL.createQueryBuilder(I_M_Product_AlbertaTherapy.class)
				.addOnlyActiveRecordsFilter();

		final Timestamp updatedAfter = TimeUtil.asTimestamp(albertaDataQuery.getUpdatedAfter());
		final ICompositeQueryFilter<I_M_Product_AlbertaTherapy> productOrTimestampFilter = queryBL.createCompositeQueryFilter(I_M_Product_AlbertaTherapy.class)
				.setJoinOr()
				.addInArrayFilter(I_M_Product_AlbertaTherapy.COLUMNNAME_M_Product_ID, albertaDataQuery.getProductIds())
				.addCompareFilter(I_M_Product_AlbertaTherapy.COLUMNNAME_Updated, CompareQueryFilter.Operator.GREATER_OR_EQUAL, updatedAfter);

		queryBuilder.filter(productOrTimestampFilter);

		return queryBuilder
				.create()
				.list()
				.stream()
				.collect(Collectors.groupingBy((record) -> ProductId.ofRepoId(record.getM_Product_ID())));
	}

	@NonNull
	public Map<ProductId, List<I_M_Product_AlbertaBillableTherapy>> getBillableTherapies(@NonNull final AlbertaDataQuery albertaDataQuery)
	{
		final IQueryBuilder<I_M_Product_AlbertaBillableTherapy> queryBuilder = queryBL.createQueryBuilder(I_M_Product_AlbertaBillableTherapy.class)
				.addOnlyActiveRecordsFilter();

		final Timestamp updatedAfter = TimeUtil.asTimestamp(albertaDataQuery.getUpdatedAfter());
		final ICompositeQueryFilter<I_M_Product_AlbertaBillableTherapy> productOrTimestampFilter = queryBL.createCompositeQueryFilter(I_M_Product_AlbertaBillableTherapy.class)
				.setJoinOr()
				.addInArrayFilter(I_M_Product_AlbertaBillableTherapy.COLUMNNAME_M_Product_ID, albertaDataQuery.getProductIds())
				.addCompareFilter(I_M_Product_AlbertaBillableTherapy.COLUMNNAME_Updated, CompareQueryFilter.Operator.GREATER_OR_EQUAL, updatedAfter);

		queryBuilder.filter(productOrTimestampFilter);

		return queryBuilder
				.create()
				.list()
				.stream()
				.collect(Collectors.groupingBy(record -> ProductId.ofRepoId(record.getM_Product_ID())));
	}

	@NonNull
	public Map<ProductId, I_M_Product_AlbertaArticle> getAlbertaArticles(@NonNull final AlbertaDataQuery albertaDataQuery)
	{
		final IQueryBuilder<I_M_Product_AlbertaArticle> queryBuilder = queryBL.createQueryBuilder(I_M_Product_AlbertaArticle.class)
				.addOnlyActiveRecordsFilter();

		final Timestamp updatedAfter = TimeUtil.asTimestamp(albertaDataQuery.getUpdatedAfter());
		final ICompositeQueryFilter<I_M_Product_AlbertaArticle> productOrTimestampFilter = queryBL.createCompositeQueryFilter(I_M_Product_AlbertaArticle.class)
				.setJoinOr()
				.addInArrayFilter(I_M_Product_AlbertaArticle.COLUMNNAME_M_Product_ID, albertaDataQuery.getProductIds())
				.addCompareFilter(I_M_Product_AlbertaArticle.COLUMNNAME_Updated, CompareQueryFilter.Operator.GREATER_OR_EQUAL, updatedAfter);

		queryBuilder.filter(productOrTimestampFilter);

		return queryBuilder
				.create()
				.list()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> ProductId.ofRepoId(record.getM_Product_ID()),
						Function.identity()));
	}

	@NonNull
	public Map<ProductId, List<I_M_Product_AlbertaPackagingUnit>> getPackagingUnits(@NonNull final AlbertaDataQuery albertaDataQuery)
	{
		final IQueryBuilder<I_M_Product_AlbertaPackagingUnit> queryBuilder = queryBL.createQueryBuilder(I_M_Product_AlbertaPackagingUnit.class)
				.addOnlyActiveRecordsFilter();

		final Timestamp updatedAfter = TimeUtil.asTimestamp(albertaDataQuery.getUpdatedAfter());
		final ICompositeQueryFilter<I_M_Product_AlbertaPackagingUnit> productOrTimestampFilter = queryBL.createCompositeQueryFilter(I_M_Product_AlbertaPackagingUnit.class)
				.setJoinOr()
				.addInArrayFilter(I_M_Product_AlbertaPackagingUnit.COLUMNNAME_M_Product_ID, albertaDataQuery.getProductIds())
				.addCompareFilter(I_M_Product_AlbertaPackagingUnit.COLUMNNAME_Updated, CompareQueryFilter.Operator.GREATER_OR_EQUAL, updatedAfter);

		queryBuilder.filter(productOrTimestampFilter);

		return queryBuilder
				.create()
				.list()
				.stream()
				.collect(Collectors.groupingBy(record -> ProductId.ofRepoId(record.getM_Product_ID())));
	}
}
