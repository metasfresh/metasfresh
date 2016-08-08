package de.metas.ui.web.window.descriptor;

import java.io.Serializable;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

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

@SuppressWarnings("serial")
public final class DocumentFieldDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	/** Internal field name (aka ColumnName) */
	private final String name;
	/** Detail ID or null if this is a field in main sections */
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String detailId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String caption;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String description;

	/** Is this the key field ? */
	@JsonIgnore
	private final boolean key;

	private final DocumentFieldWidgetType widgetType;
	
	@JsonIgnore
	private final Class<?> valueClass; 

	@JsonIgnore
	private final IStringExpression defaultValueExpression;

	@JsonIgnore
	private final ILogicExpression readonlyLogic;
	@JsonIgnore
	private final ILogicExpression displayLogic;
	@JsonIgnore
	private final ILogicExpression mandatoryLogic;

	@JsonIgnore
	private final DocumentFieldDataBindingDescriptor dataBinding;

	private DocumentFieldDescriptor(final Builder builder)
	{
		super();
		name = Preconditions.checkNotNull(builder.name, "name is null");
		detailId = builder.detailId;

		caption = builder.caption;
		description = builder.description;
		
		key = builder.key;

		widgetType = Preconditions.checkNotNull(builder.widgetType, "widgetType is null");
		
		valueClass = Preconditions.checkNotNull(builder.valueClass, "value class not null");

		defaultValueExpression = builder.defaultValueExpression;

		readonlyLogic = builder.readonlyLogic;
		displayLogic = builder.displayLogic;
		mandatoryLogic = builder.mandatoryLogic;

		dataBinding = Preconditions.checkNotNull(builder.dataBinding, "dataBinding is null");
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", name)
				.add("detailId", detailId)
				.add("widgetType", widgetType)
				.add("fieldDataBinding", dataBinding)
				.toString();
	}

	public String getName()
	{
		return name;
	}

	public String getDetailId()
	{
		return detailId;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getDescription()
	{
		return description;
	}
	
	public boolean isKey()
	{
		return key;
	}

	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
	}
	
	public Class<?> getValueClass()
	{
		return valueClass;
	}

	public IStringExpression getDefaultValueExpression()
	{
		return defaultValueExpression;
	}

	public ILogicExpression getReadonlyLogic()
	{
		return readonlyLogic;
	}

	public ILogicExpression getDisplayLogic()
	{
		return displayLogic;
	}

	public ILogicExpression getMandatoryLogic()
	{
		return mandatoryLogic;
	}

	public DocumentFieldDataBindingDescriptor getDataBinding()
	{
		return dataBinding;
	}

	public static final class Builder
	{
		private String name;
		public String detailId;

		private String caption;
		private String description;

		private boolean key = false;

		private DocumentFieldWidgetType widgetType;
		public Class<?> valueClass;

		private IStringExpression defaultValueExpression = IStringExpression.NULL;

		private ILogicExpression readonlyLogic = ILogicExpression.FALSE;
		private ILogicExpression displayLogic = ILogicExpression.TRUE;
		private ILogicExpression mandatoryLogic = ILogicExpression.FALSE;

		private DocumentFieldDataBindingDescriptor dataBinding;

		private Builder()
		{
			super();
		}

		public DocumentFieldDescriptor build()
		{
			return new DocumentFieldDescriptor(this);
		}

		public Builder setName(final String name)
		{
			this.name = name;
			return this;
		}

		public Builder setDetailId(final String detailId)
		{
			this.detailId = Strings.emptyToNull(detailId);
			return this;
		}

		public Builder setCaption(final String caption)
		{
			this.caption = Strings.emptyToNull(caption);
			return this;
		}

		public Builder setDescription(final String description)
		{
			this.description = Strings.emptyToNull(description);
			return this;
		}
		
		public Builder setKey(boolean key)
		{
			this.key = key;
			return this;
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this.widgetType = widgetType;
			return this;
		}
		
		public Builder setValueClass(final Class<?> valueClass)
		{
			this.valueClass = valueClass;
			return this;
		}

		public Builder setDefaultValueExpression(final IStringExpression defaultValueExpression)
		{
			this.defaultValueExpression = Preconditions.checkNotNull(defaultValueExpression);
			return this;
		}

		public Builder setReadonlyLogic(final ILogicExpression readonlyLogic)
		{
			this.readonlyLogic = Preconditions.checkNotNull(readonlyLogic);
			return this;
		}

		public Builder setDisplayLogic(final ILogicExpression displayLogic)
		{
			this.displayLogic = Preconditions.checkNotNull(displayLogic);
			return this;
		}

		public Builder setMandatoryLogic(final ILogicExpression mandatoryLogic)
		{
			this.mandatoryLogic = Preconditions.checkNotNull(mandatoryLogic);
			return this;
		}

		public Builder setDataBinding(final DocumentFieldDataBindingDescriptor dataBinding)
		{
			this.dataBinding = dataBinding;
			return this;
		}

	}

}
