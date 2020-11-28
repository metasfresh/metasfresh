package de.metas.contracts.flatrate.interfaces;

import de.metas.contracts.model.X_C_Flatrate_Term;

public interface I_C_DocType extends de.metas.interfaces.I_C_DocType
{

	/**
	 * @see X_C_Flatrate_Term#TYPE_CONDITIONS_Pauschalengebuehr
	 */
	public static final String DocSubType_Pauschalengebuehr = "FF";
	/**
	 * @see X_C_Flatrate_Term#TYPE_CONDITIONS_Depotgebuehr
	 */
	public static final String DocSubType_Depotgebuehr = "HF";
	/**
	 * @see X_C_Flatrate_Term#TYPE_CONDITIONS_Abonnement
	 */
	public static final String DocSubType_Abonnement = "SU";
	
	
	public static final String DocBaseType_CustomerContract = "CON";

}
