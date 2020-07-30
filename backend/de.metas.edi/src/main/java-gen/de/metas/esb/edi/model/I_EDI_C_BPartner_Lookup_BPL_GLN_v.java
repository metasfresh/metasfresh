package de.metas.esb.edi.model;


/** Generated Interface for EDI_C_BPartner_Lookup_BPL_GLN_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_C_BPartner_Lookup_BPL_GLN_v 
{

    /** TableName=EDI_C_BPartner_Lookup_BPL_GLN_v */
    public static final String Table_Name = "EDI_C_BPartner_Lookup_BPL_GLN_v";

    /** AD_Table_ID=540552 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_GLN = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "GLN", null);
    /** Column name GLN */
    public static final String COLUMNNAME_GLN = "GLN";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set StoreGLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStoreGLN (java.lang.String StoreGLN);

	/**
	 * Get StoreGLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStoreGLN();

    /** Column definition for StoreGLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_StoreGLN = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "StoreGLN", null);
    /** Column name StoreGLN */
    public static final String COLUMNNAME_StoreGLN = "StoreGLN";
}
