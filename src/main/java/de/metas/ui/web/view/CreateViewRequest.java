package de.metas.ui.web.view;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.collections.ListUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import de.metas.ui.web.process.view.ViewActionDescriptorsFactory;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Request to create a new {@link IView}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@lombok.Data
public final class CreateViewRequest
{
	public static final Builder builder(final WindowId windowId, final JSONViewDataType viewType)
	{
		return new Builder(windowId, viewType);
	}

	public static final Builder filterViewBuilder(@NonNull final IView view, @NonNull final JSONFilterViewRequest filterViewRequest)
	{
		return builder(view.getViewId().getWindowId(), view.getViewType())
				.setParentViewId(view.getParentViewId())
				.setReferencingDocumentPaths(view.getReferencingDocumentPaths())
				.setStickyFilters(view.getStickyFilters())
				.setFilters(filterViewRequest.getFilters())
		// .setFilterOnlyIds(filterOnlyIds) // N/A on this level.
		;
	}

	private final WindowId windowId;
	private final JSONViewDataType viewType;

	private final ViewId parentViewId;

	private final Set<DocumentPath> referencingDocumentPaths;
	private final List<DocumentFilter> stickyFilters;
	private final List<JSONDocumentFilter> filters;
	private final Set<Integer> filterOnlyIds;

	private final ViewActionDescriptorsList actions;

	private CreateViewRequest(final Builder builder)
	{
		windowId = builder.getWindowId();
		viewType = builder.getViewType();

		parentViewId = builder.getParentViewId();

		referencingDocumentPaths = builder.getReferencingDocumentPaths();
		stickyFilters = builder.getStickyFilters();
		filters = builder.getFilters();
		filterOnlyIds = builder.getFilterOnlyIds();

		actions = builder.getActions();
	}

	public ViewId getParentViewId()
	{
		return parentViewId;
	}

	public WindowId getWindowId()
	{
		return windowId;
	}

	public ViewActionDescriptorsList getActions()
	{
		return actions;
	}

	public Characteristic getViewTypeRequiredFieldCharacteristic()
	{
		Check.assumeNotNull(viewType, "Parameter viewType is not null for {}", this);
		return viewType.getRequiredFieldCharacteristic();
	}

	public DocumentPath getSingleReferencingDocumentPathOrNull()
	{
		final Set<DocumentPath> referencingDocumentPaths = getReferencingDocumentPaths();
		if (referencingDocumentPaths.isEmpty())
		{
			return null;
		}
		else
		{
			// NOTE: preserving the old logic and returning the first documentPath
			return referencingDocumentPaths.iterator().next();
		}
	}

	public int getSingleFilterOnlyId()
	{
		return ListUtils.singleElement(getFilterOnlyIds());
	}

	//
	//
	//
	public static final class Builder
	{
		private final WindowId windowId;
		private final JSONViewDataType viewType;

		private ViewId parentViewId;

		private Set<DocumentPath> referencingDocumentPaths;
		private List<DocumentFilter> stickyFilters;
		private List<JSONDocumentFilter> filters;
		private Set<Integer> filterOnlyIds;

		private ViewActionDescriptorsList actions = ViewActionDescriptorsList.EMPTY;

		private Builder(@NonNull final WindowId windowId, @NonNull final JSONViewDataType viewType)
		{
			this.windowId = windowId;
			this.viewType = viewType;
		}

		public CreateViewRequest build()
		{
			return new CreateViewRequest(this);
		}

		private WindowId getWindowId()
		{
			return windowId;
		}

		private JSONViewDataType getViewType()
		{
			return viewType;
		}

		public Builder setParentViewId(final ViewId parentViewId)
		{
			this.parentViewId = parentViewId;
			return this;
		}

		private ViewId getParentViewId()
		{
			return parentViewId;
		}

		public Builder setReferencingDocumentPaths(final Set<DocumentPath> referencingDocumentPaths)
		{
			this.referencingDocumentPaths = referencingDocumentPaths;
			return this;
		}

		private Set<DocumentPath> getReferencingDocumentPaths()
		{
			return referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);
		}

		public Builder setStickyFilters(List<DocumentFilter> stickyFilters)
		{
			this.stickyFilters = stickyFilters;
			return this;
		}

		private List<DocumentFilter> getStickyFilters()
		{
			return stickyFilters == null ? ImmutableList.of() : ImmutableList.copyOf(stickyFilters);
		}

		public Builder setFilters(final List<JSONDocumentFilter> filters)
		{
			this.filters = filters;
			return this;
		}

		private List<JSONDocumentFilter> getFilters()
		{
			return filters == null ? ImmutableList.of() : ImmutableList.copyOf(filters);
		}

		public Builder setFilterOnlyIds(final Collection<Integer> filterOnlyIds)
		{
			if (this.filterOnlyIds == null)
			{
				this.filterOnlyIds = new HashSet<>();
			}
			this.filterOnlyIds.addAll(filterOnlyIds);
			return this;
		}

		public Builder addFilterOnlyId(final int filterOnlyId)
		{
			if (filterOnlyIds == null)
			{
				filterOnlyIds = new HashSet<>();
			}
			filterOnlyIds.add(filterOnlyId);
			return this;
		}

		private Set<Integer> getFilterOnlyIds()
		{
			return filterOnlyIds == null ? ImmutableSet.of() : ImmutableSet.copyOf(filterOnlyIds);
		}

		public Builder addActionsFromUtilityClass(final Class<?> utilityClass)
		{
			final ViewActionDescriptorsList actionsToAdd = ViewActionDescriptorsFactory.instance.getFromClass(utilityClass);
			this.actions = this.actions.mergeWith(actionsToAdd);
			return this;
		}

		private ViewActionDescriptorsList getActions()
		{
			return actions;
		}
	}
}
