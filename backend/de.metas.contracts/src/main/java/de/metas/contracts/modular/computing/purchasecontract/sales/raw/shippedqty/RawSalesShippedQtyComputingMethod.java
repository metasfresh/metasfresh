/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.computing.purchasecontract.sales.raw.shippedqty;

import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractPriceService;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.computing.purchasecontract.AbstractShipmentForPurchaseComputingMethod;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

@Component
public class RawSalesShippedQtyComputingMethod extends AbstractShipmentForPurchaseComputingMethod
{
	public RawSalesShippedQtyComputingMethod(
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ModularContractPriceService modularContractPriceService,
			@NonNull final ComputingMethodService computingMethodService)
	{
		super(contractProvider, computingMethodService, modularContractPriceService, ComputingMethodType.SalesOnRawProductShippedQty);
	}

	@Override
	public boolean isApplicableForSettings(final @NonNull TableRecordReference recordRef, final @NonNull ModularContractSettings settings)
	{
		final I_M_InOutLine shipmentLine = getShipmentLine(recordRef);
		final ProductId shipmentProductId = ProductId.ofRepoId(shipmentLine.getM_Product_ID());
		return ProductId.equals(shipmentProductId, settings.getRawProductId()) && settings.getSoTrx().isPurchase();
	}
}
