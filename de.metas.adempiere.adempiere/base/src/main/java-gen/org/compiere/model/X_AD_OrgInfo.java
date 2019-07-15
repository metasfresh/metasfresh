/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_OrgInfo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_OrgInfo extends org.compiere.model.PO implements I_AD_OrgInfo, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1747105117L;

    /** Standard Constructor */
    public X_AD_OrgInfo (Properties ctx, int AD_OrgInfo_ID, String trxName)
    {
      super (ctx, AD_OrgInfo_ID, trxName);
      /** if (AD_OrgInfo_ID == 0)
        {
			setAD_OrgInfo_ID (0);
			setReportPrefix (null); // file:////opt/metasfresh/reports
			setStoreCreditCardData (null); // LAST4
        } */
    }

    /** Load Constructor */
    public X_AD_OrgInfo (Properties ctx, ResultSet rs, String trxName)
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

	/** Set AD_OrgInfo_ID.
		@param AD_OrgInfo_ID AD_OrgInfo_ID	  */
	@Override
	public void setAD_OrgInfo_ID (int AD_OrgInfo_ID)
	{
		if (AD_OrgInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgInfo_ID, Integer.valueOf(AD_OrgInfo_ID));
	}

	/** Get AD_OrgInfo_ID.
		@return AD_OrgInfo_ID	  */
	@Override
	public int getAD_OrgInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_OrgType getAD_OrgType()
	{
		return get_ValueAsPO(COLUMNNAME_AD_OrgType_ID, org.compiere.model.I_AD_OrgType.class);
	}

	@Override
	public void setAD_OrgType(org.compiere.model.I_AD_OrgType AD_OrgType)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgType_ID, org.compiere.model.I_AD_OrgType.class, AD_OrgType);
	}

	/** Set Organization Type.
		@param AD_OrgType_ID 
		Organization Type
	  */
	@Override
	public void setAD_OrgType_ID (int AD_OrgType_ID)
	{
		if (AD_OrgType_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgType_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgType_ID, Integer.valueOf(AD_OrgType_ID));
	}

	/** Get Organization Type.
		@return Organization Type
	  */
	@Override
	public int getAD_OrgType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_WF_Responsible getAD_WF_Responsible()
	{
		return get_ValueAsPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class);
	}

	@Override
	public void setAD_WF_Responsible(org.compiere.model.I_AD_WF_Responsible AD_WF_Responsible)
	{
		set_ValueFromPO(COLUMNNAME_AD_WF_Responsible_ID, org.compiere.model.I_AD_WF_Responsible.class, AD_WF_Responsible);
	}

	/** Set Workflow - Verantwortlicher.
		@param AD_WF_Responsible_ID 
		Verantwortlicher für die Ausführung des Workflow
	  */
	@Override
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
	}

	/** Get Workflow - Verantwortlicher.
		@return Verantwortlicher für die Ausführung des Workflow
	  */
	@Override
	public int getAD_WF_Responsible_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Responsible_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	/** Set Kalender.
		@param C_Calendar_ID 
		Accounting Calendar Name
	  */
	@Override
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	/** Get Kalender.
		@return Accounting Calendar Name
	  */
	@Override
	public int getC_Calendar_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Calendar_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Location getC_Location()
	{
		return get_ValueAsPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setC_Location(org.compiere.model.I_C_Location C_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_Location_ID, org.compiere.model.I_C_Location.class, C_Location);
	}

	/** Set Anschrift.
		@param C_Location_ID 
		Adresse oder Anschrift
	  */
	@Override
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Anschrift.
		@return Adresse oder Anschrift
	  */
	@Override
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Drop Ship Warehouse.
		@param DropShip_Warehouse_ID 
		The (logical) warehouse to use for recording drop ship receipts and shipments.
	  */
	@Override
	public void setDropShip_Warehouse_ID (int DropShip_Warehouse_ID)
	{
		if (DropShip_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Warehouse_ID, Integer.valueOf(DropShip_Warehouse_ID));
	}

	/** Get Drop Ship Warehouse.
		@return The (logical) warehouse to use for recording drop ship receipts and shipments.
	  */
	@Override
	public int getDropShip_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Image getLogo()
	{
		return get_ValueAsPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setLogo(org.compiere.model.I_AD_Image Logo)
	{
		set_ValueFromPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class, Logo);
	}

	/** Set Logo.
		@param Logo_ID Logo	  */
	@Override
	public void setLogo_ID (int Logo_ID)
	{
		if (Logo_ID < 1) 
			set_Value (COLUMNNAME_Logo_ID, null);
		else 
			set_Value (COLUMNNAME_Logo_ID, Integer.valueOf(Logo_ID));
	}

	/** Get Logo.
		@return Logo	  */
	@Override
	public int getLogo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Logo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Preissystem.
		@param M_PricingSystem_ID 
		Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public void setM_PricingSystem_ID (int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, Integer.valueOf(M_PricingSystem_ID));
	}

	/** Get Preissystem.
		@return Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public int getM_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Storage Warehouse and Service Point
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Liefer-Lager.
		@param M_WarehousePO_ID 
		Lager, an das der Lieferant eine Bestellung liefern soll.
	  */
	@Override
	public void setM_WarehousePO_ID (int M_WarehousePO_ID)
	{
		if (M_WarehousePO_ID < 1) 
			set_Value (COLUMNNAME_M_WarehousePO_ID, null);
		else 
			set_Value (COLUMNNAME_M_WarehousePO_ID, Integer.valueOf(M_WarehousePO_ID));
	}

	/** Get Liefer-Lager.
		@return Lager, an das der Lieferant eine Bestellung liefern soll.
	  */
	@Override
	public int getM_WarehousePO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_WarehousePO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Org BPartner.
		@param Org_BPartner_ID Org BPartner	  */
	@Override
	public void setOrg_BPartner_ID (int Org_BPartner_ID)
	{
		if (Org_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Org_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Org_BPartner_ID, Integer.valueOf(Org_BPartner_ID));
	}

	/** Get Org BPartner.
		@return Org BPartner	  */
	@Override
	public int getOrg_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Org_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set OrgBP_Location_ID.
		@param OrgBP_Location_ID 
		Default BP Location linked to the org.
	  */
	@Override
	public void setOrgBP_Location_ID (int OrgBP_Location_ID)
	{
		if (OrgBP_Location_ID < 1) 
			set_Value (COLUMNNAME_OrgBP_Location_ID, null);
		else 
			set_Value (COLUMNNAME_OrgBP_Location_ID, Integer.valueOf(OrgBP_Location_ID));
	}

	/** Get OrgBP_Location_ID.
		@return Default BP Location linked to the org.
	  */
	@Override
	public int getOrgBP_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_OrgBP_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Übergeordnete Organisation.
		@param Parent_Org_ID 
		Parent (superior) Organization 
	  */
	@Override
	public void setParent_Org_ID (int Parent_Org_ID)
	{
		if (Parent_Org_ID < 1) 
			set_Value (COLUMNNAME_Parent_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_Org_ID, Integer.valueOf(Parent_Org_ID));
	}

	/** Get Übergeordnete Organisation.
		@return Parent (superior) Organization 
	  */
	@Override
	public int getParent_Org_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Receipt Footer Msg.
		@param ReceiptFooterMsg 
		This message will be displayed at the bottom of a receipt when doing a sales or purchase
	  */
	@Override
	public void setReceiptFooterMsg (java.lang.String ReceiptFooterMsg)
	{
		set_Value (COLUMNNAME_ReceiptFooterMsg, ReceiptFooterMsg);
	}

	/** Get Receipt Footer Msg.
		@return This message will be displayed at the bottom of a receipt when doing a sales or purchase
	  */
	@Override
	public java.lang.String getReceiptFooterMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReceiptFooterMsg);
	}

	/** Set Berichts-Präfix.
		@param ReportPrefix 
		Präfix, der (Jasper-)Berichten und Belegen vorangestellt wird, wenn diese Abgerufen werden
	  */
	@Override
	public void setReportPrefix (java.lang.String ReportPrefix)
	{
		set_Value (COLUMNNAME_ReportPrefix, ReportPrefix);
	}

	/** Get Berichts-Präfix.
		@return Präfix, der (Jasper-)Berichten und Belegen vorangestellt wird, wenn diese Abgerufen werden
	  */
	@Override
	public java.lang.String getReportPrefix () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReportPrefix);
	}

	/** 
	 * StoreCreditCardData AD_Reference_ID=540197
	 * Reference name: StoreCreditCardData
	 */
	public static final int STORECREDITCARDDATA_AD_Reference_ID=540197;
	/** Speichern = STORE */
	public static final String STORECREDITCARDDATA_Speichern = "STORE";
	/** Nicht Speichern = DONT */
	public static final String STORECREDITCARDDATA_NichtSpeichern = "DONT";
	/** letzte 4 Stellen = LAST4 */
	public static final String STORECREDITCARDDATA_Letzte4Stellen = "LAST4";
	/** Set Speichung Kreditkartendaten.
		@param StoreCreditCardData Speichung Kreditkartendaten	  */
	@Override
	public void setStoreCreditCardData (java.lang.String StoreCreditCardData)
	{

		set_Value (COLUMNNAME_StoreCreditCardData, StoreCreditCardData);
	}

	/** Get Speichung Kreditkartendaten.
		@return Speichung Kreditkartendaten	  */
	@Override
	public java.lang.String getStoreCreditCardData () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StoreCreditCardData);
	}

	/** Set Vorgesetzter.
		@param Supervisor_ID 
		Supervisor for this user/organization - used for escalation and approval
	  */
	@Override
	public void setSupervisor_ID (int Supervisor_ID)
	{
		if (Supervisor_ID < 1) 
			set_Value (COLUMNNAME_Supervisor_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor_ID, Integer.valueOf(Supervisor_ID));
	}

	/** Get Vorgesetzter.
		@return Supervisor for this user/organization - used for escalation and approval
	  */
	@Override
	public int getSupervisor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Supervisor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Time Zone.
		@param TimeZone Time Zone	  */
	@Override
	public void setTimeZone (java.lang.String TimeZone)
	{
		set_Value (COLUMNNAME_TimeZone, TimeZone);
	}

	/** Get Time Zone.
		@return Time Zone	  */
	@Override
	public java.lang.String getTimeZone () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TimeZone);
	}

	@Override
	public org.compiere.model.I_C_Bank getTransferBank()
	{
		return get_ValueAsPO(COLUMNNAME_TransferBank_ID, org.compiere.model.I_C_Bank.class);
	}

	@Override
	public void setTransferBank(org.compiere.model.I_C_Bank TransferBank)
	{
		set_ValueFromPO(COLUMNNAME_TransferBank_ID, org.compiere.model.I_C_Bank.class, TransferBank);
	}

	/** Set Bank for transfers.
		@param TransferBank_ID 
		Bank account depending on currency will be used from this bank for doing transfers
	  */
	@Override
	public void setTransferBank_ID (int TransferBank_ID)
	{
		if (TransferBank_ID < 1) 
			set_Value (COLUMNNAME_TransferBank_ID, null);
		else 
			set_Value (COLUMNNAME_TransferBank_ID, Integer.valueOf(TransferBank_ID));
	}

	/** Get Bank for transfers.
		@return Bank account depending on currency will be used from this bank for doing transfers
	  */
	@Override
	public int getTransferBank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TransferBank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_CashBook getTransferCashBook()
	{
		return get_ValueAsPO(COLUMNNAME_TransferCashBook_ID, org.compiere.model.I_C_CashBook.class);
	}

	@Override
	public void setTransferCashBook(org.compiere.model.I_C_CashBook TransferCashBook)
	{
		set_ValueFromPO(COLUMNNAME_TransferCashBook_ID, org.compiere.model.I_C_CashBook.class, TransferCashBook);
	}

	/** Set CashBook for transfers.
		@param TransferCashBook_ID CashBook for transfers	  */
	@Override
	public void setTransferCashBook_ID (int TransferCashBook_ID)
	{
		if (TransferCashBook_ID < 1) 
			set_Value (COLUMNNAME_TransferCashBook_ID, null);
		else 
			set_Value (COLUMNNAME_TransferCashBook_ID, Integer.valueOf(TransferCashBook_ID));
	}

	/** Get CashBook for transfers.
		@return CashBook for transfers	  */
	@Override
	public int getTransferCashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_TransferCashBook_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}