/** Generated Model - DO NOT CHANGE */
package de.metas.material.cockpit.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Stock
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Stock extends org.compiere.model.PO implements I_MD_Stock, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1990181053L;

    /** Standard Constructor */
    public X_MD_Stock (Properties ctx, int MD_Stock_ID, String trxName)
    {
      super (ctx, MD_Stock_ID, trxName);
      /** if (MD_Stock_ID == 0)
        {
			setAttributesKey (null); // -1002
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
			setMD_Stock_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MD_Stock (Properties ctx, ResultSet rs, String trxName)
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

	/** Set AttributesKey (technical).
		@param AttributesKey AttributesKey (technical)	  */
	@Override
	public void setAttributesKey (java.lang.String AttributesKey)
	{
		set_Value (COLUMNNAME_AttributesKey, AttributesKey);
	}

	/** Get AttributesKey (technical).
		@return AttributesKey (technical)	  */
	@Override
	public java.lang.String getAttributesKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributesKey);
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

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Lager oder Ort für Dienstleistung
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Lager oder Ort für Dienstleistung
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Betand.
		@param MD_Stock_ID Betand	  */
	@Override
	public void setMD_Stock_ID (int MD_Stock_ID)
	{
		if (MD_Stock_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MD_Stock_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MD_Stock_ID, Integer.valueOf(MD_Stock_ID));
	}

	/** Get Betand.
		@return Betand	  */
	@Override
	public int getMD_Stock_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MD_Stock_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bestand.
		@param QtyOnHand 
		Bestand
	  */
	@Override
	public void setQtyOnHand (java.math.BigDecimal QtyOnHand)
	{
		set_Value (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get Bestand.
		@return Bestand
	  */
	@Override
	public java.math.BigDecimal getQtyOnHand () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}