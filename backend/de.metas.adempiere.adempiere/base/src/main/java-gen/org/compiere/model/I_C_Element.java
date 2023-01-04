package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Element
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Element 
{

	String Table_Name = "C_Element";

//	/** AD_Table_ID=142 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
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
	 * Set Baum.
	 * Identifies a Tree
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Tree_ID (int AD_Tree_ID);

	/**
	 * Get Baum.
	 * Identifies a Tree
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Tree_ID();

	org.compiere.model.I_AD_Tree getAD_Tree();

	void setAD_Tree(org.compiere.model.I_AD_Tree AD_Tree);

	ModelColumn<I_C_Element, org.compiere.model.I_AD_Tree> COLUMN_AD_Tree_ID = new ModelColumn<>(I_C_Element.class, "AD_Tree_ID", org.compiere.model.I_AD_Tree.class);
	String COLUMNNAME_AD_Tree_ID = "AD_Tree_ID";

	/**
	 * Set Element.
	 * Accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Element_ID (int C_Element_ID);

	/**
	 * Get Element.
	 * Accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Element_ID();

	ModelColumn<I_C_Element, Object> COLUMN_C_Element_ID = new ModelColumn<>(I_C_Element.class, "C_Element_ID", null);
	String COLUMNNAME_C_Element_ID = "C_Element_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Element, Object> COLUMN_Created = new ModelColumn<>(I_C_Element.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_Element, Object> COLUMN_Description = new ModelColumn<>(I_C_Element.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Art.
	 * Element Type (account or user defined)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setElementType (java.lang.String ElementType);

	/**
	 * Get Art.
	 * Element Type (account or user defined)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getElementType();

	ModelColumn<I_C_Element, Object> COLUMN_ElementType = new ModelColumn<>(I_C_Element.class, "ElementType", null);
	String COLUMNNAME_ElementType = "ElementType";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_Element, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Element.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Saldierung.
	 * All transactions within an element value must balance (e.g. cost centers)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBalancing (boolean IsBalancing);

	/**
	 * Get Saldierung.
	 * All transactions within an element value must balance (e.g. cost centers)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBalancing();

	ModelColumn<I_C_Element, Object> COLUMN_IsBalancing = new ModelColumn<>(I_C_Element.class, "IsBalancing", null);
	String COLUMNNAME_IsBalancing = "IsBalancing";

	/**
	 * Set Basiskonto.
	 * The primary natural account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNaturalAccount (boolean IsNaturalAccount);

	/**
	 * Get Basiskonto.
	 * The primary natural account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNaturalAccount();

	ModelColumn<I_C_Element, Object> COLUMN_IsNaturalAccount = new ModelColumn<>(I_C_Element.class, "IsNaturalAccount", null);
	String COLUMNNAME_IsNaturalAccount = "IsNaturalAccount";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_Element, Object> COLUMN_Name = new ModelColumn<>(I_C_Element.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Element, Object> COLUMN_Updated = new ModelColumn<>(I_C_Element.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVFormat (@Nullable java.lang.String VFormat);

	/**
	 * Get Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVFormat();

	ModelColumn<I_C_Element, Object> COLUMN_VFormat = new ModelColumn<>(I_C_Element.class, "VFormat", null);
	String COLUMNNAME_VFormat = "VFormat";
}
