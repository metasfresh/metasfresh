package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GL_DistributionLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GL_DistributionLine 
{

	String Table_Name = "GL_DistributionLine";

//	/** AD_Table_ID=707 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Account.
	 * Account used
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccount_ID (int Account_ID);

	/**
	 * Get Account.
	 * Account used
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAccount_ID();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_Account_ID = new ModelColumn<>(I_GL_DistributionLine.class, "Account_ID", null);
	String COLUMNNAME_Account_ID = "Account_ID";

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
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTrx_ID();

	String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_GL_DistributionLine.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LocFrom_ID (int C_LocFrom_ID);

	/**
	 * Get Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LocFrom_ID();

	@Nullable org.compiere.model.I_C_Location getC_LocFrom();

	void setC_LocFrom(@Nullable org.compiere.model.I_C_Location C_LocFrom);

	ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new ModelColumn<>(I_GL_DistributionLine.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_LocFrom_ID = "C_LocFrom_ID";

	/**
	 * Set Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LocTo_ID (int C_LocTo_ID);

	/**
	 * Get Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LocTo_ID();

	@Nullable org.compiere.model.I_C_Location getC_LocTo();

	void setC_LocTo(@Nullable org.compiere.model.I_C_Location C_LocTo);

	ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new ModelColumn<>(I_GL_DistributionLine.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_GL_DistributionLine.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Project en_US 208.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project en_US 208.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SalesRegion_ID();

	@Nullable org.compiere.model.I_C_SalesRegion getC_SalesRegion();

	void setC_SalesRegion(@Nullable org.compiere.model.I_C_SalesRegion C_SalesRegion);

	ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new ModelColumn<>(I_GL_DistributionLine.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
	String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_Created = new ModelColumn<>(I_GL_DistributionLine.class, "Created", null);
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

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_Description = new ModelColumn<>(I_GL_DistributionLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set GL Distribution.
	 * General Ledger Distribution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGL_Distribution_ID (int GL_Distribution_ID);

	/**
	 * Get GL Distribution.
	 * General Ledger Distribution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGL_Distribution_ID();

	org.compiere.model.I_GL_Distribution getGL_Distribution();

	void setGL_Distribution(org.compiere.model.I_GL_Distribution GL_Distribution);

	ModelColumn<I_GL_DistributionLine, org.compiere.model.I_GL_Distribution> COLUMN_GL_Distribution_ID = new ModelColumn<>(I_GL_DistributionLine.class, "GL_Distribution_ID", org.compiere.model.I_GL_Distribution.class);
	String COLUMNNAME_GL_Distribution_ID = "GL_Distribution_ID";

	/**
	 * Set GL Distribution Line.
	 * General Ledger Distribution Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGL_DistributionLine_ID (int GL_DistributionLine_ID);

	/**
	 * Get GL Distribution Line.
	 * General Ledger Distribution Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGL_DistributionLine_ID();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_GL_DistributionLine_ID = new ModelColumn<>(I_GL_DistributionLine.class, "GL_DistributionLine_ID", null);
	String COLUMNNAME_GL_DistributionLine_ID = "GL_DistributionLine_ID";

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

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_IsActive = new ModelColumn<>(I_GL_DistributionLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_Line = new ModelColumn<>(I_GL_DistributionLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_GL_DistributionLine, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_GL_DistributionLine.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Organisation.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrg_ID (int Org_ID);

	/**
	 * Get Organisation.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getOrg_ID();

	String COLUMNNAME_Org_ID = "Org_ID";

	/**
	 * Set Overwrite Account.
	 * Overwrite the account segment Account with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteAcct (boolean OverwriteAcct);

	/**
	 * Get Overwrite Account.
	 * Overwrite the account segment Account with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteAcct();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteAcct = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteAcct", null);
	String COLUMNNAME_OverwriteAcct = "OverwriteAcct";

	/**
	 * Set Overwrite Activity.
	 * Overwrite the account segment Activity with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteActivity (boolean OverwriteActivity);

	/**
	 * Get Overwrite Activity.
	 * Overwrite the account segment Activity with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteActivity();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteActivity = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteActivity", null);
	String COLUMNNAME_OverwriteActivity = "OverwriteActivity";

	/**
	 * Set Overwrite Bus.Partner.
	 * Overwrite the account segment Business Partner with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteBPartner (boolean OverwriteBPartner);

	/**
	 * Get Overwrite Bus.Partner.
	 * Overwrite the account segment Business Partner with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteBPartner();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteBPartner = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteBPartner", null);
	String COLUMNNAME_OverwriteBPartner = "OverwriteBPartner";

	/**
	 * Set Overwrite Campaign.
	 * Overwrite the account segment Campaign with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteCampaign (boolean OverwriteCampaign);

	/**
	 * Get Overwrite Campaign.
	 * Overwrite the account segment Campaign with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteCampaign();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteCampaign = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteCampaign", null);
	String COLUMNNAME_OverwriteCampaign = "OverwriteCampaign";

	/**
	 * Set Overwrite Location From.
	 * Overwrite the account segment Location From with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteLocFrom (boolean OverwriteLocFrom);

	/**
	 * Get Overwrite Location From.
	 * Overwrite the account segment Location From with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteLocFrom();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteLocFrom = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteLocFrom", null);
	String COLUMNNAME_OverwriteLocFrom = "OverwriteLocFrom";

	/**
	 * Set Overwrite Location To.
	 * Overwrite the account segment Location From with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteLocTo (boolean OverwriteLocTo);

	/**
	 * Get Overwrite Location To.
	 * Overwrite the account segment Location From with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteLocTo();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteLocTo = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteLocTo", null);
	String COLUMNNAME_OverwriteLocTo = "OverwriteLocTo";

	/**
	 * Set Overwrite Order.
	 * Overwrite the account segment Order with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteOrder (boolean OverwriteOrder);

	/**
	 * Get Overwrite Order.
	 * Overwrite the account segment Order with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteOrder();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteOrder = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteOrder", null);
	String COLUMNNAME_OverwriteOrder = "OverwriteOrder";

	/**
	 * Set Overwrite Organization.
	 * Overwrite the account segment Organization with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteOrg (boolean OverwriteOrg);

	/**
	 * Get Overwrite Organization.
	 * Overwrite the account segment Organization with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteOrg();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteOrg = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteOrg", null);
	String COLUMNNAME_OverwriteOrg = "OverwriteOrg";

	/**
	 * Set Overwrite Trx Organuzation.
	 * Overwrite the account segment Transaction Organization with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteOrgTrx (boolean OverwriteOrgTrx);

	/**
	 * Get Overwrite Trx Organuzation.
	 * Overwrite the account segment Transaction Organization with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteOrgTrx();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteOrgTrx = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteOrgTrx", null);
	String COLUMNNAME_OverwriteOrgTrx = "OverwriteOrgTrx";

	/**
	 * Set Overwrite Product.
	 * Overwrite the account segment Product with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteProduct (boolean OverwriteProduct);

	/**
	 * Get Overwrite Product.
	 * Overwrite the account segment Product with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteProduct();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteProduct = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteProduct", null);
	String COLUMNNAME_OverwriteProduct = "OverwriteProduct";

	/**
	 * Set Overwrite Project.
	 * Overwrite the account segment Project with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteProject (boolean OverwriteProject);

	/**
	 * Get Overwrite Project.
	 * Overwrite the account segment Project with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteProject();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteProject = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteProject", null);
	String COLUMNNAME_OverwriteProject = "OverwriteProject";

	/**
	 * Set Overwrite Sales Region.
	 * Overwrite the account segment Sales Region with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteSalesRegion (boolean OverwriteSalesRegion);

	/**
	 * Get Overwrite Sales Region.
	 * Overwrite the account segment Sales Region with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteSalesRegion();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteSalesRegion = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteSalesRegion", null);
	String COLUMNNAME_OverwriteSalesRegion = "OverwriteSalesRegion";

	/**
	 * Set Overwrite Section Code.
	 * Overwrite the account segment Sectoin Coder with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteSectionCode (boolean OverwriteSectionCode);

	/**
	 * Get Overwrite Section Code.
	 * Overwrite the account segment Sectoin Coder with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteSectionCode();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteSectionCode = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteSectionCode", null);
	String COLUMNNAME_OverwriteSectionCode = "OverwriteSectionCode";

	/**
	 * Set Overwrite User1.
	 * Overwrite the account segment User 1 with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteUser1 (boolean OverwriteUser1);

	/**
	 * Get Overwrite User1.
	 * Overwrite the account segment User 1 with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteUser1();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteUser1 = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteUser1", null);
	String COLUMNNAME_OverwriteUser1 = "OverwriteUser1";

	/**
	 * Set Overwrite User2.
	 * Overwrite the account segment User 2 with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOverwriteUser2 (boolean OverwriteUser2);

	/**
	 * Get Overwrite User2.
	 * Overwrite the account segment User 2 with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOverwriteUser2();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteUser2 = new ModelColumn<>(I_GL_DistributionLine.class, "OverwriteUser2", null);
	String COLUMNNAME_OverwriteUser2 = "OverwriteUser2";

	/**
	 * Set Percent.
	 * Percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPercent (BigDecimal Percent);

	/**
	 * Get Percent.
	 * Percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPercent();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_Percent = new ModelColumn<>(I_GL_DistributionLine.class, "Percent", null);
	String COLUMNNAME_Percent = "Percent";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_GL_DistributionLine, Object> COLUMN_Updated = new ModelColumn<>(I_GL_DistributionLine.class, "Updated", null);
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
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	@Nullable org.compiere.model.I_C_ElementValue getUser1();

	void setUser1(@Nullable org.compiere.model.I_C_ElementValue User1);

	ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_GL_DistributionLine.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	@Nullable org.compiere.model.I_C_ElementValue getUser2();

	void setUser2(@Nullable org.compiere.model.I_C_ElementValue User2);

	ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_GL_DistributionLine.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User2_ID = "User2_ID";
}
