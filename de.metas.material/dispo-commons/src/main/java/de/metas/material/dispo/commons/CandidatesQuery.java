package de.metas.material.dispo.commons;

import java.util.Objects;

import org.adempiere.util.Check;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateSubType;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.event.commons.MaterialDescriptor;
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
@Builder
@Value
@Wither
public final class CandidatesQuery
{
	public static final ProductionDetail NO_PRODUCTION_DETAIL = ProductionDetail.builder().build();

	public static final DistributionDetail NO_DISTRIBUTION_DETAIL = DistributionDetail.builder().build();

	public static CandidatesQuery fromCandidate(@NonNull final Candidate candidate)
	{
		return CandidatesQuery.builder()
				.materialDescriptor(candidate.getMaterialDescriptor())
				.matchExactStorageAttributesKey(true)
				.demandDetail(candidate.getDemandDetail())
				.distributionDetail(candidate.getDistributionDetail())
				.groupId(candidate.getGroupId())
				.id(candidate.getId())
				.orgId(candidate.getOrgId())
				.parentId(candidate.getParentId())
				.productionDetail(candidate.getProductionDetail())
				.seqNo(candidate.getSeqNo())
				.status(candidate.getStatus())
				.subType(candidate.getSubType())
				.type(candidate.getType())
				.build();
	}

	public static CandidatesQuery fromId(final int id)
	{
		return CandidatesQuery.builder().id(id).build();
	}

	/**
	 * If set, then this query is about {@link Candidate}s that have a parent candidate which matches the given material descriptor.
	 */
	MaterialDescriptor parentMaterialDescriptor;

	int orgId;

	CandidateType type;

	/**
	 * Should be {@code null} for stock candidates.
	 */
	CandidateSubType subType;

	CandidateStatus status;

	int id;

	/**
	 * A supply candidate has a stock candidate as its parent. A demand candidate has a stock candidate as its child.
	 */
	int parentId;

	/**
	 * A supply candidate and its corresponding demand candidate are associated by a common group id.
	 */
	int groupId;

	int seqNo;

	MaterialDescriptor materialDescriptor;

	boolean matchExactStorageAttributesKey;

	/**
	 * Used for additional infos if this candidate has the sub type {@link CandidateSubType#PRODUCTION}.
	 */
	ProductionDetail productionDetail;

	/**
	 * Used for additional infos if this candidate has the sub type {@link CandidateSubType#DISTRIBUTION}.
	 */
	DistributionDetail distributionDetail;

	/**
	 * Used for additional infos if this candidate relates to particular demand
	 */
	DemandDetail demandDetail;

	TransactionDetail transactionDetail;

	public CandidatesQuery(
			final MaterialDescriptor parentMaterialDescriptor,
			final int orgId,
			final CandidateType type,
			final CandidateSubType subType,
			final CandidateStatus status,
			final int id,
			final int parentId,
			final int groupId,
			final int seqNo,
			final MaterialDescriptor materialDescriptor,
			final boolean matchExactStorageAttributesKey,
			final ProductionDetail productionDetail,
			final DistributionDetail distributionDetail,
			final DemandDetail demandDetail,
			final TransactionDetail transactionDetail)
	{
		this.parentMaterialDescriptor = parentMaterialDescriptor;
		this.matchExactStorageAttributesKey = matchExactStorageAttributesKey;
		this.orgId = orgId;
		this.type = type;
		this.subType = subType;
		this.status = status;
		this.id = id;
		this.parentId = parentId;
		this.groupId = groupId;
		this.seqNo = seqNo;

		this.materialDescriptor = materialDescriptor;
		this.productionDetail = productionDetail;
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
		if (materialDescriptor != null && materialDescriptor.getDate() != null)
		{
			final boolean dateMatches;
			switch (materialDescriptor.getDateOperator())
			{
				case AFTER:
					dateMatches = candidate.getDate().getTime() > materialDescriptor.getDate().getTime();
					break;
				case AT_OR_AFTER:
					dateMatches = candidate.getDate().getTime() >= materialDescriptor.getDate().getTime();
					break;
				case BEFORE_OR_AT:
					dateMatches = candidate.getDate().getTime() <= materialDescriptor.getDate().getTime();
					break;
				case AT:
					dateMatches = candidate.getDate().getTime() == materialDescriptor.getDate().getTime();
					break;
				default:
					Check.errorIf(true, "Unexpected date operator={}; this={}", materialDescriptor.getDateOperator(), this);
					return false; // won't be reached
			}
			if (!dateMatches)
			{
				return false;
			}
		}

		if (isProductIdSpecified() && !Objects.equals(materialDescriptor.getProductId(), candidate.getProductId()))
		{
			return false;
		}

		if (getType() != null && !Objects.equals(getType(), candidate.getType()))
		{
			return false;
		}

		if (isWarehouseIdSpecified() && !Objects.equals(materialDescriptor.getWarehouseId(), candidate.getWarehouseId()))
		{
			return false;
		}

		return true;
	}

	private boolean isProductIdSpecified()
	{
		return materialDescriptor != null && materialDescriptor.getProductId() > 0;
	}

	private boolean isWarehouseIdSpecified()
	{
		return materialDescriptor != null && materialDescriptor.getWarehouseId() > 0;
	}

	public boolean hasParentMaterialDescriptor()
	{
		return parentMaterialDescriptor != null;
	}
}
