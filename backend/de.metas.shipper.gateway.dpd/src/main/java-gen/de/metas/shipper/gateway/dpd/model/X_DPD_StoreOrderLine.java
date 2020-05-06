/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.dpd.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DPD_StoreOrderLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DPD_StoreOrderLine extends org.compiere.model.PO implements I_DPD_StoreOrderLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1399998494L;

    /** Standard Constructor */
    public X_DPD_StoreOrderLine (Properties ctx, int DPD_StoreOrderLine_ID, String trxName)
    {
      super (ctx, DPD_StoreOrderLine_ID, trxName);
      /** if (DPD_StoreOrderLine_ID == 0)
        {
			setDPD_StoreOrderLine_ID (0);
			setHeightInCm (0); // 0
			setLengthInCm (0); // 0
			setWeightInKg (0); // 0
			setWidthInCm (0); // 0
        } */
    }

    /** Load Constructor */
    public X_DPD_StoreOrderLine (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder getDPD_StoreOrder()
	{
		return get_ValueAsPO(COLUMNNAME_DPD_StoreOrder_ID, de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder.class);
	}

	@Override
	public void setDPD_StoreOrder(de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder DPD_StoreOrder)
	{
		set_ValueFromPO(COLUMNNAME_DPD_StoreOrder_ID, de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder.class, DPD_StoreOrder);
	}

	/** Set DPD StoreOrder.
		@param DPD_StoreOrder_ID DPD StoreOrder	  */
	@Override
	public void setDPD_StoreOrder_ID (int DPD_StoreOrder_ID)
	{
		if (DPD_StoreOrder_ID < 1) 
			set_Value (COLUMNNAME_DPD_StoreOrder_ID, null);
		else 
			set_Value (COLUMNNAME_DPD_StoreOrder_ID, Integer.valueOf(DPD_StoreOrder_ID));
	}

	/** Get DPD StoreOrder.
		@return DPD StoreOrder	  */
	@Override
	public int getDPD_StoreOrder_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_StoreOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DPD Store Order Line.
		@param DPD_StoreOrderLine_ID DPD Store Order Line	  */
	@Override
	public void setDPD_StoreOrderLine_ID (int DPD_StoreOrderLine_ID)
	{
		if (DPD_StoreOrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_StoreOrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_StoreOrderLine_ID, Integer.valueOf(DPD_StoreOrderLine_ID));
	}

	/** Get DPD Store Order Line.
		@return DPD Store Order Line	  */
	@Override
	public int getDPD_StoreOrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_StoreOrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Height In Cm.
		@param HeightInCm Height In Cm	  */
	@Override
	public void setHeightInCm (int HeightInCm)
	{
		set_Value (COLUMNNAME_HeightInCm, Integer.valueOf(HeightInCm));
	}

	/** Get Height In Cm.
		@return Height In Cm	  */
	@Override
	public int getHeightInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HeightInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Length In Cm.
		@param LengthInCm Length In Cm	  */
	@Override
	public void setLengthInCm (int LengthInCm)
	{
		set_Value (COLUMNNAME_LengthInCm, Integer.valueOf(LengthInCm));
	}

	/** Get Length In Cm.
		@return Length In Cm	  */
	@Override
	public int getLengthInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LengthInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Package getM_Package()
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

	/** Set Package Content.
		@param PackageContent Package Content	  */
	@Override
	public void setPackageContent (java.lang.String PackageContent)
	{
		set_Value (COLUMNNAME_PackageContent, PackageContent);
	}

	/** Get Package Content.
		@return Package Content	  */
	@Override
	public java.lang.String getPackageContent () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PackageContent);
	}

	/** Set Weight In Kg.
		@param WeightInKg Weight In Kg	  */
	@Override
	public void setWeightInKg (int WeightInKg)
	{
		set_Value (COLUMNNAME_WeightInKg, Integer.valueOf(WeightInKg));
	}

	/** Get Weight In Kg.
		@return Weight In Kg	  */
	@Override
	public int getWeightInKg () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WeightInKg);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Width In Cm.
		@param WidthInCm Width In Cm	  */
	@Override
	public void setWidthInCm (int WidthInCm)
	{
		set_Value (COLUMNNAME_WidthInCm, Integer.valueOf(WidthInCm));
	}

	/** Get Width In Cm.
		@return Width In Cm	  */
	@Override
	public int getWidthInCm () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WidthInCm);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}