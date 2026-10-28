// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for I_DeliveryPlanning_Data
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_I_DeliveryPlanning_Data extends org.compiere.model.PO implements I_I_DeliveryPlanning_Data, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1278135338L;

    /** Standard Constructor */
    public X_I_DeliveryPlanning_Data (final Properties ctx, final int I_DeliveryPlanning_Data_ID, @Nullable final String trxName)
    {
      super (ctx, I_DeliveryPlanning_Data_ID, trxName);
    }

    /** Load Constructor */
    public X_I_DeliveryPlanning_Data (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setFileName (final @Nullable java.lang.String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	@Override
	public java.lang.String getFileName() 
	{
		return get_ValueAsString(COLUMNNAME_FileName);
	}

	@Override
	public void setI_DeliveryPlanning_Data_ID (final int I_DeliveryPlanning_Data_ID)
	{
		if (I_DeliveryPlanning_Data_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_DeliveryPlanning_Data_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_DeliveryPlanning_Data_ID, I_DeliveryPlanning_Data_ID);
	}

	@Override
	public int getI_DeliveryPlanning_Data_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_DeliveryPlanning_Data_ID);
	}

	@Override
	public void setImported (final @Nullable java.sql.Timestamp Imported)
	{
		set_Value (COLUMNNAME_Imported, Imported);
	}

	@Override
	public java.sql.Timestamp getImported() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Imported);
	}

	@Override
	public void setIsReadyForProcessing (final boolean IsReadyForProcessing)
	{
		set_Value (COLUMNNAME_IsReadyForProcessing, IsReadyForProcessing);
	}

	@Override
	public boolean isReadyForProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReadyForProcessing);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}