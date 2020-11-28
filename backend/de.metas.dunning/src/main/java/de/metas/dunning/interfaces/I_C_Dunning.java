package de.metas.dunning.interfaces;

public interface I_C_Dunning extends org.compiere.model.I_C_Dunning
{
	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	int getC_Currency_ID();

	void setC_Currency_ID(int C_Currency_ID);

	/** Column name GraceDays */
	String COLUMNNAME_GraceDays = "GraceDays";

	/**
	 * Set Tage Frist. Tage nach Fälligkeitstermin bevor erste Mahnung versandt wird
	 */
	void setGraceDays(int GraceDays);

	/**
	 * Get Tage Frist. Tage nach Fälligkeitstermin bevor erste Mahnung versandt wird
	 */
	int getGraceDays();

	String COLUMNNAME_IsManageDunnableDocGraceDate = "IsManageDunnableDocGraceDate";

	void setIsManageDunnableDocGraceDate(boolean IsManageDunnableDocGraceDate);

	boolean isManageDunnableDocGraceDate();
}
