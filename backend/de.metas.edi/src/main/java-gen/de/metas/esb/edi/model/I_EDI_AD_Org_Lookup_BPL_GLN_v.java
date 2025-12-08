package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EDI_AD_Org_Lookup_BPL_GLN_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_AD_Org_Lookup_BPL_GLN_v 
{

	String Table_Name = "EDI_AD_Org_Lookup_BPL_GLN_v";

//	/** AD_Table_ID=540546 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGLN (@Nullable java.lang.String GLN);

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGLN();

	ModelColumn<I_EDI_AD_Org_Lookup_BPL_GLN_v, Object> COLUMN_GLN = new ModelColumn<>(I_EDI_AD_Org_Lookup_BPL_GLN_v.class, "GLN", null);
	String COLUMNNAME_GLN = "GLN";

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

	ModelColumn<I_EDI_AD_Org_Lookup_BPL_GLN_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_AD_Org_Lookup_BPL_GLN_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";
}
