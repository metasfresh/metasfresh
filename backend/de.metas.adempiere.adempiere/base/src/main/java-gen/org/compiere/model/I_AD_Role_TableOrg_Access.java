package org.compiere.model;

// FIXME generate model
public interface I_AD_Role_TableOrg_Access
{
	String Table_Name = "AD_Role_TableOrg_Access";

	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	int getAD_Table_ID();
	int getAD_Org_ID();

	String getAccess();

	/**
	 * Access AD_Reference_ID=540962
	 * Reference name: AD_User_Access
	 */
	int ACCESS_AD_Reference_ID=540962;
	/** Read = R */
	String ACCESS_Read = "R";
	/** Write = W */
	String ACCESS_Write = "W";
	/** Report = P */
	String ACCESS_Report = "P";
	/** Export = E */
	String ACCESS_Export = "E";
}
