/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Order_MFGWarehouse_Report_PrintInfo_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Order_MFGWarehouse_Report_PrintInfo_v extends org.compiere.model.PO implements I_C_Order_MFGWarehouse_Report_PrintInfo_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1354576674L;

    /** Standard Constructor */
    public X_C_Order_MFGWarehouse_Report_PrintInfo_v (Properties ctx, int C_Order_MFGWarehouse_Report_PrintInfo_v_ID, String trxName)
    {
      super (ctx, C_Order_MFGWarehouse_Report_PrintInfo_v_ID, trxName);
      /** if (C_Order_MFGWarehouse_Report_PrintInfo_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_Order_MFGWarehouse_Report_PrintInfo_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
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

	/** Set Archiv.
		@param AD_Archive_ID 
		Archiv für Belege und Berichte
	  */
	@Override
	public void setAD_Archive_ID (int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, Integer.valueOf(AD_Archive_ID));
	}

	/** Get Archiv.
		@return Archiv für Belege und Berichte
	  */
	@Override
	public int getAD_Archive_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Archive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Hardware-Drucker.
		@param AD_PrinterHW_ID Hardware-Drucker	  */
	@Override
	public void setAD_PrinterHW_ID (int AD_PrinterHW_ID)
	{
		if (AD_PrinterHW_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_ID, Integer.valueOf(AD_PrinterHW_ID));
	}

	/** Get Hardware-Drucker.
		@return Hardware-Drucker	  */
	@Override
	public int getAD_PrinterHW_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterHW_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Hardware-Schacht.
		@param AD_PrinterHW_MediaTray_ID Hardware-Schacht	  */
	@Override
	public void setAD_PrinterHW_MediaTray_ID (int AD_PrinterHW_MediaTray_ID)
	{
		if (AD_PrinterHW_MediaTray_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_MediaTray_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrinterHW_MediaTray_ID, Integer.valueOf(AD_PrinterHW_MediaTray_ID));
	}

	/** Get Hardware-Schacht.
		@return Hardware-Schacht	  */
	@Override
	public int getAD_PrinterHW_MediaTray_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrinterHW_MediaTray_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Session Printpackage ID.
		@param AD_Session_Printpackage_ID Session Printpackage ID	  */
	@Override
	public void setAD_Session_Printpackage_ID (int AD_Session_Printpackage_ID)
	{
		if (AD_Session_Printpackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Session_Printpackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Session_Printpackage_ID, Integer.valueOf(AD_Session_Printpackage_ID));
	}

	/** Get Session Printpackage ID.
		@return Session Printpackage ID	  */
	@Override
	public int getAD_Session_Printpackage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Session_Printpackage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verantwortlicher Benutzer.
		@param AD_User_Responsible_ID Verantwortlicher Benutzer	  */
	@Override
	public void setAD_User_Responsible_ID (int AD_User_Responsible_ID)
	{
		if (AD_User_Responsible_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_Responsible_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_Responsible_ID, Integer.valueOf(AD_User_Responsible_ID));
	}

	/** Get Verantwortlicher Benutzer.
		@return Verantwortlicher Benutzer	  */
	@Override
	public int getAD_User_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Order / MFG Warehouse report.
		@param C_Order_MFGWarehouse_Report_ID Order / MFG Warehouse report	  */
	@Override
	public void setC_Order_MFGWarehouse_Report_ID (int C_Order_MFGWarehouse_Report_ID)
	{
		if (C_Order_MFGWarehouse_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_MFGWarehouse_Report_ID, Integer.valueOf(C_Order_MFGWarehouse_Report_ID));
	}

	/** Get Order / MFG Warehouse report.
		@return Order / MFG Warehouse report	  */
	@Override
	public int getC_Order_MFGWarehouse_Report_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_MFGWarehouse_Report_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Druck-Warteschlangendatensatz.
		@param C_Printing_Queue_ID Druck-Warteschlangendatensatz	  */
	@Override
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID)
	{
		if (C_Printing_Queue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, Integer.valueOf(C_Printing_Queue_ID));
	}

	/** Get Druck-Warteschlangendatensatz.
		@return Druck-Warteschlangendatensatz	  */
	@Override
	public int getC_Printing_Queue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Printing_Queue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Druck-Job Anweisung.
		@param C_Print_Job_Instructions_ID Druck-Job Anweisung	  */
	@Override
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID)
	{
		if (C_Print_Job_Instructions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Job_Instructions_ID, Integer.valueOf(C_Print_Job_Instructions_ID));
	}

	/** Get Druck-Job Anweisung.
		@return Druck-Job Anweisung	  */
	@Override
	public int getC_Print_Job_Instructions_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_Job_Instructions_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Druckpaket.
		@param C_Print_Package_ID Druckpaket	  */
	@Override
	public void setC_Print_Package_ID (int C_Print_Package_ID)
	{
		if (C_Print_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_Package_ID, Integer.valueOf(C_Print_Package_ID));
	}

	/** Get Druckpaket.
		@return Druckpaket	  */
	@Override
	public int getC_Print_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Druckpaket-Info.
		@param C_Print_PackageInfo_ID 
		Contains details for the print package, like printer, tray, pages from/to and print service name.
	  */
	@Override
	public void setC_Print_PackageInfo_ID (int C_Print_PackageInfo_ID)
	{
		if (C_Print_PackageInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Print_PackageInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Print_PackageInfo_ID, Integer.valueOf(C_Print_PackageInfo_ID));
	}

	/** Get Druckpaket-Info.
		@return Contains details for the print package, like printer, tray, pages from/to and print service name.
	  */
	@Override
	public int getC_Print_PackageInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Print_PackageInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Element Queue.
		@param C_Queue_Element_ID Element Queue	  */
	@Override
	public void setC_Queue_Element_ID (int C_Queue_Element_ID)
	{
		if (C_Queue_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Element_ID, Integer.valueOf(C_Queue_Element_ID));
	}

	/** Get Element Queue.
		@return Element Queue	  */
	@Override
	public int getC_Queue_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WorkPackage Queue.
		@param C_Queue_WorkPackage_ID WorkPackage Queue	  */
	@Override
	public void setC_Queue_WorkPackage_ID (int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_WorkPackage_ID, Integer.valueOf(C_Queue_WorkPackage_ID));
	}

	/** Get WorkPackage Queue.
		@return WorkPackage Queue	  */
	@Override
	public int getC_Queue_WorkPackage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_WorkPackage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Print service name.
		@param PrintServiceName Print service name	  */
	@Override
	public void setPrintServiceName (java.lang.String PrintServiceName)
	{
		set_ValueNoCheck (COLUMNNAME_PrintServiceName, PrintServiceName);
	}

	/** Get Print service name.
		@return Print service name	  */
	@Override
	public java.lang.String getPrintServiceName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintServiceName);
	}

	/** Set Print Service Tray.
		@param PrintServiceTray Print Service Tray	  */
	@Override
	public void setPrintServiceTray (java.lang.String PrintServiceTray)
	{
		set_ValueNoCheck (COLUMNNAME_PrintServiceTray, PrintServiceTray);
	}

	/** Get Print Service Tray.
		@return Print Service Tray	  */
	@Override
	public java.lang.String getPrintServiceTray () 
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

	/** Set Ressource.
		@param S_Resource_ID 
		Ressource
	  */
	@Override
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Ressource.
		@return Ressource
	  */
	@Override
	public int getS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Status Print Job Instructions.
		@param Status_Print_Job_Instructions Status Print Job Instructions	  */
	@Override
	public void setStatus_Print_Job_Instructions (boolean Status_Print_Job_Instructions)
	{
		set_ValueNoCheck (COLUMNNAME_Status_Print_Job_Instructions, Boolean.valueOf(Status_Print_Job_Instructions));
	}

	/** Get Status Print Job Instructions.
		@return Status Print Job Instructions	  */
	@Override
	public boolean isStatus_Print_Job_Instructions () 
	{
		Object oo = get_Value(COLUMNNAME_Status_Print_Job_Instructions);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Schachtnummer.
		@param TrayNumber Schachtnummer	  */
	@Override
	public void setTrayNumber (int TrayNumber)
	{
		set_ValueNoCheck (COLUMNNAME_TrayNumber, Integer.valueOf(TrayNumber));
	}

	/** Get Schachtnummer.
		@return Schachtnummer	  */
	@Override
	public int getTrayNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TrayNumber);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WP IsError.
		@param WP_IsError WP IsError	  */
	@Override
	public void setWP_IsError (boolean WP_IsError)
	{
		set_ValueNoCheck (COLUMNNAME_WP_IsError, Boolean.valueOf(WP_IsError));
	}

	/** Get WP IsError.
		@return WP IsError	  */
	@Override
	public boolean isWP_IsError () 
	{
		Object oo = get_Value(COLUMNNAME_WP_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set WP IsProcessed.
		@param WP_IsProcessed WP IsProcessed	  */
	@Override
	public void setWP_IsProcessed (boolean WP_IsProcessed)
	{
		set_ValueNoCheck (COLUMNNAME_WP_IsProcessed, Boolean.valueOf(WP_IsProcessed));
	}

	/** Get WP IsProcessed.
		@return WP IsProcessed	  */
	@Override
	public boolean isWP_IsProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_WP_IsProcessed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}