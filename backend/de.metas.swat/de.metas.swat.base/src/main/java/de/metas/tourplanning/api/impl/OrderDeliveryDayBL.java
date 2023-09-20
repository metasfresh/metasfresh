package de.metas.tourplanning.api.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderBL;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IOrderDeliveryDayBL;
import de.metas.tourplanning.model.TourId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class OrderDeliveryDayBL implements IOrderDeliveryDayBL
{
	private static final String SYSCONFIG_Fallback_PreparationDate = "de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate";

	private static final String SYSCONFIG_Fallback_PreparationDate_Offset_Hours = "de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate_Offset_Hours";

	private static final Logger logger = LogManager.getLogger(OrderDeliveryDayBL.class);

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override
	public boolean setPreparationDateAndTour(@NonNull final I_C_Order order, final boolean fallbackToDatePromised)
	{
		try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
		{
			return setPreparationDateAndTour0(order, fallbackToDatePromised);
		}

	}

	private boolean setPreparationDateAndTour0(@NonNull final I_C_Order order, final boolean fallbackToDatePromised)
	{
		// Don't touch processed orders
		if (order.isProcessed())
		{
			return false;
		}

		//
		// Extract parameters from order
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
		if (bpartnerLocationId == null)
		{
			return false;
		}

		final ZoneId timeZone = Services.get(IOrderBL.class).getTimeZone(order);
		final ZonedDateTime datePromised = TimeUtil.asZonedDateTime(order.getDatePromised(), timeZone);
		if (datePromised == null)
		{
			return false;
		}

		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

		boolean isUseFallback = fallbackToDatePromised;

		if (!isUseFallback)
		{
			// task 09254
			// Also use the fallback to the date promised if the sysconfig is set to true
			isUseFallback = sysConfigBL.getBooleanValue(SYSCONFIG_Fallback_PreparationDate, true);
		}
		final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);
		final IContextAware context = InterfaceWrapperHelper.getContextAware(order);

		// the date+time when the order was created
		final ZonedDateTime calculationTime = CoalesceUtil.coalesceNotNull(
				TimeUtil.asZonedDateTime(order.getCreated()),
				SystemTime.asZonedDateTime());
		final ImmutablePair<TourId, ZonedDateTime> tourAndDate = deliveryDayBL.calculateTourAndPreparationDate(
				context,
				soTrx,
				calculationTime,
				datePromised,
				bpartnerLocationId);
		final ZonedDateTime preparationDate = tourAndDate.getRight();

		final ZonedDateTime systemTime = de.metas.common.util.time.SystemTime.asZonedDateTime(timeZone);
		if (preparationDate != null && preparationDate.isAfter(systemTime))
		{
			final int offset = isUseFallback ? getFallbackPreparationDateOffsetInHours() : 0;

			order.setPreparationDate(TimeUtil.asTimestamp(computePreparationTime(preparationDate, offset)));

			logger.debug("Setting Tour {} for C_Order {}. Old Tour was {} (fallbackToDatePromised={}, systemTime={})",
					tourAndDate.getLeft().getRepoId(),
					order,
					order.getM_Tour_ID(),
					isUseFallback,
					systemTime);

			order.setM_Tour_ID(tourAndDate.getLeft().getRepoId());

			logger.debug("Setting PreparationDate={}, for C_Order {} (fallbackToDatePromised={}, systemTime={})",
					preparationDate, order, isUseFallback, systemTime);
		}
		else if (isUseFallback)
		{
			int offset = getFallbackPreparationDateOffsetInHours();
			order.setPreparationDate(TimeUtil.asTimestamp(computePreparationTime(datePromised, offset)));
			order.setM_Tour_ID(-1);
			logger.debug(
					"Setting PreparationDate={} for C_Order {} from order's DatePromised value, because the computed PreparationDate={} is null or has already passed (fallbackToDatePromised={}, systemTime={}).",
					order.getDatePromised(), order, preparationDate, isUseFallback, systemTime);
		}
		else
		{
			order.setPreparationDate(null);
			order.setM_Tour_ID(-1);
			logger.info("Setting PreparationDate={} for C_Order {}, because the computed PreparationDate={} is null or has already passed (fallbackToDatePromised={}, systemTime={}). Leaving it to the user to set a date manually.",
					preparationDate, order, preparationDate, isUseFallback, systemTime);
		}

		return true; // value set
	}

	private int getFallbackPreparationDateOffsetInHours() {return sysConfigBL.getIntValue(SYSCONFIG_Fallback_PreparationDate_Offset_Hours, 0);}

	@VisibleForTesting
	static ZonedDateTime computePreparationTime(final ZonedDateTime preparationTimeBase, final int offsetInHours)
	{
		ZonedDateTime preparationTime;
		if (offsetInHours == 0)
		{
			preparationTime = preparationTimeBase;
		}
		else
		{
			final boolean add = offsetInHours >= 0;
			int offset = Math.abs(offsetInHours);
			TemporalUnit unit = ChronoUnit.HOURS;

			// Avoid daylight saving errors in case we have to offset entire days
			if (offset % 24 == 0)
			{
				offset /= 24;
				unit = ChronoUnit.DAYS;
			}

			preparationTime = add
					? preparationTimeBase.plus(offset, unit)
					: preparationTimeBase.minus(offset, unit);
		}

		return preparationTime;
	}
}
