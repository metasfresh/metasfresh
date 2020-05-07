/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;


/** Generated Interface for GL_Distribution
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_GL_Distribution 
{

    /** TableName=GL_Distribution */
    public static final String Table_Name = "GL_Distribution";

    /** AD_Table_ID=708 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Set Konto.
	 * Account used
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccount_ID (int Account_ID);

	/**
	 * Get Konto.
	 * Account used
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAccount_ID();

	public org.compiere.model.I_C_ElementValue getAccount();

	public void setAccount(org.compiere.model.I_C_ElementValue Account);

    /** Column definition for Account_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_ElementValue> COLUMN_Account_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_ElementValue>(I_GL_Distribution.class, "Account_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name Account_ID */
    public static final String COLUMNNAME_Account_ID = "Account_ID";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_Client>(I_GL_Distribution.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_Org>(I_GL_Distribution.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgTrx_ID();

	public org.compiere.model.I_AD_Org getAD_OrgTrx();

	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx);

    /** Column definition for AD_OrgTrx_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_Org>(I_GL_Distribution.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Any Account.
	 * Match any value of the Account segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyAcct (boolean AnyAcct);

	/**
	 * Get Any Account.
	 * Match any value of the Account segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyAcct();

    /** Column definition for AnyAcct */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyAcct = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyAcct", null);
    /** Column name AnyAcct */
    public static final String COLUMNNAME_AnyAcct = "AnyAcct";

	/**
	 * Set Any Activity.
	 * Match any value of the Activity segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyActivity (boolean AnyActivity);

	/**
	 * Get Any Activity.
	 * Match any value of the Activity segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyActivity();

    /** Column definition for AnyActivity */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyActivity = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyActivity", null);
    /** Column name AnyActivity */
    public static final String COLUMNNAME_AnyActivity = "AnyActivity";

	/**
	 * Set Any Bus.Partner.
	 * Match any value of the Business Partner segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyBPartner (boolean AnyBPartner);

	/**
	 * Get Any Bus.Partner.
	 * Match any value of the Business Partner segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyBPartner();

    /** Column definition for AnyBPartner */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyBPartner = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyBPartner", null);
    /** Column name AnyBPartner */
    public static final String COLUMNNAME_AnyBPartner = "AnyBPartner";

	/**
	 * Set Any Campaign.
	 * Match any value of the Campaign segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyCampaign (boolean AnyCampaign);

	/**
	 * Get Any Campaign.
	 * Match any value of the Campaign segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyCampaign();

    /** Column definition for AnyCampaign */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyCampaign = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyCampaign", null);
    /** Column name AnyCampaign */
    public static final String COLUMNNAME_AnyCampaign = "AnyCampaign";

	/**
	 * Set Any Location From.
	 * Match any value of the Location From segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyLocFrom (boolean AnyLocFrom);

	/**
	 * Get Any Location From.
	 * Match any value of the Location From segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyLocFrom();

    /** Column definition for AnyLocFrom */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyLocFrom = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyLocFrom", null);
    /** Column name AnyLocFrom */
    public static final String COLUMNNAME_AnyLocFrom = "AnyLocFrom";

	/**
	 * Set Any Location To.
	 * Match any value of the Location To segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyLocTo (boolean AnyLocTo);

	/**
	 * Get Any Location To.
	 * Match any value of the Location To segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyLocTo();

    /** Column definition for AnyLocTo */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyLocTo = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyLocTo", null);
    /** Column name AnyLocTo */
    public static final String COLUMNNAME_AnyLocTo = "AnyLocTo";

	/**
	 * Set Any Organization.
	 * Match any value of the Organization segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyOrg (boolean AnyOrg);

	/**
	 * Get Any Organization.
	 * Match any value of the Organization segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyOrg();

    /** Column definition for AnyOrg */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyOrg = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyOrg", null);
    /** Column name AnyOrg */
    public static final String COLUMNNAME_AnyOrg = "AnyOrg";

	/**
	 * Set Any Trx Organization.
	 * Match any value of the Transaction Organization segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyOrgTrx (boolean AnyOrgTrx);

	/**
	 * Get Any Trx Organization.
	 * Match any value of the Transaction Organization segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyOrgTrx();

    /** Column definition for AnyOrgTrx */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyOrgTrx = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyOrgTrx", null);
    /** Column name AnyOrgTrx */
    public static final String COLUMNNAME_AnyOrgTrx = "AnyOrgTrx";

	/**
	 * Set Any Product.
	 * Match any value of the Product segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyProduct (boolean AnyProduct);

	/**
	 * Get Any Product.
	 * Match any value of the Product segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyProduct();

    /** Column definition for AnyProduct */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyProduct = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyProduct", null);
    /** Column name AnyProduct */
    public static final String COLUMNNAME_AnyProduct = "AnyProduct";

	/**
	 * Set Any Project.
	 * Match any value of the Project segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyProject (boolean AnyProject);

	/**
	 * Get Any Project.
	 * Match any value of the Project segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyProject();

    /** Column definition for AnyProject */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyProject = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyProject", null);
    /** Column name AnyProject */
    public static final String COLUMNNAME_AnyProject = "AnyProject";

	/**
	 * Set Any Sales Region.
	 * Match any value of the Sales Region segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnySalesRegion (boolean AnySalesRegion);

	/**
	 * Get Any Sales Region.
	 * Match any value of the Sales Region segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnySalesRegion();

    /** Column definition for AnySalesRegion */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnySalesRegion = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnySalesRegion", null);
    /** Column name AnySalesRegion */
    public static final String COLUMNNAME_AnySalesRegion = "AnySalesRegion";

	/**
	 * Set Any User 1.
	 * Match any value of the User 1 segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyUser1 (boolean AnyUser1);

	/**
	 * Get Any User 1.
	 * Match any value of the User 1 segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyUser1();

    /** Column definition for AnyUser1 */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyUser1 = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyUser1", null);
    /** Column name AnyUser1 */
    public static final String COLUMNNAME_AnyUser1 = "AnyUser1";

	/**
	 * Set Any User 2.
	 * Match any value of the User 2 segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAnyUser2 (boolean AnyUser2);

	/**
	 * Get Any User 2.
	 * Match any value of the User 2 segment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAnyUser2();

    /** Column definition for AnyUser2 */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_AnyUser2 = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "AnyUser2", null);
    /** Column name AnyUser2 */
    public static final String COLUMNNAME_AnyUser2 = "AnyUser2";

	/**
	 * Set Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_AcctSchema>(I_GL_Distribution.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity();

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Activity>(I_GL_Distribution.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_BPartner>(I_GL_Distribution.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Werbemassnahme.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Campaign_ID();

	public org.compiere.model.I_C_Campaign getC_Campaign();

	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign);

    /** Column definition for C_Campaign_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Campaign>(I_GL_Distribution.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_DocType>(I_GL_Distribution.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Von Ort.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_LocFrom_ID (int C_LocFrom_ID);

	/**
	 * Get Von Ort.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_LocFrom_ID();

	public org.compiere.model.I_C_Location getC_LocFrom();

	public void setC_LocFrom(org.compiere.model.I_C_Location C_LocFrom);

    /** Column definition for C_LocFrom_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Location>(I_GL_Distribution.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_LocFrom_ID */
    public static final String COLUMNNAME_C_LocFrom_ID = "C_LocFrom_ID";

	/**
	 * Set Nach Ort.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_LocTo_ID (int C_LocTo_ID);

	/**
	 * Get Nach Ort.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_LocTo_ID();

	public org.compiere.model.I_C_Location getC_LocTo();

	public void setC_LocTo(org.compiere.model.I_C_Location C_LocTo);

    /** Column definition for C_LocTo_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Location>(I_GL_Distribution.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_LocTo_ID */
    public static final String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

	/**
	 * Set Projekt.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Projekt.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project();

	public void setC_Project(org.compiere.model.I_C_Project C_Project);

    /** Column definition for C_Project_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_Project>(I_GL_Distribution.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_User>(I_GL_Distribution.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Vertriebsgebiet.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Vertriebsgebiet.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_SalesRegion_ID();

	public org.compiere.model.I_C_SalesRegion getC_SalesRegion();

	public void setC_SalesRegion(org.compiere.model.I_C_SalesRegion C_SalesRegion);

    /** Column definition for C_SalesRegion_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_SalesRegion>(I_GL_Distribution.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
    /** Column name C_SalesRegion_ID */
    public static final String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Hauptbuch - Aufteilung.
	 * General Ledger Distribution
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGL_Distribution_ID (int GL_Distribution_ID);

	/**
	 * Get Hauptbuch - Aufteilung.
	 * General Ledger Distribution
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGL_Distribution_ID();

    /** Column definition for GL_Distribution_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_GL_Distribution_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "GL_Distribution_ID", null);
    /** Column name GL_Distribution_ID */
    public static final String COLUMNNAME_GL_Distribution_ID = "GL_Distribution_ID";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Create Reversal.
	 * Indicates that reversal movement will be created, if disabled the original movement will be deleted.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCreateReversal (boolean IsCreateReversal);

	/**
	 * Get Create Reversal.
	 * Indicates that reversal movement will be created, if disabled the original movement will be deleted.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCreateReversal();

    /** Column definition for IsCreateReversal */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_IsCreateReversal = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "IsCreateReversal", null);
    /** Column name IsCreateReversal */
    public static final String COLUMNNAME_IsCreateReversal = "IsCreateReversal";

	/**
	 * Set Gültig.
	 * Element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsValid (boolean IsValid);

	/**
	 * Get Gültig.
	 * Element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isValid();

    /** Column definition for IsValid */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_IsValid = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "IsValid", null);
    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_M_Product>(I_GL_Distribution.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Organisation.
	 * Organizational entity within client
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrg_ID (int Org_ID);

	/**
	 * Get Organisation.
	 * Organizational entity within client
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOrg_ID();

	public org.compiere.model.I_AD_Org getOrg();

	public void setOrg(org.compiere.model.I_AD_Org Org);

    /** Column definition for Org_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_Org> COLUMN_Org_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_Org>(I_GL_Distribution.class, "Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name Org_ID */
    public static final String COLUMNNAME_Org_ID = "Org_ID";

	/**
	 * Set Total Percent.
	 * Sum of the Percent details
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPercentTotal (java.math.BigDecimal PercentTotal);

	/**
	 * Get Total Percent.
	 * Sum of the Percent details
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPercentTotal();

    /** Column definition for PercentTotal */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_PercentTotal = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "PercentTotal", null);
    /** Column name PercentTotal */
    public static final String COLUMNNAME_PercentTotal = "PercentTotal";

	/**
	 * Set Buchungsart.
	 * The type of posted amount for the transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostingType (java.lang.String PostingType);

	/**
	 * Get Buchungsart.
	 * The type of posted amount for the transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostingType();

    /** Column definition for PostingType */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_PostingType = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "PostingType", null);
    /** Column name PostingType */
    public static final String COLUMNNAME_PostingType = "PostingType";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_GL_Distribution, Object>(I_GL_Distribution.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_AD_User>(I_GL_Distribution.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser1_ID (int User1_ID);

	/**
	 * Get Nutzer 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser1_ID();

	public org.compiere.model.I_C_ElementValue getUser1();

	public void setUser1(org.compiere.model.I_C_ElementValue User1);

    /** Column definition for User1_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_ElementValue>(I_GL_Distribution.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User1_ID */
    public static final String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUser2_ID (int User2_ID);

	/**
	 * Get Nutzer 2.
	 * User defined list element #2
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUser2_ID();

	public org.compiere.model.I_C_ElementValue getUser2();

	public void setUser2(org.compiere.model.I_C_ElementValue User2);

    /** Column definition for User2_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_GL_Distribution, org.compiere.model.I_C_ElementValue>(I_GL_Distribution.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";
}
