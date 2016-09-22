/** Generated Model - DO NOT CHANGE */
package de.metas.fresh.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for X_MRP_ProductInfo_Detail_MV
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_X_MRP_ProductInfo_Detail_MV extends org.compiere.model.PO implements I_X_MRP_ProductInfo_Detail_MV, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 438857873L;

    /** Standard Constructor */
    public X_X_MRP_ProductInfo_Detail_MV (Properties ctx, int X_MRP_ProductInfo_Detail_MV_ID, String trxName)
    {
      super (ctx, X_MRP_ProductInfo_Detail_MV_ID, trxName);
      /** if (X_MRP_ProductInfo_Detail_MV_ID == 0)
        {
			setASIKey (null);
			setDateGeneral (new Timestamp( System.currentTimeMillis() ));
			setIsFallBack (true);
// Y
			setM_Product_ID (0);
			setX_MRP_ProductInfo_Detail_MV_ID (0);
        } */
    }

    /** Load Constructor */
    public X_X_MRP_ProductInfo_Detail_MV (Properties ctx, ResultSet rs, String trxName)
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

	/** Set ASI Key.
		@param ASIKey ASI Key	  */
	@Override
	public void setASIKey (java.lang.String ASIKey)
	{
		set_ValueNoCheck (COLUMNNAME_ASIKey, ASIKey);
	}

	/** Get ASI Key.
		@return ASI Key	  */
	@Override
	public java.lang.String getASIKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ASIKey);
	}

	/** Set Datum.
		@param DateGeneral Datum	  */
	@Override
	public void setDateGeneral (java.sql.Timestamp DateGeneral)
	{
		set_ValueNoCheck (COLUMNNAME_DateGeneral, DateGeneral);
	}

	/** Get Datum.
		@return Datum	  */
	@Override
	public java.sql.Timestamp getDateGeneral () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateGeneral);
	}

	/** Set IsFallBack.
		@param IsFallBack IsFallBack	  */
	@Override
	public void setIsFallBack (boolean IsFallBack)
	{
		set_Value (COLUMNNAME_IsFallBack, Boolean.valueOf(IsFallBack));
	}

	/** Get IsFallBack.
		@return IsFallBack	  */
	@Override
	public boolean isFallBack () 
	{
		Object oo = get_Value(COLUMNNAME_IsFallBack);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Ausprägung Merkmals-Satz.
		@param M_AttributeSetInstance_ID 
		Instanz des Merkmals-Satzes zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Ausprägung Merkmals-Satz.
		@return Instanz des Merkmals-Satzes zum Produkt
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
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
			 return Env.ZERO;
		return bd;
	}

	/** Set X_MRP_ProductInfo_Detail_MV.
		@param X_MRP_ProductInfo_Detail_MV_ID X_MRP_ProductInfo_Detail_MV	  */
	@Override
	public void setX_MRP_ProductInfo_Detail_MV_ID (int X_MRP_ProductInfo_Detail_MV_ID)
	{
		if (X_MRP_ProductInfo_Detail_MV_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_X_MRP_ProductInfo_Detail_MV_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_X_MRP_ProductInfo_Detail_MV_ID, Integer.valueOf(X_MRP_ProductInfo_Detail_MV_ID));
	}

	/** Get X_MRP_ProductInfo_Detail_MV.
		@return X_MRP_ProductInfo_Detail_MV	  */
	@Override
	public int getX_MRP_ProductInfo_Detail_MV_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_X_MRP_ProductInfo_Detail_MV_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}