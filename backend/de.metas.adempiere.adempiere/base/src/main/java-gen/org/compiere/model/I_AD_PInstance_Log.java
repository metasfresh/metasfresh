package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for AD_PInstance_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_PInstance_Log 
{

	String Table_Name = "AD_PInstance_Log";

//	/** AD_Table_ID=578 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/**
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	org.compiere.model.I_AD_PInstance getAD_PInstance();

	void setAD_PInstance(org.compiere.model.I_AD_PInstance AD_PInstance);

	ModelColumn<I_AD_PInstance_Log, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_AD_PInstance_Log.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set AD_PInstance_Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_Log_ID (int AD_PInstance_Log_ID);

	/**
	 * Get AD_PInstance_Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_Log_ID();

	ModelColumn<I_AD_PInstance_Log, Object> COLUMN_AD_PInstance_Log_ID = new ModelColumn<>(I_AD_PInstance_Log.class, "AD_PInstance_Log_ID", null);
	String COLUMNNAME_AD_PInstance_Log_ID = "AD_PInstance_Log_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Protokoll.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLog_ID (int Log_ID);

	/**
	 * Get Protokoll.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLog_ID();

	ModelColumn<I_AD_PInstance_Log, Object> COLUMN_Log_ID = new ModelColumn<>(I_AD_PInstance_Log.class, "Log_ID", null);
	String COLUMNNAME_Log_ID = "Log_ID";

	/**
	 * Set Time.
	 * Time
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setP_Date (@Nullable java.sql.Timestamp P_Date);

	/**
	 * Get Time.
	 * Time
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getP_Date();

	ModelColumn<I_AD_PInstance_Log, Object> COLUMN_P_Date = new ModelColumn<>(I_AD_PInstance_Log.class, "P_Date", null);
	String COLUMNNAME_P_Date = "P_Date";

	/**
	 * Set Process Message.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setP_Msg (@Nullable java.lang.String P_Msg);

	/**
	 * Get Process Message.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getP_Msg();

	ModelColumn<I_AD_PInstance_Log, Object> COLUMN_P_Msg = new ModelColumn<>(I_AD_PInstance_Log.class, "P_Msg", null);
	String COLUMNNAME_P_Msg = "P_Msg";

	/**
	 * Set Process Number.
	 * Process Parameter
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setP_Number (@Nullable BigDecimal P_Number);

	/**
	 * Get Process Number.
	 * Process Parameter
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getP_Number();

	ModelColumn<I_AD_PInstance_Log, Object> COLUMN_P_Number = new ModelColumn<>(I_AD_PInstance_Log.class, "P_Number", null);
	String COLUMNNAME_P_Number = "P_Number";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_AD_PInstance_Log, Object> COLUMN_Record_ID = new ModelColumn<>(I_AD_PInstance_Log.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Warnings.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarnings (@Nullable java.lang.String Warnings);

	/**
	 * Get Warnings.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWarnings();

	ModelColumn<I_AD_PInstance_Log, Object> COLUMN_Warnings = new ModelColumn<>(I_AD_PInstance_Log.class, "Warnings", null);
	String COLUMNNAME_Warnings = "Warnings";
}
