package de.metas.esb.edi.model;


/** Generated Interface for EDI_AD_Org_Lookup_BPL_GLN_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_AD_Org_Lookup_BPL_GLN_v 
{

    /** TableName=EDI_AD_Org_Lookup_BPL_GLN_v */
    public static final String Table_Name = "EDI_AD_Org_Lookup_BPL_GLN_v";

    /** AD_Table_ID=540546 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGLN (java.lang.String GLN);

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGLN();

    /** Column definition for GLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_AD_Org_Lookup_BPL_GLN_v, Object> COLUMN_GLN = new org.adempiere.model.ModelColumn<I_EDI_AD_Org_Lookup_BPL_GLN_v, Object>(I_EDI_AD_Org_Lookup_BPL_GLN_v.class, "GLN", null);
    /** Column name GLN */
    public static final String COLUMNNAME_GLN = "GLN";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_AD_Org_Lookup_BPL_GLN_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_AD_Org_Lookup_BPL_GLN_v, Object>(I_EDI_AD_Org_Lookup_BPL_GLN_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
}
