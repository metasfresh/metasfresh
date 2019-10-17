/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DataEntry_Section
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DataEntry_Section extends org.compiere.model.PO implements I_DataEntry_Section, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -771252130L;

    /** Standard Constructor */
    public X_DataEntry_Section (Properties ctx, int DataEntry_Section_ID, String trxName)
    {
      super (ctx, DataEntry_Section_ID, trxName);
      /** if (DataEntry_Section_ID == 0)
        {
			setDataEntry_Section_ID (0);
			setDataEntry_SubTab_ID (0);
			setIsInitiallyClosed (false); // N
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_DataEntry_Section (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Sektion.
		@param DataEntry_Section_ID Sektion	  */
	@Override
	public void setDataEntry_Section_ID (int DataEntry_Section_ID)
	{
		if (DataEntry_Section_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Section_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Section_ID, Integer.valueOf(DataEntry_Section_ID));
	}

	/** Get Sektion.
		@return Sektion	  */
	@Override
	public int getDataEntry_Section_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Section_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_SubTab getDataEntry_SubTab() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_SubTab_ID, de.metas.dataentry.model.I_DataEntry_SubTab.class);
	}

	@Override
	public void setDataEntry_SubTab(de.metas.dataentry.model.I_DataEntry_SubTab DataEntry_SubTab)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_SubTab_ID, de.metas.dataentry.model.I_DataEntry_SubTab.class, DataEntry_SubTab);
	}

	/** Set Unterregister.
		@param DataEntry_SubTab_ID Unterregister	  */
	@Override
	public void setDataEntry_SubTab_ID (int DataEntry_SubTab_ID)
	{
		if (DataEntry_SubTab_ID < 1) 
			set_Value (COLUMNNAME_DataEntry_SubTab_ID, null);
		else 
			set_Value (COLUMNNAME_DataEntry_SubTab_ID, Integer.valueOf(DataEntry_SubTab_ID));
	}

	/** Get Unterregister.
		@return Unterregister	  */
	@Override
	public int getDataEntry_SubTab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_SubTab_ID);
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

	/** Set Initial geschlossen.
		@param IsInitiallyClosed 
		Legt fest, ob die Feldgruppe initial offen (sichtbar) oder geschlossen ist
	  */
	@Override
	public void setIsInitiallyClosed (boolean IsInitiallyClosed)
	{
		set_Value (COLUMNNAME_IsInitiallyClosed, Boolean.valueOf(IsInitiallyClosed));
	}

	/** Get Initial geschlossen.
		@return Legt fest, ob die Feldgruppe initial offen (sichtbar) oder geschlossen ist
	  */
	@Override
	public boolean isInitiallyClosed () 
	{
		Object oo = get_Value(COLUMNNAME_IsInitiallyClosed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Sektionsname.
		@param SectionName Sektionsname	  */
	@Override
	public void setSectionName (java.lang.String SectionName)
	{
		set_Value (COLUMNNAME_SectionName, SectionName);
	}

	/** Get Sektionsname.
		@return Sektionsname	  */
	@Override
	public java.lang.String getSectionName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SectionName);
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