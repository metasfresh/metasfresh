package de.metas.tourplanning.api.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IOrderDeliveryDayBL;
import de.metas.tourplanning.model.TourId;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

public class OrderDeliveryDayBL implements IOrderDeliveryDayBL
{
	public static final String SYSCONFIG_Fallback_PreparationDate = "de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate";

	private static final transient Logger logger = LogManager.getLogger(OrderDeliveryDayBL.class);

	@Override
	public boolean setPreparationDateAndTour(@NonNull final I_C_Order order, final boolean fallbackToDatePromised)
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

			isUseFallback = Services.get(ISysConfigBL.class)
					.getBooleanValue(
							SYSCONFIG_Fallback_PreparationDate, true // default true
					);
		}
		final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);
		final IContextAware context = InterfaceWrapperHelper.getContextAware(order);

		// the date+time when the order was created
		final ZonedDateTime calculationTime = CoalesceUtil.coalesce(
				TimeUtil.asZonedDateTime(order.getCreated()),
				SystemTime.asZonedDateTime());
		final ImmutablePair<TourId, ZonedDateTime> tourAndDate = deliveryDayBL.calculateTourAndPreparationDate(
				context,
				soTrx,
				calculationTime,
				datePromised,
				bpartnerLocationId);
		final ZonedDateTime preparationDate = tourAndDate.getRight();

		//
		// Update order
		// only set the date if it has not yet passed.
		// if it has, leave the field empty and let the user pick a new preparation date
		final ZonedDateTime systemTime = SystemTime.asZonedDateTime(timeZone);
		if (preparationDate != null && preparationDate.isAfter(systemTime))
		{
			order.setPreparationDate(TimeUtil.asTimestamp(preparationDate));
			// never update an existing tour
			if (order.getM_Tour_ID() == 0)
			{
				order.setM_Tour_ID(tourAndDate.getLeft().getRepoId());
			}
			logger.debug("Setting PreparationDate={}, for C_Order {} (fallbackToDatePromised={}, systemTime={})",
					preparationDate, order, isUseFallback, systemTime);
		}
		else if (isUseFallback)
		{
			order.setPreparationDate(order.getDatePromised());
			order.setM_Tour_ID(-1);
			logger.debug(
					"Setting PreparationDate={} for C_Order {} from order's DatePromised value, because the computed PreparationDate={} is null or has already passed (fallbackToDatePromised={}, systemTime={}).",
					order.getDatePromised(), order, preparationDate, isUseFallback, systemTime);
		}
		else
		{
			order.setPreparationDate(null);
			order.setM_Tour_ID(-1);
			logger.info(
					"Setting PreparationDate={} for C_Order {}, because the computed PreparationDate={} is null or has already passed (fallbackToDatePromised={}, systemTime={}). Leaving it to the user to set a date manually.",
					preparationDate, order, preparationDate, isUseFallback, systemTime);
		}

		return true; // value set
	}
}
