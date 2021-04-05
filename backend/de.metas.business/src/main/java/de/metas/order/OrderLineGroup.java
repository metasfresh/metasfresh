/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.order;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class OrderLineGroup
{
	String groupKey;
	boolean isGroupMainItem;

	private OrderLineGroup(@NonNull final String groupKey, final boolean isGroupMainItem)
	{
		this.groupKey = groupKey;
		this.isGroupMainItem = isGroupMainItem;
	}

	@NonNull
	public static OrderLineGroup of(@NonNull final String groupKey, final boolean isGroupMainItem)
	{
		return new OrderLineGroup(groupKey, isGroupMainItem);
	}

	@Nullable
	public static OrderLineGroup ofOrNull(@Nullable final String groupKey, final boolean isGroupMainItem)
	{
		return Check.isEmpty(groupKey) ? null : of(groupKey, isGroupMainItem);
	}

}
