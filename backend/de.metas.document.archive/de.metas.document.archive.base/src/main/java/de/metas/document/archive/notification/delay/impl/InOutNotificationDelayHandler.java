package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayHandler;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.organization.ClientAndOrgId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

/**
 * Delays the shipment ({@code M_InOut}) notification email until carrier tracking URLs are
 * available for the shipment — controlled by SysConfig {@link #SYSCONFIG_DelayUntilCarrierConfirmed}.
 *
 * <p>The notification handled here is the shipment's own doc-outbound notification: the
 * {@code C_Doc_Outbound_Log.Record_ID} is the {@code M_InOut_ID}. The readiness check:
 * <ol>
 *   <li>If the {@code M_InOut} is not a customer shipment ({@code IsSOTrx='N'}, e.g. a vendor
 *       receipt) it carries no customer tracking-link notification — <em>send</em>.</li>
 *   <li>If the shipment's shipper has no configured gateway ({@code M_Shipper.ShipperGateway} is
 *       blank) no carrier tracking is expected — <em>send</em>.</li>
 *   <li>If a gateway is configured but no {@code M_ShippingPackage} rows exist for the shipment
 *       yet, the carrier has not been invoked — <em>delay</em>.</li>
 *   <li>If packages exist, every reachable {@code Carrier_ShipmentOrder_Parcel} must carry a
 *       non-blank {@code TrackingURL}. Any missing URL — or any package/order/parcel gap in the
 *       chain — causes a <em>delay</em>.</li>
 * </ol>
 * When none of the above conditions causes a delay, the notification is released (returns {@code false}).</p>
 */
@Component
public class InOutNotificationDelayHandler implements DocOutboundNotificationDelayHandler
{
	public static final String SYSCONFIG_DelayUntilCarrierConfirmed = "delayNotificationUntilShipmentConfirmedByCarrier";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final CarrierTrackingDelayRepository carrierTrackingDelayRepository;

	public InOutNotificationDelayHandler(@NonNull final CarrierTrackingDelayRepository carrierTrackingDelayRepository)
	{
		this.carrierTrackingDelayRepository = carrierTrackingDelayRepository;
	}

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
			return false; // only customer shipments carry a tracking-link notification; vendor receipts (IsSOTrx='N') do not
		}

		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(inOut.getM_Shipper_ID());
		if (shipperId == null)
		{
			return false; // no shipper assigned → not carrier-relevant
		}

		if (!shipperDAO.getShipperGatewayId(shipperId).isPresent())
		{
			return false; // shipper has no gateway configured → no tracking expected
		}

		return carrierTrackingDelayRepository.isCarrierTrackingPending(inOutId);
	}
}
