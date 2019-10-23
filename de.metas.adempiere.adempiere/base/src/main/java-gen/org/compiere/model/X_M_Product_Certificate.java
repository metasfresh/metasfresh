/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Certificate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Product_Certificate extends org.compiere.model.PO implements I_M_Product_Certificate, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1027055531L;

    /** Standard Constructor */
    public X_M_Product_Certificate (Properties ctx, int M_Product_Certificate_ID, String trxName)
    {
      super (ctx, M_Product_Certificate_ID, trxName);
      /** if (M_Product_Certificate_ID == 0)
        {
			setM_Product_Certificate_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Product_Certificate (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Label-Zerti-Stelle.
		@param LabelCertificationAuthority 
		Zertifizierungsstelle
	  */
	@Override
	public void setLabelCertificationAuthority (java.lang.String LabelCertificationAuthority)
	{
		set_Value (COLUMNNAME_LabelCertificationAuthority, LabelCertificationAuthority);
	}

	/** Get Label-Zerti-Stelle.
		@return Zertifizierungsstelle
	  */
	@Override
	public java.lang.String getLabelCertificationAuthority () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LabelCertificationAuthority);
	}

	@Override
	public org.compiere.model.I_M_Label getM_Label() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Label_ID, org.compiere.model.I_M_Label.class);
	}

	@Override
	public void setM_Label(org.compiere.model.I_M_Label M_Label)
	{
		set_ValueFromPO(COLUMNNAME_M_Label_ID, org.compiere.model.I_M_Label.class, M_Label);
	}

	/** Set Label List.
		@param M_Label_ID Label List	  */
	@Override
	public void setM_Label_ID (int M_Label_ID)
	{
		if (M_Label_ID < 1) 
			set_Value (COLUMNNAME_M_Label_ID, null);
		else 
			set_Value (COLUMNNAME_M_Label_ID, Integer.valueOf(M_Label_ID));
	}

	/** Get Label List.
		@return Label List	  */
	@Override
	public int getM_Label_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Label_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Label_ID.
		@param M_Product_Certificate_ID Label_ID	  */
	@Override
	public void setM_Product_Certificate_ID (int M_Product_Certificate_ID)
	{
		if (M_Product_Certificate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Certificate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Certificate_ID, Integer.valueOf(M_Product_Certificate_ID));
	}

	/** Get Label_ID.
		@return Label_ID	  */
	@Override
	public int getM_Product_Certificate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Certificate_ID);
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