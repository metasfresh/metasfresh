/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_PI_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_PI_Attribute extends org.compiere.model.PO implements I_M_HU_PI_Attribute, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1396551732L;

    /** Standard Constructor */
    public X_M_HU_PI_Attribute (Properties ctx, int M_HU_PI_Attribute_ID, String trxName)
    {
      super (ctx, M_HU_PI_Attribute_ID, trxName);
      /** if (M_HU_PI_Attribute_ID == 0)
        {
			setHU_TansferStrategy_JavaClass_ID (0);
			setIsDisplayed (true); // Y
			setIsOnlyIfInProductAttributeSet (false); // N
			setIsReadOnly (false); // N
			setM_Attribute_ID (0);
			setM_HU_PI_Attribute_ID (0);
			setM_HU_PI_Version_ID (0);
			setPropagationType (null); // NONE
			setUseInASI (true); // Y
        } */
    }

    /** Load Constructor */
    public X_M_HU_PI_Attribute (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Aggregation Strategy.
		@param AggregationStrategy_JavaClass_ID Aggregation Strategy	  */
	@Override
	public void setAggregationStrategy_JavaClass_ID (int AggregationStrategy_JavaClass_ID)
	{
		if (AggregationStrategy_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_AggregationStrategy_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_AggregationStrategy_JavaClass_ID, Integer.valueOf(AggregationStrategy_JavaClass_ID));
	}

	/** Get Aggregation Strategy.
		@return Aggregation Strategy	  */
	@Override
	public int getAggregationStrategy_JavaClass_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AggregationStrategy_JavaClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

//	@Override
//	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
//	{
//		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
//	}
//
//	@Override
//	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
//	{
//		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
//	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HU Transfer Attribute Strategy.
		@param HU_TansferStrategy_JavaClass_ID 
		Strategy used for transferring an attribute from a source HU.
	  */
	@Override
	public void setHU_TansferStrategy_JavaClass_ID (int HU_TansferStrategy_JavaClass_ID)
	{
		if (HU_TansferStrategy_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_HU_TansferStrategy_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_HU_TansferStrategy_JavaClass_ID, Integer.valueOf(HU_TansferStrategy_JavaClass_ID));
	}

	/** Get HU Transfer Attribute Strategy.
		@return Strategy used for transferring an attribute from a source HU.
	  */
	@Override
	public int getHU_TansferStrategy_JavaClass_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HU_TansferStrategy_JavaClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Displayed.
		@param IsDisplayed 
		Determines, if this field is displayed
	  */
	@Override
	public void setIsDisplayed (boolean IsDisplayed)
	{
		set_Value (COLUMNNAME_IsDisplayed, Boolean.valueOf(IsDisplayed));
	}

	/** Get Displayed.
		@return Determines, if this field is displayed
	  */
	@Override
	public boolean isDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Instanz Merkmal.
		@param IsInstanceAttribute 
		The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	  */
	@Override
	public void setIsInstanceAttribute (boolean IsInstanceAttribute)
	{
		set_Value (COLUMNNAME_IsInstanceAttribute, Boolean.valueOf(IsInstanceAttribute));
	}

	/** Get Instanz Merkmal.
		@return The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	  */
	@Override
	public boolean isInstanceAttribute () 
	{
		Object oo = get_Value(COLUMNNAME_IsInstanceAttribute);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pflichtangabe.
		@param IsMandatory 
		Data entry is required in this column
	  */
	@Override
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Pflichtangabe.
		@return Data entry is required in this column
	  */
	@Override
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set OnlyIfInProductAttributeSet.
		@param IsOnlyIfInProductAttributeSet OnlyIfInProductAttributeSet	  */
	@Override
	public void setIsOnlyIfInProductAttributeSet (boolean IsOnlyIfInProductAttributeSet)
	{
		set_Value (COLUMNNAME_IsOnlyIfInProductAttributeSet, Boolean.valueOf(IsOnlyIfInProductAttributeSet));
	}

	/** Get OnlyIfInProductAttributeSet.
		@return OnlyIfInProductAttributeSet	  */
	@Override
	public boolean isOnlyIfInProductAttributeSet () 
	{
		Object oo = get_Value(COLUMNNAME_IsOnlyIfInProductAttributeSet);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Schreibgeschützt.
		@param IsReadOnly 
		Feld / Eintrag / Berecih ist schreibgeschützt
	  */
	@Override
	public void setIsReadOnly (boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, Boolean.valueOf(IsReadOnly));
	}

	/** Get Schreibgeschützt.
		@return Feld / Eintrag / Berecih ist schreibgeschützt
	  */
	@Override
	public boolean isReadOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

//	@Override
//	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
//	{
//		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
//	}
//
//	@Override
//	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute)
//	{
//		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
//	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Produkt-Merkmal
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Produkt-Merkmal
	  */
	@Override
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Handling Units Packing Instructions Attribute.
		@param M_HU_PI_Attribute_ID Handling Units Packing Instructions Attribute	  */
	@Override
	public void setM_HU_PI_Attribute_ID (int M_HU_PI_Attribute_ID)
	{
		if (M_HU_PI_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Attribute_ID, Integer.valueOf(M_HU_PI_Attribute_ID));
	}

	/** Get Handling Units Packing Instructions Attribute.
		@return Handling Units Packing Instructions Attribute	  */
	@Override
	public int getM_HU_PI_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

//	@Override
//	public de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version() throws RuntimeException
//	{
//		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class);
//	}
//
//	@Override
//	public void setM_HU_PI_Version(de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version)
//	{
//		set_ValueFromPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class, M_HU_PI_Version);
//	}

	/** Set Packvorschrift Version.
		@param M_HU_PI_Version_ID Packvorschrift Version	  */
	@Override
	public void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID)
	{
		if (M_HU_PI_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Version_ID, Integer.valueOf(M_HU_PI_Version_ID));
	}

	/** Get Packvorschrift Version.
		@return Packvorschrift Version	  */
	@Override
	public int getM_HU_PI_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * PropagationType AD_Reference_ID=540404
	 * Reference name: M_HU_PI_Attribute_PropagationType
	 */
	public static final int PROPAGATIONTYPE_AD_Reference_ID=540404;
	/** TopDown = TOPD */
	public static final String PROPAGATIONTYPE_TopDown = "TOPD";
	/** BottomUp = BOTU */
	public static final String PROPAGATIONTYPE_BottomUp = "BOTU";
	/** NoPropagation = NONE */
	public static final String PROPAGATIONTYPE_NoPropagation = "NONE";
	/** Set Propagation Type.
		@param PropagationType Propagation Type	  */
	@Override
	public void setPropagationType (java.lang.String PropagationType)
	{

		set_Value (COLUMNNAME_PropagationType, PropagationType);
	}

	/** Get Propagation Type.
		@return Propagation Type	  */
	@Override
	public java.lang.String getPropagationType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PropagationType);
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Splitter Strategy.
		@param SplitterStrategy_JavaClass_ID Splitter Strategy	  */
	@Override
	public void setSplitterStrategy_JavaClass_ID (int SplitterStrategy_JavaClass_ID)
	{
		if (SplitterStrategy_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_SplitterStrategy_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_SplitterStrategy_JavaClass_ID, Integer.valueOf(SplitterStrategy_JavaClass_ID));
	}

	/** Get Splitter Strategy.
		@return Splitter Strategy	  */
	@Override
	public int getSplitterStrategy_JavaClass_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SplitterStrategy_JavaClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Use in ASI.
		@param UseInASI 
		If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer
	  */
	@Override
	public void setUseInASI (boolean UseInASI)
	{
		set_Value (COLUMNNAME_UseInASI, Boolean.valueOf(UseInASI));
	}

	/** Get Use in ASI.
		@return If yes, new attributes will be created in ASI (copied from the M_HU_PI_Attribute) - i.e on Transfer
	  */
	@Override
	public boolean isUseInASI () 
	{
		Object oo = get_Value(COLUMNNAME_UseInASI);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}