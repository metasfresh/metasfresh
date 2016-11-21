package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;

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
public final class DocumentLayoutElementDescriptor implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String internalName;

	private final ITranslatableString caption;
	private final ITranslatableString description;

	private final DocumentFieldWidgetType widgetType;
	private Optional<Integer> precision;

	private final LayoutType layoutType;
	private final boolean gridElement;
	private final boolean advancedField;

	private final Set<DocumentLayoutElementFieldDescriptor> fields;

	private String _captionAsFieldNames; // lazy

	private static final Joiner JOINER_FieldNames = Joiner.on(" | ").skipNulls();

	private DocumentLayoutElementDescriptor(final Builder builder)
	{
		super();

		internalName = builder.internalName;
		caption = builder.caption != null ? builder.caption : ImmutableTranslatableString.empty();
		description = builder.description != null ? builder.description : ImmutableTranslatableString.empty();

		Check.assumeNotNull(builder.widgetType, "Parameter builder.widgetType is not null for {}", builder);
		widgetType = builder.widgetType;
		precision = Optional.ofNullable(builder.getPrecision());

		layoutType = builder.layoutType;
		gridElement = builder.gridElement;

		advancedField = builder.isAdvancedField();

		fields = ImmutableSet.copyOf(builder.buildFields());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("internalName", internalName)
				.add("caption", caption)
				.add("description", description)
				.add("widgetType", widgetType)
				.add("advancedField", advancedField)
				.add("fields", fields.isEmpty() ? null : fields)
				.toString();
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getCaptionAsFieldNames()
	{
		if (_captionAsFieldNames == null)
		{
			_captionAsFieldNames = fields
					.stream()
					.filter(field -> field.isPublicField()) // only those which are public
					.map(field -> field.getField())
					.collect(GuavaCollectors.toString(JOINER_FieldNames));
		}
		return _captionAsFieldNames;
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
	}

	public Optional<Integer> getPrecision()
	{
		return precision;
	}

	public LayoutType getLayoutType()
	{
		return layoutType;
	}

	public LayoutAlign getGridAlign()
	{
		if (gridElement)
			return widgetType == null ? null : widgetType.getGridAlign();
		else
			return null;
	}

	public boolean isAdvancedField()
	{
		return advancedField;
	}

	public Set<DocumentLayoutElementFieldDescriptor> getFields()
	{
		return fields;
	}

	public boolean hasFields()
	{
		return !fields.isEmpty();
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutElementDescriptor.Builder.class);

		private String internalName;
		private ITranslatableString caption = ImmutableTranslatableString.empty();
		private ITranslatableString description = ImmutableTranslatableString.empty();
		private DocumentFieldWidgetType widgetType;
		private LayoutType layoutType;
		private boolean gridElement = false;
		private boolean advancedField = false;
		private final LinkedHashMap<String, DocumentLayoutElementFieldDescriptor.Builder> fieldsBuilders = new LinkedHashMap<>();
		private boolean excludeSpecialFields = false;
		private boolean consumed = false;

		private Builder()
		{
			super();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.omitNullValues()
					.add("internalName", internalName)
					.add("caption", caption)
					.add("description", description)
					.add("widgetType", widgetType)
					.add("consumed", consumed ? Boolean.TRUE : null)
					.add("fields-count", fieldsBuilders.size())
					.toString();
		}

		public DocumentLayoutElementDescriptor build()
		{
			setConsumed();

			final DocumentLayoutElementDescriptor result = new DocumentLayoutElementDescriptor(this);

			logger.trace("Built {} for {}", result, this);
			return result;
		}

		private Set<DocumentLayoutElementFieldDescriptor> buildFields()
		{
			return fieldsBuilders
					.values()
					.stream()
					.filter(fieldBuilder -> checkValid(fieldBuilder))
					.map(fieldBuilder -> fieldBuilder.build())
					.collect(GuavaCollectors.toImmutableSet());
		}

		private boolean checkValid(final DocumentLayoutElementFieldDescriptor.Builder fieldBuilder)
		{
			if (fieldBuilder.isConsumed())
			{
				logger.trace("Skip adding {} to {} because it's already consumed", fieldBuilder, this);
				return false;
			}

			if (!fieldBuilder.isPublicField())
			{
				logger.trace("Skip adding {} to {} because it's not a public field", fieldBuilder, this);
				return false;
			}
			
			if (excludeSpecialFields && fieldBuilder.isSpecialField())
			{
				logger.trace("Skip adding {} to {} because it's a special field and we were asked to exclude special fields", fieldBuilder, this);
				return false;
			}

			return true;
		}

		public Builder setInternalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this.widgetType = widgetType;
			return this;
		}

		public DocumentFieldWidgetType getWidgetType()
		{
			return widgetType;
		}

		private Integer getPrecision()
		{
			final DocumentFieldWidgetType widgetType = this.getWidgetType();
			if (widgetType == null)
			{
				return null;
			}
			return widgetType.getStandardNumberPrecision();
		}

		public Builder setLayoutType(final LayoutType layoutType)
		{
			this.layoutType = layoutType;
			return this;
		}

		public Builder setLayoutTypeNone()
		{
			layoutType = null;
			return this;
		}

		public LayoutType getLayoutType()
		{
			return layoutType;
		}

		public Builder setAdvancedField(final boolean advancedField)
		{
			this.advancedField = advancedField;
			return this;
		}

		public boolean isAdvancedField()
		{
			return advancedField;
		}

		public Builder addField(final DocumentLayoutElementFieldDescriptor.Builder fieldBuilder)
		{
			Check.assumeNotNull(fieldBuilder, "Parameter fieldBuilder is not null");
			final DocumentLayoutElementFieldDescriptor.Builder previousFieldBuilder = fieldsBuilders.put(fieldBuilder.getFieldName(), fieldBuilder);
			if (previousFieldBuilder != null)
			{
				new AdempiereException("Field " + fieldBuilder.getFieldName() + " already exists in element: " + caption)
						.throwIfDeveloperModeOrLogWarningElse(logger);
			}
			return this;
		}

		public Set<String> getFieldNames()
		{
			return fieldsBuilders.keySet();
		}

		public DocumentLayoutElementFieldDescriptor.Builder getField(final String fieldName)
		{
			return fieldsBuilders.get(fieldName);
		}

		public DocumentLayoutElementFieldDescriptor.Builder getFirstField()
		{
			return fieldsBuilders.values().iterator().next();
		}

		public int getFieldsCount()
		{
			return fieldsBuilders.size();
		}

		public boolean hasFieldName(final String fieldName)
		{
			return fieldsBuilders.containsKey(fieldName);
		}

		public Builder setExcludeSpecialFields()
		{
			this.excludeSpecialFields = true;
			return this;
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

		/**
		 * Flags this element as a "grid element".
		 */
		public Builder setGridElement()
		{
			this.gridElement = true;
			return this;
		}

		/**
		 * Reset the "grid element" flag.
		 * 
		 * NOTE: this is false by default, but the main purpose of this method is intention revealing.
		 *
		 * @see #setGridElement()
		 */
		public Builder setNotGridElement()
		{
			this.gridElement = false;
			return this;
		}

	}
}
