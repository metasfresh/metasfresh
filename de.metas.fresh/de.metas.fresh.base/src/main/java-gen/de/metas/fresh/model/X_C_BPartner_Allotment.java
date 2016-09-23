/** Generated Model - DO NOT CHANGE */
package de.metas.fresh.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Allotment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BPartner_Allotment extends org.compiere.model.PO implements I_C_BPartner_Allotment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1834564897L;

    /** Standard Constructor */
    public X_C_BPartner_Allotment (Properties ctx, int C_BPartner_Allotment_ID, String trxName)
    {
      super (ctx, C_BPartner_Allotment_ID, trxName);
      /** if (C_BPartner_Allotment_ID == 0)
        {
			setC_Allotment_ID (0);
			setC_BPartner_Allotment_ID (0);
			setM_Product_ID (0);
			setName (null);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_Allotment (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.fresh.model.I_C_Allotment getC_Allotment() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Allotment_ID, de.metas.fresh.model.I_C_Allotment.class);
	}

	@Override
	public void setC_Allotment(de.metas.fresh.model.I_C_Allotment C_Allotment)
	{
		set_ValueFromPO(COLUMNNAME_C_Allotment_ID, de.metas.fresh.model.I_C_Allotment.class, C_Allotment);
	}

	/** Set Parzelle.
		@param C_Allotment_ID Parzelle	  */
	@Override
	public void setC_Allotment_ID (int C_Allotment_ID)
	{
		if (C_Allotment_ID < 1) 
			set_Value (COLUMNNAME_C_Allotment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Allotment_ID, Integer.valueOf(C_Allotment_ID));
	}

	/** Get Parzelle.
		@return Parzelle	  */
	@Override
	public int getC_Allotment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Allotment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartnerparzelle.
		@param C_BPartner_Allotment_ID Geschäftspartnerparzelle	  */
	@Override
	public void setC_BPartner_Allotment_ID (int C_BPartner_Allotment_ID)
	{
		if (C_BPartner_Allotment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Allotment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Allotment_ID, Integer.valueOf(C_BPartner_Allotment_ID));
	}

	/** Get Geschäftspartnerparzelle.
		@return Geschäftspartnerparzelle	  */
	@Override
	public int getC_BPartner_Allotment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Allotment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Gültig ab.
		@param ValidFrom 
		Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Gültig ab inklusiv (erster Tag)
	  */
	@Override
	public java.sql.Timestamp getValidFrom () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}