package de.metas.manufacturing.dispo;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.lang.ITableRecordReference;

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
	public enum SupplyType
	{
		DISTRIBUTION, PRODUCTION, RECEIPT
	};

	@NonNull
	private final Integer productId;

	/**
	 * The particular locator within a warehouse might remain unspecified for a candidate.
	 */
	private final Integer locatorId;

	@NonNull
	private final Integer warehouseId;

	/**
	 * The projected overall quantity which we expect at the time of {@link #getDate()}.
	 */
	@NonNull
	private final BigDecimal quantity;

	@NonNull
	private final Type type;

	/**
	 * Can be {@code null}, unless {@link #getType()} is {@link Type#SUPPLY}.
	 */
	private final SupplyType supplyType;

	private final ITableRecordReference referencedRecord;

	private final Integer id;

	private final Integer parentId;

	/**
	 * The projected date at which we expect this candidate's {@link #getQuantity()}.
	 */
	@NonNull
	private final Date date;

	public int getLocatorIdNotNull()
	{
		return locatorId == null ? 0 : locatorId;
	}

	public CandidatesSegment mkSegment()
	{
		return CandidatesSegment.builder()
				.locatorId(locatorId)
				.productId(productId)
				.projectedDate(date)
				.warehouseId(warehouseId)
				.build();
	}
}
