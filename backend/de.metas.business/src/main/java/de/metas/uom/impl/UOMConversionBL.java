package de.metas.uom.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.currency.CurrencyPrecision;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UOMConversionContext.Rounding;
import de.metas.uom.UOMConversionRate;
import de.metas.uom.UOMConversionsMap;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UOMUtil;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.NoUOMConversionException;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.util.time.DurationUtils.WORK_HOURS_PER_DAY;
import static org.adempiere.model.InterfaceWrapperHelper.load;

public class UOMConversionBL implements IUOMConversionBL
{
	private static final Logger logger = LogManager.getLogger(UOMConversionBL.class);

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IUOMConversionDAO uomConversionsDAO = Services.get(IUOMConversionDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@Override
	public BigDecimal convertQty(
			@NonNull final UOMConversionContext conversionCtx /* could technically be nullable, right now I don't see why we should allow it */,
			@NonNull final BigDecimal qty,
			@NonNull final UomId uomFrom,
			@NonNull final UomId uomTo)
	{
		return convertQty(
				conversionCtx.getProductId(),
				conversionCtx.getRounding(),
				qty,
				uomDAO.getById(uomFrom),
				uomDAO.getById(uomTo));
	}

	@Override
	public BigDecimal convertQty(
			@Nullable final ProductId productId,
			@Nullable final Rounding rounding,
			@NonNull final BigDecimal qty,
			@NonNull final I_C_UOM fromUOM,
			@NonNull final I_C_UOM toUOM)
	{
		final Rounding roundingEff = coalesce(rounding, Rounding.TO_TARGET_UOM_PRECISION);
		final UOMPrecision toUOMPrecision = extractStandardPrecision(toUOM);

		final UOMPrecision precision;
		switch (roundingEff)
		{
			case TO_TARGET_UOM_PRECISION:
				precision = toUOMPrecision;
				break;
			case PRESERVE_SCALE:
				precision = UOMPrecision.ofInt(Math.max(qty.scale(), toUOMPrecision.toInt()));
				break;
			default:
				throw new AdempiereException("Unexpected rounding=" + roundingEff);
		}

		if (qty.signum() == 0)
		{
			return precision.round(qty);
		}

		final UomId fromUomId = UomId.ofRepoId(fromUOM.getC_UOM_ID());
		final UomId toUomId = UomId.ofRepoId(toUOM.getC_UOM_ID());
		final UOMConversionRate rate = getRateIfExists(productId, fromUomId, toUomId)
				.orElseThrow(() -> new NoUOMConversionException(productId, fromUomId, toUomId));

		return rate.convert(qty, precision);
	}

	@Override
	public BigDecimal convertQty(
			@NonNull final UOMConversionContext conversionCtx,
			final BigDecimal qty,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo)
	{
		return convertQty(
				conversionCtx.getProductId(),
				conversionCtx.getRounding(),
				qty,
				uomFrom,
				uomTo);
	}

	@Override
	public Quantity convertQuantityTo(
			@NonNull final Quantity quantity,
			@Nullable final UOMConversionContext conversionCtx,
			@NonNull final UomId uomToId)
	{
		final I_C_UOM uomTo = uomDAO.getById(uomToId);
		return convertQuantityTo(quantity, conversionCtx, uomTo);
	}

	@Override
	public Quantity convertQuantityTo(
			@NonNull final Quantity quantity,
			@Nullable final UOMConversionContext conversionCtx,
			@NonNull final I_C_UOM uomTo)
	{
		final UomId uomToId = UomId.ofRepoId(uomTo.getC_UOM_ID());

		// If the Source UOM of this quantity is the same as the UOM to which we need to convert
		// we just need to return the Quantity with current/source switched
		if (UomId.equals(quantity.getSourceUomId(), uomToId))
		{
			return quantity.switchToSource();
		}

		// If current UOM is the same as the UOM to which we need to convert, we shall do nothing
		// final int currentUOMId = quantity.getUOMId();
		final UomId currentUomId = quantity.getUomId();
		if (Objects.equals(currentUomId, uomToId))
		{
			return quantity;
		}

		//
		// Convert current quantity to "uomTo"
		final BigDecimal sourceQtyNew = quantity.toBigDecimal();
		final int sourceUOMNewId = currentUomId.getRepoId();
		final I_C_UOM sourceUOMNew = uomDAO.getById(sourceUOMNewId);

		final BigDecimal qtyNew = convertQty(conversionCtx,
				sourceQtyNew,
				sourceUOMNew, // From UOM
				uomTo // To UOM
		);
		// Create an return the new quantity
		return new Quantity(qtyNew, uomTo, sourceQtyNew, sourceUOMNew);
	}

	@Override
	public BigDecimal convertQtyToProductUOM(
			@NonNull final UOMConversionContext conversionCtx,
			final BigDecimal qty,
			final I_C_UOM uomFrom)
	{
		// Get Product's stocking UOM
		final ProductId productId = conversionCtx.getProductId();
		final I_C_UOM uomTo = productBL.getStockUOM(productId);

		return convertQty(conversionCtx, qty, uomFrom, uomTo);
	}

	@NonNull
	@Override
	public Quantity convertToProductUOM(
			@NonNull final Quantity quantity,
			@NonNull final ProductId productId)
	{
		final BigDecimal sourceQty = quantity.toBigDecimal();
		final I_C_UOM sourceUOM = quantity.getUOM();

		final UOMConversionContext conversionCtx = UOMConversionContext.of(productId);
		final I_C_UOM uomTo = productBL.getStockUOM(productId);
		final BigDecimal qty = convertQty(conversionCtx, sourceQty, sourceUOM, uomTo);
		return new Quantity(qty, uomTo, sourceQty, sourceUOM);
	}

	@Override
	public Quantity computeSum(
			@NonNull final UOMConversionContext conversionCtx,
			@NonNull final Collection<Quantity> quantities,
			@NonNull final UomId toUomId)
	{
		final I_C_UOM toUomRecord = uomDAO.getById(toUomId);
		Quantity resultInTargetUOM = Quantity.zero(toUomRecord);

		for (final Quantity currentQuantity : quantities)
		{
			final Quantity currentQuantityInTargetUOM = convertQuantityTo(currentQuantity, conversionCtx, toUomId);
			resultInTargetUOM = resultInTargetUOM.add(currentQuantityInTargetUOM);
		}
		return resultInTargetUOM;
	}

	@Override
	public BigDecimal adjustToUOMPrecisionWithoutRoundingIfPossible(
			@NonNull final BigDecimal qty,
			@NonNull final I_C_UOM uom)
	{
		final UOMPrecision precision = extractStandardPrecision(uom);
		return precision.adjustToPrecisionWithoutRoundingIfPossible(qty);
	}

	private static UOMPrecision extractStandardPrecision(final I_C_UOM uom)
	{
		return UOMPrecision.ofInt(uom.getStdPrecision());
	}

	private static UOMPrecision extractCostingPrecision(final I_C_UOM uom)
	{
		return UOMPrecision.ofInt(uom.getCostingPrecision());
	}

	@Nullable
	@Override
	public BigDecimal convert(
			final I_C_UOM fromUOM,
			final I_C_UOM toUOM,
			final BigDecimal qty,
			final boolean useStdPrecision)
	{
		// Nothing to do
		if (qty == null || qty.signum() == 0 || fromUOM == null || toUOM == null)
		{
			return qty;
		}

		final UomId fromUomId = UomId.ofRepoId(fromUOM.getC_UOM_ID());
		final UomId toUomId = UomId.ofRepoId(toUOM.getC_UOM_ID());

		final UOMConversionsMap conversions = getGenericRates();
		final UOMConversionRate rate = conversions.getRate(fromUomId, toUomId);

		final UOMPrecision precision = useStdPrecision
				? extractStandardPrecision(toUOM)
				: extractCostingPrecision(toUOM);

		// Calculate & Scale
		return rate.convert(qty, precision);
	}

	@Nullable
	@Override
	public BigDecimal convertFromProductUOM(
			@Nullable final ProductId productId,
			@Nullable final UomId destUomId,
			@Nullable final BigDecimal qtyToConvert)
	{
		if (destUomId == null)
		{
			return qtyToConvert;
		}

		final I_C_UOM uomDest = load(destUomId, I_C_UOM.class);
		return convertFromProductUOM(productId, uomDest, qtyToConvert);
	}

	@Nullable
	@Override
	public BigDecimal convertFromProductUOM(
			@Nullable final ProductId productId,
			@Nullable final I_C_UOM uomDest,
			@Nullable final BigDecimal qtyToConvert)
	{
		if (qtyToConvert == null || qtyToConvert.signum() == 0 || productId == null || uomDest == null)
		{
			return qtyToConvert;
		}

		final UomId fromUomId = productBL.getStockUOMId(productId);
		final UomId toUomId = UomId.ofRepoId(uomDest.getC_UOM_ID());
		final UOMConversionRate rate = getRateIfExists(productId, fromUomId, toUomId).orElse(null);
		if (rate != null)
		{
			final UOMPrecision precision = extractStandardPrecision(uomDest);
			return rate.convert(qtyToConvert, precision);
		}
		else
		{
			return null;
		}
	}

	private UOMConversionsMap getProductConversions(@NonNull final ProductId productId)
	{
		return uomConversionsDAO.getProductConversions(productId);
	}

	private UOMConversionsMap getGenericRates()
	{
		return uomConversionsDAO.getGenericConversions();
	}

	private Optional<UOMConversionRate> getGenericRate(
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo)
	{
		final UomId fromUomId = UomId.ofRepoId(uomFrom.getC_UOM_ID());
		final UomId toUomId = UomId.ofRepoId(uomTo.getC_UOM_ID());
		if (fromUomId.equals(toUomId))
		{
			return Optional.of(UOMConversionRate.one(fromUomId));
		}

		final UOMConversionsMap conversions = getGenericRates();
		final Optional<UOMConversionRate> rate = conversions.getRateIfExists(fromUomId, toUomId);
		if (rate.isPresent())
		{
			return rate;
		}

		// try to derive
		return getTimeConversionRate(uomFrom, uomTo);
	}    // getConversion

	@Override
	public UOMConversionRate getRate(
			@Nullable final ProductId productId,
			@NonNull final UomId fromUomId,
			@NonNull final UomId toUomId)
	{
		return getRateIfExists(productId, fromUomId, toUomId)
				.orElseThrow(() -> new NoUOMConversionException(productId, fromUomId, toUomId));
	}

	private Optional<UOMConversionRate> getRateIfExists(
			@Nullable final ProductId productId,
			@NonNull final UomId fromUomId,
			@NonNull final UomId toUomId)
	{
		if (fromUomId.equals(toUomId))
		{
			return Optional.of(UOMConversionRate.one(fromUomId));
		}

		if (productId != null)
		{
			final UOMConversionsMap rates = getProductConversions(productId);
			final Optional<UOMConversionRate> rate = rates.getRateIfExists(fromUomId, toUomId);
			if (rate.isPresent())
			{
				return rate;
			}
		}

		final Optional<UOMConversionRate> genericRate = getGenericRates().getRateIfExists(fromUomId, toUomId);
		if (genericRate.isPresent())
		{
			return genericRate;
		}

		return getTimeConversionRate(fromUomId, toUomId);
	}

	@Override
	public BigDecimal convertToProductUOM(
			final ProductId productId,
			@Nullable final I_C_UOM uomSource,
			final BigDecimal qtyToConvert)
	{
		final UomId fromUomId = uomSource != null ? UomId.ofRepoId(uomSource.getC_UOM_ID()) : null;
		return convertToProductUOM(productId, qtyToConvert, fromUomId);
	}

	@Nullable
	@Override
	public BigDecimal convertToProductUOM(
			@Nullable final ProductId productId,
			@Nullable final BigDecimal qtyToConvert,
			@Nullable final UomId fromUomId)
	{
		// No conversion
		if (qtyToConvert == null || qtyToConvert.signum() == 0 || fromUomId == null || productId == null)
		{
			logger.debug("qtyToConvert={}; fromUomId={}; productId={}; -> return qtyToConvert", qtyToConvert, fromUomId, productId);
			return qtyToConvert;
		}

		final UomId toUomId = productBL.getStockUOMId(productId);
		final UOMConversionRate rate = getRateIfExists(productId, fromUomId, toUomId).orElse(null);
		if (rate != null)
		{
			final I_C_UOM toUOM = uomDAO.getById(toUomId);
			final UOMPrecision precision = extractStandardPrecision(toUOM);
			return rate.convert(qtyToConvert, precision);
		}
		else
		{
			return null;
		}
	}

	@Override
	public Optional<BigDecimal> convert(
			@NonNull final UomId fromUomId,
			@NonNull final UomId toUomId,
			@NonNull final BigDecimal qty)
	{
		final I_C_UOM fromUom = uomDAO.getById(fromUomId);
		final I_C_UOM toUom = uomDAO.getById(toUomId);

		return convert(fromUom, toUom, qty);
	}

	@Override
	public Optional<Quantity> convertQtyTo(
			@NonNull final Quantity quantity,
			@NonNull final UomId toUomId)
	{
		final I_C_UOM fromUom = uomDAO.getById(quantity.getUomId());
		final I_C_UOM toUom = uomDAO.getById(toUomId);

		return convert(fromUom, toUom, quantity.toBigDecimal()).map(bigDecimal -> Quantitys.of(bigDecimal, toUomId));
	}

	@Override
	public Optional<BigDecimal> convert(
			@NonNull final I_C_UOM fromUOM,
			// int C_UOM_ID,
			@NonNull final I_C_UOM toUOM,
			// int C_UOM_To_ID,
			@NonNull final BigDecimal qty)
	{
		if (qty.signum() == 0)
		{
			return Optional.of(qty);
		}

		final int fromUomId = fromUOM.getC_UOM_ID();
		final int toUomId = toUOM.getC_UOM_ID();
		if (fromUomId == toUomId)
		{
			return Optional.of(qty);
		}

		final UOMConversionRate rate = getGenericRate(fromUOM, toUOM).orElse(null);
		if (rate != null)
		{
			final UOMPrecision precision = extractStandardPrecision(toUOM);
			final BigDecimal qtyConv = rate.convert(qty, precision);
			return Optional.of(qtyConv);
		}
		else
		{
			return Optional.empty();
		}
	}    // convert

	private Optional<UOMConversionRate> getTimeConversionRate(
			@NonNull final UomId fromTimeUomId,
			@NonNull final UomId toTimeUomId)
	{
		final I_C_UOM fromUom = uomDAO.getById(fromTimeUomId);
		final I_C_UOM toUom = uomDAO.getById(toTimeUomId);
		return getTimeConversionRate(fromUom, toUom);
	}

	private Optional<UOMConversionRate> getTimeConversionRate(
			@NonNull final I_C_UOM fromTimeUom,
			@NonNull final I_C_UOM toTimeUom)
	{
		final BigDecimal fromToMultiplier = getTimeConversionRateAsBigDecimal(fromTimeUom, toTimeUom);
		if (fromToMultiplier == null)
		{
			return Optional.empty();
		}

		final UomId fromTimeUomId = UomId.ofRepoId(fromTimeUom.getC_UOM_ID());
		final UomId toTimeUomId = UomId.ofRepoId(toTimeUom.getC_UOM_ID());
		return Optional.of(UOMConversionRate.builder()
				.fromUomId(fromTimeUomId)
				.toUomId(toTimeUomId)
				.fromToMultiplier(fromToMultiplier)
				.build());

	}

	@Nullable
	@VisibleForTesting
	BigDecimal getTimeConversionRateAsBigDecimal(
			@NonNull final I_C_UOM fromTimeUom,
			@NonNull final I_C_UOM toTimeUom)
	{
		final UomId fromTimeUomId = UomId.ofRepoId(fromTimeUom.getC_UOM_ID());
		final UomId toTimeUomId = UomId.ofRepoId(toTimeUom.getC_UOM_ID());
		if (fromTimeUomId.equals(toTimeUomId))
		{
			return BigDecimal.ONE;
		}

		// Time - Minute
		if (UOMUtil.isMinute(fromTimeUom))
		{
			if (UOMUtil.isHour(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 60.0);
			}
			if (UOMUtil.isDay(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 1440.0); // 24 * 60
			}
			if (UOMUtil.isWorkDay(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / WORK_HOURS_PER_DAY * 60); // 8 * 60
			}
			if (UOMUtil.isWeek(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 10080.0); // 7 * 24 * 60
			}
			if (UOMUtil.isMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 43200.0); // 30 * 24 * 60
			}
			if (UOMUtil.isWorkMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 9600.0); // 4 * 5 * 8 * 60
			}
			if (UOMUtil.isYear(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 525600.0); // 365 * 24 * 60
			}
		}

