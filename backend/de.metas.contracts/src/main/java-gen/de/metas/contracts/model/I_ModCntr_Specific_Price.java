package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ModCntr_Specific_Price
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_Specific_Price 
{

	String Table_Name = "ModCntr_Specific_Price";

//	/** AD_Table_ID=542405 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term();

	void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term);

	ModelColumn<I_ModCntr_Specific_Price, de.metas.contracts.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_ModCntr_Specific_Price.class, "C_Flatrate_Term_ID", de.metas.contracts.model.I_C_Flatrate_Term.class);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Modules.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Module_ID (int ModCntr_Module_ID);

	/**
	 * Get Modules.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Module_ID();

	de.metas.contracts.model.I_ModCntr_Module getModCntr_Module();

	void setModCntr_Module(de.metas.contracts.model.I_ModCntr_Module ModCntr_Module);

	ModelColumn<I_ModCntr_Specific_Price, de.metas.contracts.model.I_ModCntr_Module> COLUMN_ModCntr_Module_ID = new ModelColumn<>(I_ModCntr_Specific_Price.class, "ModCntr_Module_ID", de.metas.contracts.model.I_ModCntr_Module.class);
	String COLUMNNAME_ModCntr_Module_ID = "ModCntr_Module_ID";
}
