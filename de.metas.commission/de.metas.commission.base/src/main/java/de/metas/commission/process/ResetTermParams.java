package de.metas.commission.process;

/*
 * #%L
 * de.metas.commission.base
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


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MTable;

import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.service.ICommissionTermDAO;
import de.metas.process.SvrProcess;

/**
 * Resets the params for a given C_AdvCommissionTerm by deleting and recreating them with default values. Use this process if an term's business logic has changed and there is a different set of
 * parameters
 * 
 * @author ts
 * @see de.metas.commission.custom.type.ICommissionType#getSponsorParams(java.util.Properties, de.metas.commission.model.I_C_AdvCommissionCondition, String)
 * 
 */
public class ResetTermParams extends SvrProcess
{
	@Override
	protected String doIt() throws Exception
	{
		final int commissionTermTableId = MTable.getTable_ID(I_C_AdvCommissionTerm.Table_Name);

		Check.errorIf(getTable_ID() != commissionTermTableId, "Wrong Table_ID: {} instead of expected {}",
				getTable_ID(),
				commissionTermTableId);

		if (getRecord_ID() <= 0)
		{
			return "@Success@"; // nothing to do
		}

		final I_C_AdvCommissionTerm term = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_C_AdvCommissionTerm.class, get_TrxName());

		Services.get(ICommissionTermDAO.class).createParameters(term); // note that createParameters starts by deleting existing parameters

		return "@Success@";
	}

	@Override
	protected void prepare()
	{
		// nothing to do
	}

}
