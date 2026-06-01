package de.metas.document.archive.notification.delay.impl;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.notification.delay.DocOutboundNotificationDelayHandler;
import de.metas.organization.ClientAndOrgId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_Carrier_ShipmentOrder;
import org.compiere.model.I_Carrier_ShipmentOrder_Parcel;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	@Override
	public String getTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	public boolean shouldDelaySending(@NonNull final I_C_Doc_Outbound_Log log)
	{
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_DelayUntilCarrierConfirmed, false, ClientAndOrgId.ofClientAndOrg(log.getAD_Client_ID(), log.getAD_Org_ID())))
		{
			return false;
		}

		final int invoiceId = log.getRecord_ID();
		if (invoiceId <= 0)
		{
			return false;
		}

		// Collect distinct M_InOutLine_IDs from invoice lines that have a shipment link
		final List<I_C_InvoiceLine> invoiceLinesWithInOutLine = queryBL
				.createQueryBuilder(I_C_InvoiceLine.class)
				.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addCompareFilter(I_C_InvoiceLine.COLUMNNAME_M_InOutLine_ID, CompareQueryFilter.Operator.GREATER, 0)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final Set<Integer> inOutLineIds = new HashSet<>();
		for (final I_C_InvoiceLine invoiceLine : invoiceLinesWithInOutLine)
		{
			inOutLineIds.add(invoiceLine.getM_InOutLine_ID());
		}

		if (inOutLineIds.isEmpty())
		{
			return false;
		}

		// ONE batched query for all inout lines → collect distinct M_InOut_IDs
		final List<I_M_InOutLine> inOutLines = queryBL
				.createQueryBuilder(I_M_InOutLine.class)
				.addInArrayFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, inOutLineIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final Set<Integer> inOutIds = new HashSet<>();
		for (final I_M_InOutLine inOutLine : inOutLines)
		{
			if (inOutLine.getM_InOut_ID() > 0)
			{
				inOutIds.add(inOutLine.getM_InOut_ID());
			}
		}

		for (final int inOutId : inOutIds)
		{
			final I_M_InOut inOut = InterfaceWrapperHelper.load(inOutId, I_M_InOut.class);
			if (inOut == null)
			{
				continue;
			}

			final int shipperId = inOut.getM_Shipper_ID();
			if (shipperId <= 0)
			{
				// no shipper assigned → not carrier-relevant, skip
				continue;
			}

			if (!shipperDAO.getShipperGatewayId(ShipperId.ofRepoId(shipperId)).isPresent())
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

	/**
	 * Steps 2 + 3: check whether carrier tracking is still pending for the given inout.
	 *
	 * @return {@code true} if we should delay (packages missing or any tracking URL blank)
	 */
	private boolean isCarrierTrackingPending(final int inOutId)
	{
		// Step 2: check whether any packages exist for this inout
		final List<I_M_ShippingPackage> packages = queryBL
				.createQueryBuilder(I_M_ShippingPackage.class)
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_M_InOut_ID, inOutId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		if (packages.isEmpty())
		{
			// gateway configured but carrier not yet invoked → delay
			return true;
		}

		// Step 3: check that every package has a shipment order with parcels that all have tracking URLs
		for (final I_M_ShippingPackage pkg : packages)
		{
			final int transportationId = pkg.getM_ShipperTransportation_ID();
			if (transportationId <= 0)
			{
				return true;
			}

			final List<I_Carrier_ShipmentOrder> orders = queryBL
					.createQueryBuilder(I_Carrier_ShipmentOrder.class)
					.addEqualsFilter(I_Carrier_ShipmentOrder.COLUMNNAME_M_ShipperTransportation_ID, transportationId)
					.addOnlyActiveRecordsFilter()
					.create()
					.list();

			if (orders.isEmpty())
			{
				return true;
			}

			for (final I_Carrier_ShipmentOrder order : orders)
			{
				final List<I_Carrier_ShipmentOrder_Parcel> parcels = queryBL
						.createQueryBuilder(I_Carrier_ShipmentOrder_Parcel.class)
						.addEqualsFilter(I_Carrier_ShipmentOrder_Parcel.COLUMNNAME_Carrier_ShipmentOrder_ID, order.getCarrier_ShipmentOrder_ID())
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
