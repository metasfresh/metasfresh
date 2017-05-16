package de.metas.material.event.ddorder;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-mrp
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
@AllArgsConstructor // used by jackson when it deserializes a string
public class DDOrderLine
{
	private final int salesOrderLineId;

	@NonNull
	private final Integer productId;

	
	private final int attributeSetInstanceId;;
	
	@NonNull
	private final BigDecimal qty;

	/**
	 * {@link DDOrder#getDatePromised()} minus this number of days tells us when the distribution for this particular line needs to start
	 */
	@NonNull
	private final Integer durationDays;
	
	@NonNull
	private final Integer networkDistributionLineId;
	
	private final int ddOrderLineId;
}
