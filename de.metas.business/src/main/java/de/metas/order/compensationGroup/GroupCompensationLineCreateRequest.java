package de.metas.order.compensationGroup;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
@Builder
public final class GroupCompensationLineCreateRequest
{
	private final int productId;
	private final int uomId;

	@NonNull
	private final GroupCompensationType type;
	@NonNull
	private final GroupCompensationAmtType amtType;

	private final BigDecimal percentage;
	private final BigDecimal qty;
	private final BigDecimal price;
	
	private final int groupTemplateLineId;
}
