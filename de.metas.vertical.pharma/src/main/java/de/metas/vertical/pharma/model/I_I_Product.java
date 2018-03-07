package de.metas.vertical.pharma.model;

import java.math.BigDecimal;

public interface I_I_Product  extends org.compiere.model.I_I_Product
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

	public static final String COLUMNNAME_PharmaProductCategory_Value = "PharmaProductCategory_Value";
	public void setPharmaProductCategory_Value(String PharmaProductCategory_Value);
	public String getPharmaProductCategory_Value();

	public static final String COLUMNNAME_M_PharmaProductCategory_ID = "M_PharmaProductCategory_ID";
	public void setM_PharmaProductCategory_ID(int M_PharmaProductCategory_ID);
	public int getM_PharmaProductCategory_ID();

	public static final String COLUMNNAME_AEP_Price_List_ID = "AEP_Price_List_ID";
	public void setAEP_Price_List_ID(int AEP_Price_List_ID);
	public int getAEP_Price_List_ID();
	public org.compiere.model.I_M_PriceList getAEP_Price_List();

	public static final String COLUMNNAME_A01AEP = "A01AEP";
	public void setA01AEP(BigDecimal A01AEP);
	public BigDecimal getA01AEP();

	public static final String COLUMNNAME_APU_Price_List_ID = "APU_Price_List_ID";
	public void setAPU_Price_List_ID(int APU_Price_List_ID);
	public int getAPU_Price_List_ID();
	public org.compiere.model.I_M_PriceList getAPU_Price_List();

	public static final String COLUMNNAME_A01APU = "A01APU";
	public void setA01APU(BigDecimal A01APU);
	public BigDecimal getA01APU();
}
