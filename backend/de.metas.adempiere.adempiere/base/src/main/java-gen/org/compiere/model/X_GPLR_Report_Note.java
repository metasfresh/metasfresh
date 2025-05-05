// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GPLR_Report_Note
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GPLR_Report_Note extends org.compiere.model.PO implements I_GPLR_Report_Note, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 857024966L;

    /** Standard Constructor */
    public X_GPLR_Report_Note (final Properties ctx, final int GPLR_Report_Note_ID, @Nullable final String trxName)
    {
      super (ctx, GPLR_Report_Note_ID, trxName);
    }

    /** Load Constructor */
    public X_GPLR_Report_Note (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_GPLR_Report getGPLR_Report()
	{
		return get_ValueAsPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class);
	}

	@Override
	public void setGPLR_Report(final org.compiere.model.I_GPLR_Report GPLR_Report)
	{
		set_ValueFromPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class, GPLR_Report);
	}

	@Override
	public void setGPLR_Report_ID (final int GPLR_Report_ID)
	{
		if (GPLR_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, GPLR_Report_ID);
	}

	@Override
	public int getGPLR_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_ID);
	}

	@Override
	public void setGPLR_Report_Note_ID (final int GPLR_Report_Note_ID)
	{
		if (GPLR_Report_Note_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Note_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_Note_ID, GPLR_Report_Note_ID);
	}

	@Override
	public int getGPLR_Report_Note_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_Note_ID);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public void setSource (final @Nullable java.lang.String Source)
	{
		set_Value (COLUMNNAME_Source, Source);
	}

	@Override
	public java.lang.String getSource() 
	{
		return get_ValueAsString(COLUMNNAME_Source);
	}
}