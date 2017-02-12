package de.metas.fresh.model;


/** Generated Interface for Fresh_QtyOnHand_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_Fresh_QtyOnHand_Line 
{

    /** TableName=Fresh_QtyOnHand_Line */
    public static final String Table_Name = "Fresh_QtyOnHand_Line";

    /** AD_Table_ID=540635 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_AD_Client>(I_Fresh_QtyOnHand_Line.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_AD_Org>(I_Fresh_QtyOnHand_Line.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set ASI Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setASIKey (java.lang.String ASIKey);

	/**
	 * Get ASI Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getASIKey();

    /** Column definition for ASIKey */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_ASIKey = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "ASIKey", null);
    /** Column name ASIKey */
    public static final String COLUMNNAME_ASIKey = "ASIKey";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_AD_User>(I_Fresh_QtyOnHand_Line.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Belegdatum.
	 * Datum des Belegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateDoc();

    /** Column definition for DateDoc */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_DateDoc = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "DateDoc", null);
    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Zählbestand Einkauf (fresh).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFresh_QtyOnHand_ID (int Fresh_QtyOnHand_ID);

	/**
	 * Get Zählbestand Einkauf (fresh).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFresh_QtyOnHand_ID();

	public de.metas.fresh.model.I_Fresh_QtyOnHand getFresh_QtyOnHand();

	public void setFresh_QtyOnHand(de.metas.fresh.model.I_Fresh_QtyOnHand Fresh_QtyOnHand);

    /** Column definition for Fresh_QtyOnHand_ID */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, de.metas.fresh.model.I_Fresh_QtyOnHand> COLUMN_Fresh_QtyOnHand_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, de.metas.fresh.model.I_Fresh_QtyOnHand>(I_Fresh_QtyOnHand_Line.class, "Fresh_QtyOnHand_ID", de.metas.fresh.model.I_Fresh_QtyOnHand.class);
    /** Column name Fresh_QtyOnHand_ID */
    public static final String COLUMNNAME_Fresh_QtyOnHand_ID = "Fresh_QtyOnHand_ID";

	/**
	 * Set Einkauf-Zählbestand Datensatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFresh_QtyOnHand_Line_ID (int Fresh_QtyOnHand_Line_ID);

	/**
	 * Get Einkauf-Zählbestand Datensatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFresh_QtyOnHand_Line_ID();

    /** Column definition for Fresh_QtyOnHand_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_Fresh_QtyOnHand_Line_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "Fresh_QtyOnHand_Line_ID", null);
    /** Column name Fresh_QtyOnHand_Line_ID */
    public static final String COLUMNNAME_Fresh_QtyOnHand_Line_ID = "Fresh_QtyOnHand_Line_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_M_AttributeSetInstance>(I_Fresh_QtyOnHand_Line.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_M_Product>(I_Fresh_QtyOnHand_Line.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Produktionsstätte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPP_Plant_ID (int PP_Plant_ID);

	/**
	 * Get Produktionsstätte.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPP_Plant_ID();

	public org.compiere.model.I_S_Resource getPP_Plant();

	public void setPP_Plant(org.compiere.model.I_S_Resource PP_Plant);

    /** Column definition for PP_Plant_ID */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_ID = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_S_Resource>(I_Fresh_QtyOnHand_Line.class, "PP_Plant_ID", org.compiere.model.I_S_Resource.class);
    /** Column name PP_Plant_ID */
    public static final String COLUMNNAME_PP_Plant_ID = "PP_Plant_ID";

	/**
	 * Set Produktgruppe.
	 * This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setProductGroup (java.lang.String ProductGroup);

	/**
	 * Get Produktgruppe.
	 * This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getProductGroup();

    /** Column definition for ProductGroup */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_ProductGroup = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "ProductGroup", null);
    /** Column name ProductGroup */
    public static final String COLUMNNAME_ProductGroup = "ProductGroup";

	/**
	 * Set Produktname.
	 * This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setProductName (java.lang.String ProductName);

	/**
	 * Get Produktname.
	 * This SQL-column is supposed to be used by the code, so pls don't set Lazy loading to true!
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getProductName();

    /** Column definition for ProductName */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_ProductName = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "ProductName", null);
    /** Column name ProductName */
    public static final String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Zählmenge.
	 * Gezählte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyCount (java.math.BigDecimal QtyCount);

	/**
	 * Get Zählmenge.
	 * Gezählte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCount();

    /** Column definition for QtyCount */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_QtyCount = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "QtyCount", null);
    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, Object>(I_Fresh_QtyOnHand_Line.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_Fresh_QtyOnHand_Line, org.compiere.model.I_AD_User>(I_Fresh_QtyOnHand_Line.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
