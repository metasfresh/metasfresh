package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_S_Resource;
import org.eevolution.api.IPPOrderWorkflowDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.I_PP_Order_NodeNext;
import org.eevolution.model.I_PP_Order_Node_Asset;
import org.eevolution.model.I_PP_Order_Node_Product;
import org.eevolution.model.I_PP_Order_Workflow;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.material.planning.pporder.LiberoException;

public class PPOrderWorkflowDAO implements IPPOrderWorkflowDAO
{
	@Cached(cacheName = I_PP_Order_Node.Table_Name + "#by#" + I_PP_Order_Node.COLUMNNAME_PP_Order_Workflow_ID)
	/* package */List<I_PP_Order_Node> retrieveNodesByWorkflowId(
			@CacheCtx final Properties ctx,
			final int ppOrderWorkflowId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_PP_Order_Node> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_Node.class, ctx, trxName);

		final ICompositeQueryFilter<I_PP_Order_Node> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_PP_Order_Node.COLUMNNAME_PP_Order_Workflow_ID, ppOrderWorkflowId);
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_PP_Order_Node.COLUMNNAME_PP_Order_Node_ID);

		return queryBuilder.create().list();
	}

	@Override
	public List<I_PP_Order_Node> retrieveNodes(final I_PP_Order_Workflow orderWorkflow)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderWorkflow);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderWorkflow);
		final int orderWorkflowId = orderWorkflow.getPP_Order_Workflow_ID();
		return retrieveNodesByWorkflowId(ctx, orderWorkflowId, trxName);
	}

	@Cached(cacheName = I_PP_Order_Node.Table_Name + "#by#" + I_PP_Order_Node.COLUMNNAME_PP_Order_ID)
	/* package */List<I_PP_Order_Node> retrieveNodesByOrderId(@CacheCtx final Properties ctx,
			final int ppOrderId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_PP_Order_Node> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_Node.class, ctx, trxName);

		final ICompositeQueryFilter<I_PP_Order_Node> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_PP_Order_Node.COLUMNNAME_PP_Order_ID, ppOrderId);
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_PP_Order_Node.COLUMNNAME_PP_Order_Node_ID);

		return queryBuilder.create().list();
	}

	@Override
	public List<I_PP_Order_Node> retrieveNodes(final I_PP_Order order)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final String trxName = InterfaceWrapperHelper.getTrxName(order);
		final int ppOrderId = order.getPP_Order_ID();
		return retrieveNodesByOrderId(ctx, ppOrderId, trxName);
	}

	@Override
	public List<I_PP_Order_NodeNext> retrieveNodeNexts(final I_PP_Order_Node node)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(node);
		final String trxName = InterfaceWrapperHelper.getTrxName(node);
		final int orderNodeId = node.getPP_Order_Node_ID();
		return retrieveNodeNexts(ctx, orderNodeId, trxName);
	}

	@Cached(cacheName = I_PP_Order_NodeNext.Table_Name + "#by#" + I_PP_Order_NodeNext.COLUMNNAME_PP_Order_Node_ID)
	/* package */List<I_PP_Order_NodeNext> retrieveNodeNexts(
			@CacheCtx final Properties ctx,
			final int orderNodeId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_PP_Order_NodeNext> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_NodeNext.class, ctx, trxName);

		final ICompositeQueryFilter<I_PP_Order_NodeNext> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_PP_Order_NodeNext.COLUMNNAME_PP_Order_Node_ID, orderNodeId);
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_PP_Order_NodeNext.COLUMNNAME_SeqNo);

		return queryBuilder.create().list();
	}

	@Override
	public void deleteOrderWorkflow(final I_PP_Order ppOrder)
	{
		// New record, nothing to do
		if (ppOrder.getPP_Order_ID() <= 0)
		{
			return;
		}

		//
		// Set PP_Order_Workflow.PP_Order_Node_ID to null
		// ... to be able to delete nodes first
		final I_PP_Order_Workflow orderWorkflow = retrieveOrderWorkflow(ppOrder);
		if (orderWorkflow != null)
		{
			orderWorkflow.setPP_Order_Node_ID(-1);
			InterfaceWrapperHelper.save(orderWorkflow);
		}

		//
		// Delete PP_Order_Node_Asset
		for (final I_PP_Order_Node_Asset orderNodeAsset : retrieveOrderNodeAssets(ppOrder))
		{
			InterfaceWrapperHelper.delete(orderNodeAsset);
		}

		//
		// Delete PP_Order_Node_Product
		for (final I_PP_Order_Node_Product orderNodeProduct : retrieveOrderNodeProducts(ppOrder))
		{
			InterfaceWrapperHelper.delete(orderNodeProduct);
		}

		//
		// Delete PP_Order_NodeNext
		for (final I_PP_Order_NodeNext orderNodeNext : retrieveOrderNodeNexts(ppOrder))
		{
			InterfaceWrapperHelper.delete(orderNodeNext);
		}

		//
		// Delete PP_Order_Node
		for (final I_PP_Order_Node orderNode : retrieveOrderNodes(ppOrder))
		{
			InterfaceWrapperHelper.delete(orderNode);
		}

		//
		// Delete PP_Order_Workflow
		// (after everything else which depends on this was deleted)
		if (orderWorkflow != null)
		{
			InterfaceWrapperHelper.delete(orderWorkflow);
		}
	}

	@Override
	public I_PP_Order_Workflow retrieveOrderWorkflow(final I_PP_Order order)
	{
		final I_PP_Order_Workflow orderWorkflow = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_Workflow.class, order)
				.filter(new EqualsQueryFilter<I_PP_Order_Workflow>(I_PP_Order_Workflow.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID()))
				.filter(ActiveRecordQueryFilter.getInstance(I_PP_Order_Workflow.class))
				.create()
				.firstOnly(I_PP_Order_Workflow.class);
		if (orderWorkflow == null)
		{
			throw new LiberoException("@NotFound@ @PP_Order_Workflow_ID@ (@PP_Order_ID@: " + order.getDocumentNo() + ")");
		}
		return orderWorkflow;
	}

	@Override
	public List<I_PP_Order_Node> retrieveOrderNodes(final I_PP_Order order)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_Node.class, order)
				.filter(new EqualsQueryFilter<I_PP_Order_Node>(I_PP_Order_Node.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID()))
				.filter(ActiveRecordQueryFilter.getInstance(I_PP_Order_Node.class))
				.create()
				.list();
	}

	@Override
	public List<I_PP_Order_Node_Asset> retrieveOrderNodeAssets(final I_PP_Order order)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_Node_Asset.class, order)
				.filter(new EqualsQueryFilter<I_PP_Order_Node_Asset>(I_PP_Order_Node_Asset.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID()))
				.create()
				// .setOnlyActiveRecords(true)
				.list();
	}

	@Override
	public List<I_PP_Order_Node_Product> retrieveOrderNodeProducts(final I_PP_Order order)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_Node_Product.class, order)
				.filter(new EqualsQueryFilter<I_PP_Order_Node_Product>(I_PP_Order_Node_Product.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID()))
				.create()
				// .setOnlyActiveRecords(true)
				.list();
	}

	@Override
	public List<I_PP_Order_NodeNext> retrieveOrderNodeNexts(final I_PP_Order order)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_NodeNext.class, order)
				.filter(new EqualsQueryFilter<I_PP_Order_NodeNext>(I_PP_Order_NodeNext.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID()))
				.create()
				// .setOnlyActiveRecords(true)
				.list();
	}

	@Override
	public I_S_Resource retrieveResourceForFirstNode(I_PP_Order order)
	{
		final I_PP_Order_Workflow orderWorkflow = Services.get(IPPOrderWorkflowDAO.class).retrieveOrderWorkflow(order);
		final I_PP_Order_Node startNode = orderWorkflow.getPP_Order_Node();
		Check.assumeNotNull(startNode, LiberoException.class, "Start node shall exist for {}", order);

		return startNode.getS_Resource();
	}

}
