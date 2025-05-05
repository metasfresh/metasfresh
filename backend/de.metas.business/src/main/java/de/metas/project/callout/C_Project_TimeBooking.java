/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.project.callout;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.HmmUtils;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project_TimeBooking;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Interceptor(I_C_Project_TimeBooking.class)
@Callout(I_C_Project_TimeBooking.class)
public class C_Project_TimeBooking
{
	private final static String INCORRECT_FORMAT_MSG_KEY = "de.metas.project.incorrectHmmFormat";

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@CalloutMethod(columnNames = { I_C_Project_TimeBooking.COLUMNNAME_HoursAndMinutes })
	public void validateHmmInput(@NonNull final I_C_Project_TimeBooking record)
	{
		final String hoursAndMinutes = record.getHoursAndMinutes();

		if (Check.isBlank(hoursAndMinutes))
		{
			return;
		}

		if (!HmmUtils.matches(hoursAndMinutes))
		{

			throw new AdempiereException(msgBL.getTranslatableMsgText(AdMessageKey.of(INCORRECT_FORMAT_MSG_KEY)))
					.markAsUserValidationError();
		}
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_Project_TimeBooking.COLUMNNAME_HoursAndMinutes })
	public void updateBookedSeconds(@NonNull final I_C_Project_TimeBooking record)
	{
		record.setBookedSeconds(BigDecimal.valueOf(HmmUtils.hmmToSeconds(record.getHoursAndMinutes())));
	}
}
