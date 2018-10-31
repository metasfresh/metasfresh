package de.metas.ui.web.window.descriptor;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Check;
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

public final class DocumentLayoutDetailDescriptor
{
	public static final Builder builder(final WindowId windowId, final DetailId detailId)
	{
		return new Builder(windowId, detailId);
	}

	private final WindowId windowId;
	private final DetailId detailId;
	private final String internalName;

	private final ViewLayout gridLayout;
	private final DocumentLayoutSingleRow singleRowLayout;

	private final boolean supportQuickInput;
	private final boolean queryOnActivate;

	private DocumentLayoutDetailDescriptor(final Builder builder)
	{
		windowId = Check.assumeNotNull(builder.windowId, "Parameter windowId is not null");

		detailId = builder.detailId;

		internalName = builder.internalName;

		gridLayout = builder.buildGridLayout();
		singleRowLayout = builder.buildSingleRowLayout();

		supportQuickInput = builder.isSupportQuickInput();
		queryOnActivate = builder.queryOnActivate;
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
		return !gridLayout.hasElements() && singleRowLayout.isEmpty();
	}

	public boolean isSupportQuickInput()
	{
		return supportQuickInput;
	}

	public boolean isQueryOnActivate()
	{
		return queryOnActivate;
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
			return gridLayout.build();
		}

		private DocumentLayoutSingleRow buildSingleRowLayout()
		{
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
	}
}
