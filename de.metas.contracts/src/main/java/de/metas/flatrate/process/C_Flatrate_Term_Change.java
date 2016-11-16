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


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.flatrate.api.IContractChangeBL;
import de.metas.flatrate.model.I_C_Contract_Change;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class C_Flatrate_Term_Change extends SvrProcess
{

	public static final String ChangeTerm_ACTION_SwitchContract = "SC";
	public static final String ChangeTerm_ACTION_Cancel = "CA";


	public static final String PARAM_CHANGE_DATE = "EventDate";

	public static final String PARAM_ACTION = I_C_Contract_Change.COLUMNNAME_Action;

	@SuppressWarnings("unused")
	private int newSubscriptionId = 0;

	private String action;

	private Timestamp changeDate;

	@Override
	protected String doIt()
	{
		if (ChangeTerm_ACTION_SwitchContract.equals(action))
		{
			throw new AdempiereException("Not implemented");
		}

		
		final I_C_Flatrate_Term currentTerm = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_C_Flatrate_Term.class, get_TrxName());

		Services.get(IContractChangeBL.class).cancelContract(currentTerm, changeDate);

		return "@Success@";
	}

	

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals(I_C_Flatrate_Transition.COLUMNNAME_C_Flatrate_Conditions_Next_ID))
			{
				// currently not supported by business logic
				newSubscriptionId = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals(PARAM_ACTION))
			{
				action = (String)para[i].getParameter();
			}
			else if (name.equals(PARAM_CHANGE_DATE))
			{
				changeDate = (Timestamp)para[i].getParameter();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}
}
