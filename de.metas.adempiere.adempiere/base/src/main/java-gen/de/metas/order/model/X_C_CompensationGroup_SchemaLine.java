/** Generated Model - DO NOT CHANGE */
package de.metas.order.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_CompensationGroup_SchemaLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_CompensationGroup_SchemaLine extends org.compiere.model.PO implements I_C_CompensationGroup_SchemaLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1180626619L;

    /** Standard Constructor */
    public X_C_CompensationGroup_SchemaLine (Properties ctx, int C_CompensationGroup_SchemaLine_ID, String trxName)
    {
      super (ctx, C_CompensationGroup_SchemaLine_ID, trxName);
      /** if (C_CompensationGroup_SchemaLine_ID == 0)
        {
			setC_CompensationGroup_Schema_ID (0);
			setC_CompensationGroup_SchemaLine_ID (0);
			setM_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_CompensationGroup_SchemaLine (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class);
	}

	@Override
	public void setC_CompensationGroup_Schema(de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema)
	{
		set_ValueFromPO(COLUMNNAME_C_CompensationGroup_Schema_ID, de.metas.order.model.I_C_CompensationGroup_Schema.class, C_CompensationGroup_Schema);
	}

	/** Set Compensation Group Schema.
		@param C_CompensationGroup_Schema_ID Compensation Group Schema	  */
	@Override
	public void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID)
	{
		if (C_CompensationGroup_Schema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_Schema_ID, Integer.valueOf(C_CompensationGroup_Schema_ID));
	}

	/** Get Compensation Group Schema.
		@return Compensation Group Schema	  */
	@Override
	public int getC_CompensationGroup_Schema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CompensationGroup_Schema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Compensation Group Schema Line.
		@param C_CompensationGroup_SchemaLine_ID Compensation Group Schema Line	  */
	@Override
	public void setC_CompensationGroup_SchemaLine_ID (int C_CompensationGroup_SchemaLine_ID)
	{
		if (C_CompensationGroup_SchemaLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_SchemaLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_CompensationGroup_SchemaLine_ID, Integer.valueOf(C_CompensationGroup_SchemaLine_ID));
	}

	/** Get Compensation Group Schema Line.
		@return Compensation Group Schema Line	  */
	@Override
	public int getC_CompensationGroup_SchemaLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CompensationGroup_SchemaLine_ID);
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