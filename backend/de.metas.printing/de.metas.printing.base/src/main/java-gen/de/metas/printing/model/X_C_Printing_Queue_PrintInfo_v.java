/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Printing_Queue_PrintInfo_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Printing_Queue_PrintInfo_v extends org.compiere.model.PO implements I_C_Printing_Queue_PrintInfo_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 374590407L;

    /** Standard Constructor */
    public X_C_Printing_Queue_PrintInfo_v (Properties ctx, int C_Printing_Queue_PrintInfo_v_ID, String trxName)
    {
      super (ctx, C_Printing_Queue_PrintInfo_v_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Printing_Queue_PrintInfo_v (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Archive getAD_Archive()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class);
	}

	@Override
	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class, AD_Archive);
	}

	@Override
	public void setAD_Archive_ID (int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, Integer.valueOf(AD_Archive_ID));
	}

	@Override
	public int getAD_Archive_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Archive_ID);
	}

	@Override
	public void setAD_Org_Print_Job_Instructions_ID (int AD_Org_Print_Job_Instructions_ID)
	{
		if (AD_Org_Print_Job_Instructions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Org_Print_Job_Instructions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Org_Print_Job_Instructions_ID, Integer.valueOf(AD_Org_Print_Job_Instructions_ID));
	}

	@Override
	public int getAD_Org_Print_Job_Instructions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_Print_Job_Instructions_ID);
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
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, Integer.valueOf(AD_PrinterHW_ID));
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
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_MediaTray_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_MediaTray_ID, Integer.valueOf(AD_PrinterHW_MediaTray_ID));
	}

	@Override
	public int getAD_PrinterHW_MediaTray_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_MediaTray_ID);
	}

	@Override
	public org.compiere.model.I_AD_Session getAD_Session_Printpackage()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Session_Printpackage_ID, org.compiere.model.I_AD_Session.class);
	}

	@Override
	public void setAD_Session_Printpackage(org.compiere.model.I_AD_Session AD_Session_Printpackage)
	{
		set_ValueFromPO(COLUMNNAME_AD_Session_Printpackage_ID, org.compiere.model.I_AD_Session.class, AD_Session_Printpackage);
	}

	@Override
	public void setAD_Session_Printpackage_ID (int AD_Session_Printpackage_ID)
	{
		if (AD_Session_Printpackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Session_Printpackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Session_Printpackage_ID, Integer.valueOf(AD_Session_Printpackage_ID));
	}

	@Override
	public int getAD_Session_Printpackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Session_Printpackage_ID);
	}

	@Override
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID)
	{
		if (C_Printing_Queue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, Integer.valueOf(C_Printing_Queue_ID));
	}

	@Override
	public int getC_Printing_Queue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Printing_Queue_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Print_Job_Instructions getC_Print_Job_Instructions()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class);
	}

	@Override
	public void setC_Print_Job_Instructions(de.metas.printing.model.I_C_Print_Job_Instructions C_Print_Job_Instructions)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_Job_Instructions_ID, de.metas.printing.model.I_C_Print_Job_Instructions.class, C_Print_Job_Instructions);
	}

	@Override
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID)
	{
		if (C_Print_Job_Instructions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, Integer.valueOf(C_Print_Job_Instructions_ID));
	}

	@Override
	public int getC_Print_Job_Instructions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Job_Instructions_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
	}

	@Override
	public int getC_Print_Package_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Print_Package_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Print_PackageInfo getC_Print_PackageInfo()
	{
		return get_ValueAsPO(COLUMNNAME_C_Print_PackageInfo_ID, de.metas.printing.model.I_C_Print_PackageInfo.class);
	}

	@Override
	public void setC_Print_PackageInfo(de.metas.printing.model.I_C_Print_PackageInfo C_Print_PackageInfo)
	{
		set_ValueFromPO(COLUMNNAME_C_Print_PackageInfo_ID, de.metas.printing.model.I_C_Print_PackageInfo.class, C_Print_PackageInfo);
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
	public void setCreatedby_Print_Job_Instructions (int Createdby_Print_Job_Instructions)
	{
		set_ValueNoCheck (COLUMNNAME_Createdby_Print_Job_Instructions, Integer.valueOf(Createdby_Print_Job_Instructions));
	}

	@Override
	public int getCreatedby_Print_Job_Instructions() 
	{
		return get_ValueAsInt(COLUMNNAME_Createdby_Print_Job_Instructions);
	}

	@Override
	public void setCreated_Print_Job_Instructions (java.sql.Timestamp Created_Print_Job_Instructions)
	{
		set_ValueNoCheck (COLUMNNAME_Created_Print_Job_Instructions, Created_Print_Job_Instructions);
	}

	@Override
	public java.sql.Timestamp getCreated_Print_Job_Instructions() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Created_Print_Job_Instructions);
	}

	@Override
	public void setPrintServiceName (java.lang.String PrintServiceName)
	{
		set_ValueNoCheck (COLUMNNAME_PrintServiceName, PrintServiceName);
	}

	@Override
	public java.lang.String getPrintServiceName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintServiceName);
	}

	@Override
	public void setPrintServiceTray (java.lang.String PrintServiceTray)
	{
		set_ValueNoCheck (COLUMNNAME_PrintServiceTray, PrintServiceTray);
	}

	@Override
	public java.lang.String getPrintServiceTray() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintServiceTray);
	}

	@Override
	public void setStatus_Print_Job_Instructions (boolean Status_Print_Job_Instructions)
	{
		set_ValueNoCheck (COLUMNNAME_Status_Print_Job_Instructions, Boolean.valueOf(Status_Print_Job_Instructions));
	}

	@Override
	public boolean isStatus_Print_Job_Instructions() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Status_Print_Job_Instructions);
	}

	@Override
	public void setTrayNumber (int TrayNumber)
	{
		set_ValueNoCheck (COLUMNNAME_TrayNumber, Integer.valueOf(TrayNumber));
	}

	@Override
	public int getTrayNumber() 
	{
		return get_ValueAsInt(COLUMNNAME_TrayNumber);
	}

	@Override
	public void setUpdatedby_Print_Job_Instructions (int Updatedby_Print_Job_Instructions)
	{
		set_ValueNoCheck (COLUMNNAME_Updatedby_Print_Job_Instructions, Integer.valueOf(Updatedby_Print_Job_Instructions));
	}

	@Override
	public int getUpdatedby_Print_Job_Instructions() 
	{
		return get_ValueAsInt(COLUMNNAME_Updatedby_Print_Job_Instructions);
	}

	@Override
	public void setUpdated_Print_Job_Instructions (java.sql.Timestamp Updated_Print_Job_Instructions)
	{
		set_ValueNoCheck (COLUMNNAME_Updated_Print_Job_Instructions, Updated_Print_Job_Instructions);
	}

	@Override
	public java.sql.Timestamp getUpdated_Print_Job_Instructions() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Updated_Print_Job_Instructions);
	}
}