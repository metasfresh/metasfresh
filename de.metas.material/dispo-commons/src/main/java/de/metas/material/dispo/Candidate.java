package de.metas.material.dispo;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
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

@Data
@Builder
@Wither
public class Candidate
{
	/**
	 * Please keep in sync with the values of {@link X_MD_Candidate#MD_CANDIDATE_TYPE_AD_Reference_ID}
	 */
	public enum Type
	{
		DEMAND, SUPPLY, STOCK, STOCK_UP
	};

	/**
	 * Please keep in sync with the values of {@link X_MD_Candidate#MD_CANDIDATE_SUBTYPE_AD_Reference_ID}
	 */
	public enum SubType
	{
		DISTRIBUTION, PRODUCTION, RECEIPT, SHIPMENT, FORECAST
	};

	/**
	 * Please keep in sync with the values of {@link X_MD_Candidate#MD_CANDIDATE_STATUS_AD_Reference_ID}
	 */
	public enum Status
	{
		doc_planned, doc_created, doc_completed, doc_closed, unexpected
	}

	@NonNull
	private final Integer clientId;

	@NonNull
	private final Integer orgId;

	@NonNull
	private final MaterialDescriptor materialDescr;

	@NonNull
	private final Type type;

	/**
	 * Should be {@code null} for stock candidates.
	 */
	private final SubType subType;

	private final Status status;

	private final TableRecordReference reference;

	private final Integer id;

	/**
	 * A supply candidate has a stock candidate as its parent. A demand candidate has a stock candidate as its child.
	 */
	private final Integer parentId;

	/**
	 * A supply candidate and its corresponding demand candidate are associated by a common group id.
	 */
	private final Integer groupId;

	private final Integer seqNo;

	/**
	 * Used for additional infos if this candidate has the sub type {@link SubType#PRODUCTION}.
	 */
	private final ProductionCandidateDetail productionDetail;

	/**
	 * Used for additional infos if this candidate has the sub type {@link SubType#DISTRIBUTION}.
	 */
	private final DistributionCandidateDetail distributionDetail;

	/**
	 * Used for additional infos if this candidate relates to particular demand
	 */
	private final DemandCandidateDetail demandDetail;

	public static CandidateBuilder builderForEventDescr(@NonNull final EventDescr eventDescr)
	{
		return Candidate.builder()
				.clientId(eventDescr.getClientId())
				.orgId(eventDescr.getOrgId());
	}

	/**
	 * Does not create a parent segment, even if this candidate has a parent.
	 *
	 * @return
	 */
	public CandidatesSegment.CandidatesSegmentBuilder mkSegmentBuilder()
	{
		return CandidatesSegment.builder()
				.productId(materialDescr.getProductId())
				.warehouseId(materialDescr.getWarehouseId())
				.date(materialDescr.getDate());
	}

	public int getParentIdNotNull()
	{
		return getParentId() == null ? 0 : getParentId();
	}

	public BigDecimal getQuantity()
	{
		return materialDescr.getQuantity();
	}

	public Candidate withQuantity(@NonNull final BigDecimal qtyDelta)
	{
		return this.withMaterialDescr(materialDescr.withQuantity(qtyDelta));
	}

	public Candidate withDate(@NonNull final Date date)
	{
		return this.withMaterialDescr(materialDescr.withDate(date));
	}

	public Candidate withWarehouseId(final int warehouseId)
	{
		return this.withMaterialDescr(materialDescr.withWarehouseId(warehouseId));
	}

	public int getEffectiveGroupId()
	{
		if (type == Type.STOCK)
		{
			return 0;
		}
		if (groupId != null && groupId > 0)
		{
			return groupId;
		}
		return id == null ? 0 : id;
	}

	public Date getDate()
	{
		return materialDescr.getDate();
	}

	public int getProductId()
	{
		return materialDescr.getProductId();
	}

	public int getWarehouseId()
	{
		return materialDescr.getWarehouseId();
	}

}
