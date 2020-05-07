package de.metas.procurement.base.order.callout;

import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.IStatefulTabCallout;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.facet.IFacetCollector;
import org.adempiere.facet.IFacetFilterable;
import org.adempiere.facet.IFacetsPool;
import org.adempiere.facet.impl.FacetExecutor;
import org.adempiere.facet.impl.GridTabFacetFilterable;
import org.adempiere.facet.sideactions.impl.SideActionFacetsPool;
import org.adempiere.ui.sideactions.model.ISideActionsGroupsListModel;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;

import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.facet.IPurchaseCandidateFacetCollectorFactory;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PMM_PurchaseCandidate_TabCallout extends TabCalloutAdapter implements IStatefulTabCallout
{
	private FacetExecutor<I_PMM_PurchaseCandidate> gridTabFacetExecutor;

	/**
	 * Action: Create purchase orders
	 */
	private PMM_CreatePurchaseOrders_Action action_CreatePurchaseOrders = null;

	@Override
	public void onInit(final ICalloutRecord calloutRecord)
	{
		final GridTab gridTab = GridTab.fromCalloutRecordOrNull(calloutRecord);
		if(gridTab == null)
		{
			return;
		}
		
		final ISideActionsGroupsListModel sideActionsGroupsModel = gridTab.getSideActionsGroupsModel();

		//
		// Add action: Create purchase orders
		{
			action_CreatePurchaseOrders = new PMM_CreatePurchaseOrders_Action(gridTab);
			sideActionsGroupsModel
					.getGroupById(GridTab.SIDEACTIONS_Actions_GroupId)
					.addAction(action_CreatePurchaseOrders);
		}

		//
		// Setup facet filtering engine
		{
			gridTabFacetExecutor = new FacetExecutor<>(I_PMM_PurchaseCandidate.class);

			// Current facets will be displayed in GridTab's right-side panel
			final IFacetsPool<I_PMM_PurchaseCandidate> facetsPool = new SideActionFacetsPool<>(sideActionsGroupsModel);
			gridTabFacetExecutor.setFacetsPool(facetsPool);

			// The datasource which will be filtered by our facets is actually THIS grid tab
			final IFacetFilterable<I_PMM_PurchaseCandidate> facetFilterable = new GridTabFacetFilterable<>(I_PMM_PurchaseCandidate.class, gridTab);
			gridTabFacetExecutor.setFacetFilterable(facetFilterable);

			// We will use service registered collectors to collect the facets from our rows
			final IFacetCollector<I_PMM_PurchaseCandidate> facetCollectors = Services.get(IPurchaseCandidateFacetCollectorFactory.class).createFacetCollectors();
			gridTabFacetExecutor.addFacetCollector(facetCollectors);
		}
	}

	@Override
	public void onAfterQuery(final ICalloutRecord calloutRecord)
	{
		updateFacets(calloutRecord);
	}

	@Override
	public void onRefreshAll(final ICalloutRecord calloutRecord)
	{
		// NOTE: we are not updating the facets on refresh all because following case would fail:
		// Case: user is pressing the "Refresh" toolbar button to refresh THE content of the grid,
		// but user is not expecting to have all it's facets reset.
		// updateFacets(gridTab);
	}

	/**
	 * Retrieve facets from current grid tab rows and add them to window side panel
	 *
	 * @param calloutRecord
	 */
	private void updateFacets(final ICalloutRecord calloutRecord)
	{
		//
		// If user asked to approve for invoicing some ICs, the grid will be asked to refresh all,
		// but in this case we don't want to reset current facets and recollect them
		if (action_CreatePurchaseOrders.isRunning())
		{
			return;
		}

		//
		// Collect the facets from current grid tab rows and fully update the facets pool.
		gridTabFacetExecutor.collectFacetsAndResetPool();
	}

}
