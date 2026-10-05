package de.metas.order;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;

/**
 * Resolves the partner that the goods of an order are shipped to.
 */
@UtilityClass
public class OrderShipmentBPartners
{
	/**
	 * @return the order's drop-ship partner, else the order's partner; {@code null} if there is no order.
	 */
	@Nullable
	public static BPartnerId extractShipmentBPartnerId(@Nullable final OrderId orderId)
	{
		if (orderId == null)
		{
			return null;
		}

		return extractShipmentBPartnerId(Services.get(IOrderDAO.class).getById(orderId));
	}

	@NonNull
	public static BPartnerId extractShipmentBPartnerId(@NonNull final I_C_Order order)
	{
		final BPartnerId dropShipBPartnerId = BPartnerId.ofRepoIdOrNull(order.getDropShip_BPartner_ID());
		return dropShipBPartnerId != null ? dropShipBPartnerId : BPartnerId.ofRepoId(order.getC_BPartner_ID());
	}
}
