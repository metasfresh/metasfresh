package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BPartner_Recent_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_Recent_V 
{

	String Table_Name = "C_BPartner_Recent_V";

//	/** AD_Table_ID=541370 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
	 * Set C_BPartner_Recent_V_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Recent_V_ID (int C_BPartner_Recent_V_ID);

	/**
	 * Get C_BPartner_Recent_V_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Recent_V_ID();

	ModelColumn<I_C_BPartner_Recent_V, Object> COLUMN_C_BPartner_Recent_V_ID = new ModelColumn<>(I_C_BPartner_Recent_V.class, "C_BPartner_Recent_V_ID", null);
	String COLUMNNAME_C_BPartner_Recent_V_ID = "C_BPartner_Recent_V_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_Recent_V, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_Recent_V.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";
}
