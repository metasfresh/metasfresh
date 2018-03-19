/** Generated Model - DO NOT CHANGE */
package de.metas.tourplanning.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_DeliveryDay
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_DeliveryDay extends org.compiere.model.PO implements I_M_DeliveryDay, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1490329952L;

    /** Standard Constructor */
    public X_M_DeliveryDay (Properties ctx, int M_DeliveryDay_ID, String trxName)
    {
      super (ctx, M_DeliveryDay_ID, trxName);
      /** if (M_DeliveryDay_ID == 0)
        {
			setDeliveryDate (new Timestamp( System.currentTimeMillis() ));
			setDeliveryDateTimeMax (new Timestamp( System.currentTimeMillis() ));
			setIsManual (true); // Y
			setM_DeliveryDay_ID (0);
			setM_Tour_ID (0);
			setM_TourVersion_ID (0);
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_M_DeliveryDay (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Puffer (Std).
		@param BufferHours Puffer (Std)	  */
	@Override
	public void setBufferHours (int BufferHours)
	{
		set_Value (COLUMNNAME_BufferHours, Integer.valueOf(BufferHours));
	}

	/** Get Puffer (Std).
		@return Puffer (Std)	  */
	@Override
	public int getBufferHours () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BufferHours);
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

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferdatum.
		@param DeliveryDate Lieferdatum	  */
	@Override
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	/** Get Lieferdatum.
		@return Lieferdatum	  */
	@Override
	public java.sql.Timestamp getDeliveryDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDate);
	}

	/** Set Lieferzeit (inkl. Puffer).
		@param DeliveryDateTimeMax Lieferzeit (inkl. Puffer)	  */
	@Override
	public void setDeliveryDateTimeMax (java.sql.Timestamp DeliveryDateTimeMax)
	{
		set_Value (COLUMNNAME_DeliveryDateTimeMax, DeliveryDateTimeMax);
	}

	/** Get Lieferzeit (inkl. Puffer).
		@return Lieferzeit (inkl. Puffer)	  */
	@Override
	public java.sql.Timestamp getDeliveryDateTimeMax () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDateTimeMax);
	}

	/** Set Manuell.
		@param IsManual 
		Dies ist ein manueller Vorgang
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return Dies ist ein manueller Vorgang
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Delivery Days.
		@param M_DeliveryDay_ID Delivery Days	  */
	@Override
	public void setM_DeliveryDay_ID (int M_DeliveryDay_ID)
	{
		if (M_DeliveryDay_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DeliveryDay_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DeliveryDay_ID, Integer.valueOf(M_DeliveryDay_ID));
	}

	/** Get Delivery Days.
		@return Delivery Days	  */
	@Override
	public int getM_DeliveryDay_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DeliveryDay_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.tourplanning.model.I_M_Tour getM_Tour() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Tour_ID, de.metas.tourplanning.model.I_M_Tour.class);
	}

	@Override
	public void setM_Tour(de.metas.tourplanning.model.I_M_Tour M_Tour)
	{
		set_ValueFromPO(COLUMNNAME_M_Tour_ID, de.metas.tourplanning.model.I_M_Tour.class, M_Tour);
	}

	/** Set Tour.
		@param M_Tour_ID Tour	  */
	@Override
	public void setM_Tour_ID (int M_Tour_ID)
	{
		if (M_Tour_ID < 1) 
			set_Value (COLUMNNAME_M_Tour_ID, null);
		else 
			set_Value (COLUMNNAME_M_Tour_ID, Integer.valueOf(M_Tour_ID));
	}

	/** Get Tour.
		@return Tour	  */
	@Override
	public int getM_Tour_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Tour_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.tourplanning.model.I_M_Tour_Instance getM_Tour_Instance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Tour_Instance_ID, de.metas.tourplanning.model.I_M_Tour_Instance.class);
	}

	@Override
	public void setM_Tour_Instance(de.metas.tourplanning.model.I_M_Tour_Instance M_Tour_Instance)
	{
		set_ValueFromPO(COLUMNNAME_M_Tour_Instance_ID, de.metas.tourplanning.model.I_M_Tour_Instance.class, M_Tour_Instance);
	}

	/** Set Tour Instance.
		@param M_Tour_Instance_ID Tour Instance	  */
	@Override
	public void setM_Tour_Instance_ID (int M_Tour_Instance_ID)
	{
		if (M_Tour_Instance_ID < 1) 
			set_Value (COLUMNNAME_M_Tour_Instance_ID, null);
		else 
			set_Value (COLUMNNAME_M_Tour_Instance_ID, Integer.valueOf(M_Tour_Instance_ID));
	}

	/** Get Tour Instance.
		@return Tour Instance	  */
	@Override
	public int getM_Tour_Instance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Tour_Instance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.tourplanning.model.I_M_TourVersion getM_TourVersion() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_TourVersion_ID, de.metas.tourplanning.model.I_M_TourVersion.class);
	}

	@Override
	public void setM_TourVersion(de.metas.tourplanning.model.I_M_TourVersion M_TourVersion)
	{
		set_ValueFromPO(COLUMNNAME_M_TourVersion_ID, de.metas.tourplanning.model.I_M_TourVersion.class, M_TourVersion);
	}

	/** Set Tour Version.
		@param M_TourVersion_ID Tour Version	  */
	@Override
	public void setM_TourVersion_ID (int M_TourVersion_ID)
	{
		if (M_TourVersion_ID < 1) 
			set_Value (COLUMNNAME_M_TourVersion_ID, null);
		else 
			set_Value (COLUMNNAME_M_TourVersion_ID, Integer.valueOf(M_TourVersion_ID));
	}

	/** Get Tour Version.
		@return Tour Version	  */
	@Override
	public int getM_TourVersion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_TourVersion_ID);
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

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}