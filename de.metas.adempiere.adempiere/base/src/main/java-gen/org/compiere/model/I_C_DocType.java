package org.compiere.model;


/** Generated Interface for C_DocType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_DocType 
{

    /** TableName=C_DocType */
    public static final String Table_Name = "C_DocType";

    /** AD_Table_ID=217 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Set Emailtext.
	 * Standardtext bei Email-Versand
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID);

	/**
	 * Get Emailtext.
	 * Standardtext bei Email-Versand
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_BoilerPlate_ID();

    /** Column definition for AD_BoilerPlate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_AD_BoilerPlate_ID = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "AD_BoilerPlate_ID", null);
    /** Column name AD_BoilerPlate_ID */
    public static final String COLUMNNAME_AD_BoilerPlate_ID = "AD_BoilerPlate_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_Client>(I_C_DocType.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_Org>(I_C_DocType.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Druck - Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_PrintFormat_ID();

	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat();

	public void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat);

    /** Column definition for AD_PrintFormat_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_PrintFormat> COLUMN_AD_PrintFormat_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_PrintFormat>(I_C_DocType.class, "AD_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
    /** Column name AD_PrintFormat_ID */
    public static final String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Set Belegart.
	 * Document type or rules
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Document type or rules
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "C_DocType_ID", null);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Difference Document.
	 * Document type for generating in dispute Shipments
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocTypeDifference_ID (int C_DocTypeDifference_ID);

	/**
	 * Get Difference Document.
	 * Document type for generating in dispute Shipments
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocTypeDifference_ID();

	public org.compiere.model.I_C_DocType getC_DocTypeDifference();

	public void setC_DocTypeDifference(org.compiere.model.I_C_DocType C_DocTypeDifference);

    /** Column definition for C_DocTypeDifference_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_C_DocType> COLUMN_C_DocTypeDifference_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_C_DocType>(I_C_DocType.class, "C_DocTypeDifference_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocTypeDifference_ID */
    public static final String COLUMNNAME_C_DocTypeDifference_ID = "C_DocTypeDifference_ID";

	/**
	 * Set Rechnungs-Belegart.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocTypeInvoice_ID (int C_DocTypeInvoice_ID);

	/**
	 * Get Rechnungs-Belegart.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocTypeInvoice_ID();

	public org.compiere.model.I_C_DocType getC_DocTypeInvoice();

	public void setC_DocTypeInvoice(org.compiere.model.I_C_DocType C_DocTypeInvoice);

    /** Column definition for C_DocTypeInvoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_C_DocType> COLUMN_C_DocTypeInvoice_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_C_DocType>(I_C_DocType.class, "C_DocTypeInvoice_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocTypeInvoice_ID */
    public static final String COLUMNNAME_C_DocTypeInvoice_ID = "C_DocTypeInvoice_ID";

	/**
	 * Set Document Type for ProForma.
	 * Document type used for pro forma invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocTypeProforma_ID (int C_DocTypeProforma_ID);

	/**
	 * Get Document Type for ProForma.
	 * Document type used for pro forma invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocTypeProforma_ID();

	public org.compiere.model.I_C_DocType getC_DocTypeProforma();

	public void setC_DocTypeProforma(org.compiere.model.I_C_DocType C_DocTypeProforma);

    /** Column definition for C_DocTypeProforma_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_C_DocType> COLUMN_C_DocTypeProforma_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_C_DocType>(I_C_DocType.class, "C_DocTypeProforma_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocTypeProforma_ID */
    public static final String COLUMNNAME_C_DocTypeProforma_ID = "C_DocTypeProforma_ID";

	/**
	 * Set Document Type for Shipment.
	 * Document type used for shipments generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocTypeShipment_ID (int C_DocTypeShipment_ID);

	/**
	 * Get Document Type for Shipment.
	 * Document type used for shipments generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocTypeShipment_ID();

	public org.compiere.model.I_C_DocType getC_DocTypeShipment();

	public void setC_DocTypeShipment(org.compiere.model.I_C_DocType C_DocTypeShipment);

    /** Column definition for C_DocTypeShipment_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_C_DocType> COLUMN_C_DocTypeShipment_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_C_DocType>(I_C_DocType.class, "C_DocTypeShipment_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocTypeShipment_ID */
    public static final String COLUMNNAME_C_DocTypeShipment_ID = "C_DocTypeShipment_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_User>(I_C_DocType.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Definite Sequence.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDefiniteSequence_ID (int DefiniteSequence_ID);

	/**
	 * Get Definite Sequence.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDefiniteSequence_ID();

	public org.compiere.model.I_AD_Sequence getDefiniteSequence();

	public void setDefiniteSequence(org.compiere.model.I_AD_Sequence DefiniteSequence);

    /** Column definition for DefiniteSequence_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_Sequence> COLUMN_DefiniteSequence_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_Sequence>(I_C_DocType.class, "DefiniteSequence_ID", org.compiere.model.I_AD_Sequence.class);
    /** Column name DefiniteSequence_ID */
    public static final String COLUMNNAME_DefiniteSequence_ID = "DefiniteSequence_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Dokument Basis Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocBaseType (java.lang.String DocBaseType);

	/**
	 * Get Dokument Basis Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocBaseType();

    /** Column definition for DocBaseType */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_DocBaseType = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "DocBaseType", null);
    /** Column name DocBaseType */
    public static final String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Nummernfolgen für Belege.
	 * Document sequence determines the numbering of documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocNoSequence_ID (int DocNoSequence_ID);

	/**
	 * Get Nummernfolgen für Belege.
	 * Document sequence determines the numbering of documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDocNoSequence_ID();

	public org.compiere.model.I_AD_Sequence getDocNoSequence();

	public void setDocNoSequence(org.compiere.model.I_AD_Sequence DocNoSequence);

    /** Column definition for DocNoSequence_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_Sequence> COLUMN_DocNoSequence_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_Sequence>(I_C_DocType.class, "DocNoSequence_ID", org.compiere.model.I_AD_Sequence.class);
    /** Column name DocNoSequence_ID */
    public static final String COLUMNNAME_DocNoSequence_ID = "DocNoSequence_ID";

	/**
	 * Set Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocSubType (java.lang.String DocSubType);

	/**
	 * Get Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocSubType();

    /** Column definition for DocSubType */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_DocSubType = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "DocSubType", null);
    /** Column name DocSubType */
    public static final String COLUMNNAME_DocSubType = "DocSubType";

	/**
	 * Set Kopien.
	 * Number of copies to be printed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocumentCopies (int DocumentCopies);

	/**
	 * Get Kopien.
	 * Number of copies to be printed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDocumentCopies();

    /** Column definition for DocumentCopies */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_DocumentCopies = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "DocumentCopies", null);
    /** Column name DocumentCopies */
    public static final String COLUMNNAME_DocumentCopies = "DocumentCopies";

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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_DocumentNote = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "DocumentNote", null);
    /** Column name DocumentNote */
    public static final String COLUMNNAME_DocumentNote = "DocumentNote";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Hauptbuch - Kategorie.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGL_Category_ID (int GL_Category_ID);

	/**
	 * Get Hauptbuch - Kategorie.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGL_Category_ID();

	public org.compiere.model.I_GL_Category getGL_Category();

	public void setGL_Category(org.compiere.model.I_GL_Category GL_Category);

    /** Column definition for GL_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_GL_Category> COLUMN_GL_Category_ID = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_GL_Category>(I_C_DocType.class, "GL_Category_ID", org.compiere.model.I_GL_Category.class);
    /** Column name GL_Category_ID */
    public static final String COLUMNNAME_GL_Category_ID = "GL_Category_ID";

	/**
	 * Set Charges.
	 * Charges can be added to the document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHasCharges (boolean HasCharges);

	/**
	 * Get Charges.
	 * Charges can be added to the document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHasCharges();

    /** Column definition for HasCharges */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_HasCharges = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "HasCharges", null);
    /** Column name HasCharges */
    public static final String COLUMNNAME_HasCharges = "HasCharges";

	/**
	 * Set Pro forma Invoice.
	 * Indicates if Pro Forma Invoices can be generated from this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHasProforma (boolean HasProforma);

	/**
	 * Get Pro forma Invoice.
	 * Indicates if Pro Forma Invoices can be generated from this document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isHasProforma();

    /** Column definition for HasProforma */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_HasProforma = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "HasProforma", null);
    /** Column name HasProforma */
    public static final String COLUMNNAME_HasProforma = "HasProforma";

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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Copy description to document.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCopyDescriptionToDocument (boolean IsCopyDescriptionToDocument);

	/**
	 * Get Copy description to document.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCopyDescriptionToDocument();

    /** Column definition for IsCopyDescriptionToDocument */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsCopyDescriptionToDocument = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsCopyDescriptionToDocument", null);
    /** Column name IsCopyDescriptionToDocument */
    public static final String COLUMNNAME_IsCopyDescriptionToDocument = "IsCopyDescriptionToDocument";

	/**
	 * Set Create Counter Document.
	 * Create Counter Document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCreateCounter (boolean IsCreateCounter);

	/**
	 * Get Create Counter Document.
	 * Create Counter Document
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCreateCounter();

    /** Column definition for IsCreateCounter */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsCreateCounter = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsCreateCounter", null);
    /** Column name IsCreateCounter */
    public static final String COLUMNNAME_IsCreateCounter = "IsCreateCounter";

	/**
	 * Set Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefault (boolean IsDefault);

	/**
	 * Get Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Default Counter Document.
	 * The document type is the default counter document type
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefaultCounterDoc (boolean IsDefaultCounterDoc);

	/**
	 * Get Default Counter Document.
	 * The document type is the default counter document type
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefaultCounterDoc();

    /** Column definition for IsDefaultCounterDoc */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsDefaultCounterDoc = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsDefaultCounterDoc", null);
    /** Column name IsDefaultCounterDoc */
    public static final String COLUMNNAME_IsDefaultCounterDoc = "IsDefaultCounterDoc";

	/**
	 * Set Document is Number Controlled.
	 * The document has a document sequence
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDocNoControlled (boolean IsDocNoControlled);

	/**
	 * Get Document is Number Controlled.
	 * The document has a document sequence
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDocNoControlled();

    /** Column definition for IsDocNoControlled */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsDocNoControlled = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsDocNoControlled", null);
    /** Column name IsDocNoControlled */
    public static final String COLUMNNAME_IsDocNoControlled = "IsDocNoControlled";

	/**
	 * Set Indexed.
	 * Index the document for the internal search engine
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsIndexed (boolean IsIndexed);

	/**
	 * Get Indexed.
	 * Index the document for the internal search engine
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isIndexed();

    /** Column definition for IsIndexed */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsIndexed = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsIndexed", null);
    /** Column name IsIndexed */
    public static final String COLUMNNAME_IsIndexed = "IsIndexed";

	/**
	 * Set In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInTransit (boolean IsInTransit);

	/**
	 * Get In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInTransit();

    /** Column definition for IsInTransit */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsInTransit = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsInTransit", null);
    /** Column name IsInTransit */
    public static final String COLUMNNAME_IsInTransit = "IsInTransit";

	/**
	 * Set Overwrite Date on Complete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsOverwriteDateOnComplete (boolean IsOverwriteDateOnComplete);

	/**
	 * Get Overwrite Date on Complete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteDateOnComplete();

    /** Column definition for IsOverwriteDateOnComplete */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsOverwriteDateOnComplete = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsOverwriteDateOnComplete", null);
    /** Column name IsOverwriteDateOnComplete */
    public static final String COLUMNNAME_IsOverwriteDateOnComplete = "IsOverwriteDateOnComplete";

	/**
	 * Set Overwrite Sequence on Complete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsOverwriteSeqOnComplete (boolean IsOverwriteSeqOnComplete);

	/**
	 * Get Overwrite Sequence on Complete.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOverwriteSeqOnComplete();

    /** Column definition for IsOverwriteSeqOnComplete */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsOverwriteSeqOnComplete = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsOverwriteSeqOnComplete", null);
    /** Column name IsOverwriteSeqOnComplete */
    public static final String COLUMNNAME_IsOverwriteSeqOnComplete = "IsOverwriteSeqOnComplete";

	/**
	 * Set Pick/QA Confirmation.
	 * Require Pick or QA Confirmation before processing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPickQAConfirm (boolean IsPickQAConfirm);

	/**
	 * Get Pick/QA Confirmation.
	 * Require Pick or QA Confirmation before processing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPickQAConfirm();

    /** Column definition for IsPickQAConfirm */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsPickQAConfirm = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsPickQAConfirm", null);
    /** Column name IsPickQAConfirm */
    public static final String COLUMNNAME_IsPickQAConfirm = "IsPickQAConfirm";

	/**
	 * Set Bestätigung Versand/Wareneingang.
	 * Require Ship or Receipt Confirmation before processing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShipConfirm (boolean IsShipConfirm);

	/**
	 * Get Bestätigung Versand/Wareneingang.
	 * Require Ship or Receipt Confirmation before processing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShipConfirm();

    /** Column definition for IsShipConfirm */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsShipConfirm = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsShipConfirm", null);
    /** Column name IsShipConfirm */
    public static final String COLUMNNAME_IsShipConfirm = "IsShipConfirm";

	/**
	 * Set Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Split when Difference.
	 * Split document when there is a difference
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSplitWhenDifference (boolean IsSplitWhenDifference);

	/**
	 * Get Split when Difference.
	 * Split document when there is a difference
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSplitWhenDifference();

    /** Column definition for IsSplitWhenDifference */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_IsSplitWhenDifference = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "IsSplitWhenDifference", null);
    /** Column name IsSplitWhenDifference */
    public static final String COLUMNNAME_IsSplitWhenDifference = "IsSplitWhenDifference";

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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Drucktext.
	 * The label text to be printed on a document or correspondence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrintName (java.lang.String PrintName);

	/**
	 * Get Drucktext.
	 * The label text to be printed on a document or correspondence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPrintName();

    /** Column definition for PrintName */
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_PrintName = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "PrintName", null);
    /** Column name PrintName */
    public static final String COLUMNNAME_PrintName = "PrintName";

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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_DocType, Object>(I_C_DocType.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_DocType, org.compiere.model.I_AD_User>(I_C_DocType.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
