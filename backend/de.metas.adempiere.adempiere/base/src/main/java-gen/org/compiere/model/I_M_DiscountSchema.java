package org.compiere.model;

<<<<<<< HEAD

/** Generated Interface for M_DiscountSchema
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_DiscountSchema 
{

    /** TableName=M_DiscountSchema */
    public static final String Table_Name = "M_DiscountSchema";

    /** AD_Table_ID=475 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
=======
import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_DiscountSchema
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_DiscountSchema 
{

	String Table_Name = "M_DiscountSchema";

//	/** AD_Table_ID=475 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_Client>(I_M_DiscountSchema.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_Org>(I_M_DiscountSchema.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Merkmal.
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Break Value Attribute.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setBreakValue_Attribute_ID (int BreakValue_Attribute_ID);

	/**
	 * Get Merkmal.
=======
	void setBreakValue_Attribute_ID (int BreakValue_Attribute_ID);

	/**
	 * Get Break Value Attribute.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getBreakValue_Attribute_ID();

	public org.compiere.model.I_M_Attribute getBreakValue_Attribute();

	public void setBreakValue_Attribute(org.compiere.model.I_M_Attribute BreakValue_Attribute);

    /** Column definition for BreakValue_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_M_Attribute> COLUMN_BreakValue_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_M_Attribute>(I_M_DiscountSchema.class, "BreakValue_Attribute_ID", org.compiere.model.I_M_Attribute.class);
    /** Column name BreakValue_Attribute_ID */
    public static final String COLUMNNAME_BreakValue_Attribute_ID = "BreakValue_Attribute_ID";
=======
	int getBreakValue_Attribute_ID();

	String COLUMNNAME_BreakValue_Attribute_ID = "BreakValue_Attribute_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Break Value Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setBreakValueType (java.lang.String BreakValueType);
