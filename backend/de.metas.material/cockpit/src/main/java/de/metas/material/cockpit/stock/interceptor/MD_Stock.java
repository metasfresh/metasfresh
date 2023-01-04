/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.material.cockpit.stock.interceptor;

import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfigRepo;
import de.metas.material.cockpit.availableforsales.AvailableForSalesService;
import de.metas.material.cockpit.availableforsales.EnqueueAvailableForSalesRequest;
import de.metas.material.cockpit.availableforsales.interceptor.AvailableForSalesUtil;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Interceptor(I_MD_Stock.class)
@Component
public class MD_Stock
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final AvailableForSalesService availableForSalesService;
	private final AvailableForSalesConfigRepo availableForSalesConfigRepo;
	private final AvailableForSalesUtil availableForSalesUtil;

	public MD_Stock(
			@NonNull final AvailableForSalesService availableForSalesService,
			@NonNull final AvailableForSalesConfigRepo availableForSalesConfigRepo,
			@NonNull final AvailableForSalesUtil availableForSalesUtil)
	{
		this.availableForSalesService = availableForSalesService;
		this.availableForSalesConfigRepo = availableForSalesConfigRepo;
		this.availableForSalesUtil = availableForSalesUtil;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void triggerSyncAvailableForSales(@NonNull final I_MD_Stock stockRecord)
	{
		final AvailableForSalesConfig config = availableForSalesConfigRepo.getConfig(
				AvailableForSalesConfigRepo.ConfigQuery.builder()
						.clientId(ClientId.ofRepoId(stockRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(stockRecord.getAD_Org_ID()))
						.build());

		if (!config.isFeatureEnabled())
		{
			return; // nothing to do
		}

		final Properties ctx = Env.copyCtx(InterfaceWrapperHelper.getCtx(stockRecord));
		final ProductId productId = ProductId.ofRepoId(stockRecord.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(stockRecord.getAD_Org_ID());
		final AttributesKey attributesKey = AttributesKey.ofString(stockRecord.getAttributesKey());

		final EnqueueAvailableForSalesRequest enqueueAvailableForSalesRequest = availableForSalesUtil
				.createRequestWithPreparationDateNow(ctx, config, productId, orgId, attributesKey);

		trxManager.runAfterCommit(() -> availableForSalesService.enqueueAvailableForSalesRequest(enqueueAvailableForSalesRequest));
	}
}