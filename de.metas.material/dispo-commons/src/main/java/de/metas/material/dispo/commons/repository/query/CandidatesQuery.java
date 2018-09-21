package de.metas.material.dispo.commons.repository.query;

import java.util.List;
import java.util.Objects;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
	/**
	 * This query matches no candidate
	 */
	public static final CandidatesQuery FALSE = CandidatesQuery.fromId(CandidateId.ofRepoId(Integer.MAX_VALUE - 3));

	public static CandidatesQuery fromCandidate(
			@NonNull final Candidate candidate,
			final boolean includeParentId)
	{
		if (!candidate.getId().isNull())
		{
			return CandidatesQuery.fromId(candidate.getId());
		}

		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery
				.ofProductionDetailOrNull(ProductionDetail.castOrNull(candidate.getBusinessCaseDetail()));

		final DistributionDetailsQuery distributionDetailsQuery = DistributionDetailsQuery
				.ofDistributionDetailOrNull(DistributionDetail.castOrNull(candidate.getBusinessCaseDetail()));

		final PurchaseDetailsQuery purchaseDetailsQuery = PurchaseDetailsQuery
				.ofPurchaseDetailOrNull(PurchaseDetail.castOrNull(candidate.getBusinessCaseDetail()));

		final DemandDetailsQuery demandDetailsQuery = DemandDetailsQuery
				.ofDemandDetailOrNull(DemandDetail.castOrNull(candidate.getBusinessCaseDetail()));

		final CandidatesQueryBuilder builder = CandidatesQuery.builder()
				.materialDescriptorQuery(MaterialDescriptorQuery.forDescriptor(candidate.getMaterialDescriptor()))
				.matchExactStorageAttributesKey(true)
				.demandDetailsQuery(demandDetailsQuery)
				.distributionDetailsQuery(distributionDetailsQuery)
				.productionDetailsQuery(productionDetailsQuery)
				.purchaseDetailsQuery(purchaseDetailsQuery)
				.transactionDetails(candidate.getTransactionDetails())
				.groupId(candidate.getGroupId())
				.orgId(candidate.getOrgId())
				.status(candidate.getStatus())
				.businessCase(candidate.getBusinessCase())
				.type(candidate.getType());

		if (includeParentId)
		{
			builder.parentId(candidate.getParentId());
		}
		else
		{
			builder.parentId(CandidateId.UNSPECIFIED);
		}
		return builder.build();
	}

	public static CandidatesQuery fromId(final CandidateId id)
	{
		return CandidatesQuery.builder()
				.id(id)
				.parentId(CandidateId.UNSPECIFIED)
				.build();
	}

	/**
	 * If set, then this query is about {@link Candidate}s that have a parent candidate which matches the given material descriptor.
	 */
	MaterialDescriptorQuery parentMaterialDescriptorQuery;

	DemandDetailsQuery parentDemandDetailsQuery;

	int orgId;

	CandidateType type;

	/**
	 * Should be {@code null} for stock candidates.
	 */
	CandidateBusinessCase businessCase;

	CandidateStatus status;

	CandidateId id;

	/**
	 * A supply candidate has a stock candidate as its parent. A demand candidate has a stock candidate as its child.<br>
	 * -1 means {@link #PARENT_ID_UNSPECIFIED}
	 */
	CandidateId parentId;

	/**
	 * A supply candidate and its corresponding demand candidate are associated by a common group id.
	 */
	int groupId;

	MaterialDescriptorQuery materialDescriptorQuery;

	boolean matchExactStorageAttributesKey;

	/**
	 * Used for additional infos if this candidate has the business case {@link CandidateBusinessCase#PRODUCTION}.
	 */
	ProductionDetailsQuery productionDetailsQuery;

	/**
	 * Used for additional infos if this candidate has the business case {@link CandidateBusinessCase#DISTRIBUTION}.
	 */
	DistributionDetailsQuery distributionDetailsQuery;

	PurchaseDetailsQuery purchaseDetailsQuery;

	/**
	 * Used for additional infos if this candidate relates to particular demand
	 */
	DemandDetailsQuery demandDetailsQuery;

	/**
	 * If multiple transactionDetails are specified here, then a matching candidate needs to have matching transactionDetails for all of them.
	 */
	List<TransactionDetail> transactionDetails;

	@Builder
	public CandidatesQuery(
			final MaterialDescriptorQuery parentMaterialDescriptorQuery,
			final DemandDetailsQuery parentDemandDetailsQuery,
			final int orgId,
			final CandidateType type,
			final CandidateBusinessCase businessCase,
			final CandidateStatus status,
			final CandidateId id,
			final CandidateId parentId,
			final int groupId,
			final MaterialDescriptorQuery materialDescriptorQuery,
			final boolean matchExactStorageAttributesKey,
			final ProductionDetailsQuery productionDetailsQuery,
			final DistributionDetailsQuery distributionDetailsQuery,
			final PurchaseDetailsQuery purchaseDetailsQuery,
			final DemandDetailsQuery demandDetailsQuery,
			@Singular final List<TransactionDetail> transactionDetails)
	{
		this.parentMaterialDescriptorQuery = parentMaterialDescriptorQuery;
		this.parentDemandDetailsQuery = parentDemandDetailsQuery;

		this.matchExactStorageAttributesKey = matchExactStorageAttributesKey;
		this.orgId = orgId;
		this.type = type;
		this.businessCase = businessCase;
		this.status = status;
		this.id = id == null ? CandidateId.UNSPECIFIED : id;
		this.parentId = parentId == null ? CandidateId.UNSPECIFIED : parentId;
		this.groupId = groupId;

		this.materialDescriptorQuery = materialDescriptorQuery;
		this.productionDetailsQuery = productionDetailsQuery;
		this.distributionDetailsQuery = distributionDetailsQuery;
		this.purchaseDetailsQuery = purchaseDetailsQuery;

		this.demandDetailsQuery = demandDetailsQuery;
		this.transactionDetails = transactionDetails;
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
