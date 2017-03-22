package org.adempiere.uom.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.NoUOMConversionException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMBL;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.uom.api.IUOMConversionDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.util.CacheCtx;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;

public class UOMConversionBL implements IUOMConversionBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public IUOMConversionContext createConversionContext(final I_M_Product product)
	{
		return IUOMConversionContext.of(product);
	}

	@Override
	public BigDecimal convertQty(final I_M_Product product, final BigDecimal qty, final I_C_UOM uomFrom, final I_C_UOM uomTo)
	{
		Check.assumeNotNull(uomFrom, "uomFrom not null");
		Check.assumeNotNull(uomTo, "uomTo not null");

		if (qty.signum() == 0)
		{
			return roundToUOMPrecisionIfPossible(qty, uomTo);
		}

		if (uomFrom.getC_UOM_ID() == uomTo.getC_UOM_ID())
		{
			return roundToUOMPrecisionIfPossible(qty, uomTo);
		}

		final BigDecimal qtyConverted = convertQty0(product, qty, uomFrom, uomTo);

		if (qtyConverted == null)
		{

			return roundToUOMPrecisionIfPossible(qtyConverted, uomTo);
		}

		return roundToUOMPrecisionIfPossible(qtyConverted, uomTo);
	}

	@Override
	public BigDecimal convertQty(final IUOMConversionContext conversionCtx, final BigDecimal qty, final I_C_UOM uomFrom, final I_C_UOM uomTo)
	{
		Check.assumeNotNull(conversionCtx, "conversionCtx not null");
		final I_M_Product product = conversionCtx.getM_Product();
		return convertQty(product, qty, uomFrom, uomTo);
	}

	@Override
	public BigDecimal convertQtyToProductUOM(final IUOMConversionContext conversionCtx, final BigDecimal qty, final I_C_UOM uomFrom)
	{
		Check.assumeNotNull(conversionCtx, "conversionCtx not null");

		// Get Product's stocking UOM
		final I_M_Product product = conversionCtx.getM_Product();
		final I_C_UOM uomTo = Services.get(IProductBL.class).getStockingUOM(product);

		return convertQty(conversionCtx, qty, uomFrom, uomTo);
	}

	@Override
	public BigDecimal roundToUOMPrecisionIfPossible(final BigDecimal qty, final I_C_UOM uom)
	{
		Check.assumeNotNull(qty, "qty not null");
		Check.assumeNotNull(uom, "uom not null");

		final int precision = uom.getStdPrecision();
		// NOTE: negative precision is not supported atm
		Check.assume(precision >= 0, "UOM {} shall have positive precision", uom);

		// NOTE: it seems that ZERO is a special case of BigDecimal, so we are computing it right away
		if (qty == null || qty.signum() == 0)
		{
			return BigDecimal.ZERO.setScale(precision);
		}

		final BigDecimal qtyNoZero = qty.stripTrailingZeros();
		final int qtyScale = qtyNoZero.scale();
		if (qtyScale >= precision)
		{
			// Qty's actual scale is bigger than UOM precision, don't touch it
			return qtyNoZero;
		}
		else
		{
			// Qty's actual scale is less than UOM precision. Try to converte it to UOM precision
			// NOTE: we are using without scale because it shall be scaled without any problem
			return qtyNoZero.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
	}

	protected BigDecimal convertQty0(I_M_Product product, BigDecimal qty, I_C_UOM uomFrom, I_C_UOM uomTo)
	{
		// FIXME: this is actually a workaround. We shall refactor the MUOMConversion completely
		// see http://dewiki908/mediawiki/index.php/05529_Fix_UOM_Conversion_%28109933191619%29

		final Properties ctx = InterfaceWrapperHelper.getCtx(product);

		BigDecimal result;

		if (product == null)
		{
			result = convert(ctx, uomFrom, uomTo, qty);
		}

		//
		// Case: uomFrom is the stocking UOM
		if (product.getC_UOM_ID() == uomFrom.getC_UOM_ID())
		{
			// convertProductFrom: converts Qty from stocking UOM to given UOM
			// result = MUOMConversion.convertFromProductUOM(ctx, product, uomTo, qty);
			result = convertFromProductUOM(ctx, product, uomTo, qty);
		}
		//
		// Case: uomTo is the stocking UOM
		else if (product.getC_UOM_ID() == uomTo.getC_UOM_ID())
		{
			// convertProductTo: converts Qty from given UOM to stocking UOM
			result = convertToProductUOM(ctx, product, uomFrom, qty);
		}
		//
		//
		else
		{
			final I_C_UOM productUOM = product.getC_UOM();
			throw new AdempiereException("Case not supported: product's UOM is not " + uomFrom.getUOMSymbol()
					+ " and not " + uomTo.getUOMSymbol()
					+ " but it is " + (productUOM == null ? "NULL" : productUOM.getUOMSymbol()));
		}

		//
		// If result is null throw an exception
		// NOTE: we check the result first and then we gather more debug info
		if (result == null)
		{
			Check.errorIf(true, "Failed to convert Qty={} of product={} from UOM={} to UOM={}",
					qty, product.getValue(), uomFrom.getName(), uomTo.getName());
		}

		return result;

	}

	@Override
	public BigDecimal convertPrice(I_M_Product product, BigDecimal price, I_C_UOM uomFrom, I_C_UOM uomTo, int pricePrecision)
	{
		BigDecimal priceConv = convertQty(product, price, uomFrom, uomTo);
		if (priceConv.scale() > pricePrecision)
		{
			priceConv = priceConv.setScale(pricePrecision, BigDecimal.ROUND_HALF_UP);
		}

		return priceConv;
	}

	@Override
	public BigDecimal convertPriceToUOMUnit(
			final Properties ctx,
			final I_M_Product product,
			final BigDecimal priceInUOMFrom,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo)
	{
		if (priceInUOMFrom.signum() == 0)
		{
			return roundToUOMPrecisionIfPossible(priceInUOMFrom, uomTo);
		}

		if (uomFrom.getC_UOM_ID() == uomTo.getC_UOM_ID())
		{
			return roundToUOMPrecisionIfPossible(priceInUOMFrom, uomTo);
		}
		
		final BigDecimal conversionRate = getRateForConversionToProductUOM(ctx, product, uomTo);
		
		final BigDecimal qtyConverted = conversionRate.multiply(priceInUOMFrom);
		
		
		if (qtyConverted == null)
		{

			return roundToUOMPrecisionIfPossible(qtyConverted, uomTo);
		}

		return roundToUOMPrecisionIfPossible(qtyConverted, uomTo);
	}

	@Override
	public int getPrecision(final Properties ctx, final int uomId)
	{
		if (uomId <= 0)
		{
			// NOTE: if there is no UOM specified, we assume UOM is Each => precision=0
			return 0;
		}
		// NOTE: we assume C_UOM is table level cached
		final I_C_UOM uom = InterfaceWrapperHelper.create(ctx, uomId, I_C_UOM.class, ITrx.TRXNAME_None);
		if (uom == null)
		{
			return 2; // FIXME: is this ok, or better throw exception
		}

		return uom.getStdPrecision();
	}

	@Override
	public BigDecimal roundQty(final I_C_UOM uom, final BigDecimal qty, final boolean useStdPrecision)
	{
		final int precision;
		if (useStdPrecision)
		{
			precision = uom.getStdPrecision();
		}
		else
		{
			precision = uom.getCostingPrecision();
		}

		if (qty.scale() > precision)
		{
			return qty.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		return qty;
	}

	@Override
	public BigDecimal convert(
			final Properties ctx,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo,
			final BigDecimal qty,
			final boolean useStdPrecision)
	{
		// Nothing to do
		if (qty == null || qty.signum() == 0 || uomFrom == null || uomTo == null)
		{
			return qty;
		}

		final Map<ArrayKey, BigDecimal> conversions = getRates(ctx);

		final int uomFromID = uomFrom.getC_UOM_ID();
		final int uomToID = uomTo.getC_UOM_ID();

		final ArrayKey key = mkGenericRatesKey(uomFromID, uomToID);
		final BigDecimal multiplyRate = conversions.get(key);
		if(multiplyRate == null)
		{
			throw new NoUOMConversionException(-1, uomFromID, uomToID);
		}

		final int precision = useStdPrecision ? uomTo.getStdPrecision() : uomTo.getCostingPrecision();

		// Calculate & Scale
		BigDecimal qtyConv = multiplyRate.multiply(qty);
		if (qtyConv.scale() > precision)
		{
			qtyConv = qtyConv.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		return qtyConv;
	}   // convert

	@Override
	public BigDecimal convertFromProductUOM(
			final Properties ctx,
			final I_M_Product product,
			// final int C_UOM_Dest_ID,
			final I_C_UOM uomDest,
			final BigDecimal qtyToConvert)
	{

		if (qtyToConvert == null || qtyToConvert.signum() == 0 || product == null || uomDest == null)
		{
			return qtyToConvert;
		}

		BigDecimal rate = getRateForConversionFromProductUOM(ctx, product, uomDest);
		if (rate != null)
		{
			if (BigDecimal.ONE.compareTo(rate) == 0)
			{
				return qtyToConvert;
			}

			return roundQty(uomDest, rate.multiply(qtyToConvert), true);
		}

		// metas: tsa: begin: 01428
		// Fallback: check general conversion rates
		final I_C_UOM productUOM = product.getC_UOM();
		BigDecimal qtyConv = convert(ctx, productUOM, uomDest, qtyToConvert);
		if (qtyConv != null)
		{
			return qtyConv;
		}
		// metas: tsa: end: 01428

		return null;
	}	// convertProductTo

	@Override
	public List<I_C_UOM_Conversion> getProductConversions(Properties ctx, final I_M_Product product)
	{
		if (product == null)
		{
			return Collections.emptyList();
		}

		final int productId = product.getM_Product_ID();
		if (productId <= 0)
		{
			return Collections.emptyList();
		}

		//
		// Retrieve from database
		final List<I_C_UOM_Conversion> conversions = Services.get(IUOMConversionDAO.class).retrieveProductConversions(ctx, product);

		return conversions;
	}

	@Override
	public BigDecimal getRate(Properties ctx, I_C_UOM uomFrom, I_C_UOM uomTo)
	{
		final int uomFromId = uomFrom.getC_UOM_ID();

		final int uomToId = uomTo.getC_UOM_ID();
		// nothing to do
		if (uomFromId == uomToId)
		{
			return BigDecimal.ONE;
		}

		//
		BigDecimal rate = null;

		final Map<ArrayKey, BigDecimal> conversions = getRates(ctx);

		final ArrayKey conversionKey = mkGenericRatesKey(uomFromId, uomToId);

		rate = conversions.get(conversionKey);

		if (rate != null)
		{
			return rate;
		}

		// try to derive
		return deriveRate(ctx, uomFrom, uomTo);
	}	// getConversion

	private final ArrayKey mkGenericRatesKey(final int uomFromId, final int uomToId)
	{
		return new ArrayKey(uomFromId, uomToId);
	}

	/**
	 * Create Conversion Matrix (Client)
	 * 
	 * @param ctx context
	 */
	@Cached(cacheName = I_C_UOM_Conversion.Table_Name + "#by#GenericConversions",
			expireMinutes = Cached.EXPIREMINUTES_Never)
	Map<ArrayKey, BigDecimal> getRates(@CacheCtx final Properties ctx)
	{
		// Here the conversions will be mapped
		final ImmutableMap.Builder<ArrayKey, BigDecimal> conversionsMap = ImmutableMap.builder();

		final List<I_C_UOM_Conversion> conversions = Services.get(IUOMConversionDAO.class).retrieveGenericConversions(ctx);
		for (final I_C_UOM_Conversion conversion : conversions)
		{
			final int fromUOMId = conversion.getC_UOM_ID();
			final int toUOMId = conversion.getC_UOM_To_ID();

			final ArrayKey directConversionKey = mkGenericRatesKey(fromUOMId, toUOMId);

			//
			// Add fromUOMId -> toUOMId conversion (using multiply rate)
			final BigDecimal multiplyRate = conversion.getMultiplyRate();
			if (multiplyRate.signum() != 0)
			{
				conversionsMap.put(directConversionKey, multiplyRate);
			}

			//
			// Add toUOMId -> fromUOMId conversion (using divide rate)
			BigDecimal divideRate = conversion.getDivideRate();
			if (divideRate.signum() == 0 && multiplyRate.signum() != 0)
			{
				// In case divide rate is not available, calculate divide rate as 1/multiplyRate (precision=12)
				divideRate = BigDecimal.ONE.divide(multiplyRate, 12, BigDecimal.ROUND_HALF_UP);
			}
			
			final ArrayKey reversedConversionKey = mkGenericRatesKey(toUOMId, fromUOMId);
			if (divideRate != null && divideRate.signum() != 0)
			{
				conversionsMap.put(reversedConversionKey, divideRate);
			}
			else
			{
				logger.warn("Not considering product conversion rate {} because divide rate was not determined from {}", reversedConversionKey, conversion);
			}
		}

		return conversionsMap.build();
	}

	/**
	 * Get rate to convert a qty from the stocking UOM of the given <code>M_Product_ID</code>'s product to the given <code>C_UOM_Dest_ID</code> to.
	 * 
	 * @param ctx context
	 * @param product product from whose stocking UOM we want to convert
	 * @param uomDest uom we want to convert to
	 * @return multiplier or null
	 */
	/* package */BigDecimal getRateForConversionFromProductUOM(
			final Properties ctx,
			final I_M_Product product,
			final I_C_UOM uomDest)
	{
		if (product == null)
		{
			return null;
		}
		final List<I_C_UOM_Conversion> rates = getProductConversions(ctx, product);
		if (rates.isEmpty())
		{
			logger.debug("None found");
			return null;
		}

		final int uomSourceId = product.getC_UOM_ID();
		final int uomDestId = uomDest.getC_UOM_ID();

		//
		// Iterate through rates and try finding the best match
		I_C_UOM_Conversion rateReversed = null;
		for (final I_C_UOM_Conversion rate : rates)
		{
			final int rateUomSourceId = rate.getC_UOM_ID();
			final int rateUomDestId = rate.getC_UOM_To_ID();

			// Check if we have a direct conversion
			if (rateUomSourceId == uomSourceId && rateUomDestId == uomDestId)
			{
				return rate.getMultiplyRate();
			}

			// Check if we have a reversed conversion
			if (rateUomSourceId == uomDestId && rateUomDestId == uomSourceId)
			{
				rateReversed = rate;
			}

		}

		if (rateReversed != null)
		{
			return rateReversed.getDivideRate();
		}

		logger.debug("None applied");
		return null;
	}	// getProductRateTo

	/**
	 * Get rate to convert a qty from the given <code>C_UOM_Source_ID</code> to the stocking UOM of the given <code>M_Product_ID</code>'s product.
	 * 
	 * @param ctx context
	 * @param M_Product_ID product to whose stocking UOM we want to convert
	 * @param C_UOM_Source_ID UOM we want to convert from
	 * 
	 * @return multiplier or null
	 */
	/* package */BigDecimal getRateForConversionToProductUOM(
			final Properties ctx,
			final I_M_Product product,
			final I_C_UOM uomSource)
	{
		final List<I_C_UOM_Conversion> rates = getProductConversions(ctx, product);
		if (rates.isEmpty())
		{
			logger.debug("getProductRateFrom - none found");
			return null;
		}

		final int uomSourceId = uomSource.getC_UOM_ID();
		final int uomDestId = product.getC_UOM_ID();

		//
		// Iterate through rates and try finding the best match
		I_C_UOM_Conversion rateReversed = null;
		for (final I_C_UOM_Conversion rate : rates)
		{
			final int rateUomSourceId = rate.getC_UOM_ID();
			final int rateUomDestId = rate.getC_UOM_To_ID();

			// Check if we have a direct conversion
			if (rateUomSourceId == uomDestId && rateUomDestId == uomSourceId)
			{
				return rate.getDivideRate();
			}

			// Check if we have a reversed conversion
			if (rateUomSourceId == uomSourceId && rateUomDestId == uomDestId)
			{
				rateReversed = rate;
			}
		}

		if (rateReversed != null)
		{
			return rateReversed.getMultiplyRate();
		}

		logger.debug("None applied");
		return null;
	}	// getProductRateFrom

	@Override
	public BigDecimal convertToProductUOM(
			final Properties ctx,
			final I_M_Product product,
			final I_C_UOM uomSource,
			final BigDecimal qtyToConvert)
	{

		// No conversion
		if (qtyToConvert == null || qtyToConvert.signum() == 0 || uomSource == null || product == null)
		{
			logger.debug("No Conversion - QtyPrice={}", qtyToConvert);
			return qtyToConvert;
		}

		BigDecimal rate = getRateForConversionToProductUOM(ctx, product, uomSource);
		if (rate != null)
		{
			if (BigDecimal.ONE.compareTo(rate) == 0)
			{
				return qtyToConvert;
			}

			BigDecimal qtyConv = rate.multiply(qtyToConvert);

			// Round converted quantity to product UOM precision
			// NOTE: product UOM is the UOM in which we converted
			final I_C_UOM productUOM = product.getC_UOM();
			qtyConv = roundQty(productUOM, qtyConv, true);

			return qtyConv;
		}

		// metas: tsa: begin: 01428
		// Fallback: check general conversion rates

		final I_C_UOM productUOM = product.getC_UOM();
		BigDecimal conversion = convert(ctx, uomSource, productUOM, qtyToConvert);
		if (conversion != null)
		{
			return conversion;
		}
		// metas: tsa: end: 01428

		logger.debug("No Rate found for product: {}", product);
		return null;
	}	// convertProductFrom

	@Override
	public BigDecimal convert(
			final Properties ctx,
			final I_C_UOM uomFrom, // int C_UOM_ID,
			final I_C_UOM uomTo, // int C_UOM_To_ID,
			final BigDecimal qty)
	{
		final int uomFromID = uomFrom.getC_UOM_ID();
		final int uomToID = uomTo.getC_UOM_ID();

		if (qty == null || qty.signum() == 0 || uomFromID == uomToID)
		{
			return qty;
		}

		BigDecimal rate = getRate(ctx, uomFrom, uomTo);
		if (rate != null)
		{
			BigDecimal qtyConv = rate.multiply(qty);
			qtyConv = roundQty(uomTo, qtyConv, true);
			return qtyConv;
		}
		return null;
	}	// convert

	@Override
	public BigDecimal deriveRate(
			final Properties ctx,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo)
	{

		final int uomFromID = uomFrom.getC_UOM_ID();
		final int uomToID = uomTo.getC_UOM_ID();

		if (uomFromID == uomToID)
		{
			return BigDecimal.ONE;
		}
		// get Info

		if (uomFrom == null || uomTo == null)
		{
			return null;
		}

		final IUOMBL uomBL = Services.get(IUOMBL.class);

		// Time - Minute
		if (uomBL.isMinute(uomFrom))
		{
			if (uomBL.isHour(uomTo))
			{
				return new BigDecimal(1.0 / 60.0);
			}
			if (uomBL.isDay(uomTo))
			{
				return new BigDecimal(1.0 / 1440.0); // 24 * 60
			}
			if (uomBL.isWorkDay(uomTo))
			{
				return new BigDecimal(1.0 / 480.0); // 8 * 60
			}
			if (uomBL.isWeek(uomTo))
			{
				return new BigDecimal(1.0 / 10080.0); // 7 * 24 * 60
			}
			if (uomBL.isMonth(uomTo))
			{
				return new BigDecimal(1.0 / 43200.0); // 30 * 24 * 60
			}
			if (uomBL.isWorkMonth(uomTo))
			{
				return new BigDecimal(1.0 / 9600.0); // 4 * 5 * 8 * 60
			}
			if (uomBL.isYear(uomTo))
			{
				return new BigDecimal(1.0 / 525600.0); // 365 * 24 * 60
			}
		}

		// Time - Hour
		if (uomBL.isHour(uomFrom))
		{
			if (uomBL.isMinute(uomTo))
			{
				return new BigDecimal(60.0);
			}
			if (uomBL.isDay(uomTo))
			{
				return new BigDecimal(1.0 / 24.0);
			}
			if (uomBL.isWorkDay(uomTo))
			{
				return new BigDecimal(1.0 / 8.0);
			}
			if (uomBL.isWeek(uomTo))
			{
				return new BigDecimal(1.0 / 168.0); // 7 * 24
			}
			if (uomBL.isMonth(uomTo))
			{
				return new BigDecimal(1.0 / 720.0); // 30 * 24
			}
			if (uomBL.isWorkMonth(uomTo))
			{
				return new BigDecimal(1.0 / 160.0); // 4 * 5 * 8
			}
			if (uomBL.isYear(uomTo))
			{
				return new BigDecimal(1.0 / 8760.0); // 365 * 24
			}
		}

		// Time - Day
		if (uomBL.isDay(uomFrom))
		{
			if (uomBL.isMinute(uomTo))
			{
				return new BigDecimal(1440.0); // 24 * 60
			}
			if (uomBL.isHour(uomTo))
			{
				return new BigDecimal(24.0);
			}
			if (uomBL.isWorkDay(uomTo))
			{
				return new BigDecimal(3.0); // 24 / 8
			}
			if (uomBL.isWeek(uomTo))
			{
				return new BigDecimal(1.0 / 7.0); // 7
			}
			if (uomBL.isMonth(uomTo))
			{
				return new BigDecimal(1.0 / 30.0); // 30
			}
			if (uomBL.isWorkMonth(uomTo))
			{
				return new BigDecimal(1.0 / 20.0); // 4 * 5
			}
			if (uomBL.isYear(uomTo))
			{
				return new BigDecimal(1.0 / 365.0); // 365
			}
		}

		// Time - WorkDay

		if (uomBL.isWorkDay(uomFrom))
		{
			if (uomBL.isMinute(uomTo))
			{
				return new BigDecimal(480.0); // 8 * 60
			}
			if (uomBL.isHour(uomTo))
			{
				return new BigDecimal(8.0); // 8
			}
			if (uomBL.isDay(uomTo))
			{
				return new BigDecimal(1.0 / 3.0); // 24 / 8
			}
			if (uomBL.isWeek(uomTo))
			{
				return new BigDecimal(1.0 / 5); // 5
			}
			if (uomBL.isMonth(uomTo))
			{
				return new BigDecimal(1.0 / 20.0); // 4 * 5
			}
			if (uomBL.isWorkMonth(uomTo))
			{
				return new BigDecimal(1.0 / 20.0); // 4 * 5
			}
			if (uomBL.isYear(uomTo))
			{
				return new BigDecimal(1.0 / 240.0); // 4 * 5 * 12
			}
		}

		// Time - Week

		if (uomBL.isWeek(uomFrom))
		{
			if (uomBL.isMinute(uomTo))
			{
				return new BigDecimal(10080.0); // 7 * 24 * 60
			}
			if (uomBL.isHour(uomTo))
			{
				return new BigDecimal(168.0); // 7 * 24
			}
			if (uomBL.isDay(uomTo))
			{
				return new BigDecimal(7.0);
			}
			if (uomBL.isWorkDay(uomTo))
			{
				return new BigDecimal(5.0);
			}
			if (uomBL.isMonth(uomTo))
			{
				return new BigDecimal(1.0 / 4.0); // 4
			}
			if (uomBL.isWorkMonth(uomTo))
			{
				return new BigDecimal(1.0 / 4.0); // 4
			}
			if (uomBL.isYear(uomTo))
			{
				return new BigDecimal(1.0 / 50.0); // 50
			}
		}

		// Time - Month
		if (uomBL.isMonth(uomFrom))
		{
			if (uomBL.isMinute(uomTo))
			{
				return new BigDecimal(43200.0); // 30 * 24 * 60
			}
			if (uomBL.isHour(uomTo))
			{
				return new BigDecimal(720.0); // 30 * 24
			}
			if (uomBL.isDay(uomTo))
			{
				return new BigDecimal(30.0); // 30
			}
			if (uomBL.isWorkDay(uomTo))
			{
				return new BigDecimal(20.0); // 4 * 5
			}
			if (uomBL.isWeek(uomTo))
			{
				return new BigDecimal(4.0); // 4
			}
			if (uomBL.isWorkMonth(uomTo))
			{
				return new BigDecimal(1.5); // 30 / 20
			}
			if (uomBL.isYear(uomTo))
			{
				return new BigDecimal(1.0 / 12.0); // 12
			}
		}

		// Time - WorkMonth
		if (uomBL.isWorkMonth(uomFrom))
		{
			if (uomBL.isMinute(uomTo))
			{
				return new BigDecimal(9600.0); // 4 * 5 * 8 * 60
			}
			if (uomBL.isHour(uomTo))
			{
				return new BigDecimal(160.0); // 4 * 5 * 8
			}
			if (uomBL.isDay(uomTo))
			{
				return new BigDecimal(20.0); // 4 * 5
			}
			if (uomBL.isWorkDay(uomTo))
			{
				return new BigDecimal(20.0); // 4 * 5
			}
			if (uomBL.isWeek(uomTo))
			{
				return new BigDecimal(4.0); // 4
			}
			if (uomBL.isMonth(uomTo))
			{
				return new BigDecimal(20.0 / 30.0); // 20 / 30
			}
			if (uomBL.isYear(uomTo))
			{
				return new BigDecimal(1.0 / 12.0); // 12
			}
		}

		// Time - Year
		if (uomBL.isYear(uomFrom))

		{
			if (uomBL.isMinute(uomTo))
			{
				return new BigDecimal(518400.0); // 12 * 30 * 24 * 60
			}
			if (uomBL.isHour(uomTo))
			{
				return new BigDecimal(8640.0); // 12 * 30 * 24
			}
			if (uomBL.isDay(uomTo))
			{
				return new BigDecimal(365.0); // 365
			}
			if (uomBL.isWorkDay(uomTo))
			{
				return new BigDecimal(240.0); // 12 * 4 * 5
			}
			if (uomBL.isWeek(uomTo))
			{
				return new BigDecimal(50.0); // 52
			}
			if (uomBL.isMonth(uomTo))
			{
				return new BigDecimal(12.0); // 12
			}
			if (uomBL.isWorkMonth(uomTo))
			{
				return new BigDecimal(12.0); // 12
			}
		}

		return null;
	}	// deriveRate

}
