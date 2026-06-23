package de.metas.tourplanning.api;

import org.compiere.model.I_C_Order;

import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Handles the relation between {@link I_C_Order} and Tour Planning module.
 *
 * @author tsa
 *
 */
public interface IOrderDeliveryDayBL extends ISingletonService
{
	/**
	 * Set Preparation Date from matching {@link I_M_DeliveryDay} if possible.
	 *
	 * Preparation Date won't be set if the value of fields on which depends are not set or if the order is already processed.
	 *
	 * @param order
	 * @param fallbackToDatePromised if the computed preparationDate is already in the past, then let this parameter decide if the PreparationDate remains empty or is filled with the given
	 *            <code>order</code>'s <code>DatePromised</code> value (task 08931).
	 * @return true if preparation date was set
	 */
	boolean setPreparationDateAndTour(I_C_Order order, boolean fallbackToDatePromised);

	/**
	 * Computes the preparation date for the given {@code order} as if its delivery date were the given
	 * {@code deliveryDate}, applying the exact same tour-found / no-tour-fallback / offset / sysconfig logic that
	 * {@link #setPreparationDateAndTour(I_C_Order, boolean)} uses for the order header.
	 * <p>
	 * This lets callers derive a <i>per-line</i> preparation date (e.g. for a per-line delivery date) that is consistent
	 * with the header value: passing {@code deliveryDate == order.getDatePromised()} yields the same preparation date the
	 * header carries (for system/OLCand-created orders). Does NOT mutate {@code order}.
	 *
	 * @return the computed preparation date, or {@code null} when there is no usable tour and the fallback is disabled.
	 */
	@Nullable
	ZonedDateTime computePreparationDate(@NonNull I_C_Order order, @NonNull ZonedDateTime deliveryDate);

}
