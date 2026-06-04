package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayHandler;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.ClientAndOrgId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

/**
 * Delays invoice notification until tracking URLs are available for all carrier-tracked shipments
 * linked to the invoice — controlled by SysConfig {@link #SYSCONFIG_DelayUntilCarrierConfirmed}.
 *
 * <p>The check is per {@code M_InOut} linked to the invoice via its invoice lines:
 * <ol>
 *   <li>If the inout's shipper has no configured gateway ({@code M_Shipper.ShipperGateway} is
 *       blank) the inout is skipped — no tracking is expected for it.</li>
 *   <li>If a gateway is configured but no {@code M_ShippingPackage} rows exist for the inout yet,
 *       the carrier has not been invoked — <em>delay</em>.</li>
 *   <li>If packages exist, every reachable {@code Carrier_ShipmentOrder_Parcel} must carry a
 *       non-blank {@code TrackingURL}.  Any missing URL — or any package/order/parcel gap in the
 *       chain — causes a <em>delay</em>.</li>
 * </ol>
 * When all gateway-inouts satisfy condition 3, the notification is released (returns
 * {@code false}).</p>
 */
@Component
public class InvoiceNotificationDelayHandler implements DocOutboundNotificationDelayHandler
{
	public static final String SYSCONFIG_DelayUntilCarrierConfirmed = "delayNotificationUntilShipmentConfirmedByCarrier";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final CarrierTrackingDelayRepository carrierTrackingDelayRepository;

	public InvoiceNotificationDelayHandler(@NonNull final CarrierTrackingDelayRepository carrierTrackingDelayRepository)
	{
		this.carrierTrackingDelayRepository = carrierTrackingDelayRepository;
	}

	@Override
	public String getTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	public boolean shouldDelaySending(@NonNull final I_C_Doc_Outbound_Log log)
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(log.getAD_Client_ID(), log.getAD_Org_ID());
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_DelayUntilCarrierConfirmed, false, clientAndOrgId))
		{
			return false;
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(log.getRecord_ID());
		if (invoiceId == null)
		{
			return false;
		}

		for (final InOutId inOutId : carrierTrackingDelayRepository.retrieveShipmentIds(invoiceId))
		{
			final I_M_InOut inOut = inOutDAO.getById(inOutId);
			final ShipperId shipperId = ShipperId.ofRepoIdOrNull(inOut.getM_Shipper_ID());
			if (shipperId == null)
			{
				continue; // no shipper assigned → not carrier-relevant
			}

			if (!shipperDAO.getShipperGatewayId(shipperId).isPresent())
			{
				continue; // shipper has no gateway configured → no tracking expected
			}

			if (carrierTrackingDelayRepository.isCarrierTrackingPending(inOutId))
			{
				return true;
			}
		}

		return false;
	}
}
