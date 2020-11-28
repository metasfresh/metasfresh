/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.derkurier.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DerKurier_DeliveryOrderLine_Package
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DerKurier_DeliveryOrderLine_Package extends org.compiere.model.PO implements I_DerKurier_DeliveryOrderLine_Package, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 869792793L;

    /** Standard Constructor */
    public X_DerKurier_DeliveryOrderLine_Package (Properties ctx, int DerKurier_DeliveryOrderLine_Package_ID, String trxName)
    {
      super (ctx, DerKurier_DeliveryOrderLine_Package_ID, trxName);
      /** if (DerKurier_DeliveryOrderLine_Package_ID == 0)
        {
			setDerKurier_DeliveryOrderLine_ID (0);
			setDerKurier_DeliveryOrderLine_Package_ID (0);
			setM_Package_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DerKurier_DeliveryOrderLine_Package (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine getDerKurier_DeliveryOrderLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DerKurier_DeliveryOrderLine_ID, de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.class);
	}

	@Override
	public void setDerKurier_DeliveryOrderLine(de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine DerKurier_DeliveryOrderLine)
	{
		set_ValueFromPO(COLUMNNAME_DerKurier_DeliveryOrderLine_ID, de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrderLine.class, DerKurier_DeliveryOrderLine);
	}

	/** Set DerKurier_DeliveryOrderLine.
		@param DerKurier_DeliveryOrderLine_ID DerKurier_DeliveryOrderLine	  */
	@Override
	public void setDerKurier_DeliveryOrderLine_ID (int DerKurier_DeliveryOrderLine_ID)
	{
		if (DerKurier_DeliveryOrderLine_ID < 1) 
			set_Value (COLUMNNAME_DerKurier_DeliveryOrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_DerKurier_DeliveryOrderLine_ID, Integer.valueOf(DerKurier_DeliveryOrderLine_ID));
	}

	/** Get DerKurier_DeliveryOrderLine.
		@return DerKurier_DeliveryOrderLine	  */
	@Override
	public int getDerKurier_DeliveryOrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DerKurier_DeliveryOrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DerKurier_DeliveryOrderLine_Package.
		@param DerKurier_DeliveryOrderLine_Package_ID DerKurier_DeliveryOrderLine_Package	  */
	@Override
	public void setDerKurier_DeliveryOrderLine_Package_ID (int DerKurier_DeliveryOrderLine_Package_ID)
	{
		if (DerKurier_DeliveryOrderLine_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DerKurier_DeliveryOrderLine_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DerKurier_DeliveryOrderLine_Package_ID, Integer.valueOf(DerKurier_DeliveryOrderLine_Package_ID));
	}

	/** Get DerKurier_DeliveryOrderLine_Package.
		@return DerKurier_DeliveryOrderLine_Package	  */
	@Override
	public int getDerKurier_DeliveryOrderLine_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DerKurier_DeliveryOrderLine_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Package getM_Package() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class);
	}

	@Override
	public void setM_Package(org.compiere.model.I_M_Package M_Package)
	{
		set_ValueFromPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class, M_Package);
	}

	/** Set Packstück.
		@param M_Package_ID 
		Shipment Package
	  */
	@Override
	public void setM_Package_ID (int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_Value (COLUMNNAME_M_Package_ID, null);
		else 
			set_Value (COLUMNNAME_M_Package_ID, Integer.valueOf(M_Package_ID));
	}

	/** Get Packstück.
		@return Shipment Package
	  */
	@Override
	public int getM_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}