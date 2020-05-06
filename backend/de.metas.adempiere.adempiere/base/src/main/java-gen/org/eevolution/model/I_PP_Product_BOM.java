package org.eevolution.model;


/** Generated Interface for PP_Product_BOM
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Product_BOM 
{

    /** TableName=PP_Product_BOM */
    public static final String Table_Name = "PP_Product_BOM";

    /** AD_Table_ID=53018 */
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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Stücklisten-Zugehörigkeit.
	 * Type of BOM
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBOMType (java.lang.String BOMType);

	/**
	 * Get Stücklisten-Zugehörigkeit.
	 * Type of BOM
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBOMType();

    /** Column definition for BOMType */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_BOMType = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "BOMType", null);
    /** Column name BOMType */
    public static final String COLUMNNAME_BOMType = "BOMType";

	/**
	 * Set BOM Use.
	 * The use of the Bill of Material
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBOMUse (java.lang.String BOMUse);

	/**
	 * Get BOM Use.
	 * The use of the Bill of Material
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBOMUse();

    /** Column definition for BOMUse */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_BOMUse = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "BOMUse", null);
    /** Column name BOMUse */
    public static final String COLUMNNAME_BOMUse = "BOMUse";

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

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCopyFrom (java.lang.String CopyFrom);

	/**
	 * Get Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCopyFrom();

    /** Column definition for CopyFrom */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_CopyFrom = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "CopyFrom", null);
    /** Column name CopyFrom */
    public static final String COLUMNNAME_CopyFrom = "CopyFrom";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "Created", null);
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

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "Help", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, org.compiere.model.I_M_AttributeSetInstance>(I_PP_Product_BOM.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, org.compiere.model.I_M_ChangeNotice> COLUMN_M_ChangeNotice_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, org.compiere.model.I_M_ChangeNotice>(I_PP_Product_BOM.class, "M_ChangeNotice_ID", org.compiere.model.I_M_ChangeNotice.class);
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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set BOM & Formula.
	 * BOM & Formula
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/**
	 * Get BOM & Formula.
	 * BOM & Formula
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Product_BOM_ID();

    /** Column definition for PP_Product_BOM_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_PP_Product_BOM_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "PP_Product_BOM_ID", null);
    /** Column name PP_Product_BOM_ID */
    public static final String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Revision.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRevision (java.lang.String Revision);

	/**
	 * Get Revision.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRevision();

    /** Column definition for Revision */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_Revision = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "Revision", null);
    /** Column name Revision */
    public static final String COLUMNNAME_Revision = "Revision";

	/**
	 * Set Nummernfolge für Seriennummer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSerialNo_Sequence_ID (int SerialNo_Sequence_ID);

	/**
	 * Get Nummernfolge für Seriennummer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSerialNo_Sequence_ID();

	public org.compiere.model.I_AD_Sequence getSerialNo_Sequence();

	public void setSerialNo_Sequence(org.compiere.model.I_AD_Sequence SerialNo_Sequence);

    /** Column definition for SerialNo_Sequence_ID */
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, org.compiere.model.I_AD_Sequence> COLUMN_SerialNo_Sequence_ID = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, org.compiere.model.I_AD_Sequence>(I_PP_Product_BOM.class, "SerialNo_Sequence_ID", org.compiere.model.I_AD_Sequence.class);
    /** Column name SerialNo_Sequence_ID */
    public static final String COLUMNNAME_SerialNo_Sequence_ID = "SerialNo_Sequence_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "ValidFrom", null);
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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

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
    public static final org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_PP_Product_BOM, Object>(I_PP_Product_BOM.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
