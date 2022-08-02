package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

public interface I_C_InterimInvoice_FlatrateTerm extends org.compiere.model.I_C_InterimInvoice_FlatrateTerm
{
	// code formatter will be off to maintain aspect

	// @formatter:off
	String COLUMNNAME_C_FlatrateTerm_ID = "C_FlatrateTerm_ID";
	ModelColumn<I_C_InterimInvoice_FlatrateTerm, I_C_Flatrate_Term> COLUMN_C_FlatrateTerm_ID = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm.class, "C_FlatrateTerm_ID", I_C_Flatrate_Term.class);

	void setC_FlatrateTerm_ID(int C_FlatrateTerm_ID);
	int getC_FlatrateTerm_ID();
	void setC_FlatrateTerm(I_C_Flatrate_Term C_Flatrate_Term);
	I_C_Flatrate_Term getC_FlatrateTerm();
	// @formatter:on
}
