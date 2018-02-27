/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_LUTU_Configuration
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_LUTU_Configuration extends org.compiere.model.PO implements I_M_HU_LUTU_Configuration, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1233112750L;

    /** Standard Constructor */
    public X_M_HU_LUTU_Configuration (Properties ctx, int M_HU_LUTU_Configuration_ID, String trxName)
    {
      super (ctx, M_HU_LUTU_Configuration_ID, trxName);
      /** if (M_HU_LUTU_Configuration_ID == 0)
        {
			setC_UOM_ID (0);
			setIsInfiniteQtyCU (false); // N
			setIsInfiniteQtyLU (false); // N
			setIsInfiniteQtyTU (false); // N
			setM_HU_LUTU_Configuration_ID (0);
			setM_Product_ID (0);
			setM_TU_HU_PI_ID (0);
			setQtyCU (BigDecimal.ZERO);
			setQtyLU (BigDecimal.ZERO);
			setQtyTU (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_HU_LUTU_Configuration (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

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

		set_Value (COLUMNNAME_HUStatus, HUStatus);
	}

	/** Get Gebinde Status.
		@return Gebinde Status	  */
	@Override
	public java.lang.String getHUStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HUStatus);
	}

	/** Set Unendliche CU Menge.
		@param IsInfiniteQtyCU Unendliche CU Menge	  */
	@Override
	public void setIsInfiniteQtyCU (boolean IsInfiniteQtyCU)
	{
		set_Value (COLUMNNAME_IsInfiniteQtyCU, Boolean.valueOf(IsInfiniteQtyCU));
	}

	/** Get Unendliche CU Menge.
		@return Unendliche CU Menge	  */
	@Override
	public boolean isInfiniteQtyCU () 
	{
		Object oo = get_Value(COLUMNNAME_IsInfiniteQtyCU);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Unendliche LU Menge.
		@param IsInfiniteQtyLU Unendliche LU Menge	  */
	@Override
	public void setIsInfiniteQtyLU (boolean IsInfiniteQtyLU)
	{
		set_Value (COLUMNNAME_IsInfiniteQtyLU, Boolean.valueOf(IsInfiniteQtyLU));
	}

	/** Get Unendliche LU Menge.
		@return Unendliche LU Menge	  */
	@Override
	public boolean isInfiniteQtyLU () 
	{
		Object oo = get_Value(COLUMNNAME_IsInfiniteQtyLU);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Unendliche TU Menge.
		@param IsInfiniteQtyTU Unendliche TU Menge	  */
	@Override
	public void setIsInfiniteQtyTU (boolean IsInfiniteQtyTU)
	{
		set_Value (COLUMNNAME_IsInfiniteQtyTU, Boolean.valueOf(IsInfiniteQtyTU));
	}

	/** Get Unendliche TU Menge.
		@return Unendliche TU Menge	  */
	@Override
	public boolean isInfiniteQtyTU () 
	{
		Object oo = get_Value(COLUMNNAME_IsInfiniteQtyTU);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gebindekonfiguration.
		@param M_HU_LUTU_Configuration_ID Gebindekonfiguration	  */
	@Override
	public void setM_HU_LUTU_Configuration_ID (int M_HU_LUTU_Configuration_ID)
	{
		if (M_HU_LUTU_Configuration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_LUTU_Configuration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_LUTU_Configuration_ID, Integer.valueOf(M_HU_LUTU_Configuration_ID));
	}

	/** Get Gebindekonfiguration.
		@return Gebindekonfiguration	  */
	@Override
	public int getM_HU_LUTU_Configuration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_LUTU_Configuration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI_Item_Product getM_HU_PI_Item_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Item_Product_ID, de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class);
	}

	@Override
	public void setM_HU_PI_Item_Product(de.metas.handlingunits.model.I_M_HU_PI_Item_Product M_HU_PI_Item_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Item_Product_ID, de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class, M_HU_PI_Item_Product);
	}

	/** Set Packvorschrift.
		@param M_HU_PI_Item_Product_ID Packvorschrift	  */
	@Override
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, Integer.valueOf(M_HU_PI_Item_Product_ID));
	}

	/** Get Packvorschrift.
		@return Packvorschrift	  */
	@Override
	public int getM_HU_PI_Item_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Item_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class);
	}

	@Override
	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator)
	{
		set_ValueFromPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class, M_Locator);
	}

	/** Set Lagerort.
		@param M_Locator_ID 
		Lagerort im Lager
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
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

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI getM_LU_HU_PI() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_LU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setM_LU_HU_PI(de.metas.handlingunits.model.I_M_HU_PI M_LU_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_M_LU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, M_LU_HU_PI);
	}

	/** Set Packvorschrift (LU).
		@param M_LU_HU_PI_ID Packvorschrift (LU)	  */
	@Override
	public void setM_LU_HU_PI_ID (int M_LU_HU_PI_ID)
	{
		if (M_LU_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_M_LU_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_M_LU_HU_PI_ID, Integer.valueOf(M_LU_HU_PI_ID));
	}

	/** Get Packvorschrift (LU).
		@return Packvorschrift (LU)	  */
	@Override
	public int getM_LU_HU_PI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_LU_HU_PI_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI_Item getM_LU_HU_PI_Item() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_LU_HU_PI_Item_ID, de.metas.handlingunits.model.I_M_HU_PI_Item.class);
	}

	@Override
	public void setM_LU_HU_PI_Item(de.metas.handlingunits.model.I_M_HU_PI_Item M_LU_HU_PI_Item)
	{
		set_ValueFromPO(COLUMNNAME_M_LU_HU_PI_Item_ID, de.metas.handlingunits.model.I_M_HU_PI_Item.class, M_LU_HU_PI_Item);
	}

	/** Set Packvorschrift Position (LU).
		@param M_LU_HU_PI_Item_ID Packvorschrift Position (LU)	  */
	@Override
	public void setM_LU_HU_PI_Item_ID (int M_LU_HU_PI_Item_ID)
	{
		if (M_LU_HU_PI_Item_ID < 1) 
			set_Value (COLUMNNAME_M_LU_HU_PI_Item_ID, null);
		else 
			set_Value (COLUMNNAME_M_LU_HU_PI_Item_ID, Integer.valueOf(M_LU_HU_PI_Item_ID));
	}

	/** Get Packvorschrift Position (LU).
		@return Packvorschrift Position (LU)	  */
	@Override
	public int getM_LU_HU_PI_Item_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_LU_HU_PI_Item_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI getM_TU_HU_PI() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_TU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setM_TU_HU_PI(de.metas.handlingunits.model.I_M_HU_PI M_TU_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_M_TU_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, M_TU_HU_PI);
	}

	/** Set Packvorschrift (TU).
		@param M_TU_HU_PI_ID Packvorschrift (TU)	  */
	@Override
	public void setM_TU_HU_PI_ID (int M_TU_HU_PI_ID)
	{
		if (M_TU_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_M_TU_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_M_TU_HU_PI_ID, Integer.valueOf(M_TU_HU_PI_ID));
	}

	/** Get Packvorschrift (TU).
		@return Packvorschrift (TU)	  */
	@Override
	public int getM_TU_HU_PI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_TU_HU_PI_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Menge CU.
		@param QtyCU Menge CU	  */
	@Override
	public void setQtyCU (java.math.BigDecimal QtyCU)
	{
		set_Value (COLUMNNAME_QtyCU, QtyCU);
	}

	/** Get Menge CU.
		@return Menge CU	  */
	@Override
	public java.math.BigDecimal getQtyCU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCU);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge LU.
		@param QtyLU Menge LU	  */
	@Override
	public void setQtyLU (java.math.BigDecimal QtyLU)
	{
		set_Value (COLUMNNAME_QtyLU, QtyLU);
	}

	/** Get Menge LU.
		@return Menge LU	  */
	@Override
	public java.math.BigDecimal getQtyLU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyLU);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge TU.
		@param QtyTU Menge TU	  */
	@Override
	public void setQtyTU (java.math.BigDecimal QtyTU)
	{
		set_Value (COLUMNNAME_QtyTU, QtyTU);
	}

	/** Get Menge TU.
		@return Menge TU	  */
	@Override
	public java.math.BigDecimal getQtyTU () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyTU);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}