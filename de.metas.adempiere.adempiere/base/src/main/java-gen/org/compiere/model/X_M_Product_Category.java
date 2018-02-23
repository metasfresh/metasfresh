/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Category
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Product_Category extends org.compiere.model.PO implements I_M_Product_Category, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1997853992L;

    /** Standard Constructor */
    public X_M_Product_Category (Properties ctx, int M_Product_Category_ID, String trxName)
    {
      super (ctx, M_Product_Category_ID, trxName);
      /** if (M_Product_Category_ID == 0)
        {
			setIsDefault (false);
			setIsPackagingMaterial (false); // N
			setIsSelfService (true); // Y
			setM_Product_Category_ID (0);
			setMMPolicy (null); // F
			setName (null);
			setPlannedMargin (BigDecimal.ZERO);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_M_Product_Category (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class);
	}

	@Override
	public void setA_Asset_Group(org.compiere.model.I_A_Asset_Group A_Asset_Group)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class, A_Asset_Group);
	}

	/** Set Asset-Gruppe.
		@param A_Asset_Group_ID 
		Group of Assets
	  */
	@Override
	public void setA_Asset_Group_ID (int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
	}

	/** Get Asset-Gruppe.
		@return Group of Assets
	  */
	@Override
	public int getA_Asset_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_PrintColor getAD_PrintColor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintColor_ID, org.compiere.model.I_AD_PrintColor.class);
	}

	@Override
	public void setAD_PrintColor(org.compiere.model.I_AD_PrintColor AD_PrintColor)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintColor_ID, org.compiere.model.I_AD_PrintColor.class, AD_PrintColor);
	}

	/** Set Druck - Farbe.
		@param AD_PrintColor_ID 
		Color used for printing and display
	  */
	@Override
	public void setAD_PrintColor_ID (int AD_PrintColor_ID)
	{
		if (AD_PrintColor_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintColor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintColor_ID, Integer.valueOf(AD_PrintColor_ID));
	}

	/** Get Druck - Farbe.
		@return Color used for printing and display
	  */
	@Override
	public int getAD_PrintColor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintColor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class);
	}

	@Override
	public void setC_CompensationGroup_Schema(de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema)
	{
		set_ValueFromPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class, C_CompensationGroup_Schema);
	}

	/** Set Compensation Group Schema.
		@param C_CompensationGroup_Schema_ID Compensation Group Schema	  */
	@Override
	public void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID)
	{
		if (C_CompensationGroup_Schema_ID < 1) 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_Value (COLUMNNAME_C_CompensationGroup_Schema_ID, Integer.valueOf(C_CompensationGroup_Schema_ID));
	}

	/** Get Compensation Group Schema.
		@return Compensation Group Schema	  */
	@Override
	public int getC_CompensationGroup_Schema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CompensationGroup_Schema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Min. Garantie-Tage.
		@param GuaranteeDaysMin 
		Mindestanzahl Garantie-Tage
	  */
	@Override
	public void setGuaranteeDaysMin (int GuaranteeDaysMin)
	{
		set_Value (COLUMNNAME_GuaranteeDaysMin, Integer.valueOf(GuaranteeDaysMin));
	}

	/** Get Min. Garantie-Tage.
		@return Mindestanzahl Garantie-Tage
	  */
	@Override
	public int getGuaranteeDaysMin () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GuaranteeDaysMin);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verpackungsmaterial.
		@param IsPackagingMaterial Verpackungsmaterial	  */
	@Override
	public void setIsPackagingMaterial (boolean IsPackagingMaterial)
	{
		set_Value (COLUMNNAME_IsPackagingMaterial, Boolean.valueOf(IsPackagingMaterial));
	}

	/** Get Verpackungsmaterial.
		@return Verpackungsmaterial	  */
	@Override
	public boolean isPackagingMaterial () 
	{
		Object oo = get_Value(COLUMNNAME_IsPackagingMaterial);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selbstbedienung.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Selbstbedienung.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Summary Level.
		@param IsSummary 
		This is a summary entity
	  */
	@Override
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Summary Level.
		@return This is a summary entity
	  */
	@Override
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_AttributeSet getM_AttributeSet() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class);
	}

	@Override
	public void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class, M_AttributeSet);
	}

	/** Set Merkmals-Satz.
		@param M_AttributeSet_ID 
		Merkmals-Satz zum Produkt
	  */
	@Override
	public void setM_AttributeSet_ID (int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSet_ID, Integer.valueOf(M_AttributeSet_ID));
	}

	/** Get Merkmals-Satz.
		@return Merkmals-Satz zum Produkt
	  */
	@Override
	public int getM_AttributeSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Produkt Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt Kategorie.
		@return Kategorie eines Produktes
	  */
	@Override
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product_Category getM_Product_Category_Parent() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Category_Parent_ID, org.compiere.model.I_M_Product_Category.class);
	}

	@Override
	public void setM_Product_Category_Parent(org.compiere.model.I_M_Product_Category M_Product_Category_Parent)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Category_Parent_ID, org.compiere.model.I_M_Product_Category.class, M_Product_Category_Parent);
	}

	/** Set Parent Product Category.
		@param M_Product_Category_Parent_ID Parent Product Category	  */
	@Override
	public void setM_Product_Category_Parent_ID (int M_Product_Category_Parent_ID)
	{
		if (M_Product_Category_Parent_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_Parent_ID, Integer.valueOf(M_Product_Category_Parent_ID));
	}

	/** Get Parent Product Category.
		@return Parent Product Category	  */
	@Override
	public int getM_Product_Category_Parent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MMPolicy AD_Reference_ID=335
	 * Reference name: _MMPolicy
	 */
	public static final int MMPOLICY_AD_Reference_ID=335;
	/** LiFo = L */
	public static final String MMPOLICY_LiFo = "L";
	/** FiFo = F */
	public static final String MMPOLICY_FiFo = "F";
	/** Set Materialfluß.
		@param MMPolicy 
		Material Movement Policy
	  */
	@Override
	public void setMMPolicy (java.lang.String MMPolicy)
	{

		set_Value (COLUMNNAME_MMPolicy, MMPolicy);
	}

	/** Get Materialfluß.
		@return Material Movement Policy
	  */
	@Override
	public java.lang.String getMMPolicy () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MMPolicy);
	}

	/** 
	 * MRP_Exclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int MRP_EXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String MRP_EXCLUDE_Yes = "Y";
	/** No = N */
	public static final String MRP_EXCLUDE_No = "N";
	/** Set MRP ausschliessen.
		@param MRP_Exclude MRP ausschliessen	  */
	@Override
	public void setMRP_Exclude (java.lang.String MRP_Exclude)
	{

		set_Value (COLUMNNAME_MRP_Exclude, MRP_Exclude);
	}

	/** Get MRP ausschliessen.
		@return MRP ausschliessen	  */
	@Override
	public java.lang.String getMRP_Exclude () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MRP_Exclude);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set DB1 %.
		@param PlannedMargin 
		Project's planned margin as a percentage
	  */
	@Override
	public void setPlannedMargin (java.math.BigDecimal PlannedMargin)
	{
		set_Value (COLUMNNAME_PlannedMargin, PlannedMargin);
	}

	/** Get DB1 %.
		@return Project's planned margin as a percentage
	  */
	@Override
	public java.math.BigDecimal getPlannedMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PlannedMargin);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}