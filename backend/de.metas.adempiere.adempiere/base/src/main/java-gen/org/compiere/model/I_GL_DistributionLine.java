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


/** Generated Interface for GL_DistributionLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_GL_DistributionLine 
{

    /** TableName=GL_DistributionLine */
    public static final String Table_Name = "GL_DistributionLine";

    /** AD_Table_ID=707 */
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

    /** Column definition for Account_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_Account_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "Account_ID", null);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_Client>(I_GL_DistributionLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_Org>(I_GL_DistributionLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_Org>(I_GL_DistributionLine.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Activity>(I_GL_DistributionLine.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_BPartner>(I_GL_DistributionLine.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Campaign>(I_GL_DistributionLine.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Location>(I_GL_DistributionLine.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Location>(I_GL_DistributionLine.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_LocTo_ID */
    public static final String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

	/**
	 * Set Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Projekt.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Project_ID();

	public org.compiere.model.I_C_Project getC_Project();

	public void setC_Project(org.compiere.model.I_C_Project C_Project);

    /** Column definition for C_Project_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_Project>(I_GL_DistributionLine.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_User>(I_GL_DistributionLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_SalesRegion>(I_GL_DistributionLine.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Hauptbuch - Aufteilung.
	 * General Ledger Distribution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGL_Distribution_ID (int GL_Distribution_ID);

	/**
	 * Get Hauptbuch - Aufteilung.
	 * General Ledger Distribution
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGL_Distribution_ID();

	public org.compiere.model.I_GL_Distribution getGL_Distribution();

	public void setGL_Distribution(org.compiere.model.I_GL_Distribution GL_Distribution);

    /** Column definition for GL_Distribution_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_GL_Distribution> COLUMN_GL_Distribution_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_GL_Distribution>(I_GL_DistributionLine.class, "GL_Distribution_ID", org.compiere.model.I_GL_Distribution.class);
    /** Column name GL_Distribution_ID */
    public static final String COLUMNNAME_GL_Distribution_ID = "GL_Distribution_ID";

	/**
	 * Set GL Distribution Line.
	 * General Ledger Distribution Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGL_DistributionLine_ID (int GL_DistributionLine_ID);

	/**
	 * Get GL Distribution Line.
	 * General Ledger Distribution Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGL_DistributionLine_ID();

    /** Column definition for GL_DistributionLine_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_GL_DistributionLine_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "GL_DistributionLine_ID", null);
    /** Column name GL_DistributionLine_ID */
    public static final String COLUMNNAME_GL_DistributionLine_ID = "GL_DistributionLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_M_Product>(I_GL_DistributionLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_Org> COLUMN_Org_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_Org>(I_GL_DistributionLine.class, "Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name Org_ID */
    public static final String COLUMNNAME_Org_ID = "Org_ID";

	/**
	 * Set Overwrite Account.
	 * Overwrite the account segment Account with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteAcct (boolean OverwriteAcct);

	/**
	 * Get Overwrite Account.
	 * Overwrite the account segment Account with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteAcct();

    /** Column definition for OverwriteAcct */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteAcct = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteAcct", null);
    /** Column name OverwriteAcct */
    public static final String COLUMNNAME_OverwriteAcct = "OverwriteAcct";

	/**
	 * Set Overwrite Activity.
	 * Overwrite the account segment Activity with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteActivity (boolean OverwriteActivity);

	/**
	 * Get Overwrite Activity.
	 * Overwrite the account segment Activity with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteActivity();

    /** Column definition for OverwriteActivity */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteActivity = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteActivity", null);
    /** Column name OverwriteActivity */
    public static final String COLUMNNAME_OverwriteActivity = "OverwriteActivity";

	/**
	 * Set Overwrite Bus.Partner.
	 * Overwrite the account segment Business Partner with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteBPartner (boolean OverwriteBPartner);

	/**
	 * Get Overwrite Bus.Partner.
	 * Overwrite the account segment Business Partner with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteBPartner();

    /** Column definition for OverwriteBPartner */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteBPartner = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteBPartner", null);
    /** Column name OverwriteBPartner */
    public static final String COLUMNNAME_OverwriteBPartner = "OverwriteBPartner";

	/**
	 * Set Overwrite Campaign.
	 * Overwrite the account segment Campaign with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteCampaign (boolean OverwriteCampaign);

	/**
	 * Get Overwrite Campaign.
	 * Overwrite the account segment Campaign with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteCampaign();

    /** Column definition for OverwriteCampaign */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteCampaign = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteCampaign", null);
    /** Column name OverwriteCampaign */
    public static final String COLUMNNAME_OverwriteCampaign = "OverwriteCampaign";

	/**
	 * Set Overwrite Location From.
	 * Overwrite the account segment Location From with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteLocFrom (boolean OverwriteLocFrom);

	/**
	 * Get Overwrite Location From.
	 * Overwrite the account segment Location From with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteLocFrom();

    /** Column definition for OverwriteLocFrom */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteLocFrom = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteLocFrom", null);
    /** Column name OverwriteLocFrom */
    public static final String COLUMNNAME_OverwriteLocFrom = "OverwriteLocFrom";

	/**
	 * Set Overwrite Location To.
	 * Overwrite the account segment Location From with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteLocTo (boolean OverwriteLocTo);

	/**
	 * Get Overwrite Location To.
	 * Overwrite the account segment Location From with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteLocTo();

    /** Column definition for OverwriteLocTo */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteLocTo = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteLocTo", null);
    /** Column name OverwriteLocTo */
    public static final String COLUMNNAME_OverwriteLocTo = "OverwriteLocTo";

	/**
	 * Set Overwrite Organization.
	 * Overwrite the account segment Organization with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteOrg (boolean OverwriteOrg);

	/**
	 * Get Overwrite Organization.
	 * Overwrite the account segment Organization with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteOrg();

    /** Column definition for OverwriteOrg */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteOrg = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteOrg", null);
    /** Column name OverwriteOrg */
    public static final String COLUMNNAME_OverwriteOrg = "OverwriteOrg";

	/**
	 * Set Overwrite Trx Organuzation.
	 * Overwrite the account segment Transaction Organization with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteOrgTrx (boolean OverwriteOrgTrx);

	/**
	 * Get Overwrite Trx Organuzation.
	 * Overwrite the account segment Transaction Organization with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteOrgTrx();

    /** Column definition for OverwriteOrgTrx */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteOrgTrx = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteOrgTrx", null);
    /** Column name OverwriteOrgTrx */
    public static final String COLUMNNAME_OverwriteOrgTrx = "OverwriteOrgTrx";

	/**
	 * Set Overwrite Product.
	 * Overwrite the account segment Product with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteProduct (boolean OverwriteProduct);

	/**
	 * Get Overwrite Product.
	 * Overwrite the account segment Product with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteProduct();

    /** Column definition for OverwriteProduct */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteProduct = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteProduct", null);
    /** Column name OverwriteProduct */
    public static final String COLUMNNAME_OverwriteProduct = "OverwriteProduct";

	/**
	 * Set Overwrite Project.
	 * Overwrite the account segment Project with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteProject (boolean OverwriteProject);

	/**
	 * Get Overwrite Project.
	 * Overwrite the account segment Project with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteProject();

    /** Column definition for OverwriteProject */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteProject = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteProject", null);
    /** Column name OverwriteProject */
    public static final String COLUMNNAME_OverwriteProject = "OverwriteProject";

	/**
	 * Set Overwrite Sales Region.
	 * Overwrite the account segment Sales Region with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteSalesRegion (boolean OverwriteSalesRegion);

	/**
	 * Get Overwrite Sales Region.
	 * Overwrite the account segment Sales Region with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteSalesRegion();

    /** Column definition for OverwriteSalesRegion */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteSalesRegion = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteSalesRegion", null);
    /** Column name OverwriteSalesRegion */
    public static final String COLUMNNAME_OverwriteSalesRegion = "OverwriteSalesRegion";

	/**
	 * Set Overwrite User1.
	 * Overwrite the account segment User 1 with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteUser1 (boolean OverwriteUser1);

	/**
	 * Get Overwrite User1.
	 * Overwrite the account segment User 1 with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteUser1();

    /** Column definition for OverwriteUser1 */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteUser1 = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteUser1", null);
    /** Column name OverwriteUser1 */
    public static final String COLUMNNAME_OverwriteUser1 = "OverwriteUser1";

	/**
	 * Set Overwrite User2.
	 * Overwrite the account segment User 2 with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setOverwriteUser2 (boolean OverwriteUser2);

	/**
	 * Get Overwrite User2.
	 * Overwrite the account segment User 2 with the value specified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteUser2();

    /** Column definition for OverwriteUser2 */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_OverwriteUser2 = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "OverwriteUser2", null);
    /** Column name OverwriteUser2 */
    public static final String COLUMNNAME_OverwriteUser2 = "OverwriteUser2";

	/**
	 * Set Percent.
	 * Percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPercent (java.math.BigDecimal Percent);

	/**
	 * Get Percent.
	 * Percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPercent();

    /** Column definition for Percent */
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_Percent = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "Percent", null);
    /** Column name Percent */
    public static final String COLUMNNAME_Percent = "Percent";

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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, Object>(I_GL_DistributionLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_AD_User>(I_GL_DistributionLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_ElementValue>(I_GL_DistributionLine.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_GL_DistributionLine, org.compiere.model.I_C_ElementValue>(I_GL_DistributionLine.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";
}
