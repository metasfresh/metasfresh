package de.metas.datev.modelvalidator;

/*
 * #%L
 * de.metas.datev
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.calendar.Period;
import de.metas.calendar.PeriodRepo;
import de.metas.datev.DATEVExportConfig;
import de.metas.datev.DATEVExportConfigRepository;
import de.metas.datev.model.I_DATEV_Export;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_DATEV_Export.class)
@Callout(I_DATEV_Export.class)
@RequiredArgsConstructor
public class DATEV_Export
{
	@NonNull private final DATEVExportConfigRepository exportConfigRepo;
	@NonNull private final PeriodRepo periodRepo;

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setDatevExportConfig(final I_DATEV_Export datevExport)
	{
		final DATEVExportConfig exportConfig = exportConfigRepo.getByOrgId(OrgId.ofRepoId(datevExport.getAD_Org_ID()));
		if (exportConfig != null)
		{
			datevExport.setDATEV_Export_Config_ID(exportConfig.getId());
			datevExport.setAdvisorNumber(exportConfig.getAdvisorNumber());
			datevExport.setClientNumber(exportConfig.getClientNumber());
			datevExport.setChartOfAccounts(exportConfig.getChartOfAccounts());
			datevExport.setChartOfAccountsNumberLength(exportConfig.getChartOfAccountsNumberLength());
			datevExport.setOrigin(exportConfig.getOrigin());
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = I_DATEV_Export.COLUMNNAME_C_Period_ID)
	@CalloutMethod(columnNames = { I_DATEV_Export.COLUMNNAME_C_Period_ID})
	public void setDateAcctFromAndDateAcctTo(final I_DATEV_Export datevExport)
	{
		if (datevExport.getC_Period_ID() > 0)
		{
			final Period period = periodRepo.getByRepoId(datevExport.getC_Period_ID());
			datevExport.setDateAcctFrom(TimeUtil.asTimestamp(period.getStartDate()));
			datevExport.setDateAcctTo(TimeUtil.asTimestamp(period.getEndDate()));
		}
		else
		{
			datevExport.setDateAcctFrom(null);
			datevExport.setDateAcctTo(null);
		}
	}
}
