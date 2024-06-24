package de.metas.pricing.service;

import de.metas.adempiere.model.I_M_Product;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.ProductPriceQuery.IProductPriceQueryMatcher;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UOMConversionsMap;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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
	private static final AdMessageKey MSG_NO_UOM_CONVERSION_AVAILABLE = AdMessageKey.of("de.metas.pricing.service.product.MissingUOMConversion");

	private static final CopyOnWriteArrayList<IProductPriceQueryMatcher> MATCHERS_MainProductPrice = new CopyOnWriteArrayList<>();

	private static final Logger logger = LogManager.getLogger(ProductPrices.class);

	public static ProductPriceQuery newQuery(@NonNull final I_M_PriceList_Version plv)
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID());
		return new ProductPriceQuery()
				.setPriceListVersionId(priceListVersionId);
	}

	/**
	 * Convenient method to check if the main product price exists.
	 *
	 * @param plv       price list version or null
	 * @param productId product (negative values are tolerated)
	 * @return true if exists
	 */
	public static boolean hasMainProductPrice(@Nullable final I_M_PriceList_Version plv, @Nullable final ProductId productId)
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

	public static void assertMainProductPriceIsNotDuplicate(final I_M_ProductPrice productPrice)
	{
		if (productPrice == null || !productPrice.isActive())
		{
			return;
		}

		if (productPrice.isInvalidPrice())
		{
			return;
		}

		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(productPrice.getM_PriceList_Version_ID());
		final I_M_PriceList_Version priceListVersion = priceListsRepo.getPriceListVersionByIdInTrx(priceListVersionId);
		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());

		final List<I_M_ProductPrice> allMainPrices = retrieveAllMainPrices(priceListVersion, productId);

		final boolean productPriceIsMainPrice = allMainPrices.stream()
				.anyMatch(mainPrice -> mainPrice.getM_ProductPrice_ID() == productPrice.getM_ProductPrice_ID());
		if (!productPriceIsMainPrice)
		{
			return;
		}

		getFirstOrThrowExceptionIfMoreThanOne(allMainPrices);
	}

	public static void assertUomConversionExists(final I_M_ProductPrice productPrice)
	{
		if (productPrice == null || !productPrice.isActive())
		{
			return;
		}

		if (productPrice.isInvalidPrice())
		{
			return;
		}

		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final org.compiere.model.I_M_Product product = productDAO.getById(productPrice.getM_Product_ID());

		if (UomId.ofRepoId(product.getC_UOM_ID()).equals(UomId.ofRepoId(productPrice.getC_UOM_ID())))
		{
			return;
		}

		final IUOMConversionDAO uomConversionRepo = Services.get(IUOMConversionDAO.class);
		final ProductId productId = ProductId.ofRepoId(productPrice.getM_Product_ID());

		final UOMConversionsMap conversionsMap = uomConversionRepo.getProductConversions(productId);

		if (!conversionsMap.getRateIfExists(UomId.ofRepoId(product.getC_UOM_ID()), UomId.ofRepoId(productPrice.getC_UOM_ID())).isPresent())
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			final ITranslatableString message = msgBL.getTranslatableMsgText(MSG_NO_UOM_CONVERSION_AVAILABLE);
			throw new AdempiereException(message).markAsUserValidationError();
		}
	}

	@Nullable
	public static I_M_ProductPrice retrieveMainProductPriceOrNull(final I_M_PriceList_Version plv, final ProductId productId)
	{
		final List<I_M_ProductPrice> allMainPrices = retrieveAllMainPrices(plv, productId);
		return getFirstOrThrowExceptionIfMoreThanOne(allMainPrices);
	}

	private static List<I_M_ProductPrice> retrieveAllMainPrices(
			@NonNull final I_M_PriceList_Version plv,
			@NonNull final ProductId productId)
	{
		return newMainProductPriceQuery(plv, productId)
				.list();
	}

	@NonNull
	private static ProductPriceQuery newMainProductPriceQuery(final I_M_PriceList_Version plv, final ProductId productId)
	{
		return newQuery(plv)
				.setProductId(productId)
				.noAttributePricing()
				.onlyValidPrices(true)
				//
				.addMatchersIfAbsent(MATCHERS_MainProductPrice); // IMORTANT: keep it last
	}

	@Nullable
	private static I_M_ProductPrice getFirstOrThrowExceptionIfMoreThanOne(@NonNull final List<I_M_ProductPrice> allMainPrices)
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
		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
		final IProductBL productBL = Services.get(IProductBL.class);

		final String productName = productBL.getProductValueAndName(ProductId.ofRepoId(someMainProductPrice.getM_Product_ID()));

		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(someMainProductPrice.getM_PriceList_Version_ID());
		final I_M_PriceList_Version plv = priceListsRepo.getPriceListVersionById(priceListVersionId);

		final PriceListId priceListId = PriceListId.ofRepoId(plv.getM_PriceList_ID());
		final I_M_PriceList pl = priceListsRepo.getById(priceListId);

		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(pl.getM_PricingSystem_ID());
		final String pricingSystemName = priceListsRepo.getPricingSystemName(pricingSystemId);

		return new DuplicateMainProductPriceException(someMainProductPrice)
				.setParameter(I_M_PricingSystem.Table_Name, pricingSystemName)
				.setParameter(I_M_PriceList.Table_Name, pl.getName())
				.setParameter(I_M_PriceList_Version.Table_Name, plv.getName())
				.setParameter(I_M_Product.Table_Name, productName);
	}

	@SuppressWarnings("serial")
	public static final class DuplicateMainProductPriceException extends AdempiereException
	{
		private static final AdMessageKey MSG_M_ProductPrice_DublicateMainPrice = AdMessageKey.of("M_ProductPrice_DublicateMainPrice");

		private DuplicateMainProductPriceException(@NonNull final I_M_ProductPrice anyDublicatedMainProductPrice)
		{
			super(createExceptionMessage(anyDublicatedMainProductPrice));
		}

		private static String createExceptionMessage(@NonNull final I_M_ProductPrice anyDublicatedMainProductPrice)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(anyDublicatedMainProductPrice);
			final IMsgBL msgBL = Services.get(IMsgBL.class);

			final I_M_Product productRecord = load(anyDublicatedMainProductPrice.getM_Product_ID(), I_M_Product.class);
			final I_M_PriceList_Version plvRecord = load(anyDublicatedMainProductPrice.getM_PriceList_Version_ID(), I_M_PriceList_Version.class);

			return msgBL.getMsg(ctx,
					MSG_M_ProductPrice_DublicateMainPrice,
					new Object[] { productRecord.getValue(), plvRecord.getName() });
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

	public static void clearMainProductPriceMatchers()
	{
		if (!Adempiere.isUnitTestMode())
		{
			throw new AdempiereException("Resetting main product matchers is allowed only when running in JUnit mode");
		}

		MATCHERS_MainProductPrice.clear();
		logger.info("Cleared all main product matchers");
	}

	@Nullable public static <T extends I_M_ProductPrice> T iterateAllPriceListVersionsAndFindProductPrice(
			@Nullable final I_M_PriceList_Version startPriceListVersion,
			@NonNull final Function<I_M_PriceList_Version, T> productPriceMapper,
			@NonNull final ZonedDateTime priceDate)
	{
		if (startPriceListVersion == null)
		{
			return null;
		}

		final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

		final Set<Integer> checkedPriceListVersionIds = new HashSet<>();

		I_M_PriceList_Version currentPriceListVersion = startPriceListVersion;
		while (currentPriceListVersion != null)
		{
			// Stop here if the price list version was already considered
			if (!checkedPriceListVersionIds.add(currentPriceListVersion.getM_PriceList_Version_ID()))
			{
				return null;
			}

			final T productPrice = productPriceMapper.apply(currentPriceListVersion);
			if (productPrice != null)
			{
				return productPrice;
			}

			currentPriceListVersion = priceListsRepo.getBasePriceListVersionForPricingCalculationOrNull(currentPriceListVersion, priceDate);
		}

		return null;
	}

	/**
	 * @deprecated Please use {@link IPriceListDAO#addProductPrice(AddProductPriceRequest)}. If doesn't fit, extend it ;)
	 */
	@Deprecated
	public static I_M_ProductPrice createProductPriceOrUpdateExistentOne(@NonNull final ProductPriceCreateRequest ppRequest, @NonNull final I_M_PriceList_Version plv)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final BigDecimal price = ppRequest.getPrice().setScale(2);
		I_M_ProductPrice pp = ProductPrices.retrieveMainProductPriceOrNull(plv, ProductId.ofRepoId(ppRequest.getProductId()));
		if (pp == null)
		{
			pp = newInstance(I_M_ProductPrice.class, plv);
		}
		// do not update the price with value 0; 0 means that no price was changed
		else if (pp != null && price.signum() == 0)
		{
			return pp;
		}

		pp.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID());
		pp.setM_Product_ID(ppRequest.getProductId());
		pp.setSeqNo(ppRequest.getSeqNo());
		pp.setPriceLimit(price);
		pp.setPriceList(price);
		pp.setPriceStd(price);

		final org.compiere.model.I_M_Product product = productDAO.getById(ppRequest.getProductId());
		pp.setC_UOM_ID(product.getC_UOM_ID());
		pp.setC_TaxCategory_ID(ppRequest.getTaxCategoryId());
		save(pp);

		return pp;
	}
}
