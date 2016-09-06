package de.metas.ui.web.window.model;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Immutable
public final class DocumentQueryFilter
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String fieldName;
	private final boolean range;
	private final Object value;
	private final Object valueTo;

	private DocumentQueryFilter(final Builder builder)
	{
		super();

		fieldName = builder.fieldName;
		Check.assumeNotNull(fieldName, "Parameter fieldName is not null");

		range = builder.range;
		value = builder.value;
		valueTo = builder.valueTo;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("fieldName", fieldName)
				.add("range", range)
				.add("value", value)
				.add("valueTo", valueTo)
				.toString();
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public boolean isRange()
	{
		return range;
	}

	public Object getValue()
	{
		return value;
	}

	public Object getValueTo()
	{
		return valueTo;
	}

	public static final class Builder
	{
		private String fieldName;
		private boolean range;
		private Object value;
		private Object valueTo;

		private Builder()
		{
			super();
		}

		public DocumentQueryFilter build()
		{
			return new DocumentQueryFilter(this);
		}

		public Builder setFieldName(final String fieldName)
		{
			this.fieldName = fieldName;
			return this;
		}

		public Builder setRange(final boolean range)
		{
			this.range = range;
			return this;
		}

		public Builder setValue(final Object value)
		{
			this.value = value;
			return this;
		}

		public Builder setValueTo(final Object valueTo)
		{
			this.valueTo = valueTo;
			return this;
		}
	}

}
