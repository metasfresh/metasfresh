package de.metas.material.dispo;

import java.util.Date;
import java.util.Objects;

import org.adempiere.util.Check;

import de.metas.material.dispo.candidate.Candidate;
import de.metas.material.dispo.candidate.DemandDetail;
import de.metas.material.dispo.candidate.DistributionDetail;
import de.metas.material.dispo.candidate.ProductionDetail;
import de.metas.material.dispo.candidate.TransactionDetail;
import de.metas.material.event.MaterialDescriptor;
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
public class CandidatesQuery implements CandidateSpecification
{
	public enum DateOperator
	{
		/**
		 * With this operator, the segment is supposed to match records with a date <b>before</b> the segment's {@link CandidatesSegment#getDate()}.
		 */
		UNTIL,

		/**
		 * With this operator, the segment is supposed to match records with a date <b>after</b> the segment's {@link CandidatesSegment#getDate()}.
		 */
		AFTER,

		/**
		 * With this operator the segment matches records with a date <b>after</b> and also exactly <b>at</b> the segment's {@link CandidatesSegment#getDate()}.
		 */
		FROM,

		AT
	}

	public static final ProductionDetail NO_PRODUCTION_DETAIL = ProductionDetail.builder().build();
	
	public static final DistributionDetail NO_DISTRIBUTION_DETAIL = DistributionDetail.builder().build();

	public static CandidatesQuery fromCandidate(@NonNull final Candidate candidate)
	{
		return CandidatesQuery.builder()
				.dateOperator(DateOperator.AT)
				.demandDetail(candidate.getDemandDetail())
				.distributionDetail(candidate.getDistributionDetail())
				.groupId(candidate.getGroupId())
				.id(candidate.getId())
				.materialDescr(candidate.getMaterialDescr())
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
	 * This property specifies how to interpret the date.
	 */
	DateOperator dateOperator;

	/**
	 * If set, then this segment is about {@link Candidate}s that have a parent candidate which has the given product ID.
	 */
	int parentProductId;

	/**
	 * If set, then this segment is about {@link Candidate}s that have a parent candidate which has the given warehouse ID.
	 */
	int parentWarehouseId;

	int orgId;

	Type type;

	/**
	 * Should be {@code null} for stock candidates.
	 */
	SubType subType;

	Status status;

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

	MaterialDescriptor materialDescr;

	/**
	 * Used for additional infos if this candidate has the sub type {@link SubType#PRODUCTION}.
	 */
	ProductionDetail productionDetail;

	/**
	 * Used for additional infos if this candidate has the sub type {@link SubType#DISTRIBUTION}.
	 */
	DistributionDetail distributionDetail;

	/**
	 * Used for additional infos if this candidate relates to particular demand
	 */
	DemandDetail demandDetail;

	TransactionDetail transactionDetail;
	
	/**
	 * This method ignores parent {@link #getParentProductId()}, {@link #getParentWarehouseId()},
	 * because we don't need it right now and it would mean that we had to fetch the given {@code candidate}'s parent from the repo.
	 *
	 * @param candidate
	 * @return
	 */
	public boolean matches(final Candidate candidate)
	{
		final boolean dateMatches;
		switch (dateOperator)
		{
			case AFTER:
				dateMatches = candidate.getDate().getTime() > getDate().getTime();
				break;
			case FROM:
				dateMatches = candidate.getDate().getTime() >= getDate().getTime();
				break;
			case UNTIL:
				dateMatches = candidate.getDate().getTime() <= getDate().getTime();
				break;
			case AT:
				dateMatches = candidate.getDate().getTime() == getDate().getTime();
				break;
			default:
				Check.errorIf(true, "Unexpected date operator={}; this={}", dateOperator, this);
				return false; // won't be reached
		}

		if (!dateMatches)
		{
			return false;
		}

		if (isProductIdSpecified() && !Objects.equals(materialDescr.getProductId(), candidate.getProductId()))
		{
			return false;
		}

		if (getType() != null && !Objects.equals(getType(), candidate.getType()))
		{
			return false;
		}

		if (isWarehouseIdSpecitified() && !Objects.equals(materialDescr.getWarehouseId(), candidate.getWarehouseId()))
		{
			return false;
		}

		return true;
	}

	public Date getDate()
	{
		return materialDescr == null ? null : materialDescr.getDate();
	}

	private boolean isProductIdSpecified()
	{
		return materialDescr != null && materialDescr.getProductId() > 0;
	}

	private boolean isWarehouseIdSpecitified()
	{
		return materialDescr != null && materialDescr.getWarehouseId() > 0;
	}

	public int getProductId()
	{
		return materialDescr == null ? 0 : materialDescr.getProductId();
	}

	public int getWarehouseId()
	{
		return materialDescr == null ? 0 : materialDescr.getWarehouseId();
	}
}
