package de.metas.pricing.service;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.pricing.service.ProductPriceQuery.IProductPriceQueryMatcher;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ProductPrices
{
	private static final CopyOnWriteArrayList<IProductPriceQueryMatcher> MATCHERS_MainProductPrice = new CopyOnWriteArrayList<>();

	private static final Logger logger = LogManager.getLogger(ProductPrices.class);

	public static final ProductPriceQuery newQuery(@NonNull final I_M_PriceList_Version plv)
	{
		return new ProductPriceQuery()
				.setContextProvider(plv)
				.setM_PriceList_Version_ID(plv);
	}

	/**
	 * Convenient method to check if the main product price exists.
	 *
	 * @param plv price list version or null
	 * @param productId product (negative values are tolerated)
	 * @return true if exists
	 */
	public static boolean hasMainProductPrice(final I_M_PriceList_Version plv, final ProductId productId)
	{
		if (plv == null)
		{
			return false;
		}
		if (productId == null)
		{
			return false;
		}

		return newMainProductPriceQuery(plv, productId)
				.matches();
	}

	public static void assertIsNoMainPriceDuplicate(final I_M_ProductPrice productPrice)
	{
		if (productPrice == null || !productPrice.isActive())
		{
			return;
		}

		final List<I_M_ProductPrice> allMainPrices = retrieveAllMainPrices(
				productPrice.getM_PriceList_Version(),
				ProductId.ofRepoId(productPrice.getM_Product_ID()));

		final boolean productPriceIsMainPrice = allMainPrices.stream()
				.anyMatch(mainPrice -> mainPrice.getM_ProductPrice_ID() == productPrice.getM_ProductPrice_ID());
		if (!productPriceIsMainPrice)
		{
			return;
		}

		getFirstOrThrowExceptionIfMoreThanOne(allMainPrices);
	}

	public static final I_M_ProductPrice retrieveMainProductPriceOrNull(final I_M_PriceList_Version plv, final ProductId productId)
	{
		final List<I_M_ProductPrice> allMainPrices = retrieveAllMainPrices(plv, productId);
		return getFirstOrThrowExceptionIfMoreThanOne(allMainPrices);
	}

	private static List<I_M_ProductPrice> retrieveAllMainPrices(
			@NonNull final I_M_PriceList_Version plv,
			final ProductId productId)
	{
		final List<I_M_ProductPrice> allMainPrices = newMainProductPriceQuery(plv, productId)
				.toQuery()
				.list();
		return allMainPrices;
	}

	private static final ProductPriceQuery newMainProductPriceQuery(final I_M_PriceList_Version plv, final ProductId productId)
	{
		return newQuery(plv)
				.setProductId(productId)
				.noAttributePricing()
				//
				.addMatchersIfAbsent(MATCHERS_MainProductPrice); // IMORTANT: keep it last
	}

	private static I_M_ProductPrice getFirstOrThrowExceptionIfMoreThanOne(final List<I_M_ProductPrice> allMainPrices)
	{
		if (allMainPrices.isEmpty())
		{
			return null;
		}
		else if (allMainPrices.size() == 1)
		{
			return allMainPrices.get(0);
		}
		else
		{
			throw newDuplicateMainProductPriceException(allMainPrices.get(0));
		}
	}

	private static AdempiereException newDuplicateMainProductPriceException(@NonNull final I_M_ProductPrice someMainProductPrice)
	{
		final IProductBL productBL = Services.get(IProductBL.class);
		final String productName = productBL.getProductValueAndName(ProductId.ofRepoId(someMainProductPrice.getM_Product_ID()));

		final I_M_PriceList_Version plv = someMainProductPrice.getM_PriceList_Version();
		final I_M_PriceList pl = plv.getM_PriceList();
		final I_M_PricingSystem ps = pl.getM_PricingSystem();

		final AdempiereException exception = new DuplicateMainProductPriceException(someMainProductPrice)
				.setParameter(I_M_PricingSystem.Table_Name, ps.getName())
				.setParameter(I_M_PriceList.Table_Name, pl.getName())
				.setParameter(I_M_PriceList_Version.Table_Name, plv.getName())
				.setParameter(I_M_Product.Table_Name, productName);

		return exception;
	}

	public static final class DuplicateMainProductPriceException extends AdempiereException
	{
		private static final long serialVersionUID = 6473484914632431744L;

		private DuplicateMainProductPriceException(@NonNull final I_M_ProductPrice anyDublicatedMainProductPrice)
		{
			super(createExceptionMessage(anyDublicatedMainProductPrice));
		}

		private static String createExceptionMessage(@NonNull final I_M_ProductPrice anyDublicatedMainProductPrice)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(anyDublicatedMainProductPrice);
			final IMsgBL msgBL = Services.get(IMsgBL.class);

			return msgBL.getMsg(ctx,
					"M_ProductPrice_DublicateMainPrice",
					new Object[] { anyDublicatedMainProductPrice.getM_Product().getValue(), anyDublicatedMainProductPrice.getM_PriceList_Version().getName() });
		}
	}

	public static void registerMainProductPriceMatcher(final IProductPriceQueryMatcher matcher)
	{
		Check.assumeNotNull(matcher, "Parameter matcher is not null");
		final boolean added = MATCHERS_MainProductPrice.addIfAbsent(matcher);
		if (!added)
		{
			logger.warn("Main product matcher {} was not registered because it's a duplicate: {}", matcher, MATCHERS_MainProductPrice);
		}
		else
		{
			logger.info("Registered main product matcher: {}", matcher);
		}
	}

}
