// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Role
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Role extends org.compiere.model.PO implements I_AD_Role, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -335821006L;

    /** Standard Constructor */
    public X_AD_Role (final Properties ctx, final int AD_Role_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Role_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Role (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Form getAD_Form()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class);
	}

	@Override
	public void setAD_Form(final org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	@Override
	public void setAD_Form_ID (final int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, AD_Form_ID);
	}

	@Override
	public int getAD_Form_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Form_ID);
	}

	@Override
	public void setAD_Role_ID (final int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_Menu()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Menu_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Menu(final org.compiere.model.I_AD_Tree AD_Tree_Menu)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Menu_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Menu);
	}

	@Override
	public void setAD_Tree_Menu_ID (final int AD_Tree_Menu_ID)
	{
		if (AD_Tree_Menu_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Menu_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Menu_ID, AD_Tree_Menu_ID);
	}

	@Override
	public int getAD_Tree_Menu_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Tree_Menu_ID);
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_Org()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Org_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Org(final org.compiere.model.I_AD_Tree AD_Tree_Org)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Org_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Org);
	}

	@Override
	public void setAD_Tree_Org_ID (final int AD_Tree_Org_ID)
	{
		if (AD_Tree_Org_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Org_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Org_ID, AD_Tree_Org_ID);
	}

	@Override
	public int getAD_Tree_Org_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Tree_Org_ID);
	}

	@Override
	public void setAllow_Info_Account (final boolean Allow_Info_Account)
	{
		set_Value (COLUMNNAME_Allow_Info_Account, Allow_Info_Account);
	}

	@Override
	public boolean isAllow_Info_Account() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_Account);
	}

	@Override
	public void setAllow_Info_Asset (final boolean Allow_Info_Asset)
	{
		set_Value (COLUMNNAME_Allow_Info_Asset, Allow_Info_Asset);
	}

	@Override
	public boolean isAllow_Info_Asset() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_Asset);
	}

	@Override
	public void setAllow_Info_BPartner (final boolean Allow_Info_BPartner)
	{
		set_Value (COLUMNNAME_Allow_Info_BPartner, Allow_Info_BPartner);
	}

	@Override
	public boolean isAllow_Info_BPartner() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_BPartner);
	}

	@Override
	public void setAllow_Info_CashJournal (final boolean Allow_Info_CashJournal)
	{
		set_Value (COLUMNNAME_Allow_Info_CashJournal, Allow_Info_CashJournal);
	}

	@Override
	public boolean isAllow_Info_CashJournal() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_CashJournal);
	}

	@Override
	public void setAllow_Info_CRP (final boolean Allow_Info_CRP)
	{
		set_Value (COLUMNNAME_Allow_Info_CRP, Allow_Info_CRP);
	}

	@Override
	public boolean isAllow_Info_CRP() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_CRP);
	}

	@Override
	public void setAllow_Info_InOut (final boolean Allow_Info_InOut)
	{
		set_Value (COLUMNNAME_Allow_Info_InOut, Allow_Info_InOut);
	}

	@Override
	public boolean isAllow_Info_InOut() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_InOut);
	}

	@Override
	public void setAllow_Info_Invoice (final boolean Allow_Info_Invoice)
	{
		set_Value (COLUMNNAME_Allow_Info_Invoice, Allow_Info_Invoice);
	}

	@Override
	public boolean isAllow_Info_Invoice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_Invoice);
	}

	@Override
	public void setAllow_Info_MRP (final boolean Allow_Info_MRP)
	{
		set_Value (COLUMNNAME_Allow_Info_MRP, Allow_Info_MRP);
	}

	@Override
	public boolean isAllow_Info_MRP() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_MRP);
	}

	@Override
	public void setAllow_Info_Order (final boolean Allow_Info_Order)
	{
		set_Value (COLUMNNAME_Allow_Info_Order, Allow_Info_Order);
	}

	@Override
	public boolean isAllow_Info_Order() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_Order);
	}

	@Override
	public void setAllow_Info_Payment (final boolean Allow_Info_Payment)
	{
		set_Value (COLUMNNAME_Allow_Info_Payment, Allow_Info_Payment);
	}

	@Override
	public boolean isAllow_Info_Payment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_Payment);
	}

	@Override
	public void setAllow_Info_Product (final boolean Allow_Info_Product)
	{
		set_Value (COLUMNNAME_Allow_Info_Product, Allow_Info_Product);
	}

	@Override
	public boolean isAllow_Info_Product() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_Product);
	}

	@Override
	public void setAllow_Info_Resource (final boolean Allow_Info_Resource)
	{
		set_Value (COLUMNNAME_Allow_Info_Resource, Allow_Info_Resource);
	}

	@Override
	public boolean isAllow_Info_Resource() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_Resource);
	}

	@Override
	public void setAllow_Info_Schedule (final boolean Allow_Info_Schedule)
	{
		set_Value (COLUMNNAME_Allow_Info_Schedule, Allow_Info_Schedule);
	}

	@Override
	public boolean isAllow_Info_Schedule() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Allow_Info_Schedule);
	}

	@Override
	public void setAmtApproval (final @Nullable BigDecimal AmtApproval)
	{
		set_Value (COLUMNNAME_AmtApproval, AmtApproval);
	}

	@Override
	public BigDecimal getAmtApproval() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmtApproval);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setConfirmQueryRecords (final int ConfirmQueryRecords)
	{
		set_Value (COLUMNNAME_ConfirmQueryRecords, ConfirmQueryRecords);
	}

	@Override
	public int getConfirmQueryRecords() 
	{
		return get_ValueAsInt(COLUMNNAME_ConfirmQueryRecords);
	}

	/** 
	 * ConnectionProfile AD_Reference_ID=364
	 * Reference name: AD_User ConnectionProfile
	 */
	public static final int CONNECTIONPROFILE_AD_Reference_ID=364;
	/** LAN = L */
	public static final String CONNECTIONPROFILE_LAN = "L";
	/** TerminalServer = T */
	public static final String CONNECTIONPROFILE_TerminalServer = "T";
	/** VPN = V */
	public static final String CONNECTIONPROFILE_VPN = "V";
	/** WAN = W */
	public static final String CONNECTIONPROFILE_WAN = "W";
	@Override
	public void setConnectionProfile (final @Nullable java.lang.String ConnectionProfile)
	{
		set_Value (COLUMNNAME_ConnectionProfile, ConnectionProfile);
	}

	@Override
	public java.lang.String getConnectionProfile() 
	{
		return get_ValueAsString(COLUMNNAME_ConnectionProfile);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setIsAccessAllOrgs (final boolean IsAccessAllOrgs)
	{
		set_Value (COLUMNNAME_IsAccessAllOrgs, IsAccessAllOrgs);
	}

	@Override
	public boolean isAccessAllOrgs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAccessAllOrgs);
	}

	@Override
	public void setIsAllowLoginDateOverride (final boolean IsAllowLoginDateOverride)
	{
		set_Value (COLUMNNAME_IsAllowLoginDateOverride, IsAllowLoginDateOverride);
	}

	@Override
	public boolean isAllowLoginDateOverride() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowLoginDateOverride);
	}

	@Override
	public void setIsAttachmentDeletionAllowed (final boolean IsAttachmentDeletionAllowed)
	{
		set_Value (COLUMNNAME_IsAttachmentDeletionAllowed, IsAttachmentDeletionAllowed);
	}

	@Override
	public boolean isAttachmentDeletionAllowed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAttachmentDeletionAllowed);
	}

	@Override
	public void setIsAutoRoleLogin (final boolean IsAutoRoleLogin)
	{
		set_Value (COLUMNNAME_IsAutoRoleLogin, IsAutoRoleLogin);
	}

	@Override
	public boolean isAutoRoleLogin() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoRoleLogin);
	}

	@Override
	public void setIsCanApproveOwnDoc (final boolean IsCanApproveOwnDoc)
	{
		set_Value (COLUMNNAME_IsCanApproveOwnDoc, IsCanApproveOwnDoc);
	}

	@Override
	public boolean isCanApproveOwnDoc() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCanApproveOwnDoc);
	}

	@Override
	public void setIsCanExport (final boolean IsCanExport)
	{
		set_Value (COLUMNNAME_IsCanExport, IsCanExport);
	}

	@Override
	public boolean isCanExport() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCanExport);
	}

	@Override
	public void setIsCanReport (final boolean IsCanReport)
	{
		set_Value (COLUMNNAME_IsCanReport, IsCanReport);
	}

	@Override
	public boolean isCanReport() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCanReport);
	}

	@Override
	public void setIsChangeLog (final boolean IsChangeLog)
	{
		set_Value (COLUMNNAME_IsChangeLog, IsChangeLog);
	}

	@Override
	public boolean isChangeLog() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsChangeLog);
	}

	@Override
	public void setIsDiscountAllowedOnTotal (final boolean IsDiscountAllowedOnTotal)
	{
		set_Value (COLUMNNAME_IsDiscountAllowedOnTotal, IsDiscountAllowedOnTotal);
	}

	@Override
	public boolean isDiscountAllowedOnTotal() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDiscountAllowedOnTotal);
	}

	@Override
	public void setIsDiscountUptoLimitPrice (final boolean IsDiscountUptoLimitPrice)
	{
		set_Value (COLUMNNAME_IsDiscountUptoLimitPrice, IsDiscountUptoLimitPrice);
	}

	@Override
	public boolean isDiscountUptoLimitPrice() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDiscountUptoLimitPrice);
	}

	@Override
	public void setIsManual (final boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, IsManual);
	}

	@Override
	public boolean isManual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManual);
	}

	@Override
	public void setIsMenuAvailable (final boolean IsMenuAvailable)
	{
		set_Value (COLUMNNAME_IsMenuAvailable, IsMenuAvailable);
	}

	@Override
	public boolean isMenuAvailable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMenuAvailable);
	}

	@Override
	public void setIsPersonalAccess (final boolean IsPersonalAccess)
	{
		set_Value (COLUMNNAME_IsPersonalAccess, IsPersonalAccess);
	}

	@Override
	public boolean isPersonalAccess() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPersonalAccess);
	}

	@Override
	public void setIsPersonalLock (final boolean IsPersonalLock)
	{
		set_Value (COLUMNNAME_IsPersonalLock, IsPersonalLock);
	}

	@Override
	public boolean isPersonalLock() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPersonalLock);
	}

	@Override
	public void setIsRoleAlwaysUseBetaFunctions (final boolean IsRoleAlwaysUseBetaFunctions)
	{
		set_Value (COLUMNNAME_IsRoleAlwaysUseBetaFunctions, IsRoleAlwaysUseBetaFunctions);
	}

	@Override
	public boolean isRoleAlwaysUseBetaFunctions() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRoleAlwaysUseBetaFunctions);
	}

	@Override
	public void setIsShowAcct (final boolean IsShowAcct)
	{
		set_Value (COLUMNNAME_IsShowAcct, IsShowAcct);
	}

	@Override
	public boolean isShowAcct() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShowAcct);
	}

	@Override
	public void setIsShowAllEntityTypes (final boolean IsShowAllEntityTypes)
	{
		set_Value (COLUMNNAME_IsShowAllEntityTypes, IsShowAllEntityTypes);
	}

	@Override
	public boolean isShowAllEntityTypes() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShowAllEntityTypes);
	}

	@Override
	public void setIsUseUserOrgAccess (final boolean IsUseUserOrgAccess)
	{
		set_Value (COLUMNNAME_IsUseUserOrgAccess, IsUseUserOrgAccess);
	}

	@Override
	public boolean isUseUserOrgAccess() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseUserOrgAccess);
	}

	@Override
	public void setMaxQueryRecords (final int MaxQueryRecords)
	{
		set_Value (COLUMNNAME_MaxQueryRecords, MaxQueryRecords);
	}

	@Override
	public int getMaxQueryRecords() 
	{
		return get_ValueAsInt(COLUMNNAME_MaxQueryRecords);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setOverwritePriceLimit (final boolean OverwritePriceLimit)
	{
		set_Value (COLUMNNAME_OverwritePriceLimit, OverwritePriceLimit);
	}

	@Override
	public boolean isOverwritePriceLimit() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OverwritePriceLimit);
	}

	/** 
	 * PreferenceType AD_Reference_ID=330
	 * Reference name: AD_Role PreferenceType
	 */
	public static final int PREFERENCETYPE_AD_Reference_ID=330;
	/** Client = C */
	public static final String PREFERENCETYPE_Client = "C";
	/** Organization = O */
	public static final String PREFERENCETYPE_Organization = "O";
	/** User = U */
	public static final String PREFERENCETYPE_User = "U";
	/** None = N */
	public static final String PREFERENCETYPE_None = "N";
	@Override
	public void setPreferenceType (final java.lang.String PreferenceType)
	{
		set_Value (COLUMNNAME_PreferenceType, PreferenceType);
	}

	@Override
	public java.lang.String getPreferenceType() 
	{
		return get_ValueAsString(COLUMNNAME_PreferenceType);
	}

	/** 
	 * Role_Group AD_Reference_ID=541681
	 * Reference name: Role_Group
	 */
	public static final int ROLE_GROUP_AD_Reference_ID=541681;
	/** Accounting = Accounting */
	public static final String ROLE_GROUP_Accounting = "Accounting";
	@Override
	public void setRole_Group (final @Nullable java.lang.String Role_Group)
	{
		set_Value (COLUMNNAME_Role_Group, Role_Group);
	}

	@Override
	public java.lang.String getRole_Group() 
	{
		return get_ValueAsString(COLUMNNAME_Role_Group);
	}

	@Override
	public org.compiere.model.I_AD_Menu getRoot_Menu()
	{
		return get_ValueAsPO(COLUMNNAME_Root_Menu_ID, org.compiere.model.I_AD_Menu.class);
	}

	@Override
	public void setRoot_Menu(final org.compiere.model.I_AD_Menu Root_Menu)
	{
		set_ValueFromPO(COLUMNNAME_Root_Menu_ID, org.compiere.model.I_AD_Menu.class, Root_Menu);
	}

	@Override
	public void setRoot_Menu_ID (final int Root_Menu_ID)
	{
		if (Root_Menu_ID < 1) 
			set_Value (COLUMNNAME_Root_Menu_ID, null);
		else 
			set_Value (COLUMNNAME_Root_Menu_ID, Root_Menu_ID);
	}

	@Override
	public int getRoot_Menu_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Root_Menu_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
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
	public void setUserDiscount (final @Nullable BigDecimal UserDiscount)
	{
		set_Value (COLUMNNAME_UserDiscount, UserDiscount);
	}

	@Override
	public BigDecimal getUserDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_UserDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * UserLevel AD_Reference_ID=226
	 * Reference name: AD_Role User Level
	 */
	public static final int USERLEVEL_AD_Reference_ID=226;
	/** System = S__ */
	public static final String USERLEVEL_System = "S__";
	/** Client = _C_ */
	public static final String USERLEVEL_Client = "_C_";
	/** Organization = __O */
	public static final String USERLEVEL_Organization = "__O";
	/** Client+Organization = _CO */
	public static final String USERLEVEL_ClientPlusOrganization = "_CO";
	@Override
	public void setUserLevel (final java.lang.String UserLevel)
	{
		set_Value (COLUMNNAME_UserLevel, UserLevel);
	}

	@Override
	public java.lang.String getUserLevel() 
	{
		return get_ValueAsString(COLUMNNAME_UserLevel);
	}

	@Override
	public void setWEBUI_Role (final boolean WEBUI_Role)
	{
		set_Value (COLUMNNAME_WEBUI_Role, WEBUI_Role);
	}

	@Override
	public boolean isWEBUI_Role() 
	{
		return get_ValueAsBoolean(COLUMNNAME_WEBUI_Role);
	}
}