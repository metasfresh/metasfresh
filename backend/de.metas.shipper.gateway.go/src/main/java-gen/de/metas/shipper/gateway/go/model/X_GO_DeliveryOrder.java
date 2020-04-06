/** Generated Model - DO NOT CHANGE */
package de.metas.shipper.gateway.go.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for GO_DeliveryOrder
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_GO_DeliveryOrder extends org.compiere.model.PO implements I_GO_DeliveryOrder, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -522317920L;

    /** Standard Constructor */
    public X_GO_DeliveryOrder (Properties ctx, int GO_DeliveryOrder_ID, String trxName)
    {
      super (ctx, GO_DeliveryOrder_ID, trxName);
      /** if (GO_DeliveryOrder_ID == 0)
        {
			setGO_DeliverToBPartner_ID (0);
			setGO_DeliverToBPLocation_ID (0);
			setGO_DeliverToCompanyName (null);
			setGO_DeliverToDepartment (null);
			setGO_DeliverToLocation_ID (0);
			setGO_DeliveryOrder_ID (0);
			setGO_GrossWeightKg (0);
			setGO_NumberOfPackages (0);
			setGO_PaidMode (null);
			setGO_PickupCompanyName (null);
			setGO_PickupDate (new Timestamp( System.currentTimeMillis() ));
			setGO_PickupLocation_ID (0);
			setGO_SelfDelivery (null);
			setGO_SelfPickup (null);
			setGO_ServiceType (null);
			setM_Shipper_ID (0);
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_GO_DeliveryOrder (Properties ctx, ResultSet rs, String trxName)
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

	/** Set AX4 Number.
		@param GO_AX4Number AX4 Number	  */
	@Override
	public void setGO_AX4Number (java.lang.String GO_AX4Number)
	{
		set_Value (COLUMNNAME_GO_AX4Number, GO_AX4Number);
	}

	/** Get AX4 Number.
		@return AX4 Number	  */
	@Override
	public java.lang.String getGO_AX4Number ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_AX4Number);
	}

	/** Set Customer Reference.
		@param GO_CustomerReference Customer Reference	  */
	@Override
	public void setGO_CustomerReference (java.lang.String GO_CustomerReference)
	{
		set_Value (COLUMNNAME_GO_CustomerReference, GO_CustomerReference);
	}

	/** Get Customer Reference.
		@return Customer Reference	  */
	@Override
	public java.lang.String getGO_CustomerReference ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_CustomerReference);
	}

	@Override
	public org.compiere.model.I_C_BPartner getGO_DeliverToBPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GO_DeliverToBPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setGO_DeliverToBPartner(org.compiere.model.I_C_BPartner GO_DeliverToBPartner)
	{
		set_ValueFromPO(COLUMNNAME_GO_DeliverToBPartner_ID, org.compiere.model.I_C_BPartner.class, GO_DeliverToBPartner);
	}

	/** Set Deliver To BPartner.
		@param GO_DeliverToBPartner_ID Deliver To BPartner	  */
	@Override
	public void setGO_DeliverToBPartner_ID (int GO_DeliverToBPartner_ID)
	{
		if (GO_DeliverToBPartner_ID < 1)
			set_Value (COLUMNNAME_GO_DeliverToBPartner_ID, null);
		else
			set_Value (COLUMNNAME_GO_DeliverToBPartner_ID, Integer.valueOf(GO_DeliverToBPartner_ID));
	}

	/** Get Deliver To BPartner.
		@return Deliver To BPartner	  */
	@Override
	public int getGO_DeliverToBPartner_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_DeliverToBPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getGO_DeliverToBPLocation() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GO_DeliverToBPLocation_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setGO_DeliverToBPLocation(org.compiere.model.I_C_BPartner_Location GO_DeliverToBPLocation)
	{
		set_ValueFromPO(COLUMNNAME_GO_DeliverToBPLocation_ID, org.compiere.model.I_C_BPartner_Location.class, GO_DeliverToBPLocation);
	}

	/** Set Deliver To BPartner Location.
		@param GO_DeliverToBPLocation_ID Deliver To BPartner Location	  */
	@Override
	public void setGO_DeliverToBPLocation_ID (int GO_DeliverToBPLocation_ID)
	{
		if (GO_DeliverToBPLocation_ID < 1)
			set_Value (COLUMNNAME_GO_DeliverToBPLocation_ID, null);
		else
			set_Value (COLUMNNAME_GO_DeliverToBPLocation_ID, Integer.valueOf(GO_DeliverToBPLocation_ID));
	}

	/** Get Deliver To BPartner Location.
		@return Deliver To BPartner Location	  */
	@Override
	public int getGO_DeliverToBPLocation_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_DeliverToBPLocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Deliver To Company Name.
		@param GO_DeliverToCompanyName Deliver To Company Name	  */
	@Override
	public void setGO_DeliverToCompanyName (java.lang.String GO_DeliverToCompanyName)
	{
		set_Value (COLUMNNAME_GO_DeliverToCompanyName, GO_DeliverToCompanyName);
	}

	/** Get Deliver To Company Name.
		@return Deliver To Company Name	  */
	@Override
	public java.lang.String getGO_DeliverToCompanyName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_DeliverToCompanyName);
	}

	/** Set Deliver To Company Name2.
		@param GO_DeliverToCompanyName2 Deliver To Company Name2	  */
	@Override
	public void setGO_DeliverToCompanyName2 (java.lang.String GO_DeliverToCompanyName2)
	{
		set_Value (COLUMNNAME_GO_DeliverToCompanyName2, GO_DeliverToCompanyName2);
	}

	/** Get Deliver To Company Name2.
		@return Deliver To Company Name2	  */
	@Override
	public java.lang.String getGO_DeliverToCompanyName2 ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_DeliverToCompanyName2);
	}

	/** Set Deliver To Date.
		@param GO_DeliverToDate Deliver To Date	  */
	@Override
	public void setGO_DeliverToDate (java.sql.Timestamp GO_DeliverToDate)
	{
		set_Value (COLUMNNAME_GO_DeliverToDate, GO_DeliverToDate);
	}

	/** Get Deliver To Date.
		@return Deliver To Date	  */
	@Override
	public java.sql.Timestamp getGO_DeliverToDate ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_GO_DeliverToDate);
	}

	/** Set Deliver To Company Department.
		@param GO_DeliverToDepartment Deliver To Company Department	  */
	@Override
	public void setGO_DeliverToDepartment (java.lang.String GO_DeliverToDepartment)
	{
		set_Value (COLUMNNAME_GO_DeliverToDepartment, GO_DeliverToDepartment);
	}

	/** Get Deliver To Company Department.
		@return Deliver To Company Department	  */
	@Override
	public java.lang.String getGO_DeliverToDepartment ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_DeliverToDepartment);
	}

	@Override
	public org.compiere.model.I_C_Location getGO_DeliverToLocation() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GO_DeliverToLocation_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setGO_DeliverToLocation(org.compiere.model.I_C_Location GO_DeliverToLocation)
	{
		set_ValueFromPO(COLUMNNAME_GO_DeliverToLocation_ID, org.compiere.model.I_C_Location.class, GO_DeliverToLocation);
	}

	/** Set Deliver To Address.
		@param GO_DeliverToLocation_ID Deliver To Address	  */
	@Override
	public void setGO_DeliverToLocation_ID (int GO_DeliverToLocation_ID)
	{
		if (GO_DeliverToLocation_ID < 1)
			set_Value (COLUMNNAME_GO_DeliverToLocation_ID, null);
		else
			set_Value (COLUMNNAME_GO_DeliverToLocation_ID, Integer.valueOf(GO_DeliverToLocation_ID));
	}

	/** Get Deliver To Address.
		@return Deliver To Address	  */
	@Override
	public int getGO_DeliverToLocation_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_DeliverToLocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Deliver To Note.
		@param GO_DeliverToNote Deliver To Note	  */
	@Override
	public void setGO_DeliverToNote (java.lang.String GO_DeliverToNote)
	{
		set_Value (COLUMNNAME_GO_DeliverToNote, GO_DeliverToNote);
	}

	/** Get Deliver To Note.
		@return Deliver To Note	  */
	@Override
	public java.lang.String getGO_DeliverToNote ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_DeliverToNote);
	}

	/** Set Deliver To Phone No.
		@param GO_DeliverToPhoneNo Deliver To Phone No	  */
	@Override
	public void setGO_DeliverToPhoneNo (java.lang.String GO_DeliverToPhoneNo)
	{
		set_Value (COLUMNNAME_GO_DeliverToPhoneNo, GO_DeliverToPhoneNo);
	}

	/** Get Deliver To Phone No.
		@return Deliver To Phone No	  */
	@Override
	public java.lang.String getGO_DeliverToPhoneNo ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_DeliverToPhoneNo);
	}

	/** Set Deliver Time (from).
		@param GO_DeliverToTimeFrom Deliver Time (from)	  */
	@Override
	public void setGO_DeliverToTimeFrom (java.sql.Timestamp GO_DeliverToTimeFrom)
	{
		set_Value (COLUMNNAME_GO_DeliverToTimeFrom, GO_DeliverToTimeFrom);
	}

	/** Get Deliver Time (from).
		@return Deliver Time (from)	  */
	@Override
	public java.sql.Timestamp getGO_DeliverToTimeFrom ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_GO_DeliverToTimeFrom);
	}

	/** Set Deliver Time (to).
		@param GO_DeliverToTimeTo Deliver Time (to)	  */
	@Override
	public void setGO_DeliverToTimeTo (java.sql.Timestamp GO_DeliverToTimeTo)
	{
		set_Value (COLUMNNAME_GO_DeliverToTimeTo, GO_DeliverToTimeTo);
	}

	/** Get Deliver Time (to).
		@return Deliver Time (to)	  */
	@Override
	public java.sql.Timestamp getGO_DeliverToTimeTo ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_GO_DeliverToTimeTo);
	}

	/** Set GO Delivery Order.
		@param GO_DeliveryOrder_ID GO Delivery Order	  */
	@Override
	public void setGO_DeliveryOrder_ID (int GO_DeliveryOrder_ID)
	{
		if (GO_DeliveryOrder_ID < 1)
			set_ValueNoCheck (COLUMNNAME_GO_DeliveryOrder_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_GO_DeliveryOrder_ID, Integer.valueOf(GO_DeliveryOrder_ID));
	}

	/** Get GO Delivery Order.
		@return GO Delivery Order	  */
	@Override
	public int getGO_DeliveryOrder_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_DeliveryOrder_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gross Weight (Kg).
		@param GO_GrossWeightKg Gross Weight (Kg)	  */
	@Override
	public void setGO_GrossWeightKg (int GO_GrossWeightKg)
	{
		set_Value (COLUMNNAME_GO_GrossWeightKg, Integer.valueOf(GO_GrossWeightKg));
	}

	/** Get Gross Weight (Kg).
		@return Gross Weight (Kg)	  */
	@Override
	public int getGO_GrossWeightKg ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_GrossWeightKg);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set HWB Number.
		@param GO_HWBNumber HWB Number	  */
	@Override
	public void setGO_HWBNumber (java.lang.String GO_HWBNumber)
	{
		set_Value (COLUMNNAME_GO_HWBNumber, GO_HWBNumber);
	}

	/** Get HWB Number.
		@return HWB Number	  */
	@Override
	public java.lang.String getGO_HWBNumber ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_HWBNumber);
	}

	/** Set Number Of Packages.
		@param GO_NumberOfPackages Number Of Packages	  */
	@Override
	public void setGO_NumberOfPackages (int GO_NumberOfPackages)
	{
		set_Value (COLUMNNAME_GO_NumberOfPackages, Integer.valueOf(GO_NumberOfPackages));
	}

	/** Get Number Of Packages.
		@return Number Of Packages	  */
	@Override
	public int getGO_NumberOfPackages ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_NumberOfPackages);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/**
	 * GO_OrderStatus AD_Reference_ID=540807
	 * Reference name: GO_OrderStatus
	 */
	public static final int GO_ORDERSTATUS_AD_Reference_ID=540807;
	/** New = 1 */
	public static final String GO_ORDERSTATUS_New = "1";
	/** Approved = 3 */
	public static final String GO_ORDERSTATUS_Approved = "3";
	/** Canceled = 20 */
	public static final String GO_ORDERSTATUS_Canceled = "20";
	/** Set Order Status.
		@param GO_OrderStatus Order Status	  */
	@Override
	public void setGO_OrderStatus (java.lang.String GO_OrderStatus)
	{

		set_Value (COLUMNNAME_GO_OrderStatus, GO_OrderStatus);
	}

	/** Get Order Status.
		@return Order Status	  */
	@Override
	public java.lang.String getGO_OrderStatus ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_OrderStatus);
	}

	/** Set Package content description.
		@param GO_PackageContentDescription Package content description	  */
	@Override
	public void setGO_PackageContentDescription (java.lang.String GO_PackageContentDescription)
	{
		set_Value (COLUMNNAME_GO_PackageContentDescription, GO_PackageContentDescription);
	}

	/** Get Package content description.
		@return Package content description	  */
	@Override
	public java.lang.String getGO_PackageContentDescription ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_PackageContentDescription);
	}

	/**
	 * GO_PaidMode AD_Reference_ID=540804
	 * Reference name: GO_PaidMode
	 */
	public static final int GO_PAIDMODE_AD_Reference_ID=540804;
	/** Prepaid = 0 */
	public static final String GO_PAIDMODE_Prepaid = "0";
	/** Unpaid = 1 */
	public static final String GO_PAIDMODE_Unpaid = "1";
	/** Set Paid Mode.
		@param GO_PaidMode Paid Mode	  */
	@Override
	public void setGO_PaidMode (java.lang.String GO_PaidMode)
	{

		set_Value (COLUMNNAME_GO_PaidMode, GO_PaidMode);
	}

	/** Get Paid Mode.
		@return Paid Mode	  */
	@Override
	public java.lang.String getGO_PaidMode ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_PaidMode);
	}

	/** Set Pickup Company Name.
		@param GO_PickupCompanyName Pickup Company Name	  */
	@Override
	public void setGO_PickupCompanyName (java.lang.String GO_PickupCompanyName)
	{
		set_Value (COLUMNNAME_GO_PickupCompanyName, GO_PickupCompanyName);
	}

	/** Get Pickup Company Name.
		@return Pickup Company Name	  */
	@Override
	public java.lang.String getGO_PickupCompanyName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_PickupCompanyName);
	}

	/** Set Pickup Date.
		@param GO_PickupDate Pickup Date	  */
	@Override
	public void setGO_PickupDate (java.sql.Timestamp GO_PickupDate)
	{
		set_Value (COLUMNNAME_GO_PickupDate, GO_PickupDate);
	}

	/** Get Pickup Date.
		@return Pickup Date	  */
	@Override
	public java.sql.Timestamp getGO_PickupDate ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_GO_PickupDate);
	}

	@Override
	public org.compiere.model.I_C_Location getGO_PickupLocation() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GO_PickupLocation_ID, org.compiere.model.I_C_Location.class);
	}

	@Override
	public void setGO_PickupLocation(org.compiere.model.I_C_Location GO_PickupLocation)
	{
		set_ValueFromPO(COLUMNNAME_GO_PickupLocation_ID, org.compiere.model.I_C_Location.class, GO_PickupLocation);
	}

	/** Set Pickup address.
		@param GO_PickupLocation_ID Pickup address	  */
	@Override
	public void setGO_PickupLocation_ID (int GO_PickupLocation_ID)
	{
		if (GO_PickupLocation_ID < 1)
			set_Value (COLUMNNAME_GO_PickupLocation_ID, null);
		else
			set_Value (COLUMNNAME_GO_PickupLocation_ID, Integer.valueOf(GO_PickupLocation_ID));
	}

	/** Get Pickup address.
		@return Pickup address	  */
	@Override
	public int getGO_PickupLocation_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GO_PickupLocation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pickup note.
		@param GO_PickupNote Pickup note	  */
	@Override
	public void setGO_PickupNote (java.lang.String GO_PickupNote)
	{
		set_Value (COLUMNNAME_GO_PickupNote, GO_PickupNote);
	}

	/** Get Pickup note.
		@return Pickup note	  */
	@Override
	public java.lang.String getGO_PickupNote ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_PickupNote);
	}

	/** Set Pickup Time (from).
		@param GO_PickupTimeFrom Pickup Time (from)	  */
	@Override
	public void setGO_PickupTimeFrom (java.sql.Timestamp GO_PickupTimeFrom)
	{
		set_Value (COLUMNNAME_GO_PickupTimeFrom, GO_PickupTimeFrom);
	}

	/** Get Pickup Time (from).
		@return Pickup Time (from)	  */
	@Override
	public java.sql.Timestamp getGO_PickupTimeFrom ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_GO_PickupTimeFrom);
	}

	/** Set Pickup Time (to).
		@param GO_PickupTimeTo Pickup Time (to)	  */
	@Override
	public void setGO_PickupTimeTo (java.sql.Timestamp GO_PickupTimeTo)
	{
		set_Value (COLUMNNAME_GO_PickupTimeTo, GO_PickupTimeTo);
	}

	/** Get Pickup Time (to).
		@return Pickup Time (to)	  */
	@Override
	public java.sql.Timestamp getGO_PickupTimeTo ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_GO_PickupTimeTo);
	}

	/**
	 * GO_SelfDelivery AD_Reference_ID=540805
	 * Reference name: GO_SelfDelivery
	 */
	public static final int GO_SELFDELIVERY_AD_Reference_ID=540805;
	/** Pickup = 0 */
	public static final String GO_SELFDELIVERY_Pickup = "0";
	/** SelfPickup = 1 */
	public static final String GO_SELFDELIVERY_SelfPickup = "1";
	/** Set Self Delivery.
		@param GO_SelfDelivery Self Delivery	  */
	@Override
	public void setGO_SelfDelivery (java.lang.String GO_SelfDelivery)
	{

		set_Value (COLUMNNAME_GO_SelfDelivery, GO_SelfDelivery);
	}

	/** Get Self Delivery.
		@return Self Delivery	  */
	@Override
	public java.lang.String getGO_SelfDelivery ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_SelfDelivery);
	}

	/**
	 * GO_SelfPickup AD_Reference_ID=540806
	 * Reference name: GO_SelfPickup
	 */
	public static final int GO_SELFPICKUP_AD_Reference_ID=540806;
	/** Delivery = 0 */
	public static final String GO_SELFPICKUP_Delivery = "0";
	/** SelfDelivery = 1 */
	public static final String GO_SELFPICKUP_SelfDelivery = "1";
	/** Set Self Pickup.
		@param GO_SelfPickup Self Pickup	  */
	@Override
	public void setGO_SelfPickup (java.lang.String GO_SelfPickup)
	{

		set_Value (COLUMNNAME_GO_SelfPickup, GO_SelfPickup);
	}

	/** Get Self Pickup.
		@return Self Pickup	  */
	@Override
	public java.lang.String getGO_SelfPickup ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_SelfPickup);
	}

	/**
	 * GO_ServiceType AD_Reference_ID=540803
	 * Reference name: GO_ServiceType
	 */
	public static final int GO_SERVICETYPE_AD_Reference_ID=540803;
	/** Overnight = 0 */
	public static final String GO_SERVICETYPE_Overnight = "0";
	/** OvernightLetter = 1 */
	public static final String GO_SERVICETYPE_OvernightLetter = "1";
	/** International = 2 */
	public static final String GO_SERVICETYPE_International = "2";
	/** InternationalLetter = 3 */
	public static final String GO_SERVICETYPE_InternationalLetter = "3";
	/** Set Service type.
		@param GO_ServiceType Service type	  */
	@Override
	public void setGO_ServiceType (java.lang.String GO_ServiceType)
	{

		set_Value (COLUMNNAME_GO_ServiceType, GO_ServiceType);
	}

	/** Get Service type.
		@return Service type	  */
	@Override
	public java.lang.String getGO_ServiceType ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_GO_ServiceType);
	}

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	/** Set Lieferweg.
		@param M_Shipper_ID
		Methode oder Art der Warenlieferung
	  */
	@Override
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Lieferweg.
		@return Methode oder Art der Warenlieferung
	  */
	@Override
	public int getM_Shipper_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Transport Auftrag.
		@param M_ShipperTransportation_ID Transport Auftrag	  */
	@Override
	public void setM_ShipperTransportation_ID (int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1)
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, null);
		else
			set_Value (COLUMNNAME_M_ShipperTransportation_ID, Integer.valueOf(M_ShipperTransportation_ID));
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

	/** Set Verarbeitet.
		@param Processed
		Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde.
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