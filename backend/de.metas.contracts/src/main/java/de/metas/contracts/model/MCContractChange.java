package de.metas.contracts.model;

import java.io.Serial;
import java.sql.ResultSet;
import java.util.Properties;

import de.metas.contracts.model.X_C_Contract_Change;

public class MCContractChange extends X_C_Contract_Change
{

	/**
	 * 
	 */
	@Serial
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
