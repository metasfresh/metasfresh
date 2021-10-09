package de.metas.ui.web.window.descriptor;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.DebugProperties;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.exceptions.DocumentLayoutDetailNotFoundException;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

public final class DocumentLayoutDescriptor
{
	public static Builder builder()
	{
		return new Builder();
	}

	private final WindowId windowId;
	private final ITranslatableString caption;

	/**
	 * Special element: Document summary
	 */
	private final DocumentLayoutElementDescriptor documentSummaryElement;
	/**
	 * Special element: DocStatus/DocAction
	 */
	private final DocumentLayoutElementDescriptor docActionElement;

	private final DocumentLayoutSingleRow singleRowLayout;
	private final ViewLayout gridView;
	/**
	 * Side list layout
	 */
	private final ViewLayout sideListView;

	// private final Map<DocumentLayoutDetailGroupDescriptor, List<DocumentLayoutDetailDescriptor>> detailGroupToDetails = new HashMap<>();
	//

	// private final Map<DetailId, DocumentLayoutDetailDescriptor> details;

	/**
	 * Single row layout: included tabs.
	 */
	private final Map<DetailId, DocumentLayoutDetailDescriptor> details;

	/**
	 * {@link #details} plus their included details.
	 */
	private final Map<DetailId, DocumentLayoutDetailDescriptor> allDetails;

	/**
	 * Misc debugging properties
	 */
	@Getter
	private final DebugProperties debugProperties;

	private DocumentLayoutDescriptor(@NonNull final Builder builder)
	{
		windowId = builder.windowId;
		Check.assumeNotNull(windowId, "builder.windowId may not be null; builder={}", builder);

		caption = builder.caption;

		documentSummaryElement = builder.documentSummaryElement;
		docActionElement = builder.docActionElement;

		Check.assumeNotNull(builder.getSingleRowLayout(), "builder.singleRowLayout may not be null; builder={}", builder);
		singleRowLayout = builder.getSingleRowLayout()
				.setWindowId(windowId)
				.build();
		gridView = builder.getGridView()
				.setWindowId(windowId)
				.build();
		details = ImmutableMap.copyOf(builder.buildDetails());
		allDetails = ImmutableMap.copyOf(builder.buildAllDetails());
		sideListView = builder.getSideList();

		debugProperties = DebugProperties.ofNullableMap(builder.debugProperties);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("windowId", windowId)
				.add("singleRowLayout", singleRowLayout)
				.add("gridView", gridView)
				.add("details", details.isEmpty() ? null : details)
				.add("sideList", sideListView)
				.toString();
	}

