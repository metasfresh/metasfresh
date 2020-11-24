/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PrinterHW
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_PrinterHW extends org.compiere.model.PO implements I_AD_PrinterHW, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -516711222L;

    /** Standard Constructor */
    public X_AD_PrinterHW (Properties ctx, int AD_PrinterHW_ID, String trxName)
    {
      super (ctx, AD_PrinterHW_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_PrinterHW (Properties ctx, ResultSet rs, String trxName)
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
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID)
	{
		if (AD_PrinterHW_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, Integer.valueOf(AD_PrinterHW_ID));
	}

	@Override
	public int getAD_PrinterHW_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_ID);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setHostKey (java.lang.String HostKey)
	{
		set_ValueNoCheck (COLUMNNAME_HostKey, HostKey);
	}

	@Override
	public java.lang.String getHostKey() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HostKey);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** 
	 * OutputType AD_Reference_ID=540632
	 * Reference name: OutputType
	 */
	public static final int OUTPUTTYPE_AD_Reference_ID=540632;
	/** Attach = Attach */
	public static final String OUTPUTTYPE_Attach = "Attach";
	/** Store = Store */
	public static final String OUTPUTTYPE_Store = "Store";
	/** Queue = Queue */
	public static final String OUTPUTTYPE_Queue = "Queue";
	@Override
	public void setOutputType (java.lang.String OutputType)
	{

		set_Value (COLUMNNAME_OutputType, OutputType);
	}

	@Override
	public java.lang.String getOutputType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OutputType);
	}
}