package de.metas.ui.web.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.collections.ListUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.DocumentFiltersList;
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
@lombok.Value
public final class CreateViewRequest
{
	public static final Builder builder(final WindowId windowId, final JSONViewDataType viewType)
	{
		final ViewId viewId = ViewId.random(windowId);
		return new Builder(viewId, viewType);
	}

	public static final Builder builder(final ViewId viewId, final JSONViewDataType viewType)
	{
		return new Builder(viewId, viewType);
	}

	public static final Builder filterViewBuilder(
			@NonNull final IView view,
			@NonNull final JSONFilterViewRequest filterViewRequest)
	{
		final List<JSONDocumentFilter> jsonFilters = filterViewRequest.getFilters();

		return builder(view.getViewId().getWindowId(), view.getViewType())
				.setProfileId(view.getProfileId())
				.setParentViewId(view.getParentViewId())
				.setParentRowId(view.getParentRowId())
				.setReferencingDocumentPaths(view.getReferencingDocumentPaths())
				.setStickyFilters(view.getStickyFilters())
				.setFiltersFromJSON(jsonFilters)
				// .setFilterOnlyIds(filterOnlyIds) // N/A on this level.
				.setUseAutoFilters(false)
				.addActions(view.getActions())
				.addAdditionalRelatedProcessDescriptors(view.getAdditionalRelatedProcessDescriptors());
	}

	public static final Builder deleteStickyFilterBuilder(
			@NonNull final IView view,
			@NonNull final String stickyFilterIdToDelete)
	{
		final List<DocumentFilter> stickyFilters = view.getStickyFilters()
				.stream()
				.filter(stickyFilter -> !Objects.equals(stickyFilter.getFilterId(), stickyFilterIdToDelete))
				.collect(ImmutableList.toImmutableList());

		// FIXME: instead of removing all referencing document paths (to prevent creating sticky filters from them),
		// we shall remove only those is are related to "stickyFilterIdToDelete".
		final Set<DocumentPath> referencingDocumentPaths = ImmutableSet.of(); // view.getReferencingDocumentPaths();

		return builder(view.getViewId().getWindowId(), view.getViewType())
				.setProfileId(view.getProfileId())
				.setParentViewId(view.getParentViewId())
				.setParentRowId(view.getParentRowId())
				.setReferencingDocumentPaths(referencingDocumentPaths)
				.setStickyFilters(stickyFilters)
				.setFilters(view.getFilters())
				// .setFilterOnlyIds(filterOnlyIds) // N/A on this level.
				.setUseAutoFilters(false)
				.addActions(view.getActions())
				.addAdditionalRelatedProcessDescriptors(view.getAdditionalRelatedProcessDescriptors());
	}

	ViewId viewId;
	JSONViewDataType viewType;
	ViewProfileId profileId;

	ViewId parentViewId;
	DocumentId parentRowId;

	Set<DocumentPath> referencingDocumentPaths;

	/**
	 * Sticky filters can't be changed by the user.<br>
	 * Use them to e.g. restrict the available HUs to a particular warehouse if it doesn't make sense to select HUs from foreign WHs in a given window.
	 * <p>
	 * Multiple sticky filters are always <code>AND</code>ed.
	 */
	List<DocumentFilter> stickyFilters;

	/**
	 * Filters can be changed by the user.
	 * <p>
	 * Multiple filters are always <code>AND</code>ed.
	 */
	DocumentFiltersList filters;

	/**
	 * This one is becoming kind of legacy.... it's a particular kind of sticky filter which filters by given IDs.<br>
	 * <b>Important:</b> never null, empty means "no restriction"
	 * 
	 * @deprecated please rather use {@link #getFilters()} {@link #getStickyFilters()}.
	 */
	@Deprecated
	Set<Integer> filterOnlyIds;

	boolean useAutoFilters;

	ViewActionDescriptorsList actions;
	ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;
	
	ImmutableMap<String, Object> parameters;


