<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
=======
// Generated Model - DO NOT CHANGE
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
package de.metas.shipping.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
<<<<<<< HEAD

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
=======
import javax.annotation.Nullable;

/** Generated Model for M_ShippingPackage
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ShippingPackage extends org.compiere.model.PO implements I_M_ShippingPackage, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1336235374L;

    /** Standard Constructor */
    public X_M_ShippingPackage (final Properties ctx, final int M_ShippingPackage_ID, @Nullable final String trxName)
    {
      super (ctx, M_ShippingPackage_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShippingPackage (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


<<<<<<< HEAD
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
=======
	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setActualDischargeQuantity (final BigDecimal ActualDischargeQuantity)
	{
		set_Value (COLUMNNAME_ActualDischargeQuantity, ActualDischargeQuantity);
	}

	@Override
	public BigDecimal getActualDischargeQuantity() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualDischargeQuantity);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualLoadQty (final BigDecimal ActualLoadQty)
	{
		set_Value (COLUMNNAME_ActualLoadQty, ActualLoadQty);
	}

	@Override
	public BigDecimal getActualLoadQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualLoadQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBatch (final @Nullable java.lang.String Batch)
	{
		set_Value (COLUMNNAME_Batch, Batch);
	}

	@Override
	public java.lang.String getBatch() 
	{
		return get_ValueAsString(COLUMNNAME_Batch);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BPartner_Location_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
<<<<<<< HEAD
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
=======
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

<<<<<<< HEAD
	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
=======
	@Override
	public void setC_Order_ID (final int C_Order_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setIsToBeFetched (final boolean IsToBeFetched)
	{
		set_Value (COLUMNNAME_IsToBeFetched, IsToBeFetched);
	}

	@Override
	public boolean isToBeFetched() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsToBeFetched);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
<<<<<<< HEAD
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
=======
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

<<<<<<< HEAD
	/** Set Lieferung/Wareneingang.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	@Override
	public void setM_InOut_ID (int M_InOut_ID)
=======
	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public void setM_Locator_ID (final int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, M_Locator_ID);
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public org.compiere.model.I_M_Package getM_Package()
	{
		return get_ValueAsPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class);
	}

	@Override
<<<<<<< HEAD
	public void setM_Package(org.compiere.model.I_M_Package M_Package)
=======
	public void setM_Package(final org.compiere.model.I_M_Package M_Package)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_M_Package_ID, org.compiere.model.I_M_Package.class, M_Package);
	}

<<<<<<< HEAD
	/** Set Package.
		@param M_Package_ID 
		Shipment Package
	  */
	@Override
	public void setM_Package_ID (int M_Package_ID)
=======
	@Override
	public void setM_Package_ID (final int M_Package_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, M_Package_ID);
	}

	@Override
	public int getM_Package_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Package_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public de.metas.shipping.model.I_M_ShipperTransportation getM_ShipperTransportation()
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipperTransportation_ID, de.metas.shipping.model.I_M_ShipperTransportation.class);
	}

	@Override
<<<<<<< HEAD
	public void setM_ShipperTransportation(de.metas.shipping.model.I_M_ShipperTransportation M_ShipperTransportation)
=======
	public void setM_ShipperTransportation(final de.metas.shipping.model.I_M_ShipperTransportation M_ShipperTransportation)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_M_ShipperTransportation_ID, de.metas.shipping.model.I_M_ShipperTransportation.class, M_ShipperTransportation);
	}

<<<<<<< HEAD
	/** Set Transport Auftrag.
		@param M_ShipperTransportation_ID Transport Auftrag	  */
	@Override
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID)
=======
	@Override
	public void setM_ShipperTransportation_ID (final int M_ShipperTransportation_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_ShipperTransportation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_M_ShipperTransportation_ID, M_ShipperTransportation_ID);
	}

	@Override
	public int getM_ShipperTransportation_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipperTransportation_ID);
	}

	@Override
	public void setM_ShippingPackage_ID (final int M_ShippingPackage_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_ShippingPackage_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShippingPackage_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_M_ShippingPackage_ID, M_ShippingPackage_ID);
	}

	@Override
	public int getM_ShippingPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShippingPackage_ID);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Note, Note);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public void setPackageNetTotal (final BigDecimal PackageNetTotal)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_PackageNetTotal, PackageNetTotal);
	}

<<<<<<< HEAD
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
=======
	@Override
	public BigDecimal getPackageNetTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PackageNetTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPackageWeight (final @Nullable BigDecimal PackageWeight)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_PackageWeight, PackageWeight);
	}

<<<<<<< HEAD
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
=======
	@Override
	public BigDecimal getPackageWeight() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PackageWeight);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProductName (final @Nullable java.lang.String ProductName)
	{
		throw new IllegalArgumentException ("ProductName is virtual column");	}

	@Override
	public java.lang.String getProductName() 
	{
		return get_ValueAsString(COLUMNNAME_ProductName);
	}

	@Override
	public void setProductValue (final @Nullable java.lang.String ProductValue)
	{
		throw new IllegalArgumentException ("ProductValue is virtual column");	}

	@Override
	public java.lang.String getProductValue() 
	{
		return get_ValueAsString(COLUMNNAME_ProductValue);
	}

	@Override
	public void setQtyLU (final @Nullable BigDecimal QtyLU)
	{
		set_Value (COLUMNNAME_QtyLU, QtyLU);
	}

	@Override
	public BigDecimal getQtyLU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyLU);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyTU (final @Nullable BigDecimal QtyTU)
	{
		set_Value (COLUMNNAME_QtyTU, QtyTU);
	}

	@Override
	public BigDecimal getQtyTU() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyTU);
		return bd != null ? bd : BigDecimal.ZERO;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}