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
/** Generated Model - DO NOT CHANGE */
package org.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for AD_TriggerUI_Criteria
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a#333 - $Id$ */
public class X_AD_TriggerUI_Criteria extends PO implements I_AD_TriggerUI_Criteria, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110620L;

    /** Standard Constructor */
    public X_AD_TriggerUI_Criteria (Properties ctx, int AD_TriggerUI_Criteria_ID, String trxName)
    {
      super (ctx, AD_TriggerUI_Criteria_ID, trxName);
      /** if (AD_TriggerUI_Criteria_ID == 0)
        {
			setAD_TriggerUI_Criteria_ID (0);
			setAD_TriggerUI_ID (0);
			setEntityType (null);
			setIsMandatory (false);
// N
			setIsNullFieldValue (false);
// N
			setType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_TriggerUI_Criteria (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AD_TriggerUI_Criteria[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Column)MTable.get(getCtx(), org.compiere.model.I_AD_Column.Table_Name)
			.getPO(getAD_Column_ID(), get_TrxName());	}

	/** Set Spalte.
		@param AD_Column_ID 
		Spalte in der Tabelle
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Spalte in der Tabelle
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_Reference getAD_Reference() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Reference)MTable.get(getCtx(), org.compiere.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_ID(), get_TrxName());	}

	/** Set Referenz.
		@param AD_Reference_ID 
		Systemreferenz und Validierung
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		throw new IllegalArgumentException ("AD_Reference_ID is virtual column");	}

	/** Get Referenz.
		@return Systemreferenz und Validierung
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UI Trigger Criteria.
		@param AD_TriggerUI_Criteria_ID UI Trigger Criteria	  */
	public void setAD_TriggerUI_Criteria_ID (int AD_TriggerUI_Criteria_ID)
	{
		if (AD_TriggerUI_Criteria_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_TriggerUI_Criteria_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_TriggerUI_Criteria_ID, Integer.valueOf(AD_TriggerUI_Criteria_ID));
	}

	/** Get UI Trigger Criteria.
		@return UI Trigger Criteria	  */
	public int getAD_TriggerUI_Criteria_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_TriggerUI_Criteria_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_TriggerUI getAD_TriggerUI() throws RuntimeException
    {
		return (I_AD_TriggerUI)MTable.get(getCtx(), I_AD_TriggerUI.Table_Name)
			.getPO(getAD_TriggerUI_ID(), get_TrxName());	}

	/** Set UI Trigger.
		@param AD_TriggerUI_ID UI Trigger	  */
	public void setAD_TriggerUI_ID (int AD_TriggerUI_ID)
	{
		if (AD_TriggerUI_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_TriggerUI_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_TriggerUI_ID, Integer.valueOf(AD_TriggerUI_ID));
	}

	/** Get UI Trigger.
		@return UI Trigger	  */
	public int getAD_TriggerUI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_TriggerUI_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Attribute Name.
		@param AttributeName 
		Name of the Attribute
	  */
	public void setAttributeName (String AttributeName)
	{
		set_Value (COLUMNNAME_AttributeName, AttributeName);
	}

	/** Get Attribute Name.
		@return Name of the Attribute
	  */
	public String getAttributeName () 
	{
		return (String)get_Value(COLUMNNAME_AttributeName);
	}

	/** Set Beschreibung.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitaets-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitaets-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Field Value.
		@param FieldValue Field Value	  */
	public void setFieldValue (String FieldValue)
	{
		set_Value (COLUMNNAME_FieldValue, FieldValue);
	}

	/** Get Field Value.
		@return Field Value	  */
	public String getFieldValue () 
	{
		return (String)get_Value(COLUMNNAME_FieldValue);
	}

	/** Set Value Format.
		@param FieldValueFormat Value Format	  */
	public void setFieldValueFormat (String FieldValueFormat)
	{
		set_Value (COLUMNNAME_FieldValueFormat, FieldValueFormat);
	}

	/** Get Value Format.
		@return Value Format	  */
	public String getFieldValueFormat () 
	{
		return (String)get_Value(COLUMNNAME_FieldValueFormat);
	}

	/** Set Mandatory.
		@param IsMandatory 
		Data entry is required in this column
	  */
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Mandatory.
		@return Data entry is required in this column
	  */
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Null Value.
		@param IsNullFieldValue Null Value	  */
	public void setIsNullFieldValue (boolean IsNullFieldValue)
	{
		set_Value (COLUMNNAME_IsNullFieldValue, Boolean.valueOf(IsNullFieldValue));
	}

	/** Get Null Value.
		@return Null Value	  */
	public boolean isNullFieldValue () 
	{
		Object oo = get_Value(COLUMNNAME_IsNullFieldValue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Operation AD_Reference_ID=205 */
	public static final int OPERATION_AD_Reference_ID=205;
	/**  = = == */
	public static final String OPERATION_Eq = "==";
	/** >= = >= */
	public static final String OPERATION_GtEq = ">=";
	/** > = >> */
	public static final String OPERATION_Gt = ">>";
	/** < = << */
	public static final String OPERATION_Le = "<<";
	/**  ~ = ~~ */
	public static final String OPERATION_Like = "~~";
	/** <= = <= */
	public static final String OPERATION_LeEq = "<=";
	/** |<x>| = AB */
	public static final String OPERATION_X = "AB";
	/** sql = SQ */
	public static final String OPERATION_Sql = "SQ";
	/** != = != */
	public static final String OPERATION_NotEq = "!=";
	/** Set Arbeitsvorgang .
		@param Operation 
		Compare Operation
	  */
	public void setOperation (String Operation)
	{

		set_Value (COLUMNNAME_Operation, Operation);
	}

	/** Get Arbeitsvorgang .
		@return Compare Operation
	  */
	public String getOperation () 
	{
		return (String)get_Value(COLUMNNAME_Operation);
	}

	/** Type AD_Reference_ID=540202 */
	public static final int TYPE_AD_Reference_ID=540202;
	/** Field Value = FV */
	public static final String TYPE_FieldValue = "FV";
	/** Context Value = CV */
	public static final String TYPE_ContextValue = "CV";
	/** Set Art.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}
}
