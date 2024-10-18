package de.metas.material.dispo.commons.candidate;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.dimension.Dimension;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.Adempiere;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

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
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class Candidate
{
	public static CandidateBuilder builderForEventDescriptor(@NonNull final EventDescriptor eventDescriptor)
	{
		return Candidate.builder()
				.clientAndOrgId(eventDescriptor.getClientAndOrgId());
	}

	public static CandidateBuilder builderForClientAndOrgId(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return Candidate.builder()
				.clientAndOrgId(clientAndOrgId);
	}

	@NonNull ClientAndOrgId clientAndOrgId;

	@NonNull @With CandidateType type;

	/**
	 * Should be {@code null} for stock candidates.
	 */
	@Nullable CandidateBusinessCase businessCase;

	@NonNull CandidateId id;

	/**
	 * A supply candidate has a stock candidate as its parent. A demand candidate has a stock candidate as its child.
	 * We have this for historic reasons.
	 * On the longer run, stock-candidates will be merged into "normal" candidates, and we won't need the parent-id anymore.
	 */
	@NonNull CandidateId parentId;

	/**
	 * The different supply candidate(s) and their corresponding demand candidate(s)
	 * that make up one business case are associated by a common group id.
	 * Note that {@link CandidateBusinessCase#PRODUCTION} and {@link CandidateBusinessCase#DISTRIBUTION} have multiple candidates in one group,
	 * Others like {@link CandidateBusinessCase#PURCHASE} have just one candidate in a group.
	 */
	@Nullable @With MaterialDispoGroupId groupId;

	@With int seqNo;

	@NonNull @With MaterialDescriptor materialDescriptor;

	@NonNull MinMaxDescriptor minMaxDescriptor;

	@Nullable @With BusinessCaseDetail businessCaseDetail;

	@Nullable DemandDetail additionalDemandDetail;

	@NonNull @With ImmutableList<TransactionDetail> transactionDetails;

	@With Dimension dimension;

	boolean simulated;

	@Builder(toBuilder = true)
	private Candidate(
			@NonNull final ClientAndOrgId clientAndOrgId,
			@NonNull final CandidateType type,
			@Nullable final CandidateBusinessCase businessCase,
			@Nullable final CandidateId id,
			@Nullable final CandidateId parentId,
			@Nullable final MaterialDispoGroupId groupId,
			final int seqNo,
			@NonNull final MaterialDescriptor materialDescriptor,
			@Nullable final MinMaxDescriptor minMaxDescriptor,
			@Nullable final BusinessCaseDetail businessCaseDetail,
			@Nullable final DemandDetail additionalDemandDetail,
			@Singular @NonNull final List<TransactionDetail> transactionDetails,
			final Dimension dimension,
			final boolean simulated)
	{
		this.clientAndOrgId = clientAndOrgId;
		this.type = type;
		this.businessCase = businessCase;

		this.id = CoalesceUtil.coalesceNotNull(id, CandidateId.NULL);
		Check.errorIf(this.id.isUnspecified(), "The given id may be null or CandidateId.NULL, but not unspecified");

		this.parentId = CoalesceUtil.coalesceNotNull(parentId, CandidateId.NULL);
		Check.errorIf(this.parentId.isUnspecified(), "The given parentId may be null or CandidateId.NULL, but not unspecified");

		this.groupId = groupId;
		this.seqNo = seqNo;

		this.materialDescriptor = materialDescriptor;
		this.minMaxDescriptor = CoalesceUtil.coalesceNotNull(minMaxDescriptor, MinMaxDescriptor.ZERO);
		this.businessCaseDetail = businessCaseDetail;
		this.additionalDemandDetail = additionalDemandDetail;

		this.transactionDetails = ImmutableList.copyOf(transactionDetails);

		if (type != CandidateType.STOCK
				&& !Adempiere.isUnitTestMode() /* TODO create unit test candidates such that they are always valid and remove this */)
		{
			validateNonStockCandidate();
		}

		this.dimension = dimension;
		this.simulated = simulated;
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
	public void validateNonStockCandidate()
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
						transactionDetails.isEmpty(),
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
					"Every element from the given parameter transactionDetails needs to have isComplete==true; transactionDetail={}; this={}",
					transactionDetail, this);
		}

		Check.errorIf((businessCase == null) != (businessCaseDetail == null),
				"The given parameters businessCase and businessCaseDetail need to be both null or both not-null; businessCase={}; businessCaseDetail={}; this={}",
				businessCase, businessCaseDetail, this);

		Check.errorIf(
				businessCase != null && !businessCase.isMatching(businessCaseDetail),
				"The given parameters businessCase and businessCaseDetail don't match; businessCase={}; businessCaseDetail={}; this={}",
				businessCase, businessCaseDetail, this);
	}

	public Candidate withNullId()
	{
		return CandidateId.isNull(this.id)
				? this
				: toBuilder().id(null).build();
	}

	public Candidate withId(@Nullable final CandidateId id)
	{
		return CandidateId.equals(this.id, id)
				? this
				: toBuilder().id(id).build();
	}

	public Candidate withParentId(@Nullable final CandidateId parentId)
	{
		return CandidateId.equals(this.parentId, parentId)
				? this
				: toBuilder().parentId(parentId).build();
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

	public MaterialDispoGroupId getGroupIdNotNull()
	{
		return Check.assumeNotNull(getGroupId(), "candidate has groupId set: {}", this);
	}

	public Instant getDate() {return getMaterialDescriptor().getDate();}

	public ProductId getProductId() {return ProductId.ofRepoId(getMaterialDescriptor().getProductId());}

	public AttributeSetInstanceId getAttributeSetInstanceId() {return AttributeSetInstanceId.ofRepoIdOrNone(getMaterialDescriptor().getAttributeSetInstanceId());}

	public WarehouseId getWarehouseId() {return getMaterialDescriptor().getWarehouseId();}

	public BPartnerId getCustomerId() {return getMaterialDescriptor().getCustomerId();}

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

	@Nullable
	public OrderAndLineId getSalesOrderLineId()
	{
		final DemandDetail demandDetail = getDemandDetail();
		if (demandDetail == null)
		{
			return null;
		}

		return OrderAndLineId.ofRepoIdsOrNull(demandDetail.getOrderId(), demandDetail.getOrderLineId());
	}

	@NonNull
	public BusinessCaseDetail getBusinessCaseDetailNotNull()
	{
		return Check.assumeNotNull(getBusinessCaseDetail(), "businessCaseDetail is not null: {}", this);
	}

	public <T extends BusinessCaseDetail> Optional<T> getBusinessCaseDetail(@NonNull final Class<T> type)
	{
		return type.isInstance(businessCaseDetail) ? Optional.of(type.cast(businessCaseDetail)) : Optional.empty();
	}

	public <T extends BusinessCaseDetail> T getBusinessCaseDetailNotNull(@NonNull final Class<T> type)
	{
		if (type.isInstance(businessCaseDetail))
		{
			return type.cast(businessCaseDetail);
		}
		else
		{
			throw new AdempiereException("businessCaseDetail is not matching " + type.getSimpleName() + ": " + this);
		}
	}

	public <T extends BusinessCaseDetail> Candidate withBusinessCaseDetail(@NonNull final Class<T> type, @NonNull UnaryOperator<T> mapper)
	{
		final T businessCaseDetail = getBusinessCaseDetailNotNull(type);
		final T businessCaseDetailChanged = mapper.apply(businessCaseDetail);
		if (Objects.equals(businessCaseDetail, businessCaseDetailChanged))
		{
			return this;
		}

		return withBusinessCaseDetail(businessCaseDetailChanged);
	}

	@Nullable
	public String getTraceId()
	{
		final DemandDetail demandDetail = getDemandDetail();
		return demandDetail != null ? demandDetail.getTraceId() : null;
	}
}
