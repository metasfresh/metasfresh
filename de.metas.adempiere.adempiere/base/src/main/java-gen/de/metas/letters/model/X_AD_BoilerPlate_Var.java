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
package de.metas.letters.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_BoilerPlate_Var
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_BoilerPlate_Var extends org.compiere.model.PO implements I_AD_BoilerPlate_Var, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1279595309L;

    /** Standard Constructor */
    public X_AD_BoilerPlate_Var (Properties ctx, int AD_BoilerPlate_Var_ID, String trxName)
    {
      super (ctx, AD_BoilerPlate_Var_ID, trxName);
      /** if (AD_BoilerPlate_Var_ID == 0)
        {
			setAD_BoilerPlate_Var_ID (0);
			setName (null);
			setType (null);
// S
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_AD_BoilerPlate_Var (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AD_BoilerPlate_Var[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Boiler Plate Variable.
		@param AD_BoilerPlate_Var_ID Boiler Plate Variable	  */
	@Override
	public void setAD_BoilerPlate_Var_ID (int AD_BoilerPlate_Var_ID)
	{
		if (AD_BoilerPlate_Var_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_BoilerPlate_Var_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BoilerPlate_Var_ID, Integer.valueOf(AD_BoilerPlate_Var_ID));
	}

	/** Get Boiler Plate Variable.
		@return Boiler Plate Variable	  */
	@Override
	public int getAD_BoilerPlate_Var_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_BoilerPlate_Var_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Rule getAD_Rule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Rule_ID, org.compiere.model.I_AD_Rule.class);
	}

	@Override
	public void setAD_Rule(org.compiere.model.I_AD_Rule AD_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Rule_ID, org.compiere.model.I_AD_Rule.class, AD_Rule);
	}

	/** Set Rule.
		@param AD_Rule_ID Rule	  */
	@Override
	public void setAD_Rule_ID (int AD_Rule_ID)
	{
		if (AD_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Rule_ID, Integer.valueOf(AD_Rule_ID));
	}

	/** Get Rule.
		@return Rule	  */
	@Override
	public int getAD_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Validation code.
		@param Code 
		Validation Code
	  */
	@Override
	public void setCode (java.lang.String Code)
	{
		set_Value (COLUMNNAME_Code, Code);
	}

	/** Get Validation code.
		@return Validation Code
	  */
	@Override
	public java.lang.String getCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Code);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** 
	 * Type AD_Reference_ID=540047
	 * Reference name: AD_BoilerPlate_VarType
	 */
	public static final int TYPE_AD_Reference_ID=540047;
	/** SQL = S */
	public static final String TYPE_SQL = "S";
	/** Rule Engine = R */
	public static final String TYPE_RuleEngine = "R";
	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public void setType (java.lang.String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public java.lang.String getType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}