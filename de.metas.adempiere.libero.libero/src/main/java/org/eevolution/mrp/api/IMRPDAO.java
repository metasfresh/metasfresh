package org.eevolution.mrp.api;

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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.model.I_PP_MRP_Alternative;

import de.metas.document.engine.IDocument;
import de.metas.material.planning.IMRPSegment;

public interface IMRPDAO extends ISingletonService
{
	IMRPQueryBuilder createMRPQueryBuilder();

	IMRPQueryBuilder retrieveQueryBuilder(Object model, String typeMRP, String orderType);

	/**
	 * Retrieve all MRP records linked to given <code>referencedModel</code>.
	 * 
	 * NOTE: this method will NOT skip MRP_Excluded records (see {@link IMRPQueryBuilder#setSkipIfMRPExcluded(boolean)}) because it's assumed they are used in create/update logic.
	 *
	 * @param referencedModel
	 * @return MRP records
	 */
	List<I_PP_MRP> retrieveMRPRecords(final Object referencedModel);

	/**
	 * Retrieve MRP record linked to given <code>referencedModel</code>.
	 *
	 * This method assumes there is only one MRP record.
	 * 
	 * NOTE: this method will NOT skip MRP_Excluded records (see {@link IMRPQueryBuilder#setSkipIfMRPExcluded(boolean)}) because it's assumed they are used in create/update logic.
	 *
	 * @param referencedModel
	 * @return MRP record or null
	 */
	I_PP_MRP retrieveMRPRecord(Object referencedModel);

	/**
	 * Delete MRP records for given document/document line.
	 *
	 * @param model
	 */
	void deleteMRP(Object model);

	/**
	 * Delete MRP records that match given query
	 * 
	 * @param mrpQuery
	 * @return how many MRP records were actually deleted
	 */
	int deleteMRP(IQueryBuilder<I_PP_MRP> mrpQuery);

	/**
	 * @param product
	 * @return true if there are MRP records for given product
	 */
	boolean hasProductRecords(I_M_Product product);

	/**
	 * Maximum Low Level Code
	 *
	 * @param ctx
	 * @param trxName
	 * @return maximum low level
	 */
	int getMaxLowLevel(final IContextAware context);

	/**
	 * Get Qty On Hand
	 *
	 * @param context
	 * @param warehouse
	 * @param product
	 * @return qtyOnHand
	 */
	BigDecimal getQtyOnHand(final IContextAware context, I_M_Warehouse warehouse, I_M_Product product);

	BigDecimal getQtyOnHand(Properties ctx, int M_Warehouse_ID, int M_Product_ID, String trxName);

	String getDocumentNo(int PP_MRP_ID);

	/**
	 * Retrieves document produced this MRP record
	 *
	 * @param mrp
	 * @return document or null
	 */
	IDocument retrieveDocumentOrNull(I_PP_MRP mrp);

	/**
	 * Mark given {@link I_PP_MRP} as not available and save it.
	 *
	 * @param mrp
	 * @param trxName
	 */
	void markNotAvailable(I_PP_MRP mrp, String trxName);

	IQueryBuilder<I_PP_MRP_Alternative> retrieveMRPAlternativesQuery(I_PP_MRP mrp);

	/**
	 * Gets a unique list of MRP segments were we have at least one MRP demand.
	 * 
	 * @param ctx
	 * @return
	 */
	List<IMRPSegment> retrieveMRPSegmentsForAvailableDemands(Properties ctx);

	/**
	 * Gets a unique list of MRP segments were we have at least one MRP demand or supply.
	 * 
	 * @param ctx
	 * @return
	 */
	List<IMRPSegment> retrieveMRPSegmentsForAvailableDemandsOrSupplies(Properties ctx);

	/** @return all MRP demands which have an allocation pointing to given MRP supply */
	IQueryBuilder<I_PP_MRP> retrieveForwardMRPDemandsForSupplyQuery(I_PP_MRP mrpSupply);

	/** @return {@link I_PP_MRP_Alloc}s which are linking to given MRP supply */
	IQueryBuilder<I_PP_MRP_Alloc> retrieveMRPAllocsForSupplyQuery(I_PP_MRP mrpSupply);

	/** @return all MRP supplies which have an allocation pointing to given MRP demand */
	IQueryBuilder<I_PP_MRP> retrieveBackwardMRPSuppliesForDemandQuery(I_PP_MRP mrpDemand);

	/** @return {@link I_PP_MRP_Alloc}s which are linking to given MRP demand */
	IQueryBuilder<I_PP_MRP_Alloc> retrieveMRPAllocsForDemandQuery(I_PP_MRP mrpDemand);
}
