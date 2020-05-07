package org.eevolution.mrp.api.impl;

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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.dao.impl.NotEqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IResourceDAO;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.model.I_PP_MRP_Alternative;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPQueryBuilder;
import org.slf4j.Logger;

import com.google.common.base.Optional;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.material.planning.IMRPSegment;

public class MRPDAO implements IMRPDAO
{
	private static final transient Logger logger = LogManager.getLogger(MRPDAO.class);

	@Override
	public IMRPQueryBuilder createMRPQueryBuilder()
	{
		return new MRPQueryBuilder();
	}

	@Override
	public IMRPQueryBuilder retrieveQueryBuilder(final Object model, final String typeMRP, final String orderType)
	{
		final Optional<Integer> adClientId = InterfaceWrapperHelper.getValue(model, "AD_Client_ID");
		Check.assume(adClientId.isPresent(), "param 'model'={} has an AD_Client_ID", model);

		final IMRPQueryBuilder queryBuilder = createMRPQueryBuilder()
				.setContextProvider(model) // use context from model
				.setAD_Client_ID(adClientId.get()) // use model's AD_Client_ID
				// Only those MRP records which are referencing our model
				.setReferencedModel(model)
				// Filter by TypeMRP (Demand/Supply)
				.setTypeMRP(typeMRP)
				// Filter by OrderType (i.e. like document base type)
				.setOrderType(orderType);

		//
		// In case we query for PP_Order and TypeMRP=Supply, we need to make sure only header Supply is returned (because that's what is expected).
		// The TypeMRP=D/S filter is not enough since a BOM Line can produce a supply (e.g. co-product)
		// TODO: get rid of this HARDCODED/particular case
		if (InterfaceWrapperHelper.isInstanceOf(model, I_PP_Order.class)
				&& X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			queryBuilder.setPP_Order_BOMLine_Null();
		}

		//
		// Return the query builder
		return queryBuilder;
	}

	@Override
	public List<I_PP_MRP> retrieveMRPRecords(final Object referencedModel)
	{
		final String typeMRP = null; // all
		final String orderType = null; // all
		return retrieveQueryBuilder(referencedModel, typeMRP, orderType)
				// Retrieve the record even if some master data is marked to be skipped.
				// We do this because the record is supposed to be retrieved for updating
				.setSkipIfMRPExcluded(false)
				//
				.list();
	}

	@Override
	public I_PP_MRP retrieveMRPRecord(final Object referencedModel)
	{
		final String typeMRP = null; // all
		final String orderType = null; // all
		return retrieveQueryBuilder(referencedModel, typeMRP, orderType)
				// Retrieve the record even if some master data is marked to be skipped.
				// We do this because the record is supposed to be retrieved for updating
				.setSkipIfMRPExcluded(false)
				//
				.firstOnly();
	}

	@Override
	public void deleteMRP(final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final String keyColumnName = tableName + "_ID";
		if (!InterfaceWrapperHelper.hasColumnName(I_PP_MRP.class, keyColumnName))
		{
			logger.info("There is PP_MRP.{} column. Skip deleting related MRP records for {}", new Object[] { keyColumnName, model });
			return;
		}

		//
		// Delete MRP records which reference given "model"
		createMRPQueryBuilder()
				.setSkipIfMRPExcluded(false) // delete ALL
				.setContextProvider(model)
				.setReferencedModel(model)
				.setOnlyActiveRecords(false) // delete ALL records no matter if they are active or not
				.deleteMRPRecords();

		//
		// Delete generated manufacturing orders which are linked to this order line
		// NOTE: initially this method was used for deleting the Make-to-Order generated manufacturing orders but now we are doing it for all orders
		if (InterfaceWrapperHelper.isInstanceOf(model, I_C_OrderLine.class))
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
			final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);

