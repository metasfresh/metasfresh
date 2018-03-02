package org.adempiere.bpartner.model.interceptor;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater;
import org.adempiere.bpartner.service.IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest;
import org.adempiere.bpartner.service.IBPartnerStatsDAO;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.interfaces.I_C_BPartner;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Interceptor(I_C_BPartner.class)
public class C_BPartner
{
	@Init
	public void init()
	{
		Services.get(ITabCalloutFactory.class)
				.registerTabCalloutForTable(I_C_BPartner.Table_Name, org.adempiere.bpartner.callout.C_BPartner_TabCallout.class);

		Services.get(IProgramaticCalloutProvider.class)
				.registerAnnotatedCallout(new org.adempiere.bpartner.callout.C_BPartner());
	}

	/**
	 * Makes sure that a new bPartner gets a C_BPartner_Stats record.
	 * We do this because there is at least one hard-coded inner join between the two (in CalloutOrder).
	 * Note that in the DB we have an FK-constraint with "on delete cascade".
	 *
	 * @param bpartner
	 * @task https://github.com/metasfresh/metasfresh/issues/2121
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void createBPartnerStatsRecord(@NonNull final I_C_BPartner bpartner)
	{
		Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(bpartner);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_C_BPartner.COLUMNNAME_C_BP_Group_ID })
	public void updateSO_CreditStatus(@NonNull final I_C_BPartner bpartner)
	{
		// make sure that the SO_CreditStatus is correct
		Services.get(IBPartnerStatisticsUpdater.class)
				.updateBPartnerStatistics(BPartnerStatisticsUpdateRequest.builder()
						.bpartnerId(bpartner.getC_BPartner_ID())
						.updateFromBPGroup(true)
						.build());
	}

}
