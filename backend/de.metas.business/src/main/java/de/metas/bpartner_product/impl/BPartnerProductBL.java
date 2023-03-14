package de.metas.bpartner_product.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductBL;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.bpartner_product.ProductExclude;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * de.metas.business
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

public class BPartnerProductBL implements IBPartnerProductBL
{
	private static final String MSG_ProductSalesExclusionError = "ProductSalesExclusionError";
	private static final String MSG_ProductPurchaseExclusionError = "ProductPurchaseExclusionError";

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerProductDAO bpProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public void assertNotExcludedFromTransaction(@NonNull final SOTrx soTrx, @NonNull final ProductId productId, @NonNull final BPartnerId partnerId)
	{
		if (soTrx.isSales())
		{
			assertNotExcludedFromSaleToCustomer(productId, partnerId);
		}
		else
		{
			assertNotExcludedFromPurchaseFromVendor(productId, partnerId);
		}
	}

	private void assertNotExcludedFromSaleToCustomer(@NonNull final ProductId productId, @NonNull final BPartnerId partnerId)
	{
		bpProductDAO.getExcludedFromSaleToCustomer(productId, partnerId)
				.ifPresent(productExclude -> this.throwException(productExclude, AdMessageKey.of(MSG_ProductSalesExclusionError)));
	}

	private void assertNotExcludedFromPurchaseFromVendor(@NonNull final ProductId productId, @NonNull final BPartnerId partnerId)
	{
		bpProductDAO.getExcludedFromPurchaseFromVendor(productId, partnerId)
				.ifPresent(productExclude -> this.throwException(productExclude, AdMessageKey.of(MSG_ProductPurchaseExclusionError)));
	}

	private void throwException(@NonNull final ProductExclude productExclude, @NonNull final AdMessageKey adMessage)
	{
		final String partnerName = bpartnerDAO.getBPartnerNameById(productExclude.getBpartnerId());
		final String msg = msgBL.getMsg(adMessage,
										ImmutableList.of(partnerName, productExclude.getReason()));

		throw new AdempiereException(msg);
	}
}
