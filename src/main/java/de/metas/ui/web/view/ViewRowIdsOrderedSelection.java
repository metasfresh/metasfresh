package de.metas.ui.web.view;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;

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

@Immutable
@EqualsAndHashCode
public final class ViewRowIdsOrderedSelection
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final ViewId viewId;
	private final long size;
	private final ImmutableList<DocumentQueryOrderBy> orderBys;

	private final int queryLimit;
	private final boolean queryLimitHit;

	private ViewRowIdsOrderedSelection(final Builder builder)
	{
		super();
		viewId = builder.getViewId();
		size = builder.getSize();
		orderBys = builder.getOrderBys();

		queryLimit = builder.getQueryLimit();
		queryLimitHit = builder.isQueryLimitHit();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("viewId", viewId)
				.add("size", size)
				.add("orderBys", orderBys.isEmpty() ? null : orderBys)
				.toString();
	}
	
	public Builder toBuilder()
	{
		return builder()
				.setViewId(viewId)
				.setSize(size)
				.setOrderBys(orderBys)
				.setQueryLimit(queryLimit);
	}

	public ViewId getViewId()
	{
		return viewId;
	}
	
	public WindowId getWindowId()
	{
		return viewId.getWindowId();
	}
	
	public String getSelectionId()
	{
		return viewId.getViewId();
	}

	public long getSize()
	{
		return size;
	}

	public ImmutableList<DocumentQueryOrderBy> getOrderBys()
	{
		return orderBys;
	}
	
	public int getQueryLimit()
	{
		return queryLimit;
	}
	
	public boolean isQueryLimitHit()
	{
		return queryLimitHit;
	}

	public static final class Builder
	{
		private ViewId viewId;
		private long size = -1;
		private List<DocumentQueryOrderBy> orderBys;

		private int queryLimit;

		private Builder()
		{
		}

		public ViewRowIdsOrderedSelection build()
		{
			return new ViewRowIdsOrderedSelection(this);
		}

		private ViewId getViewId()
		{
			Check.assumeNotNull(viewId, "Parameter viewId is not null");
			return viewId;
		}

		public Builder setViewId(final ViewId viewId)
		{
			this.viewId = viewId;
			return this;
		}

		private long getSize()
		{
			return size;
		}

		public Builder setSize(final long size)
		{
			this.size = size;
			return this;
		}

		private ImmutableList<DocumentQueryOrderBy> getOrderBys()
		{
			return orderBys == null ? ImmutableList.of() : ImmutableList.copyOf(orderBys);
		}

		public Builder setOrderBys(final List<DocumentQueryOrderBy> orderBys)
		{
			this.orderBys = orderBys;
			return this;
		}

		public Builder setQueryLimit(final int queryLimit)
		{
			this.queryLimit = queryLimit;
			return this;
		}

		private int getQueryLimit()
		{
			return queryLimit;
		}

		private boolean isQueryLimitHit()
		{
			return queryLimit > 0
					&& size > 0
					&& size >= queryLimit;
		}
	}
}
