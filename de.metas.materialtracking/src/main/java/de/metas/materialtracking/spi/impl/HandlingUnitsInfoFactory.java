package de.metas.materialtracking.spi.impl;

/*
 * #%L
 * de.metas.materialtracking
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


import org.adempiere.model.I_C_Invoice_Detail;

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.IHandlingUnitsInfoWritableQty;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;

/**
 * Default implementation of {@link IHandlingUnitsInfoFactory} which does nothing.
 *
 * @author tsa
 *
 */
public class HandlingUnitsInfoFactory implements IHandlingUnitsInfoFactory
{
	/**
	 * @return <code>null</code>
	 */
	@Override
	public IHandlingUnitsInfo createFromModel(final Object model)
	{
		return null;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void updateInvoiceDetail(final I_C_Invoice_Detail invoiceDetail, final IHandlingUnitsInfo handlingUnitsInfo)
	{
		// nothing
	}

	/**
	 * @return <code>null</code>
	 */
	@Override
	public IHandlingUnitsInfoWritableQty createHUInfoWritableQty(IHandlingUnitsInfo ingored)
	{
		return null;
	}
}
