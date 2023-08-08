// Generated Model - DO NOT CHANGE
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Picking_Job_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Picking_Job_Line extends org.compiere.model.PO implements I_M_Picking_Job_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 711241014L;

    /** Standard Constructor */
    public X_M_Picking_Job_Line (final Properties ctx, final int M_Picking_Job_Line_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Job_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Job_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.handlingunits.model.I_M_Picking_Job getM_Picking_Job()
	{
		return get_ValueAsPO(COLUMNNAME_M_Picking_Job_ID, de.metas.handlingunits.model.I_M_Picking_Job.class);
	}

	@Override
	public void setM_Picking_Job(final de.metas.handlingunits.model.I_M_Picking_Job M_Picking_Job)
	{
		set_ValueFromPO(COLUMNNAME_M_Picking_Job_ID, de.metas.handlingunits.model.I_M_Picking_Job.class, M_Picking_Job);
	}

	@Override
	public void setM_Picking_Job_ID (final int M_Picking_Job_ID)
	{
		if (M_Picking_Job_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_ID, M_Picking_Job_ID);
	}

	@Override
	public int getM_Picking_Job_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_ID);
	}

	@Override
	public void setM_Picking_Job_Line_ID (final int M_Picking_Job_Line_ID)
	{
		if (M_Picking_Job_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Job_Line_ID, M_Picking_Job_Line_ID);
	}

	@Override
	public int getM_Picking_Job_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Job_Line_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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