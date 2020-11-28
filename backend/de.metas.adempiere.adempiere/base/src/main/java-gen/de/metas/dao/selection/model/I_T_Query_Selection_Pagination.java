package de.metas.dao.selection.model;


/** Generated Interface for T_Query_Selection_Pagination
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_T_Query_Selection_Pagination 
{

    /** TableName=T_Query_Selection_Pagination */
    public static final String Table_Name = "T_Query_Selection_Pagination";

    /** AD_Table_ID=541369 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Set Page_Identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPage_Identifier (java.lang.String Page_Identifier);

	/**
	 * Get Page_Identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPage_Identifier();

    /** Column definition for Page_Identifier */
    public static final org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object> COLUMN_Page_Identifier = new org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object>(I_T_Query_Selection_Pagination.class, "Page_Identifier", null);
    /** Column name Page_Identifier */
    public static final String COLUMNNAME_Page_Identifier = "Page_Identifier";

	/**
	 * Set Page_Offset.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPage_Offset (int Page_Offset);

	/**
	 * Get Page_Offset.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPage_Offset();

    /** Column definition for Page_Offset */
    public static final org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object> COLUMN_Page_Offset = new org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object>(I_T_Query_Selection_Pagination.class, "Page_Offset", null);
    /** Column name Page_Offset */
    public static final String COLUMNNAME_Page_Offset = "Page_Offset";

	/**
	 * Set Page_Size.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPage_Size (int Page_Size);

	/**
	 * Get Page_Size.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPage_Size();

    /** Column definition for Page_Size */
    public static final org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object> COLUMN_Page_Size = new org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object>(I_T_Query_Selection_Pagination.class, "Page_Size", null);
    /** Column name Page_Size */
    public static final String COLUMNNAME_Page_Size = "Page_Size";

	/**
	 * Set Result_Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setResult_Time (java.sql.Timestamp Result_Time);

	/**
	 * Get Result_Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getResult_Time();

    /** Column definition for Result_Time */
    public static final org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object> COLUMN_Result_Time = new org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object>(I_T_Query_Selection_Pagination.class, "Result_Time", null);
    /** Column name Result_Time */
    public static final String COLUMNNAME_Result_Time = "Result_Time";

	/**
	 * Set Total_Size.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTotal_Size (int Total_Size);

	/**
	 * Get Total_Size.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getTotal_Size();

    /** Column definition for Total_Size */
    public static final org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object> COLUMN_Total_Size = new org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object>(I_T_Query_Selection_Pagination.class, "Total_Size", null);
    /** Column name Total_Size */
    public static final String COLUMNNAME_Total_Size = "Total_Size";

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
    public static final org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object> COLUMN_UUID = new org.adempiere.model.ModelColumn<I_T_Query_Selection_Pagination, Object>(I_T_Query_Selection_Pagination.class, "UUID", null);
    /** Column name UUID */
    public static final String COLUMNNAME_UUID = "UUID";
}
