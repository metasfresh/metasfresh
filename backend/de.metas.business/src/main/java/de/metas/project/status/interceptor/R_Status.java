/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.project.status.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_R_Status;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Callout(I_R_Status.class)
@Component
public class R_Status
{
	private static final AdMessageKey MSG_INVALID_HEXADECIMAL_COLOR_CODE_PATTERN = AdMessageKey.of("de.metas.project.status.interceptor.InvalidHexadecimalColorCodePattern");

	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_R_Status.COLUMNNAME_CalendarColor)
	public void validateHexadecimalCalendarColor(@NonNull final I_R_Status statusRecord)
	{
		if (Check.isBlank(statusRecord.getCalendarColor()))
		{
			return;
		}

		final Pattern hexDecCodePattern = Pattern.compile("^#[a-fA-F0-9]{3,6}$");

		if (!hexDecCodePattern.matcher(statusRecord.getCalendarColor()).matches())
		{
			throw new AdempiereException(MSG_INVALID_HEXADECIMAL_COLOR_CODE_PATTERN, I_R_Status.COLUMNNAME_CalendarColor);
		}
	}
}
