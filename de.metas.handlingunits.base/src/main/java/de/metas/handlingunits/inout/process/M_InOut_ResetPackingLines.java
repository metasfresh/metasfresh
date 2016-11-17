package de.metas.handlingunits.inout.process;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.process.SvrProcess;

import de.metas.handlingunits.inout.IHUInOutBL;

/**
 * Deletes and Recreates an inout's packing material lines. Supposed to be called via gear.
 *
 */
public class M_InOut_ResetPackingLines extends SvrProcess implements ISvrProcessPrecondition
{

	@Override
	protected void prepare()
	{
		// nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_InOut inout = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_M_InOut.class, getTrxName());
		Services.get(IHUInOutBL.class).recreatePackingMaterialLines(inout);

		return "@Success@";
	}

	/**
	 * Returns <code>true</code> for unprocessed shipments only.
	 */
	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		if (!I_M_InOut.Table_Name.equals(context.getTableName()))
		{
			return false;
		}

		final I_M_InOut inout = context.getModel(I_M_InOut.class);
		return inout.isSOTrx() && !inout.isProcessed();
	}

}
