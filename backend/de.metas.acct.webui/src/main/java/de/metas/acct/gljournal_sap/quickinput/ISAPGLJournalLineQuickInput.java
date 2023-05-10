package de.metas.acct.gljournal_sap.quickinput;

import java.math.BigDecimal;

public interface ISAPGLJournalLineQuickInput
{
	String COLUMNNAME_PostingSign = "PostingSign";

	java.lang.String getPostingSign();
	String COLUMNNAME_GL_Account_ID = "GL_Account_ID";

	int getGL_Account_ID();

	String COLUMNNAME_Amount = "Amount";

	BigDecimal getAmount();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	int getM_SectionCode_ID();

	int getC_Tax_ID();
}
