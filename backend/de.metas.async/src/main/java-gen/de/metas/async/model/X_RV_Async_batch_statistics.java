// Generated Model - DO NOT CHANGE
package de.metas.async.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for RV_Async_batch_statistics
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RV_Async_batch_statistics extends org.compiere.model.PO implements I_RV_Async_batch_statistics, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1052013634L;

    /** Standard Constructor */
    public X_RV_Async_batch_statistics (final Properties ctx, final int RV_Async_batch_statistics_ID, @Nullable final String trxName)
    {
      super (ctx, RV_Async_batch_statistics_ID, trxName);
    }

    /** Load Constructor */
    public X_RV_Async_batch_statistics (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public de.metas.async.model.I_C_Async_Batch getC_Async_Batch()
	{
		return get_ValueAsPO(COLUMNNAME_C_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class);
	}

	@Override
	public void setC_Async_Batch(final de.metas.async.model.I_C_Async_Batch C_Async_Batch)
	{
		set_ValueFromPO(COLUMNNAME_C_Async_Batch_ID, de.metas.async.model.I_C_Async_Batch.class, C_Async_Batch);
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
	public void setCountEnqueued (final int CountEnqueued)
	{
		set_ValueNoCheck (COLUMNNAME_CountEnqueued, CountEnqueued);
	}

	@Override
	public int getCountEnqueued() 
	{
		return get_ValueAsInt(COLUMNNAME_CountEnqueued);
	}

	@Override
	public void setCountProcessed (final int CountProcessed)
	{
		set_ValueNoCheck (COLUMNNAME_CountProcessed, CountProcessed);
	}

	@Override
	public int getCountProcessed() 
	{
		return get_ValueAsInt(COLUMNNAME_CountProcessed);
	}

	@Override
	public de.metas.async.model.I_C_Queue_PackageProcessor getC_Queue_PackageProcessor()
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class);
	}

	@Override
	public void setC_Queue_PackageProcessor(final de.metas.async.model.I_C_Queue_PackageProcessor C_Queue_PackageProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class, C_Queue_PackageProcessor);
	}

	@Override
	public void setC_Queue_PackageProcessor_ID (final int C_Queue_PackageProcessor_ID)
	{
		if (C_Queue_PackageProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_PackageProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_PackageProcessor_ID, C_Queue_PackageProcessor_ID);
	}

	@Override
	public int getC_Queue_PackageProcessor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_PackageProcessor_ID);
	}
}