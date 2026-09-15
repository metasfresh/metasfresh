package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayHandler;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

/**
 * Answers whether the shipment ({@code M_InOut}) notification email is <b>not yet ready</b> to send
 * because the carrier tracking link is still missing.
 *
 * <p>The on/off switch and the maximum wait time are owned by the mail workpackage processor via
 * SysConfig {@code mailNotificationMaxDelayMillis} (0 = never delay). This handler only answers the
 * readiness question for {@code M_InOut} (the doc-outbound log's {@code Record_ID} is the
 * {@code M_InOut_ID}):
 * <ol>
 *   <li>not a customer shipment ({@code IsSOTrx='N'}, e.g. a vendor receipt) → ready (don't delay);</li>
 *   <li>shipper has no configured gateway ({@code M_Shipper.ShipperGateway}) → no carrier tracking
 *       expected → ready;</li>
 *   <li>otherwise not ready while {@code M_InOut.TrackingURL} — the same value the email body renders —
 *       is still blank.</li>
 * </ol>
 */
@Component
public class InOutNotificationDelayHandler implements DocOutboundNotificationDelayHandler
{
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	@Override
	public String getTableName()
	{
		return I_M_InOut.Table_Name;
	}

	@Override
	public boolean shouldDelaySending(@NonNull final I_C_Doc_Outbound_Log log)
	{
		final InOutId inOutId = InOutId.ofRepoIdOrNull(log.getRecord_ID());
		if (inOutId == null)
		{
			return false;
		}

		final I_M_InOut inOut = inOutDAO.getById(inOutId);
		if (!inOut.isSOTrx())
		{
			return false; // vendor receipts carry no customer tracking-link notification
		}

		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(inOut.getM_Shipper_ID());
		if (shipperId == null)
		{
			return false; // no shipper assigned → not carrier-relevant
		}

		if (!shipperDAO.getShipperGatewayId(shipperId).isPresent())
		{
			return false; // shipper has no gateway configured → no tracking link expected
		}

		// carrier-tracked shipment: not ready until the tracking link the email renders is available.
		// getTrackingURL() is the @Deprecated lazy virtual-column accessor — intentional: one read per
		// readiness check, and it is exactly the value the email body renders.
		@SuppressWarnings("deprecation")
		final String trackingURL = inOut.getTrackingURL();
		return Check.isBlank(trackingURL);
	}
}