	public WindowId getWindowId()
	{
		return windowId;
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public DocumentLayoutElementDescriptor getDocumentSummaryElement()
	{
		return documentSummaryElement;
	}

	public DocumentLayoutElementDescriptor getDocActionElement()
	{
		return docActionElement;
	}

	public DocumentLayoutSingleRow getSingleRowLayout()
	{
		return singleRowLayout;
	}

	/**
	 * @return the layout for grid view (for header documents)
	 */
	public ViewLayout getGridViewLayout()
	{
		return gridView;
	}

	public ViewLayout getSideListViewLayout()
	{
		return sideListView;
	}

	/**
	 * the this instance's "direct" details, without their included sub-details.
	 */
	public Collection<DocumentLayoutDetailDescriptor> getDetails()
	{
		return details.values();
	}

	/**
	 * @throws DocumentLayoutDetailNotFoundException
	 */
	public DocumentLayoutDetailDescriptor getDetail(final DetailId detailId)
	{
		final DocumentLayoutDetailDescriptor detail = allDetails.get(detailId);
		if (detail == null)
		{
			throw new DocumentLayoutDetailNotFoundException("Tab '" + detailId + "' was not found. Available tabs are: " + details.keySet());
		}

		return detail;
	}

	public static final class Builder
	{
		private static final Logger logger = LogManager.getLogger(DocumentLayoutDescriptor.Builder.class);

		private WindowId windowId;
		private ITranslatableString caption = TranslatableStrings.empty();
		@Nullable private DocumentLayoutElementDescriptor documentSummaryElement;
		@Nullable private DocumentLayoutElementDescriptor docActionElement;

		private DocumentLayoutSingleRow.Builder singleRowLayout;
		private ViewLayout.Builder _gridView;
		private ViewLayout _sideListView;

		private final List<DocumentLayoutDetailDescriptor> details = new ArrayList<>();

		private final Map<String, String> debugProperties = new LinkedHashMap<>();
		private Stopwatch stopwatch;

		private Builder()
		{
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("windowId", windowId)
					.toString();
		}

		public DocumentLayoutDescriptor build()
		{
			//
			// Debug informations:
			putDebugProperty("generator-thread", Thread.currentThread().getName());
			putDebugProperty("generator-timestamp", Instant.now().toString());
			if (stopwatch != null)
			{
				putDebugProperty("generator-duration", stopwatch.toString());
			}

			return new DocumentLayoutDescriptor(this);
		}

		private Map<DetailId, DocumentLayoutDetailDescriptor> buildDetails()
		{
			final ImmutableMap.Builder<DetailId, DocumentLayoutDetailDescriptor> map = ImmutableMap.builder();
			for (final DocumentLayoutDetailDescriptor detail : details)
			{
				putIfNotEmpty(detail, map);
			}
			return map.build();
		}

		private Map<DetailId, DocumentLayoutDetailDescriptor> buildAllDetails()
		{
			final ImmutableMap.Builder<DetailId, DocumentLayoutDetailDescriptor> map = ImmutableMap.builder();
			for (final DocumentLayoutDetailDescriptor detail : details)
			{
				buildDetailsRecurse(detail, map);
			}
			return map.build();
		}

		private void buildDetailsRecurse(
				@NonNull final DocumentLayoutDetailDescriptor detail,
				@NonNull final ImmutableMap.Builder<DetailId, DocumentLayoutDetailDescriptor> map)
		{
			putIfNotEmpty(detail, map);
			for (final DocumentLayoutDetailDescriptor subDetail : detail.getSubTabLayouts())
			{
				buildDetailsRecurse(subDetail, map);
			}
		}

		private void putIfNotEmpty(final DocumentLayoutDetailDescriptor detail, final ImmutableMap.Builder<DetailId, DocumentLayoutDetailDescriptor> map)
		{
			if (detail.isEmpty())
			{
				return;
			}
			map.put(detail.getDetailId(), detail);
		}

		public Builder setWindowId(final WindowId windowId)
		{
			this.windowId = windowId;
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this.caption = TranslatableStrings.nullToEmpty(caption);
			return this;
		}

		public Builder setDocumentSummaryElement(@Nullable final DocumentLayoutElementDescriptor documentSummaryElement)
		{
			this.documentSummaryElement = documentSummaryElement;
			return this;
		}

		public Builder setDocActionElement(@Nullable final DocumentLayoutElementDescriptor docActionElement)
		{
			this.docActionElement = docActionElement;
			return this;
		}

		public Builder setGridView(final ViewLayout.Builder gridView)
		{
			this._gridView = gridView;
			return this;
		}

		public Builder setSingleRowLayout(@NonNull final DocumentLayoutSingleRow.Builder singleRowLayout)
		{
			this.singleRowLayout = singleRowLayout;
			return this;
		}

		private DocumentLayoutSingleRow.Builder getSingleRowLayout()
		{
			return singleRowLayout;
		}

		private ViewLayout.Builder getGridView()
		{
			return _gridView;
		}

		public Builder addDetail(@Nullable final DocumentLayoutDetailDescriptor detail)
		{
			if (detail == null)
			{
				return this;
			}

			if (detail.isEmpty())
			{
				logger.trace("Skip adding detail to layout because it is empty; detail={}", detail);
				return this;
			}
			details.add(detail);

			return this;
		}

		public Builder setSideListView(final ViewLayout sideListViewLayout)
		{
			this._sideListView = sideListViewLayout;
			return this;
		}

		private ViewLayout getSideList()
		{
			Preconditions.checkNotNull(_sideListView, "sideList");
			return _sideListView;
		}

		public Builder putDebugProperty(final String name, final String value)
		{
			debugProperties.put(name, value);
			return this;
		}

		public Builder setStopwatch(final Stopwatch stopwatch)
		{
			this.stopwatch = stopwatch;
			return this;
		}
	}
}
