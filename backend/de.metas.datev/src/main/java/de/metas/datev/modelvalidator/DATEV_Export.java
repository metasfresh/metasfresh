package de.metas.datev.modelvalidator;

/*
 * #%L
 * de.metas.dunning
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

import de.metas.datev.DATEVExportConfig;
import de.metas.datev.DATEVExportConfigRepository;
import de.metas.datev.model.I_DATEV_Export;
import de.metas.organization.OrgId;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;

@Validator(I_DATEV_Export.class)
public class DATEV_Export
{
	private final DATEVExportConfigRepository exportConfigRepo =
			SpringContextHolder.instance.getBean(DATEVExportConfigRepository.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setDatevExportConfig(final I_DATEV_Export datevExport)
	{
		if (InterfaceWrapperHelper.isNew(datevExport))
		{
			final DATEVExportConfig exportConfig = exportConfigRepo.getByOrgId(OrgId.ofRepoId(datevExport.getAD_Org_ID()));
			if (exportConfig != null)
			{
				datevExport.setDATEV_Export_Config_ID(exportConfig.getId());
				datevExport.setAdvisorNumber(exportConfig.getAdvisorNumber());
				datevExport.setClientNumber(exportConfig.getClientNumber());
				datevExport.setChartOfAccounts(exportConfig.getChartOfAccounts());
				datevExport.setChartOfAccountsNumberLength(exportConfig.getChartOfAccountsNumberLength());
			}
		}
	}

}
