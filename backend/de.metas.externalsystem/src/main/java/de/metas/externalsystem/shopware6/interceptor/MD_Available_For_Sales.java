/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.shopware6.interceptor;

import de.metas.externalsystem.export.availableforsales.ExportAvailableForSalesToShopwareExternalSystem;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_MD_Available_For_Sales;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_MD_Available_For_Sales.class)
@Component
public class MD_Available_For_Sales
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ExportAvailableForSalesToShopwareExternalSystem exportAvailableForSalesToShopwareExternalSystem;

	public MD_Available_For_Sales(@NonNull final ExportAvailableForSalesToShopwareExternalSystem exportAvailableForSalesToShopwareExternalSystem)
	{
		this.exportAvailableForSalesToShopwareExternalSystem = exportAvailableForSalesToShopwareExternalSystem;
	}

	@ModelChange( //
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void exportAvailableForSalesToShopware(@NonNull final I_MD_Available_For_Sales availableForSales)
	{
		final ProductId productId = ProductId.ofRepoId(availableForSales.getM_Product_ID());

		trxManager.runAfterCommit(() -> exportAvailableForSalesToShopwareExternalSystem.enqueueProductToBeExported(productId));
	}
}