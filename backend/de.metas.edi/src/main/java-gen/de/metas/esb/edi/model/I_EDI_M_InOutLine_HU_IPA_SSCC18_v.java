package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EDI_M_InOutLine_HU_IPA_SSCC18_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_M_InOutLine_HU_IPA_SSCC18_v 
{

	String Table_Name = "EDI_M_InOutLine_HU_IPA_SSCC18_v";

//	/** AD_Table_ID=540541 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeName (@Nullable java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeName();

	ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_AttributeName = new ModelColumn<>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "AttributeName", null);
	String COLUMNNAME_AttributeName = "AttributeName";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_M_HU_ID = new ModelColumn<>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "M_HU_ID", null);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getValue();

	ModelColumn<I_EDI_M_InOutLine_HU_IPA_SSCC18_v, Object> COLUMN_Value = new ModelColumn<>(I_EDI_M_InOutLine_HU_IPA_SSCC18_v.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
