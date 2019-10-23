package org.adempiere.facet.sideactions.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.facet.IFacet;
import org.adempiere.facet.IFacetCategory;
import org.adempiere.facet.IFacetCollectorResult;
import org.adempiere.facet.IFacetsPool;
import org.adempiere.facet.IFacetsPoolListener;
import org.adempiere.facet.impl.CompositeFacetsPoolListener;
import org.adempiere.ui.sideactions.model.ISideAction;
import org.adempiere.ui.sideactions.model.ISideActionExecuteDelegate;
import org.adempiere.ui.sideactions.model.ISideActionsGroupModel;
import org.adempiere.ui.sideactions.model.ISideActionsGroupsListModel;
import org.adempiere.ui.sideactions.model.SideActionsGroupModel;

import de.metas.util.Check;

/**
 * Wraps an {@link ISideActionsGroupsListModel} to {@link IFacetsPool}.
 *
 * The {@link ISideAction#execute()} will be forwarded to {@link IFacetsPoolListener}s.
 *
 * @author tsa
 *
 * @param <ModelType>
 */
public class SideActionFacetsPool<ModelType> implements IFacetsPool<ModelType>
{
	private final ISideActionsGroupsListModel sideActionsGroupListModel;

	private final CompositeFacetsPoolListener listeners = new CompositeFacetsPoolListener();

	/**
	 * Own actions map (actions which were created and are managed by this pool).
	 * 
	 * The map binds {@link IFacet#getId()} to {@link FacetSideAction}.
	 */
	private final Map<String, FacetSideAction<ModelType>> ownActions = new HashMap<>();
	/**
	 * Own action groups (groups which were created and are managed by this pool).
	 */
	private final Set<ISideActionsGroupModel> ownActionGroups = new HashSet<>();

	/**
	 * {@link ISideAction} execution delegate which forwards the {@link ISideAction#execute()} to {@link IFacetsPoolListener#onFacetExecute(IFacet)}.
	 */
	private final ISideActionExecuteDelegate sideActionsExecuteDelegate = new ISideActionExecuteDelegate()
	{
		@Override
		public void execute(final ISideAction action)
		{
			if (action instanceof FacetSideAction<?>)
			{
				final FacetSideAction<?> facetSideAction = (FacetSideAction<?>)action;
				final IFacet<?> facet = facetSideAction.getFacet();
				listeners.onFacetExecute(facet);
			}
		}
	};

	public SideActionFacetsPool(final ISideActionsGroupsListModel sideActionsGroupListModel)
	{
		super();

		Check.assumeNotNull(sideActionsGroupListModel, "sideActionsGroupListModel not null");
		this.sideActionsGroupListModel = sideActionsGroupListModel;
	}

	@Override
	public void addListener(final IFacetsPoolListener listener)
	{
		listeners.addListener(listener);
	}

	@Override
	public void removeListener(final IFacetsPoolListener listener)
	{
		listeners.removeListener(listener);
	}

	/** @return "side actions group" model; never return null */
	private final ISideActionsGroupsListModel getSideActionsGroupsModel()
	{
		return sideActionsGroupListModel;
	}

	@Override
	public final void setFacetCategories(final List<IFacetCategory> facetCategories)
	{
		for (final IFacetCategory facetCategory : facetCategories)
		{
			getCreateSideActionsGroupFor(facetCategory);
		}
	}

	private final FacetSideAction<ModelType> createFacetSideAction(final IFacet<ModelType> facet)
	{
		final FacetSideAction<ModelType> facetSideAction = new FacetSideAction<>(facet, sideActionsExecuteDelegate);
		return facetSideAction;
	}

	@Override
	public final void setFacets(final Set<IFacet<ModelType>> facets)
	{
		this.ownActions.clear();

		//
		// Build the Group to Actions map
		final IdentityHashMap<ISideActionsGroupModel, Set<FacetSideAction<ModelType>>> group2actionsMap = new IdentityHashMap<>();
		for (final IFacet<ModelType> facet : facets)
		{
			final IFacetCategory facetCategory = facet.getFacetCategory();
			final ISideActionsGroupModel group = getCreateSideActionsGroupFor(facetCategory);

			Set<FacetSideAction<ModelType>> groupActions = group2actionsMap.get(group);
			if (groupActions == null)
			{
				groupActions = new HashSet<>();
				group2actionsMap.put(group, groupActions);
			}

			final FacetSideAction<ModelType> action = createFacetSideAction(facet);
			groupActions.add(action);
		}

		//
		// Clear all action groups which we own:
		for (final ISideActionsGroupModel group : ownActionGroups)
		{
			group.removeAllActions();
		}

		//
		// Set actions to group
		for (final Map.Entry<ISideActionsGroupModel, Set<FacetSideAction<ModelType>>> group2actions : group2actionsMap.entrySet())
		{
			final ISideActionsGroupModel group = group2actions.getKey();
			final Set<FacetSideAction<ModelType>> actions = group2actions.getValue();
			group.setActions(actions);

			for (final FacetSideAction<ModelType> action : actions)
			{
				this.ownActions.put(action.getFacetId(), action);
			}
		}

		// Fire event: facets (re-)initialized
		listeners.onFacetsInit();
	}

