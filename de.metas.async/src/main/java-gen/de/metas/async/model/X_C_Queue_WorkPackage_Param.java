/** Generated Model - DO NOT CHANGE */
package de.metas.async.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Queue_WorkPackage_Param
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Queue_WorkPackage_Param extends org.compiere.model.PO implements I_C_Queue_WorkPackage_Param, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2074310948L;

    /** Standard Constructor */
    public X_C_Queue_WorkPackage_Param (Properties ctx, int C_Queue_WorkPackage_Param_ID, String trxName)
    {
      super (ctx, C_Queue_WorkPackage_Param_ID, trxName);
      /** if (C_Queue_WorkPackage_Param_ID == 0)
        {
			setAD_Reference_ID (0);
			setC_Queue_WorkPackage_ID (0);
			setC_Queue_WorkPackage_Param_ID (0);
			setParameterName (null);
        } */
    }

    /** Load Constructor */
    public X_C_Queue_WorkPackage_Param (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Reference getAD_Reference()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class, AD_Reference);
	}

	/** Set Referenz.
		@param AD_Reference_ID 
		Systemreferenz und Validierung
	  */
	@Override
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Referenz.
		@return Systemreferenz und Validierung
	  */
	@Override
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Queue_WorkPackage getC_Queue_WorkPackage()
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class);
	}

	@Override
	public void setC_Queue_WorkPackage(de.metas.async.model.I_C_Queue_WorkPackage C_Queue_WorkPackage)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class, C_Queue_WorkPackage);
	}

	/** Set WorkPackage Queue.
		@param C_Queue_WorkPackage_ID WorkPackage Queue	  */
	@Override
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, Integer.valueOf(C_Queue_WorkPackage_ID));
	}

	/** Get WorkPackage Queue.
		@return WorkPackage Queue	  */
	@Override
	public int getC_Queue_WorkPackage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_WorkPackage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Workpackage parameter.
		@param C_Queue_WorkPackage_Param_ID Workpackage parameter	  */
	@Override
	public void setC_Queue_WorkPackage_Param_ID (int C_Queue_WorkPackage_Param_ID)
	{
		if (C_Queue_WorkPackage_Param_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_Param_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_Param_ID, Integer.valueOf(C_Queue_WorkPackage_Param_ID));
	}

	/** Get Workpackage parameter.
		@return Workpackage parameter	  */
	@Override
	public int getC_Queue_WorkPackage_Param_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_WorkPackage_Param_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Date.
		@param P_Date 
		Prozess-Parameter
	  */
	@Override
	public void setP_Date (java.sql.Timestamp P_Date)
	{
		set_Value (COLUMNNAME_P_Date, P_Date);
	}

	/** Get Process Date.
		@return Prozess-Parameter
	  */
	@Override
	public java.sql.Timestamp getP_Date () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_P_Date);
	}

	/** Set Process Number.
		@param P_Number 
		Prozess-Parameter
	  */
	@Override
	public void setP_Number (java.math.BigDecimal P_Number)
	{
		set_Value (COLUMNNAME_P_Number, P_Number);
	}

	/** Get Process Number.
		@return Prozess-Parameter
	  */
	@Override
	public java.math.BigDecimal getP_Number () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_P_Number);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Process String.
		@param P_String 
		Prozess-Parameter
	  */
	@Override
	public void setP_String (java.lang.String P_String)
	{
		set_Value (COLUMNNAME_P_String, P_String);
	}

	/** Get Process String.
		@return Prozess-Parameter
	  */
	@Override
	public java.lang.String getP_String () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_P_String);
	}

	/** Set Parameter Name.
		@param ParameterName Parameter Name	  */
	@Override
	public void setParameterName (java.lang.String ParameterName)
	{
		set_ValueNoCheck (COLUMNNAME_ParameterName, ParameterName);
	}

	/** Get Parameter Name.
		@return Parameter Name	  */
	@Override
	public java.lang.String getParameterName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ParameterName);
	}
}