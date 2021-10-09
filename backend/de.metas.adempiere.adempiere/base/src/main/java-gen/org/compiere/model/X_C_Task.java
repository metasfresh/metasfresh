// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Task
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Task extends org.compiere.model.PO implements I_C_Task, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -274065570L;

    /** Standard Constructor */
    public X_C_Task (final Properties ctx, final int C_Task_ID, @Nullable final String trxName)
    {
      super (ctx, C_Task_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Task (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Phase getC_Phase()
	{
		return get_ValueAsPO(COLUMNNAME_C_Phase_ID, org.compiere.model.I_C_Phase.class);
	}

	@Override
	public void setC_Phase(final org.compiere.model.I_C_Phase C_Phase)
	{
		set_ValueFromPO(COLUMNNAME_C_Phase_ID, org.compiere.model.I_C_Phase.class, C_Phase);
	}

	@Override
	public void setC_Phase_ID (final int C_Phase_ID)
	{
		if (C_Phase_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Phase_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Phase_ID, C_Phase_ID);
	}

	@Override
	public int getC_Phase_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Phase_ID);
	}

	@Override
	public void setC_Task_ID (final int C_Task_ID)
	{
		if (C_Task_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Task_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Task_ID, C_Task_ID);
	}

	@Override
	public int getC_Task_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Task_ID);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setHelp (final java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setStandardQty (final BigDecimal StandardQty)
	{
		set_Value (COLUMNNAME_StandardQty, StandardQty);
	}

	@Override
	public BigDecimal getStandardQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_StandardQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}