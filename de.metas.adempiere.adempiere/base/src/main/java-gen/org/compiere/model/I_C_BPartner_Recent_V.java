package org.compiere.model;


/** Generated Interface for C_BPartner_Recent_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BPartner_Recent_V 
{

    /** TableName=C_BPartner_Recent_V */
    public static final String Table_Name = "C_BPartner_Recent_V";

    /** AD_Table_ID=541370 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Recent_V, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Recent_V, org.compiere.model.I_C_BPartner>(I_C_BPartner_Recent_V.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set C_BPartner_Recent_V_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Recent_V_ID (int C_BPartner_Recent_V_ID);

	/**
	 * Get C_BPartner_Recent_V_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Recent_V_ID();

    /** Column definition for C_BPartner_Recent_V_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Recent_V, Object> COLUMN_C_BPartner_Recent_V_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Recent_V, Object>(I_C_BPartner_Recent_V.class, "C_BPartner_Recent_V_ID", null);
    /** Column name C_BPartner_Recent_V_ID */
    public static final String COLUMNNAME_C_BPartner_Recent_V_ID = "C_BPartner_Recent_V_ID";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Recent_V, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BPartner_Recent_V, Object>(I_C_BPartner_Recent_V.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";
}
