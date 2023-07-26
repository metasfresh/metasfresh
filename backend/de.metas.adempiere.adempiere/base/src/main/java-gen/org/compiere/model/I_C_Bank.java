package org.compiere.model;


/** Generated Interface for C_Bank
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Bank 
{

    /** TableName=C_Bank */
    public static final String Table_Name = "C_Bank";

    /** AD_Table_ID=296 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
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
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Bank.
	 * Bank
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Bank_ID (int C_Bank_ID);

	/**
	 * Get Bank.
	 * Bank
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Bank_ID();

    /** Column definition for C_Bank_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_C_Bank_ID = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "C_Bank_ID", null);
    /** Column name C_Bank_ID */
    public static final String COLUMNNAME_C_Bank_ID = "C_Bank_ID";

	/**
	 * Set Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Location_ID();

	public org.compiere.model.I_C_Location getC_Location();

	public void setC_Location(org.compiere.model.I_C_Location C_Location);

    /** Column definition for C_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new org.adempiere.model.ModelColumn<I_C_Bank, org.compiere.model.I_C_Location>(I_C_Bank.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
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
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Bank ist die Postfinance-Bank.
	 * Markiert die Postfinanz-Bank. Hinweis: auch andere Banken können am ESR-Verfahren teilnehmen, ohne selbst die Postfinance-Bank zu sein.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setESR_PostBank (boolean ESR_PostBank);

	/**
	 * Get Bank ist die Postfinance-Bank.
	 * Markiert die Postfinanz-Bank. Hinweis: auch andere Banken können am ESR-Verfahren teilnehmen, ohne selbst die Postfinance-Bank zu sein.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isESR_PostBank();

    /** Column definition for ESR_PostBank */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_ESR_PostBank = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "ESR_PostBank", null);
    /** Column name ESR_PostBank */
    public static final String COLUMNNAME_ESR_PostBank = "ESR_PostBank";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Cash Bank.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCashBank (boolean IsCashBank);

	/**
	 * Get Cash Bank.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCashBank();

    /** Column definition for IsCashBank */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_IsCashBank = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "IsCashBank", null);
    /** Column name IsCashBank */
    public static final String COLUMNNAME_IsCashBank = "IsCashBank";

	/**
	 * Set Own Bank.
	 * Bank for this Organization
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsOwnBank (boolean IsOwnBank);

	/**
	 * Get Own Bank.
	 * Bank for this Organization
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isOwnBank();

    /** Column definition for IsOwnBank */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_IsOwnBank = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "IsOwnBank", null);
    /** Column name IsOwnBank */
    public static final String COLUMNNAME_IsOwnBank = "IsOwnBank";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRoutingNo (java.lang.String RoutingNo);

	/**
	 * Get BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRoutingNo();

    /** Column definition for RoutingNo */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_RoutingNo = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "RoutingNo", null);
    /** Column name RoutingNo */
    public static final String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Set Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSwiftCode (java.lang.String SwiftCode);

	/**
	 * Get Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSwiftCode();

    /** Column definition for SwiftCode */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_SwiftCode = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "SwiftCode", null);
    /** Column name SwiftCode */
    public static final String COLUMNNAME_SwiftCode = "SwiftCode";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Bank, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Bank, Object>(I_C_Bank.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
