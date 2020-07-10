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

/** Generated Interface for AD_Attribute_Value
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_Attribute_Value 
{

    /** TableName=AD_Attribute_Value */
    public static final String Table_Name = "AD_Attribute_Value";

    /** AD_Table_ID=406 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Attribute_ID */
    public static final String COLUMNNAME_AD_Attribute_ID = "AD_Attribute_ID";

	/** Set System Attribute	  */
	public void setAD_Attribute_ID (int AD_Attribute_ID);

	/** Get System Attribute	  */
	public int getAD_Attribute_ID();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name V_Date */
    public static final String COLUMNNAME_V_Date = "V_Date";

	/** Set V_Date	  */
	public void setV_Date (Timestamp V_Date);

	/** Get V_Date	  */
	public Timestamp getV_Date();

    /** Column name V_Number */
    public static final String COLUMNNAME_V_Number = "V_Number";

	/** Set V_Number	  */
	public void setV_Number (String V_Number);

	/** Get V_Number	  */
	public String getV_Number();

    /** Column name V_String */
    public static final String COLUMNNAME_V_String = "V_String";

	/** Set V_String	  */
	public void setV_String (String V_String);

	/** Get V_String	  */
	public String getV_String();
}
