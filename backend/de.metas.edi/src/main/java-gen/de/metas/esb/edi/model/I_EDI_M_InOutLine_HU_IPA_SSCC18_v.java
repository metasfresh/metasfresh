package de.metas.esb.edi.model;

<<<<<<< HEAD
=======
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/** Generated Interface for EDI_M_InOutLine_HU_IPA_SSCC18_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public interface I_EDI_M_InOutLine_HU_IPA_SSCC18_v 
{

    /** TableName=EDI_M_InOutLine_HU_IPA_SSCC18_v */
    public static final String Table_Name = "EDI_M_InOutLine_HU_IPA_SSCC18_v";

    /** AD_Table_ID=540541 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
=======
@SuppressWarnings("unused")
public interface I_EDI_M_InOutLine_HU_IPA_SSCC18_v 
{

	String Table_Name = "EDI_M_InOutLine_HU_IPA_SSCC18_v";

//	/** AD_Table_ID=540541 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))


	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAttributeName (java.lang.String AttributeName);
=======
	void setAttributeName (@Nullable java.lang.String AttributeName);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getAttributeName();

    /** Column definition for AttributeName */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_AttributeName = new org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "AttributeName", null);
    /** Column name AttributeName */
    public static final String COLUMNNAME_AttributeName = "AttributeName";
=======
	@Nullable java.lang.String getAttributeName();

	ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_AttributeName = new ModelColumn<>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "AttributeName", null);
	String COLUMNNAME_AttributeName = "AttributeName";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_HU_ID (int M_HU_ID);
=======
	void setM_HU_ID (int M_HU_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_HU_ID();

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "M_HU_ID", null);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";
=======
	int getM_HU_ID();

	ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_M_HU_ID = new ModelColumn<>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "M_HU_ID", null);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_InOutLine_ID (int M_InOutLine_ID);
=======
	void setM_InOutLine_ID (int M_InOutLine_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_InOutLine_ID();

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, org.compiere.model.I_M_InOutLine>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";
=======
	int getM_InOutLine_ID();

	ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setValue (java.lang.String Value);
=======
	void setValue (@Nullable java.lang.String Value);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
=======
	@Nullable java.lang.String getValue();

	ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_Value = new ModelColumn<>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "Value", null);
	String COLUMNNAME_Value = "Value";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
