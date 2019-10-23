package org.eevolution.model;


/** Generated Interface for PP_Product_BOMLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Product_BOMLine 
{

    /** TableName=PP_Product_BOMLine */
    public static final String Table_Name = "PP_Product_BOMLine";

    /** AD_Table_ID=53019 */
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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_AD_Client>(I_PP_Product_BOMLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_AD_Org>(I_PP_Product_BOMLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Mengen Probe.
	 * Indicated the Quantity Assay to use into Quality Order
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAssay (java.math.BigDecimal Assay);

	/**
	 * Get Mengen Probe.
	 * Indicated the Quantity Assay to use into Quality Order
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAssay();

    /** Column definition for Assay */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Assay = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "Assay", null);
    /** Column name Assay */
    public static final String COLUMNNAME_Assay = "Assay";

	/**
	 * Set Retrograde Gruppe.
	 * The Grouping Components to the Backflush
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBackflushGroup (java.lang.String BackflushGroup);

	/**
	 * Get Retrograde Gruppe.
	 * The Grouping Components to the Backflush
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBackflushGroup();

    /** Column definition for BackflushGroup */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_BackflushGroup = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "BackflushGroup", null);
    /** Column name BackflushGroup */
    public static final String COLUMNNAME_BackflushGroup = "BackflushGroup";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_C_UOM>(I_PP_Product_BOMLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Component Type.
	 * Component Type for a Bill of Material or Formula
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setComponentType (java.lang.String ComponentType);

	/**
	 * Get Component Type.
	 * Component Type for a Bill of Material or Formula
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getComponentType();

    /** Column definition for ComponentType */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_ComponentType = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "ComponentType", null);
    /** Column name ComponentType */
    public static final String COLUMNNAME_ComponentType = "ComponentType";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_AD_User>(I_PP_Product_BOMLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set CU Label Qty.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCULabelQuanitity (java.lang.String CULabelQuanitity);

	/**
	 * Get CU Label Qty.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCULabelQuanitity();

    /** Column definition for CULabelQuanitity */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_CULabelQuanitity = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "CULabelQuanitity", null);
    /** Column name CULabelQuanitity */
    public static final String COLUMNNAME_CULabelQuanitity = "CULabelQuanitity";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Erwartetes Ergebnis.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setexpectedResult (java.math.BigDecimal expectedResult);

	/**
	 * Get Erwartetes Ergebnis.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getexpectedResult();

    /** Column definition for expectedResult */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_expectedResult = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "expectedResult", null);
    /** Column name expectedResult */
    public static final String COLUMNNAME_expectedResult = "expectedResult";

	/**
	 * Set Feature.
	 * Indicated the Feature for Product Configure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFeature (java.lang.String Feature);

	/**
	 * Get Feature.
	 * Indicated the Feature for Product Configure
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFeature();

    /** Column definition for Feature */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Feature = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "Feature", null);
    /** Column name Feature */
    public static final String COLUMNNAME_Feature = "Feature";

	/**
	 * Set Forecast.
	 * Indicated the % of participation this component into a of the BOM Planning
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setForecast (java.math.BigDecimal Forecast);

	/**
	 * Get Forecast.
	 * Indicated the % of participation this component into a of the BOM Planning
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getForecast();

    /** Column definition for Forecast */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Forecast = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "Forecast", null);
    /** Column name Forecast */
    public static final String COLUMNNAME_Forecast = "Forecast";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "Help", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Is Critical Component.
	 * Indicate that a Manufacturing Order can not begin without have this component
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsCritical (boolean IsCritical);

	/**
	 * Get Is Critical Component.
	 * Indicate that a Manufacturing Order can not begin without have this component
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isCritical();

    /** Column definition for IsCritical */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IsCritical = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "IsCritical", null);
    /** Column name IsCritical */
    public static final String COLUMNNAME_IsCritical = "IsCritical";

	/**
	 * Set Is Qty Percentage.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsQtyPercentage (boolean IsQtyPercentage);

	/**
	 * Get Is Qty Percentage.
	 * Indicate that this component is based in % Quantity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isQtyPercentage();

    /** Column definition for IsQtyPercentage */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IsQtyPercentage = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "IsQtyPercentage", null);
    /** Column name IsQtyPercentage */
    public static final String COLUMNNAME_IsQtyPercentage = "IsQtyPercentage";

	/**
	 * Set Zuteil Methode.
	 * There are two methods for issue the components to Manufacturing Order
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIssueMethod (java.lang.String IssueMethod);

	/**
	 * Get Zuteil Methode.
	 * There are two methods for issue the components to Manufacturing Order
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIssueMethod();

    /** Column definition for IssueMethod */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_IssueMethod = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "IssueMethod", null);
    /** Column name IssueMethod */
    public static final String COLUMNNAME_IssueMethod = "IssueMethod";

	/**
	 * Set Lead Time Offset.
	 * Optional Lead Time offest before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLeadTimeOffset (int LeadTimeOffset);

	/**
	 * Get Lead Time Offset.
	 * Optional Lead Time offest before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLeadTimeOffset();

    /** Column definition for LeadTimeOffset */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_LeadTimeOffset = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "LeadTimeOffset", null);
    /** Column name LeadTimeOffset */
    public static final String COLUMNNAME_LeadTimeOffset = "LeadTimeOffset";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_AttributeSetInstance>(I_PP_Product_BOMLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Änderungsmeldung.
	 * Bill of Materials (Engineering) Change Notice (Version)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ChangeNotice_ID (int M_ChangeNotice_ID);

	/**
	 * Get Änderungsmeldung.
	 * Bill of Materials (Engineering) Change Notice (Version)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ChangeNotice_ID();

	public org.compiere.model.I_M_ChangeNotice getM_ChangeNotice();

	public void setM_ChangeNotice(org.compiere.model.I_M_ChangeNotice M_ChangeNotice);

    /** Column definition for M_ChangeNotice_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_ChangeNotice> COLUMN_M_ChangeNotice_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_ChangeNotice>(I_PP_Product_BOMLine.class, "M_ChangeNotice_ID", org.compiere.model.I_M_ChangeNotice.class);
    /** Column name M_ChangeNotice_ID */
    public static final String COLUMNNAME_M_ChangeNotice_ID = "M_ChangeNotice_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_Product>(I_PP_Product_BOMLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set % oldScrap.
	 * Indicate the % Scrap  for calculate the Scrap Quantity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setoldScrap (java.math.BigDecimal oldScrap);

	/**
	 * Get % oldScrap.
	 * Indicate the % Scrap  for calculate the Scrap Quantity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getoldScrap();

    /** Column definition for oldScrap */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_oldScrap = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "oldScrap", null);
    /** Column name oldScrap */
    public static final String COLUMNNAME_oldScrap = "oldScrap";

	/**
	 * Set BOM & Formula.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/**
	 * Get BOM & Formula.
	 * BOM & Formula
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Product_BOM_ID();

	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM();

	public void setPP_Product_BOM(org.eevolution.model.I_PP_Product_BOM PP_Product_BOM);

    /** Column definition for PP_Product_BOM_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.eevolution.model.I_PP_Product_BOM> COLUMN_PP_Product_BOM_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.eevolution.model.I_PP_Product_BOM>(I_PP_Product_BOMLine.class, "PP_Product_BOM_ID", org.eevolution.model.I_PP_Product_BOM.class);
    /** Column name PP_Product_BOM_ID */
    public static final String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	/**
	 * Set BOM Line.
	 * BOM Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID);

	/**
	 * Get BOM Line.
	 * BOM Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Product_BOMLine_ID();

    /** Column definition for PP_Product_BOMLine_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_PP_Product_BOMLine_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "PP_Product_BOMLine_ID", null);
    /** Column name PP_Product_BOMLine_ID */
    public static final String COLUMNNAME_PP_Product_BOMLine_ID = "PP_Product_BOMLine_ID";

	/**
	 * Set Qty Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty_Attribute_ID (int Qty_Attribute_ID);

	/**
	 * Get Qty Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getQty_Attribute_ID();

	public org.compiere.model.I_M_Attribute getQty_Attribute();

	public void setQty_Attribute(org.compiere.model.I_M_Attribute Qty_Attribute);

    /** Column definition for Qty_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_Attribute> COLUMN_Qty_Attribute_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_M_Attribute>(I_PP_Product_BOMLine.class, "Qty_Attribute_ID", org.compiere.model.I_M_Attribute.class);
    /** Column name Qty_Attribute_ID */
    public static final String COLUMNNAME_Qty_Attribute_ID = "Qty_Attribute_ID";

	/**
	 * Set Quantity in %.
	 * Indicate the Quantity % use in this Formula
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyBatch (java.math.BigDecimal QtyBatch);

	/**
	 * Get Quantity in %.
	 * Indicate the Quantity % use in this Formula
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyBatch();

    /** Column definition for QtyBatch */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_QtyBatch = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "QtyBatch", null);
    /** Column name QtyBatch */
    public static final String COLUMNNAME_QtyBatch = "QtyBatch";

	/**
	 * Set Quantity.
	 * Indicate the Quantity  use in this BOM
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyBOM (java.math.BigDecimal QtyBOM);

	/**
	 * Get Quantity.
	 * Indicate the Quantity  use in this BOM
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyBOM();

    /** Column definition for QtyBOM */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_QtyBOM = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "QtyBOM", null);
    /** Column name QtyBOM */
    public static final String COLUMNNAME_QtyBOM = "QtyBOM";

	/**
	 * Set % Scrap.
	 * Indicate the % Scrap  for calculate the Scrap Quantity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setScrap (java.math.BigDecimal Scrap);

	/**
	 * Get % Scrap.
	 * Indicate the % Scrap  for calculate the Scrap Quantity
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getScrap();

    /** Column definition for Scrap */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Scrap = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "Scrap", null);
    /** Column name Scrap */
    public static final String COLUMNNAME_Scrap = "Scrap";

	/**
	 * Set Show Sub BOM Ingredients.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setShowSubBOMIngredients (boolean ShowSubBOMIngredients);

	/**
	 * Get Show Sub BOM Ingredients.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isShowSubBOMIngredients();

    /** Column definition for ShowSubBOMIngredients */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_ShowSubBOMIngredients = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "ShowSubBOMIngredients", null);
    /** Column name ShowSubBOMIngredients */
    public static final String COLUMNNAME_ShowSubBOMIngredients = "ShowSubBOMIngredients";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, org.compiere.model.I_AD_User>(I_PP_Product_BOMLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Varianten Gruppe.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVariantGroup (java.lang.String VariantGroup);

	/**
	 * Get Varianten Gruppe.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVariantGroup();

    /** Column definition for VariantGroup */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object> COLUMN_VariantGroup = new org.adempiere.model.ModelColumn<I_PP_Product_BOMLine, Object>(I_PP_Product_BOMLine.class, "VariantGroup", null);
    /** Column name VariantGroup */
    public static final String COLUMNNAME_VariantGroup = "VariantGroup";
}
