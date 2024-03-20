package de.metas.ui.web.dashboard;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.kpi.KPITimeRangeDefaults;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.kpi.descriptor.KPIId;
import de.metas.ui.web.kpi.descriptor.KPISupplier;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Value
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(exclude = "kpiSupplier", doNotUseGetters = true)
public class UserDashboardItem
{
	@NonNull UserDashboardItemId id;
	@NonNull ITranslatableString caption;
	String url;
	int seqNo;
	DashboardWidgetType widgetType;
	@Nullable KPISupplier kpiSupplier;
	@NonNull KPITimeRangeDefaults timeRangeDefaults;

	@Builder
	private UserDashboardItem(
			@NonNull final UserDashboardItemId id,
			@NonNull final ITranslatableString caption,
			final String url,
			final int seqNo,
			final DashboardWidgetType widgetType,
			@Nullable final KPISupplier kpiSupplier,
			@Nullable final KPITimeRangeDefaults timeRangeDefaults)
	{
		this.id = id;
		this.caption = caption;
		this.url = url;
		this.seqNo = seqNo;
		this.widgetType = widgetType;
		this.kpiSupplier = kpiSupplier;
		this.timeRangeDefaults = timeRangeDefaults != null ? timeRangeDefaults : KPITimeRangeDefaults.DEFAULT;
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public KPIId getKPIId()
	{
		if (kpiSupplier == null)
		{
			throw new EntityNotFoundException("No KPI defined for " + this);
		}
		else
		{
			return kpiSupplier.getKpiId();
		}
	}

	public KPI getKPI()
	{
		final KPI kpi = kpiSupplier == null ? null : kpiSupplier.get();
		if (kpi == null)
		{
			throw new EntityNotFoundException("No KPI defined for " + this);
		}
		return kpi;
	}

	public KPITimeRangeDefaults getTimeRangeDefaults()
	{
		final KPI kpi = getKPI();
		return timeRangeDefaults.compose(kpi.getTimeRangeDefaults());
	}
}
