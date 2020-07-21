/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Print_PackageInfo
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Print_PackageInfo extends org.compiere.model.PO implements I_C_Print_PackageInfo, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1741868480L;

    /** Standard Constructor */
    public X_C_Print_PackageInfo (Properties ctx, int C_Print_PackageInfo_ID, String trxName)
    {
      super (ctx, C_Print_PackageInfo_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Print_PackageInfo (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.printing.model.I_AD_PrinterHW_MediaTray getAD_PrinterHW_MediaTray()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_MediaTray_ID, de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
	}

	@Override
	public void setAD_PrinterHW_MediaTray(de.metas.printing.model.I_AD_PrinterHW_MediaTray AD_PrinterHW_MediaTray)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_MediaTray_ID, de.metas.printing.model.I_AD_PrinterHW_MediaTray.class, AD_PrinterHW_MediaTray);
	}

	@Override
	public void setAD_PrinterHW_MediaTray_ID (int AD_PrinterHW_MediaTray_ID)
	{
		if (AD_PrinterHW_MediaTray_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaTray_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaTray_ID, Integer.valueOf(AD_PrinterHW_MediaTray_ID));
	}

	@Override
	public int getAD_PrinterHW_MediaTray_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_MediaTray_ID);
	}

	@Override
	public void setCalX (int CalX)
	{
		set_Value (COLUMNNAME_CalX, Integer.valueOf(CalX));
	}

	@Override
	public int getCalX() 
	{
		return get_ValueAsInt(COLUMNNAME_CalX);
	}

	@Override
	public void setCalY (int CalY)
	{
		set_Value (COLUMNNAME_CalY, Integer.valueOf(CalY));
	}

	@Override
	public int getCalY() 
	{
		return get_ValueAsInt(COLUMNNAME_CalY);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Package getC_Print_Package()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class);
	}

	@Override
	public void setC_Print_Package(de.metas.printing.model.I_C_Print_Package C_Print_Package)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Package_ID, de.metas.printing.model.I_C_Print_Package.class, C_Print_Package);
	}

	@Override
	public void setC_Print_Package_ID (int C_Print_Package_ID)
	{
		if (C_Print_Package_ID < 1) 
			set_Value (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_Value (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
	}

	@Override
	public int getC_Print_Package_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Package_ID);
	}

	@Override
	public void setC_Print_PackageInfo_ID (int C_Print_PackageInfo_ID)
	{
		if (C_Print_PackageInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_PackageInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_PackageInfo_ID, Integer.valueOf(C_Print_PackageInfo_ID));
	}

	@Override
	public int getC_Print_PackageInfo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_PackageInfo_ID);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		throw new IllegalArgumentException ("Name is virtual column");	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public void setPageFrom (int PageFrom)
	{
		set_Value (COLUMNNAME_PageFrom, Integer.valueOf(PageFrom));
	}

	@Override
	public int getPageFrom() 
	{
		return get_ValueAsInt(COLUMNNAME_PageFrom);
	}

	@Override
	public void setPageTo (int PageTo)
	{
		set_Value (COLUMNNAME_PageTo, Integer.valueOf(PageTo));
	}

	@Override
	public int getPageTo() 
	{
		return get_ValueAsInt(COLUMNNAME_PageTo);
	}

	@Override
	public void setPrintServiceName (java.lang.String PrintServiceName)
	{
		throw new IllegalArgumentException ("PrintServiceName is virtual column");	}

	@Override
	public java.lang.String getPrintServiceName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintServiceName);
	}

	@Override
	public void setTrayNumber (int TrayNumber)
	{
		throw new IllegalArgumentException ("TrayNumber is virtual column");	}

	@Override
	public int getTrayNumber() 
	{
		return get_ValueAsInt(COLUMNNAME_TrayNumber);
	}
}