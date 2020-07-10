package org.compiere.model;


/** Generated Interface for AD_ClientInfo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_ClientInfo 
{

    /** TableName=AD_ClientInfo */
    public static final String Table_Name = "AD_ClientInfo";

    /** AD_Table_ID=227 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

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
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Client>(I_AD_ClientInfo.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Org>(I_AD_ClientInfo.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Baum Aktivität.
	 * Tree to determine activity hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_Activity_ID (int AD_Tree_Activity_ID);

	/**
	 * Get Baum Aktivität.
	 * Tree to determine activity hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_Activity_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_Activity();

	public void setAD_Tree_Activity(org.compiere.model.I_AD_Tree AD_Tree_Activity);

    /** Column definition for AD_Tree_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Activity_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree>(I_AD_ClientInfo.class, "AD_Tree_Activity_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_Activity_ID */
    public static final String COLUMNNAME_AD_Tree_Activity_ID = "AD_Tree_Activity_ID";

	/**
	 * Set Primärbaum Geschäftspartner.
	 * Tree to determine business partner hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_BPartner_ID (int AD_Tree_BPartner_ID);

	/**
	 * Get Primärbaum Geschäftspartner.
	 * Tree to determine business partner hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_BPartner_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_BPartner();

	public void setAD_Tree_BPartner(org.compiere.model.I_AD_Tree AD_Tree_BPartner);

    /** Column definition for AD_Tree_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_BPartner_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree>(I_AD_ClientInfo.class, "AD_Tree_BPartner_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_BPartner_ID */
    public static final String COLUMNNAME_AD_Tree_BPartner_ID = "AD_Tree_BPartner_ID";

	/**
	 * Set Baum Kampagne.
	 * Tree to determine marketing campaign hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_Campaign_ID (int AD_Tree_Campaign_ID);

	/**
	 * Get Baum Kampagne.
	 * Tree to determine marketing campaign hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_Campaign_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_Campaign();

	public void setAD_Tree_Campaign(org.compiere.model.I_AD_Tree AD_Tree_Campaign);

    /** Column definition for AD_Tree_Campaign_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Campaign_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree>(I_AD_ClientInfo.class, "AD_Tree_Campaign_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_Campaign_ID */
    public static final String COLUMNNAME_AD_Tree_Campaign_ID = "AD_Tree_Campaign_ID";

	/**
	 * Set Primärbaum Menü.
	 * Tree of the menu
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_Menu_ID (int AD_Tree_Menu_ID);

	/**
	 * Get Primärbaum Menü.
	 * Tree of the menu
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_Menu_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_Menu();

	public void setAD_Tree_Menu(org.compiere.model.I_AD_Tree AD_Tree_Menu);

    /** Column definition for AD_Tree_Menu_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Menu_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree>(I_AD_ClientInfo.class, "AD_Tree_Menu_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_Menu_ID */
    public static final String COLUMNNAME_AD_Tree_Menu_ID = "AD_Tree_Menu_ID";

	/**
	 * Set Primärbaum Organisation.
	 * Tree to determine organizational hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_Org_ID (int AD_Tree_Org_ID);

	/**
	 * Get Primärbaum Organisation.
	 * Tree to determine organizational hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_Org_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_Org();

	public void setAD_Tree_Org(org.compiere.model.I_AD_Tree AD_Tree_Org);

    /** Column definition for AD_Tree_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Org_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree>(I_AD_ClientInfo.class, "AD_Tree_Org_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_Org_ID */
    public static final String COLUMNNAME_AD_Tree_Org_ID = "AD_Tree_Org_ID";

	/**
	 * Set Primärbaum Produkt.
	 * Tree to determine product hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_Product_ID (int AD_Tree_Product_ID);

	/**
	 * Get Primärbaum Produkt.
	 * Tree to determine product hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_Product_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_Product();

	public void setAD_Tree_Product(org.compiere.model.I_AD_Tree AD_Tree_Product);

    /** Column definition for AD_Tree_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Product_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree>(I_AD_ClientInfo.class, "AD_Tree_Product_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_Product_ID */
    public static final String COLUMNNAME_AD_Tree_Product_ID = "AD_Tree_Product_ID";

	/**
	 * Set Primärbaum Projekt.
	 * Tree to determine project hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_Project_ID (int AD_Tree_Project_ID);

	/**
	 * Get Primärbaum Projekt.
	 * Tree to determine project hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_Project_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_Project();

	public void setAD_Tree_Project(org.compiere.model.I_AD_Tree AD_Tree_Project);

    /** Column definition for AD_Tree_Project_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_Project_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree>(I_AD_ClientInfo.class, "AD_Tree_Project_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_Project_ID */
    public static final String COLUMNNAME_AD_Tree_Project_ID = "AD_Tree_Project_ID";

	/**
	 * Set Primärbaum Vertriebsgebiet.
	 * Tree to determine sales regional hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tree_SalesRegion_ID (int AD_Tree_SalesRegion_ID);

	/**
	 * Get Primärbaum Vertriebsgebiet.
	 * Tree to determine sales regional hierarchy
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tree_SalesRegion_ID();

	public org.compiere.model.I_AD_Tree getAD_Tree_SalesRegion();

	public void setAD_Tree_SalesRegion(org.compiere.model.I_AD_Tree AD_Tree_SalesRegion);

    /** Column definition for AD_Tree_SalesRegion_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_SalesRegion_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Tree>(I_AD_ClientInfo.class, "AD_Tree_SalesRegion_ID", org.compiere.model.I_AD_Tree.class);
    /** Column name AD_Tree_SalesRegion_ID */
    public static final String COLUMNNAME_AD_Tree_SalesRegion_ID = "AD_Tree_SalesRegion_ID";

	/**
	 * Set Primäres Buchführungsschema.
	 * Primary rules for accounting
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema1_ID (int C_AcctSchema1_ID);

	/**
	 * Get Primäres Buchführungsschema.
	 * Primary rules for accounting
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema1_ID();

//	public org.compiere.model.I_C_AcctSchema getC_AcctSchema1();
//
//	public void setC_AcctSchema1(org.compiere.model.I_C_AcctSchema C_AcctSchema1);

    /** Column definition for C_AcctSchema1_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema1_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_AcctSchema>(I_AD_ClientInfo.class, "C_AcctSchema1_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema1_ID */
    public static final String COLUMNNAME_C_AcctSchema1_ID = "C_AcctSchema1_ID";

	/**
	 * Set Vorlage Geschäftspartner.
	 * Business Partner used for creating new Business Partners on the fly
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartnerCashTrx_ID (int C_BPartnerCashTrx_ID);

	/**
	 * Get Vorlage Geschäftspartner.
	 * Business Partner used for creating new Business Partners on the fly
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartnerCashTrx_ID();

	public org.compiere.model.I_C_BPartner getC_BPartnerCashTrx();

	public void setC_BPartnerCashTrx(org.compiere.model.I_C_BPartner C_BPartnerCashTrx);

    /** Column definition for C_BPartnerCashTrx_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_BPartner> COLUMN_C_BPartnerCashTrx_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_BPartner>(I_AD_ClientInfo.class, "C_BPartnerCashTrx_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartnerCashTrx_ID */
    public static final String COLUMNNAME_C_BPartnerCashTrx_ID = "C_BPartnerCashTrx_ID";

	/**
	 * Set Kalender.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Kalender.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Calendar_ID();

	public org.compiere.model.I_C_Calendar getC_Calendar();

	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar);

    /** Column definition for C_Calendar_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_Calendar>(I_AD_ClientInfo.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
    /** Column name C_Calendar_ID */
    public static final String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Set Maßeinheit für Länge.
	 * Standard Unit of Measure for Length
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_Length_ID (int C_UOM_Length_ID);

	/**
	 * Get Maßeinheit für Länge.
	 * Standard Unit of Measure for Length
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_Length_ID();

	public org.compiere.model.I_C_UOM getC_UOM_Length();

	public void setC_UOM_Length(org.compiere.model.I_C_UOM C_UOM_Length);

    /** Column definition for C_UOM_Length_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_UOM> COLUMN_C_UOM_Length_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_UOM>(I_AD_ClientInfo.class, "C_UOM_Length_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_Length_ID */
    public static final String COLUMNNAME_C_UOM_Length_ID = "C_UOM_Length_ID";

	/**
	 * Set Maßeinheit für Zeit.
	 * Standard Unit of Measure for Time
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_Time_ID (int C_UOM_Time_ID);

	/**
	 * Get Maßeinheit für Zeit.
	 * Standard Unit of Measure for Time
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_Time_ID();

	public org.compiere.model.I_C_UOM getC_UOM_Time();

	public void setC_UOM_Time(org.compiere.model.I_C_UOM C_UOM_Time);

    /** Column definition for C_UOM_Time_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_UOM> COLUMN_C_UOM_Time_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_UOM>(I_AD_ClientInfo.class, "C_UOM_Time_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_Time_ID */
    public static final String COLUMNNAME_C_UOM_Time_ID = "C_UOM_Time_ID";

	/**
	 * Set Maßeinheit für Volumen.
	 * Standard Unit of Measure for Volume
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_Volume_ID (int C_UOM_Volume_ID);

	/**
	 * Get Maßeinheit für Volumen.
	 * Standard Unit of Measure for Volume
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_Volume_ID();

	public org.compiere.model.I_C_UOM getC_UOM_Volume();

	public void setC_UOM_Volume(org.compiere.model.I_C_UOM C_UOM_Volume);

    /** Column definition for C_UOM_Volume_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_UOM> COLUMN_C_UOM_Volume_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_UOM>(I_AD_ClientInfo.class, "C_UOM_Volume_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_Volume_ID */
    public static final String COLUMNNAME_C_UOM_Volume_ID = "C_UOM_Volume_ID";

	/**
	 * Set Maßeinheit für Gewicht.
	 * Standard Unit of Measure for Weight
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_Weight_ID (int C_UOM_Weight_ID);

	/**
	 * Get Maßeinheit für Gewicht.
	 * Standard Unit of Measure for Weight
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_Weight_ID();

	public org.compiere.model.I_C_UOM getC_UOM_Weight();

	public void setC_UOM_Weight(org.compiere.model.I_C_UOM C_UOM_Weight);

    /** Column definition for C_UOM_Weight_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_UOM> COLUMN_C_UOM_Weight_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_C_UOM>(I_AD_ClientInfo.class, "C_UOM_Weight_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_Weight_ID */
    public static final String COLUMNNAME_C_UOM_Weight_ID = "C_UOM_Weight_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object>(I_AD_ClientInfo.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_User>(I_AD_ClientInfo.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object>(I_AD_ClientInfo.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Rabatt aus Zeilenbeträgen berechnet.
	 * Payment Discount calculation does not include Taxes and Charges
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDiscountLineAmt (boolean IsDiscountLineAmt);

	/**
	 * Get Rabatt aus Zeilenbeträgen berechnet.
	 * Payment Discount calculation does not include Taxes and Charges
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDiscountLineAmt();

    /** Column definition for IsDiscountLineAmt */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object> COLUMN_IsDiscountLineAmt = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object>(I_AD_ClientInfo.class, "IsDiscountLineAmt", null);
    /** Column name IsDiscountLineAmt */
    public static final String COLUMNNAME_IsDiscountLineAmt = "IsDiscountLineAmt";

	/**
	 * Set Tage Protokoll aufheben.
	 * Number of days to keep the log entries
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setKeepLogDays (int KeepLogDays);

	/**
	 * Get Tage Protokoll aufheben.
	 * Number of days to keep the log entries
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getKeepLogDays();

    /** Column definition for KeepLogDays */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object> COLUMN_KeepLogDays = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object>(I_AD_ClientInfo.class, "KeepLogDays", null);
    /** Column name KeepLogDays */
    public static final String COLUMNNAME_KeepLogDays = "KeepLogDays";

	/**
	 * Set Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLogo_ID (int Logo_ID);

	/**
	 * Get Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLogo_ID();

	public org.compiere.model.I_AD_Image getLogo();

	public void setLogo(org.compiere.model.I_AD_Image Logo);

    /** Column definition for Logo_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Image> COLUMN_Logo_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Image>(I_AD_ClientInfo.class, "Logo_ID", org.compiere.model.I_AD_Image.class);
    /** Column name Logo_ID */
    public static final String COLUMNNAME_Logo_ID = "Logo_ID";

	/**
	 * Set Logo Report.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLogoReport_ID (int LogoReport_ID);

	/**
	 * Get Logo Report.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLogoReport_ID();

	public org.compiere.model.I_AD_Image getLogoReport();

	public void setLogoReport(org.compiere.model.I_AD_Image LogoReport);

    /** Column definition for LogoReport_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Image> COLUMN_LogoReport_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Image>(I_AD_ClientInfo.class, "LogoReport_ID", org.compiere.model.I_AD_Image.class);
    /** Column name LogoReport_ID */
    public static final String COLUMNNAME_LogoReport_ID = "LogoReport_ID";

	/**
	 * Set Logo Web.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLogoWeb_ID (int LogoWeb_ID);

	/**
	 * Get Logo Web.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLogoWeb_ID();

	public org.compiere.model.I_AD_Image getLogoWeb();

	public void setLogoWeb(org.compiere.model.I_AD_Image LogoWeb);

    /** Column definition for LogoWeb_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Image> COLUMN_LogoWeb_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_Image>(I_AD_ClientInfo.class, "LogoWeb_ID", org.compiere.model.I_AD_Image.class);
    /** Column name LogoWeb_ID */
    public static final String COLUMNNAME_LogoWeb_ID = "LogoWeb_ID";

	/**
	 * Set Produkt für Fracht.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ProductFreight_ID (int M_ProductFreight_ID);

	/**
	 * Get Produkt für Fracht.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ProductFreight_ID();

	public org.compiere.model.I_M_Product getM_ProductFreight();

	public void setM_ProductFreight(org.compiere.model.I_M_Product M_ProductFreight);

    /** Column definition for M_ProductFreight_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_M_Product> COLUMN_M_ProductFreight_ID = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_M_Product>(I_AD_ClientInfo.class, "M_ProductFreight_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_ProductFreight_ID */
    public static final String COLUMNNAME_M_ProductFreight_ID = "M_ProductFreight_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, Object>(I_AD_ClientInfo.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_ClientInfo, org.compiere.model.I_AD_User>(I_AD_ClientInfo.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
