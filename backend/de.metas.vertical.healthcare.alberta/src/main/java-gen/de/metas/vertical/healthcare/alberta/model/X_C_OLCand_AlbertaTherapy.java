// Generated Model - DO NOT CHANGE
package de.metas.vertical.healthcare.alberta.model;

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_OLCand_AlbertaTherapy
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_C_OLCand_AlbertaTherapy extends PO implements I_C_OLCand_AlbertaTherapy, I_Persistent
{

	private static final long serialVersionUID = -249973234L;

	/**
	 * Standard Constructor
	 */
	public X_C_OLCand_AlbertaTherapy(final Properties ctx, final int C_OLCand_AlbertaTherapy_ID, @Nullable final String trxName)
	{
		super(ctx, C_OLCand_AlbertaTherapy_ID, trxName);
	}

	/**
	 * Load Constructor
	 */
	public X_C_OLCand_AlbertaTherapy(final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Load Meta Data
	 */
	@Override
	protected POInfo initPO(final Properties ctx)
	{
		return POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_OLCand_AlbertaTherapy_ID(final int C_OLCand_AlbertaTherapy_ID)
	{
		if (C_OLCand_AlbertaTherapy_ID < 1)
			set_ValueNoCheck(COLUMNNAME_C_OLCand_AlbertaTherapy_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_C_OLCand_AlbertaTherapy_ID, C_OLCand_AlbertaTherapy_ID);
	}

	@Override
	public int getC_OLCand_AlbertaTherapy_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_AlbertaTherapy_ID);
	}

	@Override
	public void setC_OLCand_ID(final int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1)
			set_Value(COLUMNNAME_C_OLCand_ID, null);
		else
			set_Value(COLUMNNAME_C_OLCand_ID, C_OLCand_ID);
	}

	@Override
	public int getC_OLCand_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_ID);
	}

	@Override
	public void setTherapy(final String Therapy)
	{
		set_Value(COLUMNNAME_Therapy, Therapy);
	}

	@Override
	public String getTherapy()
	{
		return get_ValueAsString(COLUMNNAME_Therapy);
	}
}