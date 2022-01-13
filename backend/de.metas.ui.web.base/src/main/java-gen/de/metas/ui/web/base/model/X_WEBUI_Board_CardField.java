// Generated Model - DO NOT CHANGE
package de.metas.ui.web.base.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_Board_CardField
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_WEBUI_Board_CardField extends org.compiere.model.PO implements I_WEBUI_Board_CardField, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -51246966L;

    /** Standard Constructor */
    public X_WEBUI_Board_CardField (final Properties ctx, final int WEBUI_Board_CardField_ID, @Nullable final String trxName)
    {
      super (ctx, WEBUI_Board_CardField_ID, trxName);
    }

    /** Load Constructor */
    public X_WEBUI_Board_CardField (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(final org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	@Override
	public void setAD_Column_ID (final int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
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
	public void setWEBUI_Board_CardField_ID (final int WEBUI_Board_CardField_ID)
	{
		if (WEBUI_Board_CardField_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_CardField_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Board_CardField_ID, WEBUI_Board_CardField_ID);
	}

	@Override
	public int getWEBUI_Board_CardField_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_Board_CardField_ID);
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
}