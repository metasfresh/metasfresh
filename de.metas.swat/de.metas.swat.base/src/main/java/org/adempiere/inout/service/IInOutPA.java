package org.adempiere.inout.service;

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
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MPackage;

import de.metas.inout.model.I_M_InOutLine;


public interface IInOutPA extends ISingletonService
{

	I_M_InOutLine retrieveInOutLine(int inoutLineId, String trxName);

	MInOutLine createNewLine(I_M_InOut inOut, String trxName);

	void setLineWareHouseId(I_M_InOutLine line, int warehouseId);

	void setLineOrderLine(I_M_InOutLine line, I_C_OrderLine orderLine, int locatorId, BigDecimal qty);

	void setLineQty(I_M_InOutLine line, BigDecimal qty);

	List<I_M_InOutLine> retrieveLinesForOrderLine(I_C_OrderLine orderLine, String where, String trxName);

	void renumberLinesWithoutComment(int step, I_M_InOut inOut);

	Collection<MPackage> retrieve(Properties ctx, I_M_InOut inOut, String trxName);

}
