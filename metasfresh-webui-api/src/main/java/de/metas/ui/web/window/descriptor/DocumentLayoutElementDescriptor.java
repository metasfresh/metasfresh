package de.metas.ui.web.window.descriptor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

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
		fields = ImmutableSet.copyOf(builder.fields);
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

	public static final class Builder
	{
		private String caption;
		private String description;
		private DocumentFieldWidgetType widgetType;
		private LayoutType layoutType;
		private final LinkedHashSet<DocumentLayoutElementFieldDescriptor> fields = new LinkedHashSet<>();

		private Builder()
		{
			super();
		}

		public DocumentLayoutElementDescriptor build()
		{
			return new DocumentLayoutElementDescriptor(this);
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
			this.layoutType = null;
			return this;
		}

		public LayoutType getLayoutType()
		{
			return layoutType;
		}

		public Builder addField(final DocumentLayoutElementFieldDescriptor field)
		{
			final boolean added = fields.add(field);
			if (!added)
			{
				new AdempiereException("Field " + field + " already exists: " + fields + " for " + caption)
						.throwIfDeveloperModeOrLogWarningElse(logger);
			}
			return this;
		}
	}
}
