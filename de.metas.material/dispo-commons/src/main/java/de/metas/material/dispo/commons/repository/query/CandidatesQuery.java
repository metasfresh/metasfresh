package de.metas.material.dispo.commons.repository.query;

import java.util.Objects;

import org.adempiere.util.Check;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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

/**
 * Identifies a set of candidates.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@Wither
public final class CandidatesQuery
{
	public static final DistributionDetail NO_DISTRIBUTION_DETAIL = DistributionDetail.builder().build();

	public static final int UNSPECIFIED_PARENT_ID = -1;

	public static final int UNSPECIFIED_ID = -1;

	/**
	 * This query matches no candidate
	 */
	public static final CandidatesQuery FALSE = CandidatesQuery.fromId(-99);

	public static CandidatesQuery fromCandidate(
			@NonNull final Candidate candidate,
			final boolean includeParentId)
	{
		if (candidate.getId() > 0)
		{
			return CandidatesQuery.fromId(candidate.getId());
		}

		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery
				.fromProductionDetail(candidate.getProductionDetail());

		final CandidatesQueryBuilder builder = CandidatesQuery.builder()
				.materialDescriptorQuery(MaterialDescriptorQuery.forDescriptor(candidate.getMaterialDescriptor()))
				.matchExactStorageAttributesKey(true)
				.demandDetail(candidate.getDemandDetail())
				.distributionDetail(candidate.getDistributionDetail())
				.groupId(candidate.getGroupId())
				.orgId(candidate.getOrgId())
				.productionDetailsQuery(productionDetailsQuery)
				.status(candidate.getStatus())
				.businessCase(candidate.getBusinessCase())
				.type(candidate.getType());

		if (includeParentId)
		{
			builder.parentId(candidate.getParentId());
		}
		else
		{
			builder.parentId(UNSPECIFIED_PARENT_ID);
		}
		return builder.build();
	}

	public static CandidatesQuery fromId(final int id)
	{
		return CandidatesQuery.builder().id(id).parentId(UNSPECIFIED_PARENT_ID).build();
	}

	/**
	 * If set, then this query is about {@link Candidate}s that have a parent candidate which matches the given material descriptor.
	 */
	MaterialDescriptorQuery parentMaterialDescriptorQuery;

	DemandDetail parentDemandDetail;

	int orgId;

	CandidateType type;

	/**
	 * Should be {@code null} for stock candidates.
	 */
	CandidateBusinessCase businessCase;

	CandidateStatus status;

	int id;

	/**
	 * A supply candidate has a stock candidate as its parent. A demand candidate has a stock candidate as its child.<br>
	 * -1 means {@link #UNSPECIFIED_PARENT_ID}
	 */
	int parentId;

	/**
	 * A supply candidate and its corresponding demand candidate are associated by a common group id.
	 */
	int groupId;

	MaterialDescriptorQuery materialDescriptorQuery;

	boolean matchExactStorageAttributesKey;

	/**
	 * Used for additional infos if this candidate has the sub type {@link CandidateBusinessCase#PRODUCTION}.
	 */
	ProductionDetailsQuery productionDetailsQuery;

	/**
	 * Used for additional infos if this candidate has the sub type {@link CandidateBusinessCase#DISTRIBUTION}.
	 */
	DistributionDetail distributionDetail;

	/**
	 * Used for additional infos if this candidate relates to particular demand
	 */
	DemandDetail demandDetail;

	TransactionDetail transactionDetail;

	@Builder
	public CandidatesQuery(
			final MaterialDescriptorQuery parentMaterialDescriptorQuery,
			final DemandDetail parentDemandDetail,
			final int orgId,
			final CandidateType type,
			final CandidateBusinessCase businessCase,
			final CandidateStatus status,
			final Integer id,
			final Integer parentId,
			final int groupId,
			final MaterialDescriptorQuery materialDescriptorQuery,
			final boolean matchExactStorageAttributesKey,
			final ProductionDetailsQuery productionDetailsQuery,
			final DistributionDetail distributionDetail,
			final DemandDetail demandDetail,
			final TransactionDetail transactionDetail)
	{
		this.parentMaterialDescriptorQuery = parentMaterialDescriptorQuery;
		this.parentDemandDetail = parentDemandDetail;

		this.matchExactStorageAttributesKey = matchExactStorageAttributesKey;
		this.orgId = orgId;
		this.type = type;
		this.businessCase = businessCase;
		this.status = status;
		this.id = id == null ? UNSPECIFIED_ID : id;
		this.parentId = parentId == null ? UNSPECIFIED_PARENT_ID : parentId;
		this.groupId = groupId;

		this.materialDescriptorQuery = materialDescriptorQuery;
		this.productionDetailsQuery = productionDetailsQuery;
		this.distributionDetail = distributionDetail;
		this.demandDetail = demandDetail;
		this.transactionDetail = transactionDetail;
	}

	/**
	 * This method ignores parent {@link #getParentProductId()}, {@link #getParentWarehouseId()},
	 * because we don't need it right now and it would mean that we had to fetch the given {@code candidate}'s parent from the repo.
	 *
	 * @param candidate
	 * @return
	 */
	public boolean matches(final Candidate candidate)
	{
		if (materialDescriptorQuery != null && materialDescriptorQuery.getDate() != null)
		{
			final boolean dateMatches;
			switch (materialDescriptorQuery.getDateOperator())
			{
				case BEFORE:
					dateMatches = candidate.getDate().getTime() < materialDescriptorQuery.getDate().getTime();
					break;
				case BEFORE_OR_AT:
					dateMatches = candidate.getDate().getTime() <= materialDescriptorQuery.getDate().getTime();
					break;
				case AT:
					dateMatches = candidate.getDate().getTime() == materialDescriptorQuery.getDate().getTime();
					break;
				case AT_OR_AFTER:
					dateMatches = candidate.getDate().getTime() >= materialDescriptorQuery.getDate().getTime();
					break;
				case AFTER:
					dateMatches = candidate.getDate().getTime() > materialDescriptorQuery.getDate().getTime();
					break;
				default:
					Check.errorIf(true, "Unexpected date operator={}; this={}", materialDescriptorQuery.getDateOperator(), this);
					return false; // won't be reached
			}
			if (!dateMatches)
			{
				return false;
			}
		}

		if (isProductIdSpecified() && !Objects.equals(materialDescriptorQuery.getProductId(), candidate.getProductId()))
		{
			return false;
		}

		if (getType() != null && !Objects.equals(getType(), candidate.getType()))
		{
			return false;
		}

		if (isWarehouseIdSpecified() && !Objects.equals(materialDescriptorQuery.getWarehouseId(), candidate.getWarehouseId()))
		{
			return false;
		}

		return true;
	}

	private boolean isProductIdSpecified()
	{
		return materialDescriptorQuery != null && materialDescriptorQuery.getProductId() > 0;
	}

	private boolean isWarehouseIdSpecified()
	{
		return materialDescriptorQuery != null && materialDescriptorQuery.getWarehouseId() > 0;
	}
}
