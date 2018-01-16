package de.metas.material.dispo.commons.repository.query;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;

import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail.DistributionDetailBuilder;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
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
public class DistributionDetailsQuery
{
	public static final DistributionDetailsQuery NO_DISTRIBUTION_DETAIL = DistributionDetailsQuery.builder()
			.productPlanningId(-10)
			.networkDistributionLineId(-10)
			.ddOrderId(-10)
			.ddOrderLineId(-10)
			.build();

	public static DistributionDetailsQuery fromDistributionDetail(DistributionDetail distributionDetail)
	{
		return builder()
				.productPlanningId(distributionDetail.getProductPlanningId())
				.networkDistributionLineId(distributionDetail.getNetworkDistributionLineId())
				.ddOrderId(distributionDetail.getDdOrderId())
				.ddOrderLineId(distributionDetail.getDdOrderLineId())
				.build();
	}

	int productPlanningId;

	int networkDistributionLineId;

	int ddOrderLineId;

	int ddOrderId;

	/**
	 * Convenience method that uses this instance to kickstart and return a builder.
	 *
	 */
	public DistributionDetailBuilder toDistributionDetailBuilder()
	{
		return DistributionDetail.builder()
				.productPlanningId(productPlanningId)
				.networkDistributionLineId(networkDistributionLineId)
				.ddOrderId(ddOrderId)
				.ddOrderLineId(ddOrderLineId);
	}

	public void augmentQueryBuilder(@NonNull final IQueryBuilder<I_MD_Candidate> builder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate_Dist_Detail> distDetailSubQueryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_Dist_Detail.class)
				.addOnlyActiveRecordsFilter();

		if (NO_DISTRIBUTION_DETAIL.equals(this))
		{
			builder.addNotInSubQueryFilter(
					I_MD_Candidate.COLUMN_MD_Candidate_ID,
					I_MD_Candidate_Dist_Detail.COLUMN_MD_Candidate_ID,
					distDetailSubQueryBuilder.create());
		}
		else
		{
			boolean doFilter = false;
			if (productPlanningId > 0)
			{
				distDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_PP_Product_Planning_ID, productPlanningId);
				doFilter = true;
			}
			if (networkDistributionLineId > 0)
			{
				distDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_DD_NetworkDistributionLine_ID, networkDistributionLineId);
				doFilter = true;
			}
			if (ddOrderId > 0)
			{
				distDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_DD_Order_ID, ddOrderId);
				doFilter = true;
			}
			if (ddOrderLineId > 0)
			{
				distDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_DD_OrderLine_ID, ddOrderLineId);
				doFilter = true;
			}

			if (doFilter)
			{
				builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID, I_MD_Candidate_Dist_Detail.COLUMN_MD_Candidate_ID, distDetailSubQueryBuilder.create());
			}
		}
	}
}
