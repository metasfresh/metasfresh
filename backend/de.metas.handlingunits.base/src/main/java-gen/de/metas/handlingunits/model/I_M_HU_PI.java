package de.metas.handlingunits.model;


/** Generated Interface for M_HU_PI
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_PI 
{

    /** TableName=M_HU_PI */
    public static final String Table_Name = "M_HU_PI";

    /** AD_Table_ID=540511 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_PI, Object>(I_M_HU_PI.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_HU_PI, Object>(I_M_HU_PI.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_PI, Object>(I_M_HU_PI.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Default For Picking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDefaultForPicking (boolean IsDefaultForPicking);

	/**
	 * Get Default For Picking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDefaultForPicking();

    /** Column definition for IsDefaultForPicking */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI, Object> COLUMN_IsDefaultForPicking = new org.adempiere.model.ModelColumn<I_M_HU_PI, Object>(I_M_HU_PI.class, "IsDefaultForPicking", null);
    /** Column name IsDefaultForPicking */
    public static final String COLUMNNAME_IsDefaultForPicking = "IsDefaultForPicking";

	/**
	 * Set Standard-LU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefaultLU (boolean IsDefaultLU);

	/**
	 * Get Standard-LU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefaultLU();

    /** Column definition for IsDefaultLU */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI, Object> COLUMN_IsDefaultLU = new org.adempiere.model.ModelColumn<I_M_HU_PI, Object>(I_M_HU_PI.class, "IsDefaultLU", null);
    /** Column name IsDefaultLU */
    public static final String COLUMNNAME_IsDefaultLU = "IsDefaultLU";

	/**
	 * Set Packvorschrift.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_ID (int M_HU_PI_ID);

	/**
	 * Get Packvorschrift.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_ID();

    /** Column definition for M_HU_PI_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI, Object> COLUMN_M_HU_PI_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI, Object>(I_M_HU_PI.class, "M_HU_PI_ID", null);
    /** Column name M_HU_PI_ID */
    public static final String COLUMNNAME_M_HU_PI_ID = "M_HU_PI_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_HU_PI, Object>(I_M_HU_PI.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_PI, Object>(I_M_HU_PI.class, "Updated", null);
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
