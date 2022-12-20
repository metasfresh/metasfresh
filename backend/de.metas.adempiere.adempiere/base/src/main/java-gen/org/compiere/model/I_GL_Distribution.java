package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GL_Distribution
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GL_Distribution 
{

	String Table_Name = "GL_Distribution";

//	/** AD_Table_ID=708 */
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

	@Nullable org.compiere.model.I_C_ElementValue getAccount();

	void setAccount(@Nullable org.compiere.model.I_C_ElementValue Account);

	ModelColumn<I_GL_Distribution, org.compiere.model.I_C_ElementValue> COLUMN_Account_ID = new ModelColumn<>(I_GL_Distribution.class, "Account_ID", org.compiere.model.I_C_ElementValue.class);
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
	 * Set Any Account.
	 * Match any value of the Account segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyAcct (boolean AnyAcct);

	/**
	 * Get Any Account.
	 * Match any value of the Account segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyAcct();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyAcct = new ModelColumn<>(I_GL_Distribution.class, "AnyAcct", null);
	String COLUMNNAME_AnyAcct = "AnyAcct";

	/**
	 * Set Any Activity.
	 * Match any value of the Activity segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyActivity (boolean AnyActivity);

	/**
	 * Get Any Activity.
	 * Match any value of the Activity segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyActivity();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyActivity = new ModelColumn<>(I_GL_Distribution.class, "AnyActivity", null);
	String COLUMNNAME_AnyActivity = "AnyActivity";

	/**
	 * Set Any Bus.Partner.
	 * Match any value of the Business Partner segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyBPartner (boolean AnyBPartner);

	/**
	 * Get Any Bus.Partner.
	 * Match any value of the Business Partner segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyBPartner();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyBPartner = new ModelColumn<>(I_GL_Distribution.class, "AnyBPartner", null);
	String COLUMNNAME_AnyBPartner = "AnyBPartner";

	/**
	 * Set Any Campaign.
	 * Match any value of the Campaign segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyCampaign (boolean AnyCampaign);

	/**
	 * Get Any Campaign.
	 * Match any value of the Campaign segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyCampaign();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyCampaign = new ModelColumn<>(I_GL_Distribution.class, "AnyCampaign", null);
	String COLUMNNAME_AnyCampaign = "AnyCampaign";

	/**
	 * Set Any Location From.
	 * Match any value of the Location From segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyLocFrom (boolean AnyLocFrom);

	/**
	 * Get Any Location From.
	 * Match any value of the Location From segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyLocFrom();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyLocFrom = new ModelColumn<>(I_GL_Distribution.class, "AnyLocFrom", null);
	String COLUMNNAME_AnyLocFrom = "AnyLocFrom";

	/**
	 * Set Any Location To.
	 * Match any value of the Location To segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyLocTo (boolean AnyLocTo);

	/**
	 * Get Any Location To.
	 * Match any value of the Location To segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyLocTo();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyLocTo = new ModelColumn<>(I_GL_Distribution.class, "AnyLocTo", null);
	String COLUMNNAME_AnyLocTo = "AnyLocTo";

	/**
	 * Set Any Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyOrder (boolean AnyOrder);

	/**
	 * Get Any Order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyOrder();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyOrder = new ModelColumn<>(I_GL_Distribution.class, "AnyOrder", null);
	String COLUMNNAME_AnyOrder = "AnyOrder";

	/**
	 * Set Any Organization.
	 * Match any value of the Organization segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyOrg (boolean AnyOrg);

	/**
	 * Get Any Organization.
	 * Match any value of the Organization segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyOrg();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyOrg = new ModelColumn<>(I_GL_Distribution.class, "AnyOrg", null);
	String COLUMNNAME_AnyOrg = "AnyOrg";

	/**
	 * Set Any Trx Organization.
	 * Match any value of the Transaction Organization segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyOrgTrx (boolean AnyOrgTrx);

	/**
	 * Get Any Trx Organization.
	 * Match any value of the Transaction Organization segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyOrgTrx();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyOrgTrx = new ModelColumn<>(I_GL_Distribution.class, "AnyOrgTrx", null);
	String COLUMNNAME_AnyOrgTrx = "AnyOrgTrx";

	/**
	 * Set Any Product.
	 * Match any value of the Product segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyProduct (boolean AnyProduct);

	/**
	 * Get Any Product.
	 * Match any value of the Product segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyProduct();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyProduct = new ModelColumn<>(I_GL_Distribution.class, "AnyProduct", null);
	String COLUMNNAME_AnyProduct = "AnyProduct";

	/**
	 * Set Any Project.
	 * Match any value of the Project segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyProject (boolean AnyProject);

	/**
	 * Get Any Project.
	 * Match any value of the Project segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyProject();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyProject = new ModelColumn<>(I_GL_Distribution.class, "AnyProject", null);
	String COLUMNNAME_AnyProject = "AnyProject";

	/**
	 * Set Any Sales Region.
	 * Match any value of the Sales Region segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnySalesRegion (boolean AnySalesRegion);

	/**
	 * Get Any Sales Region.
	 * Match any value of the Sales Region segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnySalesRegion();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnySalesRegion = new ModelColumn<>(I_GL_Distribution.class, "AnySalesRegion", null);
	String COLUMNNAME_AnySalesRegion = "AnySalesRegion";

	/**
	 * Set Any Section Code.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnySectionCode (boolean AnySectionCode);

	/**
	 * Get Any Section Code.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnySectionCode();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnySectionCode = new ModelColumn<>(I_GL_Distribution.class, "AnySectionCode", null);
	String COLUMNNAME_AnySectionCode = "AnySectionCode";

	/**
	 * Set Any User 1.
	 * Match any value of the User 1 segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyUser1 (boolean AnyUser1);

	/**
	 * Get Any User 1.
	 * Match any value of the User 1 segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyUser1();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyUser1 = new ModelColumn<>(I_GL_Distribution.class, "AnyUser1", null);
	String COLUMNNAME_AnyUser1 = "AnyUser1";

	/**
	 * Set Any User 2.
	 * Match any value of the User 2 segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAnyUser2 (boolean AnyUser2);

	/**
	 * Get Any User 2.
	 * Match any value of the User 2 segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAnyUser2();

	ModelColumn<I_GL_Distribution, Object> COLUMN_AnyUser2 = new ModelColumn<>(I_GL_Distribution.class, "AnyUser2", null);
	String COLUMNNAME_AnyUser2 = "AnyUser2";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_GL_Distribution, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_GL_Distribution.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

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

	ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_GL_Distribution.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

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

	ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new ModelColumn<>(I_GL_Distribution.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
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

	ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new ModelColumn<>(I_GL_Distribution.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
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

	ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_GL_Distribution.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Project en_US 208.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project en_US 208.
	 * Financial Project
	 *
	 * <br>Type: Search
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

	ModelColumn<I_GL_Distribution, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new ModelColumn<>(I_GL_Distribution.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
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

	ModelColumn<I_GL_Distribution, Object> COLUMN_Created = new ModelColumn<>(I_GL_Distribution.class, "Created", null);
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

	ModelColumn<I_GL_Distribution, Object> COLUMN_Description = new ModelColumn<>(I_GL_Distribution.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set GL Distribution.
	 * General Ledger Distribution
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGL_Distribution_ID (int GL_Distribution_ID);

	/**
	 * Get GL Distribution.
	 * General Ledger Distribution
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGL_Distribution_ID();

	ModelColumn<I_GL_Distribution, Object> COLUMN_GL_Distribution_ID = new ModelColumn<>(I_GL_Distribution.class, "GL_Distribution_ID", null);
	String COLUMNNAME_GL_Distribution_ID = "GL_Distribution_ID";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_GL_Distribution, Object> COLUMN_Help = new ModelColumn<>(I_GL_Distribution.class, "Help", null);
	String COLUMNNAME_Help = "Help";

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

	ModelColumn<I_GL_Distribution, Object> COLUMN_IsActive = new ModelColumn<>(I_GL_Distribution.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Create Reversal.
	 * Indicates that reversal movement will be created, if disabled the original movement will be deleted.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreateReversal (boolean IsCreateReversal);

	/**
	 * Get Create Reversal.
	 * Indicates that reversal movement will be created, if disabled the original movement will be deleted.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreateReversal();

	ModelColumn<I_GL_Distribution, Object> COLUMN_IsCreateReversal = new ModelColumn<>(I_GL_Distribution.class, "IsCreateReversal", null);
	String COLUMNNAME_IsCreateReversal = "IsCreateReversal";

	/**
	 * Set Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsValid (boolean IsValid);

	/**
	 * Get Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isValid();

	ModelColumn<I_GL_Distribution, Object> COLUMN_IsValid = new ModelColumn<>(I_GL_Distribution.class, "IsValid", null);
	String COLUMNNAME_IsValid = "IsValid";

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

	ModelColumn<I_GL_Distribution, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_GL_Distribution.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

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

	ModelColumn<I_GL_Distribution, Object> COLUMN_Name = new ModelColumn<>(I_GL_Distribution.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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
	 * Set Total Percent.
	 * Sum of the Percent details
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPercentTotal (BigDecimal PercentTotal);

	/**
	 * Get Total Percent.
	 * Sum of the Percent details
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPercentTotal();

	ModelColumn<I_GL_Distribution, Object> COLUMN_PercentTotal = new ModelColumn<>(I_GL_Distribution.class, "PercentTotal", null);
	String COLUMNNAME_PercentTotal = "PercentTotal";

	/**
	 * Set Posting Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostingType (@Nullable java.lang.String PostingType);

	/**
	 * Get Posting Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostingType();

	ModelColumn<I_GL_Distribution, Object> COLUMN_PostingType = new ModelColumn<>(I_GL_Distribution.class, "PostingType", null);
	String COLUMNNAME_PostingType = "PostingType";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_GL_Distribution, Object> COLUMN_Processing = new ModelColumn<>(I_GL_Distribution.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_GL_Distribution, Object> COLUMN_Updated = new ModelColumn<>(I_GL_Distribution.class, "Updated", null);
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

	ModelColumn<I_GL_Distribution, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_GL_Distribution.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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

	ModelColumn<I_GL_Distribution, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_GL_Distribution.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User2_ID = "User2_ID";
}
