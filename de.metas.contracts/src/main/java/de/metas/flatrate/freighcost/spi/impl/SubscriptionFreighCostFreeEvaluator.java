package de.metas.flatrate.freighcost.spi.impl;

/*
 * #%L
 * de.metas.contracts
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


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.Query;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.freighcost.spi.IFreightCostFreeEvaluator;

/**
 * 
 * @author ts
 * 
 */
public class SubscriptionFreighCostFreeEvaluator implements IFreightCostFreeEvaluator
{
	/**
	 * Returns <code>true</code> if the given <code>inOutLine</code>'s C_OrderLine_ID is also referenced by a {@link I_C_Flatrate_Term} that has <code>IsPostageFree='Y'</code>.
	 */
	@Override
	public boolean isFreighCostFree(I_M_InOutLine inOutLine)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(inOutLine);
		final Properties ctx = InterfaceWrapperHelper.getCtx(inOutLine);

		final I_C_Flatrate_Term term =
				new Query(ctx, I_C_Flatrate_Term.Table_Name, I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID + "=?", trxName)
						.setParameters(inOutLine.getC_OrderLine_ID())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.firstOnly(I_C_Flatrate_Term.class);

		return (term == null ? false : term.isPostageFree());
	}

}
