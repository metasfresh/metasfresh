package de.metas.ui.web.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.json.JSONDocumentFilter;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.references.DocumentReferenceId;
import de.metas.ui.web.process.view.ViewActionDescriptorsFactory;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.view.json.JSONFilterViewRequest;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

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
 */
@Value
public final class CreateViewRequest
{
	public static Builder builder(final WindowId windowId)
	{
		final ViewId viewId = ViewId.random(windowId);
		return new Builder(viewId, JSONViewDataType.grid);
	}

	public static Builder builder(final WindowId windowId, final JSONViewDataType viewType)
	{
		final ViewId viewId = ViewId.random(windowId);
		return new Builder(viewId, viewType);
	}

	public static Builder builder(final ViewId viewId, final JSONViewDataType viewType)
	{
		return new Builder(viewId, viewType);
	}

	public static Builder filterViewBuilder(
			@NonNull final IView view,
			@NonNull final JSONFilterViewRequest filterViewRequest)
	{
		return filterViewBuilder(view)
				.setFiltersFromJSON(filterViewRequest.getFilters());
	}

	public static Builder filterViewBuilder(@NonNull final IView view)
	{
		return builder(view.getViewId().getWindowId(), view.getViewType())
				.setProfileId(view.getProfileId())
				.setParentViewId(view.getParentViewId())
				.setParentRowId(view.getParentRowId())
				.setReferencingDocumentPaths(view.getReferencingDocumentPaths())
				.setDocumentReferenceId(view.getDocumentReferenceId())
				.setStickyFilters(view.getStickyFilters())
				// .setFiltersFromJSON(jsonFilters)
				// .setFilterOnlyIds(filterOnlyIds) // N/A on this level.
				.setUseAutoFilters(false)
				.addActions(view.getActions())
				.addAdditionalRelatedProcessDescriptors(view.getAdditionalRelatedProcessDescriptors())
				.setParameters(view.getParameters());
	}

	public static Builder deleteStickyFilterBuilder(
			@NonNull final IView view,
			@NonNull final String stickyFilterIdToDelete)
	{
		final DocumentFilterList stickyFilters = view.getStickyFilters()
				.stream()
				.filter(stickyFilter -> !Objects.equals(stickyFilter.getFilterId(), stickyFilterIdToDelete))
				.collect(DocumentFilterList.toDocumentFilterList());

		// FIXME: instead of removing all referencing document paths (to prevent creating sticky filters from them),
		// we shall remove only those is are related to "stickyFilterIdToDelete".
		final Set<DocumentPath> referencingDocumentPaths = ImmutableSet.of(); // view.getReferencingDocumentPaths();

		return builder(view.getViewId().getWindowId(), view.getViewType())
				.setProfileId(view.getProfileId())
				.setParentViewId(view.getParentViewId())
				.setParentRowId(view.getParentRowId())
				.setReferencingDocumentPaths(referencingDocumentPaths)
				.setDocumentReferenceId(view.getDocumentReferenceId())
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

	ImmutableSet<DocumentPath> referencingDocumentPaths;
	DocumentReferenceId documentReferenceId;

	/**
	 * Sticky filters can't be changed by the user.<br>
	 * Use them to e.g. restrict the available HUs to a particular warehouse if it doesn't make sense to select HUs from foreign WHs in a given window.
	 * <p>
	 * Multiple sticky filters are always <code>AND</code>ed.
	 */
	DocumentFilterList stickyFilters;

	/**
	 * Filters can be changed by the user.
	 * <p>
	 * Multiple filters are always <code>AND</code>ed.
	 */
	@Getter(AccessLevel.PRIVATE)
	WrappedDocumentFilterList filters;

	/**
	 * This one is becoming kind of legacy.... it's a particular kind of sticky filter which filters by given IDs.<br>
	 * <b>Important:</b> never null, empty means "no restriction"
	 *
	 * @deprecated please rather use {@link #getFilters()} {@link #getStickyFilters()}.
	 */
	@Deprecated
	ImmutableSet<Integer> filterOnlyIds;

	boolean useAutoFilters;

	ViewActionDescriptorsList actions;
	ImmutableList<RelatedProcessDescriptor> additionalRelatedProcessDescriptors;

	ImmutableMap<String, Object> parameters;

	boolean applySecurityRestrictions;

	private CreateViewRequest(final Builder builder)
	{
		viewId = builder.getViewId();
		viewType = builder.getViewType();
		profileId = builder.getProfileId();

		parentViewId = builder.getParentViewId();
		parentRowId = builder.getParentRowId();

		referencingDocumentPaths = builder.getReferencingDocumentPaths();
		documentReferenceId = builder.getDocumentReferenceId();
		filterOnlyIds = builder.getFilterOnlyIds();
		filters = builder.getFilters();
		stickyFilters = builder.getStickyFilters();
		useAutoFilters = builder.isUseAutoFilters();

		actions = builder.getActions();
		additionalRelatedProcessDescriptors = ImmutableList.copyOf(builder.getAdditionalRelatedProcessDescriptors());

		parameters = builder.getParameters();

		applySecurityRestrictions = builder.isApplySecurityRestrictions();
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
		return CollectionUtils.singleElement(getFilterOnlyIds());
	}

	public DocumentFilterList getFiltersUnwrapped(final DocumentFilterDescriptorsProvider descriptors)
	{
		return getFilters().unwrap(descriptors);
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

	@Nullable
	public <T> T getParameterAs(@NonNull final String parameterName, @NonNull final Class<T> type)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)getParameters().get(parameterName);
		return value;
	}

