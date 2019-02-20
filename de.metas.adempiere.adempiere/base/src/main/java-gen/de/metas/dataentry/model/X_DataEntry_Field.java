/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DataEntry_Field
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DataEntry_Field extends org.compiere.model.PO implements I_DataEntry_Field, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2121135502L;

    /** Standard Constructor */
    public X_DataEntry_Field (Properties ctx, int DataEntry_Field_ID, String trxName)
    {
      super (ctx, DataEntry_Field_ID, trxName);
      /** if (DataEntry_Field_ID == 0)
        {
			setDataEntry_Field_ID (0);
			setDataEntry_RecordType (null); // T
			setDataEntry_SubGroup_ID (0);
			setIsMandatory (false); // N
			setName (null);
			setPersonalDataCategory (null); // NP
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM DataEntry_Field WHERE DataEntry_SubGroup_ID=@DataEntry_SubGroup_ID/0@
        } */
    }

    /** Load Constructor */
    public X_DataEntry_Field (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Dateneingabefeld.
		@param DataEntry_Field_ID Dateneingabefeld	  */
	@Override
	public void setDataEntry_Field_ID (int DataEntry_Field_ID)
	{
		if (DataEntry_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Field_ID, Integer.valueOf(DataEntry_Field_ID));
	}

	/** Get Dateneingabefeld.
		@return Dateneingabefeld	  */
	@Override
	public int getDataEntry_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DataEntry_RecordType AD_Reference_ID=540956
	 * Reference name: DataEntry_RecordType
	 */
	public static final int DATAENTRY_RECORDTYPE_AD_Reference_ID=540956;
	/** Date = D */
	public static final String DATAENTRY_RECORDTYPE_Date = "D";
	/** Number = N */
	public static final String DATAENTRY_RECORDTYPE_Number = "N";
	/** List = L */
	public static final String DATAENTRY_RECORDTYPE_List = "L";
	/** Text = T */
	public static final String DATAENTRY_RECORDTYPE_Text = "T";
	/** YesNo = B */
	public static final String DATAENTRY_RECORDTYPE_YesNo = "B";
	/** LongText = LT */
	public static final String DATAENTRY_RECORDTYPE_LongText = "LT";
	/** Set Datentyp.
		@param DataEntry_RecordType Datentyp	  */
	@Override
	public void setDataEntry_RecordType (java.lang.String DataEntry_RecordType)
	{

		set_Value (COLUMNNAME_DataEntry_RecordType, DataEntry_RecordType);
	}

	/** Get Datentyp.
		@return Datentyp	  */
	@Override
	public java.lang.String getDataEntry_RecordType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DataEntry_RecordType);
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_SubGroup getDataEntry_SubGroup() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_SubGroup_ID, de.metas.dataentry.model.I_DataEntry_SubGroup.class);
	}

	@Override
	public void setDataEntry_SubGroup(de.metas.dataentry.model.I_DataEntry_SubGroup DataEntry_SubGroup)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_SubGroup_ID, de.metas.dataentry.model.I_DataEntry_SubGroup.class, DataEntry_SubGroup);
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

	/** Set Pflichtangabe.
		@param IsMandatory 
		Data entry is required in this column
	  */
	@Override
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Pflichtangabe.
		@return Data entry is required in this column
	  */
	@Override
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
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

	/** 
	 * PersonalDataCategory AD_Reference_ID=540857
	 * Reference name: PersonalDataCategory
	 */
	public static final int PERSONALDATACATEGORY_AD_Reference_ID=540857;
	/** NotPersonal = NP */
	public static final String PERSONALDATACATEGORY_NotPersonal = "NP";
	/** Personal = P */
	public static final String PERSONALDATACATEGORY_Personal = "P";
	/** SensitivePersonal = SP */
	public static final String PERSONALDATACATEGORY_SensitivePersonal = "SP";
	/** Set Personal Data Category.
		@param PersonalDataCategory Personal Data Category	  */
	@Override
	public void setPersonalDataCategory (java.lang.String PersonalDataCategory)
	{

		set_Value (COLUMNNAME_PersonalDataCategory, PersonalDataCategory);
	}

	/** Get Personal Data Category.
		@return Personal Data Category	  */
	@Override
	public java.lang.String getPersonalDataCategory () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PersonalDataCategory);
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