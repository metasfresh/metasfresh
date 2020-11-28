/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_OrgInfo
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_OrgInfo extends org.compiere.model.PO implements I_AD_OrgInfo, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -771889961L;

    /** Standard Constructor */
    public X_AD_OrgInfo (Properties ctx, int AD_OrgInfo_ID, String trxName)
    {
      super (ctx, AD_OrgInfo_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_OrgInfo (Properties ctx, ResultSet rs, String trxName)
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
	public void setAD_OrgInfo_ID (int AD_OrgInfo_ID)
	{
		if (AD_OrgInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgInfo_ID, Integer.valueOf(AD_OrgInfo_ID));
	}

	@Override
	public int getAD_OrgInfo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgInfo_ID);
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

	@Override
	public void setAD_OrgType_ID (int AD_OrgType_ID)
	{
		if (AD_OrgType_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgType_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgType_ID, Integer.valueOf(AD_OrgType_ID));
	}

	@Override
	public int getAD_OrgType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgType_ID);
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

	@Override
	public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, Integer.valueOf(AD_WF_Responsible_ID));
	}

	@Override
	public int getAD_WF_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Responsible_ID);
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

	@Override
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	@Override
	public int getC_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Calendar_ID);
	}

	@Override
	public void setDropShip_Warehouse_ID (int DropShip_Warehouse_ID)
	{
		if (DropShip_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Warehouse_ID, Integer.valueOf(DropShip_Warehouse_ID));
	}

	@Override
	public int getDropShip_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Warehouse_ID);
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

	@Override
	public void setLogo_ID (int Logo_ID)
	{
		if (Logo_ID < 1) 
			set_Value (COLUMNNAME_Logo_ID, null);
		else 
			set_Value (COLUMNNAME_Logo_ID, Integer.valueOf(Logo_ID));
	}

	@Override
	public int getLogo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Logo_ID);
	}

	@Override
	public void setM_PricingSystem_ID (int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, Integer.valueOf(M_PricingSystem_ID));
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
	}

	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setM_WarehousePO_ID (int M_WarehousePO_ID)
	{
		if (M_WarehousePO_ID < 1) 
			set_Value (COLUMNNAME_M_WarehousePO_ID, null);
		else 
			set_Value (COLUMNNAME_M_WarehousePO_ID, Integer.valueOf(M_WarehousePO_ID));
	}

	@Override
	public int getM_WarehousePO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_WarehousePO_ID);
	}

	@Override
	public void setOrg_BPartner_ID (int Org_BPartner_ID)
	{
		if (Org_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Org_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Org_BPartner_ID, Integer.valueOf(Org_BPartner_ID));
	}

	@Override
	public int getOrg_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Org_BPartner_ID);
	}

	@Override
	public void setOrgBP_Location_ID (int OrgBP_Location_ID)
	{
		if (OrgBP_Location_ID < 1) 
			set_Value (COLUMNNAME_OrgBP_Location_ID, null);
		else 
			set_Value (COLUMNNAME_OrgBP_Location_ID, Integer.valueOf(OrgBP_Location_ID));
	}

	@Override
	public int getOrgBP_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_OrgBP_Location_ID);
	}

	@Override
	public void setParent_Org_ID (int Parent_Org_ID)
	{
		if (Parent_Org_ID < 1) 
			set_Value (COLUMNNAME_Parent_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_Org_ID, Integer.valueOf(Parent_Org_ID));
	}

	@Override
	public int getParent_Org_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Parent_Org_ID);
	}

	@Override
	public void setReceiptFooterMsg (java.lang.String ReceiptFooterMsg)
	{
		set_Value (COLUMNNAME_ReceiptFooterMsg, ReceiptFooterMsg);
	}

	@Override
	public java.lang.String getReceiptFooterMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReceiptFooterMsg);
	}

	@Override
	public void setReportPrefix (java.lang.String ReportPrefix)
	{
		set_Value (COLUMNNAME_ReportPrefix, ReportPrefix);
	}

	@Override
	public java.lang.String getReportPrefix() 
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
	@Override
	public void setStoreCreditCardData (java.lang.String StoreCreditCardData)
	{

		set_Value (COLUMNNAME_StoreCreditCardData, StoreCreditCardData);
	}

	@Override
	public java.lang.String getStoreCreditCardData() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StoreCreditCardData);
	}

	@Override
	public void setSupervisor_ID (int Supervisor_ID)
	{
		if (Supervisor_ID < 1) 
			set_Value (COLUMNNAME_Supervisor_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor_ID, Integer.valueOf(Supervisor_ID));
	}

	@Override
	public int getSupervisor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Supervisor_ID);
	}

	@Override
	public void setTimeZone (java.lang.String TimeZone)
	{
		set_Value (COLUMNNAME_TimeZone, TimeZone);
	}

	@Override
	public java.lang.String getTimeZone() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TimeZone);
	}

	@Override
	public void setTransferBank_ID (int TransferBank_ID)
	{
		if (TransferBank_ID < 1) 
			set_Value (COLUMNNAME_TransferBank_ID, null);
		else 
			set_Value (COLUMNNAME_TransferBank_ID, Integer.valueOf(TransferBank_ID));
	}

	@Override
	public int getTransferBank_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_TransferBank_ID);
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

	@Override
	public void setTransferCashBook_ID (int TransferCashBook_ID)
	{
		if (TransferCashBook_ID < 1) 
			set_Value (COLUMNNAME_TransferCashBook_ID, null);
		else 
			set_Value (COLUMNNAME_TransferCashBook_ID, Integer.valueOf(TransferCashBook_ID));
	}

	@Override
	public int getTransferCashBook_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_TransferCashBook_ID);
	}
}