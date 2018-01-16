package de.metas.material.dispo.commons.repository;

import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery.DateOperator;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Dist_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import de.metas.material.event.commons.AttributesKey;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class RepositoryCommons
{

	/**
	 * Turns the given segment into the "where part" of a big query builder. Does not specify the ordering.
	 *
	 * @param query
	 * @return
	 */
	public IQueryBuilder<I_MD_Candidate> mkQueryBuilder(@NonNull final CandidatesQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate> builder = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter();

		if(CandidatesQuery.FALSE.equals(query))
		{
			builder.filter(ConstantQueryFilter.of(false));
			return builder;
		}
		else if (query.getId() > 0)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID, query.getId());
			return builder;
		}

		if (query.getType() != null)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, query.getType().toString());
		}


		if (query.getParentId() >= 0)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, query.getParentId());
		}

		if (query.getGroupId() > 0)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_GroupId, query.getGroupId());
		}

		addMaterialDescriptorToQueryBuilderIfNotNull(
				query.getMaterialDescriptorQuery(),
				query.isMatchExactStorageAttributesKey(),
				builder);

		if (query.getParentMaterialDescriptorQuery() != null)
		{
			final IQueryBuilder<I_MD_Candidate> parentBuilder = queryBL.createQueryBuilder(I_MD_Candidate.class)
					.addOnlyActiveRecordsFilter();

			final boolean atLeastOneFilterAdded = addMaterialDescriptorToQueryBuilderIfNotNull(
					query.getParentMaterialDescriptorQuery(),
					query.isMatchExactStorageAttributesKey(),
					parentBuilder);

			if (atLeastOneFilterAdded)
			{
				// restrict our set of matches to those records that reference a parent record which have the give product and/or warehouse.
				builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, I_MD_Candidate.COLUMN_MD_Candidate_ID, parentBuilder.create());
			}
		}

		if (query.getParentDemandDetail() != null)
		{
			final IQueryBuilder<I_MD_Candidate> parentBuilder = queryBL.createQueryBuilder(I_MD_Candidate.class)
					.addOnlyActiveRecordsFilter();
			addDemandDetailToBuilder(query.getParentDemandDetail(), parentBuilder);
			builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_Parent_ID, I_MD_Candidate.COLUMN_MD_Candidate_ID, parentBuilder.create());
		}

		if (query.getDemandDetail() != null)
		{
			addDemandDetailToBuilder(query.getDemandDetail(), builder);
		}

		addProductionDetailToFilter(query, builder);

		addDistributionDetailToFilter(query, builder);

		addTransactionDetailToFilter(query, builder);

		return builder;
	}

	@VisibleForTesting
	boolean addMaterialDescriptorToQueryBuilderIfNotNull(
			@Nullable final MaterialDescriptorQuery materialDescriptorQuery,
			final boolean matchExactStorageAttributesKey,
			@NonNull final IQueryBuilder<I_MD_Candidate> builder)
	{
		boolean atLeastOneFilterAdded = false;

		if (materialDescriptorQuery == null)
		{
			return atLeastOneFilterAdded;
		}

		if (materialDescriptorQuery.getWarehouseId() > 0)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_M_Warehouse_ID, materialDescriptorQuery.getWarehouseId());
			atLeastOneFilterAdded = true;
		}
		if (materialDescriptorQuery.getProductId() > 0)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, materialDescriptorQuery.getProductId());
			atLeastOneFilterAdded = true;
		}
		if (materialDescriptorQuery.getBPartnerId() > 0)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_C_BPartner_ID, materialDescriptorQuery.getBPartnerId());
			atLeastOneFilterAdded = true;
		}
		else if (materialDescriptorQuery.getBPartnerId() == StockQuery.BPARTNER_ID_NONE)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_C_BPartner_ID, null);
			atLeastOneFilterAdded = true;
		}

		if (!Objects.equals(materialDescriptorQuery.getStorageAttributesKey(), AttributesKey.ALL))
		{
			final AttributesKey attributesKey = materialDescriptorQuery.getStorageAttributesKey();
			if (matchExactStorageAttributesKey)
			{
				builder.addEqualsFilter(I_MD_Candidate.COLUMN_StorageAttributesKey, attributesKey.getAsString());
			}
			else
			{
				builder.addStringLikeFilter(I_MD_Candidate.COLUMN_StorageAttributesKey, attributesKey.getSqlLikeString(), false); // iggnoreCase=false
			}
			atLeastOneFilterAdded = true;
		}

		atLeastOneFilterAdded = configureBuilderDateFilters(materialDescriptorQuery, builder) || atLeastOneFilterAdded;

		return atLeastOneFilterAdded;
	}

	private boolean configureBuilderDateFilters(
			@NonNull final MaterialDescriptorQuery materialDescriptorQuery,
			@NonNull final IQueryBuilder<I_MD_Candidate> builder)
	{
		if (materialDescriptorQuery.getDate() == null)
		{
			return false;
		}
		final DateOperator dateOperator = Preconditions.checkNotNull(materialDescriptorQuery.getDateOperator(),
				"As the given parameter query spefifies a date, it also needs to have a not-null dateOperator; query=%s", materialDescriptorQuery);
		switch (dateOperator)
		{
			case BEFORE:
				builder.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.LESS, materialDescriptorQuery.getDate());
				break;
			case BEFORE_OR_AT:
				builder.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, materialDescriptorQuery.getDate());
				break;
			case AT:
				builder.addEqualsFilter(I_MD_Candidate.COLUMN_DateProjected, materialDescriptorQuery.getDate());
				break;
			case AT_OR_AFTER:
				builder.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.GREATER_OR_EQUAL, materialDescriptorQuery.getDate());
				break;
			case AFTER:
				builder.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.GREATER, materialDescriptorQuery.getDate());
				break;
			default:
				Check.errorIf(true, "segment has a unexpected dateOperator {}; segment={}", materialDescriptorQuery.getDateOperator(), materialDescriptorQuery);
				break;
		}
		return true;
	}

	/**
	 * filter by demand detail, ignore if there is none!
	 *
	 * @param candidate
	 * @param builder
	 */
	private void addDemandDetailToBuilder(
			@Nullable final DemandDetail demandDetail,
			@NonNull final IQueryBuilder<I_MD_Candidate> builder)
	{
		if (demandDetail == null)
		{
			return;
		}

		final IQueryBuilder<I_MD_Candidate_Demand_Detail> demandDetailsSubQueryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate_Demand_Detail.class)
				.addOnlyActiveRecordsFilter();

		final boolean hasOrderLine = demandDetail.getOrderLineId() > 0;
		if (hasOrderLine)
		{
			demandDetailsSubQueryBuilder
					.addEqualsFilter(I_MD_Candidate_Demand_Detail.COLUMN_C_OrderLine_ID, demandDetail.getOrderLineId());
		}

		final boolean hasShipmentschedule = demandDetail.getShipmentScheduleId() > 0;
		if (hasShipmentschedule)
		{
			demandDetailsSubQueryBuilder
					.addEqualsFilter(I_MD_Candidate_Demand_Detail.COLUMNNAME_M_ShipmentSchedule_ID, demandDetail.getShipmentScheduleId());
		}

		final boolean hasForecastLine = demandDetail.getForecastLineId() > 0;
		if (hasForecastLine)
		{
			demandDetailsSubQueryBuilder
					.addEqualsFilter(I_MD_Candidate_Demand_Detail.COLUMN_M_ForecastLine_ID, demandDetail.getForecastLineId());
		}

		if (hasOrderLine || hasForecastLine || hasShipmentschedule)
		{
			builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID,
					I_MD_Candidate_Demand_Detail.COLUMN_MD_Candidate_ID,
					demandDetailsSubQueryBuilder.create());
		}
	}

	private void addProductionDetailToFilter(
			final CandidatesQuery candidate,
			final IQueryBuilder<I_MD_Candidate> builder)
	{
		final ProductionDetailsQuery productionDetailsQuery = candidate.getProductionDetailsQuery();
		if (productionDetailsQuery == null)
		{
			return;
		}

		productionDetailsQuery.augmentQueryBuilder(builder);
	}

	private void addDistributionDetailToFilter(
			@NonNull final CandidatesQuery query,
			@NonNull final IQueryBuilder<I_MD_Candidate> builder)
	{
		final DistributionDetail distributionDetail = query.getDistributionDetail();
		if (distributionDetail == null)
		{
			return;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate_Dist_Detail> distDetailSubQueryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_Dist_Detail.class)
				.addOnlyActiveRecordsFilter();

		if (distributionDetail == CandidatesQuery.NO_DISTRIBUTION_DETAIL)
		{
			builder.addNotInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID, I_MD_Candidate_Dist_Detail.COLUMN_MD_Candidate_ID, distDetailSubQueryBuilder.create());
		}
		else
		{
			boolean doFilter = false;
			if (distributionDetail.getProductPlanningId() > 0)
			{
				distDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_PP_Product_Planning_ID, distributionDetail.getProductPlanningId());
				doFilter = true;
			}
			if (distributionDetail.getNetworkDistributionLineId() > 0)
			{
				distDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Dist_Detail.COLUMN_DD_NetworkDistributionLine_ID, distributionDetail.getNetworkDistributionLineId());
				doFilter = true;
			}
			if (doFilter)
			{
				builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID, I_MD_Candidate_Dist_Detail.COLUMN_MD_Candidate_ID, distDetailSubQueryBuilder.create());
			}
		}
	}

	private void addTransactionDetailToFilter(
			@NonNull final CandidatesQuery query,
			@NonNull final IQueryBuilder<I_MD_Candidate> builder)
	{
		final TransactionDetail transactionDetail = query.getTransactionDetail();
		if (transactionDetail == null)
		{
			return;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate_Transaction_Detail> transactionDetailSubQueryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_Transaction_Detail.class)
				.addOnlyActiveRecordsFilter();

		Preconditions.checkArgument(
				transactionDetail.getTransactionId() > 0,
				"Every transactionDetail instance needs to have transactionId>0; transactionDetail=%s",
				transactionDetail);
		transactionDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Transaction_Detail.COLUMN_M_Transaction_ID, transactionDetail.getTransactionId());

		if (transactionDetail.getQuantity() != null)
		{
			transactionDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Transaction_Detail.COLUMN_MovementQty, transactionDetail.getQuantity());
		}

		builder.addInSubQueryFilter(I_MD_Candidate.COLUMN_MD_Candidate_ID, I_MD_Candidate_Transaction_Detail.COLUMN_MD_Candidate_ID, transactionDetailSubQueryBuilder.create());
	}

	public <T> T retrieveSingleCandidateDetail(
			@NonNull final I_MD_Candidate candidateRecord,
			@NonNull final Class<T> modelClass)
	{
		final IQuery<T> candidateDetailQueryBuilder = createCandidateDetailQueryBuilder(candidateRecord, modelClass);
		final T existingDetail = candidateDetailQueryBuilder
				.firstOnly(modelClass);
		return existingDetail;
	}

	public <T> IQuery<T> createCandidateDetailQueryBuilder(
			@NonNull final I_MD_Candidate candidateRecord,
			@NonNull final Class<T> modelClass)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQuery<T> candidateDetailQueryBuilder = queryBL.createQueryBuilder(modelClass)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_ID, candidateRecord.getMD_Candidate_ID())
				.create();
		return candidateDetailQueryBuilder;
	}

}
