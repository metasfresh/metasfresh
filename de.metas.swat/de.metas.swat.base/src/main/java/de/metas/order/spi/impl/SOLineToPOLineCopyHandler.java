package de.metas.order.spi.impl;

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


import org.adempiere.document.service.ICopyHandler;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.interfaces.I_C_OrderLine;

/**
 * Copy swat specific detail from <code>orderLineFrom</code> to the <code>orderLineTo</code>.<br>
 * Currently used with <code>orderLineFrom</code> being the linked order line of <code>orderLineTo</code>.
 * <p>
 * Background about linked order line: <code>orderLineTo</code> is usually a purchase order line, created from the sales order line <code>orderLineFrom</code>. This includes that
 * <code>orderLineTo</code> references <code>orderLineFrom</code> via <code>Link_OrderLine_ID</code>.
 * 
 * At the moment, the implementation only copies the AD_User_ID. To be extended when more details are necessary.
 * 
 * @param orderLineTo
 * @param orderLineFrom
 * @see IOrderLineBL#createSOLineToPOLineCopyHandlerFilter()
 */
public class SOLineToPOLineCopyHandler implements ICopyHandler<I_C_OrderLine>
{
	@Override
	public void copyPreliminaryValues(final I_C_OrderLine from, final I_C_OrderLine to)
	{
		// nothing to do
	}

	@Override
	public void copyValues(I_C_OrderLine from, I_C_OrderLine to)
	{
		final int userID = from.getAD_User_ID();
		to.setAD_User_ID(userID);
	}

	@Override
	public Class<I_C_OrderLine> getSupportedItemsClass()
	{
		return I_C_OrderLine.class;
	}
}
