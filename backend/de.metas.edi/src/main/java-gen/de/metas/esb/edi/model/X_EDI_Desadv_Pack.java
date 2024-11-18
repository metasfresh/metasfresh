// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_Desadv_Pack
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_Desadv_Pack extends org.compiere.model.PO implements I_EDI_Desadv_Pack, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1886649508L;

    /** Standard Constructor */
    public X_EDI_Desadv_Pack (final Properties ctx, final int EDI_Desadv_Pack_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_Desadv_Pack_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_Desadv_Pack (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.esb.edi.model.I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(final de.metas.esb.edi.model.I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, de.metas.esb.edi.model.I_EDI_Desadv.class, EDI_Desadv);
	}

	@Override
	public void setEDI_Desadv_ID (final int EDI_Desadv_ID)
	{
		if (EDI_Desadv_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_ID, EDI_Desadv_ID);
	}

	@Override
	public int getEDI_Desadv_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_ID);
	}

	@Override
	public void setEDI_Desadv_Pack_ID (final int EDI_Desadv_Pack_ID)
	{
		if (EDI_Desadv_Pack_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_Pack_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_Desadv_Pack_ID, EDI_Desadv_Pack_ID);
	}

	@Override
	public int getEDI_Desadv_Pack_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_Pack_ID);
	}

	@Override
	public de.metas.esb.edi.model.I_EDI_Desadv_Pack getEDI_Desadv_Parent_Pack()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_Parent_Pack_ID, de.metas.esb.edi.model.I_EDI_Desadv_Pack.class);
	}

	@Override
	public void setEDI_Desadv_Parent_Pack(final de.metas.esb.edi.model.I_EDI_Desadv_Pack EDI_Desadv_Parent_Pack)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_Parent_Pack_ID, de.metas.esb.edi.model.I_EDI_Desadv_Pack.class, EDI_Desadv_Parent_Pack);
	}

	@Override
	public void setEDI_Desadv_Parent_Pack_ID (final int EDI_Desadv_Parent_Pack_ID)
	{
		if (EDI_Desadv_Parent_Pack_ID < 1) 
			set_Value (COLUMNNAME_EDI_Desadv_Parent_Pack_ID, null);
		else 
			set_Value (COLUMNNAME_EDI_Desadv_Parent_Pack_ID, EDI_Desadv_Parent_Pack_ID);
	}

	@Override
	public int getEDI_Desadv_Parent_Pack_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_Desadv_Parent_Pack_ID);
	}

	@Override
	public void setGTIN_PackingMaterial (final @Nullable java.lang.String GTIN_PackingMaterial)
	{
		set_Value (COLUMNNAME_GTIN_PackingMaterial, GTIN_PackingMaterial);
	}

	@Override
	public java.lang.String getGTIN_PackingMaterial() 
	{
		return get_ValueAsString(COLUMNNAME_GTIN_PackingMaterial);
	}

	@Override
	public void setIPA_SSCC18 (final java.lang.String IPA_SSCC18)
	{
		set_Value (COLUMNNAME_IPA_SSCC18, IPA_SSCC18);
	}

	@Override
	public java.lang.String getIPA_SSCC18() 
	{
		return get_ValueAsString(COLUMNNAME_IPA_SSCC18);
	}

	@Override
	public void setIsManual_IPA_SSCC18 (final boolean IsManual_IPA_SSCC18)
	{
		set_Value (COLUMNNAME_IsManual_IPA_SSCC18, IsManual_IPA_SSCC18);
	}

	@Override
	public boolean isManual_IPA_SSCC18() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManual_IPA_SSCC18);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_HU_PackagingCode_ID (final int M_HU_PackagingCode_ID)
	{
		if (M_HU_PackagingCode_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackagingCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackagingCode_ID, M_HU_PackagingCode_ID);
	}

	@Override
	public int getM_HU_PackagingCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PackagingCode_ID);
	}

	@Override
	public void setM_HU_PackagingCode_Text (final @Nullable java.lang.String M_HU_PackagingCode_Text)
	{
		throw new IllegalArgumentException ("M_HU_PackagingCode_Text is virtual column");	}

	@Override
	public java.lang.String getM_HU_PackagingCode_Text() 
	{
		return get_ValueAsString(COLUMNNAME_M_HU_PackagingCode_Text);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		throw new IllegalArgumentException ("M_InOut_ID is virtual column");	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
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