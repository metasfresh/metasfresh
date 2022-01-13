// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_DocType_BoilerPlate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_DocType_BoilerPlate extends org.compiere.model.PO implements I_AD_DocType_BoilerPlate, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2032686751L;

    /** Standard Constructor */
    public X_AD_DocType_BoilerPlate (final Properties ctx, final int AD_DocType_BoilerPlate_ID, @Nullable final String trxName)
    {
      super (ctx, AD_DocType_BoilerPlate_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_DocType_BoilerPlate (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_BoilerPlate_ID (final int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1) 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, null);
		else 
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, AD_BoilerPlate_ID);
	}

	@Override
	public int getAD_BoilerPlate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_BoilerPlate_ID);
	}

	@Override
	public void setAD_DocType_BoilerPlate_ID (final int AD_DocType_BoilerPlate_ID)
	{
		if (AD_DocType_BoilerPlate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_DocType_BoilerPlate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_DocType_BoilerPlate_ID, AD_DocType_BoilerPlate_ID);
	}

	@Override
	public int getAD_DocType_BoilerPlate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_DocType_BoilerPlate_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
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
}