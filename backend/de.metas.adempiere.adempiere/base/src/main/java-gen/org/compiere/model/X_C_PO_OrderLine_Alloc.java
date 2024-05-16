// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_PO_OrderLine_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PO_OrderLine_Alloc extends org.compiere.model.PO implements I_C_PO_OrderLine_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -797670499L;

    /** Standard Constructor */
    public X_C_PO_OrderLine_Alloc (final Properties ctx, final int C_PO_OrderLine_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, C_PO_OrderLine_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PO_OrderLine_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_PO_OrderLine_Alloc_ID (final int C_PO_OrderLine_Alloc_ID)
	{
		if (C_PO_OrderLine_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PO_OrderLine_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PO_OrderLine_Alloc_ID, C_PO_OrderLine_Alloc_ID);
	}

	@Override
	public int getC_PO_OrderLine_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PO_OrderLine_Alloc_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_PO_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_PO_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_PO_OrderLine(final org.compiere.model.I_C_OrderLine C_PO_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_PO_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_PO_OrderLine);
	}

	@Override
	public void setC_PO_OrderLine_ID (final int C_PO_OrderLine_ID)
	{
		if (C_PO_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_PO_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_PO_OrderLine_ID, C_PO_OrderLine_ID);
	}

	@Override
	public int getC_PO_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PO_OrderLine_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_SO_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_SO_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_SO_OrderLine(final org.compiere.model.I_C_OrderLine C_SO_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_SO_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_SO_OrderLine);
	}

	@Override
	public void setC_SO_OrderLine_ID (final int C_SO_OrderLine_ID)
	{
		if (C_SO_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_SO_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_SO_OrderLine_ID, C_SO_OrderLine_ID);
	}

	@Override
	public int getC_SO_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SO_OrderLine_ID);
	}
}