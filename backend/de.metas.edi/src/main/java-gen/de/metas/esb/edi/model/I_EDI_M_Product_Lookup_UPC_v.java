package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EDI_M_Product_Lookup_UPC_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_M_Product_Lookup_UPC_v 
{

	String Table_Name = "EDI_M_Product_Lookup_UPC_v";

//	/** AD_Table_ID=540547 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


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

	ModelColumn<I_EDI_M_Product_Lookup_UPC_v, Object> COLUMN_GLN = new ModelColumn<>(I_EDI_M_Product_Lookup_UPC_v.class, "GLN", null);
	String COLUMNNAME_GLN = "GLN";
}
