package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayHandler;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.organization.ClientAndOrgId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

/**
 * Delays the shipment ({@code M_InOut}) notification email until the carrier tracking link is
 * available — controlled by SysConfig {@link #SYSCONFIG_DelayUntilCarrierConfirmed}.
 *
 * <p>The notification handled here is the shipment's own doc-outbound notification: the
 * {@code C_Doc_Outbound_Log.Record_ID} is the {@code M_InOut_ID}. The gate releases as soon as the
 * shipment has a tracking link to render — i.e. the {@code M_InOut.TrackingURL} virtual column (the
 * <em>same</em> value the email body renders) is non-blank. The check:
 * <ol>
 *   <li>only customer shipments ({@code IsSOTrx='Y'}) — vendor receipts carry no customer notification;</li>
 *   <li>only shipments whose shipper has a configured gateway ({@code M_Shipper.ShipperGateway}) —
 *       without a carrier gateway no tracking link is ever expected, so don't hold;</li>
 *   <li>then delay while {@code M_InOut.TrackingURL} is still blank (carrier hasn't returned tracking yet).</li>
 * </ol>
 * Releasing on the first available tracking link (rather than requiring every parcel to be tracked)
 * keeps the gate aligned with what the email actually renders and avoids holding the mail for the
 * full timeout when an individual parcel never receives a tracking number.</p>
 */
@Component
public class InOutNotificationDelayHandler implements DocOutboundNotificationDelayHandler
{
	public static final String SYSCONFIG_DelayUntilCarrierConfirmed = "delayNotificationUntilShipmentConfirmedByCarrier";

	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
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
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(log.getAD_Client_ID(), log.getAD_Org_ID());
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_DelayUntilCarrierConfirmed, false, clientAndOrgId))
		{
			return false;
		}

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

		// carrier-tracked shipment: hold until the tracking link the email will render is available.
		// getTrackingURL() is the @Deprecated lazy virtual-column accessor — intentional here: one read
		// per notification check (not a loop), and it is exactly the value the email body renders.
		@SuppressWarnings("deprecation")
		final String trackingURL = inOut.getTrackingURL();
		return Check.isBlank(trackingURL);
	}
}
