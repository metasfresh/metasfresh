package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Role
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Role 
{

	String Table_Name = "AD_Role";

//	/** AD_Table_ID=156 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Special Form.
	 * Special Form
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Form_ID (int AD_Form_ID);

	/**
	 * Get Special Form.
	 * Special Form
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Form_ID();

	@Nullable org.compiere.model.I_AD_Form getAD_Form();

	void setAD_Form(@Nullable org.compiere.model.I_AD_Form AD_Form);

	ModelColumn<I_AD_Role, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new ModelColumn<>(I_AD_Role.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
	String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Role.
	 * Responsibility Role
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Role.
	 * Responsibility Role
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Role_ID();

	ModelColumn<I_AD_Role, Object> COLUMN_AD_Role_ID = new ModelColumn<>(I_AD_Role.class, "AD_Role_ID", null);
	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Menu Tree.
	 * Tree of the menu
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Tree_Menu_ID (int AD_Tree_Menu_ID);

	/**
	 * Get Menu Tree.
	 * Tree of the menu
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Tree_Menu_ID();

	@Nullable org.compiere.model.I_AD_Tree getAD_Tree_Menu();

	void setAD_Tree_Menu(@Nullable org.compiere.model.I_AD_Tree AD_Tree_Menu);

	ModelColumn<I_AD_Role, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Menu_ID = new ModelColumn<>(I_AD_Role.class, "AD_Tree_Menu_ID", org.compiere.model.I_AD_Tree.class);
	String COLUMNNAME_AD_Tree_Menu_ID = "AD_Tree_Menu_ID";

	/**
	 * Set Organization Tree.
	 * Trees are used for (financial) reporting and security access (via role)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Tree_Org_ID (int AD_Tree_Org_ID);

	/**
	 * Get Organization Tree.
	 * Trees are used for (financial) reporting and security access (via role)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Tree_Org_ID();

	@Nullable org.compiere.model.I_AD_Tree getAD_Tree_Org();

	void setAD_Tree_Org(@Nullable org.compiere.model.I_AD_Tree AD_Tree_Org);

	ModelColumn<I_AD_Role, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Org_ID = new ModelColumn<>(I_AD_Role.class, "AD_Tree_Org_ID", org.compiere.model.I_AD_Tree.class);
	String COLUMNNAME_AD_Tree_Org_ID = "AD_Tree_Org_ID";

	/**
	 * Set Allow Info Account.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_Account (boolean Allow_Info_Account);

	/**
	 * Get Allow Info Account.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_Account();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Account = new ModelColumn<>(I_AD_Role.class, "Allow_Info_Account", null);
	String COLUMNNAME_Allow_Info_Account = "Allow_Info_Account";

	/**
	 * Set Allow Info Asset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_Asset (boolean Allow_Info_Asset);

	/**
	 * Get Allow Info Asset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_Asset();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Asset = new ModelColumn<>(I_AD_Role.class, "Allow_Info_Asset", null);
	String COLUMNNAME_Allow_Info_Asset = "Allow_Info_Asset";

	/**
	 * Set Allow Info BPartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_BPartner (boolean Allow_Info_BPartner);

	/**
	 * Get Allow Info BPartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_BPartner();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_BPartner = new ModelColumn<>(I_AD_Role.class, "Allow_Info_BPartner", null);
	String COLUMNNAME_Allow_Info_BPartner = "Allow_Info_BPartner";

	/**
	 * Set Allow Info CashJournal.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_CashJournal (boolean Allow_Info_CashJournal);

	/**
	 * Get Allow Info CashJournal.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_CashJournal();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_CashJournal = new ModelColumn<>(I_AD_Role.class, "Allow_Info_CashJournal", null);
	String COLUMNNAME_Allow_Info_CashJournal = "Allow_Info_CashJournal";

	/**
	 * Set Allow Info CRP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_CRP (boolean Allow_Info_CRP);

	/**
	 * Get Allow Info CRP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_CRP();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_CRP = new ModelColumn<>(I_AD_Role.class, "Allow_Info_CRP", null);
	String COLUMNNAME_Allow_Info_CRP = "Allow_Info_CRP";

	/**
	 * Set Allow Info InOut.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_InOut (boolean Allow_Info_InOut);

	/**
	 * Get Allow Info InOut.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_InOut();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_InOut = new ModelColumn<>(I_AD_Role.class, "Allow_Info_InOut", null);
	String COLUMNNAME_Allow_Info_InOut = "Allow_Info_InOut";

	/**
	 * Set Allow Info Invoice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_Invoice (boolean Allow_Info_Invoice);

	/**
	 * Get Allow Info Invoice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_Invoice();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Invoice = new ModelColumn<>(I_AD_Role.class, "Allow_Info_Invoice", null);
	String COLUMNNAME_Allow_Info_Invoice = "Allow_Info_Invoice";

	/**
	 * Set Allow Info MRP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_MRP (boolean Allow_Info_MRP);

	/**
	 * Get Allow Info MRP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_MRP();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_MRP = new ModelColumn<>(I_AD_Role.class, "Allow_Info_MRP", null);
	String COLUMNNAME_Allow_Info_MRP = "Allow_Info_MRP";

	/**
	 * Set Allow Info Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_Order (boolean Allow_Info_Order);

	/**
	 * Get Allow Info Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_Order();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Order = new ModelColumn<>(I_AD_Role.class, "Allow_Info_Order", null);
	String COLUMNNAME_Allow_Info_Order = "Allow_Info_Order";

	/**
	 * Set Allow Info Payment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_Payment (boolean Allow_Info_Payment);

	/**
	 * Get Allow Info Payment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_Payment();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Payment = new ModelColumn<>(I_AD_Role.class, "Allow_Info_Payment", null);
	String COLUMNNAME_Allow_Info_Payment = "Allow_Info_Payment";

	/**
	 * Set Allow Info Product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_Product (boolean Allow_Info_Product);

	/**
	 * Get Allow Info Product.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_Product();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Product = new ModelColumn<>(I_AD_Role.class, "Allow_Info_Product", null);
	String COLUMNNAME_Allow_Info_Product = "Allow_Info_Product";

	/**
	 * Set Allow Info Resource.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_Resource (boolean Allow_Info_Resource);

	/**
	 * Get Allow Info Resource.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_Resource();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Resource = new ModelColumn<>(I_AD_Role.class, "Allow_Info_Resource", null);
	String COLUMNNAME_Allow_Info_Resource = "Allow_Info_Resource";

	/**
	 * Set Allow Info Schedule.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllow_Info_Schedule (boolean Allow_Info_Schedule);

	/**
	 * Get Allow Info Schedule.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllow_Info_Schedule();

	ModelColumn<I_AD_Role, Object> COLUMN_Allow_Info_Schedule = new ModelColumn<>(I_AD_Role.class, "Allow_Info_Schedule", null);
	String COLUMNNAME_Allow_Info_Schedule = "Allow_Info_Schedule";

	/**
	 * Set Approval Amount.
	 * The approval amount limit for this role
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmtApproval (@Nullable BigDecimal AmtApproval);

	/**
	 * Get Approval Amount.
	 * The approval amount limit for this role
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtApproval();

	ModelColumn<I_AD_Role, Object> COLUMN_AmtApproval = new ModelColumn<>(I_AD_Role.class, "AmtApproval", null);
	String COLUMNNAME_AmtApproval = "AmtApproval";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Confirm Query Records.
	 * Require Confirmation if more records will be returned by the query (If not defined 500)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setConfirmQueryRecords (int ConfirmQueryRecords);

	/**
	 * Get Confirm Query Records.
	 * Require Confirmation if more records will be returned by the query (If not defined 500)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getConfirmQueryRecords();

	ModelColumn<I_AD_Role, Object> COLUMN_ConfirmQueryRecords = new ModelColumn<>(I_AD_Role.class, "ConfirmQueryRecords", null);
	String COLUMNNAME_ConfirmQueryRecords = "ConfirmQueryRecords";

	/**
	 * Set Connection Profile.
	 * How a Java Client connects to the server(s)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setConnectionProfile (@Nullable java.lang.String ConnectionProfile);

	/**
	 * Get Connection Profile.
	 * How a Java Client connects to the server(s)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getConnectionProfile();

	ModelColumn<I_AD_Role, Object> COLUMN_ConnectionProfile = new ModelColumn<>(I_AD_Role.class, "ConnectionProfile", null);
	String COLUMNNAME_ConnectionProfile = "ConnectionProfile";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Role, Object> COLUMN_Created = new ModelColumn<>(I_AD_Role.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Role, Object> COLUMN_Description = new ModelColumn<>(I_AD_Role.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Access all Orgs.
	 * Access all Organizations (no org access control) of the client
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAccessAllOrgs (boolean IsAccessAllOrgs);

	/**
	 * Get Access all Orgs.
	 * Access all Organizations (no org access control) of the client
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAccessAllOrgs();

	ModelColumn<I_AD_Role, Object> COLUMN_IsAccessAllOrgs = new ModelColumn<>(I_AD_Role.class, "IsAccessAllOrgs", null);
	String COLUMNNAME_IsAccessAllOrgs = "IsAccessAllOrgs";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_AD_Role, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Role.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allow chaning login date.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowLoginDateOverride (boolean IsAllowLoginDateOverride);

	/**
	 * Get Allow chaning login date.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowLoginDateOverride();

	ModelColumn<I_AD_Role, Object> COLUMN_IsAllowLoginDateOverride = new ModelColumn<>(I_AD_Role.class, "IsAllowLoginDateOverride", null);
	String COLUMNNAME_IsAllowLoginDateOverride = "IsAllowLoginDateOverride";

	/**
	 * Set Attachment Deletion Allowed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAttachmentDeletionAllowed (boolean IsAttachmentDeletionAllowed);

	/**
	 * Get Attachment Deletion Allowed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAttachmentDeletionAllowed();

	ModelColumn<I_AD_Role, Object> COLUMN_IsAttachmentDeletionAllowed = new ModelColumn<>(I_AD_Role.class, "IsAttachmentDeletionAllowed", null);
	String COLUMNNAME_IsAttachmentDeletionAllowed = "IsAttachmentDeletionAllowed";

	/**
	 * Set Skip role login page.
	 * Skip role login page and take the defaults
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoRoleLogin (boolean IsAutoRoleLogin);

	/**
	 * Get Skip role login page.
	 * Skip role login page and take the defaults
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoRoleLogin();

	ModelColumn<I_AD_Role, Object> COLUMN_IsAutoRoleLogin = new ModelColumn<>(I_AD_Role.class, "IsAutoRoleLogin", null);
	String COLUMNNAME_IsAutoRoleLogin = "IsAutoRoleLogin";

	/**
	 * Set Approve own Documents.
	 * Users with this role can approve their own documents
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCanApproveOwnDoc (boolean IsCanApproveOwnDoc);

	/**
	 * Get Approve own Documents.
	 * Users with this role can approve their own documents
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCanApproveOwnDoc();

	ModelColumn<I_AD_Role, Object> COLUMN_IsCanApproveOwnDoc = new ModelColumn<>(I_AD_Role.class, "IsCanApproveOwnDoc", null);
	String COLUMNNAME_IsCanApproveOwnDoc = "IsCanApproveOwnDoc";

	/**
	 * Set Can Export.
	 * Users with this role can export data
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCanExport (boolean IsCanExport);

	/**
	 * Get Can Export.
	 * Users with this role can export data
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCanExport();

	ModelColumn<I_AD_Role, Object> COLUMN_IsCanExport = new ModelColumn<>(I_AD_Role.class, "IsCanExport", null);
	String COLUMNNAME_IsCanExport = "IsCanExport";

	/**
	 * Set Can Report.
	 * Users with this role can create reports
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCanReport (boolean IsCanReport);

	/**
	 * Get Can Report.
	 * Users with this role can create reports
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCanReport();

	ModelColumn<I_AD_Role, Object> COLUMN_IsCanReport = new ModelColumn<>(I_AD_Role.class, "IsCanReport", null);
	String COLUMNNAME_IsCanReport = "IsCanReport";

	/**
	 * Set Maintain Change Log.
	 * Maintain a log of changes
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsChangeLog (boolean IsChangeLog);

	/**
	 * Get Maintain Change Log.
	 * Maintain a log of changes
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isChangeLog();

	ModelColumn<I_AD_Role, Object> COLUMN_IsChangeLog = new ModelColumn<>(I_AD_Role.class, "IsChangeLog", null);
	String COLUMNNAME_IsChangeLog = "IsChangeLog";

	/**
	 * Set IsDiscountAllowedOnTotal.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDiscountAllowedOnTotal (boolean IsDiscountAllowedOnTotal);

	/**
	 * Get IsDiscountAllowedOnTotal.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDiscountAllowedOnTotal();

	ModelColumn<I_AD_Role, Object> COLUMN_IsDiscountAllowedOnTotal = new ModelColumn<>(I_AD_Role.class, "IsDiscountAllowedOnTotal", null);
	String COLUMNNAME_IsDiscountAllowedOnTotal = "IsDiscountAllowedOnTotal";

	/**
	 * Set IsDiscountUptoLimitPrice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDiscountUptoLimitPrice (boolean IsDiscountUptoLimitPrice);

	/**
	 * Get IsDiscountUptoLimitPrice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDiscountUptoLimitPrice();

	ModelColumn<I_AD_Role, Object> COLUMN_IsDiscountUptoLimitPrice = new ModelColumn<>(I_AD_Role.class, "IsDiscountUptoLimitPrice", null);
	String COLUMNNAME_IsDiscountUptoLimitPrice = "IsDiscountUptoLimitPrice";

	/**
	 * Set Invoice manually allocated.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManual (boolean IsManual);

	/**
	 * Get Invoice manually allocated.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManual();

	ModelColumn<I_AD_Role, Object> COLUMN_IsManual = new ModelColumn<>(I_AD_Role.class, "IsManual", null);
	String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Menu available.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMenuAvailable (boolean IsMenuAvailable);

	/**
	 * Get Menu available.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMenuAvailable();

	ModelColumn<I_AD_Role, Object> COLUMN_IsMenuAvailable = new ModelColumn<>(I_AD_Role.class, "IsMenuAvailable", null);
	String COLUMNNAME_IsMenuAvailable = "IsMenuAvailable";

	/**
	 * Set Personal Access.
	 * Allow access to all personal records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPersonalAccess (boolean IsPersonalAccess);

	/**
	 * Get Personal Access.
	 * Allow access to all personal records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPersonalAccess();

	ModelColumn<I_AD_Role, Object> COLUMN_IsPersonalAccess = new ModelColumn<>(I_AD_Role.class, "IsPersonalAccess", null);
	String COLUMNNAME_IsPersonalAccess = "IsPersonalAccess";

	/**
	 * Set Personal Lock.
	 * Allow users with role to lock access to personal records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPersonalLock (boolean IsPersonalLock);

	/**
	 * Get Personal Lock.
	 * Allow users with role to lock access to personal records
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPersonalLock();

	ModelColumn<I_AD_Role, Object> COLUMN_IsPersonalLock = new ModelColumn<>(I_AD_Role.class, "IsPersonalLock", null);
	String COLUMNNAME_IsPersonalLock = "IsPersonalLock";

	/**
	 * Set Use Beta Functionality override.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsRoleAlwaysUseBetaFunctions (boolean IsRoleAlwaysUseBetaFunctions);

	/**
	 * Get Use Beta Functionality override.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isRoleAlwaysUseBetaFunctions();

	ModelColumn<I_AD_Role, Object> COLUMN_IsRoleAlwaysUseBetaFunctions = new ModelColumn<>(I_AD_Role.class, "IsRoleAlwaysUseBetaFunctions", null);
	String COLUMNNAME_IsRoleAlwaysUseBetaFunctions = "IsRoleAlwaysUseBetaFunctions";

	/**
	 * Set Show Accounting.
	 * Users with this role can see accounting information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShowAcct (boolean IsShowAcct);

	/**
	 * Get Show Accounting.
	 * Users with this role can see accounting information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShowAcct();

	ModelColumn<I_AD_Role, Object> COLUMN_IsShowAcct = new ModelColumn<>(I_AD_Role.class, "IsShowAcct", null);
	String COLUMNNAME_IsShowAcct = "IsShowAcct";

	/**
	 * Set Show all entity types.
	 * Show all entity types, even if some of them were marked to not be shown.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShowAllEntityTypes (boolean IsShowAllEntityTypes);

	/**
	 * Get Show all entity types.
	 * Show all entity types, even if some of them were marked to not be shown.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShowAllEntityTypes();

	ModelColumn<I_AD_Role, Object> COLUMN_IsShowAllEntityTypes = new ModelColumn<>(I_AD_Role.class, "IsShowAllEntityTypes", null);
	String COLUMNNAME_IsShowAllEntityTypes = "IsShowAllEntityTypes";

	/**
	 * Set Use User Org Access.
	 * Use Org Access defined by user instead of Role Org Access
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseUserOrgAccess (boolean IsUseUserOrgAccess);

	/**
	 * Get Use User Org Access.
	 * Use Org Access defined by user instead of Role Org Access
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseUserOrgAccess();

	ModelColumn<I_AD_Role, Object> COLUMN_IsUseUserOrgAccess = new ModelColumn<>(I_AD_Role.class, "IsUseUserOrgAccess", null);
	String COLUMNNAME_IsUseUserOrgAccess = "IsUseUserOrgAccess";

	/**
	 * Set Max Query Records.
	 * If defined, you cannot query more records as defined - the query criteria needs to be changed to query less records
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMaxQueryRecords (int MaxQueryRecords);

	/**
	 * Get Max Query Records.
	 * If defined, you cannot query more records as defined - the query criteria needs to be changed to query less records
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMaxQueryRecords();

	ModelColumn<I_AD_Role, Object> COLUMN_MaxQueryRecords = new ModelColumn<>(I_AD_Role.class, "MaxQueryRecords", null);
	String COLUMNNAME_MaxQueryRecords = "MaxQueryRecords";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_AD_Role, Object> COLUMN_Name = new ModelColumn<>(I_AD_Role.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Overwrite Price Limit.
	 * Overwrite Price Limit if the Price List  enforces the Price Limit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwritePriceLimit (boolean OverwritePriceLimit);

	/**
	 * Get Overwrite Price Limit.
	 * Overwrite Price Limit if the Price List  enforces the Price Limit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwritePriceLimit();

	ModelColumn<I_AD_Role, Object> COLUMN_OverwritePriceLimit = new ModelColumn<>(I_AD_Role.class, "OverwritePriceLimit", null);
	String COLUMNNAME_OverwritePriceLimit = "OverwritePriceLimit";

	/**
	 * Set Preference Level.
	 * Determines what preferences the user can set
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPreferenceType (java.lang.String PreferenceType);

	/**
	 * Get Preference Level.
	 * Determines what preferences the user can set
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPreferenceType();

	ModelColumn<I_AD_Role, Object> COLUMN_PreferenceType = new ModelColumn<>(I_AD_Role.class, "PreferenceType", null);
	String COLUMNNAME_PreferenceType = "PreferenceType";

	/**
	 * Set Role group.
	 * Can be used to mark roles;
 the respective group is added to the logged user's CTX and can be used e.g. to determine if particular fields shall be read-only.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRole_Group (@Nullable java.lang.String Role_Group);

	/**
	 * Get Role group.
	 * Can be used to mark roles;
 the respective group is added to the logged user's CTX and can be used e.g. to determine if particular fields shall be read-only.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRole_Group();

	ModelColumn<I_AD_Role, Object> COLUMN_Role_Group = new ModelColumn<>(I_AD_Role.class, "Role_Group", null);
	String COLUMNNAME_Role_Group = "Role_Group";

	/**
	 * Set Root menu node.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRoot_Menu_ID (int Root_Menu_ID);

	/**
	 * Get Root menu node.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRoot_Menu_ID();

	@Nullable org.compiere.model.I_AD_Menu getRoot_Menu();

	void setRoot_Menu(@Nullable org.compiere.model.I_AD_Menu Root_Menu);

	ModelColumn<I_AD_Role, org.compiere.model.I_AD_Menu> COLUMN_Root_Menu_ID = new ModelColumn<>(I_AD_Role.class, "Root_Menu_ID", org.compiere.model.I_AD_Menu.class);
	String COLUMNNAME_Root_Menu_ID = "Root_Menu_ID";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_AD_Role, Object> COLUMN_SeqNo = new ModelColumn<>(I_AD_Role.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Supervisor.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupervisor_ID (int Supervisor_ID);

	/**
	 * Get Supervisor.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSupervisor_ID();

	String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Role, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Role.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set UserDiscount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserDiscount (@Nullable BigDecimal UserDiscount);

	/**
	 * Get UserDiscount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getUserDiscount();

	ModelColumn<I_AD_Role, Object> COLUMN_UserDiscount = new ModelColumn<>(I_AD_Role.class, "UserDiscount", null);
	String COLUMNNAME_UserDiscount = "UserDiscount";

	/**
	 * Set User Level.
	 * System Client Organization
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUserLevel (java.lang.String UserLevel);

	/**
	 * Get User Level.
	 * System Client Organization
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getUserLevel();

	ModelColumn<I_AD_Role, Object> COLUMN_UserLevel = new ModelColumn<>(I_AD_Role.class, "UserLevel", null);
	String COLUMNNAME_UserLevel = "UserLevel";

	/**
	 * Set Is webui role.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWEBUI_Role (boolean WEBUI_Role);

	/**
	 * Get Is webui role.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWEBUI_Role();

	ModelColumn<I_AD_Role, Object> COLUMN_WEBUI_Role = new ModelColumn<>(I_AD_Role.class, "WEBUI_Role", null);
	String COLUMNNAME_WEBUI_Role = "WEBUI_Role";
}
