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
	private static final long serialVersionUID = 2092727521L;

    /** Standard Constructor */
    public X_AD_OrgInfo (Properties ctx, int AD_OrgInfo_ID, String trxName)
    {
      super (ctx, AD_OrgInfo_ID, trxName);
      /** if (AD_OrgInfo_ID == 0)
        {
			setDUNS (null);
			setReceiptFooterMsg (null); // 1
			setTaxID (null);
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

	@Override
	public org.compiere.model.I_AD_OrgType getAD_OrgType() throws RuntimeException
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
	public org.compiere.model.I_C_Calendar getC_Calendar() throws RuntimeException
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
	public org.compiere.model.I_C_Location getC_Location() throws RuntimeException
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

	@Override
	public org.compiere.model.I_M_Warehouse getDropShip_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setDropShip_Warehouse(org.compiere.model.I_M_Warehouse DropShip_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, DropShip_Warehouse);
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

	/** Set D-U-N-S.
		@param DUNS 
		Dun & Bradstreet Number
	  */
	@Override
	public void setDUNS (java.lang.String DUNS)
	{
		set_Value (COLUMNNAME_DUNS, DUNS);
	}

	/** Get D-U-N-S.
		@return Dun & Bradstreet Number
	  */
	@Override
	public java.lang.String getDUNS () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DUNS);
	}

	@Override
	public org.compiere.model.I_AD_Image getLogo() throws RuntimeException
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

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
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

	@Override
	public org.compiere.model.I_M_Warehouse getM_WarehousePO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_WarehousePO_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_WarehousePO(org.compiere.model.I_M_Warehouse M_WarehousePO)
	{
		set_ValueFromPO(COLUMNNAME_M_WarehousePO_ID, org.compiere.model.I_M_Warehouse.class, M_WarehousePO);
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

	@Override
	public org.compiere.model.I_AD_Org getParent_Org() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Parent_Org_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setParent_Org(org.compiere.model.I_AD_Org Parent_Org)
	{
		set_ValueFromPO(COLUMNNAME_Parent_Org_ID, org.compiere.model.I_AD_Org.class, Parent_Org);
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

	@Override
	public org.compiere.model.I_AD_User getSupervisor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Supervisor_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSupervisor(org.compiere.model.I_AD_User Supervisor)
	{
		set_ValueFromPO(COLUMNNAME_Supervisor_ID, org.compiere.model.I_AD_User.class, Supervisor);
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

	/** Set Steuer-ID.
		@param TaxID 
		Tax Identification
	  */
	@Override
	public void setTaxID (java.lang.String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Steuer-ID.
		@return Tax Identification
	  */
	@Override
	public java.lang.String getTaxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TaxID);
	}

	@Override
	public org.compiere.model.I_C_Bank getTransferBank() throws RuntimeException
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
	public org.compiere.model.I_C_CashBook getTransferCashBook() throws RuntimeException
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