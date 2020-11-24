package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.devices.DeviceDescriptorsList;
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
	public static Builder builder(final String fieldName)
	{
		return new Builder(fieldName);
	}

	/**
	 * Please keep in sync with {@link JSONLookupSource}.
	 */
	public enum LookupSource
	{
		lookup,

		list,

		text
	}

	/**
	 * Please keep in sync with {@link JSONFieldType}.
	 */
	public enum FieldType
	{
		ActionButtonStatus,

		ActionButton,

		Tooltip
	}

	@Getter
	private final String field;

	@Getter
	private final ITranslatableString caption;

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
	@Getter
	private final int lookupSearchStringMinLength;
	@Getter
	private final Optional<Duration> lookupSearchStartDelay;

	private final ITranslatableString listNullItemCaption;
	private final ITranslatableString emptyFieldText;

	@Getter
	private final DeviceDescriptorsList devices;

	@Getter
	private final boolean supportZoomInto;

	private DocumentLayoutElementFieldDescriptor(@NonNull final Builder builder)
	{
		field = builder.getFieldName();
		caption = builder.getCaption();
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
		listNullItemCaption = TranslatableStrings.copyOfNullable(builder.listNullItemCaption);
		emptyFieldText = TranslatableStrings.copyOfNullable(builder.emptyFieldText);
		devices = builder.getDevices();

		lookupSource = builder.lookupSource;
		lookupTableName = builder.getLookupTableName();
		lookupSearchStringMinLength = builder.lookupSearchStringMinLength;
		lookupSearchStartDelay = builder.lookupSearchStartDelay;

		supportZoomInto = builder.isSupportZoomInto();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("field", field)
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

	public String getListNullItemCaption(final String adLanguage)
	{
		return listNullItemCaption.translate(adLanguage);
	}

	public String getEmptyFieldText(final String adLanguage)
	{
		return emptyFieldText.translate(adLanguage);
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutElementFieldDescriptor.Builder.class);

		// FIXME TRL HARDCODED_FIELD_EMPTY_TEXT
		private static final ITranslatableString HARDCODED_FIELD_EMPTY_TEXT = ImmutableTranslatableString.builder()
				.defaultValue("none")
				.trl("de_DE", "leer")
				.trl("de_CH", "leer")
				.build();

		private final String fieldName;
		private ITranslatableString caption = TranslatableStrings.empty();
		private FieldType fieldType;
		private String tooltipIconName;

		private ITranslatableString listNullItemCaption = HARDCODED_FIELD_EMPTY_TEXT;
		private ITranslatableString emptyFieldText = HARDCODED_FIELD_EMPTY_TEXT;
		private boolean publicField = true;
		private DeviceDescriptorsList _devices = DeviceDescriptorsList.EMPTY;

		private LookupSource lookupSource;
		private Optional<String> lookupTableName = null;
		private int lookupSearchStringMinLength = -1;
		private Optional<Duration> lookupSearchStartDelay = Optional.empty();

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
					.add("fieldName", fieldName)
					.add("publicField", publicField)
					.add("fieldActions", (_devices == null || _devices.isEmpty()) ? null : _devices)
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

		public String getFieldName()
		{
			Check.assumeNotEmpty(fieldName, "fieldName is not empty");
			return fieldName;
		}

		public Builder setCaption(@NonNull final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public ITranslatableString getCaption()
		{
			return caption;
		}

		public Builder setLookupInfos(final LookupDescriptor lookupDescriptor)
		{
			if (lookupDescriptor != null)
			{
				this.lookupSource = lookupDescriptor.getLookupSourceType();
				this.lookupTableName = lookupDescriptor.getTableName();
				this.lookupSearchStringMinLength = lookupDescriptor.getSearchStringMinLength();
				this.lookupSearchStartDelay = lookupDescriptor.getSearchStartDelay();
			}
			else
			{
				this.lookupSource = null;
				this.lookupTableName = Optional.empty();
				this.lookupSearchStringMinLength = -1;
				this.lookupSearchStartDelay = Optional.empty();
			}

			return this;
		}

		public Builder setLookupSourceAsText()
		{
			this.lookupSource = LookupSource.text;
			this.lookupTableName = Optional.empty();
			this.lookupSearchStringMinLength = -1;
			this.lookupSearchStartDelay = Optional.empty();
			return this;
		}

		private Optional<String> getLookupTableName()
		{
			if (lookupTableName != null)
			{
				return lookupTableName;
			}
			else if (documentFieldBuilder != null)
			{
				return documentFieldBuilder.getLookupTableName();
			}
			else
			{
				return Optional.empty();
			}
		}

		public Builder setFieldType(final FieldType fieldType)
		{
			this.fieldType = fieldType;
			return this;
		}

		public boolean isRegularField()
		{
			return fieldType == null;
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

		public Builder setListNullItemCaption(@NonNull final ITranslatableString listNullItemCaption)
		{
			this.listNullItemCaption = listNullItemCaption;
			return this;
		}

		public Builder setEmptyFieldText(ITranslatableString emptyFieldText)
		{
			this.emptyFieldText = emptyFieldText;
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

		public Builder setDevices(@NonNull final DeviceDescriptorsList devices)
		{
			this._devices = devices;
			return this;
		}

		private DeviceDescriptorsList getDevices()
		{
			return _devices;
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
