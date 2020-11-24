package de.metas.printing.model;


/** Generated Interface for C_Print_Package
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Print_Package 
{

    /** TableName=C_Print_Package */
    public static final String Table_Name = "C_Print_Package";

    /** AD_Table_ID=540458 */
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
	 * Set Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Session_ID (int AD_Session_ID);

	/**
	 * Get Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Session_ID();

	public org.compiere.model.I_AD_Session getAD_Session();

	public void setAD_Session(org.compiere.model.I_AD_Session AD_Session);

    /** Column definition for AD_Session_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, org.compiere.model.I_AD_Session> COLUMN_AD_Session_ID = new org.adempiere.model.ModelColumn<I_C_Print_Package, org.compiere.model.I_AD_Session>(I_C_Print_Package.class, "AD_Session_ID", org.compiere.model.I_AD_Session.class);
    /** Column name AD_Session_ID */
    public static final String COLUMNNAME_AD_Session_ID = "AD_Session_ID";

	/**
	 * Set Binärformat.
	 * Binary format of the print package (e.g. postscript vs pdf)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBinaryFormat (java.lang.String BinaryFormat);

	/**
	 * Get Binärformat.
	 * Binary format of the print package (e.g. postscript vs pdf)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBinaryFormat();

    /** Column definition for BinaryFormat */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, Object> COLUMN_BinaryFormat = new org.adempiere.model.ModelColumn<I_C_Print_Package, Object>(I_C_Print_Package.class, "BinaryFormat", null);
    /** Column name BinaryFormat */
    public static final String COLUMNNAME_BinaryFormat = "BinaryFormat";

	/**
	 * Set Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCopies (int Copies);

	/**
	 * Get Kopien.
	 * Anzahl der zu erstellenden/zu druckenden Exemplare
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCopies();

    /** Column definition for Copies */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, Object> COLUMN_Copies = new org.adempiere.model.ModelColumn<I_C_Print_Package, Object>(I_C_Print_Package.class, "Copies", null);
    /** Column name Copies */
    public static final String COLUMNNAME_Copies = "Copies";

	/**
	 * Set Druck-Job Anweisung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Job_Instructions_ID (int C_Print_Job_Instructions_ID);

	/**
	 * Get Druck-Job Anweisung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Job_Instructions_ID();

	public de.metas.printing.model.I_C_Print_Job_Instructions getC_Print_Job_Instructions();

	public void setC_Print_Job_Instructions(de.metas.printing.model.I_C_Print_Job_Instructions C_Print_Job_Instructions);

    /** Column definition for C_Print_Job_Instructions_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, de.metas.printing.model.I_C_Print_Job_Instructions> COLUMN_C_Print_Job_Instructions_ID = new org.adempiere.model.ModelColumn<I_C_Print_Package, de.metas.printing.model.I_C_Print_Job_Instructions>(I_C_Print_Package.class, "C_Print_Job_Instructions_ID", de.metas.printing.model.I_C_Print_Job_Instructions.class);
    /** Column name C_Print_Job_Instructions_ID */
    public static final String COLUMNNAME_C_Print_Job_Instructions_ID = "C_Print_Job_Instructions_ID";

	/**
	 * Set Druckpaket.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Print_Package_ID (int C_Print_Package_ID);

	/**
	 * Get Druckpaket.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Print_Package_ID();

    /** Column definition for C_Print_Package_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, Object> COLUMN_C_Print_Package_ID = new org.adempiere.model.ModelColumn<I_C_Print_Package, Object>(I_C_Print_Package.class, "C_Print_Package_ID", null);
    /** Column name C_Print_Package_ID */
    public static final String COLUMNNAME_C_Print_Package_ID = "C_Print_Package_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Print_Package, Object>(I_C_Print_Package.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Print_Package, Object>(I_C_Print_Package.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Anz. Druckpaket-Infos.
	 * Number of different package infos for a given print package.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setPackageInfoCount (int PackageInfoCount);

	/**
	 * Get Anz. Druckpaket-Infos.
	 * Number of different package infos for a given print package.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getPackageInfoCount();

    /** Column definition for PackageInfoCount */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, Object> COLUMN_PackageInfoCount = new org.adempiere.model.ModelColumn<I_C_Print_Package, Object>(I_C_Print_Package.class, "PackageInfoCount", null);
    /** Column name PackageInfoCount */
    public static final String COLUMNNAME_PackageInfoCount = "PackageInfoCount";

	/**
	 * Set Seitenzahl.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPageCount (int PageCount);

	/**
	 * Get Seitenzahl.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPageCount();

    /** Column definition for PageCount */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, Object> COLUMN_PageCount = new org.adempiere.model.ModelColumn<I_C_Print_Package, Object>(I_C_Print_Package.class, "PageCount", null);
    /** Column name PageCount */
    public static final String COLUMNNAME_PageCount = "PageCount";

	/**
	 * Set Transaktions-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTransactionID (java.lang.String TransactionID);

	/**
	 * Get Transaktions-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTransactionID();

    /** Column definition for TransactionID */
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, Object> COLUMN_TransactionID = new org.adempiere.model.ModelColumn<I_C_Print_Package, Object>(I_C_Print_Package.class, "TransactionID", null);
    /** Column name TransactionID */
    public static final String COLUMNNAME_TransactionID = "TransactionID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Print_Package, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Print_Package, Object>(I_C_Print_Package.class, "Updated", null);
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
