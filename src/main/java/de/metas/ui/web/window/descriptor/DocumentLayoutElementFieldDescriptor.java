package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.devices.JSONDeviceDescriptor;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElementField.JSONFieldType;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElementField.JSONLookupSource;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;

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

@SuppressWarnings("serial")
public final class DocumentLayoutElementFieldDescriptor implements Serializable
{
	public static final Builder builder(final String fieldName)
	{
		return new Builder(fieldName);
	}

	/**
	 * Please keep in sync with {@link JSONLookupSource}.
	 */
	public static enum LookupSource
	{
		lookup,

		list,

		text
	};

	/**
	 * Please keep in sync with {@link JSONFieldType}.
	 */
	public static enum FieldType
	{
		ActionButtonStatus,

		ActionButton,

		Tooltip
	}

	private final String internalName;

	@Getter
	private final String field;

	@Getter
	private final FieldType fieldType;

	/**
	 * Only relevant if the field type is {@link FieldType#Tooltip}.
	 */
	@Getter
	private final String tooltipIconName;

	@Getter
	private final boolean publicField;

	@Getter
	private final LookupSource lookupSource;

	@Getter
	private final Optional<String> lookupTableName;

	private final ITranslatableString emptyText;

	@Getter
	private final List<JSONDeviceDescriptor> devices;

	@Getter
	private final boolean supportZoomInto;

	private DocumentLayoutElementFieldDescriptor(@NonNull final Builder builder)
	{
		internalName = builder.internalName;
		field = builder.getFieldName();

		fieldType = builder.fieldType;
		if (FieldType.Tooltip.equals(fieldType))
		{
			tooltipIconName = Check.assumeNotEmpty(builder.tooltipIconName, "If fieldType=tooltip, then a tooltipIcon needs to be provided; builder={}", builder);
		}
		else
		{
			tooltipIconName = null;
		}

		publicField = builder.publicField;
		emptyText = ImmutableTranslatableString.copyOfNullable(builder.emptyText);
		devices = builder.getDevices();

		lookupSource = builder.lookupSource;
		lookupTableName = builder.getLookupTableName();

		supportZoomInto = builder.isSupportZoomInto();
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

	public String getEmptyText(final String adLanguage)
	{
		return emptyText.translate(adLanguage);
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
		private FieldType fieldType;
		private String tooltipIconName;

		private ITranslatableString emptyText = HARDCODED_FIELD_EMPTY_TEXT;
		private boolean publicField = true;
		private List<JSONDeviceDescriptor> _devices;

		private LookupSource lookupSource;
		private Optional<String> lookupTableName = null;

		private boolean consumed = false;
		private DocumentFieldDescriptor.Builder documentFieldBuilder;

		private boolean supportZoomInto;

		private Builder(final String fieldName)
		{
			Check.assumeNotEmpty(fieldName, "fieldName is not empty");
			this.fieldName = fieldName;
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

		public Builder setLookupTableName(@NonNull final Optional<String> lookupTableName)
		{
			this.lookupTableName = lookupTableName;
			return this;
		}

		public Optional<String> getLookupTableName()
		{
			if (lookupTableName != null)
			{
				return lookupTableName;
			}
			if (documentFieldBuilder != null)
			{
				return documentFieldBuilder.getLookupTableName();
			}

			return Optional.empty();
		}

		public Builder setFieldType(final FieldType fieldType)
		{
			this.fieldType = fieldType;
			return this;
		}

		/** May be {@code null}, unless the field type is {@link FieldType#Tooltip}. */
		public Builder setTooltipIconName(final String tooltipIconName)
		{
			this.tooltipIconName = tooltipIconName;
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

		public boolean isSpecialFieldToExcludeFromLayout()
		{
			return documentFieldBuilder != null && documentFieldBuilder.isSpecialFieldToExcludeFromLayout();
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

		public Builder setSupportZoomInto(boolean supportZoomInto)
		{
			this.supportZoomInto = supportZoomInto;
			return this;
		}

		private boolean isSupportZoomInto()
		{
			return supportZoomInto;
		}
	}
}
