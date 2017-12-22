package org.adempiere.inout.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.inout.service.IInOutPA;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.MiscUtils;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
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
		
		final List<I_M_InOutLine> result = new ArrayList<>(lines.length);

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
}
