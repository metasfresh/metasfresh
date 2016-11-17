package de.metas.fresh.mrp_productinfo;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.api.IParams;

/*
 * #%L
 * de.metas.fresh.base
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

public interface IMRPProductInfoSelectorFactory extends ISingletonService
{
	/**
	 * Create a selector for the given <code>model</code>. Support the following kinds of models:
	 * <ul>
	 * <li>M_InOutLine
	 * <li>M_MovementLine
	 * <li>C_OrderLine
	 * <li>Fresh_QtyOnHand
	 * <li>X_MRP_ProductInfo_Detail_MV
	 * </ul>
	 * <p>
	 * Notes: In case another table is added to the view <code>"de.metas.sq80".M_Product_ID_M_AttributeSetInstance_ID_V</code>, then also this factory needs to be extended.<br>
	 * M_InOutLine and M_MovementLine are actually about particular M_Transactions, but we can't work with M_Transtactions directly as they are a bit elusive (they are deleted on reactivate and then
	 * we can't async-process them anymore).<br>
	 * Also, please keeps the date related code in sync.
	 *
	 * @param model
	 * @return
	 */
	IMRPProductInfoSelector createOrNull(Object model);

	/**
	 * Create a selector using the given <code>model</code>, but set the selector's <code>M_Product_ID</code>, <code>M_attributeSetInstance_ID</code> and <code>Date</code> from the given <code>params</code>.
	 * If the given <code>params</code> is <code>null</code>, then delegate to {@link #createOrNull(Object)}.
	 *
	 * @param model
	 * @param params
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/409
	 */
	IMRPProductInfoSelector createOrNull(Object model, IParams params);
}
