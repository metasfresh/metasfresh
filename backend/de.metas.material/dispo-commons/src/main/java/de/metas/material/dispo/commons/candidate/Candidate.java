package de.metas.material.dispo.commons.candidate;

import de.metas.common.util.CoalesceUtil;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.With;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.Adempiere;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

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

@With
@Value
@EqualsAndHashCode(doNotUseGetters = true)
public class Candidate
{
	public static CandidateBuilder builderForEventDescr(@NonNull final EventDescriptor eventDescr)
	{
		return Candidate.builder()
				.clientAndOrgId(eventDescr.getClientAndOrgId());
	}

	public static CandidateBuilder builderForClientAndOrgId(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return Candidate.builder()
				.clientAndOrgId(clientAndOrgId);
	}

	ClientAndOrgId clientAndOrgId;

	CandidateType type;

	/**
	 * Should be {@code null} for stock candidates.
	 */
	CandidateBusinessCase businessCase;

	CandidateId id;

	/**
	 * A supply candidate has a stock candidate as its parent. A demand candidate has a stock candidate as its child.
	 * We have this for historic reasons.
	 * On the longer run, stock-candidates will be merged into "normal" candidates and we won't need the parent-id anymore.
	 */
	CandidateId parentId;

	/**
	 * The different supply candidate(s) and their corresponding demand candidate(s)
	 * that make up one business case are associated by a common group id.
	 * Note that {@link CandidateBusinessCase#PRODUCTION} and {@link CandidateBusinessCase#DISTRIBUTION} have multiple candidates in one group,
	 * Others like {@link CandidateBusinessCase#PURCHASE} have just one candidate in a group.
	 */
	MaterialDispoGroupId groupId;

	int seqNo;

	MaterialDescriptor materialDescriptor;

	MinMaxDescriptor minMaxDescriptor;

	BusinessCaseDetail businessCaseDetail;

	DemandDetail additionalDemandDetail;

	List<TransactionDetail> transactionDetails;

	@Builder(toBuilder = true)
	private Candidate(
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final CandidateType type,
			final CandidateBusinessCase businessCase,
			final CandidateId id,
			final CandidateId parentId,
			final MaterialDispoGroupId groupId,
			final int seqNo,
			@NonNull final MaterialDescriptor materialDescriptor,
			final MinMaxDescriptor minMaxDescriptor,
			final BusinessCaseDetail businessCaseDetail,
			final DemandDetail additionalDemandDetail,
			@Singular @NonNull final List<TransactionDetail> transactionDetails)
	{
		this.clientAndOrgId = clientAndOrgId;
		this.type = type;
		this.businessCase = businessCase;

		this.id = CoalesceUtil.coalesce(id, CandidateId.NULL);
		Check.errorIf(this.id.isUnspecified(), "The given id may be null or CandidateId.NULL, but not unspecified");

		this.parentId = CoalesceUtil.coalesce(parentId, CandidateId.NULL);
		Check.errorIf(this.parentId.isUnspecified(), "The given parentId may be null or CandidateId.NULL, but not unspecified");

		this.groupId = groupId;
		this.seqNo = seqNo;

		this.materialDescriptor = materialDescriptor;
		this.minMaxDescriptor = CoalesceUtil.coalesce(minMaxDescriptor, MinMaxDescriptor.ZERO);
		this.businessCaseDetail = businessCaseDetail;
		this.additionalDemandDetail = additionalDemandDetail;

		this.transactionDetails = transactionDetails;

		if (type != CandidateType.STOCK
				&& !Adempiere.isUnitTestMode() /* TODO create unit test candidates such that they are always valid and remove this */)
		{
			validateNonStockCandidate();
		}
	}

	public static class CandidateBuilder
	{
		public CandidateBuilder quantity(final BigDecimal quantity)
		{
			Check.assumeNotNull(materialDescriptor, "Parameter materialDescriptor is not null");
			return materialDescriptor(materialDescriptor.withQuantity(quantity));
		}
	}

