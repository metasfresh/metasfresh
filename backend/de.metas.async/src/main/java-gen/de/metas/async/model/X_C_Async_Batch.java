// Generated Model - DO NOT CHANGE
package de.metas.async.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Async_Batch
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Async_Batch extends org.compiere.model.PO implements I_C_Async_Batch, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 859483800L;

    /** Standard Constructor */
    public X_C_Async_Batch (final Properties ctx, final int C_Async_Batch_ID, @Nullable final String trxName)
    {
      super (ctx, C_Async_Batch_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Async_Batch (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	@Override
	public void setC_Async_Batch_ID (final int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_ID, C_Async_Batch_ID);
	}

	@Override
	public int getC_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public de.metas.async.model.I_C_Async_Batch_Type getC_Async_Batch_Type()
	{
		return get_ValueAsPO(COLUMNNAME_C_Async_Batch_Type_ID, de.metas.async.model.I_C_Async_Batch_Type.class);
	}

	@Override
	public void setC_Async_Batch_Type(final de.metas.async.model.I_C_Async_Batch_Type C_Async_Batch_Type)
	{
		set_ValueFromPO(COLUMNNAME_C_Async_Batch_Type_ID, de.metas.async.model.I_C_Async_Batch_Type.class, C_Async_Batch_Type);
	}

	@Override
	public void setC_Async_Batch_Type_ID (final int C_Async_Batch_Type_ID)
	{
		if (C_Async_Batch_Type_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_Type_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_Type_ID, C_Async_Batch_Type_ID);
	}

	@Override
	public int getC_Async_Batch_Type_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_Type_ID);
	}

	@Override
	public void setCountEnqueued (final int CountEnqueued)
	{
		set_Value (COLUMNNAME_CountEnqueued, CountEnqueued);
	}

	@Override
	public int getCountEnqueued() 
	{
		return get_ValueAsInt(COLUMNNAME_CountEnqueued);
	}

	@Override
	public void setCountExpected (final int CountExpected)
	{
		set_Value (COLUMNNAME_CountExpected, CountExpected);
	}

	@Override
	public int getCountExpected() 
	{
		return get_ValueAsInt(COLUMNNAME_CountExpected);
	}

	@Override
	public void setCountProcessed (final int CountProcessed)
	{
		set_Value (COLUMNNAME_CountProcessed, CountProcessed);
	}

	@Override
	public int getCountProcessed() 
	{
		return get_ValueAsInt(COLUMNNAME_CountProcessed);
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
	public void setFirstEnqueued (final @Nullable java.sql.Timestamp FirstEnqueued)
	{
		set_Value (COLUMNNAME_FirstEnqueued, FirstEnqueued);
	}

	@Override
	public java.sql.Timestamp getFirstEnqueued() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_FirstEnqueued);
	}

	@Override
	public void setIsProcessing (final boolean IsProcessing)
	{
		set_Value (COLUMNNAME_IsProcessing, IsProcessing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsProcessing);
	}

	@Override
	public void setLastEnqueued (final @Nullable java.sql.Timestamp LastEnqueued)
	{
		set_Value (COLUMNNAME_LastEnqueued, LastEnqueued);
	}

	@Override
	public java.sql.Timestamp getLastEnqueued() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LastEnqueued);
	}

	@Override
	public void setLastProcessed (final @Nullable java.sql.Timestamp LastProcessed)
	{
		set_Value (COLUMNNAME_LastProcessed, LastProcessed);
	}

	@Override
	public java.sql.Timestamp getLastProcessed() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_LastProcessed);
	}

	@Override
	public de.metas.async.model.I_C_Queue_WorkPackage getLastProcessed_WorkPackage()
	{
		return get_ValueAsPO(COLUMNNAME_LastProcessed_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class);
	}

	@Override
	public void setLastProcessed_WorkPackage(final de.metas.async.model.I_C_Queue_WorkPackage LastProcessed_WorkPackage)
	{
		set_ValueFromPO(COLUMNNAME_LastProcessed_WorkPackage_ID, de.metas.async.model.I_C_Queue_WorkPackage.class, LastProcessed_WorkPackage);
	}

	@Override
	public void setLastProcessed_WorkPackage_ID (final int LastProcessed_WorkPackage_ID)
	{
		if (LastProcessed_WorkPackage_ID < 1) 
			set_Value (COLUMNNAME_LastProcessed_WorkPackage_ID, null);
		else 
			set_Value (COLUMNNAME_LastProcessed_WorkPackage_ID, LastProcessed_WorkPackage_ID);
	}

	@Override
	public int getLastProcessed_WorkPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_LastProcessed_WorkPackage_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public de.metas.async.model.I_C_Async_Batch getParent_Async_Batch()
	{
		return get_ValueAsPO(COLUMNNAME_Parent_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class);
	}

	@Override
	public void setParent_Async_Batch(final de.metas.async.model.I_C_Async_Batch Parent_Async_Batch)
	{
		set_ValueFromPO(COLUMNNAME_Parent_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class, Parent_Async_Batch);
	}

	@Override
	public void setParent_Async_Batch_ID (final int Parent_Async_Batch_ID)
	{
		if (Parent_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_Parent_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_Async_Batch_ID, Parent_Async_Batch_ID);
	}

	@Override
	public int getParent_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Parent_Async_Batch_ID);
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