package de.metas.flatrate.process;

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


import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MTable;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;

import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.flatrate.Contracts_Constants;
import de.metas.flatrate.interfaces.I_C_OLCand;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;

public class C_FlatrateTerm_Create_From_OLCand extends SvrProcess implements ISvrProcessPrecondition
{

	@Override
	protected void prepare()
	{
		// nothing to do
	}

	@Override
	protected String doIt() throws Exception
	{
		final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);

		if (getRecord_ID() > 0)
		{
			Check.assume(getTable_ID() == MTable.getTable_ID(I_C_OLCand.Table_Name), "Process is called for C_OLCands");

			final I_C_OLCand olCand = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_C_OLCand.class, get_TrxName());
			subscriptionBL.createTermForOLCand(getCtx(), olCand, getAD_PInstance_ID(), true, get_TrxName());
			addLog("@C_OLCand_ID@ " + olCand.getC_OLCand_ID() + " @Processed@");
		}
		else
		{
			final int counter = subscriptionBL.createMissingTermsForOLCands(getCtx(), true, getAD_PInstance_ID(), get_TrxName());
			addLog("@Processed@ " + counter + " @C_OLCand_ID@");
		}

		return "@Success@";
	}

	/**
	 * Method returns true if the given gridTab is a {@link I_C_OLCand} with the correct data destination.
	 * 
	 * @param gridTab
	 */
	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		if (!I_C_OLCand.Table_Name.equals(context.getTableName()))
		{
			return false;
		}

		final I_C_OLCand olCand = context.getModel(I_C_OLCand.class);
		if(olCand.isError())
		{
			return false;
		}
		
		final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);

		final I_AD_InputDataSource dest = inputDataSourceDAO.retrieveInputDataSource(Env.getCtx(), Contracts_Constants.DATA_DESTINATION_INTERNAL_NAME, false, get_TrxName());
		if (dest == null)
		{
			return false;
		}
		if (dest.getAD_InputDataSource_ID() != olCand.getAD_DataDestination_ID())
		{
			return false;
		}

		return true;
	}

}
