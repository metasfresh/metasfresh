/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Order_MFGWarehouse_Report_PrintInfo_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Order_MFGWarehouse_Report_PrintInfo_v extends org.compiere.model.PO implements I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2086856061L;

    /** Standard Constructor */
    public X_C_Order_MFGWarehouse_Report_PrintInfo_v (Properties ctx, int C_Order_MFGWarehouse_Report_PrintInfo_v_ID, String trxName)
    {
      super (ctx, C_Order_MFGWarehouse_Report_PrintInfo_v_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Order_MFGWarehouse_Report_PrintInfo_v (Properties ctx, ResultSet rs, String trxName)
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
	public void setAD_User_Responsible_ID (int AD_User_Responsible_ID)
	{
		if (AD_User_Responsible_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_Responsible_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_Responsible_ID, Integer.valueOf(AD_User_Responsible_ID));
	}

	@Override
	public int getAD_User_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Responsible_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_Order_MFGWarehouse_Report_ID (int C_Order_MFGWarehouse_Report_ID)
	{
		if (C_Order_MFGWarehouse_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, Integer.valueOf(C_Order_MFGWarehouse_Report_ID));
	}

	@Override
	public int getC_Order_MFGWarehouse_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_MFGWarehouse_Report_ID);
	}

	@Override
	public de.metas.printing.model.I_C_Printing_Queue getC_Printing_Queue()
	{
		return get_ValueAsPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class);
	}

	@Override
	public void setC_Printing_Queue(de.metas.printing.model.I_C_Printing_Queue C_Printing_Queue)
	{
		set_ValueFromPO(COLUMNNAME_C_Printing_Queue_ID, de.metas.printing.model.I_C_Printing_Queue.class, C_Printing_Queue);
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
	public void setC_Queue_Element_ID (int C_Queue_Element_ID)
	{
		if (C_Queue_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Element_ID, Integer.valueOf(C_Queue_Element_ID));
	}

	@Override
	public int getC_Queue_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_Element_ID);
	}

	@Override
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, Integer.valueOf(C_Queue_WorkPackage_ID));
	}

	@Override
	public int getC_Queue_WorkPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_WorkPackage_ID);
	}

	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
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
	public org.compiere.model.I_S_Resource getS_Resource()
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	@Override
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	@Override
	public int getS_Resource_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_Resource_ID);
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
	public void setWP_IsError (boolean WP_IsError)
	{
		set_ValueNoCheck (COLUMNNAME_WP_IsError, Boolean.valueOf(WP_IsError));
	}

	@Override
	public boolean isWP_IsError() 
	{
		return get_ValueAsBoolean(COLUMNNAME_WP_IsError);
	}

	@Override
	public void setWP_IsProcessed (boolean WP_IsProcessed)
	{
		set_ValueNoCheck (COLUMNNAME_WP_IsProcessed, Boolean.valueOf(WP_IsProcessed));
	}

	@Override
	public boolean isWP_IsProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_WP_IsProcessed);
	}
}