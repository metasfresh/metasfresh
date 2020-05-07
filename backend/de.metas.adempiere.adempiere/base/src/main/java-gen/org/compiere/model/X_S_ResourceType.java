/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_ResourceType
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_ResourceType extends org.compiere.model.PO implements I_S_ResourceType, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -665473995L;

    /** Standard Constructor */
    public X_S_ResourceType (Properties ctx, int S_ResourceType_ID, String trxName)
    {
      super (ctx, S_ResourceType_ID, trxName);
      /** if (S_ResourceType_ID == 0)
        {
			setAllowUoMFractions (false); // N
			setC_UOM_ID (0);
			setIsDateSlot (false);
			setIsSingleAssignment (false);
			setIsTimeSlot (false);
			setM_Product_Category_ID (0);
			setName (null);
			setOnFriday (true); // Y
			setOnMonday (true); // Y
			setOnSaturday (false);
			setOnSunday (false);
			setOnThursday (true); // Y
			setOnTuesday (true); // Y
			setOnWednesday (true); // Y
			setS_ResourceType_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_S_ResourceType (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Bruchteil der Mengeneinheit zulassen.
		@param AllowUoMFractions 
		Allow Unit of Measure Fractions
	  */
	@Override
	public void setAllowUoMFractions (boolean AllowUoMFractions)
	{
		set_Value (COLUMNNAME_AllowUoMFractions, Boolean.valueOf(AllowUoMFractions));
	}

	/** Get Bruchteil der Mengeneinheit zulassen.
		@return Allow Unit of Measure Fractions
	  */
	@Override
	public boolean isAllowUoMFractions () 
	{
		Object oo = get_Value(COLUMNNAME_AllowUoMFractions);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Unit of Measure
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Unit of Measure
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abrechenbare Menge.
		@param ChargeableQty Abrechenbare Menge	  */
	@Override
	public void setChargeableQty (int ChargeableQty)
	{
		set_Value (COLUMNNAME_ChargeableQty, Integer.valueOf(ChargeableQty));
	}

	/** Get Abrechenbare Menge.
		@return Abrechenbare Menge	  */
	@Override
	public int getChargeableQty () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ChargeableQty);
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

	/** Set Tag.
		@param IsDateSlot 
		Resource has day slot availability
	  */
	@Override
	public void setIsDateSlot (boolean IsDateSlot)
	{
		set_Value (COLUMNNAME_IsDateSlot, Boolean.valueOf(IsDateSlot));
	}

	/** Get Tag.
		@return Resource has day slot availability
	  */
	@Override
	public boolean isDateSlot () 
	{
		Object oo = get_Value(COLUMNNAME_IsDateSlot);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Nur einmalige Zuordnung.
		@param IsSingleAssignment 
		Only one assignment at a time (no double-booking or overlapping)
	  */
	@Override
	public void setIsSingleAssignment (boolean IsSingleAssignment)
	{
		set_Value (COLUMNNAME_IsSingleAssignment, Boolean.valueOf(IsSingleAssignment));
	}

	/** Get Nur einmalige Zuordnung.
		@return Only one assignment at a time (no double-booking or overlapping)
	  */
	@Override
	public boolean isSingleAssignment () 
	{
		Object oo = get_Value(COLUMNNAME_IsSingleAssignment);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zeitabschnitt.
		@param IsTimeSlot 
		Resource has time slot availability
	  */
	@Override
	public void setIsTimeSlot (boolean IsTimeSlot)
	{
		set_Value (COLUMNNAME_IsTimeSlot, Boolean.valueOf(IsTimeSlot));
	}

	/** Get Zeitabschnitt.
		@return Resource has time slot availability
	  */
	@Override
	public boolean isTimeSlot () 
	{
		Object oo = get_Value(COLUMNNAME_IsTimeSlot);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Produkt Kategorie.
		@param M_Product_Category_ID 
		Kategorie eines Produktes
	  */
	@Override
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Produkt Kategorie.
		@return Kategorie eines Produktes
	  */
	@Override
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
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

	/** Set Freitag.
		@param OnFriday 
		Available on Fridays
	  */
	@Override
	public void setOnFriday (boolean OnFriday)
	{
		set_Value (COLUMNNAME_OnFriday, Boolean.valueOf(OnFriday));
	}

	/** Get Freitag.
		@return Available on Fridays
	  */
	@Override
	public boolean isOnFriday () 
	{
		Object oo = get_Value(COLUMNNAME_OnFriday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Montag.
		@param OnMonday 
		Available on Mondays
	  */
	@Override
	public void setOnMonday (boolean OnMonday)
	{
		set_Value (COLUMNNAME_OnMonday, Boolean.valueOf(OnMonday));
	}

	/** Get Montag.
		@return Available on Mondays
	  */
	@Override
	public boolean isOnMonday () 
	{
		Object oo = get_Value(COLUMNNAME_OnMonday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Samstag.
		@param OnSaturday 
		Available on Saturday
	  */
	@Override
	public void setOnSaturday (boolean OnSaturday)
	{
		set_Value (COLUMNNAME_OnSaturday, Boolean.valueOf(OnSaturday));
	}

	/** Get Samstag.
		@return Available on Saturday
	  */
	@Override
	public boolean isOnSaturday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSaturday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sonntag.
		@param OnSunday 
		Available on Sundays
	  */
	@Override
	public void setOnSunday (boolean OnSunday)
	{
		set_Value (COLUMNNAME_OnSunday, Boolean.valueOf(OnSunday));
	}

	/** Get Sonntag.
		@return Available on Sundays
	  */
	@Override
	public boolean isOnSunday () 
	{
		Object oo = get_Value(COLUMNNAME_OnSunday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Donnerstag.
		@param OnThursday 
		Available on Thursdays
	  */
	@Override
	public void setOnThursday (boolean OnThursday)
	{
		set_Value (COLUMNNAME_OnThursday, Boolean.valueOf(OnThursday));
	}

	/** Get Donnerstag.
		@return Available on Thursdays
	  */
	@Override
	public boolean isOnThursday () 
	{
		Object oo = get_Value(COLUMNNAME_OnThursday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Dienstag.
		@param OnTuesday 
		Available on Tuesdays
	  */
	@Override
	public void setOnTuesday (boolean OnTuesday)
	{
		set_Value (COLUMNNAME_OnTuesday, Boolean.valueOf(OnTuesday));
	}

	/** Get Dienstag.
		@return Available on Tuesdays
	  */
	@Override
	public boolean isOnTuesday () 
	{
		Object oo = get_Value(COLUMNNAME_OnTuesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mittwoch.
		@param OnWednesday 
		Available on Wednesdays
	  */
	@Override
	public void setOnWednesday (boolean OnWednesday)
	{
		set_Value (COLUMNNAME_OnWednesday, Boolean.valueOf(OnWednesday));
	}

	/** Get Mittwoch.
		@return Available on Wednesdays
	  */
	@Override
	public boolean isOnWednesday () 
	{
		Object oo = get_Value(COLUMNNAME_OnWednesday);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ressourcenart.
		@param S_ResourceType_ID Ressourcenart	  */
	@Override
	public void setS_ResourceType_ID (int S_ResourceType_ID)
	{
		if (S_ResourceType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ResourceType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ResourceType_ID, Integer.valueOf(S_ResourceType_ID));
	}

	/** Get Ressourcenart.
		@return Ressourcenart	  */
	@Override
	public int getS_ResourceType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_ResourceType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Endzeitpunkt.
		@param TimeSlotEnd 
		Time when timeslot ends
	  */
	@Override
	public void setTimeSlotEnd (java.sql.Timestamp TimeSlotEnd)
	{
		set_Value (COLUMNNAME_TimeSlotEnd, TimeSlotEnd);
	}

	/** Get Endzeitpunkt.
		@return Time when timeslot ends
	  */
	@Override
	public java.sql.Timestamp getTimeSlotEnd () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_TimeSlotEnd);
	}

	/** Set Startzeitpunkt.
		@param TimeSlotStart 
		Time when timeslot starts
	  */
	@Override
	public void setTimeSlotStart (java.sql.Timestamp TimeSlotStart)
	{
		set_Value (COLUMNNAME_TimeSlotStart, TimeSlotStart);
	}

	/** Get Startzeitpunkt.
		@return Time when timeslot starts
	  */
	@Override
	public java.sql.Timestamp getTimeSlotStart () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_TimeSlotStart);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}