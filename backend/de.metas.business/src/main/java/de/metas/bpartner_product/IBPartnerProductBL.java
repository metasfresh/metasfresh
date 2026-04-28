package de.metas.bpartner_product;

import de.metas.bpartner.BPartnerId;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner_Product;

import javax.annotation.Nullable;

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

public interface IBPartnerProductBL extends ISingletonService
{
	/**
	 * Throw an exception if the product and partner are involved in a C_BPartnerProduct entry that is flagged to be excluded from the given type of transaction.
	 * if soTrx = SALES and C_BPartner_Product.IsExcludedFromSale = 'Y' => error
	 * if soTrx = PURCHASE and C_BPartner_Product.IsExcludedFromPurchase = 'Y' => error
	 */
	void assertNotExcludedFromTransaction(SOTrx soTrx, ProductId productId, BPartnerId partnerId);

	void setProductCodeFieldsFromGTIN(@NonNull I_C_BPartner_Product record, @Nullable GTIN gtin);

	void setProductCodeFieldsFromEAN13ProductCode(@NonNull I_C_BPartner_Product record, @Nullable EAN13ProductCode ean13ProductCode);
}
