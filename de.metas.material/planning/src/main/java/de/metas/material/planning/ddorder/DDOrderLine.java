package de.metas.material.planning.ddorder;

import java.math.BigDecimal;

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
public class DDOrderLine
{

	private final Integer demandBPartnerId;

	private final Integer salesOrderLineId;

	@NonNull
	private final Integer fromLocatorId;

	@NonNull
	private final Integer toLocatorId;

	@NonNull
	private final Integer productId;

	@NonNull
	private final BigDecimal qty;

	@NonNull
	private final Boolean allowPush;

	@NonNull
	private final Boolean keepTargetPlant;

	/**
	 * {@link DDOrder#getDatePromised()} minus this number of days tells us when the distribution for this particular line needs to start
	 */
	@NonNull
	private final Integer durationDays;

}
