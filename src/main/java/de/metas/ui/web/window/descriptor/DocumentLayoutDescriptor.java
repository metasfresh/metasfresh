package de.metas.ui.web.window.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.exceptions.DocumentLayoutDetailNotFoundException;

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
	public static final Builder builder()
	{
		return new Builder();
	}

	private final WindowId windowId;
	private final ITranslatableString caption;

	/** Special element: Document summary */
	private final DocumentLayoutElementDescriptor documentSummaryElement;
	/** Special element: DocStatus/DocAction */
	private final DocumentLayoutElementDescriptor docActionElement;

	private final DocumentLayoutSingleRow singleRowLayout;
	private final ViewLayout gridView;
	/** Side list layout */
	private final ViewLayout sideListView;

	/** Single row layout: included tabs */
	private final Map<DetailId, DocumentLayoutDetailDescriptor> details;

	/** Misc debugging properties */
	private final Map<String, String> debugProperties;

	private DocumentLayoutDescriptor(final Builder builder)
	{
		super();
		windowId = builder.windowId;
		Check.assumeNotNull(windowId, "Parameter windowId is not null");

		caption = builder.caption;

		documentSummaryElement = builder.documentSummaryElement;
		docActionElement = builder.docActionElement;

		singleRowLayout = builder.getSingleRowLayout()
				.setWindowId(windowId)
				.build();
		gridView = builder.getGridView()
				.setWindowId(windowId)
				.build();
		details = ImmutableMap.copyOf(builder.buildDetails());
		sideListView = builder.getSideList();

		debugProperties = ImmutableMap.copyOf(builder.debugProperties);
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

	public Collection<DocumentLayoutDetailDescriptor> getDetails()
	{
		return details.values();
	}

	/**
	 *
	 * @param detailId
	 * @return detail
	 * @throws DocumentLayoutDetailNotFoundException
	 */
	public DocumentLayoutDetailDescriptor getDetail(final DetailId detailId)
	{
		final DocumentLayoutDetailDescriptor detail = details.get(detailId);
		if (detail == null)
		{
			throw new DocumentLayoutDetailNotFoundException("Tab '" + detailId + "' was not found. Available tabs are: " + details.keySet());
		}

		return detail;
	}

	public Map<String, String> getDebugProperties()
	{
		return debugProperties;
	}

	public static final class Builder
	{

		private static final Logger logger = LogManager.getLogger(DocumentLayoutDescriptor.Builder.class);

		private WindowId windowId;
		private ITranslatableString caption = ImmutableTranslatableString.empty();
		private DocumentLayoutElementDescriptor documentSummaryElement;
		private DocumentLayoutElementDescriptor docActionElement;

		private DocumentLayoutSingleRow.Builder singleRowLayout;
		private ViewLayout.Builder _gridView;
		private ViewLayout _sideListView;

		private final List<DocumentLayoutDetailDescriptor.Builder> detailsBuilders = new ArrayList<>();

		private final Map<String, String> debugProperties = new LinkedHashMap<>();
		private Stopwatch stopwatch;

		private Builder()
		{
			super();
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
			putDebugProperty("generator-timestamp", new java.util.Date().toString());
			if (stopwatch != null)
			{
				putDebugProperty("generator-duration", stopwatch.toString());
			}

			return new DocumentLayoutDescriptor(this);
		}

		private Map<DetailId, DocumentLayoutDetailDescriptor> buildDetails()
		{
			return detailsBuilders
					.stream()
					.map(detailBuilder -> detailBuilder.build())
					.filter(detail -> !detail.isEmpty())
					.collect(GuavaCollectors.toImmutableMapByKey(detail -> detail.getDetailId()));
		}

		public Builder setWindowId(WindowId windowId)
		{
			this.windowId = windowId;
			return this;
		}

		public Builder setCaption(ITranslatableString caption)
		{
			this.caption = caption == null ? ImmutableTranslatableString.empty() : caption;
			return this;
		}

		public Builder setDocumentSummaryElement(DocumentLayoutElementDescriptor documentSummaryElement)
		{
			this.documentSummaryElement = documentSummaryElement;
			return this;
		}

		public Builder setDocActionElement(final DocumentLayoutElementDescriptor docActionElement)
		{
			this.docActionElement = docActionElement;
			return this;
		}

		public Builder setGridView(final ViewLayout.Builder gridView)
		{
			this._gridView = gridView;
			return this;
		}

		public Builder setSingleRowLayout(DocumentLayoutSingleRow.Builder singleRowLayout)
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

		/**
		 * Adds detail/tab if it's valid.
		 *
		 * @param detailBuilder detail/tab builder
		 */
		public Builder addDetailIfValid(@Nullable final DocumentLayoutDetailDescriptor.Builder detailBuilder)
		{
			if (detailBuilder == null)
			{
				return this;
			}

			if (detailBuilder.isEmpty())
			{
				logger.trace("Skip adding detail tab to layout because it does not have elements: {}", detailBuilder);
				return this;
			}

			detailsBuilders.add(detailBuilder);
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
