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

import org.adempiere.ad.dao.IQueryBuilder;
import org.eevolution.model.I_PP_MRP;

import javax.annotation.Nullable;

/**
 * To get an instance call {@link IMRPDAO#createMRPQueryBuilder()}.
 * 
 * @author tsa
 *
 */
public interface IMRPQueryBuilder
{
	IQueryBuilder<I_PP_MRP> createQueryBuilder();

	IMRPQueryBuilder setContextProvider(Object contextProvider);

	IMRPQueryBuilder setAD_Client_ID(Integer adClientId);

	IMRPQueryBuilder setAD_Org_ID(Integer adOrgId);

	IMRPQueryBuilder setPP_Plant_ID(Integer ppPlantId);

	IMRPQueryBuilder setM_Warehouse_ID(Integer warehouseId);

	IMRPQueryBuilder setM_Product_ID(Integer productId);

	IMRPQueryBuilder setTypeMRP(final String typeMRP);

	IMRPQueryBuilder setOnlyActiveRecords(boolean onlyActiveRecords);

	/**
	 * Filter by {@link I_PP_MRP#COLUMNNAME_OrderType}
	 */
	IMRPQueryBuilder setOrderType(String orderType);

	IMRPQueryBuilder setReferencedModel(Object referencedModel);

	/**
	 * Retrieve only those {@link I_PP_MRP} records where {@link I_PP_MRP#getPP_Order_BOMLine()} is null.
	 */
	void setPP_Order_BOMLine_Null();
}
