/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.dashboard.callout;

import de.metas.ui.web.base.model.I_WEBUI_DashboardItem;
import de.metas.ui.web.kpi.descriptor.KPI;
import de.metas.ui.web.kpi.descriptor.KPIId;
import de.metas.ui.web.kpi.descriptor.KPIRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Callout(I_WEBUI_DashboardItem.class)
@Component
public class WEBUI_DashboardItem
{
	private final KPIRepository kpiRepository;

	public WEBUI_DashboardItem(final KPIRepository kpiRepository) {this.kpiRepository = kpiRepository;}

	@PostConstruct
	public void postConstruct()
	{
		final IProgramaticCalloutProvider programmaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programmaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_WEBUI_DashboardItem.COLUMNNAME_WEBUI_KPI_ID)
	public void onKPIChanged(@NonNull final I_WEBUI_DashboardItem item)
	{
		final KPIId kpiId = KPIId.ofRepoIdOrNull(item.getWEBUI_KPI_ID());
		if (kpiId == null)
		{
			return;
		}

		final KPI kpi = kpiRepository.getKPI(kpiId);
		item.setName(kpi.getCaption().getDefaultValue());
	}

}
