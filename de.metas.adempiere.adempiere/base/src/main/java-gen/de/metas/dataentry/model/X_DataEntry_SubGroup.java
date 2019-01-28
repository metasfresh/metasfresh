/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DataEntry_SubGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DataEntry_SubGroup extends org.compiere.model.PO implements I_DataEntry_SubGroup, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1194595281L;

    /** Standard Constructor */
    public X_DataEntry_SubGroup (Properties ctx, int DataEntry_SubGroup_ID, String trxName)
    {
      super (ctx, DataEntry_SubGroup_ID, trxName);
      /** if (DataEntry_SubGroup_ID == 0)
        {
			setDataEntry_Group_ID (0);
			setDataEntry_SubGroup_ID (0);
			setName (null);
			setTabName (null);
        } */
    }

    /** Load Constructor */
    public X_DataEntry_SubGroup (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.dataentry.model.I_DataEntry_Group getDataEntry_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Group_ID, de.metas.dataentry.model.I_DataEntry_Group.class);
	}

	@Override
	public void setDataEntry_Group(de.metas.dataentry.model.I_DataEntry_Group DataEntry_Group)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Group_ID, de.metas.dataentry.model.I_DataEntry_Group.class, DataEntry_Group);
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

	/** Set Untergruppe.
		@param DataEntry_SubGroup_ID Untergruppe	  */
	@Override
	public void setDataEntry_SubGroup_ID (int DataEntry_SubGroup_ID)
	{
		if (DataEntry_SubGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_SubGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_SubGroup_ID, Integer.valueOf(DataEntry_SubGroup_ID));
	}

	/** Get Untergruppe.
		@return Untergruppe	  */
	@Override
	public int getDataEntry_SubGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_SubGroup_ID);
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