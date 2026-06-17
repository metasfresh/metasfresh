/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2025 metas GmbH
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
import de.metas.material.cockpit.availableforsales.interceptor.AvailableForSalesUtil;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_MD_Stock.class)
@Component
public class MD_Stock
{
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
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(stockRecord.getAD_Client_ID(), stockRecord.getAD_Org_ID());
		final AvailableForSalesConfig config = availableForSalesConfigRepo.getConfig(clientAndOrgId);
		if (!config.isFeatureEnabled())
		{
			return; // nothing to do
		}

		availableForSalesService.enqueueAvailableForSalesRequestAfterCommit(
				availableForSalesUtil.requestWithPreparationDateNow()
						.ctx(InterfaceWrapperHelper.getCtx(stockRecord))
						.config(config)
						.productId(ProductId.ofRepoId(stockRecord.getM_Product_ID()))
						.clientAndOrgId(clientAndOrgId)
						.storageAttributesKey(AttributesKey.ofString(stockRecord.getAttributesKey()))
						.warehouseId(WarehouseId.ofRepoId(stockRecord.getM_Warehouse_ID()))
						.build()
		);
	}
}
