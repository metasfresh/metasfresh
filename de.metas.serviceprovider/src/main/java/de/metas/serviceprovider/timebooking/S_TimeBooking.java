/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.serviceprovider.timebooking;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.serviceprovider.external.reference.ExternalReferenceRepository;
import de.metas.serviceprovider.external.reference.ExternalReferenceType;
import de.metas.serviceprovider.model.I_S_TimeBooking;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Interceptor(I_S_TimeBooking.class)
@Callout(I_S_TimeBooking.class)
@Component
public class S_TimeBooking
{
	private final Pattern hmmPattern = Pattern.compile("^[0-9]+:[0-5][0-9]$");
	private final String INCORRECT_FORMAT_MSG_KEY = "de.metas.serviceprovider.incorrectHmmFormat";

	private final ExternalReferenceRepository externalReferenceRepository;
	private final IMsgBL msgBL;

	public S_TimeBooking(final ExternalReferenceRepository externalReferenceRepository, final IMsgBL msgBL)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.msgBL = msgBL;
	}

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void afterDelete(@NonNull final I_S_TimeBooking record)
	{
		externalReferenceRepository.deleteByRecordIdAndType(record.getS_TimeBooking_ID(), ExternalReferenceType.TIME_BOOKING_ID);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {I_S_TimeBooking.COLUMNNAME_HoursAndMinutes})
	public void updateSecondsBasedOnHHmm(@NonNull final I_S_TimeBooking record)
	{
		record.setBookedSeconds(BigDecimal.valueOf(TimeUtil.hmmToSeconds(record.getHoursAndMinutes())));
	}

	@CalloutMethod(columnNames = { I_S_TimeBooking.COLUMNNAME_HoursAndMinutes })
	public void validateHmmInput(@NonNull final I_S_TimeBooking record)
	{
		final String hoursAndMinutes = record.getHoursAndMinutes();

		if (Check.isBlank(hoursAndMinutes))
		{
			return;
		}

		final Matcher matcher = hmmPattern.matcher(hoursAndMinutes);

		if (!matcher.matches())
		{

			throw new AdempiereException(msgBL.getTranslatableMsgText(AdMessageKey.of(INCORRECT_FORMAT_MSG_KEY)))
					.markAsUserValidationError();
		}
	}
}
