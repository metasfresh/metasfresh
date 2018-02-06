package de.metas.ui.web.handlingunits;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.process.view.ViewActionDescriptorsList;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
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

public final class HUEditorViewBuilder
{
	private ViewId parentViewId;
	private DocumentId parentRowId;
	private ViewId viewId;
	private JSONViewDataType viewType = JSONViewDataType.grid;

	private String referencingTableName;
	private Set<DocumentPath> referencingDocumentPaths;

	private ViewActionDescriptorsList actions = ViewActionDescriptorsList.EMPTY;
	private List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors = null;

	private List<DocumentFilter> stickyFilters;
	private List<DocumentFilter> filters;
	private DocumentFilterDescriptorsProvider filterDescriptors = NullDocumentFilterDescriptorsProvider.instance;

	private List<DocumentQueryOrderBy> orderBys = null;

	private LinkedHashMap<String, Object> parameters;
	private HUEditorViewRepository huEditorViewRepository;

	HUEditorViewBuilder()
	{
	}

	public HUEditorView build()
	{
		return new HUEditorView(this);
	}

	public HUEditorViewBuilder setParentViewId(final ViewId parentViewId)
	{
		this.parentViewId = parentViewId;
		return this;
	}

	ViewId getParentViewId()
	{
		return parentViewId;
	}

	public HUEditorViewBuilder setParentRowId(final DocumentId parentRowId)
	{
		this.parentRowId = parentRowId;
		return this;
	}

	DocumentId getParentRowId()
	{
		return parentRowId;
	}

	public HUEditorViewBuilder setViewId(final ViewId viewId)
	{
		this.viewId = viewId;
		return this;
	}

	@NonNull
	ViewId getViewId()
	{
		return viewId;
	}

	public HUEditorViewBuilder setViewType(@NonNull final JSONViewDataType viewType)
	{
		this.viewType = viewType;
		return this;
	}

	JSONViewDataType getViewType()
	{
		return viewType;
	}

	public HUEditorViewBuilder setReferencingDocumentPaths(final String referencingTableName, final Set<DocumentPath> referencingDocumentPaths)
	{
		this.referencingTableName = referencingTableName;
		this.referencingDocumentPaths = referencingDocumentPaths;
		return this;
	}

	public String getReferencingTableName()
	{
		return referencingTableName;
	}

	Set<DocumentPath> getReferencingDocumentPaths()
	{
		return referencingDocumentPaths == null ? ImmutableSet.of() : ImmutableSet.copyOf(referencingDocumentPaths);
	}

	public HUEditorViewBuilder setActions(@NonNull final ViewActionDescriptorsList actions)
	{
		this.actions = actions;
		return this;
	}

	ViewActionDescriptorsList getActions()
	{
		return actions;
	}

	public HUEditorViewBuilder setAdditionalRelatedProcessDescriptors(@NonNull final List<RelatedProcessDescriptor> additionalRelatedProcessDescriptors)
	{
		if (additionalRelatedProcessDescriptors == null || additionalRelatedProcessDescriptors.isEmpty())
		{
			this.additionalRelatedProcessDescriptors = null;
		}
		else
		{
			this.additionalRelatedProcessDescriptors = new ArrayList<>(additionalRelatedProcessDescriptors);
		}

		return this;
	}

	public HUEditorViewBuilder addAdditionalRelatedProcessDescriptor(@NonNull final RelatedProcessDescriptor descriptor)
	{
		if (additionalRelatedProcessDescriptors == null)
		{
			additionalRelatedProcessDescriptors = new ArrayList<>();
		}
		additionalRelatedProcessDescriptors.add(descriptor);

		return this;
	}

	public HUEditorViewBuilder addAdditionalRelatedProcessDescriptors(@NonNull final List<RelatedProcessDescriptor> descriptors)
	{
		if (additionalRelatedProcessDescriptors == null)
		{
			additionalRelatedProcessDescriptors = new ArrayList<>();
		}
		additionalRelatedProcessDescriptors.addAll(descriptors);

		return this;
	}

	ImmutableList<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return additionalRelatedProcessDescriptors != null && !additionalRelatedProcessDescriptors.isEmpty() ? ImmutableList.copyOf(additionalRelatedProcessDescriptors) : ImmutableList.of();
	}

	public HUEditorViewBuilder setFilterDescriptors(@NonNull final DocumentFilterDescriptorsProvider filterDescriptors)
	{
		this.filterDescriptors = filterDescriptors;
		return this;
	}

	DocumentFilterDescriptorsProvider getFilterDescriptors()
	{
		return filterDescriptors;
	}

	public HUEditorViewBuilder setStickyFilters(final List<DocumentFilter> stickyFilters)
	{
		this.stickyFilters = stickyFilters;
		return this;
	}

	private List<DocumentFilter> getStickyFilters()
	{
		return stickyFilters != null ? stickyFilters : ImmutableList.of();
	}

	public HUEditorViewBuilder setFilters(final List<DocumentFilter> filters)
	{
		this.filters = filters;
		return this;
	}

	List<DocumentFilter> getFilters()
	{
		return filters != null ? filters : ImmutableList.of();
	}

	public HUEditorViewBuilder orderBy(@NonNull final DocumentQueryOrderBy orderBy)
	{
		if (orderBys == null)
		{
			orderBys = new ArrayList<>();
		}
		orderBys.add(orderBy);
		return this;
	}

	public HUEditorViewBuilder orderBys(@NonNull final List<DocumentQueryOrderBy> orderBys)
	{
		this.orderBys = new ArrayList<>(orderBys);
		return this;
	}

	public HUEditorViewBuilder clearOrderBys()
	{
		this.orderBys = null;
		return this;
	}

	private ImmutableList<DocumentQueryOrderBy> getOrderBys()
	{
		return orderBys != null ? ImmutableList.copyOf(orderBys) : ImmutableList.of();
	}

	public HUEditorViewBuilder setParameter(final String name, final Object value)
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

	public HUEditorViewBuilder setParameters(final Map<String, Object> parameters)
	{
		parameters.forEach(this::setParameter);
		return this;
	}

	ImmutableMap<String, Object> getParameters()
	{
		return parameters != null ? ImmutableMap.copyOf(parameters) : ImmutableMap.of();
	}

	public HUEditorViewBuilder setHUEditorViewRepository(final HUEditorViewRepository huEditorViewRepository)
	{
		this.huEditorViewRepository = huEditorViewRepository;
		return this;
	}

	final HUEditorViewBuffer createRowsBuffer()
	{
		final ViewId viewId = getViewId();
		final List<DocumentFilter> stickyFilters = getStickyFilters();
		final List<DocumentFilter> filters = getFilters();

		if (HUEditorViewBuffer_HighVolume.isHighVolume(stickyFilters))
		{
			return new HUEditorViewBuffer_HighVolume(viewId, huEditorViewRepository, stickyFilters, filters, getOrderBys());
		}
		else
		{
			return new HUEditorViewBuffer_FullyCached(viewId, huEditorViewRepository, stickyFilters, filters, getOrderBys());
		}
	}
}
