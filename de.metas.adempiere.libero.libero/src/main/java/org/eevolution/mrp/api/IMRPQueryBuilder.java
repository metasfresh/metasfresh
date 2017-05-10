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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryUpdater;
import org.eevolution.model.I_PP_MRP;

import de.metas.material.planning.IMaterialPlanningContext;

/**
 * To get an instance call {@link IMRPDAO#createMRPQueryBuilder()}.
 * 
 * @author tsa
 *
 */
public interface IMRPQueryBuilder
{
	IQueryBuilder<I_PP_MRP> createQueryBuilder();

	/**
	 * Sum up {@link I_PP_MRP#COLUMNNAME_Qty} for current selection
	 * 
	 * @return Qty sum or zero; never return null
	 */
	BigDecimal calculateQtySUM();

	/**
	 * Directly DELETE matched MRP records
	 * 
	 * @return how many MRP records were deleted
	 */
	int deleteMRPRecords();

	/**
	 * Update MRP records directly
	 * 
	 * @param queryUpdater
	 * @return how many MRP records where updated
	 */
	int updateMRPRecords(IQueryUpdater<I_PP_MRP> queryUpdater);

	/**
	 * Updates MRP records directly and sets {@link I_PP_MRP#COLUMN_IsAvailable} to <code>true</code>.
	 * 
	 * @return how many MRP records where updated
	 * @see #updateMRPRecords(IQueryUpdater)
	 */
	int updateMRPRecordsAndMarkAvailable();

	/**
	 * @return first {@link I_PP_MRP} record that is matching this query. If more then one record is found, an exception will be thrown.
	 */
	I_PP_MRP firstOnly();
	
	/**
	 * 
	 * @return all matching {@link I_PP_MRP} records.
	 */
	List<I_PP_MRP> list();

	/**
	 * Clears all filters (also the default ones!). After calling this method this filter will do nothing (i.e. pass-through).
	 */
	IMRPQueryBuilder clear();

	IMRPQueryBuilder setContextProvider(Object contextProvider);

	IMRPQueryBuilder setContextProvider(Properties ctx, String trxName);

	/**
	 * Sets {@link IMaterialPlanningContext} to be used.
	 * 
	 * From MRP context following things are taken:
	 * <ul>
	 * <li>context provider: ctx, trxName
	 * <li>planning dimension: AD_Client_ID, AD_Org_ID, M_Warehouse_ID, S_Resource_ID/PP_Plant_ID
	 * <li>product
	 * </ul>
	 * 
	 * @param mrpContext
	 * @return this
	 */
	IMRPQueryBuilder setMRPContext(final IMaterialPlanningContext mrpContext);

	IMRPQueryBuilder setAD_Client_ID(Integer adClientId);

	IMRPQueryBuilder setAD_Org_ID(Integer adOrgId);

	IMRPQueryBuilder setPP_Plant_ID(Integer ppPlantId);

	/**
	 * 
	 * @param acceptNoPlant true if we shall also retrieve MRP records which have no PP_Plant_ID set (e.g. Demands from Sales Orders which are not bounded to a plant).
	 * @return this
	 */
	IMRPQueryBuilder setAcceptWithoutPlant(boolean acceptNoPlant);

	IMRPQueryBuilder setM_Warehouse_ID(Integer warehouseId);

	IMRPQueryBuilder setM_Product_ID(Integer productId);

	/**
	 * 
	 * @param datePromisedMax last DatePromised (inclusive) to be considered
	 * @return this
	 */
	IMRPQueryBuilder setDatePromisedMax(final Date datePromisedMax);

	IMRPQueryBuilder setTypeMRP(final String typeMRP);

	IMRPQueryBuilder setLowLevelCode(final int productLLC);

	IMRPQueryBuilder setMRPFirmType(MRPFirmType mrpFirmType);

	/**
	 * 
	 * @param qtyNotZero true if we shall filter out MRP records where Qty is zero
	 * @return this
	 */
	IMRPQueryBuilder setQtyNotZero(boolean qtyNotZero);

	/**
	 * Filter by {@link I_PP_MRP#COLUMNNAME_IsAvailable} flag
	 * 
	 * @param mrpAvailable
	 * @return this
	 */
	IMRPQueryBuilder setMRPAvailable(Boolean mrpAvailable);

	IMRPQueryBuilder setOnlyActiveRecords(boolean onlyActiveRecords);

	/**
	 * Filter by {@link I_PP_MRP#COLUMNNAME_OrderType}
	 * 
	 * @param orderType
	 * @return this
	 */
	IMRPQueryBuilder setOrderType(String orderType);

	IMRPQueryBuilder addOnlyOrderType(String orderType);

	IMRPQueryBuilder setReferencedModel(Object referencedModel);

	/**
	 * Retrieve only those {@link I_PP_MRP} records where {@link I_PP_MRP#getPP_Order_BOMLine()} is null.
	 * @return 
	 */
	IMRPQueryBuilder setPP_Order_BOMLine_Null();

	IMRPQueryBuilder addOnlyPP_MRP_IDs(Collection<Integer> mrpIds);

	IMRPQueryBuilder addOnlyPP_MRP_ID(int mrpId);

	IMRPQueryBuilder addOnlyPP_MRPs(Collection<I_PP_MRP> mrps);

	IMRPQueryBuilder addOnlyPP_MRP(I_PP_MRP mrp);

	IMRPQueryBuilder setEnforced_PP_MRP_Demand_ID(int mrpId);

	/**
	 * Set if we shall skip the MRP records which shall be excluded by MRP_Exclude option (on BPartner, Product etc).
	 * 
	 * @param skipIfMRPExcluded
	 * @return this
	 */
	IMRPQueryBuilder setSkipIfMRPExcluded(boolean skipIfMRPExcluded);
}
