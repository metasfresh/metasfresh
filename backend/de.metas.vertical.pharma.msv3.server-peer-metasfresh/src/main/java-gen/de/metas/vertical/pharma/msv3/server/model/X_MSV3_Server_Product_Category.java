/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.msv3.server.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_Server_Product_Category
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_Server_Product_Category extends org.compiere.model.PO implements I_MSV3_Server_Product_Category, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1186575449L;

    /** Standard Constructor */
    public X_MSV3_Server_Product_Category (Properties ctx, int MSV3_Server_Product_Category_ID, String trxName)
    {
      super (ctx, MSV3_Server_Product_Category_ID, trxName);
      /** if (MSV3_Server_Product_Category_ID == 0)
        {
			setM_Product_Category_ID (0);
			setMSV3_Server_Product_Category_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_Server_Product_Category (Properties ctx, ResultSet rs, String trxName)
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

	/** Set MSV3 Product_Category.
		@param MSV3_Server_Product_Category_ID MSV3 Product_Category	  */
	@Override
	public void setMSV3_Server_Product_Category_ID (int MSV3_Server_Product_Category_ID)
	{
		if (MSV3_Server_Product_Category_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_Server_Product_Category_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_Server_Product_Category_ID, Integer.valueOf(MSV3_Server_Product_Category_ID));
	}

	/** Get MSV3 Product_Category.
		@return MSV3 Product_Category	  */
	@Override
	public int getMSV3_Server_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_Server_Product_Category_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}