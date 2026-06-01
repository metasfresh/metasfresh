package de.metas.document.archive.notification.delay.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayHandler;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.ClientAndOrgId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.List;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

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

		for (final InOutId inOutId : retrieveShipmentIds(invoiceId))
		{
			final I_M_InOut inOut = inOutDAO.getById(inOutId);
			final int shipperRepoId = inOut.getM_Shipper_ID();
			if (shipperRepoId <= 0)
			{
				// no shipper assigned → not carrier-relevant, skip
				continue;
			}

			if (!shipperDAO.getShipperGatewayId(ShipperId.ofRepoId(shipperRepoId)).isPresent())
			{
				// shipper has no gateway configured → no tracking expected, skip
				continue;
			}

			if (isCarrierTrackingPending(inOutId))
			{
				return true;
			}
		}

		return false;
	}

	/** @return the distinct shipments ({@code M_InOut}) the invoice's lines were derived from. */
	private ImmutableSet<InOutId> retrieveShipmentIds(@NonNull final InvoiceId invoiceId)
	{
		final ImmutableSet<Integer> inOutLineIds = queryBL
				.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addCompareFilter(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, Operator.GREATER, 0)
				.addOnlyActiveRecordsFilter()
				.create()
				.list()
				.stream()
				.map(I_C_InvoiceLine::getM_InOutLine_ID)
				.collect(ImmutableSet.toImmutableSet());

		if (inOutLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL
				.createQueryBuilder(I_M_InOutLine.class)
				.addInArrayFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, inOutLineIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list()
				.stream()
				.map(I_M_InOutLine::getM_InOut_ID)
				.filter(inOutRepoId -> inOutRepoId > 0)
				.map(InOutId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Steps 2 + 3: whether carrier tracking is still pending for the given shipment.
	 *
	 * @return {@code true} if we should delay (no packages yet, or any tracking URL still blank)
	 */
	private boolean isCarrierTrackingPending(@NonNull final InOutId inOutId)
	{
		// Step 2: a gateway shipment with no packages yet means the carrier has not been invoked.
		final List<I_M_ShippingPackage> packages = queryBL
				.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID, inOutId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		if (packages.isEmpty())
		{
			return true;
		}

		// Step 3: every package's shipment order must have parcels that all carry a tracking URL.
		for (final I_M_ShippingPackage shippingPackage : packages)
		{
			final int shipperTransportationId = shippingPackage.getM_ShipperTransportation_ID();
			if (shipperTransportationId <= 0)
			{
				return true;
			}

			final List<I_Carrier_ShipmentOrder> shipmentOrders = queryBL
					.createQueryBuilder(I_Carrier_ShipmentOrder.class)
					.addEqualsFilter(I_Carrier_ShipmentOrder.COLUMNNAME_M_ShipperTransportation_ID, shipperTransportationId)
					.addOnlyActiveRecordsFilter()
					.create()
					.list();

			if (shipmentOrders.isEmpty())
			{
				return true;
			}

			for (final I_Carrier_ShipmentOrder shipmentOrder : shipmentOrders)
			{
				final List<I_Carrier_ShipmentOrder_Parcel> parcels = queryBL
						.createQueryBuilder(I_Carrier_ShipmentOrder_Parcel.class)
						.addEqualsFilter(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_Carrier_ShipmentOrder_ID, shipmentOrder.getCarrier_ShipmentOrder_ID())
						.addOnlyActiveRecordsFilter()
						.create()
						.list();

				if (parcels.isEmpty())
				{
					return true;
				}

				for (final I_Carrier_ShipmentOrder_Parcel parcel : parcels)
				{
					if (Check.isBlank(parcel.getTrackingURL()))
					{
						return true;
					}
				}
			}
		}

		return false;
	}
}
