/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_PrinterHW_MediaSize
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_PrinterHW_MediaSize extends org.compiere.model.PO implements I_AD_PrinterHW_MediaSize, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1034681283L;

    /** Standard Constructor */
    public X_AD_PrinterHW_MediaSize (Properties ctx, int AD_PrinterHW_MediaSize_ID, String trxName)
    {
      super (ctx, AD_PrinterHW_MediaSize_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_PrinterHW_MediaSize (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class);
	}

	@Override
	public void setAD_PrinterHW(de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class, AD_PrinterHW);
	}

	@Override
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID)
	{
		if (AD_PrinterHW_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, Integer.valueOf(AD_PrinterHW_ID));
	}

	@Override
	public int getAD_PrinterHW_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_ID);
	}

	@Override
	public void setAD_PrinterHW_MediaSize_ID (int AD_PrinterHW_MediaSize_ID)
	{
		if (AD_PrinterHW_MediaSize_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_MediaSize_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_MediaSize_ID, Integer.valueOf(AD_PrinterHW_MediaSize_ID));
	}

	@Override
	public int getAD_PrinterHW_MediaSize_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_MediaSize_ID);
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
}