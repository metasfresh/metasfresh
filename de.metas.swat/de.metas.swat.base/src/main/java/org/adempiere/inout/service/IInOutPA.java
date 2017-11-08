package org.adempiere.inout.service;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MPackage;

import de.metas.inout.model.I_M_InOutLine;


public interface IInOutPA extends ISingletonService
{
	I_M_InOutLine retrieveInOutLine(int inoutLineId, String trxName);

	void setLineWareHouseId(I_M_InOutLine line, int warehouseId);

	List<I_M_InOutLine> retrieveLinesForOrderLine(I_C_OrderLine orderLine, String where, String trxName);

	void renumberLinesWithoutComment(int step, I_M_InOut inOut);

	Collection<MPackage> retrieve(Properties ctx, I_M_InOut inOut, String trxName);

}
