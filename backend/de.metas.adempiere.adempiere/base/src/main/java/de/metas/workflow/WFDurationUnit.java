/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.workflow;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Workflow;

import java.time.Duration;

public enum WFDurationUnit implements ReferenceListAwareEnum
{
	Year(X_AD_Workflow.DURATIONUNIT_Year, Duration.ofDays(365)),
	Month(X_AD_Workflow.DURATIONUNIT_Month, Duration.ofDays(30)),
	Day(X_AD_Workflow.DURATIONUNIT_Day, Duration.ofDays(1)),
	Hour(X_AD_Workflow.DURATIONUNIT_Hour, Duration.ofHours(1)),
	Minute(X_AD_Workflow.DURATIONUNIT_Minute, Duration.ofMinutes(1)),
	Second(X_AD_Workflow.DURATIONUNIT_Second, Duration.ofSeconds(1));

	private static final ReferenceListAwareEnums.ValuesIndex<WFDurationUnit> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;
	@Getter
	private final Duration duration;

	WFDurationUnit(@NonNull final String code, @NonNull final Duration duration)
	{
		this.code = code;
		this.duration = duration;
	}

	public static WFDurationUnit ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}
}