	// TODO always validate on construction, then make this method private
	public Candidate validateNonStockCandidate()
	{
		switch (type)
		{
			case DEMAND:
			case STOCK_UP:
				Check.errorIf(
						businessCaseDetail == null,
						"If type={}, then the given businessCaseDetail may not be null; this={}",
						type, this);
				break;
			case SUPPLY: // supply candidates can be created without businessCaseDetail if the request was made but no response from the planner came in yet
			case INVENTORY_UP:
			case INVENTORY_DOWN:
				break;
			case UNEXPECTED_INCREASE:
			case UNEXPECTED_DECREASE:
				Check.errorIf(
						transactionDetails == null || transactionDetails.isEmpty(),
						"If type={}, then the given transactionDetails may not be null or empty; this={}",
						type, this);
				break;
			case ATTRIBUTES_CHANGED_FROM:
			case ATTRIBUTES_CHANGED_TO:
				break;
			default:
				Check.errorIf(true, "Unexpected candidateType={}; this={}", type, this);
		}

		for (final TransactionDetail transactionDetail : transactionDetails)
		{
			Check.errorIf(
					!transactionDetail.isComplete(),
					"Every element from the given parameter transactionDetails needs to have iscomplete==true; transactionDetail={}; this={}",
					transactionDetail, this);
		}

		Check.errorIf((businessCase == null) != (businessCaseDetail == null),
				"The given paramters businessCase and businessCaseDetail need to be both null or both not-null; businessCase={}; businessCaseDetail={}; this={}",
				businessCase, businessCaseDetail, this);

		Check.errorIf(
				businessCase != null && !businessCase.getDetailClass().isAssignableFrom(businessCaseDetail.getClass()),
				"The given paramters businessCase and businessCaseDetail don't match; businessCase={}; businessCaseDetail={}; this={}",
				businessCase, businessCaseDetail, this);

		return this;
	}

	public OrgId getOrgId()
	{
		return getClientAndOrgId().getOrgId();
	}

	public Candidate withNegatedQuantity()
	{
		return withQuantity(getQuantity().negate());
	}

	public BigDecimal getQuantity()
	{
		return materialDescriptor.getQuantity();
	}

	public Candidate withQuantity(@NonNull final BigDecimal quantity)
	{
		return withMaterialDescriptor(materialDescriptor.withQuantity(quantity));
	}

	public Candidate withDate(@NonNull final Instant date)
	{
		return withMaterialDescriptor(materialDescriptor.withDate(date));
	}

	public Candidate withWarehouseId(final WarehouseId warehouseId)
	{
		return withMaterialDescriptor(materialDescriptor.withWarehouseId(warehouseId));
	}

	@Nullable
	public MaterialDispoGroupId getEffectiveGroupId()
	{
		if (type == CandidateType.STOCK)
		{
			return null;
		}
		else if (groupId != null)
		{
			return groupId;
		}
		else
		{
			return MaterialDispoGroupId.ofIdOrNull(id);
		}
	}

	public Instant getDate()
	{
		return materialDescriptor.getDate();
	}

	public int getProductId()
	{
		return materialDescriptor.getProductId();
	}

	public WarehouseId getWarehouseId()
	{
		return materialDescriptor.getWarehouseId();
	}

	public BigDecimal computeActualQty()
	{
		return getTransactionDetails()
				.stream()
				.map(TransactionDetail::getQuantity)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public DemandDetail getDemandDetail()
	{
		return CoalesceUtil.coalesce(DemandDetail.castOrNull(businessCaseDetail), additionalDemandDetail);
	}

	public BigDecimal getBusinessCaseDetailQty()
	{
		if (businessCaseDetail == null)
		{
			return BigDecimal.ZERO;
		}
		return businessCaseDetail.getQty();
	}
}