	@Nullable
	public <T> Set<T> getParameterAsSet(@NonNull final String parameterName, @NonNull final Class<T> type)
	{
		@SuppressWarnings("unchecked")
		final Set<T> value = (Set<T>)getParameters().get(parameterName);
		return value;
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
		private DocumentReferenceId documentReferenceId;

		/**
		 * @deprecated see {@link CreateViewRequest#filterOnlyIds}
		 */
		@Deprecated
		private LinkedHashSet<Integer> filterOnlyIds;

		private ArrayList<DocumentFilter> stickyFilters;
		private WrappedDocumentFilterList filters;
		private boolean useAutoFilters;

		private ViewActionDescriptorsList actions = ViewActionDescriptorsList.EMPTY;
		private final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors = new ArrayList<>();

		private LinkedHashMap<String, Object> parameters;

		private boolean applySecurityRestrictions = true;

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

		private ImmutableSet<DocumentPath> getReferencingDocumentPaths()
		{
			return referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);
		}

		public Builder setDocumentReferenceId(DocumentReferenceId documentReferenceId)
		{
			this.documentReferenceId = documentReferenceId;
			return this;
		}

		private DocumentReferenceId getDocumentReferenceId()
		{
			return documentReferenceId;
		}

		public Builder setStickyFilters(final DocumentFilterList stickyFilters)
		{
			this.stickyFilters = stickyFilters != null ? new ArrayList<>(stickyFilters.toList()) : null;
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

		private DocumentFilterList getStickyFilters()
		{
			return DocumentFilterList.ofList(stickyFilters);
		}

		public Builder setFiltersFromJSON(final List<JSONDocumentFilter> jsonFilters)
		{
			filters = WrappedDocumentFilterList.ofJSONFilters(jsonFilters);
			return this;
		}

		public Builder setFilters(final DocumentFilterList filters)
		{
			this.filters = WrappedDocumentFilterList.ofFilters(filters);
			return this;
		}

		private WrappedDocumentFilterList getFilters()
		{
			return filters != null ? filters : WrappedDocumentFilterList.EMPTY;
		}

		@Deprecated
		public Builder setFilterOnlyIds(final Collection<Integer> filterOnlyIds)
		{
			if (this.filterOnlyIds == null)
			{
				this.filterOnlyIds = new LinkedHashSet<>();
			}
			this.filterOnlyIds.addAll(filterOnlyIds);
			return this;
		}

		@Deprecated
		public Builder addFilterOnlyId(final int filterOnlyId)
		{
			if (filterOnlyIds == null)
			{
				filterOnlyIds = new LinkedHashSet<>();
			}
			filterOnlyIds.add(filterOnlyId);
			return this;
		}

		@Deprecated
		private ImmutableSet<Integer> getFilterOnlyIds()
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

		public Builder setParameter(@NonNull final String name, @Nullable final Object value)
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
				}
				parameters.put(name, value);
			}

			return this;
		}

		public Builder setParameters(@NonNull final Map<String, Object> parameters)
		{
			parameters.forEach(this::setParameter);
			return this;
		}

		private ImmutableMap<String, Object> getParameters()
		{
			return parameters != null ? ImmutableMap.copyOf(parameters) : ImmutableMap.of();
		}

		public Builder applySecurityRestrictions(final boolean applySecurityRestrictions)
		{
			this.applySecurityRestrictions = applySecurityRestrictions;
			return this;
		}

		private boolean isApplySecurityRestrictions()
		{
			return applySecurityRestrictions;
		}
	}

	//
	//
	//
	//
	//
	@ToString
	private static final class WrappedDocumentFilterList
	{
		public static WrappedDocumentFilterList ofFilters(final DocumentFilterList filters)
		{
			if (filters == null || filters.isEmpty())
			{
				return EMPTY;
			}

			final ImmutableList<JSONDocumentFilter> jsonFiltersEffective = null;
			final DocumentFilterList filtersEffective = filters;
			return new WrappedDocumentFilterList(jsonFiltersEffective, filtersEffective);
		}

		public static WrappedDocumentFilterList ofJSONFilters(final List<JSONDocumentFilter> jsonFilters)
		{
			if (jsonFilters == null || jsonFilters.isEmpty())
			{
				return EMPTY;
			}

			final ImmutableList<JSONDocumentFilter> jsonFiltersEffective = ImmutableList.copyOf(jsonFilters);
			final DocumentFilterList filtersEffective = null;
			return new WrappedDocumentFilterList(jsonFiltersEffective, filtersEffective);
		}

		public static final WrappedDocumentFilterList EMPTY = new WrappedDocumentFilterList();

		private final ImmutableList<JSONDocumentFilter> jsonFilters;
		private final DocumentFilterList filters;

		private WrappedDocumentFilterList(final ImmutableList<JSONDocumentFilter> jsonFilters, final DocumentFilterList filters)
		{
			this.jsonFilters = jsonFilters;
			this.filters = filters;
		}

		/**
		 * empty constructor
		 */
		private WrappedDocumentFilterList()
		{
			filters = DocumentFilterList.EMPTY;
			jsonFilters = null;
		}

		public DocumentFilterList unwrap(final DocumentFilterDescriptorsProvider descriptors)
		{
			if (filters != null)
			{
				return filters;
			}

			if (jsonFilters == null || jsonFilters.isEmpty())
			{
				return DocumentFilterList.EMPTY;
			}

			return JSONDocumentFilter.unwrapList(jsonFilters, descriptors);
		}
	}
}
