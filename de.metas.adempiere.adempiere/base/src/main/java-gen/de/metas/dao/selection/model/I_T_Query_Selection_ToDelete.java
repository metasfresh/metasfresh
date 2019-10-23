package de.metas.dao.selection.model;


/** Generated Interface for T_Query_Selection_ToDelete
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_T_Query_Selection_ToDelete 
{

    /** TableName=T_Query_Selection_ToDelete */
    public static final String Table_Name = "T_Query_Selection_ToDelete";

    /** AD_Table_ID=541368 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Set Executor_UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExecutor_UUID (java.lang.String Executor_UUID);

	/**
	 * Get Executor_UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExecutor_UUID();

    /** Column definition for Executor_UUID */
    public static final org.adempiere.model.ModelColumn<I_T_Query_Selection_ToDelete, Object> COLUMN_Executor_UUID = new org.adempiere.model.ModelColumn<I_T_Query_Selection_ToDelete, Object>(I_T_Query_Selection_ToDelete.class, "Executor_UUID", null);
    /** Column name Executor_UUID */
    public static final String COLUMNNAME_Executor_UUID = "Executor_UUID";

	/**
	 * Set UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUUID (java.lang.String UUID);

	/**
	 * Get UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUUID();

    /** Column definition for UUID */
    public static final org.adempiere.model.ModelColumn<I_T_Query_Selection_ToDelete, Object> COLUMN_UUID = new org.adempiere.model.ModelColumn<I_T_Query_Selection_ToDelete, Object>(I_T_Query_Selection_ToDelete.class, "UUID", null);
    /** Column name UUID */
    public static final String COLUMNNAME_UUID = "UUID";
}
