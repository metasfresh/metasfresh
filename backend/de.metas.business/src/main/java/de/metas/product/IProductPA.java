package de.metas.product;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.adempiere.model.I_M_Product;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.model.I_M_ProductScalePrice;
import org.compiere.model.MAttributeSetInstance;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Properties;

public interface IProductPA extends ISingletonService
{
	I_M_Product retrieveProduct(Properties ctx, String value, boolean throwEx, String trxName);

	I_M_Product retrieveProduct(Properties ctx, int productId, boolean throwEx, String trxName);

	Collection<MAttributeSetInstance> retrieveSerno(int productId, String text, String trxName);

	/**
	 * throws ProductNotOnPriceListException
	 */
	BigDecimal retrievePriceStd(@NonNull OrgId orgId, int productId, int bPartnerId, int priceListId, @Nullable CountryId countryId, BigDecimal qty, boolean soTrx);

	Collection<I_M_ProductScalePrice> retrieveScalePrices(int productPriceId, String trxName);

	I_M_ProductScalePrice retrieveOrCreateScalePrices(int productPriceId, BigDecimal qty, boolean createNew, String trxName);

	I_M_ProductScalePrice retrieveScalePrices(int productPriceId, BigDecimal qty, String trxName);
}