=======
	void setBreakValueType (java.lang.String BreakValueType);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Break Value Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getBreakValueType();

    /** Column definition for BreakValueType */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_BreakValueType = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "BreakValueType", null);
    /** Column name BreakValueType */
    public static final String COLUMNNAME_BreakValueType = "BreakValueType";

	/**
	 * Get Erstellt.
=======
	java.lang.String getBreakValueType();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_BreakValueType = new ModelColumn<>(I_M_DiscountSchema.class, "BreakValueType", null);
	String COLUMNNAME_BreakValueType = "BreakValueType";

	/**
	 * Get Created.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Created = new ModelColumn<>(I_M_DiscountSchema.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_User>(I_M_DiscountSchema.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Anwenden auf.
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Accumulation Level.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Level for accumulative calculations
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setCumulativeLevel (java.lang.String CumulativeLevel);

	/**
	 * Get Anwenden auf.
=======
	void setCumulativeLevel (@Nullable java.lang.String CumulativeLevel);

	/**
	 * Get Accumulation Level.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Level for accumulative calculations
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getCumulativeLevel();

    /** Column definition for CumulativeLevel */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_CumulativeLevel = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "CumulativeLevel", null);
    /** Column name CumulativeLevel */
    public static final String COLUMNNAME_CumulativeLevel = "CumulativeLevel";

	/**
	 * Set Beschreibung.
=======
	@Nullable java.lang.String getCumulativeLevel();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_CumulativeLevel = new ModelColumn<>(I_M_DiscountSchema.class, "CumulativeLevel", null);
	String COLUMNNAME_CumulativeLevel = "CumulativeLevel";

	/**
	 * Set Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
=======
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Rabatt Art.
=======
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Description = new ModelColumn<>(I_M_DiscountSchema.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Discount Type.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Type of trade discount calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDiscountType (java.lang.String DiscountType);

	/**
	 * Get Rabatt Art.
=======
	void setDiscountType (java.lang.String DiscountType);

	/**
	 * Get Discount Type.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Type of trade discount calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getDiscountType();

    /** Column definition for DiscountType */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_DiscountType = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "DiscountType", null);
    /** Column name DiscountType */
    public static final String COLUMNNAME_DiscountType = "DiscountType";

	/**
	 * Set Fester Rabatt %.
=======
	java.lang.String getDiscountType();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_DiscountType = new ModelColumn<>(I_M_DiscountSchema.class, "DiscountType", null);
	String COLUMNNAME_DiscountType = "DiscountType";

	/**
	 * Set Retain zero prices.
	 * Do not change prices equal to 0 when updating (derivative) price lists.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDoNotChangeZeroPrices (boolean DoNotChangeZeroPrices);

	/**
	 * Get Retain zero prices.
	 * Do not change prices equal to 0 when updating (derivative) price lists.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDoNotChangeZeroPrices();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_DoNotChangeZeroPrices = new ModelColumn<>(I_M_DiscountSchema.class, "DoNotChangeZeroPrices", null);
	String COLUMNNAME_DoNotChangeZeroPrices = "DoNotChangeZeroPrices";

	/**
	 * Set Flat Discount %.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setFlatDiscount (java.math.BigDecimal FlatDiscount);

	/**
	 * Get Fester Rabatt %.
=======
	void setFlatDiscount (@Nullable BigDecimal FlatDiscount);

	/**
	 * Get Flat Discount %.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getFlatDiscount();

    /** Column definition for FlatDiscount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_FlatDiscount = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "FlatDiscount", null);
    /** Column name FlatDiscount */
    public static final String COLUMNNAME_FlatDiscount = "FlatDiscount";

	/**
	 * Set Aktiv.
=======
	BigDecimal getFlatDiscount();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_FlatDiscount = new ModelColumn<>(I_M_DiscountSchema.class, "FlatDiscount", null);
	String COLUMNNAME_FlatDiscount = "FlatDiscount";

	/**
	 * Set Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
=======
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fester Rabatt.
=======
	boolean isActive();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_IsActive = new ModelColumn<>(I_M_DiscountSchema.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Flat Discount.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Use flat discount defined on Business Partner Level
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsBPartnerFlatDiscount (boolean IsBPartnerFlatDiscount);

	/**
	 * Get Fester Rabatt.
=======
	void setIsBPartnerFlatDiscount (boolean IsBPartnerFlatDiscount);

	/**
	 * Get Flat Discount.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Use flat discount defined on Business Partner Level
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isBPartnerFlatDiscount();

    /** Column definition for IsBPartnerFlatDiscount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_IsBPartnerFlatDiscount = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "IsBPartnerFlatDiscount", null);
    /** Column name IsBPartnerFlatDiscount */
    public static final String COLUMNNAME_IsBPartnerFlatDiscount = "IsBPartnerFlatDiscount";

	/**
	 * Set Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
=======
	boolean isBPartnerFlatDiscount();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_IsBPartnerFlatDiscount = new ModelColumn<>(I_M_DiscountSchema.class, "IsBPartnerFlatDiscount", null);
	String COLUMNNAME_IsBPartnerFlatDiscount = "IsBPartnerFlatDiscount";

	/**
	 * Set Price Schema - Calculated Surcharge.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchema_Calculated_Surcharge_ID (int M_DiscountSchema_Calculated_Surcharge_ID);

	/**
	 * Get Price Schema - Calculated Surcharge.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchema_Calculated_Surcharge_ID();

	@Nullable org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge getM_DiscountSchema_Calculated_Surcharge();

	void setM_DiscountSchema_Calculated_Surcharge(@Nullable org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge M_DiscountSchema_Calculated_Surcharge);

	ModelColumn<I_M_DiscountSchema, org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge> COLUMN_M_DiscountSchema_Calculated_Surcharge_ID = new ModelColumn<>(I_M_DiscountSchema.class, "M_DiscountSchema_Calculated_Surcharge_ID", org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge.class);
	String COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID = "M_DiscountSchema_Calculated_Surcharge_ID";

	/**
	 * Set Discount Schema.
	 * Schema to calculate the trade discount percentage
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
=======
	void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Discount Schema.
	 * Schema to calculate the trade discount percentage
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getM_DiscountSchema_ID();

    /** Column definition for M_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_M_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "M_DiscountSchema_ID", null);
    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
=======
	int getM_DiscountSchema_ID();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_M_DiscountSchema_ID = new ModelColumn<>(I_M_DiscountSchema.class, "M_DiscountSchema_ID", null);
	String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Name.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
=======
	void setName (java.lang.String Name);

	/**
	 * Get Name.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Verarbeiten.
=======
	java.lang.String getName();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Name = new ModelColumn<>(I_M_DiscountSchema.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Process Now.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
=======
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Skript.
=======
	boolean isProcessing();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Processing = new ModelColumn<>(I_M_DiscountSchema.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Script.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setScript (java.lang.String Script);

	/**
	 * Get Skript.
=======
	void setScript (@Nullable java.lang.String Script);

	/**
	 * Get Script.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getScript();

    /** Column definition for Script */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Script = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Script", null);
    /** Column name Script */
    public static final String COLUMNNAME_Script = "Script";

	/**
	 * Get Aktualisiert.
=======
	@Nullable java.lang.String getScript();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Script = new ModelColumn<>(I_M_DiscountSchema.class, "Script", null);
	String COLUMNNAME_Script = "Script";

	/**
	 * Get Updated.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_Updated = new ModelColumn<>(I_M_DiscountSchema.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, org.compiere.model.I_AD_User>(I_M_DiscountSchema.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Valid from including this date (first day)
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Valid From.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Valid from including this date (first day)
=======
	void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_M_DiscountSchema, Object>(I_M_DiscountSchema.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";
=======
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_M_DiscountSchema, Object> COLUMN_ValidFrom = new ModelColumn<>(I_M_DiscountSchema.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
