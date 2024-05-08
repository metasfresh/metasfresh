/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_AttributeSet
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_AttributeSet extends org.compiere.model.PO implements I_M_AttributeSet, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1002845033L;

    /** Standard Constructor */
    public X_M_AttributeSet (Properties ctx, int M_AttributeSet_ID, String trxName)
    {
      super (ctx, M_AttributeSet_ID, trxName);
      /** if (M_AttributeSet_ID == 0)
        {
			setIsGuaranteeDate (false);
			setIsGuaranteeDateMandatory (false);
			setIsInstanceAttribute (false);
			setIsLot (false);
			setIsLotMandatory (false);
			setIsSerNo (false);
			setIsSerNoMandatory (false);
			setM_AttributeSet_ID (0);
			setMandatoryType (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_AttributeSet (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Garantie-Datum.
		@param IsGuaranteeDate 
		Product has Guarantee or Expiry Date
	  */
	@Override
	public void setIsGuaranteeDate (boolean IsGuaranteeDate)
	{
		set_Value (COLUMNNAME_IsGuaranteeDate, Boolean.valueOf(IsGuaranteeDate));
	}

	/** Get Garantie-Datum.
		@return Product has Guarantee or Expiry Date
	  */
	@Override
	public boolean isGuaranteeDate () 
	{
		Object oo = get_Value(COLUMNNAME_IsGuaranteeDate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Garantie-Datum ist Pflicht.
		@param IsGuaranteeDateMandatory 
		The entry of a Guarantee Date is mandatory when creating a Product Instance
	  */
	@Override
	public void setIsGuaranteeDateMandatory (boolean IsGuaranteeDateMandatory)
	{
		set_Value (COLUMNNAME_IsGuaranteeDateMandatory, Boolean.valueOf(IsGuaranteeDateMandatory));
	}

	/** Get Garantie-Datum ist Pflicht.
		@return The entry of a Guarantee Date is mandatory when creating a Product Instance
	  */
	@Override
	public boolean isGuaranteeDateMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsGuaranteeDateMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Instanz-Attribut.
		@param IsInstanceAttribute 
		The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	  */
	@Override
	public void setIsInstanceAttribute (boolean IsInstanceAttribute)
	{
		set_Value (COLUMNNAME_IsInstanceAttribute, Boolean.valueOf(IsInstanceAttribute));
	}

	/** Get Instanz-Attribut.
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

	/** Set Los.
		@param IsLot 
		The product instances have a Lot Number
	  */
	@Override
	public void setIsLot (boolean IsLot)
	{
		set_Value (COLUMNNAME_IsLot, Boolean.valueOf(IsLot));
	}

	/** Get Los.
		@return The product instances have a Lot Number
	  */
	@Override
	public boolean isLot () 
	{
		Object oo = get_Value(COLUMNNAME_IsLot);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory Lot.
		@param IsLotMandatory 
		The entry of Lot info is mandatory when creating a Product Instance
	  */
	@Override
	public void setIsLotMandatory (boolean IsLotMandatory)
	{
		set_Value (COLUMNNAME_IsLotMandatory, Boolean.valueOf(IsLotMandatory));
	}

	/** Get Mandatory Lot.
		@return The entry of Lot info is mandatory when creating a Product Instance
	  */
	@Override
	public boolean isLotMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsLotMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Serien-Nr..
		@param IsSerNo 
		The product instances have Serial Numbers
	  */
	@Override
	public void setIsSerNo (boolean IsSerNo)
	{
		set_Value (COLUMNNAME_IsSerNo, Boolean.valueOf(IsSerNo));
	}

	/** Get Serien-Nr..
		@return The product instances have Serial Numbers
	  */
	@Override
	public boolean isSerNo () 
	{
		Object oo = get_Value(COLUMNNAME_IsSerNo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory Serial No.
		@param IsSerNoMandatory 
		The entry of a Serial No is mandatory when creating a Product Instance
	  */
	@Override
	public void setIsSerNoMandatory (boolean IsSerNoMandatory)
	{
		set_Value (COLUMNNAME_IsSerNoMandatory, Boolean.valueOf(IsSerNoMandatory));
	}

	/** Get Mandatory Serial No.
		@return The entry of a Serial No is mandatory when creating a Product Instance
	  */
	@Override
	public boolean isSerNoMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsSerNoMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lot Char End Overwrite.
		@param LotCharEOverwrite 
		Lot/Batch End Indicator overwrite - default »
	  */
	@Override
	public void setLotCharEOverwrite (java.lang.String LotCharEOverwrite)
	{
		set_Value (COLUMNNAME_LotCharEOverwrite, LotCharEOverwrite);
	}

	/** Get Lot Char End Overwrite.
		@return Lot/Batch End Indicator overwrite - default »
	  */
	@Override
	public java.lang.String getLotCharEOverwrite () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LotCharEOverwrite);
	}

	/** Set Lot Char Start Overwrite.
		@param LotCharSOverwrite 
		Lot/Batch Start Indicator overwrite - default «
	  */
	@Override
	public void setLotCharSOverwrite (java.lang.String LotCharSOverwrite)
	{
		set_Value (COLUMNNAME_LotCharSOverwrite, LotCharSOverwrite);
	}

	/** Get Lot Char Start Overwrite.
		@return Lot/Batch Start Indicator overwrite - default «
	  */
	@Override
	public java.lang.String getLotCharSOverwrite () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LotCharSOverwrite);
	}

	/** Set Merkmals-Satz.
		@param M_AttributeSet_ID 
		Product Attribute Set
	  */
	@Override
	public void setM_AttributeSet_ID (int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_ID, Integer.valueOf(M_AttributeSet_ID));
	}

	/** Get Merkmals-Satz.
		@return Product Attribute Set
	  */
	@Override
	public int getM_AttributeSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_LotCtl getM_LotCtl() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_LotCtl_ID, org.compiere.model.I_M_LotCtl.class);
	}

	@Override
	public void setM_LotCtl(org.compiere.model.I_M_LotCtl M_LotCtl)
	{
		set_ValueFromPO(COLUMNNAME_M_LotCtl_ID, org.compiere.model.I_M_LotCtl.class, M_LotCtl);
	}

	/** Set Los-Definition.
		@param M_LotCtl_ID 
		Product Lot Control
	  */
	@Override
	public void setM_LotCtl_ID (int M_LotCtl_ID)
	{
		if (M_LotCtl_ID < 1) 
			set_Value (COLUMNNAME_M_LotCtl_ID, null);
		else 
			set_Value (COLUMNNAME_M_LotCtl_ID, Integer.valueOf(M_LotCtl_ID));
	}

	/** Get Los-Definition.
		@return Product Lot Control
	  */
	@Override
	public int getM_LotCtl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_LotCtl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_SerNoCtl getM_SerNoCtl() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_SerNoCtl_ID, org.compiere.model.I_M_SerNoCtl.class);
	}

	@Override
	public void setM_SerNoCtl(org.compiere.model.I_M_SerNoCtl M_SerNoCtl)
	{
		set_ValueFromPO(COLUMNNAME_M_SerNoCtl_ID, org.compiere.model.I_M_SerNoCtl.class, M_SerNoCtl);
	}

	/** Set Seriennummern-Definition.
		@param M_SerNoCtl_ID 
		Product Serial Number Control
	  */
	@Override
	public void setM_SerNoCtl_ID (int M_SerNoCtl_ID)
	{
		if (M_SerNoCtl_ID < 1) 
			set_Value (COLUMNNAME_M_SerNoCtl_ID, null);
		else 
			set_Value (COLUMNNAME_M_SerNoCtl_ID, Integer.valueOf(M_SerNoCtl_ID));
	}

	/** Get Seriennummern-Definition.
		@return Product Serial Number Control
	  */
	@Override
	public int getM_SerNoCtl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_SerNoCtl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MandatoryType AD_Reference_ID=324
	 * Reference name: M_AttributeSet MandatoryType
	 */
	public static final int MANDATORYTYPE_AD_Reference_ID=324;
	/** Not Mandatary = N */
	public static final String MANDATORYTYPE_NotMandatary = "N";
	/** Always Mandatory = Y */
	public static final String MANDATORYTYPE_AlwaysMandatory = "Y";
	/** WhenShipping = S */
	public static final String MANDATORYTYPE_WhenShipping = "S";
	/** Set Mandatory Type.
		@param MandatoryType 
		The specification of a Product Attribute Instance is mandatory
	  */
	@Override
	public void setMandatoryType (java.lang.String MandatoryType)
	{

		set_Value (COLUMNNAME_MandatoryType, MandatoryType);
	}

	/** Get Mandatory Type.
		@return The specification of a Product Attribute Instance is mandatory
	  */
	@Override
	public java.lang.String getMandatoryType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MandatoryType);
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

	/** Set SerNo Char End Overwrite.
		@param SerNoCharEOverwrite 
		Serial Number End Indicator overwrite - default empty
	  */
	@Override
	public void setSerNoCharEOverwrite (java.lang.String SerNoCharEOverwrite)
	{
		set_Value (COLUMNNAME_SerNoCharEOverwrite, SerNoCharEOverwrite);
	}

	/** Get SerNo Char End Overwrite.
		@return Serial Number End Indicator overwrite - default empty
	  */
	@Override
	public java.lang.String getSerNoCharEOverwrite () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SerNoCharEOverwrite);
	}

	/** Set SerNo Char Start Overwrite.
		@param SerNoCharSOverwrite 
		Serial Number Start Indicator overwrite - default #
	  */
	@Override
	public void setSerNoCharSOverwrite (java.lang.String SerNoCharSOverwrite)
	{
		set_Value (COLUMNNAME_SerNoCharSOverwrite, SerNoCharSOverwrite);
	}

	/** Get SerNo Char Start Overwrite.
		@return Serial Number Start Indicator overwrite - default #
	  */
	@Override
	public java.lang.String getSerNoCharSOverwrite () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SerNoCharSOverwrite);
	}
}