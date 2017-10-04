package de.metas.contracts.flatrate.interfaces;

import de.metas.contracts.model.I_C_Flatrate_Conditions;

public interface I_C_OLCand extends de.metas.ordercandidate.model.I_C_OLCand
{
	public static final String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	public void setC_Flatrate_Conditions_ID(int C_Flatrate_Conditions_ID);

	public void setC_Flatrate_Conditions(I_C_Flatrate_Conditions C_Flatrate_Conditions);

	public int getC_Flatrate_Conditions_ID();

	public I_C_Flatrate_Conditions getC_Flatrate_Conditions();
}
