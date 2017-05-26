package org.eevolution.model;


/** Generated Interface for DD_OrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DD_OrderLine 
{

    /** TableName=DD_OrderLine */
    public static final String Table_Name = "DD_OrderLine";

    /** AD_Table_ID=53038 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_Client>(I_DD_OrderLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_Org>(I_DD_OrderLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_Org>(I_DD_OrderLine.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_Activity>(I_DD_OrderLine.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_BPartner>(I_DD_OrderLine.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_Campaign>(I_DD_OrderLine.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge();

	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_Charge>(I_DD_OrderLine.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLineSO_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLineSO();

	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO);

    /** Column definition for C_OrderLineSO_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_OrderLine>(I_DD_OrderLine.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLineSO_ID */
    public static final String COLUMNNAME_C_OrderLineSO_ID = "C_OrderLineSO_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_Project> COLUMN_C_Project_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_Project>(I_DD_OrderLine.class, "C_Project_ID", org.compiere.model.I_C_Project.class);
    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_UOM>(I_DD_OrderLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Bestätigte Menge.
	 * Confirmation of a received quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConfirmedQty (java.math.BigDecimal ConfirmedQty);

	/**
	 * Get Bestätigte Menge.
	 * Confirmation of a received quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getConfirmedQty();

    /** Column definition for ConfirmedQty */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_ConfirmedQty = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "ConfirmedQty", null);
    /** Column name ConfirmedQty */
    public static final String COLUMNNAME_ConfirmedQty = "ConfirmedQty";

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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_User>(I_DD_OrderLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Lieferdatum.
	 * Date when the product was delivered
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateDelivered (java.sql.Timestamp DateDelivered);

	/**
	 * Get Lieferdatum.
	 * Date when the product was delivered
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDelivered();

    /** Column definition for DateDelivered */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_DateDelivered = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "DateDelivered", null);
    /** Column name DateDelivered */
    public static final String COLUMNNAME_DateDelivered = "DateDelivered";

	/**
	 * Set Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Auftragsdatum.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Allow Push.
	 * Allow pushing materials from suppliers through this path.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDD_AllowPush (boolean DD_AllowPush);

	/**
	 * Get Allow Push.
	 * Allow pushing materials from suppliers through this path.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDD_AllowPush();

    /** Column definition for DD_AllowPush */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_DD_AllowPush = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "DD_AllowPush", null);
    /** Column name DD_AllowPush */
    public static final String COLUMNNAME_DD_AllowPush = "DD_AllowPush";

	/**
	 * Set Network Distribution Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDD_NetworkDistributionLine_ID (int DD_NetworkDistributionLine_ID);

	/**
	 * Get Network Distribution Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDD_NetworkDistributionLine_ID();

	public org.eevolution.model.I_DD_NetworkDistributionLine getDD_NetworkDistributionLine();

	public void setDD_NetworkDistributionLine(org.eevolution.model.I_DD_NetworkDistributionLine DD_NetworkDistributionLine);

    /** Column definition for DD_NetworkDistributionLine_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.eevolution.model.I_DD_NetworkDistributionLine> COLUMN_DD_NetworkDistributionLine_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.eevolution.model.I_DD_NetworkDistributionLine>(I_DD_OrderLine.class, "DD_NetworkDistributionLine_ID", org.eevolution.model.I_DD_NetworkDistributionLine.class);
    /** Column name DD_NetworkDistributionLine_ID */
    public static final String COLUMNNAME_DD_NetworkDistributionLine_ID = "DD_NetworkDistributionLine_ID";

	/**
	 * Set Distribution Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDD_Order_ID (int DD_Order_ID);

	/**
	 * Get Distribution Order.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDD_Order_ID();

	public org.eevolution.model.I_DD_Order getDD_Order();

	public void setDD_Order(org.eevolution.model.I_DD_Order DD_Order);

    /** Column definition for DD_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.eevolution.model.I_DD_Order> COLUMN_DD_Order_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.eevolution.model.I_DD_Order>(I_DD_OrderLine.class, "DD_Order_ID", org.eevolution.model.I_DD_Order.class);
    /** Column name DD_Order_ID */
    public static final String COLUMNNAME_DD_Order_ID = "DD_Order_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDD_OrderLine_ID();

    /** Column definition for DD_OrderLine_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_DD_OrderLine_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "DD_OrderLine_ID", null);
    /** Column name DD_OrderLine_ID */
    public static final String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	
	/**
	 * Set Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFreightAmt (java.math.BigDecimal FreightAmt);

	/**
	 * Get Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFreightAmt();

    /** Column definition for FreightAmt */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_FreightAmt = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "FreightAmt", null);
    /** Column name FreightAmt */
    public static final String COLUMNNAME_FreightAmt = "FreightAmt";

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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDelivered (boolean IsDelivered);

	/**
	 * Get Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDelivered();

    /** Column definition for IsDelivered */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_IsDelivered = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "IsDelivered", null);
    /** Column name IsDelivered */
    public static final String COLUMNNAME_IsDelivered = "IsDelivered";

	/**
	 * Set Zugestellt abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDelivered_Override (java.lang.String IsDelivered_Override);

	/**
	 * Get Zugestellt abw..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsDelivered_Override();

    /** Column definition for IsDelivered_Override */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_IsDelivered_Override = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "IsDelivered_Override", null);
    /** Column name IsDelivered_Override */
    public static final String COLUMNNAME_IsDelivered_Override = "IsDelivered_Override";

	/**
	 * Set Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDescription (boolean IsDescription);

	/**
	 * Get Description Only.
	 * if true, the line is just description and no transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDescription();

    /** Column definition for IsDescription */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_IsDescription = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "IsDescription", null);
    /** Column name IsDescription */
    public static final String COLUMNNAME_IsDescription = "IsDescription";

	/**
	 * Set Berechnete Menge.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInvoiced (boolean IsInvoiced);

	/**
	 * Get Berechnete Menge.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInvoiced();

    /** Column definition for IsInvoiced */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_IsInvoiced = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "IsInvoiced", null);
    /** Column name IsInvoiced */
    public static final String COLUMNNAME_IsInvoiced = "IsInvoiced";

	/**
	 * Set Keep target plant.
	 * If set, the MRP demand of the distribution order which will be generated will have the same plant is target warehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsKeepTargetPlant (boolean IsKeepTargetPlant);

	/**
	 * Get Keep target plant.
	 * If set, the MRP demand of the distribution order which will be generated will have the same plant is target warehouse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isKeepTargetPlant();

    /** Column definition for IsKeepTargetPlant */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_IsKeepTargetPlant = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "IsKeepTargetPlant", null);
    /** Column name IsKeepTargetPlant */
    public static final String COLUMNNAME_IsKeepTargetPlant = "IsKeepTargetPlant";

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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Zeilennetto.
	 * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLineNetAmt (java.math.BigDecimal LineNetAmt);

	/**
	 * Get Zeilennetto.
	 * Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLineNetAmt();

    /** Column definition for LineNetAmt */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_LineNetAmt = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "LineNetAmt", null);
    /** Column name LineNetAmt */
    public static final String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_AttributeSetInstance>(I_DD_OrderLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Attribute Set Instance To.
	 * Target Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstanceTo_ID (int M_AttributeSetInstanceTo_ID);

	/**
	 * Get Attribute Set Instance To.
	 * Target Product Attribute Set Instance
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstanceTo_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstanceTo();

	public void setM_AttributeSetInstanceTo(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstanceTo);

    /** Column definition for M_AttributeSetInstanceTo_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstanceTo_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_AttributeSetInstance>(I_DD_OrderLine.class, "M_AttributeSetInstanceTo_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstanceTo_ID */
    public static final String COLUMNNAME_M_AttributeSetInstanceTo_ID = "M_AttributeSetInstanceTo_ID";

	/**
	 * Set Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_Locator>(I_DD_OrderLine.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Lagerort An.
	 * Location inventory is moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_LocatorTo_ID (int M_LocatorTo_ID);

	/**
	 * Get Lagerort An.
	 * Location inventory is moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_LocatorTo_ID();

	public org.compiere.model.I_M_Locator getM_LocatorTo();

	public void setM_LocatorTo(org.compiere.model.I_M_Locator M_LocatorTo);

    /** Column definition for M_LocatorTo_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_Locator> COLUMN_M_LocatorTo_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_Locator>(I_DD_OrderLine.class, "M_LocatorTo_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_LocatorTo_ID */
    public static final String COLUMNNAME_M_LocatorTo_ID = "M_LocatorTo_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_M_Product>(I_DD_OrderLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Picked Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPickedQty (java.math.BigDecimal PickedQty);

	/**
	 * Get Picked Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPickedQty();

    /** Column definition for PickedQty */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_PickedQty = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "PickedQty", null);
    /** Column name PickedQty */
    public static final String COLUMNNAME_PickedQty = "PickedQty";

	/**
	 * Set Produktionsstätte ab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPP_Plant_From_ID (int PP_Plant_From_ID);

	/**
	 * Get Produktionsstätte ab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPP_Plant_From_ID();

	public org.compiere.model.I_S_Resource getPP_Plant_From();

	public void setPP_Plant_From(org.compiere.model.I_S_Resource PP_Plant_From);

    /** Column definition for PP_Plant_From_ID */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_From_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_S_Resource>(I_DD_OrderLine.class, "PP_Plant_From_ID", org.compiere.model.I_S_Resource.class);
    /** Column name PP_Plant_From_ID */
    public static final String COLUMNNAME_PP_Plant_From_ID = "PP_Plant_From_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Delivered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyDelivered();

    /** Column definition for QtyDelivered */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_QtyDelivered = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "QtyDelivered", null);
    /** Column name QtyDelivered */
    public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Menge.
	 * The Quantity Entered is based on the selected UoM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyEntered (java.math.BigDecimal QtyEntered);

	/**
	 * Get Menge.
	 * The Quantity Entered is based on the selected UoM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyEntered();

    /** Column definition for QtyEntered */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_QtyEntered = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "QtyEntered", null);
    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Qty In Transit.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyInTransit (java.math.BigDecimal QtyInTransit);

	/**
	 * Get Qty In Transit.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInTransit();

    /** Column definition for QtyInTransit */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_QtyInTransit = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "QtyInTransit", null);
    /** Column name QtyInTransit */
    public static final String COLUMNNAME_QtyInTransit = "QtyInTransit";

	/**
	 * Set Bestellte Menge.
	 * Ordered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellte Menge.
	 * Ordered Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Reservierte Menge.
	 * Reserved Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved (java.math.BigDecimal QtyReserved);

	/**
	 * Get Reservierte Menge.
	 * Reserved Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved();

    /** Column definition for QtyReserved */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_QtyReserved = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "QtyReserved", null);
    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Verworfene Menge.
	 * The Quantity scrapped due to QA issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setScrappedQty (java.math.BigDecimal ScrappedQty);

	/**
	 * Get Verworfene Menge.
	 * The Quantity scrapped due to QA issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getScrappedQty();

    /** Column definition for ScrappedQty */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_ScrappedQty = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "ScrappedQty", null);
    /** Column name ScrappedQty */
    public static final String COLUMNNAME_ScrappedQty = "ScrappedQty";

	/**
	 * Set Zielmenge.
	 * Target Movement Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTargetQty (java.math.BigDecimal TargetQty);

	/**
	 * Get Zielmenge.
	 * Target Movement Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTargetQty();

    /** Column definition for TargetQty */
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_TargetQty = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "TargetQty", null);
    /** Column name TargetQty */
    public static final String COLUMNNAME_TargetQty = "TargetQty";

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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DD_OrderLine, Object>(I_DD_OrderLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_AD_User>(I_DD_OrderLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_ElementValue>(I_DD_OrderLine.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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
    public static final org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new org.adempiere.model.ModelColumn<I_DD_OrderLine, org.compiere.model.I_C_ElementValue>(I_DD_OrderLine.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name User2_ID */
    public static final String COLUMNNAME_User2_ID = "User2_ID";
}
