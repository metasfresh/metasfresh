package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;

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

	private static final Logger logger = LogManager.getLogger(DocumentLayoutElementDescriptor.class);

	private final String caption;
	private final String description;

	private final DocumentFieldWidgetType widgetType;
	private final LayoutType layoutType;

	private final Set<DocumentLayoutElementFieldDescriptor> fields;

	private DocumentLayoutElementDescriptor(final Builder builder)
	{
		super();

		caption = builder.caption;
		description = builder.description;
		widgetType = Preconditions.checkNotNull(builder.widgetType, "widgetType is null");
		layoutType = builder.layoutType;
		fields = ImmutableSet.copyOf(builder.buildFields());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("caption", caption)
				.add("description", description)
				.add("widgetType", widgetType)
				.add("fields", fields.isEmpty() ? null : fields)
				.toString();
	}

	public String getCaption()
	{
		return caption;
	}

	public String getDescription()
	{
		return description;
	}

	public DocumentFieldWidgetType getWidgetType()
	{
		return widgetType;
	}

	public LayoutType getLayoutType()
	{
		return layoutType;
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
		private String caption;
		private String description;
		private DocumentFieldWidgetType widgetType;
		private LayoutType layoutType;
		private final LinkedHashMap<String, DocumentLayoutElementFieldDescriptor.Builder> fieldsBuilders = new LinkedHashMap<>();
		private boolean consumed = false;

		private Builder()
		{
			super();
		}

		public DocumentLayoutElementDescriptor build()
		{
			setConsumed();
			return new DocumentLayoutElementDescriptor(this);
		}

		private Set<DocumentLayoutElementFieldDescriptor> buildFields()
		{
			return fieldsBuilders
					.values()
					.stream()
					.filter(fieldBuilder -> !fieldBuilder.isConsumed()) // skip those which were already consumed
					.filter(fieldBuilder -> fieldBuilder.isPublicField()) // only those which are public
					.map(fieldBuilder -> fieldBuilder.build())
					.collect(GuavaCollectors.toImmutableSet());
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

		public Builder setWidgetType(final DocumentFieldWidgetType widgetType)
		{
			this.widgetType = widgetType;
			return this;
		}

		public DocumentFieldWidgetType getWidgetType()
		{
			return widgetType;
		}

		public Builder setLayoutType(final String layoutTypeStr)
		{
			layoutType = LayoutType.fromNullable(layoutTypeStr);
			return this;
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

		public Builder setConsumed()
		{
			this.consumed = true;
			return this;
		}

		public boolean isConsumed()
		{
			return consumed;
		}

		/**
		 * (DEBUG option) Use field names instead of caption
		 * 
		 * @return
		 */
		public Builder setCaptionAsFieldNames()
		{
			final String caption = fieldsBuilders.values()
					.stream()
					.filter(fieldBuilder -> fieldBuilder.isPublicField()) // only those which are public
					.map(fieldBuilder -> fieldBuilder.getFieldName())
					.collect(GuavaCollectors.toString(Joiner.on(" | ").skipNulls()));
			setCaption(caption);
			return this;
		}

	}
}
