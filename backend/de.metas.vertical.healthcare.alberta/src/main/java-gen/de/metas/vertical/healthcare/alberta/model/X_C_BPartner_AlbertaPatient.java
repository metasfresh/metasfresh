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

/** Generated Model for C_BPartner_AlbertaPatient
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_AlbertaPatient extends org.compiere.model.PO implements I_C_BPartner_AlbertaPatient, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1264888881L;

    /** Standard Constructor */
    public X_C_BPartner_AlbertaPatient (final Properties ctx, final int C_BPartner_AlbertaPatient_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_AlbertaPatient_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_AlbertaPatient (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_User_CreatedBy_ID (final int AD_User_CreatedBy_ID)
	{
		if (AD_User_CreatedBy_ID < 1) 
			set_Value (COLUMNNAME_AD_User_CreatedBy_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_CreatedBy_ID, AD_User_CreatedBy_ID);
	}

	@Override
	public int getAD_User_CreatedBy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_CreatedBy_ID);
	}

	@Override
	public void setAD_User_FieldNurse_ID (final int AD_User_FieldNurse_ID)
	{
		if (AD_User_FieldNurse_ID < 1) 
			set_Value (COLUMNNAME_AD_User_FieldNurse_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_FieldNurse_ID, AD_User_FieldNurse_ID);
	}

	@Override
	public int getAD_User_FieldNurse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_FieldNurse_ID);
	}

	@Override
	public void setAD_User_UpdatedBy_ID (final int AD_User_UpdatedBy_ID)
	{
		if (AD_User_UpdatedBy_ID < 1) 
			set_Value (COLUMNNAME_AD_User_UpdatedBy_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_UpdatedBy_ID, AD_User_UpdatedBy_ID);
	}

	@Override
	public int getAD_User_UpdatedBy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_UpdatedBy_ID);
	}

	@Override
	public void setC_BPartner_AlbertaPatient_ID (final int C_BPartner_AlbertaPatient_ID)
	{
		if (C_BPartner_AlbertaPatient_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_AlbertaPatient_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_AlbertaPatient_ID, C_BPartner_AlbertaPatient_ID);
	}

	@Override
	public int getC_BPartner_AlbertaPatient_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_AlbertaPatient_ID);
	}

	@Override
	public void setC_BPartner_Hospital_ID (final int C_BPartner_Hospital_ID)
	{
		if (C_BPartner_Hospital_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Hospital_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Hospital_ID, C_BPartner_Hospital_ID);
	}

	@Override
	public int getC_BPartner_Hospital_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Hospital_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Payer_ID (final int C_BPartner_Payer_ID)
	{
		if (C_BPartner_Payer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Payer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Payer_ID, C_BPartner_Payer_ID);
	}

	@Override
	public int getC_BPartner_Payer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Payer_ID);
	}

	@Override
	public void setCopaymentFrom (final @Nullable java.sql.Timestamp CopaymentFrom)
	{
		set_Value (COLUMNNAME_CopaymentFrom, CopaymentFrom);
	}

	@Override
	public java.sql.Timestamp getCopaymentFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CopaymentFrom);
	}

	@Override
	public void setCopaymentTo (final @Nullable java.sql.Timestamp CopaymentTo)
	{
		set_Value (COLUMNNAME_CopaymentTo, CopaymentTo);
	}

	@Override
	public java.sql.Timestamp getCopaymentTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CopaymentTo);
	}

	@Override
	public void setCreatedAt (final @Nullable java.sql.Timestamp CreatedAt)
	{
		set_ValueNoCheck (COLUMNNAME_CreatedAt, CreatedAt);
	}

	@Override
	public java.sql.Timestamp getCreatedAt() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CreatedAt);
	}

	@Override
	public void setDeactivationComment (final @Nullable java.lang.String DeactivationComment)
	{
		set_Value (COLUMNNAME_DeactivationComment, DeactivationComment);
	}

	@Override
	public java.lang.String getDeactivationComment() 
	{
		return get_ValueAsString(COLUMNNAME_DeactivationComment);
	}

	@Override
	public void setDeactivationDate (final @Nullable java.sql.Timestamp DeactivationDate)
	{
		set_Value (COLUMNNAME_DeactivationDate, DeactivationDate);
	}

	@Override
	public java.sql.Timestamp getDeactivationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeactivationDate);
	}

	/** 
	 * DeactivationReason AD_Reference_ID=541320
	 * Reference name: DeactivationReason_List
	 */
	public static final int DEACTIVATIONREASON_AD_Reference_ID=541320;
	/** Unbekannt = 0 */
	public static final String DEACTIVATIONREASON_Unbekannt = "0";
	/** Patient verstorben = 1 */
	public static final String DEACTIVATIONREASON_PatientVerstorben = "1";
	/** Therapieende (alle Therapien) = 2 */
	public static final String DEACTIVATIONREASON_TherapieendeAlleTherapien = "2";
	/** Leistungserbringerwechsel = 3 */
	public static final String DEACTIVATIONREASON_Leistungserbringerwechsel = "3";
	/** Sonstiges = 4 */
	public static final String DEACTIVATIONREASON_Sonstiges = "4";
	@Override
	public void setDeactivationReason (final @Nullable java.lang.String DeactivationReason)
	{
		set_Value (COLUMNNAME_DeactivationReason, DeactivationReason);
	}

	@Override
	public java.lang.String getDeactivationReason() 
	{
		return get_ValueAsString(COLUMNNAME_DeactivationReason);
	}

	@Override
	public void setDischargeDate (final @Nullable java.sql.Timestamp DischargeDate)
	{
		set_Value (COLUMNNAME_DischargeDate, DischargeDate);
	}

	@Override
	public java.sql.Timestamp getDischargeDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DischargeDate);
	}

	@Override
	public void setIsIVTherapy (final boolean IsIVTherapy)
	{
		set_Value (COLUMNNAME_IsIVTherapy, IsIVTherapy);
	}

	@Override
	public boolean isIVTherapy() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsIVTherapy);
	}

	@Override
	public void setIsTransferPatient (final boolean IsTransferPatient)
	{
		set_Value (COLUMNNAME_IsTransferPatient, IsTransferPatient);
	}

	@Override
	public boolean isTransferPatient() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTransferPatient);
	}

	@Override
	public void setNumberOfInsured (final @Nullable java.lang.String NumberOfInsured)
	{
		set_Value (COLUMNNAME_NumberOfInsured, NumberOfInsured);
	}

	@Override
	public java.lang.String getNumberOfInsured() 
	{
		return get_ValueAsString(COLUMNNAME_NumberOfInsured);
	}

	/** 
	 * PayerType AD_Reference_ID=541319
	 * Reference name: PayerType_list
	 */
	public static final int PAYERTYPE_AD_Reference_ID=541319;
	/** Unbekannt = 0 */
	public static final String PAYERTYPE_Unbekannt = "0";
	/** Gesetzlich = 1 */
	public static final String PAYERTYPE_Gesetzlich = "1";
	/** Privat = 2 */
	public static final String PAYERTYPE_Privat = "2";
	/** Berufsgenossenschaft = 3 */
	public static final String PAYERTYPE_Berufsgenossenschaft = "3";
	/** Selbstzahler = 4 */
	public static final String PAYERTYPE_Selbstzahler = "4";
	/** Andere = 5 */
	public static final String PAYERTYPE_Andere = "5";
	@Override
	public void setPayerType (final @Nullable java.lang.String PayerType)
	{
		set_Value (COLUMNNAME_PayerType, PayerType);
	}

	@Override
	public java.lang.String getPayerType() 
	{
		return get_ValueAsString(COLUMNNAME_PayerType);
	}

	@Override
	public void setUpdatedAt (final @Nullable java.sql.Timestamp UpdatedAt)
	{
		set_Value (COLUMNNAME_UpdatedAt, UpdatedAt);
	}

	@Override
	public java.sql.Timestamp getUpdatedAt() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_UpdatedAt);
	}
}