package de.metas.vertical.pharma.msv3.server.peer.metasfresh.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.vertical.pharma.msv3.server.peer.metasfresh.model.MSV3ServerConfig;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.services.MSV3ServerConfigService;
import de.metas.vertical.pharma.msv3.server.peer.metasfresh.services.MSV3StockAvailabilityService;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Interceptor(I_M_Product.class)
@Component
public class M_Product
{
	@Autowired
	private MSV3ServerConfigService serverConfigService;
	@Autowired
	private MSV3StockAvailabilityService stockAvailabilityService;

	private void runAfterCommit(@NonNull final Runnable runnable)
	{
		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> runnable.run());
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onNew(final I_M_Product product)
	{
		final MSV3ServerConfig serverConfig = serverConfigService.getServerConfig();
		if (!isMSV3ServerProduct(serverConfig, product))
		{
			return;
		}

		final int productId = product.getM_Product_ID();
		runAfterCommit(() -> stockAvailabilityService.publishProductAddedEvent(productId));
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_M_Product.COLUMNNAME_IsActive, I_M_Product.COLUMNNAME_M_Product_Category_ID })
	public void onChange(final I_M_Product product)
	{
		final MSV3ServerConfig serverConfig = serverConfigService.getServerConfig();
		final I_M_Product productOld = InterfaceWrapperHelper.createOld(product, I_M_Product.class);
		final boolean wasMSV3Product = isMSV3ServerProduct(serverConfig, productOld);
		final boolean isMSV3Product = isMSV3ServerProduct(serverConfig, product);
		if (wasMSV3Product == isMSV3Product)
		{
			return;
		}

		final int productId = product.getM_Product_ID();
		if (isMSV3Product)
		{
			runAfterCommit(() -> stockAvailabilityService.publishProductChangedEvent(productId));
		}
		else
		{
			runAfterCommit(() -> stockAvailabilityService.publishProductDeletedEvent(productId));
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onDelete(final I_M_Product product)
	{
		final MSV3ServerConfig serverConfig = serverConfigService.getServerConfig();
		if (!isMSV3ServerProduct(serverConfig, product))
		{
			return;
		}

		final int productId = product.getM_Product_ID();
		runAfterCommit(() -> stockAvailabilityService.publishProductDeletedEvent(productId));
	}

	private boolean isMSV3ServerProduct(final MSV3ServerConfig serverConfig, final I_M_Product product)
	{
		if (!serverConfig.hasProducts())
		{
			return false;
		}

		if (!product.isActive())
		{
			return false;
		}

		if (!serverConfig.getProductCategoryIds().contains(product.getM_Product_Category_ID()))
		{
			return false;
		}

		return true;
	}
}
