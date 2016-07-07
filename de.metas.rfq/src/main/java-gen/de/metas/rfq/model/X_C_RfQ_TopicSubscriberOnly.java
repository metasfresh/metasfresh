/** Generated Model - DO NOT CHANGE */
package de.metas.rfq.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_RfQ_TopicSubscriberOnly
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RfQ_TopicSubscriberOnly extends org.compiere.model.PO implements I_C_RfQ_TopicSubscriberOnly, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1032139653L;

    /** Standard Constructor */
    public X_C_RfQ_TopicSubscriberOnly (Properties ctx, int C_RfQ_TopicSubscriberOnly_ID, String trxName)
    {
      super (ctx, C_RfQ_TopicSubscriberOnly_ID, trxName);
      /** if (C_RfQ_TopicSubscriberOnly_ID == 0)
        {
			setC_RfQ_TopicSubscriber_ID (0);
			setC_RfQ_TopicSubscriberOnly_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_RfQ_TopicSubscriberOnly (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.rfq.model.I_C_RfQ_TopicSubscriber getC_RfQ_TopicSubscriber() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RfQ_TopicSubscriber_ID, de.metas.rfq.model.I_C_RfQ_TopicSubscriber.class);
	}

	@Override
	public void setC_RfQ_TopicSubscriber(de.metas.rfq.model.I_C_RfQ_TopicSubscriber C_RfQ_TopicSubscriber)
	{
		set_ValueFromPO(COLUMNNAME_C_RfQ_TopicSubscriber_ID, de.metas.rfq.model.I_C_RfQ_TopicSubscriber.class, C_RfQ_TopicSubscriber);
	}

	/** Set RfQ Subscriber.
		@param C_RfQ_TopicSubscriber_ID 
		Request for Quotation Topic Subscriber
	  */
	@Override
	public void setC_RfQ_TopicSubscriber_ID (int C_RfQ_TopicSubscriber_ID)
	{
		if (C_RfQ_TopicSubscriber_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_TopicSubscriber_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_TopicSubscriber_ID, Integer.valueOf(C_RfQ_TopicSubscriber_ID));
	}

	/** Get RfQ Subscriber.
		@return Request for Quotation Topic Subscriber
	  */
	@Override
	public int getC_RfQ_TopicSubscriber_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_TopicSubscriber_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RfQ Topic Subscriber Restriction.
		@param C_RfQ_TopicSubscriberOnly_ID 
		Include Subscriber only for certain products or product categories
	  */
	@Override
	public void setC_RfQ_TopicSubscriberOnly_ID (int C_RfQ_TopicSubscriberOnly_ID)
	{
		if (C_RfQ_TopicSubscriberOnly_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_TopicSubscriberOnly_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_TopicSubscriberOnly_ID, Integer.valueOf(C_RfQ_TopicSubscriberOnly_ID));
	}

	/** Get RfQ Topic Subscriber Restriction.
		@return Include Subscriber only for certain products or product categories
	  */
	@Override
	public int getC_RfQ_TopicSubscriberOnly_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_TopicSubscriberOnly_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
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

	/** Set Produkt-Kategorie.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt-Kategorie.
		@return Category of a Product
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