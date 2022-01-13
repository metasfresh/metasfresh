// Generated Model - DO NOT CHANGE
package de.metas.ui.web.base.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Board_RecordAssignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_WEBUI_Board_RecordAssignment extends org.compiere.model.PO implements I_WEBUI_Board_RecordAssignment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2057186035L;

    /** Standard Constructor */
    public X_WEBUI_Board_RecordAssignment (final Properties ctx, final int WEBUI_Board_RecordAssignment_ID, @Nullable final String trxName)
    {
      super (ctx, WEBUI_Board_RecordAssignment_ID, trxName);
    }

    /** Load Constructor */
    public X_WEBUI_Board_RecordAssignment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public de.metas.ui.web.base.model.I_WEBUI_Board getWEBUI_Board()
	{
		return get_ValueAsPO(COLUMNNAME_WEBUI_Board_ID, de.metas.ui.web.base.model.I_WEBUI_Board.class);
	}

	@Override
	public void setWEBUI_Board(final de.metas.ui.web.base.model.I_WEBUI_Board WEBUI_Board)
	{
		set_ValueFromPO(COLUMNNAME_WEBUI_Board_ID, de.metas.ui.web.base.model.I_WEBUI_Board.class, WEBUI_Board);
	}

	@Override
	public void setWEBUI_Board_ID (final int WEBUI_Board_ID)
	{
		if (WEBUI_Board_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_ID, WEBUI_Board_ID);
	}

	@Override
	public int getWEBUI_Board_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_Board_ID);
	}

	@Override
	public de.metas.ui.web.base.model.I_WEBUI_Board_Lane getWEBUI_Board_Lane()
	{
		return get_ValueAsPO(COLUMNNAME_WEBUI_Board_Lane_ID, de.metas.ui.web.base.model.I_WEBUI_Board_Lane.class);
	}

	@Override
	public void setWEBUI_Board_Lane(final de.metas.ui.web.base.model.I_WEBUI_Board_Lane WEBUI_Board_Lane)
	{
		set_ValueFromPO(COLUMNNAME_WEBUI_Board_Lane_ID, de.metas.ui.web.base.model.I_WEBUI_Board_Lane.class, WEBUI_Board_Lane);
	}

	@Override
	public void setWEBUI_Board_Lane_ID (final int WEBUI_Board_Lane_ID)
	{
		if (WEBUI_Board_Lane_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_Lane_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_Lane_ID, WEBUI_Board_Lane_ID);
	}

	@Override
	public int getWEBUI_Board_Lane_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_Board_Lane_ID);
	}

	@Override
	public void setWEBUI_Board_RecordAssignment_ID (final int WEBUI_Board_RecordAssignment_ID)
	{
		if (WEBUI_Board_RecordAssignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_RecordAssignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_RecordAssignment_ID, WEBUI_Board_RecordAssignment_ID);
	}

	@Override
	public int getWEBUI_Board_RecordAssignment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_Board_RecordAssignment_ID);
	}
}