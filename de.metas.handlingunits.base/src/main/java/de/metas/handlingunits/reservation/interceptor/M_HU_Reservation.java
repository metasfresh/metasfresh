package de.metas.handlingunits.reservation.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.model.I_M_HU_Reservation;
import lombok.NonNull;

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

@Component("de.metas.handlingunits.reservation.interceptor.M_HU_Reservation")
@Interceptor(I_M_HU_Reservation.class)
public class M_HU_Reservation
{
	@ModelChange( //
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_M_HU_Reservation.COLUMNNAME_C_OrderLineSO_ID)
	public void updateC_BPartner_ID(@NonNull final I_M_HU_Reservation huReservationRecord)
	{
		final I_C_OrderLine orderLineRecord = huReservationRecord.getC_OrderLineSO();
		huReservationRecord.setC_BPartner_Customer_ID(orderLineRecord.getC_BPartner_ID());
	}
}
