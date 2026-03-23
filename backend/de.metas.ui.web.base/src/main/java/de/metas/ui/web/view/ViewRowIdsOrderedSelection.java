package de.metas.ui.web.view;

import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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
public class ViewRowIdsOrderedSelection
{
	ViewId viewId;
	long size;
	DocumentQueryOrderByList orderBys;

	QueryLimit queryLimit;
	boolean queryLimitHit;

	@Nullable EmptyReason emptyReason;

	@Builder(toBuilder = true)
	private ViewRowIdsOrderedSelection(
			@NonNull final ViewId viewId,
			final long size,
			@Nullable final DocumentQueryOrderByList orderBys,
			@Nullable final QueryLimit queryLimit,
			@Nullable final EmptyReason emptyReason)
	{
		this.viewId = viewId;
		this.size = size;
		this.orderBys = orderBys != null ? orderBys : DocumentQueryOrderByList.EMPTY;
		this.queryLimit = queryLimit != null ? queryLimit : QueryLimit.NO_LIMIT;
		this.emptyReason = emptyReason;

		this.queryLimitHit = this.queryLimit.isLimited()
				&& size > 0
				&& size >= this.queryLimit.toInt();
	}

	public static boolean equals(@Nullable final ViewRowIdsOrderedSelection s1, @Nullable final ViewRowIdsOrderedSelection s2)
	{
		return Objects.equals(s1, s2);
	}

	public WindowId getWindowId()
	{
		return getViewId().getWindowId();
	}

	public String getSelectionId()
	{
		return getViewId().getViewId();
	}

	public ViewRowIdsOrderedSelection withSize(final int size)
	{
		return this.size == size
				? this
				: toBuilder().size(size).build();
	}
}
