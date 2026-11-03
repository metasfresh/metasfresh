package de.metas.tourplanning.api.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.common.util.time.SystemTime;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IOrderDeliveryDayBL;
import de.metas.tourplanning.model.TourId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.MDC;

import javax.annotation.Nullable;
import java.sql.Timestamp;
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
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

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

		// Compute the header's preparation date + tour from the header's DatePromised.
		final PreparationDateAndTour preparationDateAndTour = computePreparationDateAndTour0(order, datePromised, fallbackToDatePromised, timeZone);

		order.setPreparationDate(TimeUtil.asTimestamp(preparationDateAndTour.getPreparationDate()));
		order.setM_Tour_ID(TourId.toRepoId(preparationDateAndTour.getTourId()));

		return true;
	}

	// Derive the preparation date from a (per-line) deliveryDate using the same tour / no-tour-fallback / offset /
	// sysconfig logic the order header uses; fallbackToDatePromised=true mirrors the system/OLCand path so a non-null
	// date is returned even without a configured tour. Does NOT mutate the order.
	@Nullable
	private ZonedDateTime computePreparationDate(@NonNull final I_C_Order order, @NonNull final ZonedDateTime deliveryDate)
	{
		final ZoneId timeZone = orderBL.getTimeZone(order);
		// the tour calculation needs a bpartner location; if absent, fall back to the order's stored preparation date
		if (BPartnerLocationId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID()) == null)
		{
			return TimeUtil.asZonedDateTime(order.getPreparationDate(), timeZone);
		}
		return computePreparationDateAndTour0(order, deliveryDate, true, timeZone).getPreparationDate();
	}

	@Override
	@Nullable
	public ZonedDateTime computePreparationDate(@NonNull final I_C_Order order, @NonNull final I_C_OrderLine orderLine, @NonNull final ZonedDateTime deliveryDate)
	{
		// An explicit per-line C_OrderLine.PreparationDate override wins verbatim; otherwise derive from the
		// (per-line) delivery date. This method is the single owner of the override-or-derive decision.
		final Timestamp lineOverride = orderLine.getPreparationDate();
		if (lineOverride != null)
		{
			return TimeUtil.asZonedDateTime(lineOverride, orderBL.getTimeZone(order));
		}
		return computePreparationDate(order, deliveryDate);
	}

	@Override
	public ZonedDateTime computeDeliveryDate(@NonNull final I_C_Order order, @NonNull final I_C_OrderLine orderLine)
	{
		final ZonedDateTime deliveryDate = computeDeliveryDateOrNull(order, orderLine, orderBL.getTimeZone(order));
		if (deliveryDate == null)
		{
			throw new AdempiereException("@NotFound@ @DeliveryDate@")
					.appendParametersToMessage()
					.setParameter("orderLine", orderLine)
					.setParameter("order", order);
		}
		return deliveryDate;
	}

	/**
	 * The per-line delivery date: an explicit {@code C_OrderLine.PresetDateShipped} wins, then the line's own
	 * {@code DatePromised}, finally the order header's {@code DatePromised}. Returns {@code null} when none is set.
	 */
	@VisibleForTesting
	@Nullable
	static ZonedDateTime computeDeliveryDateOrNull(@NonNull final I_C_Order order, @NonNull final I_C_OrderLine orderLine, @NonNull final ZoneId timeZone)
	{
		final ZonedDateTime presetDateShipped = TimeUtil.asZonedDateTime(orderLine.getPresetDateShipped(), timeZone);
		if (presetDateShipped != null)
		{
			return presetDateShipped;
		}
		final ZonedDateTime lineDatePromised = TimeUtil.asZonedDateTime(orderLine.getDatePromised(), timeZone);
		if (lineDatePromised != null)
		{
			return lineDatePromised;
		}
		return TimeUtil.asZonedDateTime(order.getDatePromised(), timeZone);
	}

	@Override
	public boolean setLinePreparationDate(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		// The per-line preparation date is a sales concept; purchase orders recompute the header (updatePurchaseHeaderPreparationDate).
		if (!order.isSOTrx() || order.isProcessed())
		{
			return false;
		}

		final ZonedDateTime deliveryDate = computeDeliveryDateOrNull(order, orderLine, orderBL.getTimeZone(order));
		if (deliveryDate == null)
		{
			return false;
		}

		// Reuse the exact derive path the shipment-schedule provider uses, so the line's PreparationDate always equals
		// the schedule's initial PreparationDate. Overrides live on M_ShipmentSchedule.PreparationDate_Override.
		final ZonedDateTime preparationDate = computePreparationDate(order, deliveryDate);
		if (preparationDate == null)
		{
			// Reachable only when the order has no C_BPartner_Location_ID and no stored header PreparationDate; leaving
			// the line's PreparationDate empty lets the shipment-schedule provider re-derive it the same way later.
			logger.debug("No preparation date derived for C_OrderLine {} (deliveryDate={}); leaving it empty", orderLine, deliveryDate);
		}
		orderLine.setPreparationDate(TimeUtil.asTimestamp(preparationDate));
		return preparationDate != null;
	}

	@Override
	public boolean updatePurchaseHeaderPreparationDate(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		if (order.isSOTrx())
		{
			return false;
		}
		final boolean set = setPreparationDateAndTour(order, /* fallbackToDatePromised= */ true);
		orderBL.save(order);
		return set;
	}

	/**
	 * Core preparation-date computation, shared by the header path ({@link #setPreparationDateAndTour0}) and the
	 * per-line path ({@link #computePreparationDate}).
	 * <p>
	 * The given {@code deliveryDate} is used both as the {@code datePromised} argument to
	 * {@link IDeliveryDayBL#calculateTourAndPreparationDate} AND as the base of the no-tour fallback. All other inputs
	 * (calculationTime, soTrx, bpartnerLocationId, sysconfig reads, offset) are unchanged from the original inline logic.
	 *
	 * @return the computed preparation date (may be {@code null} when there is no usable tour and the fallback is
	 *         disabled) together with the tour to assign ({@code null} when no tour was used).
	 */
	private PreparationDateAndTour computePreparationDateAndTour0(
			@NonNull final I_C_Order order,
			@NonNull final ZonedDateTime deliveryDate,
			final boolean fallbackToDatePromised,
			@NonNull final ZoneId timeZone)
	{
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
		// both callers reach this only with a bpartner location set; calculateTourAndPreparationDate below requires it
		Check.assumeNotNull(bpartnerLocationId, "C_BPartner_Location_ID is set for {}", order);

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
				deliveryDate,
				bpartnerLocationId);
		final ZonedDateTime preparationDate = tourAndDate.getRight();

		final ZonedDateTime systemTime = SystemTime.asZonedDateTime(timeZone);
		if (preparationDate != null && preparationDate.isAfter(systemTime))
		{
			final int offset = isUseFallback ? getFallbackPreparationDateOffsetInHours() : 0;

			final TourId tourId = tourAndDate.getLeft();

			logger.debug("Setting Tour {} for C_Order {}. Old Tour was {} (fallbackToDatePromised={}, systemTime={})",
					tourId.getRepoId(),
					order,
					order.getM_Tour_ID(),
					isUseFallback,
					systemTime);

			logger.debug("Setting PreparationDate={}, for C_Order {} (fallbackToDatePromised={}, systemTime={})",
					preparationDate, order, isUseFallback, systemTime);

			return new PreparationDateAndTour(computePreparationTime(preparationDate, offset), tourId);
		}
		else if (isUseFallback)
		{
			final int offset = getFallbackPreparationDateOffsetInHours();
			final ZonedDateTime fallbackBase;
			if (soTrx.isPurchase())
			{
				final int maxTransportDays = orderBL.getMaxPurchaseTransportDays(order);
				fallbackBase = deliveryDate.minusDays(maxTransportDays);
			}
			else
			{
				fallbackBase = deliveryDate;
			}
			final ZonedDateTime fallbackPreparationDate = computePreparationTime(fallbackBase, offset);
			logger.debug(
					"Setting PreparationDate={} for C_Order {} (soTrx={}, fallbackToDatePromised={}, systemTime={}).",
					fallbackPreparationDate, order, soTrx, isUseFallback, systemTime);
			return new PreparationDateAndTour(fallbackPreparationDate, null);
		}
		else
		{
			logger.info("Setting PreparationDate={} for C_Order {}, because the computed PreparationDate={} is null or has already passed (fallbackToDatePromised={}, systemTime={}). Leaving it to the user to set a date manually.",
					preparationDate, order, preparationDate, isUseFallback, systemTime);
			return new PreparationDateAndTour(null, null);
		}
	}

	@Value
	private static class PreparationDateAndTour
	{
		@Nullable ZonedDateTime preparationDate;
		@Nullable TourId tourId;
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
