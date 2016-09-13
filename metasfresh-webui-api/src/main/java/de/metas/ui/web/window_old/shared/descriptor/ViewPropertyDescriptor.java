package de.metas.ui.web.window_old.shared.descriptor;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window_old.PropertyName;

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
public final class ViewPropertyDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	@JsonProperty("propertyName")
	private final PropertyName propertyName;
	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("type")
	private final PropertyDescriptorType type;
	@JsonProperty("valueType")
	private final PropertyDescriptorValueType valueType;
	@JsonProperty("layoutInfo")
	private final PropertyLayoutInfo layoutInfo;
	@JsonProperty("childDescriptors")
	private final Map<PropertyName, ViewPropertyDescriptor> childDescriptors;

	private ViewPropertyDescriptor(final Builder builder)
	{
		super();
		propertyName = builder.propertyName;
		caption = builder.caption;
		type = builder.type;
		valueType = builder.valueType;
		layoutInfo = builder.layoutInfo;
		childDescriptors = builder.getChildDescriptors();
	}

	/** JSON constructor */
	private ViewPropertyDescriptor( //
			@JsonProperty("propertyName") final PropertyName propertyName //
			, @JsonProperty("caption") final String caption //
			, @JsonProperty("type") final PropertyDescriptorType type //
			, @JsonProperty("valueType") final PropertyDescriptorValueType valueType //
			, @JsonProperty("layoutInfo") final PropertyLayoutInfo layoutInfo //
			, @JsonProperty("childDescriptors") final Map<PropertyName, ViewPropertyDescriptor> childDescriptors //
	)
	{
		this(builder()
				.setPropertyName(propertyName)
				.setCaption(caption)
				.setType(type)
				.setValueType(valueType)
				.setLayoutInfo(layoutInfo)
				.setChildDescriptors(childDescriptors));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", propertyName)
				.add("type", type)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(propertyName, caption, type, valueType, layoutInfo, childDescriptors);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}

		if (!(obj instanceof ViewPropertyDescriptor))
		{
			return false;
		}

		final ViewPropertyDescriptor other = (ViewPropertyDescriptor)obj;
		return Objects.equals(this.propertyName, other.propertyName)
				&& Objects.equals(this.caption, other.caption)
				&& Objects.equals(this.type, other.type)
				&& Objects.equals(this.valueType, other.valueType)
				&& Objects.equals(this.layoutInfo, other.layoutInfo)
				&& Objects.equals(this.childDescriptors, other.childDescriptors);
	}

	public PropertyName getPropertyName()
	{
		return propertyName;
	}

	public String getCaption()
	{
		return caption;
	}

	public PropertyDescriptorType getType()
	{
		return type;
	}

	@JsonIgnore
	public boolean isValueProperty()
	{
		return valueType != null;
	}

	public PropertyDescriptorValueType getValueType()
	{
		return valueType;
	}

	public PropertyLayoutInfo getLayoutInfo()
	{
		return layoutInfo;
	}

	@JsonIgnore
	public boolean isDocumentFragment()
	{
		return type == PropertyDescriptorType.Group;
	}

	public Map<PropertyName, ViewPropertyDescriptor> getChildDescriptors()
	{
		return childDescriptors;
	}

	@JsonIgnore
	public Collection<ViewPropertyDescriptor> getChildDescriptorsList()
	{
		return childDescriptors.values();
	}

	public static final class Builder
	{
		private PropertyName propertyName;
		private String caption;
		private PropertyDescriptorType type;
		private PropertyDescriptorValueType valueType;
		private PropertyLayoutInfo layoutInfo;
		private Map<PropertyName, ViewPropertyDescriptor> _childDescriptors = null;

		private Builder()
		{
			super();
		}

		public ViewPropertyDescriptor build()
		{
			return new ViewPropertyDescriptor(this);
		}

		public Builder setPropertyName(final PropertyName propertyName)
		{
			this.propertyName = propertyName;
			return this;
		}

		public Builder setCaption(final String caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setType(final PropertyDescriptorType type)
		{
			this.type = type;
			return this;
		}

		public Builder setValueType(final PropertyDescriptorValueType valueType)
		{
			this.valueType = valueType;
			return this;
		}

		public Builder setLayoutInfo(final PropertyLayoutInfo layoutInfo)
		{
			this.layoutInfo = layoutInfo;
			return this;
		}

		private final ImmutableMap<PropertyName, ViewPropertyDescriptor> getChildDescriptors()
		{
			if (_childDescriptors == null)
			{
				return ImmutableMap.of();
			}
			return ImmutableMap.copyOf(_childDescriptors);
		}

		public Builder addChildDescriptor(final ViewPropertyDescriptor childDescriptor)
		{
			if (_childDescriptors == null)
			{
				_childDescriptors = new LinkedHashMap<>();
			}
			_childDescriptors.put(childDescriptor.getPropertyName(), childDescriptor);
			return this;
		}

		private Builder setChildDescriptors(final Map<PropertyName, ViewPropertyDescriptor> childDescriptors)
		{
			_childDescriptors = childDescriptors;
			return this;
		}
	}
}
