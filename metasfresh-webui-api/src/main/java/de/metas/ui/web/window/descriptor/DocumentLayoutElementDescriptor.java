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
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;

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
	private final LayoutAlign gridAlign;
	private final boolean advancedField;

	private final Set<DocumentLayoutElementFieldDescriptor> fields;

	private String _captionAsFieldNames; // lazy


	private static final Joiner JOINER_FieldNames = Joiner.on(" | ").skipNulls();

	private DocumentLayoutElementDescriptor(final Builder builder)
	{
		super();

		internalName = builder.getInternalName();
		caption = builder.getCaption();
		description = builder.getDescription();

		widgetType = builder.getWidgetType();
		precision = Optional.ofNullable(builder.getPrecision());

		layoutType = builder.getLayoutType();
		gridAlign = builder.getGridAlign();

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
		return gridAlign;
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

		private String _internalName;
		private ITranslatableString _caption = ImmutableTranslatableString.empty();
		private ITranslatableString _description = ImmutableTranslatableString.empty();
		private DocumentFieldWidgetType _widgetType;
		private LayoutType _layoutType;
		private boolean _gridElement = false;
		private boolean _advancedField = false;
		private final LinkedHashMap<String, DocumentLayoutElementFieldDescriptor.Builder> _fieldsBuilders = new LinkedHashMap<>();
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
					.add("internalName", _internalName)
					.add("caption", _caption)
					.add("description", _description)
					.add("widgetType", _widgetType)
					.add("consumed", consumed ? Boolean.TRUE : null)
					.add("fields-count", _fieldsBuilders.size())
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
			return _fieldsBuilders
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
			this._internalName = internalName;
			return this;
		}
		
		private String getInternalName()
		{
			return _internalName;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this._caption = caption == null ? ImmutableTranslatableString.empty() : caption;
			return this;
		}
		
		private ITranslatableString getCaption()
		{
			return _caption;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			this._description = description == null ? ImmutableTranslatableString.empty() : description;
			return this;
		}
		
		private ITranslatableString getDescription()
		{
			return _description;
		}

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this._widgetType = widgetType;
			return this;
		}

		public boolean isWidgetTypeSet()
		{
			return _widgetType != null;
		}
		
		private DocumentFieldWidgetType getWidgetType()
		{
			Check.assumeNotNull(_widgetType, DocumentLayoutBuildException.class, "Parameter widgetType is not null for {}", this);
			return _widgetType;
		}

		private Integer getPrecision()
		{
			return getWidgetType().getStandardNumberPrecision();
		}

		public Builder setLayoutType(final LayoutType layoutType)
		{
			this._layoutType = layoutType;
			return this;
		}

		public Builder setLayoutTypeNone()
		{
			_layoutType = null;
			return this;
		}

		private LayoutType getLayoutType()
		{
			return _layoutType;
		}

		public Builder setAdvancedField(final boolean advancedField)
		{
			this._advancedField = advancedField;
			return this;
		}

		public boolean isAdvancedField()
		{
			return _advancedField;
		}

		public Builder addField(final DocumentLayoutElementFieldDescriptor.Builder fieldBuilder)
		{
			Check.assumeNotNull(fieldBuilder, "Parameter fieldBuilder is not null");
			final DocumentLayoutElementFieldDescriptor.Builder previousFieldBuilder = _fieldsBuilders.put(fieldBuilder.getFieldName(), fieldBuilder);
			if (previousFieldBuilder != null)
			{
				new AdempiereException("Field " + fieldBuilder.getFieldName() + " already exists in element: " + _caption)
						.throwIfDeveloperModeOrLogWarningElse(logger);
			}
			return this;
		}

		public Set<String> getFieldNames()
		{
			return _fieldsBuilders.keySet();
		}

		public DocumentLayoutElementFieldDescriptor.Builder getField(final String fieldName)
		{
			return _fieldsBuilders.get(fieldName);
		}

		public DocumentLayoutElementFieldDescriptor.Builder getFirstField()
		{
			return _fieldsBuilders.values().iterator().next();
		}

		public int getFieldsCount()
		{
			return _fieldsBuilders.size();
		}

		public boolean hasFieldName(final String fieldName)
		{
			return _fieldsBuilders.containsKey(fieldName);
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
			this._gridElement = true;
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
			this._gridElement = false;
			return this;
		}
		
		private LayoutAlign getGridAlign()
		{
			return _gridElement ? getWidgetType().getGridAlign() : null;
		}

	}
}
