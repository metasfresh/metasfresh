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
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_OLCand_AlbertaOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_OLCand_AlbertaOrder extends org.compiere.model.PO implements I_C_OLCand_AlbertaOrder, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -969070952L;

    /** Standard Constructor */
    public X_C_OLCand_AlbertaOrder (final Properties ctx, final int C_OLCand_AlbertaOrder_ID, @Nullable final String trxName)
    {
      super (ctx, C_OLCand_AlbertaOrder_ID, trxName);
    }

    /** Load Constructor */
    public X_C_OLCand_AlbertaOrder (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAnnotation (final @Nullable java.lang.String Annotation)
	{
		set_Value (COLUMNNAME_Annotation, Annotation);
	}

	@Override
	public java.lang.String getAnnotation() 
	{
		return get_ValueAsString(COLUMNNAME_Annotation);
	}

	@Override
	public void setC_Doctor_BPartner_ID (final int C_Doctor_BPartner_ID)
	{
		if (C_Doctor_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_Doctor_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_Doctor_BPartner_ID, C_Doctor_BPartner_ID);
	}

	@Override
	public int getC_Doctor_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Doctor_BPartner_ID);
	}

	@Override
	public void setC_OLCand_AlbertaOrder_ID (final int C_OLCand_AlbertaOrder_ID)
	{
		if (C_OLCand_AlbertaOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_AlbertaOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_AlbertaOrder_ID, C_OLCand_AlbertaOrder_ID);
	}

	@Override
	public int getC_OLCand_AlbertaOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_AlbertaOrder_ID);
	}

	@Override
	public void setC_OLCand_ID (final int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1) 
			set_Value (COLUMNNAME_C_OLCand_ID, null);
		else 
			set_Value (COLUMNNAME_C_OLCand_ID, C_OLCand_ID);
	}

	@Override
	public int getC_OLCand_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_ID);
	}

	@Override
	public void setC_Pharmacy_BPartner_ID (final int C_Pharmacy_BPartner_ID)
	{
		if (C_Pharmacy_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_Pharmacy_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_Pharmacy_BPartner_ID, C_Pharmacy_BPartner_ID);
	}

	@Override
	public int getC_Pharmacy_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Pharmacy_BPartner_ID);
	}

	@Override
	public void setCreationDate (final @Nullable java.sql.Timestamp CreationDate)
	{
		set_Value (COLUMNNAME_CreationDate, CreationDate);
	}

	@Override
	public java.sql.Timestamp getCreationDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_CreationDate);
	}

	@Override
	public void setDayOfDelivery (final @Nullable BigDecimal DayOfDelivery)
	{
		set_Value (COLUMNNAME_DayOfDelivery, DayOfDelivery);
	}

	@Override
	public BigDecimal getDayOfDelivery() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DayOfDelivery);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDurationAmount (final @Nullable BigDecimal DurationAmount)
	{
		set_Value (COLUMNNAME_DurationAmount, DurationAmount);
	}

	@Override
	public BigDecimal getDurationAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DurationAmount);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setExternallyUpdatedAt (final @Nullable java.sql.Timestamp ExternallyUpdatedAt)
	{
		set_Value (COLUMNNAME_ExternallyUpdatedAt, ExternallyUpdatedAt);
	}

	@Override
	public java.sql.Timestamp getExternallyUpdatedAt() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ExternallyUpdatedAt);
	}

	@Override
	public void setIsArchived (final boolean IsArchived)
	{
		set_Value (COLUMNNAME_IsArchived, IsArchived);
	}

	@Override
	public boolean isArchived() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsArchived);
	}

	@Override
	public void setIsInitialCare (final boolean IsInitialCare)
	{
		set_Value (COLUMNNAME_IsInitialCare, IsInitialCare);
	}

	@Override
	public boolean isInitialCare() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInitialCare);
	}

	@Override
	public void setIsPrivateSale (final boolean IsPrivateSale)
	{
		set_Value (COLUMNNAME_IsPrivateSale, IsPrivateSale);
	}

	@Override
	public boolean isPrivateSale() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrivateSale);
	}

	@Override
	public void setIsRentalEquipment (final boolean IsRentalEquipment)
	{
		set_Value (COLUMNNAME_IsRentalEquipment, IsRentalEquipment);
	}

	@Override
	public boolean isRentalEquipment() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRentalEquipment);
	}

	@Override
	public void setIsSeriesOrder (final boolean IsSeriesOrder)
	{
		set_Value (COLUMNNAME_IsSeriesOrder, IsSeriesOrder);
	}

	@Override
	public boolean isSeriesOrder() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSeriesOrder);
	}

	@Override
	public void setNextDelivery (final @Nullable java.sql.Timestamp NextDelivery)
	{
		set_Value (COLUMNNAME_NextDelivery, NextDelivery);
	}

	@Override
	public java.sql.Timestamp getNextDelivery() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_NextDelivery);
	}

	@Override
	public void setRootId (final @Nullable java.lang.String RootId)
	{
		set_Value (COLUMNNAME_RootId, RootId);
	}

	@Override
	public java.lang.String getRootId() 
	{
		return get_ValueAsString(COLUMNNAME_RootId);
	}

	@Override
	public void setSalesLineId (final @Nullable java.lang.String SalesLineId)
	{
		set_Value (COLUMNNAME_SalesLineId, SalesLineId);
	}

	@Override
	public java.lang.String getSalesLineId() 
	{
		return get_ValueAsString(COLUMNNAME_SalesLineId);
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
	public void setTimePeriod (final @Nullable BigDecimal TimePeriod)
	{
		set_Value (COLUMNNAME_TimePeriod, TimePeriod);
	}

	@Override
	public BigDecimal getTimePeriod() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TimePeriod);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setUnit (final @Nullable java.lang.String Unit)
	{
		set_Value (COLUMNNAME_Unit, Unit);
	}

	@Override
	public java.lang.String getUnit() 
	{
		return get_ValueAsString(COLUMNNAME_Unit);
	}
}