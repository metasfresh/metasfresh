package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EDI_C_BPartner_Lookup_BPL_GLN_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_C_BPartner_Lookup_BPL_GLN_v 
{

	String Table_Name = "EDI_C_BPartner_Lookup_BPL_GLN_v";

//	/** AD_Table_ID=540552 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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

	ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_GLN = new ModelColumn<>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "GLN", null);
	String COLUMNNAME_GLN = "GLN";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lookup Label.
	 * Can be used to differentiate when different data records otherwise have the same lookup characteristics.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLookup_Label (@Nullable java.lang.String Lookup_Label);

	/**
	 * Get Lookup Label.
	 * Can be used to differentiate when different data records otherwise have the same lookup characteristics.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLookup_Label();

	ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_Lookup_Label = new ModelColumn<>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "Lookup_Label", null);
	String COLUMNNAME_Lookup_Label = "Lookup_Label";

	/**
	 * Set StoreGLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStoreGLN (@Nullable java.lang.String StoreGLN);

	/**
	 * Get StoreGLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStoreGLN();

	ModelColumn<I_EDI_C_BPartner_Lookup_BPL_GLN_v, Object> COLUMN_StoreGLN = new ModelColumn<>(I_EDI_C_BPartner_Lookup_BPL_GLN_v.class, "StoreGLN", null);
	String COLUMNNAME_StoreGLN = "StoreGLN";
}
