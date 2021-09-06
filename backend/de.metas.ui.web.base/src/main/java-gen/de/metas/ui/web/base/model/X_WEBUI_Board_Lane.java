// Generated Model - DO NOT CHANGE
package de.metas.ui.web.base.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Board_Lane
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_WEBUI_Board_Lane extends org.compiere.model.PO implements I_WEBUI_Board_Lane, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -492769515L;

    /** Standard Constructor */
    public X_WEBUI_Board_Lane (final Properties ctx, final int WEBUI_Board_Lane_ID, @Nullable final String trxName)
    {
      super (ctx, WEBUI_Board_Lane_ID, trxName);
    }

    /** Load Constructor */
    public X_WEBUI_Board_Lane (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
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
}