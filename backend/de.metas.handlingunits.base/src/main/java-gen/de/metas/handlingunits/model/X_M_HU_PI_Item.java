/** Generated Model - DO NOT CHANGE */
package de.metas.handlingunits.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_HU_PI_Item
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_HU_PI_Item extends org.compiere.model.PO implements I_M_HU_PI_Item, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -825836056L;

    /** Standard Constructor */
    public X_M_HU_PI_Item (Properties ctx, int M_HU_PI_Item_ID, String trxName)
    {
      super (ctx, M_HU_PI_Item_ID, trxName);
      /** if (M_HU_PI_Item_ID == 0)
        {
			setItemType (null);
			setM_HU_PI_Item_ID (0);
			setM_HU_PI_Version_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_HU_PI_Item (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.handlingunits.model.I_M_HU_PI getIncluded_HU_PI() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Included_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class);
	}

	@Override
	public void setIncluded_HU_PI(de.metas.handlingunits.model.I_M_HU_PI Included_HU_PI)
	{
		set_ValueFromPO(COLUMNNAME_Included_HU_PI_ID, de.metas.handlingunits.model.I_M_HU_PI.class, Included_HU_PI);
	}

	/** Set Unter-Packvorschrift.
		@param Included_HU_PI_ID Unter-Packvorschrift	  */
	@Override
	public void setIncluded_HU_PI_ID (int Included_HU_PI_ID)
	{
		if (Included_HU_PI_ID < 1) 
			set_Value (COLUMNNAME_Included_HU_PI_ID, null);
		else 
			set_Value (COLUMNNAME_Included_HU_PI_ID, Integer.valueOf(Included_HU_PI_ID));
	}

	/** Get Unter-Packvorschrift.
		@return Unter-Packvorschrift	  */
	@Override
	public int getIncluded_HU_PI_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Included_HU_PI_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AllowDirectlyOnPM.
		@param IsAllowDirectlyOnPM AllowDirectlyOnPM	  */
	@Override
	public void setIsAllowDirectlyOnPM (boolean IsAllowDirectlyOnPM)
	{
		set_Value (COLUMNNAME_IsAllowDirectlyOnPM, Boolean.valueOf(IsAllowDirectlyOnPM));
	}

	/** Get AllowDirectlyOnPM.
		@return AllowDirectlyOnPM	  */
	@Override
	public boolean isAllowDirectlyOnPM () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowDirectlyOnPM);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * ItemType AD_Reference_ID=540395
	 * Reference name: M_HU_PI_Item_ItemType
	 */
	public static final int ITEMTYPE_AD_Reference_ID=540395;
	/** Material = MI */
	public static final String ITEMTYPE_Material = "MI";
	/** PackingMaterial = PM */
	public static final String ITEMTYPE_PackingMaterial = "PM";
	/** HandlingUnit = HU */
	public static final String ITEMTYPE_HandlingUnit = "HU";
	/** Set Positionsart.
		@param ItemType Positionsart	  */
	@Override
	public void setItemType (java.lang.String ItemType)
	{

		set_Value (COLUMNNAME_ItemType, ItemType);
	}

	/** Get Positionsart.
		@return Positionsart	  */
	@Override
	public java.lang.String getItemType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ItemType);
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PackingMaterial getM_HU_PackingMaterial() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PackingMaterial_ID, de.metas.handlingunits.model.I_M_HU_PackingMaterial.class);
	}

	@Override
	public void setM_HU_PackingMaterial(de.metas.handlingunits.model.I_M_HU_PackingMaterial M_HU_PackingMaterial)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_PackingMaterial_ID, de.metas.handlingunits.model.I_M_HU_PackingMaterial.class, M_HU_PackingMaterial);
	}

	/** Set Packmittel.
		@param M_HU_PackingMaterial_ID Packmittel	  */
	@Override
	public void setM_HU_PackingMaterial_ID (int M_HU_PackingMaterial_ID)
	{
		if (M_HU_PackingMaterial_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackingMaterial_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackingMaterial_ID, Integer.valueOf(M_HU_PackingMaterial_ID));
	}

	/** Get Packmittel.
		@return Packmittel	  */
	@Override
	public int getM_HU_PackingMaterial_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PackingMaterial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packvorschrift Position.
		@param M_HU_PI_Item_ID Packvorschrift Position	  */
	@Override
	public void setM_HU_PI_Item_ID (int M_HU_PI_Item_ID)
	{
		if (M_HU_PI_Item_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_PI_Item_ID, Integer.valueOf(M_HU_PI_Item_ID));
	}

	/** Get Packvorschrift Position.
		@return Packvorschrift Position	  */
	@Override
	public int getM_HU_PI_Item_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Item_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.handlingunits.model.I_M_HU_PI_Version getM_HU_PI_Version() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_PI_Version_ID, de.metas.handlingunits.model.I_M_HU_PI_Version.class);
	}

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

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
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