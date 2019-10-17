/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_Stock_Detail_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_Stock_Detail_V extends org.compiere.model.PO implements I_M_HU_Stock_Detail_V, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -637130292L;

    /** Standard Constructor */
    public X_M_HU_Stock_Detail_V (Properties ctx, int M_HU_Stock_Detail_V_ID, String trxName)
    {
      super (ctx, M_HU_Stock_Detail_V_ID, trxName);
      /** if (M_HU_Stock_Detail_V_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_M_HU_Stock_Detail_V (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Merkmals-Wert.
		@param AttributeValue 
		Value of the Attribute
	  */
	@Override
	public void setAttributeValue (java.lang.String AttributeValue)
	{
		set_ValueNoCheck (COLUMNNAME_AttributeValue, AttributeValue);
	}

	/** Get Merkmals-Wert.
		@return Value of the Attribute
	  */
	@Override
	public java.lang.String getAttributeValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeValue);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
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

	/** 
	 * HUStatus AD_Reference_ID=540478
	 * Reference name: HUStatus
	 */
	public static final int HUSTATUS_AD_Reference_ID=540478;
	/** Planning = P */
	public static final String HUSTATUS_Planning = "P";
	/** Active = A */
	public static final String HUSTATUS_Active = "A";
	/** Destroyed = D */
	public static final String HUSTATUS_Destroyed = "D";
	/** Picked = S */
	public static final String HUSTATUS_Picked = "S";
	/** Shipped = E */
	public static final String HUSTATUS_Shipped = "E";
	/** Issued = I */
	public static final String HUSTATUS_Issued = "I";
	/** Set Gebinde Status.
		@param HUStatus Gebinde Status	  */
	@Override
	public void setHUStatus (java.lang.String HUStatus)
	{

		set_ValueNoCheck (COLUMNNAME_HUStatus, HUStatus);
	}

	/** Get Gebinde Status.
		@return Gebinde Status	  */
	@Override
	public java.lang.String getHUStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HUStatus);
	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Produkt-Merkmal
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
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

	@Override
	public de.metas.handlingunits.model.I_M_HU_Attribute getM_HU_Attribute()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Attribute_ID, de.metas.handlingunits.model.I_M_HU_Attribute.class);
	}

	@Override
	public void setM_HU_Attribute(de.metas.handlingunits.model.I_M_HU_Attribute M_HU_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Attribute_ID, de.metas.handlingunits.model.I_M_HU_Attribute.class, M_HU_Attribute);
	}

	/** Set Handling Unit Attribute.
		@param M_HU_Attribute_ID Handling Unit Attribute	  */
	@Override
	public void setM_HU_Attribute_ID (int M_HU_Attribute_ID)
	{
		if (M_HU_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Attribute_ID, Integer.valueOf(M_HU_Attribute_ID));
	}

	/** Get Handling Unit Attribute.
		@return Handling Unit Attribute	  */
	@Override
	public int getM_HU_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class);
	}

	@Override
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, de.metas.handlingunits.model.I_M_HU.class, M_HU);
	}

	/** Set Handling Unit.
		@param M_HU_ID Handling Unit	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Unit.
		@return Handling Unit	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Storage getM_HU_Storage()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Storage_ID, de.metas.handlingunits.model.I_M_HU_Storage.class);
	}

	@Override
	public void setM_HU_Storage(de.metas.handlingunits.model.I_M_HU_Storage M_HU_Storage)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Storage_ID, de.metas.handlingunits.model.I_M_HU_Storage.class, M_HU_Storage);
	}

	/** Set Handling Units Storage.
		@param M_HU_Storage_ID Handling Units Storage	  */
	@Override
	public void setM_HU_Storage_ID (int M_HU_Storage_ID)
	{
		if (M_HU_Storage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_Storage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_Storage_ID, Integer.valueOf(M_HU_Storage_ID));
	}

	/** Get Handling Units Storage.
		@return Handling Units Storage	  */
	@Override
	public int getM_HU_Storage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Storage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lagerort.
		@param M_Locator_ID 
		Lagerort im Lager
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Lagerort.
		@return Lagerort im Lager
	  */
	@Override
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_ValueNoCheck (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}