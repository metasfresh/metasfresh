package de.metas.contracts.model;

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


import java.sql.ResultSet;
import java.util.Properties;

import de.metas.contracts.model.X_C_Contract_Change;

public class MCContractChange extends X_C_Contract_Change
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2441395360731780186L;

	public MCContractChange(final Properties ctx, final int C_SubscriptionChange_ID, final String trxName)
	{
		super(ctx, C_SubscriptionChange_ID, trxName);
	}

	public MCContractChange(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer("MCContractChange[") //
				.append(get_ID()) //
				.append(", C_Flatrate_Transition_ID=").append(getC_Flatrate_Transition_ID()) //
				.append(", Action=").append(getAction()) //
				.append(", DeadLine=").append(getDeadLine()) //
				.append(", DeadLineUnit=").append(getDeadLineUnit());

		if (ACTION_Abowechsel.equals(getAction()))
		{
			sb.append(", C_Flatrate_Conditions_Next_ID=").append(getC_Flatrate_Conditions_Next_ID());
		}
		else
		{
			sb.append(", ContractStatus=").append(getContractStatus());
		}
		sb.append(", M_PricingSystem_ID=").append(getM_PricingSystem_ID());
		sb.append(", M_Product_ID=").append(getM_Product_ID());
		sb.append("]");

		return sb.toString();
	}
}
