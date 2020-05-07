package de.metas.material.dispo.commons.candidate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.util.Util;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
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

@Value
@Builder(toBuilder = true)
@Wither
public class Candidate
{
	public static CandidateBuilder builderForEventDescr(@NonNull final EventDescriptor eventDescr)
	{
		return Candidate.builder()
				.clientId(eventDescr.getClientId())
				.orgId(eventDescr.getOrgId());
	}

	int clientId;

	int orgId;

	@NonNull
	CandidateType type;

	/**
	 * Should be {@code null} for stock candidates.
	 */
	CandidateBusinessCase businessCase;

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

	@NonNull
	MaterialDescriptor materialDescriptor;

	BusinessCaseDetail businessCaseDetail;

	DemandDetail additionalDemandDetail;

	@Singular
	List<TransactionDetail> transactionDetails;

	public Candidate withAddedQuantity(@NonNull final BigDecimal addedQuantity)
	{
		return withQuantity(getQuantity().add(addedQuantity));
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

	public Candidate withDate(@NonNull final Date date)
	{
		return withMaterialDescriptor(materialDescriptor.withDate(date));
	}

	public Candidate withWarehouseId(final int warehouseId)
	{
		return withMaterialDescriptor(materialDescriptor.withWarehouseId(warehouseId));
	}

	public int getEffectiveGroupId()
	{
		if (type == CandidateType.STOCK)
		{
			return 0;
		}
		if (groupId > 0)
		{
			return groupId;
		}
		return id;
	}

	public Date getDate()
	{
		return materialDescriptor.getDate();
	}

	public int getProductId()
	{
		return materialDescriptor.getProductId();
	}

	public int getWarehouseId()
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
		return Util.coalesce(DemandDetail.castOrNull(businessCaseDetail), additionalDemandDetail);
	}

	public BigDecimal getPlannedQty()
	{
		if (businessCaseDetail == null)
		{
			return BigDecimal.ZERO;
		}
		return businessCaseDetail.getPlannedQty();
	}

	private Candidate(final int clientId, final int orgId,
			@NonNull final CandidateType type,
			final CandidateBusinessCase businessCase,
			final CandidateStatus status,
			final int id,
			final int parentId,
			final int groupId,
			final int seqNo,
			@NonNull final MaterialDescriptor materialDescriptor,
			final BusinessCaseDetail businessCaseDetail,
			final DemandDetail additionalDemandDetail,
			final List<TransactionDetail> transactionDetails)
	{
		this.clientId = clientId;
		this.orgId = orgId;
		this.type = type;
		this.businessCase = businessCase;
		this.status = status;
		this.id = id;
		this.parentId = parentId;
		this.groupId = groupId;
		this.seqNo = seqNo;

		this.materialDescriptor = materialDescriptor;

		this.businessCaseDetail = businessCaseDetail;
		this.additionalDemandDetail = additionalDemandDetail;

		this.transactionDetails = transactionDetails;
	}

	/** we don't call this from the constructor, because some tests don't need a "valid" candidate to get particular aspects. */
	public void validate()
	{
		switch (type)
		{
			case DEMAND:
			case STOCK_UP:
			case SUPPLY:
				Check.errorIf(
						businessCaseDetail == null,
						"If type={}, then the given businessCaseDetail may not be null; this={}",
						type, this);
				break;
			case UNRELATED_INCREASE:
			case UNRELATED_DECREASE:
				Check.errorIf(
						transactionDetails == null || transactionDetails.isEmpty(),
						"If type={}, then the given transactionDetails may not be null or empty; this={}",
						type, this);
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

		Check.errorIf((businessCase != null) != (businessCaseDetail != null),
				"The given paramters businessCase and businessCaseDetail need to be both null or both not-null; businessCase={}; businessCaseDetail={}; this={}",
				businessCase, businessCaseDetail, this);

		Check.errorIf(
				businessCase != null && !businessCase.getDetailClass().isAssignableFrom(businessCaseDetail.getClass()),
				"The given paramters businessCase and businessCaseDetail don't match; businessCase={}; businessCaseDetail={}; this={}",
				businessCase, businessCaseDetail, this);
	}
}
