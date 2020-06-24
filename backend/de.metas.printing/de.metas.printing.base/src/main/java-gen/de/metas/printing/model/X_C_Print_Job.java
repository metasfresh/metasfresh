/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Print_Job
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Print_Job extends org.compiere.model.PO implements I_C_Print_Job, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1440503648L;

    /** Standard Constructor */
    public X_C_Print_Job (Properties ctx, int C_Print_Job_ID, String trxName)
    {
      super (ctx, C_Print_Job_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Print_Job (Properties ctx, ResultSet rs, String trxName)
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
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setC_Print_Job_ID (int C_Print_Job_ID)
	{
		if (C_Print_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_ID, Integer.valueOf(C_Print_Job_ID));
	}

	@Override
	public int getC_Print_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_ID);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}