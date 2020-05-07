/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Printing_Queue_Recipient
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Printing_Queue_Recipient extends org.compiere.model.PO implements I_C_Printing_Queue_Recipient, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1435794584L;

    /** Standard Constructor */
    public X_C_Printing_Queue_Recipient (Properties ctx, int C_Printing_Queue_Recipient_ID, String trxName)
    {
      super (ctx, C_Printing_Queue_Recipient_ID, trxName);
      /** if (C_Printing_Queue_Recipient_ID == 0)
        {
			setAD_User_ToPrint_ID (0);
			setC_Printing_Queue_Recipient_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Printing_Queue_Recipient (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_User getAD_User_ToPrint() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ToPrint_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_ToPrint(org.compiere.model.I_AD_User AD_User_ToPrint)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ToPrint_ID, org.compiere.model.I_AD_User.class, AD_User_ToPrint);
	}

	/** Set Ausdruck für.
		@param AD_User_ToPrint_ID Ausdruck für	  */
	@Override
	public void setAD_User_ToPrint_ID (int AD_User_ToPrint_ID)
	{
		if (AD_User_ToPrint_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ToPrint_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ToPrint_ID, Integer.valueOf(AD_User_ToPrint_ID));
	}

	/** Get Ausdruck für.
		@return Ausdruck für	  */
	@Override
	public int getAD_User_ToPrint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ToPrint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.printing.model.I_C_Printing_Queue getC_Printing_Queue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class);
	}

	@Override
	public void setC_Printing_Queue(de.metas.printing.model.I_C_Printing_Queue C_Printing_Queue)
	{
		set_ValueFromPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class, C_Printing_Queue);
	}

	/** Set Druck-Warteschlangendatensatz.
		@param C_Printing_Queue_ID Druck-Warteschlangendatensatz	  */
	@Override
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID)
	{
		if (C_Printing_Queue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, Integer.valueOf(C_Printing_Queue_ID));
	}

	/** Get Druck-Warteschlangendatensatz.
		@return Druck-Warteschlangendatensatz	  */
	@Override
	public int getC_Printing_Queue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Printing_Queue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Druck-Empfänger.
		@param C_Printing_Queue_Recipient_ID 
		Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.
	  */
	@Override
	public void setC_Printing_Queue_Recipient_ID (int C_Printing_Queue_Recipient_ID)
	{
		if (C_Printing_Queue_Recipient_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_Recipient_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_Recipient_ID, Integer.valueOf(C_Printing_Queue_Recipient_ID));
	}

	/** Get Druck-Empfänger.
		@return Bei einem automatischer Druck über das "Massendruck" Framework ist nicht der eigenentlich druckende Nutzer, sondern der jeweils als Druck-Empfänger eingerichtete Nutzer der Empfänger der Druckstücke.
	  */
	@Override
	public int getC_Printing_Queue_Recipient_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Printing_Queue_Recipient_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}