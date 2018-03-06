package de.metas.material.dispo.commons.repository.query;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;

import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail.ProductionDetailBuilder;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Prod_Detail;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder
public class ProductionDetailsQuery
{
	public static final int NO_PP_ORDER_LINE_ID = Integer.MIN_VALUE;

	public static final ProductionDetailsQuery NO_PRODUCTION_DETAIL = ProductionDetailsQuery.builder()
			.productPlanningId(-10)
			.productBomLineId(-10)
			.ppOrderId(-10)
			.ppOrderLineId(-10).build();

	public static ProductionDetailsQuery ofProductionDetailOrNull(
			@Nullable final ProductionDetail productionDetail)
	{
		if (productionDetail == null)
		{
			return null;
		}

		return ProductionDetailsQuery.builder()
				.productPlanningId(productionDetail.getProductPlanningId())
				.productBomLineId(productionDetail.getProductBomLineId())
				.ppOrderId(productionDetail.getPpOrderId())
				.ppOrderLineId(productionDetail.getPpOrderLineId())
				.build();
	}

	int productPlanningId;

	int productBomLineId;

	int ppOrderId;

	int ppOrderLineId;

	/**
	 * Convenience method that uses this instance to kickstart an return a builder.
	 *
	 * @return
	 */
	public ProductionDetailBuilder toProductionDetailBuilder()
	{
		return ProductionDetail.builder()
				.productPlanningId(productPlanningId)
				.productBomLineId(productBomLineId)
				.ppOrderId(ppOrderId)
				.ppOrderLineId(ppOrderLineId);
	}

	public void augmentQueryBuilder(@NonNull final IQueryBuilder<I_MD_Candidate> builder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate_Prod_Detail> productDetailSubQueryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_Prod_Detail.class)
				.addOnlyActiveRecordsFilter();

		if (ProductionDetailsQuery.NO_PRODUCTION_DETAIL.equals(this))
		{
			builder.addNotInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID,
					I_MD_Candidate_Prod_Detail.COLUMN_MD_Candidate_ID,
					productDetailSubQueryBuilder.create());
		}
		else
		{
			boolean doFilter = false;
			if (productPlanningId > 0)
			{
				productDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_PP_Product_Planning_ID, productPlanningId);
				doFilter = true;
			}
			if (productBomLineId > 0)
			{
				productDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_PP_Product_BOMLine_ID, productBomLineId);
				doFilter = true;
			}
			if (ppOrderId > 0)
			{
				productDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_PP_Order_ID, ppOrderId);
				doFilter = true;
			}

			if (ppOrderLineId > 0)
			{
				productDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_PP_Order_BOMLine_ID, ppOrderLineId);
				doFilter = true;
			}
			else if (ppOrderLineId == NO_PP_ORDER_LINE_ID)
			{
				productDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Prod_Detail.COLUMN_PP_Order_BOMLine_ID, null);
				doFilter = true;
			}

			if (doFilter)
			{
				builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID,
						I_MD_Candidate_Prod_Detail.COLUMN_MD_Candidate_ID,
						productDetailSubQueryBuilder.create());
			}
		}
	}
}
