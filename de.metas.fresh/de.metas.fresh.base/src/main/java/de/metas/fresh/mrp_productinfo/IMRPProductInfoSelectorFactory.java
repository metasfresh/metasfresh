package de.metas.fresh.mrp_productinfo;

import org.adempiere.util.ISingletonService;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IMRPProductInfoSelectorFactory extends ISingletonService
{
	/**
	 * Supports the following models:
	 * <ul>
	 * <li>M_InOutLine
	 * <li>M_MovementLine
	 * <li>C_OrderLine
	 * <li>Fresh_QtyOnHand
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
	IMRPProdcutInfoSelector createOrNull(Object model);
}
