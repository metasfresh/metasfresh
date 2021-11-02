// Generated Model - DO NOT CHANGE
package de.metas.async.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Async_Batch_Milestone
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Async_Batch_Milestone extends org.compiere.model.PO implements I_C_Async_Batch_Milestone, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1529199707L;

    /** Standard Constructor */
    public X_C_Async_Batch_Milestone (final Properties ctx, final int C_Async_Batch_Milestone_ID, @Nullable final String trxName)
    {
      super (ctx, C_Async_Batch_Milestone_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Async_Batch_Milestone (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, C_Async_Batch_ID);
	}

	@Override
	public int getC_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public void setC_Async_Batch_Milestone_ID (final int C_Async_Batch_Milestone_ID)
	{
		if (C_Async_Batch_Milestone_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_Milestone_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Async_Batch_Milestone_ID, C_Async_Batch_Milestone_ID);
	}

	@Override
	public int getC_Async_Batch_Milestone_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_Milestone_ID);
	}

	/** 
	 * Name AD_Reference_ID=541374
	 * Reference name: Async batch milestone name
	 */
	public static final int NAME_AD_Reference_ID=541374;
	/** Lieferungserstellung = Lieferungserstellung */
	public static final String NAME_Lieferungserstellung = "Lieferungserstellung";
	/** Rechnungserstellung = Rechnungserstellung */
	public static final String NAME_Rechnungserstellung = "Rechnungserstellung";
	/** Auftragserstellung = Auftragserstellung */
	public static final String NAME_Auftragserstellung = "Auftragserstellung";
	/** Enqueue schedule for order = EnqueueScheduleForOrder */
	public static final String NAME_EnqueueScheduleForOrder = "EnqueueScheduleForOrder";
	/** Rechnungsdispo = Rechnungsdispo */
	public static final String NAME_Rechnungsdispo = "Rechnungsdispo";
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