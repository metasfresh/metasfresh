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

import de.metas.order.compensationGroup.GroupCompensationOrderBy;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class OrderLineGroup
{
	@NonNull
	String groupKey;

	boolean isGroupMainItem;

	boolean isGroupingError;

	@Nullable
	String groupingErrorMessage;

	@Nullable
	Percent discount;

	@Nullable
	GroupCompensationOrderBy groupCompensationOrderBy;

	@Builder
	private OrderLineGroup(
			@NonNull final String groupKey,
			final boolean isGroupMainItem,
			final boolean isGroupingError,
			@Nullable final String groupingErrorMessage,
			@Nullable final Percent discount,
			@Nullable final GroupCompensationOrderBy groupCompensationOrderBy)
	{
		this.groupKey = groupKey;
		this.isGroupMainItem = isGroupMainItem;
		this.isGroupingError = isGroupingError;
		this.groupingErrorMessage = groupingErrorMessage;
		this.discount = discount;
		this.groupCompensationOrderBy = groupCompensationOrderBy;
	}
}
