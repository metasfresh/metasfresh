/** Generated Model - DO NOT CHANGE */
package de.metas.ui.web.base.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Board_RecordAssignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_WEBUI_Board_RecordAssignment extends org.compiere.model.PO implements I_WEBUI_Board_RecordAssignment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1717774210L;

    /** Standard Constructor */
    public X_WEBUI_Board_RecordAssignment (Properties ctx, int WEBUI_Board_RecordAssignment_ID, String trxName)
    {
      super (ctx, WEBUI_Board_RecordAssignment_ID, trxName);
      /** if (WEBUI_Board_RecordAssignment_ID == 0)
        {
			setRecord_ID (0);
			setSeqNo (0);
			setWEBUI_Board_ID (0);
			setWEBUI_Board_Lane_ID (0);
			setWEBUI_Board_RecordAssignment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_WEBUI_Board_RecordAssignment (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
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

	@Override
	public de.metas.ui.web.base.model.I_WEBUI_Board getWEBUI_Board() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_WEBUI_Board_ID, de.metas.ui.web.base.model.I_WEBUI_Board.class);
	}

	@Override
	public void setWEBUI_Board(de.metas.ui.web.base.model.I_WEBUI_Board WEBUI_Board)
	{
		set_ValueFromPO(COLUMNNAME_WEBUI_Board_ID, de.metas.ui.web.base.model.I_WEBUI_Board.class, WEBUI_Board);
	}

	/** Set Board.
		@param WEBUI_Board_ID Board	  */
	@Override
	public void setWEBUI_Board_ID (int WEBUI_Board_ID)
	{
		if (WEBUI_Board_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_ID, Integer.valueOf(WEBUI_Board_ID));
	}

	/** Get Board.
		@return Board	  */
	@Override
	public int getWEBUI_Board_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_Board_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.ui.web.base.model.I_WEBUI_Board_Lane getWEBUI_Board_Lane() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_WEBUI_Board_Lane_ID, de.metas.ui.web.base.model.I_WEBUI_Board_Lane.class);
	}

	@Override
	public void setWEBUI_Board_Lane(de.metas.ui.web.base.model.I_WEBUI_Board_Lane WEBUI_Board_Lane)
	{
		set_ValueFromPO(COLUMNNAME_WEBUI_Board_Lane_ID, de.metas.ui.web.base.model.I_WEBUI_Board_Lane.class, WEBUI_Board_Lane);
	}

	/** Set Board lane.
		@param WEBUI_Board_Lane_ID Board lane	  */
	@Override
	public void setWEBUI_Board_Lane_ID (int WEBUI_Board_Lane_ID)
	{
		if (WEBUI_Board_Lane_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_Lane_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_Lane_ID, Integer.valueOf(WEBUI_Board_Lane_ID));
	}

	/** Get Board lane.
		@return Board lane	  */
	@Override
	public int getWEBUI_Board_Lane_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_Board_Lane_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Board record assignment.
		@param WEBUI_Board_RecordAssignment_ID Board record assignment	  */
	@Override
	public void setWEBUI_Board_RecordAssignment_ID (int WEBUI_Board_RecordAssignment_ID)
	{
		if (WEBUI_Board_RecordAssignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_RecordAssignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_RecordAssignment_ID, Integer.valueOf(WEBUI_Board_RecordAssignment_ID));
	}

	/** Get Board record assignment.
		@return Board record assignment	  */
	@Override
	public int getWEBUI_Board_RecordAssignment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_WEBUI_Board_RecordAssignment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}