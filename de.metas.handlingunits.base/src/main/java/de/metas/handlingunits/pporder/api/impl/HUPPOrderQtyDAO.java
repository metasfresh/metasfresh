package de.metas.handlingunits.pporder.api.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HUPPOrderQtyDAO implements IHUPPOrderQtyDAO
{
	@Override
	public List<I_PP_Order_Qty> retrieveOrderQtys(final int ppOrderId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_Qty.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_Qty.COLUMN_PP_Order_ID, ppOrderId)
				.create()
				.list(I_PP_Order_Qty.class);

	}

}
