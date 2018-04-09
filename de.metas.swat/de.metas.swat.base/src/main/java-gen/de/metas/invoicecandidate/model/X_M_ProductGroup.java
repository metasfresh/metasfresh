/** Generated Model - DO NOT CHANGE */
package de.metas.invoicecandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ProductGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ProductGroup extends org.compiere.model.PO implements I_M_ProductGroup, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 803712055L;

    /** Standard Constructor */
    public X_M_ProductGroup (Properties ctx, int M_ProductGroup_ID, String trxName)
    {
      super (ctx, M_ProductGroup_ID, trxName);
      /** if (M_ProductGroup_ID == 0)
        {
			setM_ProductGroup_ID (0);
			setM_Product_Proxy_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_ProductGroup (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_M_Product getM_Product_Proxy() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Proxy_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product_Proxy(org.compiere.model.I_M_Product M_Product_Proxy)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Proxy_ID, org.compiere.model.I_M_Product.class, M_Product_Proxy);
	}

	/** Set Stellvertreter-Produkt.
		@param M_Product_Proxy_ID 
		Produkt, dass bei einer Aggregation unterschiedlicher Produkte als Stellvertreter der aggregierten Produkte ausgewiesen werden kann
	  */
	@Override
	public void setM_Product_Proxy_ID (int M_Product_Proxy_ID)
	{
		if (M_Product_Proxy_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Proxy_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Proxy_ID, Integer.valueOf(M_Product_Proxy_ID));
	}

	/** Get Stellvertreter-Produkt.
		@return Produkt, dass bei einer Aggregation unterschiedlicher Produkte als Stellvertreter der aggregierten Produkte ausgewiesen werden kann
	  */
	@Override
	public int getM_Product_Proxy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Proxy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}