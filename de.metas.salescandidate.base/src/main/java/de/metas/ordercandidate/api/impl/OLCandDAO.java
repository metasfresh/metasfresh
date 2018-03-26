package de.metas.ordercandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;

public class OLCandDAO implements IOLCandDAO
{
	@Override
	public List<I_C_OLCand> retrieveReferencing(final Properties ctx, final String tableName, final int recordId, final String trxName)
	{
		Check.assume(!Check.isEmpty(tableName), "Param 'tableName' is not empty");
		Check.assume(recordId > 0, "Param 'recordId' is > 0");

		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OLCand.class, ctx, trxName)
				.addEqualsFilter(I_C_OLCand.COLUMN_AD_Table_ID, tableId)
				.addEqualsFilter(I_C_OLCand.COLUMN_Record_ID, recordId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_C_OLCand.COLUMN_C_OLCand_ID)
				.create()
				.list(I_C_OLCand.class);
	}

	@Override
	public <T extends I_C_OLCand> List<T> retrieveOLCands(final I_C_OrderLine ol, final Class<T> clazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);
		final int orderLineId = ol.getC_OrderLine_ID();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order_Line_Alloc.class, ctx, trxName)
				.addEqualsFilter(I_C_Order_Line_Alloc.COLUMN_C_OrderLine_ID, orderLineId)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_C_Order_Line_Alloc.COLUMN_C_OLCand_ID)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_C_OLCand.COLUMN_C_OLCand_ID)
				.create()
				.list(clazz);
	}

	@Override
	public List<I_C_Order_Line_Alloc> retrieveAllOlas(final I_C_OrderLine ol)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ol);
		final String trxName = InterfaceWrapperHelper.getTrxName(ol);
		final int orderLineId = ol.getC_OrderLine_ID();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order_Line_Alloc.class, ctx, trxName)
				.addEqualsFilter(I_C_Order_Line_Alloc.COLUMN_C_OrderLine_ID, orderLineId)
				// .addOnlyActiveRecordsFilter() // note that we also load records that are inactive or belong to different AD_Clients
				.orderBy(I_C_Order_Line_Alloc.COLUMN_C_Order_Line_Alloc_ID)
				.create()
				.list(I_C_Order_Line_Alloc.class);
	}

	@Override
	public List<I_C_Order_Line_Alloc> retrieveAllOlas(final I_C_OLCand olCand)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);
		final int olCandId = olCand.getC_OLCand_ID();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order_Line_Alloc.class, ctx, trxName)
				.addEqualsFilter(I_C_Order_Line_Alloc.COLUMN_C_OLCand_ID, olCandId)
				// .addOnlyActiveRecordsFilter() // note that we also load records that are inactive or belong to different AD_Clients
				.orderBy(I_C_Order_Line_Alloc.COLUMN_C_Order_Line_Alloc_ID)
				.create()
				.list(I_C_Order_Line_Alloc.class);
	}
}
