/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DataEntry_Group
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DataEntry_Group extends org.compiere.model.PO implements I_DataEntry_Group, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1202103903L;

    /** Standard Constructor */
    public X_DataEntry_Group (Properties ctx, int DataEntry_Group_ID, String trxName)
    {
      super (ctx, DataEntry_Group_ID, trxName);
      /** if (DataEntry_Group_ID == 0)
        {
			setDataEntry_Group_ID (0);
			setDataEntry_TargetWindow_ID (0);
			setName (null);
			setTabName (null);
        } */
    }

    /** Load Constructor */
    public X_DataEntry_Group (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Eingabegruppe.
		@param DataEntry_Group_ID Eingabegruppe	  */
	@Override
	public void setDataEntry_Group_ID (int DataEntry_Group_ID)
	{
		if (DataEntry_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Group_ID, Integer.valueOf(DataEntry_Group_ID));
	}

	/** Get Eingabegruppe.
		@return Eingabegruppe	  */
	@Override
	public int getDataEntry_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Window getDataEntry_TargetWindow() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_TargetWindow_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setDataEntry_TargetWindow(org.compiere.model.I_AD_Window DataEntry_TargetWindow)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_TargetWindow_ID, org.compiere.model.I_AD_Window.class, DataEntry_TargetWindow);
	}

	/** Set Eingabefenster.
		@param DataEntry_TargetWindow_ID 
		Bestehendes Fenster, das um diese Eingabegruppe erweitert werden soll
	  */
	@Override
	public void setDataEntry_TargetWindow_ID (int DataEntry_TargetWindow_ID)
	{
		if (DataEntry_TargetWindow_ID < 1) 
			set_Value (COLUMNNAME_DataEntry_TargetWindow_ID, null);
		else 
			set_Value (COLUMNNAME_DataEntry_TargetWindow_ID, Integer.valueOf(DataEntry_TargetWindow_ID));
	}

	/** Get Eingabefenster.
		@return Bestehendes Fenster, das um diese Eingabegruppe erweitert werden soll
	  */
	@Override
	public int getDataEntry_TargetWindow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_TargetWindow_ID);
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

	/** Set Registername.
		@param TabName Registername	  */
	@Override
	public void setTabName (java.lang.String TabName)
	{
		set_Value (COLUMNNAME_TabName, TabName);
	}

	/** Get Registername.
		@return Registername	  */
	@Override
	public java.lang.String getTabName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TabName);
	}
}