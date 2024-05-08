package org.compiere.model;


/** Generated Interface for M_AttributeSet
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_AttributeSet 
{

    /** TableName=M_AttributeSet */
    public static final String Table_Name = "M_AttributeSet";

    /** AD_Table_ID=560 */
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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_AD_Client>(I_M_AttributeSet.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_AD_Org>(I_M_AttributeSet.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_AD_User>(I_M_AttributeSet.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Garantie-Datum.
	 * Product has Guarantee or Expiry Date
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsGuaranteeDate (boolean IsGuaranteeDate);

	/**
	 * Get Garantie-Datum.
	 * Product has Guarantee or Expiry Date
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isGuaranteeDate();

    /** Column definition for IsGuaranteeDate */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_IsGuaranteeDate = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "IsGuaranteeDate", null);
    /** Column name IsGuaranteeDate */
    public static final String COLUMNNAME_IsGuaranteeDate = "IsGuaranteeDate";

	/**
	 * Set Garantie-Datum ist Pflicht.
	 * The entry of a Guarantee Date is mandatory when creating a Product Instance
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsGuaranteeDateMandatory (boolean IsGuaranteeDateMandatory);

	/**
	 * Get Garantie-Datum ist Pflicht.
	 * The entry of a Guarantee Date is mandatory when creating a Product Instance
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isGuaranteeDateMandatory();

    /** Column definition for IsGuaranteeDateMandatory */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_IsGuaranteeDateMandatory = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "IsGuaranteeDateMandatory", null);
    /** Column name IsGuaranteeDateMandatory */
    public static final String COLUMNNAME_IsGuaranteeDateMandatory = "IsGuaranteeDateMandatory";

	/**
	 * Set Instanz-Attribut.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInstanceAttribute (boolean IsInstanceAttribute);

	/**
	 * Get Instanz-Attribut.
	 * The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInstanceAttribute();

    /** Column definition for IsInstanceAttribute */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_IsInstanceAttribute = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "IsInstanceAttribute", null);
    /** Column name IsInstanceAttribute */
    public static final String COLUMNNAME_IsInstanceAttribute = "IsInstanceAttribute";

	/**
	 * Set Los.
	 * The product instances have a Lot Number
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsLot (boolean IsLot);

	/**
	 * Get Los.
	 * The product instances have a Lot Number
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isLot();

    /** Column definition for IsLot */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_IsLot = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "IsLot", null);
    /** Column name IsLot */
    public static final String COLUMNNAME_IsLot = "IsLot";

	/**
	 * Set Mandatory Lot.
	 * The entry of Lot info is mandatory when creating a Product Instance
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsLotMandatory (boolean IsLotMandatory);

	/**
	 * Get Mandatory Lot.
	 * The entry of Lot info is mandatory when creating a Product Instance
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isLotMandatory();

    /** Column definition for IsLotMandatory */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_IsLotMandatory = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "IsLotMandatory", null);
    /** Column name IsLotMandatory */
    public static final String COLUMNNAME_IsLotMandatory = "IsLotMandatory";

	/**
	 * Set Serien-Nr..
	 * The product instances have Serial Numbers
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSerNo (boolean IsSerNo);

	/**
	 * Get Serien-Nr..
	 * The product instances have Serial Numbers
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSerNo();

    /** Column definition for IsSerNo */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_IsSerNo = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "IsSerNo", null);
    /** Column name IsSerNo */
    public static final String COLUMNNAME_IsSerNo = "IsSerNo";

	/**
	 * Set Mandatory Serial No.
	 * The entry of a Serial No is mandatory when creating a Product Instance
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSerNoMandatory (boolean IsSerNoMandatory);

	/**
	 * Get Mandatory Serial No.
	 * The entry of a Serial No is mandatory when creating a Product Instance
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSerNoMandatory();

    /** Column definition for IsSerNoMandatory */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_IsSerNoMandatory = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "IsSerNoMandatory", null);
    /** Column name IsSerNoMandatory */
    public static final String COLUMNNAME_IsSerNoMandatory = "IsSerNoMandatory";

	/**
	 * Set Lot Char End Overwrite.
	 * Lot/Batch End Indicator overwrite - default »
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLotCharEOverwrite (java.lang.String LotCharEOverwrite);

	/**
	 * Get Lot Char End Overwrite.
	 * Lot/Batch End Indicator overwrite - default »
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLotCharEOverwrite();

    /** Column definition for LotCharEOverwrite */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_LotCharEOverwrite = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "LotCharEOverwrite", null);
    /** Column name LotCharEOverwrite */
    public static final String COLUMNNAME_LotCharEOverwrite = "LotCharEOverwrite";

	/**
	 * Set Lot Char Start Overwrite.
	 * Lot/Batch Start Indicator overwrite - default «
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLotCharSOverwrite (java.lang.String LotCharSOverwrite);

	/**
	 * Get Lot Char Start Overwrite.
	 * Lot/Batch Start Indicator overwrite - default «
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLotCharSOverwrite();

    /** Column definition for LotCharSOverwrite */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_LotCharSOverwrite = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "LotCharSOverwrite", null);
    /** Column name LotCharSOverwrite */
    public static final String COLUMNNAME_LotCharSOverwrite = "LotCharSOverwrite";

	/**
	 * Set Merkmals-Satz.
	 * Product Attribute Set
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Merkmals-Satz.
	 * Product Attribute Set
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSet_ID();

    /** Column definition for M_AttributeSet_ID */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_M_AttributeSet_ID = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "M_AttributeSet_ID", null);
    /** Column name M_AttributeSet_ID */
    public static final String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

	/**
	 * Set Los-Definition.
	 * Product Lot Control
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_LotCtl_ID (int M_LotCtl_ID);

	/**
	 * Get Los-Definition.
	 * Product Lot Control
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_LotCtl_ID();

	public org.compiere.model.I_M_LotCtl getM_LotCtl();

	public void setM_LotCtl(org.compiere.model.I_M_LotCtl M_LotCtl);

    /** Column definition for M_LotCtl_ID */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_M_LotCtl> COLUMN_M_LotCtl_ID = new org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_M_LotCtl>(I_M_AttributeSet.class, "M_LotCtl_ID", org.compiere.model.I_M_LotCtl.class);
    /** Column name M_LotCtl_ID */
    public static final String COLUMNNAME_M_LotCtl_ID = "M_LotCtl_ID";

	/**
	 * Set Seriennummern-Definition.
	 * Product Serial Number Control
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_SerNoCtl_ID (int M_SerNoCtl_ID);

	/**
	 * Get Seriennummern-Definition.
	 * Product Serial Number Control
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_SerNoCtl_ID();

	public org.compiere.model.I_M_SerNoCtl getM_SerNoCtl();

	public void setM_SerNoCtl(org.compiere.model.I_M_SerNoCtl M_SerNoCtl);

    /** Column definition for M_SerNoCtl_ID */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_M_SerNoCtl> COLUMN_M_SerNoCtl_ID = new org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_M_SerNoCtl>(I_M_AttributeSet.class, "M_SerNoCtl_ID", org.compiere.model.I_M_SerNoCtl.class);
    /** Column name M_SerNoCtl_ID */
    public static final String COLUMNNAME_M_SerNoCtl_ID = "M_SerNoCtl_ID";

	/**
	 * Set Mandatory Type.
	 * The specification of a Product Attribute Instance is mandatory
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMandatoryType (java.lang.String MandatoryType);

	/**
	 * Get Mandatory Type.
	 * The specification of a Product Attribute Instance is mandatory
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMandatoryType();

    /** Column definition for MandatoryType */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_MandatoryType = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "MandatoryType", null);
    /** Column name MandatoryType */
    public static final String COLUMNNAME_MandatoryType = "MandatoryType";

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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set SerNo Char End Overwrite.
	 * Serial Number End Indicator overwrite - default empty
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSerNoCharEOverwrite (java.lang.String SerNoCharEOverwrite);

	/**
	 * Get SerNo Char End Overwrite.
	 * Serial Number End Indicator overwrite - default empty
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSerNoCharEOverwrite();

    /** Column definition for SerNoCharEOverwrite */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_SerNoCharEOverwrite = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "SerNoCharEOverwrite", null);
    /** Column name SerNoCharEOverwrite */
    public static final String COLUMNNAME_SerNoCharEOverwrite = "SerNoCharEOverwrite";

	/**
	 * Set SerNo Char Start Overwrite.
	 * Serial Number Start Indicator overwrite - default #
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSerNoCharSOverwrite (java.lang.String SerNoCharSOverwrite);

	/**
	 * Get SerNo Char Start Overwrite.
	 * Serial Number Start Indicator overwrite - default #
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSerNoCharSOverwrite();

    /** Column definition for SerNoCharSOverwrite */
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_SerNoCharSOverwrite = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "SerNoCharSOverwrite", null);
    /** Column name SerNoCharSOverwrite */
    public static final String COLUMNNAME_SerNoCharSOverwrite = "SerNoCharSOverwrite";

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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_AttributeSet, Object>(I_M_AttributeSet.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_AttributeSet, org.compiere.model.I_AD_User>(I_M_AttributeSet.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
