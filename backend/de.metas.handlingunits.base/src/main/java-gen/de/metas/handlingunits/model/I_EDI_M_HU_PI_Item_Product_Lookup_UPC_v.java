package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for EDI_M_HU_PI_Item_Product_Lookup_UPC_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v 
{

	String Table_Name = "EDI_M_HU_PI_Item_Product_Lookup_UPC_v";

//	/** AD_Table_ID=540548 */
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

	ModelColumn<I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v, Object> COLUMN_GLN = new ModelColumn<>(I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v.class, "GLN", null);
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

	ModelColumn<I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

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

	ModelColumn<I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v, Object> COLUMN_StoreGLN = new ModelColumn<>(I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v.class, "StoreGLN", null);
	String COLUMNNAME_StoreGLN = "StoreGLN";

	/**
	 * Set UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC (@Nullable java.lang.String UPC);

	/**
	 * Get UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC();

	ModelColumn<I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v, Object> COLUMN_UPC = new ModelColumn<>(I_EDI_M_HU_PI_Item_Product_Lookup_UPC_v.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";
}
