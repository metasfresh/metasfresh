package de.metas.ui.web.vaadin.window.prototype.order.datasource.sql;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * metasfresh-webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ModelDataSourceQuery
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final Object recordId;
	private final Object parentLinkId;
	
	private ModelDataSourceQuery(final Builder builder)
	{
		super();
		this.recordId = builder.recordId;
		this.parentLinkId = builder.parentLinkId;		
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("recordId", recordId)
				.add("parentLinkId", parentLinkId)
				.toString();
	}
	
	public Object getParentLinkId()
	{
		return parentLinkId;
	}
	
	public static final class Builder
	{
		private Object parentLinkId;
		private Object recordId;

		private Builder()
		{
			super();
		}
		
		public ModelDataSourceQuery build()
		{
			return new ModelDataSourceQuery(this);
		}
		
		public Builder setRecordId(Object recordId)
		{
			this.recordId = recordId;
			return this;
		}
		
		public Builder setParentLinkId(final Object parentLinkId)
		{
			this.parentLinkId = parentLinkId;
			return this;
		}
	}
}
