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
	 * Resolves the per-line preparation date for the shipment-schedule provider: if
	 * {@code C_OrderLine.PreparationDate} is set (normally the derived value written back by
	 * {@link #setLinePreparationDate}) it is returned as-is; otherwise it is derived fresh from the given
	 * {@code deliveryDate} using the same tour / no-tour-fallback / offset / sysconfig logic the order header uses.
	 * Does NOT mutate the line. Preparation-date overrides live on {@code M_ShipmentSchedule.PreparationDate_Override},
	 * not on the line.
	 *
	 * @return the stored per-line preparation date (if set), else the derived preparation date (which may be
	 *         {@code null} when there is no usable tour and the fallback is disabled).
	 */
	@Nullable
	ZonedDateTime computePreparationDate(@NonNull I_C_Order order, @NonNull I_C_OrderLine orderLine, @NonNull ZonedDateTime deliveryDate);

	/**
	 * The effective per-line delivery date: an explicit {@code C_OrderLine.PresetDateShipped} wins, then the line's
	 * own {@code DatePromised}, finally the order header's {@code DatePromised}. This is the single source of truth for
	 * the delivery date used both to build the shipment schedule and to (re)derive the line's {@code PreparationDate}.
	 *
	 * @throws org.adempiere.exceptions.AdempiereException if no delivery date can be determined.
	 */
	ZonedDateTime computeDeliveryDate(@NonNull I_C_Order order, @NonNull I_C_OrderLine orderLine);

	/**
	 * Sales side: (re)derive and store this order line's {@code PreparationDate} from its own delivery date
	 * ({@link #computeDeliveryDate}), so the line always reflects the value the shipment schedule will receive
	 * initially. User overrides live on {@code M_ShipmentSchedule.PreparationDate_Override}, never on the line — hence
	 * the line is always derived, never preserved. No-op for purchase or processed orders. Does NOT save the line.
	 *
	 * @return true if a preparation date was set on the line.
	 */
	boolean setLinePreparationDate(@NonNull I_C_OrderLine orderLine);

	/**
	 * Purchase side: a line's product change can change the order's tour, so recompute the order header's
	 * preparation date + tour from the given line and save the order. No-op for sales orders.
	 *
	 * @return true if a preparation date was set on the order header.
	 */
	boolean updatePurchaseHeaderPreparationDate(@NonNull I_C_OrderLine orderLine);

}
