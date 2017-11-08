package de.metas.contracts.flatrate.interfaces;

import de.metas.contracts.model.I_C_Flatrate_Conditions;

public interface IFlatrateConditionsAware
{
	// @formatter:off
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";
	
	void setC_Flatrate_Conditions(I_C_Flatrate_Conditions C_Flatrate_Conditions);
	void setC_Flatrate_Conditions_ID(int C_Flatrate_Conditions_ID);
	I_C_Flatrate_Conditions getC_Flatrate_Conditions();
	int getC_Flatrate_Conditions_ID();
	// @formatter:on
}
