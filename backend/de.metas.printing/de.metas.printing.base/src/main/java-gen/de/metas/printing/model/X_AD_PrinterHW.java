// Generated Model - DO NOT CHANGE
package de.metas.printing.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PrinterHW
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_PrinterHW extends org.compiere.model.PO implements I_AD_PrinterHW, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1403843343L;

    /** Standard Constructor */
    public X_AD_PrinterHW (final Properties ctx, final int AD_PrinterHW_ID, @Nullable final String trxName)
    {
      super (ctx, AD_PrinterHW_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_PrinterHW (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_PrinterHW_ID (final int AD_PrinterHW_ID)
	{
		if (AD_PrinterHW_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, AD_PrinterHW_ID);
	}

	@Override
	public int getAD_PrinterHW_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
	}

	@Override
	public void setHostKey (final @Nullable java.lang.String HostKey)
	{
		set_ValueNoCheck (COLUMNNAME_HostKey, HostKey);
	}

	@Override
	public java.lang.String getHostKey() 
	{
		return get_ValueAsString(COLUMNNAME_HostKey);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
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
	public void setOutputType (final java.lang.String OutputType)
	{
		set_Value (COLUMNNAME_OutputType, OutputType);
	}

	@Override
	public java.lang.String getOutputType() 
	{
		return get_ValueAsString(COLUMNNAME_OutputType);
	}
}