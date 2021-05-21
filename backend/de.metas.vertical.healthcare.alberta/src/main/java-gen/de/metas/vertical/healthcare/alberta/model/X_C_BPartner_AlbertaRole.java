/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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
package de.metas.vertical.healthcare.alberta.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_AlbertaRole
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_AlbertaRole extends org.compiere.model.PO implements I_C_BPartner_AlbertaRole, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -620940632L;

    /** Standard Constructor */
    public X_C_BPartner_AlbertaRole (final Properties ctx, final int C_BPartner_AlbertaRole_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_AlbertaRole_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_AlbertaRole (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * AlbertaRole AD_Reference_ID=541322
	 * Reference name: AlbertaRole
	 */
	public static final int ALBERTAROLE_AD_Reference_ID=541322;
	/** Caregiver = CG */
	public static final String ALBERTAROLE_Caregiver = "CG";
	/** Caretaker = CT */
	public static final String ALBERTAROLE_Caretaker = "CT";
	/** General Practitioner = GP */
	public static final String ALBERTAROLE_GeneralPractitioner = "GP";
	/** Health Insurance = HI */
	public static final String ALBERTAROLE_HealthInsurance = "HI";
	/** Hostpital = HO */
	public static final String ALBERTAROLE_Hostpital = "HO";
	/** Main Producer = MP */
	public static final String ALBERTAROLE_MainProducer = "MP";
	/** Nursing Home = NH */
	public static final String ALBERTAROLE_NursingHome = "NH";
	/** Nursing Service = NS */
	public static final String ALBERTAROLE_NursingService = "NS";
	/** Payer = PA */
	public static final String ALBERTAROLE_Payer = "PA";
	/** Doctor = PD */
	public static final String ALBERTAROLE_Doctor = "PD";
	/** Pharmacy = PH */
	public static final String ALBERTAROLE_Pharmacy = "PH";
	/** Preferred Pharmacy = PP */
	public static final String ALBERTAROLE_PreferredPharmacy = "PP";
	/** Pacient = PT */
	public static final String ALBERTAROLE_Pacient = "PT";
	@Override
	public void setAlbertaRole (final @Nullable java.lang.String AlbertaRole)
	{
		set_Value (COLUMNNAME_AlbertaRole, AlbertaRole);
	}

	@Override
	public java.lang.String getAlbertaRole() 
	{
		return get_ValueAsString(COLUMNNAME_AlbertaRole);
	}

	@Override
	public void setC_BPartner_AlbertaRole_ID (final int C_BPartner_AlbertaRole_ID)
	{
		if (C_BPartner_AlbertaRole_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_AlbertaRole_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_AlbertaRole_ID, C_BPartner_AlbertaRole_ID);
	}

	@Override
	public int getC_BPartner_AlbertaRole_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_AlbertaRole_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}
}