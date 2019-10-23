/** Generated Model - DO NOT CHANGE */
package de.metas.dataentry.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DataEntry_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DataEntry_Line extends org.compiere.model.PO implements I_DataEntry_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2052385052L;

    /** Standard Constructor */
    public X_DataEntry_Line (Properties ctx, int DataEntry_Line_ID, String trxName)
    {
      super (ctx, DataEntry_Line_ID, trxName);
      /** if (DataEntry_Line_ID == 0)
        {
			setDataEntry_Line_ID (0);
			setDataEntry_Section_ID (0);
			setSeqNo (0); // @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM DataEntry_Line WHERE DataEntry_Section_ID=@DataEntry_Section_ID/0@
        } */
    }

    /** Load Constructor */
    public X_DataEntry_Line (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Zeile.
		@param DataEntry_Line_ID Zeile	  */
	@Override
	public void setDataEntry_Line_ID (int DataEntry_Line_ID)
	{
		if (DataEntry_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DataEntry_Line_ID, Integer.valueOf(DataEntry_Line_ID));
	}

	/** Get Zeile.
		@return Zeile	  */
	@Override
	public int getDataEntry_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DataEntry_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dataentry.model.I_DataEntry_Section getDataEntry_Section() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DataEntry_Section_ID, de.metas.dataentry.model.I_DataEntry_Section.class);
	}

	@Override
	public void setDataEntry_Section(de.metas.dataentry.model.I_DataEntry_Section DataEntry_Section)
	{
		set_ValueFromPO(COLUMNNAME_DataEntry_Section_ID, de.metas.dataentry.model.I_DataEntry_Section.class, DataEntry_Section);
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