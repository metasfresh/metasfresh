/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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

	private static final long serialVersionUID = -344053074L;

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
	public I_EDI_Desadv getEDI_Desadv()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_ID, I_EDI_Desadv.class);
	}

	@Override
	public void setEDI_Desadv(final I_EDI_Desadv EDI_Desadv)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_ID, I_EDI_Desadv.class, EDI_Desadv);
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
	public I_EDI_Desadv_Pack getEDI_Desadv_Parent_Pack()
	{
		return get_ValueAsPO(COLUMNNAME_EDI_Desadv_Parent_Pack_ID, I_EDI_Desadv_Pack.class);
	}

	@Override
	public void setEDI_Desadv_Parent_Pack(final I_EDI_Desadv_Pack EDI_Desadv_Parent_Pack)
	{
		set_ValueFromPO(COLUMNNAME_EDI_Desadv_Parent_Pack_ID, I_EDI_Desadv_Pack.class, EDI_Desadv_Parent_Pack);
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
	public void setGTIN_LU_PackingMaterial (final @Nullable String GTIN_LU_PackingMaterial)
	{
		set_Value (COLUMNNAME_GTIN_LU_PackingMaterial, GTIN_LU_PackingMaterial);
	}

	@Override
	public String getGTIN_LU_PackingMaterial()
	{
		return get_ValueAsString(COLUMNNAME_GTIN_LU_PackingMaterial);
	}

	@Override
	public void setIPA_SSCC18 (final String IPA_SSCC18)
	{
		set_Value (COLUMNNAME_IPA_SSCC18, IPA_SSCC18);
	}

	@Override
	public String getIPA_SSCC18()
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
	public void setM_HU_PackagingCode_LU_ID (final int M_HU_PackagingCode_LU_ID)
	{
		if (M_HU_PackagingCode_LU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PackagingCode_LU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PackagingCode_LU_ID, M_HU_PackagingCode_LU_ID);
	}

	@Override
	public int getM_HU_PackagingCode_LU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_PackagingCode_LU_ID);
	}

	@Override
	public void setM_HU_PackagingCode_LU_Text (final @Nullable String M_HU_PackagingCode_LU_Text)
	{
		throw new IllegalArgumentException ("M_HU_PackagingCode_LU_Text is virtual column");	}

	@Override
	public String getM_HU_PackagingCode_LU_Text()
	{
		return get_ValueAsString(COLUMNNAME_M_HU_PackagingCode_LU_Text);
	}
}