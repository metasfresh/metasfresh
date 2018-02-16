/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_CreditLimit_Type
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_CreditLimit_Type extends org.compiere.model.PO implements I_C_CreditLimit_Type, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -545376154L;

    /** Standard Constructor */
    public X_C_CreditLimit_Type (Properties ctx, int C_CreditLimit_Type_ID, String trxName)
    {
      super (ctx, C_CreditLimit_Type_ID, trxName);
      /** if (C_CreditLimit_Type_ID == 0)
        {
			setC_CreditLimit_Type_ID (0);
			setIsAutoApproval (false); // N
			setName (null);
			setSeqNo (BigDecimal.ZERO); // 1
        } */
    }

    /** Load Constructor */
    public X_C_CreditLimit_Type (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Credit Limit Type.
		@param C_CreditLimit_Type_ID Credit Limit Type	  */
	@Override
	public void setC_CreditLimit_Type_ID (int C_CreditLimit_Type_ID)
	{
		if (C_CreditLimit_Type_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CreditLimit_Type_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CreditLimit_Type_ID, Integer.valueOf(C_CreditLimit_Type_ID));
	}

	/** Get Credit Limit Type.
		@return Credit Limit Type	  */
	@Override
	public int getC_CreditLimit_Type_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CreditLimit_Type_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Auto Approval.
		@param IsAutoApproval Auto Approval	  */
	@Override
	public void setIsAutoApproval (boolean IsAutoApproval)
	{
		set_Value (COLUMNNAME_IsAutoApproval, Boolean.valueOf(IsAutoApproval));
	}

	/** Get Auto Approval.
		@return Auto Approval	  */
	@Override
	public boolean isAutoApproval () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoApproval);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (java.math.BigDecimal SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public java.math.BigDecimal getSeqNo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SeqNo);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}