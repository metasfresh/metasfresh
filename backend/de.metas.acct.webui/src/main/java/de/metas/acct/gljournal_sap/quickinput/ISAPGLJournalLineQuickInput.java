package de.metas.acct.gljournal_sap.quickinput;

import java.math.BigDecimal;

public interface ISAPGLJournalLineQuickInput
{
	String COLUMNNAME_PostingSign = "PostingSign";

	java.lang.String getPostingSign();

	String COLUMNNAME_C_ValidCombination_ID = "C_ValidCombination_ID";

	int getC_ValidCombination_ID();

	String COLUMNNAME_Amount = "Amount";

	BigDecimal getAmount();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	int getC_Tax_ID();
}