			final List<I_PP_Order> ppOrders = ppOrderDAO.retrieveAllForOrderLine(orderLine);
			for (final I_PP_Order ppOrder : ppOrders)
			{
				// Skip those which are processed
				if (ppOrder.isProcessed())
				{
					continue;
				}

				//
				// Delete the order
				InterfaceWrapperHelper.delete(ppOrder);
			}
		}
	}

	@Override
	public int deleteMRP(final IQueryBuilder<I_PP_MRP> mrpQuery)
	{
		return mrpQuery
				.create()
				.deleteDirectly();
	}

	@Override
	public boolean hasProductRecords(final I_M_Product product)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_MRP.class, product)
				.filter(new EqualsQueryFilter<I_PP_MRP>(I_PP_MRP.COLUMNNAME_M_Product_ID, product.getM_Product_ID()))
				.filter(new NotEqualsQueryFilter<I_PP_MRP>(I_PP_MRP.COLUMNNAME_Qty, BigDecimal.ZERO))
				.create()
				.match();
	}

	@Override
	public int getMaxLowLevel(final IContextAware context)
	{
		final IQueryBuilder<I_M_Product> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class, context)
				.addOnlyContextClient()
				.addNotEqualsFilter(I_M_Product.COLUMNNAME_LowLevel, null); // LowLevel is not null

		final BigDecimal lowLevelMaxBD = queryBuilder.create()
				.aggregate(I_M_Product.COLUMNNAME_LowLevel, Aggregate.MAX, BigDecimal.class);

		final int lowLevelMax = lowLevelMaxBD == null ? -1 : lowLevelMaxBD.intValueExact();

		// TODO: Question: not sure why we return LowLevelMax+1, but we keep old logic for now
		return lowLevelMax + 1;
	}

	/**
	 * Get Qty On Hand
	 *
	 * @param AD_Client_ID
	 * @param M_Warehouse_ID
	 * @param M_Product_ID
	 * @return
	 */
	@Override
	public BigDecimal getQtyOnHand(final Properties ctx, final int M_Warehouse_ID, final int M_Product_ID, final String trxName)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);

		final String sql = "SELECT COALESCE(bomQtyOnHand (M_Product_ID,?,0),0) FROM M_Product"
				+ " WHERE AD_Client_ID=? AND M_Product_ID=?";
		final BigDecimal qtyOnHand = DB.getSQLValueBDEx(trxName, sql, new Object[] { M_Warehouse_ID, adClientId, M_Product_ID });
		if (qtyOnHand == null)
		{
			return Env.ZERO;
		}
		return qtyOnHand;
	}

	@Override
	public BigDecimal getQtyOnHand(final IContextAware context, final I_M_Warehouse warehouse, final I_M_Product product)
	{
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();
		final int warehouseId = warehouse.getM_Warehouse_ID();
		final int productId = product.getM_Product_ID();
		return getQtyOnHand(ctx, warehouseId, productId, trxName);
	}

	@Override
	public String getDocumentNo(final int PP_MRP_ID)
	{
		return DB.getSQLValueStringEx(null, "SELECT documentNo(PP_MRP_ID) AS DocumentNo FROM PP_MRP WHERE PP_MRP_ID = ?", PP_MRP_ID);
	}

	@Override
	public IDocument retrieveDocumentOrNull(final I_PP_MRP mrp)
	{
		final Object documentObj;
		if (mrp.getC_Order_ID() > 0)
		{
			documentObj = mrp.getC_Order();
		}
		else if (mrp.getDD_Order_ID() > 0)
		{
			documentObj = mrp.getDD_Order();
		}
		else if (mrp.getM_Forecast_ID() > 0)
		{
			// Forecast is not a document
			documentObj = null;
		}
		else if (mrp.getM_Requisition_ID() > 0)
		{
			documentObj = mrp.getM_Requisition();
		}
		else if (mrp.getPP_Order_ID() > 0)
		{
			documentObj = mrp.getPP_Order();
		}
		else
		{
			return null;
		}

		if (documentObj == null)
		{
			return null;
		}

		final IDocument document = Services.get(IDocumentBL.class).getDocument(documentObj);
		return document;
	}

	@Override
	public void markNotAvailable(final I_PP_MRP mrp, final String trxName)
	{
		// If MRP record is already not available => do nothing
		if (!mrp.isAvailable())
		{
			return;
		}

		final ITrxConstraintsBL trxConstraintsBL = Services.get(ITrxConstraintsBL.class);
		trxConstraintsBL.saveConstraints();
		try
		{
			// Because it could be that we are running out of transaction, so we need to allow local transactions
			trxConstraintsBL.getConstraints().addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);

			mrp.setIsAvailable(false);
			// ts: added the ",ITrx.TRXNAME_None", because i assume that we want to save out of the trx..otherwise the invocations to the TrxConstraints API make no sense
			InterfaceWrapperHelper.save(mrp, trxName);
		}
		finally
		{
			trxConstraintsBL.restoreConstraints();
		}
	}

	@Override
	public IQueryBuilder<I_PP_MRP_Alternative> retrieveMRPAlternativesQuery(final I_PP_MRP mrp)
	{
		Check.assumeNotNull(mrp, "mrp not null");
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_PP_MRP_Alternative> queryBuilder = queryBL.createQueryBuilder(I_PP_MRP_Alternative.class, mrp)
				.addEqualsFilter(I_PP_MRP_Alternative.COLUMN_PP_MRP_ID, mrp.getPP_MRP_ID());

		// just to have a predictable order
		queryBuilder.orderBy()
				.addColumn(I_PP_MRP_Alternative.COLUMN_PP_MRP_ID)
				.addColumn(I_PP_MRP_Alternative.COLUMN_M_Product_ID);

		return queryBuilder;
	}

	@Override
	public List<IMRPSegment> retrieveMRPSegmentsForAvailableDemands(final Properties ctx)
	{
		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Retrieve distinct AD_Client_ID/AD_Org_ID/M_Warhouse_ID/Plant_ID from database
		final IQueryBuilder<I_PP_MRP> mrpsQuery = queryBL.createQueryBuilder(I_PP_MRP.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_PP_MRP.COLUMN_IsAvailable, true)
				.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Demand);

		//
		// Create MRP Segments
		final List<IMRPSegment> mrpSegments = retrieveMRPSegments(mrpsQuery);
		return mrpSegments;
	}

	@Override
	public List<IMRPSegment> retrieveMRPSegmentsForAvailableDemandsOrSupplies(final Properties ctx)
	{
		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Retrieve distinct AD_Client_ID/AD_Org_ID/M_Warhouse_ID/Plant_ID from database
		final IQueryBuilder<I_PP_MRP> mrpsQuery = queryBL.createQueryBuilder(I_PP_MRP.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_PP_MRP.COLUMN_IsAvailable, true)
		// .addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Demand)
		;

		//
		// Create MRP Segments
		final List<IMRPSegment> mrpSegments = retrieveMRPSegments(mrpsQuery);
		return mrpSegments;
	}

	private final List<IMRPSegment> retrieveMRPSegments(final IQueryBuilder<I_PP_MRP> mrpsQuery)
	{
		// services
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);

		//
		// Retrieve distinct AD_Client_ID/AD_Org_ID/M_Warehouse_ID/S_Resource_ID items
		final Properties ctx = mrpsQuery.getCtx();
		final List<Map<String, Object>> mrpSegmentValuesList = mrpsQuery
				.create()
				.listDistinct(I_PP_MRP.COLUMNNAME_AD_Client_ID
						, I_PP_MRP.COLUMNNAME_AD_Org_ID
						, I_PP_MRP.COLUMNNAME_M_Warehouse_ID
						, I_PP_MRP.COLUMNNAME_S_Resource_ID);

		//
		// Create MRP Segments
		final List<IMRPSegment> mrpSegments = new ArrayList<>(mrpSegmentValuesList.size());
		for (final Map<String, Object> mrpSegmentValues : mrpSegmentValuesList)
		{
			final int adClientId = (int)mrpSegmentValues.get(I_PP_MRP.COLUMNNAME_AD_Client_ID);

			final Integer adOrgId = (Integer)mrpSegmentValues.get(I_PP_MRP.COLUMNNAME_AD_Org_ID);
			I_AD_Org adOrg = null;
			if (adOrgId != null && adOrgId > 0)
			{
				adOrg = orgDAO.retrieveOrg(ctx, adOrgId);
			}

			final Integer warehouseId = (Integer)mrpSegmentValues.get(I_PP_MRP.COLUMNNAME_M_Warehouse_ID);
			I_M_Warehouse warehouse = null;
			if (warehouseId != null && warehouseId > 0)
			{
				warehouse = InterfaceWrapperHelper.create(ctx, warehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);
			}

			final Integer plantId = (Integer)mrpSegmentValues.get(I_PP_MRP.COLUMNNAME_S_Resource_ID);
			I_S_Resource plant = null;
			if (plantId != null && plantId > 0)
			{
				plant = resourceDAO.retrievePlant(ctx, plantId);
			}

			final I_M_Product product = null;
			final MRPSegment mrpSegment = new MRPSegment(adClientId, adOrg, warehouse, plant, product);
			mrpSegments.add(mrpSegment);
		}

		return mrpSegments;
	}

	@Override
	public IQueryBuilder<I_PP_MRP> retrieveForwardMRPDemandsForSupplyQuery(final I_PP_MRP mrpSupply)
	{
		return retrieveMRPAllocsForSupplyQuery(mrpSupply)
				.andCollect(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID);
	}

	@Override
	public IQueryBuilder<I_PP_MRP_Alloc> retrieveMRPAllocsForSupplyQuery(final I_PP_MRP mrpSupply)
	{
		Check.assumeNotNull(mrpSupply, "mrpSupply not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_PP_MRP_Alloc.class, mrpSupply)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID, mrpSupply.getPP_MRP_ID());
	}

	@Override
	public IQueryBuilder<I_PP_MRP> retrieveBackwardMRPSuppliesForDemandQuery(final I_PP_MRP mrpDemand)
	{
		return retrieveMRPAllocsForDemandQuery(mrpDemand)
				.andCollect(I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID);
	}

	@Override
	public IQueryBuilder<I_PP_MRP_Alloc> retrieveMRPAllocsForDemandQuery(final I_PP_MRP mrpDemand)
	{
		Check.assumeNotNull(mrpDemand, "mrpDemand not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_PP_MRP_Alloc.class, mrpDemand)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID, mrpDemand.getPP_MRP_ID());
	}

}
