package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.adempiere.util.Check;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.devices.JSONDeviceDescriptor;

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
public final class DocumentLayoutElementFieldDescriptor implements Serializable
{
	public static final Builder builder(final String fieldName)
	{
		return new Builder(fieldName);
	}

	public static enum LookupSource
	{
		lookup //
		, list //
	};

	public static enum FieldType
	{
		ActionButtonStatus, ActionButton //
	}

	private final String internalName;
	private final String field;
	private final LookupSource lookupSource;
	private final FieldType fieldType;
	private final boolean publicField;

	private final ITranslatableString emptyText;

	private final List<JSONDeviceDescriptor> devices;

	private DocumentLayoutElementFieldDescriptor(final Builder builder)
	{
		super();

		internalName = builder.internalName;
		field = builder.getFieldName();
		lookupSource = builder.lookupSource;
		fieldType = builder.fieldType;
		publicField = builder.publicField;
		emptyText = ImmutableTranslatableString.copyOfNullable(builder.emptyText);
		devices = builder.getDevices();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("field", field)
				.add("internalName", internalName)
				.add("publicField", publicField)
				.add("devices", devices.isEmpty() ? null : devices)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(field);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof DocumentLayoutElementFieldDescriptor))
		{
			return false;
		}
		final DocumentLayoutElementFieldDescriptor other = (DocumentLayoutElementFieldDescriptor)obj;
		return Objects.equals(field, other.field); // only the field name shall be matched
	}

	public String getField()
	{
		return field;
	}

	public LookupSource getLookupSource()
	{
		return lookupSource;
	}

	public FieldType getFieldType()
	{
		return fieldType;
	}

	public String getEmptyText(final String adLanguage)
	{
		return emptyText.translate(adLanguage);
	}

	public boolean isPublicField()
	{
		return publicField;
	}

	public List<JSONDeviceDescriptor> getDevices()
	{
		return devices;
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutElementFieldDescriptor.Builder.class);

		// FIXME TRL HARDCODED_FIELD_EMPTY_TEXT
		private static final ITranslatableString HARDCODED_FIELD_EMPTY_TEXT = ImmutableTranslatableString.builder()
				.setDefaultValue("none")
				.put("de_DE", "leer")
				.put("de_CH", "leer")
				.build();

		private String internalName;
		private final String fieldName;
		private LookupSource lookupSource;
		private FieldType fieldType;
		private ITranslatableString emptyText = HARDCODED_FIELD_EMPTY_TEXT;
		private boolean publicField = true;
		private List<JSONDeviceDescriptor> _devices;

		private boolean consumed = false;
		private DocumentFieldDescriptor.Builder documentFieldBuilder;

		private Builder(final String fieldName)
		{
			super();
			Check.assumeNotEmpty(fieldName, "fieldName is not empty");
			this.fieldName = fieldName;
		}

		private Builder(final Builder from)
		{
			internalName = from.internalName;
			fieldName = from.fieldName;
			lookupSource = from.lookupSource;
			fieldType = from.fieldType;
			emptyText = from.emptyText;
			publicField = from.publicField;
			consumed = false;

		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("internalName", internalName)
					.add("fieldName", fieldName)
					.add("publicField", publicField)
					.add("fieldActions", _devices.isEmpty() ? null : _devices)
					.add("consumed", consumed)
					.toString();
		}

		public DocumentLayoutElementFieldDescriptor build()
		{
			setConsumed();
			final DocumentLayoutElementFieldDescriptor result = new DocumentLayoutElementFieldDescriptor(this);

			logger.trace("Build {} for {}", result, this);
			return result;
		}

		public Builder copy()
		{
			return new Builder(this);
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		public String getFieldName()
		{
			Check.assumeNotEmpty(fieldName, "fieldName is not empty");
			return fieldName;
		}

		public Builder setLookupSource(final LookupSource lookupSource)
		{
			this.lookupSource = lookupSource;
			return this;
		}

		public LookupSource getLookupSource()
		{
			return lookupSource;
		}

		public boolean isLookup()
		{
			return lookupSource != null;
		}

		public Builder setFieldType(final FieldType fieldType)
		{
			this.fieldType = fieldType;
			return this;
		}

		public Builder setPublicField(final boolean publicField)
		{
			this.publicField = publicField;
			return this;
		}

		public boolean isPublicField()
		{
			return publicField;
		}

		private Builder setConsumed()
		{
			consumed = true;
			return this;
		}

		public boolean isConsumed()
		{
			return consumed;
		}

		public Builder setEmptyText(final ITranslatableString emptyText)
		{
			Check.assumeNotNull(emptyText, "Parameter emptyText is not null");
			this.emptyText = emptyText;
			return this;
		}

		public Builder trackField(final DocumentFieldDescriptor.Builder field)
		{
			documentFieldBuilder = field;
			return this;
		}

		public boolean isSpecialField()
		{
			return documentFieldBuilder != null && documentFieldBuilder.isSpecialField();
		}

		public Builder addDevice(final JSONDeviceDescriptor device)
		{
			Check.assumeNotNull(device, "Parameter device is not null");
			if (_devices == null)
			{
				_devices = new ArrayList<>();
			}
			_devices.add(device);

			return this;
		}

		public Builder addDevices(final List<JSONDeviceDescriptor> devices)
		{
			if (devices == null || devices.isEmpty())
			{
				return this;
			}

			if (_devices == null)
			{
				_devices = new ArrayList<>();
			}
			_devices.addAll(devices);

			return this;

		}

		private List<JSONDeviceDescriptor> getDevices()
		{
			if (_devices == null)
			{
				return ImmutableList.of();
			}
			return ImmutableList.copyOf(_devices);
		}
	}
}
