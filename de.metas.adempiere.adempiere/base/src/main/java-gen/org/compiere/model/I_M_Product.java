package org.compiere.model;


/** Generated Interface for M_Product
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Product 
{

    /** TableName=M_Product */
    public static final String Table_Name = "M_Product";

    /** AD_Table_ID=208 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_Client>(I_M_Product.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_Org>(I_M_Product.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Umsatzrealisierung.
	 * Method for recording revenue
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_RevenueRecognition_ID (int C_RevenueRecognition_ID);

	/**
	 * Get Umsatzrealisierung.
	 * Method for recording revenue
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_RevenueRecognition_ID();

	public org.compiere.model.I_C_RevenueRecognition getC_RevenueRecognition();

	public void setC_RevenueRecognition(org.compiere.model.I_C_RevenueRecognition C_RevenueRecognition);

    /** Column definition for C_RevenueRecognition_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_C_RevenueRecognition> COLUMN_C_RevenueRecognition_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_C_RevenueRecognition>(I_M_Product.class, "C_RevenueRecognition_ID", org.compiere.model.I_C_RevenueRecognition.class);
    /** Column name C_RevenueRecognition_ID */
    public static final String COLUMNNAME_C_RevenueRecognition_ID = "C_RevenueRecognition_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_C_UOM>(I_M_Product.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Klassifizierung.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setClassification (java.lang.String Classification);

	/**
	 * Get Klassifizierung.
	 * Classification for grouping
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClassification();

    /** Column definition for Classification */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Classification = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Classification", null);
    /** Column name Classification */
    public static final String COLUMNNAME_Classification = "Classification";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_User>(I_M_Product.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Auszeichnungsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomerLabelName (java.lang.String CustomerLabelName);

	/**
	 * Get Auszeichnungsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCustomerLabelName();

    /** Column definition for CustomerLabelName */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_CustomerLabelName = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "CustomerLabelName", null);
    /** Column name CustomerLabelName */
    public static final String COLUMNNAME_CustomerLabelName = "CustomerLabelName";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Beschreibungs-URL.
	 * URL for the description
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescriptionURL (java.lang.String DescriptionURL);

	/**
	 * Get Beschreibungs-URL.
	 * URL for the description
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescriptionURL();

    /** Column definition for DescriptionURL */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_DescriptionURL = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "DescriptionURL", null);
    /** Column name DescriptionURL */
    public static final String COLUMNNAME_DescriptionURL = "DescriptionURL";

	/**
	 * Set Eingestellt.
	 * This product is no longer available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscontinued (boolean Discontinued);

	/**
	 * Get Eingestellt.
	 * This product is no longer available
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDiscontinued();

    /** Column definition for Discontinued */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Discontinued = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Discontinued", null);
    /** Column name Discontinued */
    public static final String COLUMNNAME_Discontinued = "Discontinued";

	/**
	 * Set Eingestellt durch.
	 * Discontinued By
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscontinuedBy (java.sql.Timestamp DiscontinuedBy);

	/**
	 * Get Eingestellt durch.
	 * Discontinued By
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDiscontinuedBy();

    /** Column definition for DiscontinuedBy */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_DiscontinuedBy = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "DiscontinuedBy", null);
    /** Column name DiscontinuedBy */
    public static final String COLUMNNAME_DiscontinuedBy = "DiscontinuedBy";

	/**
	 * Set Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNote (java.lang.String DocumentNote);

	/**
	 * Get Notiz / Zeilentext.
	 * Additional information for a Document
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNote();

    /** Column definition for DocumentNote */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_DocumentNote = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "DocumentNote", null);
    /** Column name DocumentNote */
    public static final String COLUMNNAME_DocumentNote = "DocumentNote";

	/**
	 * Set Group1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroup1 (java.lang.String Group1);

	/**
	 * Get Group1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroup1();

    /** Column definition for Group1 */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Group1 = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Group1", null);
    /** Column name Group1 */
    public static final String COLUMNNAME_Group1 = "Group1";

	/**
	 * Set Group2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroup2 (java.lang.String Group2);

	/**
	 * Get Group2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroup2();

    /** Column definition for Group2 */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Group2 = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Group2", null);
    /** Column name Group2 */
    public static final String COLUMNNAME_Group2 = "Group2";

	/**
	 * Set Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationAmtType (java.lang.String GroupCompensationAmtType);

	/**
	 * Get Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroupCompensationAmtType();

    /** Column definition for GroupCompensationAmtType */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_GroupCompensationAmtType = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "GroupCompensationAmtType", null);
    /** Column name GroupCompensationAmtType */
    public static final String COLUMNNAME_GroupCompensationAmtType = "GroupCompensationAmtType";

	/**
	 * Set Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGroupCompensationType (java.lang.String GroupCompensationType);

	/**
	 * Get Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGroupCompensationType();

    /** Column definition for GroupCompensationType */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_GroupCompensationType = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "GroupCompensationType", null);
    /** Column name GroupCompensationType */
    public static final String COLUMNNAME_GroupCompensationType = "GroupCompensationType";

	/**
	 * Set Min. Garantie-Tage.
	 * Minumum number of guarantee days
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGuaranteeDaysMin (int GuaranteeDaysMin);

	/**
	 * Get Min. Garantie-Tage.
	 * Minumum number of guarantee days
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGuaranteeDaysMin();

    /** Column definition for GuaranteeDaysMin */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_GuaranteeDaysMin = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "GuaranteeDaysMin", null);
    /** Column name GuaranteeDaysMin */
    public static final String COLUMNNAME_GuaranteeDaysMin = "GuaranteeDaysMin";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Bild-URL.
	 * URL of  image
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImageURL (java.lang.String ImageURL);

	/**
	 * Get Bild-URL.
	 * URL of  image
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImageURL();

    /** Column definition for ImageURL */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_ImageURL = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "ImageURL", null);
    /** Column name ImageURL */
    public static final String COLUMNNAME_ImageURL = "ImageURL";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Stückliste.
	 * Bill of Materials
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBOM (boolean IsBOM);

	/**
	 * Get Stückliste.
	 * Bill of Materials
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBOM();

    /** Column definition for IsBOM */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsBOM = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsBOM", null);
    /** Column name IsBOM */
    public static final String COLUMNNAME_IsBOM = "IsBOM";

	/**
	 * Set Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDropShip();

    /** Column definition for IsDropShip */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsDropShip = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsDropShip", null);
    /** Column name IsDropShip */
    public static final String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set Ausnehmen von Automatischer Lieferung.
	 * Exclude from automatic Delivery
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsExcludeAutoDelivery (boolean IsExcludeAutoDelivery);

	/**
	 * Get Ausnehmen von Automatischer Lieferung.
	 * Exclude from automatic Delivery
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isExcludeAutoDelivery();

    /** Column definition for IsExcludeAutoDelivery */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsExcludeAutoDelivery = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsExcludeAutoDelivery", null);
    /** Column name IsExcludeAutoDelivery */
    public static final String COLUMNNAME_IsExcludeAutoDelivery = "IsExcludeAutoDelivery";

	/**
	 * Set Print detail records on invoice .
	 * Print detail BOM elements on the invoice
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInvoicePrintDetails (boolean IsInvoicePrintDetails);

	/**
	 * Get Print detail records on invoice .
	 * Print detail BOM elements on the invoice
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInvoicePrintDetails();

    /** Column definition for IsInvoicePrintDetails */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsInvoicePrintDetails = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsInvoicePrintDetails", null);
    /** Column name IsInvoicePrintDetails */
    public static final String COLUMNNAME_IsInvoicePrintDetails = "IsInvoicePrintDetails";

	/**
	 * Set Wird produziert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsManufactured (boolean IsManufactured);

	/**
	 * Get Wird produziert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isManufactured();

    /** Column definition for IsManufactured */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsManufactured = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsManufactured", null);
    /** Column name IsManufactured */
    public static final String COLUMNNAME_IsManufactured = "IsManufactured";

	/**
	 * Set Detaileinträge auf Kommissionierschein drucken.
	 * Print detail BOM elements on the pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPickListPrintDetails (boolean IsPickListPrintDetails);

	/**
	 * Get Detaileinträge auf Kommissionierschein drucken.
	 * Print detail BOM elements on the pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPickListPrintDetails();

    /** Column definition for IsPickListPrintDetails */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsPickListPrintDetails = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsPickListPrintDetails", null);
    /** Column name IsPickListPrintDetails */
    public static final String COLUMNNAME_IsPickListPrintDetails = "IsPickListPrintDetails";

	/**
	 * Set Eingekauft.
	 * Organization purchases this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPurchased (boolean IsPurchased);

	/**
	 * Get Eingekauft.
	 * Organization purchases this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPurchased();

    /** Column definition for IsPurchased */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsPurchased = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsPurchased", null);
    /** Column name IsPurchased */
    public static final String COLUMNNAME_IsPurchased = "IsPurchased";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelfService();

    /** Column definition for IsSelfService */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Verkauft.
	 * Organization sells this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSold (boolean IsSold);

	/**
	 * Get Verkauft.
	 * Organization sells this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSold();

    /** Column definition for IsSold */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsSold = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsSold", null);
    /** Column name IsSold */
    public static final String COLUMNNAME_IsSold = "IsSold";

	/**
	 * Set Lagerhaltig.
	 * Organization stocks this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsStocked (boolean IsStocked);

	/**
	 * Get Lagerhaltig.
	 * Organization stocks this product
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isStocked();

    /** Column definition for IsStocked */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsStocked = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsStocked", null);
    /** Column name IsStocked */
    public static final String COLUMNNAME_IsStocked = "IsStocked";

	/**
	 * Set Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSummary (boolean IsSummary);

	/**
	 * Get Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSummary();

    /** Column definition for IsSummary */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsSummary = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsSummary", null);
    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Verified.
	 * The BOM configuration has been verified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsVerified (boolean IsVerified);

	/**
	 * Get Verified.
	 * The BOM configuration has been verified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isVerified();

    /** Column definition for IsVerified */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsVerified = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsVerified", null);
    /** Column name IsVerified */
    public static final String COLUMNNAME_IsVerified = "IsVerified";

	/**
	 * Set Beworben im Web-Shop.
	 * If selected, the product is displayed in the inital or any empy search
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsWebStoreFeatured (boolean IsWebStoreFeatured);

	/**
	 * Get Beworben im Web-Shop.
	 * If selected, the product is displayed in the inital or any empy search
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWebStoreFeatured();

    /** Column definition for IsWebStoreFeatured */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_IsWebStoreFeatured = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "IsWebStoreFeatured", null);
    /** Column name IsWebStoreFeatured */
    public static final String COLUMNNAME_IsWebStoreFeatured = "IsWebStoreFeatured";

	/**
	 * Set Low Level.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLowLevel (int LowLevel);

	/**
	 * Get Low Level.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLowLevel();

    /** Column definition for LowLevel */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_LowLevel = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "LowLevel", null);
    /** Column name LowLevel */
    public static final String COLUMNNAME_LowLevel = "LowLevel";

	/**
	 * Set Merkmals-Satz.
	 * Product Attribute Set
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Merkmals-Satz.
	 * Product Attribute Set
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSet_ID();

	public org.compiere.model.I_M_AttributeSet getM_AttributeSet();

	public void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet);

    /** Column definition for M_AttributeSet_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_AttributeSet> COLUMN_M_AttributeSet_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_AttributeSet>(I_M_Product.class, "M_AttributeSet_ID", org.compiere.model.I_M_AttributeSet.class);
    /** Column name M_AttributeSet_ID */
    public static final String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_AttributeSetInstance>(I_M_Product.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Fracht-Kategorie.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_FreightCategory_ID (int M_FreightCategory_ID);

	/**
	 * Get Fracht-Kategorie.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_FreightCategory_ID();

	public org.compiere.model.I_M_FreightCategory getM_FreightCategory();

	public void setM_FreightCategory(org.compiere.model.I_M_FreightCategory M_FreightCategory);

    /** Column definition for M_FreightCategory_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_FreightCategory> COLUMN_M_FreightCategory_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_FreightCategory>(I_M_Product.class, "M_FreightCategory_ID", org.compiere.model.I_M_FreightCategory.class);
    /** Column name M_FreightCategory_ID */
    public static final String COLUMNNAME_M_FreightCategory_ID = "M_FreightCategory_ID";

	/**
	 * Set Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_Locator>(I_M_Product.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category();

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_M_Product_Category>(I_M_Product.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "M_Product_ID", null);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set M_ProductPlanningSchema_Selector.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ProductPlanningSchema_Selector (java.lang.String M_ProductPlanningSchema_Selector);

	/**
	 * Get M_ProductPlanningSchema_Selector.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getM_ProductPlanningSchema_Selector();

    /** Column definition for M_ProductPlanningSchema_Selector */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_M_ProductPlanningSchema_Selector = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "M_ProductPlanningSchema_Selector", null);
    /** Column name M_ProductPlanningSchema_Selector */
    public static final String COLUMNNAME_M_ProductPlanningSchema_Selector = "M_ProductPlanningSchema_Selector";

	/**
	 * Set Hersteller.
	 * Hersteller des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setManufacturer (java.lang.String Manufacturer);

	/**
	 * Get Hersteller.
	 * Hersteller des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getManufacturer();

    /** Column definition for Manufacturer */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Manufacturer = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Manufacturer", null);
    /** Column name Manufacturer */
    public static final String COLUMNNAME_Manufacturer = "Manufacturer";

	/**
	 * Set MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMRP_Exclude (java.lang.String MRP_Exclude);

	/**
	 * Get MRP ausschliessen.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMRP_Exclude();

    /** Column definition for MRP_Exclude */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_MRP_Exclude = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "MRP_Exclude", null);
    /** Column name MRP_Exclude */
    public static final String COLUMNNAME_MRP_Exclude = "MRP_Exclude";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Package UOM.
	 * UOM of the package
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackage_UOM_ID (int Package_UOM_ID);

	/**
	 * Get Package UOM.
	 * UOM of the package
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPackage_UOM_ID();

	public org.compiere.model.I_C_UOM getPackage_UOM();

	public void setPackage_UOM(org.compiere.model.I_C_UOM Package_UOM);

    /** Column definition for Package_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_C_UOM> COLUMN_Package_UOM_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_C_UOM>(I_M_Product.class, "Package_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name Package_UOM_ID */
    public static final String COLUMNNAME_Package_UOM_ID = "Package_UOM_ID";

	/**
	 * Set Package Size.
	 * Size of a package
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPackageSize (java.lang.String PackageSize);

	/**
	 * Get Package Size.
	 * Size of a package
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPackageSize();

    /** Column definition for PackageSize */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_PackageSize = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "PackageSize", null);
    /** Column name PackageSize */
    public static final String COLUMNNAME_PackageSize = "PackageSize";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Produktart.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProductType (java.lang.String ProductType);

	/**
	 * Get Produktart.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductType();

    /** Column definition for ProductType */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_ProductType = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "ProductType", null);
    /** Column name ProductType */
    public static final String COLUMNNAME_ProductType = "ProductType";

	/**
	 * Set EMail-Vorlage.
	 * Text templates for mailings
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_MailText_ID (int R_MailText_ID);

	/**
	 * Get EMail-Vorlage.
	 * Text templates for mailings
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_MailText_ID();

	public org.compiere.model.I_R_MailText getR_MailText();

	public void setR_MailText(org.compiere.model.I_R_MailText R_MailText);

    /** Column definition for R_MailText_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_R_MailText> COLUMN_R_MailText_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_R_MailText>(I_M_Product.class, "R_MailText_ID", org.compiere.model.I_R_MailText.class);
    /** Column name R_MailText_ID */
    public static final String COLUMNNAME_R_MailText_ID = "R_MailText_ID";

	/**
	 * Set Aufwandsart.
	 * Expense report type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_ExpenseType_ID (int S_ExpenseType_ID);

	/**
	 * Get Aufwandsart.
	 * Expense report type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_ExpenseType_ID();

	public org.compiere.model.I_S_ExpenseType getS_ExpenseType();

	public void setS_ExpenseType(org.compiere.model.I_S_ExpenseType S_ExpenseType);

    /** Column definition for S_ExpenseType_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_S_ExpenseType> COLUMN_S_ExpenseType_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_S_ExpenseType>(I_M_Product.class, "S_ExpenseType_ID", org.compiere.model.I_S_ExpenseType.class);
    /** Column name S_ExpenseType_ID */
    public static final String COLUMNNAME_S_ExpenseType_ID = "S_ExpenseType_ID";

	/**
	 * Set Ressource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setS_Resource_ID (int S_Resource_ID);

	/**
	 * Get Ressource.
	 * Resource
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getS_Resource_ID();

	public org.compiere.model.I_S_Resource getS_Resource();

	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource);

    /** Column definition for S_Resource_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_S_Resource> COLUMN_S_Resource_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_S_Resource>(I_M_Product.class, "S_Resource_ID", org.compiere.model.I_S_Resource.class);
    /** Column name S_Resource_ID */
    public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/**
	 * Set Aussendienst.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Aussendienst.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep();

	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column definition for SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_User>(I_M_Product.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Regaltiefe.
	 * Shelf depth required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShelfDepth (int ShelfDepth);

	/**
	 * Get Regaltiefe.
	 * Shelf depth required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getShelfDepth();

    /** Column definition for ShelfDepth */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_ShelfDepth = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "ShelfDepth", null);
    /** Column name ShelfDepth */
    public static final String COLUMNNAME_ShelfDepth = "ShelfDepth";

	/**
	 * Set Regalhöhe.
	 * Shelf height required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShelfHeight (java.math.BigDecimal ShelfHeight);

	/**
	 * Get Regalhöhe.
	 * Shelf height required
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getShelfHeight();

    /** Column definition for ShelfHeight */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_ShelfHeight = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "ShelfHeight", null);
    /** Column name ShelfHeight */
    public static final String COLUMNNAME_ShelfHeight = "ShelfHeight";

	/**
	 * Set Regalbreite.
	 * Shelf width required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShelfWidth (int ShelfWidth);

	/**
	 * Get Regalbreite.
	 * Shelf width required
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getShelfWidth();

    /** Column definition for ShelfWidth */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_ShelfWidth = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "ShelfWidth", null);
    /** Column name ShelfWidth */
    public static final String COLUMNNAME_ShelfWidth = "ShelfWidth";

	/**
	 * Set SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSKU (java.lang.String SKU);

	/**
	 * Get SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSKU();

    /** Column definition for SKU */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_SKU = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "SKU", null);
    /** Column name SKU */
    public static final String COLUMNNAME_SKU = "SKU";

	/**
	 * Set UnitsPerPack.
	 * The Units Per Pack indicates the no of units of a product packed together.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUnitsPerPack (int UnitsPerPack);

	/**
	 * Get UnitsPerPack.
	 * The Units Per Pack indicates the no of units of a product packed together.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUnitsPerPack();

    /** Column definition for UnitsPerPack */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_UnitsPerPack = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "UnitsPerPack", null);
    /** Column name UnitsPerPack */
    public static final String COLUMNNAME_UnitsPerPack = "UnitsPerPack";

	/**
	 * Set Einheiten pro Palette.
	 * Units Per Pallet
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUnitsPerPallet (java.math.BigDecimal UnitsPerPallet);

	/**
	 * Get Einheiten pro Palette.
	 * Units Per Pallet
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getUnitsPerPallet();

    /** Column definition for UnitsPerPallet */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_UnitsPerPallet = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "UnitsPerPallet", null);
    /** Column name UnitsPerPallet */
    public static final String COLUMNNAME_UnitsPerPallet = "UnitsPerPallet";

	/**
	 * Set UPC/EAN.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC (java.lang.String UPC);

	/**
	 * Get UPC/EAN.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC();

    /** Column definition for UPC */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

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
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Product, org.compiere.model.I_AD_User>(I_M_Product.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Versions-Nr..
	 * Version Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVersionNo (java.lang.String VersionNo);

	/**
	 * Get Versions-Nr..
	 * Version Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVersionNo();

    /** Column definition for VersionNo */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_VersionNo = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "VersionNo", null);
    /** Column name VersionNo */
    public static final String COLUMNNAME_VersionNo = "VersionNo";

	/**
	 * Set Volumen.
	 * Volume of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVolume (java.math.BigDecimal Volume);

	/**
	 * Get Volumen.
	 * Volume of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getVolume();

    /** Column definition for Volume */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Volume = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Volume", null);
    /** Column name Volume */
    public static final String COLUMNNAME_Volume = "Volume";

	/**
	 * Set Gewicht.
	 * Weight of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWeight (java.math.BigDecimal Weight);

	/**
	 * Get Gewicht.
	 * Weight of a product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getWeight();

    /** Column definition for Weight */
    public static final org.adempiere.model.ModelColumn<I_M_Product, Object> COLUMN_Weight = new org.adempiere.model.ModelColumn<I_M_Product, Object>(I_M_Product.class, "Weight", null);
    /** Column name Weight */
    public static final String COLUMNNAME_Weight = "Weight";
}
