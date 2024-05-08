/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Role
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Role extends org.compiere.model.PO implements I_AD_Role, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1996748214L;

    /** Standard Constructor */
    public X_AD_Role (Properties ctx, int AD_Role_ID, String trxName)
    {
      super (ctx, AD_Role_ID, trxName);
      /** if (AD_Role_ID == 0)
        {
			setAD_Role_ID (0);
			setAllow_Info_Account (false); // N
			setAllow_Info_Asset (false); // N
			setAllow_Info_BPartner (false); // N
			setAllow_Info_CashJournal (false); // N
			setAllow_Info_CRP (true); // Y
			setAllow_Info_InOut (false); // N
			setAllow_Info_Invoice (false); // N
			setAllow_Info_MRP (true); // Y
			setAllow_Info_Order (false); // N
			setAllow_Info_Payment (false); // N
			setAllow_Info_Product (true); // Y
			setAllow_Info_Resource (false); // N
			setAllow_Info_Schedule (false); // N
			setConfirmQueryRecords (0); // 0
			setIsAccessAllOrgs (false); // N
			setIsAllowLoginDateOverride (false); // N
			setIsAttachmentDeletionAllowed (false); // N
			setIsAutoRoleLogin (false); // N
			setIsCanApproveOwnDoc (false);
			setIsCanExport (true); // Y
			setIsCanReport (true); // Y
			setIsChangeLog (false); // N
			setIsDiscountAllowedOnTotal (false);
			setIsDiscountUptoLimitPrice (false);
			setIsManual (false);
			setIsMenuAvailable (true); // Y
			setIsPersonalAccess (false); // N
			setIsPersonalLock (false); // N
			setIsRoleAlwaysUseBetaFunctions (false); // N
			setIsShowAcct (false); // N
			setIsShowAllEntityTypes (false); // N
			setIsUseUserOrgAccess (false); // N
			setMaxQueryRecords (0); // 0
			setName (null);
			setOverwritePriceLimit (false); // N
			setPreferenceType (null); // O
			setUserLevel (null); // _CO
			setWEBUI_Role (true); // Y
        } */
    }

    /** Load Constructor */
    public X_AD_Role (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Form getAD_Form() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class);
	}

	@Override
	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form)
	{
		set_ValueFromPO(COLUMNNAME_AD_Form_ID, org.compiere.model.I_AD_Form.class, AD_Form);
	}

	/** Set Special Form.
		@param AD_Form_ID 
		Special Form
	  */
	@Override
	public void setAD_Form_ID (int AD_Form_ID)
	{
		if (AD_Form_ID < 1) 
			set_Value (COLUMNNAME_AD_Form_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Form_ID, Integer.valueOf(AD_Form_ID));
	}

	/** Get Special Form.
		@return Special Form
	  */
	@Override
	public int getAD_Form_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Form_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rolle.
		@param AD_Role_ID 
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_Menu() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Menu_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Menu(org.compiere.model.I_AD_Tree AD_Tree_Menu)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Menu_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Menu);
	}

	/** Set Primärbaum Menü.
		@param AD_Tree_Menu_ID 
		Tree of the menu
	  */
	@Override
	public void setAD_Tree_Menu_ID (int AD_Tree_Menu_ID)
	{
		if (AD_Tree_Menu_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Menu_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Menu_ID, Integer.valueOf(AD_Tree_Menu_ID));
	}

	/** Get Primärbaum Menü.
		@return Tree of the menu
	  */
	@Override
	public int getAD_Tree_Menu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Menu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Tree getAD_Tree_Org() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Tree_Org_ID, org.compiere.model.I_AD_Tree.class);
	}

	@Override
	public void setAD_Tree_Org(org.compiere.model.I_AD_Tree AD_Tree_Org)
	{
		set_ValueFromPO(COLUMNNAME_AD_Tree_Org_ID, org.compiere.model.I_AD_Tree.class, AD_Tree_Org);
	}

	/** Set Primärbaum Organisation.
		@param AD_Tree_Org_ID 
		Tree to determine organizational hierarchy
	  */
	@Override
	public void setAD_Tree_Org_ID (int AD_Tree_Org_ID)
	{
		if (AD_Tree_Org_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Org_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Org_ID, Integer.valueOf(AD_Tree_Org_ID));
	}

	/** Get Primärbaum Organisation.
		@return Tree to determine organizational hierarchy
	  */
	@Override
	public int getAD_Tree_Org_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Allow Info Account.
		@param Allow_Info_Account Allow Info Account	  */
	@Override
	public void setAllow_Info_Account (boolean Allow_Info_Account)
	{
		set_Value (COLUMNNAME_Allow_Info_Account, Boolean.valueOf(Allow_Info_Account));
	}

	/** Get Allow Info Account.
		@return Allow Info Account	  */
	@Override
	public boolean isAllow_Info_Account () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_Account);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info Asset.
		@param Allow_Info_Asset Allow Info Asset	  */
	@Override
	public void setAllow_Info_Asset (boolean Allow_Info_Asset)
	{
		set_Value (COLUMNNAME_Allow_Info_Asset, Boolean.valueOf(Allow_Info_Asset));
	}

	/** Get Allow Info Asset.
		@return Allow Info Asset	  */
	@Override
	public boolean isAllow_Info_Asset () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_Asset);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info BPartner.
		@param Allow_Info_BPartner Allow Info BPartner	  */
	@Override
	public void setAllow_Info_BPartner (boolean Allow_Info_BPartner)
	{
		set_Value (COLUMNNAME_Allow_Info_BPartner, Boolean.valueOf(Allow_Info_BPartner));
	}

	/** Get Allow Info BPartner.
		@return Allow Info BPartner	  */
	@Override
	public boolean isAllow_Info_BPartner () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_BPartner);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info CashJournal.
		@param Allow_Info_CashJournal Allow Info CashJournal	  */
	@Override
	public void setAllow_Info_CashJournal (boolean Allow_Info_CashJournal)
	{
		set_Value (COLUMNNAME_Allow_Info_CashJournal, Boolean.valueOf(Allow_Info_CashJournal));
	}

	/** Get Allow Info CashJournal.
		@return Allow Info CashJournal	  */
	@Override
	public boolean isAllow_Info_CashJournal () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_CashJournal);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info CRP.
		@param Allow_Info_CRP Allow Info CRP	  */
	@Override
	public void setAllow_Info_CRP (boolean Allow_Info_CRP)
	{
		set_Value (COLUMNNAME_Allow_Info_CRP, Boolean.valueOf(Allow_Info_CRP));
	}

	/** Get Allow Info CRP.
		@return Allow Info CRP	  */
	@Override
	public boolean isAllow_Info_CRP () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_CRP);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info InOut.
		@param Allow_Info_InOut Allow Info InOut	  */
	@Override
	public void setAllow_Info_InOut (boolean Allow_Info_InOut)
	{
		set_Value (COLUMNNAME_Allow_Info_InOut, Boolean.valueOf(Allow_Info_InOut));
	}

	/** Get Allow Info InOut.
		@return Allow Info InOut	  */
	@Override
	public boolean isAllow_Info_InOut () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_InOut);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info Invoice.
		@param Allow_Info_Invoice Allow Info Invoice	  */
	@Override
	public void setAllow_Info_Invoice (boolean Allow_Info_Invoice)
	{
		set_Value (COLUMNNAME_Allow_Info_Invoice, Boolean.valueOf(Allow_Info_Invoice));
	}

	/** Get Allow Info Invoice.
		@return Allow Info Invoice	  */
	@Override
	public boolean isAllow_Info_Invoice () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_Invoice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info MRP.
		@param Allow_Info_MRP Allow Info MRP	  */
	@Override
	public void setAllow_Info_MRP (boolean Allow_Info_MRP)
	{
		set_Value (COLUMNNAME_Allow_Info_MRP, Boolean.valueOf(Allow_Info_MRP));
	}

	/** Get Allow Info MRP.
		@return Allow Info MRP	  */
	@Override
	public boolean isAllow_Info_MRP () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_MRP);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info Order.
		@param Allow_Info_Order Allow Info Order	  */
	@Override
	public void setAllow_Info_Order (boolean Allow_Info_Order)
	{
		set_Value (COLUMNNAME_Allow_Info_Order, Boolean.valueOf(Allow_Info_Order));
	}

	/** Get Allow Info Order.
		@return Allow Info Order	  */
	@Override
	public boolean isAllow_Info_Order () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_Order);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info Payment.
		@param Allow_Info_Payment Allow Info Payment	  */
	@Override
	public void setAllow_Info_Payment (boolean Allow_Info_Payment)
	{
		set_Value (COLUMNNAME_Allow_Info_Payment, Boolean.valueOf(Allow_Info_Payment));
	}

	/** Get Allow Info Payment.
		@return Allow Info Payment	  */
	@Override
	public boolean isAllow_Info_Payment () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_Payment);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info Product.
		@param Allow_Info_Product Allow Info Product	  */
	@Override
	public void setAllow_Info_Product (boolean Allow_Info_Product)
	{
		set_Value (COLUMNNAME_Allow_Info_Product, Boolean.valueOf(Allow_Info_Product));
	}

	/** Get Allow Info Product.
		@return Allow Info Product	  */
	@Override
	public boolean isAllow_Info_Product () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_Product);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info Resource.
		@param Allow_Info_Resource Allow Info Resource	  */
	@Override
	public void setAllow_Info_Resource (boolean Allow_Info_Resource)
	{
		set_Value (COLUMNNAME_Allow_Info_Resource, Boolean.valueOf(Allow_Info_Resource));
	}

	/** Get Allow Info Resource.
		@return Allow Info Resource	  */
	@Override
	public boolean isAllow_Info_Resource () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_Resource);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Info Schedule.
		@param Allow_Info_Schedule Allow Info Schedule	  */
	@Override
	public void setAllow_Info_Schedule (boolean Allow_Info_Schedule)
	{
		set_Value (COLUMNNAME_Allow_Info_Schedule, Boolean.valueOf(Allow_Info_Schedule));
	}

	/** Get Allow Info Schedule.
		@return Allow Info Schedule	  */
	@Override
	public boolean isAllow_Info_Schedule () 
	{
		Object oo = get_Value(COLUMNNAME_Allow_Info_Schedule);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Freigabe-Betrag.
		@param AmtApproval 
		The approval amount limit for this role
	  */
	@Override
	public void setAmtApproval (java.math.BigDecimal AmtApproval)
	{
		set_Value (COLUMNNAME_AmtApproval, AmtApproval);
	}

	/** Get Freigabe-Betrag.
		@return The approval amount limit for this role
	  */
	@Override
	public java.math.BigDecimal getAmtApproval () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtApproval);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bestätigung Anzahl Suchergebnisse.
		@param ConfirmQueryRecords 
		Require Confirmation if more records will be returned by the query (If not defined 500)
	  */
	@Override
	public void setConfirmQueryRecords (int ConfirmQueryRecords)
	{
		set_Value (COLUMNNAME_ConfirmQueryRecords, Integer.valueOf(ConfirmQueryRecords));
	}

	/** Get Bestätigung Anzahl Suchergebnisse.
		@return Require Confirmation if more records will be returned by the query (If not defined 500)
	  */
	@Override
	public int getConfirmQueryRecords () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ConfirmQueryRecords);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	/** Set Verbindungsart.
		@param ConnectionProfile 
		How a Java Client connects to the server(s)
	  */
	@Override
	public void setConnectionProfile (java.lang.String ConnectionProfile)
	{

		set_Value (COLUMNNAME_ConnectionProfile, ConnectionProfile);
	}

	/** Get Verbindungsart.
		@return How a Java Client connects to the server(s)
	  */
	@Override
	public java.lang.String getConnectionProfile () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ConnectionProfile);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Zugriff auf alle Organisationen.
		@param IsAccessAllOrgs 
		Access all Organizations (no org access control) of the client
	  */
	@Override
	public void setIsAccessAllOrgs (boolean IsAccessAllOrgs)
	{
		set_Value (COLUMNNAME_IsAccessAllOrgs, Boolean.valueOf(IsAccessAllOrgs));
	}

	/** Get Zugriff auf alle Organisationen.
		@return Access all Organizations (no org access control) of the client
	  */
	@Override
	public boolean isAccessAllOrgs () 
	{
		Object oo = get_Value(COLUMNNAME_IsAccessAllOrgs);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow chaning login date.
		@param IsAllowLoginDateOverride Allow chaning login date	  */
	@Override
	public void setIsAllowLoginDateOverride (boolean IsAllowLoginDateOverride)
	{
		set_Value (COLUMNNAME_IsAllowLoginDateOverride, Boolean.valueOf(IsAllowLoginDateOverride));
	}

	/** Get Allow chaning login date.
		@return Allow chaning login date	  */
	@Override
	public boolean isAllowLoginDateOverride () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowLoginDateOverride);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsAttachmentDeletionAllowed.
		@param IsAttachmentDeletionAllowed IsAttachmentDeletionAllowed	  */
	@Override
	public void setIsAttachmentDeletionAllowed (boolean IsAttachmentDeletionAllowed)
	{
		set_Value (COLUMNNAME_IsAttachmentDeletionAllowed, Boolean.valueOf(IsAttachmentDeletionAllowed));
	}

	/** Get IsAttachmentDeletionAllowed.
		@return IsAttachmentDeletionAllowed	  */
	@Override
	public boolean isAttachmentDeletionAllowed () 
	{
		Object oo = get_Value(COLUMNNAME_IsAttachmentDeletionAllowed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Skip role login page.
		@param IsAutoRoleLogin 
		Skip role login page and take the defaults
	  */
	@Override
	public void setIsAutoRoleLogin (boolean IsAutoRoleLogin)
	{
		set_Value (COLUMNNAME_IsAutoRoleLogin, Boolean.valueOf(IsAutoRoleLogin));
	}

	/** Get Skip role login page.
		@return Skip role login page and take the defaults
	  */
	@Override
	public boolean isAutoRoleLogin () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoRoleLogin);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Freigabe eigener Belege.
		@param IsCanApproveOwnDoc 
		Users with this role can approve their own documents
	  */
	@Override
	public void setIsCanApproveOwnDoc (boolean IsCanApproveOwnDoc)
	{
		set_Value (COLUMNNAME_IsCanApproveOwnDoc, Boolean.valueOf(IsCanApproveOwnDoc));
	}

	/** Get Freigabe eigener Belege.
		@return Users with this role can approve their own documents
	  */
	@Override
	public boolean isCanApproveOwnDoc () 
	{
		Object oo = get_Value(COLUMNNAME_IsCanApproveOwnDoc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kann exportieren.
		@param IsCanExport 
		Users with this role can export data
	  */
	@Override
	public void setIsCanExport (boolean IsCanExport)
	{
		set_Value (COLUMNNAME_IsCanExport, Boolean.valueOf(IsCanExport));
	}

	/** Get Kann exportieren.
		@return Users with this role can export data
	  */
	@Override
	public boolean isCanExport () 
	{
		Object oo = get_Value(COLUMNNAME_IsCanExport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kann Berichte erstellen.
		@param IsCanReport 
		Users with this role can create reports
	  */
	@Override
	public void setIsCanReport (boolean IsCanReport)
	{
		set_Value (COLUMNNAME_IsCanReport, Boolean.valueOf(IsCanReport));
	}

	/** Get Kann Berichte erstellen.
		@return Users with this role can create reports
	  */
	@Override
	public boolean isCanReport () 
	{
		Object oo = get_Value(COLUMNNAME_IsCanReport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Änderungen protokollieren.
		@param IsChangeLog 
		Maintain a log of changes
	  */
	@Override
	public void setIsChangeLog (boolean IsChangeLog)
	{
		set_Value (COLUMNNAME_IsChangeLog, Boolean.valueOf(IsChangeLog));
	}

	/** Get Änderungen protokollieren.
		@return Maintain a log of changes
	  */
	@Override
	public boolean isChangeLog () 
	{
		Object oo = get_Value(COLUMNNAME_IsChangeLog);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDiscountAllowedOnTotal.
		@param IsDiscountAllowedOnTotal IsDiscountAllowedOnTotal	  */
	@Override
	public void setIsDiscountAllowedOnTotal (boolean IsDiscountAllowedOnTotal)
	{
		set_Value (COLUMNNAME_IsDiscountAllowedOnTotal, Boolean.valueOf(IsDiscountAllowedOnTotal));
	}

	/** Get IsDiscountAllowedOnTotal.
		@return IsDiscountAllowedOnTotal	  */
	@Override
	public boolean isDiscountAllowedOnTotal () 
	{
		Object oo = get_Value(COLUMNNAME_IsDiscountAllowedOnTotal);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDiscountUptoLimitPrice.
		@param IsDiscountUptoLimitPrice IsDiscountUptoLimitPrice	  */
	@Override
	public void setIsDiscountUptoLimitPrice (boolean IsDiscountUptoLimitPrice)
	{
		set_Value (COLUMNNAME_IsDiscountUptoLimitPrice, Boolean.valueOf(IsDiscountUptoLimitPrice));
	}

	/** Get IsDiscountUptoLimitPrice.
		@return IsDiscountUptoLimitPrice	  */
	@Override
	public boolean isDiscountUptoLimitPrice () 
	{
		Object oo = get_Value(COLUMNNAME_IsDiscountUptoLimitPrice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manuell.
		@param IsManual 
		This is a manual process
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return This is a manual process
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menu available.
		@param IsMenuAvailable Menu available	  */
	@Override
	public void setIsMenuAvailable (boolean IsMenuAvailable)
	{
		set_Value (COLUMNNAME_IsMenuAvailable, Boolean.valueOf(IsMenuAvailable));
	}

	/** Get Menu available.
		@return Menu available	  */
	@Override
	public boolean isMenuAvailable () 
	{
		Object oo = get_Value(COLUMNNAME_IsMenuAvailable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zugriff auf gesperrte Einträge.
		@param IsPersonalAccess 
		Allow access to all personal records
	  */
	@Override
	public void setIsPersonalAccess (boolean IsPersonalAccess)
	{
		set_Value (COLUMNNAME_IsPersonalAccess, Boolean.valueOf(IsPersonalAccess));
	}

	/** Get Zugriff auf gesperrte Einträge.
		@return Allow access to all personal records
	  */
	@Override
	public boolean isPersonalAccess () 
	{
		Object oo = get_Value(COLUMNNAME_IsPersonalAccess);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Persönliche Sperre.
		@param IsPersonalLock 
		Allow users with role to lock access to personal records
	  */
	@Override
	public void setIsPersonalLock (boolean IsPersonalLock)
	{
		set_Value (COLUMNNAME_IsPersonalLock, Boolean.valueOf(IsPersonalLock));
	}

	/** Get Persönliche Sperre.
		@return Allow users with role to lock access to personal records
	  */
	@Override
	public boolean isPersonalLock () 
	{
		Object oo = get_Value(COLUMNNAME_IsPersonalLock);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Beta-Funktionalität verwenden abw..
		@param IsRoleAlwaysUseBetaFunctions 
		Diese Einstellung kann für eine bestimmte Rolle die bei "Mandant" hinterlegte Einstellung überschreiben
	  */
	@Override
	public void setIsRoleAlwaysUseBetaFunctions (boolean IsRoleAlwaysUseBetaFunctions)
	{
		set_Value (COLUMNNAME_IsRoleAlwaysUseBetaFunctions, Boolean.valueOf(IsRoleAlwaysUseBetaFunctions));
	}

	/** Get Beta-Funktionalität verwenden abw..
		@return Diese Einstellung kann für eine bestimmte Rolle die bei "Mandant" hinterlegte Einstellung überschreiben
	  */
	@Override
	public boolean isRoleAlwaysUseBetaFunctions () 
	{
		Object oo = get_Value(COLUMNNAME_IsRoleAlwaysUseBetaFunctions);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Buchführungsdaten zeigen.
		@param IsShowAcct 
		Users with this role can see accounting information
	  */
	@Override
	public void setIsShowAcct (boolean IsShowAcct)
	{
		set_Value (COLUMNNAME_IsShowAcct, Boolean.valueOf(IsShowAcct));
	}

	/** Get Buchführungsdaten zeigen.
		@return Users with this role can see accounting information
	  */
	@Override
	public boolean isShowAcct () 
	{
		Object oo = get_Value(COLUMNNAME_IsShowAcct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Show all entity types.
		@param IsShowAllEntityTypes 
		Show all entity types, even if some of them were marked to not be shown.
	  */
	@Override
	public void setIsShowAllEntityTypes (boolean IsShowAllEntityTypes)
	{
		set_Value (COLUMNNAME_IsShowAllEntityTypes, Boolean.valueOf(IsShowAllEntityTypes));
	}

	/** Get Show all entity types.
		@return Show all entity types, even if some of them were marked to not be shown.
	  */
	@Override
	public boolean isShowAllEntityTypes () 
	{
		Object oo = get_Value(COLUMNNAME_IsShowAllEntityTypes);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Nutzerbezogener Organisationszugriff.
		@param IsUseUserOrgAccess 
		Use Org Access defined by user instead of Role Org Access
	  */
	@Override
	public void setIsUseUserOrgAccess (boolean IsUseUserOrgAccess)
	{
		set_Value (COLUMNNAME_IsUseUserOrgAccess, Boolean.valueOf(IsUseUserOrgAccess));
	}

	/** Get Nutzerbezogener Organisationszugriff.
		@return Use Org Access defined by user instead of Role Org Access
	  */
	@Override
	public boolean isUseUserOrgAccess () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseUserOrgAccess);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Max. Suchergebnisse.
		@param MaxQueryRecords 
		If defined, you cannot query more records as defined - the query criteria needs to be changed to query less records
	  */
	@Override
	public void setMaxQueryRecords (int MaxQueryRecords)
	{
		set_Value (COLUMNNAME_MaxQueryRecords, Integer.valueOf(MaxQueryRecords));
	}

	/** Get Max. Suchergebnisse.
		@return If defined, you cannot query more records as defined - the query criteria needs to be changed to query less records
	  */
	@Override
	public int getMaxQueryRecords () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxQueryRecords);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Mindestpreis überschreiben.
		@param OverwritePriceLimit 
		Overwrite Price Limit if the Price List  enforces the Price Limit
	  */
	@Override
	public void setOverwritePriceLimit (boolean OverwritePriceLimit)
	{
		set_Value (COLUMNNAME_OverwritePriceLimit, Boolean.valueOf(OverwritePriceLimit));
	}

	/** Get Mindestpreis überschreiben.
		@return Overwrite Price Limit if the Price List  enforces the Price Limit
	  */
	@Override
	public boolean isOverwritePriceLimit () 
	{
		Object oo = get_Value(COLUMNNAME_OverwritePriceLimit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
	/** Set Ebene für Einstellungen.
		@param PreferenceType 
		Determines what preferences the user can set
	  */
	@Override
	public void setPreferenceType (java.lang.String PreferenceType)
	{

		set_Value (COLUMNNAME_PreferenceType, PreferenceType);
	}

	/** Get Ebene für Einstellungen.
		@return Determines what preferences the user can set
	  */
	@Override
	public java.lang.String getPreferenceType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PreferenceType);
	}

	@Override
	public org.compiere.model.I_AD_Menu getRoot_Menu() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Root_Menu_ID, org.compiere.model.I_AD_Menu.class);
	}

	@Override
	public void setRoot_Menu(org.compiere.model.I_AD_Menu Root_Menu)
	{
		set_ValueFromPO(COLUMNNAME_Root_Menu_ID, org.compiere.model.I_AD_Menu.class, Root_Menu);
	}

	/** Set Root menu node.
		@param Root_Menu_ID Root menu node	  */
	@Override
	public void setRoot_Menu_ID (int Root_Menu_ID)
	{
		if (Root_Menu_ID < 1) 
			set_Value (COLUMNNAME_Root_Menu_ID, null);
		else 
			set_Value (COLUMNNAME_Root_Menu_ID, Integer.valueOf(Root_Menu_ID));
	}

	/** Get Root menu node.
		@return Root menu node	  */
	@Override
	public int getRoot_Menu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Root_Menu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der EintrÃ¤ge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der EintrÃ¤ge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set UserDiscount.
		@param UserDiscount UserDiscount	  */
	@Override
	public void setUserDiscount (java.math.BigDecimal UserDiscount)
	{
		set_Value (COLUMNNAME_UserDiscount, UserDiscount);
	}

	/** Get UserDiscount.
		@return UserDiscount	  */
	@Override
	public java.math.BigDecimal getUserDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UserDiscount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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
	/** Set Nutzer-Ebene.
		@param UserLevel 
		System Client Organization
	  */
	@Override
	public void setUserLevel (java.lang.String UserLevel)
	{

		set_Value (COLUMNNAME_UserLevel, UserLevel);
	}

	/** Get Nutzer-Ebene.
		@return System Client Organization
	  */
	@Override
	public java.lang.String getUserLevel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserLevel);
	}

	/** Set Is webui role.
		@param WEBUI_Role Is webui role	  */
	@Override
	public void setWEBUI_Role (boolean WEBUI_Role)
	{
		set_Value (COLUMNNAME_WEBUI_Role, Boolean.valueOf(WEBUI_Role));
	}

	/** Get Is webui role.
		@return Is webui role	  */
	@Override
	public boolean isWEBUI_Role () 
	{
		Object oo = get_Value(COLUMNNAME_WEBUI_Role);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}