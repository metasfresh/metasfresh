/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Printing_Queue_Recipient
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Printing_Queue_Recipient extends org.compiere.model.PO implements I_C_Printing_Queue_Recipient, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1550500719L;

    /** Standard Constructor */
    public X_C_Printing_Queue_Recipient (Properties ctx, int C_Printing_Queue_Recipient_ID, String trxName)
    {
      super (ctx, C_Printing_Queue_Recipient_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Printing_Queue_Recipient (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_User_ToPrint_ID (int AD_User_ToPrint_ID)
	{
		if (AD_User_ToPrint_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ToPrint_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ToPrint_ID, Integer.valueOf(AD_User_ToPrint_ID));
	}

	@Override
	public int getAD_User_ToPrint_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ToPrint_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Printing_Queue getC_Printing_Queue()
	{
		return get_ValueAsPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class);
	}

	@Override
	public void setC_Printing_Queue(de.metas.printing.model.I_C_Printing_Queue C_Printing_Queue)
	{
		set_ValueFromPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class, C_Printing_Queue);
	}

	@Override
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID)
	{
		if (C_Printing_Queue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, Integer.valueOf(C_Printing_Queue_ID));
	}

	@Override
	public int getC_Printing_Queue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Printing_Queue_ID);
	}

	@Override
	public void setC_Printing_Queue_Recipient_ID (int C_Printing_Queue_Recipient_ID)
	{
		if (C_Printing_Queue_Recipient_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_Recipient_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_Recipient_ID, Integer.valueOf(C_Printing_Queue_Recipient_ID));
	}

	@Override
	public int getC_Printing_Queue_Recipient_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Printing_Queue_Recipient_ID);
	}
}