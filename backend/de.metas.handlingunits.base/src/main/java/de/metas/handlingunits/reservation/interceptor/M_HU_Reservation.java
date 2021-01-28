package de.metas.handlingunits.reservation.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelChangeUtil;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
@Interceptor(I_M_HU_Reservation.class)
public class M_HU_Reservation
{
	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_M_HU_Reservation.COLUMNNAME_C_OrderLineSO_ID)
	public void updateC_BPartner_ID(@NonNull final I_M_HU_Reservation huReservationRecord)
	{
		final I_C_OrderLine orderLineRecord = huReservationRecord.getC_OrderLineSO();
		if (orderLineRecord != null)
		{
			huReservationRecord.setC_BPartner_Customer_ID(orderLineRecord.getC_BPartner_ID());
		}
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = I_M_HU_Reservation.COLUMNNAME_IsActive)
	public void setVhuReservedFlag(
			@NonNull final I_M_HU_Reservation huReservationRecord,
			@NonNull final ModelChangeType type)
	{
		final boolean reservationIsHere = type.isNew() || ModelChangeUtil.isJustActivated(huReservationRecord);
		if (reservationIsHere)
		{
			final boolean isReserved = true;
			updateVhuIsReservedFlag(huReservationRecord, isReserved);
		}
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_BEFORE_DELETE }, //
			ifColumnsChanged = I_M_HU_Reservation.COLUMNNAME_IsActive)
	public void unsetVhuReservedFlag(
			@NonNull final I_M_HU_Reservation huReservationRecord,
			@NonNull final ModelChangeType type)
	{
		final boolean reservationIsGone = type.isDelete() || ModelChangeUtil.isJustDeactivated(huReservationRecord);
		if (reservationIsGone)
		{
			final boolean isReserved = false;
			updateVhuIsReservedFlag(huReservationRecord, isReserved);
		}
	}

	private void updateVhuIsReservedFlag(
			@NonNull final I_M_HU_Reservation huReservationRecord,
			final boolean isReserved)
	{
		final HuId vhuId = HuId.ofRepoId(huReservationRecord.getVHU_ID());
		Services.get(IHandlingUnitsDAO.class).setReservedByHUIds(ImmutableSet.of(vhuId), isReserved);
	}
}
