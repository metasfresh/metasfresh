/** Generated Model - DO NOT CHANGE */
package de.metas.invoicecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ProductGroup_Product
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ProductGroup_Product extends org.compiere.model.PO implements I_M_ProductGroup_Product, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1554657872L;

    /** Standard Constructor */
    public X_M_ProductGroup_Product (Properties ctx, int M_ProductGroup_Product_ID, String trxName)
    {
      super (ctx, M_ProductGroup_Product_ID, trxName);
      /** if (M_ProductGroup_Product_ID == 0)
        {
			setM_Product_Category_ID (0);
			setM_ProductGroup_ID (0);
			setM_ProductGroup_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_ProductGroup_Product (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Produkt Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
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
	public de.metas.invoicecandidate.model.I_M_ProductGroup getM_ProductGroup()
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductGroup_ID, de.metas.invoicecandidate.model.I_M_ProductGroup.class);
	}

	@Override
	public void setM_ProductGroup(de.metas.invoicecandidate.model.I_M_ProductGroup M_ProductGroup)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductGroup_ID, de.metas.invoicecandidate.model.I_M_ProductGroup.class, M_ProductGroup);
	}

	/** Set Produktgruppe.
		@param M_ProductGroup_ID 
		Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	  */
	@Override
	public void setM_ProductGroup_ID (int M_ProductGroup_ID)
	{
		if (M_ProductGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_ID, Integer.valueOf(M_ProductGroup_ID));
	}

	/** Get Produktgruppe.
		@return Fasst eine Anzahl von Produkten oder Produktkategorien zu einer Gruppe zusammen.
	  */
	@Override
	public int getM_ProductGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Produktgruppe - Produkt.
		@param M_ProductGroup_Product_ID Produktgruppe - Produkt	  */
	@Override
	public void setM_ProductGroup_Product_ID (int M_ProductGroup_Product_ID)
	{
		if (M_ProductGroup_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductGroup_Product_ID, Integer.valueOf(M_ProductGroup_Product_ID));
	}

	/** Get Produktgruppe - Produkt.
		@return Produktgruppe - Produkt	  */
	@Override
	public int getM_ProductGroup_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductGroup_Product_ID);
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
}