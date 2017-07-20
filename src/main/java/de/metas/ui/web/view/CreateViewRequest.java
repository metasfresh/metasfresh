package de.metas.ui.web.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.collections.ListUtils;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import de.metas.ui.web.process.view.ViewActionDescriptorsFactory;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
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
		final List<JSONDocumentFilter> jsonFilters = filterViewRequest.getFilters();

		return builder(view.getViewId().getWindowId(), view.getViewType())
				.setParentViewId(view.getParentViewId())
				.setParentRowId(view.getParentRowId())
				.setReferencingDocumentPaths(view.getReferencingDocumentPaths())
				.setStickyFilters(view.getStickyFilters())
				.setFiltersFromJSON(jsonFilters)
				// .setFilterOnlyIds(filterOnlyIds) // N/A on this level.
				.addActions(view.getActions())
				.addAdditionalRelatedProcessDescriptors(view.getAdditionalRelatedProcessDescriptors())
				;
	}

	public static final Builder deleteStickyFilterBuilder(@NonNull final IView view, @NonNull final String stickyFilterIdToDelete)
	{
		final List<DocumentFilter> stickyFilters = view.getStickyFilters()
				.stream()
				.filter(stickyFilter -> !Objects.equals(stickyFilter.getFilterId(), stickyFilterIdToDelete))
				.collect(ImmutableList.toImmutableList());

		// FIXME: instead of removing all referencing document paths (to prevent creating sticky filters from them),
		// we shall remove only those is are related to "stickyFilterIdToDelete". 
		final Set<DocumentPath> referencingDocumentPaths = ImmutableSet.of(); // view.getReferencingDocumentPaths();
		
		return builder(view.getViewId().getWindowId(), view.getViewType())
				.setParentViewId(view.getParentViewId())
				.setParentRowId(view.getParentRowId())
				.setReferencingDocumentPaths(referencingDocumentPaths)
				.setStickyFilters(stickyFilters)
				.setFilters(view.getFilters())
				// .setFilterOnlyIds(filterOnlyIds) // N/A on this level.
				.addActions(view.getActions())
				.addAdditionalRelatedProcessDescriptors(view.getAdditionalRelatedProcessDescriptors());
	}

	private final WindowId windowId;
	private final JSONViewDataType viewType;

	private final ViewId parentViewId;
	private final DocumentId parentRowId;

	private final Set<DocumentPath> referencingDocumentPaths;
	private final List<DocumentFilter> stickyFilters;
	private final DocumentFiltersList filters;
	private final Set<Integer> filterOnlyIds;

	private final ViewActionDescriptorsList actions;
	private final ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;
	
	private CreateViewRequest(final Builder builder)
	{
		windowId = builder.getWindowId();
		viewType = builder.getViewType();

		parentViewId = builder.getParentViewId();
		parentRowId = builder.getParentRowId();

		referencingDocumentPaths = builder.getReferencingDocumentPaths();
		filterOnlyIds = builder.getFilterOnlyIds();
		filters = builder.getFilters();
		stickyFilters = builder.getStickyFilters();

		actions = builder.getActions();
		additionalRelatedProcessDescriptors = ImmutableList.copyOf(builder.getAdditionalRelatedProcessDescriptors());
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

	public List<DocumentFilter> getOrUnwrapFilters(final DocumentFilterDescriptorsProvider descriptors)
	{
		return getFilters().getOrUnwrapFilters(descriptors);
	}

	//
	//
	//
	//
	//
	public static final class Builder
	{
		private final WindowId windowId;
		private final JSONViewDataType viewType;

		private ViewId parentViewId;
		private DocumentId parentRowId;

		private Set<DocumentPath> referencingDocumentPaths;
		private Set<Integer> filterOnlyIds;
		private List<DocumentFilter> stickyFilters;
		private DocumentFiltersList filters;

		private ViewActionDescriptorsList actions = ViewActionDescriptorsList.EMPTY;
		private final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors = new ArrayList<>();

		private Builder(
				@NonNull final WindowId windowId, 
				@NonNull final JSONViewDataType viewType)
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
		
		public Builder setParentRowId(DocumentId parentRowId)
		{
			this.parentRowId = parentRowId;
			return this;
		}
		
		private DocumentId getParentRowId()
		{
			return parentRowId;
		}

		public Builder setReferencingDocumentPaths(final Set<DocumentPath> referencingDocumentPaths)
		{
			this.referencingDocumentPaths = referencingDocumentPaths;
			return this;
		}

		public Builder setReferencingDocumentPath(final DocumentPath referencingDocumentPath)
		{
			setReferencingDocumentPaths(ImmutableSet.of(referencingDocumentPath));
			return this;
		}

		private Set<DocumentPath> getReferencingDocumentPaths()
		{
			return referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);
		}

		public Builder setStickyFilters(final List<DocumentFilter> stickyFilters)
		{
			this.stickyFilters = stickyFilters;
			return this;
		}

		public Builder addStickyFilters(@NonNull final DocumentFilter stickyFilter)
		{
			if (stickyFilters == null)
			{
				stickyFilters = new ArrayList<>();
			}
			stickyFilters.add(stickyFilter);
			return this;
		}

		private List<DocumentFilter> getStickyFilters()
		{
			return stickyFilters == null ? ImmutableList.of() : ImmutableList.copyOf(stickyFilters);
		}

		public Builder setFiltersFromJSON(final List<JSONDocumentFilter> jsonFilters)
		{
			filters = DocumentFiltersList.ofJSONFilters(jsonFilters);
			return this;
		}

		public Builder setFilters(final List<DocumentFilter> filters)
		{
			this.filters = DocumentFiltersList.ofFilters(filters);
			return this;
		}

		private DocumentFiltersList getFilters()
		{
			return filters != null ? filters : DocumentFiltersList.EMPTY;
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
			addActions(actionsToAdd);
			return this;
		}

		public Builder addActions(final ViewActionDescriptorsList actionsToAdd)
		{
			actions = actions.mergeWith(actionsToAdd);
			return this;
		}

		private ViewActionDescriptorsList getActions()
		{
			return actions;
		}
		
		private List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
		{
			return additionalRelatedProcessDescriptors;
		}
		
		public Builder addAdditionalRelatedProcessDescriptor(@NonNull final RelatedProcessDescriptor relatedProcessDescriptor)
		{
			additionalRelatedProcessDescriptors.add(relatedProcessDescriptor);
			return this;
		}

		public Builder addAdditionalRelatedProcessDescriptors(@NonNull final List<RelatedProcessDescriptor> relatedProcessDescriptors)
		{
			additionalRelatedProcessDescriptors.addAll(relatedProcessDescriptors);
			return this;
		}

	}

	public static final class DocumentFiltersList
	{
		private static DocumentFiltersList ofFilters(final List<DocumentFilter> filters)
		{
			if (filters == null || filters.isEmpty())
			{
				return EMPTY;
			}

			final List<JSONDocumentFilter> jsonFiltersEffective = null;
			final List<DocumentFilter> filtersEffective = ImmutableList.copyOf(filters);
			return new DocumentFiltersList(jsonFiltersEffective, filtersEffective);
		}

		private static DocumentFiltersList ofJSONFilters(final List<JSONDocumentFilter> jsonFilters)
		{
			if (jsonFilters == null || jsonFilters.isEmpty())
			{
				return EMPTY;
			}

			final List<JSONDocumentFilter> jsonFiltersEffective = ImmutableList.copyOf(jsonFilters);
			final List<DocumentFilter> filtersEffective = null;
			return new DocumentFiltersList(jsonFiltersEffective, filtersEffective);
		}

		private static final DocumentFiltersList EMPTY = new DocumentFiltersList();

		private final List<JSONDocumentFilter> jsonFilters;
		private final List<DocumentFilter> filters;

		private DocumentFiltersList(final List<JSONDocumentFilter> jsonFilters, final List<DocumentFilter> filters)
		{
			this.jsonFilters = jsonFilters;
			this.filters = filters;
		}

		/** empty constructor */
		private DocumentFiltersList()
		{
			filters = ImmutableList.of();
			jsonFilters = null;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this).omitNullValues().addValue(jsonFilters).addValue(filters).toString();
		}

		public boolean isJson()
		{
			return jsonFilters != null;
		}

		public List<JSONDocumentFilter> getJsonFilters()
		{
			if (jsonFilters == null)
			{
				throw new AdempiereException("Json filters are not available for " + this);
			}
			return jsonFilters;
		}

		public List<DocumentFilter> getFilters()
		{
			if (filters == null)
			{
				throw new AdempiereException("Filters are not available for " + this);
			}
			return filters;
		}

		private List<DocumentFilter> getOrUnwrapFilters(final DocumentFilterDescriptorsProvider descriptors)
		{
			if (filters != null)
			{
				return filters;
			}

			if (jsonFilters == null || jsonFilters.isEmpty())
			{
				return ImmutableList.of();
			}

			return JSONDocumentFilter.unwrapList(jsonFilters, descriptors);
		}
	}
}
