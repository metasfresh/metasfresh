package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-planning
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
 * Not needed, because it can be taken directly from the parent ppOrder:
 * <ul>
 * <li>orgId</li>
 * <li>warehouseId</li>
 * <li>locatorId</li>
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PPOrderLine
{
	/**
	 * Can contain the {@code PP_Order_BOMLine_ID} of the production order document line this is all about, but also note that there might not yet exist one.
	 */
	int ppOrderLineId;

	PPOrderLineData ppOrderLineData;

	@JsonCreator
	@Builder(toBuilder = true)
	public PPOrderLine(
			@JsonProperty("ppOrderLineId") final int ppOrderLineId,
			@JsonProperty("ppOrderLineData") @NonNull final PPOrderLineData ppOrderLineData)
	{
		this.ppOrderLineId = ppOrderLineId;
		this.ppOrderLineData = ppOrderLineData;
	}
}
