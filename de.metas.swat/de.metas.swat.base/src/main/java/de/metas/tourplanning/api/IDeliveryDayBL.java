package de.metas.tourplanning.api;

import java.time.ZonedDateTime;
import java.util.Date;

import org.adempiere.util.lang.IContextAware;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.lang.SOTrx;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_TourVersionLine;
import de.metas.tourplanning.spi.IDeliveryDayHandler;
import de.metas.util.ISingletonService;

public interface IDeliveryDayBL extends ISingletonService
{

	void registerDeliveryDayHandler(IDeliveryDayHandler updater);

	IDeliveryDayHandler getDeliveryDayHandlers();

	/**
	 * 
	 * @param tourVersionLine
	 * @param deliveryDate
	 * @param trxName
	 * @return created {@link I_M_DeliveryDay} or null
	 */
	I_M_DeliveryDay createDeliveryDay(I_M_TourVersionLine tourVersionLine, Date deliveryDate, String trxName);

	/**
	 * Get/Create current Delivery Day Allocation.
	 * 
	 * Following cases are handled:
	 * <ul>
	 * <li>if there is no allocation but we found a matching delivery day, a new allocation will be created on that delivery day
	 * <li>if there is an allocation but we found another delivery day which is matching, the old allocation will be deleted and a new one is created
	 * <li>if there is an allocation and delivery day is matching, that allocation will be kept
	 * </ul>
	 * 
	 * @param context
	 * @param model
	 * @return delivery day allocation or null
	 */
	I_M_DeliveryDay_Alloc getCreateDeliveryDayAlloc(final IContextAware context, final IDeliveryDayAllocable model);

	/**
	 * Mark given delivery day as no longer needed.
	 * 
	 * The implementation will decide if it will just set IsActive flag to <code>false</code> or it will also delete the record.
	 * 
	 * @param deliveryDay
	 * @param trxName
	 */
	void inactivate(I_M_DeliveryDay deliveryDay, String trxName);

	/**
	 * Invalidates given {@link I_M_DeliveryDay}.
	 * 
	 * As a effect, given delivery day will be revalidated (synchronously or asynchronously, depends on implementation).
	 * 
	 * @param deliveryDay
	 */
	void invalidate(I_M_DeliveryDay deliveryDay);

	/**
	 * Calculate the Preparation time based on a ContextAware object, a date promised, sotrx and a location ID
	 * 
	 * @param context - object from where the context is taken
	 * @param soTrx
	 * @param calculationTime the date+time when the calculation is made.
	 *            It will usually be when the date+time when the order was created, or the system time
	 * @param datePromised
	 * @param bpartnerLocationId
	 * @return
	 */
	ZonedDateTime calculatePreparationDateOrNull(
			IContextAware context,
			SOTrx soTrx,
			ZonedDateTime calculationTime,
			ZonedDateTime datePromised,
			BPartnerLocationId bpartnerLocationId);

	/**
	 * Sets DeliveryDateTimeMax = DeliveryDate + BufferHours.
	 * 
	 * @param deliveryDay
	 */
	void setDeliveryDateTimeMax(I_M_DeliveryDay deliveryDay);
}
