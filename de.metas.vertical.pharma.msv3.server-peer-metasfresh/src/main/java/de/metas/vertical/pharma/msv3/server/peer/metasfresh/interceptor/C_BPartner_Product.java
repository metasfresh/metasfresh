package de.metas.vertical.pharma.msv3.server.peer.metasfresh.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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

@Interceptor(I_C_BPartner_Product.class)
@Component("de.metas.vertical.pharma.msv3.server.peer.metasfresh.interceptor.C_BPartner_Product")
public class C_BPartner_Product
{

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_C_BPartner_Product bpartnerProduct)
	{
		if (!bpartnerProduct.isSalesBan())
		{
			return;
		}

		if (Check.isEmpty(bpartnerProduct.getSalesBanReason(), true))
		{
			throw new FillMandatoryException(I_C_BPartner_Product.COLUMNNAME_SalesBanReason);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void onAfterNew(final I_C_BPartner_Product bpartnerProduct)
	{
		if (!isSalesBan(bpartnerProduct))
		{
			return;
		}

		final MSV3StockAvailabilityService stockAvailabilityService = getStockAvailabilityService();
		final int productId = bpartnerProduct.getM_Product_ID();
		final int newBPartnerId = bpartnerProduct.getC_BPartner_ID();
		final int oldBPartnerId = -1;
		runAfterCommit(() -> stockAvailabilityService.publishProductExcludeAddedOrChanged(productId, newBPartnerId, oldBPartnerId));
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = { I_C_BPartner_Product.COLUMNNAME_IsActive, I_C_BPartner_Product.COLUMNNAME_IsSalesBan, I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID })
	public void onAfterChanged(final I_C_BPartner_Product bpartnerProduct)
	{
		final I_C_BPartner_Product bpartnerProductOld = InterfaceWrapperHelper.createOld(bpartnerProduct, I_C_BPartner_Product.class);
		final boolean wasBanned = isSalesBan(bpartnerProductOld);
		final boolean isBanned = isSalesBan(bpartnerProduct);
		if (!wasBanned && !isBanned)
		{
			return;
		}

		final MSV3StockAvailabilityService stockAvailabilityService = getStockAvailabilityService();
		final int productId = bpartnerProduct.getM_Product_ID();
		final int newBPartnerId = bpartnerProduct.getC_BPartner_ID();
		final int oldBPartnerId = bpartnerProductOld.getC_BPartner_ID();
		if (isBanned)
		{
			runAfterCommit(() -> stockAvailabilityService.publishProductExcludeAddedOrChanged(productId, newBPartnerId, oldBPartnerId));
		}
		else
		{
			runAfterCommit(() -> stockAvailabilityService.publishProductExcludeDeleted(productId, newBPartnerId, oldBPartnerId));
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void onAfterDeleted(final I_C_BPartner_Product bpartnerProduct)
	{
		final I_C_BPartner_Product bpartnerProductOld = InterfaceWrapperHelper.createOld(bpartnerProduct, I_C_BPartner_Product.class);

		final MSV3StockAvailabilityService stockAvailabilityService = getStockAvailabilityService();
		final int productId = bpartnerProduct.getM_Product_ID();
		final int newBPartnerId = bpartnerProduct.getC_BPartner_ID();
		final int oldBPartnerId = bpartnerProductOld.getC_BPartner_ID();
		runAfterCommit(() -> stockAvailabilityService.publishProductExcludeDeleted(productId, newBPartnerId, oldBPartnerId));
	}

	private static boolean isSalesBan(final I_C_BPartner_Product bpartnerProduct)
	{
		return bpartnerProduct.isActive() && bpartnerProduct.isSalesBan();
	}

	private MSV3StockAvailabilityService getStockAvailabilityService()
	{
		return Adempiere.getBean(MSV3StockAvailabilityService.class);
	}

	private void runAfterCommit(@NonNull final Runnable runnable)
	{
		Services.get(ITrxManager.class)
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> runnable.run());
	}
}
