package de.metas.tourplanning.api;

import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

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
	 * Resolves a per-line preparation date: if the order line carries an explicit {@code C_OrderLine.PreparationDate}
	 * override it is returned verbatim; otherwise the preparation date is derived from the given {@code deliveryDate}
	 * using the same tour / no-tour-fallback / offset / sysconfig logic the order header uses. This is the single owner
	 * of the override-or-derive decision — callers must not branch on the override themselves. Does NOT mutate the line.
	 *
	 * @return the explicit per-line override (if set), else the derived preparation date (which may be {@code null}
	 *         when there is no usable tour and the fallback is disabled).
	 */
	@Nullable
	ZonedDateTime computePreparationDate(@NonNull I_C_Order order, @NonNull I_C_OrderLine orderLine, @NonNull ZonedDateTime deliveryDate);

}
