package de.metas.fresh.mrp_productinfo;

import java.util.List;

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.api.IParams;
import org.compiere.model.I_AD_InfoColumn;

import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
import de.metas.fresh.model.I_X_MRP_ProductInfo_V;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IMRPProductInfoBL extends ISingletonService
{

	String DB_FUNCTION_X_MRP_PRODUCT_INFO_DETAIL_MV_REFRESH = "\"de.metas.fresh\".X_MRP_ProductInfo_Detail_MV_Refresh";

	String DB_FUNCTION_X_MRP_PRODUCTINFO_DETAIL_UPDATE_POOR_MANS_MRP = "\"de.metas.fresh\".x_mrp_productinfo_detail_update_poor_mans_mrp";

	/**
	 * Update {@link I_X_MRP_ProductInfo_Detail_MV} records that are matched by the given <code>items</code>.<br>
	 * For this, first call the DB function {@value #DB_FUNCTION_X_MRP_PRODUCT_INFO_DETAIL_MV_REFRESH}.
	 * <p>
	 * If the {@link I_AD_InfoColumn} for the {@link I_X_MRP_ProductInfo_V#COLUMNNAME_Fresh_QtyMRP} is present and active,
	 * then call the DB function {@value #DB_FUNCTION_X_MRP_PRODUCTINFO_DETAIL_UPDATE_POOR_MANS_MRP}.
	 * <p>
	 * Finally, if the {@link I_AD_InfoColumn} for the {@link I_X_MRP_ProductInfo_V#COLUMNNAME_QtyOnHand} is present and active, update {@link I_X_MRP_ProductInfo_Detail_MV#COLUMN_QtyOnHand} like this:
	 * <ul>
	 * <li>get the {@link IMRPProductInfoSelector} for the current item.
	 * <li>retrieve all {@link I_X_MRP_ProductInfo_Detail_MV}s with the current selector's <code>M_Product_ID</code>, <code>ASIKey</code> and <code>DateGeneral</code> equal <b>or greater</b> than the selector's date.
	 * <li>there are multiple records for the same selector (i.e. multiple {@link I_X_MRP_ProductInfo_Detail_MV}s with the same date, product and ASIKey), check if there is a "real" one with <code>IsFallBack='N'</code>.<br>
	 * If yes, update it and skip over <code>IsFallBack='N'</code>-records that come later. If no, the copy the first fallback-one, create a "real"-one from it and update that real-one's <code>QtyOnHand</code>. Again, skip any further fallback-ones.<br>
	 * For the "skipping" to work, it is important to have the correct {@link IMRPProductInfoSelector#equals(Object)} implementation.
	 * <li>About the actual <code>QtyOnHand</code> update: if teh current <code>item</code>'s model is an <code>M_InOutLine</code>, and QtyOnHand is not <code>null</code>, then just add or subtract the <code>M_InOutLine</code>'s <code>MovementQty</code>.<br>
	 * Otherwise, fire up the storage engine to compute the <code>QtyOnHand</code> value from scratch.
	 * </ul>
	 *
	 *
	 * @param ctxProvider
	 * @param items objects that can be turned into {@link IMRPProductInfoSelector}s by the {@link IMRPProductInfoSelectorFactory}.
	 * @param params if not <code>null</code>, then the params will be passed on to the {@link IMRPProductInfoSelectorFactory}
	 *
	 */
	void updateItems(IContextAware ctxProvider, List<Object> items, IParams params);

}
