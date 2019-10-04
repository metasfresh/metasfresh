package de.metas.esb.edi.model;


/** Generated Interface for EDI_M_InOutLine_HU_IPA_SSCC18_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_M_InOutLine_HU_IPA_SSCC18_v 
{

    /** TableName=EDI_M_InOutLine_HU_IPA_SSCC18_v */
    public static final String Table_Name = "EDI_M_InOutLine_HU_IPA_SSCC18_v";

    /** AD_Table_ID=540541 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAttributeName (java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeName();

    /** Column definition for AttributeName */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_AttributeName = new org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "AttributeName", null);
    /** Column name AttributeName */
    public static final String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_ID();

    /** Column definition for M_HU_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_M_HU_ID = new org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "M_HU_ID", null);
    /** Column name M_HU_ID */
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, org.compiere.model.I_M_InOutLine>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
