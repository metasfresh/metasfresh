package org.compiere.wf.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_WF_NodeNext;
import org.compiere.model.I_AD_Workflow;
import org.compiere.wf.api.IADWorkflowDAO;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class ADWorkflowDAO implements IADWorkflowDAO
{
	@Override
	@Cached(cacheName = I_AD_WF_Node.Table_Name + "#by#" + I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID)
	public List<I_AD_WF_Node> retrieveNodes(
			@CacheCtx final Properties ctx,
			final int adWorkflowId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_AD_WF_Node> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_WF_Node.class, ctx, trxName);

		final ICompositeQueryFilter<I_AD_WF_Node> filters = queryBuilder.getFilters();
		filters.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID, adWorkflowId);
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID);

		return queryBuilder.create().list();
	}

	@Override
	public List<I_AD_WF_Node> retrieveNodes(final I_AD_Workflow workflow)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(workflow);
		final String trxName = InterfaceWrapperHelper.getTrxName(workflow);
		final int adWorkflowId = workflow.getAD_Workflow_ID();
		return retrieveNodes(ctx, adWorkflowId, trxName);
	}

	@Override
	public List<I_AD_WF_Node> retrieveNodes(final I_AD_Workflow workflow, final int adClientId)
	{
		final List<I_AD_WF_Node> nodesAll = retrieveNodes(workflow);
		final List<I_AD_WF_Node> nodes = new ArrayList<I_AD_WF_Node>(nodesAll);
		for (final Iterator<I_AD_WF_Node> it = nodes.iterator(); it.hasNext();)
		{
			final I_AD_WF_Node node = it.next();

			if (node.getAD_Client_ID() > 0 && node.getAD_Client_ID() != adClientId)
			{
				it.remove();
			}
		}

		return nodes;
	}

	@Override
	public List<I_AD_WF_NodeNext> retrieveNodeNexts(final I_AD_WF_Node node)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(node);
		final String trxName = InterfaceWrapperHelper.getTrxName(node);
		final int adNodeId = node.getAD_WF_Node_ID();
		return retrieveNodeNexts(ctx, adNodeId, trxName);
	}

	@Cached(cacheName = I_AD_WF_NodeNext.Table_Name + "#by#" + I_AD_WF_NodeNext.COLUMNNAME_AD_WF_Node_ID)
	/* package */ List<I_AD_WF_NodeNext> retrieveNodeNexts(
			@CacheCtx final Properties ctx,
			final int adWFNodeId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_AD_WF_NodeNext> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_WF_NodeNext.class, ctx, trxName);

		final ICompositeQueryFilter<I_AD_WF_NodeNext> filters = queryBuilder.getFilters();
		filters.addEqualsFilter(I_AD_WF_NodeNext.COLUMNNAME_AD_WF_Node_ID, adWFNodeId);
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_AD_WF_NodeNext.COLUMNNAME_SeqNo);

		return queryBuilder.create().list();
	}

	@Override
	public List<I_AD_WF_NodeNext> retrieveNodeNexts(final I_AD_WF_Node node, final int adClientId)
	{
		final List<I_AD_WF_NodeNext> nodeNextsAll = retrieveNodeNexts(node);
		final List<I_AD_WF_NodeNext> nodeNexts = new ArrayList<I_AD_WF_NodeNext>(nodeNextsAll);
		for (final Iterator<I_AD_WF_NodeNext> it = nodeNexts.iterator(); it.hasNext();)
		{
			final I_AD_WF_NodeNext nodeNext = it.next();

			if (nodeNext.getAD_Client_ID() > 0 && nodeNext.getAD_Client_ID() != adClientId)
			{
				it.remove();
			}
		}

		return nodeNexts;
	}
}