	private CreateViewRequest(final Builder builder)
	{
		viewId = builder.getViewId();
		viewType = builder.getViewType();
		profileId = builder.getProfileId();

		parentViewId = builder.getParentViewId();
		parentRowId = builder.getParentRowId();

		referencingDocumentPaths = builder.getReferencingDocumentPaths();
		filterOnlyIds = builder.getFilterOnlyIds();
		filters = builder.getFilters();
		stickyFilters = builder.getStickyFilters();
		useAutoFilters = builder.isUseAutoFilters();

		actions = builder.getActions();
		additionalRelatedProcessDescriptors = ImmutableList.copyOf(builder.getAdditionalRelatedProcessDescriptors());
		
		parameters = builder.getParameters();
	}

	private CreateViewRequest(@NonNull final CreateViewRequest from, @NonNull final DocumentFiltersList filters)
	{
		viewId = from.viewId;
		viewType = from.viewType;
		profileId = from.profileId;

		parentViewId = from.parentViewId;
		parentRowId = from.parentRowId;

		referencingDocumentPaths = from.referencingDocumentPaths;
		filterOnlyIds = from.filterOnlyIds;
		this.filters = filters;
		stickyFilters = from.stickyFilters;
		useAutoFilters = from.useAutoFilters;

		actions = from.actions;
		additionalRelatedProcessDescriptors = from.additionalRelatedProcessDescriptors;
		
		parameters = from.parameters;
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

	public CreateViewRequest unwrapFiltersAndCopy(final DocumentFilterDescriptorsProvider descriptors)
	{
		final DocumentFiltersList filters = getFilters();
		final DocumentFiltersList filtersNew = filters.unwrapAndCopy(descriptors);
		if (Objects.equals(filters, filtersNew))
		{
			return this;
		}

		return new CreateViewRequest(this, filtersNew);
	}

	public void assertNoParentViewOrRow()
	{
		if (parentViewId != null)
		{
			throw new AdempiereException("parentViewId '" + parentViewId + "' is not supported in " + this);
		}
		if (parentRowId != null)
		{
			throw new AdempiereException("parentViewId '" + parentRowId + "' is not supported in " + this);
		}
	}

	//
	//
	//
	//
	//
	public static final class Builder
	{
		private final ViewId viewId;
		private final JSONViewDataType viewType;
		private ViewProfileId profileId = ViewProfileId.NULL;

		private ViewId parentViewId;
		private DocumentId parentRowId;

		private Set<DocumentPath> referencingDocumentPaths;

		/**
		 * @deprecated see {@link CreateViewRequest#filterOnlyIds}
		 */
		@Deprecated
		private Set<Integer> filterOnlyIds;

		private List<DocumentFilter> stickyFilters;
		private DocumentFiltersList filters;
		private boolean useAutoFilters;

		private ViewActionDescriptorsList actions = ViewActionDescriptorsList.EMPTY;
		private final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors = new ArrayList<>();
		
		private LinkedHashMap<String, Object> parameters;

		private Builder(
				@NonNull final ViewId viewId,
				@NonNull final JSONViewDataType viewType)
		{
			this.viewId = viewId;
			this.viewType = viewType;
		}

		public CreateViewRequest build()
		{
			return new CreateViewRequest(this);
		}

		public ViewId getViewId()
		{
			return viewId;
		}

		private JSONViewDataType getViewType()
		{
			return viewType;
		}

		public Builder setProfileId(ViewProfileId profileId)
		{
			this.profileId = profileId;
			return this;
		}

		private ViewProfileId getProfileId()
		{
			return profileId;
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

		public Builder setUseAutoFilters(boolean useAutoFilters)
		{
			this.useAutoFilters = useAutoFilters;
			return this;
		}

		private boolean isUseAutoFilters()
		{
			return useAutoFilters;
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

		public Builder setParameter(final String name, final Object value)
		{
			if (value == null)
			{
				if (parameters != null)
				{
					parameters.remove(name);
				}
			}
			else
			{
				if (parameters == null)
				{
					parameters = new LinkedHashMap<>();
					parameters.put(name, value);
				}
			}

			return this;
		}

		private ImmutableMap<String, Object> getParameters()
		{
			return parameters != null ? ImmutableMap.copyOf(parameters) : ImmutableMap.of();
		}
	}
}
