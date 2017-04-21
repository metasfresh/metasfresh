package de.metas.material.dispo;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.lang.impl.TableRecordReference;

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
	public enum Type
	{
		DEMAND, SUPPLY, STOCK
	};

	/**
	 * the supply type is relevant if the candidate's type is {@link Type#SUPPLY}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	public enum SubType
	{
		DISTRIBUTION, PRODUCTION, RECEIPT
	};

	@NonNull
	private final Integer orgId;

	@NonNull
	private final Integer productId;

	@NonNull
	private final Integer warehouseId;

	/**
	 * The meaning of this field might differ.
	 * It can be the absolute stock quantity at a given time (if the type is "stock") or it can be a supply, demand or stock related <b>delta</b>,
	 * i.e. one addition or removal that occurs at a particular time.
	 */
	@NonNull
	private final BigDecimal quantity;

	@NonNull
	private final Type type;

	/**
	 * Currently this can be {@code null}, unless {@link #getType()} is {@link Type#SUPPLY}.
	 */
	private final SubType subType;

	private final TableRecordReference reference;

	private final Integer id;

	private final Integer parentId;

	/**
	 * The projected date at which we expect this candidate's {@link #getQuantity()}.
	 */
	@NonNull
	private final Date date;

	/**
	 * Does not create a parent segment, even if this candidate has a parent.
	 * 
	 * @return
	 */
	public CandidatesSegment.CandidatesSegmentBuilder mkSegmentBuilder()
	{
		return CandidatesSegment.builder()
				.productId(productId)
				.warehouseId(warehouseId)
				.date(date);
	}

	public int getParentIdNotNull()
	{
		return getParentId() == null ? 0 : getParentId();

	}
}
