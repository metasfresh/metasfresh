// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_OrgInfo
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_OrgInfo extends org.compiere.model.PO implements I_AD_OrgInfo, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1622689770L;

    /** Standard Constructor */
    public X_AD_OrgInfo (final Properties ctx, final int AD_OrgInfo_ID, @Nullable final String trxName)
    {
      super (ctx, AD_OrgInfo_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_OrgInfo (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_OrgInfo_ID (final int AD_OrgInfo_ID)
	{
		if (AD_OrgInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_OrgInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_OrgInfo_ID, AD_OrgInfo_ID);
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
	public void setAD_OrgType(final org.compiere.model.I_AD_OrgType AD_OrgType)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgType_ID, org.compiere.model.I_AD_OrgType.class, AD_OrgType);
	}

	@Override
	public void setAD_OrgType_ID (final int AD_OrgType_ID)
	{
		if (AD_OrgType_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgType_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgType_ID, AD_OrgType_ID);
	}

	@Override
	public int getAD_OrgType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_OrgType_ID);
	}

	@Override
	public void setAD_WF_Responsible_ID (final int AD_WF_Responsible_ID)
	{
		if (AD_WF_Responsible_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Responsible_ID, AD_WF_Responsible_ID);
	}

	@Override
	public int getAD_WF_Responsible_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_Responsible_ID);
	}

	@Override
	public org.compiere.model.I_AD_UserGroup getC_BPartner_CreatedFromAnotherOrg_Notify_UserGroup()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID, org.compiere.model.I_AD_UserGroup.class);
	}

	@Override
	public void setC_BPartner_CreatedFromAnotherOrg_Notify_UserGroup(final org.compiere.model.I_AD_UserGroup C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID, org.compiere.model.I_AD_UserGroup.class, C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup);
	}

	@Override
	public void setC_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID (final int C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID)
	{
		if (C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID, C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID);
	}

	@Override
	public int getC_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_CreatedFromAnotherOrg_Notify_UserGroup_ID);
	}

	@Override
	public org.compiere.model.I_AD_UserGroup getC_BP_SupplierApproval_Expiration_Notify_UserGroup()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID, org.compiere.model.I_AD_UserGroup.class);
	}

	@Override
	public void setC_BP_SupplierApproval_Expiration_Notify_UserGroup(final org.compiere.model.I_AD_UserGroup C_BP_SupplierApproval_Expiration_Notify_UserGroup)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID, org.compiere.model.I_AD_UserGroup.class, C_BP_SupplierApproval_Expiration_Notify_UserGroup);
	}

	@Override
	public void setC_BP_SupplierApproval_Expiration_Notify_UserGroup_ID (final int C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID)
	{
		if (C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID < 1) 
			set_Value (COLUMNNAME_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID, C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID);
	}

	@Override
	public int getC_BP_SupplierApproval_Expiration_Notify_UserGroup_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID);
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(final org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	@Override
	public void setC_Calendar_ID (final int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, C_Calendar_ID);
	}

	@Override
	public int getC_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Calendar_ID);
	}

	@Override
	public void setDropShip_Warehouse_ID (final int DropShip_Warehouse_ID)
	{
		if (DropShip_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Warehouse_ID, DropShip_Warehouse_ID);
	}

	@Override
	public int getDropShip_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Warehouse_ID);
	}

	@Override
	public void setIsAutoInvoiceFlatrateTerm (final boolean IsAutoInvoiceFlatrateTerm)
	{
		set_Value (COLUMNNAME_IsAutoInvoiceFlatrateTerm, IsAutoInvoiceFlatrateTerm);
	}

	@Override
	public boolean isAutoInvoiceFlatrateTerm() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoInvoiceFlatrateTerm);
	}

	@Override
	public org.compiere.model.I_AD_Image getLogo()
	{
		return get_ValueAsPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setLogo(final org.compiere.model.I_AD_Image Logo)
	{
		set_ValueFromPO(COLUMNNAME_Logo_ID, org.compiere.model.I_AD_Image.class, Logo);
	}

	@Override
	public void setLogo_ID (final int Logo_ID)
	{
		if (Logo_ID < 1) 
			set_Value (COLUMNNAME_Logo_ID, null);
		else 
			set_Value (COLUMNNAME_Logo_ID, Logo_ID);
	}

	@Override
	public int getLogo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Logo_ID);
	}

	@Override
	public void setM_PricingSystem_ID (final int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, M_PricingSystem_ID);
	}

	@Override
	public int getM_PricingSystem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PricingSystem_ID);
	}

	@Override
	public void setM_Warehouse_ID (final int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, M_Warehouse_ID);
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setM_WarehousePO_ID (final int M_WarehousePO_ID)
	{
		if (M_WarehousePO_ID < 1) 
			set_Value (COLUMNNAME_M_WarehousePO_ID, null);
		else 
			set_Value (COLUMNNAME_M_WarehousePO_ID, M_WarehousePO_ID);
	}

	@Override
	public int getM_WarehousePO_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_WarehousePO_ID);
	}

	@Override
	public void setOrg_BPartner_ID (final int Org_BPartner_ID)
	{
		if (Org_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Org_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Org_BPartner_ID, Org_BPartner_ID);
	}

	@Override
	public int getOrg_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Org_BPartner_ID);
	}

	@Override
	public void setOrgBP_Location_ID (final int OrgBP_Location_ID)
	{
		if (OrgBP_Location_ID < 1) 
			set_Value (COLUMNNAME_OrgBP_Location_ID, null);
		else 
			set_Value (COLUMNNAME_OrgBP_Location_ID, OrgBP_Location_ID);
	}

	@Override
	public int getOrgBP_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_OrgBP_Location_ID);
	}

	@Override
	public void setParent_Org_ID (final int Parent_Org_ID)
	{
		if (Parent_Org_ID < 1) 
			set_Value (COLUMNNAME_Parent_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_Org_ID, Parent_Org_ID);
	}

	@Override
	public int getParent_Org_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Parent_Org_ID);
	}

	@Override
	public void setReceiptFooterMsg (final @Nullable java.lang.String ReceiptFooterMsg)
	{
		set_Value (COLUMNNAME_ReceiptFooterMsg, ReceiptFooterMsg);
	}

	@Override
	public java.lang.String getReceiptFooterMsg() 
	{
		return get_ValueAsString(COLUMNNAME_ReceiptFooterMsg);
	}

	@Override
	public org.compiere.model.I_AD_Image getReportBottom_Logo()
	{
		return get_ValueAsPO(COLUMNNAME_ReportBottom_Logo_ID, org.compiere.model.I_AD_Image.class);
	}

	@Override
	public void setReportBottom_Logo(final org.compiere.model.I_AD_Image ReportBottom_Logo)
	{
		set_ValueFromPO(COLUMNNAME_ReportBottom_Logo_ID, org.compiere.model.I_AD_Image.class, ReportBottom_Logo);
	}

	@Override
	public void setReportBottom_Logo_ID (final int ReportBottom_Logo_ID)
	{
		if (ReportBottom_Logo_ID < 1) 
			set_Value (COLUMNNAME_ReportBottom_Logo_ID, null);
		else 
			set_Value (COLUMNNAME_ReportBottom_Logo_ID, ReportBottom_Logo_ID);
	}

	@Override
	public int getReportBottom_Logo_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ReportBottom_Logo_ID);
	}

	@Override
	public void setReportPrefix (final java.lang.String ReportPrefix)
	{
		set_Value (COLUMNNAME_ReportPrefix, ReportPrefix);
	}

	@Override
	public java.lang.String getReportPrefix() 
	{
		return get_ValueAsString(COLUMNNAME_ReportPrefix);
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
	public void setStoreCreditCardData (final java.lang.String StoreCreditCardData)
	{
		set_Value (COLUMNNAME_StoreCreditCardData, StoreCreditCardData);
	}

	@Override
	public java.lang.String getStoreCreditCardData() 
	{
		return get_ValueAsString(COLUMNNAME_StoreCreditCardData);
	}

	@Override
	public void setSupervisor_ID (final int Supervisor_ID)
	{
		if (Supervisor_ID < 1) 
			set_Value (COLUMNNAME_Supervisor_ID, null);
		else 
			set_Value (COLUMNNAME_Supervisor_ID, Supervisor_ID);
	}

	@Override
	public int getSupervisor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Supervisor_ID);
	}

	@Override
	public void setTimeZone (final @Nullable java.lang.String TimeZone)
	{
		set_Value (COLUMNNAME_TimeZone, TimeZone);
	}

	@Override
	public java.lang.String getTimeZone() 
	{
		return get_ValueAsString(COLUMNNAME_TimeZone);
	}

	@Override
	public void setTransferBank_ID (final int TransferBank_ID)
	{
		if (TransferBank_ID < 1) 
			set_Value (COLUMNNAME_TransferBank_ID, null);
		else 
			set_Value (COLUMNNAME_TransferBank_ID, TransferBank_ID);
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
	public void setTransferCashBook(final org.compiere.model.I_C_CashBook TransferCashBook)
	{
		set_ValueFromPO(COLUMNNAME_TransferCashBook_ID, org.compiere.model.I_C_CashBook.class, TransferCashBook);
	}

	@Override
	public void setTransferCashBook_ID (final int TransferCashBook_ID)
	{
		if (TransferCashBook_ID < 1) 
			set_Value (COLUMNNAME_TransferCashBook_ID, null);
		else 
			set_Value (COLUMNNAME_TransferCashBook_ID, TransferCashBook_ID);
	}

	@Override
	public int getTransferCashBook_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_TransferCashBook_ID);
	}
}