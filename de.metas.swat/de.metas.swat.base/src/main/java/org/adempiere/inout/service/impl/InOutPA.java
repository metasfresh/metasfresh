package org.adempiere.inout.service.impl;

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
import java.util.Properties;

import org.adempiere.inout.service.IInOutPA;
import org.adempiere.misc.service.IPOService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPackage;
import org.compiere.model.Query;
import org.compiere.util.Env;

import de.metas.inout.model.I_M_InOutLine;

public final class InOutPA implements IInOutPA
{

	/**
	 * Invokes {@link MInOutLine#MInOutLine(java.util.Properties, java.sql.ResultSet, String)} .
	 */
	@Override
	public I_M_InOutLine retrieveInOutLine(
			final int inoutLineId,
			final String trxName)
	{
		return InterfaceWrapperHelper.create(new MInOutLine(Env.getCtx(), inoutLineId, trxName), I_M_InOutLine.class);
	}

	/**
	 * Invokes {@link MInOutLine#MInOutLine(java.util.Properties, int, String)}.
	 */
	@Override
	public MInOutLine createNewLine(final I_M_InOut inout, final String trxName)
	{
		final MInOutLine inoutLine = new MInOutLine(Env.getCtx(), 0, trxName);

		final IPOService poService = Services.get(IPOService.class);
		poService.copyClientOrg(inout, inoutLine);
		inoutLine.setM_Warehouse_ID(inout.getM_Warehouse_ID());
		inoutLine.setC_Project_ID(inout.getC_Project_ID());

		// set the inOutId only if the inOut actually has one
		if (inout.getM_InOut_ID() > 0)
		{
			inoutLine.setM_InOut_ID(inout.getM_InOut_ID());
		}

		return inoutLine;
	}

	/**
	 * Invokes {@link MInOutLine#setOrderLine(MOrderLine, int, BigDecimal)}
	 * 
	 * @throws IllegalArgumentException
	 *             if InterfaceWrapperHelper.getPO() is not able to produce an {@link MInOutLine} final the given line or orderLine
	 *             is not an {@link MOrderLine}.
	 */
	@Override
	public void setLineOrderLine(
			final I_M_InOutLine line,
			final I_C_OrderLine orderLine,
			final int locatorId,
			final BigDecimal qty)
	{
		final MInOutLine linePO = (MInOutLine)InterfaceWrapperHelper.getPO(line);
		linePO.setOrderLine(orderLine, locatorId, qty);
	
		line.setIsIndividualDescription(InterfaceWrapperHelper.create(orderLine, de.metas.interfaces.I_C_OrderLine.class).isIndividualDescription());
	}

	/**
	 * Invokes {@link MInOutLine#setQty(BigDecimal)}.
	 * 
	 * @throws IllegalArgumentException
	 *             if line is not an {@link MInOutLine}
	 */
	@Override
	public void setLineQty(final I_M_InOutLine line, final BigDecimal qty)
	{
		final MInOutLine linePO = InterfaceWrapperHelper.getPO(line);
		linePO.setQty(qty);
	}

	/**
	 * Invokes {@link MInOutLine#setM_Warehouse_ID(int)}.
	 * 
	 * @throws IllegalArgumentException
	 *             if line is not an {@link MInOutLine}
	 */
	@Override
	public void setLineWareHouseId(final I_M_InOutLine line, final int warehouseId)
	{
		final MInOutLine linePO = InterfaceWrapperHelper.getPO(line);
		linePO.setM_Warehouse_ID(warehouseId);
	}

	/**
	 * Invokes {@link MInOutLine#getOfOrderLine(java.util.Properties, int, String, String)} .
	 */
	@Override
	public List<I_M_InOutLine> retrieveLinesForOrderLine(
			final I_C_OrderLine orderLine,
			final String where,
			final String trxName)
	{
		final MInOutLine[] lines = MInOutLine.getOfOrderLine(Env.getCtx(), orderLine.getC_OrderLine_ID(), where, trxName);
		
		final List<I_M_InOutLine> result = new ArrayList<I_M_InOutLine>(lines.length);

		for (final MInOutLine currentLine : lines)
		{
			result.add(InterfaceWrapperHelper.create(currentLine, I_M_InOutLine.class));
		}
		return result;
	}

	@Override
	public void renumberLinesWithoutComment(final int step, final I_M_InOut inOut)
	{
		final MInOut inOutPO = MiscUtils.asPO(inOut);
		inOutPO.renumberLinesWithoutComment(step);
	}

	@Override
	public List<MPackage> retrieve(
			final Properties ctx,
			final I_M_InOut inOut,
			final String trxName)
	{
		final String whereClause = I_M_Package.COLUMNNAME_M_InOut_ID + "=?";

		return new Query(ctx, I_M_Package.Table_Name, whereClause, trxName)
				.setParameters(inOut.getM_InOut_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_M_Package.COLUMNNAME_M_Package_ID)
				.list();
	}


}
