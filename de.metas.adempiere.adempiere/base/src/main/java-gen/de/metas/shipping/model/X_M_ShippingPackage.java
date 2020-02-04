/** Generated Model - DO NOT CHANGE */
package de.metas.shipping.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShippingPackage
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ShippingPackage extends org.compiere.model.PO implements I_M_ShippingPackage, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1310228163L;

    /** Standard Constructor */
    public X_M_ShippingPackage (Properties ctx, int M_ShippingPackage_ID, String trxName)
    {
      super (ctx, M_ShippingPackage_ID, trxName);
      /** if (M_ShippingPackage_ID == 0)
        {
			setC_BPartner_Location_ID (0);
			setIsToBeFetched (false); // N
			setM_Package_ID (0);
			setM_ShipperTransportation_ID (0);
			setM_ShippingPackage_ID (0);
			setPackageNetTotal (BigDecimal.ZERO);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_M_ShippingPackage (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abholung.
		@param IsToBeFetched Abholung	  */
	@Override
	public void setIsToBeFetched (boolean IsToBeFetched)
	{
		set_Value (COLUMNNAME_IsToBeFetched, Boolean.valueOf(IsToBeFetched));
	}

	/** Get Abholung.
		@return Abholung	  */
	@Override
	public boolean isToBeFetched () 
	{
		Object oo = get_Value(COLUMNNAME_IsToBeFetched);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Lieferung/Wareneingang.
		@return Material Shipment Document
	  */
	@Override
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
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

	/** Set Package.
		@param M_Package_ID 
		Shipment Package
	  */
	@Override
	public void setM_Package_ID (int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, Integer.valueOf(M_Package_ID));
	}

	/** Get Package.
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

	@Override
	public de.metas.shipping.model.I_M_ShipperTransportation getM_ShipperTransportation()
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipperTransportation_ID, de.metas.shipping.model.I_M_ShipperTransportation.class);
	}

	@Override
	public void setM_ShipperTransportation(de.metas.shipping.model.I_M_ShipperTransportation M_ShipperTransportation)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipperTransportation_ID, de.metas.shipping.model.I_M_ShipperTransportation.class, M_ShipperTransportation);
	}

	/** Set Transport Auftrag.
		@param M_ShipperTransportation_ID Transport Auftrag	  */
	@Override
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, Integer.valueOf(M_ShipperTransportation_ID));
	}

	/** Get Transport Auftrag.
		@return Transport Auftrag	  */
	@Override
	public int getM_ShipperTransportation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipperTransportation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Shipping Package.
		@param M_ShippingPackage_ID Shipping Package	  */
	@Override
	public void setM_ShippingPackage_ID (int M_ShippingPackage_ID)
	{
		if (M_ShippingPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShippingPackage_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShippingPackage_ID, Integer.valueOf(M_ShippingPackage_ID));
	}

	/** Get Shipping Package.
		@return Shipping Package	  */
	@Override
	public int getM_ShippingPackage_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShippingPackage_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Notiz.
		@param Note 
		Optional weitere Information
	  */
	@Override
	public void setNote (java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information
	  */
	@Override
	public java.lang.String getNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Note);
	}

	/** Set Package Net Total.
		@param PackageNetTotal Package Net Total	  */
	@Override
	public void setPackageNetTotal (java.math.BigDecimal PackageNetTotal)
	{
		set_Value (COLUMNNAME_PackageNetTotal, PackageNetTotal);
	}

	/** Get Package Net Total.
		@return Package Net Total	  */
	@Override
	public java.math.BigDecimal getPackageNetTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PackageNetTotal);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Package Weight.
		@param PackageWeight 
		Weight of a package
	  */
	@Override
	public void setPackageWeight (java.math.BigDecimal PackageWeight)
	{
		set_Value (COLUMNNAME_PackageWeight, PackageWeight);
	}

	/** Get Package Weight.
		@return Weight of a package
	  */
	@Override
	public java.math.BigDecimal getPackageWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PackageWeight);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}