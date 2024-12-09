<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
=======
// Generated Model - DO NOT CHANGE
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
<<<<<<< HEAD

/** Generated Model for M_PriceList_Version
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_M_PriceList_Version extends org.compiere.model.PO implements I_M_PriceList_Version, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1249893862L;

    /** Standard Constructor */
    public X_M_PriceList_Version (Properties ctx, int M_PriceList_Version_ID, String trxName)
    {
      super (ctx, M_PriceList_Version_ID, trxName);
      /** if (M_PriceList_Version_ID == 0)
        {
			setFallbackToBasePriceListPrices (false); // N
			setM_PriceList_ID (0);
			setM_PriceList_Version_ID (0);
			setName (null); // @#Date@
			setProcessed (false); // N
			setValidFrom (new Timestamp( System.currentTimeMillis() )); // @#Date@
        } */
    }

    /** Load Constructor */
    public X_M_PriceList_Version (Properties ctx, ResultSet rs, String trxName)
=======
import javax.annotation.Nullable;

/** Generated Model for M_PriceList_Version
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_PriceList_Version extends org.compiere.model.PO implements I_M_PriceList_Version, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1093307806L;

    /** Standard Constructor */
    public X_M_PriceList_Version (final Properties ctx, final int M_PriceList_Version_ID, @Nullable final String trxName)
    {
      super (ctx, M_PriceList_Version_ID, trxName);
    }

    /** Load Constructor */
    public X_M_PriceList_Version (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
=======
	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_C_Region getC_Region()
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(final org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	@Override
	public void setC_Region_ID (final int C_Region_ID)
	{
		if (C_Region_ID < 1) 
			set_Value (COLUMNNAME_C_Region_ID, null);
		else 
			set_Value (COLUMNNAME_C_Region_ID, C_Region_ID);
	}

	@Override
	public int getC_Region_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Region_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Description, Description);
	}

<<<<<<< HEAD
	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema() throws RuntimeException
=======
	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class);
	}

	@Override
<<<<<<< HEAD
	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
=======
	public void setM_DiscountSchema(final org.compiere.model.I_M_DiscountSchema M_DiscountSchema)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_ID, org.compiere.model.I_M_DiscountSchema.class, M_DiscountSchema);
	}

<<<<<<< HEAD
	/** Set Rabatt Schema.
		@param M_DiscountSchema_ID
		Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1)
			set_Value (COLUMNNAME_M_DiscountSchema_ID, null);
		else
			set_Value (COLUMNNAME_M_DiscountSchema_ID, Integer.valueOf(M_DiscountSchema_ID));
	}

	/** Get Rabatt Schema.
		@return Schema um den prozentualen Rabatt zu berechnen
	  */
	@Override
	public int getM_DiscountSchema_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DiscountSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList getM_PriceList() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class);
	}

	@Override
	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList)
	{
		set_ValueFromPO(COLUMNNAME_M_PriceList_ID, org.compiere.model.I_M_PriceList.class, M_PriceList);
	}

	/** Set Preisliste.
		@param M_PriceList_ID
		Unique identifier of a Price List
	  */
	@Override
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Preisliste.
		@return Unique identifier of a Price List
	  */
	@Override
	public int getM_PriceList_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList_Version getM_Pricelist_Version_Base() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Pricelist_Version_Base_ID, org.compiere.model.I_M_PriceList_Version.class);
	}

	@Override
	public void setM_Pricelist_Version_Base(org.compiere.model.I_M_PriceList_Version M_Pricelist_Version_Base)
	{
		set_ValueFromPO(COLUMNNAME_M_Pricelist_Version_Base_ID, org.compiere.model.I_M_PriceList_Version.class, M_Pricelist_Version_Base);
	}

	/** Set Basis-Preislistenversion.
		@param M_Pricelist_Version_Base_ID
		Basis für Preiskalkulationen
	  */
	@Override
	public void setM_Pricelist_Version_Base_ID (int M_Pricelist_Version_Base_ID)
	{
		if (M_Pricelist_Version_Base_ID < 1)
			set_Value (COLUMNNAME_M_Pricelist_Version_Base_ID, null);
		else
			set_Value (COLUMNNAME_M_Pricelist_Version_Base_ID, Integer.valueOf(M_Pricelist_Version_Base_ID));
	}

	/** Get Basis-Preislistenversion.
		@return Basis für Preiskalkulationen
	  */
	@Override
	public int getM_Pricelist_Version_Base_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Pricelist_Version_Base_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Version Preisliste.
		@param M_PriceList_Version_ID
		Identifies a unique instance of a Price List
	  */
	@Override
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	/** Get Version Preisliste.
		@return Identifies a unique instance of a Price List
	  */
	@Override
	public int getM_PriceList_Version_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID);
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
=======
	@Override
	public void setM_DiscountSchema_ID (final int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_ID, M_DiscountSchema_ID);
	}

	@Override
	public int getM_DiscountSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_ID);
	}

	@Override
	public void setM_PriceList_ID (final int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_ID, M_PriceList_ID);
	}

	@Override
	public int getM_PriceList_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_ID);
	}

	@Override
	public void setM_Pricelist_Version_Base_ID (final int M_Pricelist_Version_Base_ID)
	{
		if (M_Pricelist_Version_Base_ID < 1) 
			set_Value (COLUMNNAME_M_Pricelist_Version_Base_ID, null);
		else 
			set_Value (COLUMNNAME_M_Pricelist_Version_Base_ID, M_Pricelist_Version_Base_ID);
	}

	@Override
	public int getM_Pricelist_Version_Base_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Pricelist_Version_Base_ID);
	}

	@Override
	public void setM_PriceList_Version_ID (final int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, M_PriceList_Version_ID);
	}

	@Override
	public int getM_PriceList_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_PriceList_Version_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Name, Name);
	}

<<<<<<< HEAD
	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Erstellen.
		@param ProcCreate Erstellen	  */
	@Override
	public void setProcCreate (java.lang.String ProcCreate)
=======
	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setProcCreate (final @Nullable java.lang.String ProcCreate)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ProcCreate, ProcCreate);
	}

<<<<<<< HEAD
	/** Get Erstellen.
		@return Erstellen	  */
	@Override
	public java.lang.String getProcCreate ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProcCreate);
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

	/** Set Gültig ab.
		@param ValidFrom
		Valid from including this date (first day)
	  */
	@Override
	public void setValidFrom (java.sql.Timestamp ValidFrom)
=======
	@Override
	public java.lang.String getProcCreate() 
	{
		return get_ValueAsString(COLUMNNAME_ProcCreate);
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
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

<<<<<<< HEAD
	/** Get Gültig ab.
		@return Valid from including this date (first day)
	  */
	@Override
	public java.sql.Timestamp getValidFrom ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
=======
	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}