	@Override
	public void updateFrom(final IFacetCollectorResult<ModelType> result)
	{
		Check.assumeNotNull(result, "result not null");

		for (final IFacetCategory facetCategory : result.getFacetCategories())
		{
			final Set<IFacet<ModelType>> facets = result.getFacetsByCategory(facetCategory);
			updateSideActionsGroup(facetCategory, facets);
		}
	}

	private final void updateSideActionsGroup(final IFacetCategory facetCategory, final Set<IFacet<ModelType>> facets)
	{
		final ISideActionsGroupModel actionsGroup = getCreateSideActionsGroupFor(facetCategory);

		// FacetId->Facet
		final LinkedHashMap<String, IFacet<ModelType>> facetId2facet = new LinkedHashMap<>(facets.size());
		for (final IFacet<ModelType> facet : facets)
		{
			facetId2facet.put(facet.getId(), facet);
		}

		//
		// Iterate the target actions list and collect:
		// * actions which we don't own => we need to keep them
		// * actions which we need to remove
		// * actions which we need to update
		final List<ISideAction> actionsNotOwned = new ArrayList<>();
		final Map<String, FacetSideAction<ModelType>> actionsToRemove = new HashMap<>();
		final Map<String, FacetSideAction<ModelType>> actionsToUpdate = new HashMap<>();
		for (final ISideAction action : actionsGroup.getActionsList())
		{
			// If it's not our own action, just blindly keep it
			if (!ownActions.containsValue(action))
			{
				actionsNotOwned.add(action);
				continue;
			}

			// Get Facet Action
			final FacetSideAction<ModelType> facetAction = FacetSideAction.cast(action);
			final IFacet<ModelType> facet = facetAction.getFacet();
			final String facetId = facet.getId();

			// Collect action to remove / action to be updated
			if (!facetId2facet.containsKey(facetId))
			{
				actionsToRemove.put(facetId, facetAction);
			}
			else
			{
				actionsToUpdate.put(facetId, facetAction);
			}
		}

		//
		// Build the final actions list which we will set it to the action group
		final Map<String, FacetSideAction<ModelType>> actionsToAdd = new HashMap<>();
		final List<ISideAction> actionsListToSet = new ArrayList<>();
		actionsListToSet.addAll(actionsNotOwned);
		for (final IFacet<ModelType> facet : facets)
		{
			final String facetId = facet.getId();
			final FacetSideAction<ModelType> facetAction = createFacetSideAction(facet);
			actionsToAdd.put(facetId, facetAction);

			// Copy setting from existing facet action (if any)
			final FacetSideAction<ModelType> facetActionExisting = actionsToUpdate.get(facetId);
			if (facetActionExisting != null)
			{
				facetAction.setToggled(facetActionExisting.isToggled());
				actionsToRemove.put(facetId, facetActionExisting);
			}

			actionsListToSet.add(facetAction);
		}

		//
		// Update Own Actions List
		for (final String facetId : actionsToRemove.keySet())
		{
			ownActions.remove(facetId);
		}
		for (final FacetSideAction<ModelType> action : actionsToAdd.values())
		{
			ownActions.put(action.getFacetId(), action);
		}
		actionsGroup.setActions(actionsListToSet);
	}

	private ISideActionsGroupModel getCreateSideActionsGroupFor(final IFacetCategory facetCategory)
	{
		final String displayName = facetCategory.getDisplayName();
		final boolean defaultCollapsed = facetCategory.isCollapsed();
		final String groupId = getClass().getName() + "." + displayName;
		final ISideActionsGroupsListModel actionsGroupsListModel = getSideActionsGroupsModel();
		ISideActionsGroupModel group = actionsGroupsListModel.getGroupByIdOrNull(groupId);
		if (group == null)
		{
			group = new SideActionsGroupModel(groupId, displayName, defaultCollapsed);
			actionsGroupsListModel.addGroup(group);
			this.ownActionGroups.add(group);
		}

		return group;
	}

	@Override
	public Set<IFacet<ModelType>> getActiveFacets()
	{
		final Set<IFacet<ModelType>> activeFacets = new HashSet<>();
		for (final FacetSideAction<ModelType> action : ownActions.values())
		{
			// Skip actions which are not active (i.e. checkbox set by user)
			if (!action.isToggled())
			{
				continue;
			}

			final IFacet<ModelType> facet = action.getFacet();
			activeFacets.add(facet);
		}
		return activeFacets;
	}
}
