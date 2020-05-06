package de.metas.order.model;


/** Generated Interface for C_CompensationGroup_SchemaLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_CompensationGroup_SchemaLine 
{

    /** TableName=C_CompensationGroup_SchemaLine */
    public static final String Table_Name = "C_CompensationGroup_SchemaLine";

    /** AD_Table_ID=540941 */
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
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_AD_Client>(I_C_CompensationGroup_SchemaLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_AD_Org>(I_C_CompensationGroup_SchemaLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Break Value.
	 * Low Value of trade discount break level
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBreakValue (java.math.BigDecimal BreakValue);

	/**
	 * Get Break Value.
	 * Low Value of trade discount break level
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBreakValue();

    /** Column definition for BreakValue */
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object> COLUMN_BreakValue = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object>(I_C_CompensationGroup_SchemaLine.class, "BreakValue", null);
    /** Column name BreakValue */
    public static final String COLUMNNAME_BreakValue = "BreakValue";

	/**
	 * Set Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID);

	/**
	 * Get Compensation Group Schema.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_CompensationGroup_Schema_ID();

	public de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema();

	public void setC_CompensationGroup_Schema(de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema);

    /** Column definition for C_CompensationGroup_Schema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, de.metas.order.model.I_C_CompensationGroup_Schema> COLUMN_C_CompensationGroup_Schema_ID = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, de.metas.order.model.I_C_CompensationGroup_Schema>(I_C_CompensationGroup_SchemaLine.class, "C_CompensationGroup_Schema_ID", de.metas.order.model.I_C_CompensationGroup_Schema.class);
    /** Column name C_CompensationGroup_Schema_ID */
    public static final String COLUMNNAME_C_CompensationGroup_Schema_ID = "C_CompensationGroup_Schema_ID";

	/**
	 * Set Compensation Group Schema Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_CompensationGroup_SchemaLine_ID (int C_CompensationGroup_SchemaLine_ID);

	/**
	 * Get Compensation Group Schema Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_CompensationGroup_SchemaLine_ID();

    /** Column definition for C_CompensationGroup_SchemaLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object> COLUMN_C_CompensationGroup_SchemaLine_ID = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object>(I_C_CompensationGroup_SchemaLine.class, "C_CompensationGroup_SchemaLine_ID", null);
    /** Column name C_CompensationGroup_SchemaLine_ID */
    public static final String COLUMNNAME_C_CompensationGroup_SchemaLine_ID = "C_CompensationGroup_SchemaLine_ID";

	/**
	 * Set Gesamtauftragsrabatt %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCompleteOrderDiscount (java.math.BigDecimal CompleteOrderDiscount);

	/**
	 * Get Gesamtauftragsrabatt %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCompleteOrderDiscount();

    /** Column definition for CompleteOrderDiscount */
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object> COLUMN_CompleteOrderDiscount = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object>(I_C_CompensationGroup_SchemaLine.class, "CompleteOrderDiscount", null);
    /** Column name CompleteOrderDiscount */
    public static final String COLUMNNAME_CompleteOrderDiscount = "CompleteOrderDiscount";

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
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object>(I_C_CompensationGroup_SchemaLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_AD_User>(I_C_CompensationGroup_SchemaLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object>(I_C_CompensationGroup_SchemaLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_M_Product>(I_C_CompensationGroup_SchemaLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object>(I_C_CompensationGroup_SchemaLine.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setType (java.lang.String Type);

	/**
	 * Get Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getType();

    /** Column definition for Type */
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object>(I_C_CompensationGroup_SchemaLine.class, "Type", null);
    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

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
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, Object>(I_C_CompensationGroup_SchemaLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_CompensationGroup_SchemaLine, org.compiere.model.I_AD_User>(I_C_CompensationGroup_SchemaLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
