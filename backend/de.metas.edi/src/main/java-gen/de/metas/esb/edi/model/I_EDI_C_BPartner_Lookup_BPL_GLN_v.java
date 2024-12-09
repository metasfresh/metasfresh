package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_C_BPartner_Lookup_BPL_GLN_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_C_BPartner_Lookup_BPL_GLN_v 
{

    /** TableName=EDI_C_BPartner_Lookup_BPL_GLN_v */
    public static final String Table_Name = "EDI_C_BPartner_Lookup_BPL_GLN_v";

    /** AD_Table_ID=540552 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_C_BPartner_Lookup_BPL_GLN_v 
{

	String Table_Name = "EDI_C_BPartner_Lookup_BPL_GLN_v";

//	/** AD_Table_ID=540552 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))


	/**
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setC_BPartner_ID (int C_BPartner_ID);
=======
	void setC_BPartner_ID (int C_BPartner_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
=======
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setGLN (java.lang.String GLN);
=======
	void setGLN (@Nullable java.lang.String GLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getGLN();

    /** Column definition for GLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_GLN = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "GLN", null);
    /** Column name GLN */
    public static final String COLUMNNAME_GLN = "GLN";
=======
	@Nullable java.lang.String getGLN();

	ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_GLN = new ModelColumn<>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "GLN", null);
	String COLUMNNAME_GLN = "GLN";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);
=======
	void setIsActive (boolean IsActive);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
=======
	boolean isActive();

	ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lookup Label.
	 * Can be used to differentiate when different data records otherwise have the same lookup characteristics.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLookup_Label (@Nullable java.lang.String Lookup_Label);

	/**
	 * Get Lookup Label.
	 * Can be used to differentiate when different data records otherwise have the same lookup characteristics.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLookup_Label();

	ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_Lookup_Label = new ModelColumn<>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "Lookup_Label", null);
	String COLUMNNAME_Lookup_Label = "Lookup_Label";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set StoreGLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setStoreGLN (java.lang.String StoreGLN);
=======
	void setStoreGLN (@Nullable java.lang.String StoreGLN);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get StoreGLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getStoreGLN();

    /** Column definition for StoreGLN */
    public static final org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_StoreGLN = new org.adempiere.model.ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "StoreGLN", null);
    /** Column name StoreGLN */
    public static final String COLUMNNAME_StoreGLN = "StoreGLN";
=======
	@Nullable java.lang.String getStoreGLN();

	ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_StoreGLN = new ModelColumn<>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "StoreGLN", null);
	String COLUMNNAME_StoreGLN = "StoreGLN";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
