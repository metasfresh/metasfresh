package de.metas.ui.web.window.descriptor;

import static de.metas.util.Check.assumeNotNull;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.WindowId;
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

/**
 * Specifies the fields of a tab, both in terms of a table row and of a one-record detail view.
 */
public final class DocumentLayoutDetailDescriptor
{
	public static final Builder builder(
			@NonNull final WindowId windowId,
			@NonNull final DetailId detailId)
	{
		return new Builder(windowId, detailId);
	}

	private final WindowId windowId;
	private final DetailId detailId;
	private final String internalName;

	private final ITranslatableString caption;
	private final ITranslatableString description;

	private final ViewLayout gridLayout;
	private final DocumentLayoutSingleRow singleRowLayout;

	private final boolean supportQuickInput;
	private final boolean queryOnActivate;

	/** May be {@code true} for a tab that can have just zero or one record and that shall be displayed in detail (i.e. not grid) layout. */
	@Getter
	private final boolean singleRowDetailLayout;

	@Getter
	private final List<DocumentLayoutDetailDescriptor> subTabLayouts;

	private DocumentLayoutDetailDescriptor(@NonNull final Builder builder)
	{
		windowId = Check.assumeNotNull(builder.windowId, "Parameter windowId is not null");

		detailId = builder.detailId;

		caption = assumeNotNull(builder.caption, "builder.caption may not be null; builder={}", builder);
		description = assumeNotNull(builder.description, "builder.description may not be null; builder={}", builder);

		internalName = builder.internalName;

		gridLayout = builder.buildGridLayout();
		singleRowLayout = builder.buildSingleRowLayout();

		supportQuickInput = builder.isSupportQuickInput();
		queryOnActivate = builder.queryOnActivate;

		subTabLayouts = builder.subTabLayouts;

		singleRowDetailLayout = builder.singleRowDetailLayout;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("detailId", detailId)
				.add("internalName", internalName)
				.toString();
	}

	public WindowId getWindowId()
	{
		return windowId;
	}

	public DetailId getDetailId()
	{
		return detailId;
	}

	public String getInternalName()
	{
		return internalName;
	}

	public ViewLayout getGridLayout()
	{
		return gridLayout;
	}

	public DocumentLayoutSingleRow getSingleRowLayout()
	{
		return singleRowLayout;
	}

	public boolean isEmpty()
	{
		final boolean hasSubLayouts = !Check.isEmpty(subTabLayouts);

		final boolean hasGridLayout = gridLayout != null && gridLayout.hasElements();
		final boolean hasDetailLayout = singleRowLayout != null && !singleRowLayout.isEmpty();

		final boolean hasFields = (singleRowDetailLayout || hasGridLayout) && hasDetailLayout;

		return !hasSubLayouts && !hasFields;
	}

	public boolean isSupportQuickInput()
	{
		return supportQuickInput;
	}

	public boolean isQueryOnActivate()
	{
		return queryOnActivate;
	}

	public String getCaption(@NonNull final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getDescription(@NonNull final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public static final class Builder
	{
		private final WindowId windowId;
		private final DetailId detailId;
		private String internalName;

		private ViewLayout.Builder gridLayout = null;
		private DocumentLayoutSingleRow.Builder singleRowLayout = null;

		private boolean supportQuickInput;

		private boolean queryOnActivate;

		private boolean singleRowDetailLayout = false;

		private final List<DocumentLayoutDetailDescriptor> subTabLayouts = new ArrayList<>();

		private ITranslatableString caption;
		private ITranslatableString description;

		private Builder(@NonNull final WindowId windowId, @NonNull final DetailId detailId)
		{
			this.windowId = windowId;
			this.detailId = detailId;
		}

		public DocumentLayoutDetailDescriptor build()
		{
			return new DocumentLayoutDetailDescriptor(this);
		}

		private ViewLayout buildGridLayout()
		{
			if (gridLayout == null)
			{
				return null;
			}
			return gridLayout.build();
		}

		private DocumentLayoutSingleRow buildSingleRowLayout()
		{
			if (singleRowLayout == null)
			{
				return null;
			}
			return singleRowLayout.build();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("detailId", detailId)
					.toString();
		}

		public Builder internalName(final String internalName)
		{
			this.internalName = internalName;
			return this;
		}

		public Builder gridLayout(@NonNull final ViewLayout.Builder gridLayout)
		{
			this.gridLayout = gridLayout;
			gridLayout.setWindowId(windowId);
			gridLayout.setDetailId(detailId);
			return this;
		}

		public Builder singleRowLayout(@NonNull final DocumentLayoutSingleRow.Builder singleRowLayout)
		{
			this.singleRowLayout = singleRowLayout;
			singleRowLayout.setWindowId(windowId);
			return this;
		}

		/** The default is {@code false} */
		public Builder singleRowDetailLayout(final boolean singleRowDetailLayout)
		{
			this.singleRowDetailLayout = singleRowDetailLayout;
			return this;
		}

		/* package */ boolean isEmpty()
		{
			return (gridLayout == null || !gridLayout.hasElements())
					&& (singleRowLayout == null || singleRowLayout.isEmpty());
		}

		public Builder queryOnActivate(final boolean queryOnActivate)
		{
			this.queryOnActivate = queryOnActivate;
			return this;
		}

		public Builder supportQuickInput(final boolean supportQuickInput)
		{
			this.supportQuickInput = supportQuickInput;
			return this;
		}

		public boolean isSupportQuickInput()
		{
			return supportQuickInput;
		}

		public Builder caption(@NonNull final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder description(@NonNull final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder addSubTabLayout(@NonNull final DocumentLayoutDetailDescriptor subTabLayout)
		{
			this.subTabLayouts.add(subTabLayout);
			return this;
		}

		public Builder addAllSubTabLayouts(@NonNull final List<DocumentLayoutDetailDescriptor> subTabLayouts)
		{
			this.subTabLayouts.addAll(subTabLayouts);
			return this;
		}
	}
}
