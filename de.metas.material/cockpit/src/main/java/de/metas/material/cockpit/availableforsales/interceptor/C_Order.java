package de.metas.material.cockpit.availableforsales.interceptor;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo.ConfigQuery;
import de.metas.material.cockpit.availableforsales.interceptor.AvailableForSalesUtil.CheckAvailableForSalesRequest;
import de.metas.organization.OrgId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-cockpit
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

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{

	private final AvailableForSalesUtil availableForSalesUtil;
	private final AvailableForSalesConfigRepo availableForSalesConfigRepo;

	public C_Order(
			@NonNull final AvailableForSalesUtil availableForSalesUtil,
			@NonNull final AvailableForSalesConfigRepo availableForSalesConfigRepo)
	{
		this.availableForSalesUtil = availableForSalesUtil;
		this.availableForSalesConfigRepo = availableForSalesConfigRepo;
	}

	@ModelChange( //
			timings = ModelValidator.TYPE_AFTER_CHANGE, // no need to run after new, because there are no orderLines yet
			ifColumnsChanged = I_C_Order.COLUMNNAME_PreparationDate)
	public void vaildateQtyAvailableForSale(@NonNull final I_C_Order orderRecord)
	{
		if (!availableForSalesUtil.isOrderEligibleForFeature(orderRecord))
		{
			return; // nothing to do
		}

		final AvailableForSalesConfig config = availableForSalesConfigRepo.getConfig(
				ConfigQuery.builder()
						.clientId(ClientId.ofRepoId(orderRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(orderRecord.getAD_Org_ID()))
						.build());
		if (!config.isFeatureEnabled())
		{
			return; // nothing to do
		}

		// has to contain everything that the method to be invoked after commit needs
		final List<CheckAvailableForSalesRequest> requests = availableForSalesUtil.createRequests(orderRecord);

		availableForSalesUtil.checkAndUpdateOrderLineRecords(requests, config);
	}

}
