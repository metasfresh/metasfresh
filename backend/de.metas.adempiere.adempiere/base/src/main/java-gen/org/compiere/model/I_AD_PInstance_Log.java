<<<<<<< HEAD
/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for AD_PInstance_Log
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_PInstance_Log 
{

    /** TableName=AD_PInstance_Log */
    public static final String Table_Name = "AD_PInstance_Log";

    /** AD_Table_ID=578 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

    /** Load Meta Data */

    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/** Set Process Instance.
	  * Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/** Get Process Instance.
	  * Instance of the process
	  */
	public int getAD_PInstance_ID();

	public I_AD_PInstance getAD_PInstance() throws RuntimeException;

    /** Column name Log_ID */
    public static final String COLUMNNAME_Log_ID = "Log_ID";

	/** Set Log	  */
	public void setLog_ID (int Log_ID);

	/** Get Log	  */
	public int getLog_ID();

    /** Column name P_Date */
    public static final String COLUMNNAME_P_Date = "P_Date";

	/** Set Process Date.
	  * Process Parameter
	  */
	public void setP_Date (Timestamp P_Date);

	/** Get Process Date.
	  * Process Parameter
	  */
	public Timestamp getP_Date();

    /** Column name P_ID */
    public static final String COLUMNNAME_P_ID = "P_ID";

	/** Set Process ID	  */
	public void setP_ID (int P_ID);

	/** Get Process ID	  */
	public int getP_ID();

    /** Column name P_Msg */
    public static final String COLUMNNAME_P_Msg = "P_Msg";

	/** Set Process Message	  */
	public void setP_Msg (String P_Msg);

	/** Get Process Message	  */
	public String getP_Msg();

    /** Column name P_Number */
    public static final String COLUMNNAME_P_Number = "P_Number";

	/** Set Process Number.
	  * Process Parameter
	  */
	public void setP_Number (BigDecimal P_Number);

	/** Get Process Number.
	  * Process Parameter
	  */
	public BigDecimal getP_Number();
=======
package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
