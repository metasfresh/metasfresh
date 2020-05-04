/** Generated Model - DO NOT CHANGE */
package de.metas.material.cockpit.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MD_Available_For_Sales_QueryResult
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MD_Available_For_Sales_QueryResult extends org.compiere.model.PO implements I_MD_Available_For_Sales_QueryResult, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 384838790L;

    /** Standard Constructor */
    public X_MD_Available_For_Sales_QueryResult (Properties ctx, int MD_Available_For_Sales_QueryResult_ID, String trxName)
    {
      super (ctx, MD_Available_For_Sales_QueryResult_ID, trxName);
      /** if (MD_Available_For_Sales_QueryResult_ID == 0)
        {
			setQueryNo (0); // 0
        } */
    }

    /** Load Constructor */
    public X_MD_Available_For_Sales_QueryResult (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Lagerbestand.
		@param QtyOnHandStock 
		Aktueller oder geplanter Lagerbestand
	  */
	@Override
	public void setQtyOnHandStock (java.math.BigDecimal QtyOnHandStock)
	{
		set_Value (COLUMNNAME_QtyOnHandStock, QtyOnHandStock);
	}

	/** Get Lagerbestand.
		@return Aktueller oder geplanter Lagerbestand
	  */
	@Override
	public java.math.BigDecimal getQtyOnHandStock () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHandStock);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set QtyToBeShipped.
		@param QtyToBeShipped QtyToBeShipped	  */
	@Override
	public void setQtyToBeShipped (java.math.BigDecimal QtyToBeShipped)
	{
		set_Value (COLUMNNAME_QtyToBeShipped, QtyToBeShipped);
	}

	/** Get QtyToBeShipped.
		@return QtyToBeShipped	  */
	@Override
	public java.math.BigDecimal getQtyToBeShipped () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToBeShipped);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set QueryNo.
		@param QueryNo QueryNo	  */
	@Override
	public void setQueryNo (int QueryNo)
	{
		set_Value (COLUMNNAME_QueryNo, Integer.valueOf(QueryNo));
	}

	/** Get QueryNo.
		@return QueryNo	  */
	@Override
	public int getQueryNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QueryNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set StorageAttributesKey (technical).
		@param StorageAttributesKey StorageAttributesKey (technical)	  */
	@Override
	public void setStorageAttributesKey (java.lang.String StorageAttributesKey)
	{
		set_Value (COLUMNNAME_StorageAttributesKey, StorageAttributesKey);
	}

	/** Get StorageAttributesKey (technical).
		@return StorageAttributesKey (technical)	  */
	@Override
	public java.lang.String getStorageAttributesKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StorageAttributesKey);
	}
}