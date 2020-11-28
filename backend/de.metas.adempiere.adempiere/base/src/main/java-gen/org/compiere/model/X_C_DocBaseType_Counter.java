/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DocBaseType_Counter
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DocBaseType_Counter extends org.compiere.model.PO implements I_C_DocBaseType_Counter, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1892124787L;

    /** Standard Constructor */
    public X_C_DocBaseType_Counter (Properties ctx, int C_DocBaseType_Counter_ID, String trxName)
    {
      super (ctx, C_DocBaseType_Counter_ID, trxName);
      /** if (C_DocBaseType_Counter_ID == 0)
        {
			setC_DocBaseType_Counter_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_DocBaseType_Counter (Properties ctx, ResultSet rs, String trxName)
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

	/** Set C_DocBaseType_Counter.
		@param C_DocBaseType_Counter_ID C_DocBaseType_Counter	  */
	@Override
	public void setC_DocBaseType_Counter_ID (int C_DocBaseType_Counter_ID)
	{
		if (C_DocBaseType_Counter_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocBaseType_Counter_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocBaseType_Counter_ID, Integer.valueOf(C_DocBaseType_Counter_ID));
	}

	/** Get C_DocBaseType_Counter.
		@return C_DocBaseType_Counter	  */
	@Override
	public int getC_DocBaseType_Counter_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocBaseType_Counter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Counter_DocBaseType.
		@param Counter_DocBaseType Counter_DocBaseType	  */
	@Override
	public void setCounter_DocBaseType (java.lang.String Counter_DocBaseType)
	{
		set_Value (COLUMNNAME_Counter_DocBaseType, Counter_DocBaseType);
	}

	/** Get Counter_DocBaseType.
		@return Counter_DocBaseType	  */
	@Override
	public java.lang.String getCounter_DocBaseType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Counter_DocBaseType);
	}

	/** Set Document BaseType.
		@param DocBaseType 
		Logical type of document
	  */
	@Override
	public void setDocBaseType (java.lang.String DocBaseType)
	{
		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	/** Get Document BaseType.
		@return Logical type of document
	  */
	@Override
	public java.lang.String getDocBaseType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocBaseType);
	}
}