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

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for Alberta_PrescriptionRequest
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Alberta_PrescriptionRequest extends org.compiere.model.PO implements I_Alberta_PrescriptionRequest, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1578858701L;

    /** Standard Constructor */
    public X_Alberta_PrescriptionRequest (final Properties ctx, final int Alberta_PrescriptionRequest_ID, @Nullable final String trxName)
    {
      super (ctx, Alberta_PrescriptionRequest_ID, trxName);
    }

    /** Load Constructor */
    public X_Alberta_PrescriptionRequest (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAccountingMonths (final @Nullable java.lang.String AccountingMonths)
	{
		set_Value (COLUMNNAME_AccountingMonths, AccountingMonths);
	}

	@Override
	public java.lang.String getAccountingMonths() 
	{
		return get_ValueAsString(COLUMNNAME_AccountingMonths);
	}

	@Override
	public void setAlberta_PrescriptionRequest_ID (final int Alberta_PrescriptionRequest_ID)
	{
		if (Alberta_PrescriptionRequest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Alberta_PrescriptionRequest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Alberta_PrescriptionRequest_ID, Alberta_PrescriptionRequest_ID);
	}

	@Override
	public int getAlberta_PrescriptionRequest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Alberta_PrescriptionRequest_ID);
	}

	@Override
	public void setArchived (final boolean Archived)
	{
		set_Value (COLUMNNAME_Archived, Archived);
	}

	@Override
	public boolean isArchived() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Archived);
	}

	@Override
	public void setC_BPartner_Doctor_ID (final int C_BPartner_Doctor_ID)
	{
		if (C_BPartner_Doctor_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Doctor_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Doctor_ID, C_BPartner_Doctor_ID);
	}

	@Override
	public int getC_BPartner_Doctor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Doctor_ID);
	}

	@Override
	public void setC_BPartner_Patient_ID (final int C_BPartner_Patient_ID)
	{
		if (C_BPartner_Patient_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Patient_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Patient_ID, C_BPartner_Patient_ID);
	}

	@Override
	public int getC_BPartner_Patient_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Patient_ID);
	}

	@Override
	public void setC_BPartner_Pharmacy_ID (final int C_BPartner_Pharmacy_ID)
	{
		if (C_BPartner_Pharmacy_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Pharmacy_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Pharmacy_ID, C_BPartner_Pharmacy_ID);
	}

	@Override
	public int getC_BPartner_Pharmacy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Pharmacy_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setEndDate (final @Nullable java.sql.Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	@Override
	public java.sql.Timestamp getEndDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EndDate);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setExternalOrderId (final @Nullable java.lang.String ExternalOrderId)
	{
		set_Value (COLUMNNAME_ExternalOrderId, ExternalOrderId);
	}

	@Override
	public java.lang.String getExternalOrderId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalOrderId);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public void setPatientBirthday (final @Nullable java.sql.Timestamp PatientBirthday)
	{
		set_Value (COLUMNNAME_PatientBirthday, PatientBirthday);
	}

	@Override
	public java.sql.Timestamp getPatientBirthday() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PatientBirthday);
	}

	@Override
	public void setPrescriptionRequestCreatedAt (final @Nullable java.sql.Timestamp PrescriptionRequestCreatedAt)
	{
		set_Value (COLUMNNAME_PrescriptionRequestCreatedAt, PrescriptionRequestCreatedAt);
	}

	@Override
	public java.sql.Timestamp getPrescriptionRequestCreatedAt() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PrescriptionRequestCreatedAt);
	}

	@Override
	public void setPrescriptionRequestCreatedBy (final int PrescriptionRequestCreatedBy)
	{
		set_Value (COLUMNNAME_PrescriptionRequestCreatedBy, PrescriptionRequestCreatedBy);
	}

	@Override
	public int getPrescriptionRequestCreatedBy() 
	{
		return get_ValueAsInt(COLUMNNAME_PrescriptionRequestCreatedBy);
	}

	@Override
	public void setPrescriptionRequestTimestamp (final @Nullable java.sql.Timestamp PrescriptionRequestTimestamp)
	{
		set_Value (COLUMNNAME_PrescriptionRequestTimestamp, PrescriptionRequestTimestamp);
	}

	@Override
	public java.sql.Timestamp getPrescriptionRequestTimestamp() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PrescriptionRequestTimestamp);
	}

	@Override
	public void setPrescriptionRequestUpdateAt (final @Nullable java.sql.Timestamp PrescriptionRequestUpdateAt)
	{
		set_Value (COLUMNNAME_PrescriptionRequestUpdateAt, PrescriptionRequestUpdateAt);
	}

	@Override
	public java.sql.Timestamp getPrescriptionRequestUpdateAt() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_PrescriptionRequestUpdateAt);
	}

	/** 
	 * PrescriptionType AD_Reference_ID=541296
	 * Reference name: Rezepttyp
	 */
	public static final int PRESCRIPTIONTYPE_AD_Reference_ID=541296;
	/** Arzneimittel = 0 */
	public static final String PRESCRIPTIONTYPE_Arzneimittel = "0";
	/** Verbandstoffe = 1 */
	public static final String PRESCRIPTIONTYPE_Verbandstoffe = "1";
	/** BtM-Rezept = 2 */
	public static final String PRESCRIPTIONTYPE_BtM_Rezept = "2";
	/** Pflegehilfsmittel = 3 */
	public static final String PRESCRIPTIONTYPE_Pflegehilfsmittel = "3";
	/** Hilfsmittel zum Verbrauch bestimmt = 4 */
	public static final String PRESCRIPTIONTYPE_HilfsmittelZumVerbrauchBestimmt = "4";
	/** Hilfsmittel zum Gebrauch bestimmt = 5 */
	public static final String PRESCRIPTIONTYPE_HilfsmittelZumGebrauchBestimmt = "5";
	@Override
	public void setPrescriptionType (final @Nullable java.lang.String PrescriptionType)
	{
		set_Value (COLUMNNAME_PrescriptionType, PrescriptionType);
	}

	@Override
	public java.lang.String getPrescriptionType() 
	{
		return get_ValueAsString(COLUMNNAME_PrescriptionType);
	}

	@Override
	public void setStartDate (final @Nullable java.sql.Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	@Override
	public java.sql.Timestamp getStartDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_StartDate);
	}

	@Override
	public void setTherapyTypeIds (final @Nullable java.lang.String TherapyTypeIds)
	{
		set_Value (COLUMNNAME_TherapyTypeIds, TherapyTypeIds);
	}

	@Override
	public java.lang.String getTherapyTypeIds() 
	{
		return get_ValueAsString(COLUMNNAME_TherapyTypeIds);
	}
}