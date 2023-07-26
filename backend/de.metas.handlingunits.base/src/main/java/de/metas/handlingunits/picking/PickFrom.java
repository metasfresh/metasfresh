package de.metas.handlingunits.picking;

import javax.annotation.Nullable;

import de.metas.handlingunits.HuId;
import org.eevolution.api.PPOrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class PickFrom
{
	public static PickFrom ofHuId(@NonNull final HuId huId)
	{
		return builder().huId(huId).build();
	}

	public static PickFrom ofPickingOrderId(@NonNull final PPOrderId pickingOrderId)
	{
		return builder().pickingOrderId(pickingOrderId).build();
	}

	@Nullable
	HuId huId;

	@Nullable
	PPOrderId pickingOrderId;

	@Builder
	private PickFrom(
			@Nullable HuId huId,
			@Nullable PPOrderId pickingOrderId)
	{
		this.pickingOrderId = pickingOrderId;
		this.huId = huId;
	}

	public boolean isPickFromHU()
	{
		return getHuId() != null;
	}

	public boolean isPickFromPickingOrder()
	{
		return getPickingOrderId() != null;
	}
}