		// Time - Hour
		if (UOMUtil.isHour(fromTimeUom))
		{
			if (UOMUtil.isMinute(toTimeUom))
			{
				return BigDecimal.valueOf(60.0);
			}
			if (UOMUtil.isDay(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 24.0);
			}
			if (UOMUtil.isWorkDay(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / WORK_HOURS_PER_DAY);
			}
			if (UOMUtil.isWeek(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 168.0); // 7 * 24
			}
			if (UOMUtil.isMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 720.0); // 30 * 24
			}
			if (UOMUtil.isWorkMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 20 * WORK_HOURS_PER_DAY); // 4 * 5 * 8(WORK_HOURS_PER_DAY)
			}
			if (UOMUtil.isYear(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 8760.0); // 365 * 24
			}
		}

		// Time - Day
		if (UOMUtil.isDay(fromTimeUom))
		{
			if (UOMUtil.isMinute(toTimeUom))
			{
				return BigDecimal.valueOf(1440.0); // 24 * 60
			}
			if (UOMUtil.isHour(toTimeUom))
			{
				return BigDecimal.valueOf(24.0);
			}
			if (UOMUtil.isWorkDay(toTimeUom))
			{
				return BigDecimal.valueOf(24 / WORK_HOURS_PER_DAY); // 24 / 8
			}
			if (UOMUtil.isWeek(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 7.0); // 7
			}
			if (UOMUtil.isMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 30.0); // 30
			}
			if (UOMUtil.isWorkMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 20.0); // 4 * 5
			}
			if (UOMUtil.isYear(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 365.0); // 365
			}
		}

		// Time - WorkDay
		if (UOMUtil.isWorkDay(fromTimeUom))
		{
			if (UOMUtil.isMinute(toTimeUom))
			{
				return BigDecimal.valueOf(WORK_HOURS_PER_DAY * 60); // 8 * 60
			}
			if (UOMUtil.isHour(toTimeUom))
			{
				return BigDecimal.valueOf(WORK_HOURS_PER_DAY); // 8
			}
			if (UOMUtil.isDay(toTimeUom))
			{
				return BigDecimal.valueOf(WORK_HOURS_PER_DAY / 3); // 24 / 8
			}
			if (UOMUtil.isWeek(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 5); // 5
			}
			if (UOMUtil.isMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 20.0); // 4 * 5
			}
			if (UOMUtil.isWorkMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 20.0); // 4 * 5
			}
			if (UOMUtil.isYear(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 240.0); // 4 * 5 * 12
			}
		}

		// Time - Week
		if (UOMUtil.isWeek(fromTimeUom))
		{
			if (UOMUtil.isMinute(toTimeUom))
			{
				return BigDecimal.valueOf(10080.0); // 7 * 24 * 60
			}
			if (UOMUtil.isHour(toTimeUom))
			{
				return BigDecimal.valueOf(168.0); // 7 * 24
			}
			if (UOMUtil.isDay(toTimeUom))
			{
				return BigDecimal.valueOf(7.0);
			}
			if (UOMUtil.isWorkDay(toTimeUom))
			{
				return BigDecimal.valueOf(5.0);
			}
			if (UOMUtil.isMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 4.0); // 4
			}
			if (UOMUtil.isWorkMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 4.0); // 4
			}
			if (UOMUtil.isYear(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 50.0); // 50
			}
		}

		// Time - Month
		if (UOMUtil.isMonth(fromTimeUom))
		{
			if (UOMUtil.isMinute(toTimeUom))
			{
				return BigDecimal.valueOf(43200.0); // 30 * 24 * 60
			}
			if (UOMUtil.isHour(toTimeUom))
			{
				return BigDecimal.valueOf(720.0); // 30 * 24
			}
			if (UOMUtil.isDay(toTimeUom))
			{
				return BigDecimal.valueOf(30.0); // 30
			}
			if (UOMUtil.isWorkDay(toTimeUom))
			{
				return BigDecimal.valueOf(20.0); // 4 * 5
			}
			if (UOMUtil.isWeek(toTimeUom))
			{
				return BigDecimal.valueOf(4.0); // 4
			}
			if (UOMUtil.isWorkMonth(toTimeUom))
			{
				return BigDecimal.valueOf(1.5); // 30 / 20
			}
			if (UOMUtil.isYear(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 12.0); // 12
			}
		}

		// Time - WorkMonth
		if (UOMUtil.isWorkMonth(fromTimeUom))
		{
			if (UOMUtil.isMinute(toTimeUom))
			{
				return BigDecimal.valueOf(9600.0); // 4 * 5 * 8 * 60
			}
			if (UOMUtil.isHour(toTimeUom))
			{
				return BigDecimal.valueOf(160.0); // 4 * 5 * 8
			}
			if (UOMUtil.isDay(toTimeUom))
			{
				return BigDecimal.valueOf(20.0); // 4 * 5
			}
			if (UOMUtil.isWorkDay(toTimeUom))
			{
				return BigDecimal.valueOf(20.0); // 4 * 5
			}
			if (UOMUtil.isWeek(toTimeUom))
			{
				return BigDecimal.valueOf(4.0); // 4
			}
			if (UOMUtil.isMonth(toTimeUom))
			{
				return BigDecimal.valueOf(20.0 / 30.0); // 20 / 30
			}
			if (UOMUtil.isYear(toTimeUom))
			{
				return BigDecimal.valueOf(1.0 / 12.0); // 12
			}
		}

		// Time - Year
		if (UOMUtil.isYear(fromTimeUom))

		{
			if (UOMUtil.isMinute(toTimeUom))
			{
				return BigDecimal.valueOf(518400.0); // 12 * 30 * 24 * 60
			}
			if (UOMUtil.isHour(toTimeUom))
			{
				return BigDecimal.valueOf(8640.0); // 12 * 30 * 24
			}
			if (UOMUtil.isDay(toTimeUom))
			{
				return BigDecimal.valueOf(365.0); // 365
			}
			if (UOMUtil.isWorkDay(toTimeUom))
			{
				return BigDecimal.valueOf(240.0); // 12 * 4 * 5
			}
			if (UOMUtil.isWeek(toTimeUom))
			{
				return BigDecimal.valueOf(50.0); // 52
			}
			if (UOMUtil.isMonth(toTimeUom))
			{
				return BigDecimal.valueOf(12.0); // 12
			}
			if (UOMUtil.isWorkMonth(toTimeUom))
			{
				return BigDecimal.valueOf(12.0); // 12
			}
		}

		return null;
	}

	@Override
	public ProductPrice convertProductPriceToUom(
			@NonNull final ProductPrice price,
			@NonNull final UomId toUomId,
			@NonNull final CurrencyPrecision pricePrecision)
	{
		return price.convertToUom(toUomId, pricePrecision, this);
	}

	@Override
	public void createUOMConversion(@NonNull final CreateUOMConversionRequest request)
	{
		uomConversionsDAO.createUOMConversion(request);
	}

}
