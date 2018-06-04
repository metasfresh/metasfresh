/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU extends org.compiere.model.PO implements I_M_HU, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2008069057L;

    /** Standard Constructor */
    public X_M_HU (Properties ctx, int M_HU_ID, String trxName)
    {
      super (ctx, M_HU_ID, trxName);
      /** if (M_HU_ID == 0)
        {
			setHUStatus (null); // 'P'
			setM_HU_ID (0);
			setM_HU_PI_Version_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_M_HU (Properties ctx, ResultSet rs, String trxName)
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

	/** Set eigene Gebinde.
		@param HUPlanningReceiptOwnerPM 
		If true, then the packing material's owner is "us" (the guys who ordered it). If false, then the packing material's owner is the PO's partner.
	  */
	@Override
	public void setHUPlanningReceiptOwnerPM (boolean HUPlanningReceiptOwnerPM)
	{
		set_Value (COLUMNNAME_HUPlanningReceiptOwnerPM, Boolean.valueOf(HUPlanningReceiptOwnerPM));
	}

	/** Get eigene Gebinde.
		@return If true, then the packing material's owner is "us" (the guys who ordered it). If false, then the packing material's owner is the PO's partner.
	  */
	@Override
	public boolean isHUPlanningReceiptOwnerPM () 
	{
		Object oo = get_Value(COLUMNNAME_HUPlanningReceiptOwnerPM);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set in HU enthalten.
		@param IsChildHU 
		Sagt aus, ob die gegenbene HU in einer anderen HU enthalten ist (z.B. IFCO auf einer Palette)
	  */
	@Override
	public void setIsChildHU (boolean IsChildHU)
	{
		throw new IllegalArgumentException ("IsChildHU is virtual column");	}

	/** Get in HU enthalten.
		@return Sagt aus, ob die gegenbene HU in einer anderen HU enthalten ist (z.B. IFCO auf einer Palette)
	  */
	@Override
	public boolean isChildHU () 
	{
		Object oo = get_Value(COLUMNNAME_IsChildHU);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Gesperrt.
		@param Locked Gesperrt	  */
	@Override
	public void setLocked (boolean Locked)
	{
		throw new IllegalArgumentException ("Locked is virtual column");	}

	/** Get Gesperrt.
		@return Gesperrt	  */
	@Override
	public boolean isLocked () 
	{
		Object oo = get_Value(COLUMNNAME_Locked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Handling Units.
		@param M_HU_ID Handling Units	  */
	@Override
	public void setM_HU_ID (int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_ID, Integer.valueOf(M_HU_ID));
	}

	/** Get Handling Units.
		@return Handling Units	  */
	@Override
	public int getM_HU_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_Item getM_HU_Item_Parent() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_Item_Parent_ID, de.metas.handlingunits.model.I_M_HU_Item.class);
	}

	@Override
	public void setM_HU_Item_Parent(de.metas.handlingunits.model.I_M_HU_Item M_HU_Item_Parent)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_Item_Parent_ID, de.metas.handlingunits.model.I_M_HU_Item.class, M_HU_Item_Parent);
	}

	/** Set Handling Units Item Parent ID.
		@param M_HU_Item_Parent_ID Handling Units Item Parent ID	  */
	@Override
	public void setM_HU_Item_Parent_ID (int M_HU_Item_Parent_ID)
	{
		if (M_HU_Item_Parent_ID < 1) 
			set_Value (COLUMNNAME_M_HU_Item_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_Item_Parent_ID, Integer.valueOf(M_HU_Item_Parent_ID));
	}

	/** Get Handling Units Item Parent ID.
		@return Handling Units Item Parent ID	  */
	@Override
	public int getM_HU_Item_Parent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_Item_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_LUTU_Configuration_ID, de.metas.handlingunits.model.I_M_HU_LUTU_Configuration.class);
	}

	@Override
	public void setM_HU_LUTU_Configuration(de.metas.handlingunits.model.I_M_HU_LUTU_Configuration M_HU_LUTU_Configuration)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_LUTU_Configuration_ID, de.metas.handlingunits.model.I_M_HU_LUTU_Configuration.class, M_HU_LUTU_Configuration);
	}

	/** Set Gebindekonfiguration.
		@param M_HU_LUTU_Configuration_ID Gebindekonfiguration	  */
	@Override
	public void setM_HU_LUTU_Configuration_ID (int M_HU_LUTU_Configuration_ID)
	{
		if (M_HU_LUTU_Configuration_ID < 1) 
			set_Value (COLUMNNAME_M_HU_LUTU_Configuration_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_LUTU_Configuration_ID, Integer.valueOf(M_HU_LUTU_Configuration_ID));
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

//	@Override
//	public de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version() throws RuntimeException
//	{
//		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class);
//	}

	@Override
	public void setM_HU_PI_Version(de.metas.handlingunits.model.I_M_HU_PI_Version M_HU_PI_Version)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class, M_HU_PI_Version);
	}

	/** Set Packvorschrift Version.
		@param M_HU_PI_Version_ID Packvorschrift Version	  */
	@Override
	public void setM_HU_PI_Version_ID (int M_HU_PI_Version_ID)
	{
		if (M_HU_PI_Version_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Version_ID, Integer.valueOf(M_HU_PI_Version_ID));
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
	public org.compiere.model.I_M_Product_Category getM_Product_Category() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class);
	}

	@Override
	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Category_ID, org.compiere.model.I_M_Product_Category.class, M_Product_Category);
	}

	/** Set Produkt Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		throw new IllegalArgumentException ("M_Product_Category_ID is virtual column");	}

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
		throw new IllegalArgumentException ("M_Product_ID is virtual column");	}

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

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}