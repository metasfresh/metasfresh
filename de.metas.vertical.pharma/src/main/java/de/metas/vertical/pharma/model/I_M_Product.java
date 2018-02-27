package de.metas.vertical.pharma.model;


public interface I_M_Product extends org.compiere.model.I_M_Product
{
	public static final String COLUMNNAME_M_DosageForm_ID = "M_DosageForm_ID";
	public void setM_DosageForm_ID(int M_DosageForm_ID);
	public int getM_DosageForm_ID();

    public static final String COLUMNNAME_IsPrescription = "IsPrescription";
    public void setIsPrescription(boolean IsPrescription);
	public boolean isPrescription();

	public static final String COLUMNNAME_IsColdChain = "IsColdChain";
    public void setIsColdChain(boolean IsColdChain);
	public boolean isColdChain();

	public static final String COLUMNNAME_IsNarcotic = "IsNarcotic";
    public void setIsNarcotic(boolean IsNarcotic);
	public boolean isNarcotic();

	public static final String COLUMNNAME_IsTFG = "IsTFG";
    public void setIsTFG(boolean IsTFG);
	public boolean isTFG();

	public static final String COLUMNNAME_M_Indication_ID = "M_Indication_ID";
	public void setM_Indication_ID(int M_Indication_ID);
	public int getM_Indication_ID();

	public static final String COLUMNNAME_FAM_ZUB = "FAM_ZUB";
	public void setFAM_ZUB (String FAM_ZUB);
	public String getFAM_ZUB();
}
